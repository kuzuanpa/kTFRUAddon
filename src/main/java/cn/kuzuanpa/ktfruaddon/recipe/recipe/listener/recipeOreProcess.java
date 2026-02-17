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
                ItemStack[] largeOreOutputList = new ItemStack[9];
                largeOreOutputList[0] = OP.dust.mat(aEvent.mMaterial, 3);
                for (int i = 1; i < 9; i++) {
                    if(i>aEvent.mMaterial.mByProducts.size())largeOreOutputList[i] = OP.dustSmall.mat(aEvent.mMaterial, 1);
                    else largeOreOutputList[i] = OP.dustSmall.mat(aEvent.mMaterial.mByProducts.get(i-1), 1);
                }
                OreProcessSystem.addRecipe1(true, 128,100, OP.crushed.mat(aEvent.mMaterial, 2), FL.Water.make(3600), FL.Sluice.make(3600), largeOreOutputList);
                ItemStack[] smallOreOutputList = new ItemStack[9];
                smallOreOutputList[0] = OP.dustDiv72.mat(aEvent.mMaterial, 12);
                for (int i = 1; i < 9; i++) {
                    if(i>aEvent.mMaterial.mByProducts.size())smallOreOutputList[i] = OP.dustDiv72.mat(aEvent.mMaterial, 1);
                    else smallOreOutputList[i] = OP.dustDiv72.mat(aEvent.mMaterial.mByProducts.get(i-1), 1);
                }
                OreProcessSystem.addRecipe1(true, 128,100, OP.crushedTiny.mat(aEvent.mMaterial, 1), FL.Water.make(200), FL.Sluice.make(200), smallOreOutputList);
            }
        }
    }

}
