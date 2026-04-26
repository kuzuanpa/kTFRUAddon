/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */

package cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.generator;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.cover.ICover;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import gregapi.util.UT;
import gregapi.util.WD;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import zmaster587.libVulpes.items.ItemProjector;

import static gregapi.data.CS.*;

public class SunHeaterMirrorLarge extends TileEntityBase10MultiBlockBase implements IMultiTileEntity.IMTE_SyncDataByteArray {
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.sunheater.mirror.large";
    }
    public ChunkCoordinates targetSunBoilerPos=null;
    public SunHeater target;
    public float rotateHorizontal,rotateVertical,rotateHorizontalToMove,rotateVerticalToMove;
    public long generateRate=0;
    public boolean isValid=true;

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_TARGET)) {
            targetSunBoilerPos=new ChunkCoordinates(UT.Code.bindInt(aNBT.getLong(NBT_TARGET_X)),UT.Code.bindInt(aNBT.getLong(NBT_TARGET_Y)),UT.Code.bindInt(aNBT.getLong(NBT_TARGET_Z)));
        }
    }

    @Override
    public boolean allowCover(byte aSide, ICover aCover) {
        return false;
    }

    @Override
    public int getLightOpacity(){
        return 1;
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        if (targetSunBoilerPos != null) {
            UT.NBT.setBoolean(aNBT, NBT_TARGET, T);
            UT.NBT.setNumber(aNBT, NBT_TARGET_X, targetSunBoilerPos.posX);
            UT.NBT.setNumber(aNBT, NBT_TARGET_Y, targetSunBoilerPos.posY);
            UT.NBT.setNumber(aNBT, NBT_TARGET_Z, targetSunBoilerPos.posZ);
        }
    }

    public boolean[] getValidSides() {return SIDES_BACK;}
    @Override
    public void onTick2(long aTimer, boolean isServerside){
        if(isServerside&&target!=null) ITileEntityEnergy.Util.insertEnergyInto(TD.Energy.HU,SIDE_BOTTOM,generateRate,1,this,target);

        if(isServerside) return;

        if(targetSunBoilerPos==null||!isValid){
            rotateVerticalToMove=0;
            return;
        }

        if (aTimer%10==0) updateRotates();

        float f1 =rotateVertical - rotateVerticalToMove;
        float f2 =rotateHorizontal - rotateHorizontalToMove;

        if(f1>0.01)rotateVertical-=f1>10?1:(f1/10);
        if(f1<0.01)rotateVertical-=f1<-10?-1:(f1/10);
        if(f2>0.01)rotateHorizontal-=f2>15?1.5F:(f2/10);
        if(f2<0.01)rotateHorizontal-=f2<-15?-1.5F:(f2/10);
    }
    @SideOnly(Side.CLIENT)
    public void updateRotates(){
        long Ti= (getWorldObj().getWorldTime()) % getDayTotalTime();

        if(Ti > getDayTotalTime()/2){
            rotateVerticalToMove =0;
            return;
        }

        int X2A = targetSunBoilerPos.posX - xCoord, Y2A = targetSunBoilerPos.posY + 3 - yCoord, Z2A = targetSunBoilerPos.posZ - zCoord;

        double L = Math.sqrt(X2A * X2A + Y2A * Y2A + Z2A * Z2A);
        double X2 = X2A / L, Y2 = Y2A / L, Z2 = Z2A / L;
        double alpha = Ti / (getDayTotalTime() / 2F)  * 3.14159, theta = 0, phi = 0;
        double X1 = Math.cos(alpha), Y1 = Math.sin(alpha);
        double[][] coefficient = {
                {Y1 * Z2, -X1 * Z2, (-Y1 * X2 + X1 * Y2)},
                {(X1 - X2), (Y1 - Y2), -Z2}
        };

        Matrix A = new Matrix(coefficient);
        Matrix mATA = A.transpose().times(A);
        EigenvalueDecomposition E = mATA.eig();

        //特征值
        Matrix mD = E.getD();
        //特征向量
        Matrix mV = E.getV();

        //检测哪一行特征值是0
        int index = 0;
        for (int i = 0; i < 3; i++) {
            double c = mD.get(i, i);
            if (c <= 1e-6 && c >= -1e-6) {
                index = i;
                break;
            }
        }
        double Xn=0,Yn=0,Zn=0;

        Xn = mV.get(0, index);
        Yn = mV.get(1, index);
        Zn = mV.get(2, index);

        L = Math.sqrt((Xn * Xn + Yn * Yn + Zn * Zn));

        if (Yn < 0) L = -L;
        Xn = Xn / L;
        Yn = Yn / L;
        Zn = Zn / L;

        phi = Math.acos(Yn) * 180/3.14159 ;
        theta = Math.asin(Math.abs(Zn) / Math.sqrt(1 - Yn * Yn)) * 180/3.14159 ;

        if(Xn < 0) theta = 180 - theta;
        if(Zn < 0) theta = -theta;

        rotateVerticalToMove = (float) (phi);
        rotateHorizontalToMove = (float) (theta);
    }

    @Override
    public boolean onTickCheck(long aTimer) {
        super.onTickCheck(aTimer);
        if(aTimer%20!=0 || !isServerSide() || targetSunBoilerPos==null)return false;

        TileEntity te = WD.te(worldObj,targetSunBoilerPos.posX,targetSunBoilerPos.posY,targetSunBoilerPos.posZ, false);
        if(te instanceof SunHeater) target= (SunHeater) te;
        else {
            targetSunBoilerPos=null;
            return false;
        }
        isValid = true;
        for (int x = -1; x < 1; x++)  for (int z = -1; z < 1; z++) {
            if(!worldObj.canBlockSeeTheSky(xCoord+x, yCoord+3, zCoord+z))isValid = false;
        }
        if (target!=null && !isValid){
            generateRate=0;
            return false;
        }

        int currentTime = (int) getWorldObj().getWorldTime() % getDayTotalTime() ;
        int halfDayTime = getDayTotalTime()/2;
        generateRate = currentTime > halfDayTime ? 0 : (int) (160+ 1120* (1-(Math.abs ( halfDayTime - currentTime ) / (float)halfDayTime)));
        return isValid;
    }
    public int getDayTotalTime(){
        return 24000;
    }
    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        if(targetSunBoilerPos==null)return getClientDataPacketByteArray(aSendAll,(byte)UT.Code.getR(mRGBa), (byte)UT.Code.getG(mRGBa), (byte)UT.Code.getB(mRGBa),getVisualData(),getDirectionData());
        return getClientDataPacketByteArray(aSendAll,(byte)UT.Code.getR(mRGBa), (byte)UT.Code.getG(mRGBa), (byte)UT.Code.getB(mRGBa),getVisualData(),getDirectionData(),UT.Code.toByteI(targetSunBoilerPos.posX,0),UT.Code.toByteI(targetSunBoilerPos.posX,1),UT.Code.toByteI(targetSunBoilerPos.posY,0),UT.Code.toByteI(targetSunBoilerPos.posY,1),UT.Code.toByteI(targetSunBoilerPos.posZ,0),UT.Code.toByteI(targetSunBoilerPos.posZ,1));
    }
    @Override
    public boolean breakBlock() {
        targetSunBoilerPos=null;
        target=null;
        structure.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.RESET, worldObj, xCoord, yCoord, zCoord, mFacing, null, null));
        return super.breakBlock();
    }
    @Override
    public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler){
        mRGBa = UT.Code.getRGBInt(new short[] {UT.Code.unsignB(aData[0]), UT.Code.unsignB(aData[1]), UT.Code.unsignB(aData[2])});
        setVisualData(aData[3]);
        setDirectionData(aData[4]);
        if(aData.length>5)targetSunBoilerPos=new ChunkCoordinates(UT.Code.combine(aData[5],aData[6]),UT.Code.combine(aData[7],aData[8]),UT.Code.combine(aData[9],aData[10]));
        return true;
    }
    @Override
    public boolean canDrop(int aSlot) {return true;}

    //Structure
    ChunkCoordinates lastFailedPos=null;
    static IStringBaseStructure structure = new LayerStructure(StructureContext.Axis.Y).layerRule("ABC")
            .fixedLayer('A',
                    "WWW",
                    "W W",
                    "WWW"
            ).fixedLayer('B',
                    "   ",
                    " W ",
                    "   "
            ).fixedLayer('C',
                    "MMM",
                    "MMM",
                    "MMM"
            )
            .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31050, MultiTileEntityMultiBlockPart.NOTHING, 1)))
            .where('M', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31051, MultiTileEntityMultiBlockPart.NOTHING, 1)))
            .setOffset(-1,0,-1);

    @Override
    public boolean isInsideStructure(int i, int i1, int i2) {
        return true;
    }

    public static IIconContainer sTextureCommon= new Textures.BlockIcons.CustomIcon("machines/multiblockmains/transformer/common"),
            sOverlayFront= new Textures.BlockIcons.CustomIcon("machines/multiblockmains/transformer/front");
    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon,mRGBa), aSide==mFacing? BlockTextureDefault.get(sOverlayFront) : null);
    }
    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        lastFailedPos = structure.checkStructure(new StructureContext(this, (aPlayer != null || aInventory != null)? StructureContext.StringBaseMode.SET: StructureContext.StringBaseMode.CHECK, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));

        if(lastFailedPos!=null)structure.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.RESET, worldObj, xCoord, yCoord, zCoord, mFacing, null, null));

        return lastFailedPos==null;
    }
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide())return true;

        if(!mStructureOkay){
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED+LH.get(I18nHandler.STRUCTURE_ERR)));
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.YELLOW+LH.get("ktfru.structure.complex.tip")));
        }

        ItemStack equippedItem=aPlayer.getCurrentEquippedItem();
        if (equippedItem!=null && equippedItem.getItem() instanceof ItemProjector) {
            structure.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
            return true;
        }

        if(!isValid) {
            aPlayer.addChatMessage(new ChatComponentText(LH.get(I18nHandler.SUN_BOILER_MIRROR_ERR)));
            return false;
        }

        if (equippedItem!=null && equippedItem.hasTagCompound() && equippedItem.getTagCompound().hasKey(NBT_USB_DATA)) {
            NBTTagCompound aNBT = equippedItem.getTagCompound().getCompoundTag(NBT_USB_DATA);
            targetSunBoilerPos = new ChunkCoordinates(UT.Code.bindInt(aNBT.getLong(NBT_TARGET_X)), UT.Code.bindInt(aNBT.getLong(NBT_TARGET_Y)), UT.Code.bindInt(aNBT.getLong(NBT_TARGET_Z)));
            if (worldObj.getTileEntity(targetSunBoilerPos.posX, targetSunBoilerPos.posY, targetSunBoilerPos.posZ) instanceof SunHeater) {
                target= (SunHeater) worldObj.getTileEntity(targetSunBoilerPos.posX, targetSunBoilerPos.posY, targetSunBoilerPos.posZ);
                updateClientData();
                aPlayer.addChatMessage(new ChatComponentText(LH.get(I18nHandler.SUN_BOILER_MIRROR) + targetSunBoilerPos.posX + "," + targetSunBoilerPos.posY + "," + targetSunBoilerPos.posZ));
            } else targetSunBoilerPos = null;
            return true;
        }
        return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
    }
}
