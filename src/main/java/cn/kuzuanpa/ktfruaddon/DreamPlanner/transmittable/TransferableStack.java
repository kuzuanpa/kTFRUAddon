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

public final class TransferableStack {
    public long amount;
    public ITransferable type;
    public TransferableStack(ITransferable type, long amount){
        this.type=type;
        this.amount=amount;
    }
    public boolean isEqual(TransferableStack t){
        return t != null && Objects.equals(t.type, this.type) && t.amount == this.amount;
    }
    public boolean isTypeEqual(TransferableStack t){
        return t != null && Objects.equals(t.type, this.type);
    }

    public TransferableStack clone(long amount){
        return new TransferableStack(this.type, amount);
    }
    @Override
    public String toString() {
        return type.toString()+":"+amount;
    }
}
