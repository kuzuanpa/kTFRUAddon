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

package cn.kuzuanpa.ktfruaddon.gui;

import cn.kuzuanpa.ktfruaddon.gui.button.ArrowDown;
import cn.kuzuanpa.ktfruaddon.gui.button.ArrowRight;
import cn.kuzuanpa.ktfruaddon.gui.button.CommonGuiButton;
import cn.kuzuanpa.ktfruaddon.gui.button.DataBar;
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

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

@SideOnly(Side.CLIENT)
public class ContainerClientTest extends ContainerClientbase {
	@Override
	public void initGui() {
		super.initGui();
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		mBackground = new ResourceLocation(MOD_ID,"textures/gui/SatelliteMonitor/main.png");
		buttonList.add(new ArrowRight(0,  x+54,y+46,""));
		buttonList.add(new ArrowDown(1,  x+53,y+16,""));
		buttonList.add(new DataBar(2,  x+32,y+15,38,""));
	}

	public ContainerClientTest(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, RecipeMap aRecipes, int aGUIID, String aGUITexture) {
		super(new ContainerCommonTest(aInventoryPlayer, aTileEntity, aGUIID), aGUITexture);

	}
	protected void drawGuiContainerForegroundLayer2(int par1, int par2) {
		fontRendererObj.drawString(LH.get("arfix.machine.satellite.name"), 8,  4, 4210752);

	}
	@Override
	protected void drawGuiContainerBackgroundLayer2(float par1, int par2, int par3) {
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x,y-5, 0, 0, xSize, ySize+5);

	}
	public void mouseMove(int x, int y){
		int ContainerX = (width - xSize) / 2;
		int ContainerY = (height - ySize) / 2;
		buttonList.forEach(button-> {if (button instanceof CommonGuiButton)((CommonGuiButton) button).isMouseHover= ((CommonGuiButton) button).isMouseOverButton(x, y);});
		requestOrUpdateTooltip(0,new String[]{LH.get("arfix.tooltip.data")+"0"+"/"+"1000"}, (ContainerX+31<x&&x<ContainerX+38&&ContainerY+13<y&&y<ContainerY+53));
		requestOrUpdateTooltip(1,new String[]{LH.get("arfix.tooltip.move.buffer")}, ((CommonGuiButton) buttonList.get(0)).isMouseHover);
		requestOrUpdateTooltip(2,new String[]{LH.get("arfix.tooltip.move.chip")}, ((CommonGuiButton) buttonList.get(1)).isMouseHover);
	}

	public boolean onButtonPressed(GuiButton button) {
		switch (button.id){
			case 0:	FMLLog.log(Level.FATAL,"1+"+button.getHoverState(true));
			case 1: FMLLog.log(Level.FATAL,"B");
			case 2:
			default: return true;
		}
	}}
