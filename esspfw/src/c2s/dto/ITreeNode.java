package c2s.dto;

import java.util.List;
import java.io.Serializable;

public interface ITreeNode extends Serializable{
    //树根的Level设置成1
    public static final int ROOT_LEVEL = 1;
//    public List getChildList();

    /**
     * Returns the child <code>TreeNode</code> at index
     * <code>childIndex</code>.*/
    ITreeNode getChildAt(int childIndex);

    /**
     * Returns the number of children <code>TreeNode</code>s the receiver
     * contains.
     */
    int getChildCount();

    /**
     * Returns the parent <code>TreeNode</code> of the receiver.
     */
    ITreeNode getParent();

    /**
     * set the parent <code>TreeNode</code> of the receiver.*/
    void setParent(ITreeNode parent);

    /**
     * Returns the index of <code>node</code> in the receivers children.
     * If the receiver does not contain <code>node</code>, -1 will be
     * returned.*/
    int getIndex(ITreeNode node);

    /**
     * Returns true if the receiver allows children.
     */
//    boolean getAllowsChildren();

    /**
     * Returns true if the receiver is a leaf.
     * If it has 0 child, it's a leaf.
     */
    boolean isLeaf();

    /**
     * Returns the children of the receiver as an <code>Enumeration</code>.*/
    List children();

    /**
     * Add a new child.
     * This operation will set the child's op to IDto.OP_INSERT
     */
    void addChild(ITreeNode newChild);
    void addChild(int index, ITreeNode newChild);

    /**
     * @param newChild ITreeNode - the node to add
     * @param op String - set the added node's op to op
     */
    void addChild(ITreeNode newChild, String op);
    void addChild(int index, ITreeNode newChild, String op);


    /**
     * Delete a child.
     * This operation will set the child's op to IDto.OP_DELETE
     */
    void deleteChild(ITreeNode node);


    public void deleteChild( ITreeNode node, boolean remeberDelete );

    public Object getDataBean();

    public void setDataBean(Object dataBean);
    /**
     * add by xr
     * list all children of the node and all "grandchildren"
     * 先序遍历方式遍历所有的子节点和孙子节点
     * @return List
     */
    public List listAllChildrenByPreOrder();
    /**
     * 后序遍历方式遍历所有的子节点和孙子节点
     * @return List
     */
    public List listAllChildrenByPostOrder();
    //判断当前节点在树中在第几层,
    public int getLevel();
}
