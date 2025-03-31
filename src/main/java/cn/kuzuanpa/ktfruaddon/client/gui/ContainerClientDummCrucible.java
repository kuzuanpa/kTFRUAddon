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
package cn.kuzuanpa.ktfruaddon.client.gui;

import cn.kuzuanpa.ktfruaddon.tile.multiblock.specialRend.DummyCrucible;
import gregapi.data.LH;
import gregapi.gui.ContainerClientDefault;
import gregapi.oredict.OreDictMaterial;
import gregapi.tileentity.ITileEntityInventoryGUI;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Map;

public class ContainerClientDummCrucible extends ContainerClientDefault {
    public ContainerClientDummCrucible(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, int aGUIID) {
        super(aInventoryPlayer, aTileEntity, aGUIID, "ktfruaddon:textures/gui/DummyCrucible.png");
    }
    ResourceLocation texture = new ResourceLocation("ktfruaddon:textures/gui/DummyCrucible.png");

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        DummyCrucible tile = (DummyCrucible)mContainer.mTileEntity;
        drawMatList(tile, 8, 16, 49, 57, mouseX, mouseY);
        fontRendererObj.drawStringWithShadow(LH.get(LH.TEMPERATURE)+": " , 110, 10, 0xffffff);
        fontRendererObj.drawStringWithShadow(String.format("%.2f", tile.mTemp), 110, 22, 0xffffff);

        mc.getTextureManager().bindTexture(texture);
        drawTextureRect(Tessellator.instance, 110 + 56*(tile.mTemp/tile.mTempMax), 34, 64, 166, 1, 4);

    }
    protected void drawMatList(DummyCrucible tile, int x, int y, int width, float height, int mouseX, int mouseY){
        mouseX -=  (this.width - xSize) / 2;
        mouseY -=  (this.height - ySize) / 2;

        Map<Short,Short> map = tile.mDisplayContent;
        if(map.isEmpty()) return;
        float index = height;
        for (Map.Entry<Short, Short> entry : map.entrySet()) {
            Short matID = entry.getKey();
            Short amount = entry.getValue();
            OreDictMaterial mat = OreDictMaterial.get(matID);
            if(mat == null) continue;
            float h = (Math.abs(amount)/32767F)*height;
            if(mouseY - y < index && mouseY - y>index-h){
                fontRendererObj.drawStringWithShadow(LH.get(mat.mNameInternal), 112, 48, 0xffffff);
                fontRendererObj.drawStringWithShadow((Math.abs(amount)/327.67F) + "%" + (mat.mMeltingPoint < tile.mTemp?", "+ LH.Chat.ORANGE+"Molten":""), 112, 60, 0xffffff);
            }
            GL11.glColor4f( mat.mRGBaSolid[0]/255F, mat.mRGBaSolid[1]/255F, mat.mRGBaSolid[2]/255F, mat.mRGBaSolid[3]/255F);
            index-=h;
            mc.getTextureManager().bindTexture(texture);
            drawTextureRect(Tessellator.instance, x, index + y, amount>0?0:0/*todo: different soild and molten*/, 166, width, h);


            GL11.glColor4f(1f, 1f, 1f, 1f);
        }
    }
    public void drawTextureRect(Tessellator tessellator, float x, float y, float u, float v, float width, float height){
        final float f  = 0.00390625F;
        final float f1 = 0.00390625F;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + height, zLevel, (u * f), (v + height) * f1);
        tessellator.addVertexWithUV(x + width, y + height, zLevel, (u + width) * f, (v + height) * f1);
        tessellator.addVertexWithUV(x + width, y, zLevel, (u + width) * f, v * f1);
        tessellator.addVertexWithUV(x, y, zLevel, (u * f), v * f1);
        tessellator.draw();
    }
}
