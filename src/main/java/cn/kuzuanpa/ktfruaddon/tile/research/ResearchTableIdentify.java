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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.Queue;
import java.util.*;

public class ResearchTableIdentify extends ResearchTableBase implements ITileReceiveContainerButtonClick, ITileSyncByteArrayLong {
    public ColorGame theGame;
    public ColorGame theGameClient;
    public ResearchTableIdentify(){
        theGame = new ColorGame((byte) 6, (byte) 4);
        theGame.initializeGame();
    }
    @Override public String getTileEntityName() {return "ktfru.multitileentity.research.table.identify";}
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
        theGameClient = ColorGame.loadFromByteArray(aData);
    }

    @Override
    public boolean onTickCheck(long aTimer) {
        return super.onTickCheck(aTimer) || rng(10)==0;
    }

    @Override
    public void onContainerButtonClick(int buttonID, byte @Nullable [] data) {
        if(data == null || data.length == 0)return;
        if(data[0] == -1)theGame.removeTile(theGame.tiles.get((byte)buttonID));
        if(data[0] == 1)theGame.placeTile(theGame.tiles.get((byte)buttonID), data[1], data[2]);
    }

    public static class ColorGame {

        private static final int MIN_TILE_SIZE = 2;
        private static final int MAX_ATTEMPTS = 5;
        public byte size;
        public byte tileCount;
        public HashMap<Byte, PuzzleShape> tiles;
        public Set<Point> placedPoints;

        public ColorGame(byte size, byte tileCount) {
            if (tileCount > size * size) throw new IllegalArgumentException("X 不能超过场地格子总数");
            this.size = size;
            this.tileCount = tileCount;
            this.tiles = new HashMap<>();
            this.placedPoints = new HashSet<>();
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
        public static ColorGame loadFromByteArray(byte[] data) {
            try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
                 DataInputStream dis = new DataInputStream(bis)) {

                // 读取基础信息
                byte size = dis.readByte();
                byte tileCount = dis.readByte();
                ColorGame game = new ColorGame(size, tileCount);
                // 读取图块数据
                game.tiles = new HashMap<>();
                for (int i = 0; i < tileCount; i++) {
                    byte puzzleID = dis.readByte();
                    byte pointCount = dis.readByte();
                    Set<Point> tile = new HashSet<>();
                    for (int j = 0; j < pointCount; j++) {
                        int x = dis.readByte();
                        int y = dis.readByte();
                        tile.add(new Point(x, y));
                    }
                    game.tiles.put(puzzleID, new PuzzleShape(tile));
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

        public boolean placeTile(PuzzleShape tile, int offsetX, int offsetY) {
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
        }

        public boolean checkWin() {
            return placedPoints.size() == size * size;
        }

        public static class PuzzleShape {
            public Set<Point> content;
            public int width;
            public int height;
            public int placedOnX;
            public int placedOnY;
            public PuzzleShape(Set<Point> content){
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
        }
    }
}
