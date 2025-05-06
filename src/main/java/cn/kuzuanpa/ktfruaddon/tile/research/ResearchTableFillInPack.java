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
import cn.kuzuanpa.ktfruaddon.client.gui.research.ContainerClientFillThePack;
import cn.kuzuanpa.ktfruaddon.client.gui.research.ContainerCommonFillThePack;
import cn.kuzuanpa.ktfruaddon.ktfruaddon;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import org.jetbrains.annotations.Nullable;

import java.awt.Point;
import java.io.*;
import java.util.List;
import java.util.Queue;
import java.util.*;

public class ResearchTableFillInPack extends ResearchTableBase implements ITileReceiveContainerButtonClick, ITileSyncByteArrayLong {
    public ResearchTableFillInPack.PuzzleGame theGame= new PuzzleGame(0,0);
    public ResearchTableFillInPack.PuzzleGame theGameClient = new PuzzleGame(0,0);
    public boolean theGameNeedSync = true;

    @Override public String getTileEntityName() {return "ktfru.multitileentity.research.table.fill_pack";}
    @Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerClientFillThePack(aPlayer.inventory, this, aGUIID);
    }
    @Override public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerCommonFillThePack(aPlayer.inventory, this,aGUIID);
    }
    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        return getClientDataPacketByteArrayLong(aSendAll, theGame.saveToByteArray());
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
        PuzzleGame theGame = PuzzleGame.loadFromByteArray(aData);
        if(theGame != null)theGameClient = theGame;
    }

    @Override
    public boolean onTickCheck(long aTimer) {
        boolean isGameNeedSync = theGameNeedSync;
        theGameNeedSync=false;
        return super.onTickCheck(aTimer) || isGameNeedSync;
    }

    @Override
    public void onContainerButtonClick(int buttonID, byte @Nullable [] data) {
        if(data == null)return;
        if(buttonID == 0){
            theGame = new PuzzleGame((byte)16,(byte)4);
            theGame.initializeGame();
            theGameNeedSync=true;
        }
        if(buttonID == -1)theGame.removeTile(theGame.tiles.get(data[0]));
        if(buttonID == 1)theGame.placeTile(theGame.tiles.get(data[0]), data[1], data[2], false);
    }

    @Override
    public boolean allowInteraction(Entity aEntity) {
        return super.allowInteraction(aEntity);
    }

    public static class PuzzleGame {

        private static final int MIN_TILE_SIZE = 2;
        private static final int MAX_ATTEMPTS = 5;
        public byte size;
        public byte tileCount;
        public HashMap<Byte, PuzzleShape> tiles;
        public Set<Point> placedPoints;
        public PuzzleGame(int size, int tileCount){
            this((byte)size,(byte)tileCount);
        }
        public PuzzleGame(byte size, byte tileCount) {
            if (tileCount > size * size) throw new IllegalArgumentException("X 不能超过场地格子总数");
            this.size = size;
            this.tileCount = tileCount;
            this.tiles = new HashMap<>();
            this.placedPoints = new HashSet<>();
        }
        public double calculateScore() {
            if (!checkWin()) return 0; // 仅当游戏成功时计分

            final double baseComplexity = Math.pow(size, 2); // 基础复杂度与尺寸相关
            final double optimalRatio = 0.3; // 最佳图块数量占比经验值
            final double penaltyFactor = 2.5; // 偏离最佳值的惩罚系数

            double totalCells = size * size;
            double actualRatio = tileCount / totalCells;

            // 核心评分公式
            double score = baseComplexity *
                    Math.exp(-penaltyFactor * Math.pow(actualRatio - optimalRatio, 2)) *
                    (1 - Math.exp(-tileCount / (size * 0.8))); // 数量不足惩罚项

            // 限制极值情况
            double minScore = Math.sqrt(size + tileCount/4f);
            double maxScore = baseComplexity * 10;
            return Math.max(minScore, Math.min(score, maxScore));
        }
        public byte[] saveToByteArray() {
            try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos)) {

                // 写入基础信息
                dos.writeByte(size);        // 场地尺寸
                dos.writeByte(tileCount);   // 图块总数

                // 写入每个图块数据
                for (Map.Entry<Byte, PuzzleShape> entry : tiles.entrySet() ) {
                    PuzzleShape tile = entry.getValue();
                    // 写入当前图块点数
                    dos.writeByte(entry.getKey());
                    dos.writeByte(tile.placedOnX);
                    dos.writeByte(tile.placedOnY);
                    dos.writeByte(tile.content.size());
                    // 写入每个点的坐标
                    for (Point p : tile.content) {
                        dos.writeByte(p.x);
                        dos.writeByte(p.y);
                    }
                }

                dos.flush();
                return bos.toByteArray();
            }catch (IOException e){
                e.printStackTrace();
                return new byte[0];
            }
        }
        public static PuzzleGame loadFromByteArray(byte[] data) {
            try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
                 DataInputStream dis = new DataInputStream(bis)) {

                // 读取基础信息
                byte size = dis.readByte();
                byte tileCount = dis.readByte();
                PuzzleGame game = new PuzzleGame(size, tileCount);
                // 读取图块数据
                game.tiles = new HashMap<>();
                for (int i = 0; i < tileCount; i++) {
                    byte puzzleID = dis.readByte();
                    byte pX = dis.readByte();
                    byte pY = dis.readByte();
                    byte pointCount = dis.readByte();
                    Set<Point> tile = new HashSet<>();
                    for (int j = 0; j < pointCount; j++) {
                        int x = dis.readByte();
                        int y = dis.readByte();
                        tile.add(new Point(x, y));
                    }
                    game.tiles.put(puzzleID, new PuzzleShape(tile).setPlacedOnX(pX).setPlacedOnY(pY));
                }
                return game;
            }catch (IOException e){
                e.printStackTrace();
                return null;
            }
        }

        private void initializeGame() {
            Set<Point> allPoints = new HashSet<>();
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    allPoints.add(new Point(x, y));
                }
            }

            List<Set<Point>> regions = new ArrayList<>();
            regions.add(allPoints);
            Random random = new Random();

            while (regions.size() < tileCount) {
                // 优先选择最大的区域进行分裂
                Set<Point> largest = findLargestRegion(regions);
                if (largest == null || largest.size() < MIN_TILE_SIZE * 2) break;

                List<Set<Point>> splitResult = splitRegion(largest, random);
                if (splitResult != null) {
                    regions.remove(largest);
                    regions.addAll(splitResult);
                } else {
                    // 尝试强制分裂较小的区域
                    Optional<Set<Point>> splittable = regions.stream()
                            .filter(r -> r.size() >= MIN_TILE_SIZE * 2)
                            .findFirst();
                    if (!splittable.isPresent()) break;
                    regions.remove(splittable.get());
                    regions.addAll(forceSplit(splittable.get(), random));
                }
            }
            byte i = 0;
            tiles = new HashMap<>();
            for (Set<Point> region : regions) tiles.put(i++, new PuzzleShape(region));

        }

        private Set<Point> findLargestRegion(List<Set<Point>> regions) {
            return regions.stream()
                    .max(Comparator.comparingInt(Set::size))
                    .orElse(null);
        }

        private List<Set<Point>> splitRegion(Set<Point> region, Random random) {
            List<Point> points = new ArrayList<>(region);

            for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
                Point seed = points.get(random.nextInt(points.size()));

                // 动态计算分裂比例（40%-60%）
                int minSplit = (int)(region.size() * 0.4);
                int maxSplit = (int)(region.size() * 0.6);
                minSplit = Math.max(MIN_TILE_SIZE, minSplit);
                maxSplit = Math.min(region.size() - MIN_TILE_SIZE, maxSplit);
                if (minSplit > maxSplit) return null;

                int targetSize = minSplit + random.nextInt(maxSplit - minSplit + 1);

                Set<Point> partA = growRegion(region, seed, targetSize, random);
                Set<Point> partB = new HashSet<>(region);
                partB.removeAll(partA);

                if (isValidSplit(partA, partB)) {
                    return Arrays.asList(partA, partB);
                }
            }
            return null;
        }

        private Set<Point> growRegion(Set<Point> region, Point seed, int targetSize, Random random) {
            Set<Point> result = new HashSet<>();
            Queue<Point> queue = new LinkedList<>();
            result.add(seed);
            queue.add(seed);

            while (result.size() < targetSize && !queue.isEmpty()) {
                Point current = queue.poll();

                List<Point> neighbors = getShuffledNeighbors(current, random);
                for (Point neighbor : neighbors) {
                    if (region.contains(neighbor) && !result.contains(neighbor)) {
                        result.add(neighbor);
                        queue.add(neighbor);
                        if (result.size() == targetSize) break;
                    }
                }
            }
            return result;
        }

        private List<Point> getShuffledNeighbors(Point p, Random random) {
            List<Point> neighbors = Arrays.asList(
                    new Point(p.x+1, p.y), new Point(p.x-1, p.y),
                    new Point(p.x, p.y+1), new Point(p.x, p.y-1)
            );
            Collections.shuffle(neighbors, random);
            return neighbors;
        }

        private boolean isValidSplit(Set<Point> a, Set<Point> b) {
            return a.size() >= MIN_TILE_SIZE && b.size() >= MIN_TILE_SIZE &&
                    isConnected(a) && isConnected(b);
        }

        private List<Set<Point>> forceSplit(Set<Point> region, Random random) {
            // 确保总能分裂的保底方法
            Point seed = new ArrayList<>(region).get(random.nextInt(region.size()));
            Set<Point> partA = growRegion(region, seed, MIN_TILE_SIZE, random);
            Set<Point> partB = new HashSet<>(region);
            partB.removeAll(partA);
            return Arrays.asList(partA, partB);
        }

        private boolean isConnected(Set<Point> region) {
            if (region.isEmpty()) return false;
            Set<Point> visited = new HashSet<>();
            Queue<Point> queue = new LinkedList<>();
            Point start = region.iterator().next();
            queue.add(start);
            visited.add(start);

            while (!queue.isEmpty()) {
                Point current = queue.poll();
                for (Point neighbor : Arrays.asList(
                        new Point(current.x + 1, current.y),
                        new Point(current.x - 1, current.y),
                        new Point(current.x, current.y + 1),
                        new Point(current.x, current.y - 1))
                ) {
                    if (region.contains(neighbor) && !visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.add(neighbor);
                    }
                }
            }
            return visited.size() == region.size();
        }

        public boolean placeTile(PuzzleShape tile, int offsetX, int offsetY, boolean dryRun) {
            Set<Point> displaced = new HashSet<>();

            // 检查边界和冲突
            for (Point p : tile.content) {
                int newX = p.x + offsetX;
                int newY = p.y + offsetY;
                if (newX < 0 || newX >= size || newY < 0 || newY >= size) {
                    return false;
                }
                Point displacedPoint = new Point(newX, newY);
                if (placedPoints.contains(displacedPoint)) {
                    return false;
                }
                displaced.add(displacedPoint);
            }
            if(dryRun) return true;
            tile.placedOnX = offsetX;
            tile.placedOnY = offsetY;
            placedPoints.addAll(displaced);
            return true;
        }

        public void removeTile(PuzzleShape tile) {
            if(tile.placedOnX == -1)return;
            for (Point p : tile.content) {
                int newX = p.x + tile.placedOnX;
                int newY = p.y + tile.placedOnY;
                Point displacedPoint = new Point(newX, newY);
                placedPoints.remove(displacedPoint);
            }
            tile.placedOnX=-1;
            tile.placedOnY=-1;
        }

        public boolean checkWin() {
            return placedPoints.size() == size * size;
        }

        public static class PuzzleShape {
            public Set<Point> content;
            public int width;
            public int height;
            public int placedOnX = -1;
            public int placedOnY = -1;
            public PuzzleShape(Set<Point> content){
                this.content = content;
                normalize();
            }
            public void normalize(){
                int minX = Integer.MAX_VALUE;
                int minY = Integer.MAX_VALUE;
                int maxX = Integer.MIN_VALUE;
                int maxY = Integer.MIN_VALUE;
                for (Point p : content) {
                    minX = Math.min(minX, p.x);
                    minY = Math.min(minY, p.y);
                    maxX = Math.max(maxX, p.x);
                    maxY = Math.max(maxY, p.y);
                }

                Set<Point> normalized = new HashSet<>();
                for (Point p : content) {
                    normalized.add(new Point(p.x - minX, p.y - minY));
                }
                this.content=normalized;
                width = maxX - minX + 1;
                height = maxY - minY + 1;
            }

            public void rotateClockwise90() {
                Set<Point> rotated = new HashSet<>();

                for (Point p : content) {
                    // 顺时针旋转90度的变换公式：(x, y) → (y, -x)
                    int newX = p.y;
                    int newY = -p.x;
                    rotated.add(new Point(newX, newY));
                }

                content = rotated;

                normalize();
            }

            public PuzzleShape setPlacedOnX(int placedOnX) {
                this.placedOnX = placedOnX;
                return this;
            }
            public PuzzleShape setPlacedOnY(int placedOnY) {
                this.placedOnY = placedOnY;
                return this;
            }
        }
    }
}
