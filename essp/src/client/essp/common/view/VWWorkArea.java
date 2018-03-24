package client.essp.common.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import client.framework.view.IVWWorkArea;
import client.framework.view.event.StateChangedListener;
import com.wits.util.Parameter;
import org.apache.log4j.Category;
import java.util.HashMap;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author not attributable
 * @version 1.0
 */

public class VWWorkArea extends VWJPanel implements IVWWorkArea {
    static{
        UISetting.settingUIManager();
    }

    protected static Category log = Category.getInstance("client");

    StateChangedListener stateChangedListener; //listener监听选项卡变化事件
    public SideCompTabbedPane upTabbedPane; //选项卡
    //如果workArea是简单的面板, 那么不会加入upTabbedPane
    private boolean hasAddTabbedPane = false;

    //显示的卡片
    List panelList = new ArrayList();

    int lastSelectedIndex = -1;
    boolean stateChangedLocked = false;

    public HashMap tabMap = new HashMap();
    public List tabIndex = new ArrayList();

    public VWWorkArea() {
        try {
            init();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Component initialization
    private void init() throws Exception {
        upTabbedPane = new SideCompTabbedPane();

        upTabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                //事件是否被锁住
                if (stateChangedLocked == false) {

                    //save last selected workarea
                    if (lastSelectedIndex != -1) {
                        VWWorkArea lastSelectedPanel = (VWWorkArea) panelList.
                            get(lastSelectedIndex);

                        //切换前,先保存之前的workArea
                        lastSelectedPanel.saveWorkArea();
                        if (lastSelectedPanel.isSaveOk() == false) {

                            //如果保存没有成功,则不容许切换
                            stateChangedLocked=true;
                            upTabbedPane.setSelectedIndex(lastSelectedIndex);
                            stateChangedLocked=false;
                            return;
                        }
                    }
                    lastSelectedIndex = upTabbedPane.getSelectedIndex();

                    VWWorkArea p = (VWWorkArea) panelList.get(upTabbedPane.
                        getSelectedIndex());
                    p.refreshWorkArea(); //refresh new selected panel
                    setButtonPanel(p.getButtonPanel()); //将新的workarea的buttonPanel设到肩膀上去
    //                log.debug("select " + p.getClass().getName());

                    if (stateChangedListener != null) {
                        stateChangedListener.processStateChanged();
                    }
                }
            }
        });

        this.setBorder(BorderFactory.createEmptyBorder());
    }

    //加一个卡片,默认的是没有滚动条
    public void addTab(String title, VWWorkArea panel) {
        this.addTab(title, panel, false);
    }

    /**
     *
     * @param title String  卡片的title
     * @param panel VWJPanel  卡片的内容
     * @param bScroll boolean  卡片的内容显示不下时，是否出现滚动条
     */

    //加一个卡片
    public void addTab(String title, VWWorkArea panel, boolean bScroll) {
        if (hasAddTabbedPane == false) {
            this.add(upTabbedPane, BorderLayout.CENTER);
            hasAddTabbedPane = true;
        }

        this.panelList.add(panel);

        VWWorkArea p = new VWWorkArea();
//        p.setName(title);
        p.addPanel(panel, bScroll);
        panel.setName(title);
        this.upTabbedPane.addTab(title, p);

        String newTitle=title;
        if(newTitle==null || newTitle.trim().equals("")) {
            newTitle=panel.toString();
        }
        tabMap.put(newTitle,panel);
        tabIndex.add(newTitle);
    }

    public VWWorkArea getTab(String title) {
        return (VWWorkArea)tabMap.get(title);
    }

    public int getTabIndex(String title) {
        for(int i=0;i<tabIndex.size();i++) {
            if(tabIndex.get(i).equals(title)) {
                return i;
            }
        }
        return -1;
    }

    public VWWorkArea getTab(int index) {
        return (VWWorkArea)tabMap.get(tabIndex.get(index));
    }


    public int getTabCount() {
        return tabIndex.size();
    }

    public void showAllTabs() {
        for(int i=0;i<tabIndex.size();i++) {
            this.getTab(i).setVisible(true);
        }
    }

    public void enableAllTabs() {
        for(int i=0;i<tabIndex.size();i++) {
            this.setEnabledAt(i,true);
        }
    }

    public void disableAllTabs() {
        for(int i=0;i<tabIndex.size();i++) {
            this.setEnabledAt(i,false);
        }
    }

    public void replaceTab(int i, VWWorkArea newPanel) {
        String newTabTitle = ((VWWorkArea) panelList.get(i)).getName();
        replaceTab(i, newPanel, newTabTitle,false);
    }

    public void replaceTab(int i, VWWorkArea newPanel, boolean bScroll) {
        String newTabTitle = ((VWWorkArea) panelList.get(i)).getName();
        replaceTab(i, newPanel, newTabTitle, bScroll);
    }


    public void replaceTab(int i, VWWorkArea newPanel, String newTabTitle) {
         replaceTab(i, newPanel, newTabTitle, false);
    }

    public void replaceTab(int i, VWWorkArea newPanel, String newTabTitle, boolean bScroll){
        VWWorkArea p = (VWWorkArea)this.upTabbedPane.getComponent(i);
        p.removeAll();
        panelList.remove(i);

        //p.add(newPanel, null);
        p.addPanel(newPanel, bScroll);

        setButtonPanel(newPanel.getButtonPanel());
        panelList.add(i, newPanel);

        setTabTitle(i, newTabTitle);
        this.updateUI();

        String oldTabTitle=(String)tabIndex.get(i);
        tabMap.remove(oldTabTitle);
        tabMap.put(newTabTitle,newPanel);
        tabIndex.add(i,newTabTitle);
    }

    public void replaceTab(String title, VWWorkArea newPanel) {
        String newTabTitle = title;
        replaceTab(title,newPanel,newTabTitle);
    }

    public void replaceTab(String title, VWWorkArea newPanel, String newTabTitle) {
        int index=getTabIndex(title);
        replaceTab(index,newPanel,newTabTitle);
    }

    public void setTabTitle(int i, String tabTitle) {
        this.upTabbedPane.setTitleAt(i, tabTitle);
        ((VWWorkArea) panelList.get(i)).setName(tabTitle);

        String oldTabTitle=(String)tabIndex.get(i);
        Object obj=tabMap.remove(oldTabTitle);
        String newTitle=tabTitle;
        if(newTitle==null || newTitle.trim().equals("")) {
            newTitle=obj.toString();
        }

        tabMap.put(newTitle,obj);
        tabIndex.add(i,newTitle);
    }

    public void setButtonPanel(JPanel upButtonPanel) {
        upTabbedPane.setSideComp(upButtonPanel);
    }

    public int getSelectedIndex() {
        return upTabbedPane.getSelectedIndex();
    }

    public String getSelectedTitle() {
        int i=getSelectedIndex();
        return (String)tabIndex.get(i);
    }

    public String getTabTitle(int index) {
        return (String)tabIndex.get(index);
    }

    public JPanel getSelectedPanel() {
        return (JPanel) upTabbedPane.getSelectedComponent();
    }

    public VWWorkArea getSelectedWorkArea() {
        if (panelList != null) {
            if( upTabbedPane.getSelectedIndex() >= 0 ){
                return (VWWorkArea) panelList.get(upTabbedPane.getSelectedIndex());
            }
        }

        return null;
    }

    public VWWorkArea getWorkArea(int tabIndex) {
        if (panelList != null) {
            return (VWWorkArea) panelList.get(tabIndex);
        } else {
            return null;
        }
    }

    public VWWorkArea getWorkArea(String tabTitle) {
        if (panelList != null) {
            for (int i = 0; i < panelList.size(); i++) {
                if (((VWWorkArea) panelList.get(i)).getName().equals(
                        tabTitle) == true) {
                    return (VWWorkArea) panelList.get(i);
                }
            }
        }

        return null;
    }

    public void setSelectedIndex(int i) {
        this.upTabbedPane.setSelectedIndex(i);
    }

    public void setSelected(String title) {
        int index=this.getTabIndex(title);
        setSelectedIndex(index);
    }

    public void addStateChangedListener(StateChangedListener
                                        stateChangedListener) {
        this.stateChangedListener = stateChangedListener;
    }

    public void setParameter(Parameter parameter) {
    }

    public void refreshWorkArea() {
    }

    public void saveWorkArea() {
    }

    public boolean isSaveOk() {
        return true;
    }

    public void setEnabledAt(int index, boolean enabled) {
        this.upTabbedPane.setEnabledAt(index, enabled);
    }

    public void setEnabledAt(String title, boolean enabled) {
        int index=this.getTabIndex(title);
        this.upTabbedPane.setEnabledAt(index, enabled);
    }


    public void showTabOnly(String title) {
        for(int i=0;i<tabIndex.size();i++) {
            String oldtitle=(String)tabIndex.get(i);
            if(oldtitle.equals(title)) {
                this.getTab(oldtitle).setVisible(true);
            } else {
                this.getTab(oldtitle).setVisible(false);
            }
        }
    }

    public void showTabOnly(int index) {
        for(int i=0;i<tabIndex.size();i++) {
            if(i==index) {
                this.getTab(i).setVisible(true);
            } else {
                this.getTab(i).setVisible(false);
            }
        }
    }


    public void enableTabOnly(int index) {
        for(int i=0;i<tabIndex.size();i++) {
            if(i==index) {
                this.setEnabledAt(i,true);
            } else {
                this.setEnabledAt(i,false);
            }
        }
    }

    public void enableTabOnly(String title) {
        for(int i=0;i<tabIndex.size();i++) {
            String oldtitle=(String)tabIndex.get(i);
            if(oldtitle.equals(title)) {
                this.setEnabledAt(i,true);
            } else {
                this.setEnabledAt(i,false);
            }
        }
    }

    public void setTabVisible(int index,boolean isShow) {
        this.getTab(index).setVisible(isShow);
    }

    public boolean isTabVisible(int index) {
        return this.getTab(index).isVisible();
    }

    public void setTabVisible(String title,boolean isShow) {
        this.getTab(title).setVisible(isShow);
    }

    public boolean isTabVisible(String title) {
        return this.getTab(title).isVisible();
    }

    public void removeTab(String title) {
        int index=this.getTabIndex(title);
        this.upTabbedPane.removeTabAt(index);
        tabMap.remove(title);
        tabIndex.remove(title);
    }

    public void removeTab(int index) {
        String title=(String)tabIndex.get(index);
        this.upTabbedPane.removeTabAt(index);
        tabMap.remove(title);
        tabIndex.remove(title);
    }

    /**
     * 取得父窗口句柄
     * @return Frame
     */
    protected java.awt.Frame getParentWindow() {
        java.awt.Container c = this.getParent();

        while (c != null) {
            if (c instanceof java.awt.Frame) {
                return (java.awt.Frame) c;
            }

            c = c.getParent();
        }

        return null;
    }

}
