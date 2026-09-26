/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 */
package cn.kuzuanpa.ktfruaddon.tile.multiblock.machine;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.base.TileEntityBaseControlledMachine;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.ControllerPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import gregapi.data.LH;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;
import zmaster587.libVulpes.items.ItemProjector;

import java.util.List;

import static gregapi.data.CS.*;

/** Q3-01. Fixed 3x3x5 observation tower; the controller occupies K in the middle layer. */
public class QuantumObservationTower extends TileEntityBaseControlledMachine {
    private ChunkCoordinates lastFailedPos;

    private static final IStringBaseStructure STRUCTURE = new LayerStructure(StructureContext.Axis.Y).layerRule("ABCDE")
            .fixedLayer('A',
                    "WWW",
                    "WEW",
                    "WWW")
            .fixedLayer('B',
                    "WIW",
                    "BSB",
                    "WIW")
            .fixedLayer('C',
                    "RCR",
                    "DKS",
                    "RCR")
            .fixedLayer('D',
                    "WIW",
                    "BSB",
                    "WIW")
            .fixedLayer('E',
                    "WWW",
                    "WEW",
                    "WWW")
            .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31111, MultiTileEntityMultiBlockPart.NOTHING, 1)))
            .where('B', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31112, MultiTileEntityMultiBlockPart.NOTHING, 1)))
            .where('C', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31113, MultiTileEntityMultiBlockPart.NOTHING, 1)))
            .where('R', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31114, MultiTileEntityMultiBlockPart.NOTHING, 1)))
            .where('D', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31115, MultiTileEntityMultiBlockPart.NOTHING, 1)))
            .where('S', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31116, MultiTileEntityMultiBlockPart.NOTHING, 1)))
            .where('E', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31000, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN, 1)))
            .where('I', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31000, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID, 1)))
            .where('K', new ControllerPredicate())
            .setOffset(-1, -2, -1);

    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        if (!worldObj.blockExists(xCoord, yCoord, zCoord)) return mStructureOkay;
        boolean building = aPlayer != null || aInventory != null;
        lastFailedPos = STRUCTURE.checkStructure(new StructureContext(this,
                building ? StructureContext.StringBaseMode.SET : StructureContext.StringBaseMode.CHECK,
                worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        if (lastFailedPos != null && building) resetParts();
        return lastFailedPos == null;
    }

    private void resetParts() {
        if (!worldObj.blockExists(xCoord, yCoord, zCoord)) return;
        STRUCTURE.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.RESET,
                worldObj, xCoord, yCoord, zCoord, mFacing, null, null));
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide()) return true;
        if (!mStructureOkay) aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED + LH.get(I18nHandler.STRUCTURE_ERR)));
        ItemStack equipped = aPlayer.getCurrentEquippedItem();
        if (equipped != null && equipped.getItem() instanceof ItemProjector) {
            STRUCTURE.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT,
                    worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
            return true;
        }
        return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.quantumobservation.1"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.quantumobservation.2"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.quantumobservation.3"));
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return STRUCTURE.isInsideStructure(this, mFacing, aX, aY, aZ);
    }

    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidOutputTarget(byte aSide, Fluid aOutput) {
        return null;
    }

    @Override
    public DelegatorTileEntity<TileEntity> getItemOutputTarget(byte aSide) {
        return delegator(SIDE_BOTTOM);
    }

    @Override
    public DelegatorTileEntity<IInventory> getItemInputTarget(byte aSide) {
        return new DelegatorTileEntity<IInventory>(this, SIDE_FRONT);
    }

    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidInputTarget(byte aSide) {
        return null;
    }

    @Override
    public boolean breakBlock() {
        setStateOnOff(T);
        GarbageGT.trash(mTanksInput);
        GarbageGT.trash(mTanksOutput);
        GarbageGT.trash(mOutputItems);
        GarbageGT.trash(mOutputFluids);
        resetParts();
        return super.breakBlock();
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.30080";
    }
}
