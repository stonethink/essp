package c2s.essp.pms.wbs;

import java.util.List;
import java.util.ArrayList;
import c2s.dto.ITreeNode;
import c2s.dto.IDto;
import c2s.dto.DtoUtil;
import java.io.Serializable;
import server.framework.common.BusinessException;
import java.util.Collections;

public class DtoWbsTreeNode implements ITreeNode, Serializable {
//    private Log log = new Log();
    private boolean wbsTree;
    private List children = new ArrayList();
    private ITreeNode parent;
    private Object dataBean;

    //This list keep the node that is deleted.
    List deleteNodeList = new ArrayList();

    public DtoWbsTreeNode(Object dataBean) {
        wbsTree = false;
        this.setDataBean(dataBean);
    }

    public ITreeNode getChildAt(int index) {
        if (children == null) {
            throw new ArrayIndexOutOfBoundsException("node has no children");
        }
        if (isWbsTree()) {

        }
        return (ITreeNode) children.get(index);
    }

    public int getChildCount() {
        if (isActivityTree()) {
            if (children == null) {
                return 0;
            } else {
                return children.size();
            }
        } else {
            if (children == null || children.size() == 0) {
                return 0;
            } else {
                int iCount = 0;
                for (int i = 0; i < children.size(); i++) {
                    ITreeNode aChild = (ITreeNode) children.get(i);
                    IDtoWbsActivity dataBean = (IDtoWbsActivity) aChild.
                                               getDataBean();
                    if (dataBean.isWbs()) {
                        iCount++;
                    }
                }
                return iCount;
            }

        }
    }

    public int getIndex(ITreeNode aChild) {
        if (aChild == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if (children == null) {
            return -1;
        }

        if (isActivityTree()) {
            for (int i = 0; i < getChildCount(); i++) {
                if (aChild.equals(children.get(i)) == true) {
                    return i;
                }
            }

            return -1;
        } else {
            int iCount = 0;
            for (int i = 0; i < getChildCount(); i++) {
                ITreeNode currChild = (ITreeNode) children.get(i);
                IDtoWbsActivity dataBean = (IDtoWbsActivity) currChild.
                                           getDataBean();
                if (dataBean.isWbs()) {
                    iCount++;
                }
                if (aChild.equals(currChild) == true) {
                    return i;
                }
            }

            return -1;
        }
    }

    public List children() {
        if (isActivityTree()) {
            if (children == null) {
                children = new ArrayList();
            }
            return children;
        } else {
            if (children == null) {
                children = new ArrayList();
            }
            List wbsChild = new ArrayList();
            for (int i = 0; i < children.size(); i++) {
                ITreeNode currChild = (ITreeNode) children.get(i);
                IDtoWbsActivity dataBean = (IDtoWbsActivity) currChild.
                                           getDataBean();
                if (dataBean.isWbs()) {
                    wbsChild.add(currChild);
                }

            }
            return wbsChild;
        }
    }

    public boolean isLeaf() {
        return getChildCount() <= 0;
    }

    public ITreeNode getParent() {
        return parent;
    }

    public void setParent(ITreeNode parent) {
        this.parent = parent;
    }

    public void addChild(ITreeNode child) {
        if (children == null) {
            children = new ArrayList();
        }
        this.addChild(this.children.size(), child);
    }

    //toDo:新加入的子结点的op设不设?
    public void addChild(int index, ITreeNode child) {
        addChild(index, child, IDto.OP_INSERT);
    }

    public void addChild(ITreeNode child, String op) {
        this.addChild(this.getChildCount(), child, op);
    }

    //toDo:新加入的子结点的op设不设?
    public void addChild(int index, ITreeNode child, String op) {
        if (children == null) {
            children = new ArrayList();
        }
        child.setParent(this);
        if (child.getDataBean() instanceof IDto) {
            ((IDto) child.getDataBean()).setOp(op);
        }
//        child.setOp( op );

        children.add(index, child);
    }


    public void deleteChild(ITreeNode node) {
        int i = this.getIndex(node);
        if (i >= 0 && i < this.getChildCount()) {
            children.remove(i);

            if (node.getDataBean() instanceof IDto) {
                IDto dto = (IDto) node.getDataBean();
                if (IDto.OP_INSERT.equals(dto.getOp()) == false) {
                    dto.setOp(IDto.OP_DELETE);

                    deleteNodeList.add(node);
                }
            } else {
                deleteNodeList.add(node);
            }
        }
    }

    public void deleteChild(ITreeNode node, boolean remeberDelete) {
        int i = this.getIndex(node);
        if (i >= 0 && i < this.getChildCount()) {
            children.remove(i);

            if (remeberDelete == true) {
                if (node.getDataBean() instanceof IDto) {
                    IDto dto = (IDto) node.getDataBean();
                    if (IDto.OP_INSERT.equals(dto.getOp()) == false) {
                        dto.setOp(IDto.OP_DELETE);

                        deleteNodeList.add(node);
                    }
                } else {
                    deleteNodeList.add(node);
                }
            }
        }
    }

//    public String toString() {
//        if(this.getDataBean()!=null) {
//            return this.getDataBean().toString();
//        } else {
//            return super.toString();
//        }
//    }

    public List getDeleteNodeList() {
        return this.deleteNodeList;
    }

    public boolean isChanged() {
        if (this.getDataBean() instanceof IDto == true) {
            IDto dataBean = (IDto)this.getDataBean();
            if (dataBean.isChanged() == true) {
                return true;
            }
        }

        if (this.getDeleteNodeList().size() > 0) {
            return true;
        }

        for (int i = 0; i < this.getChildCount(); i++) {
            ITreeNode child = (ITreeNode)this.getChildAt(i);
            if (child instanceof DtoWbsTreeNode) {
                if (((DtoWbsTreeNode) child).isChanged() == true) {
                    return true;
                }
            }
        }

        return false;
    }


    public boolean isWbsTree() {
        return wbsTree;
    }

    public void setWbsTree(boolean wbsTree) {
        DtoWbsTreeNode parent = (DtoWbsTreeNode)this.getParent();
        while (parent != null) {
            parent = (DtoWbsTreeNode)this.getParent();
        }

        if (parent == null) {
            this.setWbsTreeLocal(wbsTree);
        } else {
            parent.setWbsTreeLocal(wbsTree);
        }
    }

    private void setWbsTreeLocal(boolean wbsTree) {
        this.wbsTree = wbsTree;
        List childs = this.children();
        if (childs != null && childs.size() > 0) {
            for (int i = 0; i < childs.size(); i++) {
                Object child = childs.get(i);
                if (child instanceof DtoWbsTreeNode) {
                    ((DtoWbsTreeNode) child).setWbsTreeLocal(wbsTree);
                }
            }
        }
    }

    public boolean isActivityTree() {
        return!isWbsTree();
    }

    public void setActivityTree(boolean activityTree) {
        setWbsTree(!activityTree);
    }

    public void setDataBean(Object dataBean) {
        this.dataBean = dataBean;
    }

    public Object getDataBean() {
        return this.dataBean;
    }

    public boolean findAndSetData(DtoWbsActivity dto) {
        if (dto == null) {
            return false;
        }
        if (dto.equals(dataBean)) {
            try {
                DtoUtil.copyProperties(dataBean, dto);
            } catch (Exception ex) {
            }
            return true;
        } else {
            List allChild = this.children();
            if (allChild == null || allChild.size() == 0) {
                return false;
            } else {
                for (int i = 0; i < allChild.size(); i++) {
                    DtoWbsTreeNode childNode = (DtoWbsTreeNode) allChild.get(i);
                    if (childNode.findAndSetData(dto)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * deep clone!!!
     * @return Object
     */
    public Object clone() {
        DtoWbsTreeNode newNode = new DtoWbsTreeNode(dataBean);
        if (this.children.size() > 0) {
            for (int i = 0; i < children.size(); i++) {
                DtoWbsTreeNode child = (DtoWbsTreeNode) children.get(i);
                newNode.addChild((DtoWbsTreeNode) child.clone(),
                                 ((IDto) dataBean).getOp());
            }
        }
        if (this.deleteNodeList.size() > 0) {
            for (int i = 0; i < deleteNodeList.size(); i++) {
                DtoWbsTreeNode child = (DtoWbsTreeNode) deleteNodeList.get(i);
                newNode.deleteNodeList.add(child.clone());
            }

        }
        newNode.wbsTree = this.wbsTree;
        return newNode;
    }

    public boolean hasActivities() {
        if (children == null) {
            children = new ArrayList();
        }
        for (int i = 0; i < children.size(); i++) {
            ITreeNode currChild = (ITreeNode) children.get(i);
            IDtoWbsActivity dataBean = (IDtoWbsActivity) currChild.
                                       getDataBean();
            if (dataBean.isActivity()) {
                return true;
            }
        }
        return false;
    }

    public List listAllChildrenByPreOrder() {
        List result = new ArrayList();
        List children = this.children();
        if (children == null || children.size() == 0) {
            return result;
        }
        for (int i = 0; i < children.size(); i++) {
            ITreeNode child = (ITreeNode) children.get(i);
            result.add(child);
            result.addAll(child.listAllChildrenByPreOrder());
        }
        return result;
    }

    public List listAllChildrenByPostOrder() {
        List result = new ArrayList();
        List children = this.children();
        if (children == null || children.size() == 0) {
            return result;
        }
        for (int i = children.size() - 1; i >= 0; i--) {
            ITreeNode child = (ITreeNode) children.get(i);
            List grandChild = child.listAllChildrenByPreOrder();
            Collections.reverse(grandChild);
            result.addAll(grandChild);
            result.add(child);
        }
        return result;
    }

    public int getLevel() {
        //根节点的Level设为1
        if (parent == null) {
            return ROOT_LEVEL;
        } else {
            return parent.getLevel() + 1;
        }
    }


    public ITreeNode[] getPath() {
        return getPathToRoot(this, 0);
    }

    public ITreeNode[] getPathToRoot(ITreeNode aNode, int depth) {
        ITreeNode[] retNodes;
        if (aNode == null) {
            if (depth == 0) {
                return null;
            } else {
                retNodes = new ITreeNode[depth];
            }
        } else {
            depth++;
            retNodes = getPathToRoot(aNode.getParent(), depth);
            retNodes[retNodes.length - depth] = aNode;
        }
        return retNodes;
    }

}
