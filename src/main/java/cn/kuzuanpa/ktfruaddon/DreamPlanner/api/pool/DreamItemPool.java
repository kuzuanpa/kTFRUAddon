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

import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.IAbstractTransferable;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.ITransferable;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DreamItemPool {
    public UUID uuid ;
    public Lock lock = new ReentrantLock();
    protected ConcurrentHashMap<ITransferable, Long> items = new ConcurrentHashMap<>();
    public List<IAbstractTransferable> abstractTransmittableList = new ArrayList<>();

    public long requestAddItem(ITransferable item, long required){
        if (items.computeIfPresent(item, (k,v)-> v + required) == null)items.put(item,required);
        return required;
    }

    public long tryRemoveItem(ITransferable item, long required){
        long count = Math.min(required,items.getOrDefault(item, 0L));
        items.computeIfPresent(item, (k,v)-> v - count);
        return count;
    }

    public void save(File dir) {
        lock.lock();
        NBTTagCompound root = new NBTTagCompound();
        NBTTagList list = new NBTTagList();

        root.setLong("UUIDMost", uuid.getMostSignificantBits());
        root.setLong("UUIDLess", uuid.getLeastSignificantBits());

        items.forEach((type,amount)-> {
            NBTTagCompound compound = ITransferable.save(type);
            compound.setLong("Amount",amount);
            list.appendTag(compound);
        });
        root.setTag("PoolList", list);

        try (FileOutputStream fos = new FileOutputStream(dir)) {
            CompressedStreamTools.writeCompressed(root, fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static DreamItemPool load(File dir) {
        DreamItemPool pool = new DreamItemPool();
        try (FileInputStream fis = new FileInputStream(dir)) {
            NBTTagCompound root = CompressedStreamTools.readCompressed(fis);

            long most = root.getLong("UUIDMost");
            long least = root.getLong("UUIDLess");
            pool.uuid = new UUID(most, least);

            NBTTagList list = root.getTagList("PoolList", 10);
            ConcurrentHashMap<ITransferable, Long> newItems = new ConcurrentHashMap<>();
            for (int i = 0; i < list.tagCount(); i++) {
                NBTTagCompound compound = list.getCompoundTagAt(i);
                ITransferable type = ITransferable.load(compound);
                long amount = compound.getLong("Amount");
                newItems.put(type, amount);
            }
            pool.items = newItems;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return pool;
    }
}
