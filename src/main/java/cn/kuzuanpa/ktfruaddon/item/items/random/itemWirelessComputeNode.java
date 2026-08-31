/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.

 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */



package cn.kuzuanpa.ktfruaddon.item.items.random;

import cn.kuzuanpa.ktfruaddon.api.item.IComputerItem;
import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputerCluster;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputeItemUser;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterController;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterUser;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.UserData;
import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

/**
 * A computer that owns no silicon of its own, it rents Compute Power from the cluster the host is
 * joined to and presents it to the host as if it was local. Both the type and the amount only depend
 * on the meta, so a node cannot be tuned by NBT.
 */
public class itemWirelessComputeNode extends MultiItemRandom implements IComputerItem {
    public itemWirelessComputeNode() {
        super(MOD_ID, "ktfru.item.it.wirelessComputeNode");
        setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: Wireless Compute Nodes", this, (short) 0));
    }

    //Index:                                        0     ,1     ,2       ,3        ,4      ,5      ,6       ,7        ,8      ,9      ,10      ,11      ,12     ,13     ,14      ,15
    private static final ComputePower[] TYPE     = {ComputePower.Normal   ,ComputePower.Normal   ,ComputePower.Normal   ,ComputePower.Normal
                                                   ,ComputePower.Biology  ,ComputePower.Biology  ,ComputePower.Biology  ,ComputePower.Biology
                                                   ,ComputePower.Quantum  ,ComputePower.Quantum  ,ComputePower.Quantum  ,ComputePower.Quantum
                                                   ,ComputePower.Spacetime,ComputePower.Spacetime,ComputePower.Spacetime,ComputePower.Spacetime};
    /**What the host sees as local Compute Power.**/
    private static final long[] PROVIDED         = {2000  ,20000 ,200000  ,2000000  ,1000   ,10000  ,100000  ,1000000  ,500    ,5000   ,50000   ,500000  ,200    ,2000   ,20000   ,200000};
    /**What gets taken out of the cluster, the part above {@link #PROVIDED} is the wireless overhead.**/
    private static final long[] REQUESTED        = {3000  ,26000 ,240000  ,2200000  ,1500   ,13000  ,120000  ,1100000  ,750    ,6500   ,60000   ,550000  ,300    ,2600   ,24000   ,220000};

    public static ComputePower getTypeFromID(int id) {
        return id >= 0 && id < TYPE.length ? TYPE[id] : ComputePower.Normal;
    }

    protected static long amountFromID(long[] table, int id) {
        return id >= 0 && id < table.length ? table[id] : 0;
    }

    @Override
    public @NotNull Map.Entry<ComputePower, Long> getComputePower(int amount, int meta) {
        return getTypeFromID(meta).asEntry(amountFromID(PROVIDED, meta) * Math.max(0, amount));
    }

    /**@return what this stack has to rent from the cluster in order to run.**/
    protected Map.Entry<ComputePower, Long> getRequestedComputePower(ItemStack stack) {
        int meta = stack.getItemDamage();
        return getTypeFromID(meta).asEntry(amountFromID(REQUESTED, meta) * Math.max(0, stack.stackSize));
    }

    /**@return the cluster this host rents from, null when the host is not a cluster user or not connected.**/
    protected static @Nullable ComputerCluster getCluster(@Nullable IComputeItemUser host) {
        if (!(host instanceof IComputerClusterUser)) return null;
        IComputerClusterController controller = ((IComputerClusterUser) host).getController();
        return controller == null ? null : controller.getCluster();
    }

    @Override
    public boolean onStart(ItemStack stack, IComputeItemUser host) {
        ComputerCluster cluster = getCluster(host);
        if (cluster == null) return false;
        IComputerClusterUser user = (IComputerClusterUser) host;

        UserData data = cluster.getUserData(user.getUUID());
        if (data == null) {
            cluster.joinUser(user);
            data = cluster.getUserData(user.getUUID());
            if (data == null) return false;
        }

        Map.Entry<ComputePower, Long> requested = getRequestedComputePower(stack);
        if (requested.getValue() <= 0) return true;
        if (!cluster.isComputePowerSufficient(requested)) return false;

        cluster.usedComputePower.merge(requested.getKey(), requested.getValue(), Long::sum);
        //Booked on the UserData as well, that way the cluster hands it back on its own when the host disappears.
        data.consumingPower.merge(requested.getKey(), requested.getValue(), Long::sum);
        cluster.clientDataDirty = true;
        return true;
    }

    @Override
    public void onStop(ItemStack stack, @Nullable IComputeItemUser host) {
        ComputerCluster cluster = getCluster(host);
        if (cluster == null) return;
        UserData data = cluster.getUserData(((IComputerClusterUser) host).getUUID());
        if (data == null) return;

        Map.Entry<ComputePower, Long> requested = getRequestedComputePower(stack);
        if (requested.getValue() <= 0) return;

        cluster.usedComputePower.merge(requested.getKey(), requested.getValue(), (used, released) -> Math.max(0L, used - released));
        data.consumingPower.merge(requested.getKey(), requested.getValue(), (held, released) -> Math.max(0L, held - released));
        data.consumingPower.values().removeIf(amount -> amount == null || amount <= 0);
        cluster.clientDataDirty = true;
    }

    @Override
    public void addItems() {
        ItemList.WirelessComputeNodeNormalT1   .set(addItem( 0, "Wireless Compute Node T1"          , "Rents 3000 MFLOPS, provides 2000 MFLOPS"));
        ItemList.WirelessComputeNodeNormalT2   .set(addItem( 1, "Wireless Compute Node T2"          , "Rents 26000 MFLOPS, provides 20000 MFLOPS"));
        ItemList.WirelessComputeNodeNormalT3   .set(addItem( 2, "Wireless Compute Node T3"          , "Rents 240000 MFLOPS, provides 200000 MFLOPS"));
        ItemList.WirelessComputeNodeNormalT4   .set(addItem( 3, "Wireless Compute Node T4"          , "Rents 2200000 MFLOPS, provides 2000000 MFLOPS"));

        ItemList.WirelessComputeNodeBiologyT1  .set(addItem( 4, "Wireless Biology Compute Node T1"  , "Rents 1500 MFLOPS, provides 1000 MFLOPS"));
        ItemList.WirelessComputeNodeBiologyT2  .set(addItem( 5, "Wireless Biology Compute Node T2"  , "Rents 13000 MFLOPS, provides 10000 MFLOPS"));
        ItemList.WirelessComputeNodeBiologyT3  .set(addItem( 6, "Wireless Biology Compute Node T3"  , "Rents 120000 MFLOPS, provides 100000 MFLOPS"));
        ItemList.WirelessComputeNodeBiologyT4  .set(addItem( 7, "Wireless Biology Compute Node T4"  , "Rents 1100000 MFLOPS, provides 1000000 MFLOPS"));

        ItemList.WirelessComputeNodeQuantumT1  .set(addItem( 8, "Wireless Quantum Compute Node T1"  , "Rents 750 MFLOPS, provides 500 MFLOPS"));
        ItemList.WirelessComputeNodeQuantumT2  .set(addItem( 9, "Wireless Quantum Compute Node T2"  , "Rents 6500 MFLOPS, provides 5000 MFLOPS"));
        ItemList.WirelessComputeNodeQuantumT3  .set(addItem(10, "Wireless Quantum Compute Node T3"  , "Rents 60000 MFLOPS, provides 50000 MFLOPS"));
        ItemList.WirelessComputeNodeQuantumT4  .set(addItem(11, "Wireless Quantum Compute Node T4"  , "Rents 550000 MFLOPS, provides 500000 MFLOPS"));

        ItemList.WirelessComputeNodeSpacetimeT1.set(addItem(12, "Wireless Spacetime Compute Node T1", "Rents 300 MFLOPS, provides 200 MFLOPS"));
        ItemList.WirelessComputeNodeSpacetimeT2.set(addItem(13, "Wireless Spacetime Compute Node T2", "Rents 2600 MFLOPS, provides 2000 MFLOPS"));
        ItemList.WirelessComputeNodeSpacetimeT3.set(addItem(14, "Wireless Spacetime Compute Node T3", "Rents 24000 MFLOPS, provides 20000 MFLOPS"));
        ItemList.WirelessComputeNodeSpacetimeT4.set(addItem(15, "Wireless Spacetime Compute Node T4", "Rents 220000 MFLOPS, provides 200000 MFLOPS"));
    }
}
