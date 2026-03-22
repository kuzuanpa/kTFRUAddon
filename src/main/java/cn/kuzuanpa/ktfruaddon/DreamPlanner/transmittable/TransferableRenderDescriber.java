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

package cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable;

import net.minecraft.item.ItemStack;

public class TransferableRenderDescriber {
    ItemStack stack;
    String name = null;
    String desc = null;
    int height = 0;
    int width = 0;
    public TransferableRenderDescriber(ItemStack stack){
        this.stack=stack;
    }
    public TransferableRenderDescriber setName(String name){
        this.name=name;
        return this;
    }
    public TransferableRenderDescriber setDesc(String desc){
        this.desc=desc;
        return this;
    }
    public TransferableRenderDescriber setHeight(int height){
        this.height=height;
        return this;
    }
    public TransferableRenderDescriber setWidth(int width){
        this.width=width;
        return this;
    }
    public ItemStack getDescribeStack(){
        return stack;
    };
    public String getName(){return stack.getDisplayName();};
    public String getDesc(){return desc==null? "":desc;}
    public int getHeight(){return height>0? height: 32;}
    public int getWidth(){return width>0?width:64;}
}
