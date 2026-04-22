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

import cn.kuzuanpa.ktfruaddon.tile.energy.generator.WaterMill;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import static net.minecraftforge.common.util.ForgeDirection.VALID_DIRECTIONS;
import static org.lwjgl.opengl.GL11.*;

public class TESRWaterMill extends TileEntitySpecialRenderer {
    /**this model is copied from Create under MIT license**/
    IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("ktfruaddon:models/water_wheel.obj"));
    ResourceLocation texture = new ResourceLocation("ktfruaddon:textures/specialRend/TFCBrichPlank.png");

    private static int bodyLists;

    public TESRWaterMill() {
        bodyLists = GL11.glGenLists(1);
        GL11.glNewList(bodyLists, GL11.GL_COMPILE);
        model.renderPart("Waterwheel_Big_Axle.007");
        GL11.glEndList();
    }


    @Override
    public void renderTileEntityAt(TileEntity til, double x, double y, double z, float f) {
        if (! (til instanceof WaterMill)) return;
        WaterMill tile = (WaterMill)til;
        GL11.glPushMatrix();
        glEnable(GL_BLEND);
        glEnable(GL_LIGHTING);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);


        //Rotate and move the model into position
        GL11.glTranslated(x, y, z );
        GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        ForgeDirection front = VALID_DIRECTIONS[tile.mFacing];
        GL11.glRotatef(90f-front.offsetY*90f, 1, 0, 0);
        GL11.glRotatef(front.offsetX*-90f, 0, 0, 1);

        if(tile.rotateTorque != 0 )GL11.glRotatef(System.currentTimeMillis()%36000/10f, 0, tile.rotateTorque, 0);

        bindTexture(texture);
        GL11.glCallList(bodyLists);

        GL11.glColor4f(1f, 1f, 1f, 1f);

        bindTexture(TextureMap.locationBlocksTexture);
        GL11.glPopMatrix();
    }
}
