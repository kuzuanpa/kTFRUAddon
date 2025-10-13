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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.storage;

import cn.kuzuanpa.ktfruaddon.api.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.layerType.ExpandableLayer;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.SpecialPartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.parts.TransformerPart;
import gregapi.block.multitileentity.IWailaTile;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.util.WD;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static gregapi.data.CS.*;

public class WirelessBattery extends MultiBatteryBase implements SpecialPartPredicate.IReceiveSpecialPart, IWailaTile {
    public List<ChunkCoordinates> receiverPosList = new ArrayList<>();

    public boolean sealed = false;

    //Structure
    ChunkCoordinates lastFailedPos=null;
    static final IStringBaseStructure structure  = new LayerStructure(StructureContext.Axis.Y).layerRule("A")
            .layer('A',
                    new ExpandableLayer(6)
                            .variation("CXXX",
                                    "CXXX",
                                    " XXX",
                                    "CXXX",
                                    "CXXX"
                            ).variation("CCCC",
                                    "CXXX",
                                    " CXX",
                                    "CXXX",
                                    "CCCC"
                            )
            )
            .where('X', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18002)))
            .where('C', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18006)))
            .setOffset(-2,0,0);
    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        lastFailedPos = structure.checkStructure(new StructureContext(this, (aPlayer != null || aInventory != null)? StructureContext.StringBaseMode.SET: StructureContext.StringBaseMode.CHECK, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        return lastFailedPos==null;
    }
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide())return true;

        if(!mStructureOkay)aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED+LH.get(I18nHandler.STRUCTURE_ERR)));

        ItemStack equippedItem=aPlayer.getCurrentEquippedItem();
        if (equippedItem!=null && equippedItem.getItem() instanceof ItemProjector) {
            structure.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
            return true;
        }
        return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
    }
    public final short sizeX = 5, sizeY = 1, sizeZ = 4;
    //决定结构检测的起始位置，默认情况下是从主方块起始
    //This controls where is the start point to check structure,Default is the position of controller block
    public final short xMapOffset = -2, zMapOffset = 0;
    //这里是设置该机器的内部区域
    //controls areas inside the machine
    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return new BoundingBox(utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset),yCoord,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset),utils.getRealX(mFacing,utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset), sizeX, sizeZ),yCoord+ sizeY,utils.getRealZ(mFacing,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset), sizeX, sizeZ)).isXYZInBox(aX,aY,aZ);
    }
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        int[] pos = aNBT.getIntArray("ktfru.receiver.list");
        for (int i = 0; i < pos.length/3; i++) {
            int index = i*3;
            receiverPosList.add(new ChunkCoordinates(pos[index], pos[index+1], pos[index+2]));
        }
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        int[] pos = new int[receiverPosList.size()];
        for (int i = 0; i < receiverPosList.size(); i++) {
            int index = i*3;
            pos[index] = receiverPosList.get(i).posX;
            pos[index+1] = receiverPosList.get(i).posY;
            pos[index+2] = receiverPosList.get(i).posZ;
        }
        aNBT.setIntArray("ktfru.receiver.list", pos);
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if(aTool.equals(TOOL_magnifyingglass)){
            if(mCapacity > 0 )aChatReturn.add(String.format("%.4f", mEnergyStored*100F/mCapacity) + " %");
            aChatReturn.add(mEnergyStored + " / " +mCapacity + mEnergyType.getLocalisedChatNameShort());
            if(!aSneaking){
                aChatReturn.add(LH.get("ktfru.msg.sneak.to.see.receivers"));
                return 1L;
            }
            receiverPosList.forEach(receiver -> aChatReturn.add(receiver.posX+", "+receiver.posY+", "+receiver.posZ));
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if (aIsServerSide && sealed && checkStructure(false)) {
            receiverPosList.stream().map(pos-> (TransformerPart)WD.te(worldObj, pos,false)).filter(Objects::nonNull).filter(tile-> mEnergyStored > tile.mOutputVoltage * tile.mOutputAmpere).forEach(tile-> mEnergyStored -= tile.mOutputVoltage * tile.doInject(mEnergyTypeOut, SIDE_INSIDE, tile.mOutputVoltage, tile.mOutputAmpere,true));
        }
    }

    @Override
    public boolean isEnergyAcceptingFrom(TagData aEnergyType, byte aSide, boolean aTheoretical) {
        return super.isEnergyAcceptingFrom(aEnergyType, aSide, aTheoretical);
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        super.addToolTips(aList, aStack, aF3_H);
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.storage.wireless";
    }

    @Override
    public boolean isUseableByPlayerGUI(EntityPlayer aPlayer) {
        return super.isUseableByPlayerGUI(aPlayer);
    }

    @Override public boolean isInput(byte aSide) {return true;}

    @Override
    public boolean[] getValidSides() {
        return SIDES_HORIZONTAL;
    }

    public static IIconContainer sTextureCommon= new Textures.BlockIcons.CustomIcon("machines/multiblockmains/transformer/common"),
            sOverlayFront= new Textures.BlockIcons.CustomIcon("machines/multiblockmains/transformer/front");
    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon,mRGBa), aSide==mFacing? BlockTextureDefault.get(sOverlayFront) : null);
    }

    @Override
    public IWailaInfoProvider[] getWailaInfos() {
        return instanceInfoEnergyIORange.asArray();
    }

    @Override
    public NBTTagCompound getWailaNBT(TileEntity te, NBTTagCompound aNBT) {
        IWailaTile.super.getWailaNBT(te, aNBT);

        return aNBT;
    }

    @Override
    public List<String> getWailaBody(List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        IWailaTile.super.getWailaBody(currentTip, accessor, config);

        return currentTip;
    }

    @Override
    public void receiveSpecialPart(ChunkCoordinates partPos, TileEntity part) {
        receiverPosList.add(partPos);
    }
}
