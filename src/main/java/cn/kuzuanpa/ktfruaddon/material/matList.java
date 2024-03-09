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
    ,/**三乙基铝**/TriethylAluminium,/**电石**/CalciumCarbide,/**氢氧化钙**/CalciumHydroxide
    ,/**DPC,碳酸二苯酯**/DiphenylCarbonate,/**双酚**/BPA, /**PC,聚碳酸酯/工程塑料**/Polycarbonate ,/**四氟乙烯**/Tetrafluoroethylene,/**环氧树脂**/EpoxyResin
    ,/**乙酸乙烯酯**/VinylAcetate,/**聚乙酸乙烯**/PolyVinylAcetate,/**丁二烯**/Butadiene
    ,/**铬酸锌**/ ZincChromate,/**氧化锌**/Zincoxide,/**氯化锌**/ZincChloride
    ,/**醋酸钙**/CalciumAcetate,/**甲基氯化镁**/MethylmagnesiumChloride,/**一甲三氯硅烷**/Methyltrichlorosilane,/**二甲二氯硅烷**/Dichlorodimethylsilane,/**硅橡胶液**/SiliconeRubber,/**氯化汞**/MercuryIIChloride,/**二氯乙烷**/DichloroEthane,/**二氯丙烷**/DichloroPropane,/**氯乙烯**/VinylChloride,/**乙苯**/Ethylbenzene,/**一氯乙苯**/ChloroPhenylethane,/**乙烯苯**/Styrene,/**丁苯橡胶**/SBR,/**异戊二烯**/Isoprene,/**氯乙醇**/Chloroethanol,/**乙二醇**/EthyleneGlycol,/**聚甲基丙烯酸甲酯**/PolymethylMethacrylate,/**聚乙烯醇**/PVA,/**六氟丙烯**/HPF,/**四氟磺内酯**/TFES,/**全氟环氧丙烷**/HFPO,/**全氟磺酸单体前体**/PrecursorPSVE,/**全氟磺酸单体**/PSVE,/**全氟树脂**/PFSA,/**八氟环丁烷**/Perfluorocyclobutane,/**四氯化硅**/Tetrachorosilane,/**氢溴酸**/HydrobromicAcid
    ,/**萘**/Naphthalene,/**1-萘酚**/Naphthalenol,/**2-氨基,1-萘酚盐酸盐**/NaphthalenolAminoHydrochloride,/**2-重氮,1-萘酚**/DiazoNaphthol,/**丙二醇甲醚**/MethoxyPropanol,/**丙二醇甲醚醋酸酯**/PGMEA,/**重氮系光刻胶**/DNQPhotoresist,/**环己醇**/Cyclohexanol,/**环己酮**/Cyclohexanone,/**苊**/Acenaphthylene,/**5-硝基苊**/Nitroacenaphthene,/**PMMA系光刻胶**/PMMAPhotoresist,/**硝酸铵**/AmmoniumNitrate
    ,/**水合硝酸铀**/UraniumNitrateHexahydrate,/**磷酸三丁酯**/TributylPhosphate,/**丁醇**/Butanol,/**碳酸铀酰络合离子溶液**/UranylCarbonateSolution,/**甲基叔胺**/MethylTertiaryAmine,/**油酸乙酯**/EthylOleate,/**油酸**/OleicAcid,/**晶质铀浸渣**/UraniniteCinder,/**沥青铀浸渣**/PitchblendeCinder,/**硅烷**/Silane,/**硅化镁**/MagnesiumSilicide,/**氯化铵**/AmmoniumChloride,/**四氟化硅**/SiliconTetrafluoride,/**四氢铝钠**/SodiumAluminiumHydride

    ,/**钠钾合金**/PotassiumSodium

    ,/**CaiXuKunMeme**/ HensSoPretty

    ,/**智金**/Ij
    ;
    public OreDictMaterial mat;
    public OreDictMaterial registerWithDust(int id, String OreDictName, String EnglishName, int meltTempK, int boilTempK, int colorR, int colorG, int colorB, int colorA, String formula) {
        mat = register(id, OreDictName, EnglishName, meltTempK, boilTempK, colorR, colorG, colorB, colorA, formula);
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