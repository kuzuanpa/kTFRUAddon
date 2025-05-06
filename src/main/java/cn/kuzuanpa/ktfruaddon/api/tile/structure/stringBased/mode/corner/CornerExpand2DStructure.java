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

package cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.corner;

/*
public class CornerExpand2DStructure implements IStringBaseStructure {
    public enum Corner { NORTH_WEST, NORTH_EAST, SOUTH_WEST, SOUTH_EAST }
    public enum Edge { NORTH, EAST, SOUTH, WEST }

    private final Map<Corner, FixedLayer> corners = new HashMap<>();
    private final Map<Edge, ExpandableLayer> edges = new HashMap<>();
    private ExpandableLayer center;
    private final int maxEdgeExpansion;

    public CornerExpand2DStructure(int maxEdgeExpansion) {
        this.maxEdgeExpansion = maxEdgeExpansion;
    }

    // 配置方法
    public CornerExpand2DStructure setCorner(Corner position, FixedLayer layer) {
        corners.put(position, layer);
        return this;
    }

    public CornerExpand2DStructure setEdge(Edge edge, ExpandableLayer layer) {
        edges.put(edge, layer);
        return this;
    }

    public CornerExpand2DStructure setCenter(ExpandableLayer layer) {
        this.center = layer;
        return this;
    }

    @Override
    public boolean checkStructure(StructureContext ctx) {
        // 1. 验证四个角落
        for (Corner corner : Corner.values()) {
            if (!validateCorner(ctx, corner)) return false;
        }

        // 2. 验证四条边
        for (Edge edge : Edge.values()) {
            if (!validateEdge(ctx, edge)) return false;
        }

        // 3. 验证中心区域
        return validateCenter(ctx);
    }

    // 角落验证
    private boolean validateCorner(StructureContext ctx, Corner corner) {
        int[] offset = getCornerOffset(corner);
        FixedLayer layer = corners.get(corner);

        return layer.validate(ctx, StructureContext.Axis.Y,
                ctx.getBaseX() + offset[0],
                ctx.getBaseY(),
                ctx.getBaseZ() + offset[1]
        );
    }

    private int[] getCornerOffset(Corner corner) {
        return switch (corner) {
            case NORTH_WEST -> new int[]{-1, -1};
            case NORTH_EAST -> new int[]{1, -1};
            case SOUTH_WEST -> new int[]{-1, 1};
            case SOUTH_EAST -> new int[]{1, 1};
        };
    }

    // 边验证
    private boolean validateEdge(StructureContext ctx, Edge edge) {
        ExpandableLayer layer = edges.get(edge);
        if (layer == null) return false;

        int startX = ctx.getBaseX();
        int startZ = ctx.getBaseZ();
        StructureContext.Axis edgeAxis = getEdgeAxis(edge);

        return switch (edge) {
            case NORTH -> layer.validate(ctx, StructureContext.Axis.Z, startZ - 1, maxEdgeExpansion);
            case SOUTH -> layer.validate(ctx, StructureContext.Axis.Z, startZ + 1, maxEdgeExpansion);
            case EAST -> layer.validate(ctx, StructureContext.Axis.X, startX + 1, maxEdgeExpansion);
            case WEST -> layer.validate(ctx, StructureContext.Axis.X, startX - 1, maxEdgeExpansion);
        };
    }

    private StructureContext.Axis getEdgeAxis(Edge edge) {
        return (edge == Edge.NORTH || edge == Edge.SOUTH) ? StructureContext.Axis.Z : StructureContext.Axis.X;
    }

    // 中心验证
    private boolean validateCenter(StructureContext ctx) {
        if (center == null) return true; // 允许无中心

        return center.validate(ctx, StructureContext.Axis.X, ctx.getBaseX(), maxEdgeExpansion) &&
                center.validate(ctx, StructureContext.Axis.Z, ctx.getBaseZ(), maxEdgeExpansion);
    }
}
*/