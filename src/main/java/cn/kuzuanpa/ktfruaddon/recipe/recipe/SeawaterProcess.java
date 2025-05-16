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
package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.api.fluid.flList;
import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import cn.kuzuanpa.ktfruaddon.api.material.matList;
import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import gregapi.data.*;
import gregapi.util.ST;

import static gregapi.data.CS.*;

public class SeawaterProcess {
    public static void init() {

        //seawater main process
        recipeMaps. DistillTower.       addrecipe0(F, 64, 20, FL.array(FL.Ocean.make(1000)), OP.dustSmall.mat(MT.NaCl, 1), FL.array(flList.Bittern.make(100), FL.DistW.make(500)));
        recipeMaps. SmallDistillTower.  addrecipe0(F, 64, 30, FL.array(FL.Ocean.make(1000)), OP.dustSmall.mat(MT.NaCl, 1), FL.array(flList.Bittern.make(100), FL.DistW.make(500)));
        recipeMaps. TinyDistillTower.   addrecipe0(F, 64, 40, FL.array(FL.Ocean.make(1000)), OP.dustSmall.mat(MT.NaCl, 1), FL.array(flList.Bittern.make(100), FL.DistW.make(500)));
        RM.         Distillery.         addrecipe0(F, 64, 100, FL.array(FL.Ocean.make(1000)), OP.dustSmall.mat(MT.NaCl, 1), FL.array(flList.Bittern.make(50), FL.DistW.make(200)));

        RM.         Bath.               addrecipe1(T, 0, 20, OP.dust.mat(MT.Zeolite, 0), FL.array(flList.Bittern.make(1000)), FL.array(flList.Brine.make(800), MT.SaltedWater.liquid(U5, T)));
        RM.         Bath.               addrecipe1(T, 0, 40, OP.dust.mat(MT.QuickLime, 1), FL.array(flList.Brine.make(1000)), FL.array(flList.BrineMgFree.make(1000)), ItemList.MagnesiumDihydroxide.get(2));
        RM.         Bath.               addrecipe0(T, 0, 40, FL.array(FL.CarbonDioxide.make(1000), flList.BrineMgFree.make(1000)), FL.array(flList.BrineBrRich.make(1000), OP.dust.mat(MT.CaCO3, 1)));
        RM.         Bath.               addrecipe0(T, 0, 100, FL.array(MT.Cl.gas(U5, F), flList.BrineBrRich.make(1000)), FL.array(MT.Br.liquid(U5, F), flList.BrineWaste.make(1000)));

        // chemical recycling
        RM.         Bath.               addrecipe1(T, 16, 80, ItemList.MagnesiumDihydroxide.get(5), FL.array(MT.HCl.gas(U * 2, false)), FL.array(MT.H2O.liquid(U * 6)), OP.dust.mat(MT.MgCl2, 1));
        RM.         Roasting.           addrecipe1(F, 256, 100, OP.dust.mat(MT.CaCO3, 5), OP.dust.mat(MT.QuickLime, 2));
    }
}
