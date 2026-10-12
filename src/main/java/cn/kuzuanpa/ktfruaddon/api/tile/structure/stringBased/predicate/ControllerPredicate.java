package cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate;

import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;

public class ControllerPredicate implements IStructurePredicate {
    @Override public boolean supportsAsync() {return true;}
    /**异步: 控制器自己那一格 —— 直接和控制器坐标比, 不需要任何世界读取。**/
    @Override public boolean checkAsync(StructureContext ctx, int x, int y, int z) {return x == ctx.oX && y == ctx.oY && z == ctx.oZ;}
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
