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


package cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer;

import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.layerType.IStructureLayer;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.IStructurePredicate;

import java.util.HashMap;
import java.util.Map;

public class LayerStructure implements IStringBaseStructure {
    private final StructureContext.Axis expandAxis;
    private String layerSequence;
    private final Map<Character, IStructureLayer> layers = new HashMap<>();
    private final Map<Character, IStructurePredicate> predicates = new HashMap<>();

    public LayerStructure(StructureContext.Axis expandAxis) {
        this.expandAxis = expandAxis;
    }

    public LayerStructure layerRule(String sequence) {
        this.layerSequence = sequence;
        return this;
    }

    public LayerStructure layer(char symbol, IStructureLayer layer) {
        layer.setStructure(this);
        layers.put(symbol, layer);
        return this;
    }
    /**
     * 绑定符号与方块条件
     * @param symbol 结构模式中的字符
     * @param predicate 匹配条件
     * @return 当前对象
     */
    public LayerStructure where(char symbol, IStructurePredicate predicate) {
        predicates.put(symbol, predicate);
        return this;
    }
    @Override
    public boolean checkStructure(StructureContext ctx) {
        for(char c : layerSequence.toCharArray()) {
            IStructureLayer layer = layers.get(c);
            if(layer == null) return false;

            int step = validateLayer(ctx, layer,ctx.getMapCoord());
            if(step == 0)return false;
            promoteContext(ctx, expandAxis, step);
        }
        return true;
    }

    @Override
    public Map<Character, IStructurePredicate> getPredicates() {
        return predicates;
    }

    private int validateLayer(StructureContext ctx, IStructureLayer layer, int[] base) {
        return layer.validate(ctx, expandAxis,
             base[0],
             base[1],
             base[2]
        );
    }
    public void promoteContext(StructureContext ctx, StructureContext.Axis axis, int num){
        int aX = axis == StructureContext.Axis.X ? num : 0;
        int aY = axis == StructureContext.Axis.Y ? num : 0;
        int aZ = axis == StructureContext.Axis.Z ? num : 0;
        ctx.addX += aX;
        ctx.addY += aY;
        ctx.addZ += aZ;
    }
}