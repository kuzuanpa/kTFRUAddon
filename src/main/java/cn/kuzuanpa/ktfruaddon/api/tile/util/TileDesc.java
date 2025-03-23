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

package cn.kuzuanpa.ktfruaddon.api.tile.util;

import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;

public class TileDesc {
    public short aRegistryMeta;
    public int aRegistryID;
    public int aDesign;
    public int aUsage;

    public TileDesc(int aRegistryID, short aRegistryMeta, int aUsage, int aDesign) {
        this.aDesign = aDesign;
        this.aRegistryID = aRegistryID;
        this.aRegistryMeta = aRegistryMeta;
        this.aUsage = aUsage;
    }

    public TileDesc(int aRegistryID, int aRegistryMeta, int aUsage, int aDesign) {
        this(aRegistryID, (short) aRegistryMeta, aUsage, aDesign);
    }

    public TileDesc(int aRegistryID, int aRegistryMeta, int aUsage) {
        this(aRegistryID, (short) aRegistryMeta, aUsage, 0);
    }

    public TileDesc(int aRegistryID, int aRegistryMeta) {
        this(aRegistryID, (short) aRegistryMeta, MultiTileEntityMultiBlockPart.NOTHING, 0);
    }

    @Override
    public String toString() {
        return aRegistryID+":"+aRegistryMeta;
    }
}
