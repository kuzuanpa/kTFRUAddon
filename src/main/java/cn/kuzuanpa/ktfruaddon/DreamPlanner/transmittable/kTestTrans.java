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

public class kTestTrans implements ITransmittable{

    long amount =0;
    kTestTransType stack;
    public kTestTrans(String stack){
        this.stack = new kTestTransType(stack);
        this.amount = 0;
    }
    public kTestTrans(String stack, long amount){
        this.stack = new kTestTransType(stack);
        this.amount = amount;
    }

    public String get(){
        return stack.string;
    }
    @Override
    public ITransmittable initFrom(ITransmittableType type, long amount) {
        return new kTestTrans(((kTestTransType) type).string, amount);
    }

    @Override
    public ITransmittableType getType() {
        return  stack;
    }

    @Override
    public long getAmount() {
        return amount;
    }
    @Override
    public String toString() {
        return stack.string + ":" + amount;
    }
    public static class kTestTransType implements ITransmittableType{
        public String string;
        public kTestTransType(String string){
            this.string=string;
        }

        @Override
        public ITransmittable make(long amount) {
            return new kTestTrans(this.string,amount);
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            kTestTransType that = (kTestTransType) o;
            return Objects.equals(string, that.string);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(string);
        }
    }
}
