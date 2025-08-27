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


package cn.kuzuanpa.ktfruaddon.api.fluid;

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
    ,/**硅橡胶液**/ SiliconeRubber,/**甲基氯化镁**/MethylmagnesiumChloride,/**一甲三氯硅烷**/Methyltrichlorosilane,/**二甲二氯硅烷**/Dichlorodimethylsilane,/**二氯乙烷**/DichloroEthane,/**二氯丙烷**/DichloroPropane,/**氯乙烯**/VinylChloride,/**乙苯**/Ethylbenzene,/**一氯乙苯**/ChloroPhenylethane,/**乙烯苯**/Styrene,/**丁苯橡胶液**/SBR,/**异戊二烯**/Isoprene,/**氯乙醇**/Chloroethanol,/**乙二醇**/EthyleneGlycol
    ,/**乙酸乙烯酯**/VinylAcetate,/**丁二烯**/Butadiene
    ,/**六氟丙烯**/HexaFluoroPropylene,/**四氟磺内酯**/TFES,/**全氟环氧丙烷**/HFPO,/**全氟磺酸单体前体**/PrecursorPSVE,/**全氟磺酸单体**/PSVE,/**八氟环丁烷**/Perfluorocyclobutane,/**四氯化硅**/Tetrachorosilane,/**氢溴酸**/HydrobromicAcid
    ,/**丙二醇甲醚**/MethoxyPropanol,/**丙二醇甲醚醋酸酯**/PGMEA,/**重氮系光刻胶**/DNQPhotoresist,/**环己酮**/Cyclohexanone,/**PMMA系光刻胶**/PMMAPhotoresist
    ,CoalTar, WoodTar
    ,BlendedFuel1,BlendedFuel2,BlendedFuel3,BlendedFuel4,BlendedFuel5,BioFuel1,BioFuel2,/**蚀刻液**/EtchingSolution
    ,NegativeColloid,PositiveColloid
    ,/**磷酸三丁酯**/TributylPhosphate,/**丁醇**/Butanol,/**碳酸铀酰络合离子溶液**/UranylCarbonateSolution,/**甲基叔胺**/MethylTertiaryAmine,/**油酸乙酯**/EthylOleate,/**铀萃取剂**/UraniumExtractant,/**用过的铀萃取剂**/UsedUraniumExtractant,/**萃取的铀**/ExtractedUranium,/**萃取的铀**/ExtractedUranium2,/**硅烷**/Silane,/**四氟化硅**/SiliconTetrafluoride
    ,/**双酚钠盐溶液**/ SolutionBPASodium ,/**氧化钇锆电解质**/ YttriumZirconiumOxide, /**二氧化钼**/MolybdenumOxide, /**硫酸钡**/BariumSulfate, /**醋酸钡**/BariumAcetate
    ,/**乙烯酮**/Ketene,/**双乙烯酮**/Diketene
    ,/**含杂酰化钛**/ImpureTitaniumAcylate,/**粗制酰化钛**/CrudeTitaniumAcylate,/**净化酰化钛**/PurifyTitaniumAcylate,/**酰化钛**/TitaniumAcylate
    ,RecycledFuel0 ,RecycledFuel1 ,RecycledFuel2 ,RecycledFuel3 ,RecycledFuel4
    ,CrackedNaphthaLow,CrackedNaphthaMedium,CrackedNaphthaHigh, CrackedPetrolLow, CrackedPetrolMedium, CrackedPetrolHigh
    ,MoltenTeflon,MoltenBakelite,MoltenNaK,HotMoltenNaK, MoltenPolycarbonate
    ,/**苦卤**/Bittern,/**卤水**/Brine,/**除镁卤水**/BrineMgFree,/**富溴卤水**/BrineBrRich,/**废卤**/BrineWaste, /**丁炔二醇**/Butynediol, /**丁二醇**/Butanediol, /**γ-丁内酯**/Butyrolactone,/**一甲胺**/Methylamine, /**甲基吡咯烷酮**/Methylpyrrolidone, /**石墨-甲基吡咯烷酮**/Graphite_Methylpyrrolidone
    ,/**三甲胺**/Trimethylamine,/**二甲胺**/Dimethylamine,/**对氯甲基苯乙烯**/VinylbenzylChloride,/**一氯乙酸乙酯**/EthylChloroacetate,/**二正己基硫醚**/DinhexylSulfide,/**正己烷**/Hexane, /**二甲基乙酰胺**/Dimethylglycine
    ,/**铂族粗处理液**/RoughPlatinumGroupSolution, /**硫浸铂族处理液**/SulphurPlatinumGroupSolution, /**萃取铂族处理液上液**/ExtractedPlatinumGroupSolutionUp, /**盐酸化铂族处理液上液**/AcidPlatinumGroupSolutionUp, /**二正己基硫醚化铂族处理液**/DinhexylSulfidePlatinumGroupSolution, /**萃取铂族处理液下液**/ExtractedPlatinumGroupSolutionDown, /**蒸馏铂族处理下液**/ DistilledPlatinumGroupSolutionDown, /**硝酸-三甲胺化铂族处理液上液**/NitricPlatinumGroupSolutionUp, /**硝酸-三甲胺化铂族处理液上液余液**/DeprecatedNitricPlatinumGroupSolutionUp, /**硝酸-三甲胺化铂族处理液下液**/NitricPlatinumGroupSolutionDown, /**电解硝酸-三甲胺化铂族处理液下液**/ ElectrolyzedNitricPlatinumGroupSolution, /**铱铂族处理液**/ IridiumPlatinumGroupSolution
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