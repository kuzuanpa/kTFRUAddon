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

package cn.kuzuanpa.ktfruaddon.DreamPlanner.client.gui;

import cn.kuzuanpa.kGuiLib.client.kGuiContainerBase;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.client.gui.button.TransferableDetail;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.test.StringTestTransferable;
import cn.kuzuanpa.ktfruaddon.api.nei.IHiddenNei;
import gregapi.gui.ContainerCommon;
import gregapi.tileentity.ITileEntityInventoryGUI;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class PlanTreeView extends kGuiContainerBase implements IHiddenNei {

    private final PlanTreeViewContainer mContainer;

    public PlanTreeView(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, int aGUIID) {
        super(new PlanTreeViewContainer(aInventoryPlayer, aTileEntity,aGUIID));
        this.mContainer= (PlanTreeViewContainer) inventorySlots;
    }

    @Override
    public void addButtons() {
        buttons.add(new TransferableDetail(0, new StringTestTransferable("Test1").getRenderDescriber()));
    }

    @Override
    public void onKeyTyped(char key, int keyCode) {
        close();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        final ResourceLocation background = new ResourceLocation(MOD_ID,"textures/gui/research/background.png");

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        mc.getTextureManager().bindTexture(background);
        GL11.glColor4f(1,1,1,0.1f);
        this.drawTexturedModalRect(0,0, 0, 14, width, height);
    }

    public static class PlanTreeViewContainer extends ContainerCommon {

        public PlanTreeViewContainer(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, int aGUIID) {
            super(aInventoryPlayer, aTileEntity, aGUIID);
        }
        public boolean doesBindPlayerInventory() {return false;}

        @Override public int getStartIndex() {return 0;}
        @Override public int getSlotCount() {return 0;}
        @Override public int getShiftClickStartIndex() {return 0;}
        @Override public int getShiftClickSlotCount() {return 0;}
    }
}
