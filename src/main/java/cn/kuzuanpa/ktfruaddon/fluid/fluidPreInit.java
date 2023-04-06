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

public class fluidPreInit {
    public fluidPreInit(FMLPreInitializationEvent e){
        fluidList.AcidPickledBauxide.register("acidpickledbauxide", "Acid Pickled Bauxide"    , null                  , 1);
        fluidList.SodiumAluminate.register("sodiumaluminate","Sodium Aluminate",null,1);
        fluidList.PotassiumAluminate.register("potassiumaluminate","Potassium Aluminate",null,1);
        fluidList.SodiumCarbonate.register("sodiumcarbonate","Sodium Carbonate",null,1);
        fluidList.PotassiumCarbonate.register("potassiumcarbonate","Potassium Carbonate",null,1);
        fluidList.LiquifiedNaturalgas.register("liquifiednaturalgas","Liquified Natural Gas",null,1);
        fluidList.SodiumHeterotungstate.register("sodiumheterotungstate","Sodium Heterotungstate",null,1);
        fluidList.AmmoniumTungstate.register("ammoniumtungstate","Ammonium Tungstate",null,1);}
}
