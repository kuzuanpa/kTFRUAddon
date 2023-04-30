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
        flList.SodiumCarbonate.register("sodiumcarbonate","Sodium Carbonate",null,LIQUID);
        flList.PotassiumCarbonate.register("potassiumcarbonate","Potassium Carbonate",null,LIQUID);
        flList.LiquifiedNaturalgas.register("liquifiednaturalgas","Liquified Natural Gas",null,LIQUID);
        flList.SodiumHeterotungstate.register("sodiumheterotungstate","Sodium Heterotungstate",null,LIQUID);
        flList.AmmoniumTungstate.register("ammoniumtungstate","Ammonium Tungstate",null,LIQUID);
        flList.Acetone.register("acetone","Acetone", matList.Acetone.mat,LIQUID);
        flList.Acetylene.register("acetylene","Acetylene",matList.Acetylene.mat, GAS);
    }
}
