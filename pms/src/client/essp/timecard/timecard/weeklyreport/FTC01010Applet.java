package client.essp.timecard.timecard.weeklyreport;

import client.essp.common.view.UISetting;
import client.framework.view.vwcomp.VWJApplet;

import com.wits.util.Parameter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.UIManager;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author not attributable
 * @version 1.0
 */
public class FTC01010Applet extends VWJApplet {
    //  String logoner = "";
    private String       prjID        = "";
    private String       prjAccount   = "";
    private String       prjName      = "";
    private String       isPrjPM      = "";
    private ArrayList    projectList  = new ArrayList();
    private FTC01010Main fTC01010Main = null;

    //Construct the applet
    public FTC01010Applet() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UISetting.settingUIManager();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //  public static FTC01010_Applet instance;
    //Get a parameter value
    public String getParameter(String key,
                               String def) {
        return isStandalone ? System.getProperty(key, def)
                            : ((getParameter(key) != null) ? getParameter(key)
                                                           : def);
    }

    //Initialize the applet
    public void initUI() {
        fTC01010Main = new FTC01010Main();
        this.getContentPane().add(fTC01010Main, BorderLayout.CENTER);

        try {
            log.debug("Applet Init!");
            this.setSize(new Dimension(800, 505));

            /**
            projectList.add(new String[] { "173", "W3000", "ESSP Dev'mt", "1" });
            projectList.add(new String[] { "807", "002792W", "陕西省纪委警示训诫系统", "1" });
            projectList.add(new String[] { "2588", "002742W", "TSE2005年人力支持", "1" });
            projectList.add(new String[] { "4306", "002913W", "苏州电子监察系统", "0" });
            projectList.add(new String[] {
                                "4392", "002959W", "广州Ericsson人力支持", "0"
                            });
            jbInit();

            **/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Component initialization
    public void jbInit() throws Exception {
        log.debug("Set Parameter ");

        Parameter para = new Parameter();
        para.put("prjList", projectList);
        fTC01010Main.setParameter(para);

        log.debug("Initialize Data");
        fTC01010Main.refreshWorkArea();
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
        String[][] pinfo = {
                               { "PRJID", "String", "" },
                               { "PROJACCOUNT", "String", "" },
                               { "PROJNAME", "String", "" },
                               { "ISPM", "String", "" },
                           };

        return pinfo;
    }

    //javascript 调用，设置参数
    public void setPrjID(String prjID) {
        this.prjID = prjID;
    }

    public void setPrjAccount(String prjAccount) {
        this.prjAccount = prjAccount;
    }

    public void setPrjName(String prjName) {
        this.prjName = prjName;
    }

    public void setIsPrjPM(String isPrjPM) {
        this.isPrjPM = isPrjPM;
    }

    public void addParameter() {
        log.debug("Get Parameters!");
        projectList.add(new String[] { prjID, prjAccount, prjName, isPrjPM });
    }

    //Main method
    public static void main(String[] args) {
        FTC01010Applet applet = new FTC01010Applet();
        applet.isStandalone = true;

        final Frame frame = new Frame();
        frame.setTitle("FTC010100");
        frame.add(applet, BorderLayout.CENTER);
        applet.init();

        //        NetConnector connector = ConnectFactory.createConnector();
        //        connector.setDefaultControllerURL("http://localhost:8080/Controller");
        try {
            //        applet.addParameter();
            applet.jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //    applet.start();
        frame.setSize(800, 525);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((d.width - frame.getSize().width) / 2,
                          (d.height - frame.getSize().height) / 2);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    frame.dispose();
                }
            });
    }
}
