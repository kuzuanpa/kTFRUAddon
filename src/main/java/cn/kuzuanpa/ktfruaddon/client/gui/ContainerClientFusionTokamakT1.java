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

import cn.kuzuanpa.ktfruaddon.client.gui.button.CommonGuiButton;
import cn.kuzuanpa.ktfruaddon.api.code.codeUtil;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.kUII18n;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregapi.data.LH;
import gregapi.recipes.Recipe.RecipeMap;
import gregapi.tileentity.ITileEntityInventoryGUI;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;
import static cn.kuzuanpa.ktfruaddon.tile.multiblock.fusionReactorTokamakExp.MAX_FIELD_STRENGTH;
import static cn.kuzuanpa.ktfruaddon.tile.multiblock.fusionReactorTokamakT1.*;

@SideOnly(Side.CLIENT)
public class ContainerClientFusionTokamakT1 extends ContainerClientbase {
	@Override
	public void initGui() {
		super.initGui();
		mBackground = new ResourceLocation(MOD_ID,"textures/gui/fusionReactor/tokamakT1.png");
	}
	private RecipeMap mRecipes;
	public ContainerClientFusionTokamakT1(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, RecipeMap aRecipes, int aGUIID, String aGUITexture) {
		super(new ContainerCommonFusionTokamakT1(aInventoryPlayer, aTileEntity, aRecipes,aGUIID), aGUITexture);
		mRecipes=aRecipes;
		this.ySize= 219;
	}
	protected void drawGuiContainerForegroundLayer2(int par1, int par2) {
		ContainerCommonFusionTokamakT1 container = (ContainerCommonFusionTokamakT1) mContainer;
		fontRendererObj.drawString(LH.get("ktfru.ui.multiblock.fusion.tokamak.t1.0"), 120- fontRendererObj.getStringWidth(LH.get("ktfru.ui.multiblock.fusion.tokamak.t1.0"))/2,  10, 4210752);
		switch (container.mState){
			case STATE_STOPPED      : fontRendererObj.drawString(LH.get(kUII18n.FUSION_TOKAMAK_STATE_STOPPED   ), 120- fontRendererObj.getStringWidth(LH.get(kUII18n.FUSION_TOKAMAK_STATE_STOPPED   ))/2,  19, 0xffffff); break;
			case STATE_CHARGING     : fontRendererObj.drawString(LH.get(kUII18n.FUSION_TOKAMAK_STATE_CHARGING  ), 120- fontRendererObj.getStringWidth(LH.get(kUII18n.FUSION_TOKAMAK_STATE_CHARGING  ))/2,  19, 0x77eeee);break;
			case STATE_RUNNING      : fontRendererObj.drawString(LH.get(kUII18n.FUSION_TOKAMAK_STATE_RUNNING   ), 120- fontRendererObj.getStringWidth(LH.get(kUII18n.FUSION_TOKAMAK_STATE_RUNNING   ))/2,  19, 0x00ffff);break;
			case STATE_ERROR        : fontRendererObj.drawString(LH.get(kUII18n.FUSION_TOKAMAK_STATE_ERROR     ), 120- fontRendererObj.getStringWidth(LH.get(kUII18n.FUSION_TOKAMAK_STATE_ERROR     ))/2,  19, 0xff0000);break;
			case STATE_VOID_CHARGING: fontRendererObj.drawString(LH.get(kUII18n.FUSION_TOKAMAK_STATE_VOIDCHARGE), 120- fontRendererObj.getStringWidth(LH.get(kUII18n.FUSION_TOKAMAK_STATE_VOIDCHARGE))/2,  19, 0xffff00);break;
		}

		fontRendererObj.drawString(LH.get("ktfru.ui.multiblock.fusion.tokamak.t1.2"), 120- fontRendererObj.getStringWidth(LH.get("ktfru.ui.multiblock.fusion.tokamak.t1.2"))/2,  31, 4210752);

		fontRendererObj.drawString(LH.get("ktfru.ui.multiblock.fusion.tokamak.t1.1"), 120- fontRendererObj.getStringWidth(LH.get("ktfru.ui.multiblock.fusion.tokamak.t1.1"))/2,  51, 4210752);

	}
	static {
		LH.add("ktfru.ui.multiblock.fusion.tokamak.t1.0","State");
		LH.add("ktfru.ui.multiblock.fusion.tokamak.t1.1","Field Strength");
		LH.add("ktfru.ui.multiblock.fusion.tokamak.t1.2","Plasma Temp");
	}
	@Override
	protected void drawGuiContainerBackgroundLayer2(float par1, int par2, int par3) {
		ContainerCommonFusionTokamakT1 container = (ContainerCommonFusionTokamakT1) mContainer;

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x,y-5, 0, 0, xSize, ySize+5);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);

		GL11.glColor4f(1.0F,1.0F,1.0F,container.mFieldStrength*1F/ MAX_FIELD_STRENGTH);
		this.drawTexturedModalRect(x+15,y+8, 176, 0, 57, 57);

		Color color = new Color(codeUtil.getColorFromTemp(container.mTemperature));
		GL11.glColor4f(color.getRed()/255F,color.getGreen()/255F,color.getBlue()/255F, 1.0F);
		this.drawTexturedModalRect(x+15,y+8, 176, 57, 57, 57);

		GL11.glColor4f(1,1,1,1);
		this.drawTexturedModalRect((int) (x+89+container.mTemperature/1400F*61),y+41, 0, 224, 1, 7);
		this.drawTexturedModalRect(x+75,y+92, 1, 224, container.mProgressBar/1311, 20);

	}
	public void mouseMove(int x, int y){
		int ContainerX = (width - xSize) / 2;
		int ContainerY = (height - ySize) / 2;
		buttonList.forEach(button-> {if (button instanceof CommonGuiButton)((CommonGuiButton) button).isMouseHover= ((CommonGuiButton) button).isMouseOverButton(x, y);});
		//requestOrUpdateTooltip(0,new String[]{LH.get("arfix.tooltip.data")+"0"+"/"+"1000"}, (ContainerX+31<x&&x<ContainerX+38&&ContainerY+13<y&&y<ContainerY+53));
		//requestOrUpdateTooltip(1,new String[]{LH.get("arfix.tooltip.move.buffer")}, ((CommonGuiButton) buttonList.get(0)).isMouseHover);
		//requestOrUpdateTooltip(2,new String[]{LH.get("arfix.tooltip.move.chip")}, ((CommonGuiButton) buttonList.get(1)).isMouseHover);
	}

	public boolean onButtonPressed(GuiButton button) {
		switch (button.id){
			case 0:	FMLLog.log(Level.FATAL,"1+"+button.getHoverState(true));
			case 1: FMLLog.log(Level.FATAL,"B");
			case 2:
			default: return true;
		}
	}
	@Override
	public boolean onNoButtonPressed() {
		return true;
	}
}
