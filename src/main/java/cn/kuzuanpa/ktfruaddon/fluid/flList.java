/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.fluid;

import gregapi.data.FL;
import gregapi.data.MT;
import gregapi.data.RM;
import gregapi.oredict.OreDictMaterial;
import gregapi.util.OM;
import gregapi.util.ST;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static gregapi.data.CS.*;


public enum flList {
    //Ore Process
    AcidPickledBauxide, SodiumAluminate, PotassiumAluminate, SodiumCarbonate, PotassiumCarbonate, SodiumHeterotungstate, AmmoniumTungstate
    //Oil Process And Plastics
    ,AqueousOilExtraHeavy,AqueousOilHeavy,AqueousOilMedium,AqueousOilNormal,AqueousOilLight
    ,DesaltOilExtraHeavy,DesaltOilHeavy,DesaltOilMedium,DesaltOilNormal,DesaltOilLight
    ,CleanedOilExtraHeavy,CleanedOilHeavy,CleanedOilMedium,CleanedOilNormal,CleanedOilLight
    ,/**一氧化碳**/CarbonMonoxide,/**石油脱硫剂**/ OilDesulfurizationer,/**硫化石油脱硫剂**/SulfuredOilDesulfurizationer,/**石油气**/OilGas,/**裂化石油气**/CrackedOilGas,/**初底油**/InitalBottomOil,/**石脑油**/Naphtha
    ,/**次氯酸**/HypochlorousAcid,/**氯酸**/ChloricAcid,/**高氯酸**/PerchloricAcid,/**光气**/Phosgene
    ,/**苯**/Benzene,/**甲苯**/Toluene,/**苯酚**/Phenol
    ,/**甲醇**/Methanol,/**丙醇**/Propanol,/**丙二醇**/Propanediol,/**乙烷**/Ethane,/**二甲醚**/Methoxymethane
    ,/**甲醛**/Formaldehyde,/**乙醛**/Acetaldehyde,/**丙醛**/Propionaldehyde
    ,/**乙炔**/Acetylene
    ,/**丙酮**/Acetone
    ,/**一氯甲烷**/Chloromethane,/**二氯甲烷**/Dichloromethane,/**三氯甲烷/氯仿**/Chloroform,/**氯丙烯**/AllylChloride,/**二氯丙醇**/DichloroPropanol,/**丙烯醇**/AllylAlcohol,/**乙酸烯丙酯**/AllylAcetate,/**环氧氯丙烷**/Epichlorohydrin,/**甲基丙烯酸**/MethacrylicAcid
    ,/**冰醋酸**/GlacialAceticAcid,/**四氟乙烯**/Tetrafluoroethylene
    ,/**硅橡胶液**/LiquidSiliconeRubber,/**甲基氯化镁**/MethylmagnesiumChloride,/**一甲三氯硅烷**/Methyltrichlorosilane,/**二甲二氯硅烷**/Dichlorodimethylsilane,/**二氯乙烷**/DichloroEthane,/**二氯丙烷**/DichloroPropane,/**氯乙烯**/VinylChloride,/**乙苯**/Ethylbenzene,/**一氯乙苯**/ChloroPhenylethane,/**乙烯苯**/Styrene,/**丁苯橡胶液**/SBR,/**异戊二烯**/Isoprene,/**氯乙醇**/Chloroethanol,/**乙二醇**/EthyleneGlycol
    ,/**乙酸乙烯酯**/VinylAcetate,/**丁二烯**/Butadiene
    ,/**六氟丙烯**/HexaFluoroPropylene,/**四氟磺内酯**/TFES,/**全氟环氧丙烷**/HFPO,/**全氟磺酸单体前体**/PrecursorPSVE,/**全氟磺酸单体**/PSVE,/**八氟环丁烷**/Perfluorocyclobutane,/**四氯化硅**/Tetrachorosilane,/**氢溴酸**/HydrobromicAcid
    ,/**丙二醇甲醚**/MethoxyPropanol,/**丙二醇甲醚醋酸酯**/PGMEA,/**重氮系光刻胶**/DNQPhotoresist,/**环己酮**/Cyclohexanone,/**PMMA系光刻胶**/PMMAPhotoresist
    ,CoalTar, WoodTar
    ,BlendedFuel1,BlendedFuel2,BlendedFuel3,BlendedFuel4,BlendedFuel5,BioFuel1,BioFuel2,/**蚀刻液**/EtchingSolution
    ,NegativeColloid,PositiveColloid
    ,/**磷酸三丁酯**/TributylPhosphate,/**丁醇**/Butanol,/**碳酸铀酰络合离子溶液**/UranylCarbonateSolution,/**甲基叔胺**/MethylTertiaryAmine,/**油酸乙酯**/EthylOleate,/**铀萃取剂**/UraniumExtractant,/**用过的铀萃取剂**/UsedUraniumExtractant,/**萃取的铀**/ExtractedUranium,/**萃取的铀**/ExtractedUranium2,/**硅烷**/Silane,/**四氟化硅**/SiliconTetrafluoride
    ,/**氢氧化钾溶液**/SolutionPotassiumHydroxide,/**双酚钠盐溶液**/ SolutionBPASodium
    ,HotDistW
    ,CrackedNaphthaLow,CrackedNaphthaMedium,CrackedNaphthaHigh,CrackedDieselLow,CrackedDieselMedium,CrackedDieselHigh
    ,MoltenTeflon,MoltenBakelite,MoltenNaK,HotMoltenNaK, MoltenPolycarbonate
    ;
    public Fluid fluid;
    public String name;
    /**This will create Solutions with transforming recipe**/
    public void registerSolution(String name, String localizedName, @NotNull OreDictMaterial material, int AmountPerUnit) {
        fluid = FL.create(name,localizedName,material,1,AmountPerUnit,277);
        this.name=name;
        RM.Drying.addRecipeX(T,32,40, ST.array(ZL_IS),FL.array(FL.make(fluid,AmountPerUnit)),FL.array(FL.DistW.make(800)), OM.dust(material,U));
        RM.Mixer.addRecipeX(T,16,10, ST.array(OM.dust(material,U)),FL.array(MT.H2O.liquid(U , T)),FL.array(FL.make(fluid,AmountPerUnit)),ZL_IS);
        RM.Bath.addRecipeX(T,0,20, ST.array(OM.dust(material,U)),FL.array(MT.H2O.liquid(U , T)),FL.array(FL.make(fluid,AmountPerUnit)),ZL_IS);
    }
    /**Register a liquid which is a molten form of a material,notice 2 name args will add molten prefix automatically**/
    public void registerMolten(String name, String localizedName, @NotNull OreDictMaterial material) {
        fluid = FL.create("molten."+name, "Molten "+localizedName,material,1,144,material.mMeltingPoint);
        this.name=name;
    }
    /**Register a liquid which is a molten form of a material,notice 2 name args will add molten prefix automatically**/
    public void registerMolten(String name, String localizedName, @NotNull OreDictMaterial material,int offsetTemp) {
        fluid = FL.create("molten."+name, "Molten "+localizedName,material,1,144,material.mMeltingPoint+offsetTemp);
        this.name=name;
    }
    /**Register a liquid which is a Vapor form of a material,notice 2 name args will add vapor prefix automatically**/
    public void registerVapor(String name, String localizedName, @NotNull OreDictMaterial material) {
        fluid = FL.create("Vapor."+name, "Vapor "+localizedName,material,2,144,material.mBoilingPoint);
        this.name=name;
    }
    public void register(String name, String localizedName, @Nullable OreDictMaterial material, int state) {
        fluid = FL.create(name,localizedName,material,state);
        this.name=name;
    }

    public void register(String name, String localizedName, @Nullable OreDictMaterial material, int state, long amountPerUnit) {
        fluid = FL.create(name,localizedName,material,state,amountPerUnit,277);
        this.name=name;
    }
    public void register(String name, String localizedName, @Nullable OreDictMaterial material, int state, long amountPerUnit, long temp) {
        fluid = FL.create(name,localizedName,material,state,amountPerUnit,temp);
        this.name=name;
    }
    public void register(String name, String localizedName, @Nullable OreDictMaterial material, int state, long amountPerUnit, long temp, int density) {
        fluid = FL.create(name,localizedName,material,state,amountPerUnit,temp);
        fluid.setDensity(density);
        this.name=name;
    }
    public void register(String name, String localizedName, @Nullable OreDictMaterial material, int state, long amountPerUnit, long temp, int density, int viscosity) {
        fluid = FL.create(name,localizedName,material,state,amountPerUnit,temp);
        fluid.setViscosity(viscosity);
        fluid.setDensity(density);
        this.name=name;
    }
    public void register(String name, String localizedName, @Nullable OreDictMaterial material, int state, long amountPerUnit, long temp, int density, int viscosity, int luminosity) {
        fluid = FL.create(name,localizedName,material,state,amountPerUnit,temp);
        fluid.setLuminosity(luminosity);
        fluid.setViscosity(viscosity);
        fluid.setDensity(density);
        this.name=name;
    }

    public FluidStack make(long amount) {
        return FL.make(fluid,amount);
    }

    public Fluid get() {
        return fluid;
    }

    public String getName(){return name;}
}