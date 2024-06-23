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

package cn.kuzuanpa.ktfruaddon.client.gui.button.DysonSphere;

import cn.kuzuanpa.ktfruaddon.client.gui.button.CommonGuiButton;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class StarLight extends CommonGuiButton {
    ResourceLocation textures;
    public float[] color;
    public int u=0,v=0;
    public StarLight(int id, int xPos, int yPos, ResourceLocation texture, int width, int height, int u, int v, String displayText, float[] color) {
        super(id, xPos, yPos,width,height,displayText);
        this.color=color;
        this.u=u;
        this.v=v;
        this.textures=texture;
    }
    public StarLight(int id, int xPos, int yPos, ResourceLocation texture, int width, int height, int u, int v, String displayText) {
        super(id, xPos, yPos,width,height,displayText);
        this.color=new float[]{1.0F,1.0F,1.0F,1.0F};
        this.u=u;
        this.v=v;
    }
    public void drawButton(Minecraft mc, int p_146112_2_, int p_146112_3_) {
        if (this.visible) {
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            mc.getTextureManager().bindTexture(textures);
            GL11.glColor4f(color[0],color[1],color[2],1.0F);
            this.drawTexturedModalRect(xPosition, yPosition, u, v, width, height);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }
    }
}
