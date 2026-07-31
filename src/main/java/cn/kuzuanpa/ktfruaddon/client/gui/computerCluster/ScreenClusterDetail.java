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
import cn.kuzuanpa.kGuiLib.client.objects.IAnimatableButton;
import cn.kuzuanpa.kGuiLib.client.objects.gui.ButtonList;
import cn.kuzuanpa.kGuiLib.client.objects.gui.CommonTexturedButton;
import cn.kuzuanpa.kGuiLib.client.objects.gui.Text;
import cn.kuzuanpa.kGuiLib.client.objects.gui.kGuiButtonBase;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.kUII18n;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputerClusterClientData;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants;
import gregapi.data.LH;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class ScreenClusterDetail extends kGuiScreenContainerLayerBase {

    protected CommonTexturedButton stateButton =null;
    protected Text stateTextButton  = null, controllerCountButton= null, userCountButton     = null,  fullLineEvent =null;
    protected ButtonList clusterEventListButton = null;

    protected byte clusterState  = 0;
    protected int  controllerCount  = 0,userCount=0;

    Map<ComputePower,Long> clusterAvailPowers = new HashMap<>();
    Map<ComputePower,Long> clusterUsedPowers = new HashMap<>();

    public ScreenClusterDetail updateFromData(ComputerClusterClientData.ClusterDetail data){
        if(data == null)return this;
        updateCluster(data.clusterState,data.controllerCount,data.userCount,data.availPowers,data.usedPowers,data.events, data.eventExtra);
        return this;
    }
    public ScreenClusterDetail updateCluster(byte clusterState, int controllerCount, int userCount, Map<ComputePower,Long> availPowers, Map<ComputePower,Long> usedPowers, byte[] events, String[] eventExtra){
        this.clusterState=clusterState;
        this.controllerCount=controllerCount;
        this.userCount=userCount;
        this.clusterAvailPowers=availPowers;
        this.clusterUsedPowers=usedPowers;
        syncValueToButton();
        clusterEventListButton.clearSubButton();
        for (int i = 0; i < events.length; i++) {
            String fullText = Constants.getClusterEventDesc(events[i], i < eventExtra.length ? eventExtra[i] :"");
            String shortText = Constants.getClusterEventShortDesc(events[i]);
            kGuiButtonBase button = new ClusterEventButton(20+i, ContainerX+144, 14 + ContainerY + i*10, 104, shortText, fullText).setJoinLeaveTime(i*70,Integer.MAX_VALUE).addAnime(new animeMoveLinear(-1,0, 50,0)).addAnime(new animeTransparency(-1,0,255,-255)).addAnime(new animeMoveSlowIn(i*70, 800+i*70,-50,0,2)).addAnime(new animeRGBA(i*70,600+i*70,255,255,255,55,-150,-150,-150,200));
            clusterEventListButton.addSubButton(button);
        }
        clusterEventListButton.setMaxScrolled(Math.max(20, events.length*10 - 120));
        return this;
    }

    protected void syncValueToButton(){
        controllerCountButton.text = String.valueOf(controllerCount);
        userCountButton.text = String.valueOf(userCount);
        switch (clusterState){
            case 0:
                stateButton.u =0;
                stateTextButton.text = LH.get(I18nHandler.OFFLINE);
                break;
            case 1:
                stateButton.u =32;
                stateTextButton.text = LH.get(I18nHandler.NORMAL);
                break;
            case 2:
                stateButton.u =64;
                stateTextButton.text = LH.get(I18nHandler.WARNING);
                break;
            default:
                stateButton.u =96;
                stateTextButton.text = LH.get(I18nHandler.ERROR);
        }
    }
    @Override
    public void addButtons() {
        buttons.add(new CommonTexturedButton(-1,ContainerX,ContainerY,0,0,226,146, MOD_ID, "textures/gui/computerCluster/clusterOverview.png").setAnimatedInFBO(true).addAnime(new animeMoveLinear(-1,0,50,0)).addAnime(new animeMoveSlowIn(0, 300,-50,0,2)).addAnime(new animeFadeIn(300)));

        buttons.add(new Text(1, LH.get(I18nHandler.COMPUTE_CLUSTER_UI_CLUSTER_OVERVIEW), ContainerX+4,ContainerY+3).addAnime(new animeMoveLinear(-1,0, 50,0)).addAnime(new animeMoveSlowIn(0, 600,-50,0,2)).addAnime(new animeRGBA(0,400,255,255,255,55,-150,-150,-150,200)));

        int animeTimeSection1 = 900;

        String str;
        stateButton = new CommonTexturedButton(10,ContainerX+24,ContainerY+18,0,146,32,32, MOD_ID, "textures/gui/computerCluster/clusterOverview.png");
        buttons.add(stateButton.setAnimatedInFBO(true).addAnime(new animeMoveLinear(-1,0, 50,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection1,-50,0,2)).addAnime(new animeFadeIn(animeTimeSection1)));

        str = LH.get(I18nHandler.COMPUTE_CLUSTER_UI_LABEL_STATE) + ": ";
        buttons.add(new Text(2,str,ContainerX+40-fontRendererObj.getStringWidth(str),ContainerY+56).addAnime(new animeMoveLinear(-1,0, 50,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection1,-50,0,2)).addAnime(new animeRGBA(0,animeTimeSection1,255,255,255,55,-150,-150,-150,200)));

        stateTextButton =new Text(11,LH.get(I18nHandler.OFFLINE),ContainerX+42,ContainerY+56);
        buttons.add(stateTextButton.addAnime(new animeMoveLinear(-1,0, 50,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection1,-50,0,2)).addAnime(new animeRGBA(0,animeTimeSection1,255,255,255,55,-150,-150,-150,200)));

        int animeTimeSection2 = 1300;
        str = LH.get(I18nHandler.COMPUTE_CLUSTER_UI_LABEL_CONTROLLER_COUNT) + ": ";
        buttons.add(new Text(3,str,ContainerX+6,ContainerY+70).addAnime(new animeMoveLinear(-1,0, 80,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection2,-80,0,2)).addAnime(new animeRGBA(0,animeTimeSection2,255,255,255,55,-150,-150,-150,200)));

        controllerCountButton=new Text(12,String.valueOf(controllerCount),ContainerX+6+fontRendererObj.getStringWidth(str),ContainerY+70);
        buttons.add(controllerCountButton.addAnime(new animeMoveLinear(-1,0, 80,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection2,-80,0,2)).addAnime(new animeRGBA(0,animeTimeSection2,255,255,255,55,-150,-150,-150,200)));

        str = LH.get(I18nHandler.COMPUTE_CLUSTER_UI_LABEL_USER_COUNT) + ": ";
        buttons.add(new Text(4,str,ContainerX+6,ContainerY+80).addAnime(new animeMoveLinear(-1,0, 80,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection2,-80,0,2)).addAnime(new animeRGBA(0,animeTimeSection2,255,255,255,55,-150,-150,-150,200)));

        userCountButton=new Text(13,String.valueOf(userCount),ContainerX+6+fontRendererObj.getStringWidth(str),ContainerY+80);
        buttons.add(userCountButton.addAnime(new animeMoveLinear(-1,0, 80,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection2,-80,0,2)).addAnime(new animeRGBA(0,animeTimeSection2,255,255,255,55,-150,-150,-150,200)));

        int animeTimeSection3 = 1600;
        buttons.add(new Text(5,LH.get(I18nHandler.COMPUTE_CLUSTER_UI_LABEL_CLUSTER_COMPUTE_POWERS),ContainerX+6,ContainerY+102).addAnime(new animeMoveLinear(-1,0,120,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection3,-120,0,2)).addAnime(new animeRGBA(0,animeTimeSection3,255,255,255,55,-150,-150,-150,200)));

        buttons.add(new ClusterOverviewChartButton(7,ContainerX+6,ContainerY+112,154,7).addAnime(new animeMoveLinear(-1,0,120,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection3,-120,0,2)).addAnime(new animeFadeIn(animeTimeSection3)));

        str = LH.get(I18nHandler.COMPUTE_CLUSTER_UI_LABEL_EVENT_LOG);
        buttons.add(new Text(6, str,ContainerX+196 - fontRendererObj.getStringWidth(str)/2,ContainerY+4).addAnime(new animeMoveLinear(-1,0, 80,0)).addAnime(new animeMoveSlowIn(0, 400,-80,0,2)).addAnime(new animeRGBA(0,400,255,255,255,55,-150,-150,-150,200)));

        clusterEventListButton = new ButtonList(8,ContainerX+140,ContainerY+14,154,130);

        buttons.add(clusterEventListButton.addAnime(new animeMoveLinear(-1,0, 80,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection2,-80,0,2)).addAnime(new animeFadeIn(animeTimeSection2)));

        fullLineEvent = new Text(14,"",ContainerX+6,ContainerY+90,0x00000000);
        buttons.add(fullLineEvent);
    }

    @Override
    public void handleMouseInput2(int mouseX,int mouseY) {
        super.handleMouseInput2(mouseX,mouseY);
        addOrUpdateTooltip(0, new String[]{LH.get(I18nHandler.COMPUTE_CLUSTER_UI_LABEL_DATA_LOG_SCALE)}, vector2-> (2<vector2.x&&vector2.x<166&& 99 < vector2.y && vector2.y <= 107));

        for (ComputePower computePower : ComputePower.values()){
            if(clusterAvailPowers.get(computePower)==null || clusterAvailPowers.get(computePower)==0)continue;
            addOrUpdateTooltip(computePower.ordinal()+1,
                    new String[]{LH.get(kUII18n.TYPE)+": "+LH.get(kUII18n.COMPUTE_POWER+"."+ computePower.ordinal()),
                            LH.get(I18nHandler.COMPUTE_CLUSTER_UI_LABEL_AVAIL)+": "+clusterAvailPowers.get(computePower)+", "+LH.get(I18nHandler.COMPUTE_CLUSTER_UI_LABEL_USED)+": "+ clusterUsedPowers.getOrDefault(computePower,0L)},
                    vector2-> (2<vector2.x&&vector2.x<166&& 109+ 8*(computePower.ordinal()) < vector2.y && vector2.y <= 121 + 8*(computePower.ordinal())));
        }
    }

    @Override
    public void onKeyTyped(char c, int i) {
        if(i == Keyboard.KEY_E|| i == Keyboard.KEY_ESCAPE) close();
    }

    public class ClusterOverviewChartButton extends kGuiButtonBase {
        public ClusterOverviewChartButton(int id, int xPos, int yPos, int width, int heightPerBar) {
            super(id, xPos, yPos, width, heightPerBar* ComputePower.values().length, "");
            this.heightPerBar = heightPerBar;
        }

        int heightPerBar = 4;

        final ResourceLocation commonBackground = new ResourceLocation(MOD_ID,"textures/gui/computerCluster/clusterOverview.png");

        @Override
        public void drawButton2(Minecraft mc, int mouseX, int mouseY) {
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            mc.getTextureManager().bindTexture(commonBackground);
            AtomicLong longest = new AtomicLong();
            clusterAvailPowers.forEach((k,v)-> longest.set(Math.max(longest.get(), v)));

            for (ComputePower computePower : ComputePower.values()){
                if(clusterAvailPowers.get(computePower)==null)continue;
                int color = computePower.color;

                int aWidth = (int) Math.max(1,(width * (Math.sqrt(clusterAvailPowers.get(computePower))/Math.sqrt(longest.get()))));
                GL11.glColor3ub((byte) (0xff & color >> 16), (byte)(0xff & color >> 8) , (byte)(color & 0xff));
                this.drawTexturedModalRect(xPosition,yPosition+ heightPerBar* computePower.ordinal(), 0, 236, aWidth, heightPerBar);

                long used = clusterUsedPowers.get(computePower)==null?0:clusterUsedPowers.get(computePower);
                int aWidthUsed = (int) Math.max(1,(width * (Math.sqrt(used)/Math.sqrt(longest.get()))));
                GL11.glColor3ub((byte) ((0xff & color >> 16)*0.6F), (byte)((0xff & color >> 8)*0.6F) , (byte)((0xff & color)*0.6F));
                this.drawTexturedModalRect(xPosition,yPosition+1+ heightPerBar* computePower.ordinal(), 0, 236, aWidthUsed-1, heightPerBar-2);
            }
        }

        @Override
        public void drawFBOToScreen(Minecraft mc, int mouseX, int mouseY) {
            IAnimatableButton.drawPre(this,timer);
            IAnimatableButton.draw(this,timer);
            super.drawFBOToScreen(mc, mouseX, mouseY);
            IAnimatableButton.drawAfter(this,timer);
        }
    }

    public class ClusterEventButton extends kGuiButtonBase {
        public final String shortText;
        public final String fullText;

        public ClusterEventButton(int id, int xPos, int yPos, int width, String shortText, String fullText) {
            super(id, xPos, yPos, width, 10, shortText);
            this.shortText = shortText;
            this.fullText = fullText;
            setAnimatedInFBO(true);
        }

        @Override
        public void drawButton2(Minecraft mc, int mouseX, int mouseY) {
            int drawColor = isMouseInButton(mouseX, mouseY) ? 0x999999 : 0x202020;
            String drawText = shortText;
            while (fontRendererObj.getStringWidth(drawText) > width && drawText.length() > 3) {
                drawText = drawText.substring(0, drawText.length() - 4) + "..";
            }
            fontRendererObj.drawString(drawText, xPosition, yPosition, drawColor);
            if(isMouseInButton(mouseX,(int) (mouseY - clusterEventListButton.YOffset))){
                fullLineEvent.text = fullText;
            }
            GL11.glColor4f(1,1,1,1);
        }
    }
}
