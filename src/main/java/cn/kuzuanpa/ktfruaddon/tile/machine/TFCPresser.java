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
package cn.kuzuanpa.ktfruaddon.tile.machine;


import com.bioxx.tfc.Core.GTRecipes;
import com.bioxx.tfc.TileEntities.TEAnvil;
import com.bioxx.tfc.api.Crafting.AnvilReq;
import gregapi.code.TagData;
import gregapi.cover.ICover;
import gregapi.data.OP;
import gregapi.data.TD;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.util.OM;
import gregapi.util.ST;
import gregapi.util.UT;
import gregapi.util.WD;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

import static gregapi.data.CS.*;

public class TFCPresser extends TileEntityBase09FacingSingle implements ITileEntityEnergy{

    public boolean isAnvilLevelEnough = false, displayedParticles = false;
    public long mEnergy = 0;
    public byte raisingTimer = -41;
    public float TESRRasingTimer = -41;
    public int mCost = 16;
    public String getTileEntityName() {
        return "ktfru.multitileentity.machine.tfc.presser";
    }

    public boolean[] getValidSides() {
        return SIDES_HORIZONTAL;
    }

    @Override
    public void onTick2(long aTimer, boolean isServerside) {
        //auto output
        if(OP.plate.contains(slot(0)) && WD.te(worldObj,xCoord,yCoord-2,zCoord,false)!=null && ST.move(delegator(SIDE_BOTTOM), new DelegatorTileEntity<>(WD.te(worldObj,xCoord,yCoord-2,zCoord,false),SIDE_TOP)) != 0) slotKill(0);
        if(mEnergy>mCost)mEnergy -= mCost;
        if(raisingTimer > 0){
            if(mEnergy < mCost)return;
            displayedParticles = false;
            raisingTimer ++;
            if(raisingTimer >= 40)raisingTimer = -41;
            return;
        }

        TileEntity te = worldObj.getTileEntity(xCoord, yCoord -1, zCoord);
        if(!(te instanceof TEAnvil)){
            isAnvilLevelEnough=false;
            return;
        }
        int anvilLevel = ((TEAnvil) te).anvilTier;
        if(raisingTimer >= -1){
            processItem(anvilLevel);
            raisingTimer = 1;
            return;
        }
        if(raisingTimer >= -40) {
            raisingTimer++;
            return;
        }
        if(slotHas(0) && isItemProcessable(anvilLevel))raisingTimer = -40;
    }

    public boolean isItemProcessable(int anvilLevel){
        AnvilReq req = GTRecipes.getAnvilReqFromMaterial(OM.data(slot(0)).mMaterial.mMaterial);
        isAnvilLevelEnough = anvilLevel >= req.Tier;

        return OP.ingotDouble.contains(slot(0)) && isAnvilLevelEnough;
    }
    public void processItem(int anvilLevel){
        if(OP.ingotDouble.contains(slot(0)) && anvilLevel >= GTRecipes.getAnvilReqFromMaterial(OM.data(slot(0)).mMaterial.mMaterial).Tier){
            setInventorySlotContents(0, OP.plate.mat(OM.data(slot(0)).mMaterial.mMaterial, 1));
            UT.Sounds.send(SFX.MC_BREAK, (TileEntity) this);
        }
    }

    @Override
    public boolean isEnergyAcceptingFrom(TagData aEnergyType, byte aSide, boolean aTheoretical) {
        return aEnergyType.equals(TD.Energy.RU);
    }

    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if(!aEnergyType.equals(TD.Energy.RU))return 0;
        if(Math.abs(aSize) > 64)overcharge(8, aEnergyType);
        long tInput = Math.min(mCost*4L - mEnergy, aSize * aAmount), tConsumed = Math.min(aAmount, (tInput/aSize) + (tInput%aSize!=0?1:0));
        if (aDoInject) mEnergy += tConsumed * Math.abs(aSize);
        return tConsumed;
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if(isServerSide() && aChatReturn!=null && aTool.equals(TOOL_magnifyingglass)){
            if(mEnergy < mCost)aChatReturn.add(String.format("energy seems not enough, You need at least %s RU/t to use it", mCost));
            if(OP.plate.contains(slot(0)))aChatReturn.add("no space to output, place a container under the anvil to auto output");
            if(!isAnvilLevelEnough)aChatReturn.add("anvil not exist or cannot handle this item.");
            if(aChatReturn.isEmpty())aChatReturn.add("working normally");
            if(slotHas(0))aChatReturn.add("Contents: "+slot(0).getDisplayName());
            return 1;
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
            ItemStack aStack=aPlayer.getCurrentEquippedItem();
            if(aStack == null ){
                if(!slotHas(0)) return false;
                aPlayer.inventory.setInventorySlotContents(aPlayer.inventory.currentItem, slot(0));
                slotKill(0);
                return true;
            }else if (OP.ingotDouble.contains(aStack)){
                if (slotHas(0)) return false;
                ItemStack stack = aStack.copy();
                stack.stackSize = 1;
                setInventorySlotContents(0, stack);
                aStack.stackSize-=1;
                if(aStack.stackSize==0)aPlayer.inventory.setInventorySlotContents(aPlayer.inventory.currentItem, null);
                return T;
            }

        return false;
    }

    @Override
    public boolean allowCover(byte aSide, ICover aCover) {
        return false;
    }

    @Override
    public boolean isSurfaceOpaque2(byte aSide) {
        return false;
    }

    @Override
    public boolean addDefaultCollisionBoxToList() {
        return true;
    }

    @Override
    public int getLightOpacity(){
        return 1;
    }

    @Override
    public boolean renderItem(Block aBlock, RenderBlocks aRenderer) {
        TileEntityRendererDispatcher.instance.renderTileEntityAt(this, 0, 0, 0, 0);
        return T;
    }
    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return null;
    }

    @Override
    public boolean onTickCheck(long aTimer) {
        return true;
    }

    @Override
    public byte getVisualData() {
        return raisingTimer;
    }

    @Override
    public void setVisualData(byte aData) {
        raisingTimer = aData;
    }

    //Inventory
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[1];}
    @Override public boolean canDrop(int aInventorySlot) {return T;}

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    private static final int[] ACCESSIBLE_SLOTS = new int[] {0};

    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return ACCESSIBLE_SLOTS;}

    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) {
        return OP.plate.contains(slot(aSlot));
    }
}
