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

package cn.kuzuanpa.ktfruaddon.client.gui.research;

import cn.kuzuanpa.kGuiLib.client.anime.animeMoveLinear;
import cn.kuzuanpa.kGuiLib.client.anime.animeMoveSlowIn;
import cn.kuzuanpa.kGuiLib.client.anime.shortcut.animeTransparency;
import cn.kuzuanpa.kGuiLib.client.kGuiContainerBase;
import cn.kuzuanpa.kGuiLib.client.objects.gui.Text;
import cn.kuzuanpa.kGuiLib.client.objects.gui.kGuiButtonBase;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.kUII18n;
import cn.kuzuanpa.ktfruaddon.api.nei.IHiddenNei;
import cn.kuzuanpa.ktfruaddon.api.network.PacketContainerButtonPressed;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import cn.kuzuanpa.ktfruaddon.tile.research.ResearchTableFillInPack;
import gregapi.data.LH;
import gregapi.tileentity.ITileEntityInventoryGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.concurrent.atomic.AtomicInteger;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;
import static cn.kuzuanpa.ktfruaddon.ktfruaddon.kNetworkHandler;

public class ContainerClientFillThePack extends kGuiContainerBase implements IHiddenNei {
    private final ContainerCommonFillThePack mContainer;
    public ResearchTableFillInPack.PuzzleGame theGame;
    public ContainerClientFillThePack(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, int aGUIID) {
        super(new ContainerCommonFillThePack(aInventoryPlayer, aTileEntity,aGUIID));
        this.mContainer= (ContainerCommonFillThePack) inventorySlots;
        theGame = ((ResearchTableFillInPack) mContainer.mTileEntity).theGameClient;
        puzzleSize =  totalSize/(theGame.size+1);
    }
    public void reinitGame(){
        theGame = ((ResearchTableFillInPack) mContainer.mTileEntity).theGameClient;
        puzzleSize =  totalSize/(theGame.size+1);
        initGui();
    }
    final ResourceLocation background = new ResourceLocation(MOD_ID,"textures/gui/research/background.png");
    final ResourceLocation main = new ResourceLocation(MOD_ID,"textures/gui/research/main.png");

    protected int totalSize = 180;
    protected int puzzleSize;
    ShapeButton selectedButton, selectedButtonOld;
    public ResearchCommonElements.CurrentPanel currentPanel= null;

    float buttonX = -1 , buttonY = -1, buttonToGoX = -1, buttonToGoY = -1, buttonOldX= -1, buttonOldY = -1, buttonOldToGoX = -1, buttonOldToGoY = -1;
    int mouseStartX=-1,mouseStartY=-1, buttonOriginX = -1, buttonOriginY= -1, currentFocusX = -1, currentFocusY = -1;
    public Text text = null;
    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        mc.getTextureManager().bindTexture(background);
        GL11.glColor4f(1,1,1,0.1f);
        currentFocusX = -1; currentFocusY = -1;
        this.drawTexturedModalRect(0,0, 0, 14, width, height);
    }

    @Override
    public void drawScreen2(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        if(selectedButton != null) tickSelectedButton();
        if(selectedButtonOld != null) tickSelectedButtonOld();
        if(selectedButtonOld != null) tryDisposeOldSelectedButton();
        if(((ResearchTableFillInPack) mContainer.mTileEntity).clientGameUpdated && ((ResearchTableFillInPack) mContainer.mTileEntity).theGameClient.size>0){
            reinitGame();
            ((ResearchTableFillInPack) mContainer.mTileEntity).clientGameUpdated = false;
        }
        GL11.glColor4f(1,1,1,1);

        currentPanel.currentProject = ((ResearchTableFillInPack) mContainer.mTileEntity).getCurrentProject();
        super.drawScreen2(p_73863_1_, p_73863_2_, p_73863_3_);
    }

    @Override
    public void handleMouseInput2(int mouseX,int mouseY) {
        super.handleMouseInput2(mouseX, mouseY);
        if(Mouse.isButtonDown(0)){
            if(selectedButton == null) return;
            //apply mouse move to SelectedButton
            int dX = mouseX-mouseStartX;
            int dY = mouseY- mouseStartY;
            buttonToGoX=buttonOriginX+dX;
            buttonToGoY=buttonOriginY+dY;
        }else onLeftHoldReleased(mouseX,mouseY);
    }
    public void onLeftHoldReleased(int mouseX,int mouseY){
        if(selectedButton==null)return;
        if(!tryPlaceShapeOnGround()) expireSelectedButton(mouseX<width/2?4:width-4-selectedButton.width, mouseY);
    }

    public boolean isPosInGround(int x,int y){
        return (x >= (width - totalSize) / 2 && x <= (width + totalSize) / 2 && y >= 16 && y <= 16 + totalSize);
    }

    public boolean tryPlaceShapeOnGround(){
        if(currentFocusX == -1)return false;
        TileEntity t = (TileEntity) mContainer.mTileEntity;
        if (!theGame.placeTile(selectedButton.shape, currentFocusX, currentFocusY, false)) return false;

        kNetworkHandler.sendToServer(new PacketContainerButtonPressed(utils.dimID(t.getWorldObj()), t.xCoord,t.yCoord,t.zCoord,1, (byte) selectedButton.shapeID, (byte)currentFocusX, (byte)currentFocusY)) ;
        expireSelectedButton((width-totalSize)/2 + currentFocusX*puzzleSize, 16 + currentFocusY*puzzleSize);
        if(theGame.checkWin(true) && text != null)text.text = LH.get(kUII18n.RESEARCH_TABLE_FILL_WIN) +": "+ theGame.calculateScore();
        return true;
    }

    public void tickSelectedButton(){
        float deltaX = ((buttonToGoX - buttonX) / 4f);
        float deltaY = ((buttonToGoY - buttonY) / 4f);
        buttonX += deltaX;
        buttonY += deltaY;
        selectedButton.xPosition = (int) buttonX;
        selectedButton.yPosition = (int) buttonY;
    }

    public void tickSelectedButtonOld(){
        float deltaXOld= ((buttonOldToGoX-buttonOldX)/4f);
        float deltaYOld= ((buttonOldToGoY-buttonOldY)/4f);
        buttonOldX+=deltaXOld;
        buttonOldY+=deltaYOld;
        selectedButtonOld.xPosition = (int)buttonOldX;
        selectedButtonOld.yPosition = (int)buttonOldY;
    }
    @Override
    public void addButtons() {
        AtomicInteger i = new AtomicInteger();
        buttons.add(new kGuiButtonBase(i.getAndIncrement(),0,0,20,20,"x"));
        text = new Text(i.getAndIncrement(),LH.get(kUII18n.RESEARCH_TABLE_FILL_TITLE) +": "+ theGame.calculateScore(),20, (int) (height*0.9));
        buttons.add(text);

        currentPanel = (ResearchCommonElements.CurrentPanel) new ResearchCommonElements.CurrentPanel(this,i.getAndIncrement(), 96).setJoinLeaveTime(200,Integer.MAX_VALUE).addAnime(new animeTransparency(200,800,0,255)).addAnime(new animeMoveLinear(-1,0,120,0)).addAnime(new animeMoveSlowIn(200,800, -120,0,3f));
        buttons.add(currentPanel);
        //for (byte x = 0; x < theGame.size; x++) for (byte y = 0; y < theGame.size; y++) buttons.add(new SlotButton(i.getAndIncrement(), (width-totalSize)/2 + x*puzzleSize,16 + y*puzzleSize,puzzleSize, x,y));
        buttons.add(new SlotButton(i.getAndIncrement(), (width-totalSize)/2 ,16, totalSize));

        for (byte j = 0;j < theGame.tiles.size(); j++) {
            ResearchTableFillInPack.PuzzleGame.PuzzleShape shape = theGame.tiles.get(j);
            buttons.add(new ShapeButton(i.getAndIncrement(), shape.placedOnX == -1? 8 : (width-totalSize)/2 + shape.placedOnX*puzzleSize, shape.placedOnY == -1 ? 8: 16 + shape.placedOnY*puzzleSize, puzzleSize, shape, j));
        }
    }

    @Override
    public boolean onButtonPressed(GuiButton button, int mouseX, int mouseY) {
        TileEntity t = (TileEntity) mContainer.mTileEntity;
        if(button.id==0)kNetworkHandler.sendToServer(new PacketContainerButtonPressed(utils.dimID(t.getWorldObj()), t.xCoord,t.yCoord,t.zCoord,button.id));
        if(button instanceof ShapeButton){
            selectButton((ShapeButton) button, mouseX, mouseY);
            mouseStartX=mouseX;
            mouseStartY=mouseY;
            return true;
        }
        return false;
    }
    public void selectButton(ShapeButton button, int mouseX, int mouseY){
        selectedButton = button;
        buttonX = buttonOriginX = selectedButton.xPosition;
        buttonY = buttonOriginY = selectedButton.yPosition;
        TileEntity t = (TileEntity) mContainer.mTileEntity;
        if(isPosInGround(mouseX, mouseY)){
            theGame.removeTile(selectedButton.shape);
            kNetworkHandler.sendToServer(new PacketContainerButtonPressed(utils.dimID(t.getWorldObj()), t.xCoord,t.yCoord,t.zCoord, -1, (byte)button.shapeID));
        }
    }
    public void tryDisposeOldSelectedButton(){
        if(Math.abs(selectedButtonOld.xPosition - buttonOriginX)<2 && Math.abs(selectedButtonOld.yPosition - buttonOriginY)<2 && !Mouse.isButtonDown(0)){
            selectedButtonOld.xPosition = buttonOriginX;
            selectedButtonOld.yPosition = buttonOriginY;
            selectedButtonOld = null;
            buttonX = -1 ; buttonY = -1; buttonToGoX = -1; buttonToGoY = -1; buttonOriginX = -1; buttonOriginY= -1; currentFocusX=-1;currentFocusY=-1;
        }
    }
    public void expireSelectedButton(int moveToX, int moveToY){
        buttonOriginX=moveToX;
        buttonOriginY=moveToY;
        buttonOldToGoX=moveToX;
        buttonOldToGoY=moveToY;
        buttonOldX=buttonX;
        buttonOldY=buttonY;
        selectedButtonOld = selectedButton;
        selectedButton = null;
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
    public class SlotButton extends kGuiButtonBase {
        public SlotButton(int id, int drawPosX, int drawPosY, int size) {
            super(id, drawPosX, drawPosY, size, size, "");
            setAnimatedInFBO(true);
            GL11.glNewList(glListID =GL11.glGenLists(1), GL11.GL_COMPILE);
            Tessellator tessellator = Tessellator.instance;
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glColor4f(1, 1, 1, 1);
            for (byte x = 0; x < theGame.size; x++) for (byte y = 0; y < theGame.size; y++) drawTextureRect(tessellator, xPosition +  x*puzzleSize, yPosition +  y*puzzleSize, 8, 8, puzzleSize, puzzleSize);
            GL11.glEndList();
        }

        public final int glListID;

        @Override
        public void drawButton2(Minecraft mc, int mouseX, int mouseY) {
            if (!visible) return;

            mc.getTextureManager().bindTexture(main);

            GL11.glCallList(glListID);

            if(selectedButton != null)for (byte x = 0; x < theGame.size; x++) for (byte y = 0; y < theGame.size; y++) {
                int focusScreenX = selectedButton.xPosition+puzzleSize/2;
                int focusScreenY = selectedButton.yPosition+puzzleSize/2;
                if(xPosition + x*puzzleSize < focusScreenX && xPosition +  x*puzzleSize + puzzleSize >= focusScreenX && yPosition +  y*puzzleSize < focusScreenY && yPosition +  y*puzzleSize + puzzleSize >= focusScreenY){
                    currentFocusX = x;
                    currentFocusY = y;
                }
            }

            GL11.glColor4f(1, 1, 1, 1);
        }

        @Override
        public void destroy() {
            super.destroy();
            TileEntity t = (TileEntity) mContainer.mTileEntity;
            if(theGame.ended)kNetworkHandler.sendToServer(new PacketContainerButtonPressed(utils.dimID(t.getWorldObj()), t.xCoord,t.yCoord,t.zCoord,-100));

            GL11.glDeleteLists(glListID, 1);
        }
    }
    public class ShapeButton extends kGuiButtonBase {
        public ShapeButton(int id, int drawPosX, int drawPosY, int size, ResearchTableFillInPack.PuzzleGame.PuzzleShape shape, int shapeID) {
            super(id, drawPosX, drawPosY, size * shape.width, size * shape.height, "");
            this.shape=shape;
            this.shapeID=shapeID;
            setAnimatedInFBO(true);
            GL11.glNewList(glListID =GL11.glGenLists(1), GL11.GL_COMPILE);

            Tessellator tessellator = Tessellator.instance;

            shape.content.forEach(p->drawTextureRect(tessellator,  p.x*puzzleSize, p.y*puzzleSize, 0,0, puzzleSize,puzzleSize));

            GL11.glEndList();
        }

        public final ResearchTableFillInPack.PuzzleGame.PuzzleShape shape;
        public final int shapeID, glListID;

        @Override
        public void drawButton2(Minecraft mc, int mouseX, int mouseY) {
            if (!visible) return;
            if(currentFocusX!=-1 && selectedButton == this && theGame.placeTile(selectedButton.shape, currentFocusX, currentFocusY, true)){
                GL11.glPushMatrix();
                mc.getTextureManager().bindTexture(main);
                GL11.glTranslatef((mc.currentScreen.width-totalSize)/2F + currentFocusX*puzzleSize, 16 + currentFocusY*puzzleSize,0);
                GL11.glColor4f(0.4F, 1.0F, 0.4F, 0.7F);
                GL11.glCallList(glListID);
                GL11.glColor4f(1, 1, 1, 1);
                GL11.glPopMatrix();
            }

            GL11.glPushMatrix();
            mc.getTextureManager().bindTexture(main);
            GL11.glTranslatef(xPosition,yPosition,0);
            GL11.glCallList(glListID);
            GL11.glColor4f(1, 1, 1, 1);
            GL11.glPopMatrix();
        }
        @Override
        public void destroy() {
            super.destroy();
            GL11.glDeleteLists(glListID, 1);
        }
    }

}
