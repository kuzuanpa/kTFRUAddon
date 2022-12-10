package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import gregapi.data.FL;
import gregapi.data.FM;
import gregapi.data.MT;
import gregapi.data.RM;
import gregapi.oredict.OreDictMaterial;
import gregapi.util.OM;
import gregapi.util.ST;

import static gregapi.data.CS.*;

public class Chemistry {
    public Chemistry() {
        RM.Centrifuge.addRecipeX(F, 120, 80, ST.array(ST.tag(0)),FL.array(FL.make("gas_natural_gas", 4000)), FL.array(FL.make("liquifiednaturalgas", 2100)), ZL_IS);
        FM.Burn.addRecipe0(T, -64, 50, FL.make("liquifiednaturalgas", 5), FL.CarbonDioxide.make(9), FL.Water.make(6));
        FM.Engine.addRecipe0(T, -64, 62, FL.make("liquifiednaturalgas", 5), FL.CarbonDioxide.make(9), FL.Water.make(6));

    }
}
