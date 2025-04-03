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

import cn.kuzuanpa.kGuiLib.client.kGuiContainerBase;
import cn.kuzuanpa.kGuiLib.client.objects.gui.kGuiButtonBase;
import cn.kuzuanpa.ktfruaddon.api.nei.IHiddenNei;
import cn.kuzuanpa.ktfruaddon.tile.research.ResearchTableFillInPack;
import gregapi.tileentity.ITileEntityInventoryGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class ContainerClientFillThePack extends kGuiContainerBase implements IHiddenNei {
    private final ContainerCommonFillThePack mContainer;
    private ResearchTableFillInPack.PuzzleGame theGame = new ResearchTableFillInPack.PuzzleGame(12,5);
    public ContainerClientFillThePack(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, int aGUIID, String aGUITexture) {
        super(new ContainerCommonFillThePack(aInventoryPlayer, aTileEntity,aGUIID));

        this.mContainer= (ContainerCommonFillThePack) inventorySlots;
    }
    final ResourceLocation background = new ResourceLocation(MOD_ID,"textures/gui/research/background.png");
    final ResourceLocation main = new ResourceLocation(MOD_ID,"textures/gui/research/main.png");
    final Random rng = new Random();
    protected int totalSize = 180;
    protected int puzzleSize = totalSize/(theGame.size+1);
    ShapeButton selectedButton, selectedButtonOld;
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
        if(selectedButton != null) tickSelectedButton();
        if(selectedButtonOld != null) tickSelectedButtonOld();
        if(selectedButtonOld != null) tryDisposeOldSelectedButton();
        GL11.glColor4f(1,1,1,1);
        super.drawScreen2(p_73863_1_, p_73863_2_, p_73863_3_);
    }

    float buttonX = -1 , buttonY = -1, buttonToGoX = -1, buttonToGoY = -1, buttonOldX= -1, buttonOldY = -1, buttonOldToGoX = -1, buttonOldToGoY = -1;
    int mouseStartX=-1,mouseStartY=-1, buttonOriginX = -1, buttonOriginY= -1, currentFocusX = -1, currentFocusY = -1;
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
        if(selectedButton.shape.placedOnX != -1 && !isPosInGround(mouseX,mouseY)) expireSelectedButton(4, 4);
        else tryPlaceShapeOnGround();
    }

    public boolean isPosInGround(int x,int y){
        return (x >= (width - totalSize) / 2 && x <= (width + totalSize) / 2 && y >= 16 && y <= 16 + totalSize);
    }

    public void tryPlaceShapeOnGround(){
        if(theGame.placeTile(selectedButton.shape, currentFocusX, currentFocusY)) {
            expireSelectedButton((width-totalSize)/2 + currentFocusX*puzzleSize, 16 + currentFocusY*puzzleSize);
            theGame.checkWin();
        }else{
            expireSelectedButton(buttonOriginX, buttonOriginY);
        }
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
        for (int x = 0; x < theGame.size; x++) for (int y = 0; y < theGame.size; y++) buttons.add(new SlotButton(i.getAndIncrement(), (width-totalSize)/2 + x*puzzleSize,16 + y*puzzleSize,puzzleSize, x,y));
        for (ResearchTableFillInPack.PuzzleGame.PuzzleShape shape : theGame.shuffledTiles)buttons.add(new ShapeButton(i.getAndIncrement(), 8, 8, puzzleSize, shape ));
    }

    @Override
    public boolean onButtonPressed(GuiButton button, int mouseX, int mouseY) {
        if(button instanceof ShapeButton){
            selectButton((ShapeButton) button);
            mouseStartX=mouseX;
            mouseStartY=mouseY;
            return true;
        }
        return false;
    }
    public void selectButton(ShapeButton button){
        selectedButton = button;
        buttonX = buttonOriginX = selectedButton.xPosition;
        buttonY = buttonOriginY = selectedButton.yPosition;

        if(selectedButton.shape.placedOnX > -1)theGame.removeTile(selectedButton.shape);
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
        public SlotButton(int id, int drawPosX, int drawPosY, int size, int x, int y) {
            super(id, drawPosX, drawPosY, size, size, "");
            this.x=x;
            this.y=y;
            setAnimatedInFBO(true);
        }

        public final int x, y;
        public boolean isTaken =false;

        @Override
        public void drawButton2(Minecraft mc, int mouseX, int mouseY) {
            if (!visible) return;

            Tessellator tessellator = Tessellator.instance;

            GL11.glEnable(GL11.GL_ALPHA_TEST);
            mc.getTextureManager().bindTexture(main);

            GL11.glColor4f(1, 1, 1, 1);
            if(selectedButton!=null && selectedButton.shape.content.stream().anyMatch(pos->this.isMouseInButton(selectedButton.xPosition+pos.x*puzzleSize+puzzleSize/2,selectedButton.yPosition+pos.y*puzzleSize+puzzleSize/2)))GL11.glColor4f(0.8F,1.0F,0.8F,1.0F);
            if(selectedButton!=null && this.isMouseInButton(selectedButton.xPosition+puzzleSize/2,selectedButton.yPosition+puzzleSize/2)) {
                currentFocusX = x;
                currentFocusY = y;
            }
            drawTextureRect(tessellator, xPosition, yPosition, 8,8, puzzleSize,puzzleSize);

            GL11.glColor4f(1, 1, 1, 1);
        }
    }
    public class ShapeButton extends kGuiButtonBase {
        public ShapeButton(int id, int drawPosX, int drawPosY, int size, ResearchTableFillInPack.PuzzleGame.PuzzleShape shape) {
            super(id, drawPosX, drawPosY, size * shape.width, size * shape.height, "");
            this.shape=shape;
            setAnimatedInFBO(true);
        }

        public final ResearchTableFillInPack.PuzzleGame.PuzzleShape shape;
        public boolean isTaken =false;

        @Override
        public void drawButton2(Minecraft mc, int mouseX, int mouseY) {
            if (!visible) return;

            Tessellator tessellator = Tessellator.instance;

            GL11.glEnable(GL11.GL_ALPHA_TEST);
            mc.getTextureManager().bindTexture(main);
            GL11.glColor4f(1, 1, 1, 1);

            shape.content.forEach(p->drawTextureRect(tessellator, xPosition + p.x*puzzleSize, yPosition + p.y*puzzleSize, 0,0, puzzleSize,puzzleSize));

            GL11.glColor4f(1, 1, 1, 1);
        }
    }

}
