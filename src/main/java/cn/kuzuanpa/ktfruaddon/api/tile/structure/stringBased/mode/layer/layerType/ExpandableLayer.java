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
import cpw.mods.fml.common.FMLLog;
import net.minecraft.util.ChunkCoordinates;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;

public class ExpandableLayer implements IStructureLayer {
    private final int maxRepeats;
    public int repeatCount = 0, extraData=0;
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

    @Override
    public int validate(StructureContext ctx, StructureContext.Axis mainAxis, int baseX, int baseY, int baseZ) {
        for (repeatCount = 0; repeatCount < (extraData!=0?extraData:maxRepeats);) {
            for (int i = 0; i < variations.size(); i++) {
                FixedLayer current = variations.get(i % variations.size());
                if (current.validate(ctx, mainAxis, baseX, baseY, baseZ) == 0) {
                    return repeatCount;
                }
            }
            repeatCount++;
        }
        return repeatCount;
    }

    @Override
    public void setExtraData(String data) {
        try {
            extraData = Integer.parseInt(data);
            if(extraData > maxRepeats)extraData = 0;
        } catch (NumberFormatException e) {
            FMLLog.log(Level.ERROR, e, "Error when setup expandable layer: extra data is not a Int? "+data);
        }
    }

    @Override
    public String getExtraDataDesc() {
        return  "ktfru.api.structure.layer.expandable.extra_data_desc";
    }

    @Override
    public void setStructure(IStringBaseStructure structure) {
        variations.forEach(variation -> variation.setStructure(structure));
        this.structure=structure;
    }

    @Override
    public ChunkCoordinates getSize() {
        ChunkCoordinates size = variations.get(0).getSize();
        size.posY = variations.size()*maxRepeats;
        return size;
    }
}