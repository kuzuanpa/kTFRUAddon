package cn.kuzuanpa.ktfruaddon.DreamPlanner.client.gui;

import cn.kuzuanpa.kGuiLib.client.kGuiContainerBase;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.test.StringTestTransferable;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.ITransferable;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.TransferableStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.*;

public class GuiTerminal extends kGuiContainerBase {
    private float scrollPosition = 0.0F;
    InventoryPlayer aInventoryPlayer;
    private ContainerTerminal container;

    public GuiTerminal(InventoryPlayer aInventoryPlayer, int aGUIID) {
        super(new ContainerTerminal(aInventoryPlayer));
        this.aInventoryPlayer = aInventoryPlayer;
        this.container= (ContainerTerminal) inventorySlots;
        ySize = 108;
    }

    @Override
    public void initGui2() {
        super.initGui2();
        int ContainerY = (height - ySize) / 2;

        this.inventorySlots = new ContainerTerminal(aInventoryPlayer, Math.max(3, (height / 18) - 9), ContainerY);
        this.container= (ContainerTerminal) inventorySlots;
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
        int ContainerX = (width - xSize) / 2;
        int ContainerY = (height - ySize) / 2;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4d(0.5,0.5,0.5,0.5);
        util.drawTexturedModalRect(ContainerX,30,0,0,0,xSize, (container.row + 5)*18 + 16);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

    }

    @Override
    public void addButtons() {

    }

    @Override
    public void onKeyTyped(char c, int i) {
        close();
    }

    public static class ContainerTerminal extends Container {
        int row;
        int containerY;
        // 虚拟物品栏，专供这 54 个槽位显示使用
        private final InventoryBasic virtualInv ;
        InventoryPlayer aInventoryPlayer;
        // 客户端缓存的所有网络物品列表（按名称或数量排序好）
        public List<TransferableStack> clientNetworkItems = new ArrayList<>();

        public ContainerTerminal(InventoryPlayer aInventoryPlayer) {
            this(aInventoryPlayer, 3, 0);
        }
        public ContainerTerminal(InventoryPlayer aInventoryPlayer, int row, int containerY) {
            this.aInventoryPlayer = aInventoryPlayer;
            this.row=row;
            this.containerY = containerY;
            this.virtualInv = new InventoryBasic("virtual", false, row*9);

            addSlots();
        }

        public void addSlots(){
            bindPlayerInventory(aInventoryPlayer, row*18 + 54 - containerY);

            for (int i = 0; i < row; ++i) {
                for (int j = 0; j < 9; ++j) {
                    this.addSlotToContainer(new SlotVirtual(virtualInv, j + i * 9, 8 + j * 18,  36 - containerY + i * 18));
                }
            }

        }
        // 核心：客户端根据滚动条位置，动态刷新 54 个槽位的内容
        public void updateScroll(float scrollPosition) {
            int totalRows = (int) Math.ceil((double) clientNetworkItems.size() / 9.0);
            int maxOffset = totalRows - row;
            if (maxOffset < 0) maxOffset = 0;

            int rowOffset = (int) (maxOffset * scrollPosition);

            clientNetworkItems.add(new StringTestTransferable("Test").make(12));
            for (int i = 0; i < row*9; i++) {
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
            if (slotId >= 36) {
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

        @Override
        public boolean canInteractWith(EntityPlayer p_75145_1_) {
            return true;
        }

        protected void bindPlayerInventory(InventoryPlayer aInventoryPlayer, int aOffset) {
            int i;
            for(i = 0; i < 3; ++i) {
                for(int j = 0; j < 9; ++j) {
                    this.addSlotToContainer(new Slot(aInventoryPlayer, j + i * 9 + 9, 8 + j * 18, aOffset + i * 18));
                }
            }

            for(i = 0; i < 9; ++i) {
                this.addSlotToContainer(new Slot(aInventoryPlayer, i, 8 + i * 18, aOffset + 58));
            }

        }
    }
    // 自定义影子槽位，禁止原版物品放入和取出
    public static class SlotVirtual extends Slot {
        public SlotVirtual(IInventory inv, int index, int x, int y) { super(inv, index, x, y); }
        @Override public boolean isItemValid(ItemStack stack) { return false; }
        @Override public boolean canTakeStack(EntityPlayer player) { return false; }
    }
}