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

package cn.kuzuanpa.ktfruaddon.DreamPlanner.client.gui.button;

import cn.kuzuanpa.kGuiLib.client.objects.gui.kGuiButtonBase;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.client.gui.util;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.TransferableDescriber;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class TransferableDetail extends kGuiButtonBase {
    TransferableDescriber describer;
    public TransferableDetail(int id, TransferableDescriber describer) {
        super(id, 0, 0, describer.getWidth(), describer.getHeight(), "");
        this.describer=describer;
        setAnimatedInFBO(true);
    }
    final ResourceLocation main = new ResourceLocation(MOD_ID,"textures/gui/research/main.png");

    @Override
    public void drawButton2(Minecraft mc, int mouseX, int mouseY) {
        if (!visible) return;
        GL11.glPushMatrix();
        //draw background
        mc.getTextureManager().bindTexture(main);
        GL11.glColor4f(1, 1, 1, 1);
        util.drawTexturedModalRect(0,0, (int) this.zLevel,0,0,width,height);

        //draw item icon
        util.itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer,mc.getTextureManager(), describer.getItemStack(), 0, 0);
        GL11.glEnable(GL11.GL_BLEND);

        //draw name&desc
        mc.fontRenderer.drawString(describer.getName(), 0,0,0x000000);
        mc.fontRenderer.drawString(describer.getDesc(), 0,0,0x000000);
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glPopMatrix();
    }
}
