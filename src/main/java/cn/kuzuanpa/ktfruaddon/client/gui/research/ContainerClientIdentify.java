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

import cn.kuzuanpa.ktfruaddon.api.nei.IHiddenNei;
import cn.kuzuanpa.ktfruaddon.api.network.PacketContainerButtonPressed;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import cn.kuzuanpa.ktfruaddon.tile.research.ResearchTableIdentify;
import gregapi.gui.ContainerClientDefault;
import gregapi.tileentity.ITileEntityInventoryGUI;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;
import static cn.kuzuanpa.ktfruaddon.ktfruaddon.kNetworkHandler;

public class ContainerClientIdentify extends ContainerClientDefault implements IHiddenNei {

    private static final ResourceLocation SLOT_TEXTURE = new ResourceLocation(MOD_ID, "textures/gui/research/background.png");

    private final ResearchTableIdentify mTile;

    // 动画相关变量
    protected float hoverAnimation = 0.0F;
    protected int hoveredIndex = -1;
    protected int lastHoveredIndex = -1;

    public ContainerClientIdentify(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, int aGUIID) {
        super(new ContainerCommonIdentify(aInventoryPlayer, aTileEntity, aGUIID));
        this.mTile = (ResearchTableIdentify) ((ContainerCommonIdentify) inventorySlots).mTileEntity;
    }
    protected void drawGuiContainerBackgroundLayer2(float par1, int par2, int par3) {
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        int gridSize = mTile.size;
        int slotSize = 16; // 每个格子的大小（像素）
        int gap = 2;       // 格子之间的间隙
        int totalSize = gridSize * slotSize + (gridSize - 1) * gap;

        int startX = (width - totalSize) / 2;
        int startY = (height - totalSize) / 2 + 10; // 稍微向下偏移

        drawRect(startX - 5, startY - 5, startX + totalSize + 5, startY + totalSize + 5, 0xFF333333);
        hoveredIndex = -1;
        for (int i = 0; i < gridSize * gridSize; i++) {
            int x = startX + (i % gridSize) * (slotSize + gap);
            int y = startY + (i / gridSize) * (slotSize + gap);

            int color = (i == mTile.targetIndex) ? mTile.targetColor : mTile.decoyColor;

            boolean isHovered = mouseX >= x && mouseX < x + slotSize && mouseY >= y && mouseY < y + slotSize;

            if (isHovered) {
                if (lastHoveredIndex != i) {
                    lastHoveredIndex = i;
                    hoverAnimation = 0.0F;
                }
                hoveredIndex = i;
                hoverAnimation += partialTicks * 0.1F;
                if (hoverAnimation > 1.0F) hoverAnimation = 1.0F;
            } else if (lastHoveredIndex == i) {
                hoverAnimation -= partialTicks * 0.1F;
                if (hoverAnimation < 0.0F) hoverAnimation = 0.0F;
            }

            // 绘制纹理并染色
            drawColoredTexture(x, y, slotSize, slotSize, color, isHovered, hoverAnimation);
        }

        // 绘制分数和关卡信息
        drawInfoOverlay();
    }

    /**
     * 绘制染色的纹理
     */
    private void drawColoredTexture(int x, int y, int width, int height, int color, boolean isHovered, float animation) {
        // 解析 RGB
        float r = ((color >> 16) & 0xFF) / 255.0F;
        float g = ((color >> 8) & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;

        // 如果悬停，稍微提亮颜色
        if (isHovered) {
            float brighten = 0.2F * animation;
            r = Math.min(1.0F, r + brighten);
            g = Math.min(1.0F, g + brighten);
            b = Math.min(1.0F, b + brighten);
        }

        GL11.glColor4f(r, g, b, 1.0F);
        mc.getTextureManager().bindTexture(SLOT_TEXTURE);

        // 绘制纹理
        drawTexturedModalRect(x, y, 0, 0, width, height);

        // 重置颜色
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * 绘制信息覆盖层（分数、关卡等）
     */
    private void drawInfoOverlay() {
        // 在屏幕顶部绘制分数
        String scoreText = "Scores: " + mTile.scores;
        fontRendererObj.drawString(scoreText, width / 2 - fontRendererObj.getStringWidth(scoreText) / 2, 20, 0xFFFFFF);

        // 绘制关卡
        String levelText = "Level: " + mTile.level;
        fontRendererObj.drawString(levelText, width / 2 - fontRendererObj.getStringWidth(levelText) / 2, 35, 0xFFFFFF);

        // 绘制提示
        if (!mTile.gameActive) {
            String hintText = "Click to Start";
            fontRendererObj.drawString(hintText, width / 2 - fontRendererObj.getStringWidth(hintText) / 2, height / 2, 0xFFFFFF);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);

        // 只响应左键点击
        if (button != 0) return;

        if (!mTile.gameActive) {
            kNetworkHandler.sendToServer(new PacketContainerButtonPressed(utils.dimID(mTile.getWorldObj()), mTile.xCoord,mTile.yCoord,mTile.zCoord,0)) ;
            return;
        }
        // 重新计算网格区域
        int gridSize = mTile.size;
        int slotSize = 18;
        int gap = 2;
        int totalSize = gridSize * slotSize + (gridSize - 1) * gap;
        int startX = (width - totalSize) / 2;
        int startY = (height - totalSize) / 2 + 10;

        // 检查点击是否在网格区域内
        if (mouseX >= startX && mouseX < startX + totalSize && mouseY >= startY && mouseY < startY + totalSize && hoveredIndex != -1) {
            kNetworkHandler.sendToServer(new PacketContainerButtonPressed(utils.dimID(mTile.getWorldObj()), mTile.xCoord,mTile.yCoord,mTile.zCoord,1, (byte)hoveredIndex)) ;
        }

    }

    @Override
    public void initGui() {
        super.initGui();
        // 可以在这里添加按钮，例如重置按钮
        // buttonList.add(new GuiButton(0, width / 2 - 50, height - 30, 100, 20, "Reset"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            kNetworkHandler.sendToServer(new PacketContainerButtonPressed(utils.dimID(mTile.getWorldObj()), mTile.xCoord,mTile.yCoord,mTile.zCoord,-100)) ;
        }
    }
}
