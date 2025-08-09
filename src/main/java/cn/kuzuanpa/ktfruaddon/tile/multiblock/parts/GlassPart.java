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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.parts;

import gregapi.data.CS;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.ITexture;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class GlassPart extends CommonPart {
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        TileEntity sideTile = getAdjacentTileEntity(aSide).mTileEntity;
        return aShouldSideBeRendered[aSide] && (!(sideTile instanceof GlassPart) || ((GlassPart) sideTile).getMultiTileEntityID() != getMultiTileEntityID()|| ((GlassPart) sideTile).getMultiTileEntityRegistryID() != getMultiTileEntityRegistryID())? BlockTextureMulti.get(BlockTextureDefault.get(this.mTextures[this.mDesign][CS.FACES_TBS[aSide]], this.mRGBa), BlockTextureDefault.get(this.mTextures[this.mDesign][CS.FACES_TBS[aSide] + 3])) : null;
    }
    @Override
    public String getTileEntityName(){
        return "kfru.multitileentity.multiblock.part.glass";
    }
}
