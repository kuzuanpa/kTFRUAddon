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

import cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.storage.LiquidBattery;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;

public class TESRLiquidBattery extends TileEntitySpecialRenderer {

    static final HashMap<IIcon, Integer> iconGLListIds = new HashMap<>();
    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float f) {
        if(! (t instanceof LiquidBattery))return;
        LiquidBattery tile = (LiquidBattery)t;
        if(!tile.mStructureOkay|| tile.disableTESR)return;
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
            tessellator.setNormal(0,1,0);
            tessellator.addVertexWithUV(0, 0.999, 0, icon.getMinU(), icon.getMaxV());
            tessellator.addVertexWithUV(0, 0.999, 1, icon.getMaxU(), icon.getMaxV());
            tessellator.addVertexWithUV(1, 0.999, 1, icon.getMaxU(), icon.getMinV());
            tessellator.addVertexWithUV(1, 0.999, 0, icon.getMinU(), icon.getMinV());
            tessellator.setNormal(0,-1,0);
            tessellator.addVertexWithUV(1, 0.999, 0, icon.getMinU(), icon.getMaxV());
            tessellator.addVertexWithUV(1, 0.999, 1, icon.getMaxU(), icon.getMaxV());
            tessellator.addVertexWithUV(0, 0.999, 1, icon.getMaxU(), icon.getMinV());
            tessellator.addVertexWithUV(0, 0.999, 0, icon.getMinU(), icon.getMinV());
            tessellator.draw();
            GL11.glEndList();
            iconGLListIds.put(icon,id);
        }

        GL11.glPushMatrix();

        GL11.glTranslated(x, y, z );
        glEnable(GL_BLEND);
        glEnable(GL_LIGHTING);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        float finalMaxYCoord = tile.liquidYLevelRender *1F/100F;

        bindTexture(new ResourceLocation("textures/atlas/blocks.png"));

        int glListID= iconGLListIds.get(icon);


        if(tile.layeredTESRRenderSpace.get((short) Math.ceil(finalMaxYCoord)) != null)tile.layeredTESRRenderSpace.get((short) Math.ceil(finalMaxYCoord)).forEach(vec -> {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) (vec.x - tile.xCoord),  finalMaxYCoord - tile.yCoord, (float) (vec.z -tile.zCoord));
            GL11.glCallList(glListID);
            GL11.glPopMatrix();
        });

        GL11.glPopMatrix();
    }
}