/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.

 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */


package cn.kuzuanpa.ktfruaddon.client.render;

import cn.kuzuanpa.ktfruaddon.tile.multiblock.machine.ElectromagnetCrucible;
import gregapi.oredict.OreDictMaterial;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import java.util.Map;

import static gregapi.data.CS.PX_P;
import static net.minecraftforge.common.util.ForgeDirection.VALID_DIRECTIONS;
import static org.lwjgl.opengl.GL11.*;

public class TESRDumyCrucible extends TileEntitySpecialRenderer {
    ResourceLocation texture = new ResourceLocation("ktfruaddon:textures/DummyCrucible.png");

    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float f) {
        ElectromagnetCrucible tile = (ElectromagnetCrucible)t;
        boolean rendNow =false;
        for (byte i = 1; i < 7; i++) {
            if (tile.shouldSideBeRendered(i)) rendNow = true;
        }
        if (!rendNow||!tile.checkStructure(false))return;
        Map<Short,Short> map = tile.mDisplayContent;
        if(map.isEmpty()) return;

        GL11.glPushMatrix();

        int bright = 15 << 20 | 15 << 4;
        int brightX = bright % 65536;
        int brightY = bright / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        GL11.glTranslated(x + .5f, y, z + 0.5f);
        ForgeDirection front = VALID_DIRECTIONS[tile.mFacing];
        GL11.glRotatef((front.offsetX == 1 ? 180 : 0) + front.offsetZ*90f, 0, 1, 0);
        GL11.glTranslated(-.501f, 0, -.5f);

        float index = 0.0f;
        bindTexture(texture);
        for (Map.Entry<Short, Short> entry : map.entrySet()) {
            OreDictMaterial mat = OreDictMaterial.get(entry.getKey());
            if(mat == null) continue;
            GL11.glColor4ub( (byte)mat.mRGBaSolid[0], (byte)mat.mRGBaSolid[1], (byte)mat.mRGBaSolid[2], (byte)mat.mRGBaSolid[3]);
            float height = (Math.abs(entry.getValue())/32000F)*PX_P[9];
            drawTextureRect(Tessellator.instance, PX_P[8], index + PX_P[4], entry.getValue()>0? 0:0/*todo: different soild and molten*/, 0, -PX_P[4], height);
            index+=height;
            GL11.glColor4f(1f, 1f, 1f, 1f);
        }
        GL11.glPopMatrix();
    }

    public void drawTextureRect(Tessellator tessellator, float x, float y, float u, float v, float width, float height){
        final float f  = 0.00390625F;
        final float f1 = 0.00390625F;
        tessellator.startDrawingQuads();
        tessellator.setBrightness(15 << 20);
        tessellator.addVertexWithUV(0, y + height, x, (u * f), (v + height) * f1);
        tessellator.addVertexWithUV(0, y + height, x + width, (u + width) * f, (v + height) * f1);
        tessellator.addVertexWithUV(0, y, x + width, (u + width) * f, v * f1);
        tessellator.addVertexWithUV(0, y, x, (u * f), v * f1);
        tessellator.draw();
    }
}
