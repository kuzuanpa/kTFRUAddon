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

import cn.kuzuanpa.ktfruaddon.api.network.PacketFxBlockOutline;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.layerType.FixedLayer;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.layerType.IStructureLayer;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.IStructurePredicate;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.util.ChunkCoordinates;

import java.util.HashMap;
import java.util.Map;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.kNetworkHandler;

public class LayerStructure implements IStringBaseStructure {
    private final StructureContext.Axis expandAxis;
    private String layerSequence;
    private final Map<Character, IStructureLayer> layers = new HashMap<>();
    private final Map<Character, IStructurePredicate> predicates = new HashMap<>();
    public ChunkCoordinates controllerOffsetPos = null;

    public LayerStructure(StructureContext.Axis expandAxis) {
        this.expandAxis = expandAxis;
    }

    public LayerStructure layerRule(String sequence) {
        this.layerSequence = sequence;
        return this;
    }

    public LayerStructure setOffset(int x, int y, int z) {
        this.controllerOffsetPos = new ChunkCoordinates(x,y,z);
        return this;
    }

    public LayerStructure layer(char symbol, IStructureLayer layer) {
        layer.setStructure(this);
        layers.put(symbol, layer);
        return this;
    }

    /**alia**/
    public LayerStructure fixedLayer(char symbol, String... rows) {
        FixedLayer layer = new FixedLayer().blockRule(rows);
        layer.setStructure(this);
        layers.put(symbol, layer);
        return this;
    }

    public LayerStructure where(char symbol, IStructurePredicate predicate) {
        predicates.put(symbol, predicate);
        return this;
    }
    @Override
    public ChunkCoordinates checkStructure(StructureContext ctx) {
        ctx.addX += controllerOffsetPos.posX;
        ctx.addY += controllerOffsetPos.posY;
        ctx.addZ += controllerOffsetPos.posZ;
        predicates.forEach((character, predicate) -> predicate.preCheck(ctx, character));
        for(char c : layerSequence.toCharArray()) {
            IStructureLayer layer = layers.get(c);
            if(layer == null) throw new IllegalArgumentException("Null Layer!");

            int step = layer.validate(ctx, expandAxis, ctx.getMapCoord()[0], ctx.getMapCoord()[1], ctx.getMapCoord()[2]);
            if(step == 0){
                kNetworkHandler.sendToAllAround(new PacketFxBlockOutline(ctx.failedPos, 0xff0000, 4000,1.0f), new NetworkRegistry.TargetPoint(ctx.world.provider.dimensionId, ctx.failedPos.posX, ctx.failedPos.posY, ctx.failedPos.posZ, 80));
                return ctx.failedPos;
            }
        }
        predicates.forEach((character, predicate) -> predicate.afterCheck(ctx, character));
        return null;
    }

    @Override
    public Map<Character, IStructurePredicate> getPredicates() {
        return predicates;
    }

    @Override
    public ChunkCoordinates getSize() {
        int y = 0;
        for(char c : layerSequence.toCharArray()) y += layers.get(c).getSize().posY;
        char firstLayer = layerSequence.charAt(0);
        return new ChunkCoordinates(layers.get(firstLayer).getSize().posX, y, layers.get(firstLayer).getSize().posZ);
    }

    @Override
    public Map<Character, String> getExtraDataDesc() {
        Map<Character, String> map = new HashMap<>();
        layers.forEach((id,layer)-> {
            if(layer.getExtraDataDesc() != null)map.put(id,layer.getExtraDataDesc());
        });
        return map;
    }

    @Override
    public void setExtraData(char id, String data) {
        layers.get(id).setExtraData(data);
    }
}