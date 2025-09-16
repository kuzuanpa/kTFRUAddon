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

import java.util.function.Predicate;

public class AbstractTransmittable implements ITransmittable{
    Predicate<ITransmittable> condition;
    long amount;
    public AbstractTransmittable(Predicate<ITransmittable> condition, long amount){
        this.condition = condition;
        this.amount = amount;
    }

    public Predicate<ITransmittable> getCondition() {
        return condition;
    }

    @Override
    public ITransmittable getSingle() {
        return new AbstractTransmittable(condition,1);
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public ITransmittable setAmount(long amount) {
        this.amount = amount;
        return this;
    }
}
