/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.gui.button.DysonSphere;

import cn.kuzuanpa.ktfruaddon.gui.button.CommonGuiButton;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class StarButton extends CommonGuiButton {
    ResourceLocation textures=new ResourceLocation(MOD_ID,"textures/gui/DysonSphere/star.png");
    public int starID;
    public float[] color;
    public StarButton(int id, int xPos, int yPos, String displayText,float[] color,int starID) {
        super(id, xPos, yPos,64,64,displayText);
        this.color=color;
        this.starID=starID;
    }
    public void drawButton(Minecraft mc, int p_146112_2_, int p_146112_3_) {
        if (this.visible) {
            GL11.glPushMatrix();
            this.drawString(mc.fontRenderer,this.displayString,this.xPosition+8,this.yPosition,0xFFFFFF);
            GL11.glEnable(GL11.GL_BLEND);
            mc.getTextureManager().bindTexture(textures);
            GL11.glColor4f(color[0],color[1],color[2],0.9F);
            GL11.glTranslatef(width*0.25F,height*0.25F+yPosition,0);
            GL11.glScalef(0.25F,0.25F,0);

            GL11.glTranslatef(-width,-height-yPosition,0);
            this.drawTexturedModalRect(xPosition, yPosition, 0, 0, 256, 256);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }
    }
}
