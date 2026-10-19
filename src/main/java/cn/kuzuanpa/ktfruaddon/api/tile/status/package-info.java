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
 * <h1>机器状态与停机原因 API</h1>
 *
 * <p>运行态与停机原因合并到同一个接口 {@link cn.kuzuanpa.ktfruaddon.api.tile.status.IMachineStatus}：
 * 它们问的是同一件事——机器现在处于什么状态。取值范围见 {@link cn.kuzuanpa.ktfruaddon.api.tile.status.MachineStatus}，
 * 输出见 {@link cn.kuzuanpa.ktfruaddon.api.tile.status.MachineStatusReport}。</p>
 *
 * <h2>机器要写的东西</h2>
 * <ul>
 *   <li>运行态：只覆写自己有意义的级别，其余逐级向下兜底（possible → running → processing）；
 *       {@code isSuccess()} 默认 false，需要 Success 检测器的机器自己覆写。</li>
 *   <li>停机原因：<b>不用自己写</b>，只在机器特有的阻塞处覆写 {@code getStatusOverride()} / {@code getStatusDetail()}。</li>
 *   <li>配方：机器有 kortex 时把它报出来（{@code getKortexWorkers()}），状态 API 就能读到配方层面的原因。</li>
 * </ul>
 *
 * <h2>内置来源与优先级</h2>
 * <p>机器覆写 → 开关关闭 → 结构 / 房间 / 条件部件 → 算力 → kortex → 运行态 → 未知。
 * 运行态按 processing → {@code RUNNING}、running → {@code STANDBY}、possible → {@code READY} 依次判定。
 * 未知时 {@code renderStatus()} 返回空串、{@code renderLines()} 什么都不加，显示层可以无条件调用。</p>
 *
 * <ul>
 *   <li>开关：GT 的 {@code ITileEntitySwitchableOnOff}，关闭即「被强制停机」。</li>
 *   <li>结构：{@code IStringStructureMachine.isStructureOkay()}，其余多方块读 GT 缓存的
 *       {@code ITileEntityMultiBlockController.checkStructure(false)}；失败坐标属于机器私有字段，需要时用
 *       {@code getStatusDetail()} 带出来。</li>
 *   <li>房间：{@code TileEntityBaseRoom.getRoomContext() == null}，即房间壳子成形却没能登记进 RoomManager
 *       （被一个重叠的房间先占了位）。{@code isRoomActive()} 说的是「房间功能有没有升起来」，不当校验用。</li>
 *   <li>条件部件：{@code TileEntityBaseControlledMachine.isControllerAllValid()}。</li>
 *   <li>算力：{@code ICircuitChangeableTileEntity.getComputeUserComponent().getStatus()}。</li>
 *   <li>配方：{@code getKortexWorkers()} 里的 kortex 状态；多台配方单元时优先报第一个在拦人的。</li>
 * </ul>
 *
 * <p>{@code IMachineStatus} 本身继承 GT 的 {@code ITileEntityRunningSuccessfully} 与
 * {@code ITileEntityRunningPowerSaving}，并把五个 GT getter 默认桥接到上面的布尔值。
 * 机器只实现一次 {@code IMachineStatus}，GT 的活动检测器 / 显示面板 / WAILA 状态行就直接认得，
 * 不需要再实现一套运行接口。</p>
 *
 * <p>A 系基类（{@code MultiTileEntityBasicMachine} 等）已经有 GT 运行接口的具体实现，
 * 父类方法会压过 {@code IMachineStatus} 的桥接默认值；这类机器沿用父类运行态，只补
 * {@code getStatusOverride()} / {@code getStatusDetail()} 即可。要让自定义布尔值生效，
 * 需要在机器类里覆写对应的 GT 方法转调过去。</p>
 *
 * <h2>显示出口</h2>
 * <ul>
 *   <li>放大镜 / 单行显示：{@code MachineControl.of(this).renderStatus()}（主原因 + 明细，未知时为空串）</li>
 *   <li>GUI / 聊天栏多行：{@code MachineControl.of(this).getStatusReport().renderLines(aList)}</li>
 *   <li>WAILA：在 {@code getWailaNBT} 里把状态写进 NBT，客户端 {@code getWailaBody} 再读；
 *       不要直接在 body 里读服务器字段。</li>
 *   <li>手持鲨鱼/鱼右键：{@code MachineControl.onFishRightClick(this, aPlayer)}</li>
 * </ul>
 *
 * <h2>i18n</h2>
 * <p>文案 key 为 {@code ktfru.text.machine.status.<小写枚举名>}，标签是 {@code ktfru.text.machine.status}。
 * kortex 旧 key {@code ktfru.text.kortex.state.*} 保留为别名，待确认无引用后再收敛。</p>
 */
package cn.kuzuanpa.ktfruaddon.api.tile.status;
