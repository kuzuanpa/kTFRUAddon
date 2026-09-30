package cn.kuzuanpa.ktfruaddon.tile.multiblock.research;

import gregapi.tileentity.delegate.DelegatorTileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;

/** Research-locked reactor using the shared research structure and reactor recipe table. */
public class ResearchReactor extends ResearchAssembler {
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.research.reactor";
    }

    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidOutputTarget(byte aSide, Fluid aOutput) {
        return getAdjacentTank(aSide);
    }
}
