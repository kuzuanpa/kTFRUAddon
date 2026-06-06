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

package cn.kuzuanpa.ktfruaddon.tile.research;

import cn.kuzuanpa.ktfruaddon.api.network.ITileReceiveContainerButtonClick;
import cn.kuzuanpa.ktfruaddon.api.network.ITileSyncByteArrayLong;
import cn.kuzuanpa.ktfruaddon.client.gui.research.ContainerClientLinkGame;
import cn.kuzuanpa.ktfruaddon.client.gui.research.ContainerCommonLinkGame;
import cn.kuzuanpa.ktfruaddon.ktfruaddon;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.IBlockAccess;

import java.io.*;
import java.util.Random;

public class ResearchTableLinkGame extends TileEntityBase09FacingSingle implements ITileReceiveContainerButtonClick, ITileSyncByteArrayLong {

    // 游戏配置
    public static final int GRID_SIZE = 8; // 8x8 网格
    public static final int TILE_TYPES = 16; // 16种不同的图块类型 (对应纹理的 16x16 分割)

    // 游戏状态
    public byte[][] grid = new byte[GRID_SIZE][GRID_SIZE]; // 地图数据
    public long scores = 0; // 分数
    public int level = 1; // 关卡
    public boolean gameActive = false; // 游戏是否进行中

    // 连接路径数据 (用于客户端渲染连接线)
    public int[] pathPoints = null; // 存储连接路径的坐标点 [x1, y1, x2, y2, ...]
    public int pathTimer = 0; // 路径显示计时器

    private final Random random = new Random();

    public ResearchTableLinkGame() {
        super();
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        scores = aNBT.getLong("scores");
        level = aNBT.getInteger("level");
        gameActive = aNBT.getBoolean("active");

        // 读取网格数据
        if (aNBT.hasKey("gridData")) {
            byte[] data = aNBT.getByteArray("gridData");
            if (data.length == GRID_SIZE * GRID_SIZE) {
                for (int y = 0; y < GRID_SIZE; y++) {
                    for (int x = 0; x < GRID_SIZE; x++) {
                        grid[y][x] = data[y * GRID_SIZE + x];
                    }
                }
            }
        }
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        aNBT.setLong("scores", scores);
        aNBT.setInteger("level", level);
        aNBT.setBoolean("active", gameActive);

        // 写入网格数据
        byte[] data = new byte[GRID_SIZE * GRID_SIZE];
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                data[y * GRID_SIZE + x] = grid[y][x];
            }
        }
        aNBT.setByteArray("gridData", data);
    }

    @Override
    public boolean canDrop(int i) {
        return false;
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.research.table.linkgame";
    }

    @Override
    public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerClientLinkGame(aPlayer.inventory, this, aGUIID);
    }

    @Override
    public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerCommonLinkGame(aPlayer, this, aGUIID);
    }

    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        return getClientDataPacketByteArrayLong(aSendAll, saveToByteArray());
    }

    @Override
    public INetworkHandler getNetworkHandler() {
        return ktfruaddon.kNetworkHandler;
    }

    @Override
    public INetworkHandler getNetworkHandlerNonOwned() {
        return ktfruaddon.kNetworkHandler2;
    }

    @Override
    public void receiveDataByteArrayLong(IBlockAccess aWorld, int aX, int aY, int aZ, byte[] aData, INetworkHandler aNetworkHandler) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(aData);
             DataInputStream dis = new DataInputStream(bis)) {

            setDirectionData(dis.readByte());

            // 读取游戏状态
            scores = dis.readLong();
            level = dis.readInt();
            gameActive = dis.readBoolean();

            // 读取网格数据
            for (int y = 0; y < GRID_SIZE; y++) {
                for (int x = 0; x < GRID_SIZE; x++) {
                    grid[y][x] = dis.readByte();
                }
            }

            // 读取路径数据
            if (dis.readBoolean()) {
                int pathLength = dis.readInt();
                pathPoints = new int[pathLength];
                for (int i = 0; i < pathLength; i++) {
                    pathPoints[i] = dis.readInt();
                }
                pathTimer = 20; // 显示 20 tick (约1秒)
            } else {
                pathPoints = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] saveToByteArray() {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             DataOutputStream dos = new DataOutputStream(bos)) {

            dos.writeByte(getDirectionData());

            // 写入游戏状态
            dos.writeLong(scores);
            dos.writeInt(level);
            dos.writeBoolean(gameActive);

            // 写入网格数据
            for (int y = 0; y < GRID_SIZE; y++) {
                for (int x = 0; x < GRID_SIZE; x++) {
                    dos.writeByte(grid[y][x]);
                }
            }

            // 写入路径数据
            if (pathPoints != null && pathTimer > 0) {
                dos.writeBoolean(true);
                dos.writeInt(pathPoints.length);
                for (int point : pathPoints) {
                    dos.writeInt(point);
                }
            } else {
                dos.writeBoolean(false);
            }

            dos.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if (aIsServerSide) {
            // 处理路径计时器
            if (pathTimer > 0) {
                pathTimer--;
                if (pathTimer == 0) {
                    pathPoints = null;
                    markDirty();
                }
            }
        }
    }

    @Override
    public ITexture getTexture2(Block block, int i, byte b, boolean[] booleans) {
        return null;
    }

    @Override
    public void onContainerButtonClick(int buttonID, byte[] data) {
        if (buttonID == -100) {
            // 重置游戏
            resetGame();
            return;
        }

        if (buttonID == 0) {
            // 开始游戏
            if (!gameActive) startLevel();
            return;
        }

        if (data != null && data.length >= 2) {
            // 处理点击: data[0]=x, data[1]=y
            int x = data[0] & 0xFF;
            int y = data[1] & 0xFF;
            handleClick(x, y);
        }
    }

    private void resetGame() {
        level = 1;
        scores = 0;
        gameActive = false;
        pathPoints = null;
        startLevel();
    }

    private void startLevel() {
        // 生成地图
        int totalTiles = GRID_SIZE * GRID_SIZE;
        if (totalTiles % 2 != 0) totalTiles--; // 确保是偶数

        // 创建成对的图块
        java.util.List<Byte> tiles = new java.util.ArrayList<>();
        for (int i = 0; i < totalTiles / 2; i++) {
            byte type = (byte) (random.nextInt(TILE_TYPES) + 1); // 1-16
            tiles.add(type);
            tiles.add(type);
        }

        // 打乱顺序
        java.util.Collections.shuffle(tiles);

        // 填充网格
        int idx = 0;
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                if (idx < tiles.size()) {
                    grid[y][x] = tiles.get(idx++);
                } else {
                    grid[y][x] = 0; // 空余位置填0
                }
            }
        }

        gameActive = true;
        markDirty();
    }

    // 记录上一次点击的位置
    private int lastClickX = -1, lastClickY = -1;

    private void handleClick(int x, int y) {
        if (!gameActive) return;
        if (x < 0 || x >= GRID_SIZE || y < 0 || y >= GRID_SIZE) return;
        if (grid[y][x] == 0) return; // 点击了空地

        if (lastClickX == -1) {
            // 第一次点击，选中
            lastClickX = x;
            lastClickY = y;
        } else {
            // 第二次点击
            if (x == lastClickX && y == lastClickY) {
                // 点击了同一个，取消选中
                lastClickX = -1;
                lastClickY = -1;
            } else {
                // 检查是否匹配
                if (grid[y][x] == grid[lastClickY][lastClickX]) {
                    // 类型相同，检查路径
                    int[] path = findPath(lastClickX, lastClickY, x, y);
                    if (path != null) {
                        // 消除
                        grid[y][x] = 0;
                        grid[lastClickY][lastClickX] = 0;
                        scores += 10 * level;

                        // 设置路径用于显示
                        pathPoints = path;
                        pathTimer = 20;

                        // 检查是否胜利
                        if (checkWin()) {
                            level++;
                            startLevel(); // 下一关
                        }

                        markDirty();
                    } else {
                        // 无法连接，更新选中为当前点击的
                        lastClickX = x;
                        lastClickY = y;
                    }
                } else {
                    // 类型不同，更新选中
                    lastClickX = x;
                    lastClickY = y;
                }
            }
        }
    }

    private boolean checkWin() {
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                if (grid[y][x] != 0) return false;
            }
        }
        return true;
    }

    // --- 连连看算法核心 ---

    // 检查点是否为空（或者是起点/终点）
    private boolean isEmpty(int x, int y, int x1, int y1, int x2, int y2) {
        if (x < 0 || x >= GRID_SIZE || y < 0 || y >= GRID_SIZE) return false; // 边界检查
        if ((x == x1 && y == y1) || (x == x2 && y == y2)) return true;
        return grid[y][x] == 0;
    }

    // 检查两点是否可以直线连接（不包括端点）
    private boolean checkLine(int x1, int y1, int x2, int y2, int targetX1, int targetY1, int targetX2, int targetY2) {
        if (x1 == x2) { // 同一列
            int minY = Math.min(y1, y2);
            int maxY = Math.max(y1, y2);
            for (int y = minY + 1; y < maxY; y++) {
                if (!isEmpty(x1, y, targetX1, targetY1, targetX2, targetY2)) return false;
            }
            return true;
        } else if (y1 == y2) { // 同一行
            int minX = Math.min(x1, x2);
            int maxX = Math.max(x1, x2);
            for (int x = minX + 1; x < maxX; x++) {
                if (!isEmpty(x, y1, targetX1, targetY1, targetX2, targetY2)) return false;
            }
            return true;
        }
        return false;
    }

    // 寻找路径
    private int[] findPath(int x1, int y1, int x2, int y2) {
        // 1. 直连
        if (checkLine(x1, y1, x2, y2, x1, y1, x2, y2)) {
            return new int[]{x1, y1, x2, y2};
        }

        // 2. 一折连接 (找一个公共空点 C)
        // C 必须与 A 直连，且与 B 直连
        // C 的坐标可能是 (x1, y2) 或 (x2, y1)
        if (isEmpty(x1, y2, x1, y1, x2, y2) && checkLine(x1, y1, x1, y2, x1, y1, x2, y2) && checkLine(x1, y2, x2, y2, x1, y1, x2, y2)) {
            return new int[]{x1, y1, x1, y2, x2, y2};
        }
        if (isEmpty(x2, y1, x1, y1, x2, y2) && checkLine(x1, y1, x2, y1, x1, y1, x2, y2) && checkLine(x2, y1, x2, y2, x1, y1, x2, y2)) {
            return new int[]{x1, y1, x2, y1, x2, y2};
        }

        // 3. 两折连接
        // 扫描 A 点的十字线上的所有空点，看是否能与 B 点一折连接
        // 扫描行
        for (int x = 0; x < GRID_SIZE; x++) {
            if (x == x1) continue;
            if (isEmpty(x, y1, x1, y1, x2, y2) && checkLine(x1, y1, x, y1, x1, y1, x2, y2)) {
                // 检查 (x, y1) 到 (x2, y2) 是否可以一折连接
                // 一折连接的逻辑是：找公共点 (x, y2) 或 (x2, y1)
                // (x2, y1) 已经被占用（因为是 A 的行），所以只检查 (x, y2)
                if (isEmpty(x, y2, x1, y1, x2, y2) && checkLine(x, y1, x, y2, x1, y1, x2, y2) && checkLine(x, y2, x2, y2, x1, y1, x2, y2)) {
                    return new int[]{x1, y1, x, y1, x, y2, x2, y2};
                }
            }
        }
        // 扫描列
        for (int y = 0; y < GRID_SIZE; y++) {
            if (y == y1) continue;
            if (isEmpty(x1, y, x1, y1, x2, y2) && checkLine(x1, y1, x1, y, x1, y1, x2, y2)) {
                // 检查 (x1, y) 到 (x2, y2) 是否可以一折连接
                // 只检查 (x2, y)
                if (isEmpty(x2, y, x1, y1, x2, y2) && checkLine(x1, y, x2, y, x1, y1, x2, y2) && checkLine(x2, y, x2, y2, x1, y1, x2, y2)) {
                    return new int[]{x1, y1, x1, y, x2, y, x2, y2};
                }
            }
        }

        return null;
    }
}
