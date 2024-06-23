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
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class DysonNodeButton extends CommonGuiButton {
    public int  nodeX,nodeY,nodeLevel,nodeType,nodeBuildingProgress,screenWidth,screenHeight;
    ResourceLocation textures=new ResourceLocation(MOD_ID,"textures/gui/DysonSphere/nodes.png");
    public DysonNodeButton(int id, int xPos, int yPos,int nodeX,int nodeY,int nodeLevel,int nodeType,int nodeBuildingProgress,int screenWidth,int screenHeight){
        super(id, xPos, yPos,8,8,"");
        this.nodeX=nodeX;
        this.nodeY=nodeY;
        this.nodeLevel=nodeLevel;
        this.nodeType=nodeType;
        this.nodeBuildingProgress=nodeBuildingProgress;
        this.screenHeight=screenHeight;
        this.screenWidth=screenWidth;
    }
    public void drawButton(Minecraft mc, int p_146112_2_, int p_146112_3_) {
        if (this.visible) {
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            mc.getTextureManager().bindTexture(textures);
            GL11.glTranslatef(width*0.25F+xPosition,height*0.25F+yPosition,0);
            GL11.glScalef(0.25F,0.25F,1);
            GL11.glTranslatef(-width-xPosition,-height-yPosition,0);

            ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
            // compute window size from scaled width & height
            int windowWidth = (int) (screenWidth / (resolution.getScaledWidth() * 1.0) * mc.displayWidth);
            int windowHeight = (int) (screenHeight / (resolution.getScaledHeight() * 1.0) * mc.displayHeight);
            GL11.glScissor((int) (windowWidth*0.6), (int) (windowHeight*0.26), (int) windowWidth,windowHeight);

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
            this.drawTexturedModalRect(xPosition, yPosition, nodeLevel*32 ,nodeType*32, 32, 32);
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }
    }
}
