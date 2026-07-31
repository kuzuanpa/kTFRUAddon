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
package cn.kuzuanpa.ktfruaddon;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ICrashCallable;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnvironmentHelper {
    public static boolean isInTFRU = false, isGregtechTFRU = false, isAdvancedRocketryTFRU = false, isTFCTFRU = false, isBotaniaTFRU = false, isDraconicEvolutionTFRU = false, isForestryTFRU = false;

    public static String checkedTFRUVer = null, TFRUVer = "4.3.0.0";
    public static List<String> changelog = new ArrayList<>();
    public static void updateTFRUEnvironment(FMLPreInitializationEvent event){
        try {if(                         gregtech.TFRUEnvHelper.isModTFRU)isGregtechTFRU          = true;}catch (Throwable e){isGregtechTFRU          = false;}
        try {if(      zmaster587.advancedRocketry.TFRUEnvHelper.isModTFRU)isAdvancedRocketryTFRU  = true;}catch (Throwable e){isAdvancedRocketryTFRU  = false;}
        try {if(                com.bioxx.tfc.api.TFRUEnvHelper.isModTFRU)isTFCTFRU               = true;}catch (Throwable e){isTFCTFRU               = false;}
        try {if(            vazkii.botania.common.TFRUEnvHelper.isModTFRU)isBotaniaTFRU           = true;}catch (Throwable e){isBotaniaTFRU           = false;}
        try {if(com.brandon3055.draconicevolution.TFRUEnvHelper.isModTFRU)isDraconicEvolutionTFRU = true;}catch (Throwable e){isDraconicEvolutionTFRU = false;}
        try {if(                         forestry.TFRUEnvHelper.isModTFRU)isForestryTFRU          = true;}catch (Throwable e){isForestryTFRU          = false;}

        isInTFRU = isGregtechTFRU&&isAdvancedRocketryTFRU&&isTFCTFRU&&isBotaniaTFRU&&isDraconicEvolutionTFRU&&isForestryTFRU&&Loader.isModLoaded("tfc-mixin");

        if(!isInTFRU)return;
        try {                        gregtech.TFRUEnvHelper.isInTFRU=true;
                  zmaster587.advancedRocketry.TFRUEnvHelper.isInTFRU=true;
                            com.bioxx.tfc.api.TFRUEnvHelper.isInTFRU=true;
                        vazkii.botania.common.TFRUEnvHelper.isInTFRU=true;
            com.brandon3055.draconicevolution.TFRUEnvHelper.isInTFRU=true;
                                     forestry.TFRUEnvHelper.isInTFRU=true;
        }catch (Throwable e){
            FMLLog.log(Level.FATAL,e,"Error Occured when setting up TFRU pack environment! are you using right version of TFRU mods?");
            isInTFRU = false;
            return;
        }

        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        TFRUVer = config.getString("TFRUVer", "main", "0.0.0.0", "Version of TFRU Modpack");
        config.save();

        new Thread(new updateChecker()).start();
        new Thread(new TFRUValidator()).start();
    }

    public static class updateChecker implements Runnable{
        private static final String USER_AGENT = "Mozilla/5.0";
        private static final String MODDRINTH_URL = "https://api.modrinth.com/v2/project/o0CuW5i0/version?featured=true";
        @Override
        public void run() {
            //FMLLog.log(Level.FATAL, Paths.get(".").toString());
            try {
                URL obj = new URL(MODDRINTH_URL);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", USER_AGENT);

                int responseCode = con.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) throw new ConnectException("GET Error:"+responseCode);

                //Success:
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                processResponse(response.toString());
            }catch (Exception e) {
                try {
                    Files.write(Paths.get("resources/mainmenu/textures/version.txt"), "Error".getBytes());
                } catch (IOException ex) {FMLLog.log(Level.ERROR, "Error checking TFRU version: ",ex);}
                changelog.clear();
                changelog.add("获取changeLog失败: 网络连接异常.");

            }
        }
        public static void processResponse(String jsonResponse) {
            try {
                JsonArray jsonArray = new JsonParser().parse(jsonResponse).getAsJsonArray();
                if (jsonArray.size() <= 0) return;
                JsonObject firstObject = jsonArray.get(0).getAsJsonObject();
                JsonElement versionNumberElement = firstObject.get("version_number");
                if (versionNumberElement != null && !versionNumberElement.isJsonNull()) checkedTFRUVer = versionNumberElement.getAsString();
                Files.write(Paths.get("resources/mainmenu/textures/version.txt"), checkedTFRUVer.getBytes());
                if(!TFRUVer.equalsIgnoreCase(checkedTFRUVer)){
                    boolean foundCurrentVersion = false;
                    for (JsonElement jsonElement : jsonArray) {
                        JsonObject obj = jsonElement.getAsJsonObject();
                        if(obj.get("version_number").getAsString().equalsIgnoreCase(TFRUVer)){
                            foundCurrentVersion=true;
                            break;
                        }
                        changelog.add(obj.get("version_number").getAsString());
                        String singleLog = obj.get("changelog").getAsString().replaceAll("\n *\n","\n");
                        changelog.addAll(Arrays.asList(singleLog.split("\n")));
                    }
                    if(!foundCurrentVersion){
                        changelog.clear();
                        //no i18n, because changeLog itself is pure chinese
                        changelog.add("获取changeLog失败: 无法找到当前版本.");
                    }
                }
            }catch (Exception e){
                changelog.clear();
                changelog.add("获取changeLog失败: java抛出错误:\n"+e.getMessage());
                FMLLog.log(Level.ERROR, "Error checking TFRU version: ",e);
            }
            changelog.forEach(System.out::println);
        }
    }

    public static class TFRUValidator implements Runnable{
        public static final byte STATE_INIT= 0, STATE_VALIDATING = 1, STATE_PASSED =2, STATE_ERRORED=3, STATE_MISMATCH=4, STATE_SKIPPED=5;
        public static byte state = STATE_INIT;
        public static List<String> mismatchedFiles = new ArrayList<>();
        public static String totalStringOfMismatchedFile = "";
        public static final List<String> excludedPaths = Arrays.asList("InvTweaks.cfg","splash.properties","carpentersblocks/CarpentersBlocksCachedResources.zip");//they changed every launch
        @Override
        public void run() {
            try {
                if(Files.exists(Paths.get("validate/SkipCheck"))){
                    state=STATE_SKIPPED;
                    return;
                }
                state=STATE_VALIDATING;

                for (String dir : Arrays.asList("config","docs","ideas","resources","scripts","mods")){
                    Map<String, String> map = walkPathSHA1(dir);
                    List<String> list = validateFiles("validate/TFRU-validate-info-"+dir,map, !dir.equals("config"));
                    mismatchedFiles.addAll(list.stream().map(str-> dir+" "+str).collect(Collectors.toList()));
                }
                state = mismatchedFiles.isEmpty()?STATE_PASSED :STATE_MISMATCH;

                if(mismatchedFiles.isEmpty())return;

                StringBuilder sb = new StringBuilder();

                mismatchedFiles.forEach(str->sb.append("    ").append(str).append("\n"));
                totalStringOfMismatchedFile = sb.toString();

                FMLLog.log(Level.WARN,"TFRU Modified: \n"+ totalStringOfMismatchedFile);
            }catch (Exception e) {
                FMLLog.log(Level.ERROR,"[TFRUValidator] Error: " + e.getMessage());
                state = STATE_ERRORED;
            }
        }

        /**
         * 递归计算目录下所有文件的SHA1值
         * @param path 目录路径
         * @return Map<相对路径, SHA1值>
         */
        public static Map<String, String> walkPathSHA1(String path) {
            Map<String, String> sha1Map = new HashMap<>();
            Path basePath = Paths.get(path).toAbsolutePath().normalize();

            try(Stream<Path> pathStream = Files.walk(basePath)){
                pathStream.filter(Files::isRegularFile).forEach(filePath -> {
                    try {
                        String relativePath = basePath.relativize(filePath).toString();
                        String sha1 = calculateSHA1(filePath);
                        sha1Map.put(relativePath, sha1);
                    }
                    catch (Exception e) {
                        FMLLog.log(Level.ERROR,"[TFRUValidator] Error processing file: " + filePath + " - " + e.getMessage());
                    }
                });
            }catch (IOException e){
                FMLLog.log(Level.ERROR,"[TFRUValidator] Error: " + e.getMessage());
            }

            return sha1Map;
        }

        /**
         * 计算单个文件的SHA1值
         */
        private static String calculateSHA1(Path filePath) throws IOException, NoSuchAlgorithmException {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            try (InputStream fis = Files.newInputStream(filePath);
                 BufferedInputStream bis = new BufferedInputStream(fis)) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = bis.read(buffer)) != -1) {
                    digest.update(buffer, 0, count);
                }
            }

            byte[] sha1Bytes = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : sha1Bytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        }

        /**
         * 加载验证文件并比对
         * @param infoFilePath 验证文件路径
         * @param sha1Map 计算的SHA1 Map
         * @return 不符合的文件路径列表
         */
        public static List<String> validateFiles(String infoFilePath, Map<String, String> sha1Map, boolean detectMoreFiles) {
            List<String> mismatchedFiles = new ArrayList<>();
            Path validatePath = Paths.get(infoFilePath);

            if (!Files.exists(validatePath)) {
                FMLLog.log(Level.ERROR,"[TFRUValidator] Validation file not found");
                return Collections.emptyList();
            }

            try (BufferedReader reader = Files.newBufferedReader(validatePath)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) {
                        continue; // 跳过空行和注释
                    }

                    // 解析格式：'相对路径' > 'sha1值'
                    String[] parts = line.split("\\s*>\\s*");
                    if (parts.length == 2) {
                        String fileName = parts[0].replaceAll("^'|'$", "").trim();
                        Path filePath = Paths.get(fileName);
                        String expectedSHA1 = parts[1].replaceAll("^'|'$", "").trim();

                        if(excludedPaths.stream().anyMatch(str->Paths.get(str).equals(filePath))){
                            sha1Map.remove(fileName);
                            continue;
                        }

                        String actualSHA1 = sha1Map.get(fileName);
                        if (actualSHA1 == null) {
                            mismatchedFiles.add("- "+filePath);
                        } else if (!actualSHA1.equalsIgnoreCase(expectedSHA1)) {
                            mismatchedFiles.add("x " + filePath);
                        }
                        sha1Map.remove(fileName);
                    }
                }
            }catch (IOException e){
                FMLLog.log(Level.ERROR,"[TFRUValidator] Error: " + e.getMessage());

            }
            if(detectMoreFiles)sha1Map.forEach((str,sha1)-> mismatchedFiles.add("+ "+str));
            return mismatchedFiles;
        }

        public static class TFRUValidatorCallable implements ICrashCallable {
            public TFRUValidatorCallable() {
            }

            public String call() throws Exception {
                return state == STATE_SKIPPED? "TFRU Validation Skipped": state == STATE_PASSED ?"TFRU Validated, please report bug to TFRU developer" : "TFRU Modified, consider bug of modification:\n" + totalStringOfMismatchedFile;
            }

            public String getLabel() {
                return "TFRUValidator";
            }
        }
    }
}
