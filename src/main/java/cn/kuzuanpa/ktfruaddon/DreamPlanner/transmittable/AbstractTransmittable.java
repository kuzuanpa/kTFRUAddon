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

import java.util.Objects;
import java.util.function.Predicate;

public class AbstractTransmittable implements ITransmittable{
    AbstractTransmittableType condition;
    long amount;
    public AbstractTransmittable(Predicate<ITransmittableType> condition, String identifier, long amount){
        this.condition = new AbstractTransmittableType(condition, identifier);
        this.amount = amount;
    }

    public Predicate<ITransmittableType> getCondition() {
        return condition.condition;
    }

    @Override
    public ITransmittable initFrom(ITransmittableType type, long amount) {
        return new AbstractTransmittable(((AbstractTransmittableType) type).condition, ((AbstractTransmittableType) type).identifier, amount);
    }

    @Override
    public ITransmittableType getType() {
        return condition;
    }

    @Override
    public long getAmount() {
        return amount;
    }
    public ITransmittable setAmount(long amount) {
        this.amount = amount;
        return this;
    }
    public static class AbstractTransmittableType implements ITransmittableType{
        public Predicate<ITransmittableType> condition;
        public String identifier;
        public AbstractTransmittableType(Predicate<ITransmittableType> condition, String identifier){
            this.condition=condition;
            this.identifier = identifier;
        }

        @Override
        public ITransmittable make(long amount) {
            return new AbstractTransmittable(this.condition, this.identifier, amount);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AbstractTransmittableType that = (AbstractTransmittableType) o;
            return Objects.equals(identifier, that.identifier);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(identifier);
        }

        @Override
        public String toString() {
            return identifier;
        }
    }
}
