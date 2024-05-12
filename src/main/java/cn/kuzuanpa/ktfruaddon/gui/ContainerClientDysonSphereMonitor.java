/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.gui;

import cn.kuzuanpa.ktfruaddon.gui.button.CommonGuiButton;
import cn.kuzuanpa.ktfruaddon.gui.button.DysonSphere.DysonNodeButton;
import cn.kuzuanpa.ktfruaddon.gui.button.DysonSphere.DysonNodeButtonDummy;
import cn.kuzuanpa.ktfruaddon.gui.button.DysonSphere.StarButton;
import cn.kuzuanpa.ktfruaddon.gui.button.customText;
import cn.kuzuanpa.ktfruaddon.nei.IHiddenNei;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregapi.data.LH;
import gregapi.recipes.Recipe.RecipeMap;
import gregapi.tileentity.ITileEntityInventoryGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import zmaster587.advancedRocketry.api.stations.DysonSphere;
import zmaster587.advancedRocketry.dimension.DimensionManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

@SideOnly(Side.CLIENT)
public class ContainerClientDysonSphereMonitor extends ContainerClientbase implements IHiddenNei {
	private int starButtonsEnd=0,starButtonWheelOffset=0,starButtonWheelOffsetOld=0,nodeButtonOffsetX=0,nodeButtonOffsetY=0,nodeButtonOffsetXOld=0,nodeButtonOffsetYOld=0;
	public byte copiedNodeLevel=0,copiedNodeType=0;
	public int DESTROY_BUTTON_REPEAT_COUNT=10;
	IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("ktfruaddon:models/DysonSphere/star.obj"));
	ResourceLocation texture = new ResourceLocation("ktfruaddon:textures/model/DysonSphere/star.png");
	private int bodyList,glTextureId=-1;
	public float rotateAngle=-1;
	protected float rotateAngleReal=0;

	/**Those values is used for the pointer to nodes**/
	protected int nodePointerClock=-1,selectedNodeY=-1,selectedNodeYPre=-1;

	public int selectedStarID =-1;
	public boolean enableCopyPasteMode=false,drawNodesCoord=false;

	@Override
	public void initGui() {
		GL11.glNewList(bodyList = GL11.glGenLists(1), GL11.GL_COMPILE);
		model.renderPart("Cube");
		GL11.glEndList();
		super.initGui();
		mBackground = new ResourceLocation(MOD_ID,"textures/gui/DysonSphere/space.png");
		AtomicInteger atomIndex= new AtomicInteger();
		DimensionManager.getInstance().getStars().forEach(star -> buttonList.add(new StarButton(atomIndex.get(), -4,atomIndex.getAndIncrement()*64,star.getName(),star.getColor(),star.getId())));
		starButtonsEnd=atomIndex.get();
		int index = atomIndex.get();
		//buttonList.add(new ArrowRight(0,  x+54,y+46,""));
		//buttonList.add(new ArrowDown(1,  x+53,y+16,""));
		//buttonList.add(new DataBar(2,  x+32,y+15,38,""));
		loadTexture();
	}
	public void loadTexture(){
		try (InputStream inputstream = Minecraft.getMinecraft().getResourceManager().getResource(texture).getInputStream())
		{
			if (this.glTextureId != -1) {
				TextureUtil.deleteTexture(this.glTextureId);
				this.glTextureId = -1;
			}
			BufferedImage bufferedimage = ImageIO.read(inputstream);
			glTextureId=TextureUtil.uploadTextureImage(glTextureId, bufferedimage);
		}catch (IOException ioexception)
		{
			FMLLog.log(Level.WARN,"Failed to load texture: " + texture.toString());
			ioexception.printStackTrace();
		}
	}
	public ContainerClientDysonSphereMonitor(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, RecipeMap aRecipes, int aGUIID, String aGUITexture) {
		super(new ContainerCommonDysonSphereMonitor(aInventoryPlayer, aTileEntity, aGUIID), aGUITexture);

	}
	protected void drawGuiContainerForegroundLayer2(int par1, int par2) {
		tickMouseOffset();
		tickRotate();
	}
	protected void tickRotate(){
		if(rotateAngle<0){
			rotateAngleReal=(System.currentTimeMillis()%36000)/100F;
		}
		else {
			if (Math.abs(rotateAngleReal-rotateAngle)<0.5F){
				selectedNodeY=selectedNodeYPre;
				return;
			}
			if (rotateAngleReal<rotateAngle)rotateAngleReal+=(rotateAngle-rotateAngleReal)/36F;
			if (rotateAngleReal>rotateAngle)rotateAngleReal-=(rotateAngleReal-rotateAngle)/36F;
		}
	}
	@Override
	protected void drawGuiContainerBackgroundLayer2(float par1, int par2, int par3) {
		GL11.glPushMatrix();
		this.drawTexturedModalRect(0,0, 0, 0, (int) (width*0.6), height);
		GL11.glPopMatrix();

		if(DimensionManager.getInstance().getStar(selectedStarID)!=null)drawStar(width/3, height/3);

		GL11.glPushMatrix();
		drawRect((int) (width*0.6), 0, width, height, 0xbb383838);
		GL11.glPopMatrix();
	}
	public void drawStar(int x ,int y){
		DysonSphere theDysonSphere =DimensionManager.getInstance().getStar(selectedStarID).dysonSphere;

		if(theDysonSphere!=null)theDysonSphere.drawBehindLayer(x,y+1,50,300,0,0.12F,0.4F,rotateAngleReal);

		if(theDysonSphere!=null)theDysonSphere.drawFrontLayer(x,y+1,50,300,0,0.12F,0.4F,rotateAngleReal);

		GL11.glPushMatrix();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, glTextureId);
		GL11.glTranslatef(x,y,290);
		float[] color = DimensionManager.getInstance().getStar(selectedStarID).getColor();
		GL11.glColor4f(color[0],color[1], color[2], 1.0F);
		GL11.glScalef(40,40,1);
		GL11.glRotatef(rotateAngleReal, 0, 1, 0);
		GL11.glCallList(bodyList);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		mc.getTextureManager().bindTexture(new ResourceLocation(MOD_ID,"textures/gui/DysonSphere/starLight.png"));
		GL11.glColor4f(color[0],color[1], color[2], 0.8F);
		GL11.glTranslatef(x,y,0);
		GL11.glScalef(0.5F,0.5F,1);
		GL11.glTranslatef(-128,-128,290);
		this.drawTexturedModalRect(0,0, 0, 0, 256, 256);
		GL11.glPopMatrix();
		if(theDysonSphere!=null)drawDysonSphereNodePointer(x,y,theDysonSphere);
	}

	public void drawDysonSphereNodePointer(int x,int y,@NotNull DysonSphere theDysonSphere){
		if(selectedNodeY>=0) {
			if(nodePointerClock<height)nodePointerClock+=2;

			float f2 = y - (1.4F * (70 + (theDysonSphere.size * 0.6F)) * (float) Math.cos(3.14 * (selectedNodeY + 11F) / (21 + DysonSphere.lengthYFromSize[theDysonSphere.size])));

			GL11.glPushMatrix();
			GL11.glColor4f(1,1,1,1);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			Tessellator tessellator = Tessellator.instance;
			GL11.glLineWidth(1.3F);
			tessellator.startDrawing(1);
			tessellator.addVertex(x-Math.min(nodePointerClock,50), f2+Math.min(nodePointerClock,50), 420);
			tessellator.addVertex(x, f2, 420);
			if(nodePointerClock>50){
				tessellator.addVertex(x-50, f2+50, 420);
				tessellator.addVertex(x-50, f2+nodePointerClock, 420);
			}
			tessellator.draw();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glPopMatrix();

		}

	}

	public void handleMouseInput(){
		super.handleMouseInput();
		int i = Mouse.getEventX() * this.width / this.mc.displayWidth;
		int j = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
		mouseMove(i,j);
		starButtonWheelOffset+=Mouse.getEventDWheel()/6;
		if(Mouse.isButtonDown(1)){
			float dX=Mouse.getDX();
			float dY=Mouse.getDY();
			if(Math.abs(nodeButtonOffsetX+dX)<300) nodeButtonOffsetX+=dX;
			if(Math.abs(nodeButtonOffsetY-dY)<300)nodeButtonOffsetY-=dY;
		}
	}

	public void tickMouseOffset(){
		int deltaX= (int) ((nodeButtonOffsetX-nodeButtonOffsetXOld)/6);
		int deltaY= (int) ((nodeButtonOffsetY-nodeButtonOffsetYOld)/6);
		int deltaWheelY=(int) ((starButtonWheelOffset-starButtonWheelOffsetOld)/10);

		if(((StarButton) buttonList.get(0)).yPosition+deltaWheelY<5)for(int k=0;k<starButtonsEnd;k++)((StarButton) buttonList.get(k)).yPosition+= deltaWheelY;

		buttonList.forEach(nodeButton->{if(nodeButton instanceof DysonNodeButton) {
			((DysonNodeButton) nodeButton).xPosition+=deltaX;
			((DysonNodeButton) nodeButton).yPosition+=deltaY;
			if(((DysonNodeButton) nodeButton).yPosition+8>height*0.76||((DysonNodeButton) nodeButton).xPosition+8<width*0.6)((DysonNodeButton) nodeButton).enabled=false;
			else ((DysonNodeButton) nodeButton).enabled=true;
		}});
		nodeButtonOffsetXOld+=deltaX;
		nodeButtonOffsetYOld+=deltaY;
		starButtonWheelOffsetOld+=deltaWheelY;
	}
	public void mouseMove(int x, int y){
		buttonList.forEach(button-> {
			if (button instanceof CommonGuiButton)((CommonGuiButton) button).isMouseHover= ((CommonGuiButton) button).isMouseOverButton(x, y);

		});
	}
	public boolean onButtonPressed(GuiButton button) {
		if(button instanceof StarButton&& selectedStarID !=((StarButton)button).starID){
			selectedStarID =((StarButton)button).starID;
			onStarChanged(false);
		}
		if(button instanceof DysonNodeButton){
			buttonList.removeAll(nodeLevelChoosingButtons);
			starButtons.removeAll(nodeLevelChoosingButtons);
			nodePointerClock=selectedNodeYPre=selectedNodeY=-1;

			nodeLevelChoosingButtons.clear();
			rotateAngle=(360.0F / DysonSphere.getValidNodesInARow(DimensionManager.getInstance().getStar(selectedStarID).dysonSphere.size, ((DysonNodeButton) button).nodeY)) * ((DysonNodeButton) button).nodeX;
			selectedNodeYPre=((DysonNodeButton) button).nodeY;
			if(enableCopyPasteMode){

				if(copiedNodeLevel==0){
					copiedNodeLevel= DimensionManager.getInstance().getStar(selectedStarID).dysonSphere.getNodeLevel(((DysonNodeButton)button).nodeX,((DysonNodeButton)button).nodeY);
					copiedNodeType= DimensionManager.getInstance().getStar(selectedStarID).dysonSphere.getNodeType(((DysonNodeButton)button).nodeX,((DysonNodeButton)button).nodeY);
				}
				else if(DysonSphere.getMinimumDysonSphereNodeLevel(DimensionManager.getInstance().getStar(selectedStarID).dysonSphere.size,((DysonNodeButton)button).nodeX,((DysonNodeButton)button).nodeY)<=copiedNodeLevel){
					updateNode(((DysonNodeButton)button).nodeX,((DysonNodeButton)button).nodeY,copiedNodeLevel,copiedNodeType);
				}else {

				}
			} else drawNodeCreatingButtons(DimensionManager.getInstance().getStar(selectedStarID).dysonSphere,((DysonNodeButton)button).nodeX,((DysonNodeButton)button).nodeY);
		}
		if(button instanceof DysonNodeButtonDummy&&((DysonNodeButtonDummy) button).isAvailable){
			if(button.id<=-30&&button.id>-40){
				buttonList.removeAll(nodeLevelChoosingButtons);
				starButtons.removeAll(nodeLevelChoosingButtons);
				nodeLevelChoosingButtons.clear();

				DysonNodeButtonDummy theButton = (DysonNodeButtonDummy)button;

				updateNode(theButton.nodeX,theButton.nodeY, (byte) theButton.nodeLevel, (byte) theButton.nodeType);
			}
			if(button.id<=-20&&button.id>-30){
				nodeLevelChoosingButtons.forEach(nodeLevelButton->{
					if(nodeLevelButton instanceof DysonNodeButtonDummy)((DysonNodeButtonDummy) nodeLevelButton).isAvailable=false;
				});

				DysonNodeButtonDummy theButton = (DysonNodeButtonDummy)button;
				int nodeX=theButton.nodeX;
				int nodeY=theButton.nodeY;
				drawNodeTypeChoosingButtons(theButton.nodeLevel,nodeX,nodeY);
			}
		}
		if(button.id==-1)drawCreateNewDysonSphereButtons();
		if(button.id==-2){
			button.displayString=LH.Chat.RED+"Click "+(DESTROY_BUTTON_REPEAT_COUNT-destroyCount)+" times more to confirm";
			destroyCount++;
			if(destroyCount>DESTROY_BUTTON_REPEAT_COUNT) {
				DimensionManager.getInstance().getStar(selectedStarID).dysonSphere=null;
				onStarChanged(false);
			}
		}
		if(button.id==-4){
			enableCopyPasteMode=!enableCopyPasteMode;
			copiedNodeType=0;
			copiedNodeLevel=0;
		}
		if(button.id<=-10&&button.id>-20){
			DimensionManager.getInstance().getStar(selectedStarID).dysonSphere=new DysonSphere((byte) (Math.abs(button.id)-10));
			onStarChanged(false);
		}
		return true;
	}

	@Override
	public boolean onNoButtonPressed() {
		rotateAngle=selectedNodeYPre=selectedNodeY=nodePointerClock=-1;
		return true;
	}

	public void updateNode(int nodeX,int nodeY,byte nodeLevel,byte nodeType) {
		DimensionManager.getInstance().getStar(selectedStarID).dysonSphere.nodesLevel[nodeY][nodeX]= nodeLevel;
		DimensionManager.getInstance().getStar(selectedStarID).dysonSphere.nodesType[nodeY][nodeX]= nodeType;
		onStarChanged(true);
	}

	int destroyCount=0;
	public void onStarChanged(boolean resumeMouseOffset){
		DysonSphere theDysonSphere= DimensionManager.getInstance().getStar(selectedStarID).dysonSphere;
		destroyCount=0;
		buttonList.removeAll(starButtons);
		starButtons.clear();
		if(theDysonSphere==null) {
			ArrayList<CommonGuiButton> tmpButtons=new ArrayList<CommonGuiButton>();
			tmpButtons.add(new CommonGuiButton(-1, (int) (this.width*0.65), this.height / 2 - 50, (int) (this.width*0.3), 20, "Create new Dyson Sphere"));

			buttonList.addAll(tmpButtons);
			starButtons.addAll(tmpButtons);
		}else {
			drawDysonNodeButtons(theDysonSphere);
		}
			if(resumeMouseOffset)buttonList.forEach(nodeButton->{if(nodeButton instanceof DysonNodeButton) {
				((DysonNodeButton) nodeButton).xPosition+= nodeButtonOffsetX;
				((DysonNodeButton) nodeButton).yPosition+= nodeButtonOffsetY;
				if(((DysonNodeButton) nodeButton).yPosition+8>height*0.76)((DysonNodeButton) nodeButton).enabled=false;
				else ((DysonNodeButton) nodeButton).enabled=true;
			}});
			else {nodeButtonOffsetX=0;nodeButtonOffsetY=0;nodeButtonOffsetYOld=0;nodeButtonOffsetXOld=0;rotateAngle=selectedNodeYPre=selectedNodeY=nodePointerClock=-1;}
	}

	/**Buttons in this list will be removed when selected Star changed**/
	public ArrayList<CommonGuiButton> starButtons =new ArrayList<CommonGuiButton>();
	public void drawCreateNewDysonSphereButtons() {
		ArrayList<CommonGuiButton> dysonSphereSizes=new ArrayList<CommonGuiButton>();
		dysonSphereSizes.add(new CommonGuiButton(-10, (int) (this.width*0.64), this.height / 2, (int) (this.width*0.05), 20, "0"));
		dysonSphereSizes.add(new CommonGuiButton(-11, (int) (this.width*0.71), this.height / 2, (int) (this.width*0.05), 20, "1"));
		dysonSphereSizes.add(new CommonGuiButton(-12, (int) (this.width*0.78), this.height / 2, (int) (this.width*0.05), 20, "2"));
		dysonSphereSizes.add(new CommonGuiButton(-13, (int) (this.width*0.85), this.height / 2, (int) (this.width*0.05), 20, "3"));
		dysonSphereSizes.add(new CommonGuiButton(-14, (int) (this.width*0.92), this.height / 2, (int) (this.width*0.05), 20, "4"));
		buttonList.addAll(dysonSphereSizes);
		starButtons.addAll(dysonSphereSizes);
	}

	public void drawDysonNodeButtons(DysonSphere theDysonSphere){
		if(theDysonSphere!=null){
			ArrayList<CommonGuiButton> tmpButtons=new ArrayList<>();
			boolean isDysonSphereBuilded=false;
			for(int y=0;y<DysonSphere.lengthYFromSize[theDysonSphere.size];y++)for(int x=0;x<DysonSphere.lengthXFromSize[theDysonSphere.size];x++){
				if(DysonSphere.getMinimumDysonSphereNodeLevel(theDysonSphere.size,x,y)>=0){
					int gap=16-theDysonSphere.size;
					DysonNodeButton nodeButton= new DysonNodeButton(starButtonsEnd+x+y*DysonSphere.lengthYFromSize[theDysonSphere.size]+1, (int) ((width*0.8)+(x-(DysonSphere.lengthXFromSize[theDysonSphere.size]/2))*gap), (int) ((height*0.3)+(y-(DysonSphere.lengthYFromSize[theDysonSphere.size]/2))*gap),x,y,theDysonSphere.nodesLevel[y][x],theDysonSphere.nodesType[y][x],theDysonSphere.nodesBuildingProgress[y][x],width,height);
					if(!isDysonSphereBuilded&&theDysonSphere.nodesBuildingProgress[y][x]>0)isDysonSphereBuilded=true;
					tmpButtons.add(nodeButton);
				}
			}
			int buttonWidth= this.width>800?240: (int) (this.width * 0.3);
			tmpButtons.add(new CommonGuiButton(-2, (int) ((this.width*0.78)-(buttonWidth*0.5)), this.height -20, buttonWidth, 20, isDysonSphereBuilded?LH.Chat.RED:LH.Chat.ORANGE+"Destroy this Dyson Sphere"));
			tmpButtons.add(new CommonGuiButton(-4, this.width-20,this.height-20,20, 20, ""));
			buttonList.addAll(tmpButtons);
			starButtons.addAll(tmpButtons);
		}
	}

	ArrayList<CommonGuiButton> nodeLevelChoosingButtons =new ArrayList<CommonGuiButton>();
	public void drawNodeCreatingButtons(DysonSphere theDysonSphere,int nodeX,int nodeY){
		nodeLevelChoosingButtons.add(new DysonNodeButtonDummy(-20, (int) (this.width*0.65)-8, (int) (this.height -62), 1,0,DysonSphere.getMinimumDysonSphereNodeLevel(theDysonSphere.size,nodeX,nodeY)<=1, nodeX, nodeY,width,height));
		nodeLevelChoosingButtons.add(new DysonNodeButtonDummy(-21, (int) (this.width*0.71)-8, (int) (this.height -62), 2,0,DysonSphere.getMinimumDysonSphereNodeLevel(theDysonSphere.size,nodeX,nodeY)<=2, nodeX, nodeY,width,height));
		nodeLevelChoosingButtons.add(new DysonNodeButtonDummy(-22, (int) (this.width*0.77)-8, (int) (this.height -62), 3,0,DysonSphere.getMinimumDysonSphereNodeLevel(theDysonSphere.size,nodeX,nodeY)<=3, nodeX, nodeY,width,height));
		nodeLevelChoosingButtons.add(new DysonNodeButtonDummy(-23, (int) (this.width*0.83)-8, (int) (this.height -62), 4,0,DysonSphere.getMinimumDysonSphereNodeLevel(theDysonSphere.size,nodeX,nodeY)<=4, nodeX, nodeY,width,height));
		nodeLevelChoosingButtons.add(new DysonNodeButtonDummy(-24, (int) (this.width*0.89)-8, (int) (this.height -62), 5,0,DysonSphere.getMinimumDysonSphereNodeLevel(theDysonSphere.size,nodeX,nodeY)<=5, nodeX, nodeY,width,height));
		nodeLevelChoosingButtons.add(new DysonNodeButtonDummy(-25, (int) (this.width*0.95)-8, (int) (this.height -62), 6,0,DysonSphere.getMinimumDysonSphereNodeLevel(theDysonSphere.size,nodeX,nodeY)<=6, nodeX, nodeY,width,height));
		nodeLevelChoosingButtons.add(new customText(-3,"Select Level of Node ("+nodeY+","+DysonSphere.getDisplayNodeX(theDysonSphere.size,nodeX,nodeY)+")",(int) (this.width*0.8), (int) (this.height )-74,true));
		buttonList.addAll(nodeLevelChoosingButtons);
		starButtons.addAll(nodeLevelChoosingButtons);
	}


	ArrayList<CommonGuiButton> nodeTypeChoosingButtons =new ArrayList<CommonGuiButton>();
	public void drawNodeTypeChoosingButtons(int nodeLevel,int nodeX,int nodeY){
		nodeLevelChoosingButtons.add(new DysonNodeButtonDummy(-30, (int) (this.width*0.65)-8, (int) (this.height -40), nodeLevel,0,true, nodeX, nodeY,width,height));
		nodeLevelChoosingButtons.add(new DysonNodeButtonDummy(-31, (int) (this.width*0.71)-8, (int) (this.height -40), nodeLevel,1,true, nodeX, nodeY,width,height));
		nodeLevelChoosingButtons.add(new DysonNodeButtonDummy(-32, (int) (this.width*0.77)-8, (int) (this.height -40), nodeLevel,2,true, nodeX, nodeY,width,height));
		nodeLevelChoosingButtons.add(new DysonNodeButtonDummy(-33, (int) (this.width*0.83)-8, (int) (this.height -40), nodeLevel,3,true, nodeX, nodeY,width,height));
		nodeLevelChoosingButtons.add(new DysonNodeButtonDummy(-34, (int) (this.width*0.89)-8, (int) (this.height -40), nodeLevel,4,true, nodeX, nodeY,width,height));
		nodeLevelChoosingButtons.add(new DysonNodeButtonDummy(-35, (int) (this.width*0.95)-8, (int) (this.height -40), nodeLevel,5,true, nodeX, nodeY,width,height));
		buttonList.addAll(nodeLevelChoosingButtons);
		starButtons.addAll(nodeLevelChoosingButtons);
	}

}
