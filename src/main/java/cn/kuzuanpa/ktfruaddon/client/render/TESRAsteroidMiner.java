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

import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.miner.AsteroidMiner;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import static net.minecraftforge.common.util.ForgeDirection.VALID_DIRECTIONS;
import static org.lwjgl.opengl.GL11.*;

public class TESRAsteroidMiner extends TileEntitySpecialRenderer {
    IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("ktfruaddon:models/asteroid_mine_rocket.obj"));
    ResourceLocation texture = new ResourceLocation("ktfruaddon:textures/specialRend/TFCPresser.png");

    private static int bodyList;

    public TESRAsteroidMiner() {
        bodyList = GL11.glGenLists(1);
        GL11.glNewList(bodyList, GL11.GL_COMPILE);
        model.renderPart("cube");
        GL11.glEndList();
    }


    @Override
    public void renderTileEntityAt(TileEntity til, double x,
                                   double y, double z, float f) {
        if (! (til instanceof AsteroidMiner )) return;
        AsteroidMiner tile = (AsteroidMiner)til;
        if(!tile.mStructureOkay || (!tile.clientIsSlotHas && tile.clientRocketSendTimer == 0) || tile.clientRocketSendTimer > 800)return;
        GL11.glPushMatrix();

        //Initial setup
        int bright = tile.getWorldObj().getLightBrightnessForSkyBlocks(tile.xCoord +utils.getXOffset(tile.mFacing,-1,0), tile.yCoord , tile.zCoord + utils.getZOffset(tile.mFacing,-1,0),0);
        int brightX = bright % 65536;
        int brightY = bright / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);

        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        //Rotate and move the model into position
        GL11.glTranslatef((float) utils.getXOffset(tile.mFacing,0.5D,2.5D),0,(float)utils.getZOffset(tile.mFacing,0.5D,2.5D));
        GL11.glTranslated(x, y, z );
        GL11.glTranslatef(0.5f, (float) (tile.clientRocketSendTimer>0?Math.pow(tile.clientRocketSendTimer/80F, 3F): tile.clientRocketSendTimer<0?Math.pow(10+tile.clientRocketSendTimer/80F, 2.4F):0), 0.5f);
        if(tile.clientRocketSendTimer > 0) tile.clientRocketSendTimer ++;
        if(tile.clientRocketSendTimer < 0 && tile.clientRocketSendTimer > -800) tile.clientRocketSendTimer --;
        ForgeDirection front = VALID_DIRECTIONS[tile.mFacing];
        GL11.glRotatef((front.offsetX == 1 ? 180 : 0) + front.offsetZ*90f, 0, 1, 0);
        GL11.glRotatef(-90,0,1,0);

        bindTexture(texture);
        GL11.glCallList(bodyList);

        GL11.glColor4f(1,1,1,1);

        GL11.glPopMatrix();
    }
}
