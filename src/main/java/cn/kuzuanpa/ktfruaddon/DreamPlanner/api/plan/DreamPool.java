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

package cn.kuzuanpa.ktfruaddon.DreamPlanner.api.plan;

import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.AbstractTransmittable;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.ITransmittableType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class DreamPool {
    public Predicate<ITransmittableType> AbstractOverallCondition = t -> false;
    public List<ITransmittableType> abstractTransmittableList = new ArrayList<>();

    public void updateAbstractCondition(){
        abstractTransmittableList.forEach(abs-> AbstractOverallCondition = AbstractOverallCondition.or(((AbstractTransmittable.AbstractTransmittableType) abs).condition));
    }
    public long requestConsumeItem(ITransmittableType item, long required){
        return 3;
    }
}
