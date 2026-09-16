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

import cn.kuzuanpa.ktfruaddon.api.research.ResearchProject;
import cn.kuzuanpa.ktfruaddon.api.research.task.IResearchTask;
import cn.kuzuanpa.ktfruaddon.api.tile.IResearchTable;
import cn.kuzuanpa.ktfruaddon.api.tile.part.IMultiBlockPart;
import cn.kuzuanpa.ktfruaddon.tile.research.ResearchTreeMonitor;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import gregapi.util.WD;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import org.jetbrains.annotations.Nullable;

public abstract class MultiResearchTableBase extends TileEntityBase10MultiBlockBase implements IResearchTable {
    protected @Nullable ChunkCoordinates monitorCoord;
    protected @Nullable ResearchTreeMonitor monitor;

    @Override
    public @Nullable ResearchTreeMonitor getMonitor() {
        return monitor;
    }

    protected void updateMonitor() {
        if (monitor != null && !monitor.isInvalid()) return;
        monitor = null;
        monitorCoord = new ChunkCoordinates(xCoord + 1, yCoord, zCoord);
        if (checkMonitorByCoord()) return;
        monitorCoord = new ChunkCoordinates(xCoord - 1, yCoord, zCoord);
        if (checkMonitorByCoord()) return;
        monitorCoord = new ChunkCoordinates(xCoord, yCoord, zCoord + 1);
        if (checkMonitorByCoord()) return;
        monitorCoord = new ChunkCoordinates(xCoord, yCoord, zCoord - 1);
        if (checkMonitorByCoord()) return;
        monitorCoord = null;
    }

    protected boolean checkMonitorByCoord() {
        if (monitorCoord == null || worldObj == null) return false;
        TileEntity tile = WD.te(worldObj, monitorCoord, false);
        if (tile instanceof IResearchTable) {
            monitor = ((IResearchTable) tile).getMonitor();
        } else if (tile instanceof MultiTileEntityMultiBlockPart) {
            ITileEntityMultiBlockController multi = ((MultiTileEntityMultiBlockPart) tile).getTarget(true);
            monitor = multi instanceof IResearchTable ? ((IResearchTable) multi).getMonitor() : null;
        } else if (tile instanceof IMultiBlockPart) {
            ITileEntityMultiBlockController multi = ((IMultiBlockPart) tile).getTarget(true);
            monitor = multi instanceof IResearchTable ? ((IResearchTable) multi).getMonitor() : null;
        } else {
            monitor = null;
        }
        return monitor != null;
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
