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

package cn.kuzuanpa.ktfruaddon.command;

import gregapi.recipes.AdvancedCraftingTool;
import gregapi.recipes.Recipe;
import gregapi.recipes.Recipe.RecipeMap;
import gregapi.util.CR;
import gregapi.util.ST;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class CommandRecipeExport extends CommandBase {
    private static final String FORMAT_VERSION = "1";
    private static final String HEADER_LINE = "recipes.tsv";

    @Override
    public String getCommandName() {
        return "exportRecipes";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "导出全部GT与工作台/熔炉配方到当前目录/recipeExport/recipes.tsv";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        try {
            File exportDir = new File("recipeExport");
            if (!exportDir.isDirectory() && !exportDir.mkdirs()) {
                throw new IOException("无法创建目录: " + exportDir.getAbsolutePath());
            }
            File exportFile = new File(exportDir, HEADER_LINE);
            try (BufferedWriter writer = Files.newBufferedWriter(exportFile.toPath(), StandardCharsets.UTF_8)) {
                writeHeader(writer);
                int gtCount = writeGTRecipes(writer);
                int craftingCount = writeCraftingRecipes(writer);
                int smeltingCount = writeSmeltingRecipes(writer);
                writer.newLine();
                writer.write("# total=" + (gtCount + craftingCount + smeltingCount)
                        + ";gt=" + gtCount
                        + ";crafting=" + craftingCount
                        + ";smelting=" + smeltingCount);
                writer.newLine();
            }
            sender.addChatMessage(new ChatComponentText("配方已导出到: " + exportFile.getAbsolutePath()));
        } catch (Throwable e) {
            e.printStackTrace();
            sender.addChatMessage(new ChatComponentText("导出失败: " + e));
        }
    }

    private void writeHeader(BufferedWriter writer) throws IOException {
        writer.write("# ktfru recipe export v" + FORMAT_VERSION);
        writer.newLine();
        writer.write("# line: @(map)\\t[inputs] -> [outputs]");
        writer.newLine();
        writer.write("# item: item:registry;damage;stackSize");
        writer.newLine();
        writer.write("# fluid: fluid:fluidName;amount");
        writer.newLine();
        writer.write("# '~' separates oredict alternatives in the same crafting slot");
        writer.newLine();
        writer.write("# special: special:className ; crafting recipes without a readable ingredient layout");
        writer.newLine();
    }

    private int writeGTRecipes(BufferedWriter writer) throws IOException {
        TreeSet<String> lines = new TreeSet<>();
        List<RecipeMap> maps = new ArrayList<>(Recipe.RecipeMap.RECIPE_MAPS.values());
        maps.sort(Comparator.comparing(map -> map.mNameInternal));

        for (RecipeMap map : maps) {
            if (!map.mNEIAllowed) continue;
            try {
                for (Recipe recipe : map.getNEIAllRecipes()) addGTLine(lines, map.mNameInternal, recipe);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }
        return lines.size();
    }

    private void addGTLine(Set<String> lines, String mapName, Recipe recipe) {
        if (recipe == null || !recipe.mEnabled || recipe.mFakeRecipe) return;
        String line = formatLine(mapName, recipe);
        if (line != null) lines.add(line);
    }

    private int writeCraftingRecipes(BufferedWriter writer) throws IOException {
        TreeSet<String> lines = new TreeSet<>();
        for (Object object : CR.list()) {
            if (!(object instanceof IRecipe)) continue;
            IRecipe recipe = (IRecipe) object;
            ItemStack output = recipe.getRecipeOutput();
            if (!materialValid(output)) continue;
            String inputs = encodeCraftingInputs(recipe);
            String map = inputs.startsWith("special:") ? "@(minecraft:crafting:special)" : "@(minecraft:crafting)";
            String line = map + "\t[" + inputs + "] -> [" + itemToken(output) + "]";
            lines.add(line);
        }
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }
        return lines.size();
    }

    private String encodeCraftingInputs(IRecipe recipe) {
        if (recipe instanceof AdvancedCraftingTool) return "special:" + recipe.getClass().getName();
        List<Object> slots = new ArrayList<>();
        if (recipe instanceof ShapedRecipes) {
            for (ItemStack slot : ((ShapedRecipes) recipe).recipeItems) if (slot != null) slots.add(slot);
        } else if (recipe instanceof ShapelessRecipes) {
            for (Object slot : ((ShapelessRecipes) recipe).recipeItems) if (slot != null) slots.add(slot);
        } else if (recipe instanceof ShapedOreRecipe) {
            for (Object slot : ((ShapedOreRecipe) recipe).getInput()) if (slot != null) slots.add(slot);
        } else if (recipe instanceof ShapelessOreRecipe) {
            for (Object slot : ((ShapelessOreRecipe) recipe).getInput()) if (slot != null) slots.add(slot);
        } else {
            return "special:" + recipe.getClass().getName();
        }
        List<String> encodedSlots = new ArrayList<>();
        for (Object slot : slots) {
            StringBuilder slotBuilder = new StringBuilder();
            appendSlot(slotBuilder, slot);
            if (slotBuilder.length() > 0) encodedSlots.add(slotBuilder.toString());
        }
        Collections.sort(encodedSlots);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < encodedSlots.size(); i++) {
            if (i > 0) result.append('|');
            result.append(encodedSlots.get(i));
        }
        return result.toString();
    }

    private void appendSlot(StringBuilder result, Object slot) {
        if (slot == null) return;
        if (result.length() > 0) result.append('|');
        if (slot instanceof ItemStack) {
            ItemStack stack = (ItemStack) slot;
            if (materialValid(stack)) result.append(itemToken(stack));
        } else if (slot instanceof Iterable) {
            boolean first = true;
            result.append("any:");
            for (Object alternative : (Iterable<?>) slot) {
                if (!(alternative instanceof ItemStack)) continue;
                ItemStack stack = (ItemStack) alternative;
                if (!materialValid(stack)) continue;
                if (!first) result.append('~');
                result.append(itemToken(stack));
                first = false;
            }
            if (first) result.append("none");
        } else {
            result.append("special:").append(slot.getClass().getName());
        }
    }

    private int writeSmeltingRecipes(BufferedWriter writer) throws IOException {
        TreeSet<String> lines = new TreeSet<>();
        @SuppressWarnings("unchecked")
        Map<ItemStack, ItemStack> smelting = FurnaceRecipes.smelting().getSmeltingList();
        for (Map.Entry<ItemStack, ItemStack> entry : smelting.entrySet()) {
            ItemStack input = entry.getKey();
            ItemStack output = entry.getValue();
            if (!materialValid(input) || !materialValid(output)) continue;
            String line = "@(minecraft:smelting)\t[" + itemToken(input)
                    + "] -> [" + itemToken(output) + "]";
            lines.add(line);
        }
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }
        return lines.size();
    }

    private String formatLine(String mapName, Recipe recipe) {
        StringBuilder result = new StringBuilder("@(").append(mapName).append(")\t[");
        boolean first = true;
        for (ItemStack input : recipe.mInputs) {
            if (!materialValid(input)) continue;
            if (!first) result.append('|');
            result.append(itemToken(input));
            first = false;
        }
        for (FluidStack input : recipe.mFluidInputs) {
            if (input == null || input.amount <= 0 || input.getFluid() == null) continue;
            if (!first) result.append('|');
            result.append(fluidToken(input));
            first = false;
        }
        result.append("] -> [");
        first = true;
        for (ItemStack output : recipe.mOutputs) {
            if (!materialValid(output)) continue;
            if (!first) result.append('|');
            result.append(itemToken(output));
            first = false;
        }
        for (FluidStack output : recipe.mFluidOutputs) {
            if (output == null || output.amount <= 0 || output.getFluid() == null) continue;
            if (!first) result.append('|');
            result.append(fluidToken(output));
            first = false;
        }
        result.append(']');
        return result.toString();
    }

    private static boolean materialValid(ItemStack stack) {
        return ST.valid(stack) && stack.stackSize > 0;
    }

    private String itemToken(ItemStack stack) {
        String registryName = ST.regName(stack);
        if (registryName == null || registryName.isEmpty()) {
            registryName = "unregistered:" + ST.id(stack) + ":" + ST.meta_(stack);
        }
        return "item:" + registryName + ';' + ST.meta_(stack) + ';' + stack.stackSize;
    }

    private String fluidToken(FluidStack stack) {
        return "fluid:" + stack.getFluid().getName() + ';' + stack.amount;
    }
}
