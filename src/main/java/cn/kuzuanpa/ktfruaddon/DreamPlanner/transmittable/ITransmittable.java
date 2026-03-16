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

import net.minecraft.nbt.NBTTagCompound;

public interface ITransmittable {
    ITransmittable initFrom(ITransmittableType type, long amount);
    ITransmittableType getType();
    long getAmount();
    default NBTTagCompound save() {return new NBTTagCompound();}
    default boolean isTypeEqual(ITransmittable t){
        return getType().equals(t.getType());
    }
    default ITransmittable copy(){ return initFrom(getType(), getAmount());}
    static ITransmittable load(NBTTagCompound nbt){
        return new kTestTrans("1");
    }
}
