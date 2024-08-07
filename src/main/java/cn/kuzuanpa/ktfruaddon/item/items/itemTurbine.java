/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.item.items;

import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import gregapi.data.MT;
import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;
import gregapi.oredict.OreDictItemData;
import net.minecraft.item.ItemStack;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;
import static gregapi.data.CS.U;

public class itemTurbine extends MultiItemRandom {
    public itemTurbine() {
        super(MOD_ID, "ktfru.item.turbine");
        setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: Turbine", this,  (short)1));
    }
    public final static short offsetDamaged =10000;
    @Override
    public void addItems() {
        ItemList.TurbineSteelSteam                .set(addItem(1,  "Steel Turbine (Steam)"                 , "Estimated Durability: run 12Hours for 4096RU/t")  , new OreDictItemData(MT.Steel         , 40*U));
        ItemList.TurbineSteelSteamChecked         .set(addItem(2,  "Checked Steel Turbine (Steam)"         , "Estimated Durability: run 12Hours for 4096RU/t")  , new OreDictItemData(MT.Steel         , 40*U));
        ItemList.TurbineStainlessSteelSteam       .set(addItem(3,  "StainlessSteel Turbine (Steam)"        , "Estimated Durability: run 24Hours for 4096RU/t")  , new OreDictItemData(MT.StainlessSteel, 40*U));
        ItemList.TurbineStainlessSteelSteamChecked.set(addItem(4,  "Checked StainlessSteel Turbine (Steam)", "Estimated Durability: run 24Hours for 4096RU/t")  , new OreDictItemData(MT.StainlessSteel, 40*U));
        ItemList.TurbineTitaniumSteam             .set(addItem(5,  "Titanium Turbine (Steam)"              , "Estimated Durability: run 24Hours for 8192RU/t")  , new OreDictItemData(MT.Ti            , 40*U));
        ItemList.TurbineTitaniumSteamChecked      .set(addItem(6,  "Checked Titanium Turbine (Steam)"      , "Estimated Durability: run 24Hours for 8192RU/t")  , new OreDictItemData(MT.Ti            , 40*U));
        ItemList.TurbineTrinitaniumSteam          .set(addItem(7,  "Trinitanium Turbine (Steam)"           , "Estimated Durability: run 48Hours for 8192RU/t")  , new OreDictItemData(MT.Trinitanium   , 40*U));
        ItemList.TurbineTrinitaniumSteamChecked   .set(addItem(8,  "Checked Trinitanium Turbine (Steam)"   , "Estimated Durability: run 48Hours for 8192RU/t")  , new OreDictItemData(MT.Trinitanium   , 40*U));
        ItemList.TurbineHSSGSteam                 .set(addItem(9,  "HSS-G Turbine (Steam)"                 , "Estimated Durability: run 48Hours for 8192RU/t")  , new OreDictItemData(MT.HSSG          , 40*U));
        ItemList.TurbineHSSGSteamChecked          .set(addItem(10, "Checked HSS-G Turbine (Steam)"         , "Estimated Durability: run 48Hours for 8192RU/t")  , new OreDictItemData(MT.HSSG          , 40*U));
        ItemList.TurbineHSSESteam                 .set(addItem(11, "HSS-E Turbine (Steam)"                 , "Estimated Durability: run 72Hours for 8192RU/t")  , new OreDictItemData(MT.HSSE          , 40*U));
        ItemList.TurbineHSSESteamChecked          .set(addItem(12, "Checked HSS-E Turbine (Steam)"         , "Estimated Durability: run 72Hours for 8192RU/t")  , new OreDictItemData(MT.HSSE          , 40*U));
        ItemList.TurbineHSSSSteam                 .set(addItem(13, "HSS-S Turbine (Steam)"                 , "Estimated Durability: run 96Hours for 8192RU/t")  , new OreDictItemData(MT.HSSS          , 40*U));
        ItemList.TurbineHSSSSteamChecked          .set(addItem(14, "Checked HSS-S Turbine (Steam)"         , "Estimated Durability: run 96Hours for 8192RU/t")  , new OreDictItemData(MT.HSSS          , 40*U));
        ItemList.TurbineTungstenSteelSteam        .set(addItem(15, "TungstenSteel Turbine (Steam)"         , "Estimated Durability: run 48Hours for 16384RU/t") , new OreDictItemData(MT.TungstenSteel , 40*U));
        ItemList.TurbineTungstenSteelSteamChecked .set(addItem(16, "Checked TungstenSteel Turbine (Steam)" , "Estimated Durability: run 48Hours for 16384RU/t") , new OreDictItemData(MT.TungstenSteel , 40*U));
        ItemList.TurbineTungstenSteam             .set(addItem(17, "Tungsten Turbine (Steam)"              , "Estimated Durability: run 96Hours for 16384RU/t") , new OreDictItemData(MT.W             , 40*U));
        ItemList.TurbineTungstenSteamChecked      .set(addItem(18, "Checked Tungsten Turbine (Steam)"      , "Estimated Durability: run 96Hours for 16384RU/t") , new OreDictItemData(MT.W             , 40*U));
        ItemList.TurbineVibramantiumSteam         .set(addItem(19, "Vibramantium Turbine (Steam)"          , "Estimated Durability: run 48Hours for 131072RU/t"), new OreDictItemData(MT.Vibramantium  , 40*U));
        ItemList.TurbineVibramantiumSteamChecked  .set(addItem(20, "Checked Vibramantium Turbine (Steam)"  , "Estimated Durability: run 48Hours for 131072RU/t"), new OreDictItemData(MT.Vibramantium  , 40*U));

        ItemList.TurbineSteelGas                .set(addItem(5001, "Steel Turbine (Gas)"                 , "Estimated Durability: run 12Hours for 4096RU/t")  , new OreDictItemData(MT.Steel         , 40*U));
        ItemList.TurbineSteelGasChecked         .set(addItem(5002, "Checked Steel Turbine (Gas)"         , "Estimated Durability: run 12Hours for 4096RU/t")  , new OreDictItemData(MT.Steel         , 40*U));
        ItemList.TurbineStainlessSteelGas       .set(addItem(5003, "StainlessSteel Turbine (Gas)"        , "Estimated Durability: run 24Hours for 4096RU/t")  , new OreDictItemData(MT.StainlessSteel, 40*U));
        ItemList.TurbineStainlessSteelGasChecked.set(addItem(5004, "Checked StainlessSteel Turbine (Gas)", "Estimated Durability: run 24Hours for 4096RU/t")  , new OreDictItemData(MT.StainlessSteel, 40*U));
        ItemList.TurbineTitaniumGas             .set(addItem(5005, "Titanium Turbine (Gas)"              , "Estimated Durability: run 24Hours for 8192RU/t")  , new OreDictItemData(MT.Ti            , 40*U));
        ItemList.TurbineTitaniumGasChecked      .set(addItem(5006, "Checked Titanium Turbine (Gas)"      , "Estimated Durability: run 24Hours for 8192RU/t")  , new OreDictItemData(MT.Ti            , 40*U));
        ItemList.TurbineTrinitaniumGas          .set(addItem(5007, "Trinitanium Turbine (Gas)"           , "Estimated Durability: run 48Hours for 8192RU/t")  , new OreDictItemData(MT.Trinitanium   , 40*U));
        ItemList.TurbineTrinitaniumGasChecked   .set(addItem(5008, "Checked Trinitanium Turbine (Gas)"   , "Estimated Durability: run 48Hours for 8192RU/t")  , new OreDictItemData(MT.Trinitanium   , 40*U));
        ItemList.TurbineHSSGGas                 .set(addItem(5009, "HSS-G Turbine (Gas)"                 , "Estimated Durability: run 48Hours for 8192RU/t")  , new OreDictItemData(MT.HSSG          , 40*U));
        ItemList.TurbineHSSGGasChecked          .set(addItem(5010, "Checked HSS-G Turbine (Gas)"         , "Estimated Durability: run 48Hours for 8192RU/t")  , new OreDictItemData(MT.HSSG          , 40*U));
        ItemList.TurbineHSSEGas                 .set(addItem(5011, "HSS-E Turbine (Gas)"                 , "Estimated Durability: run 72Hours for 8192RU/t")  , new OreDictItemData(MT.HSSE          , 40*U));
        ItemList.TurbineHSSEGasChecked          .set(addItem(5012, "Checked HSS-E Turbine (Gas)"         , "Estimated Durability: run 72Hours for 8192RU/t")  , new OreDictItemData(MT.HSSE          , 40*U));
        ItemList.TurbineHSSSGas                 .set(addItem(5013, "HSS-S Turbine (Gas)"                 , "Estimated Durability: run 96Hours for 8192RU/t")  , new OreDictItemData(MT.HSSS          , 40*U));
        ItemList.TurbineHSSSGasChecked          .set(addItem(5014, "Checked HSS-S Turbine (Gas)"         , "Estimated Durability: run 96Hours for 8192RU/t")  , new OreDictItemData(MT.HSSS          , 40*U));
        ItemList.TurbineTungstenSteelGas        .set(addItem(5015, "TungstenSteel Turbine (Gas)"         , "Estimated Durability: run 48Hours for 16384RU/t") , new OreDictItemData(MT.TungstenSteel , 40*U));
        ItemList.TurbineTungstenSteelGasChecked .set(addItem(5016, "Checked TungstenSteel Turbine (Gas)" , "Estimated Durability: run 48Hours for 16384RU/t") , new OreDictItemData(MT.TungstenSteel , 40*U));
        ItemList.TurbineTungstenGas             .set(addItem(5017, "Tungsten Turbine (Gas)"              , "Estimated Durability: run 96Hours for 16384RU/t") , new OreDictItemData(MT.W             , 40*U));
        ItemList.TurbineTungstenGasChecked      .set(addItem(5018, "Checked Tungsten Turbine (Gas)"      , "Estimated Durability: run 96Hours for 16384RU/t") , new OreDictItemData(MT.W             , 40*U));
        ItemList.TurbineVibramantiumGas         .set(addItem(5019, "Vibramantium Turbine (Gas)"          , "Estimated Durability: run 48Hours for 131072RU/t"), new OreDictItemData(MT.Vibramantium  , 40*U));
        ItemList.TurbineVibramantiumGasChecked  .set(addItem(5020, "Checked Vibramantium Turbine (Gas)"  , "Estimated Durability: run 48Hours for 131072RU/t"), new OreDictItemData(MT.Vibramantium  , 40*U));


        ItemList.TurbineSteelSteamDamaged                .set(addItem(offsetDamaged+1,  "Damaged Steel Turbine (Steam)"                 , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.Steel         , 40*U));
        ItemList.TurbineSteelSteamCheckedDamaged         .set(addItem(offsetDamaged+2,  "Damaged Checked Steel Turbine (Steam)"         , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.Steel         , 40*U));
        ItemList.TurbineStainlessSteelSteamDamaged       .set(addItem(offsetDamaged+3,  "Damaged StainlessSteel Turbine (Steam)"        , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.StainlessSteel, 40*U));
        ItemList.TurbineStainlessSteelSteamCheckedDamaged.set(addItem(offsetDamaged+4,  "Damaged Checked StainlessSteel Turbine (Steam)", "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.StainlessSteel, 40*U));
        ItemList.TurbineTitaniumSteamDamaged             .set(addItem(offsetDamaged+5,  "Damaged Titanium Turbine (Steam)"              , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.Ti            , 40*U));
        ItemList.TurbineTitaniumSteamCheckedDamaged      .set(addItem(offsetDamaged+6,  "Damaged Checked Titanium Turbine (Steam)"      , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.Ti            , 40*U));
        ItemList.TurbineTrinitaniumSteamDamaged          .set(addItem(offsetDamaged+7,  "Damaged Trinitanium Turbine (Steam)"           , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.Trinitanium   , 40*U));
        ItemList.TurbineTrinitaniumSteamCheckedDamaged   .set(addItem(offsetDamaged+8,  "Damaged Checked Trinitanium Turbine (Steam)"   , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.Trinitanium   , 40*U));
        ItemList.TurbineHSSGSteamDamaged                 .set(addItem(offsetDamaged+9,  "Damaged HSS-G Turbine (Steam)"                 , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.HSSG          , 40*U));
        ItemList.TurbineHSSGSteamCheckedDamaged          .set(addItem(offsetDamaged+10, "Damaged Checked HSS-G Turbine (Steam)"         , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.HSSG          , 40*U));
        ItemList.TurbineHSSESteamDamaged                 .set(addItem(offsetDamaged+11, "Damaged HSS-E Turbine (Steam)"                 , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.HSSE          , 40*U));
        ItemList.TurbineHSSESteamCheckedDamaged          .set(addItem(offsetDamaged+12, "Damaged Checked HSS-E Turbine (Steam)"         , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.HSSE          , 40*U));
        ItemList.TurbineHSSSSteamDamaged                 .set(addItem(offsetDamaged+13, "Damaged HSS-S Turbine (Steam)"                 , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.HSSS          , 40*U));
        ItemList.TurbineHSSSSteamCheckedDamaged          .set(addItem(offsetDamaged+14, "Damaged Checked HSS-S Turbine (Steam)"         , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.HSSS          , 40*U));
        ItemList.TurbineTungstenSteelSteamDamaged        .set(addItem(offsetDamaged+15, "Damaged TungstenSteel Turbine (Steam)"         , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.TungstenSteel , 40*U));
        ItemList.TurbineTungstenSteelSteamCheckedDamaged .set(addItem(offsetDamaged+16, "Damaged Checked TungstenSteel Turbine (Steam)" , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.TungstenSteel , 40*U));
        ItemList.TurbineTungstenSteamDamaged             .set(addItem(offsetDamaged+17, "Damaged Tungsten Turbine (Steam)"              , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.W             , 40*U));
        ItemList.TurbineTungstenSteamCheckedDamaged      .set(addItem(offsetDamaged+18, "Damaged Checked Tungsten Turbine (Steam)"      , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.W             , 40*U));
        ItemList.TurbineVibramantiumSteamDamaged         .set(addItem(offsetDamaged+19, "Damaged Vibramantium Turbine (Steam)"          , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.Vibramantium  , 40*U));
        ItemList.TurbineVibramantiumSteamCheckedDamaged  .set(addItem(offsetDamaged+20, "Damaged Checked Vibramantium Turbine (Steam)"  , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.Vibramantium  , 40*U));
                                                                            
        ItemList.TurbineSteelGasDamaged                .set(addItem(offsetDamaged+5001, "Damaged Steel Turbine (Gas)"                 , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.Steel         , 40*U));
        ItemList.TurbineSteelGasCheckedDamaged         .set(addItem(offsetDamaged+5002, "Damaged Checked Steel Turbine (Gas)"         , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.Steel         , 40*U));
        ItemList.TurbineStainlessSteelGasDamaged       .set(addItem(offsetDamaged+5003, "Damaged StainlessSteel Turbine (Gas)"        , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.StainlessSteel, 40*U));
        ItemList.TurbineStainlessSteelGasCheckedDamaged.set(addItem(offsetDamaged+5004, "Damaged Checked StainlessSteel Turbine (Gas)", "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.StainlessSteel, 40*U));
        ItemList.TurbineTitaniumGasDamaged             .set(addItem(offsetDamaged+5005, "Damaged Titanium Turbine (Gas)"              , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.Ti            , 40*U));
        ItemList.TurbineTitaniumGasCheckedDamaged      .set(addItem(offsetDamaged+5006, "Damaged Checked Titanium Turbine (Gas)"      , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.Ti            , 40*U));
        ItemList.TurbineTrinitaniumGasDamaged          .set(addItem(offsetDamaged+5007, "Damaged Trinitanium Turbine (Gas)"           , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.Trinitanium   , 40*U));
        ItemList.TurbineTrinitaniumGasCheckedDamaged   .set(addItem(offsetDamaged+5008, "Damaged Checked Trinitanium Turbine (Gas)"   , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.Trinitanium   , 40*U));
        ItemList.TurbineHSSGGasDamaged                 .set(addItem(offsetDamaged+5009, "Damaged HSS-G Turbine (Gas)"                 , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.HSSG          , 40*U));
        ItemList.TurbineHSSGGasCheckedDamaged          .set(addItem(offsetDamaged+5010, "Damaged Checked HSS-G Turbine (Gas)"         , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.HSSG          , 40*U));
        ItemList.TurbineHSSEGasDamaged                 .set(addItem(offsetDamaged+5011, "Damaged HSS-E Turbine (Gas)"                 , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.HSSE          , 40*U));
        ItemList.TurbineHSSEGasCheckedDamaged          .set(addItem(offsetDamaged+5012, "Damaged Checked HSS-E Turbine (Gas)"         , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.HSSE          , 40*U));
        ItemList.TurbineHSSSGasDamaged                 .set(addItem(offsetDamaged+5013, "Damaged HSS-S Turbine (Gas)"                 , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.HSSS          , 40*U));
        ItemList.TurbineHSSSGasCheckedDamaged          .set(addItem(offsetDamaged+5014, "Damaged Checked HSS-S Turbine (Gas)"         , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.HSSS          , 40*U));
        ItemList.TurbineTungstenSteelGasDamaged        .set(addItem(offsetDamaged+5015, "Damaged TungstenSteel Turbine (Gas)"         , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.TungstenSteel , 40*U));
        ItemList.TurbineTungstenSteelGasCheckedDamaged .set(addItem(offsetDamaged+5016, "Damaged Checked TungstenSteel Turbine (Gas)" , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.TungstenSteel , 40*U));
        ItemList.TurbineTungstenGasDamaged             .set(addItem(offsetDamaged+5017, "Damaged Tungsten Turbine (Gas)"              , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.W             , 40*U));
        ItemList.TurbineTungstenGasCheckedDamaged      .set(addItem(offsetDamaged+5018, "Damaged Checked Tungsten Turbine (Gas)"      , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.W             , 40*U));
        ItemList.TurbineVibramantiumGasDamaged         .set(addItem(offsetDamaged+5019, "Damaged Vibramantium Turbine (Gas)"          , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.Vibramantium  , 40*U));
        ItemList.TurbineVibramantiumGasCheckedDamaged  .set(addItem(offsetDamaged+5020, "Damaged Checked Vibramantium Turbine (Gas)"  , "Already Damaged. You can try to recycle it."), new OreDictItemData(MT.Vibramantium  , 40*U));

    }
    private static final long[] durability = {0, 12*3600*20*4096L, 12*3600*20*4096L , 24*3600*20*4096L , 24*3600*20*4096L , 24*3600*20*8192L , 24*3600*20*8192L , 48*3600*20*8192L , 48*3600*20*8192L , 48*3600*20*8192L , 48*3600*20*8192L , 72*3600*20*8192L , 72*3600*20*8192L , 96*3600*20*8192L , 96*3600*20*8192L , 48*3600*20*16384L , 48*3600*20*16384L , 96*3600*20*16384L , 96*3600*20*16384L , 48*3600*20*131072L , 48*3600*20*131072L} ;
    @Override public int getItemStackLimit(ItemStack aStack) {return 1;}
    public static long getDurability(int meta){
        long i =0;
        try {
          i = meta > offsetDamaged ? 0 : meta > 5000 ? durability[meta - 5000] : durability[meta];
        }catch (IndexOutOfBoundsException e){return 0;}
        return i;
    }


}