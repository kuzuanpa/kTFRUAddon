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

import cn.kuzuanpa.ktfruaddon.tile.parts.SunHeaterMirror;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import static net.minecraftforge.common.util.ForgeDirection.VALID_DIRECTIONS;

public class TileEntityRenderSunBoilerMirror extends TileEntitySpecialRenderer {
    IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("ktfruaddon:models/sunboiler/mirror.obj"));
    ResourceLocation texture = new ResourceLocation("ktfruaddon:textures/model/sunboiler/mirror.png");

    private static int bodyList;

    public TileEntityRenderSunBoilerMirror() {
        GL11.glNewList(bodyList = GL11.glGenLists(1), GL11.GL_COMPILE);
        model.renderOnly("base");
        GL11.glEndList();
    }


    @Override
    public void renderTileEntityAt(TileEntity til, double x,
                                   double y, double z, float f) {
        if (! (til instanceof SunHeaterMirror)) return;
        SunHeaterMirror tile = (SunHeaterMirror)til;
        GL11.glPushMatrix();
        //Initial setup
        int bright = tile.getWorldObj().getLightBrightnessForSkyBlocks(tile.xCoord, tile.yCoord + 1, tile.zCoord,0);
        int brightX = bright % 65536;
        int brightY = bright / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);



        //Rotate and move the model into position
        GL11.glTranslated(x, y, z );
        GL11.glTranslatef(0.5f, 0, 0.5f);
        ForgeDirection front = VALID_DIRECTIONS[tile.mFacing];
        GL11.glRotatef((front.offsetX == 1 ? 180 : 0) + front.offsetZ*90f, 0, 1, 0);
            bindTexture(texture);
            model.renderPart("base");

        GL11.glPushMatrix();


            GL11.glRotatef(-tile.rotateHorizontal, 0, 1, 0);

            GL11.glTranslatef(0, 0.99f, 0);
            GL11.glRotatef(tile.rotateVertical, 0, 0, 1);
            GL11.glTranslatef(0, -1.02f, 0);
            model.renderOnly("mirror");
        GL11.glPopMatrix();
        GL11.glPushMatrix();
            GL11.glRotatef(-tile.rotateHorizontal, 0, 1, 0);
            model.renderOnly("rotate");

            int color;


        GL11.glPopMatrix();
        GL11.glColor4f(1f, 1f, 1f, 1f);

        GL11.glPopMatrix();
    }
}
