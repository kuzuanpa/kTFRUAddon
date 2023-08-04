package cn.kuzuanpa.ktfruaddon.material;


import gregapi.data.OP;
import gregapi.data.TD;
import gregapi.oredict.OreDictMaterial;
import gregapi.render.TextureSet;
import net.minecraft.item.ItemStack;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_DATA;

public enum matList {
    //Ore Processing
    /**重铬酸铵**/AmmoniumDichromate,/**硫酸铬铵**/ AmmoniumChromicSulfate,/**硫酸铁铵**/ AmmoniumIronIIISulfate,/**硫酸铵**/ AmmoniumSulfate
    ,CookedBauxide,AcidPickledBauxide ,BauxiteRedMud
    ,/**碳酸锂**/LithiumCarbonate,/**钛酸**/MetatitanicAcid
    //Oil Process And Plastics
    ,/**次氯酸**/HypochlorousAcid,/**氯酸**/ChloricAcid,/**高氯酸**/PerchloricAcid
    ,/**苯**/Benzene,/**甲苯**/Toluene
    ,/**甲醇**/Methanol,/**丙醇**/Propanol,/**丙二醇**/Propanediol
    ,/**甲醛**/Formaldehyde,/**乙醛**/Acetaldehyde,/**丙醛**/Propionaldehyde,/**乙烷**/Ethane
    ,/**石油底渣**/OilScarp
    ,/**乙炔**/Acetylene
    ,/**丙酮**/Acetone
    ,/**一氯甲烷**/Chloromethane,/**二氯甲烷**/Dichloromethane,/**三氯甲烷/氯仿**/Chloroform,/**光气**/Phosgene,/**氯丙烯**/AllylChloride,/**二氯丙醇**/DichloroPropanol,/**环氧氯丙烷**/Epichlorohydrin
    ,/**三乙基铝**/TriethylAluminium
    ,/**DPC,碳酸二苯酯**/DiphenylCarbonate,/**双酚**/BPA, /**PC,聚碳酸酯/工程塑料**/Polycarbonate ,/**四氟乙烯**/Tetrafluoroethylene

    ,/**铬酸锌**/ ZincChromate,/**氧化锌**/Zincoxide,/**氯化锌**/ZincChloride
    ,/**醋酸钙**/CalciumAcetate
    ;
    public OreDictMaterial mat;
    public OreDictMaterial registerWithDust(int id, String OreDictName, String EnglishName, int meltTempK, int boilTempK, int colorR, int colorG, int colorB, int colorA, String formula) {
        mat = OreDictMaterial.createMaterial(id, OreDictName, EnglishName);
        mat.setTextures(TextureSet.SET_METALLIC);
        mat.setRGBa(colorR, colorG, colorB, colorA);
        mat.heat(meltTempK, boilTempK);
        mat.setOriginalMod(MOD_DATA);
        if (formula != null)mat.mTooltipChemical = formula;
        mat.put(TD.ItemGenerator.DUSTS);
        return mat;
    }
    public OreDictMaterial register(int id, String OreDictName, String EnglishName, int meltTempK, int boilTempK, int colorR, int colorG, int colorB, int colorA, String formula) {
        mat = OreDictMaterial.createMaterial(id, OreDictName, EnglishName);
        mat.setTextures(TextureSet.SET_METALLIC);
        mat.setRGBa(colorR, colorG, colorB, colorA);
        mat.heat(meltTempK, boilTempK);
        mat.setOriginalMod(MOD_DATA);
        if (formula != null)mat.mTooltipChemical = formula;
        return mat;
    }

    public OreDictMaterial registerC(int id, String OreDictName, String EnglishName, int meltTempC, int boilTempC, int colorR, int colorG, int colorB, int colorA, String formula) {
        return register(id, OreDictName, EnglishName, meltTempC+273, boilTempC+273, colorR, colorG, colorB, colorA, formula);
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