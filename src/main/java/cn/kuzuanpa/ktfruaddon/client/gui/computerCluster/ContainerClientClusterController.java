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
package cn.kuzuanpa.ktfruaddon.client.gui.computerCluster;

import cn.kuzuanpa.kGuiLib.client.IkGuiContainerLayer;
import cn.kuzuanpa.kGuiLib.client.anime.animeMoveLinear;
import cn.kuzuanpa.kGuiLib.client.anime.animeMoveSlowIn;
import cn.kuzuanpa.kGuiLib.client.anime.shortcut.animeFadeIn;
import cn.kuzuanpa.kGuiLib.client.kGuiContainerBase;
import cn.kuzuanpa.kGuiLib.client.objects.IAnimatableButton;
import cn.kuzuanpa.kGuiLib.client.objects.gui.kGuiButtonBase;
import cn.kuzuanpa.ktfruaddon.api.nei.IHiddenNei;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputerCluster;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregapi.tileentity.ITileEntityInventoryGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.UUID;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

@SideOnly(Side.CLIENT)
public class ContainerClientClusterController extends kGuiContainerBase implements IHiddenNei {
	private ContainerCommonClusterController mContainer;
	private boolean subscribed = false;
	private int requestCooldown = 0;

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		ensureSubscribed();
		requestMissingSnapshot();
		mContainer.updateFromClientCache();
		syncValuesToChildGui();
		GL11.glEnable(GL11.GL_BLEND);
		mc.getTextureManager().bindTexture(commonBackground);
		GL11.glColor4f(1,1,1,1);

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x,y+14, 0, 14, xSize, ySize-14);
	}

	public ContainerClientClusterController(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, int aGUIID, String aGUITexture) {
		super(new ContainerCommonClusterController(aInventoryPlayer, aTileEntity,aGUIID));

		this.mContainer=(ContainerCommonClusterController)inventorySlots;
		this.xSize= 226;
		this.ySize= 235;
	}
	final ResourceLocation commonBackground = new ResourceLocation(MOD_ID,"textures/gui/computerCluster/base.png");

	@Override
	public void addButtons() {
		int ContainerX = (width - xSize) / 2;
		int ContainerY = (height - ySize) / 2;
		buttons.add(new switchButton(0,ContainerX+ 2,ContainerY, 2,"Overview",        commonBackground).addAnime(new animeMoveLinear(-1,0,50,0)).addAnime(new animeMoveSlowIn(0, 600,-50,0,2)).addAnime(new animeFadeIn(200)));
		buttons.add(new switchButton(1,ContainerX+22,ContainerY,22,"Cluster Overview",commonBackground).addAnime(new animeMoveLinear(-1,0,50,0)).addAnime(new animeMoveSlowIn(0, 800,-50,0,2)).addAnime(new animeFadeIn(400)));
		buttons.add(new switchButton(2,ContainerX+42,ContainerY,42,"Controller List", commonBackground).addAnime(new animeMoveLinear(-1,0,50,0)).addAnime(new animeMoveSlowIn(0,1000,-50,0,2)).addAnime(new animeFadeIn(600)));
		buttons.add(new switchButton(3,ContainerX+62,ContainerY,62,"User List",       commonBackground).addAnime(new animeMoveLinear(-1,0,50,0)).addAnime(new animeMoveSlowIn(0,1200,-50,0,2)).addAnime(new animeFadeIn(800)));
	}

	@Override
	public boolean onButtonPressed(GuiButton button, int mouseX, int mouseY) {
		int ContainerX = (width - xSize) / 2;
		int ContainerY = (height - ySize) / 2;
		switch (button.id){
			case 0: childGui = new ScreenControllerDetail().setup(this.width,this.height,ContainerX,ContainerY).setParentGui(this).setMC(mc).setFontRenderer(fontRendererObj); break;
			case 1: childGui = new ScreenClusterDetail()   .setup(this.width,this.height,ContainerX,ContainerY).setParentGui(this).setMC(mc).setFontRenderer(fontRendererObj); break;
			case 2: childGui = new ScreenControllerList()  .setup(this.width,this.height,ContainerX,ContainerY).setParentGui(this).setMC(mc).setFontRenderer(fontRendererObj); break;
			case 3: childGui = new ScreenUserList()        .setup(this.width,this.height,ContainerX,ContainerY).setParentGui(this).setMC(mc).setFontRenderer(fontRendererObj); break;
		}
		if(childGui!=null){
			childGui.initGui2();
			syncValuesToChildGui();
			return true;
		}
		return false;
	}

	public void syncValuesToChildGui(){
		if(childGui instanceof ScreenControllerDetail)((ScreenControllerDetail) childGui).updateFromData(mContainer.dataControllerDetail);
		if(childGui instanceof ScreenClusterDetail   )((ScreenClusterDetail   ) childGui).updateFromData(mContainer.dataClusterDetail);
		if(childGui instanceof ScreenControllerList  )((ScreenControllerList  ) childGui).updateFromData(mContainer.dataControllerList);
		if(childGui instanceof ScreenUserList        )((ScreenUserList        ) childGui).updateFromData(mContainer.dataUserList);
	}

	private void ensureSubscribed() {
		if (subscribed) return;
		UUID clusterUUID = mContainer.getClusterUUID();
		if (clusterUUID == null || Minecraft.getMinecraft().thePlayer == null) return;
		ComputerCluster.sendGetClusterDataPacket(Minecraft.getMinecraft().thePlayer.getCommandSenderName(), clusterUUID, mContainer.getControllerUUID(), ComputerCluster.PACKET_SUBSCRIBE);
		subscribed = true;
		requestCooldown = 20;
	}

	private void requestMissingSnapshot() {
		if (!subscribed || mContainer.dataClusterDetail != null || Minecraft.getMinecraft().thePlayer == null) return;
		if (requestCooldown > 0) {
			requestCooldown--;
			return;
		}
		UUID clusterUUID = mContainer.getClusterUUID();
		if (clusterUUID == null) return;
		ComputerCluster.sendGetClusterDataPacket(Minecraft.getMinecraft().thePlayer.getCommandSenderName(), clusterUUID, mContainer.getControllerUUID(), ComputerCluster.PACKET_REQUEST_SYNC);
		requestCooldown = 20;
	}

	@Override
	public void onKeyTyped(char key, int keyCode) {
		if(keyCode == Keyboard.KEY_ESCAPE)close();
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		UUID clusterUUID = mContainer.getClusterUUID();
		if(clusterUUID != null && Minecraft.getMinecraft().thePlayer != null) {
			ComputerCluster.sendGetClusterDataPacket(Minecraft.getMinecraft().thePlayer.getCommandSenderName(), clusterUUID, mContainer.getControllerUUID(), ComputerCluster.PACKET_UNSUBSCRIBE);
		}
	}

	@Override
	public void setWorldAndResolution(Minecraft p_146280_1_, int p_146280_2_, int p_146280_3_) {
		super.setWorldAndResolution(p_146280_1_, p_146280_2_, p_146280_3_);
		int ContainerX = (width - xSize) / 2;
		int ContainerY = (height - ySize) / 2;
		if(childGui instanceof IkGuiContainerLayer) ((IkGuiContainerLayer) childGui).setup(this.width,this.height,ContainerX,ContainerY);
	}

	public class switchButton extends kGuiButtonBase {

		public switchButton(int id, int xPos, int yPos, int u, String displayText,ResourceLocation resourceLocation) {
			super(id, xPos, yPos, 20, 14, displayText);
			this.resourceLocation=resourceLocation;
			this.u=u;
		}
		final int u;
		final ResourceLocation resourceLocation;
		@Override
		public void drawButton2(Minecraft mc, int mouseX, int mouseY) {
			if(!visible)return;
			IAnimatableButton.drawPre(this, this.timer);

			mc.getTextureManager().bindTexture(resourceLocation);

			GL11.glColor3f(1,1,1);
            float f = 0.00390625F;
			float f1 = 0.00390625F;
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(xPosition         , yPosition + height , this.zLevel, (u * f), height * f1);
			tessellator.addVertexWithUV(xPosition + width , yPosition + height , this.zLevel, (u + width) * f, height * f1);
			tessellator.addVertexWithUV(xPosition + width , yPosition          , this.zLevel, (u + width) * f, 0);
			tessellator.addVertexWithUV(xPosition         , yPosition          , this.zLevel, (u * f), 0);
			GL11.glTranslatef(xPosition, yPosition ,0);
			GuiAnimeList.forEach(anime -> anime.animeDraw(timer));
			GL11.glTranslatef(-(xPosition ), -(yPosition ),0);
			tessellator.draw();
			IAnimatableButton.drawAfter(this, this.timer);
		}
	}
}
