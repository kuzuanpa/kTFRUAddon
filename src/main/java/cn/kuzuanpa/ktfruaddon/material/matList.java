package cn.kuzuanpa.ktfruaddon.material;


import gregapi.oredict.OreDictMaterial;
import gregapi.render.TextureSet;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_DATA;

public enum matList {
    AmmoniumDichromate, AmmoniumChromicSulfate, AmmoniumIronIIISulfate,Sulfanilamide,CookedBauxide,AcidPickledBauxide
    ,BauxiteRedMud,LithiumCarbonate,MetatitanicAcid
    ;
    public OreDictMaterial mat;
    public void register(int id, String OreDictName, String EnglishName, int meltTemp, int boilTemp, int colorR, int colorG, int colorB, int colorA, String formula) {
        mat = OreDictMaterial.createMaterial(id, OreDictName, EnglishName);
        mat.setTextures(TextureSet.SET_METALLIC);
        mat.setRGBa(colorR, colorG, colorB, colorA);
        mat.heat(meltTemp, boilTemp);
        mat.setOriginalMod(MOD_DATA);
        if (formula != null)mat.mTooltipChemical = formula;
    }

    public OreDictMaterial get() {
        return mat;
    }
}