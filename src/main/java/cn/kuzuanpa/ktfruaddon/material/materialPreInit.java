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
        int i=21999;
        matList.AmmoniumDichromate.registerWithDust(22000, "AmmoniumDichromate", "Ammonium Dichromate",456, 460, 255, 160, 51, 0,  "Cr"+NUM_SUB[2]+"O"+NUM_SUB[7]+"(NH3)"+NUM_SUB[2]);
        matList.AmmoniumChromicSulfate.registerWithDust(22001, "AmmoniumChromicSulfate", "Ammonium Chromic Sulfate",456, 460, 72, 0, 161, 0,  "NH"+NUM_SUB[4]+"Cr(SO"+NUM_SUB[4]+")"+NUM_SUB[2]);
        matList.AmmoniumIronIIISulfate.registerWithDust(22002, "AmmoniumIronIIISulfate", "Ammonium Iron(III) Sulfate", 368, 524, 230, 220, 242, 0, "NH"+NUM_SUB[4]+"Fe(SO"+NUM_SUB[4]+")"+NUM_SUB[2]);
        matList.AmmoniumSulfate.registerWithDust(22003, "AmmoniumSulfate", "Ammonium Sulfate",509, 510, 251, 251, 216, 2, "(NH"+NUM_SUB[4]+")"+NUM_SUB[2]+"SO"+NUM_SUB[4]);
        matList.CookedBauxide .registerWithDust(22004, "CookedBauxide", "Cooked Bauxide", 2370, 3234, 229, 141, 0, 4, null);
        matList.AcidPickledBauxide.registerWithDust(22005, "CookedBauxide", "Cooked Bauxide", 2370, 3234,200, 168, 0, 4, null);
        matList.BauxiteRedMud.registerWithDust(22006, "BauxiteRedMud", "Bauxite Red Mud", 1790, 3234, 148, 0, 12, 0,null);
        matList.LithiumCarbonate.registerWithDust(22007, "LithiumCarbonate", "Lithium Carbonate",943, 1582, 248, 244, 248, 0,  "Li"+NUM_SUB[2]+"CO"+NUM_SUB[3]);
        matList.MetatitanicAcid.registerWithDust(22008, "MetatitanicAcid", "Metatitanic Acid", 748,749, 248, 244, 248, 0, "TiO(OH)"+NUM_SUB[2]);
        //石油底
        matList.OilScarp.registerC(22009,"OilScarp","Oil Scarp",90,231,12,9,2,0,null)
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.DUSTS);
        //甲醇
        matList.Methanol.registerC(22010,"Methanol","Methanol",-98,64,90,80,210,140,"CH"+NUM_SUB[3]+"OH")
                .put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //乙醇
        //Already exists in GT
        //丙醇
        matList.Propanol.registerC(22011,"Propanol","Propanol",-127,97,60,60,150,140,"CH"+NUM_SUB[2]+"CH"+NUM_SUB[2]+"CH"+NUM_SUB[3]+"OH")
                .put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //丙二醇
        matList.Propanediol.registerC(22012,"Propanediol","Propanediol",-60,187,60,60,150,140,"CH"+NUM_SUB[2]+"CH"+NUM_SUB[2]+"CH"+NUM_SUB[3]+"OH")
                .put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //乙烷
        matList.Ethane.registerC(22013,"Ethane","Ethane",-172,-88,60,60,150,140,"CH"+NUM_SUB[3]+"CH"+NUM_SUB[3])
                .put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //丙酮
        matList.Acetone.register(22014,"Acetone","Acetone",143,293,210,210,210,140,"C"+NUM_SUB[3]+"H"+NUM_SUB[6]+"O")
                .put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //乙炔
        matList.Acetylene.register(22015,"Acetylene","Acetylene",147,217,210,210,210,180,"CH"+Symbols[0]+"CH")
                .put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.GASES);
        //苯
        matList.Benzene.registerC(22016,"Benzene","Benzene",5,80,255,255,130,140,"C"+NUM_SUB[6]+"H"+NUM_SUB[6])
                .put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.ItemGenerator.LIQUID);
        //甲苯
        matList.Toluene.registerC(22017,"Toluene","Toluene",-95,110,255,255,130,140,"CH"+NUM_SUB[3]+"C"+NUM_SUB[5]+"H"+NUM_SUB[6])
                .put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.ItemGenerator.LIQUID);
        //甲醛
        matList.Formaldehyde.registerC(22018,"Formaldehyde","Formaldehyde",-15,97,255,255,255,255,"CH"+NUM_SUB[2]+"O")
                .put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.ItemGenerator.GASES);
        //乙醛
        matList.Acetaldehyde.registerC(22019,"Acetaldehyde","Acetaldehyde",-125,21,255,255,255,255,"O=CHCH"+NUM_SUB[3])
                .put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.ItemGenerator.GASES);
        //丙醛
        matList.Propionaldehyde.registerC(22020,"Propionaldehyde","Propionaldehyde",-81,46,255,255,255,255,"O=CHCH"+NUM_SUB[2]+"CH"+NUM_SUB[3])
                .put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.ItemGenerator.GASES);
        //次氯酸
        matList.HypochlorousAcid.registerC(22021,"HypochlorousAcid","Hypochlorous Acid",0,100,180,255,180,150,"HClO")
                .put(TD.Properties.ACID,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //氯酸
        matList.ChloricAcid.registerC(22022,"ChloricAcid","Chloric Acid",0,41,235,255,210,210,"HClO"+NUM_SUB[3])
                .put(TD.Properties.ACID,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //高氯酸
        matList.PerchloricAcid.registerC(22023,"PerchloricAcid","Perchloric Acid",-112,19,255,255,255,255,"HClO"+NUM_SUB[4])
                .put(TD.Properties.ACID,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //光气
        matList.Phosgene.registerC(22024,"Phosgene","Phosgene",-128,8,240,245,255,240,"COCl"+NUM_SUB[2])
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.GASES);
        //氯丙烯
        matList.AllylChloride.registerC(22025,"AllylChloride","AllylChloride",-136,44,214,231,222,0,"CH"+NUM_SUB[2]+"=CHCH"+NUM_SUB[2]+"Cl")
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //二氯丙醇
        matList.DichloroPropanol.registerC(22026,"DichloroPropanol","DichloroPropanol",-4,173,245,245,245,170,"CHOH(CH"+NUM_SUB[2]+"Cl)"+NUM_SUB[2])
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID,TD.Properties.FLAMMABLE);
        //环氧氯丙烷
        matList.Epichlorohydrin.registerC(22027,"Epichlorohydrin","Epichlorohydrin",-57,115,245,245,245,170,"C"+NUM_SUB[3]+"H"+NUM_SUB[5]+"ClO")
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID,TD.Properties.FLAMMABLE);
        //三乙基铝
        matList.TriethylAluminium.registerC(22028,"TriethylAluminium","Triethyl Aluminium",-50,128,255,255,255,255,"Al(CH"+NUM_SUB[2]+"CH"+NUM_SUB[3]+")"+NUM_SUB[3])
                .put(TD.Compounds.DECOMPOSABLE,TD.Properties.FLAMMABLE,TD.ItemGenerator.DUSTS);
        //电石
        matList.CalciumCarbide.registerC(22029,"CalciumCarbide","CalciumCarbide",447,2300,255,255,255,130,"CaC"+NUM_SUB[2])
                .put(TD.ItemGenerator.DUSTS);
        //氢氧化钙
        matList.CalciumHydroxide.registerC(22030,"CalciumHydroxide","CalciumHydroxide",580,2850,255,255,255,130,"Ca(OH)"+NUM_SUB[2])
                .put(TD.ItemGenerator.DUSTS);
        //一氯甲烷
        matList.Chloromethane.registerC(22031,"Chloromethane","Chloromethane",-97,-24,255,255,255,255,"CH"+NUM_SUB[3])
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //二氯甲烷
        matList.Dichloromethane.registerC(22032,"Dichloromethane","Dichloromethane",-97,39,255,255,255,255,"CH"+NUM_SUB[2]+"Cl"+NUM_SUB[2])
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //三氯甲烷/氯仿
        matList.Chloroform.registerC(22033,"Chloroform","Chloroform",-63,61,213,212,212,148,"CHCl"+NUM_SUB[3])
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);

        //醋酸钙
        matList.CalciumAcetate.registerC(22034,"CalciumAcetate","Calcium Acetate",395,397,245,246,245,0,"CH"+NUM_SUB[3]+"COOCaOOCCH"+NUM_SUB[3])
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.DUSTS);
        //铬酸锌
        matList.ZincChromate.registerC(22035,"ZincChromate","Zinc Chromate",316,651,214,198,0,0,"ZnCrO"+NUM_SUB[4])
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.DUSTS);
        //氧化锌
        matList.Zincoxide.registerC(22036,"ZincOxide","ZincOxide",1975,2360,214,231,222,0,"ZnO")
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.DUSTS);
        //氯化锌
        matList.ZincChloride.registerC(22037,"ZincChloride","ZincChloride",293,732,214,231,222,0,"ZnCl"+NUM_SUB[2])
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.DUSTS);
        matList.BPA.registerC(22038,"BPA","BPA",158,226,255,255,255,10,"CH"+NUM_SUB[3]+"C"+"(PhOH)"+NUM_SUB[2]+"CH"+NUM_SUB[3])
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.DUSTS);
        //DPC,碳酸二苯酯
        matList.DiphenylCarbonate.registerC(22039,"DiphenylCarbonate","Diphenyl Carbonate",82,301,255,255,255,10,"PhOCOOPh")
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.DUSTS);
        //PC,聚碳酸酯
        matList.Polycarbonate.registerC(22040,"Polycarbonate","Polycarbonate",220,310,255,255,255,10,"C"+NUM_SUB[16]+"H"+NUM_SUB[18]+"O"+NUM_SUB[5])
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.DUSTS);
        //四氟乙烯
        matList.Tetrafluoroethylene.registerC(22041,"Tetrafluoroethylene","Tetrafluoroethylene",-142,-76,255,255,255,130,"CF"+NUM_SUB[2]+"CF"+NUM_SUB[2])
                .put(TD.Compounds.DECOMPOSABLE);
        //环氧树脂
        matList.EpoxyResin.registerC(22042,"EpoxyResin","Epoxy Resin",115,252,255,255,255,10,"(C"+NUM_SUB[3]+"H"+NUM_SUB[5]+"ClO)")
                .put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.DUSTS,TD.ItemGenerator.PLATES,TD.ItemGenerator.FOILS,TD.Prefix.INGOT_BASED);
        //乙酸乙烯酯
        matList.VinylAcetate.registerC(22043,"VinylAcetate","VinylAcetate",-93,72,255,255,255,130,"CH"+NUM_SUB[3]+"CHOOCH=CH"+NUM_SUB[2])
                .put();
        //聚乙酸乙烯
        matList.PolyVinylAcetate.registerC(22044,"PolyVinylAcetate","PolyVinylAcetate",60,143,255,255,255,130,"(C"+NUM_SUB[4]+"H"+NUM_SUB[6]+"O"+NUM_SUB[2]+")")
                .put();
        //丁二烯
        matList.Butadiene.registerC(22045,"Butadiene","Butadiene",-109,-4,255,255,255,130,"CH"+NUM_SUB[2]+"=CHCH=CH2"+NUM_SUB[2])
                .put();
        //甲基氯化镁
        matList.MethylmagnesiumChloride.registerC(22046,"MethylmagnesiumChloride","MethylmagnesiumChloride",-30,66,255,255,255,130,"CH"+NUM_SUB[3]+"ClMg")
                .put(TD.ItemGenerator.LIQUID);
        //一甲三氯硅烷
        matList.Methyltrichlorosilane.registerC(22047,"Methyltrichlorosilane","Methyltrichlorosilane",-77,66,255,255,255,130,"CH"+NUM_SUB[3]+"Cl"+NUM_SUB[3]+"Si")
                .put(TD.ItemGenerator.LIQUID);
        //二甲二氯硅烷
        matList.Dichlorodimethylsilane.registerC(22048,"Dichlorodimethylsilane","Dichlorodimethylsilane",-76,60,255,255,255,130,"(CH"+NUM_SUB[3]+")"+NUM_SUB[2]+"Cl"+NUM_SUB[2]+"Si")
                .put(TD.ItemGenerator.LIQUID);
        //硅橡胶液
        matList.SiliconeRubber.registerC(22049,"SiliconeRubber","SiliconeRubber",-98,237,255,255,255,130,"")
	            .put(TD.ItemGenerator.LIQUID);
        //氯化汞
        matList.MercuryIIChloride.registerC(22050,"MercuryIIChloride","MercuryIIChloride",277,302,255,255,255,130,"Cl"+NUM_SUB[2]+"Hg"+NUM_SUB[2])
                .put(TD.ItemGenerator.DUSTS);
        //二氯乙烷
        matList.DichloroEthane.registerC(22051,"DichloroEthane","DichloroEthane",-35,82,255,255,255,130,"CH"+NUM_SUB[2]+"ClCH"+NUM_SUB[2]+"Cl")
                .put(TD.ItemGenerator.LIQUID);
        //二氯丙烷
        matList.DichloroPropane.registerC(22052,"DichloroPropane","DichloroPropane",-100,95,255,255,255,130,"CH"+NUM_SUB[3]+"CH"+NUM_SUB[2]+"Cl=CH"+NUM_SUB[2]+"Cl")
                .put(TD.ItemGenerator.LIQUID);
        //氯乙烯
        matList.VinylChloride.registerC(22053,"VinylChloride","VinylChloride",-153,-13,255,255,255,130,"CH"+NUM_SUB[2]+"=CHCl")
                .put(TD.ItemGenerator.LIQUID);
        //乙苯
        matList.Ethylbenzene.registerC(22054,"Ethylbenzene","Ethylbenzene",-95,136,255,255,255,130,"PhCH"+NUM_SUB[2]+"CH"+NUM_SUB[3])
                .put(TD.ItemGenerator.LIQUID);
        //一氯乙苯
        matList.ChloroPhenylethane.registerC(22055,"ChloroPhenylethane","ChloroPhenylethane",-42,90,255,255,255,130,"PhCHClCH"+NUM_SUB[3])
                .put(TD.ItemGenerator.LIQUID);
        //乙烯苯
        matList.Styrene.registerC(22056,"Styrene","Styrene",-31,145,255,255,255,130,"PhCH=CH"+NUM_SUB[2])
                .put(TD.ItemGenerator.LIQUID);
        //丁苯橡胶液
        matList.SBR.registerC(22057,"SBR","SBR",-59,390,255,255,255,130,"")
                .put(TD.ItemGenerator.LIQUID);
        //异戊二烯
        matList.Isoprene.registerC(22058,"Isoprene","Isoprene",-145,32,255,255,255,130,"CH"+NUM_SUB[2]+"=CCH"+NUM_SUB[3]+"CHCH"+NUM_SUB[2])
                .put(TD.ItemGenerator.LIQUID);
        //氯乙醇
        matList.Chloroethanol.registerC(22059,"Chloroethanol","Chloroethanol",-67,129,255,255,255,130,"C"+NUM_SUB[2]+"H"+NUM_SUB[5]+"ClO")
                .put(TD.ItemGenerator.LIQUID);
        //乙二醇
        matList.EthyleneGlycol.registerC(22060,"EthyleneGlycol","EthyleneGlycol",-13,197,255,255,255,130,"(CH"+NUM_SUB[2]+"OH)"+NUM_SUB[2])
                .put(TD.ItemGenerator.LIQUID);
        //聚甲基丙烯酸甲酯
        matList.PolymethylMethacrylate.registerC(22061,"PolymethylMethacrylate","PolymethylMethacrylate",150,234,255,255,255,130,"(C"+NUM_SUB[5]+"O"+NUM_SUB[2]+"H"+NUM_SUB[8]+")")
                .put(TD.ItemGenerator.DUSTS);
        //聚乙烯醇
        matList.PVA.registerC(22062,"PVA","PVA",132,133,255,255,255,130,"(CH"+NUM_SUB[2]+"CHOH)")
                .put(TD.ItemGenerator.DUSTS);
        //全氟磺酸单体前体
        matList.PrecursorPSVE.registerC(22063,"PrecursorPSVE","PrecursorPSVE",-39,137,255,255,255,130,"")
                .put();
        //六氟丙烯
        matList.HPF.registerC(22064,"HPF","HPF",-153,-28,255,255,255,130,"CF"+NUM_SUB[2]+"=CFCF"+NUM_SUB[3])
                .put();
        //四氟磺内酯
        matList.TFES.registerC(22065,"TFES","TFES",-35,41,255,255,255,130,"C"+NUM_SUB[2]+"F"+NUM_SUB[4]+"O"+NUM_SUB[3]+"S")
                .put();
        //全氟环氧丙烷
        matList.HFPO.registerC(22066,"HFPO","HFPO",-129,-42,255,255,255,130,"C"+NUM_SUB[3]+"F"+NUM_SUB[6]+"O")
                .put();
        //全氟磺酸单体
        matList.PSVE.registerC(22067,"PSVE","PSVE",-42,135,255,255,255,130,"C"+NUM_SUB[7]+"F"+NUM_SUB[14]+"O"+NUM_SUB[4]+"S")
                .put();
        //四氯化硅
        matList.Tetrachorosilane.registerC(22068,"Tetrachorosilane","Tetrachorosilane",-70,57,255,255,255,130,"SiCl"+NUM_SUB[4])
                .put(TD.ItemGenerator.LIQUID);
        //全氟树脂
        matList.PFSA.registerC(22069,"PFSA","PFSA",114,467,255,255,255,130,"")
                .put(TD.ItemGenerator.DUSTS);
        //八氟环丁烷
        matList.Perfluorocyclobutane.registerC(22070,"perfluorocyclobutane","perfluorocyclobutane",-41,-8,255,255,255,130,"C"+NUM_SUB[4]+"F"+NUM_SUB[8])
                .put();
        //氢溴酸
        matList.HydrobromicAcid.registerC(22071,"HydrobromicAcid","HydrobromicAcid",-97,-67,255,255,255,130,"HBr")
                .put();
        //萘
        matList.Naphthalene.registerC(22072,"Naphthalene","Naphthalene",81,218,255,255,255,130,"C10H8")
                .put(TD.ItemGenerator.DUSTS);
        //1-萘酚
        matList.Naphthalenol.registerC(22073,"Naphthalenol","1-naphthalenol",96,278,255,255,255,130,"C10H8O")
                .put(TD.ItemGenerator.DUSTS);
        //2-氨基,1-萘酚盐酸盐
        matList.NaphthalenolAminoHydrochloride.registerC(22074,"NaphthalenolAminoHydrochloride","1-Naphthalenol-2-amino-hydrochloride",143,343,255,255,255,130,"C10H10ClNO")
                .put(TD.ItemGenerator.DUSTS);
        //2-重氮,1-萘酚
        matList.DiazoNaphthol.registerC(22075,"DiazoNaphthol","DiazoNaphthol",143,343,255,255,255,130,"C10H5N2O")
                .put(TD.ItemGenerator.DUSTS);
        //丙二醇甲醚
        matList.MethoxyPropanol.registerC(22076,"MethoxyPropanol","MethoxyPropanol",-97,120,255,255,255,130,"C4H10O2")
                .put();
        //丙二醇甲醚醋酸酯
        matList.PGMEA.registerC(22077,"PGMEA","PGMEA",-87,145,255,255,255,130,"C6H12O3")
                .put();
        //重氮系光刻胶
        matList.DNQPhotoresist.registerC(22078,"DNQPhotoresist","DNQPhotoresist",213,341,255,255,255,130,"")
                .put(TD.ItemGenerator.DUSTS);
        //环己醇
        matList.Cyclohexanol.registerC(22079,"Cyclohexanol","Cyclohexanol",20,160,255,255,255,130,"C6H12O")
                .put(TD.ItemGenerator.DUSTS);
        //环己酮
        matList.Cyclohexanone.registerC(22080,"Cyclohexanone","Cyclohexanone",-47,115,255,255,255,130,"C6H10O")
                .put();
        //苊
        matList.Acenaphthylene.registerC(22081,"Acenaphthylene","Acenaphthylene",78,280,255,255,255,130,"C12H8")
                .put(TD.ItemGenerator.DUSTS);
        //5-硝基苊
        matList.Nitroacenaphthene.registerC(22082,"Nitroacenaphthene","5-Nitroacenaphthene",100,279,255,255,255,130,"C12H9NO2")
                .put(TD.ItemGenerator.DUSTS);
        //PMMA系光刻胶
        matList.PMMAPhotoresist.registerC(22083,"PMMAPhotoresist","PMMAPhotoresist",228,321,255,255,255,130,"")
                .put(TD.ItemGenerator.DUSTS);
        //硝酸铵
        matList.AmmoniumNitrate.registerC(22084,"AmmoniumNitrate","AmmoniumNitrate",169,210,255,255,255,130,"NH4NO3")
                .put(TD.ItemGenerator.DUSTS);
        //?
        matList.JustForHensSoPretty.registerC(22085,"JustForHenSoPretty","Just For Hen So Pretty",2333,23333,0,0,0,0,"GeInNbTiMg")
                .put(TD.ItemGenerator.DUSTS);
    }
}
