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
import gregapi.data.FL;
import gregapi.data.MT;
import gregapi.data.TD;

import javax.swing.plaf.ListUI;

import static cn.kuzuanpa.ktfruaddon.code.Variables.Symbols;
import static gregapi.data.CS.NUM_SUB;

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

        //丙酮
        flList.Acetone.register("acetone","Acetone", matList.Acetone.mat,LIQUID,1000,277,1120);
        //乙炔
        flList.Acetylene.register("acetylene","Acetylene",matList.Acetylene.mat, GAS,1000,277,-90);
        //甲醇
        flList.Methanol.register("Methanol","Methanol",matList.Methanol.get(), LIQUID,1000,277,790);
        //丙醇
        flList.Propanol.register("Propanol","Propanol",matList.Propanol.get(), LIQUID,1000,277,894);
        //丙二醇
        flList.Propanediol.register("Propanediol","Propanediol",matList.Propanediol.get(), LIQUID,1000,277,1036);
        //次氯酸
        flList.HypochlorousAcid.register("HypochlorousAcid","Hypochlorous Acid",matList.HypochlorousAcid.get(), LIQUID);
        //氯酸
        flList.ChloricAcid.register("ChloricAcid","Chloric Acid",matList.ChloricAcid.get(), LIQUID);
        //高氯酸
        flList.PerchloricAcid.register("PerchloricAcid","Perchloric Acid",matList.PerchloricAcid.get(),LIQUID);
        //光气
        flList.Phosgene.register("Phosgene","Phosgene",matList.Phosgene.get(), GAS,1000,277,-76);
        //二氯甲烷
        flList.Dichloromethane.register("Dichloromethane","Dichloromethane",matList.Dichloromethane.get(), LIQUID,1000,277,1325);



        flList.DesaltOilExtraHeavy.register("desaltoilextraheavy","Desalinized Very Heavy Oil",null,LIQUID,1000,277,900,1000);
        flList.DesaltOilHeavy.register("desaltoilheavy","Desalinized Heavy Oil",null,LIQUID,1000,277,800,1000);
        flList.DesaltOilMedium.register("desaltoilmedium","Desalinized Medium Oil",null,LIQUID,1000,277,700,1000);
        flList.DesaltOilNormal.register("desaltoilnormal","Desalinized Oil",null,LIQUID,1000,277,700,1000);
        flList.DesaltOilLight.register("desaltoillight","Desalinized Light Oil",null,LIQUID,1000,277,600,1000);

        flList.OilDesulphurizationAgent.register("oildesulphurizationagent","Oil Desulphurization Agent",null,LIQUID);
        flList.OilGas.register("oilgas","Oil Gas",null,GAS,1000,277,100,16);

        flList.CarbonMonoxide.register("carbonmonoxide", "carbon Monixide",null,GAS,1000,277,100);
        //初底油
        flList.InitalBottomOil.register("initalbottomoil","Inital Bottom Oil",null, LIQUID,1000,277,500);
        //石脑油
        flList.Naphtha.register("naphtha","Naphtha",null,LIQUID);

        flList.CleanedOilExtraHeavy.register("cleanoilextraheavy","Cleaned Very Heavy Oil",null,LIQUID,1000,277,900,1000);
        flList.CleanedOilHeavy.register("cleanoilheavy","Cleaned Heavy Oil",null,LIQUID,1000,277,800,1000);
        flList.CleanedOilMedium.register("cleanoilmedium","Cleaned Medium Oil",null,LIQUID,1000,277,700,1000);
        flList.CleanedOilNormal.register("cleanoilnormal","Cleaned Oil",null,LIQUID,1000,277,700,1000);
        flList.CleanedOilLight.register("cleanoillight","Cleaned Light Oil",null,LIQUID,1000,277,600,1000);
    }
}
