package client.essp.pms;

import c2s.dto.ITreeNode;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import client.essp.pms.wbs.VwWbsPopup;
import java.awt.MenuItem;
import com.wits.util.Parameter;
import client.essp.pms.wbs.AbstractWbsEvent;

public class WbsTreeTest extends AbstractWbsEvent {
    public WbsTreeTest() {
    }

    public void onSelectedNode(ITreeNode node) {
        System.out.println("on select:"+node.toString());
    }

    public Parameter getParameter() {
        Parameter param=new Parameter();
        param.put(AbstractWbsEvent.ACCOUNT_RID,new Long(1));
        return param;
    }

    public PopupMenu getPopupMenu() {
        /**
         * 这里是弹出式菜单的例子。
         * 如果不需要弹出式菜单，只需简单地返回null即可
         */
        PopupMenu menu=new PopupMenu();
        MenuItem item1=new MenuItem();
        item1.setLabel("显示前后置");
        MenuItem item2=new MenuItem();
        item2.setLabel("显示状态");
        menu.add(item1);
        menu.add(item2);
        return menu;
    }

    public boolean onClickOK(ActionEvent e) {
        return true;
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

    public static void main(String[] args) {
        WbsTreeTest eventHandle=new WbsTreeTest();
        VwWbsPopup popup=new VwWbsPopup(eventHandle);
        popup.setVisible(true);
    }

}
