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
    private final int PlASMA=3;
    private final int GAS=2;
    private final int LIQUID=1;
    public fluidPreInit(FMLPreInitializationEvent e){
        flList.AcidPickledBauxide.register("acidpickledbauxide", "Acid Pickled Bauxide",null,LIQUID);
        flList.SodiumAluminate.registerSolution("sodiumaluminate","Sodium Aluminate",MT.NaAlO2,1000);
        flList.PotassiumAluminate.registerSolution("potassiumaluminate","Potassium Aluminate", MT.KAlO2,1000);
        flList.SodiumCarbonate.register("sodiumcarbonate","Sodium Carbonate",MT.Na2CO3,LIQUID);
        flList.PotassiumCarbonate.register("potassiumcarbonate","Potassium Carbonate",MT.K2CO3,LIQUID);
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


        //双酚钠盐溶液
        flList.SolutionBPASodium.register("solutionbpasodium","BPA & Sodium Solution",null,LIQUID);

        //四氟乙烯
        flList.Tetrafluoroethylene.register("tetrafluoroethylene","Tetrafluoroethylene",null,GAS);

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


        //木煤气
        flList.WoodTar.register("woodtar","Wood Tar",null,GAS,1000,277,-300,160);
        //煤焦油
        flList.CoalTar.register("coaltar","Coal Tar",null,GAS,1000,277,-600,120);

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
    }
}
