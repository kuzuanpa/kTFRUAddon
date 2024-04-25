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

package cn.kuzuanpa.ktfruaddon.client.render;

import cn.kuzuanpa.ktfruaddon.tile.multiblock.model.CNCMachine3;
import cn.kuzuanpa.ktfruaddon.tile.util.utils;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import static net.minecraftforge.common.util.ForgeDirection.VALID_DIRECTIONS;

public class TESRCNCMachine3 extends TileEntitySpecialRenderer {
    IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("ktfruaddon:models/CNCMachine3.obj"));
    ResourceLocation texture = new ResourceLocation("ktfruaddon:textures/model/CNCMachine3.png");

    private static int bodyList;

    public TESRCNCMachine3() {
        bodyList = GL11.glGenLists(3);
        GL11.glNewList(bodyList, GL11.GL_COMPILE);
        model.renderPart("base");
        GL11.glEndList();
        GL11.glNewList(bodyList+1, GL11.GL_COMPILE);
        model.renderPart("head");
        GL11.glEndList();
        GL11.glNewList(bodyList+2, GL11.GL_COMPILE);
        model.renderPart("processingItem");
        GL11.glEndList();
    }

    @Override
    public void renderTileEntityAt(TileEntity t, double x,
                                   double y, double z, float f) {
        if(! (t instanceof CNCMachine3))return;
        CNCMachine3 tile = (CNCMachine3)t;

        GL11.glPushMatrix();

        //Initial setup
        int bright = tile.getWorldObj().getLightBrightnessForSkyBlocks(tile.xCoord, tile.yCoord + 1, tile.zCoord,0);
        int brightX = bright % 65536;
        int brightY = bright / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);


        //Rotate and move the model into position
        GL11.glTranslatef((float) utils.getXOffset(tile.mFacing, tile.xCoord, tile.zCoord,-1.5D,1D),0,(float)utils.getZOffset(tile.mFacing, tile.xCoord, tile.zCoord,-1.5D,1D));
        GL11.glTranslated(x, y, z );
        GL11.glTranslatef(0.5f, 0, 0.5f);
        ForgeDirection front = VALID_DIRECTIONS[tile.mFacing];
        GL11.glRotatef((front.offsetX == 1 ? 180 : 0) + front.offsetZ*90f, 0, 1, 0);
        GL11.glRotatef(-90,0,1,0);

        bindTexture(texture);
        GL11.glCallList(bodyList);

        if(!tile.invempty()||tile.mActive) GL11.glCallList(bodyList+2);

        GL11.glPushMatrix();


        GL11.glCallList(bodyList+1);
        if(tile.getRandomNumber(10)==0)tile.getWorldObj().spawnParticle("dripWater",tile.xCoord-0.95,tile.yCoord+2,tile.zCoord+1.6,0,0,0);


        GL11.glPopMatrix();

        if(tile.mActive) {
            float progress = tile.mProgress/(float)tile.mMaxProgress;

            bindTexture(texture);


            GL11.glPushMatrix();
            if(progress < 0.95f)
                GL11.glTranslatef(0f, 0f, progress/.95f);
            else
                GL11.glTranslatef(0f, 0f, (1 - progress)/.05f);

            model.renderOnly("Tray");
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            GL11.glTranslatef(.5f, 1.5625f, 0f);
            GL11.glRotatef(progress*1500, 0, 0, 1);
            model.renderOnly("Cylinder");

            int color;
            //Check for rare bug when outputs is null, usually occurs if player opens machine within 1st tick
            //if(multiBlockTile.getOutputs() != null && (outputStack = multiBlockTile.getOutputs().get(0)) != null)
            //    color = MaterialRegistry.getColorFromItemMaterial(outputStack);
            //else
                color = 0;

            GL11.glColor3d((0xff & color >> 16)/256f, (0xff & color >> 8)/256f , (color & 0xff)/256f);

            model.renderOnly("rod");
            GL11.glPopMatrix();

            GL11.glColor4f(1f, 1f, 1f, 1f);
        }
        else {
            bindTexture(texture);
                model.renderPart("body");

            model.renderPart("Tray");
            //model.renderAllExcept("rod", "Cylinder");
        }
        GL11.glPopMatrix();
    }
}
