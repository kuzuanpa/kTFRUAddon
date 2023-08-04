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
                .put(TD.Compounds.DECOMPOSABLE,TD.Properties.FLAMMABLE,TD.Properties.BURNING,TD.ItemGenerator.DUSTS);
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

    }
}
