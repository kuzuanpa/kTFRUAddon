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
import gregapi.tileentity.machines.MultiTileEntityBasicMachineElectric;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.stream.Collectors;

public class ResearchTableFillInPack extends MultiTileEntityBasicMachineElectric implements ITileReceiveContainerButtonClick, ITileSyncByteArrayLong {
    @Override public String getTileEntityName() {return "ktfru.multitileentity.research.table.fill_pack";}
    @Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerClientFillThePack(aPlayer.inventory, this, aGUIID, mGUITexture);
    }
    @Override public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerCommonFillThePack(aPlayer.inventory, this,aGUIID);
    }
    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        return getClientDataPacketByteArrayLong(aSendAll, new byte[0]);
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
    }

    @Override
    public boolean onTickCheck(long aTimer) {
        return super.onTickCheck(aTimer) || rng(10)==0;
    }

    @Override
    public void onContainerButtonClick(int buttonID, byte @Nullable [] data) {
        try {
            if(data == null)return;
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            DataInputStream dis = new DataInputStream(bis);
            String id = dis.readUTF();
        } catch (IOException e) {}
    }

    public static class PuzzleGame {

        private static final int MIN_TILE_SIZE = 2;
        private static final int MAX_ATTEMPTS = 5;
        public int size;
        public int tileCount;
        public List<PuzzleShape> originalTiles;
        public List<PuzzleShape> shuffledTiles;
        public Set<Point> placedPoints;

        public PuzzleGame(int size, int tileCount) {
            if (tileCount > size * size) throw new IllegalArgumentException("X 不能超过场地格子总数");
            this.size = size;
            this.tileCount = tileCount;
            this.originalTiles = new ArrayList<>();
            this.shuffledTiles = new ArrayList<>();
            this.placedPoints = new HashSet<>();
            initializeGame();
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

            originalTiles = regions.stream().map(PuzzleGame::postProcess).collect(Collectors.toList());
            shuffledTiles = new ArrayList<>(originalTiles);
            Collections.shuffle(shuffledTiles);
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

        public static PuzzleShape postProcess(Set<Point> original) {
            if (original.isEmpty()) {
                return new PuzzleShape(new HashSet<>());
            }

            int minX = Integer.MAX_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int maxY = Integer.MIN_VALUE;
            for (Point p : original) {
                minX = Math.min(minX, p.x);
                minY = Math.min(minY, p.y);
                maxX = Math.max(maxX, p.x);
                maxY = Math.max(maxY, p.y);
            }

            Set<Point> normalized = new HashSet<>();
            for (Point p : original) {
                normalized.add(new Point(p.x - minX, p.y - minY));
            }
            PuzzleShape shape = new PuzzleShape(normalized);
            shape.width = maxX - minX + 1;
            shape.height = maxY - minY + 1;
            return shape;
        }
        public static class PuzzleShape {
            public Set<Point> content;
            public int width;
            public int height;
            public int placedOnX;
            public int placedOnY;
            public PuzzleShape(Set<Point> content){
                this.content= content;
            }
        }
    }
}
