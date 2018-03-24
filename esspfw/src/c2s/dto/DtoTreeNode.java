package c2s.dto;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class DtoTreeNode implements ITreeNode{
    //类版本标记,与CBS的DtoSubject序列化有关,不能任意修改
    private static final long serialVersionUID = 5738853728597756776L;
    List children = new ArrayList();
    ITreeNode parent;
    Object dataBean;

    //This list keep the node that is deleted.
    List deleteNodeList = new ArrayList();

    public DtoTreeNode(Object dataBean) {
        this.dataBean = dataBean;
    }

    public void setDataBean(Object dataBean){
        this.dataBean = dataBean;
    }

    public Object getDataBean(){
        return this.dataBean;
    }

    public ITreeNode getChildAt(int index) {
        if (children == null) {
            throw new ArrayIndexOutOfBoundsException("node has no children");
        }
        return (ITreeNode) children.get(index);
    }

    public int getChildCount() {
        if (children == null) {
            return 0;
        } else {
            return children.size();
        }
    }

    public int getIndex(ITreeNode aChild) {
        if (aChild == null) {
            throw new IllegalArgumentException("argument is null");
        }

        if( children == null ){
            return -1;
        }

        for( int i = 0; i < getChildCount(); i++ ){
            if( aChild.equals( children.get(i) ) == true ){
                return i;
            }
        }

        return -1;
    }

    public List children() {
        if( children == null ){
            children = new ArrayList();
        }
        return children;
    }

    public boolean isLeaf(){
        return getChildCount() <= 0;
    }

    public ITreeNode getParent(){
        return parent;
    }

    public void setParent( ITreeNode parent ){
        this.parent = parent;
    }

    public void addChild( ITreeNode child ){
        this.addChild( this.getChildCount(), child );
    }

    //toDo:新加入的子结点的op设不设?
    public void addChild( int index, ITreeNode child ){
        addChild( index, child ,IDto.OP_INSERT );
    }

    public void addChild( ITreeNode child, String op ){
        this.addChild( this.getChildCount(), child, op );
    }

    //toDo:新加入的子结点的op设不设?
    public void addChild( int index, ITreeNode child , String op ){
        if( children == null ){
            children = new ArrayList();
        }
        child.setParent( this );
        if( child.getDataBean() instanceof IDto ){
            ( (IDto)child.getDataBean() ).setOp( op );
        }
//        child.setOp( op );

        children.add( index, child );
    }

    /**
     *
     * @param node ITreeNode
     * @param simpleDelete boolean ---- 是否在deleteNodeList中记住删除的节点
     */
    public void deleteChild( ITreeNode node, boolean remeberDelete ){
        int i = this.getIndex(node);
        if (i >= 0 && i < this.getChildCount()) {
            children.remove(i);

            if( remeberDelete == true ){
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

    public void deleteChild( ITreeNode node ){
         deleteChild( node, true );
    }

    public String toString(){
        return this.getDataBean().toString();
    }

    public List getDeleteNodeList(){
        return this.deleteNodeList;
    }

    public boolean isChanged(){
        if( this.getDataBean() instanceof IDto == true ){
            IDto dataBean = (IDto)this.getDataBean();
            if( dataBean.isChanged() == true ){
                return true;
            }
        }

//        if (IDto.OP_NOCHANGE.equals(this.getOp()) == false) {
//            return true;
//        }
//
//        if( this.getDataBean() instanceof IDto ){
//            if( ( (IDto)this.getDataBean() ).isModify() ){
//                return true;
//            }
//
//        }
        if( this.getDeleteNodeList().size() > 0 ){
            return true;
        }

        for( int i = 0 ;i < this.getChildCount(); i ++ ){
            DtoTreeNode child = (DtoTreeNode)this.getChildAt(i);
            if( child.isChanged() == true ){
                return true;
            }
        }

        return false;
    }
//    /* add by xr
//    */
//    public List listAllChildren(){
//        List result = new ArrayList();
//        List children = this.children();
//        if (children == null || children.size() == 0)
//            return result;
//        for (int i = 0; i < children.size(); i++) {
//            ITreeNode child = (ITreeNode) children.get(i);
//            result.add(child);
//            result.addAll(child.listAllChildren());
//        }
//        return result;
//    }

    public int getLevel() {
        //根节点的Level设为1
        if(parent == null)
            return ROOT_LEVEL;
        else
            return parent.getLevel() + 1;
    }

    public static void main(String[] args){
        DtoTreeNode root = new DtoTreeNode("Root");
        DtoTreeNode node1 = new DtoTreeNode("node1");
        DtoTreeNode node2 = new DtoTreeNode("node2");

        root.addChild(node1);
        node1.addChild(node2);

        System.out.println("root level:"+root.getLevel());
        System.out.println("node1 level:"+node1.getLevel());
        System.out.println("node2 level:"+node2.getLevel());
    }

    public List listAllChildrenByPreOrder() {
        List result = new ArrayList();
        List children = this.children();
        if (children == null || children.size() == 0)
            return result;
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
        if (children == null || children.size() == 0)
            return result;
        for (int i = children.size() - 1; i >= 0 ; i --) {
            ITreeNode child = (ITreeNode) children.get(i);
            List grandChild = child.listAllChildrenByPreOrder();
            Collections.reverse(grandChild);
            result.addAll(grandChild);
            result.add(child);
        }
        return result;
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
