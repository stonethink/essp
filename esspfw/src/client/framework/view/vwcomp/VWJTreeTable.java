package client.framework.view.vwcomp;

import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import c2s.dto.ITreeNode;
import client.framework.common.treeTable.JTreeTable;
import client.framework.common.treeTable.TreeTableModel;
import client.framework.model.VMTreeTableModel;
import client.framework.model.VMTreeTableModelAdapter;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.event.RowSelectedLostListener;
import org.apache.log4j.Category;
import c2s.dto.IDto;
import client.framework.model.VMColumnConfig;
import java.awt.Component;
import client.framework.model.IColumnConfig;

/**
 * 	触发行选择的时机:
 *       1.setRoot() / setRows()   触发 RowSelected事件,选择第一行 / 一行都没有时选择-1行
 *       2.addRow()   触发 RowSelectedLost
 *                    触发 RowSelected事件,选择新增的行
 *       3.deleteRow()  触发 RowSelected事件,选择删除行所在的行(也可能是其附近的行) / 一行都没有时选择-1行
 *       4.mouseClick    触发 RowSelectedLost
 *                       触发 RowSelected事件,选择点击的行
 *       5.取消选中行(比如ctrl+鼠标点击)  触发 RowSelectedLost
 *                                     触发 RowSelected事件,选中-1行
 * <p>Title: </p>
 * @url element://model:project::esspfw/jdt:e_class:src:client.framework.model.VMTreeTableModel
 * @url element://model:project::esspfw/jdt:e_class:src:client.framework.view.vwcomp.VWJTreeCellRender
 */
public class VWJTreeTable extends JTreeTable implements IVWJTableViewManager {
    public static final int TREETABLE_ROW_HEIGHT = 18;
    protected Category log = Category.getInstance("client");


    private List rowSelectedListenerList = new ArrayList();
    private List rowSelectedLostListenerList = new ArrayList();
    private List mouseLeftClickListenerList = new ArrayList();
    private List mouseRightClickListenerList = new ArrayList();

    //记录当前选中的node.如果选中同一个node,不会触发rowSelected事件.
    private ITreeNode selectedNode = null;
    private int selectedRow = -1;

    NodeViewManager nodeViewManager;
    JScrollPane scrollPane;

    public VWJTreeTable(TreeTableModel treeTableModel) {
        this(treeTableModel, new NodeViewManager());
    }

    public VWJTreeTable(TreeTableModel treeTableModel,
                        NodeViewManager nodeViewManager) {
        super(treeTableModel);
        this.nodeViewManager = nodeViewManager;

        //为了不修改JTreeTable, 所以下面的代码重写JTreeTable的构造函数
        TreeSelectionModel treeSelectionModel = tree.getSelectionModel();
        ListSelectionModel listSelectionModel = this.getSelectionModel();
        listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Create the tree. It will be used as a renderer and editor.
        tree = new VWJTreeTableCellRenderer(treeTableModel, this);
        if (treeTableModel instanceof VMTreeTableModel) {
            tree.setCellRenderer(
                    new VWJTreeCellRender(nodeViewManager,
                                          ((VMTreeTableModel) treeTableModel).
                                          getTreeColumnName()));
        } else {
            tree.setCellRenderer(new VWJTreeCellRender(nodeViewManager));
        }

        // Install a tableModel representing the visible rows in the tree.
        super.setModel(new VMTreeTableModelAdapter(treeTableModel, tree));

        // Force the JTable and JTree to share their row selection models.
        tree.setSelectionModel(treeSelectionModel);

        // Install the tree editor renderer and editor.
        setDefaultRenderer(TreeTableModel.class, tree);
        VWUtil.setTableRender(this);

        //编辑器类作了调整，所以调用新的方法
        //modify by:Robin.zhang
        VWUtil.setTreeTableEditor(this);

        if (treeTableModel instanceof VMTreeTableModel) {

            for (int i = 0; i < this.getColumnCount(); i++) {
                String columnName = this.getColumnName(i);
                TableColumn column = this.getColumn(columnName);

                //column render
                column.setHeaderRenderer(new VWJTableHeaderRender(((
                        VMTreeTableModel) treeTableModel)));

                //column width
                VMColumnConfig config = ((VMTreeTableModel) treeTableModel).
                                        getColumnConfig(columnName);
                if (config.getPreferWidth() > 0) {
                    column.setPreferredWidth(config.getPreferWidth());
                }
            }
        }

        // And update the height of the trees row to match that of
        // the table.
//        if (tree.getRowHeight() < 1) {
        // Metal looks better like this.
            setRowHeight(TREETABLE_ROW_HEIGHT);
//        }

        tree.setRootVisible(true);
        tree.setShowsRootHandles(true);
        addUICEvent();
    }

    private void addUICEvent() {
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Point point = new Point(e.getX(), e.getY());
                int rowId = rowAtPoint(point);
                if (rowId < 0) {
                    return;
                }
                TreePath selPath = tree.getPathForRow(rowId);
                if (selPath == null) {
                    return;
                }
                ITreeNode currClickNode = (ITreeNode) selPath.
                                          getLastPathComponent();

                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (mouseLeftClickListenerList != null &&
                        mouseLeftClickListenerList.size() > 0) {
                        for (int i = 0; i < mouseLeftClickListenerList.size();
                                     i++) {
                            IVWTreeTableMouseClickListener listener = (
                                    IVWTreeTableMouseClickListener)
                                    mouseLeftClickListenerList.get(i);
                            listener.onMouseClick(e, currClickNode,
                                                  IVWTreeTableMouseClickListener.
                                                  POSITION_TABLE);
                        }
                    }
                } else {
                    if (mouseRightClickListenerList != null &&
                        mouseRightClickListenerList.size() > 0) {
                        for (int i = 0; i < mouseRightClickListenerList.size();
                                     i++) {
                            IVWTreeTableMouseClickListener listener = (
                                    IVWTreeTableMouseClickListener)
                                    mouseRightClickListenerList.get(i);
                            listener.onMouseClick(e, currClickNode,
                                                  IVWTreeTableMouseClickListener.
                                                  POSITION_TABLE);
                        }
                    }

                }
            }
        });

        tree.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selRow = tree.getRowForLocation(e.getX(), e.getY());
                if (selRow < 0) {
                    return;
                }
                TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
                if (selPath == null) {
                    return;
                }

                ITreeNode currClickNode = (ITreeNode) selPath.
                                          getLastPathComponent();

                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (mouseLeftClickListenerList != null &&
                        mouseLeftClickListenerList.size() > 0) {
                        for (int i = 0; i < mouseLeftClickListenerList.size();
                                     i++) {
                            IVWTreeTableMouseClickListener listener = (
                                    IVWTreeTableMouseClickListener)
                                    mouseLeftClickListenerList.get(i);
                            listener.onMouseClick(e, currClickNode,
                                                  IVWTreeTableMouseClickListener.
                                                  POSITION_TREE);
                        }
                    }
                } else {
                    if (mouseRightClickListenerList != null &&
                        mouseRightClickListenerList.size() > 0) {
                        for (int i = 0; i < mouseRightClickListenerList.size();
                                     i++) {
                            IVWTreeTableMouseClickListener listener = (
                                    IVWTreeTableMouseClickListener)
                                    mouseRightClickListenerList.get(i);
                            listener.onMouseClick(e, currClickNode,
                                                  IVWTreeTableMouseClickListener.
                                                  POSITION_TREE);
                        }
                    }

                }
            }
        });

        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                mouseClickedThis(e);
            }
        });
    }

    public void mouseClickedThis(MouseEvent e) {
        if ((e.getModifiers() & InputEvent.CTRL_MASK) != 0) {

            //不容许按ctrl键取消行选择
            if (getSelectedRow() == -1) {
                setSelectedRow(selectedRow);
                return;
            }
        }

        fireSelected();
    }

    public void addRoot(ITreeNode root) {
        if (root.getDataBean() instanceof IDto) {
            ((IDto) root.getDataBean()).setOp(IDto.OP_INSERT);
        }

        setRoot(root);
    }

    public void setRoot(ITreeNode root) {
        if (this.getTreeTableModel() instanceof VMTreeTableModel) {
            ((VMTreeTableModel) getTreeTableModel()).setRoot(root);
        }

        setSelectedRowAndFire(0, false);

        //推迟fire，因为上面的setSelectedRow方法也延迟了。
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                fireRowSelected();
            }
        });

        this.repaint();
    }

    public ITreeNode addRow(ITreeNode newNode) {
        if (this.getRowCount() == 0) {
            addRoot(newNode);
            return newNode;
        }

        int i = this.getSelectedRow();
        if (i >= 0 && i < this.getRowCount()) {
            return this.addRow(i, newNode);
        } else {
            return null;
        }
    }


    /*
     * current node is the node in the row.
     * new node is added to be the first son of current node.It's position just below current node.
     *
     * note: 1.the position of row must has a node, if not, just return;
     */
    public ITreeNode addRow(int row, ITreeNode newNode) {
        ITreeNode curNode = getNodeForRow(row);
        return addRow(curNode, newNode, true);
    }

    public ITreeNode addRow(ITreeNode parentNode, ITreeNode newNode) {
        return addRow(parentNode, newNode, true);
    }

    /**
     * 将newNode加到parentNode的child中 ,作为最后一个
     * @param parentNode ITreeNode
     * @param newNode ITreeNode
     * @param selectRow boolean  判断是否selected this row
     * @return ITreeNode
     */
    public ITreeNode addRow(ITreeNode parentNode, ITreeNode newNode,
                            boolean selectRow) {
        if (this.getRowCount() == 0) {
            addRoot(newNode);
            return newNode;
        }

        if (parentNode == null || newNode == null) {
            return null;
        }

        TreeTableModel model = this.getTreeTableModel();
        if (model instanceof VMTreeTableModel) {
            TreePath parentTreePath = new TreePath(((VMTreeTableModel) model).
                    getPathToRoot(parentNode));

            ((VMTreeTableModel) model).addNode(newNode, parentNode,
                                               parentNode.getChildCount());

            //先把curNode展开，再选中刚增加的节点。否则当curNode未展开时可能选不中。
            tree.expandPath(parentTreePath);
            if (selectRow) {
                setSelectedRow(newNode, true);
            }else{
                setSelectedRow(parentNode, false);
            }
        }

        return newNode;
    }

    /**
     * 将newNode加到parentNode的child中 ,作为第一个
     * @param parentNode ITreeNode
     * @param newNode ITreeNode
     * @param selectRow boolean  判断是否selected this row
     * @return ITreeNode
     */
    public ITreeNode addRowFirst(ITreeNode parentNode, ITreeNode newNode,
                            boolean selectRow) {
        if (this.getRowCount() == 0) {
            addRoot(newNode);
            return newNode;
        }

        if (parentNode == null || newNode == null) {
            return null;
        }

        TreeTableModel model = this.getTreeTableModel();
        if (model instanceof VMTreeTableModel) {
            TreePath parentTreePath = new TreePath(((VMTreeTableModel) model).
                    getPathToRoot(parentNode));

            ((VMTreeTableModel) model).addNode(newNode, parentNode, 0);

            //先把curNode展开，再选中刚增加的节点。否则当curNode未展开时可能选不中。
            tree.expandPath(parentTreePath);
            if (selectRow) {
                setSelectedRow(newNode, true);
            }else{
                setSelectedRow(parentNode, false);
            }
        }

        return newNode;
    }


    public ITreeNode deleteRow() {
        int i = this.getSelectedRow();
        if (i >= 0 && i < this.getModel().getRowCount()) {
            return deleteRow(i);
        } else {
            return null;
        }
    }

    public ITreeNode deleteRow(int rowIndex) {
        ITreeNode deleteNode = getNodeForRow(rowIndex);
        return deleteRow(deleteNode, true);
    }

    public ITreeNode deleteRow(ITreeNode deleteNode) {
        return deleteRow(deleteNode, true);
    }

    /**
     *
     * @param deleteNode ITreeNode
     * @param selectRow boolean   判断是否selected this row
     * @return ITreeNode
     */
    public ITreeNode deleteRow(ITreeNode deleteNode, boolean selectRow) {
        TreeTableModel model = this.getTreeTableModel();
        if (model instanceof VMTreeTableModel) {
            if (deleteNode == null || deleteNode.getParent() == null) {
                return null;
            }

            //set selected row after delete
            ITreeNode parentNode = deleteNode.getParent();
            ITreeNode nextSelectedNode;
            int position = getTreeTableModel().getIndexOfChild(parentNode,
                    deleteNode);
            int childCount = getTreeTableModel().getChildCount(parentNode);

            if (childCount == 1) {
                nextSelectedNode = parentNode;
            } else if (position == childCount - 1) {
                nextSelectedNode = parentNode.getChildAt(position - 1);
            } else {
                nextSelectedNode = parentNode.getChildAt(position + 1);
            }
            ((VMTreeTableModel) model).deleteNode(deleteNode);

            if (selectRow) {
                setSelectedRow(nextSelectedNode, false);
            } else {
                setSelectedRow(parentNode, false);
            }

            //推迟fire，因为上面的setSelectedRow方法也延迟了。
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    fireRowSelected();
                }
            });
        }
        return deleteNode;
    }

    public boolean isUpMovable() {
        int i = this.getSelectedRow();
        if (i >= 0 && i < getRowCount()) {
            return isUpMovable(i);
        } else {
            return false;
        }
    }

    public boolean isUpMovable(int row) {
        boolean bRet = false;
        TreeTableModel model = this.getTreeTableModel();
        if (model instanceof VMTreeTableModel) {
            VMTreeTableModel treeTableModel = (VMTreeTableModel) model;

            TreePath path = tree.getPathForRow(row);
            if (path != null) {
                ITreeNode node = (ITreeNode) path.getLastPathComponent();
                bRet = treeTableModel.isUpMovable(node);
            }
        }

        return bRet;
    }

    public boolean upMove(int row) {
        TreeTableModel model = this.getTreeTableModel();
        if (model instanceof VMTreeTableModel) {
            VMTreeTableModel treeTableModel = (VMTreeTableModel) model;

            TreePath path = tree.getPathForRow(row);
            if (path != null) {
                ITreeNode node = (ITreeNode) path.getLastPathComponent();

                //使move前后的“展开”状态不变
                boolean isExpand = false;
                if (tree.isExpanded(path) == true) {

                    //keep the expand status
                    isExpand = true;
                }

                boolean bRet = treeTableModel.upMove(node);

                this.setSelectedRow(path, false);
                if (bRet == true && isExpand == true) {
                    tree.expandPath(path);
                }

                return bRet;
            }
        }

        return false;
    }

    public boolean upMove() {
        int selIndex = this.getSelectedRow();
        return upMove(selIndex);
    }

    public boolean isDownMovable() {
        int i = this.getSelectedRow();
        if (i >= 0 && i < getRowCount()) {
            return isDownMovable(i);
        } else {
            return false;
        }
    }

    public boolean isDownMovable(int row) {
        boolean bRet = false;
        TreeTableModel model = this.getTreeTableModel();
        if (model instanceof VMTreeTableModel) {
            VMTreeTableModel treeTableModel = (VMTreeTableModel) model;

            TreePath path = tree.getPathForRow(row);
            if (path != null) {
                ITreeNode node = (ITreeNode) path.getLastPathComponent();
                bRet = treeTableModel.isDownMovable(node);
            }
        }

        return bRet;
    }


    public boolean downMove(int row) {
        TreeTableModel model = this.getTreeTableModel();
        if (model instanceof VMTreeTableModel) {
            VMTreeTableModel treeTableModel = (VMTreeTableModel) model;

            TreePath path = tree.getPathForRow(row);
            if (path != null) {
                ITreeNode node = (ITreeNode) path.getLastPathComponent();

                //使move前后的“展开”状态不变
                boolean isExpand = false;
                if (tree.isExpanded(path) == true) {

                    //keep the expand status
                    isExpand = true;
                }

                boolean bRet = treeTableModel.downMove(node);

                this.setSelectedRow(path, false);
                if (bRet == true && isExpand == true) {
                    tree.expandPath(path);
                }

                return bRet;
            }
        }

        return false;
    }

    public boolean downMove() {
        int selIndex = this.getSelectedRow();
        return downMove(selIndex);
    }

    public boolean isLeftMovable() {
        int i = this.getSelectedRow();
        if (i >= 0 && i < getRowCount()) {
            return isLeftMovable(i);
        } else {
            return false;
        }
    }

    public boolean isLeftMovable(int row) {
        boolean bRet = false;
        TreeTableModel model = this.getTreeTableModel();
        if (model instanceof VMTreeTableModel) {
            VMTreeTableModel treeTableModel = (VMTreeTableModel) model;

            TreePath path = tree.getPathForRow(row);
            if (path != null) {
                ITreeNode node = (ITreeNode) path.getLastPathComponent();
                bRet = treeTableModel.isLeftMovable(node);
            }
        }

        return bRet;
    }


    public boolean leftMove(int row) {
        TreeTableModel model = this.getTreeTableModel();
        if (model instanceof VMTreeTableModel) {
            VMTreeTableModel treeTableModel = (VMTreeTableModel) model;

            TreePath path = tree.getPathForRow(row);
            if (path != null) {
                ITreeNode node = (ITreeNode) path.getLastPathComponent();

                //使move前后的“展开”状态不变
                boolean isExpand = false;
                if (tree.isExpanded(path) == true) {

                    //keep the expand status
                    isExpand = true;
                }

                boolean bRet = treeTableModel.leftMove(node);

                ITreeNode[] nodes = treeTableModel.getPathToRoot(node);
                TreePath newPath = new TreePath(nodes);
                this.setSelectedRow(newPath, false);
                if (bRet == true && isExpand == true) {
                    tree.expandPath(newPath);
                }

                return bRet;
            }
        }

        return false;
    }

    public boolean leftMove() {
        return leftMove(getSelectedRow());
    }

    public boolean isRightMovable() {
        int i = this.getSelectedRow();
        if (i >= 0 && i < getRowCount()) {
            return isRightMovable(i);
        } else {
            return false;
        }
    }

    public boolean isRightMovable(int row) {
        boolean bRet = false;
        TreeTableModel model = this.getTreeTableModel();
        if (model instanceof VMTreeTableModel) {
            VMTreeTableModel treeTableModel = (VMTreeTableModel) model;

            TreePath path = tree.getPathForRow(row);
            if (path != null) {
                ITreeNode node = (ITreeNode) path.getLastPathComponent();
                bRet = treeTableModel.isRightMovable(node);
            }
        }

        return bRet;
    }

    public boolean rightMove(int row) {
        TreeTableModel model = this.getTreeTableModel();
        if (model instanceof VMTreeTableModel) {
            VMTreeTableModel treeTableModel = (VMTreeTableModel) model;

            TreePath path = tree.getPathForRow(row);
            if (path != null) {
                ITreeNode node = (ITreeNode) path.getLastPathComponent();

                //使move前后的“展开”状态不变
                boolean isExpand = false;
                if (tree.isExpanded(path) == true) {

                    //keep the expand status
                    isExpand = true;
                }

                boolean bRet = treeTableModel.rightMove(node);
                if (bRet == true) {
                    ITreeNode[] nodes = treeTableModel.getPathToRoot(node);
                    if (nodes.length > 1) {
                        ITreeNode[] pNodes = new ITreeNode[nodes.length - 1];
                        for (int i = 0; i < pNodes.length; i++) {
                            pNodes[i] = nodes[i];
                        }

                        //先把parent path展开，再选中节点。否则可能选不中。
                        TreePath newParentPath = new TreePath(pNodes);
                        tree.expandPath(newParentPath);
                    }

                    TreePath newPath = new TreePath(nodes);
                    this.setSelectedRow(newPath, false);
                    if (bRet == true && isExpand == true) {
                        tree.expandPath(newPath);
                    }
                }
                return bRet;
            }
        }

        return false;
    }

    public TreePath getPathForRow(int row) {
        TreePath path = null;
        TreeTableModel model = this.getTreeTableModel();
        if (model instanceof VMTreeTableModel) {
//            VMTreeTableModel treeTableModel = (VMTreeTableModel) model;
            path = tree.getPathForRow(row);

        }
        return path;
    }

    public TreePath getSelectionPath() {
        return tree.getSelectionPath();
    }

    public boolean rightMove() {
        return rightMove(getSelectedRow());
    }

    public void expandRow(int row) {
        TreePath path = this.tree.getPathForRow(row);
        expandPath(path);
    }

    public void expandPath(TreePath path) {
        this.tree.expandPath(path);
        ITreeNode node = (ITreeNode) path.getLastPathComponent();
        if (node != null) {
            for (int i = 0; i < node.getChildCount(); i++) {
                ITreeNode child = node.getChildAt(i);

                TreePath childPath = path.pathByAddingChild(child);
                expandPath(childPath);
            }
        }
    }

    public void expandsRow() {
        int i = this.getSelectedRow();
        if (i >= 0 && i < this.getModel().getRowCount()) {
            expandRow(i);
            this.setSelectedRowAndFire(i, false);
        }
    }

    public void setSelectedRow(ITreeNode selNode) {
        setSelectedRow(selNode, true);
    }

    private void setSelectedRow(ITreeNode selNode, boolean toFire) {
        if (getTreeTableModel() instanceof VMTreeTableModel) {
            VMTreeTableModel model = (VMTreeTableModel)
                                     getTreeTableModel();
            ITreeNode[] nodes = model.getPathToRoot(selNode);
            if (nodes != null) {
                TreePath path = new TreePath(nodes);
                setSelectedRow(path, toFire);
            }
        }

    }

    public void setSelectedRow(int row) {
        setSelectedRowAndFire(row, true);
    }

    private void setSelectedRowAndFire(int row, boolean toFire) {
        int currRowIndex = row;
        if (currRowIndex < 0) {
            currRowIndex = 0;
        }
        if (currRowIndex >= getRowCount()) {
            currRowIndex = getRowCount();
        }
        if (currRowIndex >= 0 && currRowIndex < getRowCount()) {
            TreePath path = tree.getPathForRow(currRowIndex);
            setSelectedRow(path, toFire);
        }
    }

    private void setSelectedRow(TreePath selectedPath, boolean toFire) {
        final TreePath fSelectedPath = selectedPath;
        final boolean bToFire = toFire;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                tree.setSelectionPath(fSelectedPath);
                if (bToFire == true) {
                    fireSelected();
                }
            }
        });
    }

//    public ITreeNode getSelectedNode() {
//        TreePath path = getTree().getSelectionPath();
//        if (path == null) {
//            return null;
//        }
//
//        return (ITreeNode) path.getLastPathComponent();
//    }
    public ITreeNode getSelectedNode() {
        int i = this.getSelectedRow();
        return this.getNodeForRow(i);
    }

    public TreeTableModel getTreeTableModel() {
        return (TreeTableModel) tree.getModel();
    }

    /**
     *If change the text of node in the tree, call this function to refresh tree.
     **/
    public void refreshTree() {
        this.repaint();
    }

    public NodeViewManager getNodeViewManager() {
        if (this.nodeViewManager == null) {
            return new NodeViewManager();
        } else {
            return this.nodeViewManager;
        }
    }

    public JScrollPane getScrollPane() {
        if (this.scrollPane == null) {
            this.scrollPane = new JScrollPane(this);
        }
        return this.scrollPane;
    }


    private ITreeNode getNodeForRow(int row) {
        TreePath path = getTree().getPathForRow(row);
        if (path != null) {
            ITreeNode node = (ITreeNode) path.getLastPathComponent();
            return node;
        } else {
            return null;
        }
    }

    public void addRowSelectedListener(RowSelectedListener listener) {
        this.rowSelectedListenerList.add(listener);
    }

    public void addRowSelectedLostListener(RowSelectedLostListener listener) {
        this.rowSelectedLostListenerList.add(listener);
    }

    public void addMouseLeftClickListener(IVWTreeTableMouseClickListener
                                          listener) {
        this.mouseLeftClickListenerList.add(listener);
    }

    public void addMouseRightClickListener(IVWTreeTableMouseClickListener
                                           listener) {
        this.mouseRightClickListenerList.add(listener);
    }

    public void fireSelected() {
        if (fireRowSelectedLost() == false) {

            //有listener不愿丢失select
            this.setSelectedRowAndFire(selectedRow, false);
        } else {
            fireDiffRowSelected();
        }
    }

    //fire the listeners when a different row is selected.
    protected void fireDiffRowSelected() {
        Object currentSelectedData = this.getSelectedNode();

        if (this.selectedNode != currentSelectedData) {
            fireRowSelected();
        }
    }

    //fire the listeners when a different row is selected.
    public void fireRowSelected() {
        this.selectedRow = this.getSelectedRow();
        this.selectedNode = this.getSelectedNode();

        for (int i = 0; i < rowSelectedListenerList.size(); i++) {
            RowSelectedListener listener = (RowSelectedListener)
                                           rowSelectedListenerList.get(i);
            listener.processRowSelected();
        }
    }

    //fire the listeners when a different row is selected.
    protected boolean fireRowSelectedLost() {
        boolean bSelectedLost = true;

        Object nextSelectedData = this.getSelectedNode();
        if (this.selectedNode != nextSelectedData) {

            for (int i = 0; i < rowSelectedLostListenerList.size(); i++) {
                RowSelectedLostListener listener = (RowSelectedLostListener)
                        rowSelectedLostListenerList.get(i);
                if (listener.processRowSelectedLost(selectedRow, selectedNode) == false) {

                    //记录下是否有listener不愿lost select
                    bSelectedLost = false;
                }
            }
        }

        return bSelectedLost;
    }

    public Component getCellComponent(int column) {
        TreeTableModel model = this.getTreeTableModel();
        if (model instanceof IColumnConfig) {
            IColumnConfig vmModel = (IColumnConfig) model;
            VMColumnConfig cfg = (VMColumnConfig) vmModel.getColumnConfigs().
                                 get(column);
            return cfg.getComponent();
        } else {
            return null;
        }
    }

    public String getToolTipText(MouseEvent e) {
        int row = rowAtPoint(e.getPoint());
        int col = columnAtPoint(e.getPoint());

        if ((row >= 0 && row < this.getRowCount()
             && col >= 0 && col < this.getColumnCount() == false)) {
            return null;
        }

        boolean bShow = false;
        if (getModel().getColumnClass(col) == TreeTableModel.class) {

            //显示树的那一列会有tooltip
            bShow = true;
        } else {

            Component comp = getCellComponent(col);
            if (comp != null &&
                (comp instanceof VWJText
                 || comp instanceof VWJTextArea
                 || comp instanceof VWJDisp)) {

                //text 和 textArea控件为renderer会有tooltip
                bShow = true;
            }
        }

        if (bShow == true) {
            Object value = getValueAt(row, col);
            if (value != null) {
                return value.toString();
            }
        }

        return null;
    }
}
