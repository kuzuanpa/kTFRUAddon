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

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CommandRecipeHeadsTails extends CommandBase {
    private static final String INPUT_FILE = "recipeExport/recipes.tsv";
    private static final String RESULT_FILE = "recipeExport/recipe_heads_tails.tsv";
    private static final String HEADS_FILE = "recipeExport/heads.tsv";
    private static final String TAILS_FILE = "recipeExport/tails.tsv";

    @Override
    public String getCommandName() {
        return "recipeHeadsTails";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "读取 recipeExport/recipes.tsv，统计全局头原料与尾产物";
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
        File input = new File(INPUT_FILE);
        if (!input.isFile()) {
            sender.addChatMessage(new ChatComponentText("未找到 " + input.getAbsolutePath() + "，请先运行 /exportRecipes"));
            return;
        }
        try {
            List<RecipeLine> recipes = parse(input);
            Analysis analysis = new Analysis(recipes);
            writeResults(analysis);
            sender.addChatMessage(new ChatComponentText("配方头/尾分析完成: " + new File(RESULT_FILE).getAbsolutePath()));
            sender.addChatMessage(new ChatComponentText("配方数=" + recipes.size()
                    + "; 全局头原料数=" + analysis.heads.size()
                    + "; 全局尾产物数=" + analysis.tails.size()));
        } catch (Throwable e) {
            e.printStackTrace();
            sender.addChatMessage(new ChatComponentText("失败: " + e));
        }
    }

    private List<RecipeLine> parse(File input) throws IOException {
        List<RecipeLine> recipes = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(input.toPath(), StandardCharsets.UTF_8)) {
            String rawLine;
            while ((rawLine = reader.readLine()) != null) {
                String line = rawLine.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                int mapStart = line.indexOf("@(");
                int mapEnd = mapStart < 0 ? -1 : line.indexOf(')', mapStart + 2);
                int arrow = line.indexOf(" -> ");
                if (mapStart < 0 || mapEnd < 0 || arrow < 0) continue;
                String map = line.substring(mapStart + 2, mapEnd);
                int leftStart = line.indexOf('[', mapEnd);
                int leftEnd = line.indexOf(']', leftStart);
                int rightStart = line.indexOf('[', arrow + 4);
                int rightEnd = line.indexOf(']', rightStart);
                if (leftStart < 0 || leftEnd < 0 || rightStart < 0 || rightEnd < 0) continue;
                List<String> inputs = parseSection(line.substring(leftStart + 1, leftEnd));
                List<String> outputs = parseSection(line.substring(rightStart + 1, rightEnd));
                recipes.add(new RecipeLine(map, inputs, outputs));
            }
        }
        return recipes;
    }

    private List<String> parseSection(String section) {
        List<String> keys = new ArrayList<>();
        for (String token : section.split("\\|", -1)) {
            token = token.trim();
            if (token.isEmpty() || token.equals("-")) continue;
            if (token.startsWith("item:")) {
                String key = itemKey(token);
                if (key != null) keys.add(key);
            } else if (token.startsWith("fluid:")) {
                String[] parts = token.substring("fluid:".length()).split(";", -1);
                if (parts.length >= 1 && !parts[0].isEmpty()) keys.add("fluid:" + parts[0]);
            } else if (token.startsWith("any:")) {
                for (String alternative : token.substring("any:".length()).split("~", -1)) {
                    String key = itemKey(alternative);
                    if (key != null) keys.add(key);
                }
            }
        }
        return keys;
    }

    private String itemKey(String token) {
        if (!token.startsWith("item:")) return null;
        String[] parts = token.substring("item:".length()).split(";", -1);
        if (parts.length < 3 || parts[0].isEmpty() || "0".equals(parts[2])) return null;
        return "item:" + parts[0] + ";" + parts[1];
    }

    private void writeResults(Analysis analysis) throws IOException {
        List<String> resultLines = new ArrayList<>();
        for (RecipeLine recipe : analysis.recipes) {
            Set<String> heads = new LinkedHashSet<>();
            for (String input : recipe.inputs) if (analysis.heads.contains(input)) heads.add(input);
            Set<String> tails = new LinkedHashSet<>();
            for (String output : recipe.outputs) if (analysis.tails.contains(output)) tails.add(output);
            resultLines.add("@(" + recipe.map + ")\t[" + join(heads) + "] -> [" + join(tails) + "]");
        }
        Collections.sort(resultLines);

        try (BufferedWriter writer = Files.newBufferedWriter(new File(RESULT_FILE).toPath(), StandardCharsets.UTF_8)) {
            writer.write("# recipe heads/tails v2 - global endpoints only");
            writer.newLine();
            writer.write("# each line: @(map)\\t[direct inputs that are global raw materials] -> [direct outputs that are global final products]");
            writer.newLine();
            for (String line : resultLines) {
                writer.write(line);
                writer.newLine();
            }
            writer.newLine();
            writer.write("# recipes=" + analysis.recipes.size()
                    + ";heads=" + analysis.heads.size()
                    + ";tails=" + analysis.tails.size());
            writer.newLine();
        }

        writeKeyList(HEADS_FILE, analysis.heads, "# global head materials (inputs never produced by any recipe)");
        writeKeyList(TAILS_FILE, analysis.tails, "# global tail products (outputs never consumed by any recipe)");
    }

    private void writeKeyList(String path, Set<String> keys, String comment) throws IOException {
        List<String> sorted = new ArrayList<>(keys);
        Collections.sort(sorted);
        try (BufferedWriter writer = Files.newBufferedWriter(new File(path).toPath(), StandardCharsets.UTF_8)) {
            writer.write(comment);
            writer.newLine();
            for (String key : sorted) {
                writer.write(key);
                writer.newLine();
            }
        }
    }

    private String join(Set<String> keys) {
        List<String> sorted = new ArrayList<>(keys);
        Collections.sort(sorted);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < sorted.size(); i++) {
            if (i > 0) builder.append('|');
            builder.append(sorted.get(i));
        }
        return builder.toString();
    }

    private static class RecipeLine {
        final String map;
        final List<String> inputs;
        final List<String> outputs;

        RecipeLine(String map, List<String> inputs, List<String> outputs) {
            this.map = map;
            this.inputs = inputs;
            this.outputs = outputs;
        }
    }

    private static class Analysis {
        final List<RecipeLine> recipes;
        final Set<String> heads = new LinkedHashSet<>();
        final Set<String> tails = new LinkedHashSet<>();

        private final Set<String> inputKeys = new HashSet<>();
        private final Set<String> outputKeys = new HashSet<>();
        private final Set<String> producedKeys = new HashSet<>();
        private final Set<String> consumedKeys = new HashSet<>();
        private final Set<String> producedRegistries = new HashSet<>();
        private final Set<String> consumedWildcards = new HashSet<>();

        Analysis(List<RecipeLine> recipes) {
            this.recipes = recipes;
            for (RecipeLine recipe : recipes) {
                for (String input : recipe.inputs) {
                    inputKeys.add(input);
                    addConsumed(input);
                }
                for (String output : recipe.outputs) {
                    outputKeys.add(output);
                    producedKeys.add(output);
                    String registry = registryOf(output);
                    if (registry != null) producedRegistries.add(registry);
                }
            }
            for (String input : inputKeys) if (!isProduced(input)) heads.add(input);
            for (String output : outputKeys) if (!isConsumed(output)) tails.add(output);
        }

        private void addConsumed(String key) {
            String registry = registryOf(key);
            if (key.startsWith("item:") && key.endsWith(";32767") && registry != null) {
                consumedWildcards.add(registry);
            } else {
                consumedKeys.add(key);
            }
        }

        private boolean isProduced(String key) {
            if (producedKeys.contains(key)) return true;
            String registry = registryOf(key);
            return key.startsWith("item:") && key.endsWith(";32767") && registry != null && producedRegistries.contains(registry);
        }

        private boolean isConsumed(String key) {
            if (consumedKeys.contains(key)) return true;
            String registry = registryOf(key);
            return key.startsWith("item:") && registry != null && consumedWildcards.contains(registry);
        }

        private String registryOf(String key) {
            if (!key.startsWith("item:")) return null;
            int semicolon = key.indexOf(';', "item:".length());
            return semicolon < 0 ? null : key.substring("item:".length(), semicolon);
        }
    }
}
