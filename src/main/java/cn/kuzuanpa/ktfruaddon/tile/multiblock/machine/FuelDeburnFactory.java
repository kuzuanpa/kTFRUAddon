/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.

 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.multiblock.machine;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import gregapi.code.TagData;
import gregapi.computer.ITileEntityComputerizable;
import gregapi.data.IL;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.fluid.FluidTankGT;
import gregapi.old.Textures;
import gregapi.oredict.OreDictManager;
import gregapi.oredict.OreDictMaterialStack;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.data.ITileEntityTemperature;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.multiblocks.IMultiBlockEnergy;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;
import zmaster587.libVulpes.items.ItemProjector;

import java.util.List;

import static gregapi.data.CS.*;

public class FuelDeburnFactory extends TileEntityBase10MultiBlockMachine implements ITileEntityEnergy, IMultiBlockEnergy, ITileEntityTemperature, ITileEntityComputerizable {

    public long mTempMax = 4000, mMassTotal=1,mMassSelf=1,maxStrictEUt=1024,mMassLast=1;
    public float mTemp = C, recipeBestTemp= C, recipeFactor = 0.1F;
    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if (mStopped) return 0;
        boolean tPositive = (aSize > 0);
        aSize = Math.abs(aSize);
        if (aEnergyType == mEnergyTypeCharged) {
            if (aDoInject) mTemp += (aSize * aAmount)*64F/mMassTotal;
            this.receivedEnergy.add(new MeterData(aEnergyType,aSize, aAmount));
            return aAmount;
        }
        if (aEnergyType == mEnergyTypeAccepted) {
            if(aDoInject && aSize > getEnergySizeInputMax(aEnergyType, aSide))overcharge(aSide,aEnergyType);
            if (aDoInject) mStateNew = tPositive;
            long tInput = Math.min(mInputMax - mEnergy, aSize * aAmount), tConsumed = Math.min(aAmount, (tInput/aSize) + (tInput%aSize!=0?1:0));
            if (!aDoInject) return tConsumed;
            mEnergy += tConsumed * aSize;
            this.receivedEnergy.add(new MeterData(aEnergyType, aSize, tConsumed));
            return tConsumed;
        }
        return 0;
    }

    @Override
    public boolean isEnergyAcceptingFrom(TagData aEnergyType, byte aSide, boolean aTheoretical) {
        return super.isEnergyAcceptingFrom(aEnergyType, aSide, aTheoretical);
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        mSpecialIsStartEnergy=false;
        if (aNBT.hasKey("ktfru.nbt.massSelf")) mMassTotal = mMassSelf = aNBT.getLong("ktfru.nbt.massSelf");
        if (aNBT.hasKey(NBT_TEMPERATURE+".max")) mTempMax = aNBT.getLong(NBT_TEMPERATURE+".max");

        if (aNBT.hasKey(NBT_TEMPERATURE)) mTemp = aNBT.getFloat(NBT_TEMPERATURE);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        aNBT.setFloat(NBT_TEMPERATURE, mTemp);
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if(aTool.equals(TOOL_thermometer)){
            if(aChatReturn!=null)aChatReturn.add(LH.get(I18nHandler.TEMPERATURE) +": "+ mTemp + " / " + mTempMax);
            return 1;
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if(mInventoryChanged){
            updateMass();
        }
        super.onTick2(aTimer, aIsServerSide);

        if(getStateRunningPassively())checkTempAndCauseBlockUpdate();
    }

    public void updateMass(){
        mMassLast = mMassTotal;
        mMassTotal = mMassSelf;
        for (int i = 0; i < invsize(); i++) {
            if(!slotHas(i) || IL.Circuit_Selector.equal(slot(i)) || OreDictManager.INSTANCE.getItemData(slot(i)) == null)continue;
            mMassTotal += (long) OreDictManager.INSTANCE.getItemData(slot(i)).getAllMaterialStacks().stream().mapToDouble(OreDictMaterialStack::weight).sum();
        }
        for (FluidTankGT tankGT:mTanksInput) {
            if(tankGT==null || tankGT.isEmpty())continue;
            mMassTotal += tankGT.fluid().getDensity()* tankGT.amount()/1000 ;
        }
        if(mMassLast<mMassTotal) {
            float deltaTemp = mTemp - C;
            cooldown( mTemp-(deltaTemp*mMassLast/mMassTotal+C));
        }
    }

    @Override
    public boolean doActive(long aTimer, long aEnergy) {
        if(mTemp > mTempMax)overcharge(mInputMax,TD.Energy.HU);
        //Natural Cooldown
        cooldown(Math.min(10F*Math.abs(mTemp-C)/mMassTotal, Math.abs(mTemp-C)));

        //Promote extra progress when Temp is Suitable, and if not suitable decreases the progress
        if (mMaxProgress > 0 && !(mSpecialIsStartEnergy && mChargeRequirement > 0) && mProgress <= mMaxProgress) {
            mProgress += (long) (aEnergy * getTempFactor());
        }
        if(mProgress<0)mProgress=0;
        return super.doActive(aTimer, aEnergy);
    }

    protected byte lastTickStrength = 0;
    public void checkTempAndCauseBlockUpdate(){
        byte strength = (byte)(7+(mTemp - recipeBestTemp)/50F);
        if(lastTickStrength != strength)causeBlockUpdate();
        lastTickStrength = strength;
    }
    @Override
    public int checkRecipe(boolean aApplyRecipe, boolean aUseAutoIO) {
        int i = super.checkRecipe(aApplyRecipe, aUseAutoIO);

        if(mCurrentRecipe!=null) {
            updateMass();
            recipeBestTemp = mCurrentRecipe.mSpecialValue;
            recipeFactor = 20.1F-20*Math.min(1F, mCurrentRecipe.mEUt*1F/maxStrictEUt);
        }else {
            recipeBestTemp = 1;
        }
        return i;
    }

    public void cooldown(float value){
        if(Math.abs(mTemp-C)< value){
            mTemp=C;
            return;
        }
        value=Math.abs(value);
        mTemp -= mTemp>C ? value: -value;
    }
    public float getTempFactor() {
        float delta = Math.abs(mTemp - recipeBestTemp);
        return Math.min(128, ((256*recipeFactor / (float)Math.sqrt(delta)) - (float) Math.pow(delta, 1.5F) / recipeFactor )/recipeFactor);
    }

    @Override
    public long getTemperatureValue(byte aSide) {
        return (long) mTemp;
    }

    @Override
    public long getTemperatureMax(byte aSide) {
        return mTempMax;
    }
    //这是设置主方块的物品提示
    //controls tooltip of controller block
    static {
        LH.add("ktfru.multitileentity.multiblock.fuel_deburner.1", "Store Energy into Fuel.");
        LH.add("ktfru.multitileentity.multiblock.fuel_deburner.2", "Main Block centered on Side-Bottom and facing outwards");
        LH.add("ktfru.multitileentity.multiblock.fuel_deburner.3", "Input and Output at any Blocks");
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.multitileentity.multiblock.fuel_deburner.1"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.multitileentity.multiblock.fuel_deburner.2"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.multitileentity.multiblock.fuel_deburner.3"));
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidOutputTarget(byte aSide, Fluid aOutput) {
        return getAdjacentTank(SIDE_UP);
    }

    @Override
    public DelegatorTileEntity<TileEntity> getItemOutputTarget(byte aSide) {
        return getAdjacentTileEntity(SIDE_UP);
    }

    @Override
    public DelegatorTileEntity<IInventory> getItemInputTarget(byte aSide) {
        return null;
    }

    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidInputTarget(byte aSide) {
        return null;
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
    //Structure
    ChunkCoordinates lastFailedPos=null;
    static IStringBaseStructure structure = new LayerStructure(StructureContext.Axis.Y).layerRule("ABCDCEFGHIJKLMNOPQQ")
            .fixedLayer('A',
                    " FFFFF  FFF",
                    " FWWWF  FFF",
                    " FWWWF  FFF",
                    " FWWWF  FFF",
                    " FFFFF  FFF",
                    " F      FFF",
                    " F      FFF",
                    " FFFFFFFFFF",
                    " FWWWWF FFF",
                    " FWWWWF FFF",
                    " FWWWWF FFF",
                    " FWWWWF FFF",
                    " FFFFFF FFF",
                    "           "
                    ).fixedLayer('B',
                    " F   F   P ",
                    "  WWW      ",
                    "  W W   WPW",
                    "  WWW    P ",
                    " F F F  WPW",
                    " F       P ",
                    "        WPW",
                    " FWWWWF  P ",
                    " W    W WPW",
                    " W    W  P ",
                    " W    W WPW",
                    " W    W  P ",
                    " FWWWWF WPW",
                    "    PPPPPP "
                    ).fixedLayer('C',
                    " F FFF   P ",
                    " FWWW      ",
                    " FW WF  W W",
                    "  WWWF     ",
                    " FFPFF  W W",
                    "   P       ",
                    "   P    W W",
                    " FWWWWF    ",
                    " W    W W W",
                    " W    W    ",
                    " W    W W W",
                    " W    W    ",
                    " FWWWWF W W",
                    "           "
                    ).fixedLayer('D',
                    " F   F   P ",
                    "  WWW      ",
                    "  W W   W W",
                    "  WWW      ",
                    " F F F  W W",
                    "           ",
                    "        W W",
                    " FWWWWF    ",
                    " W    W W W",
                    " W    W    ",
                    " W    W W W",
                    " W    W    ",
                    " FWWWWF W W",
                    "           "
                    ).fixedLayer('E',
                    " F   F   P ",
                    "  WWW      ",
                    "  W W   W W",
                    "  WWW      ",
                    " F F F  W W",
                    "           ",
                    "        W W",
                    " FWWWWF    ",
                    " W    W W W",
                    " W    W    ",
                    "PW    W W W",
                    "PW    W    ",
                    "PFWWWWF W W",
                    "PPP P      "
                    ).fixedLayer('F',
                    " F FFF   P ",
                    " FWWW    P ",
                    " FW WF  WPW",
                    "  WWWF   P ",
                    " FFPFF  WPW",
                    "   P     P ",
                    "   P    WPW",
                    " FWWWWF  P ",
                    " W    W WPW",
                    " W    W  P ",
                    " W    W WPW",
                    " W    W  P ",
                    " FWWWWF WPW",
                    "    P      "
                    ).fixedLayer('G',
                    " F   F     ",
                    "  WWW      ",
                    "  W W   FFF",
                    "  WWW   FFF",
                    " F F F  FFF",
                    "        FFF",
                    "        FFF",
                    " FWWWWF FFF",
                    " W    W FFF",
                    " W    W FFF",
                    "PW    W FFF",
                    "PW    W FFF",
                    "PFWWWWF FFF",
                    "PPP P      "
                    ).fixedLayer('H',
                    " FFFFF     ",
                    " FWWWF     ",
                    " FWWWF     ",
                    " FWWWF     ",
                    " FFFFF     ",
                    "           ",
                    "           ",
                    " FWWWWF    ",
                    " W    W    ",
                    " W    W    ",
                    " W    W    ",
                    " W    W    ",
                    " FWWWWF    ",
                    "    P      "
                    ).fixedLayer('I',
                    "           ",
                    "    P      ",
                    "           ",
                    "  P        ",
                    "           ",
                    "           ",
                    "           ",
                    " FWWWWF    ",
                    " W    W    ",
                    " W    W    ",
                    "PW    W    ",
                    "PW    W    ",
                    "PFWWWWF    ",
                    "PPP P      "
                    ).fixedLayer('J',
                    "           ",
                    "    P      ",
                    "           ",
                    "  P        ",
                    "           ",
                    "           ",
                    "    PPPP   ",
                    " FWWWWFP   ",
                    " W    WP   ",
                    " W    WP   ",
                    " W    W    ",
                    " W    W    ",
                    " FWWWWF    ",
                    "    P      "
                    ).fixedLayer('K',
                    "           ",
                    "    P      ",
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    " FWWWWF    ",
                    " W    W    ",
                    " W    W    ",
                    "PW    W    ",
                    "PW    W    ",
                    "PFWWWWF    ",
                    "PPP P      "
                    ).fixedLayer('L',
                    "           ",
                    "    P      ",
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "    PPPP   ",
                    " FWWWWFP   ",
                    " W    WP   ",
                    " W    WP   ",
                    " W    W    ",
                    " W    W    ",
                    " FWWWWF    ",
                    "    P      "
                    ).fixedLayer('M',
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    " FWWWWF    ",
                    " W    W    ",
                    " W    W    ",
                    " W    W    ",
                    " W    W    ",
                    " FWWWWF    ",
                    "           "
                    ).fixedLayer('N',
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    " FFFFFF    ",
                    " FWWWWF    ",
                    " FWWWWF    ",
                    " FWWWWF    ",
                    " FWWWWF    ",
                    " FFFFFF    ",
                    "           "
                    ).fixedLayer('O',
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "  P        ",
                    "    P      ",
                    "           ",
                    "     P     ",
                    "           ",
                    "           "
                    ).fixedLayer('P',
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "  P        ",
                    "    P      ",
                    "           ",
                    "           ",
                    "           ",
                    "           "
                    ).fixedLayer('Q',
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "  P        ",
                    "           ",
                    "           ",
                    "           ",
                    "           ",
                    "           "
                    )
            .where('F', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31037, MultiTileEntityMultiBlockPart.ONLY_IN)))
            .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18002, MultiTileEntityMultiBlockPart.ONLY_IN)))
            .where('P', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31038)))
            .setOffset(-6,-1,-1) ;
    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        lastFailedPos = structure.checkStructure(new StructureContext(this, (aPlayer != null || aInventory != null)? StructureContext.StringBaseMode.SET: StructureContext.StringBaseMode.CHECK, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        return lastFailedPos==null;
    }

    public final short xMapOffset = -6, zMapOffset = 0;

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {return true;}


    // Icons
    public final static IIconContainer
            sTextureSides     = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/fuelDeburner/base"),
            sOverlayStop      = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/fuelDeburner/front");


    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if (!aShouldSideBeRendered[aSide]) return null;
        if(aSide==mFacing) return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides, mRGBa),BlockTextureDefault.get(sOverlayStop ));
        return BlockTextureDefault.get(sTextureSides, mRGBa);
    }

    public static final String[] METHODS = {"getbest", "getcurrent"}, ARGS = {"void", "void"}, HELPS = {"gets the best temperature of current recipe", "gets the current temperature"};
    public static final Class<?>[] RETURNS = {int.class, int.class};

    @Override public String     getComputerizableName       (DelegatorTileEntity<TileEntity> aDelegator) {return "ktfru_fuel_deburner";}
    @Override public String[]   allComputerizableArgs       (DelegatorTileEntity<TileEntity> aDelegator) {return ARGS;}
    @Override public String[]   allComputerizableHelps      (DelegatorTileEntity<TileEntity> aDelegator) {return HELPS;}
    @Override public String[]   allComputerizableMethods    (DelegatorTileEntity<TileEntity> aDelegator) {return METHODS;}
    @Override public Class<?>[] allComputerizableReturns    (DelegatorTileEntity<TileEntity> aDelegator) {return RETURNS;}
    @Override public String     getComputerizableArgs       (DelegatorTileEntity<TileEntity> aDelegator, int aFunctionIndex) {return ARGS[aFunctionIndex];}
    @Override public String     getComputerizableHelp       (DelegatorTileEntity<TileEntity> aDelegator, int aFunctionIndex) {return HELPS[aFunctionIndex];}
    @Override public String     getComputerizableMethod     (DelegatorTileEntity<TileEntity> aDelegator, int aFunctionIndex) {return METHODS[aFunctionIndex];}
    @Override public Class<?>   getComputerizableReturn     (DelegatorTileEntity<TileEntity> aDelegator, int aFunctionIndex) {return RETURNS[aFunctionIndex];}

    @Override
    public Object[] callComputerizableMethod(DelegatorTileEntity<TileEntity> aDelegator, int aFunctionIndex, Object[] aArguments) {
        return new Object[] {aFunctionIndex == 1 ? mTemp : recipeBestTemp};
    }

    public byte isProvidingStrongPower2(byte aSide) {return (byte) (7+(mTemp - recipeBestTemp)/50F);}
    public byte isProvidingWeakPower2(byte aSide) {return (byte) (7+(mTemp - recipeBestTemp)/50F);}
    @Override
    public String getTileEntityName() {return "ktfru.multitileentity.multiblock.fuel_deburner";}
}