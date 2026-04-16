package cn.kuzuanpa.ktfruaddon.api.research;

import cn.kuzuanpa.ktfruaddon.api.code.ItemType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResearchItemManager {
    public static Map<Byte,Map<Short, List<ItemType>>> data = new HashMap<>();
    @Nullable
    public static List<ItemType> getItems(byte treeID, short researchItemID){
        if (data.get(treeID) == null)return null;
        return data.get(treeID).get(researchItemID);
    }

    public static void addItemData(byte treeID, short researchItemID, ItemType stack){
        data.putIfAbsent(treeID, new HashMap<>());
        Map<Short, List<ItemType>> tree = data.get(treeID);
        tree.putIfAbsent(researchItemID,new ArrayList<>());
        if(!tree.get(researchItemID).contains(stack)) tree.get(researchItemID).add(stack);
    }
}
