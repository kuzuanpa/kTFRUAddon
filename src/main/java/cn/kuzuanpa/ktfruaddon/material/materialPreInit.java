/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.material;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import gregapi.data.TD;

import static cn.kuzuanpa.ktfruaddon.code.Variables.Symbols;
import static gregapi.data.CS.NUM_SUB;
/**Register materials when PreInit
 * Notice: Don't change the order of codes and always append newline
 **/
public class materialPreInit {
    public materialPreInit(FMLPreInitializationEvent aEvent) {
        //todo: make these id static when releasing
        System.out.println("123");
        int i=21999;
        matList.AmmoniumDichromate.registerWithDust(++i, "AmmoniumDichromate", "Ammonium Dichromate",456, 460, 255, 160, 51, 0,  "Cr"+NUM_SUB[2]+"O"+NUM_SUB[7]+"(NH3)"+NUM_SUB[2]);
        matList.AmmoniumChromicSulfate.registerWithDust(++i, "AmmoniumChromicSulfate", "Ammonium Chromic Sulfate",456, 460, 72, 0, 161, 0,  "NH"+NUM_SUB[4]+"Cr(SO"+NUM_SUB[4]+")"+NUM_SUB[2]);
        matList.AmmoniumIronIIISulfate.registerWithDust(++i, "AmmoniumIronIIISulfate", "Ammonium Iron(III) Sulfate", 368, 524, 230, 220, 242, 0, "NH"+NUM_SUB[4]+"Fe(SO"+NUM_SUB[4]+")"+NUM_SUB[2]);
        matList.AmmoniumSulfate.registerWithDust(++i, "AmmoniumSulfate", "Ammonium Sulfate",509, 510, 251, 251, 216, 2, "(NH"+NUM_SUB[4]+")"+NUM_SUB[2]+"SO"+NUM_SUB[4]);
        matList.CookedBauxide .registerWithDust(++i, "CookedBauxide", "Cooked Bauxide", 2370, 3234, 229, 141, 0, 4, null);
        matList.AcidPickledBauxide.registerWithDust(++i, "CookedBauxide", "Cooked Bauxide", 2370, 3234,200, 168, 0, 4, null);
        matList.BauxiteRedMud.registerWithDust(++i, "BauxiteRedMud", "Bauxite Red Mud", 1790, 3234, 148, 0, 12, 0,null);
        matList.LithiumCarbonate.registerWithDust(++i, "LithiumCarbonate", "Lithium Carbonate",943, 1582, 248, 244, 248, 0,  "Li"+NUM_SUB[2]+"CO"+NUM_SUB[3]);
        matList.MetatitanicAcid.registerWithDust(++i, "MetatitanicAcid", "Metatitanic Acid", 748,749, 248, 244, 248, 0, "TiO(OH)"+NUM_SUB[2]);
        //石油底
        matList.OilScarp.registerC(++i,"OilScarp","Oil Scarp",90,231,12,9,2,0,null)
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.DUSTS);
        //甲醇
        matList.Methanol.registerC(++i,"Methanol","Methanol",-98,64,90,80,210,140,"CH"+NUM_SUB[3]+"OH")
                .put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //乙醇
        //Already exists in GT
        //丙醇
        matList.Propanol.registerC(++i,"Propanol","Propanol",-127,97,60,60,150,140,"CH"+NUM_SUB[2]+"CH"+NUM_SUB[2]+"CH"+NUM_SUB[3]+"OH")
                .put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //丙二醇
        matList.Propanediol.registerC(++i,"Propanediol","Propanediol",-60,187,60,60,150,140,"CH"+NUM_SUB[2]+"CH"+NUM_SUB[2]+"CH"+NUM_SUB[3]+"OH")
                .put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //乙烷
        matList.Ethane.registerC(++i,"Ethane","Ethane",-172,-88,60,60,150,140,"CH"+NUM_SUB[3]+"CH"+NUM_SUB[3])
                .put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //丙酮
        matList.Acetone.register(++i,"Acetone","Acetone",143,293,210,210,210,140,"C"+NUM_SUB[3]+"H"+NUM_SUB[6]+"O")
                .put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //乙炔
        matList.Acetylene.register(++i,"Acetylene","Acetylene",147,217,210,210,210,180,"CH"+Symbols[0]+"CH")
                .put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.GASES);
        //苯
        matList.Benzene.registerC(++i,"Benzene","Benzene",5,80,255,255,130,140,"C"+NUM_SUB[6]+"H"+NUM_SUB[6])
                .put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.ItemGenerator.LIQUID);
        //甲苯
        matList.Toluene.registerC(++i,"Toluene","Toluene",-95,110,255,255,130,140,"CH"+NUM_SUB[3]+"C"+NUM_SUB[5]+"H"+NUM_SUB[6])
                .put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.ItemGenerator.LIQUID);
        //甲醛
        matList.Formaldehyde.registerC(++i,"Formaldehyde","Formaldehyde",-15,97,255,255,255,255,"CH"+NUM_SUB[2]+"O")
                .put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.ItemGenerator.GASES);
        //乙醛
        matList.Acetaldehyde.registerC(++i,"Acetaldehyde","Acetaldehyde",-125,21,255,255,255,255,"O=CHCH"+NUM_SUB[3])
                .put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.ItemGenerator.GASES);
        //丙醛
        matList.Propionaldehyde.registerC(++i,"Propionaldehyde","Propionaldehyde",-81,46,255,255,255,255,"O=CHCH"+NUM_SUB[2]+"CH"+NUM_SUB[3])
                .put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.ItemGenerator.GASES);
        //次氯酸
        matList.HypochlorousAcid.registerC(++i,"HypochlorousAcid","Hypochlorous Acid",0,100,180,255,180,150,"HClO")
                .put(TD.Properties.ACID,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //氯酸
        matList.ChloricAcid.registerC(++i,"ChloricAcid","Chloric Acid",0,41,235,255,210,210,"HClO"+NUM_SUB[3])
                .put(TD.Properties.ACID,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //高氯酸
        matList.PerchloricAcid.registerC(++i,"PerchloricAcid","Perchloric Acid",-112,19,255,255,255,255,"HClO"+NUM_SUB[4])
                .put(TD.Properties.ACID,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //光气
        matList.Phosgene.registerC(++i,"Phosgene","Phosgene",-128,8,240,245,255,240,"COCl"+NUM_SUB[2])
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.GASES);
        //氯丙烯
        matList.AllylChloride.registerC(++i,"AllylChloride","AllylChloride",-136,44,214,231,222,0,"CH"+NUM_SUB[2]+"=CHCH"+NUM_SUB[2]+"Cl")
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //二氯丙醇
        matList.DichloroPropanol.registerC(++i,"DichloroPropanol","DichloroPropanol",-4,173,245,245,245,170,"CHOH(CH"+NUM_SUB[2]+"Cl)"+NUM_SUB[2])
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID,TD.Properties.FLAMMABLE);
        //环氧氯丙烷
        matList.Epichlorohydrin.registerC(++i,"Epichlorohydrin","Epichlorohydrin",-57,115,245,245,245,170,"C"+NUM_SUB[3]+"H"+NUM_SUB[5]+"ClO")
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID,TD.Properties.FLAMMABLE);
        //三乙基铝
        matList.TriethylAluminium.registerC(++i,"TriethylAluminium","Triethyl Aluminium",-50,128,255,255,255,255,"Al(CH"+NUM_SUB[2]+"CH"+NUM_SUB[3]+")"+NUM_SUB[3])
                .put(TD.Compounds.DECOMPOSABLE,TD.Properties.FLAMMABLE,TD.ItemGenerator.DUSTS);
        //电石
        matList.CalciumCarbide.registerC(++i,"CalciumCarbide","CalciumCarbide",447,2300,255,255,255,130,"CaC"+NUM_SUB[2])
                .put(TD.ItemGenerator.DUSTS);
        //氢氧化钙
        matList.CalciumHydroxide.registerC(++i,"CalciumHydroxide","CalciumHydroxide",580,2850,255,255,255,130,"Ca(OH)"+NUM_SUB[2])
                .put(TD.ItemGenerator.DUSTS);
        //一氯甲烷
        matList.Chloromethane.registerC(++i,"Chloromethane","Chloromethane",-97,-24,255,255,255,255,"CH"+NUM_SUB[3])
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //二氯甲烷
        matList.Dichloromethane.registerC(++i,"Dichloromethane","Dichloromethane",-97,39,255,255,255,255,"CH"+NUM_SUB[2]+"Cl"+NUM_SUB[2])
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //三氯甲烷/氯仿
        matList.Chloroform.registerC(++i,"Chloroform","Chloroform",-63,61,213,212,212,148,"CHCl"+NUM_SUB[3])
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);

        //醋酸钙
        matList.CalciumAcetate.registerC(++i,"CalciumAcetate","Calcium Acetate",395,397,245,246,245,0,"CH"+NUM_SUB[3]+"COOCaOOCCH"+NUM_SUB[3])
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.DUSTS);
        //铬酸锌
        matList.ZincChromate.registerC(++i,"ZincChromate","Zinc Chromate",316,651,214,198,0,0,"ZnCrO"+NUM_SUB[4])
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.DUSTS);
        //氧化锌
        matList.Zincoxide.registerC(++i,"ZincOxide","ZincOxide",1975,2360,214,231,222,0,"ZnO")
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.DUSTS);
        //氯化锌
        matList.ZincChloride.registerC(++i,"ZincChloride","ZincChloride",293,732,214,231,222,0,"ZnCl"+NUM_SUB[2])
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.DUSTS);
        matList.BPA.registerC(++i,"BPA","BPA",158,226,255,255,255,10,"CH"+NUM_SUB[3]+"C"+"(PhOH)"+NUM_SUB[2]+"CH"+NUM_SUB[3])
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.DUSTS);
        //DPC,碳酸二苯酯
        matList.DiphenylCarbonate.registerC(++i,"DiphenylCarbonate","Diphenyl Carbonate",82,301,255,255,255,10,"PhOCOOPh")
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.DUSTS);
        //PC,聚碳酸酯
        matList.Polycarbonate.registerC(++i,"Polycarbonate","Polycarbonate",220,310,255,255,255,10,"C"+NUM_SUB[16]+"H"+NUM_SUB[18]+"O"+NUM_SUB[5])
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.DUSTS);
        //四氟乙烯
        matList.Tetrafluoroethylene.registerC(++i,"Tetrafluoroethylene","Tetrafluoroethylene",-142,-76,255,255,255,130,"CF"+NUM_SUB[2]+"CF"+NUM_SUB[2])
                .put(TD.Compounds.DECOMPOSABLE);
        //环氧树脂
        matList.EpoxyResin.registerC(++i,"EpoxyResin","Epoxy Resin",115,252,255,255,255,10,"(C"+NUM_SUB[3]+"H"+NUM_SUB[5]+"ClO)")
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.DUSTS);
        //乙酸乙烯酯
        matList.VinylAcetate.registerC(++i,"VinylAcetate","VinylAcetate",-93,72,255,255,255,130,"CH"+NUM_SUB[3]+"CHOOCH=CH"+NUM_SUB[2])
                .put();
        //聚乙酸乙烯
        matList.PolyVinylAcetate.registerC(++i,"PolyVinylAcetate","PolyVinylAcetate",60,143,255,255,255,130,"(C"+NUM_SUB[4]+"H"+NUM_SUB[6]+"O"+NUM_SUB[2]+")")
                .put();
        //丁二烯
        matList.Butadiene.registerC(++i,"Butadiene","Butadiene",-109,-4,255,255,255,130,"CH"+NUM_SUB[2]+"=CHCH=CH2"+NUM_SUB[2])
                .put();
        //甲基氯化镁
        matList.MethylmagnesiumChloride.registerC(++i,"MethylmagnesiumChloride","MethylmagnesiumChloride",-30,66,255,255,255,130,"CH"+NUM_SUB[3]+"ClMg")
                .put(TD.ItemGenerator.LIQUID);
        //一甲三氯硅烷
        matList.Methyltrichlorosilane.registerC(++i,"Methyltrichlorosilane","Methyltrichlorosilane",-77,66,255,255,255,130,"CH"+NUM_SUB[3]+"Cl"+NUM_SUB[3]+"Si")
                .put(TD.ItemGenerator.LIQUID);
        //二甲二氯硅烷
        matList.Dichlorodimethylsilane.registerC(++i,"Dichlorodimethylsilane","Dichlorodimethylsilane",-76,60,255,255,255,130,"(CH"+NUM_SUB[3]+")"+NUM_SUB[2]+"Cl"+NUM_SUB[2]+"Si")
                .put(TD.ItemGenerator.LIQUID);
        //硅橡胶液
        matList.SiliconeRubber.registerC(++i,"SiliconeRubber","SiliconeRubber",-98,237,255,255,255,130,"")
	            .put(TD.ItemGenerator.LIQUID);
        //氯化汞
        matList.MercuryIIChloride.registerC(++i,"MercuryIIChloride","MercuryIIChloride",277,302,255,255,255,130,"Cl"+NUM_SUB[2]+"Hg"+NUM_SUB[2])
                .put(TD.ItemGenerator.DUSTS);
        //二氯乙烷
        matList.DichloroEthane.registerC(++i,"DichloroEthane","DichloroEthane",-35,82,255,255,255,130,"CH"+NUM_SUB[2]+"ClCH"+NUM_SUB[2]+"Cl")
                .put(TD.ItemGenerator.LIQUID);
        //二氯丙烷
        matList.DichloroPropane.registerC(++i,"DichloroPropane","DichloroPropane",-100,95,255,255,255,130,"CH"+NUM_SUB[3]+"CH"+NUM_SUB[2]+"Cl=CH"+NUM_SUB[2]+"Cl")
                .put(TD.ItemGenerator.LIQUID);
        //氯乙烯
        matList.VinylChloride.registerC(++i,"VinylChloride","VinylChloride",-153,-13,255,255,255,130,"CH"+NUM_SUB[2]+"=CHCl")
                .put(TD.ItemGenerator.LIQUID);
        //乙苯
        matList.Ethylbenzene.registerC(++i,"Ethylbenzene","Ethylbenzene",-95,136,255,255,255,130,"PhCH"+NUM_SUB[2]+"CH"+NUM_SUB[3])
                .put(TD.ItemGenerator.LIQUID);
        //一氯乙苯
        matList.ChloroPhenylethane.registerC(++i,"ChloroPhenylethane","ChloroPhenylethane",-42,90,255,255,255,130,"PhCHClCH"+NUM_SUB[3])
                .put(TD.ItemGenerator.LIQUID);
        //乙烯苯
        matList.Styrene.registerC(++i,"Styrene","Styrene",-31,145,255,255,255,130,"PhCH=CH"+NUM_SUB[2])
                .put(TD.ItemGenerator.LIQUID);
        //丁苯橡胶液
        matList.SBR.registerC(++i,"SBR","SBR",-59,390,255,255,255,130,"")
                .put(TD.ItemGenerator.LIQUID);
        //异戊二烯
        matList.Isoprene.registerC(++i,"Isoprene","Isoprene",-145,32,255,255,255,130,"CH"+NUM_SUB[2]+"=CCH"+NUM_SUB[3]+"CHCH"+NUM_SUB[2])
                .put(TD.ItemGenerator.LIQUID);
        //氯乙醇
        matList.Chloroethanol.registerC(++i,"Chloroethanol","Chloroethanol",-67,129,255,255,255,130,"C"+NUM_SUB[2]+"H"+NUM_SUB[5]+"ClO")
                .put(TD.ItemGenerator.LIQUID);
        //乙二醇
        matList.EthyleneGlycol.registerC(++i,"EthyleneGlycol","EthyleneGlycol",-13,197,255,255,255,130,"(CH"+NUM_SUB[2]+"OH)"+NUM_SUB[2])
                .put(TD.ItemGenerator.LIQUID);
        //聚甲基丙烯酸甲酯
        matList.PolymethylMethacrylate.registerC(++i,"PolymethylMethacrylate","PolymethylMethacrylate",150,234,255,255,255,130,"(C"+NUM_SUB[5]+"O"+NUM_SUB[2]+"H"+NUM_SUB[8]+")")
                .put(TD.ItemGenerator.DUSTS);
        //聚乙烯醇
        matList.PVA.registerC(++i,"PVA","PVA",132,133,255,255,255,130,"(CH"+NUM_SUB[2]+"CHOH)")
                .put(TD.ItemGenerator.DUSTS);
        //全氟磺酸单体前体
        matList.PrecursorPSVE.registerC(++i,"PrecursorPSVE","PrecursorPSVE",-39,137,255,255,255,130,"")
                .put();
        //六氟丙烯
        matList.HPF.registerC(++i,"HPF","HPF",-153,-28,255,255,255,130,"CF"+NUM_SUB[2]+"=CFCF"+NUM_SUB[3])
                .put();
        //四氟磺内酯
        matList.TFES.registerC(++i,"TFES","TFES",-35,41,255,255,255,130,"C"+NUM_SUB[2]+"F"+NUM_SUB[4]+"O"+NUM_SUB[3]+"S")
                .put();
        //全氟环氧丙烷
        matList.HFPO.registerC(++i,"HFPO","HFPO",-129,-42,255,255,255,130,"C"+NUM_SUB[3]+"F"+NUM_SUB[6]+"O")
                .put();
        //全氟磺酸单体
        matList.PSVE.registerC(++i,"PSVE","PSVE",-42,135,255,255,255,130,"C"+NUM_SUB[7]+"F"+NUM_SUB[14]+"O"+NUM_SUB[4]+"S")
                .put();
        //四氯化硅
        matList.Tetrachorosilane.registerC(++i,"Tetrachorosilane","Tetrachorosilane",-70,57,255,255,255,130,"SiCl"+NUM_SUB[4])
                .put(TD.ItemGenerator.LIQUID);
        //全氟树脂
        matList.PFSA.registerC(++i,"PFSA","PFSA",114,467,255,255,255,130,"")
                .put(TD.ItemGenerator.DUSTS);
        //八氟环丁烷
        matList.Perfluorocyclobutane.registerC(++i,"perfluorocyclobutane","perfluorocyclobutane",-41,-8,255,255,255,130,"C"+NUM_SUB[4]+"F"+NUM_SUB[8])
                .put();
        //氢溴酸
        matList.HydrobromicAcid.registerC(++i,"HydrobromicAcid","HydrobromicAcid",-97,-67,255,255,255,130,"HBr")
                .put();
    }
}
