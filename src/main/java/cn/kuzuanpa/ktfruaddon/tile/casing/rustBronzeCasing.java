/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.casing;

import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.notick.TileEntityBase03MultiTileEntities;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Arrays;

import static gregapi.data.CS.*;

public class rustBronzeCasing extends TileEntityBase03MultiTileEntities implements IMultiTileEntity.IMTE_SyncDataByteArray {

    public byte[] deRustedSides={0,0,0,0,0,0};
    @Override
    public String getTileEntityName() {
        return "ktfru.multitile.casing.rust.bronze";
    }
    public static IIconContainer
            sTextureCommon= new Textures.BlockIcons.CustomIcon("machines/casing/rustbronze/common"),
            sTextureDeRusted= new Textures.BlockIcons.CustomIcon("machines/casing/rustbronze/derust");
    @Override
    public ITexture getTexture(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return !aShouldSideBeRendered[aSide]?null: deRustedSides[aSide]==1? BlockTextureDefault.get(sTextureDeRusted):BlockTextureDefault.get(sTextureCommon);
    }

    @Override
    public boolean setBlockBounds(Block aBlock, int aRenderPass, boolean[] aShouldSideBeRendered) {
        return box(aBlock, PX_P[ 0], PX_P[ 0], PX_P[ 0], PX_P[16], PX_P[ 16], PX_P[ 16]);
    }

    @Override
    public int getRenderPasses(Block aBlock, boolean[] aShouldSideBeRendered) {
        return 1;
    }

    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        return getClientDataPacketByteArray(aSendAll,deRustedSides);
    }

    @Override
    public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler) {
        deRustedSides=aData;
        return T;
    }
@Override
    public boolean onBlockActivated2(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
    if (aPlayer.getCurrentEquippedItem().getUnlocalizedName().contains("gt.metatool") && aPlayer.getCurrentEquippedItem().getItemDamage() == 18) {
        deRustedSides[aSide] = 1;
        updateClientData();
        aPlayer.getCurrentEquippedItem().damageItem(1, aPlayer);
        if (Arrays.equals(deRustedSides, new byte[]{1, 1, 1, 1, 1, 1})){
            aPlayer.worldObj.setBlockToAir(this.xCoord,this.yCoord,this.zCoord);
            aPlayer.inventory.addItemStackToInventory(MultiTileEntityRegistry.getRegistry("ktfru.multitileentity").getItem(32762));
        }
        return T;
    }
    return F;
}

}
