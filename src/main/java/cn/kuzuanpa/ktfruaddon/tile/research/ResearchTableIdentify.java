/*
 * This class was created by <CodeGeeX>. It is distributed as
 * part of the kTFRUAddon Mod.
 */

package cn.kuzuanpa.ktfruaddon.tile.research;

import cn.kuzuanpa.ktfruaddon.api.network.ITileReceiveContainerButtonClick;
import cn.kuzuanpa.ktfruaddon.api.network.ITileSyncByteArrayLong;
import cn.kuzuanpa.ktfruaddon.client.gui.research.ContainerClientIdentify;
import cn.kuzuanpa.ktfruaddon.client.gui.research.ContainerCommonIdentify;
import cn.kuzuanpa.ktfruaddon.ktfruaddon;
import gregapi.data.CS;
import gregapi.data.LH;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.IBlockAccess;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.List;
import java.util.Random;

public class ResearchTableIdentify extends ResearchTableBase implements ITileReceiveContainerButtonClick, ITileSyncByteArrayLong {

    // 游戏状态
    public byte size = 5; // 默认 5x5
    public int targetIndex = -1; // 目标方块索引
    public int targetColor = 0xFFFFFF; // 目标颜色 (RGB)
    public int decoyColor = 0xFFFFFF; // 干扰颜色 (RGB)
    public int level = 1; // 当前关卡
    public float scores = 0; // 玩家得分
    public boolean gameActive = false, needSync = false; // 游戏是否进行中

    private final Random random = new Random();

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (isServerSide()) {
            if (CS.TOOL_magnifyingglass.equals(aTool)) {
                aChatReturn.add(LH.get("RESEARCH_TABLE_IDENTIFY_SCORES") + ": " + scores);
                return 1;
            }
            if (CS.TOOL_screwdriver.equals(aTool)) {
                // 调试：重置游戏
                resetGame();
                return 1;
            }
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        aNBT.setFloat("scores", scores);
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey("scores")) scores = aNBT.getFloat("scores");
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.research.table.identify";
    }

    @Override
    public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerClientIdentify(aPlayer.inventory, this, aGUIID);
    }

    @Override
    public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerCommonIdentify(aPlayer.inventory, this, aGUIID);
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

            // 读取 Tile 方向
            setDirectionData(dis.readByte());

            // 读取游戏数据
            size = dis.readByte();
            targetIndex = dis.readInt();
            targetColor = dis.readInt();
            decoyColor = dis.readInt();
            level = dis.readInt();
            scores = dis.readFloat();
            gameActive = dis.readBoolean();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] saveToByteArray() {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             DataOutputStream dos = new DataOutputStream(bos)) {

            // 写入 Tile 方向
            dos.writeByte(getDirectionData());

            // 写入游戏数据
            dos.writeByte(size);
            dos.writeInt(targetIndex);
            dos.writeInt(targetColor);
            dos.writeInt(decoyColor);
            dos.writeInt(level);
            dos.writeFloat(scores);
            dos.writeBoolean(gameActive);

            dos.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    @Override
    public boolean onTickCheck(long aTimer) {
        boolean result = super.onTickCheck(aTimer) || needSync;
        needSync = false;
        return result;
    }

    @Override
    public void onContainerButtonClick(int buttonID, byte @Nullable [] data) {
        if (buttonID == -100) {
            // 重置游戏
            resetGame();
            return;
        }

        if (buttonID == 0) {
            // 开始游戏/下一关
            startLevel();
            return;
        }

        if (data != null && data.length > 0) {
            // 处理玩家点击
            int clickedIndex = data[0] & 0xFF; // 将 byte 转换为无符号 int (0-255)

            if (gameActive && clickedIndex == targetIndex) {
                // 玩家找到了目标
                handleSuccess();
            } else if (gameActive) {
                // 玩家点错了
                handleFailure();
            }
        }
    }

    private void resetGame() {
        level = 1;
        size = 5;
        gameActive = false;
        startLevel();
    }

    private void startLevel() {
        size = (byte) Math.min(10, 5 + (level - 1) / 3);
        int maxDiff = Math.max(1, 36 - level * 2);

        targetColor = random.nextInt(0xFFFFFF + 1);

        int r = (targetColor >> 16) & 0xFF;
        int g = (targetColor >> 8) & 0xFF;
        int b = targetColor & 0xFF;

        int dr = (random.nextBoolean()? maxDiff : - maxDiff);
        int dg = (random.nextBoolean()? maxDiff : - maxDiff);
        int db = (random.nextBoolean()? maxDiff : - maxDiff);

        if(r+dr < 0 || r+dr > 255) r -= dr; else r += dr;
        if(g+dg < 0 || g+dg > 255) g -= dg; else g += dg;
        if(b+db < 0 || b+db > 255) b -= db; else b += db;

        decoyColor = (r << 16) | (g << 8) | b;

        // 随机选择目标位置
        targetIndex = random.nextInt(size * size);

        gameActive = true;
        needSync = true;
    }

    private void handleSuccess() {
        // 计算得分：基础分 + 难度加成
        float points = (float) (1 + 0.15F*Math.pow(level, 2F));
        scores += points;

        // 进入下一关
        level++;
        startLevel();
    }

    private void handleFailure() {
        // 游戏失败，重置
        resetGame();
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        // 如果有分数，可以在这里尝试推进研究进度
        // 类似于 ResearchTableFillInPack 中的逻辑
        if (aIsServerSide && scores > 0) {
            // scores -= tryPromoteCurrentProjectProgress(MiniGameIdentifyTask.class, scores, false);
            // 注意：你需要创建对应的 MiniGameIdentifyTask 类
        }
    }

    // Icons
    public final static IIconContainer
            sTextureSides = new Textures.BlockIcons.CustomIcon("machines/research/table/identify/base"),
            sOverlayStop = new Textures.BlockIcons.CustomIcon("machines/research/table/identify/front");

    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if (!aShouldSideBeRendered[aSide]) return null;
        if (aSide == mFacing) return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides, mRGBa), BlockTextureDefault.get(sOverlayStop));
        return BlockTextureDefault.get(sTextureSides, mRGBa);
    }
}
