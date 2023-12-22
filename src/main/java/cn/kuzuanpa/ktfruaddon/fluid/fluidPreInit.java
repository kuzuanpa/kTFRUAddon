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

import cn.kuzuanpa.ktfruaddon.material.matList;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import gregapi.data.MT;

public class fluidPreInit {



    public static void init(FMLPreInitializationEvent e){
        final int PlASMA=3;
        final int GAS=2;
        final int LIQUID=1;
        flList.AcidPickledBauxide.register("acidpickledbauxide", "Acid Pickled Bauxide",null,LIQUID);
        flList.SodiumAluminate.register("sodiumaluminate","Sodium Aluminate",null,LIQUID);
        flList.PotassiumAluminate.register("potassiumaluminate","Potassium Aluminate", null,LIQUID);
        flList.SodiumCarbonate.register("sodiumcarbonate","Sodium Carbonate",null,LIQUID);
        flList.PotassiumCarbonate.register("potassiumcarbonate","Potassium Carbonate",null,LIQUID);
        flList.SodiumHeterotungstate.register("sodiumheterotungstate","Sodium Heterotungstate",null,LIQUID);
        flList.AmmoniumTungstate.register("ammoniumtungstate","Ammonium Tungstate",null,LIQUID);

        //甲醛
        flList.Formaldehyde.register("formaldehyde","Formaldehyde",matList.Formaldehyde.mat,GAS,1000,277,8);
        //乙醛
        flList.Acetaldehyde.register("acetaldehyde","Acetaldehyde",matList.Acetaldehyde.mat,GAS,1000,277,-216);
        //丙醛
        flList.Propionaldehyde.register("propionaldehyde","Propionaldehyde",matList.Propionaldehyde.mat, GAS,1000,277,-195);
        //丙酮
        flList.Acetone.register("acetone","Acetone", matList.Acetone.mat,LIQUID,1000,277,1120);
        //乙炔
        flList.Acetylene.register("acetylene","Acetylene",matList.Acetylene.mat, GAS,1000,277,-90);
        //甲醇
        flList.Methanol.register("methanol","Methanol",matList.Methanol.get(), LIQUID,1000,277,790);
        //乙醇: FL.BioEthanol
        //丙醇
        flList.Propanol.register("propanol","Propanol",matList.Propanol.get(), LIQUID,1000,277,894);
        //丙二醇
        flList.Propanediol.register("propanediol","Propanediol",matList.Propanediol.get(), LIQUID,1000,277,1036);
        //乙烷
        flList.Ethane.register("ethane","Ethane",matList.Ethane.get(), LIQUID,1000,277,1036);
        //二甲醚
        flList.Methoxymethane.register("methoxymethane","Methoxymethane",null, LIQUID);

        //次氯酸
        flList.HypochlorousAcid.register("hypochlorousAcid","Hypochlorous Acid",matList.HypochlorousAcid.get(), LIQUID);
        //氯酸
        flList.ChloricAcid.register("chloricAcid","Chloric Acid",matList.ChloricAcid.get(), LIQUID);
        //高氯酸
        flList.PerchloricAcid.register("perchloricAcid","Perchloric Acid",matList.PerchloricAcid.get(),LIQUID);
        //光气
        flList.Phosgene.register("phosgene","Phosgene",matList.Phosgene.get(), GAS,1000,277,-76);
        //苯
        flList.Benzene.register("benzene","Benzene",matList.Benzene.mat, LIQUID);
        //甲苯
        flList.Toluene.register("toluene","Toluene",matList.Toluene.mat, LIQUID);
        //苯酚
        flList.Phenol.register("phenol","Phenol",null, LIQUID);

        //一氯甲烷
        flList.Chloromethane.register("chloromethane","Chloromethane",matList.Chloromethane.get(), GAS);
        //二氯甲烷
        flList.Dichloromethane.register("dichloromethane","Dichloromethane",matList.Dichloromethane.get(), LIQUID,1000,277,1325);
        //三氯甲烷/氯仿
        flList.Chloroform.register("chloroform","Chloroform",matList.Chloroform.get(), LIQUID);
        //氯丙烯
        flList.AllylChloride.register("allylchloride","AllylChloride",matList.AllylChloride.get(), LIQUID);
        //二氯丙醇
        flList.DichloroPropanol.register("dichloropropanol","DichloroPropanol",matList.DichloroPropanol.get(), LIQUID);
        //丙烯醇
        flList.AllylAlcohol.register("allylalcohol","allylalcohol",null, LIQUID);
        //乙酸烯丙酯
        flList.AllylAcetate.register("allylacetate","AllylAcetate",null, LIQUID);
        //环氧氯丙烷
        flList.Epichlorohydrin.register("epichlorohydrin","Epichlorohydrin",matList.Epichlorohydrin.get(), LIQUID);
        //甲基丙烯酸
        flList.MethacrylicAcid.register("methacrylicacid","MethacrylicAcid",null, LIQUID);
        //冰醋酸
        flList.GlacialAceticAcid.register("glacialaceticacid","GlacialAceticAcid",null, LIQUID);
        //硅橡胶液
        flList.LiquidSiliconeRubber.register("LiquidSiliconeRubber","LiquidSiliconeRubber",matList.SiliconeRubber.get(), LIQUID);
        //甲基氯化镁
        flList.MethylmagnesiumChloride.register("MethylmagnesiumChloride","MethylmagnesiumChloride",matList.MethylmagnesiumChloride.get(), LIQUID);
        //一甲三氯硅烷
        flList.Methyltrichlorosilane.register("Methyltrichlorosilane","Methyltrichlorosilane",matList.Methyltrichlorosilane.get(), LIQUID);
        //二甲二氯硅烷
        flList.Dichlorodimethylsilane.register("Dichlorodimethylsilane","Dichlorodimethylsilane",matList.Dichlorodimethylsilane.get(), LIQUID);
        //二氯乙烷
        flList.DichloroEthane.register("DichloroEthane","DichloroEthane",matList.DichloroEthane.get(), LIQUID);
        //二氯丙烷
        flList.DichloroPropane.register("DichloroPropane","DichloroPropane",matList.DichloroPropane.get(), LIQUID);
        //氯乙烯
        flList.VinylChloride.register("VinylChloride","VinylChloride",matList.VinylChloride.get(), GAS);
        //乙苯
        flList.Ethylbenzene.register("Ethylbenzene","Ethylbenzene",matList.Ethylbenzene.get(), LIQUID);
        //一氯乙苯
        flList.ChloroPhenylethane.register("ChloroPhenylethane","ChloroPhenylethane",matList.ChloroPhenylethane.get(), LIQUID);
        //乙烯苯
        flList.Styrene.register("Styrene","Styrene",matList.Styrene.get(), LIQUID);
        //丁苯橡胶液
        flList.SBR.register("SBR","SBR",matList.SBR.get(), LIQUID);
        //异戊二烯
        flList.Isoprene.register("Isoprene","Isoprene",matList.Isoprene.get(), LIQUID);
        //氯乙醇
        flList.Chloroethanol.register("Chloroethanol","Chloroethanol",matList.Chloroethanol.get(), LIQUID);
        //乙二醇
        flList.EthyleneGlycol.register("EthyleneGlycol","EthyleneGlycol",matList.EthyleneGlycol.get(), LIQUID);
        //六氟丙烯
        flList.HexaFluoroPropylene.register("HPF","HPF",matList.HPF.get(), GAS);
        //四氟磺内酯
        flList.TFES.register("TFES","TFES",matList.TFES.get(), LIQUID);
        //全氟环氧丙烷
        flList.HFPO.register("HFPO","HFPO",matList.HFPO.get(), GAS);
        //全氟磺酸单体前体
        flList.PrecursorPSVE.register("PrecursorPSVE","PrecursorPSVE",matList.PrecursorPSVE.get(), LIQUID);
        //全氟磺酸单体
        flList.PSVE.register("PSVE","PSVE",matList.PSVE.get(), LIQUID);
        //八氟环丁烷
        flList.Perfluorocyclobutane.register("perfluorocyclobutane","perfluorocyclobutane",matList.Perfluorocyclobutane.get(), GAS);
        //乙酸乙烯酯
        flList.VinylAcetate.register("VinylAcetate","VinylAcetate",matList.VinylAcetate.get(), LIQUID);
        //丁二烯
        flList.Butadiene.register("Butadiene","Butadiene",matList.Butadiene.get(), GAS);
        //四氟乙烯
        flList.Tetrafluoroethylene.register("tetrafluoroethylene","Tetrafluoroethylene",null,GAS);
        //四氯化硅
        flList.Tetrachorosilane.register("Tetrachorosilane","Tetrachorosilane",matList.Tetrachorosilane.get(), LIQUID);
        //氢溴酸
        flList.HydrobromicAcid.register("HydrobromicAcid","HydrobromicAcid",matList.HydrobromicAcid.get(), GAS);

        flList.DesaltOilExtraHeavy.register("desaltoilextraheavy","Desalinized Very Heavy Oil",null,LIQUID,1000,277,900,1000);
        flList.DesaltOilHeavy.register("desaltoilheavy","Desalinized Heavy Oil",null,LIQUID,1000,277,800,1000);
        flList.DesaltOilMedium.register("desaltoilmedium","Desalinized Medium Oil",null,LIQUID,1000,277,700,1000);
        flList.DesaltOilNormal.register("desaltoilnormal","Desalinized Oil",null,LIQUID,1000,277,700,1000);
        flList.DesaltOilLight.register("desaltoillight","Desalinized Light Oil",null,LIQUID,1000,277,600,1000);

        flList.OilDesulfurizationer.register("oildesulphurizationer","Oil Desulphurizationer",null,LIQUID);
        flList.SulfuredOilDesulfurizationer.register("sulfuredoildesulphurizationer","Sulfured Oil Desulphurizationer",null,LIQUID);
        flList.OilGas.register("oilgas","Oil Gas",null,GAS,1000,277,100,16);
        flList.CrackedOilGas.register("crackedoilgas","Cracked Oil Gas",null,GAS,1000,277,100,16);

        flList.CarbonMonoxide.register("carbonmonoxide", "carbon Monixide",null,GAS,1000,277,100);
        //初底油
        flList.InitalBottomOil.register("initalbottomoil","Inital Bottom Oil",null, LIQUID,1000,277,500);
        //石脑油
        flList.Naphtha.register("naphtha","Naphtha",null,LIQUID);
        //丙二醇甲醚
        flList.MethoxyPropanol.register("MethoxyPropanol","MethoxyPropanol",matList.MethoxyPropanol.get(), LIQUID);
        //丙二醇甲醚醋酸酯
        flList.PGMEA.register("PGMEA","PGMEA",matList.PGMEA.get(), LIQUID);
        //重氮系光刻胶
        flList.DNQPhotoresist.register("DNQPhotoresist","DNQPhotoresist",matList.DNQPhotoresist.get(), LIQUID,1000,213);
        //环己酮
        flList.Cyclohexanone.register("Cyclohexanone","Cyclohexanone",matList.Cyclohexanone.get(), LIQUID);
        //PMMA系光刻胶
        flList.PMMAPhotoresist.register("PMMAPhotoresist","PMMAPhotoresist",matList.PMMAPhotoresist.get(), LIQUID,1000,228);


        //木煤气
        flList.WoodTar.register("woodtar","Wood Tar",null,GAS,1000,277,300,160);
        //煤焦油
        flList.CoalTar.register("coaltar","Coal Tar",null,GAS,1000,277,600,120);

        flList.CleanedOilExtraHeavy.register("cleanoilextraheavy","Cleaned Very Heavy Oil",null,LIQUID,1000,277,900,1000);
        flList.CleanedOilHeavy.register("cleanoilheavy","Cleaned Heavy Oil",null,LIQUID,1000,277,800,1000);
        flList.CleanedOilMedium.register("cleanoilmedium","Cleaned Medium Oil",null,LIQUID,1000,277,700,1000);
        flList.CleanedOilNormal.register("cleanoilnormal","Cleaned Oil",null,LIQUID,1000,277,700,1000);
        flList.CleanedOilLight.register("cleanoillight","Cleaned Light Oil",null,LIQUID,1000,277,600,1000);

        flList.BlendedFuel1.register("blendedfuel1","Blended Fuel 1",null, LIQUID);
        flList.BlendedFuel2.register("blendedfuel2","Blended Fuel 2",null, LIQUID);
        flList.BlendedFuel3.register("blendedfuel3","Blended Fuel 3",null, LIQUID);
        flList.BlendedFuel4.register("blendedfuel4","Blended Fuel 4",null, LIQUID);
        flList.BlendedFuel5.register("blendedfuel5","Blended Fuel 5",null, LIQUID);
        flList.BioFuel1.register("biofuel1","Bio Fuel 1",null, LIQUID);
        flList.BioFuel2.register("biofuel2","Bio Fuel 2",null, LIQUID);
        flList.EtchingSolution.register("etchingsolution","Etching Solution",null, LIQUID);
        flList.NegativeColloid.register("negativecolloid","Negative Colloid",null, LIQUID);
        flList.PositiveColloid.register("positivecolloid","Positive Colloid",null, LIQUID);

        //双酚钠盐溶液
        flList.SolutionBPASodium.register("solutionbpasodium","BPA & Sodium Solution",null,LIQUID);
        flList.SolutionPotassiumHydroxide.registerSolution("solutionpotassiumhydroxide","Potassium Hydroxide Solution",MT.KOH,1000);
        flList.MoltenTeflon.registerMolten("teflon","Teflon",MT.Teflon);
        flList.MoltenNaK.registerMolten("potassiumsodium","Potassium Sodium",matList.PotassiumSodium.mat);
        flList.HotMoltenNaK.registerMolten("potassiumsodium_hot","Hot Potassium Sodium",matList.PotassiumSodium.mat,800);

        //磷酸三丁酯
        flList.TributylPhosphate.register("TributylPhosphate","TributylPhosphate",matList.TributylPhosphate.get(), LIQUID);
        //丁醇
        flList.Butanol.register("Butanol","Butanol",matList.Butanol.get(), LIQUID);
        //碳酸铀酰络合离子溶液
        flList.UranylCarbonateSolution.register("UranylCarbonateSolution","UranylCarbonateSolution",matList.UranylCarbonateSolution.get(), LIQUID);
        //甲基叔胺
        flList.MethylTertiaryAmine.register("MethylTertiaryAmine","MethylTertiaryAmine",matList.MethylTertiaryAmine.get(), LIQUID);
        //油酸乙酯
        flList.EthylOleate.register("EthylOleate","EthylOleate",matList.EthylOleate.get(), LIQUID);
        //铀萃取剂
        flList.UraniumExtractant.register("UraniumExtractant","Uranium Extractant",null, LIQUID);
        //用过的铀萃取剂
        flList.UsedUraniumExtractant.register("UsedUraniumExtractant","Used Uranium Extractant",null, LIQUID);
        //萃取的铀
        flList.ExtractedUranium.register("ExtractedUranium","Extracted Uranium",null, LIQUID);
        flList.ExtractedUranium2.register("ExtractedUranium2","Extracted Uranium 2",null, LIQUID);
        //硅烷
        flList.Silane.register("Silane","Silane",matList.Silane.get(), GAS);
        //四氟化硅
        flList.SiliconTetrafluoride.register("SiliconTetrafluoride","SiliconTetrafluoride",matList.SiliconTetrafluoride.get(), GAS);
        flList.HotDistW.register("hotdistilledwater","Hot Distilled Water",MT.DistWater,LIQUID,1000,368);
        flList.CrackedNaphthaLow.register("CrackedNaphthaLow","Cracked Naphtha (Low Temperature)",null, LIQUID);
        flList.CrackedNaphthaMedium.register("CrackedNaphthaMedium","Cracked Naphtha (Medium Temperature)",null, LIQUID);
        flList.CrackedNaphthaHigh.register("CrackedNaphthaHigh","Cracked Naphtha (High Temperature)",null, LIQUID);
        flList.CrackedDieselLow.register("CrackedDieselLow","Cracked Diesel (Low Temperature)",null, LIQUID);
        flList.CrackedDieselMedium.register("CrackedDieselMedium","Cracked Diesel (Medium Temperature)",null, LIQUID);
        flList.CrackedDieselHigh.register("CrackedDieselHigh","Cracked Diesel (High Temperature)",null, LIQUID);
    }
}
