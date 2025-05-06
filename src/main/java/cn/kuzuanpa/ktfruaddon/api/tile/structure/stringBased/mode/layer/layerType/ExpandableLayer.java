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

/*
public class ExpandableLayer implements IStructureLayer {
    private final int maxRepeats;
    private final List<FixedLayer> variations = new ArrayList<>();
    public ExpandableLayer(int maxRepeats) {
        this.maxRepeats = maxRepeats;
    }

    public ExpandableLayer addVariation(FixedLayer variation) {
        variations.add(variation);
        return this;
    }

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