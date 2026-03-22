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

package cn.kuzuanpa.ktfruaddon.DreamPlanner.client.gui;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;

public class util {
    public static RenderItem itemRender = new RenderItem();

    public static void drawTexturedModalRect(int x, int y, int z, int u, int v, int w, int h) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((x + 0), (y + h), z, ((float)(u + 0) * f), ((float)(v + h) * f1));
        tessellator.addVertexWithUV((x + w), (y + h), z, ((float)(u + w) * f), ((float)(v + h) * f1));
        tessellator.addVertexWithUV((x + w), (y + 0), z, ((float)(u + w) * f), ((float)(v + 0) * f1));
        tessellator.addVertexWithUV((x + 0), (y + 0), z, ((float)(u + 0) * f), ((float)(v + 0) * f1));
        tessellator.draw();
    }
}
