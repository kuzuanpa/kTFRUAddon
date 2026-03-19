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

package cn.kuzuanpa.ktfruaddon.DreamPlanner.api.pool;

import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.AbstractTransmittable;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.ITransmittableType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class DreamItemPool {
    public UUID uuid ;
    protected ConcurrentHashMap<ITransmittableType, Long> items = new ConcurrentHashMap<>();
    public Predicate<ITransmittableType> AbstractOverallCondition = t -> false;
    public List<ITransmittableType> abstractTransmittableList = new ArrayList<>();

    public void updateAbstractCondition(){
        abstractTransmittableList.forEach(abs-> AbstractOverallCondition = AbstractOverallCondition.or(((AbstractTransmittable.AbstractTransmittableType) abs).condition));
    }
    public long requestAddItem(ITransmittableType item, long required){
        if (items.computeIfPresent(item, (k,v)-> v + required) == null)items.put(item,required);
        return required;
    }
    public long tryRemoveItem(ITransmittableType item, long required){
        long count = Math.min(required,items.getOrDefault(item, 0L));
        items.computeIfPresent(item, (k,v)-> v - count);
        return count;
    }

}
