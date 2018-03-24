package client.framework.view.common;

import java.awt.*;
import java.awt.event.*;
import client.framework.view.jmscomp.*;
import javax.swing.*;
import java.net.URL;
import client.framework.common.Global;

public class comFORM {
  /**
   * ﾈ｡ｵﾃFrame
   * @return Frame
   */
  public static Frame getBaseFrame() {
    Component clsComp = null;

    if (Global.applet != null) {
      clsComp = Global.applet;
      while (! (clsComp instanceof Frame)) {
        clsComp = clsComp.getParent();
      }
    }
    return (Frame) clsComp;
  }

  public static void setFocus(JComponent prm_clsComp) {
    JComponent focusedComp = prm_clsComp;
    if(focusedComp.getRootPane()==null) {
        prm_clsComp.requestFocus();
        return;
    }

    Container container = focusedComp.getRootPane().getParent();
    if(container==null) {
        prm_clsComp.requestFocus();
        return;
    }

    FocusTraversalPolicy focusPolicy = container.getFocusTraversalPolicy();

    while ( (focusedComp != null)
           && !focusedComp.isEnabled()
           && (focusPolicy != null)) {
      focusedComp =
          (JComponent) focusPolicy.getComponentAfter(container, focusedComp);
    }
    if ( (focusedComp != null) && focusedComp.isEnabled()) {
      container.requestFocusInWindow();
      focusedComp.requestFocusInWindow();

      if (!focusedComp.isFocusOwner()) {
        SwingUtilities.invokeLater(new FocusSetter(focusedComp));
      }
    }
  }

  public static void moveUrl(
      String prm_sUrl,
      java.applet.AppletContext prm_apc
      ) {
    try {
      prm_apc.showDocument(new URL(prm_sUrl), "_parent");
    } catch (Exception e) {
      // エラーを標準エラーストリームに出力する
      e.printStackTrace();
    }
  }

  public static boolean checkModidfiedStatus(javax.swing.JApplet prm_clsForm) {
    Component clsComp;
    int iCount;
    int i;
    boolean bModify = false;

    bModify = false;
    iCount = prm_clsForm.getContentPane().getComponentCount();
    for (i = 0; i < iCount; i++) {
      clsComp = prm_clsForm.getContentPane().getComponent(i);
      if (clsComp instanceof JMsText) { //テキスト
        if ( ( (JMsText) clsComp).isKeyType() == true) {
          if ( ( (JMsText) clsComp).checkModified() == true) {
            bModify = true;
          }
        }
      } else if (clsComp instanceof JMsDate) { //日付
        if ( ( (JMsDate) clsComp).isKeyType() == true) {
          if ( ( (JMsDate) clsComp).checkModified() == true) {
            bModify = true;
          }
        }
      } else if (clsComp instanceof JMsCheckBox) { //チェックボックス
        if ( ( (JMsCheckBox) clsComp).isKeyType() == true) {
          if ( ( (JMsCheckBox) clsComp).checkModified() == true) {
            bModify = true;
          }
        }
      } else if (clsComp instanceof JMsRadioButton) { //ラジオボタン
        if ( ( (JMsRadioButton) clsComp).isKeyType() == true) {
          if ( ( (JMsRadioButton) clsComp).checkModified() == true) {
            bModify = true;
          }
        }
      }
    }

    if (bModify == true) {
      comMSG.dispErrorDialog("ｻｭﾃ賈ﾏｵﾄﾀｸﾎｻﾒﾑｾｭｱｻﾐﾞｸﾄ");
    }
    return bModify;
  }

  public static void clearModidfiedStatus(javax.swing.JApplet prm_clsForm) {
    Component clsComp;
    int iCount = 0;

    iCount = prm_clsForm.getContentPane().getComponentCount();
    for (int i = 0; i < iCount; i++) {
      clsComp = prm_clsForm.getContentPane().getComponent(i);
      if (clsComp instanceof JMsText) { //テキスト
        if ( ( (JMsText) clsComp).isKeyType() == true) {
          ( (JMsText) clsComp).clearModified();
        }
      } else if (clsComp instanceof JMsDate) { //日付
        if ( ( (JMsDate) clsComp).isKeyType() == true) {
          ( (JMsDate) clsComp).clearModified();
        }
      } else if (clsComp instanceof JMsCheckBox) { //チェックボックス
        if ( ( (JMsCheckBox) clsComp).isKeyType() == true) {
          ( (JMsCheckBox) clsComp).clearModified();
        }
      } else if (clsComp instanceof JMsRadioButton) { //ラジオボタン
        if ( ( (JMsRadioButton) clsComp).isKeyType() == true) {
          ( (JMsRadioButton) clsComp).clearModified();
        }
      }
    }
    return;
  }

  public static void setComboPos(
      JMsComboBox prm_clsCombo,
      String[] prm_combcd,
      String prm_data
      ) {
    int iPos;
    int i;
    if (prm_data.equals("") == false) {
      try {
        for (i = 0; i < DefaultCommon.COMBOX_MAX_NUM; i++) {
          if (prm_combcd[i] == null) {
            comMSG.dispErrorDialog("コンボデータ " + prm_data.trim() + " が登録されていません。");
            comFORM.setFocus(prm_clsCombo);
            prm_clsCombo.setField_Error("E");
            return;
          }

          if (prm_combcd[i].equals(prm_data.trim()) == true) {
            prm_clsCombo.setSelectedIndex(i);
            return;
          }
        }
      } catch (Exception clsExcept) {
        System.out.println(" clsExcept : " + clsExcept.toString());
        comMSG.dispFaitalDialog("",
                                "common",
                                "comFORM",
                                "setComboPos",
                                "コンボ表示設定時にエラーが発生しました。",
                                clsExcept);
      }
    }
  }

  public static void setEnterOrder(javax.swing.JPanel prm_clsForm, Component[] enterkeyList) {
    ComFocusTraversalPolicy enterkeyPolicy = new ComFocusTraversalPolicy(enterkeyList);
    EnterkeyEventAdapter enterkeyAdapter = new EnterkeyEventAdapter();
    enterkeyAdapter.setEnterkeyTraversalPolicy(enterkeyPolicy);
    enterkeyAdapter.render(prm_clsForm);
  }

  public static void setEnterOrder(javax.swing.JPanel prm_clsForm, java.util.List enterkeyList) {
    ComFocusTraversalPolicy enterkeyPolicy = new ComFocusTraversalPolicy(enterkeyList);
    EnterkeyEventAdapter enterkeyAdapter = new EnterkeyEventAdapter();
    enterkeyAdapter.setEnterkeyTraversalPolicy(enterkeyPolicy);
    enterkeyAdapter.render(prm_clsForm);
  }

  public static void setTABOrder(javax.swing.JPanel prm_clsForm, Component[] tabkeyList) {
    prm_clsForm.setFocusTraversalPolicy(new ComFocusTraversalPolicy(tabkeyList));
  }

  public static void setTABOrder(javax.swing.JPanel prm_clsForm, java.util.List tabkeyList) {
    prm_clsForm.setFocusTraversalPolicy(new ComFocusTraversalPolicy(tabkeyList));
  }

}

class FocusSetter implements Runnable {
  javax.swing.JComponent adapt = null;

  public FocusSetter(javax.swing.JComponent comp) {
    adapt = comp;
  }

  public void run() {
    java.awt.event.KeyEvent clsEvent;

    //++****************************
    //	繝輔か繝ｼ繧ｫ繧ｹ險ｭ螳・
    //--****************************
    if( adapt.getRootPane() == null || adapt.getRootPane().getParent() == null ){
        return;
    }

    adapt.getRootPane().getParent().requestFocusInWindow();
    adapt.requestFocusInWindow();

    if (adapt.isEnabled() == false) {
      //clsEvent	= new KeyEvent( prm_clsComp, 401, 0, 0, Event.TAB );
      //prm_clsComp.dispatchEvent( clsEvent );
      clsEvent = null;
    }
  }
}

class ComFocusTraversalPolicy extends FocusTraversalPolicy {
  java.util.List ComponentList = null;
  /**
   *
   */
  public ComFocusTraversalPolicy(java.util.List list) {
    super();
    ComponentList = list;
  }

  public ComFocusTraversalPolicy(Component[] list) {
    super();
    if (list != null) {
      ComponentList = new java.util.ArrayList();
      for (int i = 0; i < list.length; i++) {
        ComponentList.add(list[i]);
      }
    }
  }

  /*
   * @see java.awt.FocusTraversalPolicy#getComponentAfter(java.awt.Container, java.awt.Component)
   */
  public Component getComponentAfter(
      Container focusCycleRoot,
      Component aComponent) {
    if (ComponentList == null || ComponentList.size() == 0) {
      return aComponent;
    }

    int iIndex = ComponentList.indexOf(aComponent);

    for (int i = iIndex + 1; i < ComponentList.size();
         i++) {
      Component comp = (Component) ComponentList.get(i);
      if (comp.isEnabled()) {
        return comp;
      }
    }

    for (int i = 0; i < iIndex; i++) {
      Component comp = (Component) ComponentList.get(i);
      if (comp.isEnabled()) {
        return comp;
      }
    }

    return aComponent;
  }

  /*
   * @see java.awt.FocusTraversalPolicy#getComponentBefore(java.awt.Container, java.awt.Component)
   */
  public Component getComponentBefore(
      Container focusCycleRoot,
      Component aComponent) {
    if (ComponentList == null || ComponentList.size() == 0) {
      return aComponent;
    }

    int iIndex = ComponentList.indexOf(aComponent);

    for (int i = iIndex - 1; i >= 0; i--) {
      Component comp = (Component) ComponentList.get(i);
      if (comp.isEnabled()) {
        return comp;
      }
    }

    for (int i = ComponentList.size() - 1; i > iIndex;
         i--) {
      Component comp = (Component) ComponentList.get(i);
      if (comp.isEnabled()) {
        return comp;
      }
    }

    return aComponent;
  }

  /*
   * @see java.awt.FocusTraversalPolicy#getFirstComponent(java.awt.Container)
   */
  public Component getFirstComponent(Container focusCycleRoot) {
    if (ComponentList == null || ComponentList.size() == 0) {
      return null;
    }

    for (int i = 0; i < ComponentList.size(); i++) {
      Component comp = (Component) ComponentList.get(i);
      if (comp.isEnabled()) {
        return comp;
      }
    }

    return (Component) ComponentList.get(0);
  }

  /*
   * @see java.awt.FocusTraversalPolicy#getLastComponent(java.awt.Container)
   */
  public Component getLastComponent(Container focusCycleRoot) {
    if (ComponentList == null || ComponentList.size() == 0) {
      return null;
    }

    for (int i = ComponentList.size() - 1; i >= 0; i--) {
      Component comp = (Component) ComponentList.get(i);
      if (comp.isEnabled()) {
        return comp;
      }
    }

    return (Component) ComponentList.get(ComponentList.size() - 1);
  }

  /*
   * @see java.awt.FocusTraversalPolicy#getDefaultComponent(java.awt.Container)
   */
  public Component getDefaultComponent(Container focusCycleRoot) {
    if (ComponentList == null || ComponentList.size() == 0) {
      return null;
    }
    return (Component) ComponentList.get(0);
  }

  public java.util.List getComponentList() {
    return ComponentList;
  }
}

/**
 * enter event handle
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author not attributable
 * @version 1.0
 */
class EnterkeyEventAdapter {
  ComFocusTraversalPolicy focusTraversalPolicy = null;
  Container root = null;

  /**
   *
   */
  public EnterkeyEventAdapter() {
    super();
  }

  public void setEnterkeyTraversalPolicy(ComFocusTraversalPolicy policy) {
    focusTraversalPolicy = policy;
  }

  public void render(Container container) {
    java.util.List componentList = focusTraversalPolicy.getComponentList();
    for (int i = 0; componentList != null && i < componentList.size(); i++) {
      Component comp = (Component) componentList.get(i);
      if ( (comp instanceof JCheckBox) ||
          (comp instanceof JTextField) ||
          (comp instanceof JComboBox)) {
        /*
                                               if(comps[i].getKeyListeners()!=null) {
                java.awt.event.KeyListener[] allListener=comps[i].getKeyListeners();
                for(int j=0;j<allListener.length;j++) {
                        comps[i].removeKeyListener(allListener[j]);
                }
                                               }*/
        comp.addKeyListener(new EnterkeyAdapter(container, comp));
      }
    }
  }

  private class EnterkeyAdapter extends java.awt.event.KeyAdapter {
    Container container = null;
    Component comp = null;

    EnterkeyAdapter(
        Container container,
        Component comp) {
      this.container = container;
      this.comp = comp;
    }

    public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        if (focusTraversalPolicy != null) {
          Component nextComp = focusTraversalPolicy.getComponentAfter(container,
              comp);
          if (nextComp != null) {
            if (e.isConsumed() == false) {
              nextComp.requestFocus();
            }
          }
        }
      }
    }
  }
}
