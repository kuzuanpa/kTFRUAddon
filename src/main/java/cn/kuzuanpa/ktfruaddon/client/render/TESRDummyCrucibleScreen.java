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

import cn.kuzuanpa.ktfruaddon.tile.multiblock.parts.DummyCrucibleScreen;
import gregapi.render.IIconContainer;
import gregapi.render.TextureSet;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import static net.minecraftforge.common.util.ForgeDirection.VALID_DIRECTIONS;
import static org.lwjgl.opengl.GL11.*;

public class TESRDummyCrucibleScreen extends TileEntitySpecialRenderer {
    int bodyList = 0;
    public TESRDummyCrucibleScreen(){
    }
    @Override
    public void renderTileEntityAt(TileEntity til, double x, double y, double z, float f) {
        if (! (til instanceof DummyCrucibleScreen)) return;
        DummyCrucibleScreen tile = (DummyCrucibleScreen)til;
        GL11.glPushMatrix();
        //Initial setup
        int bright = tile.getWorldObj()==null? 15728656 : tile.getWorldObj().getLightBrightnessForSkyBlocks(tile.xCoord, tile.yCoord, tile.zCoord,0);
        int brightX = bright % 65536;
        int brightY = bright / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        GL11.glTranslated(x + .5f, y, z + 0.5f);
        ForgeDirection front = VALID_DIRECTIONS[tile.mFacing];
        GL11.glRotatef((front.offsetX == 1 ? 180 : 0) + front.offsetZ*90f, 0, 1, 0);
        GL11.glTranslated(-.501f, 0, -.5f);

        IIconContainer container = TextureSet.SET_METALLIC[1].mList.get(tile.getCreateTo().mIconIndexItem);
        bindTexture(container.getTextureFile());
        IIcon icon = container.getIcon(0);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        if(tile.clientMat != null)GL11.glColor4ub((byte) tile.clientMat.mRGBaSolid[0], (byte) tile.clientMat.mRGBaSolid[1], (byte) tile.clientMat.mRGBaSolid[2], (byte) tile.clientMat.mRGBaSolid[3]);
        tessellator.setNormal(0,1,0);
        tessellator.addVertexWithUV(0, 0.2, 0.2, icon.getMinU(), icon.getMaxV());
        tessellator.addVertexWithUV(0, 0.2, 0.8, icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV(0, 0.8, 0.8, icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV(0, 0.8, 0.2, icon.getMinU(), icon.getMinV());
        tessellator.draw();

        GL11.glColor4f(1f, 1f, 1f, 1f);

        bindTexture(TextureMap.locationBlocksTexture);
        GL11.glPopMatrix();
    }
}
