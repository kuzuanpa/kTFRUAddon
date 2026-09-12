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
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import gregapi.data.LH;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.WD;
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

/**
 * Q2-02.  The chamber is deliberately a real multiblock: the quantumization
 * furnace prepares its drive and lattice parts, while this structure combines
 * their fields around Mirror Gold to create Quantum Gold.  There is no
 * persistent "quantumized Mirror Gold" material between the two operations.
 *
 * Structure (three layers, viewed from the controller front):
 * Bottom:  QEQ / Q D / QQQ
 * Middle:  QGQ / GLG / QGQ
 * Top:     SSS / SRS / SSS
 * Q=31111 coherence wall, D=31000 item/fluid port, E=31000 energy port,
 * G=31115 drive module, L=31116 lattice stabilizer, S=31112 substrate,
 * R=31113 entanglement channel module.  The controller occupies the blank
 * position in the bottom layer; E is the top-right Q position on that layer.
 */
public class QuantumGoldSynthesisChamber extends TileEntityBaseControlledMachine {
    private ChunkCoordinates lastFailedPos;

    private static final IStringBaseStructure STRUCTURE = new LayerStructure(StructureContext.Axis.Y).layerRule("ABC")
            .fixedLayer('A',
                    "QEQ",
                    "Q D",
                    "QQQ")
            .fixedLayer('B',
                    "QGQ",
                    "GLG",
                    "QGQ")
            .fixedLayer('C',
                    "SSS",
                    "SRS",
                    "SSS")
            .where('Q', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31111, MultiTileEntityMultiBlockPart.NOTHING, 1)))
            .where('D', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31000, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID, 1)))
            .where('E', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31000, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN, 1)))
            .where('G', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31115, MultiTileEntityMultiBlockPart.NOTHING, 1)))
            .where('L', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31116, MultiTileEntityMultiBlockPart.NOTHING, 1)))
            .where('S', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31112, MultiTileEntityMultiBlockPart.NOTHING, 1)))
            .where('R', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31113, MultiTileEntityMultiBlockPart.NOTHING, 1)))
            .setOffset(-1, 0, 0);

    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        if (!worldObj.blockExists(xCoord, yCoord, zCoord)) return mStructureOkay;
        lastFailedPos = STRUCTURE.checkStructure(new StructureContext(this,
                (aPlayer != null || aInventory != null) ? StructureContext.StringBaseMode.SET : StructureContext.StringBaseMode.CHECK,
                worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        if (lastFailedPos != null) resetParts();
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
        aList.add(LH.Chat.WHITE + "3x3x3: 6 Coherence Walls, 1 energy port, 1 I/O port, 4 Drive Modules, 1 Lattice Stabilizer,");
        aList.add(LH.Chat.WHITE + "8 Stabilization Substrates, 1 Entanglement Channel Module, 1 I/O port.");
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
        return "ktfru.multitileentity.30073";
    }
}
