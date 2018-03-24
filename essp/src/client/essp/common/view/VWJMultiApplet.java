package client.essp.common.view;

import client.framework.view.vwcomp.*;
import java.util.ResourceBundle;
import com.wits.util.Parameter;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.util.Enumeration;

public class VWJMultiApplet extends VWJApplet {
    private HideTab mainPanel;

    public VWJMultiApplet() {
    }

    /**
     *
     * @todo Implement this client.framework.view.vwcomp.VWJApplet method
     */
    public void initUI() {
        ////System.out.println("VWJMultiApplet.initUI()");
        UISetting.settingUIManager();
        ////System.out.println("Panel.background"+javax.swing.UIManager.get("Panel.background"));

        mainPanel = new HideTab();
        this.setContentPane(mainPanel);
        String cfg = getParameter("cfg");
        //System.out.println("cfg=[" + cfg + "]");
        mainPanel.loadTabs(cfg);
    }

    //Applet�ṩ��JavaScript���õķ���
    public void enableTab(int index) {
        //UISetting.settingUIManager();
        mainPanel.enableTab(index - 1);
    }

    //Applet�ṩ��JavaScript���õķ���
    public void enableTab(String title) {
        //UISetting.settingUIManager();
        mainPanel.enableTab(title);
    }

    /**
     *
     * <p>Title: </p>
     *
     * <p>Description: </p>
     *
     * <p>Copyright: Copyright (c) 2005</p>
     *
     * <p>Company: </p>
     *
     * @author not attributable
     * @version 1.0
     */
    class HideTab extends JPanel {
        public HashMap tablist = new HashMap();
        public Vector indexVec = new Vector();
        CardLayout cardLayout1 = new CardLayout();
        JPanel jPanel2 = new JPanel();

        public HideTab() {
            try {
                jbInit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void removeAllTab() {
            this.removeAll();
            tablist.clear();
            indexVec.clear();
        }

        public void addTab(String name, JPanel tabpanel) {
            tablist.put(name, tabpanel);
            indexVec.add(name);
            this.add(tabpanel, name);
        }

        public JPanel getTab(String name) {
            JPanel tabpanel = (JPanel) tablist.get(name);
            return tabpanel;
        }

        public JPanel enableTab(String name) {
//            //System.out.println("��Ҫ��ʾ��������ƣ�" + name);
            JPanel tabpanel = (JPanel) tablist.get(name);
            if (tabpanel != null) {
                ////System.out.println("�Ѿ��ҵ������'" + name + "'");
                if (tabpanel != null && tabpanel instanceof VWWorkArea) {
                    ////System.out.println( name + "��忪ʼrefreshWorkArea:");
                    ((VWWorkArea) tabpanel).refreshWorkArea();
                    ////System.out.println(name + "���refreshWorkArea���");
                }
                cardLayout1.show(this, name);
            } else {
                ////System.out.println("δ�ҵ����'" + name + "'");
            }

            this.updateUI();
            ////System.out.println("��ʾ�������");
            return tabpanel;
        }

        public JPanel enableTab(int index) {
            if (indexVec.size() <= index || index < 0) {
                ////System.out.println("�Ҳ�����壬������=" + index);
                return null;
            } else {
                String name = (String) indexVec.get(index);
                return enableTab(name);
            }
        }

        public void loadTabs(String conf) {
            ResourceBundle res = null;
            if (conf != null && !conf.trim().equals("")) {
                ////System.out.println("cfg has been setup. cfg=[" + conf + "]");
                res = ResourceBundle.getBundle(conf);
            } else {
                ////System.out.println("cfg not setup. read default cfg=[defaultTabCfg]");
                res = ResourceBundle.getBundle("defaultTabCfg");
            }

            String loadMethod = null;
            try {
                loadMethod = res.getString("TabbedPanel.loadMethod");
            } catch (Exception ex1) {
                loadMethod = "SEQUENCE";
            }
            removeAllTab();
            if (loadMethod == null || loadMethod.equals("SEQUENCE")) {
                try {
                    String tabCountString = res.getString("TabbedPanel.count");
					int tabCount = 0;

                    //ȡ����Ƭ��
                    tabCount = Integer.parseInt(tabCountString);

                    for (int i = 1; i <= tabCount; i++) {
                        //ȡ��ÿ����Ƭ������
                        String tabTitle = res.getString("TabbedPanel." + i +
                                ".title");
                        String tabClass = res.getString("TabbedPanel." + i +
                                ".class");
                        JPanel panel = (JPanel) Class.forName(tabClass).
                                       newInstance();
                        //System.out.println("���ڼ��ص�" + i + "����� '" + tabTitle +
//                                 "',class=" + tabClass);
                        this.addTab(tabTitle, panel);

                        //System.out.println("pre-setParameter��"+tabTitle);

                        /**
                         * ��ҳ���ϵĲ�������������
                         */
//                        if (panel instanceof IVWAppletParameter &&
//                            panel instanceof VWWorkArea) {
                        if (panel instanceof VWWorkArea) {
                            Parameter parameter = new Parameter();
                            if (panel instanceof IVWAppletParameter){
                                String[][] params = ((IVWAppletParameter) panel).
                                        getParameterInfo();
                                for (int j = 0; j < params.length; j++) {
                                    String paramName = params[j][0];
                                    String paramValue = getParameter(paramName);
                                    parameter.put(paramName, paramValue);
                                }
                            }
                            //System.out.println("setParameter��"+tabTitle);
                            try {
                            	((VWWorkArea) panel).setParameter(parameter);
                            } catch(Exception e) {
                            	e.printStackTrace();
                            	return;
                            }
                        }

                    }

//                    String defaultTab = res.getString("TabbedPanel.defaultTab");
//                    if (defaultTab != null && !defaultTab.trim().equals("")) {
//                        this.enableTab(defaultTab);
//                    } else {
//                        this.enableTab(0);
//                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    Enumeration keys = res.getKeys();
                    if (keys != null) {
                        while (keys.hasMoreElements()) {
                            String key = (String) keys.nextElement();
                            key = key.trim();
                            if (key.startsWith("TabbedPanel.") &&
                                key.endsWith(".class")) {
                                int startIndex = 12;
                                int endIndex = key.length() - 6;
                                String tabTitle = key.substring(startIndex,
                                        endIndex);
                                if (tabTitle != null) {
                                    String tabClass = res.getString(key);
                                    JPanel panel = (JPanel) Class.forName(
                                            tabClass).
                                            newInstance();
                                    //System.out.println("���ڼ��ص�" + (indexVec.size() + 1) +
//                                             "����� '" + tabTitle +
//                                             "',class=" + tabClass);
                                    this.addTab(tabTitle, panel);

                                    //System.out.println("2. pre-setParameter��"+tabTitle);

                                    /**
                                     * ��ҳ���ϵĲ�������������
                                     */
//                                    if (panel instanceof IVWAppletParameter &&
//                                        panel instanceof VWWorkArea) {
                                      if (panel instanceof VWWorkArea) {
                                        Parameter parameter = new Parameter();
                                        if (panel instanceof IVWAppletParameter){
                                            String[][] params = ((
                                                    IVWAppletParameter) panel).
                                                    getParameterInfo();

                                            for (int j = 0; j < params.length;
                                                    j++) {
                                                String paramName = params[j][0];
                                                String paramValue =
                                                        getParameter(
                                                        paramName);
                                                parameter.put(paramName,
                                                        paramValue);
                                            }
                                        }
                                        //System.out.println("2. setParameter��"+tabTitle);
                                        try {
                                        	((VWWorkArea) panel).setParameter(parameter);
                                        } catch(Exception e) {
                                        	e.printStackTrace();
                                        	return;
                                        }
                                    }
                                }
                            }
                        }

//                        String defaultTab = res.getString(
//                                "TabbedPanel.defaultTab");
//                        if (defaultTab != null && !defaultTab.trim().equals("")) {
//                            this.enableTab(defaultTab);
//                        } else {
//                            this.enableTab(0);
//                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
            //��ӳ�ʼ�հ׿�Ƭ
            this.addTab("", new VWWorkArea());
        }

        private void jbInit() throws Exception {
            this.setLayout(cardLayout1);
            this.add(jPanel2, "jPanel2");
        }
    }


    class Log {

    }
}
