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

package cn.kuzuanpa.ktfruaddon.api.code;

public class StateMgr {
    protected byte state=0;
    protected boolean isStateChanged = false;
    public StateMgr(int state){this.state = (byte)state;}
    public StateMgr(){}

    public void set(int state){
        if(this.state==state)return;
        isStateChanged = true;
        this.state = (byte) state;
    }
    public byte get(){
        return state;
    }
    public byte getAndClear(){
        isStateChanged = false;
        return state;
    }
    public void markChanged(){
        isStateChanged = true;
    }
    public boolean isChangedAndClear(){
        boolean result = isStateChanged;
        isStateChanged = false;
        return result;
    }
    public boolean is(byte state){
        return this.state == state;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StateMgr && state == ((StateMgr) obj).get();
    }
}
