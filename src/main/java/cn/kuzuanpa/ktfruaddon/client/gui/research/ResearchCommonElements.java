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

import cn.kuzuanpa.kGuiLib.client.IkGui;
import cn.kuzuanpa.kGuiLib.client.anime.animeMoveLinear;
import cn.kuzuanpa.kGuiLib.client.anime.animeScaleLinear;
import cn.kuzuanpa.kGuiLib.client.anime.animeScaleQuad;
import cn.kuzuanpa.kGuiLib.client.anime.shortcut.animeTransparency;
import cn.kuzuanpa.kGuiLib.client.objects.gui.kGuiButtonBase;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.kUII18n;
import cn.kuzuanpa.ktfruaddon.api.research.ResearchProject;
import cn.kuzuanpa.ktfruaddon.api.research.task.IResearchTask;
import gregapi.data.LH;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.concurrent.atomic.AtomicInteger;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class ResearchCommonElements {
    static RenderItem itemRender = new RenderItem();
    static Minecraft mc = Minecraft.getMinecraft();
    final static ResourceLocation background = new ResourceLocation(MOD_ID,"textures/gui/research/background.png");
    final static ResourceLocation main = new ResourceLocation(MOD_ID,"textures/gui/research/main.png");
    public static void drawTexturedModelRectFromIcon(int x, int y, IIcon icon, int width, int height)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0, y + height, 0, icon.getMinU(), icon.getMaxV());
        tessellator.addVertexWithUV(x + width, y + height, 0, icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV(x + width, y + 0, 0, icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV(x + 0, y + 0, 0, icon.getMinU(), icon.getMinV());
        tessellator.draw();
    }

    public static void drawTextureRect(Tessellator tessellator, int x, int y, float z, int v, int width, int height, int u){
        final float f = 0.00390625F;
        final float f1 = 0.00390625F;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + height, z, (u * f), (v + height) * f1);
        tessellator.addVertexWithUV(x + width, y + height, z, (u + width) * f, (v + height) * f1);
        tessellator.addVertexWithUV(x + width, y, z, (u + width) * f, v * f1);
        tessellator.addVertexWithUV(x, y, z, (u * f), v * f1);
        tessellator.draw();
    }
    public static void drawItemStack(ItemStack stack, int x,int y, float scale){
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0);
        GL11.glScalef(scale,scale,1);
        itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), stack, 0, 0);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LIGHTING);
    }

    public static void drawResearchProgressBar(ResearchProject researchProject, Tessellator tessellator, int x, int y, float z, int width){
        mc.getTextureManager().bindTexture(background);
        GL11.glColor4f(1, 1, 1, 1f);
        float progress = 0.0f;
        if(researchProject.isCompleted)progress = 1;
        else for (IResearchTask condition : researchProject.tasks) {
            progress += condition.getProgress()*1f/condition.getRequiredProgress();
        }
        drawTextureRect(tessellator, x, y, z, 14, researchProject.tasks.isEmpty()? 0 : (int) ((width-4) * (progress/ researchProject.tasks.size())), 8, 0);
    }

    public static void drawResearchConditionIcon(ResearchProject researchProject, int x, int y){
        AtomicInteger i = new AtomicInteger();
        for (IResearchTask task : researchProject.tasks) {
            if (i.get() > 6) {
                mc.fontRenderer.drawStringWithShadow("…", x + i.get() * 10, y, 0xffffffff);
                GL11.glColor4f(1, 1, 1, 1);
                break;
            }

            ItemStack itemStack = task.getIcon();
            if(itemStack == null)itemStack = new ItemStack(Items.book,1);
            drawItemStack(itemStack, x + i.getAndIncrement() * 10, y, 0.5F);
        }
    }

    public static void drawResearchMainIcon(ResearchProject researchProject, int x, int y){
        mc.getTextureManager().bindTexture(mc.getTextureManager().getResourceLocation(Items.book.getSpriteNumber()));
        ItemStack itemStack = researchProject.getIcon();
        if(itemStack == null)itemStack = new ItemStack(Items.book,1);
        drawItemStack(itemStack, x,y, 1F);
    }

    public static void drawResearchNameDesc(ResearchProject researchProject, int x, int y){
        mc.fontRenderer.drawStringWithShadow(LH.get(researchProject.id), x,y,0xffffffff);
        GL11.glColor4f(1,1,1,1);
    }

    public static class HoveringPanel extends kGuiButtonBase {
        public HoveringPanel(IkGui gui, int id) {
            super(id, 0, 0, 128, 48, "");
            setAnimatedInFBO(true);
            this.gui = gui;
        }
        private final IkGui gui;
        public ResearchProject researchProject;
        public ResearchProject selectedProject;
        private ResearchProject renderedResearchProject;
        public int animeDuration = 200;

        public long quitTime = 0;
        public void update(ResearchProject hoverProject, ResearchProject selectedProject){
            if(hoverProject != null){
                visible=true;
                renderedResearchProject = hoverProject;
            }
            this.researchProject=hoverProject;
            this.selectedProject=selectedProject;
            if(this.researchProject == null && quitTime + animeDuration < gui.getTimer())visible=false;
            if(!visible)getGuiAnimeList().clear();
        }
        public void join(int mouseX, int mouseY){
            if(quitTime+ animeDuration >= gui.getTimer()) getGuiAnimeList().clear();//terminates Quit anime when player rapidly switch between items.
            addAnime(new animeMoveLinear(-1,0,mouseX,mouseY)).addAnime(new animeScaleLinear(-1,0,0.1f,1,1)).addAnime(new animeScaleQuad(gui.getTimer(), gui.getTimer() +animeDuration,10f,3,1,1)).addAnime(new animeMoveLinear(-1,0,-mouseX,-mouseY)).addAnime(new animeTransparency(gui.getTimer(), gui.getTimer() +animeDuration,0,255));
        }

        public void quit(int mouseX, int mouseY){
            quitTime = gui.getTimer();
            getGuiAnimeList().clear();
            addAnime(new animeMoveLinear(-1,0,mouseX,mouseY)).addAnime(new animeScaleQuad(gui.getTimer(), gui.getTimer() +animeDuration,0.01f,3)).addAnime(new animeMoveLinear(-1,0,-mouseX,-mouseY)).addAnime(new animeTransparency(gui.getTimer(), gui.getTimer() +animeDuration,255,-255 ));
        }
        @Override
        public void drawButton2(Minecraft mc, int mouseX, int mouseY) {
            if(!visible || renderedResearchProject == null)return;
            xPosition = mouseX + 6;
            yPosition = mouseY - 4;

            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            float colorTimer = ((float) Math.sin(System.currentTimeMillis()%3141/1000f))/2f+0.5f;
            Tessellator tessellator = Tessellator.instance;
            AtomicInteger i = new AtomicInteger();

            GL11.glEnable(GL11.GL_ALPHA_TEST);

            drawHoverDetail(i,xPosition,yPosition, colorTimer);

            GL11.glColor4f(1,1,1,1);
            if(renderedResearchProject.isUnlocked)for (IResearchTask task : renderedResearchProject.tasks) {
                i.getAndIncrement();
                drawBackground(tessellator,i.get(),colorTimer, false);

                GL11.glColor4f(1,1,1,1);

                ItemStack itemStack = task.getIcon();
                if(itemStack == null)itemStack = new ItemStack(Items.book,1);
                drawItemStack(itemStack, xPosition + 2, yPosition -11 + i.get() * 10, 0.5F);

                mc.getTextureManager().bindTexture(main);
                GL11.glColor4f(1,1,1,1);
                drawTextureRect(tessellator, xPosition + 12 ,yPosition -9 + i.get() * 10, this.zLevel, 8, width -16, 6, 0);

                GL11.glColor4f(0.0f,0.8f,0.0f,colorTimer/4+0.75f);
                float progress = renderedResearchProject.isCompleted || task.isCompleted()?1:task.getProgress()*1f/task.getRequiredProgress();
                drawTextureRect(tessellator, xPosition + 12 ,yPosition -9 + i.get() * 10, this.zLevel, 8, (int) ((width -16)*progress), 6, 60);

            }
            i.getAndIncrement();
            drawBackground(tessellator,i.get(),colorTimer, true);
            String str = LH.get(!renderedResearchProject.isUnlocked? kUII18n.RESEARCH_VIEWER_CLICK_LOCKED: selectedProject == renderedResearchProject && !selectedProject.isCompleted? kUII18n.RESEARCH_VIEWER_CLICK_RESEARCH : kUII18n.RESEARCH_VIEWER_CLICK_VIEW);
            mc.fontRenderer.drawStringWithShadow(str,xPosition+3,yPosition - 9 + i.get() * 10, 0xffffffff);

            GL11.glColor4f(1,1,1,1);
        }
        public void drawBackground(Tessellator tessellator, int i, float colorTimer, boolean isEnded){
            mc.getTextureManager().bindTexture(main);
            GL11.glColor4f(1,1,1,colorTimer/3 + 0.5f);
            drawTextureRect(tessellator, xPosition, yPosition - 10 + i*10, this.zLevel, i==1?32: isEnded?50: 41, width, isEnded?11:10, 0);
            GL11.glColor4f(1,1,1,1);
        }
        public void drawHoverDetail(AtomicInteger i, int x, int y, float colorTimer){
            String current = "";
            String last = LH.get(renderedResearchProject.desc);
            while (true){
                current = mc.fontRenderer.trimStringToWidth(last,width-4);
                i.getAndIncrement();
                if(i.get() > 2 && !last.equals(current))current+="...";
                drawBackground(Tessellator.instance, i.get(), colorTimer, false);
                mc.fontRenderer.drawStringWithShadow(current,x+2,y-8 +i.get()*9,0xffffffff);
                GL11.glColor4f(1,1,1,1);
                if(i.get() > 2)break;
                if(last.equals(current))break;
                last = last.replaceFirst(current,"");
            }
        }
    }

    public static class SidePanel extends kGuiButtonBase {
        public SidePanel(IkGui gui, int id, int width, int y) {
            super(id, mc.currentScreen.width - width, y, width, 190, "");
            setAnimatedInFBO(true);
            this.gui = gui;
        }
        private final IkGui gui;
        public ResearchProject selectedProject;

        @Override
        public void drawButton2(Minecraft mc, int mouseX, int mouseY) {
            if (!visible) return;
            float colorTimer = ((float) Math.sin(System.currentTimeMillis() % 3141 / 1000f)) / 2f + 0.5f;
            Tessellator tessellator = Tessellator.instance;
            drawBackground(tessellator, colorTimer);
            String str = LH.get(selectedProject == null? kUII18n.RESEARCH_VIEWER_SELECTED_EMPTY: kUII18n.RESEARCH_VIEWER_SELECTED);
            mc.fontRenderer.drawStringWithShadow(str, xPosition + 58 - mc.fontRenderer.getStringWidth(str)/2,yPosition +1,0xffffffff);
            if(selectedProject == null)return;
            drawResearchMainIcon(selectedProject, xPosition + 3, yPosition + 12);
            drawResearchNameDesc(selectedProject, xPosition + 22, yPosition + 16);
            drawDesc(selectedProject, xPosition + 2, yPosition + 20);
            if (!selectedProject.isUnlocked) return;
            drawProgressBar(tessellator);
            drawConditionIcon();
            GL11.glColor4f(1, 1, 1, 1);
        }

        public void fillColor(float colorTimer) {
            if (selectedProject == null || !selectedProject.isUnlocked) GL11.glColor4f(.5f, .5f, .5f, .4f);
            else if (!selectedProject.isCompleted && selectedProject.getProgress() > 0)
                GL11.glColor4f(0f, colorTimer / 2f + 0.5f, (1 - colorTimer) / 4f + 0.75f, .9f);
            else if (!selectedProject.isCompleted) GL11.glColor4f(1f, 1f - colorTimer / 2.5f, 0, .9f);
            else GL11.glColor4f(colorTimer / 3f + .1f, .8f, .0f, .9f);
        }

        public void drawBackground(Tessellator tessellator, float colorTimer) {
            fillColor(colorTimer);
            mc.getTextureManager().bindTexture(main);
            drawTextureRect(tessellator, xPosition, yPosition, this.zLevel, 65, width, height, 140);
            GL11.glColor4f(1, 1, 1, 1);
        }

        public void drawProgressBar(Tessellator tessellator) {
            mc.getTextureManager().bindTexture(background);
            GL11.glColor4f(1, 1, 1, 1f);
            float progress = 0.0f;
            if(selectedProject.isCompleted) progress = 1;
            else for (IResearchTask condition : selectedProject.tasks) {
                progress += condition.getProgress() * 1f / condition.getRequiredProgress();
            }
            drawTextureRect(tessellator, xPosition + 2, yPosition + height - 11, this.zLevel, 14, (int) ((width - 4) * (progress / selectedProject.tasks.size())), 8, 0);
        }

        public void drawConditionIcon() {
            AtomicInteger i = new AtomicInteger();
            for (IResearchTask task : selectedProject.tasks) {
                i.getAndIncrement();
                float colorTimer = ((float) Math.sin(System.currentTimeMillis() % 3141 / 1000f)) / 2f + 0.5f;
                Tessellator tessellator = Tessellator.instance;

                GL11.glColor4f(1, 1, 1, 1);

                ItemStack itemStack = task.getIcon();
                if(itemStack == null)itemStack = new ItemStack(Items.book,1);
                drawItemStack(itemStack, xPosition + 3, yPosition + height - 12 - i.get() * 18, 0.5F);

                mc.fontRenderer.drawStringWithShadow(task.getDesc(),xPosition + 13, yPosition + height - 12 - i.get() * 18,0xffffffff);

                mc.getTextureManager().bindTexture(main);
                GL11.glColor4f(1, 1, 1, 1);
                drawTextureRect(tessellator, xPosition + 2, yPosition + height - 3 - i.get() * 18, this.zLevel, 8, width - 6, 6, 0);

                GL11.glColor4f(0.0f, 0.8f, 0.0f, colorTimer / 4 + 0.75f);
                float progress = selectedProject.isCompleted || task.isCompleted()? 1 : task.getProgress() * 1f / task.getRequiredProgress();
                drawTextureRect(tessellator, xPosition + 2, yPosition + height - 3 - i.get() * 18, this.zLevel, 8, (int) ((width - 6) * progress), 6, 60);

            }
        }
        public void drawDesc(ResearchProject researchProject, int x, int y){
            String current = "";
            String last = LH.get(researchProject.desc);
            int i =0;
            while (true){
                current = mc.fontRenderer.trimStringToWidth(last,width-4);
                i ++ ;
                mc.fontRenderer.drawStringWithShadow(current,x,y + i *10,0xffffffff);
                GL11.glColor4f(1,1,1,1);
                if(last.equals(current))break;
                last = last.replaceFirst(current,"");
            }

            GL11.glColor4f(1,1,1,1);
        }
    }
    public static class CurrentPanel extends kGuiButtonBase {
        public CurrentPanel(IkGui gui, int id, int width) {
            super(id, mc.currentScreen.width - width, 0, width, 44, "");
            setAnimatedInFBO(true);
            this.gui = gui;
        }
        private final IkGui gui;
        public ResearchProject currentProject;

        @Override
        public void drawButton2(Minecraft mc, int mouseX, int mouseY) {
            if (!visible) return;
            float colorTimer = ((float) Math.sin(System.currentTimeMillis() % 3141 / 1000f)) / 2f + 0.5f;
            Tessellator tessellator = Tessellator.instance;
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            drawBackground(tessellator, colorTimer, currentProject);
            String str = LH.get(  kUII18n.RESEARCH_VIEWER_CURRENT);
            mc.fontRenderer.drawStringWithShadow(str, xPosition + 48  - mc.fontRenderer.getStringWidth(str)/2,yPosition+1,0xffffffff);
            if (currentProject != null) {
                drawResearchMainIcon(currentProject, xPosition + 3, yPosition + 13);
                drawResearchNameDesc(currentProject, xPosition + 22, yPosition + 16);
                drawResearchProgressBar(currentProject, tessellator, xPosition+2, yPosition + 32, this.zLevel, width);
                drawResearchConditionIcon(currentProject, xPosition + 2, yPosition + 28);
            }
            GL11.glColor4f(1, 1, 1, 1);
        }

        public void fillColor(float colorTimer, ResearchProject project) {
            if (project == null || !project.isUnlocked) GL11.glColor4f(.5f, .5f, .5f, .4f);
            else if (!project.isCompleted && project.getProgress() > 0)
                GL11.glColor4f(0f, colorTimer / 2f + 0.5f, (1 - colorTimer) / 4f + 0.75f, .9f);
            else if (!project.isCompleted) GL11.glColor4f(1f, 1f - colorTimer / 2.5f, 0, .9f);
            else GL11.glColor4f(colorTimer / 3f + .1f, .8f, .0f, .9f);
        }

        public void drawBackground(Tessellator tessellator, float colorTimer, ResearchProject project) {
            fillColor(colorTimer, project);
            mc.getTextureManager().bindTexture(main);
            drawTextureRect(tessellator, xPosition, yPosition, this.zLevel, 0, width, height, 160);
            GL11.glColor4f(1, 1, 1, 1);
        }
    }
}
