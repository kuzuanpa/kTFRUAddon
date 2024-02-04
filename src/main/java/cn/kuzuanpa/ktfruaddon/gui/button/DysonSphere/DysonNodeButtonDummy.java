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
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class DysonNodeButtonDummy extends CommonGuiButton {
    public int  nodeLevel,nodeType, nodeX, nodeY,screenWidth,screenHeight;
    public boolean isAvailable;
    ResourceLocation textures=new ResourceLocation(MOD_ID,"textures/gui/DysonSphere/nodes.png");
    public DysonNodeButtonDummy(int id, int xPos, int yPos, int nodeLevel, int nodeType,boolean isAvailable,int nodeX,int nodeY,int screenWidth,int screenHeight){
        super(id, xPos, yPos,16,16,"");
        this.nodeLevel=nodeLevel;
        this.nodeType=nodeType;
        this.isAvailable=isAvailable;
        this.nodeX=nodeX;
        this.nodeY=nodeY;
        this.screenHeight=screenHeight;
        this.screenWidth=screenWidth;
    }
    public void drawButton(Minecraft mc, int p_146112_2_, int p_146112_3_) {
        if (this.visible) {
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            mc.getTextureManager().bindTexture(textures);
            GL11.glTranslatef(width*0.5F+xPosition,height*0.5F+yPosition,0);
            GL11.glScalef(0.5F,0.5F,1);
            GL11.glTranslatef(-width-xPosition,-height-yPosition,0);

            ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
            // compute window size from scaled width & height
            int windowWidth = (int) (screenWidth / (resolution.getScaledWidth() * 1.0) * mc.displayWidth);
            int windowHeight = (int) (screenHeight / (resolution.getScaledHeight() * 1.0) * mc.displayHeight);
            GL11.glScissor((int) (windowWidth*0.6), (int) (windowHeight*0.26), (int) windowWidth,windowHeight);

            if(isAvailable)GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.9F);
            else GL11.glColor4f(0.6F, 0.6F, 0.6F, 0.5F);
            this.drawTexturedModalRect(xPosition, yPosition, nodeLevel*32 ,nodeType*32, 32, 32);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }
    }
}
