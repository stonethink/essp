package com.wits.util;

import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Toolkit;
import javax.swing.UIManager;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import java.awt.Color;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TestPanel {
    boolean packFrame = false;

    /*
     * Default property
     */
    int HEIGHT_TOOLBAR = 0;
    int WIDTH_MENU = 200;
    int HEIGHT_STATUSBAR = 20;
    int WIDTH_PAGE = 774;

    //WIDTH_PAGE = 1017;
    int HEIGHT_PAGE = 550;

    //HEIGHT_PAGE = 697;
    int HEIGHT_SPLITTOP = 2;
    int WIDTH_SPLITLEFT = 5;
    int WORK_TOP = 300;
    int WORK_HEAD_PANEL = WORK_TOP - 26;
    int WORK_DETAIL_PANEL = HEIGHT_PAGE - HEIGHT_TOOLBAR - HEIGHT_STATUSBAR - HEIGHT_SPLITTOP - WORK_TOP - 26 - 5;

    //Construct the application
    public TestPanel( JPanel panel) {
        JFrame frame = new JFrame();
        frame.setSize(WIDTH_PAGE,HEIGHT_PAGE);

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        frame.getContentPane().setLayout( new BorderLayout() );
        JPanel p = new JPanel(new BorderLayout());
        p.add( panel, BorderLayout.CENTER );
        panel.setBorder( BorderFactory.createLineBorder( Color.BLACK, 2 ));
        frame.getContentPane().add(p, BorderLayout.CENTER);
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

    //Construct the application
    public TestPanel( Panel panel) {
        packFrame=true;
        JFrame frame = new JFrame();
        frame.setSize(1000,600);

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        frame.getContentPane().setLayout( new BorderLayout() );
        //JPanel p = new JPanel(new BorderLayout());
        //p.add( panel, BorderLayout.CENTER );
        //panel.setBorder( BorderFactory.createLineBorder( Color.BLACK, 2 ));
        frame.getContentPane().add(panel, BorderLayout.CENTER);
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

    //Main method
    public static void show( JPanel panel ) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new TestPanel(panel);
    }

    //Main method
    public static void show( Panel panel ) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new TestPanel(panel);
    }

    //for test
    public static void main( String args[] ){
        TestPanel.show( new JPanel() );
    }
}
