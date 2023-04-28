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

import cn.kuzuanpa.ktfruaddon.recipe.recipeManager;
import gregapi.data.FL;
import gregapi.data.MT;
import gregapi.data.RM;
import gregapi.oredict.OreDictMaterial;
import gregapi.util.OM;
import gregapi.util.ST;
import net.minecraftforge.fluids.Fluid;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static gregapi.data.CS.*;
import static gregapi.data.CS.ZL_IS;


public enum flList {
    AcidPickledBauxide, SodiumAluminate, PotassiumAluminate, SodiumCarbonate, PotassiumCarbonate, LiquifiedNaturalgas, SodiumHeterotungstate, AmmoniumTungstate,
    ;
    public Fluid fluid;
    public String name;
    /**This will create Gas with transforming recipe**/
    public void registerGas(String name, String localizedName, @NotNull OreDictMaterial material,int temp,int density) {
        fluid = FL.create(name,localizedName,material,3,20000,temp);
        fluid.setDensity(density);
        fluid.setViscosity(16);
        this.name=name;
        RM.CryoMixer.addRecipeX(T,32,40, ST.array(ZL_IS),FL.array(FL.make(fluid,20000)),FL.array(), OM.dust(material,U));
        RM.Drying.addRecipeX(T,16,10, ST.array(OM.dust(material,U)),FL.array(ZL_FS),FL.array(FL.make(fluid,20000)),ZL_IS);
        recipeManager.HeatMixer.addRecipeX(T,16,10, ST.array(OM.dust(material,U)),FL.array(ZL_FS),FL.array(FL.make(fluid,20000)),ZL_IS);

    }
    /**This will create Solutions with transforming recipe**/
    public void registerSolution(String name, String localizedName, @NotNull OreDictMaterial material, int AmountPerUnit) {
        fluid = FL.create(name,localizedName,material,1);
        this.name=name;
        RM.Drying.addRecipeX(T,32,40, ST.array(ZL_IS),FL.array(FL.make(fluid,AmountPerUnit)),FL.array(FL.DistW.make(800)), OM.dust(material,U));
        RM.Mixer.addRecipeX(T,16,10, ST.array(OM.dust(material,U)),FL.array(MT.H2O.liquid(U , T)),FL.array(FL.make(fluid,AmountPerUnit)),ZL_IS);
        RM.Bath.addRecipeX(T,0,20, ST.array(OM.dust(material,U)),FL.array(MT.H2O.liquid(U , T)),FL.array(FL.make(fluid,AmountPerUnit)),ZL_IS);
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



    public Fluid get() {
        return fluid;
    }

    public String getName(){return name;}
}