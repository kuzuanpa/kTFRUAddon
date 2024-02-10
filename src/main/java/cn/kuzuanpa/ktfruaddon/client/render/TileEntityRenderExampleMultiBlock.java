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

import cn.kuzuanpa.ktfruaddon.tile.multiblock.base.ModelRenderBaseMultiBlockMachine;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import static net.minecraftforge.common.util.ForgeDirection.VALID_DIRECTIONS;

public class TileEntityRenderExampleMultiBlock extends TileEntitySpecialRenderer {
    IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("ktfruaddon:models/test.obj"));

    ResourceLocation texture = new ResourceLocation("ktfruaddon:textures/models/lathe.png");

    private static int bodyList;

    public TileEntityRenderExampleMultiBlock() {
        GL11.glNewList(bodyList = GL11.glGenLists(1), GL11.GL_COMPILE);
        model.renderPart("test");
        GL11.glEndList();
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x,
                                   double y, double z, float f) {
        ModelRenderBaseMultiBlockMachine multiBlockTile = (ModelRenderBaseMultiBlockMachine)tile;
        boolean rendNow =false;

        GL11.glPushMatrix();

        //Initial setup
        int bright = tile.getWorldObj().getLightBrightnessForSkyBlocks(tile.xCoord, tile.yCoord + 1, tile.zCoord,0);
        int brightX = bright % 65536;
        int brightY = bright / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);

        //Rotate and move the model into position
        GL11.glTranslated(x + .5f, y, z + 0.5f);
        ForgeDirection front = VALID_DIRECTIONS[multiBlockTile.mFacing];
        GL11.glRotatef((front.offsetX == 1 ? 180 : 0) + front.offsetZ*90f, 0, 1, 0);
        GL11.glTranslated(-.5f, -60f, -2.5f);

        bindTexture(texture);
        GL11.glCallList(bodyList);

        ItemStack outputStack;
        if(multiBlockTile.mActive) {

            float progress = multiBlockTile.mProgress/(float)multiBlockTile.mMaxProgress;

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
