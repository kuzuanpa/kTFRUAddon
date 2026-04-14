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

import cn.kuzuanpa.ktfruaddon.tile.machine.TFCPresser;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.util.ST;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import static net.minecraftforge.common.util.ForgeDirection.VALID_DIRECTIONS;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;

public class TESRTFCPresser extends TileEntitySpecialRenderer {
    IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("ktfruaddon:models/tfc_presser.obj"));
    ResourceLocation texture = new ResourceLocation("ktfruaddon:textures/specialRend/TFCPresser.png");

    private static int bodyLists;

    public TESRTFCPresser() {
        bodyLists = GL11.glGenLists(2);
        GL11.glNewList(bodyLists, GL11.GL_COMPILE);
        model.renderPart("body");
        GL11.glEndList();
        GL11.glNewList(bodyLists+1, GL11.GL_COMPILE);
        model.renderPart("rod");
        GL11.glEndList();
    }


    @Override
    public void renderTileEntityAt(TileEntity til, double x, double y, double z, float f) {
        if (! (til instanceof TFCPresser)) return;
        TFCPresser tile = (TFCPresser)til;
        GL11.glPushMatrix();
        glEnable(GL_BLEND);
        glEnable(GL_LIGHTING);
        GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        //Rotate and move the model into position
        GL11.glTranslated(x + .5f, y+ .6f, z + .5f);
        ForgeDirection front = VALID_DIRECTIONS[tile.mFacing];
        GL11.glRotatef((front.offsetX == 1 ? 180 : 0) + front.offsetZ*90f, 0, 1, 0);

        bindTexture(texture);
        GL11.glCallList(bodyLists);

        if(tile.raisingTimer > -40){
            float raiseAmountToGo = (float) Math.floor(tile.raisingTimer + 1.0f);
            float offset = raiseAmountToGo - tile.TESRRasingTimer;
            float timer = ((tile.TESRRasingTimer + offset/8.0f)+42)/40.0f;
            tile.TESRRasingTimer = (tile.TESRRasingTimer + offset/8.0f);
            float factorDown = (float) Math.max(0.0f, Math.min(1.0f, 1-Math.pow(timer, 4f))) * .5f;
            float factorUp = Math.max(0.0f, Math.min(1.0f, timer* 1.3f - 1.6f)) * .5f;
            GL11.glTranslated(0, timer>1?factorUp-0.5 :factorDown - 0.5, 0);
        }else tile.TESRRasingTimer = tile.raisingTimer;
        if(tile.raisingTimer == 1 && !tile.displayedParticles){
            tile.getWorld().spawnParticle("iconcrack_"+ ST.id(OP.ingotDouble.mat(MT.Fe,0))+"_"+ ST.meta(OP.ingotDouble.mat(MT.Fe,0)),tile.xCoord + 0.5f, tile.yCoord - 0.5f, tile.zCoord + 0.5f, tile.rng(10)/50f - 0.1f,0.1f,tile.rng(10)/50f - 0.1f);
            tile.displayedParticles = true;
        }

        GL11.glCallList(bodyLists+1);

        GL11.glColor4f(1f, 1f, 1f, 1f);

        GL11.glPopMatrix();
    }
}
