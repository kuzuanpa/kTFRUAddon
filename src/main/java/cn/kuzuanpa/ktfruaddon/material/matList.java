package cn.kuzuanpa.ktfruaddon.material;


import gregapi.data.OP;
import gregapi.oredict.OreDictMaterial;
import gregapi.render.TextureSet;
import net.minecraft.item.ItemStack;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_DATA;

public enum matList {
    //Ore Processing
    AmmoniumDichromate, AmmoniumChromicSulfate, AmmoniumIronIIISulfate,Sulfanilamide,CookedBauxide,AcidPickledBauxide
    ,BauxiteRedMud,LithiumCarbonate,MetatitanicAcid
    //Oil Process And Plastics
    ,HypochlorousAcid,ChloricAcid,PerchloricAcid
    ,Methanol,Propanol,Propanediol
    ,Benzene
    ,Formaldehyde,Acetaldehyde,Propionaldehyde
    ,OilScarp
    ,Acetylene,Acetone
    ,Dichloromethane,Phosgene,TriethylAluminium
    ,DiphenylCarbonate,BPA, EpoxyResin

    ,CalciumAcetate
    ;
    public OreDictMaterial mat;
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