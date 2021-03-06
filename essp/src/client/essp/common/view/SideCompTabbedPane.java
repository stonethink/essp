package client.essp.common.view;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import client.essp.common.view.ui.MLWindowsTabbedPaneUI;
import client.essp.common.view.ui.MessageUtil;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Xuxi.Chen
 * @version 1.0
 */

public class SideCompTabbedPane extends JTabbedPane {
    private java.awt.Component sideComp = new JPanel();
    public SideCompTabbedPane(){
        this.setUI(new MLWindowsTabbedPaneUI());
    }
    public Component getSideComp(){
        return sideComp;
    }
    public void setTabLayoutPolicy(int iValue){
        super.setTabLayoutPolicy(iValue);
        if(iValue ==JTabbedPane.SCROLL_TAB_LAYOUT){
            super.updateUI();
        }
    }
    public void setSideComp(Component sideComp) {
        Component oldComp = this.sideComp;
        this.sideComp = sideComp;
        if(oldComp != sideComp){
            try {
                //super.add
                super.remove( oldComp ); ///add by xh
                super.addImpl(sideComp, null, -1);
            }
            catch (Exception ex) {
            }
        }
    }

    public static void main(String[] args) {
        SideCompTabbedPane sideCompTabbedPane1 = new SideCompTabbedPane();
        java.awt.Component aComp = new javax.swing.JComboBox(new Object[]{"--select--","Number 1","Number 2"});
        sideCompTabbedPane1.setSideComp(aComp);
        sideCompTabbedPane1.add(new javax.swing.JLabel("aLabel1"),"test1");
        sideCompTabbedPane1.add(new javax.swing.JLabel("aLabel2"),"test2");

        sideCompTabbedPane1.add( new JPanel() , "test panel" );
        sideCompTabbedPane1.add( new JPanel() );

        sideCompTabbedPane1.setPreferredSize(new Dimension(400,400));
        javax.swing.JOptionPane.showMessageDialog(null,sideCompTabbedPane1);
        System.exit(0);
    }
}
