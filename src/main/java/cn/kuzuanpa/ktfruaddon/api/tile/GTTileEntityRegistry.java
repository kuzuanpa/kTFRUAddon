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

package cn.kuzuanpa.ktfruaddon.api.tile;

import cpw.mods.fml.common.FMLLog;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.CS;
import gregapi.util.ST;
import org.apache.logging.log4j.Level;

public class GTTileEntityRegistry {
    public static short gregtech = CS.W;
    public static short ktfruaddon = CS.W;
    public static void update(){
        gregtech = ST.id(MultiTileEntityRegistry.getRegistry("gt.multitileentity").mBlock);
        ktfruaddon = ST.id(MultiTileEntityRegistry.getRegistry("ktfru.multitileentity").mBlock);
        FMLLog.log(Level.INFO,"Updated Registry ID, ktfruaddon:"+ktfruaddon+", gregtech:"+gregtech);
    }
}
