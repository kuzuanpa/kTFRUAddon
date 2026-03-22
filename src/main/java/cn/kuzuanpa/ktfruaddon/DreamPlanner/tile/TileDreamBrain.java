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

package cn.kuzuanpa.ktfruaddon.DreamPlanner.tile;

import cn.kuzuanpa.ktfruaddon.DreamPlanner.api.plan.DreamBrain;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.api.plan.DreamPlanSimple;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.api.plan.DreamerPool;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.client.gui.PlanTreeView;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.test.AbstractStringTestTransferable;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.test.DreamPlanTestAbstract;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.test.StringTestTransferable;
import codechicken.lib.vec.BlockCoord;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Collections;

public class TileDreamBrain extends TileEntityBase09FacingSingle {
    DreamBrain brain = new DreamBrain();

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (isServerSide()) {
            openGUI(aPlayer, aSide);
            return true;
        }
        return false;
    }
    public void TileDreamBrain(){
        brain.dreamItemPool.abstractTransmittableList.add(new AbstractStringTestTransferable("Abs0-"));
        brain.dreamItemPool.abstractTransmittableList.add(new AbstractStringTestTransferable("Abs1-"));

        brain.addPlan(new DreamPlanTestAbstract(new BlockCoord(), "Abs0-", "Abs1-"));
        brain.addPlan(new DreamPlanSimple(new BlockCoord(), Collections.singletonList(new StringTestTransferable("Abs1-A").make(3)), Collections.singletonList(new StringTestTransferable("A").make(1))));
        brain.addPlan(new DreamPlanSimple(new BlockCoord(), Collections.singletonList(new StringTestTransferable("C").make(3)), Collections.singletonList(new StringTestTransferable("Abs0-A").make(1))));
        DreamBrain.PlanTreeNode treeNode = new DreamBrain.PlanTreeNode(-1);
        brain.makeItem(new StringTestTransferable("A"), 5, treeNode);
        brain.printTreeNode(treeNode, 0);

        DreamerPool pool = new DreamerPool();
        pool.doTreeNode(treeNode);
    }
    @Override public String getTileEntityName() {return "ktfru.multitileentity.research.table.TileDreamBrain";}

    @Override
    public ITexture getTexture2(Block block, int i, byte b, boolean[] booleans) {
        return null;
    }

    @Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {
        return new PlanTreeView(aPlayer.inventory, this, aGUIID);
    }
    @Override public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {
        return new PlanTreeView.PlanTreeViewContainer(aPlayer.inventory, this, aGUIID);
    }

    @Override
    public boolean canDrop(int i) {
        return true;
    }
}
