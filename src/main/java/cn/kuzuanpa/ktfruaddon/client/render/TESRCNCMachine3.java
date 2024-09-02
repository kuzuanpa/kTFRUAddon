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
        if(!tile.mStructureOkay)return;
        GL11.glPushMatrix();

        //Initial setup
        int bright = tile.getWorldObj().getLightBrightnessForSkyBlocks(tile.xCoord, tile.yCoord + 2, tile.zCoord,0);
        int brightX = bright % 65536;
        int brightY = bright / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);


        //Rotate and move the model into position
        GL11.glTranslatef((float) utils.getXOffset(tile.mFacing,-1.5D,1D),0,(float)utils.getZOffset(tile.mFacing,-1.5D,1D));
        GL11.glTranslated(x, y, z );
        GL11.glTranslatef(0.5f, 0, 0.5f);
        ForgeDirection front = VALID_DIRECTIONS[tile.mFacing];
        GL11.glRotatef((front.offsetX == 1 ? 180 : 0) + front.offsetZ*90f, 0, 1, 0);
        GL11.glRotatef(-90,0,1,0);

        bindTexture(texture);
        GL11.glCallList(bodyList);

        if(!tile.invempty()||tile.mActive) GL11.glCallList(bodyList+2);

        if(tile.mActive||tile.processTime>0) {
            int maxProcessTime = 2000;
            float factor = 0.5F - Math.abs((tile.processTime / (float) tile.proTime) - 0.5F);
            if (tile.processTime > 0)
                GL11.glTranslatef(tile.headMoveToX / 10000F * factor, (Math.max(8 * Math.abs((tile.processTime / (float) tile.proTime) - 0.5F), 3.21F) - 4F), (tile.headMoveToZ / 10000F * factor));

            if (tile.processTime-- < -100) {
                tile.processTime = tile.rng(maxProcessTime / 2) + maxProcessTime / 2;
                tile.proTime = tile.processTime;
                tile.headMoveToX = tile.rng(20000);
                tile.headMoveToZ = tile.rng(20000) - 10000;
            }
        }else {
            tile.processTime=0;
        }


        GL11.glCallList(bodyList+1);
        if(tile.processTime>100&&tile.rng(4)==0)tile.getWorldObj().spawnParticle("splash",utils.getRealX(tile.mFacing,tile.xCoord+0.5,1.8,1),tile.yCoord+1.3,utils.getRealZ(tile.mFacing,tile.zCoord+0.5,1.8,1),tile.rng(8)/32F,tile.rng(8)/32F,tile.rng(8)/32F);

        GL11.glColor4f(1,1,1,1);
        GL11.glPopMatrix();
    }
}
