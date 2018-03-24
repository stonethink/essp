package client.essp.pms;

import c2s.dto.ITreeNode;
import com.wits.util.Parameter;
import client.essp.pms.activity.AbstractActivityEvent;
import java.awt.PopupMenu;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import client.essp.pms.activity.VwActivityPopup;
import com.wits.util.TestPanel;

public class ActivityTreeTest extends AbstractActivityEvent {
    public ActivityTreeTest() {
    }

    public void onSelectedNode(ITreeNode node) {
        System.out.println("on select:"+node.toString());
    }

    public Parameter getParameter() {
        Parameter param=new Parameter();
        param.put(AbstractActivityEvent.ACCOUNT_RID,new Long(1));
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
//        ActivityTreeTest eventHandle=new ActivityTreeTest();
//        VwActivityPopup popup=new VwActivityPopup(eventHandle);
//        popup.setVisible(true);
    }
}
