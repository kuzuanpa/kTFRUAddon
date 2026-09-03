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

import cn.kuzuanpa.ktfruaddon.api.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.api.network.PacketFxBlockOutline;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.layerType.FixedLayer;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.layerType.IStructureLayer;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.IStructurePredicate;
import cpw.mods.fml.common.network.NetworkRegistry;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import net.minecraft.util.ChunkCoordinates;

import java.util.HashMap;
import java.util.Map;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.kNetworkHandler;

public class LayerStructure implements IStringBaseStructure {
    private final StructureContext.Axis expandAxis;
    private String layerSequence;
    public final Map<Character, IStructureLayer> layers = new HashMap<>();
    public final Map<Character, IStructurePredicate> predicates = new HashMap<>();
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
            if(step == 0) {
                kNetworkHandler.sendToAllAround(new PacketFxBlockOutline(ctx.failedPos, 0xff0000, 4000, 1.0f), new NetworkRegistry.TargetPoint(ctx.world.provider.dimensionId, ctx.failedPos.posX, ctx.failedPos.posY, ctx.failedPos.posZ, 80));
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

    /**
     * The layers span rows and columns of the plane they stack in, the sequence spans the expand axis,
     * see {@link FixedLayer#validate}. Expandable layers report their maximum, so this box is an upper
     * bound: it never rejects a block of a valid structure, but it does accept the holes of the layout
     * and the yet unbuilt part of an expandable range.
     */
    @Override
    public boolean isInsideStructure(ITileEntityMultiBlockController controller, byte facing, int aX, int aY, int aZ) {
        int tThickness = 0, tRows = 0, tCols = 0;
        for(char c : layerSequence.toCharArray()) {
            ChunkCoordinates tLayerSize = layers.get(c).getSize();
            tThickness += tLayerSize.posY;
            tRows = Math.max(tRows, tLayerSize.posX);
            tCols = Math.max(tCols, tLayerSize.posZ);
        }
        int tSizeX = expandAxis == StructureContext.Axis.X ? tThickness : expandAxis == StructureContext.Axis.Z ? tCols : tRows;
        int tSizeY = expandAxis == StructureContext.Axis.Y ? tThickness : tRows;
        int tSizeZ = expandAxis == StructureContext.Axis.Z ? tThickness : tCols;
        //The two opposing corners of the layout, BoundingBox sorts them so a mirrored facing is fine.
        int[] tCorner1 = StructureContext.convertCoord(facing, controller.getX(), controller.getY(), controller.getZ(), controllerOffsetPos.posX, controllerOffsetPos.posY, controllerOffsetPos.posZ);
        int[] tCorner2 = StructureContext.convertCoord(facing, controller.getX(), controller.getY(), controller.getZ(), controllerOffsetPos.posX + tSizeX - 1, controllerOffsetPos.posY + tSizeY - 1, controllerOffsetPos.posZ + tSizeZ - 1);
        return new BoundingBox(tCorner1[0], tCorner1[1], tCorner1[2], tCorner2[0], tCorner2[1], tCorner2[2]).isXYZInBox(aX, aY, aZ);
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