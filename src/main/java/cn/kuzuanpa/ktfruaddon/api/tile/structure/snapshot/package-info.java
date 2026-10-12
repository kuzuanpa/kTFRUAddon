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
 * <h1>结构快照检查 API（骨架）</h1>
 *
 * <p>目标：把"结构检测"的主线程开销变成<b>有界、可预算</b>的固定工作，与上层实现的算法复杂度无关。
 * 上层实现可以慢、可以笨（那是它的责任），但不能再让主线程为它买单，也不能再让它在错误的线程上碰世界。</p>
 *
 * <h2>三个线程域</h2>
 * <ol>
 *   <li><b>主线程（服务端）</b>：{@link cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureScheduler#request}
 *       只需要入队（O(1)）；真正的工作由 tick 里的预算阶段完成。
 *       <br>快照阶段（读区块、复制数组、编码部件 key）与应用阶段（写回 design/usage/target）都在这里，但都<b>受每 tick 预算限制</b>，超预算就跨 tick 续做。</li>
 *   <li><b>worker 线程</b>：只执行 {@link cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.IStructureMachine#check}。
 *       它拿到的只有四个句柄：{@link cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureView}、
 *       {@link cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureParams}、
 *       {@link cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureScratch}、
 *       {@link cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureChangeList}。
 *       <b>API 不会把 World / TileEntity / Entity / IInventory 交给它</b>，所以"在主线程外碰世界"从"需要自觉"变成"写不出来"。
 *       可以慢、可以多开线程，但请控制分配——GC 停顿是打在主线程上的。</li>
 *   <li><b>应用回调</b>：{@link cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.IStructureMachine#applyChange}
 *       与 {@link cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.IStructureMachine#onCheckFinished} 只在主线程被调用。</li>
 * </ol>
 *
 * <h2>结果三态</h2>
 * <p>{@link cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureStatus#FORM} /
 * {@link cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureStatus#NOT_FORM} /
 * {@link cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureStatus#DEFER}。
 * 数据不全（未加载区块、预算截断、异常）时应返回 DEFER：保持不变、稍后重试，而不是硬判"不成形"。
 * DEFER 的变更是<b>丢弃</b>的；FORM / NOT_FORM 的变更都会按 diff 应用（与现在边走边 setTarget 的语义一致）。</p>
 *
 * <h2>未知区域：由机器决定，不由 API 短路</h2>
 * <p>未加载区块在快照里表现为 {@link cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureStatus#UNLOADED}，
 * 且未知性是<b>区块级</b>的，所以视图提供区块位图（
 * {@link cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureView#subBoxHasUnknown} /
 * {@link cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureView#collectUnknownChunks}），
 * 让机器用 O(区块数) 判断"我真正需要的区域是否被未知覆盖"：</p>
 * <ul>
 *   <li>墙可能在未加载区 → 机器自己返回 NOT_FORM（或 DEFER）。</li>
 *   <li>只是检测范围画进了未加载区、结构并不在那里 → 机器忽略未知区域，照常判定。</li>
 * </ul>
 * <p><b>API 只报告，不代为决策。</b></p>
 *
 * <h2>禁止事项（会破坏契约）</h2>
 * <ul>
 *   <li>{@code check()} 里碰任何 Minecraft 世界状态、网络、或机器自身的活字段（facing/尺寸请在
 *       {@link cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.IStructureMachine#freezeParams()} 里冻结）。</li>
 *   <li>{@code check()} 里保留对 {@code out} 变更列表的引用（返回后它归调度器所有）。</li>
 *   <li>{@code check()} 里抛异常（会被记日志并降级为 DEFER，但请自己处理边界）。</li>
 *   <li>在 {@code applyChange()} 之外的地方调 {@code part.setTarget(...)} / {@code utils.setTarget(...)}。</li>
 * </ul>
 */
package cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot;
