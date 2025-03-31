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

import cn.kuzuanpa.kGuiLib.client.anime.animeMoveLinear;
import cn.kuzuanpa.kGuiLib.client.anime.animeMoveSlowIn;
import cn.kuzuanpa.kGuiLib.client.anime.animeScaleLinear;
import cn.kuzuanpa.kGuiLib.client.anime.animeScaleQuad;
import cn.kuzuanpa.kGuiLib.client.anime.shortcut.animeTransparency;
import cn.kuzuanpa.kGuiLib.client.kGuiContainerBase;
import cn.kuzuanpa.kGuiLib.client.objects.gui.kGuiButtonBase;
import cn.kuzuanpa.ktfruaddon.api.nei.IHiddenNei;
import cn.kuzuanpa.ktfruaddon.api.network.PacketContainerButtonPressed;
import cn.kuzuanpa.ktfruaddon.api.research.ResearchItem;
import cn.kuzuanpa.ktfruaddon.api.research.task.IResearchTask;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import cn.kuzuanpa.ktfruaddon.tile.research.ResearchTreeMonitor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregapi.tileentity.ITileEntityInventoryGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;
import static cn.kuzuanpa.ktfruaddon.ktfruaddon.kNetworkHandler;

@SideOnly(Side.CLIENT)
public class ContainerClientResearchTreeMonitor extends kGuiContainerBase implements IHiddenNei {
	private ContainerCommonResearchTreeMonitor mContainer;

	public ContainerClientResearchTreeMonitor(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, int aGUIID, String aGUITexture) {
		super(new ContainerCommonResearchTreeMonitor(aInventoryPlayer, aTileEntity,aGUIID));

		this.mContainer=(ContainerCommonResearchTreeMonitor)inventorySlots;
	}
	final ResourceLocation background = new ResourceLocation(MOD_ID,"textures/gui/research/background.png");
	final ResourceLocation main = new ResourceLocation(MOD_ID,"textures/gui/research/main.png");
	final Random rng = new Random();
	public ResearchItem pointingItem = null;
	public ResearchItem selectedItem = null;
	public HoveringPanel hoveringPanel = null;
	public SidePanel sidePanel = null;
	public float xOffset=0, yOffset=0, xOld=0, yOld =0;
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		mc.getTextureManager().bindTexture(background);
		GL11.glColor4f(1,1,1,0.1f);
		this.drawTexturedModalRect(0,0, 0, 14, width, height);
	}

	@Override
	public void drawScreen2(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		tickMouseOffset();
		GL11.glColor4f(1,1,1,1);

		pointingItem = null;
		super.drawScreen2(p_73863_1_, p_73863_2_, p_73863_3_);

		//Just pointed on some item
		if(hoveringPanel.researchItem == null && pointingItem != null)hoveringPanel.join(p_73863_1_,p_73863_2_);
		//Just not point on any item
		if(hoveringPanel.researchItem != null && pointingItem == null)hoveringPanel.quit(p_73863_1_, p_73863_2_);
		hoveringPanel.researchItem = pointingItem;

		hoveringPanel.update();
	}

	int mouseLastX=0 , mouseLastY=0;
	@Override
	public void handleMouseInput2() {
		super.handleMouseInput2();
		if(Mouse.isButtonDown(0)){
			float scaleRate = mc.displayHeight*1F/mc.currentScreen.height;
			int dX = Mouse.getX()-mouseLastX;
			int dY = Mouse.getY()-mouseLastY;
			xOffset+=dX*1f/scaleRate;
			yOffset-=dY*1f/scaleRate;
		}
		mouseLastX=Mouse.getX();
		mouseLastY=Mouse.getY();
	}

	public void tickMouseOffset(){
		float deltaX= ((xOffset-xOld)/32f);
		float deltaY= ((yOffset-yOld)/32f);

		xOld+=deltaX;
		yOld+=deltaY;
	}
	public Map<String, Integer> researchIDToIntIDMap = new HashMap<>();
	@Override
	public void addButtons() {

		AtomicInteger i = new AtomicInteger();
		((ResearchTreeMonitor)mContainer.mTileEntity).theTree.allResearch.forEach((s, researchItem) -> {
			researchIDToIntIDMap.put(s,i.get());
			int rate = 400;
			int layer = researchItem.layer;
			buttons.add(i.get(),new researchButton(i.get(),researchItem).setJoinLeaveTime(layer*rate,Integer.MAX_VALUE).addAnime(new animeTransparency(layer*rate,layer*rate+1000,0,255)).addAnime(new animeMoveLinear(-1,0,30,-2)).addAnime(new animeMoveSlowIn(layer*rate, layer*rate+500, -30,2,3)));
			i.getAndIncrement();
		});

		hoveringPanel = new HoveringPanel(i.getAndIncrement());
		buttons.add(hoveringPanel);
		sidePanel = (SidePanel) new SidePanel(i.getAndIncrement(),96).setJoinLeaveTime(1000,Integer.MAX_VALUE).addAnime(new animeTransparency(1000,2000,0,255)).addAnime(new animeMoveLinear(-1,0,120,0)).addAnime(new animeMoveSlowIn(1000,2000, -120,0,3f));
		buttons.add(sidePanel);
	}

	@Override
	public boolean onButtonPressed(GuiButton button, int mouseX, int mouseY) {
		if(button instanceof researchButton && researchIDToIntIDMap.containsValue(button.id)){
			selectedItem = ((researchButton)button).researchItem;
			TileEntity t = (TileEntity) mContainer.mTileEntity;
			kNetworkHandler.sendToServer(new PacketContainerButtonPressed(t.xCoord,t.yCoord,t.zCoord,button.id, utils.UTFToBytes(selectedItem.getId())));
		}
		if(button instanceof SidePanel && ((SidePanel) button).isMouseInButton(mouseX,mouseY))selectedItem = null;
		return false;
	}

	@Override
	public void onKeyTyped(char key, int keyCode) {
		if(keyCode == Keyboard.KEY_ESCAPE)close();
	}
	public void drawTextureRect(Tessellator tessellator, int x, int y, int u, int v, int width, int height){
		final float f = 0.00390625F;
		final float f1 = 0.00390625F;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x, y + height, this.zLevel, (u * f), (v + height) * f1);
		tessellator.addVertexWithUV(x + width, y + height, this.zLevel, (u + width) * f, (v + height) * f1);
		tessellator.addVertexWithUV(x + width, y, this.zLevel, (u + width) * f, v * f1);
		tessellator.addVertexWithUV(x, y, this.zLevel, (u * f), v * f1);
		tessellator.draw();
	}
	public class researchButton extends kGuiButtonBase {
		public researchButton(int id, ResearchItem researchItem) {
			super(id, researchItem.posX, researchItem.posY, 80, 32, "");
			this.researchItem=researchItem;
			setAnimatedInFBO(true);
		}
		public final ResearchItem researchItem;
		@Override
		public void drawButton2(Minecraft mc, int mouseX, int mouseY) {
			if(!visible)return;
			float colorTimer = ((float) Math.sin(System.currentTimeMillis()%3141/1000f))/2f+0.5f;
			Tessellator tessellator = Tessellator.instance;

			xPosition = (int) (researchItem.posX + xOld);
			yPosition = (int) (researchItem.posY + yOld);

			GL11.glEnable(GL11.GL_ALPHA_TEST);
			drawBackground(tessellator,colorTimer);
			drawMainIcon();
			drawNameDesc();
			drawLockedMask(tessellator);
			if(mouseX>xPosition && mouseY>yPosition && mouseX<xPosition+width && mouseY<yPosition+height){
				pointingItem=researchItem;
			}
			if(!researchItem.isUnlocked)return;
			drawProgressBar(tessellator);
			drawConditionIcon();

			GL11.glColor4f(1,1,1,1);
		}

		public void fillColor(float colorTimer){
			if(!researchItem.isUnlocked)GL11.glColor4f(.5f,.5f,.5f,.4f);
			else if (!researchItem.isCompleted && researchItem.getProgress() > 0) GL11.glColor4f(0f,colorTimer/2f+0.5f,(1-colorTimer)/4f+0.75f,.9f);
			else if (!researchItem.isCompleted) GL11.glColor4f(1f,1f-colorTimer/2.5f,0,.9f);
			else GL11.glColor4f(colorTimer/3f+.1f,.8f,.0f,.9f);
		}
		public void drawBackground(Tessellator tessellator, float colorTimer){
			fillColor(colorTimer);
			mc.getTextureManager().bindTexture(main);
			drawTextureRect(tessellator, xPosition, yPosition, 0, 0, width, height);
			drawDependsLine(tessellator, colorTimer);
			GL11.glColor4f(1,1,1,1);
		}

		public void drawDependsLine(Tessellator tessellator, float colorTimer){

			for (ResearchItem item : researchItem.prerequisites){
				tessellator.startDrawing(GL11.GL_LINES);
				GL11.glLineWidth(2f);
				tessellator.addVertexWithUV(xPosition, yPosition + height/2f , this.zLevel,0 ,0);
				if(item.id.equals("root")) tessellator.addVertexWithUV(0, mc.currentScreen.height/2f, this.zLevel,0 ,0);
				else if(researchIDToIntIDMap.get(item.id) != null){
					kGuiButtonBase b = buttons.get(researchIDToIntIDMap.get(item.id));
					if (b != null) tessellator.addVertexWithUV(b.xPosition + width, b.yPosition+height/2f, this.zLevel,0 ,0);
					tessellator.draw();
					continue;
				}
				tessellator.draw();
			}
			GL11.glColor4f(1,1,1,1);
		}
		public void drawProgressBar(Tessellator tessellator){
			mc.getTextureManager().bindTexture(background);
			GL11.glColor4f(1, 1, 1, 1f);
			float progress = 0.0f;
			for (IResearchTask condition : researchItem.tasks) {
				progress += condition.getProgress()*1f/condition.getMaxProgress();
			}
			drawTextureRect(tessellator, xPosition+2, yPosition+height-10, 0, 14, (int) ((width-4) * (progress/researchItem.tasks.size())), 8);
		}
		public void drawConditionIcon(){
			AtomicInteger i = new AtomicInteger();
            for (IResearchTask condition : researchItem.tasks) {
                if (i.get() > 6) {
                    mc.fontRenderer.drawStringWithShadow("…", xPosition + 2 + i.get() * 10, yPosition + height - 14, 0xffffffff);
                    GL11.glColor4f(1, 1, 1, 1);
                    break;
                }
                mc.getTextureManager().bindTexture(mc.getTextureManager().getResourceLocation(Items.saddle.getSpriteNumber()));
                if (condition.getIcon() != null) drawTexturedModelRectFromIcon(xPosition + 2 + i.getAndIncrement() * 10, yPosition + height - 14, condition.getIcon(), 8, 8);
                else drawTexturedModelRectFromIcon(xPosition + 2 + i.getAndIncrement() * 10, yPosition + height - 14, Items.book.getIconFromDamage(0), 8, 8);
            }
        }
		public void drawMainIcon(){
			mc.getTextureManager().bindTexture(mc.getTextureManager().getResourceLocation(Items.book.getSpriteNumber()));
			if(researchItem.getIcon() != null)drawTexturedModelRectFromIcon(xPosition + 3,yPosition + 3, researchItem.getIcon(), 16,16);
			else drawTexturedModelRectFromIcon(xPosition + 3,yPosition + 3, Items.book.getIconFromDamage(0), 16,16);
		}
		public void drawLockedMask(Tessellator tessellator){
			if(researchItem.isUnlocked) return;
			mc.getTextureManager().bindTexture(background);
			GL11.glColor4f(1, 1, 1, 0.5f);
			drawTextureRect(tessellator, xPosition, yPosition, 20, 40, width, height);
		}
		public void drawNameDesc(){
			mc.fontRenderer.drawStringWithShadow(researchItem.id, xPosition + 22,yPosition+6,0xffffffff);
			GL11.glColor4f(1,1,1,1);
		}
	}

	public class HoveringPanel extends kGuiButtonBase{
		public HoveringPanel(int id) {
			super(id, 0, 0, 128, 48, "");
			setAnimatedInFBO(true);
		}
		public ResearchItem researchItem;
		public ResearchItem renderedResearchItem;
		public int animeDuration = 200;

		public long quitTime = 0;
		public void update(){
			if(researchItem != null){
				visible=true;
				renderedResearchItem =researchItem;
			}
			if(researchItem == null && quitTime + animeDuration < getTimer())visible=false;
			if(!visible)getGuiAnimeList().clear();
		}
		public void join(int mouseX, int mouseY){
			if(quitTime+ animeDuration >= getTimer()) getGuiAnimeList().clear();//terminates Quit anime when player rapidly switch between items.
			addAnime(new animeMoveLinear(-1,0,mouseX,mouseY)).addAnime(new animeScaleLinear(-1,0,0.1f,1,1)).addAnime(new animeScaleQuad((int)getTimer(),(int)getTimer()+animeDuration,10f,3,1,1)).addAnime(new animeMoveLinear(-1,0,-mouseX,-mouseY)).addAnime(new animeTransparency((int)getTimer(),(int)getTimer()+animeDuration,0,255));
		}

		public void quit(int mouseX, int mouseY){
			quitTime = getTimer();
			getGuiAnimeList().clear();
			addAnime(new animeMoveLinear(-1,0,mouseX,mouseY)).addAnime(new animeScaleQuad((int)getTimer(),(int)getTimer()+animeDuration,0.01f,3)).addAnime(new animeMoveLinear(-1,0,-mouseX,-mouseY)).addAnime(new animeTransparency((int)getTimer(),(int)getTimer()+animeDuration,255,-255 ));
		}
		@Override
		public void drawButton2(Minecraft mc, int mouseX, int mouseY) {
			if(!visible || renderedResearchItem == null)return;

			xPosition = mouseX + 6;
			yPosition = mouseY - 4;

			GL11.glDisable(GL11.GL_SCISSOR_TEST);
			float colorTimer = ((float) Math.sin(System.currentTimeMillis()%3141/1000f))/2f+0.5f;
			Tessellator tessellator = Tessellator.instance;
			AtomicInteger i = new AtomicInteger();

			GL11.glEnable(GL11.GL_ALPHA_TEST);

			drawHoverDetail(i,xPosition,yPosition, colorTimer);

			GL11.glColor4f(1,1,1,1);
			if(renderedResearchItem.isUnlocked)for (IResearchTask condition : renderedResearchItem.tasks) {
				i.getAndIncrement();
				drawBackground(tessellator,i.get(),colorTimer, false);

				GL11.glColor4f(1,1,1,1);
				mc.getTextureManager().bindTexture(mc.getTextureManager().getResourceLocation(Items.book.getSpriteNumber()));
				if(condition.getIcon() != null)drawTexturedModelRectFromIcon(xPosition + 2,yPosition -8 + i.get() * 9, condition.getIcon(), 8,8);
				else drawTexturedModelRectFromIcon(xPosition + 2 ,yPosition -8 + i.get() * 9, Items.book.getIconFromDamage(0), 8,8);

				mc.getTextureManager().bindTexture(main);
				GL11.glColor4f(1,1,1,1);
				drawTextureRect(tessellator, xPosition + 12 ,yPosition -7 + i.get() * 9,0, 8,width -14, 6);

				GL11.glColor4f(0.0f,0.8f,0.0f,colorTimer/4+0.75f);
				float progress = condition.getProgress()*1f/condition.getMaxProgress();
				drawTextureRect(tessellator, xPosition + 12 ,yPosition -7 + i.get() * 9,60, 8, (int) ((width -14)*progress), 6);

			}
			i.getAndIncrement();
			drawBackground(tessellator,i.get(),colorTimer, true);
			mc.fontRenderer.drawStringWithShadow(renderedResearchItem.isUnlocked?"Click to see details.":"Complete dependencies first",xPosition+2,yPosition - 9 + i.get() * 9, 0xffffffff);

			GL11.glColor4f(1,1,1,1);
		}
		public void drawBackground(Tessellator tessellator, int i, float colorTimer, boolean isEnded){
			mc.getTextureManager().bindTexture(main);
			GL11.glColor4f(1,1,1,colorTimer/3 + 0.5f);
			drawTextureRect(tessellator, xPosition, yPosition - 9 + i*9,  0,i==1?460: isEnded? 500:480, width, 9);
			GL11.glColor4f(1,1,1,1);
		}
		public void drawHoverDetail(AtomicInteger i, int x, int y, float colorTimer){
			String current = "";
			String last = renderedResearchItem.desc;
			while (true){
				current = mc.fontRenderer.trimStringToWidth(last,width-4);
				i.getAndIncrement();
				if(i.get() > 2 && !last.equals(current))current+="...";
				drawBackground(Tessellator.instance, i.get(), colorTimer, false);
				mc.fontRenderer.drawStringWithShadow(current,x+2,y-9 +i.get()*9,0xffffffff);
				GL11.glColor4f(1,1,1,1);
				if(i.get() > 2)break;
				if(last.equals(current))break;
				last = last.replaceFirst(current,"");
			}
		}
	}
	public class SidePanel extends kGuiButtonBase{
		public SidePanel(int id,int width) {
			super(id, mc.currentScreen.width-width, 0, width, mc.currentScreen.height, "");
			setAnimatedInFBO(true);
		}
		public ResearchItem researchItem;
		@Override
		public void drawButton2(Minecraft mc, int mouseX, int mouseY) {
			if(!visible)return;
			researchItem = selectedItem;
			float colorTimer = ((float) Math.sin(System.currentTimeMillis()%3141/1000f))/2f+0.5f;
			Tessellator tessellator = Tessellator.instance;

			GL11.glEnable(GL11.GL_ALPHA_TEST);
			drawBackground(tessellator,colorTimer);
			if(researchItem==null)return;
			drawMainIcon();
			drawNameDesc();
			if(!researchItem.isUnlocked)return;
			drawProgressBar(tessellator);
			drawConditionIcon();
			GL11.glColor4f(1,1,1,1);
		}

		public boolean isMouseInConfirmButton(int mouseX, int mouseY){
			return mouseX > xPosition && mouseX < xPosition + width && mouseY > yPosition && mouseY < yPosition + height;
		}

		public void fillColor(float colorTimer){
			if(researchItem==null || !researchItem.isUnlocked)GL11.glColor4f(.5f,.5f,.5f,.4f);
			else if (!researchItem.isCompleted && researchItem.getProgress() > 0) GL11.glColor4f(0f,colorTimer/2f+0.5f,(1-colorTimer)/4f+0.75f,.9f);
			else if (!researchItem.isCompleted) GL11.glColor4f(1f,1f-colorTimer/2.5f,0,.9f);
			else GL11.glColor4f(colorTimer/3f+.1f,.8f,.0f,.9f);
		}
		public void drawBackground(Tessellator tessellator, float colorTimer){
			fillColor(colorTimer);
			mc.getTextureManager().bindTexture(main);
			drawTextureRect(tessellator, xPosition, yPosition, 0, 0, width, height);
			GL11.glColor4f(1,1,1,1);
		}

		public void drawProgressBar(Tessellator tessellator){
			mc.getTextureManager().bindTexture(background);
			GL11.glColor4f(1, 1, 1, 1f);
			float progress = 0.0f;
			for (IResearchTask condition : researchItem.tasks) {
				progress += condition.getProgress()*1f/condition.getMaxProgress();
			}
			drawTextureRect(tessellator, xPosition+2, yPosition+height-30, 0, 14, (int) ((width-4) * (progress/researchItem.tasks.size())), 8);
		}
		public void drawConditionIcon(){
			AtomicInteger i = new AtomicInteger();
			for (IResearchTask condition : researchItem.tasks) {
				i.getAndIncrement();
				float colorTimer = ((float) Math.sin(System.currentTimeMillis()%3141/1000f))/2f+0.5f;
				Tessellator tessellator = Tessellator.instance;

				GL11.glColor4f(1,1,1,1);
				mc.getTextureManager().bindTexture(mc.getTextureManager().getResourceLocation(Items.book.getSpriteNumber()));
				if(condition.getIcon() != null)drawTexturedModelRectFromIcon(xPosition + 2,yPosition + height  - 10 - i.get() * 9, condition.getIcon(), 8,8);
				else drawTexturedModelRectFromIcon(xPosition + 2 ,yPosition+ height  - 10 - i.get() * 9, Items.book.getIconFromDamage(0), 8,8);

				mc.getTextureManager().bindTexture(main);
				GL11.glColor4f(1,1,1,1);
				drawTextureRect(tessellator, xPosition + 12 ,yPosition + height - 29 - i.get() * 9,0, 8,width -14, 6);

				GL11.glColor4f(0.0f,0.8f,0.0f,colorTimer/4+0.75f);
				float progress = condition.getProgress()*1f/condition.getMaxProgress();
				drawTextureRect(tessellator, xPosition + 12 ,yPosition + height - 29 - i.get() * 9,60, 8, (int) ((width -14)*progress), 6);

			}
		}
		public void drawMainIcon(){
			mc.getTextureManager().bindTexture(mc.getTextureManager().getResourceLocation(Items.book.getSpriteNumber()));
			if(researchItem.getIcon() != null)drawTexturedModelRectFromIcon(xPosition + 3,yPosition + 3, researchItem.getIcon(), 16,16);
			else drawTexturedModelRectFromIcon(xPosition + 3,yPosition + 3, Items.book.getIconFromDamage(0), 16,16);
		}

		public void drawNameDesc(){
			mc.fontRenderer.drawStringWithShadow(researchItem.id, xPosition + 22,yPosition+6,0xffffffff);
			String current = "";
			String last = researchItem.desc;
			int i =0;
			while (true){
				current = mc.fontRenderer.trimStringToWidth(last,width-4);
				i ++ ;
				mc.fontRenderer.drawStringWithShadow(current,xPosition+2,yPosition+ 16 + i *10,0xffffffff);
				GL11.glColor4f(1,1,1,1);
				if(last.equals(current))break;
				last = last.replaceFirst(current,"");
			}

			GL11.glColor4f(1,1,1,1);
		}
	}
}
