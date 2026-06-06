/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */

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
package cn.kuzuanpa.ktfruaddon.client.gui.research;

import cn.kuzuanpa.kGuiLib.client.anime.animeMoveLinear;
import cn.kuzuanpa.kGuiLib.client.anime.animeMoveSlowIn;
import cn.kuzuanpa.kGuiLib.client.anime.animeScaleLinear;
import cn.kuzuanpa.kGuiLib.client.anime.animeScaleQuad;
import cn.kuzuanpa.kGuiLib.client.anime.shortcut.animeTransparency;
import cn.kuzuanpa.kGuiLib.client.kGuiScreenBase;
import cn.kuzuanpa.kGuiLib.client.objects.gui.kGuiButtonBase;
import cn.kuzuanpa.ktfruaddon.api.nei.IHiddenNei;
import cn.kuzuanpa.ktfruaddon.api.research.ResearchProject;
import cn.kuzuanpa.ktfruaddon.api.research.ResearchTree;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

@SideOnly(Side.CLIENT)
public class ContainerClientResearchTreeMonitor extends kGuiScreenBase implements IHiddenNei {

	public ContainerClientResearchTreeMonitor(ResearchTree researchTree) {
		super();
		theTree = researchTree;
	}
	ResearchTree theTree;
	final ResourceLocation background = new ResourceLocation(MOD_ID,"textures/gui/research/background.png");
	final ResourceLocation main = new ResourceLocation(MOD_ID,"textures/gui/research/main.png");
	public ResearchProject pointingItem = null;
	public ResearchProject selectedItem = null;
	public ResearchCommonElements.CurrentPanel currentPanel= null;
	public ResearchCommonElements.HoveringPanel hoveringPanel = null;
	public ResearchCommonElements.SidePanel sidePanelA = null;
	public ResearchCommonElements.SidePanel sidePanelB = null;
	public float xOffset=0, yOffset=0, xOld=0, yOld =0;

	@Override
	public void drawScreen2(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1,1,1,1);
		pointingItem = null;
		drawBackground();
		//draw buttons(they will update some variable)
		super.drawScreen2(p_73863_1_, p_73863_2_, p_73863_3_);

		tickMouseOffset();
		//Just pointed on some item
		if(hoveringPanel.researchProject == null && pointingItem != null)hoveringPanel.join(p_73863_1_,p_73863_2_);
		//Just not point on any item
		if(hoveringPanel.researchProject != null && pointingItem == null)hoveringPanel.quit(p_73863_1_, p_73863_2_);

		hoveringPanel.update(pointingItem, selectedItem);

		currentPanel.currentProject = theTree.getCurrentProject();
	}

	public void drawBackground(){
		mc.getTextureManager().bindTexture(background);
		GL11.glColor4f(0.2F,0.2F,0.2F,0.6f);
		this.drawTexturedModalRect(0,0, 0, 14, width, height);
	}

	int mouseLastX=0 , mouseLastY=0;
	@Override
	public void handleMouseInput2(int mouseX,int mouseY) {
		super.handleMouseInput2(mouseX,mouseY);
		if(Mouse.isButtonDown(0)){
			float scaleRate = mc.displayHeight*1F/mc.currentScreen.height;
			int dX = Mouse.getX()-mouseLastX;
			int dY = Mouse.getY()-mouseLastY;
			float speed = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)?3F:1.2F;
			xOffset+=dX*speed/scaleRate;
			yOffset-=dY*speed/scaleRate;
		}
		mouseLastX=Mouse.getX();
		mouseLastY=Mouse.getY();
	}

	public void tickMouseOffset(){
		float deltaX= ((xOffset-xOld)/12f);
		float deltaY= ((yOffset-yOld)/12f);

		xOld+=deltaX;
		yOld+=deltaY;
	}
	public Map<String, Integer> researchIDToIntIDMap = new HashMap<>();
	@Override
	public void addButtons() {

		AtomicInteger i = new AtomicInteger();
		theTree.allResearch.forEach((s, researchItem) -> {
			researchIDToIntIDMap.put(s,i.get());
			int rate = 300;
			int layer = researchItem.layer;
			buttons.add(i.get(),new researchButton(i.get(),researchItem).setJoinLeaveTime(layer*rate,Integer.MAX_VALUE).addAnime(new animeTransparency(layer*rate,layer*rate+1000,0,255)).addAnime(new animeMoveLinear(-1,0,30,-2)).addAnime(new animeMoveSlowIn(layer*rate, layer*rate+500, -30,2,3)));
			i.getAndIncrement();
		});

		hoveringPanel = new ResearchCommonElements.HoveringPanel(this,i.getAndIncrement());
		buttons.add(hoveringPanel);
		currentPanel = (ResearchCommonElements.CurrentPanel) new ResearchCommonElements.CurrentPanel(this,i.getAndIncrement(), 96).setJoinLeaveTime(200,Integer.MAX_VALUE).addAnime(new animeTransparency(200,800,0,255)).addAnime(new animeMoveLinear(-1,0,120,0)).addAnime(new animeMoveSlowIn(200,800, -120,0,3f));
		buttons.add(currentPanel);
		sidePanelA = (ResearchCommonElements.SidePanel) new ResearchCommonElements.SidePanel(this,i.getAndIncrement(),116, 48).setJoinLeaveTime(400,Integer.MAX_VALUE).addAnime(new animeTransparency(400,1000,0,255)).addAnime(new animeMoveLinear(-1,0,120,0)).addAnime(new animeMoveSlowIn(400,1000, -120,0,3f));
		sidePanelB = (ResearchCommonElements.SidePanel) new ResearchCommonElements.SidePanel(this,i.getAndIncrement(),116, 48).setJoinLeaveTime(-1,0);
		buttons.add(sidePanelA);
		buttons.add(sidePanelB);
	}

	@Override
	public boolean onButtonPressed(GuiButton button, int mouseX, int mouseY) {
		if(button instanceof researchButton && researchIDToIntIDMap.containsValue(button.id)){
			if(selectedItem == ((researchButton)button).researchProject){
				theTree.sendUpdateCurrentProjectPacket(selectedItem.getId());
				return true;
			}
			selectedItem = ((researchButton)button).researchProject;
			ResearchCommonElements.SidePanel panelOld = sidePanelA;
			sidePanelB.getGuiAnimeList().clear();
			sidePanelA.getGuiAnimeList().clear();
			sidePanelB.setJoinLeaveTime(getTimer(), Integer.MAX_VALUE).addAnime(new animeMoveSlowIn(getTimer(), getTimer() +1000, mc.currentScreen.width-sidePanelB.width - button.xPosition,48- button.yPosition, 4)).addAnime(new animeMoveLinear(0,0, button.xPosition, button.yPosition)).addAnime(new animeScaleLinear(0,0,0.1F)).addAnime(new animeScaleQuad(getTimer(), getTimer() +1000, 10.0F,4)).addAnime(new animeMoveLinear(0,0,-sidePanelB.xPosition, -sidePanelB.yPosition)).addAnime(new animeTransparency(getTimer(), getTimer() +700, 0,255));
			sidePanelA.setJoinLeaveTime(0, getTimer() +1000).addAnime(new animeMoveSlowIn(getTimer(), getTimer() +1000, sidePanelA.width,0, 4));
			sidePanelB.selectedProject = selectedItem;
			sidePanelA = sidePanelB;
			sidePanelB = panelOld;
		}
		if(button instanceof ResearchCommonElements.SidePanel && ((ResearchCommonElements.SidePanel) button).isMouseInButton(mouseX,mouseY))selectedItem = null;
		return false;
	}

	@Override
	public void onKeyTyped(char key, int keyCode) {
		if(keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_E)close();
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		ResearchTree.sendGetTreeDataPacket(Minecraft.getMinecraft().thePlayer.getCommandSenderName(), theTree.uuid, (byte) 4);
	}

	public class researchButton extends kGuiButtonBase {
		public researchButton(int id, ResearchProject researchProject) {
			super(id, researchProject.posX, researchProject.posY, 80, 32, "");
			this.researchProject = researchProject;
			setAnimatedInFBO(true);
		}
		public final ResearchProject researchProject;
		@Override
		public void drawButton2(Minecraft mc, int mouseX, int mouseY) {
			if(!visible)return;

			xPosition = (int) (researchProject.posX + xOld);
			yPosition = (int) (researchProject.posY + yOld);

			if(xPosition < -64-width || xPosition > mc.currentScreen.width+64)return;
			if(yPosition < -64-height || yPosition > mc.currentScreen.height+64)return;

			Tessellator tessellator = Tessellator.instance;
			float colorTimer = ((float) Math.sin(System.currentTimeMillis()%3141/1000f))/2f+0.5f;

			GL11.glEnable(GL11.GL_ALPHA_TEST);
			drawBackground(tessellator,colorTimer);
			ResearchCommonElements.drawResearchMainIcon(researchProject, xPosition + 3, yPosition + 3);
			ResearchCommonElements.drawResearchNameDesc(researchProject, xPosition + 22, yPosition + 6);
			drawLockedMask(tessellator);
			if(mouseX>xPosition && mouseY>yPosition && mouseX<xPosition+width && mouseY<yPosition+height){
				pointingItem= researchProject;
			}
			if(!researchProject.isUnlocked)return;
			ResearchCommonElements.drawResearchProgressBar(researchProject, tessellator, xPosition+2, yPosition+height-10, this.zLevel, width);
			ResearchCommonElements.drawResearchConditionIcon(researchProject, xPosition + 2, yPosition + height - 14);

			GL11.glColor4f(1,1,1,1);
		}

		public void fillColor(float colorTimer){
			if(!researchProject.isUnlocked)GL11.glColor4f(.5f,.5f,.5f,.4f);
			else if (!researchProject.isCompleted && researchProject.getProgress() > 0) GL11.glColor4f(0f,colorTimer/2f+0.5f,(1-colorTimer)/4f+0.75f,.9f);
			else if (!researchProject.isCompleted) GL11.glColor4f(1f,1f-colorTimer/2.5f,0,.9f);
			else GL11.glColor4f(colorTimer/3f+.1f,.8f,.0f,.9f);
		}
		public void drawBackground(Tessellator tessellator, float colorTimer){
			fillColor(colorTimer);
			mc.getTextureManager().bindTexture(main);
			ResearchCommonElements.drawTextureRect(tessellator, xPosition, yPosition, this.zLevel, 0, width, height, 0);
			drawDependsLine(tessellator, colorTimer);
			GL11.glColor4f(1,1,1,1);
		}

		public void drawDependsLine(Tessellator tessellator, float colorTimer){

			for (ResearchProject item : researchProject.prerequisites){
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
		public void drawLockedMask(Tessellator tessellator){
			if(researchProject.isUnlocked) return;
			mc.getTextureManager().bindTexture(background);
			GL11.glColor4f(1, 1, 1, 0.5f);
			ResearchCommonElements.drawTextureRect(tessellator, xPosition, yPosition, this.zLevel, 40, width, height, 20);
		}
	}
}
