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
package cn.kuzuanpa.ktfruaddon.api.tile.computerCluster;

import cn.kuzuanpa.ktfruaddon.api.code.SingleEntry;
import cn.kuzuanpa.ktfruaddon.api.code.codeUtil;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import gregapi.data.LH;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ComputePower {
    Normal(0x33cce5), Biology(0x99e533), Quantum(0xb233cc), Spacetime(0x334ce5);

    public final int color;
    ComputePower(int color){
        this.color = color;
    }
    public static ComputePower getType(int ordinal){
        switch (ordinal){
            case 0:return Normal;
            case 1:return Biology;
            case 2:return Quantum;
            case 3:return Spacetime;
        }
        return Normal;
    }
    public Map.Entry<ComputePower, Long> asEntry(long amount){
        return new SingleEntry<>(this, amount);
    }
    public Map<ComputePower, Long> asMap(long amount){
        Map<ComputePower, Long> map = new HashMap<>();
        map.put(this, amount);
        return map;
    }
    public String desc(long amount){
        String name = LH.get(I18nHandler.COMPUTE_POWER+"."+this.ordinal());
        String number = codeUtil.getDisplayShortNum(amount,1);
        return String.format(LH.get(I18nHandler.COMPUTE_POWER_DESC),name, number);
    }
    public String prefixedDesc(long amount){
        return LH.get(I18nHandler.COMPUTE_POWER)+": "+ desc(amount);
    }
    public static List<String> getDesc(Map<ComputePower, Long> map){
        List<String> list = new ArrayList<>();
        list.add(LH.get(I18nHandler.COMPUTE_POWER)+": ");
        map.forEach((k,v)->list.add(" "+k.desc(v)));
        return list;
    }

    public static String getDescOneLine(Map<ComputePower, Long> map){
        StringBuilder sb = new StringBuilder(LH.get(I18nHandler.COMPUTE_POWER)+": ");
        map.forEach((k,v)-> sb.append(k.desc(v)).append(", "));
        return sb.toString();
    }

    public static String getDescOneLine(Map.Entry<ComputePower, Long> entry){
        return LH.get(I18nHandler.COMPUTE_POWER) + ": " + entry.getKey().desc(entry.getValue()) + ", ";
    }
}
