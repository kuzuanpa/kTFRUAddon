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

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class FxRenderBlockOutline {
    public FxRenderBlockOutline(){
    }
    private static class blockOutlineToRender{
        protected blockOutlineToRender(ChunkCoordinates pos, int color, float thickness){
            this.pos=pos;
            this.color=color;
            this.thickness=thickness;
        }
        protected ChunkCoordinates pos;
        protected int color;
        protected float thickness;
    }
    public static ArrayList<blockOutlineToRender> blockOutlineToRenderArrayList = new ArrayList<blockOutlineToRender>();

    /**return: true when added,false when duplicated**/
    public static boolean addBlockOutlineToRender(@NotNull ChunkCoordinates pos, int color, float thickness){
        for (blockOutlineToRender blockOutline : blockOutlineToRenderArrayList)if (Objects.equals(blockOutline, new blockOutlineToRender(pos, color, thickness))||blockOutline.pos.equals(pos))return false;
        blockOutlineToRenderArrayList.add(new blockOutlineToRender(pos,color,thickness));
        return true;
    }

    public static void removeBlockOutlineToRender(@NotNull ChunkCoordinates pos){
        blockOutlineToRenderArrayList.removeIf(blockOutline -> blockOutline.pos.equals(pos));
    }

    @SubscribeEvent
    public void onWorldRenderLast(RenderWorldLastEvent event) {
        try {
            blockOutlineToRenderArrayList.forEach(blockOutline -> renderBlockOutlineAt(blockOutline.pos, blockOutline.color, blockOutline.thickness));
        }catch (Throwable ignored){}
    }
    /**Copied from Botania**/
    public void renderBlockOutlineAt(ChunkCoordinates pos, int color, float thickness) {
        GL11.glPushMatrix();
        GL11.glPushAttrib(2896);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glPushMatrix();
        GL11.glTranslated((double)pos.posX - RenderManager.renderPosX, (double)pos.posY - RenderManager.renderPosY, (double)pos.posZ - RenderManager.renderPosZ + 1.0);
        Color colorRGB = new Color(color);
        GL11.glColor4ub((byte)colorRGB.getRed(), (byte)colorRGB.getGreen(), (byte)colorRGB.getBlue(), (byte)-1);
        World world = Minecraft.getMinecraft().theWorld;
        Block block = world.getBlock(pos.posX, pos.posY, pos.posZ);
        if (block != null) {
            AxisAlignedBB axis= block.getSelectedBoundingBoxFromPool(world, pos.posX, pos.posY, pos.posZ);

            if (axis != null) {
                axis.minX -= (double)pos.posX;
                axis.maxX -= (double)pos.posX;
                axis.minY -= (double)pos.posY;
                axis.maxY -= (double)pos.posY;
                axis.minZ -= (double)(pos.posZ + 1);
                axis.maxZ -= (double)(pos.posZ + 1);
                GL11.glLineWidth(thickness);
                GL11.glColor4ub((byte)colorRGB.getRed(), (byte)colorRGB.getGreen(), (byte)colorRGB.getBlue(), (byte)64);
                this.renderBlockOutline(axis);
            }
        }

        GL11.glPopMatrix();
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }
    /**Copied from Botania**/
    private void renderBlockOutline(AxisAlignedBB aabb) {
        Tessellator tessellator = Tessellator.instance;
        double ix = aabb.minX;
        double iy = aabb.minY;
        double iz = aabb.minZ;
        double ax = aabb.maxX;
        double ay = aabb.maxY;
        double az = aabb.maxZ;
        tessellator.startDrawing(1);
        tessellator.addVertex(ix, iy, iz);
        tessellator.addVertex(ix, ay, iz);
        tessellator.addVertex(ix, ay, iz);
        tessellator.addVertex(ax, ay, iz);
        tessellator.addVertex(ax, ay, iz);
        tessellator.addVertex(ax, iy, iz);
        tessellator.addVertex(ax, iy, iz);
        tessellator.addVertex(ix, iy, iz);
        tessellator.addVertex(ix, iy, az);
        tessellator.addVertex(ix, ay, az);
        tessellator.addVertex(ix, iy, az);
        tessellator.addVertex(ax, iy, az);
        tessellator.addVertex(ax, iy, az);
        tessellator.addVertex(ax, ay, az);
        tessellator.addVertex(ix, ay, az);
        tessellator.addVertex(ax, ay, az);
        tessellator.addVertex(ix, iy, iz);
        tessellator.addVertex(ix, iy, az);
        tessellator.addVertex(ix, ay, iz);
        tessellator.addVertex(ix, ay, az);
        tessellator.addVertex(ax, iy, iz);
        tessellator.addVertex(ax, iy, az);
        tessellator.addVertex(ax, ay, iz);
        tessellator.addVertex(ax, ay, az);
        tessellator.draw();
    }
}
