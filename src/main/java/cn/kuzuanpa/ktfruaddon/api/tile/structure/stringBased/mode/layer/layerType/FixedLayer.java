/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */

package cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.layerType;

import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.IStructurePredicate;
import net.minecraft.util.ChunkCoordinates;

import java.util.*;

public class FixedLayer implements IStructureLayer {
    private List<String> rows = new ArrayList<>();
    IStringBaseStructure structure;
    private StructureContext.Axis layerAxis;

    public FixedLayer blockRule(String... rows) {
        this.rows.addAll(Arrays.asList(rows));
        return this;
    }

    @Override
    public int validate(StructureContext ctx, StructureContext.Axis mainAxis, int baseX, int baseY, int baseZ) {
        for(int rowIdx=0; rowIdx<rows.size(); rowIdx++) {
            String row = rows.get(rowIdx);
            for(int colIdx=0; colIdx<row.length(); colIdx++) {
                char expected = row.charAt(colIdx);
                if(expected == ' ') continue; // 跳过空格

                int[] absCoords = convertAxis(ctx, mainAxis, rowIdx, colIdx);

                IStructurePredicate condition = structure.getPredicates().get(expected);

                if(condition == null)throw new IllegalArgumentException("condition can not be null!");

                if (!condition.validate(ctx, absCoords[0], absCoords[1], absCoords[2])) return 0;
            }
        }
        promoteContext(ctx, mainAxis, 1);
        return 1;
    }

    @Override
    public void setStructure(IStringBaseStructure structure) {
        this.structure=structure;
    }

    @Override
    public ChunkCoordinates getSize() {
        return new ChunkCoordinates(rows.size(), 1, rows.get(0).length());
    }

    public void promoteContext(StructureContext ctx, StructureContext.Axis axis, int num){
        ctx.addX += axis == StructureContext.Axis.X ? num : 0;
        ctx.addY += axis == StructureContext.Axis.Y ? num : 0;
        ctx.addZ += axis == StructureContext.Axis.Z ? num : 0;
    }
    private int[] convertAxis(StructureContext ctx, StructureContext.Axis axis, int row, int col) {
        int[] current = ctx.getMapCoord();
        switch (axis) {
            case Y : return StructureContext.convertCoord(ctx.facing,current[0],current[1],current[2],+ row, 0, + col);// XZ平面
            case X : return StructureContext.convertCoord(ctx.facing,current[0],current[1],current[2],0, + row, + col);// YZ平面
            case Z : return StructureContext.convertCoord(ctx.facing,current[0],current[1],current[2],+ col, + row, 0);// XY平面
        }
        return new int[0];
    }
}