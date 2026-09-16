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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.research;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.research.ResearchProject;
import cn.kuzuanpa.ktfruaddon.api.research.task.IResearchTask;
import cn.kuzuanpa.ktfruaddon.api.tile.IResearchTable;
import cn.kuzuanpa.ktfruaddon.tile.research.ResearchTreeMonitor;
import gregapi.data.LH;
import gregapi.util.OM;
import gregapi.util.UT;
import gregapi.util.WD;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import org.jetbrains.annotations.Nullable;

import static gregapi.data.CS.*;

public abstract class MultiResearchTableBase extends TileEntityBase10MultiBlockBase implements IResearchTable {
    protected @Nullable ChunkCoordinates monitorCoord;
    protected int monitorWorldID = Integer.MIN_VALUE;
    protected @Nullable ResearchTreeMonitor monitor;

    public static final String NBT_RESEARCH_MONITOR = "researchMonitor";

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_RESEARCH_MONITOR)) {
            int[] data = aNBT.getIntArray(NBT_RESEARCH_MONITOR);
            if (data.length >= 4) {
                monitorWorldID = data[0];
                monitorCoord = new ChunkCoordinates(data[1], data[2], data[3]);
            }
        }
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        if (monitorCoord != null) {
            aNBT.setIntArray(NBT_RESEARCH_MONITOR, new int[] {monitorWorldID, monitorCoord.posX, monitorCoord.posY, monitorCoord.posZ});
        }
    }

    @Override
    public @Nullable ResearchTreeMonitor getMonitor() {
        return monitor;
    }

    protected void updateMonitor() {
        if (monitor != null && !monitor.isInvalid() && monitorCoord != null
                && monitor.getWorldObj() != null
                && monitor.getWorldObj().provider.dimensionId == monitorWorldID
                && monitor.xCoord == monitorCoord.posX
                && monitor.yCoord == monitorCoord.posY
                && monitor.zCoord == monitorCoord.posZ) return;

        monitor = null;
        if (monitorCoord == null) return;

        World monitorWorld = worldObj != null && worldObj.provider.dimensionId == monitorWorldID
                ? worldObj
                : DimensionManager.getWorld(monitorWorldID);
        if (monitorWorld == null) return;

        TileEntity tile = WD.te(monitorWorld, monitorCoord, false);
        if (tile instanceof ResearchTreeMonitor) monitor = (ResearchTreeMonitor) tile;
    }

    /**
     * Writes a monitor coordinate to the USB held by the player.
     * Follows the same worldID + target coordinate format used by computer cluster bindings.
     */
    public static boolean writeMonitorPosToUSB(EntityPlayer aPlayer, ResearchTreeMonitor aMonitor) {
        ItemStack equippedItem = aPlayer.getCurrentEquippedItem();
        if (!OM.is(OD_USB_STICKS[0], equippedItem)) return false;

        NBTTagCompound data = UT.NBT.make();
        data.setInteger("worldID", aMonitor.getWorldObj().provider.dimensionId);
        data.setInteger(NBT_TARGET_X, aMonitor.xCoord);
        data.setInteger(NBT_TARGET_Y, aMonitor.yCoord);
        data.setInteger(NBT_TARGET_Z, aMonitor.zCoord);

        if (!equippedItem.hasTagCompound()) equippedItem.setTagCompound(UT.NBT.make());
        equippedItem.getTagCompound().setTag(NBT_USB_DATA, data);
        equippedItem.getTagCompound().setByte(NBT_USB_TIER, (byte)1);
        aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN + LH.get(I18nHandler.DATA_WRITE_TO_USB)));
        return true;
    }

    /**
     * Binds this research multiblock to the monitor coordinate stored on the held USB.
     * Returns false only when the held item is not a USB stick, so the caller can fall through to its GUI.
     */
    public boolean bindMonitorFromUSB(EntityPlayer aPlayer) {
        ItemStack equippedItem = aPlayer.getCurrentEquippedItem();
        if (!OM.is(OD_USB_STICKS[0], equippedItem)) return false;

        if (!equippedItem.hasTagCompound() || !equippedItem.getTagCompound().hasKey(NBT_USB_DATA)) {
            sendBindingFailure(aPlayer, LH.get(I18nHandler.ERROR_DATA_INVALID));
            return true;
        }

        NBTTagCompound data = equippedItem.getTagCompound().getCompoundTag(NBT_USB_DATA);
        if (!data.hasKey("worldID") || !data.hasKey(NBT_TARGET_X) || !data.hasKey(NBT_TARGET_Y) || !data.hasKey(NBT_TARGET_Z)) {
            sendBindingFailure(aPlayer, LH.get(I18nHandler.ERROR_DATA_INVALID));
            return true;
        }

        int worldID = data.getInteger("worldID");
        World targetWorld = worldObj != null && worldObj.provider.dimensionId == worldID
                ? worldObj
                : DimensionManager.getWorld(worldID);
        if (targetWorld == null) {
            sendBindingFailure(aPlayer, LH.get(I18nHandler.ERROR_TARGET_NOT_EXIST));
            return true;
        }

        ChunkCoordinates target = new ChunkCoordinates(data.getInteger(NBT_TARGET_X), data.getInteger(NBT_TARGET_Y), data.getInteger(NBT_TARGET_Z));
        TileEntity tile = WD.te(targetWorld, target, false);
        if (!(tile instanceof ResearchTreeMonitor)) {
            sendBindingFailure(aPlayer, LH.get(I18nHandler.ERROR_TARGET_NOT_EXIST));
            return true;
        }

        monitorCoord = target;
        monitorWorldID = worldID;
        monitor = (ResearchTreeMonitor) tile;
        markDirty();
        aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN + LH.get(I18nHandler.DATA_READ_FROM_USB)));
        return true;
    }

    protected void sendBindingFailure(EntityPlayer aPlayer, String reason) {
        aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED + LH.get(I18nHandler.ERROR) + ": " + LH.Chat.YELLOW + reason));
    }

    public long tryPromoteCurrentProjectProgress(Class<? extends IResearchTask> taskType, @Nullable Object consume, boolean dryRun) {
        ResearchProject project = getCurrentProject();
        return project == null ? 0 : project.tryPromoteResearchProgress(taskType, consume, dryRun);
    }

    public @Nullable ResearchProject getCurrentProject() {
        if (monitorCoord == null || monitor == null || monitor.isInvalid()) return null;
        return monitor.theTree.getCurrentProject();
    }
}
