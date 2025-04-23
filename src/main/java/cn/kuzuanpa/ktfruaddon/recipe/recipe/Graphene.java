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

package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import cn.kuzuanpa.ktfruaddon.api.fluid.flList;
import cn.kuzuanpa.ktfruaddon.api.material.matList;
import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.*;
import gregapi.oredict.OreDictManager;
import gregapi.data.FL;
import gregapi.data.OP;
import gregapi.util.ST;
import gregapi.item.ItemIntegratedCircuit;

import static gregapi.data.CS.*;

public class Graphene {
    public static void init() {
        const myCraftingLensRed = [
        MT.redstone,
                MT.ruby,
                MT.jasper,
                MT.bixbite,
                MT.onyxred,
                MT.zircon,
                MT.dragoneye,
                MT.redaventurine,
                MT.almandine,
                MT.realgar,
                MT.spessartine
                ];
        for (myCratingLensRed:material
             ) {
            RM.LaserEngraver.addrecipe2(F, 320, 1200, ST.array(OP.foil.mat(MT.nickel, 4), OP.lens(material,0)),ItemList.GrapheneCatalyst.get(1));
        }

        RM.Electrolyzer.addrecipeX(F, 320, 1200, ST.array(FL.Methane.make(5000), ItemList.GrapheneCatalyst.get(1), ItemList.GrapheneCatalystWithGraphene.get(1));
        RM.Shredder.addrecipe1(F,320,100,ItemList.GrapheneCatalystWithGraphene.get(1),ItemList.GrapheneMixture.get(1));
        RM.MagneticSeperator.addrecipe1(F,320,200,[80,80],ItemList.GrapheneMixture.get(1),ST,array(OP.dustSmall.mat(MT.graphene,2),OP.dustSmall.mat(MT.nickel,2)));

    }
}