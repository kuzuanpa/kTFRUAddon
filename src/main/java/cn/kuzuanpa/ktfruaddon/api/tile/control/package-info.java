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

/**
 * <h1>机器控制面：直接用 GT 接口</h1>
 *
 * <p>开关、模式、进度、电量不再另立 addon 接口，机器直接实现 GT 的对应接口。
 * 这样 GT 覆盖板、扫描器、WAILA 和我们的显示层读的是同一份数据，不存在两套实现或优先级。</p>
 *
 * <table border="1">
 *   <tr><th>能力</th><th>GT 接口</th><th>方法</th></tr>
 *   <tr><td>开关</td><td>ITileEntitySwitchableOnOff</td><td>getStateOnOff / setStateOnOff；要往邻居传播时再加 ITileEntityAdjacentOnOff</td></tr>
 *   <tr><td>模式</td><td>ITileEntitySwitchableMode</td><td>getStateMode / setStateMode</td></tr>
 *   <tr><td>进度</td><td>ITileEntityProgress</td><td>getProgressValue / getProgressMax</td></tr>
 *   <tr><td>电量</td><td>ITileEntityEnergyDataCapacitor</td><td>stored / capacity / isEnergyCapacitorType / getEnergyCapacitorTypes</td></tr>
 *   <tr><td>运行态</td><td>ITileEntityRunningSuccessfully + ITileEntityRunningPowerSaving</td><td>由 api.tile.status.IMachineStatus 自动桥接</td></tr>
 *   <tr><td>电表</td><td>无</td><td>IMachineMeter 只补读数来源</td></tr>
 * </table>
 *
 * <p>电表是唯一例外：GT 的 {@code IMeterDetectable} 只有静态格式化方法，没有读数 getter，
 * 所以保留 {@link cn.kuzuanpa.ktfruaddon.api.tile.control.IMachineMeter} 的
 * {@code getMeterReceived} / {@code getMeterEmitted}；它不参与覆盖板判定。</p>
 *
 * <h2>查询入口 {@link cn.kuzuanpa.ktfruaddon.api.tile.control.MachineControl}</h2>
 * <p>解析只有两步：机器自己就是目标；是多方块部件则回溯到目标控制器。
 * 两步都落空就返回空控制，{@code getTileEntity() == null}、所有查询取安全默认值，调用方不用判 null。
 * {@code has()} 只服务我们自己的显示层，判断的也是 GT 接口。</p>
 *
 * <h2>部件</h2>
 * <p>{@code IMultiBlockPart} 已继承 GT 的运行态 / 开关 / 模式 / 进度 / 电量接口，并把查询默认转发到
 * {@code getTarget(false)}；GT 覆盖板查询的是部件本身，所以部件必须是这些接口的实例，光靠
 * {@code MachineControl.of()} 回溯不够。{@code CommonMachinePart} 的父类有同名具体方法，
 * 已在类里显式覆写，新增同类部件时注意这一点。</p>
 *
 * <h2>开关/模式记账 {@link cn.kuzuanpa.ktfruaddon.api.tile.control.MachineControlComponent}</h2>
 * <p>组件负责 {@code mSwitchedOn} / {@code mMode}、NBT 持久化（沿用 GT 的 {@code NBT_STOPPED} 语义）、
 * 客户端回调与相邻 {@code ITileEntityAdjacentOnOff} 联动。它不实现任何接口：
 * 机器实现 GT 接口时把 {@code getStateOnOff} / {@code setStateOnOff} / {@code getStateMode} /
 * {@code setStateMode} 转调给它即可。</p>
 *
 * <h2>机器接入（一台机器的改动量）</h2>
 * <ol>
 *   <li>按需 {@code implements} GT 的开关 / 模式 / 进度 / 电量接口；</li>
 *   <li>加一个字段 {@code public final MachineControlComponent control = new MachineControlComponent(this, this::updateClientData);}
 *       并把 GT 的开关/模式方法委托过去；</li>
 *   <li>运行态与停机原因 {@code implements IMachineStatus}（它已经继承 GT 运行接口，不用再实现一套）；</li>
 *   <li>只在机器特有的阻塞处覆写 {@code IMachineStatus.getStatusOverride()} / {@code getStatusDetail()}；</li>
 *   <li>显示：放大镜、鱼右键用 {@code renderLines}；WAILA 经 {@code getWailaNBT} 传状态，
 *       不要在客户端 body 里直接读 {@code MachineControl.of(this)}。</li>
 *   <li>电表：{@code implements IMachineMeter}，在 {@code onToolClick2} 里调
 *       {@code MachineControl.of(this).handleMeterTool(aTool, aChatReturn)}。</li>
 * </ol>
 */
package cn.kuzuanpa.ktfruaddon.api.tile.control;
