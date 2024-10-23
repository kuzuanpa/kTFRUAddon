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
import codechicken.lib.vec.BlockCoord;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TESRLiquidBattery extends TileEntitySpecialRenderer {

    static final HashMap<IIcon, Integer> iconGLListIds = new HashMap<>();
    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float f) {
        if(! (t instanceof LiquidBattery))return;
        LiquidBattery tile = (LiquidBattery)t;
        if(!tile.mStructureOkay)return;
        IIcon icon = null;

        if(tile.mTank!=null && tile.mTank.getFluid()!=null && tile.mTank.getFluid().getFluid()!=null) try {
            icon = tile.mTank.getFluid().getFluid().getIcon();
        }catch (Exception ignored){}

        if(icon==null)return;

        if(iconGLListIds.get(icon) == null){
            int id = GL11.glGenLists(1);
            GL11.glNewList(id, GL11.GL_COMPILE);
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(0, 1.01, 0, icon.getMinU(), icon.getMaxV());
            tessellator.addVertexWithUV(0, 1.01, 1, icon.getMaxU(), icon.getMaxV());
            tessellator.addVertexWithUV(1, 1.01, 1, icon.getMaxU(), icon.getMinV());
            tessellator.addVertexWithUV(1, 1.01, 0, icon.getMinU(), icon.getMinV());
            tessellator.draw();
            GL11.glEndList();
            iconGLListIds.put(icon,id);
        }

        GL11.glPushMatrix();

        GL11.glTranslated(x, y, z );
        int bright = 15 << 20 | 15 << 4;
        int brightX = bright % 65536;
        int brightY = bright / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);

        float finalMaxYCoord = tile.liquidYLevelRender *1F/100F;

        bindTexture(new ResourceLocation("textures/atlas/blocks.png"));

        int glListID= iconGLListIds.get(icon);

        List<Vec3> processedList = tile.topLayerForTESR.stream().map(coord -> Vec3.createVectorHelper(coord.x,Math.min(coord.y, finalMaxYCoord),coord.z)).filter(vec->tile.spaceListForTESR.contains(new BlockCoord((int)vec.xCoord,(int)Math.ceil(vec.yCoord),(int)vec.zCoord))).collect(Collectors.toList());
        processedList.forEach(vec -> {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) (vec.xCoord - tile.xCoord), (float) (vec.yCoord -tile.yCoord), (float) (vec.zCoord -tile.zCoord));
            GL11.glCallList(glListID);
            GL11.glPopMatrix();
        });

        GL11.glPopMatrix();
    }
}