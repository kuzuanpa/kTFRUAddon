/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.material.prefix;

import gregapi.code.ICondition;
import gregapi.code.TagData;
import gregapi.oredict.OreDictMaterial;

import static gregapi.data.CS.F;
import static gregapi.data.CS.T;

public class kOreConditions {
    public static ICondition<OreDictMaterial> tag     (TagData... aTags)      {return new kOreConditions.TagDataContainsAll(aTags);}
    public static ICondition<OreDictMaterial> tagNor  (TagData... aTags)      {return new kOreConditions.TagDataContainsNone(aTags);}
    public static ICondition<OreDictMaterial> tagAny     (TagData... aTags)      {return new kOreConditions.TagDataContainsAny(aTags);}

    private static class TagDataContainsAll implements ICondition<OreDictMaterial> {
        private final TagData[] mTags;
        public TagDataContainsAll(TagData... aTags) {mTags = aTags;}
        @Override public boolean isTrue(OreDictMaterial aPrefix) {return aPrefix.containsAll(mTags);}
    }

    private static class TagDataContainsNone implements ICondition<OreDictMaterial> {
        private final TagData[] mTags;

        public TagDataContainsNone(TagData... aTags) {
            mTags = aTags;
        }

        @Override
        public boolean isTrue(OreDictMaterial aPrefix) {
            for (TagData tTag : mTags) if (aPrefix.contains(tTag)) return F;
            return T;
        }

    }
    private static class TagDataContainsAny implements ICondition<OreDictMaterial> {
        private final TagData[] mTags;
        public TagDataContainsAny(TagData... aTags) {mTags = aTags;}
        @Override public boolean isTrue(OreDictMaterial aPrefix) {return aPrefix.containsAny(mTags);}
    }
}
