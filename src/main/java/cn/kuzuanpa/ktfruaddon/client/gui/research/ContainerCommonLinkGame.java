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

package cn.kuzuanpa.ktfruaddon.client.gui.research;

import cn.kuzuanpa.ktfruaddon.tile.research.ResearchTableLinkGame;
import gregapi.gui.ContainerCommonDefault;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerCommonLinkGame extends ContainerCommonDefault {

    public final ResearchTableLinkGame mTile;

    public ContainerCommonLinkGame(EntityPlayer aPlayer, ResearchTableLinkGame aTile, int aGUIID) {
        super(aPlayer.inventory, aTile, aGUIID);
        this.mTile = aTile;
    }

    @Override public boolean doesBindPlayerInventory() {return false;}
    @Override public int getStartIndex() {return 0;}
    @Override public int getSlotCount() {return 0;}
    @Override public int getShiftClickStartIndex() {return 0;}
    @Override public int getShiftClickSlotCount() {return 0;}
}
