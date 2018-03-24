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
         * �����ǵ���ʽ�˵������ӡ�
         * �������Ҫ����ʽ�˵���ֻ��򵥵ط���null����
         */
        PopupMenu menu=new PopupMenu();
        MenuItem item1=new MenuItem();
        item1.setLabel("��ʾǰ����");
        MenuItem item2=new MenuItem();
        item2.setLabel("��ʾ״̬");
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
