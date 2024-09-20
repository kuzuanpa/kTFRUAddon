/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.item.util;

import gregapi.api.Abstract_Mod;
import gregapi.code.IItemContainer;
import gregapi.code.TagData;
import gregapi.item.IItemEnergy;
import gregapi.oredict.OreDictItemData;
import gregapi.util.OM;
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import static gregapi.data.CS.*;

public enum ItemList implements IItemContainer{
    //itemIT
    SiliconBoulePure, MoO2Boule, MoO2BoulePure, MonocrystallineSteel, MonocrystallineStainlessSteel, MonocrystallineTitanium, MonocrystallineTrinitanium, MonocrystallineHSSG, MonocrystallineHSSE, MonocrystallineHSSS, MonocrystallineTungstenSteel, MonocrystallineTungsten, MonocrystallineVibramantium,

    SiliconPlate8inchT1,SiliconPlate8inchT2, MoO2Plate8inchT1,MoO2Plate8inchT2,
    SiliconPlate8inchCleanedT1,SiliconPlate8inchCleanedT2, MoO2Plate8inchCleanedT1,MoO2Plate8inchCleanedT2,
    SiliconPlate8inchOxidizedT1,SiliconPlate8inchOxidizedT2, MoO2Plate8inchOxidizedT1,MoO2Plate8inchOxidizedT2,
    SiliconPlate8inchCoatedT1,SiliconPlate8inchCoatedT2, MoO2Plate8inchCoatedT1,MoO2Plate8inchCoatedT2,
    SiliconPlate8inchSoftBakedT1,SiliconPlate8inchSoftBakedT2, MoO2Plate8inchSoftBakedT1,MoO2Plate8inchSoftBakedT2,
    CPUPhotomask200um, CPUPhotomask72um, CPUPhotomask28um, CPUPhotomask8um, CPUPhotomask400nm, CPUPhotomask80nm, CPUPhotomask32nm, CPUPhotomask14nm,
    CPUWafer200um, CPUWafer72um, CPUWafer28um, CPUWafer8um, CPUWafer400nm, CPUWafer80nm, CPUWafer32nm, CPUWafer14nm, 
    CPUWafer200umDeveloped, CPUWafer72umDeveloped, CPUWafer28umDeveloped, CPUWafer8umDeveloped, CPUWafer400nmDeveloped, CPUWafer80nmDeveloped, CPUWafer32nmDeveloped, CPUWafer14nmDeveloped, 
    CPUWafer200umHardBaked, CPUWafer72umHardBaked, CPUWafer28umHardBaked, CPUWafer8umHardBaked, CPUWafer400nmHardBaked, CPUWafer80nmHardBaked, CPUWafer32nmHardBaked, CPUWafer14nmHardBaked,
    CPUWafer200umDoped, CPUWafer72umDoped, CPUWafer28umDoped, CPUWafer8umDoped, CPUWafer400nmDoped, CPUWafer80nmDoped, CPUWafer32nmDoped, CPUWafer14nmDoped,
    CPUWafer200umChecked, CPUWafer72umChecked, CPUWafer28umChecked, CPUWafer8umChecked, CPUWafer400nmChecked, CPUWafer80nmChecked, CPUWafer32nmChecked, CPUWafer14nmChecked,
    CPUBoardT1,CPUBoardT2,CPUBoardT3,
    RAMPhotomask200um, RAMPhotomask72um, RAMPhotomask28um, RAMPhotomask8um, RAMPhotomask400nm, RAMPhotomask80nm, RAMPhotomask32nm, RAMPhotomask14nm,
    RAMWafer200um, RAMWafer72um, RAMWafer28um, RAMWafer8um, RAMWafer400nm, RAMWafer80nm, RAMWafer32nm, RAMWafer14nm,
    RAMWafer200umDeveloped, RAMWafer72umDeveloped, RAMWafer28umDeveloped, RAMWafer8umDeveloped, RAMWafer400nmDeveloped, RAMWafer80nmDeveloped, RAMWafer32nmDeveloped, RAMWafer14nmDeveloped,
    RAMWafer200umHardBaked, RAMWafer72umHardBaked, RAMWafer28umHardBaked, RAMWafer8umHardBaked, RAMWafer400nmHardBaked, RAMWafer80nmHardBaked, RAMWafer32nmHardBaked, RAMWafer14nmHardBaked,
    RAMWafer200umDoped, RAMWafer72umDoped, RAMWafer28umDoped, RAMWafer8umDoped, RAMWafer400nmDoped, RAMWafer80nmDoped, RAMWafer32nmDoped, RAMWafer14nmDoped,
    RAMWafer200umChecked, RAMWafer72umChecked, RAMWafer28umChecked, RAMWafer8umChecked, RAMWafer400nmChecked, RAMWafer80nmChecked, RAMWafer32nmChecked, RAMWafer14nmChecked,
    RAMBoardT1,RAMBoardT2,RAMBoardT3,
    RAMDie2K, RAMDie32K,   RAMDie256K,   RAMDie2M,   RAMDie16M,   RAMDie128M,   RAMDie768M,   RAMDie2G,

    RAMBar2K4, RAMBar32K4,  RAMBar256K4,  RAMBar2M4,  RAMBar16M4,  RAMBar128M4,  RAMBar768M4,  RAMBar2G4,
    RAMBar2K8, RAMBar32K8,  RAMBar256K8,  RAMBar2M8,  RAMBar16M8,  RAMBar128M8,  RAMBar768M8,  RAMBar2G8,
                             RAMBar256K16, RAMBar2M16, RAMBar16M16, RAMBar128M16, RAMBar768M16, RAMBar2G16,
                                                       RAMBar16M32, RAMBar128M32, RAMBar768M32, RAMBar2G32,

    CPUDieTF3386, CPUDieTF3586, CPUDieGT1000, CPUDieGT2000, CPUDieGT3660, CPUDieGT3660v2, CPUDieGT3660v3, CPUDieGT3660v4,
    CPUDieTF3386S, CPUDieTF3586S, CPUDieGT1090, CPUDieGT2090, CPUDieGT3680, CPUDieGT3680v2, CPUDieGT3680v3, CPUDieGT3680v4,
    
    CPUTF3386, CPUTF3586, CPUGT1000, CPUGT2000, CPUGT3660, CPUGT3660v2, CPUGT3660v3, CPUGT3660v4,
    CPUTF3386S, CPUTF3586S, CPUGT1090, CPUGT2090, CPUGT3680, CPUGT3680v2, CPUGT3680v3, CPUGT3680v4,
                                                  CPUGT3699, CPUGT3699v2, CPUGT3699v3, CPUGT3699v4,
                                                CPUGT3680v3E, CPUGT3680v4E, CPUGT3699v3E, CPUGT3699v4E,

    CircuitPartPhotomaskT3, CircuitPartPhotomaskT4, CircuitPartPhotomaskT5, CircuitPartPhotomaskT6,
    CircuitPartWaferT3, CircuitPartWaferT4, CircuitPartWaferT5, CircuitPartWaferT6,
    CircuitPartWaferT3Developed, CircuitPartWaferT4Developed, CircuitPartWaferT5Developed, CircuitPartWaferT6Developed,
    CircuitPartWaferT3HardBaked, CircuitPartWaferT4HardBaked, CircuitPartWaferT5HardBaked, CircuitPartWaferT6HardBaked,
    CircuitPartWaferT3Doped, CircuitPartWaferT4Doped, CircuitPartWaferT5Doped, CircuitPartWaferT6Doped,
    CircuitPartWaferT3Checked, CircuitPartWaferT4Checked, CircuitPartWaferT5Checked, CircuitPartWaferT6Checked,


    //Circuits
    //These Tier NOT corresponding the Tier of GT Circuits
    DiodePartPhotomaskT2,DiodePartPhotomaskT3,
    CircuitBoardEmptyT2,CircuitBoardEmptyT3,
    ResistanceT1, CapacitorT1, CoilT1,DiodeT1,
    ResistanceT2, CapacitorT2, CoilT2,DiodeT2,DiodeT2Part,
    ResistanceT3, CapacitorT3, CoilT3,DiodeT3,DiodeT3Part,
    ResistanceT4,              CoilT4,
    CircuitBoardBasicUncompleted,CircuitBoardGoodUncompleted1,CircuitBoardGoodUncompleted2,
//Compact
    //AdvancedRocketry
    ArmorAirSealant,SpaceSuitCloth,
    //AppliedEnergistics2
    IntelligentCore,RefinedStoragePart,RefinedFluidStoragePart,
    //Gregtech6
    CrucibleModelInnerLayer,Co60FlawDetectionCore,Tm170FlawDetectionCore, GoodCircuitPartCore, NetherStarPlus,
    //TFC
    TFRUCoin1, TFRUCoin5, TFRUCoin10, PreparedIronOre, ClayGlassBlockMold, CeramicGlassBlockMold, CeramicGlassBlockMoldFull, CeramicGlassBlockMoldComplete, ClayGlassBottleMold, CeramicGlassBottleMold, CeramicGlassBottleMoldFull, CeramicGlassBottleMoldComplete,
    //engine parts made by hand
    EngineCrankShaftManual1, EngineCrankShaftManual2,EngineCrankShaftManual3, EngineCrankShaftManual4,EngineCrankShaftManual5, EngineCrankShaftManual6,EngineCrankShaftManual7, EngineCrankShaftManual8, EngineCrankShaftManualCr,
    EngineCylinderManual1, EngineCylinderManual2,EngineCylinderManual3, EngineCylinderManual4,EngineCylinderManual5, EngineCylinderManual6,EngineCylinderManual7, EngineCylinderManual8, EngineCylinderManualCr,
    //Twilight Forest
    TwilightCore, NaturalCore, FlowerCluster,
    //engine parts made by machine
    EngineCrankShaft1, EngineCrankShaft2,EngineCrankShaft3, EngineCrankShaft4,EngineCrankShaft5, EngineCrankShaft6,EngineCrankShaft7, EngineCrankShaft8, EngineCrankShaftCr,
    EngineCylinder1, EngineCylinder2,EngineCylinder3, EngineCylinder4,EngineCylinder5, EngineCylinder6,EngineCylinder7, EngineCylinder8, EngineCylinderCr,

    EngineTurbo1, EngineTurbo2,EngineTurbo3, EngineTurbo4,EngineTurbo5, EngineTurbo6,EngineTurbo7, EngineTurbo8, EngineTurboCr,

    

    VibrateDetector,
//Chemistry
    ProtonExchangeMembrane,




    //itemComputer
    ComputerTF3386,ComputerTF3386S,ComputerTF3586,ComputerTF3586S,ComputerGT1000,ComputerGT1090,ComputerGT2000,ComputerGT2090,ComputerGT3660,ComputerGT3680,ComputerGT3699,ComputerGT3660v2,ComputerGT3680v2,ComputerGT3699v2,ComputerGT3660v3,ComputerGT3680v3,ComputerGT3699v3,ComputerGT3660v4,ComputerGT3680v4,ComputerGT3699v4,ComputerGT3680v3e,ComputerGT3699v3e,ComputerGT3680v4e,ComputerGT3699v4e,
    ComputerBasicCircuits,ComputerGoodCircuits,ComputerAdvancedCircuits,ComputerEliteCircuits,ComputerMasterCircuits,ComputerUltimateCircuits,
    UnderClockedNoviceComputer,UnderClockedModerateComputer,UnderClockedAdvancedComputer,UnderClockedEliteComputer,UnderClockedMasterComputer,UnderClockedUltimateComputer,
    //itemParticle
    Proton, Anti_Proton, Electron, Positron, Neutron, Alpha_Particle, Neutrino, Anti_Neutrino, Higgs_Boson, Kerr_Blackhole,
    FusionTokamakData0,FusionTokamakData1,FusionTokamakData2,

    //itemTechnological
    PropertiesFilter,
    //itemBatteryPole
    BatteryPoleNickel, BatteryPoleCaTiO3, BatteryPolePlatinum,BatteryPoleCarbon,
    ;

    //Copied from gt6
    private ItemStack mStack;
    private boolean mHasNotBeenSet = T;

    @Override
    public IItemContainer set(Item aItem) {
        mHasNotBeenSet = F;
        if (aItem == null) {
//          new Exception().printStackTrace(GT_Log.deb);
            return this;
        }
        mStack = ST.amount(1, ST.make(aItem, 1, 0));
        return this;
    }

    public IItemContainer set(Item aItem, long aMeta) {
        mHasNotBeenSet = F;
        if (aItem == null) {
//          new Exception().printStackTrace(GT_Log.deb);
            return this;
        }
        mStack = ST.amount(1, ST.make(aItem, 1, aMeta));
        return this;
    }

    @Override
    public IItemContainer set(ItemStack aStack) {
        mHasNotBeenSet = F;
        if (ST.invalid(aStack)) {
//          new Exception().printStackTrace(GT_Log.deb);
            return this;
        }
        mStack = ST.amount(1, aStack);
        return this;
    }

    public IItemContainer set(Item aItem, OreDictItemData aData, Object... aOreDict) {
        mHasNotBeenSet = F;
        if (aItem == null) {
//          new Exception().printStackTrace(GT_Log.deb);
            return this;
        }
        ItemStack aStack = ST.make(aItem, 1, 0);
        mStack = ST.amount(1, aStack);
        if (aData != null && !OM.reg(aData.toString(), ST.make(aItem, 1, W))) OM.data(ST.make(aItem, 1, W), aData);
        for (Object tOreDict : aOreDict) OM.reg(tOreDict, ST.make(aItem, 1, W));
        return this;
    }

    public IItemContainer set(ItemStack aStack, OreDictItemData aData, Object... aOreDict) {
        mHasNotBeenSet = F;
        if (ST.invalid(aStack)) {
//          new Exception().printStackTrace(DEB);
            return this;
        }
        mStack = ST.amount(1, aStack);
        if (aData != null && !OM.reg(aData.toString(), ST.amount(1, aStack))) OM.data(ST.amount(1, aStack), aData);
        for (Object tOreDict : aOreDict) OM.reg(tOreDict, ST.amount(1, aStack));
        return this;
    }

    @Override
    public Item item() {
        if (mHasNotBeenSet && Abstract_Mod.sFinalized < Abstract_Mod.sModCountUsingGTAPI) ERR.println("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (ST.invalid(mStack)) return null;
        return mStack.getItem();
    }

    @Override
    public Block block() {
        return ST.block(get(0));
    }

    @Override
    public boolean exists() {
        return ST.valid(mStack);
    }

    @Override
    public final boolean hasBeenSet() {
        return !mHasNotBeenSet;
    }

    @Override
    public boolean equal(Object aStackOrBlock) {
        return mStack != null && (aStackOrBlock instanceof Block ? aStackOrBlock != NB && ST.block_(mStack) == aStackOrBlock : equal(aStackOrBlock, F, F));
    }

    @Override
    public boolean equal(Object aStack, boolean aWildcard, boolean aIgnoreNBT) {
        return mStack != null && (aWildcard ? ST.item((ItemStack)aStack) == ST.item_(mStack) : ST.equal((ItemStack)aStack, mStack, aIgnoreNBT));
    }

    @Override
    public ItemStack get(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet && Abstract_Mod.sFinalized < Abstract_Mod.sModCountUsingGTAPI) ERR.println("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (ST.invalid(mStack)) return ST.copyFirst(aReplacements);
        return ST.amount(aAmount, OM.get_(mStack));
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack getWildcard(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet && Abstract_Mod.sFinalized < Abstract_Mod.sModCountUsingGTAPI) ERR.println("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (ST.invalid(mStack)) return ST.copyFirst(aReplacements);
        return ST.copyAmountAndMeta(aAmount, W, OM.get_(mStack));
    }

    @Override
    public ItemStack wild(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet && Abstract_Mod.sFinalized < Abstract_Mod.sModCountUsingGTAPI) ERR.println("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (ST.invalid(mStack)) return ST.copyFirst(aReplacements);
        return ST.copyAmountAndMeta(aAmount, W, OM.get_(mStack));
    }

    @Override
    public ItemStack getUndamaged(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet && Abstract_Mod.sFinalized < Abstract_Mod.sModCountUsingGTAPI) ERR.println("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (ST.invalid(mStack)) return ST.copyFirst(aReplacements);
        return ST.copyAmountAndMeta(aAmount, 0, OM.get_(mStack));
    }

    @Override
    public ItemStack getAlmostBroken(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet && Abstract_Mod.sFinalized < Abstract_Mod.sModCountUsingGTAPI) ERR.println("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (ST.invalid(mStack)) return ST.copyFirst(aReplacements);
        return ST.copyAmountAndMeta(aAmount, mStack.getMaxDamage()-1, OM.get_(mStack));
    }

    @Override
    public ItemStack getWithName(long aAmount, String aDisplayName, Object... aReplacements) {
        ItemStack rStack = get(1, aReplacements);
        if (ST.invalid(rStack)) return null;
        rStack.setStackDisplayName(aDisplayName);
        return ST.amount(aAmount, rStack);
    }

    @Override
    public ItemStack getWithNameAndNBT(long aAmount, String aDisplayName, NBTTagCompound aNBT, Object... aReplacements) {
        ItemStack rStack = get(1, aReplacements);
        if (ST.invalid(rStack)) return null;
        UT.NBT.set(rStack, aNBT);
        if (aDisplayName != null) rStack.setStackDisplayName(aDisplayName);
        return ST.amount(aAmount, rStack);
    }

    @Override
    public ItemStack getWithCharge(long aAmount, long aEnergy, Object... aReplacements) {
        ItemStack rStack = get(1, aReplacements);
        if (ST.invalid(rStack)) return null;
        if (rStack.getItem() instanceof IItemEnergy) for (TagData tEnergyType : ((IItemEnergy)rStack.getItem()).getEnergyTypes(rStack)) ((IItemEnergy)rStack.getItem()).setEnergyStored(tEnergyType, rStack, aEnergy);
        return ST.amount(aAmount, rStack);
    }

    @Override
    public ItemStack getWithMeta(long aAmount, long aMetaValue, Object... aReplacements) {
        if (mHasNotBeenSet && Abstract_Mod.sFinalized < Abstract_Mod.sModCountUsingGTAPI) ERR.println("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (ST.invalid(mStack)) return ST.copyFirst(aReplacements);
        return ST.copyAmountAndMeta(aAmount, aMetaValue, OM.get_(mStack));
    }

    @Override
    public ItemStack getWithDamage(long aAmount, long aMetaValue, Object... aReplacements) {
        if (mHasNotBeenSet && Abstract_Mod.sFinalized < Abstract_Mod.sModCountUsingGTAPI) ERR.println("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (ST.invalid(mStack)) return ST.copyFirst(aReplacements);
        return ST.copyAmountAndMeta(aAmount, aMetaValue, OM.get_(mStack));
    }

    @Override
    public ItemStack getWithNBT(long aAmount, NBTTagCompound aNBT, Object... aReplacements) {
        if (mHasNotBeenSet && Abstract_Mod.sFinalized < Abstract_Mod.sModCountUsingGTAPI) ERR.println("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (ST.invalid(mStack)) return ST.copyFirst(aReplacements);
        ItemStack rStack = ST.amount(aAmount, OM.get_(mStack));
        UT.NBT.set(rStack, aNBT);
        return rStack;
    }

    @Override
    public IItemContainer registerOre(Object... aOreNames) {
        if (mHasNotBeenSet && Abstract_Mod.sFinalized < Abstract_Mod.sModCountUsingGTAPI) ERR.println("The Enum '" + name() + "' has not been set to an Item at this time!");
        for (Object tOreName : aOreNames) OM.reg(tOreName, get(1));
        return this;
    }

    @Override
    public IItemContainer registerWildcardAsOre(Object... aOreNames) {
        if (mHasNotBeenSet && Abstract_Mod.sFinalized < Abstract_Mod.sModCountUsingGTAPI) ERR.println("The Enum '" + name() + "' has not been set to an Item at this time!");
        for (Object tOreName : aOreNames) OM.reg(tOreName, wild(1));
        return this;
    }

    @SuppressWarnings("deprecation") @Override public Item getItem() {return item();}
    @SuppressWarnings("deprecation") @Override public Block getBlock() {return block();}
}