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


package cn.kuzuanpa.ktfruaddon.item.items.random;

import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import cn.kuzuanpa.ktfruaddon.item.behavior.Behavior_ResearchViewer;
import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;
import net.minecraft.item.ItemStack;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class ItemDevice extends MultiItemRandom {
    public ItemDevice() {
        super(MOD_ID, "ktfru.item.device");
        setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: Devices", this,  (short) 0));
    }


    @Override
    public void addItems() {
        ItemList.AsteroidMinerRocketT1    .set(addItem(0, "Asteroid Miner Rocket (T1)",  "Travel Speed: 1"));
        ItemList.AsteroidMinerRocketT1Fast.set(addItem(1, "Fast Asteroid Miner Rocket (T1)",  "Travel Speed: 4"));
        ItemList.AsteroidMinerRocketT2    .set(addItem(2, "Asteroid Miner Rocket (T2)",  "Travel Speed: 2"));
        ItemList.AsteroidMinerRocketT2Fast.set(addItem(3, "Fast Asteroid Miner Rocket (T2)",  "Travel Speed: 7"));
        ItemList.AsteroidMinerRocketT3    .set(addItem(4, "Asteroid Miner Rocket (T3)",  "Travel Speed: 3"));
        ItemList.AsteroidMinerRocketT3Fast.set(addItem(5, "Fast Asteroid Miner Rocket (T3)",  "Travel Speed: 9"));
        ItemList.AsteroidMinerRocketT4    .set(addItem(6, "Asteroid Miner Rocket (T4)",  "Travel Speed: 3"));
        ItemList.AsteroidMinerRocketT4Fast.set(addItem(7, "Fast Asteroid Miner Rocket (T4)",  "Travel Speed: 14"));
        ItemList.AsteroidMinerRocketT5    .set(addItem(8, "Asteroid Miner Rocket (T5)",  "Travel Speed: 4"));
        ItemList.AsteroidMinerRocketT5Fast.set(addItem(9, "Fast Asteroid Miner Rocket (T5)",  "Travel Speed: 20"));

        ItemList.DeprecatedAsteroidMinerRocketT1    .set(addItem(10, "Deprecated Asteroid Miner Rocket (T1)",  ""));
        ItemList.DeprecatedAsteroidMinerRocketT1Fast.set(addItem(11, "Deprecated Fast Asteroid Miner Rocket (T1)",  ""));
        ItemList.DeprecatedAsteroidMinerRocketT2    .set(addItem(12, "Deprecated Asteroid Miner Rocket (T2)",  ""));
        ItemList.DeprecatedAsteroidMinerRocketT2Fast.set(addItem(13, "Deprecated Fast Asteroid Miner Rocket (T2)",  ""));
        ItemList.DeprecatedAsteroidMinerRocketT3    .set(addItem(14, "Deprecated Asteroid Miner Rocket (T3)",  ""));
        ItemList.DeprecatedAsteroidMinerRocketT3Fast.set(addItem(15, "Deprecated Fast Asteroid Miner Rocket (T3)",  ""));
        ItemList.DeprecatedAsteroidMinerRocketT4    .set(addItem(16, "Deprecated Asteroid Miner Rocket (T4)",  ""));
        ItemList.DeprecatedAsteroidMinerRocketT4Fast.set(addItem(17, "Deprecated Fast Asteroid Miner Rocket (T4)",  ""));
        ItemList.DeprecatedAsteroidMinerRocketT5    .set(addItem(18, "Deprecated Asteroid Miner Rocket (T5)",  ""));
        ItemList.DeprecatedAsteroidMinerRocketT5Fast.set(addItem(19, "Deprecated Fast Asteroid Miner Rocket (T5)",  ""));

        ItemList.ResearchViewer                      .set(addItem(20, "Research Viewer", "", Behavior_ResearchViewer.INSTANCE));

    }

    public static ItemStack getDeprecatedAsteroidMinerRocket(ItemStack stack){
        if(isDeviceAsteroidMinerRocket(stack)){
            stack.setItemDamage(stack.getItemDamage()+10);
            return stack;
        }
        else return null;
    }
    public static boolean isDeviceAsteroidMinerRocket(ItemStack stack){
        return stack != null && stack.getItem() instanceof ItemDevice && stack.getItemDamage() <= 9;
    }
    public static int[] asteroidMinerRocketLevel = new int[] {1,1,2,2,3,3,4,4,5,5};

    public static int getAsteroidMinerRocketLevel(ItemStack stack){
        return isDeviceAsteroidMinerRocket(stack)?asteroidMinerRocketLevel[stack.getItemDamage()]:-1;
    }
    public static int[] asteroidMinerRocketSpeed = new int[] {1,4,2,7,3,9,3,14,4,20};

    public static int getAsteroidMinerRocketSpeed(ItemStack stack){
        return isDeviceAsteroidMinerRocket(stack)?asteroidMinerRocketSpeed[stack.getItemDamage()]:-1;

    }

    @Override
    public int getItemStackLimit(ItemStack aStack) {
        return isDeviceAsteroidMinerRocket(aStack)?1:super.getItemStackLimit(aStack);
    }
}
