package client.essp.common.appletTabbed;

import java.io.UnsupportedEncodingException;
import java.util.Vector;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import client.essp.common.view.UISetting;
import com.uic.side.SideCompTabbedPane;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Enovation</p>
 * @author Xuxi.Chen
 * @version 1.0
 */

public class AppletTabbed extends JApplet {
//    static {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            UISetting.SettingUIManager();
//        }
//        catch (Exception ex) {
//        }
//
//    }

  private boolean isStandalone = false;
  public final static int MAX_PARA_NUM = 50; //������
  String[] vars;
  String strName;
  String strDate;
  private boolean[] hidePanelFlags;

  String selectBoxLabel;
  String selectBoxLabelFont;
  String optionFont;
  boolean isSelectBoxVisible = false;
  Vector options;
  String selectedValue ; //��ѡ�е��(add by xh)

//������ʶ������ѡ���¼��Ƿ���setSelectedItem���������ģ���������������¼�
//������û������ģ�������¼�
  private boolean isSelectEventEnabled = true; //add by xh

  String[] buttonNames; //��ť������
  String[] buttonActions; //��ť�Ķ���
  JButton[] arrButton;
  boolean buttSameWidth = true;

  SideCompTabbedPane jTabbedPane1;
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel[] jPanels;
  JPanel sideComp;

  Color clrSelectBack = new Color(148, 170, 214);
  Color clrUnselectBack = new Color(0x94, 0xaa, 0xd6);
  FlowLayout flowLayout1 = new FlowLayout();

  private final static String BUTTON_FONT_NAME="����";

  /**
   * ����ѡ������ʾ״̬
   */
  public void setSelectBoxVisible() {
    isSelectBoxVisible = !isSelectBoxVisible;
  }

  /**
   * ����ĳ����ťΪ����״̬
   * @param index ��1��ʼ�İ�ť������
   */
  public void setButtonHide(int index) {
    setButtonVisible(index, false);
  }

  /**
   * ����ĳ����ƬΪ��ʾ״̬
   * @param index ��1��ʼ�İ�ť������
   */
  public void setTabShow(int index) {
    index--;
    if (!this.hidePanelFlags[index]) { //alread show
      return;
    }
    int iPos = getNewPos(index);

    this.hidePanelFlags[index] = false;
    this.jTabbedPane1.setVisible(true);
    this.jTabbedPane1.add(jPanels[index], vars[index], iPos);

//        this.jTabbedPane1.setSelectedIndex(this.getNewPos(
//            this.getOldTab(this.jTabbedPane1.getSelectedIndex())
//            ));
  }

  private int getNewPos(int index) {
    //������TAB�е�λ��
    int iPos = index;
    for (int i = 0; i < index; i++) {
      if (this.hidePanelFlags[i]) {
        iPos--;
      }
    }
    return iPos;
  }

  /**
   * ����ĳ����ƬΪ����״̬
   * @param index ��1��ʼ�İ�ť������
   */
  public void setTabHide(int index) {
    //this.jTabbedPane1.getComponentAt(index).setVisible(false);
    index--;
    if (this.hidePanelFlags[index]) { //alread hide
      return;
    }
    int iPos = getNewPos(index);
    this.hidePanelFlags[index] = true;
    this.jTabbedPane1.remove(iPos);
    if (isHideAll()) {
      this.jTabbedPane1.setVisible(false);
    }
//        this.jTabbedPane1.setSelectedIndex(this.getNewPos(
//            this.getOldTab(this.jTabbedPane1.getSelectedIndex())
//            ));
  }

  private boolean isHideAll() {
    for (int i = 0; i < hidePanelFlags.length; i++) {
      if (!hidePanelFlags[i]) {
        return false;
      }
    }
    return true;
  }

  /**
   * ����ĳ����ťΪ��ʾ״̬
   * @param index ��1��ʼ�İ�ť������
   */
  public void setButtonShow(int index) {

    setButtonVisible(index, true);
  }

  /**
   * ����ĳ����ťΪ����״̬
   * @param index ��1��ʼ�İ�ť������
   */
  public void setButtonHide(String strIndex) {
    setButtonVisible(Integer.parseInt(strIndex), false);
  }

  /**
   * ����ĳ����ťΪ��ʾ״̬
   * @param index ��1��ʼ�İ�ť������
   */
  public void setButtonShow(String strIndex) {
    setButtonVisible(Integer.parseInt(strIndex), true);
  }

  /**
   * ����ĳ����ť������/��ʾ
   * @param index ��1��ʼ�İ�ť������
   * visible �Ƿ�����
   *
   */
  public void setButtonVisible(int index, boolean visible) {
    index--;
    if (this.arrButton != null && arrButton.length >= index) {
      arrButton[index].setVisible(visible);
    }

  }

  //Get a parameter value
  public String getParameter(String key, String def) {
    String value = isStandalone ? System.getProperty(key, def) :
        (getParameter(key) != null ? getParameter(key) : def);
    try {
      return new String(value.getBytes(), "GB2312");
    } catch (UnsupportedEncodingException ex) {
      return value;
    }

  }

  //Construct the applet
  public AppletTabbed() {
    try {
      //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      UISetting.settingUIManager();
    } catch (Exception ex) {
    }
  }

  void initPara() {
    //vars ȡ����Ϊparan�Ĳ���
    java.util.Vector v = new Vector();
    for (int i = 0; i < MAX_PARA_NUM; i++) { //��ʼ����Ƭ����
      try {
        String aVar = this.getParameter("param" + i, "");
        if (aVar != null && aVar.length() > 0) {
          v.add(aVar);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    this.vars = new String[v.size()];
    v.toArray(vars);
    try {
      strName = this.getParameter("userName", "");
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      strDate = this.getParameter("date", "");
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      String strSameWidth = this.getParameter("buttonSameWidth", "true");
      buttSameWidth = Boolean.valueOf(strSameWidth).booleanValue();
    } catch (Exception ex) {
      buttSameWidth = true;
    }
    v.clear();
    for (int i = 0; i < MAX_PARA_NUM; i++) {
      try {
        String aButStr = this.getParameter("button" + i, ""); //��ʼ����ť
        if (aButStr != null && aButStr.length() > 0) {
          v.add(aButStr);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    this.buttonNames = new String[v.size()];
    this.buttonActions = new String[v.size()];
    for (int i = 0; i < v.size(); i++) {
      String aStr = (String) v.get(i);
      int iPos = aStr.indexOf(";");
      if (iPos < 0) {
        buttonNames[i] = aStr;
      } else if (iPos >= (aStr.length() - 1)) {
        buttonNames[i] = aStr.substring(0, iPos);
      } else {
        buttonNames[i] = aStr.substring(0, iPos);
        buttonActions[i] = aStr.substring(iPos + 1);
      }
    }

    /**
     * ����ѡ������ʾ״̬������
     */
    try {
      String strSelectBoxVisible = this.getParameter("isSelectBoxVisible", "false");
      isSelectBoxVisible = Boolean.valueOf(strSelectBoxVisible).booleanValue();

      if (isSelectBoxVisible) {
        selectBoxLabel = this.getParameter("selectBoxLabel", "");
        if (selectBoxLabel != null && !selectBoxLabel.trim().equals("")) {
          options = new Vector();
          for (int i = 0; ; i++) { //��ʼ����Ƭ����
            try {
              String labelVar = this.getParameter("optionLabel" + i, "");
              String valueVar = this.getParameter("optionValue" + i, "");
              if (labelVar != null && labelVar.length() > 0 && valueVar != null && valueVar.length() > 0) {
                options.add(new SelectBoxItem(labelVar, valueVar));
              } else {
                break;
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
          }

          if (options.size() == 0) {
            isSelectBoxVisible = false;
          }

          this.selectBoxLabelFont = this.getParameter("selectBoxLabelFont", "Arial,10");
          this.optionFont = this.getParameter("optionFont", "Arial,10");
          this.selectedValue = this.getParameter( "selectedValue", "" );
        } else {
          isSelectBoxVisible = false;
        }
      }
    } catch (Exception ex) {
      isSelectBoxVisible = false;
    }

  }

  //Initialize the applet
  public void init() {
    try {
      UISetting.settingUIManager();
      initPara();
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    super.init();
  }

  //Component initialization
  private void jbInit() throws Exception {
    this.setFont(new java.awt.Font(UISetting.FONT_NAME, Font.BOLD, 12));
    jTabbedPane1 = new SideCompTabbedPane();
    borderLayout1 = new BorderLayout();
    java.awt.Container cotainer = this.getContentPane();
    cotainer.setLayout(borderLayout1);
    cotainer.setBackground(Color.white);
    cotainer.setFont(new java.awt.Font(UISetting.FONT_NAME, Font.BOLD, 12));
    jTabbedPane1.setFont(new java.awt.Font(UISetting.FONT_NAME, Font.BOLD, 12));
    //Border border1;
    //border1 = BorderFactory.createLineBorder(UIManager.getColor("TabbedPane.selected"),5);

    this.jPanels = new JPanel[vars.length];
    this.hidePanelFlags = new boolean[vars.length];
    for (int i = 0; i < vars.length; i++) {
      jPanels[i] = new JPanel();
      hidePanelFlags[i] = false;
      //jPanels[i].setBorder(border1);
      //jPanels[i].setOpaque(true);
      //jPanels[i].setBackground(Color.white);
      jTabbedPane1.add(jPanels[i], vars[i]);
    }
    jTabbedPane1.addChangeListener(new AppletTabbed_jTabbedPane1_changeAdapter(this));

    if (buttonNames.length > 0) {
      sideComp = new JPanel();
      this.jTabbedPane1.setSideComp(sideComp);
      flowLayout1.setHgap(5);
      flowLayout1.setVgap(1);
      sideComp.setLayout(flowLayout1);

      /**
       * ��������ѡ���
       */
      if (this.isSelectBoxVisible) {
        JLabel selectboxLabel = new JLabel(this.selectBoxLabel);
        try {
          String[] strs = this.selectBoxLabelFont.split(",");
          System.out.println("font:["+strs[0]+"]");
          Font font = new java.awt.Font(UISetting.FONT_NAME, Font.BOLD, Integer.parseInt(strs[1]));
          selectboxLabel.setFont(font);
        } catch (Exception e) {
        }
        sideComp.add(selectboxLabel, null);
        JComboBox selectbox = new JComboBox(options);
        try {
          String[] strs = this.optionFont.split(",");
          Font font = new java.awt.Font(strs[0], Font.BOLD, Integer.parseInt(strs[1]));
          selectbox.setFont(font);
        } catch (Exception e) {
        }

        selectbox.setSize(100, 15);

        sideComp.add(selectbox, null);

        selectbox.addItemListener(new ItemListener() {
          /**
           * ʵ��Java Applet�е���JavaScript����
           * @param functionName String JavaScript������
           * @param args Object[]       ���ò���
           */
          protected void callJs(String functionName,
                                Object[] args) {
            JSObject win = JSObject.getWindow(AppletTabbed.this);
            win.call(functionName, args); // Call f() in HTML page
          }

          public void itemStateChanged(ItemEvent e) {
              if (e.getStateChange() == ItemEvent.SELECTED) {
                if (isSelectEventEnabled == true) { //add by xh
                    try {
                        SelectBoxItem item = (SelectBoxItem) e.getItem();
                        String[] args = new String[1];
                        args[0] = item.getValue();
                        callJs("onSelectboxChange", args);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
          }
        });


        setSelectedItem(selectbox);
      }

      arrButton = new JButton[buttonNames.length];
      int maxWidth = 0;
      for (int i = 0; i < buttonNames.length; i++) {
        JButton jButton1 = new JButton();
        jButton1.setText(buttonNames[i]);
        jButton1.setMargin(new Insets(0, 1, 0, 1));
        Font font = jButton1.getFont();
        font = new java.awt.Font(UISetting.FONT_NAME, Font.PLAIN, 12);
        jButton1.setFont(font);
        arrButton[i] = jButton1;
        maxWidth = Math.max(maxWidth, jButton1.getPreferredSize().width);
        jButton1.addActionListener(new AppletTabbed_jButton1_actionAdapter(this, buttonActions[i]));
        sideComp.add(jButton1, null);
      }
      if (buttSameWidth) {
        setButtonSameWidth(arrButton, maxWidth);
      }
    } else { //�ұ�û�а�ť��������ʾ��������ť
      jTabbedPane1.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }
    jTabbedPane1.setPreferredSize(new Dimension(10, 35));
    cotainer.add(jTabbedPane1, BorderLayout.NORTH);
    clrUnselectBack = jTabbedPane1.getBackground();
  }

  private void setSelectedItem( JComboBox comboBox ){
      isSelectEventEnabled = false;

      System.out.println( "selectedValue :" + selectedValue );
      for( int i = 0; i < this.options.size(); i++ ){
          SelectBoxItem item = (SelectBoxItem)options.get(i);
          if( item.getValue() != null ){
              if( item.getValue().equals( selectedValue ) == true ){
                  comboBox.setSelectedItem(item);
                  break;
              }
          }else{
              if( selectedValue == null ){
                  comboBox.setSelectedItem( item );
                  break;
              }
          }
      }

      isSelectEventEnabled = true;
  }

  private void setButtonSameWidth(JButton[] arrButton, int sameWidth) {
    for (int i = 0; i < arrButton.length; i++) {
      JButton aButton = arrButton[i];
      int oldWidth = aButton.getPreferredSize().width;
      Insets inserts = aButton.getMargin();
      inserts.left += (sameWidth - oldWidth) / 2;
      inserts.right += (sameWidth - oldWidth) / 2;
      aButton.setMargin(inserts);
    }
  }

  //Start the applet
  public void start() {
    super.start();
  }

  public void paint(Graphics g) {
    super.paint(g);
    g.drawString(strName, 780, 16);
    g.drawString(strDate, 550, 16);
  }

//    public void repaint(){
//    }
//    boolean stop = false;
//    public void run() {
//        while(!stop){
//            try {
//                Thread.sleep(50);
//            }
//            catch (Exception ex) {
//            }
//            this.repaint();
//        }
//    }

//    //Stop the applet
//    public void paint() {
//    }
//    //Destroy the applet
//    public void destroy() {
//    }
  //Get Applet information
  public String getAppletInfo() {
    return "Applet Information";
  }

  //Get parameter info
  public String[][] getParameterInfo() {
    String[][] pinfo = { {"param0", "String", ""}, {"param1", "String", ""}, {"param2", "String", ""}, {"param3", "String", ""}, {"param4", "String", ""},
        {"name", "String", ""}, {"date", "String", ""},
    };
    return pinfo;
  }

  /**
   * ��ҳ���ã������ĸ����ڱ�ѡ��
   * @param strIndex �ӡ�1����ʼ�����
   */
  public void setSelectIndex(String strIndex) {
    try {
      setSelectIndex(Integer.parseInt(strIndex));
    } catch (NumberFormatException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * ��ҳ���ã������ĸ����ڱ�ѡ��
   * @param strIndex ��1��ʼ�����
   */
  public void setSelectIndex(int index) {
    this.jTabbedPane1.setSelectedIndex(this.getNewPos(index - 1));
  }

  /**
   * ��ҳ���ã�����Tab�ı���
   * @param index �ӡ�1����ʼ�����
   * @param title
   */
  public void setTableTitle(String strIndex, String title) {
    try {
      setTableTitle(Integer.parseInt(strIndex), title);
    } catch (NumberFormatException ex) {
    }
  }

  /**
   * ��ҳ���ã�����Tab�ı���
   * @param index ��1��ʼ�����
   * @param title
   */
  public void setTableTitle(int index, String title) {
    this.jTabbedPane1.setTitleAt(index - 1, title);
    this.repaint();
  }

  void jTabbedPane1_stateChanged(ChangeEvent e) {
    try {
      int index = this.jTabbedPane1.getSelectedIndex();
      //��Ϊ���������⣬�ҳ�ԭλ��
      int iPos = getOldTab(index);
      //int iCount = this.jTabbedPane1.getComponentCount();
      JSObject window = JSObject.getWindow(this);
      window.eval("turnto(" + (iPos + 1) + ");");
      //window.eval("turnto(" + (index + 1) + ");");
      //window.eval("alert(document.form1.Name.value)");
    } catch (JSException ex) {
    }
    this.repaint();
  }

  private int getOldTab(int index) {
    int iPos = 0;
    int iShow = 0;
    for (int i = 0; i < this.hidePanelFlags.length; i++) {
      if (!this.hidePanelFlags[i]) {
        iShow++;
      }
      if (iShow > index) {
        return i;
      }
    }
    return index;
  }

  void jButton1_actionPerformed(ActionEvent e, String aJScript) {
    try {
      JSObject window = JSObject.getWindow(this);
      window.eval(aJScript);
    } catch (JSException ex) {
      ex.printStackTrace();
    }
    this.repaint();
  }
}

class AppletTabbed_jTabbedPane1_changeAdapter implements javax.swing.event.ChangeListener {
  AppletTabbed adaptee;

  AppletTabbed_jTabbedPane1_changeAdapter(AppletTabbed adaptee) {
    this.adaptee = adaptee;
  }

  public void stateChanged(ChangeEvent e) {
    adaptee.jTabbedPane1_stateChanged(e);
  }
}

class AppletTabbed_jButton1_actionAdapter implements java.awt.event.ActionListener {
  AppletTabbed adaptee;
  String aJScript;

  AppletTabbed_jButton1_actionAdapter(AppletTabbed adaptee, String aJScript) {
    this.adaptee = adaptee;
    this.aJScript = aJScript;
  }

  public void actionPerformed(ActionEvent e) {
    if (this.aJScript != null && this.aJScript.length() > 0) {
      adaptee.jButton1_actionPerformed(e, aJScript);
    }
  }
}

class SelectBoxItem {
  private String label;
  private String value;
  public SelectBoxItem() {
    label = "";
    value = "";
  }

  public SelectBoxItem(String label, String value) {
    this.label = label;
    this.value = value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getLabel() {
    return this.label;
  }

  public String toString() {
    return getLabel();
  }
}
