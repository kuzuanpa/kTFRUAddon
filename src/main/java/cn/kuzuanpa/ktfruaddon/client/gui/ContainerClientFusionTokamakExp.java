/**
 * Copyright (c) 2019 Gregorius Techneticies
 *
 * This file is part of GregTech.
 *
 * GregTech is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GregTech is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with GregTech. If not, see <http://www.gnu.org/licenses/>.
 */

package cn.kuzuanpa.ktfruaddon.client.gui;

import cn.kuzuanpa.ktfruaddon.client.gui.button.CommonGuiButton;
import cn.kuzuanpa.ktfruaddon.code.codeUtil;
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
import static cn.kuzuanpa.ktfruaddon.tile.multiblock.fusionReactorTokamakExp.*;

@SideOnly(Side.CLIENT)
public class ContainerClientFusionTokamakExp extends ContainerClientbase {
	@Override
	public void initGui() {
		super.initGui();
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		mBackground = new ResourceLocation(MOD_ID,"textures/gui/fusionReactor/main.png");
	}
	private RecipeMap mRecipes;
	public ContainerClientFusionTokamakExp(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, RecipeMap aRecipes, int aGUIID, String aGUITexture) {
		super(new ContainerCommonFusionTokamakExp(aInventoryPlayer, aTileEntity, aRecipes,aGUIID), aGUITexture);
		mRecipes=aRecipes;
	}
	protected void drawGuiContainerForegroundLayer2(int par1, int par2) {
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		ContainerCommonFusionTokamakExp container = (ContainerCommonFusionTokamakExp) mContainer;
		fontRendererObj.drawString(LH.get("ktfru.ui.multiblock.fusion.tokamak.exp.0"), 84,  5, 4210752);
		switch (container.mState){
			case STATE_STOPPED      : fontRendererObj.drawString(LH.get("ktfru.ui.multiblock.fusion.tokamak.exp.state.0"), 84,  13, 4210752); break;
			case STATE_CHARGING     : fontRendererObj.drawString(LH.get("ktfru.ui.multiblock.fusion.tokamak.exp.state.1"), 84,  13, 0x77eeee);break;
			case STATE_RUNNING      : fontRendererObj.drawString(LH.get("ktfru.ui.multiblock.fusion.tokamak.exp.state.2"), 84,  13, 0x00ffff);break;
			case STATE_ERROR        : fontRendererObj.drawString(LH.get("ktfru.ui.multiblock.fusion.tokamak.exp.state.3"), 84,  13, 0xff0000);break;
			case STATE_VOID_CHARGING: fontRendererObj.drawString(LH.get("ktfru.ui.multiblock.fusion.tokamak.exp.state.4"), 84,  13, 0xffff00);break;
		}
		fontRendererObj.drawString(LH.get("ktfru.ui.multiblock.fusion.tokamak.exp.1"), 84,  23, 4210752);
		int color = container.mState==STATE_STOPPED?4210752: container.mFieldStrength==400? 0x00ff00:container.mFieldStrength>300? 0xfffff: container.mFieldStrength>100? 0xffff00 :0xff0000;
		fontRendererObj.drawString(String.valueOf(container.mFieldStrength), 84,  31, color);
		fontRendererObj.drawString(LH.get("ktfru.ui.multiblock.fusion.tokamak.exp.2"), 84,  41, 4210752);

		fontRendererObj.drawString(LH.get("ktfru.ui.multiblock.fusion.tokamak.exp.3"), 84,  58, 4210752);


	}
	static {
		LH.add("ktfru.ui.multiblock.fusion.tokamak.exp.0","State: ");
		LH.add("ktfru.ui.multiblock.fusion.tokamak.exp.1","Field Strength: ");
		LH.add("ktfru.ui.multiblock.fusion.tokamak.exp.2","Plasma Temp: ");
		LH.add("ktfru.ui.multiblock.fusion.tokamak.exp.3","Progress: ");
		LH.add("ktfru.ui.multiblock.fusion.tokamak.exp.state.0","Stopped");
		LH.add("ktfru.ui.multiblock.fusion.tokamak.exp.state.1","Charging");
		LH.add("ktfru.ui.multiblock.fusion.tokamak.exp.state.2","Running");
		LH.add("ktfru.ui.multiblock.fusion.tokamak.exp.state.3","ERROR");
		LH.add("ktfru.ui.multiblock.fusion.tokamak.exp.state.4","Void Charging");
	}
	@Override
	protected void drawGuiContainerBackgroundLayer2(float par1, int par2, int par3) {
		ContainerCommonFusionTokamakExp container = (ContainerCommonFusionTokamakExp) mContainer;

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x,y-5, 0, 0, xSize, ySize+5);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);

		GL11.glColor4f(1.0F,1.0F,1.0F,container.mFieldStrength*1F/ MAX_FIELD_STRENGTH);
		this.drawTexturedModalRect(x+17,y+12, 176, 0, 57, 57);

		Color color = new Color(codeUtil.getColorFromTemp(container.mTemperature));
		GL11.glColor4f(color.getRed()/255F,color.getGreen()/255F,color.getBlue()/255F, (container.mDensity / 16384F));
		this.drawTexturedModalRect(x+17,y+12, 176, 57, 57, 57);

		GL11.glColor4f(1,1,1,1);
		this.drawTexturedModalRect((int) (x+84+container.mTemperature/600F*61),y+51, 0, 171, 1, 6);
		this.drawTexturedModalRect(x+84,y+68, 0, 171, container.mProgressBar/528, 6);

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
