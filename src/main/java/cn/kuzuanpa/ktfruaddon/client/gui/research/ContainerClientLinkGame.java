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

import cn.kuzuanpa.ktfruaddon.tile.research.ResearchTableLinkGame;
import gregapi.gui.ContainerClientDefault;
import gregapi.tileentity.ITileEntityInventoryGUI;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ContainerClientLinkGame extends ContainerClientDefault {

    private final ResearchTableLinkGame mTile;

    // 纹理资源：512x512 的大图，包含 16x16 个小图块
    // 请确保这个资源文件存在于你的 mod 中
    private static final ResourceLocation TILES_TEXTURE = new ResourceLocation("ktfruaddon", "textures/gui/research/linkgame_tiles.png");

    // 布局参数
    private int gridOriginX;
    private int gridOriginY;
    private int tileSize = 32; // 每个格子的渲染大小
    private int gap = 2; // 间隙

    public ContainerClientLinkGame(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, int aGUIID) {
        super(new ContainerCommonIdentify(aInventoryPlayer, aTileEntity, aGUIID));
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
        mc.getTextureManager().bindTexture(TILES_TEXTURE);

        for (int y = 0; y < ResearchTableLinkGame.GRID_SIZE; y++) {
            for (int x = 0; x < ResearchTableLinkGame.GRID_SIZE; x++) {
                byte tileType = mTile.grid[y][x];
                if (tileType == 0) continue; // 空格不绘制

                int screenX = gridOriginX + x * (tileSize + gap);
                int screenY = gridOriginY + y * (tileSize + gap);

                // 计算纹理坐标
                // 纹理被分为 16x16，每个小图块大小为 32x32 (512/16)
                // tileType 1-16 对应索引 0-15
                int texIndex = tileType - 1;
                int u = (texIndex % 16) * 32;
                int v = (texIndex / 16) * 32;

                // 检查鼠标悬停
                boolean isHovered = mouseX >= screenX && mouseX < screenX + tileSize &&
                        mouseY >= screenY && mouseY < screenY + tileSize;

                // 绘制图块
                if (isHovered) {
                    // 悬停时稍微提亮
                    GL11.glColor4f(1.2F, 1.2F, 1.2F, 1.0F);
                } else {
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                }

                drawTexturedModalRect(screenX, screenY, u, v, tileSize, tileSize);

                // 绘制选中框
                //if (mTile.lastClickX == x && mTile.lastClickY == y) {
                    drawRect(screenX - 1, screenY - 1, screenX + tileSize + 1, screenY + tileSize + 1, 0xFFFF0000);
                //}
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

            // 发送点击事件
            byte[] data = new byte[]{(byte) x, (byte) y};
            //sendContainerButtonClick(1, data);
        } else {
            // 点击外部，尝试开始游戏
            if (!mTile.gameActive) {
               // sendContainerButtonClick(0, null);
            }
        }
    }
}
