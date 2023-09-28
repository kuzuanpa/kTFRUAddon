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
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class CommonGuiButton extends GuiButton {
    ResourceLocation textures=new ResourceLocation(MOD_ID,"textures/gui/buttons.png");
    public boolean isMouseHover=false;
    public CommonGuiButton(int id, int xPos, int yPos, int width, int height, String displayText) {
        super(id, xPos, yPos,width,height,displayText);
    }
    public boolean isMouseOverButton(int mouseX, int mouseY) {
        return mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
    }
    public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_) {
        if (this.visible) {
            GL11.glPushMatrix();
            p_146112_1_.getTextureManager().bindTexture(textures);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(xPosition, yPosition, 26, 0, 6, height);
            GL11.glPopMatrix();
        }
    }
}
