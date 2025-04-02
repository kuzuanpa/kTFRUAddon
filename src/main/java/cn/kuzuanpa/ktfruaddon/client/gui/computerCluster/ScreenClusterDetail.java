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
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.kUserInterface;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputerClusterClientData;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
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
    protected Text stateTextButton  = null, controllerCountButton= null, clientCountButton     = null;
    protected ButtonList clusterEventListButton = null;

    protected byte clusterState  = 0;
    protected int  controllerCount  = 0,clientCount=0;

    Map<ComputePower,Long> clusterAvailPowers = new HashMap<>();
    Map<ComputePower,Long> clusterUsedPowers = new HashMap<>();

    public ScreenClusterDetail updateFromData(ComputerClusterClientData.ClusterDetail data){
        if(data == null)return this;
        updateCluster(data.clusterState,data.controllerCount,data.clientCount,data.availPowers,data.usedPowers,data.events);
        return this;
    }
    public ScreenClusterDetail updateCluster(byte clusterState, int controllerCount, int clientCount, Map<ComputePower,Long> availPowers, Map<ComputePower,Long> usedPowers, byte[] events){
        this.clusterState=clusterState;
        this.controllerCount=controllerCount;
        this.clientCount=clientCount;
        this.clusterAvailPowers=availPowers;
        this.clusterUsedPowers=usedPowers;
        syncValueToButton();
        clusterEventListButton.clearSubButton();
        for (int i = 0; i < events.length; i++) {
            String str = Constants.getClusterEventDesc(events[i]);
            clusterEventListButton.addSubButton(new Text(20+i, str,ContainerX+196-fontRendererObj.getStringWidth(str)/2,14+ ContainerY).setFBOOffset(0,i*10).setJoinLeaveTime(i*70,Integer.MAX_VALUE).addAnime(new animeMoveLinear(-1,0, 50,0)).addAnime(new animeTransparency(-1,0,255,-255)).addAnime(new animeMoveSlowIn(i*70, 800+i*70,-50,0,2)).addAnime(new animeRGBA(i*70,600+i*70,255,255,255,55,-150,-150,-150,200)));
        }
        clusterEventListButton.setMaxScrolled(events.length*10 - 120);
        return this;
    }

    protected void syncValueToButton(){
        controllerCountButton .text = String.valueOf(controllerCount);
        clientCountButton .text = String.valueOf(clientCount);
        switch (clusterState){
            case 0:
                stateButton.u =0;
                stateTextButton.text = "Offline";
                break;
            case 1:
                stateButton.u =32;
                stateTextButton.text = "Normal";
                break;
            case 2:
                stateButton.u =64;
                stateTextButton.text = "Warning";
                break;
            default:
                stateButton.u =96;
                stateTextButton.text = "Error";
        }
    }
    @Override
    public void addButtons() {
        buttons.add(new CommonTexturedButton(-1,ContainerX,ContainerY,0,0,226,146, MOD_ID, "textures/gui/computerCluster/clusterOverview.png").setAnimatedInFBO(true).addAnime(new animeMoveLinear(-1,0,50,0)).addAnime(new animeMoveSlowIn(0, 300,-50,0,2)).addAnime(new animeFadeIn(300)));

        buttons.add(new Text(1,"Cluster Overview",ContainerX+4,ContainerY+3)                                  .addAnime(new animeMoveLinear(-1,0, 50,0)).addAnime(new animeMoveSlowIn(0, 600,-50,0,2)).addAnime(new animeRGBA(0,400,255,255,255,55,-150,-150,-150,200)));

        int animeTimeSection1 = 900;

        String str;
        stateButton = new CommonTexturedButton(10,ContainerX+24,ContainerY+18,0,146,32,32, MOD_ID, "textures/gui/computerCluster/clusterOverview.png");
        buttons.add(stateButton                                                                                                  .setAnimatedInFBO(true).addAnime(new animeMoveLinear(-1,0, 50,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection1,-50,0,2)).addAnime(new animeFadeIn(animeTimeSection1)));

        str="State: ";
        buttons.add(new Text(2,str,ContainerX+40-fontRendererObj.getStringWidth(str),ContainerY+58).addAnime(new animeMoveLinear(-1,0, 50,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection1,-50,0,2)).addAnime(new animeRGBA(0,animeTimeSection1,255,255,255,55,-150,-150,-150,200)));

        stateTextButton =new Text(11,"Offline",ContainerX+42,ContainerY+58);
        buttons.add(stateTextButton                                                                                              .addAnime(new animeMoveLinear(-1,0, 50,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection1,-50,0,2)).addAnime(new animeRGBA(0,animeTimeSection1,255,255,255,55,-150,-150,-150,200)));

        int animeTimeSection2 = 1300;
        str = "Controller Count: ";
        buttons.add(new Text(3,str,ContainerX+6,ContainerY+76).addAnime(new animeMoveLinear(-1,0, 80,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection2,-80,0,2)).addAnime(new animeRGBA(0,animeTimeSection2,255,255,255,55,-150,-150,-150,200)));

        controllerCountButton=new Text(12,String.valueOf(controllerCount),ContainerX+6+fontRendererObj.getStringWidth(str),ContainerY+76);
        buttons.add(controllerCountButton                                                                                        .addAnime(new animeMoveLinear(-1,0, 80,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection2,-80,0,2)).addAnime(new animeRGBA(0,animeTimeSection2,255,255,255,55,-150,-150,-150,200)));

        str = "Client Count: ";
        buttons.add(new Text(4,str,ContainerX+6,ContainerY+88).addAnime(new animeMoveLinear(-1,0, 80,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection2,-80,0,2)).addAnime(new animeRGBA(0,animeTimeSection2,255,255,255,55,-150,-150,-150,200)));

        clientCountButton=new Text(13,String.valueOf(clientCount),ContainerX+6+fontRendererObj.getStringWidth(str),ContainerY+88);
        buttons.add(clientCountButton                                                                                            .addAnime(new animeMoveLinear(-1,0, 80,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection2,-80,0,2)).addAnime(new animeRGBA(0,animeTimeSection2,255,255,255,55,-150,-150,-150,200)));

        int animeTimeSection3 = 1600;
        buttons.add(new Text(6,"Cluster Compute Powers",ContainerX+6,ContainerY+102)                          .addAnime(new animeMoveLinear(-1,0,120,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection3,-120,0,2)).addAnime(new animeRGBA(0,animeTimeSection3,255,255,255,55,-150,-150,-150,200)));

        buttons.add(new ClusterOverviewChartButton(7,ContainerX+6,ContainerY+112,154,7)               .addAnime(new animeMoveLinear(-1,0,120,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection3,-120,0,2)).addAnime(new animeFadeIn(animeTimeSection3)));

        str = "Event Log";
        buttons.add(new Text(6, str,ContainerX+196 - fontRendererObj.getStringWidth(str)/2,ContainerY+4)                                  .addAnime(new animeMoveLinear(-1,0, 80,0)).addAnime(new animeMoveSlowIn(0, 400,-80,0,2)).addAnime(new animeRGBA(0,400,255,255,255,55,-150,-150,-150,200)));

        clusterEventListButton = new ButtonList(7,ContainerX+140,ContainerY+14,154,130);

        buttons.add(clusterEventListButton.addAnime(new animeMoveLinear(-1,0, 80,0)).addAnime(new animeMoveSlowIn(0, animeTimeSection2,-80,0,2)).addAnime(new animeFadeIn(animeTimeSection2)));

    }

    @Override
    public void handleMouseInput2(int mouseX,int mouseY) {
        super.handleMouseInput2(mouseX,mouseY);
        int index =0;
        addOrUpdateTooltip(index++, new String[]{"Data plotted on a logarithmic scale"}, vector2-> (2<vector2.x&&vector2.x<166&& 99 < vector2.y && vector2.y <= 107));

        for (ComputePower computePower : ComputePower.values()){
            if(clusterAvailPowers.get(computePower)==null || clusterAvailPowers.get(computePower)==0)continue;
            addOrUpdateTooltip(index+ computePower.ordinal(),
                    new String[]{LH.get(kUserInterface.TYPE)+": "+LH.get(kUserInterface.COMPUTE_POWER+"."+ computePower.ordinal()),
                            "Avail: "+clusterAvailPowers.get(computePower)+", Used: "+ clusterUsedPowers.getOrDefault(computePower,0L)},
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
}
