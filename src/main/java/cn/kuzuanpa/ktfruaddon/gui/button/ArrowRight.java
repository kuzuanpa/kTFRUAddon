/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class ArrowRight extends CommonGuiButton {
    public ArrowRight(int id, int xPos, int yPos, String displayText) {
        super(id, xPos, yPos, 5,8, displayText);
    }
    ResourceLocation textures=new ResourceLocation(MOD_ID,"textures/gui/buttons.png");

    public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_) {
        if (this.visible)
        {
            GL11.glPushMatrix();
            p_146112_1_.getTextureManager().bindTexture(textures);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            if (isMouseHover) this.drawTexturedModalRect(xPosition,yPosition, 21, 8, 5, 8);
            else this.drawTexturedModalRect(xPosition,yPosition, 21, 0, 5, 8);
            GL11.glPopMatrix();
        }
    }
}
