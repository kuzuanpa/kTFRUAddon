package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import gregapi.data.IL;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.util.CR;
import gregapi.util.ST;

import static cn.kuzuanpa.ktfruaddon.recipe.recipeManager.Assembler;
import static gregapi.data.CS.*;

public class Assembler
{
    public Assembler() {
        //Copied from GT6U
        Assembler.addRecipeX(T, F, F, F, T, 32, 16, ST.array(OP.wireFine.mat(MT.Cu, 1), OP.wireFine.mat(MT.Signalum, 1), OP.plateGemTiny.mat(MT.Si, 1)), IL.Circuit_Part_Good.get(1));
        Assembler.addRecipeX(T, F, F, F, T, 32, 16, ST.array(OP.wireFine.mat(MT.AnnealedCopper, 1), OP.wireFine.mat(MT.Signalum, 1), OP.plateGemTiny.mat(MT.Si, 1)), IL.Circuit_Part_Good.get(1));
        Assembler.addRecipeX(T, F, F, F, T, 32, 16, ST.array(OP.wireFine.mat(MT.Cu, 1), OP.wireFine.mat(MT.RedAlloy, 1), OP.plateGemTiny.mat(MT.Si, 1)), IL.Circuit_Part_Good.get(1));
        Assembler.addRecipeX(T, F, F, F, T, 32, 16, ST.array(OP.wireFine.mat(MT.AnnealedCopper, 1), OP.wireFine.mat(MT.RedAlloy, 1), OP.plateGemTiny.mat(MT.Si, 1)), IL.Circuit_Part_Good.get(1));
        Assembler.addRecipeX(T, F, F, F, T, 32, 16, ST.array(OP.wireFine.mat(MT.Cu, 1), OP.wireFine.mat(MT.Signalum, 1), OP.plateGemTiny.mat(MT.Ge, 1)), IL.Circuit_Part_Good.get(1));
        Assembler.addRecipeX(T, F, F, F, T, 32, 16, ST.array(OP.wireFine.mat(MT.AnnealedCopper, 1), OP.wireFine.mat(MT.Signalum, 1), OP.plateGemTiny.mat(MT.Ge, 1)), IL.Circuit_Part_Good.get(1));
        Assembler.addRecipeX(T, F, F, F, T, 32, 16, ST.array(OP.wireFine.mat(MT.Cu, 1), OP.wireFine.mat(MT.RedAlloy, 1), OP.plateGemTiny.mat(MT.Ge, 1)), IL.Circuit_Part_Good.get(1));
        Assembler.addRecipeX(T, F, F, F, T, 32, 16, ST.array(OP.wireFine.mat(MT.AnnealedCopper, 1), OP.wireFine.mat(MT.RedAlloy, 1), OP.plateGemTiny.mat(MT.Ge, 1)), IL.Circuit_Part_Good.get(1));
        Assembler.addRecipeX(T, F, F, F, T, 32, 16, ST.array(OP.wireFine.mat(MT.Cu, 1), OP.wireFine.mat(MT.Signalum, 1), OP.plateGemTiny.mat(MT.RedstoneAlloy, 1)), IL.Circuit_Part_Good.get(1));
        Assembler.addRecipeX(T, F, F, F, T, 32, 16, ST.array(OP.wireFine.mat(MT.AnnealedCopper, 1), OP.wireFine.mat(MT.Signalum, 1), OP.plateGemTiny.mat(MT.RedstoneAlloy, 1)), IL.Circuit_Part_Good.get(1));
        Assembler.addRecipeX(T, F, F, F, T, 32, 16, ST.array(OP.wireFine.mat(MT.Cu, 1), OP.wireFine.mat(MT.RedAlloy, 1), OP.plateGemTiny.mat(MT.RedstoneAlloy, 1)), IL.Circuit_Part_Good.get(1));
        Assembler.addRecipeX(T, F, F, F, T, 32, 16, ST.array(OP.wireFine.mat(MT.AnnealedCopper, 1), OP.wireFine.mat(MT.RedAlloy, 1), OP.plateGemTiny.mat(MT.RedstoneAlloy, 1)), IL.Circuit_Part_Good.get(1));
        Assembler.addRecipeX(T, F, F, F, T, 128, 48, ST.array(OP.wireFine.mat(MT.Au, 1), OP.wireFine.mat(MT.Signalum, 1), OP.plateGemTiny.mat(MT.Si, 1)), IL.Circuit_Part_Advanced.get(1));
        Assembler.addRecipeX(T, F, F, F, T, 128, 48, ST.array(OP.wireFine.mat(MT.Au, 1), OP.wireFine.mat(MT.Signalum, 1), OP.plateGemTiny.mat(MT.Ge, 1)), IL.Circuit_Part_Advanced.get(1));
        Assembler.addRecipeX(T, F, F, F, T, 128, 48, ST.array(OP.wireFine.mat(MT.Au, 1), OP.wireFine.mat(MT.Signalum, 1), OP.plateGemTiny.mat(MT.RedstoneAlloy, 1)), IL.Circuit_Part_Elite.get(1));
        Assembler.addRecipeX(T, F, F, F, T, 128, 192, ST.array(OP.wireFine.mat(MT.Pt, 1), OP.wireFine.mat(MT.Signalum, 1), OP.plateGemTiny.mat(MT.Si, 1)), IL.Circuit_Part_Master.get(1));
        Assembler.addRecipeX(T, F, F, F, T, 128, 192, ST.array(OP.wireFine.mat(MT.Pt, 1), OP.wireFine.mat(MT.Signalum, 1), OP.plateGemTiny.mat(MT.Ge, 1)), IL.Circuit_Part_Master.get(1));
        Assembler.addRecipeX(T, F, F, F, T, 128, 192, ST.array(OP.wireFine.mat(MT.Pt, 1), OP.wireFine.mat(MT.Signalum, 1), OP.plateGemTiny.mat(MT.RedstoneAlloy, 1)), IL.Circuit_Part_Ultimate.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 32, 128, IL.Circuit_Plate_Copper.get(1), IL.Circuit_Part_Good.get(4), IL.Circuit_Board_Good.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 32, 128, IL.Circuit_Plate_Copper.get(1), IL.Circuit_Part_Advanced.get(4), IL.Circuit_Board_Good.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 32, 128, IL.Circuit_Plate_Copper.get(1), IL.Circuit_Part_Elite.get(4), IL.Circuit_Board_Good.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 32, 128, IL.Circuit_Plate_Copper.get(1), IL.Circuit_Part_Master.get(4), IL.Circuit_Board_Good.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 32, 128, IL.Circuit_Plate_Copper.get(1), IL.Circuit_Part_Ultimate.get(4), IL.Circuit_Board_Good.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 32, 128, IL.Circuit_Plate_Gold.get(1), IL.Circuit_Part_Good.get(4), IL.Circuit_Board_Good.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 32, 512, IL.Circuit_Plate_Gold.get(1), IL.Circuit_Part_Advanced.get(4), IL.Circuit_Board_Advanced.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 128, 256, IL.Circuit_Plate_Gold.get(1), IL.Circuit_Part_Elite.get(4), IL.Circuit_Board_Elite.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 128, 256, IL.Circuit_Plate_Gold.get(1), IL.Circuit_Part_Master.get(4), IL.Circuit_Board_Elite.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 128, 256, IL.Circuit_Plate_Gold.get(1), IL.Circuit_Part_Ultimate.get(4), IL.Circuit_Board_Elite.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 32, 128, IL.Circuit_Plate_Platinum.get(1), IL.Circuit_Part_Good.get(4), IL.Circuit_Board_Good.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 32, 512, IL.Circuit_Plate_Platinum.get(1), IL.Circuit_Part_Advanced.get(4), IL.Circuit_Board_Advanced.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 128, 256, IL.Circuit_Plate_Platinum.get(1), IL.Circuit_Part_Elite.get(4), IL.Circuit_Board_Elite.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 128, 512, IL.Circuit_Plate_Platinum.get(1), IL.Circuit_Part_Master.get(4), IL.Circuit_Board_Master.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 128, 1024, IL.Circuit_Plate_Platinum.get(1), IL.Circuit_Part_Ultimate.get(4), IL.Circuit_Board_Ultimate.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 16, 64, IL.Circuit_Plate_Magic.get(1), IL.Circuit_Part_Magic.get(4), IL.Circuit_Board_Magic.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 16, 64, IL.Circuit_Plate_Enderium.get(1), IL.Circuit_Part_Enderium.get(4), IL.Circuit_Board_Enderium.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 16, 64, IL.Circuit_Plate_Signalum.get(1), IL.Circuit_Part_Signalum.get(4), IL.Circuit_Board_Signalum.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 16, 64, IL.Circuit_Plate_HSLA.get(1), IL.Circuit_Part_EnderPearl.get(4), IL.Circuit_Board_HSLA_Circuit.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 16, 64, IL.Circuit_Plate_HSLA.get(1), IL.Circuit_Part_EnderEye.get(4), IL.Circuit_Board_Power_Module.get(1));
        Assembler.addRecipe1(T, F, F, F, T, 32, 64, IL.Circuit_Board_Magic.get(1), MT.SolderingAlloy.liquid(U, T), NF, IL.Circuit_Magic.get(1));
        Assembler.addRecipe1(T, F, F, F, T, 32, 64, IL.Circuit_Board_Enderium.get(1), MT.SolderingAlloy.liquid(U, T), NF, IL.Circuit_Enderium.get(1));
        Assembler.addRecipe1(T, F, F, F, T, 32, 64, IL.Circuit_Board_Signalum.get(1), MT.Pb.liquid(U, T), NF, IL.Circuit_Signalum.get(1));
        Assembler.addRecipe1(T, F, F, F, T, 32, 64, IL.Circuit_Board_Signalum.get(1), MT.Sn.liquid(U, T), NF, IL.Circuit_Signalum.get(1));
        Assembler.addRecipe1(T, F, F, F, T, 32, 64, IL.Circuit_Board_Signalum.get(1), MT.SolderingAlloy.liquid(U, T), NF, IL.Circuit_Signalum.get(1));
        Assembler.addRecipe1(T, F, F, F, T, 32, 64, IL.Circuit_Board_BC_Redstone.get(1), MT.SolderingAlloy.liquid(U, T), NF, IL.Circuit_BC_Redstone.get(1));
        Assembler.addRecipe1(T, F, F, F, T, 32, 64, IL.Circuit_Board_BC_Iron.get(1), MT.SolderingAlloy.liquid(U, T), NF, IL.Circuit_BC_Iron.get(1));
        Assembler.addRecipe1(T, F, F, F, T, 32, 64, IL.Circuit_Board_BC_Gold.get(1), MT.SolderingAlloy.liquid(U, T), NF, IL.Circuit_BC_Gold.get(1));
        Assembler.addRecipe1(T, F, F, F, T, 32, 64, IL.Circuit_Board_BC_Diamond.get(1), MT.SolderingAlloy.liquid(U, T), NF, IL.Circuit_BC_Diamond.get(1));
        Assembler.addRecipe1(T, F, F, F, T, 32, 64, IL.Circuit_Board_BC_Ender.get(1), MT.SolderingAlloy.liquid(U, T), NF, IL.Circuit_BC_Ender.get(1));
        Assembler.addRecipe1(T, F, F, F, T, 32, 64, IL.Circuit_Board_BC_Quartz.get(1), MT.SolderingAlloy.liquid(U, T), NF, IL.Circuit_BC_Quartz.get(1));
        Assembler.addRecipe1(T, F, F, F, T, 32, 64, IL.Circuit_Board_BC_Comparator.get(1), MT.SolderingAlloy.liquid(U, T), NF, IL.Circuit_BC_Comparator.get(1));
        Assembler.addRecipe1(T, F, F, F, T, 32, 64, IL.Circuit_Board_BC_Emerald.get(1), MT.SolderingAlloy.liquid(U, T), NF, IL.Circuit_BC_Emerald.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 480, 144, IL.Processor_Crystal_Empty.get(1), IL.Circuit_Crystal_Diamond.get(1), IL.Processor_Crystal_Diamond.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 480, 144, IL.Processor_Crystal_Empty.get(1), IL.Circuit_Crystal_Ruby.get(1), IL.Processor_Crystal_Ruby.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 480, 144, IL.Processor_Crystal_Empty.get(1), IL.Circuit_Crystal_Emerald.get(1), IL.Processor_Crystal_Emerald.get(1));
        Assembler.addRecipe2(T, F, F, F, T, 480, 144, IL.Processor_Crystal_Empty.get(1), IL.Circuit_Crystal_Sapphire.get(1), IL.Processor_Crystal_Sapphire.get(1));
    }
}
