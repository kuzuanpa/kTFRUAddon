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

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import gregapi.data.MT;

public class fluidPreInit {
    public fluidPreInit(FMLPreInitializationEvent e){
        flList.AcidPickledBauxide.registerLiquid("acidpickledbauxide", "Acid Pickled Bauxide");
        flList.SodiumAluminate.registerSolution("sodiumaluminate","Sodium Aluminate",MT.NaAlO2,1000);
        flList.PotassiumAluminate.registerSolution("potassiumaluminate","Potassium Aluminate", MT.KAlO2,1000);
        flList.SodiumCarbonate.registerLiquid("sodiumcarbonate","Sodium Carbonate");
        flList.PotassiumCarbonate.registerLiquid("potassiumcarbonate","Potassium Carbonate");
        flList.LiquifiedNaturalgas.registerLiquid("liquifiednaturalgas","Liquified Natural Gas");
        flList.SodiumHeterotungstate.registerLiquid("sodiumheterotungstate","Sodium Heterotungstate");
        flList.AmmoniumTungstate.registerLiquid("ammoniumtungstate","Ammonium Tungstate");}
}
