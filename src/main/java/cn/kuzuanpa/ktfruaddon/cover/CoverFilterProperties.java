/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.cover;

import cn.kuzuanpa.ktfruaddon.code.codeUtil;
import gregapi.code.TagData;
import gregapi.cover.CoverData;
import gregapi.cover.covers.AbstractCoverAttachment;
import gregapi.data.LH;
import gregapi.oredict.OreDictItemData;
import gregapi.oredict.OreDictManager;
import gregapi.oredict.OreDictMaterial;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.ITexture;
import gregapi.tileentity.connectors.ITileEntityItemPipe;
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static gregapi.data.CS.*;
 public class CoverFilterProperties extends AbstractCoverAttachment {
        public CoverFilterProperties() {}

        @Override
        public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
            ItemStack tStack = ST.load(aStack.getTagCompound(), "ktfru.filter.property");
            if (ST.valid(tStack)) try {aList.add(LH.Chat.CYAN + tStack.getDisplayName());} catch(Throwable e) {aList.add(LH.Chat.BLINKING_RED + "ERROR, CANNOT DISPLAY ITEM NAME");}
            aList.add(LH.Chat.ORANGE + "Not NBT sensitive!");
            super.addToolTips(aList, aStack, aF3_H);
            aList.add(LH.Chat.DGRAY + LH.get(LH.TOOL_TO_TOGGLE_CONTROLLER_COVER));
            aList.add(LH.Chat.DGRAY + LH.get(LH.TOOL_TO_TOGGLE_SCREWDRIVER));
            aList.add(LH.Chat.DGRAY + LH.get(LH.TOOL_TO_RESET_SOFT_HAMMER));
        }

        @Override
        public long onToolClick(byte aCoverSide, CoverData aData, String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSideClicked, float aHitX, float aHitY, float aHitZ) {
            if (aTool.equals(TOOL_screwdriver)) {
                aData.visual(aCoverSide, (short)(aData.mVisuals[aCoverSide] == 0 ? 1 : 0));
                if (aChatReturn != null) aChatReturn.add(aData.mVisuals[aCoverSide] == 0 ? "Whitelist Filter" : "Blacklist Filter");
                return 1000;
            }
            if (aTool.equals(TOOL_softhammer)) {
                if (aData.mNBTs[aCoverSide] != null) aData.mNBTs[aCoverSide].removeTag("ktfru.filter.property");
                return 10000;
            }
            if (aTool.equals(TOOL_magnifyingglass)) {
                if (aChatReturn != null) {
                    aChatReturn.add(aData.mVisuals[aCoverSide] == 0 ? "Whitelist Filter" : "Blacklist Filter");
                    if (aData.mNBTs[aCoverSide] == null) {
                        aChatReturn.add("Filter is empty!");
                        aData.mNBTs[aCoverSide] = null;
                    } else {
                        ItemStack tStack = ST.load(aData.mNBTs[aCoverSide], "ktfru.filter.property");
                        if (ST.invalid(tStack)) {
                            aChatReturn.add("Filter is empty!");
                            aData.mNBTs[aCoverSide] = null;
                        } else {
                            aChatReturn.add("Filters for: " + LH.Chat.CYAN + ST.regName(tStack) + LH.Chat.GRAY + " ; " + (ST.meta_(tStack) == W ? LH.Chat.GREEN + "Wildcard" : LH.Chat.CYAN + ST.meta_(tStack)));
                        }
                    }
                }
                return 1;
            }
            return 0;
        }

        private List<String> lastClickStackTags = new ArrayList<>();
        private int clickPointer = 0;
        @Override
        public boolean onCoverClickedRight(byte aCoverSide, CoverData aData, Entity aPlayer, byte aSideClicked, float aHitX, float aHitY, float aHitZ) {
            if (aPlayer instanceof EntityPlayer && aData.mTileEntity.isServerSide()) {
                ItemStack tStack = ST.make(((EntityPlayer)aPlayer).getCurrentEquippedItem(), null, null);
                if (ST.valid(tStack)) {
                    String tmp = "";
                    if(lastClickStackTags.equals(getProperties(tStack))){
                        if(clickPointer<lastClickStackTags.size()-1)clickPointer++;
                        else clickPointer=0;
                    }
                    else {
                        clickPointer=0;
                        lastClickStackTags = getProperties(tStack);
                    }
                    if(clickPointer==0) {
                        UT.Entities.sendchat(aPlayer, LH.get("ktfru.msg.filter.properties.all"));
                        lastClickStackTags.forEach(str-> UT.Entities.sendchat(aPlayer,str));
                    }

                    tmp=lastClickStackTags.get(clickPointer);
                    if(aData.mNBTs[aCoverSide]==null)aData.mNBTs[aCoverSide] = UT.NBT.make();
                    aData.mNBTs[aCoverSide].setString("ktfru.filter.property",tmp);
                    UT.Sounds.send(aData.mTileEntity.getWorld(), SFX.MC_CLICK, 1, 1, aData.mTileEntity.getCoords());
                    UT.Entities.sendchat(aPlayer, LH.get("ktfru.msg.filter.properties")+" " + tmp);

                }
            }
            return T;
        }

        @Override
        public boolean interceptItemInsert(byte aCoverSide, CoverData aData, int aSlot, ItemStack aStack, byte aSide) {
            if (aCoverSide != aSide) return F;
            if (aData.mStopped) return T;
            if (aData.mNBTs[aCoverSide] == null || !aData.mNBTs[aCoverSide].hasKey("ktfru.filter.property")) return aData.mVisuals[aCoverSide] == 0;
            return (aData.mVisuals[aCoverSide] == 0) != getProperties(aStack).contains(aData.mNBTs[aCoverSide].getString("ktfru.filter.property"));
        }
        @Override
        public boolean interceptItemExtract(byte aCoverSide, CoverData aData, int aSlot, ItemStack aStack, byte aSide) {
            if (aCoverSide != aSide) return F;
            if (aData.mStopped) return T;
            if (aData.mNBTs[aCoverSide] == null || !aData.mNBTs[aCoverSide].hasKey("ktfru.filter.property")) return aData.mVisuals[aCoverSide] == 0;
            return (aData.mVisuals[aCoverSide] == 0) != getProperties(aStack).contains(aData.mNBTs[aCoverSide].getString("ktfru.filter.property"));
        }
        @Override
        public boolean interceptConnect(byte aCoverSide, CoverData aData) {
            boolean rIntercept = aData.mTileEntity instanceof ITileEntityItemPipe && aData.mTileEntity.getAdjacentTileEntity(aCoverSide).mTileEntity instanceof ITileEntityItemPipe;
            if (rIntercept) ((ITileEntityItemPipe)aData.mTileEntity).disconnect(aCoverSide, T);
            return rIntercept;
        }
        @Override
        public boolean interceptCoverPlacement(byte aCoverSide, CoverData aData, Entity aPlayer) {
            return aData.mTileEntity instanceof ITileEntityItemPipe && aData.mTileEntity.getAdjacentTileEntity(aCoverSide).mTileEntity instanceof ITileEntityItemPipe;
        }

     public static List<String> getProperties(ItemStack aStack){
         ArrayList<String> list= new ArrayList<>();
         OreDictItemData data= OreDictManager.INSTANCE.getItemData(aStack);
         if (data==null||data.mMaterial==null||data.mMaterial.mMaterial==null)return list;
         OreDictMaterial mat = data.mMaterial.mMaterial;
         for (TagData tagDatum : codeUtil.getAllTagData(mat)) list.add(tagDatum.mName);
         if(!mat.mToThis.isEmpty())mat.mToThis.forEach(subMat-> {for (TagData tagDatum : codeUtil.getAllTagData(subMat)) if(!list.contains(tagDatum.mName))list.add(tagDatum.mName);});
         return list;
     }

        @Override public ITexture getCoverTextureSurface(byte aCoverSide, CoverData aData) {return aData.mVisuals[aCoverSide]==0?sTextureNormal:sTextureInverted;}
        @Override public ITexture getCoverTextureAttachment(byte aCoverSide, CoverData aData, byte aTextureSide) {return ALONG_AXIS[aCoverSide][aTextureSide] ? BlockTextureMulti.get(BACKGROUND_COVER, aData.mVisuals[aCoverSide]==0?sTextureNormal:sTextureInverted) : BACKGROUND_COVER;}
        @Override public ITexture getCoverTextureHolder(byte aCoverSide, CoverData aData, byte aTextureSide) {return BACKGROUND_COVER;}
        @Override public boolean needsVisualsSaved(byte aCoverSide, CoverData aData) {return T;}
        @Override public boolean showsConnectorFront(byte aCoverSide, CoverData aData) {return F;}

        public static final ITexture
                sTextureInverted = BlockTextureDefault.get("machines/covers/filteritem/inverted"),
                sTextureNormal = BlockTextureDefault.get("machines/covers/filteritem/normal");
}
