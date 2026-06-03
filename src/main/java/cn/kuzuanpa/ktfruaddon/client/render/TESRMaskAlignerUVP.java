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

import cn.kuzuanpa.ktfruaddon.tile.multiblock.machine.MaskAlignerUVPlus;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import static net.minecraftforge.common.util.ForgeDirection.VALID_DIRECTIONS;
import static org.lwjgl.opengl.GL11.*;

public class TESRMaskAlignerUVP extends TileEntitySpecialRenderer {
    IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("ktfruaddon:models/mask_aligner_2.obj"));
    ResourceLocation texture = new ResourceLocation("ktfruaddon:textures/specialRend/mask_aligner_2.png");

    private static int bodyLists;

    public TESRMaskAlignerUVP() {
        bodyLists = GL11.glGenLists(6);
        GL11.glNewList(bodyLists, GL11.GL_COMPILE);
        model.renderPart("base");
        GL11.glEndList();
        GL11.glNewList(bodyLists+1, GL11.GL_COMPILE);
        model.renderPart("light");
        GL11.glEndList();
        GL11.glNewList(bodyLists+2, GL11.GL_COMPILE);
        model.renderPart("model");
        GL11.glEndList();
        GL11.glNewList(bodyLists+3, GL11.GL_COMPILE);
        model.renderPart("wafer");
        GL11.glEndList();
        GL11.glNewList(bodyLists+4, GL11.GL_COMPILE);
        model.renderPart("arm.x");
        GL11.glEndList();
        GL11.glNewList(bodyLists+5, GL11.GL_COMPILE);
        model.renderPart("arm.z");
        GL11.glEndList();
    }


    @Override
    public void renderTileEntityAt(TileEntity til, double x, double y, double z, float f) {
        if (! (til instanceof MaskAlignerUVPlus)) return;
        MaskAlignerUVPlus tile = (MaskAlignerUVPlus)til;
        GL11.glPushMatrix();
        glEnable(GL_BLEND);
        glEnable(GL_LIGHTING);
        GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        //Rotate and move the model into position
        GL11.glTranslated(x + .5f, y, z + .5f);
        ForgeDirection front = VALID_DIRECTIONS[tile.mFacing];
        GL11.glRotatef((front.offsetX == 1 ? 180 : 0) + front.offsetZ*90f, 0, 1, 0);
        GL11.glTranslated(-0.5f, 0,+1.5f);

        bindTexture(texture);
        GL11.glCallList(bodyLists);

        int timer = (int)tile.getTimer() % 512 /8;
        float xToGo= (float) -(0.05F + Math.floor(timer / 8F)/8F);
        float zToGo= -(0.05F + (timer % 8)/8F);

        GL11.glPushMatrix();
        tile.clientY = tile.clientY +( (zToGo - tile.clientY) /8.0F);
        GL11.glTranslatef(tile.clientY,0,0);
        GL11.glCallList(bodyLists+5);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        tile.clientX = tile.clientX +( (xToGo - tile.clientX) /8.0F);
        GL11.glTranslatef(0,0,tile.clientX);
        GL11.glCallList(bodyLists+4);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(tile.clientY,0,tile.clientX);
        GL11.glCallList(bodyLists+3);
        GL11.glPopMatrix();

        GL11.glCallList(bodyLists+1);
        GL11.glCallList(bodyLists+2);

        GL11.glColor4f(1f, 1f, 1f, 1f);

        GL11.glPopMatrix();
    }
}
