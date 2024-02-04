/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.code.upgradeQuest;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class upgradeQuests {
    public static void main(String[] args) throws IOException {
            Path template = Paths.get("template.lang");
            ArrayList<Path> oldLang = new ArrayList<>();
            Files.list(Paths.get("oldLang")).forEach(oldLang::add);
            Path newDir = Paths.get("newLang");
            try {
                Files.createDirectory(newDir);
            }catch (FileAlreadyExistsException e){
                System.out.println("WARN: New Lang Dir already exists, Unexpected behavior may happen");
            }
            oldLang.forEach(path -> {
                try {
                    PrintWriter writer = new PrintWriter(Files.newOutputStream(new File(newDir.toString(), String.valueOf(path.getFileName())).toPath()));
                    HashMap<String,String> keyToLangMap = new HashMap<>();
                    Files.readAllLines(path).forEach(line -> {
                        String[] tmp = line.split("=",2);
                        if(tmp.length==1) keyToLangMap.put(tmp[0],"");
                        else if(tmp.length==2) keyToLangMap.put(tmp[0],tmp[1]);
                    });
                    Files.readAllLines(template).forEach(line->{
                        try {
                            if (line.split("=").length != 2) {writer.print(line+"\n");return;}
                            String key = line.split("=")[1];
                            String content=keyToLangMap.get(key);
                            if(content==null)content="";
                            String newline = line.replaceAll(key, content);
                            writer.print(newline+"\n");
                        }catch (Throwable t){t.printStackTrace();}
                    });
                    writer.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
    }
}
