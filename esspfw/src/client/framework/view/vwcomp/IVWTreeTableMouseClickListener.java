package client.framework.view.vwcomp;

import java.awt.event.MouseEvent;
import c2s.dto.ITreeNode;

public interface IVWTreeTableMouseClickListener {
    public final static int POSITION_TREE=1;
    public final static int POSITION_TABLE=2;
    /**
     * @param e MouseEvent 鼠标事件对象
     * @param node ITreeNode 鼠标点击影响的树节点
     * @param positionType int 点击位置。表示当前是点击在树上，还是点击在表格上
     */
    void onMouseClick(MouseEvent e,ITreeNode node,int positionType);
}
