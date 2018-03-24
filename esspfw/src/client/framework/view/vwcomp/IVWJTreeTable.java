package client.framework.view.vwcomp;

import c2s.dto.ITreeNode;
import client.framework.common.treeTable.TreeTableModel;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IVWJTreeTable {
    TreeTableModel getTreeTableModel();

    /**
     * 事件监听
     */
    void addListener(IVWJTreeTableListener listener);

    void removeListener(IVWJTreeTableListener listener);

    void removeAllListener();

    //取得第row行的节点
    ITreeNode getNodeForRow(int row);

    //取得当前被选中的节点，如果没有被选中的，返回null。
    ITreeNode getSelectedNode();

    //取得当前被选中的行的行号
    int getSelectedRow();

    /**
     *-------------移动操作------------------------------------
     *
     *对节点作平级上移(upMove)，平级下移(downMove)，越级上移(leftMove)，越级下移(rightMove)。
     *在进行某一移动操作前一般要先判断是否能做移动操作。
     * 判断函数isUpMovable, isDownMovable, isLeftMovable, isRightMovable是
     * 缺省实现。
     */

    /**
     *如果第<code>rowid</code>个节点是叶子节点，那么它可以平级上移，返回true;
     * 否则返回false;
     */
    boolean isUpMovable(int rowid);

    /**
     *将第<code>rowid</code>个节点向上移一个位置, 即将它和它的上面一个兄弟节点调换位置;
     *移动后它和它兄弟的子节点都不变。
     * 下面情况不作移动：
     * 1.它是root节点；
     * 2.它是它父亲的第一个子节点
     */
    void upMove(int rowid);

    /**
     *如果第<code>rowid</code>个节点是叶子节点，那么它可以平级下移，返回true;
     * 否则返回false;
     */
    boolean isDownMovable(int rowid);

    /**
     *将第<code>rowid</code>个节点向下移一个位置, 即将它和它的下面一个兄弟节点调换位置;
     *移动后它和它兄弟的子节点都不变。
     * 下面情况不作移动：
     * 1.它是root节点；
     * 2.它是它父亲的最后一个子节点
     */
    void downMove(int rowid);

    /**
     *如果第<code>rowid</code>个节点是叶子节点，那么它可以平级上移，返回true;
     * 否则返回false;
     */
    boolean isLeftMovable(int rowid);

    /**
     *将第<code>rowid</code>个节点向上移一个位置，移到与它父节点平级，位置在它父节点的下面
     *移动后它的子节点都不变。
     * 下面情况不作移动：
     * 1.它是root节点；
     * 2.它的父节点是root节点
     */
    void leftMove(int rowid);

    /**
     *如果第<code>rowid</code>个节点是叶子节点，那么它可以平级下移，返回true;
     * 否则返回false;
     */
    boolean isRightMovable(int rowid);

    /**
     *将第<code>rowid</code>个节点移到它上兄弟节点的下面，成为它兄弟节点的最后一个子节点。
     *移动后它的子节点都不变。
     * 下面情况不作移动：
     * 1.它是root节点；
     * 2.它的父节点是root节点
     */
    void rightMove(int rowid);

    /**
     * 对 当前选中行 执行 移动操作
     */
    boolean isUpMovable();

    void upMove();

    boolean isDownMovable();

    void downMove();

    boolean isLeftMovable();

    void leftMove();

    boolean isRightMovable();

    void rightMove();

    /*
     * 将<code>objNewNode</code>加到第<code>row</code>行。
     * 假设加入之前第<code>row</code>行是节点nodeOldRow，那么加入后<code>objNewNode</code>
     * 成为nodeOldRow的兄弟节点，位置在nodeOldRow的下一个位子。
     * 新增后，节点<code>objNewNode</code>的子节点不变。
     * 下面情况不作增加：
     * 1.<code>row</code>等于零，   不能用addRow方法增加一个根节点；
     * 2.<code>row</code>大于等于行数
     */
    Object addRow(int row, Object objNewNode);

    /**
     * 在<code>rowIndex</code>新增一默认行。
     * 新增对象 的类型见@see ITreeTableModel, IColumnConfig的getDtoType()
     */
    Object addRow(int rowIndex);

    /**
     * 将第<code>rowIndex</code>行节点删除。
     * 删除后，它和它的子节点都被删除。
     */
    Object deleteRow(int rowIndex);

    Object deleteRows(int[] rowIndexs);
}
