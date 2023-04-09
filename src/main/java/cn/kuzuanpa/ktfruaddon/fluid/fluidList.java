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
import gregapi.oredict.OreDictMaterial;
import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nullable;


public enum fluidList {
    AcidPickledBauxide, SodiumAluminate, PotassiumAluminate, SodiumCarbonate, PotassiumCarbonate, LiquifiedNaturalgas, SodiumHeterotungstate, AmmoniumTungstate,
    ;
    public Fluid fluid;
    public void registerLiquid(String name, String localizedName, @Nullable OreDictMaterial material) {
        fluid = FL.create(name,localizedName,material,1);
    }
    public void registerGas(String name, String localizedName, @Nullable OreDictMaterial material) {
        fluid = FL.create(name,localizedName,material,2);
    }
    public void registerPlasma(String name, String localizedName, @Nullable OreDictMaterial material) {
        fluid = FL.create(name,localizedName,material,3);
    }

    public void register(String name, String localizedName, @Nullable OreDictMaterial material, int state) {
        fluid = FL.create(name,localizedName,material,state);
    }
    public void register(String name, String localizedName, @Nullable OreDictMaterial material, int state, long amountPerUnit) {
        fluid = FL.create(name,localizedName,material,state,amountPerUnit,277);
    }
    public void register(String name, String localizedName, @Nullable OreDictMaterial material, int state, long amountPerUnit, long temp) {
        fluid = FL.create(name,localizedName,material,state,amountPerUnit,temp);
    }
    public void register(String name, String localizedName, @Nullable OreDictMaterial material, int state, long amountPerUnit, long temp, int density) {
        fluid = FL.create(name,localizedName,material,state,amountPerUnit,temp);
        fluid.setDensity(density);
    }
    public void register(String name, String localizedName, @Nullable OreDictMaterial material, int state, long amountPerUnit, long temp, int density, int viscosity) {
        fluid = FL.create(name,localizedName,material,state,amountPerUnit,temp);
        fluid.setViscosity(viscosity);
        fluid.setDensity(density);
    }
    public void register(String name, String localizedName, @Nullable OreDictMaterial material, int state, long amountPerUnit, long temp, int density, int viscosity, int luminosity) {
        fluid = FL.create(name,localizedName,material,state,amountPerUnit,temp);
        fluid.setLuminosity(luminosity);
        fluid.setViscosity(viscosity);
        fluid.setDensity(density);
    }



    public Fluid get() {
        return fluid;
    }
}