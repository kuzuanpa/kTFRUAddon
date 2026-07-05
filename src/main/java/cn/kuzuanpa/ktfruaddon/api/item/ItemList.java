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


package cn.kuzuanpa.ktfruaddon.api.item;

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
    SiliconBoulePure, MoO2Boule, MoO2BoulePure,

    SiliconPlateT1,SiliconPlateT2, MoO2PlateT1,MoO2PlateT2,
    SiliconPlateCleanedT1,SiliconPlateCleanedT2, MoO2PlateCleanedT1,MoO2PlateCleanedT2,
    SiliconPlateOxidizedT1,SiliconPlateOxidizedT2, MoO2PlateOxidizedT1,MoO2PlateOxidizedT2,
    SiliconPlateCoatedT1,SiliconPlateCoatedT2, MoO2PlateCoatedT1,MoO2PlateCoatedT2,
    SiliconPlateSoftBakedT1,SiliconPlateSoftBakedT2, MoO2PlateSoftBakedT1,MoO2PlateSoftBakedT2,
    CPUPhotomaskT1, CPUPhotomaskT2, CPUPhotomaskT3, CPUPhotomaskT4, CPUPhotomaskT5, CPUPhotomaskT6, CPUPhotomaskT7, CPUPhotomaskT8,
    CPUWaferT1, CPUWaferT2, CPUWaferT3, CPUWaferT4, CPUWaferT5, CPUWaferT6, CPUWaferT7, CPUWaferT8,
    CPUWaferT1Developed, CPUWaferT2Developed, CPUWaferT3Developed, CPUWaferT4Developed, CPUWaferT5Developed, CPUWaferT6Developed, CPUWaferT7Developed, CPUWaferT8Developed,
    CPUWaferT1HardBaked, CPUWaferT2HardBaked, CPUWaferT3HardBaked, CPUWaferT4HardBaked, CPUWaferT5HardBaked, CPUWaferT6HardBaked, CPUWaferT7HardBaked, CPUWaferT8HardBaked,
    CPUWaferT1Doped, CPUWaferT2Doped, CPUWaferT3Doped, CPUWaferT4Doped, CPUWaferT5Doped, CPUWaferT6Doped, CPUWaferT7Doped, CPUWaferT8Doped,
    CPUWaferT1Checked, CPUWaferT2Checked, CPUWaferT3Checked, CPUWaferT4Checked, CPUWaferT5Checked, CPUWaferT6Checked, CPUWaferT7Checked, CPUWaferT8Checked,
    CPUBoardT1,CPUBoardT2,CPUBoardT3,
    RAMPhotomaskT1, RAMPhotomaskT2, RAMPhotomaskT3, RAMPhotomaskT4, RAMPhotomaskT5, RAMPhotomaskT6, RAMPhotomaskT7, RAMPhotomaskT8,
    RAMWaferT1, RAMWaferT2, RAMWaferT3, RAMWaferT4, RAMWaferT5, RAMWaferT6, RAMWaferT7, RAMWaferT8,
    RAMWaferT1Developed, RAMWaferT2Developed, RAMWaferT3Developed, RAMWaferT4Developed, RAMWaferT5Developed, RAMWaferT6Developed, RAMWaferT7Developed, RAMWaferT8Developed,
    RAMWaferT1HardBaked, RAMWaferT2HardBaked, RAMWaferT3HardBaked, RAMWaferT4HardBaked, RAMWaferT5HardBaked, RAMWaferT6HardBaked, RAMWaferT7HardBaked, RAMWaferT8HardBaked,
    RAMWaferT1Doped, RAMWaferT2Doped, RAMWaferT3Doped, RAMWaferT4Doped, RAMWaferT5Doped, RAMWaferT6Doped, RAMWaferT7Doped, RAMWaferT8Doped,
    RAMWaferT1Checked, RAMWaferT2Checked, RAMWaferT3Checked, RAMWaferT4Checked, RAMWaferT5Checked, RAMWaferT6Checked, RAMWaferT7Checked, RAMWaferT8Checked,
    RAMBoardT1,RAMBoardT2,RAMBoardT3,
    RAMDie2K, RAMDie32K,   RAMDie256K,   RAMDie2M,   RAMDie16M,   RAMDie128M,   RAMDie768M,   RAMDie2G,

    RAMBar2K4, RAMBar32K4,  RAMBar256K4,  RAMBar2M4,  RAMBar16M4,  RAMBar128M4,  RAMBar768M4,  RAMBar2G4,
    RAMBar2K8, RAMBar32K8,  RAMBar256K8,  RAMBar2M8,  RAMBar16M8,  RAMBar128M8,  RAMBar768M8,  RAMBar2G8,
    RAMBar256K16, RAMBar2M16, RAMBar16M16, RAMBar128M16, RAMBar768M16, RAMBar2G16,
    RAMBar16M32, RAMBar128M32, RAMBar768M32, RAMBar2G32,

    InterLayerPhotomaskT1, InterLayerPhotomaskT2, InterLayerWaferT7, InterLayerWaferT8,
    InterLayerWaferT7Developed, InterLayerWaferT8Developed,
    InterLayerWaferT7HardBaked, InterLayerWaferT8HardBaked,
    InterLayerWaferT7Doped, InterLayerWaferT8Doped,
    InterLayerWaferT7Checked, InterLayerWaferT8Checked,
    InterLayerT1, InterLayerT2,

    CPUDieTF3386, CPUDieTF3586, CPUDieGT1000, CPUDieGT2000, CPUDieGT3660, CPUDieGT3660v2, CPUDieGT3660v3, CPUDieGT3660v4,
    CPUDieTF3386S, CPUDieTF3586S, CPUDieGT1090, CPUDieGT2090, CPUDieGT3680, CPUDieGT3680v2, CPUDieGT3680v3, CPUDieGT3680v4,

    CPUTF3386 , CPUTF3586 , CPUGT1000, CPUGT2000, CPUGT3660, CPUGT3660v2, CPUGT3660v3, CPUGT3660v4,
    CPUTF3386S, CPUTF3586S, CPUGT1090, CPUGT2090, CPUGT3680, CPUGT3680v2, CPUGT3680v3, CPUGT3680v4, CPUGT3680v3E, CPUGT3680v4E,
                                                  CPUGT3699, CPUGT3699v2, CPUGT3699v3, CPUGT3699v4, CPUGT3699v3E, CPUGT3699v4E,

    CircuitPartPhotomaskT3, CircuitPartPhotomaskT4, CircuitPartPhotomaskT5, CircuitPartPhotomaskT6,
    CircuitPartWaferT3, CircuitPartWaferT4, CircuitPartWaferT5, CircuitPartWaferT6,
    CircuitPartWaferT3Developed, CircuitPartWaferT4Developed, CircuitPartWaferT5Developed, CircuitPartWaferT6Developed,
    CircuitPartWaferT3HardBaked, CircuitPartWaferT4HardBaked, CircuitPartWaferT5HardBaked, CircuitPartWaferT6HardBaked,
    CircuitPartWaferT3Doped, CircuitPartWaferT4Doped, CircuitPartWaferT5Doped, CircuitPartWaferT6Doped,
    CircuitPartWaferT3Checked, CircuitPartWaferT4Checked, CircuitPartWaferT5Checked, CircuitPartWaferT6Checked,

    DiodePhotomaskT1, DiodePhotomaskT2,
    DiodeWaferT1, DiodeWaferT2,
    DiodeWaferT1Developed, DiodeWaferT2Developed,
    DiodeWaferT1HardBaked, DiodeWaferT2HardBaked,
    DiodeWaferT1Doped, DiodeWaferT2Doped,
    DiodeWaferT1Checked, DiodeWaferT2Checked,

    //Circuits
    //These Tier NOT corresponding the Tier of GT Circuits
    CircuitBoardEmptyT2,CircuitBoardEmptyT3,
    ResistanceT1, CapacitorT1, CoilT1,DiodeT1, LEDSet,
    ResistanceT2, CapacitorT2, CoilT2,DiodeT2,DiodeT2Part,
    ResistanceT3, CapacitorT3, CoilT3,DiodeT3,DiodeT3Part,
    ResistanceT4,              CoilT4,
    CircuitBoardBasicUncompleted,CircuitBoardGoodUncompleted1,CircuitBoardGoodUncompleted2,
    //Compact
    //Research Related
    ResearchViewer,
    //Useless
    GTQTIcon,
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
    EngineCrankShaft1, EngineCrankShaft2,EngineCrankShaft3, EngineCrankShaft4,EngineCrankShaft5, EngineCrankShaft6,EngineCrankShaft7, EngineCrankShaft8, EngineCrankShaftCr, EngineCrankShaftOs, EngineCrankShaft9,
    EngineCylinder1, EngineCylinder2,EngineCylinder3, EngineCylinder4,EngineCylinder5, EngineCylinder6,EngineCylinder7, EngineCylinder8, EngineCylinderCr,EngineCylinderOs,EngineCylinder9,
    EngineTurbo1, EngineTurbo2,EngineTurbo3, EngineTurbo4,EngineTurbo5, EngineTurbo6,EngineTurbo7, EngineTurbo8, EngineTurboCr,EngineTurboOs,EngineTurbo9,


    VibrateDetector,UltrasonicGenerator,PiezoelectricCeramicPlate,
    //Chemistry
    ProtonExchangeMembrane, AlkalineIonExchangeMembrane, IrAlkalineIonExchangeMembrane,


    //itemComputer
    ComputerTF3386,ComputerTF3386S,ComputerTF3586,ComputerTF3586S,ComputerGT1000,ComputerGT1090,ComputerGT2000,ComputerGT2090,ComputerGT3660,ComputerGT3680,ComputerGT3699,ComputerGT3660v2,ComputerGT3680v2,ComputerGT3699v2,ComputerGT3660v3,ComputerGT3680v3,ComputerGT3699v3,ComputerGT3660v4,ComputerGT3680v4,ComputerGT3699v4,ComputerGT3680v3e,ComputerGT3699v3e,ComputerGT3680v4e,ComputerGT3699v4e,
    ComputerBasicCircuits,ComputerGoodCircuits,ComputerAdvancedCircuits,ComputerEliteCircuits,ComputerMasterCircuits,ComputerUltimateCircuits,
    UnderClockedNoviceComputer,UnderClockedModerateComputer,UnderClockedAdvancedComputer,UnderClockedEliteComputer,UnderClockedMasterComputer,UnderClockedUltimateComputer,
    //itemParticle
    Proton, Anti_Proton, Electron, Positron, Neutron, Alpha_Particle, Neutrino, Anti_Neutrino, Higgs_Boson, Kerr_Blackhole,
    TechResearchData, TechResearchData0, TechResearchData1, TechResearchData2, TechResearchData3, TechResearchData4, TechResearchData5, TechResearchData6, TechResearchData7, TechResearchData8, TechResearchData9, TechResearchData10, TechResearchData11, TechResearchData12, TechResearchData13, TechResearchData14, TechResearchData15, TechResearchData16, TechResearchData17, TechResearchData18, TechResearchData19, TechResearchData20, TechResearchData21, TechResearchData22, TechResearchData23, TechResearchData24, TechResearchData25, TechResearchData26, TechResearchData27, TechResearchData28, TechResearchData29, TechResearchData30, TechResearchData31, TechResearchData32, TechResearchData33, TechResearchData34, TechResearchData35, TechResearchData36, TechResearchData37, TechResearchData38, TechResearchData39, TechResearchData40, TechResearchData41, TechResearchData42, TechResearchData43, TechResearchData44, TechResearchData45, TechResearchData46, TechResearchData47, TechResearchData48, TechResearchData49, TechResearchData50, TechResearchData51, TechResearchData52, TechResearchData53, TechResearchData54, TechResearchData55, TechResearchData56, TechResearchData57, TechResearchData58, TechResearchData59, TechResearchData60, TechResearchData61, TechResearchData62, TechResearchData63, TechResearchData64, TechResearchData65, TechResearchData66, TechResearchData67, TechResearchData68, TechResearchData69, TechResearchData70,
    //itemTechnological
    PropertiesFilter,
    //itemBatteryPole
    BatteryPoleNickel, BatteryPoleCaTiO3, BatteryPolePlatinum,BatteryPoleCarbon,
    //ItemDevice
    AsteroidMinerRocketT1, AsteroidMinerRocketT1Fast, AsteroidMinerRocketT2, AsteroidMinerRocketT2Fast, AsteroidMinerRocketT3, AsteroidMinerRocketT3Fast, AsteroidMinerRocketT4, AsteroidMinerRocketT4Fast, AsteroidMinerRocketT5, AsteroidMinerRocketT5Fast,
    DeprecatedAsteroidMinerRocketT1, DeprecatedAsteroidMinerRocketT1Fast, DeprecatedAsteroidMinerRocketT2, DeprecatedAsteroidMinerRocketT2Fast, DeprecatedAsteroidMinerRocketT3, DeprecatedAsteroidMinerRocketT3Fast, DeprecatedAsteroidMinerRocketT4, DeprecatedAsteroidMinerRocketT4Fast, DeprecatedAsteroidMinerRocketT5, DeprecatedAsteroidMinerRocketT5Fast,
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