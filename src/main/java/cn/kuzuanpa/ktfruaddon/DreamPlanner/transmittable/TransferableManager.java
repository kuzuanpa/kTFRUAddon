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

package cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable;

import cpw.mods.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

public class TransferableManager {
    public static Map<Integer, Class<ITransferable>> registry = new HashMap<>();
    public void register(Class<ITransferable> clazz){
        try {
            ITransferable t = clazz.newInstance();
            registry.put(t.typeID(), clazz);
        }catch (Throwable throwable){
            FMLLog.log(Level.FATAL, "Error occoured when register ITransferable.",throwable);
            throw new IllegalArgumentException(throwable);
        }
    }
    public static ITransferable getDefault(int typeID){
        try {
            return registry.get(typeID).newInstance();
        }catch (Throwable throwable){
            FMLLog.log(Level.FATAL, "Error occoured when creating a default ITransferable type:"+typeID,throwable);
            throw new IllegalArgumentException(throwable);
        }
    }
}
