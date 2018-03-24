package client.essp.common.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import com.wits.util.Parameter;
import client.essp.common.view.VWWorkArea;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TestPanelParam {
    boolean packFrame = false;

    /*
     * Default property
     */
    static final int HEIGHT_TOOLBAR = 0;
    static final int WIDTH_MENU = 200;
    static final int HEIGHT_STATUSBAR = 20;
    static final int WIDTH_PAGE = 800;

    //WIDTH_PAGE = 1017;
    static final int HEIGHT_PAGE = 600;

    //HEIGHT_PAGE = 697;
    static final int HEIGHT_SPLITTOP = 2;
    static final int WIDTH_SPLITLEFT = 5;
    static final int WORK_TOP = 300;
    static final int WORK_HEAD_PANEL = WORK_TOP - 26;
    static final int WORK_DETAIL_PANEL = HEIGHT_PAGE - HEIGHT_TOOLBAR - HEIGHT_STATUSBAR - HEIGHT_SPLITTOP - WORK_TOP - 26 - 5;

    JPanel mainPanel;
    ParamPanel p;

    //Construct the application
    public TestPanelParam( JPanel panel) {
        mainPanel = panel;

        JFrame frame = new JFrame();
        frame.setSize(1000,600);

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        frame.getContentPane().setLayout( new BorderLayout() );
        JPanel p = null;
//        JPanel p = new JPanel(new BorderLayout());
//        p.add( panel, BorderLayout.CENTER );
        if( panel instanceof VWWorkArea ){
            p = new VWWorkArea();
            ( (VWWorkArea)p ).addTab( "test", (VWWorkArea)panel );
        }else{
            p = new JPanel(new BorderLayout());
            p.add( panel, BorderLayout.CENTER );
        }

        panel.setBorder( BorderFactory.createLineBorder( Color.BLACK, 2 ));
        frame.getContentPane().add(p, BorderLayout.CENTER);

        JButton button = new JButton("changed parameter");
        frame.getContentPane().add(button, BorderLayout.SOUTH);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedBtn();
            }
        });

//        frame.getContentPane().add(p);
        //Validate frames that have preset sizes
        //Pack frames that have useful preferred size info, e.g. from their layout
        if (packFrame) {
            frame.pack();
        } else {
            frame.validate();
        }
        //Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation( (screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);

    }

    public void actionPerformedBtn(){
        if( p == null ){
            p = new ParamPanel();
        }
        int i = JOptionPane.showConfirmDialog( null, p, "set parameter", JOptionPane.YES_NO_OPTION );
        if( i == JOptionPane.YES_OPTION ){
            if (mainPanel instanceof VWWorkArea) {
                VWWorkArea workArea = (VWWorkArea) mainPanel;
                workArea.setParameter(p.getParam());
                workArea.refreshWorkArea();
            } else {
                JOptionPane.showMessageDialog(null, "This function is only good to VWWorkArea.");
            }
        }
    }

    //Main method
    public static void show( JPanel panel ) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new TestPanelParam(panel);
    }

    //for test
    public static void main( String[] args ){
        TestPanelParam t = new TestPanelParam( new JPanel() );
    }

    public static class ParamPanel extends JPanel {
        javax.swing.JCheckBox[] chk = new javax.swing.JCheckBox[10];
//        javax.swing.JLabel[] LBL_name = new javax.swing.JLabel[10];
        javax.swing.JTextField[] textName = new javax.swing.JTextField[10];
//        javax.swing.JLabel[] LBL_value = new javax.swing.JLabel[10];
        javax.swing.JTextField[] textValue = new javax.swing.JTextField[10];

        public ParamPanel() {
            this.setLayout(null);
            this.setPreferredSize(new java.awt.Dimension(300, 500));

            javax.swing.JLabel label1 = new javax.swing.JLabel( "param name" );
            javax.swing.JLabel label2 = new javax.swing.JLabel( "param value" );
            label1.setBounds(40,5,80,20);
            label2.setBounds(140,5,80,20);
            this.add( label1 );
            this.add( label2 );

            // x: 10 -- 30 --- 70 -- 130 -- 170 -- 230
            // y: 10 -- 40 --- 70 -- 100 -- .....
            for (int i = 0; i < 10; i++) {
                chk[i] = new javax.swing.JCheckBox();
//                LBL_name[i] = new javax.swing.JLabel();
                textName[i] = new javax.swing.JTextField();
//                LBL_value[i] = new javax.swing.JLabel();
                textValue[i] = new javax.swing.JTextField();

                chk[i].setBounds(10, 30 + 30 * i, 20, 20);
//                LBL_name[i].setBounds(33, 30 + 30 * i, 30, 20);
                textName[i].setBounds(40, 30 + 30 * i, 80, 20);
//                LBL_value[i].setBounds(133, 30 + 30 * i, 30, 20);
                textValue[i].setBounds(140, 30 + 30 * i, 80, 20);

                this.add(chk[i]);
//                this.add(LBL_name[i]);
                this.add(textName[i]);
//                this.add(LBL_value[i]);
                this.add(textValue[i]);
            }

        }

        public Parameter getParam(){
            Parameter param = new Parameter();
            for (int i = 0; i < 10; i++) {
                if( chk[i].isSelected() == true ){
                    param.put( textName[i].getText(), textValue[i].getText() );
                }
            }
            return param;
        }
    }

}
