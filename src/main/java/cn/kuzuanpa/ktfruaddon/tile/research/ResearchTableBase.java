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

package cn.kuzuanpa.ktfruaddon.tile.research;

import cn.kuzuanpa.ktfruaddon.api.research.ResearchProject;
import cn.kuzuanpa.ktfruaddon.api.research.task.IResearchTask;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import gregapi.data.CS;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.util.WD;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import org.jetbrains.annotations.Nullable;

public abstract class ResearchTableBase extends TileEntityBase09FacingSingle {
    protected @Nullable ChunkCoordinates monitorCoord;
    protected @Nullable ResearchTreeMonitor monitor;
    protected long lastUsedTime = 0;
    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (isServerSide()) {
            openGUI(aPlayer, aSide);
            return true;
        }
        return false;
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        monitorCoord = utils.getRealCoord(mFacing, xCoord, yCoord, zCoord, -1,0,0);
    }

    @Override
    public boolean[] getValidSides() {
        return CS.SIDES_HORIZONTAL;
    }
    @Override
    public byte getDefaultSide() {
        return CS.SIDE_FRONT;
    }

    @Override
    public boolean allowInteraction(Entity aEntity) {
        if ((getTimer() - lastUsedTime) > 10 && aEntity != null) {
            mOwner = aEntity.getUniqueID();
        }
        lastUsedTime = getTimer();
        return super.allowInteraction(aEntity);
    }

    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return null;
    }

    @Override
    public boolean canDrop(int aSlot) {
        return true;
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        updateMonitorCoord();
        checkMonitor(aTimer);
        super.onTick2(aTimer, aIsServerSide);
    }

    public void updateMonitorCoord(){}

    public void checkMonitor(long aTimer){
        if(monitorCoord == null || aTimer % 10 != 0)return;
        TileEntity tile = WD.te(getWorldObj(),monitorCoord, false);
        if(tile instanceof ResearchTreeMonitor) monitor = ((ResearchTreeMonitor) tile);
        else if(tile instanceof ResearchTableBase) monitor = ((ResearchTableBase) tile).monitor;
        else monitor = null;
    }

    public long tryPromoteCurrentProjectProgress(Class<? extends IResearchTask> taskType, @Nullable Object consume, boolean dryRun){
        return getCurrentProject() == null?0: getCurrentProject().tryPromoteResearchProgress(taskType,consume, dryRun);
    }

    public @Nullable ResearchProject getCurrentProject(){
        if(monitorCoord == null || monitor == null || monitor.isInvalid())return null;
        return monitor.currentProject;
    }


}
