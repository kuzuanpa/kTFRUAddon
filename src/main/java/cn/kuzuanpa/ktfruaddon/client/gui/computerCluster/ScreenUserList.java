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
import cn.kuzuanpa.kGuiLib.client.objects.gui.ButtonList;
import cn.kuzuanpa.kGuiLib.client.objects.gui.CommonTexturedButton;
import cn.kuzuanpa.kGuiLib.client.objects.gui.Text;
import cn.kuzuanpa.kGuiLib.client.objects.gui.kGuiButtonBase;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.UserData;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputerClusterClientData;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.List;

import static cn.kuzuanpa.ktfruaddon.api.code.codeUtil.getDisplayShortNum;
import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class ScreenUserList extends kGuiScreenContainerLayerBase {

    ButtonList dataListButton = null;
    public ScreenUserList updateFromData(ComputerClusterClientData.UserList data){
        if(data == null)return this;
        updateUserList(data.datas);
        return this;
    }
    public void updateUserList(List<UserData> datas){
        dataListButton.clearSubButton();
        for (int i = 0; i < datas.size(); i++) {
            dataListButton.addSubButton(new UserButton(10+i,ContainerX+4,ContainerY+14,320).setData(datas.get(i)).setFBOOffset(0,i*18).setJoinLeaveTime(i*70,Integer.MAX_VALUE).addAnime(new animeMoveLinear(-1,0, 50,0)).addAnime(new animeTransparency(-1,0,255,-255)).addAnime(new animeMoveSlowIn(i*70, 800+i*70,-50,0,2)).addAnime(new animeTransparency(i*70,600+i*70,0,255)));
        }
        dataListButton.setMaxScrolled(datas.size()*10);
    }

    @Override
    public void addButtons() {
        buttons.add(new CommonTexturedButton(-1,ContainerX,ContainerY,0,0,226,146, MOD_ID, "textures/gui/computerCluster/clientBar.png").setAnimatedInFBO(true).addAnime(new animeMoveLinear(-1,0,50,0)).addAnime(new animeMoveSlowIn(0, 300,-50,0,2)).addAnime(new animeFadeIn(300)));

        buttons.add(new Text(1,"State",ContainerX+4,ContainerY+3)                                .setAnimatedInFBO(true).addAnime(new animeMoveLinear(-1,0, 50,0)).addAnime(new animeMoveSlowIn(0, 600,-50,0,2)).addAnime(new animeRGBA(0,400,255,255,255,55,-150,-150,-150,200)));

        buttons.add(new Text(2,"Position",ContainerX+55,ContainerY+3)                                .setAnimatedInFBO(true).addAnime(new animeMoveLinear(-1,0, 50,0)).addAnime(new animeMoveSlowIn(0, 600,-50,0,2)).addAnime(new animeRGBA(0,400,255,255,255,55,-150,-150,-150,200)));

        buttons.add(new Text(3,"Consuming",ContainerX+162,ContainerY+3)                                .setAnimatedInFBO(true).addAnime(new animeMoveLinear(-1,0, 50,0)).addAnime(new animeMoveSlowIn(0, 600,-50,0,2)).addAnime(new animeRGBA(0,400,255,255,255,55,-150,-150,-150,200)));

        dataListButton = new ButtonList(4, ContainerX,ContainerY+14,width,height-14);

        buttons.add(dataListButton);
    }

    @Override
    public void onKeyTyped(char c, int i) {
        if(i == Keyboard.KEY_ESCAPE)close();
    }

    public class UserButton extends kGuiButtonBase {
        public UserButton(int id, int xPos, int yPos, int width) {
            super(id, xPos, yPos, width, 16, "");
            setAnimatedInFBO(true);
        }

        public UserButton setData(UserData data) {
            this.data = data;
            return this;
        }

        public UserData data = null;

        final ResourceLocation background = new ResourceLocation(MOD_ID,"textures/gui/computerCluster/clientBar.png");

        @Override
        public void drawButton2(Minecraft mc, int mouseX, int mouseY) {
            if(data==null)return;
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glColor4f(1,1,1,1);

            mc.getTextureManager().bindTexture(background);

            this.drawTexturedModalRect(xPosition,yPosition,16*data.state,146,16,16);
            wrappedDrawStr("User",xPosition+24,yPosition+3,0x000000);

            for (int i = 0; i < ComputePower.values().length; i++) {
                if(data.consumingPower.get(ComputePower.getType(i)) == null)continue;

                mc.getTextureManager().bindTexture(background);

                int color = ComputePower.getType(i).color;

                GL11.glColor3ub((byte) (0xff & color >> 16), (byte)(0xff & color >> 8) , (byte)(color & 0xff));
                this.drawTexturedModalRect(xPosition+width-94-(ComputePower.values().length - i)*22,yPosition+1,0,236,8,8);

                String str = getDisplayShortNum(data.consumingPower.get(ComputePower.getType(i)), 1);
                wrappedDrawStr(str ,xPosition+width-90-(ComputePower.values().length - i)*22 - (fontRendererObj.getStringWidth(str)/2),yPosition+9,0x000000);
            }
        }

        void wrappedDrawStr(String str, int x,int y,int color){
            fontRendererObj.drawString(str, x, y, color);
            GL11.glColor4f(1,1,1,1);
        }
    }
}
