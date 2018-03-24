package net.sourceforge.ganttproject;

import net.sourceforge.ganttproject.gui.UIConfiguration;
import net.sourceforge.ganttproject.task.TaskManager;
import net.sourceforge.ganttproject.time.TimeUnitStack;
import client.framework.model.IColumnConfig;
import client.framework.common.treeTable.TreeTableModel;
import javax.swing.tree.TreePath;
import javax.swing.event.TreeModelListener;
import java.util.List;
import java.util.Map;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import java.util.ArrayList;
import java.util.HashMap;
import c2s.dto.ITreeNode;
import c2s.dto.IDto;
import java.lang.reflect.Constructor;
import c2s.dto.DtoBase;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import client.framework.model.VMColumnConfig;
import c2s.dto.DtoUtil;
import net.sourceforge.ganttproject.chart.ChartModelImpl;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author Robin
 * @version 1.0
 */
public class WitsGanttChartModel extends ChartModelImpl implements
        IColumnConfig, TreeTableModel {
    //TreeTableModel需要用到的属性
    protected Object root;
    protected EventListenerList listenerList = new EventListenerList();

    //IColumnConfig需要用到的属性
    private List columnConfigs = new ArrayList();
    private Map columnMap = new HashMap();
    private String treeColumnName = "";
    private boolean isTreeColumnEditable = false;
    private Class dtoType;


    public WitsGanttChartModel(TaskManager taskManager,
                               TimeUnitStack timeUnitStack,
                               UIConfiguration projectConfig) {
        super(taskManager, timeUnitStack, projectConfig);
    }

    public WitsGanttChartModel(TaskManager taskManager,
                               TimeUnitStack timeUnitStack,
                               UIConfiguration projectConfig, Object root,
                               Object[][] configs, String treeColumnName) {
        super(taskManager, timeUnitStack, projectConfig);
        this.root = root;
        this.setColumnConfigs(configs);
        this.treeColumnName = treeColumnName;
    }


    public void setColumnConfigs(Object[][] configs) {
        if (configs == null) {
            return;
        }
        columnConfigs.clear();
        for (int i = 0; i < configs.length; i++) {
            VMColumnConfig columnConfig = new VMColumnConfig(configs[i]);
            if (columnConfig.getIsHidden() == false) {
                columnConfigs.add(columnConfig);
            }

            columnMap.put(columnConfig.getName(), columnConfig);
        }
    }

    //更新ColumnConfig
    public void updateColumnConfig(Object[] config) {
        VMColumnConfig columnConfig = new VMColumnConfig(config);
        List list = this.getColumnConfigs();
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (((VMColumnConfig) list.get(i)).getName().equals(columnConfig.
                    getName())) {
                index = i;
                break; //找到一个就结束
            }
        }
        if (index != -1) {
            list.remove(index);
            list.add(index, columnConfig);
            this.setColumnConfigs(list, false);
        }
    }

    public List getColumnConfigs() {
        return this.columnConfigs;
    }

    public VMColumnConfig getColumnConfig(String columnName) {
        for (int i = 0; i < columnConfigs.size(); i++) {
            VMColumnConfig columnConfig = (VMColumnConfig) columnConfigs.get(i);
            if (columnConfig.getName().equals(columnName)) {
                return columnConfig;
            }
        }
        return null;
    }

    public VMColumnConfig getColumnConfigByDataName(String columnDataName) {
        for (int i = 0; i < columnConfigs.size(); i++) {
            VMColumnConfig columnConfig = (VMColumnConfig) columnConfigs.get(i);
            if (columnConfig.getDataName().equals(columnDataName)) {
                return columnConfig;
            }
        }
        return null;
    }

    public Map getColumnMap() {
        return this.columnMap;
    }

    public void setColumnConfigs(List columnConfigs) {
        setColumnConfigs(columnConfigs, true);
    }

    public void setColumnConfigs(List columnConfigs, boolean bFlag) {
        this.columnConfigs = columnConfigs;
        if (bFlag == true) {
            columnMap.clear();
            for (int i = 0; i < columnConfigs.size(); i++) {
                VMColumnConfig columnConfig = (VMColumnConfig) columnConfigs.
                                              get(i);
                columnMap.put(columnConfig.getName(), columnConfig);
            }
        }
    }

    public Class getDtoType() {
        return this.dtoType;
    }

    public void setDtoType(Class dtoType) {
        this.dtoType = dtoType;
    }


    //
    // TreeModel interface
    //

    /**
     * TreeModel method to return the number of children of a particular
     * node. Since <code>node</code> is a TreeNode, this can be answered
     * via the TreeNode method <code>getChildCount</code>.
     */
    public int getChildCount(Object node) {
        return ((ITreeNode) node).getChildCount();
    }

    /**
     * TreeModel method to locate a particular child of the specified
     * node. Since <code>node</code> is a TreeNode, this can be answered
     * via the TreeNode method <code>getChild</code>.
     */
    public Object getChild(Object node, int i) {
        return ((ITreeNode) node).getChildAt(i);
    }


    /**
     * TreeModel method to determine if a node is a leaf.
     * Since <code>node</code> is a TreeNode, this can be answered
     * via the TreeNode method <code>isLeaf</code>.
     */
    public boolean isLeaf(Object node) {
        return ((ITreeNode) node).isLeaf();
    }


    //
    //  The TreeTable interface.
    //

    /**
     * Returns the number of column names passed into the constructor.
     */
    public int getColumnCount() {
        return columnConfigs.size();
    }


    /**
     * Returns the column name passed into the constructor.
     */
    public String getColumnName(int columnIndex) {
        VMColumnConfig columnConfig = (VMColumnConfig) columnConfigs.get(
                columnIndex);
        return columnConfig.getName();
    }

    /**
     * Returns the column name passed into the constructor.
     */
    public String getColumnDataName(int columnIndex) {
        VMColumnConfig columnConfig = (VMColumnConfig) columnConfigs.get(
                columnIndex);
        return columnConfig.getDataName();
    }


    /**
     * Returns the column class for column <code>column</code>. This
     * is set in the constructor.
     */
    public Class getColumnClass(int columnIndex) {
        //Class cls = super.getColumnClass(columnIndex);
        Class cls = null;
        if (this.treeColumnName.equals(getColumnDataName(columnIndex))) {
            return TreeTableModel.class;
        }

        VMColumnConfig columnConfig = (VMColumnConfig) columnConfigs.get(
                columnIndex);
        String dataName = columnConfig.getDataName();

        try {
            if (root != null) { //防止root为null时会报异常
                ITreeNode treeNode = (ITreeNode) root;
                Object dataBean = treeNode.getDataBean();
                Object value = DtoUtil.getProperty(dataBean, dataName);
                cls = value.getClass();
            } else {
                cls = String.class;
            }
        } catch (Exception e) {
//            e.printStackTrace();
//            log.debug(e);
            cls = String.class; //added by xh
        }

        return cls;

    }


    /**
     * Returns the value for the column <code>column</code> and object
     * <code>node</code>. The return value is determined by invoking
     * the method specified in constructor for the passed in column.
     */
    public Object getValueAt(Object node, int columnIndex) {
        Object value = null;
        VMColumnConfig columnConfig = (VMColumnConfig) columnConfigs.get(
                columnIndex);
        String dataName = columnConfig.getDataName();
        try {
            //modfiy by stone for DataNode define 20050824
            Object dataNode = node;
            Object dataBean = null;
            if (dataNode instanceof ITreeNode) {
                dataBean = ((ITreeNode) dataNode).getDataBean();
            } else {
                dataBean = dataNode;
            }
            value = DtoUtil.getProperty(dataBean, dataName);
            //value = DtoUtil.getProperty(getRow(rowIndex), dataName);
        } catch (Exception e) {
            e.printStackTrace();
//            log.debug(e);
        }
        return value;
    }


    /**
     * Returns true if there is a setter method name for column
     * <code>column</code>. This is set in the constructor.
     */
    public boolean isCellEditable(Object node, int columnIndex) {
        VMColumnConfig columnConfig = (VMColumnConfig) columnConfigs.get(
                columnIndex);
        //modfiy by stone for DataNode define 20050824
        boolean bColumnEditable = columnConfig.getEditable();
        boolean bRowEditable = true;

        //这里不能作判断，如果这里返回false之后，树的展开和折叠将不能使用了
        //所以只能将切入点改到编辑器上去判断
        //note by: Robin.zhang   20060622
//        Object dataNode = node;
//        if (dataNode instanceof ITreeNode) {
//            DtoBase dto=(DtoBase)((ITreeNode) dataNode).getDataBean();
//            if(dto.isReadonly()){
//                bRowEditable=false;
//            }
//        }

        return (bColumnEditable && bRowEditable);
    }


    public void setValueAt(Object aValue, Object node, int column) {
//        System.out.println("传进来的值为：" + aValue.toString() + "@@@@@@@@@@@@@@");
        boolean found = false;
        try {
            // We have to search through all the methods since the
            // types may not match up.

            //得到节点类的所有的方法

            ITreeNode nodeData = (ITreeNode) node;

            Method[] methods = nodeData.getDataBean().getClass().getMethods();
            VMColumnConfig columnConfig = (VMColumnConfig) columnConfigs.get(
                    column);
            String setName = columnConfig.getDataName(); //columnConfig.getName();//列的名字
            setName = "set" + setName.substring(0, 1).toUpperCase() +
                      setName.substring(1); //构造出一个set方法
//            System.out.println(setName);
            for (int counter = methods.length - 1; counter >= 0; counter--) {
                //如果方法[i]的名字等于column列set方法名
                //并且方法[i]的的参数类型不为空
                //并且方法[i]的参数的长度为1
//                System.out.println(counter + ":" + methods[counter].getName());
                if (methods[counter].getName().equals
                    (setName) && methods[counter].
                    getParameterTypes() != null && methods[counter].
                    getParameterTypes().length == 1) {
                    // We found a matching method
                    Class param = methods[counter].getParameterTypes()[0];
                    if (!param.isInstance(aValue)) {
                        //判定指定的 aValue 是否与此 Class 所表示的对象赋值兼容
                        if (aValue instanceof String &&
                            ((String) aValue).length() == 0) {
                            aValue = null;
                        } else {
                            Constructor cs = param.getConstructor
                                             (new Class[] {String.class});
                            aValue = cs.newInstance(new Object[] {aValue.
                                    toString()});
                            System.out.println(aValue);
                        }
                    }
                    //根据列名调用相应的方法(如setName)把这个值设到DataBean里面去
                    Object tempObj = nodeData.getDataBean();
                    if (aValue != null && !aValue.toString().equals("")) {
                        methods[counter].invoke(tempObj, new Object[] {aValue});
                        if (tempObj instanceof DtoBase) {
                            if (!((DtoBase) tempObj).getOp().endsWith(IDto.
                                    OP_INSERT)) {
                                ((DtoBase) tempObj).setOp(IDto.OP_MODIFY);
                            }
                        }
                        nodeData.setDataBean(tempObj);
                        found = true;
                        break;
                    }
                }
            }
        } catch (Throwable th) {
            System.out.println("exception: " + th);
        }
//        found = true;
        if (found) {
            // The value changed, fire an event to notify listeners.
            ITreeNode parent = ((ITreeNode) node).getParent();
            if (parent != null) {
                fireTreeNodesChanged(node, getPathToRoot(parent),
                                     new int[] {getIndexOfChild(parent, node)},
                                     new Object[] {node});
            }
        }
    }


    public void setTreeColumnName(String treeColumnName) {
        this.treeColumnName = treeColumnName;
    }

    public String getTreeColumnName() {
        return treeColumnName;
    }

    public boolean isTreeColumnEditable() {
        return this.isTreeColumnEditable;
    }

    public void setTreeColumnEditable(boolean f) {
        this.isTreeColumnEditable = f;
    }

    /**
     * Builds the parents of the node up to and including the root node,
     * where the original node is the last element in the returned array.
     * The length of the returned array gives the node's depth in the
     * tree.
     *
     * @param aNode the TreeNode to get the path for
     * @param an array of TreeNodes giving the path from the root to the
     *        specified node.
     */
    public ITreeNode[] getPathToRoot(ITreeNode aNode) {
        return getPathToRoot(aNode, 0);
    }


    /**
     * Builds the parents of the node up to and including the root node,
     * where the original node is the last element in the returned array.
     * The length of the returned array gives the node's depth in the
     * tree.
     *
     * @param aNode  the TreeNode to get the path for
     * @param depth  an int giving the number of steps already taken towards
     *        the root (on recursive calls), used to size the returned array
     * @return an array of TreeNodes giving the path from the root to the
     *         specified node
     */
    public ITreeNode[] getPathToRoot(ITreeNode aNode, int depth) {
        ITreeNode[] retNodes;
        // This method recurses, traversing towards the root in order
        // size the array. On the way back, it fills in the nodes,
        // starting from the root and working back to the original node.

        /* Check for null, in case someone passed in a null node, or
           they passed in an element that isn't rooted at root. */
        if (aNode == null) {
            if (depth == 0) {
                return null;
            } else {
                retNodes = newTreeNodeArray(aNode, depth);
            }
        } else {
            depth++;
            if (aNode == root) {
                retNodes = newTreeNodeArray(aNode, depth);
            } else {
                retNodes = getPathToRoot(aNode.getParent(), depth);
            }
            retNodes[retNodes.length - depth] = aNode;
        }
        return retNodes;
    }

    private ITreeNode[] newTreeNodeArray(ITreeNode aNode, int depth) {
        ITreeNode[] retNodes = new ITreeNode[depth];
        int i = 0;
        try {
            Class nodeClass = aNode.getClass();
            Constructor constructor = nodeClass.getConstructor(new Class[] {
                    Object.class});

            Class beanClass;
            if (aNode.getDataBean() != null) {
                beanClass = aNode.getDataBean().getClass();
            } else {
                beanClass = DtoBase.class;
            }

            for (; i < depth; i++) {
                Object dataBean = beanClass.newInstance();
                retNodes[i] = (ITreeNode) (constructor.newInstance(new Object[] {
                        dataBean}));
            }
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } catch (InstantiationException ex) {
            throw new RuntimeException(ex);
        } catch (SecurityException ex1) {
        } catch (NoSuchMethodException ex1) {
        } catch (InvocationTargetException ex2) {
        }

        return retNodes;
    }

    /**
     * Sets the root to <code>root</code>. A null <code>root</code> implies
     * the tree is to display nothing, and is legal.
     *
     * note: if want to delete all nodes ,please use remove function - removeAllNode()
     */
    public void setRoot(ITreeNode root) {
        Object oldRoot = this.root;
        this.root = root;
        if (root == null && oldRoot != null) {
            fireTreeStructureChanged(this, null);
        } else {
            nodeStructureChanged(root);
        }
    }


    /**
     * Invoke this method after you've changed how node is to be
     * represented in the tree.
     */
    public void nodeChanged(ITreeNode node) {
        if (listenerList != null && node != null) {
            ITreeNode parent = node.getParent();

            if (parent != null) {
                int anIndex = this.getIndexOfChild(parent, node);
                if (anIndex != -1) {
                    int[] cIndexs = new int[1];

                    cIndexs[0] = anIndex;
                    nodesChanged(parent, cIndexs);
                }
            } else if (node == getRoot()) {
                nodesChanged(node, null);
            }
        }
    }

    /**
     * Invoke this method after you've inserted some TreeNodes into
     * node.  childIndices should be the index of the new elements and
     * must be sorted in ascending order.
     */
    public void nodesWereInserted(ITreeNode node, int[] childIndices) {
        if (listenerList != null && node != null
            && childIndices != null && childIndices.length > 0) {
            int cCount = childIndices.length;
            Object[] newChildren = new Object[cCount];

            for (int counter = 0; counter < cCount; counter++) {
                newChildren[counter] = this.getChild(node, childIndices[counter]);
            }
            fireTreeNodesInserted(this, getPathToRoot(node), childIndices,
                                  newChildren);
        }
    }

    /**
     * Invoke this method after you've removed some TreeNodes from
     * node.  childIndices should be the index of the removed elements and
     * must be sorted in ascending order. And removedChildren should be
     * the array of the children objects that were removed.
     */
    public void nodesWereRemoved(ITreeNode node, int[] childIndices,
                                 Object[] removedChildren) {
        if (node != null && childIndices != null) {
            fireTreeNodesRemoved(this, getPathToRoot(node), childIndices,
                                 removedChildren);
        }
    }

    /**
     * Invoke this method after you've changed how the children identified by
     * childIndicies are to be represented in the tree.
     */
    public void nodesChanged(ITreeNode node, int[] childIndices) {
        if (node != null) {
            if (childIndices != null) {
                int cCount = childIndices.length;

                if (cCount > 0) {
                    Object[] cChildren = new Object[cCount];

                    for (int counter = 0; counter < cCount; counter++) {
                        cChildren[counter] = this.getChild(node,
                                childIndices[counter]);
                    }
                    fireTreeNodesChanged(this, getPathToRoot(node),
                                         childIndices, cChildren);
                }
            } else if (node == getRoot()) {
                fireTreeNodesChanged(this, getPathToRoot(node), null, null);
            }
        }
    }

    /**
     * Invoke this method if you've totally changed the children of
     * node and its childrens children...  This will post a
     * treeStructureChanged event.
     */
    public void nodeStructureChanged(ITreeNode node) {
        if (node != null) {
            fireTreeStructureChanged(this, getPathToRoot(node), null, null);
        }
    }


    public void fireTreeNodesChanged(Object source, Object[] path,
                                     int[] childIndices,
                                     Object[] children) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == TreeModelListener.class) {
                // Lazily create the event:
                if (e == null) {
                    e = new TreeModelEvent(source, path,
                                           childIndices, children);
                }
                ((TreeModelListener) listeners[i + 1]).treeNodesChanged(e);
            }
        }
    }

    public void fireTreeNodesInserted(Object source, Object[] path,
                                      int[] childIndices,
                                      Object[] children) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == TreeModelListener.class) {
                // Lazily create the event:
                if (e == null) {
                    e = new TreeModelEvent(source, path,
                                           childIndices, children);
                }
                ((TreeModelListener) listeners[i + 1]).treeNodesInserted(e);
            }
        }
    }

    public void fireTreeNodesRemoved(Object source, Object[] path,
                                     int[] childIndices,
                                     Object[] children) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == TreeModelListener.class) {
                // Lazily create the event:
                if (e == null) {
                    e = new TreeModelEvent(source, path,
                                           childIndices, children);
                }
                ((TreeModelListener) listeners[i + 1]).treeNodesRemoved(e);
            }
        }
    }

    public void fireTreeStructureChanged(Object source, Object[] path,
                                         int[] childIndices,
                                         Object[] children) {
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == TreeModelListener.class) {
                if (e == null) {
                    e = new TreeModelEvent(source, path,
                                           childIndices, children);
                }
                ((TreeModelListener) listeners[i + 1]).treeStructureChanged(e);
            }
        }
    }

    private void fireTreeStructureChanged(Object source, TreePath path) {
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == TreeModelListener.class) {
                if (e == null) {
                    e = new TreeModelEvent(source, path);
                }
                ((TreeModelListener) listeners[i + 1]).treeStructureChanged(e);
            }
        }
    }


    public Object getRoot() {
        return this.root;
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    public int getIndexOfChild(Object parent, Object child) {
        for (int i = 0; i < getChildCount(parent); i++) {
            if (getChild(parent, i).equals(child)) {
                return i;
            }
        }
        return -1;
    }

    public void addTreeModelListener(TreeModelListener l) {
        listenerList.add(TreeModelListener.class, l);
    }

    public void removeTreeModelListener(TreeModelListener l) {
        listenerList.remove(TreeModelListener.class, l);
    }


    /***Override ***/
    public void addNode(ITreeNode newChild,
                        ITreeNode parent, int index) {

        List childList = parent.children();
        if (childList != null) {
//                newChild.setParent(parent);
//                childList.add(index, newChild);
            parent.addChild(index, newChild);
        }

        int[] newIndexs = new int[1];

        newIndexs[0] = index;
        this.nodesWereInserted(parent, newIndexs);
    }

    public void deleteNode(ITreeNode node) {
        ITreeNode parent = (ITreeNode) node.getParent();

        if (parent == null) {
            throw new IllegalArgumentException(
                    "node does not have a parent.");
        }

        int[] childIndex = new int[1];
        Object[] removedArray = new Object[1];

        childIndex[0] = this.getIndexOfChild(parent, node);

        parent.deleteChild(node);

        removedArray[0] = node;
        this.nodesWereRemoved(parent, childIndex, removedArray);
    }

    public boolean isUpMovable(ITreeNode node) {
        ITreeNode parentNode = (ITreeNode) node.getParent();
        if (parentNode == null) {

            //It's root,and cannot up move anymore
            return false;
        }

        int nIndex = this.getIndexOfChild(parentNode, node);

        if (nIndex < 0) {
            return false;
        } else if (nIndex == 0) {

            //It's the first son of his parent, cannot up move anymore
            return false;
        } else {

            return true;
        }
    }

    public boolean upMove(ITreeNode node) {
        ITreeNode parentNode = (ITreeNode) node.getParent();
        if (parentNode == null) {

            //It's root,and cannot up move anymore
            return false;
        }

        int nIndex = this.getIndexOfChild(parentNode, node);

        if (nIndex < 0) {
            return false;
        } else if (nIndex == 0) {

            //It's the first son of his parent, cannot up move anymore
            return false;
        } else {
            parentNode.deleteChild(node, false);
//            parentNode.children().remove(nIndex);
            this.nodesWereRemoved(parentNode, new int[] {nIndex},
                                  new Object[] {node});
//            parentNode.children().add(nIndex - 1, node);
            //保持删除前后的op不变
            parentNode.addChild(nIndex - 1, node,
                                ((IDto) node.getDataBean()).getOp());
            this.nodesWereInserted(parentNode, new int[] {nIndex - 1});

            return true;
        }
    }

    public boolean isDownMovable(ITreeNode node) {
        ITreeNode parentNode = (ITreeNode) node.getParent();
        if (parentNode == null) {

            //It's root,and cannot down move anymore
            return false;
        }

        int nIndex = this.getIndexOfChild(parentNode, node);

        if (nIndex < 0) {
            return false;
        } else if (nIndex == parentNode.getChildCount() - 1) {

            //It's the last son of his parent, cannot down move anymore
            return false;
        } else {

            return true;
        }
    }

    public boolean downMove(ITreeNode node) {
        ITreeNode parentNode = (ITreeNode) node.getParent();
        if (parentNode == null) {

            //It's root,and cannot down move anymore
            return false;
        }

        int nIndex = this.getIndexOfChild(parentNode, node);

        if (nIndex < 0) {
            return false;
        } else if (nIndex == parentNode.getChildCount() - 1) {

            //It's the last son of his parent, cannot down move anymore
            return false;
        } else {
//            parentNode.children().remove(nIndex);
            parentNode.deleteChild(node, false);
            this.nodesWereRemoved(parentNode, new int[] {nIndex},
                                  new Object[] {node});
//            parentNode.children().add(nIndex + 1, node);
            //保持删除前后的op不变
            parentNode.addChild(nIndex + 1, node,
                                ((IDto) (node.getDataBean())).getOp());
            this.nodesWereInserted(parentNode, new int[] {nIndex + 1});

            return true;
        }
    }

    public boolean isLeftMovable(ITreeNode node) {
        ITreeNode parentNode = (ITreeNode) node.getParent();
        if (parentNode == null) {

            //Its the root,and cannot left move anymore
            return false;
        }

        ITreeNode parentParentNode = (ITreeNode) parentNode.getParent();
        if (parentParentNode == null) {

            //Its parent is the root, and cannot left move anymore
            return false;
        }

        return true;
    }

    public boolean leftMove(ITreeNode node) {
        ITreeNode parentNode = (ITreeNode) node.getParent();
        if (parentNode == null) {

            //Its the root,and cannot left move anymore
            return false;
        }

        ITreeNode parentParentNode = (ITreeNode) parentNode.getParent();
        if (parentParentNode == null) {

            //Its parent is the root, and cannot left move anymore
            return false;
        }

        int position = parentNode.getIndex(node);
        int parentPosition = parentParentNode.getIndex(parentNode);
//        parentNode.children().remove(position);
        parentNode.deleteChild(node, false);
        this.nodesWereRemoved(parentNode, new int[] {position},
                              new Object[] {node});
//        parentParentNode.children().add(parentPosition + 1, node);
        //保持删除前后的op不变
        parentParentNode.addChild(parentPosition + 1, node,
                                  ((IDto) (node.getDataBean())).getOp());
//        node.setParent(parentParentNode);
        this.nodesWereInserted(parentParentNode, new int[] {parentPosition + 1});

        return true;
    }

    public boolean isRightMovable(ITreeNode node) {
        ITreeNode parentNode = (ITreeNode) node.getParent();
        if (parentNode == null ||
            parentNode.getChildCount() == 1) {

            //It's the root or the only son of his parent, cannot right move anymore
            return false;
        }

        int nIndex = parentNode.getIndex(node);

        if (nIndex < 0) {
            return false;
        } else if (nIndex == 0) {

            //It's the first son of his parent, cannot right move anymore
            return false;
        } else {

            return true;
        }
    }


    public boolean rightMove(ITreeNode node) {
        ITreeNode parentNode = (ITreeNode) node.getParent();
        if (parentNode == null ||
            parentNode.getChildCount() == 1) {

            //It's the root or the only son of his parent, cannot right move anymore
            return false;
        }

        int nIndex = parentNode.getIndex(node);

        if (nIndex < 0) {
            return false;
        } else if (nIndex == 0) {

            //It's the first son of his parent, cannot right move anymore
            return false;
        } else {

            //move it to be a son of his up brother node
            ITreeNode bNode = parentNode.getChildAt(nIndex - 1);
            int bChildCount = bNode.getChildCount();

//            parentNode.children().remove(nIndex);
            parentNode.deleteChild(node, false);
            this.nodesWereRemoved(parentNode, new int[] {nIndex},
                                  new Object[] {node});
//            bNode.children().add(bChildCount, node);
//            node.setParent(bNode);
            bNode.addChild(bChildCount, node,
                           ((IDto) (node.getDataBean())).getOp());

            this.nodesWereInserted(bNode, new int[] {bChildCount});
        }

        return true;
    }

}
