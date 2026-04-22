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
    private int scrollPosition = 0;
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

    public void onDeltaSyncReceived(List<TransferableStack> changes) {
        for (TransferableStack changedStack : changes) {
            boolean found = false;

            Iterator<TransferableStack> iterator = container.clientNetworkItems.iterator();
            while (iterator.hasNext()){
                TransferableStack tStack = iterator.next();
                ITransferable currentItemStack = tStack.type;
                if (currentItemStack.equals(changedStack.type)) {
                    found = true;
                    if (changedStack.amount <= 0) iterator.remove();
                    else tStack.amount = changedStack.amount;

                    break;
                }
            }

            if (!found && changedStack.amount > 0) {
                container.clientNetworkItems.add(changedStack);
            }
        }

        container.clientNetworkItems.sort(Comparator.comparing(is->is.type.describe().getItemStack().getDisplayName()));

        container.updateScroll(scrollPosition);
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        int wheel = Mouse.getEventDWheel();
        if (wheel == 0) return;

        if (wheel > 0) scrollPosition -= 1;
        if (wheel < 0) scrollPosition += 1;

        int totalRows = (int) Math.ceil((double) container.clientNetworkItems.size() / 9.0);
        int maxOffset = Math.max(0, totalRows - container.row);

        scrollPosition = MathHelper.clamp_int(scrollPosition, 0, maxOffset);

        this.container.updateScroll(scrollPosition);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        int ContainerX = (width - xSize) / 2;
        int ContainerY = (height - ySize) / 2;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4d(0.5,0.5,0.5,0.5);
        util.drawTexturedModalRect(ContainerX,30,0,0,0,xSize, (container.row + 5)*18 + 16);


        GL11.glColor4d(0.5,0.5,0.5,0.9);
        float totalRows = (float) Math.ceil( container.clientNetworkItems.size() *1F / 9.0F);

        float percent = container.row / totalRows;
        int totalLength = ((container.row)*18 + 16);
        if(percent < 1.0F) util.drawTexturedModalRect(ContainerX + xSize - 2, (int) (30 + (scrollPosition / totalRows)* totalLength ),0,0,0,2, (int) Math.max(1,(percent * totalLength)));
        GL11.glEnable(GL11.GL_TEXTURE_2D);

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        drawStackAmount();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    protected void drawStackAmount(){
        int index = scrollPosition * 9;
        int containerX = (width - xSize) / 2;
        int containerY = (height - ySize) / 2;
        for (int i = 0; i < container.row; ++i) for (int j = 0; j < 9; ++j) {
            if (index >= container.clientNetworkItems.size()) return;
            String str = String.valueOf(container.clientNetworkItems.get(index).amount);
            fontRendererObj.drawString(str, 24 + j * 18 - fontRendererObj.getStringWidth(str), 46 - containerY + i * 18, 0xffffff);
            index++;
        }
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
        private final InventoryBasic virtualInv ;
        InventoryPlayer aInventoryPlayer;
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

            for (int i = 0; i < row; ++i) for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new SlotVirtual(virtualInv, j + i * 9, 8 + j * 18,  36 - containerY + i * 18));
            }
        }

        public void updateScroll(int scrollPosition) {
            clientNetworkItems.add(new StringTestTransferable("Test").make((long) (new Random().nextFloat()*64)));
            for (int i = 0; i < row*9; i++) {

                int itemIndex = (scrollPosition * 9) + i;
                if (itemIndex < clientNetworkItems.size()) {
                    ItemStack stack = clientNetworkItems.get(itemIndex).type.describe().getItemStack();
                    stack.stackSize = 1;
                    virtualInv.setInventorySlotContents(i, stack);
                } else {
                    virtualInv.setInventorySlotContents(i, null);
                }
            }
        }

        @Override
        public ItemStack slotClick(int slotId, int dragType, int clickTypeIn, EntityPlayer player) {
            if (slotId < 36) return super.slotClick(slotId, dragType, clickTypeIn, player);

            if (player.worldObj.isRemote) {
                ItemStack clicked = virtualInv.getStackInSlot(slotId);
                if (clicked != null) {
                    // TODO: 发送 PacketExtractItem 到服务端
                }
            }
            return null;
        }

        @Override
        public boolean canInteractWith(EntityPlayer p_75145_1_) {
            return true;
        }

        protected void bindPlayerInventory(InventoryPlayer aInventoryPlayer, int aOffset) {
            int i;
            for(i = 0; i < 3; ++i) for(int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(aInventoryPlayer, j + i * 9 + 9, 8 + j * 18, aOffset + i * 18));
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