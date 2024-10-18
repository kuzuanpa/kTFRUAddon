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

import cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.storage.LiquidBattery;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.stream.Collectors;

public class TESRLiquidBattery extends TileEntitySpecialRenderer {

    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float f) {
        if(! (t instanceof LiquidBattery))return;
        LiquidBattery tile = (LiquidBattery)t;
        if(!tile.mStructureOkay)return;

        GL11.glPushMatrix();

        GL11.glTranslated(x, y, z );
        int bright = 15 << 20 | 15 << 4;
        int brightX = bright % 65536;
        int brightY = bright / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);

        float finalMaxYCoord = tile.liquidYLevelRender *1F/100F;
        List<Vec3> processedList = tile.spaceListForTESR.stream().map(coord -> Vec3.createVectorHelper(coord.x,Math.min(coord.y, finalMaxYCoord),coord.z)).collect(Collectors.toList());
        processedList.forEach(vec -> {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) (vec.xCoord - tile.xCoord), (float) (vec.yCoord -tile.yCoord), (float) (vec.zCoord -tile.zCoord));
            try {
                IIcon icon = tile.mTank.getFluid().getFluid().getIcon();

                bindTexture(new ResourceLocation("textures/atlas/blocks.png"));

                GL11.glColor4f(1,1,1,1);
                Tessellator tessellator = Tessellator.instance;
                tessellator.startDrawingQuads();
                tessellator.addVertexWithUV(0, 1.001, 0, icon.getMinU(), icon.getMaxV());
                tessellator.addVertexWithUV(0, 1.001, 1, icon.getMaxU(), icon.getMaxV());
                tessellator.addVertexWithUV(1, 1.001, 1, icon.getMaxU(), icon.getMinV());
                tessellator.addVertexWithUV(1, 1.001, 0, icon.getMinU(), icon.getMinV());
                tessellator.draw();
            }catch (Exception e){}
            GL11.glPopMatrix();

        });

        GL11.glPopMatrix();
    }
}