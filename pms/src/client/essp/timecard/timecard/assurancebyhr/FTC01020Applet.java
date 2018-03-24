package client.essp.timecard.timecard.assurancebyhr;

import client.essp.common.view.UISetting;
import client.framework.view.vwcomp.VWJApplet;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;


public class FTC01020Applet extends VWJApplet {
    private boolean      isStandalone = false;
    private FTC01020Main fTC01020Main;

    //Construct the applet
    public FTC01020Applet() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UISetting.settingUIManager();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Get a parameter value
    public String getParameter(String key,
                               String def) {
        return isStandalone ? System.getProperty(key, def)
                            : ((getParameter(key) != null) ? getParameter(key)
                                                           : def);
    }

    //Initialize the applet
    public void initUI() {
        fTC01020Main = new FTC01020Main();

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Component initialization
    private void jbInit() throws Exception {
        this.setSize(new Dimension(800, 525));
        fTC01020Main.setRefreshFlag(true);
        fTC01020Main.refreshWorkArea();
        this.getContentPane().add(fTC01020Main, BorderLayout.CENTER);
    }

    //Start the applet
    public void start() {
    }

    //Stop the applet
    public void stop() {
    }

    //Destroy the applet
    public void destroy() {
    }

    //Get Applet information
    public String getAppletInfo() {
        return "Applet Information";
    }

    //Get parameter info
    public String[][] getParameterInfo() {
        return null;
    }

    //Main method
    public static void main(String[] args) {
        FTC01020Applet applet = new FTC01020Applet();
        applet.isStandalone = true;

        JFrame frame = new JFrame();

        //EXIT_ON_CLOSE == 3
        frame.setDefaultCloseOperation(3);
        frame.setTitle("Applet Frame");
        frame.getContentPane().add(applet, BorderLayout.CENTER);
        applet.init();

        //        NetConnector connector = ConnectFactory.createConnector();
        //        connector.setDefaultControllerURL("http://localhost:8080/Controller");
        frame.setSize(800, 545);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((d.width - frame.getSize().width) / 2,
                          (d.height - frame.getSize().height) / 2);
        frame.setVisible(true);
    }
}
