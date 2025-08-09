/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.

 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */


package cn.kuzuanpa.ktfruaddon.tile.multiblock.parts;

import gregapi.GT_API;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import static gregapi.data.CS.*;


public class CommonPartGlow extends CommonPart {
    protected IIconContainer[][] mTexturesGlow = L1L6_IICONCONTAINER;
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_OPAQUE)) isOpaque = aNBT.getBoolean(NBT_OPAQUE);
        if (CODE_CLIENT) {
            if (GT_API.sBlockIcons == null && aNBT.hasKey(NBT_TEXTURE)) {
                String tTextureName = aNBT.getString(NBT_TEXTURE);
                mTexturesGlow = new IIconContainer[UT.Code.bind8(aNBT.getShort(NBT_DESIGNS))+1][6];
                for (short i = 0; i < mTexturesGlow.length; i++) {
                    mTexturesGlow[i] = new IIconContainer[] {
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+i+"/overlay_glow/bottom"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+i+"/overlay_glow/top"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+i+"/overlay_glow/side")
                };}
            } else {
                TileEntity tCanonicalTileEntity = MultiTileEntityRegistry.getCanonicalTileEntity(getMultiTileEntityRegistryID(), getMultiTileEntityID());
                if (tCanonicalTileEntity instanceof CommonPartGlow) {
                    mTexturesGlow = ((CommonPartGlow)tCanonicalTileEntity).mTexturesGlow;
                }
            }
        }
    }

    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return aShouldSideBeRendered[aSide] ? BlockTextureMulti.get(BlockTextureDefault.get(mTextures[mDesign][FACES_TBS[aSide]], mRGBa), BlockTextureDefault.get(mTextures[mDesign][FACES_TBS[aSide]+3]), BlockTextureDefault.get(mTexturesGlow[mDesign][FACES_TBS[aSide]], true)) : null;
    }
    @Override
    public String getTileEntityName(){
        return "kfru.multitileentity.multiblock.commonpart.glow";
    }
}
