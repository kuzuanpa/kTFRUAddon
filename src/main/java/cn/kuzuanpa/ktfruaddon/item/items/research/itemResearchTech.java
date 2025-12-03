/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */

package cn.kuzuanpa.ktfruaddon.item.items.research;

import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class itemResearchTech extends itemResearch {
    public itemResearchTech() {
        super(MOD_ID, "ktfru.item.research.0");
        setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: Tech Research", this, (short) 0));
    }

    @Override
    public void addItems() {
        ItemList.FusionTokamakData0.set(addItem(0, "Tokamak Experiment Data (Electric)",  "A common data, do some help in improving tokamak reactor."));
        ItemList.FusionTokamakData1.set(addItem(1, "Tokamak Experiment Data (Heat)",      "A common data, do some help in improving tokamak reactor."));
        ItemList.FusionTokamakData2.set(addItem(2, "Tokamak Experiment Data (Neutron)",   "A rare data, do massive help in improving tokamak reactor."));
        ItemList.FusionTokamakData3.set(addItem(3, "Tokamak Experiment Data (Proton)",    "A rare data, do massive help in improving tokamak reactor."));
        ItemList.FusionTokamakData4.set(addItem(4, "Tokamak Experiment Data (Structure)", "A legendary data, can be used to create a fusion reactor."));
        ItemList.ResearchItem      .set(addItem(32766, "Common Research Data",  "A common data, You shouldn't get this."));


    }

    @Override
    public byte getTreeId() {
        return 0;
    }
}
