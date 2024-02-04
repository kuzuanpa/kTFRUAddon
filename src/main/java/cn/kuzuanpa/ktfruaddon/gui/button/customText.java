package cn.kuzuanpa.ktfruaddon.gui.button;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class customText extends CommonGuiButton {
    int color;
    boolean isTextCentered;
    public customText(int id, String text, int posX, int posY,boolean isTextCentered){
        super(id,posX,posY,text.length(),12,text);
        this.color=-1;
        this.isTextCentered=isTextCentered;
    }
    public customText(int id, String text, int posX, int posY, int color,boolean isTextCentered){
        super(id,posX,posY,text.length()*5,12,text);
        this.color=color;
        this.isTextCentered=isTextCentered;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY){
        if (this.visible) {
            GL11.glPushMatrix();
            if(isTextCentered)this.drawCenteredString(Minecraft.getMinecraft().fontRenderer, displayString, xPosition, yPosition, color);
            else this.drawString(Minecraft.getMinecraft().fontRenderer, displayString, xPosition, yPosition, color);
            GL11.glPopMatrix();
        }
    }

}
