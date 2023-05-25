package cn.kuzuanpa.ktfruaddon.material;


import gregapi.data.OP;
import gregapi.data.RM;
import gregapi.data.TD;
import gregapi.oredict.OreDictMaterial;
import gregapi.recipes.Recipe;
import gregapi.render.TextureSet;
import gregapi.util.ST;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_DATA;
import static gregapi.data.CS.F;
import static gregapi.data.CS.ZL_FS;

public enum matList {
    //Ore Processing
    /**重铬酸铵**/AmmoniumDichromate,/**硫酸铬铵**/ AmmoniumChromicSulfate,/**硫酸铁铵**/ AmmoniumIronIIISulfate,/**硫酸铵**/ AmmoniumSulfate
    ,CookedBauxide,AcidPickledBauxide ,BauxiteRedMud
    ,/**碳酸锂**/LithiumCarbonate,/**钛酸**/MetatitanicAcid
    //Oil Process And Plastics
    ,/**次氯酸**/HypochlorousAcid,/**氯酸**/ChloricAcid,/**高氯酸**/PerchloricAcid
    ,/**苯**/Benzene
    ,/**甲醇**/Methanol,/**丙醇**/Propanol,/**丙二醇**/Propanediol
    ,/**甲醛**/Formaldehyde,/**乙醛**/Acetaldehyde,/**丙醛**/Propionaldehyde
    ,/**石油底渣**/OilScarp
    ,/**乙炔**/Acetylene
    ,/**丙酮**/Acetone
    ,/**二氯甲烷**/Dichloromethane,/**光气**/Phosgene,/**三乙基铝**/TriethylAluminium
    ,/**DPC,碳酸二苯酯**/DiphenylCarbonate,/**双酚**/BPA, /**PC,环氧树脂**/EpoxyResin

    ,/**醋酸钙**/CalciumAcetate
    ;
    public OreDictMaterial mat;
    public static class decomposingTarget{ public OreDictMaterial material;public int amount;
        public decomposingTarget(OreDictMaterial material,int amount){this.amount=amount;this.material=material;}
    }

    /** Register Material that decomposes when heating
     * @param decompAmount how many unit of this material will decompose to decompTargets material
     */
    public void registerHeatDecomp(int id, String OreDictName, String EnglishName, int decompTempK, int colorR, int colorG, int colorB, int colorA, String formula, int decompAmount,decomposingTarget... decompTargets) {
        mat = OreDictMaterial.createMaterial(id, OreDictName, EnglishName);
        mat.setTextures(TextureSet.SET_METALLIC);
        mat.setRGBa(colorR, colorG, colorB, colorA);
        mat.heat(decompTempK+1, decompTempK+2);
        mat.setOriginalMod(MOD_DATA);
        if (formula != null)mat.mTooltipChemical = formula;
        mat.put(TD.ItemGenerator.DUSTS,TD.Compounds.DECOMPOSABLE);
        ArrayList<ItemStack> outputsTmp= new ArrayList<>();
        for (decomposingTarget target:decompTargets) outputsTmp.add(OP.dust.mat(target.material,target.amount));
        ItemStack[] outputs=new ItemStack[outputsTmp.size()];
        outputsTmp.toArray(outputs);
        RM.CrucibleSmelting.addRecipe(new Recipe(F, F, F, ST.array(getDust(decompAmount)),outputs, null, null, ZL_FS, ZL_FS, 0, 0,decompTempK));
    }
    public void registerWithDust(int id, String OreDictName, String EnglishName, int meltTempK, int boilTempK, int colorR, int colorG, int colorB, int colorA, String formula) {
        mat = OreDictMaterial.createMaterial(id, OreDictName, EnglishName);
        mat.setTextures(TextureSet.SET_METALLIC);
        mat.setRGBa(colorR, colorG, colorB, colorA);
        mat.heat(meltTempK, boilTempK);
        mat.setOriginalMod(MOD_DATA);
        if (formula != null)mat.mTooltipChemical = formula;
        mat.put(TD.ItemGenerator.DUSTS);
    }
    public void register(int id, String OreDictName, String EnglishName, int meltTempK, int boilTempK, int colorR, int colorG, int colorB, int colorA, String formula) {
        mat = OreDictMaterial.createMaterial(id, OreDictName, EnglishName);
        mat.setTextures(TextureSet.SET_METALLIC);
        mat.setRGBa(colorR, colorG, colorB, colorA);
        mat.heat(meltTempK, boilTempK);
        mat.setOriginalMod(MOD_DATA);
        if (formula != null)mat.mTooltipChemical = formula;
    }

    public void registerC(int id, String OreDictName, String EnglishName, int meltTempC, int boilTempC, int colorR, int colorG, int colorB, int colorA, String formula) {
        register(id, OreDictName, EnglishName, meltTempC+235, boilTempC+235, colorR, colorG, colorB, colorA, formula);
    }
    public OreDictMaterial get() {
        return mat;
    }
    public ItemStack getDust(int amount){
        return OP.dust.mat(this.mat,amount);
    }
    public ItemStack getDustTiny(int amount){
        return OP.dustTiny.mat(this.mat,amount);
    }
    public ItemStack getDustDiv72(int amount){
        return OP.dustDiv72.mat(this.mat,amount);
    }
    public ItemStack getDustSmall(int amount){
        return OP.dustSmall.mat(this.mat,amount);
    }
}