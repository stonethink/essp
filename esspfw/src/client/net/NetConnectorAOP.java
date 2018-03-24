package client.net;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import client.framework.common.Global;
import c2s.dto.InputInfo;
import c2s.dto.TransactionData;
import java.awt.Image;
import java.awt.Frame;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JDialog;
import java.awt.Toolkit;
import java.awt.GridBagConstraints;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class NetConnectorAOP implements InvocationHandler {
  private Object delegate;
  public NetConnectorAOP(Object delegate) {
    this.delegate = delegate;
  }

  private boolean isAop(Method method, Object[] args) {
    boolean isAOP = false;
    try {
      InputInfo input = null;

      Class[] paramTypes = method.getParameterTypes();
      if (paramTypes != null) {
        if (paramTypes.length == 2) {
          input = (InputInfo) args[0];
          if (input.getInputObj("isShowLoading") != null) {
            String strIsAOP = (String) input.getInputObj("isShowLoading");
            if (strIsAOP.trim().equalsIgnoreCase("true")) {
              isAOP = true;
            }
          }
        } else if (paramTypes.length == 1) {
          TransactionData data = (TransactionData) args[0];
          input = data.getInputInfo();
          if (input.getInputObj("isShowLoading") != null) {
            String strIsAOP = (String) input.getInputObj("isShowLoading");
            if (strIsAOP.trim().equalsIgnoreCase("true")) {
              isAOP = true;
            }
          }
        }
      }
    } catch (Exception ex) {
    }
    return isAOP;
  }

  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    Object o = null;
    try {

      if (method.getName().equals("accessData")) {
        boolean isAOP = isAop(method, args);

        if (Global.applet != null && isAOP) {
          ImageDialog dialog = new ImageDialog(delegate, method, args);
          dialog.showDialog();
          o = dialog.getResult();
          dialog.dispose();
//            System.out.println("[AOP] method start...:" + method);
//            o = method.invoke(delegate, args);
//            System.out.println("[AOP] method end...:" + method);
        } else {
          //System.out.println("[AOP] method start...:" + method);
          o = method.invoke(delegate, args);
          //System.out.println("[AOP] method end...:" + method);
        }
      } else {
        o = method.invoke(delegate, args);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return o;
  }
}

class ImageDialog extends JDialog {
  private static Image agif;
  private static Image img = null;
  private static int aw, ah;

  private ImagePanel imagePanel = new ImagePanel();
  private Object delegate = null;
  private Method method = null;
  private Object[] args = null;
  private Object result = null;
  public ImageDialog(Object delegate,
                     Method method,
                     Object[] args) {
    super( (Frame)null, true);
    this.delegate = delegate;
    this.method = method;
    this.args = args;
    init();
  }

  public void init() {
    Dimension dim = new Dimension(400, 300);
    imagePanel.setSize(dim);
    imagePanel.setPreferredSize(dim);
    this.setContentPane(imagePanel);
    this.setBackground(Color.white);
    this.setSize(dim);
    this.setResizable(false);
    setCenter(this);
    this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    //this.setDefaultLookAndFeelDecorated(true);
  }

  public void showDialog() {
//    ShowDialogThread showDialogThread=new ShowDialogThread();
//    showDialogThread.start();
    InvokeThread invokeThread = new InvokeThread();
    invokeThread.start();
    this.setVisible(true);
  }

  public Object getResult() {
    return result;
  }

  private static void setCenter(java.awt.Component comp) {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension compSize = comp.getSize();
    if (compSize.height > screenSize.height) {
      compSize.height = screenSize.height;
    }
    if (compSize.width > screenSize.width) {
      compSize.width = screenSize.width;
    }
    comp.setLocation( (screenSize.width - compSize.width) / 2, (screenSize.height - compSize.height) / 2);
  }

  private static Frame getParentWindow() {
    java.awt.Container c = Global.applet;

    while (c != null) {
      if (c instanceof java.awt.Frame) {
        return (java.awt.Frame) c;
      }

      c = c.getParent();
    }

    return null;
  }

  class ImagePanel extends JPanel {
    private JLabel jLabel1 = new JLabel();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    ImageIcon pasteImg = new ImageIcon(this.getClass().getResource("Loading.gif"));

    public ImagePanel() {
      try {
        jbInit();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    private void jbInit() throws Exception {
      Dimension dim = new Dimension(400, 300);
      this.setSize(dim);
      this.setPreferredSize(dim);
      jLabel1.setText("");
      jLabel1.setIcon(pasteImg);
      jLabel1.setSize(pasteImg.getIconWidth(), pasteImg.getIconHeight());
      this.setLayout(gridBagLayout1);
      jLabel1.setBackground(Color.WHITE);
      this.setBackground(Color.WHITE);
      this.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                               GridBagConstraints.CENTER,
                                               GridBagConstraints.NONE,
                                               new Insets(0, 0, 0, 0), 0, 150));
    }
  }

  class InvokeThread extends Thread {
    public void run() {
//      for (int i = 0; i < 100; i++) {
//        for (int j = 0; j < 100; j++) {
//
//        }
//      }
      System.out.println("Æô¶¯animating");
      try {
        result = method.invoke(delegate, args);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      ImageDialog.this.setVisible(false);
    }
  }
}
