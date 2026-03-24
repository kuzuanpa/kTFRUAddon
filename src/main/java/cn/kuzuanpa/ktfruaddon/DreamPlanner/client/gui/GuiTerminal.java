package cn.kuzuanpa.ktfruaddon.DreamPlanner.client.gui;

import cn.kuzuanpa.kGuiLib.client.kGuiContainerBase;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.ITransferable;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.TransferableStack;
import gregapi.gui.ContainerCommon;
import gregapi.tileentity.ITileEntityInventoryGUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;

import java.util.*;

public class GuiTerminal extends kGuiContainerBase {
    private float scrollPosition = 0.0F;
    private ContainerTerminal container;

    public GuiTerminal(ContainerTerminal container) {
        super(container);
        this.container = container;
    }

    public void onDeltaSyncReceived(ContainerTerminal container, Map<ITransferable, Long> changes) {
        for (Map.Entry<ITransferable, Long> change : changes.entrySet()) {
            boolean found = false;

            // 1. 在本地缓存中寻找对应的物品
            Iterator<TransferableStack> iterator = container.clientNetworkItems.iterator();
            while (iterator.hasNext()){
                TransferableStack tStack = iterator.next();
                ItemStack currentItemStack = tStack.type.describe().getItemStack();
                if (currentItemStack.equals(change.getKey().describe().getItemStack())) {
                    found = true;
                    if (change.getValue() <= 0) iterator.remove();
                    else tStack.amount = change.getValue();

                    break;
                }
            }

            if (!found && change.getValue() > 0) {
                container.clientNetworkItems.add(new TransferableStack(change));
            }
        }

        container.clientNetworkItems.sort(Comparator.comparing(is->is.type.describe().getItemStack().getDisplayName()));

        // 4. 通知 Container 刷新当前的 54 个虚拟槽位
        // 假设 GuiTerminal 里有一个静态或实例变量记录了当前的 scrollPosition
        container.updateScroll(this.scrollPosition);
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        int wheel = Mouse.getEventDWheel();
        if (wheel != 0) {
            int totalRows = (int) Math.ceil((double) container.clientNetworkItems.size() / 9.0);
            float scrollStep = 1.0F / Math.max(1, totalRows - 6);

            if (wheel > 0) scrollPosition -= scrollStep;
            if (wheel < 0) scrollPosition += scrollStep;

            scrollPosition = MathHelper.clamp_float(scrollPosition, 0.0F, 1.0F);

            // 每次滚动，通知 Container 更新虚拟槽位的内容（NEI会自动读取这些新槽位）
            this.container.updateScroll(scrollPosition);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        // 绘制背景和根据 scrollPosition 绘制滚动条滑块...
    }

    @Override
    public void addButtons() {

    }

    @Override
    public void onKeyTyped(char c, int i) {

    }

    public static class ContainerTerminal extends ContainerCommon {
        // 虚拟物品栏，专供这 54 个槽位显示使用
        private InventoryBasic virtualInv = new InventoryBasic("virtual", false, 54);

        // 客户端缓存的所有网络物品列表（按名称或数量排序好）
        public List<TransferableStack> clientNetworkItems = new ArrayList<>();

        public ContainerTerminal(InventoryPlayer playerInv, ITileEntityInventoryGUI tile) {
            super(playerInv, tile);
            // 注册 54 个虚拟槽位 (9x6)
            for (int i = 0; i < 6; ++i) {
                for (int j = 0; j < 9; ++j) {
                    this.addSlotToContainer(new SlotVirtual(virtualInv, j + i * 9, 8 + j * 18, 18 + i * 18));
                }
            }
            // ... 注册玩家背包槽位 (略) ...
        }

        // 核心：客户端根据滚动条位置，动态刷新 54 个槽位的内容
        public void updateScroll(float scrollPosition) {
            int totalRows = (int) Math.ceil((double) clientNetworkItems.size() / 9.0);
            int maxOffset = totalRows - 6;
            if (maxOffset < 0) maxOffset = 0;

            int rowOffset = (int) (maxOffset * scrollPosition);

            for (int i = 0; i < 54; i++) {
                int itemIndex = (rowOffset * 9) + i;
                if (itemIndex < clientNetworkItems.size()) {
                    virtualInv.setInventorySlotContents(i, clientNetworkItems.get(itemIndex).type.describe().getItemStack());
                } else {
                    virtualInv.setInventorySlotContents(i, null);
                }
            }
        }

        @Override
        public ItemStack slotClick(int slotId, int dragType, int clickTypeIn, EntityPlayer player) {
            // 拦截虚拟槽位的原版点击，改为发送自定义封包
            if (slotId >= 0 && slotId < 54) {
                if (player.worldObj.isRemote) { // 仅限客户端
                    ItemStack clicked = virtualInv.getStackInSlot(slotId);
                    if (clicked != null) {
                        // TODO: 发送 PacketExtractItem 到服务端
                    }
                }
                return null; // 阻止原版逻辑
            }
            return super.slotClick(slotId, dragType, clickTypeIn, player);
        }
    }
    // 自定义影子槽位，禁止原版物品放入和取出
    public static class SlotVirtual extends Slot {
        public SlotVirtual(IInventory inv, int index, int x, int y) { super(inv, index, x, y); }
        @Override public boolean isItemValid(ItemStack stack) { return false; }
        @Override public boolean canTakeStack(EntityPlayer player) { return false; }
    }
}