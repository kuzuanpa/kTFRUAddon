/*
 * This class was created by <equ>. It is distributed as
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

import cn.kuzuanpa.ktfruaddon.cover.CoverFilterProperties;
import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import gregapi.data.TC;
import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class itemGraphene extends MultiItemRandom {
    public itemGraphene() {
        super(MOD_ID, "ktfru.item.graphene");
        setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: Graphene", this, (short) 0));
    }


    @Override
    public void addItems() {
        ItemList.GrapheneCatalyst.set(addItem(0, "Graphite Catalyst", "A Nickel-based catalyst"));
        ItemList.GrapheneCatalystWithGraphene.set(addItem(1,"Graphite Catalyst(used)","A Nickel-based catalyst plate, attached with a small amount of graphene"));
        ItemList.GrapheneMixture.set(addItem(2,"Graphite Mixture","A mixture of nickel and a small amount of graphene"));
    }
}
