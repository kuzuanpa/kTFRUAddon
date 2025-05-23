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

import java.util.ArrayList;
import java.util.List;

public class ExpandableLayer implements IStructureLayer {
    private final int maxRepeats;
    public int repeatCount = 0;
    private final List<FixedLayer> variations = new ArrayList<>();
    public ExpandableLayer(int maxRepeats) {
        this.maxRepeats = maxRepeats;
    }

    public ExpandableLayer variation(FixedLayer variation) {
        variations.add(variation);
        return this;
    }
    /**alia**/
    public ExpandableLayer variation(String... rows) {
        FixedLayer variation = new FixedLayer().blockRule(rows);
        variations.add(variation);
        return this;
    }
    IStringBaseStructure structure;
    private StructureContext.Axis layerAxis;

    @Override
    public int validate(StructureContext ctx, StructureContext.Axis mainAxis, int baseX, int baseY, int baseZ, boolean tryAutoBuild, boolean fastAutoBuild) {

        for (repeatCount = 0; repeatCount < maxRepeats;) {
            for (int i = 0; i < variations.size(); i++) {
                FixedLayer current = variations.get(i % variations.size());
                if (current.validate(ctx, mainAxis, baseX, baseY, baseZ, tryAutoBuild, fastAutoBuild) == 0) {
                    promoteContext(ctx,mainAxis,-1);
                    return repeatCount;
                }
                promoteContext(ctx, mainAxis, 1);
            }
            repeatCount++;
        }
        return repeatCount;
    }

    @Override
    public void setStructure(IStringBaseStructure structure) {
        variations.forEach(variation -> variation.setStructure(structure));
        this.structure=structure;
    }

    public void promoteContext(StructureContext ctx, StructureContext.Axis axis, int num){
        ctx.addX += axis == StructureContext.Axis.X ? num : 0;
        ctx.addY += axis == StructureContext.Axis.Y ? num : 0;
        ctx.addZ += axis == StructureContext.Axis.Z ? num : 0;
    }
}

/*
public class ExpandableLayer implements IStructureLayer {


    public int validate(StructureContext ctx, StructureContext.Axis mainAxis, int startCoord) {
        for(int i=0; i<variations.size(); i++) {
            FixedLayer current = variations.get(i % variations.size());
            int offset = calculateOffset(mainAxis, startCoord, i);

            if(!current.validate(ctx, mainAxis,
                    offset,
                    ctx.getBaseY(),
                    ctx.getBaseZ()
            )) return false;
        }
        return true;
    }

    private int calculateOffset(StructureContext.Axis axis, int start, int step) {
        switch (axis) {
            case X :return start + step;
            case Y :return start + step;
            case Z :return start + step;
        };
        return 0;
    }
}*/