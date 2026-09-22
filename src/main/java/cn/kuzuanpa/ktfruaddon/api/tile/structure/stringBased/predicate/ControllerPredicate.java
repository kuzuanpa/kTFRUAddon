package cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate;

import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;

public class ControllerPredicate implements IStructurePredicate {
    @Override
    public boolean check(StructureContext ctx, int x, int y, int z) {
        return ctx.controller.getTileEntity(x, y, z) == ctx.controller;
    }

    @Override
    public boolean project(StructureContext ctx, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean set(StructureContext ctx, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean reset(StructureContext ctx, int x, int y, int z) {
        return true;
    }
}
