package client.essp.common.view;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.awt.event.*;
import javax.swing.JOptionPane;
import org.apache.log4j.Category;
public class VWJPage extends JPanel implements IExpender {
    Category log = Category.getInstance( "client" );
    /*
     * Default property
     */
    private static final int HEIGHT_TOOLBAR = 0;
    private static final int WIDTH_MENU =  200; //0;//200
    private static final int HEIGHT_STATUSBAR = 0;//20
    private static final int WIDTH_PAGE = 800;

    //WIDTH_PAGE = 1017;
    private static final int HEIGHT_PAGE = 505;

    //HEIGHT_PAGE = 697;
    private static final int HEIGHT_SPLITTOP = 2;
    private static final int WIDTH_SPLITLEFT = 0;//5
    private static final int WORK_TOP = 300;

    private static final Color COLOR_TOOLBAR = Color.BLUE;
    private static final Color COLOR_MENU = Color.GRAY;
    private static final Color COLOR_STATUSBAR = Color.LIGHT_GRAY;
    private static final Color COLOR_WORKAREA = Color.GREEN;
    private static final Color COLOR_SPLITTOP = Color.LIGHT_GRAY;
    private static final Color COLOR_SPLITLEFT = new Color(148, 170, 214);

    VWJToolBar toolBar = new VWJToolBar();
    VWJSplitTop splitTop = new VWJSplitTop();
    VWJSplitLeft splitLeft = new VWJSplitLeft();
    VWJMenu menu = new VWJMenu();
    public static VWJStatusBar statusBar = new VWJStatusBar();
//    JPanel workArea = null;//new VWJWorkArea_3(WORK_TOP);
    JPanel workArea = new VWTDWorkArea(WORK_TOP);//new VWJWorkArea_3(WORK_TOP);

    JPanel jpanel0 = null;
    JPanel jpanel1 = null;


    public VWJPage() {
        this(null);
    }

    public VWJPage(Map properties) {
        try {
            if (properties != null) {
                setProperties(properties);
            }
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map getProperties() {
        return null;
    }

    public void setProperties(Map properties) {
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        this.setSize(WIDTH_PAGE, HEIGHT_PAGE);
        this.addHierarchyBoundsListener(new VWJHierarchyBoundsAdapter(this));
        this.setPreferredSize(new Dimension(WIDTH_PAGE, HEIGHT_PAGE));
        /*
         * init ToolBar
         */
        toolBar.setBounds(0, 0, this.getWidth(), HEIGHT_TOOLBAR);
        toolBar.setBackground(COLOR_TOOLBAR);

        /*
         * init SplitTop
         */
        splitTop.setBounds(0, HEIGHT_TOOLBAR, this.getWidth(), HEIGHT_SPLITTOP);
        splitTop.setBackground(COLOR_SPLITTOP);

        /*
         * init Menu
         */
        menu.setBounds(0, HEIGHT_TOOLBAR + HEIGHT_SPLITTOP, WIDTH_MENU,
                       this.getHeight() - HEIGHT_TOOLBAR -
                       HEIGHT_STATUSBAR - HEIGHT_SPLITTOP);
        menu.setBackground(COLOR_MENU);

        /*
         * init SplitLeft
         */
        splitLeft.setBounds(WIDTH_MENU, HEIGHT_TOOLBAR + HEIGHT_SPLITTOP,
                            WIDTH_SPLITLEFT, this.getHeight() - HEIGHT_TOOLBAR -
                            HEIGHT_STATUSBAR - HEIGHT_SPLITTOP);
        splitLeft.setBackground(COLOR_SPLITLEFT);
        splitLeft.setExpender(this);
        /*
         * init StatusBar
         */
        statusBar.setBounds(0, this.getHeight() - HEIGHT_STATUSBAR,
                            this.getWidth(), HEIGHT_STATUSBAR);
        statusBar.setBackground(COLOR_STATUSBAR);

        /**
         * init WorkArea
         */
        workArea.setBounds(WIDTH_MENU + WIDTH_SPLITLEFT,
                           HEIGHT_TOOLBAR + HEIGHT_SPLITTOP,
                           this.getWidth() - WIDTH_MENU - WIDTH_SPLITLEFT,
                           this.getHeight() - HEIGHT_TOOLBAR -
                           HEIGHT_STATUSBAR - HEIGHT_SPLITTOP);
        System.out.println("getHeight"+ (this.getHeight() - HEIGHT_TOOLBAR - HEIGHT_STATUSBAR - HEIGHT_SPLITTOP));
        workArea.setBackground(COLOR_WORKAREA);

        this.add(splitTop, null);
        this.add(splitLeft, null);
        this.add(toolBar, null);
        this.add(menu, null);
        this.add(statusBar, null);
        this.add(workArea, null);

        Menu menu0 = new Menu("root");
        Menu menu1 = new Menu("Administrator");
        MenuItem menu11 = new MenuItem("Leave");
        menu11.setActionCommand("Leave");
        MenuItem menu12 = new MenuItem("Overtime");
        menu12.setActionCommand("Overtime");
        MenuItem menu13 = new MenuItem("Outgoing");
        menu13.setActionCommand("Outgoing");
        MenuItem menu14 = new MenuItem("Purchase");
        menu14.setActionCommand("Purchase");
        menu1.add(menu11);
        menu1.add(menu12);
        menu1.add(menu13);
        menu1.add(menu14);
        Menu menu2 = new Menu("Report");
        MenuItem menu21 = new MenuItem("Attendance2");
        menu21.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ent) {
//                JPanel jpanel =  new VWFFT00030_UI(WORK_TOP) ;
//                setWorkArea( jpanel );
                setworkpanel("client.fw_tst.FFT00070.FFT00070");
            }
        });
        MenuItem menu22 = new MenuItem("Parameters Managerment");
        menu22.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ent) {
                //JPanel jpanel =  new VWFFT00040_UI(WORK_TOP) ;
//                JPanel jpanel =  new VWFFT00050_UI(WORK_TOP) ;
//                setWorkArea( jpanel );
                setworkpanel("VWFFT00050_UI");
            }
        });

        menu2.add(menu21);
        menu2.add(menu22);
        menu0.add(menu1);
        menu0.add(menu2);
        menu.setMenu(menu0);
//        setworkpanel("VWFFT00030_UI");
    }

    public void setworkpanel(String className){
//        try {
//            Class cl = Class.forName(className);
//            try {
//                VWJWorkArea p = (VWJWorkArea) cl.newInstance();
//                p.setpanelheight(WORK_TOP);
//                setWorkArea( p );
//            } catch (IllegalAccessException ex1) {
//            } catch (InstantiationException ex1) {
//            }
//        } catch (ClassNotFoundException ex) {
//        }

    try {
        Class cls = Class.forName(className);
        VWWorkArea page = (VWWorkArea) cls.newInstance();
        setWorkArea( page );

    } catch (Exception e) {
        log.error( "error occur", e );
    }



//        if(className.equalsIgnoreCase("VWFFT00030_UI")){
//            if(jpanel0==null){
// //            jpanel0 =  new client.fw_tst.FFT00070.WORKCARD() ;
//            }
//            setWorkArea( jpanel0 );
//        }else if(className.equalsIgnoreCase("VWFFT00050_UI")){
//            if(jpanel1==null){
//            //            jpanel1 =  new VWFFT00050_UI(WORK_TOP) ;
// //            jpanel1 =  new client.fw_tst.FFT00060.WORKCARD() ;
//            }
//            setWorkArea( jpanel1 );
//        }

    }

    public void expend(boolean isExpand) {
        if (isExpand == true) {
            menu.setBounds(0, HEIGHT_TOOLBAR + HEIGHT_SPLITTOP, 0, 0);
            splitLeft.setBounds(0, HEIGHT_TOOLBAR + HEIGHT_SPLITTOP,
                                WIDTH_SPLITLEFT, this.getHeight() - HEIGHT_TOOLBAR -
                                HEIGHT_STATUSBAR - HEIGHT_SPLITTOP);
            workArea.setBounds(WIDTH_SPLITLEFT,
                               HEIGHT_TOOLBAR + HEIGHT_SPLITTOP,
                               this.getWidth() - WIDTH_SPLITLEFT,
                               this.getHeight() - HEIGHT_TOOLBAR -
                               HEIGHT_STATUSBAR - HEIGHT_SPLITTOP);
            menu.updateUI();
            workArea.updateUI();
            this.updateUI();
        } else {
            menu.setBounds(0, HEIGHT_TOOLBAR + HEIGHT_SPLITTOP, WIDTH_MENU,
                           this.getHeight() - HEIGHT_TOOLBAR -
                           HEIGHT_STATUSBAR - HEIGHT_SPLITTOP);
            splitLeft.setBounds(WIDTH_MENU, HEIGHT_TOOLBAR + HEIGHT_SPLITTOP,
                                WIDTH_SPLITLEFT, this.getHeight() - HEIGHT_TOOLBAR -
                                HEIGHT_STATUSBAR - HEIGHT_SPLITTOP);
            workArea.setBounds(WIDTH_MENU + WIDTH_SPLITLEFT,
                               HEIGHT_TOOLBAR + HEIGHT_SPLITTOP,
                               this.getWidth() - WIDTH_MENU - WIDTH_SPLITLEFT,
                               this.getHeight() - HEIGHT_TOOLBAR -
                               HEIGHT_STATUSBAR - HEIGHT_SPLITTOP);
            menu.updateUI();
            workArea.updateUI();
            this.updateUI();
        }
        System.out.println("getHeightQQ"+ (this.getHeight() - HEIGHT_TOOLBAR - HEIGHT_STATUSBAR - HEIGHT_SPLITTOP));

    }

    public void setMenu(Menu menu) {
        this.menu.setMenu(menu);
    }

    public void setWorkArea(JPanel panel) {
        this.remove(this.workArea);
        this.workArea=panel;
        workArea.setBounds(WIDTH_MENU + WIDTH_SPLITLEFT,
                           HEIGHT_TOOLBAR + HEIGHT_SPLITTOP,
                           this.getWidth() - WIDTH_MENU - WIDTH_SPLITLEFT,
                           this.getHeight() - HEIGHT_TOOLBAR -
                           HEIGHT_STATUSBAR - HEIGHT_SPLITTOP);
        this.add(this.workArea);
        this.updateUI();
    }

    public static void setStatusMsg( String s ){
        ///+++add for test
        JOptionPane.showMessageDialog(null, s);
        statusBar.setStatus( s );
    }

    public static void main(String[] args) throws Exception {

        try {
            UISetting.settingUIManager();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame();
        VWJPage page = new VWJPage();
        frame.setContentPane(page);

        frame.pack();
        frame.show();

        statusBar.setStatus( "test" );
    }


    void onAncestorResized(HierarchyEvent e) {
        statusBar.setBounds(0, this.getHeight() - HEIGHT_STATUSBAR,
                            this.getWidth(), HEIGHT_STATUSBAR);
        expend(splitLeft.isIsExpand());
    }
}

class VWJHierarchyBoundsAdapter extends java.awt.event.HierarchyBoundsAdapter {
    VWJPage adaptee;

    VWJHierarchyBoundsAdapter(VWJPage adaptee) {
        this.adaptee = adaptee;
    }

    public void ancestorResized(HierarchyEvent e) {
        adaptee.onAncestorResized(e);
    }
}
