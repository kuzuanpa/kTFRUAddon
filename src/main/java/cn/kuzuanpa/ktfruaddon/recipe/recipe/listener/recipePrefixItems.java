/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.recipe.recipe.listener;

import cn.kuzuanpa.ktfruaddon.material.prefix.prefixList;
import cn.kuzuanpa.ktfruaddon.recipe.recipeMaps;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.generator.MultiTileEntityLargeTurbine;
import gregapi.code.ICondition;
import gregapi.data.FL;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.data.RM;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.event.IOreDictListenerEvent;
import gregapi.util.ST;

import java.util.HashMap;

import static gregapi.data.CS.U;
import static gregapi.data.CS.ZL_FS;

public class recipePrefixItems {

    public static class Parts_Turbine implements IOreDictListenerEvent {
        static HashMap<OreDictMaterial,Double> durMap=new HashMap<>();
        static HashMap<OreDictMaterial,Double> wattMap=new HashMap<>();

        private final ICondition<OreDictMaterial> mCondition;
            public Parts_Turbine(ICondition<OreDictMaterial> aCondition) {mCondition = aCondition;}

            @Override
            public void onOreRegistration(OreDictRegistrationContainer aEvent) {
                if (mCondition.isTrue(aEvent.mMaterial)) {
                    wattMap.put(aEvent.mMaterial, aEvent.mMaterial.mToolQuality /4D + Math.pow(aEvent.mMaterial.mToolSpeed, 2)/64D - aEvent.mMaterial.mMass/800);
                    durMap.put(aEvent.mMaterial, MultiTileEntityLargeTurbine.getTurbineDurability(aEvent.mMaterial)/(3600*20D*24));

                    recipeMaps.CNCMachine.addRecipe2(true,32,aEvent.mMaterial.mToolQuality*aEvent.mMaterial.mToolDurability, ST.tag(1), OP.bouleGt.mat(aEvent.mMaterial,1), FL.array(FL.DistW.make(2000)),ZL_FS, prefixList.largeTurbineBlade.mat(aEvent.mMaterial,1),OP.dust.mat(aEvent.mMaterial, 2));
                    RM.        Welder    .addRecipe2(true,aEvent.mMaterial.mMeltingPoint/2, aEvent.mMaterial.mToolQuality*800, prefixList.largeTurbineBlade.mat(aEvent.mMaterial,24), ST.tag(0), FL.array(MT.SolderingAlloy.liquid(12*U,true)),ZL_FS, prefixList.steamLargeTurbine.mat(aEvent.mMaterial,1));
                    RM.        Welder    .addRecipe2(true,aEvent.mMaterial.mMeltingPoint/2, aEvent.mMaterial.mToolQuality*600, prefixList.largeTurbineBlade.mat(aEvent.mMaterial,12), prefixList.steamLargeTurbine.mat(aEvent.mMaterial,1), FL.array(MT.SolderingAlloy.liquid(6*U,true)),ZL_FS, prefixList.gasLargeTurbine.mat(aEvent.mMaterial,1));
                    RM.        Welder    .addRecipe2(true,aEvent.mMaterial.mMeltingPoint/2, aEvent.mMaterial.mToolQuality*600, prefixList.largeTurbineBlade.mat(aEvent.mMaterial,12), prefixList.steamLargeTurbineChecked.mat(aEvent.mMaterial,1), FL.array(MT.SolderingAlloy.liquid(6*U,true)),ZL_FS, prefixList.gasLargeTurbine.mat(aEvent.mMaterial,1));

                    recipeMaps.FlawDetector.addRecipe1(true,16,12000, prefixList.steamLargeTurbine.mat(aEvent.mMaterial,1), prefixList.steamLargeTurbineChecked.mat(aEvent.mMaterial,1));
                    recipeMaps.FlawDetector.addRecipe1(true,16,18000, new long[]{6000,4000}, prefixList.gasLargeTurbine.mat(aEvent.mMaterial,1), prefixList.gasLargeTurbineChecked.mat(aEvent.mMaterial,1), prefixList.gasLargeTurbineDamaged.mat(aEvent.mMaterial,1));
                }
            }
        }

}
