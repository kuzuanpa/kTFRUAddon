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

import cn.kuzuanpa.ktfruaddon.api.network.PacketContainerButtonPressed;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import cn.kuzuanpa.ktfruaddon.tile.research.ResearchTableLinkGame;
import gregapi.gui.ContainerClientDefault;
import gregapi.tileentity.ITileEntityInventoryGUI;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.kNetworkHandler;

public class ContainerClientLinkGame extends ContainerClientDefault {

    private final ResearchTableLinkGame mTile;

    // 布局参数
    private int gridOriginX;
    private int gridOriginY;
    private int tileSize = 32; // 每个格子的渲染大小
    private int gap = 2; // 间隙

    public ContainerClientLinkGame(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, int aGUIID) {
        super(new ContainerCommonLinkGame(aInventoryPlayer.player, (ResearchTableLinkGame) aTileEntity, aGUIID));
        this.mTile = (ResearchTableLinkGame) ((ContainerCommonLinkGame) inventorySlots).mTileEntity;
    }

    @Override
    public void initGui() {
        super.initGui();
        // 计算网格居中位置
        int totalGridSize = ResearchTableLinkGame.GRID_SIZE * tileSize + (ResearchTableLinkGame.GRID_SIZE - 1) * gap;
        gridOriginX = (width - totalGridSize) / 2;
        gridOriginY = (height - totalGridSize) / 2 + 10;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        // 绘制背景
        drawRect(0, 0, width, height, 0xFF222222);

        // 绘制网格
        drawGrid(mouseX, mouseY);

        // 绘制连接线
        if (mTile.pathPoints != null && mTile.pathTimer > 0) {
            drawPath();
        }

        // 绘制信息
        drawInfo();
    }

    private void drawGrid(int mouseX, int mouseY) {
        for (int y = 0; y < ResearchTableLinkGame.GRID_SIZE; y++) {
            for (int x = 0; x < ResearchTableLinkGame.GRID_SIZE; x++) {
                byte tileType = mTile.grid[y][x];
                if (tileType == 0) continue; // 空格不绘制

                int screenX = gridOriginX + x * (tileSize + gap);
                int screenY = gridOriginY + y * (tileSize + gap);

                // 检查鼠标悬停
                boolean isHovered = mouseX >= screenX && mouseX < screenX + tileSize &&
                        mouseY >= screenY && mouseY < screenY + tileSize;

                int color = 0xFF000000 | ((tileType * 73) & 0xFF) << 16 | ((tileType * 131) & 0xFF) << 8 | ((tileType * 197) & 0xFF);
                drawRect(screenX, screenY, screenX + tileSize, screenY + tileSize, color);
                int border = mTile.lastClickX == x && mTile.lastClickY == y ? 0xFFFFFF00 : isHovered ? 0xFFFFFFFF : 0xFF30343B;
                drawRect(screenX - 1, screenY - 1, screenX + tileSize + 1, screenY, border);
                drawRect(screenX - 1, screenY + tileSize, screenX + tileSize + 1, screenY + tileSize + 1, border);
                drawRect(screenX - 1, screenY, screenX, screenY + tileSize, border);
                drawRect(screenX + tileSize, screenY, screenX + tileSize + 1, screenY + tileSize, border);
            }
        }

        // 重置颜色
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void drawPath() {
        if (mTile.pathPoints == null || mTile.pathPoints.length < 4) return;

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glLineWidth(3.0F);
        GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F); // 绿色连接线

        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (int i = 0; i < mTile.pathPoints.length; i += 2) {
            int gridX = mTile.pathPoints[i];
            int gridY = mTile.pathPoints[i+1];
            int screenX = gridOriginX + gridX * (tileSize + gap) + tileSize / 2;
            int screenY = gridOriginY + gridY * (tileSize + gap) + tileSize / 2;
            GL11.glVertex2i(screenX, screenY);
        }
        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glLineWidth(1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void drawInfo() {
        String scoreStr = "Scores: " + mTile.scores;
        String levelStr = "Level: " + mTile.level;

        fontRendererObj.drawString(scoreStr, 10, 10, 0xFFFFFF);
        fontRendererObj.drawString(levelStr, 10, 25, 0xFFFFFF);

        if (!mTile.gameActive) {
            String hint = "Click to Start";
            fontRendererObj.drawString(hint, width / 2 - fontRendererObj.getStringWidth(hint) / 2, height / 2, 0xFF5555);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);

        if (button != 0) return; // 只响应左键

        // 检查是否点击了网格
        int totalGridSize = ResearchTableLinkGame.GRID_SIZE * tileSize + (ResearchTableLinkGame.GRID_SIZE - 1) * gap;
        if (mouseX >= gridOriginX && mouseX < gridOriginX + totalGridSize &&
                mouseY >= gridOriginY && mouseY < gridOriginY + totalGridSize) {

            int x = (mouseX - gridOriginX) / (tileSize + gap);
            int y = (mouseY - gridOriginY) / (tileSize + gap);

            int localX = (mouseX - gridOriginX) % (tileSize + gap);
            int localY = (mouseY - gridOriginY) % (tileSize + gap);
            if (localX >= tileSize || localY >= tileSize) return;
            kNetworkHandler.sendToServer(new PacketContainerButtonPressed(utils.dimID(mTile.getWorldObj()), mTile.xCoord, mTile.yCoord, mTile.zCoord, 1, (byte) x, (byte) y));
        } else {
            // 点击外部，尝试开始游戏
            if (!mTile.gameActive) {
                kNetworkHandler.sendToServer(new PacketContainerButtonPressed(utils.dimID(mTile.getWorldObj()), mTile.xCoord, mTile.yCoord, mTile.zCoord, 0));
            }
        }
    }
}
