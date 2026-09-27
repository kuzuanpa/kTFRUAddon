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

package cn.kuzuanpa.ktfruaddon.command;

import cn.kuzuanpa.ktfruaddon.api.code.WorldPos;
import cn.kuzuanpa.ktfruaddon.api.network.PacketUUIDAssignedData;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputerCluster;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterController;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterUser;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.storage.WirelessBatteryBase;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.parts.WirelessEnergyReceiver;
import cn.kuzuanpa.ktfruaddon.tile.research.ResearchTreeMonitor;
import gregapi.data.LH;
import gregapi.util.WD;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.kNetworkHandler;

public class CommandKTFRUWireless extends CommandBase {
    public static final String NBT_ROOT = "ktfru.wireless.cli";
    public static final String NBT_ACTIVE = "active";
    public static final String MODE_ENERGY = "energy";
    public static final String MODE_COMPUTE = "compute";
    public static final String MODE_RESEARCH = "research";

    /**Non-player senders (console, command block) cannot persist bindings on an entity, so they get an in-memory binding store keyed by sender name. Session only, admin convenience.**/
    protected static final Map<String, NBTTagCompound> adminBindings = new HashMap<>();

    static {
        LH.add("ktfru.wireless.cli.usage", "/kTFRUWireless <status|bind|energy|compute|research> ...");
        LH.add("ktfru.wireless.cli.header", "=== Wireless Device Command Line Interface ===");
        LH.add("ktfru.wireless.cli.bind.usage", "Usage: bind <energy|compute|research> <x> <y> <z> [dim]");
        LH.add("ktfru.wireless.cli.bind.bad_mode", "Unknown mode, available: energy, compute, research");
        LH.add("ktfru.wireless.cli.bind.ok", "Bound [%s] terminal: ");
        LH.add("ktfru.wireless.cli.bind.wrong_type", "That position is not a valid [%s] terminal");
        LH.add("ktfru.wireless.cli.status.title", "Bindings:");
        LH.add("ktfru.wireless.cli.status.active", "Interface activated");
        LH.add("ktfru.wireless.cli.status.none", "  [%s] not bound");
        LH.add("ktfru.wireless.cli.status.bound", "  [%s] -> ");
        LH.add("ktfru.wireless.cli.no_binding", "No [%s] terminal bound, use /kTFRUWireless bind %s <x> <y> <z> first");
        LH.add("ktfru.wireless.cli.unreachable", "Cannot connect to %s: target chunk not loaded or no block there");
        LH.add("ktfru.wireless.cli.wrong_type", "%s is not a valid [%s] terminal");
        LH.add("ktfru.wireless.cli.energy.info", "Wireless transmitter %s: energy %d / %d, links %d, state %s");
        LH.add("ktfru.wireless.cli.energy.link.usage", "Usage: energy link <add|remove> <x> <y> <z> [dim]");
        LH.add("ktfru.wireless.cli.energy.link.added", "Energy link added -> ");
        LH.add("ktfru.wireless.cli.energy.link.removed", "Energy link removed -> ");
        LH.add("ktfru.wireless.cli.energy.link.duplicate", "That link already exists");
        LH.add("ktfru.wireless.cli.energy.link.missing", "No such link found");
        LH.add("ktfru.wireless.cli.energy.link.not_receiver", "%s is not a wireless energy receiver");
        LH.add("ktfru.wireless.cli.compute.info", "Compute cluster %s: state %s, controllers %d, users %d");
        LH.add("ktfru.wireless.cli.compute.power", "  Compute %s: %d / %d");
        LH.add("ktfru.wireless.cli.compute.no_cluster", "That controller has not formed a cluster yet");
        LH.add("ktfru.wireless.cli.compute.add.usage", "Usage: compute add <controller|user> <x> <y> <z> [dim]");
        LH.add("ktfru.wireless.cli.compute.add.controller.ok", "Controller joined the cluster -> ");
        LH.add("ktfru.wireless.cli.compute.add.user.ok", "User bound to the controller -> ");
        LH.add("ktfru.wireless.cli.compute.add.not_controller", "%s is not a compute controller");
        LH.add("ktfru.wireless.cli.compute.add.not_user", "%s is not a compute user");
        LH.add("ktfru.wireless.cli.compute.add.failed", "Operation failed: ");
        LH.add("ktfru.wireless.cli.research.open", "Opening the research tree GUI...");
        LH.add("ktfru.wireless.cli.research.syncing", "Syncing research tree data, please retry shortly");
        LH.add("ktfru.wireless.cli.research.no_tree", "That monitor has no available research tree");
        LH.add("ktfru.wireless.cli.player_only", "This action can only be performed by a player");
    }

    @Override public String getCommandName() {return "kTFRUWireless";}
    @Override public String getCommandUsage(ICommandSender sender) {return LH.get("ktfru.wireless.cli.usage");}
    @Override public int getRequiredPermissionLevel() {return 0;}
    @Override public boolean canCommandSenderUseCommand(ICommandSender sender) {return true;}

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        //Only offer completions to senders that actually have access, so a player without a wristband cannot probe the command.
        if (resolveBindings(sender) == null) return null;

        if (args.length == 1)
            return getListOfStringsMatchingLastWord(args, "status", "bind", MODE_ENERGY, MODE_COMPUTE, MODE_RESEARCH);

        String domain = args[0].toLowerCase();
        if (domain.equals("bind")) {
            if (args.length == 2) return getListOfStringsMatchingLastWord(args, MODE_ENERGY, MODE_COMPUTE, MODE_RESEARCH);
        } else if (domain.equals(MODE_ENERGY)) {
            if (args.length == 2) return getListOfStringsMatchingLastWord(args, "link");
            if (args[1].equalsIgnoreCase("link") && args.length == 3) return getListOfStringsMatchingLastWord(args, "add", "remove");
        } else if (domain.equals(MODE_COMPUTE)) {
            if (args.length == 2) return getListOfStringsMatchingLastWord(args, "add");
            if (args[1].equalsIgnoreCase("add") && args.length == 3) return getListOfStringsMatchingLastWord(args, "controller", "user");
        }
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        NBTTagCompound bindings = resolveBindings(sender);
        //Player without an activated wristband: behave as if they carry no device, give no feedback at all.
        if (bindings == null) return;

        if (args.length == 0) {
            msg(sender, LH.Chat.CYAN + LH.get("ktfru.wireless.cli.header"));
            msg(sender, LH.get("ktfru.wireless.cli.usage"));
            return;
        }

        String domain = args[0].toLowerCase();
        switch (domain) {
            case "status": doStatus(sender, bindings); return;
            case "bind": doBind(sender, bindings, args); return;
            case MODE_ENERGY: doEnergy(sender, bindings, args); return;
            case MODE_COMPUTE: doCompute(sender, bindings, args); return;
            case MODE_RESEARCH: doResearch(sender, bindings, args); return;
            default: msg(sender, LH.get("ktfru.wireless.cli.usage"));
        }
    }

    protected void doStatus(ICommandSender sender, NBTTagCompound bindings) {
        msg(sender, LH.Chat.CYAN + LH.get("ktfru.wireless.cli.status.active"));
        msg(sender, LH.get("ktfru.wireless.cli.status.title"));
        for (String mode : new String[]{MODE_ENERGY, MODE_COMPUTE, MODE_RESEARCH}) {
            WorldPos pos = readBinding(bindings, mode);
            if (pos == null) msg(sender, String.format(LH.get("ktfru.wireless.cli.status.none"), mode));
            else msg(sender, String.format(LH.get("ktfru.wireless.cli.status.bound"), mode) + fmt(pos));
        }
    }

    protected void doBind(ICommandSender sender, NBTTagCompound bindings, String[] args) {
        if (args.length < 5) {msg(sender, LH.get("ktfru.wireless.cli.bind.usage")); return;}
        String mode = args[1].toLowerCase();
        if (!mode.equals(MODE_ENERGY) && !mode.equals(MODE_COMPUTE) && !mode.equals(MODE_RESEARCH)) {
            msg(sender, LH.get("ktfru.wireless.cli.bind.bad_mode")); return;
        }
        WorldPos pos = parsePos(sender, args, 2);
        if (pos == null) return;

        TileEntity tile = resolveTile(pos);
        if (tile == null) {msg(sender, String.format(LH.get("ktfru.wireless.cli.unreachable"), fmt(pos))); return;}
        if (!isValidTerminal(mode, tile)) {msg(sender, String.format(LH.get("ktfru.wireless.cli.bind.wrong_type"), mode)); return;}

        writeBinding(bindings, mode, pos);
        persistBindings(sender, bindings);
        msg(sender, LH.Chat.CYAN + String.format(LH.get("ktfru.wireless.cli.bind.ok"), mode) + fmt(pos));
    }

    protected boolean isValidTerminal(String mode, TileEntity tile) {
        switch (mode) {
            case MODE_ENERGY: return tile instanceof WirelessBatteryBase;
            case MODE_COMPUTE: return tile instanceof IComputerClusterController;
            case MODE_RESEARCH: return tile instanceof ResearchTreeMonitor;
            default: return false;
        }
    }

    protected void doEnergy(ICommandSender sender, NBTTagCompound bindings, String[] args) {
        WorldPos pos = requireBinding(sender, bindings, MODE_ENERGY);
        if (pos == null) return;
        TileEntity tile = requireTerminal(sender, pos, MODE_ENERGY);
        if (!(tile instanceof WirelessBatteryBase)) return;
        WirelessBatteryBase battery = (WirelessBatteryBase) tile;

        if (args.length >= 2 && args[1].equalsIgnoreCase("link")) {
            doEnergyLink(sender, battery, args);
            return;
        }
        msg(sender, LH.Chat.CYAN + String.format(LH.get("ktfru.wireless.cli.energy.info"),
                fmt(pos), battery.mEnergyStored, battery.mCapacity, battery.partLinks.size(), battery.sealed ? "ON" : "OFF"));
        for (WirelessBatteryBase.WirelessLink link : battery.partLinks)
            msg(sender, "  -> dim " + link.dimension + ": " + link.x + ", " + link.y + ", " + link.z + (link.quantum ? " (quantum)" : ""));
    }

    protected void doEnergyLink(ICommandSender sender, WirelessBatteryBase battery, String[] args) {
        if (args.length < 6) {msg(sender, LH.get("ktfru.wireless.cli.energy.link.usage")); return;}
        String action = args[2].toLowerCase();
        WorldPos target = parsePos(sender, args, 3);
        if (target == null) return;

        boolean quantum = false;
        TileEntity tile = resolveTile(target);
        if (tile instanceof WirelessEnergyReceiver) quantum = ((WirelessEnergyReceiver) tile).mUniversal;
        else if (action.equals("add")) {msg(sender, String.format(LH.get("ktfru.wireless.cli.energy.link.not_receiver"), fmt(target))); return;}

        WirelessBatteryBase.WirelessLink link = new WirelessBatteryBase.WirelessLink(target.dim, target.x, target.y, target.z, quantum);
        if (action.equals("add")) {
            if (battery.partLinks.contains(link)) {msg(sender, LH.Chat.YELLOW + LH.get("ktfru.wireless.cli.energy.link.duplicate")); return;}
            battery.partLinks.add(link);
            msg(sender, LH.Chat.CYAN + LH.get("ktfru.wireless.cli.energy.link.added") + fmt(target));
        } else if (action.equals("remove")) {
            boolean removed = battery.partLinks.remove(link);
            msg(sender, (removed ? LH.Chat.CYAN : LH.Chat.YELLOW) + LH.get(removed ? "ktfru.wireless.cli.energy.link.removed" : "ktfru.wireless.cli.energy.link.missing") + (removed ? fmt(target) : ""));
        } else {
            msg(sender, LH.get("ktfru.wireless.cli.energy.link.usage"));
        }
    }

    protected void doCompute(ICommandSender sender, NBTTagCompound bindings, String[] args) {
        WorldPos pos = requireBinding(sender, bindings, MODE_COMPUTE);
        if (pos == null) return;
        TileEntity tile = requireTerminal(sender, pos, MODE_COMPUTE);
        if (!(tile instanceof IComputerClusterController)) return;
        IComputerClusterController controller = (IComputerClusterController) tile;
        ComputerCluster cluster = controller.getCluster();
        if (cluster == null) {msg(sender, LH.Chat.YELLOW + LH.get("ktfru.wireless.cli.compute.no_cluster")); return;}

        if (args.length >= 2 && args[1].equalsIgnoreCase("add")) {
            doComputeAdd(sender, controller, cluster, args);
            return;
        }
        msg(sender, LH.Chat.CYAN + String.format(LH.get("ktfru.wireless.cli.compute.info"),
                shortUUID(cluster.clusterUUID), stateName(cluster.state), cluster.controllerList.size(), cluster.userList.size()));
        for (ComputePower type : ComputePower.values()) {
            long total = cluster.totalComputePower.getOrDefault(type, 0L);
            long used = cluster.usedComputePower.getOrDefault(type, 0L);
            if (total > 0 || used > 0) msg(sender, String.format(LH.get("ktfru.wireless.cli.compute.power"), type.name(), used, total));
        }
    }

    protected void doComputeAdd(ICommandSender sender, IComputerClusterController controller, ComputerCluster cluster, String[] args) {
        if (args.length < 6) {msg(sender, LH.get("ktfru.wireless.cli.compute.add.usage")); return;}
        String kind = args[2].toLowerCase();
        WorldPos target = parsePos(sender, args, 3);
        if (target == null) return;
        TileEntity tile = resolveTile(target);
        if (tile == null) {msg(sender, String.format(LH.get("ktfru.wireless.cli.unreachable"), fmt(target))); return;}

        if (kind.equals("controller")) {
            if (!(tile instanceof IComputerClusterController)) {msg(sender, String.format(LH.get("ktfru.wireless.cli.compute.add.not_controller"), fmt(target))); return;}
            String err = cluster.join(resolveWorld(target.dim), new WorldPos(target.x, target.y, target.z, target.dim), (IComputerClusterController) tile);
            if (err != null) {msg(sender, LH.Chat.YELLOW + LH.get("ktfru.wireless.cli.compute.add.failed") + LH.get(err)); return;}
            msg(sender, LH.Chat.CYAN + LH.get("ktfru.wireless.cli.compute.add.controller.ok") + fmt(target));
        } else if (kind.equals("user")) {
            if (!(tile instanceof IComputerClusterUser)) {msg(sender, String.format(LH.get("ktfru.wireless.cli.compute.add.not_user"), fmt(target))); return;}
            IComputerClusterUser user = (IComputerClusterUser) tile;
            user.setController(controller);
            cluster.joinUser(user);
            msg(sender, LH.Chat.CYAN + LH.get("ktfru.wireless.cli.compute.add.user.ok") + fmt(target));
        } else {
            msg(sender, LH.get("ktfru.wireless.cli.compute.add.usage"));
        }
    }

    protected void doResearch(ICommandSender sender, NBTTagCompound bindings, String[] args) {
        if (!(sender instanceof EntityPlayerMP)) {msg(sender, LH.get("ktfru.wireless.cli.player_only")); return;}
        WorldPos pos = requireBinding(sender, bindings, MODE_RESEARCH);
        if (pos == null) return;
        TileEntity tile = requireTerminal(sender, pos, MODE_RESEARCH);
        if (!(tile instanceof ResearchTreeMonitor)) return;
        ResearchTreeMonitor monitor = (ResearchTreeMonitor) tile;
        if (monitor.theTree == null || monitor.theTree.uuid == null) {msg(sender, LH.Chat.YELLOW + LH.get("ktfru.wireless.cli.research.no_tree")); return;}

        //Read-only: hand the tree UUID to the client which opens the same portable viewer GUI as the research viewer item.
        kNetworkHandler.sendToPlayer(new PacketUUIDAssignedData((byte) 2, monitor.theTree.uuid), (EntityPlayerMP) sender);
        msg(sender, LH.Chat.CYAN + LH.get("ktfru.wireless.cli.research.open"));
    }

    protected WorldPos requireBinding(ICommandSender sender, NBTTagCompound bindings, String mode) {
        WorldPos pos = readBinding(bindings, mode);
        if (pos == null) msg(sender, LH.Chat.YELLOW + String.format(LH.get("ktfru.wireless.cli.no_binding"), mode, mode));
        return pos;
    }

    /**@return the tile at pos, after emitting the proper failure message when it is missing or the wrong type. Callers still have to instanceof-check the result.**/
    protected TileEntity requireTerminal(ICommandSender sender, WorldPos pos, String mode) {
        TileEntity tile = resolveTile(pos);
        if (tile == null) {msg(sender, LH.Chat.YELLOW + String.format(LH.get("ktfru.wireless.cli.unreachable"), fmt(pos))); return null;}
        if (!isValidTerminal(mode, tile)) {msg(sender, LH.Chat.YELLOW + String.format(LH.get("ktfru.wireless.cli.wrong_type"), fmt(pos), mode)); return null;}
        return tile;
    }

    protected static World resolveWorld(int dim) {
        return DimensionManager.getWorld(dim);
    }

    protected static TileEntity resolveTile(WorldPos pos) {
        World world = resolveWorld(pos.dim);
        if (world == null || !world.blockExists(pos.x, pos.y, pos.z)) return null;
        return WD.te(world, pos.x, pos.y, pos.z, false);
    }

    protected WorldPos parsePos(ICommandSender sender, String[] args, int offset) {
        try {
            int x = (int) Math.floor(func_110666_a(sender, sender.getPlayerCoordinates().posX, args[offset]));
            int y = (int) Math.floor(func_110666_a(sender, sender.getPlayerCoordinates().posY, args[offset + 1]));
            int z = (int) Math.floor(func_110666_a(sender, sender.getPlayerCoordinates().posZ, args[offset + 2]));
            int dim = args.length > offset + 3 ? Integer.parseInt(args[offset + 3]) : sender.getEntityWorld().provider.dimensionId;
            return new WorldPos(x, y, z, dim);
        } catch (Exception e) {
            msg(sender, LH.get("ktfru.wireless.cli.bind.usage"));
            return null;
        }
    }

    protected static WorldPos readBinding(NBTTagCompound bindings, String mode) {
        if (!bindings.hasKey(mode)) return null;
        int[] a = bindings.getIntArray(mode);
        return a.length >= 4 ? new WorldPos(a[0], a[1], a[2], a[3]) : null;
    }

    protected static void writeBinding(NBTTagCompound bindings, String mode, WorldPos pos) {
        bindings.setIntArray(mode, new int[]{pos.x, pos.y, pos.z, pos.dim});
    }

    protected static String fmt(WorldPos pos) {
        return "dim " + pos.dim + " (" + pos.x + ", " + pos.y + ", " + pos.z + ")";
    }

    protected static String shortUUID(java.util.UUID uuid) {
        return uuid == null ? "null" : uuid.toString().substring(0, 8);
    }

    protected static String stateName(byte state) {
        switch (state) {
            case 0: return "OFFLINE";
            case 1: return "NORMAL";
            case 2: return "WARNING";
            case 3: return "ERROR";
            case 4: return "BELONG_ERR";
            default: return "?";
        }
    }

    protected static void msg(ICommandSender sender, String text) {
        sender.addChatMessage(new ChatComponentText(text));
    }

    public static NBTTagCompound getWristbandData(EntityPlayer player) {
        NBTTagCompound persisted = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        return persisted.getCompoundTag(NBT_ROOT);
    }

    public static void setWristbandData(EntityPlayer player, NBTTagCompound wristband) {
        NBTTagCompound entityData = player.getEntityData();
        NBTTagCompound persisted = entityData.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        persisted.setTag(NBT_ROOT, wristband);
        entityData.setTag(EntityPlayer.PERSISTED_NBT_TAG, persisted);
    }

    /**@return the binding store for this sender, or null when a player has not activated a wristband yet (the caller must then stay silent).**/
    protected NBTTagCompound resolveBindings(ICommandSender sender) {
        if (sender instanceof EntityPlayer) {
            NBTTagCompound wristband = getWristbandData((EntityPlayer) sender);
            return wristband.getBoolean(NBT_ACTIVE) ? wristband : null;
        }
        return adminBindings.computeIfAbsent(sender.getCommandSenderName(), k -> new NBTTagCompound());
    }

    protected void persistBindings(ICommandSender sender, NBTTagCompound bindings) {
        if (sender instanceof EntityPlayer) setWristbandData((EntityPlayer) sender, bindings);
    }
}
