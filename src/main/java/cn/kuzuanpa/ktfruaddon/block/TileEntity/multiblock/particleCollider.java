package cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock;

import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.LH;
import gregapi.data.LH.Chat;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;
import gregapi.util.ST;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.List;

import static gregapi.data.CS.*;
/**
 * @author Gregorius Techneticies
 * @author YueSha
 * Copied from GT6U
 */
    public class particleCollider extends TileEntityBase10MultiBlockMachine {
    MultiTileEntityRegistry gtRegistry = MultiTileEntityRegistry.getRegistry("gt.multitileentity");
    @Override
        public boolean checkStructure2() {
            int tX = getOffsetXN(mFacing, 2), tY = yCoord, tZ = getOffsetZN(mFacing, 2);
            if (worldObj.blockExists(tX - 9, tY, tZ - 9) && worldObj.blockExists(tX + 9, tY, tZ - 9) && worldObj.blockExists(tX - 9, tY, tZ + 9) && worldObj.blockExists(tX + 9, tY, tZ + 9)) {
                boolean tSuccess = T;

                int tVersatile = 3, tLogic = 12, tControl = 12;

                for (int i = -2; i <= 2; i++)
                    for (int j = -2; j <= 2; j++)
                        for (int k = -2; k <= 2; k++) {
                            if (i * i + j * j + k * k < 4) {
                                if (ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + i, tY + j, tZ + k, 18200, ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.NOTHING)) {
                                    tVersatile--;
                                } else if (ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + i, tY + j, tZ + k, 18201,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.NOTHING)) {
                                    tLogic--;
                                } else if (ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + i, tY + j, tZ + k, 18202,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.NOTHING)) {
                                    tControl--;
                                } else {
                                    tSuccess = F;
                                }
                            } else if (i * i + j * j + k * k > 6 || (j == 0 && (((i == -2 || i == 2) && k == 0) || (((k == -2 || k == 2) && i == 0))))) {
                                if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + i, tY + j, tZ + k, 18008,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.NOTHING))
                                    tSuccess = F;
                            } else {
                                if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + i, tY + j, tZ + k, 18299,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.NOTHING))
                                    tSuccess = F;
                            }
                        }

                if (tVersatile > 0 || tLogic > 0 || tControl > 0) tSuccess = F;

                if (mFacing != SIDE_X_NEG) {
                    if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX - 3, tY, tZ, 18008,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.NOTHING))
                        tSuccess = F;
                    if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX - 4, tY, tZ, 18008,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.NOTHING))
                        tSuccess = F;
                }
                if (mFacing != SIDE_X_POS) {
                    if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + 3, tY, tZ, 18008,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.NOTHING))
                        tSuccess = F;
                    if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + 4, tY, tZ, 18008,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.NOTHING))
                        tSuccess = F;
                }
                if (mFacing != SIDE_Z_NEG) {
                    if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX, tY, tZ - 3, 18008,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.NOTHING))
                        tSuccess = F;
                    if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX, tY, tZ - 4, 18008,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.NOTHING))
                        tSuccess = F;
                }
                if (mFacing != SIDE_Z_POS) {
                    if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX, tY, tZ + 3, 18008,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.NOTHING))
                        tSuccess = F;
                    if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX, tY, tZ + 4, 18008,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.NOTHING))
                        tSuccess = F;
                }

                tX -= 9;
                tZ -= 9;

                for (int i = 0; i < 19; i++)
                    for (int j = 0; j < 19; j++) {
                        if (OCTAGONS[0][i][j]) {
                            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + i, tY - 1, tZ + j, 18014,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                                tSuccess = F;
                            if ((i == 9 && (j == 0 || j == 18)) || (j == 9 && (i == 0 || i == 18))) {
                                if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + i, tY, tZ + j, 18014,ST.id(gtRegistry.mBlock), 2, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN))
                                    tSuccess = F;
                            } else {
                                if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + i, tY, tZ + j, 18014,ST.id(gtRegistry.mBlock), mActive ? 6 : 5, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN))
                                    tSuccess = F;
                            }
                            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + i, tY + 1, tZ + j, 18014,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                                tSuccess = F;
                        }
                        if (OCTAGONS[1][i][j]) {
                            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + i, tY - 2, tZ + j, 18014,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                                tSuccess = F;

                            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + i, tY - 1, tZ + j, 18014,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.NOTHING))
                                tSuccess = F;

                            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + i, tY, tZ + j, 18046,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.NOTHING))
                                tSuccess = F;

                            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + i, tY + 1, tZ + j, 18014,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.NOTHING))
                                tSuccess = F;

                            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + i, tY + 2, tZ + j, 18014,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                                tSuccess = F;
                        }
                        if (OCTAGONS[2][i][j]) {
                            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + i, tY - 2, tZ + j, 18014,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                                tSuccess = F;

                            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + i, tY - 1, tZ + j, 18046,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.NOTHING))
                                tSuccess = F;

                            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + i, tY, tZ + j, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.NOTHING))
                                tSuccess = F;

                            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + i, tY + 1, tZ + j, 18046,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.NOTHING))
                                tSuccess = F;

                            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + i, tY + 2, tZ + j, 18014,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                                tSuccess = F;
                        }
                    }
                return tSuccess;
            }
            return mStructureOkay;
        }

        public static boolean[][][] OCTAGONS = {{
                {F, F, F, F, F, F, F, T, T, T, T, T, F, F, F, F, F, F, F},
                {F, F, F, F, F, F, T, F, F, F, F, F, T, F, F, F, F, F, F},
                {F, F, F, F, F, T, F, F, F, F, F, F, F, T, F, F, F, F, F},
                {F, F, F, F, T, F, F, F, F, F, F, F, F, F, T, F, F, F, F},
                {F, F, F, T, F, F, F, T, T, T, T, T, F, F, F, T, F, F, F},
                {F, F, T, F, F, F, T, F, F, F, F, F, T, F, F, F, T, F, F},
                {F, T, F, F, F, T, F, F, F, F, F, F, F, T, F, F, F, T, F},
                {T, F, F, F, T, F, F, F, F, F, F, F, F, F, T, F, F, F, T},
                {T, F, F, F, T, F, F, F, F, F, F, F, F, F, T, F, F, F, T},
                {T, F, F, F, T, F, F, F, F, F, F, F, F, F, T, F, F, F, T},
                {T, F, F, F, T, F, F, F, F, F, F, F, F, F, T, F, F, F, T},
                {T, F, F, F, T, F, F, F, F, F, F, F, F, F, T, F, F, F, T},
                {F, T, F, F, F, T, F, F, F, F, F, F, F, T, F, F, F, T, F},
                {F, F, T, F, F, F, T, F, F, F, F, F, T, F, F, F, T, F, F},
                {F, F, F, T, F, F, F, T, T, T, T, T, F, F, F, T, F, F, F},
                {F, F, F, F, T, F, F, F, F, F, F, F, F, F, T, F, F, F, F},
                {F, F, F, F, F, T, F, F, F, F, F, F, F, T, F, F, F, F, F},
                {F, F, F, F, F, F, T, F, F, F, F, F, T, F, F, F, F, F, F},
                {F, F, F, F, F, F, F, T, T, T, T, T, F, F, F, F, F, F, F},
        }, {
                {F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F},
                {F, F, F, F, F, F, F, T, T, T, T, T, F, F, F, F, F, F, F},
                {F, F, F, F, F, F, T, F, F, F, F, F, T, F, F, F, F, F, F},
                {F, F, F, F, F, T, F, T, T, T, T, T, F, T, F, F, F, F, F},
                {F, F, F, F, T, F, T, F, F, F, F, F, T, F, T, F, F, F, F},
                {F, F, F, T, F, T, F, F, F, F, F, F, F, T, F, T, F, F, F},
                {F, F, T, F, T, F, F, F, F, F, F, F, F, F, T, F, T, F, F},
                {F, T, F, T, F, F, F, F, F, F, F, F, F, F, F, T, F, T, F},
                {F, T, F, T, F, F, F, F, F, F, F, F, F, F, F, T, F, T, F},
                {F, T, F, T, F, F, F, F, F, F, F, F, F, F, F, T, F, T, F},
                {F, T, F, T, F, F, F, F, F, F, F, F, F, F, F, T, F, T, F},
                {F, T, F, T, F, F, F, F, F, F, F, F, F, F, F, T, F, T, F},
                {F, F, T, F, T, F, F, F, F, F, F, F, F, F, T, F, T, F, F},
                {F, F, F, T, F, T, F, F, F, F, F, F, F, T, F, T, F, F, F},
                {F, F, F, F, T, F, T, F, F, F, F, F, T, F, T, F, F, F, F},
                {F, F, F, F, F, T, F, T, T, T, T, T, F, T, F, F, F, F, F},
                {F, F, F, F, F, F, T, F, F, F, F, F, T, F, F, F, F, F, F},
                {F, F, F, F, F, F, F, T, T, T, T, T, F, F, F, F, F, F, F},
                {F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F},
        }, {
                {F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F},
                {F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F},
                {F, F, F, F, F, F, F, T, T, T, T, T, F, F, F, F, F, F, F},
                {F, F, F, F, F, F, T, F, F, F, F, F, T, F, F, F, F, F, F},
                {F, F, F, F, F, T, F, F, F, F, F, F, F, T, F, F, F, F, F},
                {F, F, F, F, T, F, F, F, F, F, F, F, F, F, T, F, F, F, F},
                {F, F, F, T, F, F, F, F, F, F, F, F, F, F, F, T, F, F, F},
                {F, F, T, F, F, F, F, F, F, F, F, F, F, F, F, F, T, F, F},
                {F, F, T, F, F, F, F, F, F, F, F, F, F, F, F, F, T, F, F},
                {F, F, T, F, F, F, F, F, F, F, F, F, F, F, F, F, T, F, F},
                {F, F, T, F, F, F, F, F, F, F, F, F, F, F, F, F, T, F, F},
                {F, F, T, F, F, F, F, F, F, F, F, F, F, F, F, F, T, F, F},
                {F, F, F, T, F, F, F, F, F, F, F, F, F, F, F, T, F, F, F},
                {F, F, F, F, T, F, F, F, F, F, F, F, F, F, T, F, F, F, F},
                {F, F, F, F, F, T, F, F, F, F, F, F, F, T, F, F, F, F, F},
                {F, F, F, F, F, F, T, F, F, F, F, F, T, F, F, F, F, F, F},
                {F, F, F, F, F, F, F, T, T, T, T, T, F, F, F, F, F, F, F},
                {F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F},
                {F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F},
        }};

        static {
            LH.add("gt.tooltip.multiblock.particlecollider.1", "For Construction Instructions read the Manual or the GUI.");
            LH.add("gt.tooltip.multiblock.particlecollider.2", "144 Superconducting Coils, 576 Regular Osmiridium Walls, 50 Ventilation Units.");
            LH.add("gt.tooltip.multiblock.particlecollider.3", "36 Regular Stainless Steel Walls, 53 Galvanized Steel Walls.");
            LH.add("gt.tooltip.multiblock.particlecollider.4", "3 Versatile, 12 Logic and 12 Control Quadcore Processing Units.");
            LH.add("gt.tooltip.multiblock.particlecollider.5", "Input energy for start.Then for process");
            LH.add("gt.tooltip.multiblock.particlecollider.6", "Electric power Input at the 'Glass' Ring");
            LH.add("gt.tooltip.multiblock.particlecollider.7", "Items and Fluids are handeled at the normal Walls");
        }

        @Override
        public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
            aList.add(Chat.CYAN + LH.get(LH.STRUCTURE) + ":");
            aList.add(Chat.WHITE + LH.get("gt.tooltip.multiblock.particlecollider.1"));
            aList.add(Chat.WHITE + LH.get("gt.tooltip.multiblock.particlecollider.2"));
            aList.add(Chat.WHITE + LH.get("gt.tooltip.multiblock.particlecollider.3"));
            aList.add(Chat.WHITE + LH.get("gt.tooltip.multiblock.particlecollider.4"));
            aList.add(Chat.WHITE + LH.get("gt.tooltip.multiblock.particlecollider.5"));
            aList.add(Chat.WHITE + LH.get("gt.tooltip.multiblock.particlecollider.6"));
            aList.add(Chat.WHITE + LH.get("gt.tooltip.multiblock.particlecollider.7"));
            super.addToolTips(aList, aStack, aF3_H);
        }

        @Override
        public boolean isInsideStructure(int aX, int aY, int aZ) {
            int tX = getOffsetXN(mFacing, 2), tY = yCoord - 2, tZ = getOffsetZN(mFacing, 2);
            return aX >= tX - 9 && aY >= tY && aZ >= tZ - 9 && aX <= tX + 9 && aY <= tY + 5 && aZ <= tZ + 9;
        }

        @Override
        public DelegatorTileEntity<IFluidHandler> getFluidOutputTarget(byte aSide, Fluid aOutput) {
            return null;
        }

        @Override
        public DelegatorTileEntity<TileEntity> getItemOutputTarget(byte aSide) {
            return null;
        }

        @Override
        public DelegatorTileEntity<IInventory> getItemInputTarget(byte aSide) {
            return null;
        }

        @Override
        public DelegatorTileEntity<IFluidHandler> getFluidInputTarget(byte aSide) {
            return null;
        }

        @Override
        public boolean refreshStructureOnActiveStateChange() {
            return T;
        }

        @Override
        public String getTileEntityName() {
            return "gt.multitileentity.multiblock.particlecollider";
        }
    }
