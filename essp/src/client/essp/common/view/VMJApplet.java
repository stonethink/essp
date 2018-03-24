package client.essp.common.view;

import java.awt.*;
import javax.swing.*;
import netscape.javascript.JSObject;
import netscape.javascript.JSException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author not attributable
 * @version 1.0
 */

public class VMJApplet extends JApplet{
  private boolean isStandalone = false;
  public static String serverURL = "";
  VWJPage vmjpage ;
  public VMJApplet(){
    try{
      UISetting.settingUIManager();
    }
        catch(Exception e) {
          e.printStackTrace();
    }

  }
  public String getParameter(String key, String def) {
  return isStandalone ? System.getProperty(key, def) :
    (getParameter(key) != null ? getParameter(key) : def);
}

  public void init() {
    try {
        String serverUrl = this.getParameter("serverURL","http://localhost:8080/essp");
        jbInit();
    }
    catch (Exception e) {
        e.printStackTrace();
    }
}

public void start(){
    super.start();
}

//Destroy the applet
  public void destroy() {
  }


  public static void main(String[] args) {
      VMJApplet applet=new VMJApplet();
      applet.isStandalone = true;
      Frame frame;
      frame = new Frame();
      frame.setTitle("VMJApplet");

      frame.add(applet, BorderLayout.CENTER);
      frame.setSize(810, 630);

      applet.init();
      applet.start();
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setLocation( (d.width - frame.getSize().width) / 2,
                        (d.height - frame.getSize().height) / 2);
      frame.setVisible(true);
      frame.pack();
      frame.show();
  }
  private void jbInit() throws Exception {
      vmjpage = new VWJPage();
      this.getContentPane().add(vmjpage, BorderLayout.CENTER);
      this.setName("");
   }

  public void hrAllocOK(String newData){
      this.vmjpage.setworkpanel(newData);
  }

  public void setstatusstr(String statusStr){
        try {
            JSObject window = JSObject.getWindow(this);
            String orderStr = "setstatus(\"" + statusStr + "\");";
            window.eval(orderStr);
        }
        catch (JSException ex) {
            System.out.println("request error:"+ ex.getMessage());
        }
    }

}
