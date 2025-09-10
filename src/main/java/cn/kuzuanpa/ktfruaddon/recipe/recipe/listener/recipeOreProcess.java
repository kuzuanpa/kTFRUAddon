/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.

 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */



package cn.kuzuanpa.ktfruaddon.recipe.recipe.listener;

import gregapi.data.FL;
import gregapi.data.OP;
import gregapi.data.TD;
import gregapi.oredict.event.IOreDictListenerEvent;
import net.minecraft.item.ItemStack;

import static cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps.OreProcessSystem;

public class recipeOreProcess {

    public static class OneStepOreProcess implements IOreDictListenerEvent {
        @Override
        public void onOreRegistration(OreDictRegistrationContainer aEvent) {
            if (TD.ItemGenerator.ORES.isTrue(aEvent.mMaterial)) {
                ItemStack[] outputList = new ItemStack[9];
                outputList[0] = OP.dust.mat(aEvent.mMaterial, 6);
                for (int i = 1; i < 9; i++) {
                    if(i>aEvent.mMaterial.mByProducts.size())outputList[i] = OP.dustSmall.mat(aEvent.mMaterial, 2);
                    else outputList[i] = OP.dustSmall.mat(aEvent.mMaterial.mByProducts.get(i-1), 2);
                }
                OreProcessSystem.addRecipe1(true, 128,100, OP.crushed.mat(aEvent.mMaterial, 4), FL.Water.make(4000), FL.Sluice.make(4000), outputList);
            }
        }
    }

}
