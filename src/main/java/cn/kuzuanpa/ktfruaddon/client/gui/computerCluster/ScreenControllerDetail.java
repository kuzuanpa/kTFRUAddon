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
 *
 */
package cn.kuzuanpa.ktfruaddon.client.gui.computerCluster;

import cn.kuzuanpa.kGuiLib.client.anime.animeMoveLinear;
import cn.kuzuanpa.kGuiLib.client.anime.animeMoveSlowIn;
import cn.kuzuanpa.kGuiLib.client.anime.animeRGBA;
import cn.kuzuanpa.kGuiLib.client.anime.shortcut.animeFadeIn;
import cn.kuzuanpa.kGuiLib.client.anime.shortcut.animeTransparency;
import cn.kuzuanpa.kGuiLib.client.kGuiScreenContainerLayerBase;
import cn.kuzuanpa.kGuiLib.client.objects.gui.ButtonList;
import cn.kuzuanpa.kGuiLib.client.objects.gui.CommonTexturedButton;
import cn.kuzuanpa.kGuiLib.client.objects.gui.Text;
import cn.kuzuanpa.kGuiLib.client.objects.gui.kGuiButtonBase;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputerClusterClientData;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants;
import gregapi.data.LH;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class ScreenControllerDetail extends kGuiScreenContainerLayerBase {

    protected CommonTexturedButton stateButton =null;
    protected Text stateTextButton  = null, fullLineEvent= null, controllerProvidingButton= null, clusterTotalButton     = null;
    protected ButtonList controllerEventListButton = null;

    protected ComputePower computing = ComputePower.Normal;
    protected byte controllerState = 0;
    protected long controllerProviding = 0,clusterTotal=0;

    protected Text stateInfo0= null, stateInfo1= null, stateInfo2= null, stateInfo3 = null;

    public ScreenControllerDetail updateFromData(ComputerClusterClientData.ControllerDetail data){
        if(data == null)return this;
        updateController(data.controllerState,data.computing,data.controllerProviding,data.clusterTotal,data.events, data.eventExtra);
        return this;
    }

    public ScreenControllerDetail updateController(byte controllerState, byte computing, long controllerProviding, long clusterTotal, byte[] events, String[] eventExtra){
        this.controllerState =controllerState;
        this.computing= ComputePower.getType(computing);
        this.controllerProviding=controllerProviding;
        this.clusterTotal=clusterTotal;
        syncStateButton();
        controllerEventListButton.clearSubButton();
        for (int i = 0; i < events.length; i++) {
            String fullText = Constants.getControllerEventDesc(events[i], i < eventExtra.length ? eventExtra[i] : "");
            String shortText = Constants.getControllerEventShortDesc(events[i]);
            kGuiButtonBase button = new ControllerEventButton(20+i, ContainerX+170, 14 + ContainerY, 104, shortText, fullText).setFBOOffset(0, i*EVENT_LINE_HEIGHT).setJoinLeaveTime(i*70,Integer.MAX_VALUE).addAnime(new animeMoveLinear(-1,0, 50,0)).addAnime(new animeTransparency(-1,0,255,-255)).addAnime(new animeMoveSlowIn(i*70, 800+i*70,-50,0,2)).addAnime(new animeRGBA(i*70,600+i*70,255,255,255,55,-150,-150,-150,200));
            controllerEventListButton.addSubButton(button);
        }
        controllerEventListButton.setMaxScrolled(Math.max(0, events.length*EVENT_LINE_HEIGHT - EVENT_LIST_HEIGHT));
        return this;
    }

    /**Height of one event line and of the visible event list, they define how far the list may scroll.**/
    protected static final int EVENT_LINE_HEIGHT = 10, EVENT_LIST_HEIGHT = 130;

    protected void syncStateButton(){
        switch (controllerState){
            case 0:
                stateButton.u =0;
                stateTextButton.text = LH.get(I18nHandler.OFFLINE);
                break;
            case 1:
                stateButton.u =16;
                stateTextButton.text = LH.get(I18nHandler.NORMAL);
                break;
            case 2:
                stateButton.u =32;
                stateTextButton.text = LH.get(I18nHandler.WARNING);
                break;
            default:
                stateButton.u =48;
                stateTextButton.text = LH.get(I18nHandler.ERROR);
        }
        controllerProvidingButton.text = String.valueOf(controllerProviding);
        clusterTotalButton.text = String.valueOf(clusterTotal);
    }

    @Override
    public void addButtons() {
        buttons.add(new CommonTexturedButton(-1,ContainerX,ContainerY,0,0,226,146, MOD_ID, "textures/gui/computerCluster/controllerOverview.png").setAnimatedInFBO(true).addAnime(new animeMoveLinear(-1,0,50,0)).addAnime(new animeMoveSlowIn(0, 300,-50,0,2)).addAnime(new animeFadeIn(300)));

        buttons.add(new Text(1,LH.get(I18nHandler.COMPUTE_CLUSTER_UI_CONTROLLER_OVERVIEW),ContainerX+4,ContainerY+3).addAnime(new animeMoveLinear(-1,0, 50,0)).addAnime(new animeMoveSlowIn(0, 600,-50,0,2)).addAnime(new animeRGBA(0,400,255,255,255,55,-150,-150,-150,200)));

        int animeTimeSection1 = 900;

        String str;
        stateButton = new CommonTexturedButton(10,ContainerX+28,ContainerY+20,0,146,16,16, MOD_ID, "textures/gui/computerCluster/controllerOverview.png");
        buttons.add(stateButton.setAnimatedInFBO(true).addAnime(new animeMoveLinear(-1,0, 50,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection1,-50,0,2)).addAnime(new animeFadeIn(animeTimeSection1)));

        str = LH.get(I18nHandler.COMPUTE_CLUSTER_UI_LABEL_STATE) + ": ";
        buttons.add(new Text(2,str,ContainerX+36-fontRendererObj.getStringWidth(str),ContainerY+44).addAnime(new animeMoveLinear(-1,0, 50,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection1,-50,0,2)).addAnime(new animeRGBA(0,animeTimeSection1,255,255,255,55,-150,-150,-150,200)));

        stateTextButton =new Text(11,LH.get(I18nHandler.OFFLINE),ContainerX+38,ContainerY+44);
        buttons.add(stateTextButton.addAnime(new animeMoveLinear(-1,0, 50,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection1,-50,0,2)).addAnime(new animeRGBA(0,animeTimeSection1,255,255,255,55,-150,-150,-150,200)));

        int animeTimeSection2 = 1300;
        str = LH.get(I18nHandler.COMPUTE_CLUSTER_UI_LABEL_CONTROLLER_PROVIDING) + ": ";
        buttons.add(new Text(3,str,ContainerX+6,ContainerY+64).addAnime(new animeMoveLinear(-1,0, 80,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection2,-80,0,2)).addAnime(new animeRGBA(0,animeTimeSection2,255,255,255,55,-150,-150,-150,200)));

        controllerProvidingButton=new Text(12,String.valueOf(controllerProviding),ContainerX+6+fontRendererObj.getStringWidth(str),ContainerY+64);
        buttons.add(controllerProvidingButton.addAnime(new animeMoveLinear(-1,0, 80,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection2,-80,0,2)).addAnime(new animeRGBA(0,animeTimeSection2,255,255,255,55,-150,-150,-150,200)));

        str = LH.get(I18nHandler.COMPUTE_CLUSTER_UI_LABEL_CLUSTER_TOTAL) + ": ";
        buttons.add(new Text(4,str,ContainerX+6,ContainerY+76).addAnime(new animeMoveLinear(-1,0, 80,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection2,-80,0,2)).addAnime(new animeRGBA(0,animeTimeSection2,255,255,255,55,-150,-150,-150,200)));

        clusterTotalButton=new Text(13,String.valueOf(clusterTotal),ContainerX+6+fontRendererObj.getStringWidth(str),ContainerY+76);
        buttons.add(clusterTotalButton.addAnime(new animeMoveLinear(-1,0, 80,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection2,-80,0,2)).addAnime(new animeRGBA(0,animeTimeSection2,255,255,255,55,-150,-150,-150,200)));

        buttons.add(new ControllerOverviewChartButton(5,ContainerX+4,ContainerY+88,154,8).addAnime(new animeMoveLinear(-1,0, 80,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection2,-80,0,2)).addAnime(new animeFadeIn(animeTimeSection2)));

        str = LH.get(I18nHandler.COMPUTE_CLUSTER_UI_LABEL_EVENT_LOG);
        buttons.add(new Text(6, str,ContainerX+196 - fontRendererObj.getStringWidth(str)/2,ContainerY+4).addAnime(new animeMoveLinear(-1,0, 80,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection2,-80,0,2)).addAnime(new animeRGBA(0,animeTimeSection2,255,255,255,55,-150,-150,-150,200)));

        controllerEventListButton = new ButtonList(7,ContainerX+170,ContainerY+14,104,130);

        buttons.add(controllerEventListButton.addAnime(new animeMoveLinear(-1,0, 80,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection2,-80,0,2)).addAnime(new animeFadeIn(animeTimeSection2)));

        fullLineEvent = new Text(14,"",ContainerX+6,ContainerY+90,0x00000000);
        buttons.add(fullLineEvent);
    }

    @Override
    public void onKeyTyped(char c, int i) {
        if(i == Keyboard.KEY_E|| i == Keyboard.KEY_ESCAPE)close();
    }

    /**The hovered event line is resolved while drawing, so the label has to be cleared before every frame.**/
    @Override
    public void drawScreen2(int mouseX, int mouseY, float partialTicks) {
        if (fullLineEvent != null) fullLineEvent.text = "";
        super.drawScreen2(mouseX, mouseY, partialTicks);
    }

    public class ControllerOverviewChartButton extends kGuiButtonBase {
        public ControllerOverviewChartButton(int id, int xPos, int yPos, int width, int heightPerBar) {
            super(id, xPos, yPos, width, heightPerBar* ComputePower.values().length, "");
            setAnimatedInFBO(true);
            this.heightPerBar = heightPerBar;
        }

        int heightPerBar = 4;

        final ResourceLocation commonBackground = new ResourceLocation(MOD_ID,"textures/gui/computerCluster/clusterOverview.png");

        @Override
        public void drawButton2(Minecraft mc, int mouseX, int mouseY) {
            if(clusterTotal==0)return;
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            mc.getTextureManager().bindTexture(commonBackground);

            GL11.glPushMatrix();
            int color = computing.color;

            //Total
            GL11.glColor3ub((byte) (0xff & color >> 16), (byte)(0xff & color >> 8) , (byte)(color & 0xff));
            this.drawTexturedModalRect(xPosition,yPosition, 0, 236, width, heightPerBar);

            GL11.glColor4f(1,1,1,0.3F);
            this.drawTexturedModalRect(xPosition,yPosition, 0, 236, width, heightPerBar);

            //Providing
            int aWidthProviding = (int) Math.max(1,(width * ((controllerProviding)*1F/(clusterTotal))));
            GL11.glColor3ub((byte) (0xff & color >> 16), (byte)(0xff & color >> 8) , (byte)(color & 0xff));
            this.drawTexturedModalRect(xPosition,yPosition+1, 0, 236, aWidthProviding, heightPerBar-2);

            GL11.glPopMatrix();
            GL11.glColor4f(1,1,1,1);
        }
    }

    public class ControllerEventButton extends kGuiButtonBase {
        public final String shortText;
        public final String fullText;

        public ControllerEventButton(int id, int xPos, int yPos, int width, String shortText, String fullText) {
            super(id, xPos, yPos, width, 10, shortText);
            this.shortText = shortText;
            this.fullText = fullText;
            setAnimatedInFBO(true);
        }

        @Override
        public void drawButton2(Minecraft mc, int mouseX, int mouseY) {
            //The list draws its children translated by its scroll offset, so the mouse has to be shifted back.
            boolean hovered = controllerEventListButton != null
                    && controllerEventListButton.isMouseInButton(mouseX, mouseY)
                    && isMouseInButton(mouseX, (int) (mouseY - controllerEventListButton.YOffset));
            int drawColor = hovered ? 0x404040 : 0x202020;
            String drawText = shortText;
            while (fontRendererObj.getStringWidth(drawText) > width && drawText.length() > 3) {
                drawText = drawText.substring(0, drawText.length() - 4) + "..";
            }
            fontRendererObj.drawString(drawText, xPosition, yPosition, drawColor);
            if (hovered && fullLineEvent != null) fullLineEvent.text = fullText;
            GL11.glColor4f(1,1,1,1);
        }
    }
}
