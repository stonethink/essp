package client.net;

import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.dto.InputInfo;
import client.framework.common.Global;
import javax.swing.JFrame;

/**
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
public class NetConnectorTestImp implements NetConnector {
  public int accessData(InputInfo input,ReturnInfo output) {
    System.out.println("处理中...");
    for (int i = 0; i < 100; i++) {
      for (int j = 0; j < 90000000; j++) {
        //jProgressBar1.setValue(i);
      }
    }
    System.out.println("处理完了");
    return 0;
  }
  public int accessData(TransactionData data) {
    System.out.println("处理中...");
    for (int i = 0; i < 100; i++) {
      for (int j = 0; j < 90000000; j++) {
        //jProgressBar1.setValue(i);
      }
    }
    System.out.println("处理完了");
    return 0;
  }
  public String getDefaultControllerURL() {
    return "";
  }
  public void setDefaultControllerURL(String controllerURL) {

  }

  public static void main(String[] args) {
    javax.swing.JFrame frame=new javax.swing.JFrame();
    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    TestApplet applet=new TestApplet();
    frame.setContentPane(applet);
    frame.setSize(400,300);
    Global.applet=applet;
    frame.setVisible(true);
  }
}

class TestApplet extends java.applet.Applet {
  java.awt.Button button=new java.awt.Button("test");


  public TestApplet() {
    this.setSize(400,300);
    this.setLayout(new java.awt.FlowLayout());
    button.setSize(50,20);
    this.add(button);
    button.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent event) {
        TransactionData data=new TransactionData();
        data.getInputInfo().setInputObj("isShowLoading","true");
        ConnectFactory.createConnector().accessData(data);
      }
    });
  }
}
