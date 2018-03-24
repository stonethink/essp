package client.framework.view.vwcomp;

import c2s.dto.ITreeNode;
import java.awt.PopupMenu;
import com.wits.util.Parameter;

public interface IVWTreeTablePopupEvent extends IVWPopupEditorEvent {
    /**
     * È¡µÃParameter
     * @return Long
     */
    Parameter getParameter();

    /**
     * When a node of the tree is selected , this action will be invoked
     * @param node ITreeNode
     */
    void onSelectedNode(ITreeNode selectedNode);

    //void onDbClickNode(ITreeNode selectedNode);
    //void onRightClickNode(ITreeNode selectedNode);

    /**
     * When mouse right click on a node, this action will be invoked.
     * A popup menu will occus
     * @return PopupMenu
     */
    PopupMenu getPopupMenu();
}
