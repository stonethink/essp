package client.essp.common.calendar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.text.ParseException;

import java.util.Calendar;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JWindow;
import javax.swing.text.JTextComponent;
import client.framework.common.Global;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Enovation</p>
 * @author Xuxi.Chen
 * @version 1.0
 */
public class CalendarWin extends JWindow {
    public static final String              DATE_SELECTED = "Date Selected";
    static java.text.DateFormat             defDateformat = new java.text.SimpleDateFormat("yyyy-MM-dd");
    private BorderLayout                    borderLayout1 = new BorderLayout();
    private PanelCalendar                   jPanelMain    = new PanelCalendar();
    private JTable                          tableMain     = jPanelMain
                                                            .getJTableMain();
    private JComponent                      lockComp;
    private java.awt.Component              owner; //容器
    private java.text.DateFormat            df;
    private javax.swing.text.JTextComponent textComp; //受影响的文本组件
    private Calendar                        oldCal;
    private PanelCalendar                   jpanelMain;

    public CalendarWin(Frame          owner,
                       JTextComponent showComp,
                       JComponent     lockComp) {
        super(owner);
        this.owner    = owner;
        this.textComp = showComp;
        this.lockComp = lockComp;
        init();
    }

    public CalendarWin(Window         owner,
                       JTextComponent showComp,
                       JComponent     lockComp) {
        super(owner);
        this.owner    = owner;
        this.textComp = showComp;
        this.lockComp = lockComp;
        init();
    }

    public CalendarWin(JTextComponent showComp,
                       JComponent     lockComp) {
        super(getCompWinOwner(lockComp));
        this.owner    = getCompWinOwner(lockComp);
        this.textComp = showComp;
        this.lockComp = lockComp;
        init();
    }

    public void setSelectDate(String strDate) {
        java.text.DateFormat dateFmt = (this.df == null) ? defDateformat : this.df;
        Calendar             aCal = new java.util.GregorianCalendar();

        try {
            aCal.setTime(dateFmt.parse(strDate));
        } catch (ParseException ex) {
            System.out.println("format error:" + ex);
        }

        this.setSelectDate(aCal);
    }

    public void setSelectDate(Calendar aCal) {
        if (aCal != null) {
            oldCal = aCal;
        } else {
            oldCal = Calendar.getInstance();
            oldCal.setTime(Global.todayDate);
        }

        this.jPanelMain.setSelectCalendar(oldCal);
    }

    public void show() {
        Point              p   = this.lockComp.getLocationOnScreen();
        java.awt.Dimension dim = this.lockComp.getSize();
        p.y += (dim.height + 2);
        p.x -= 90;

        if (p.x < 0) {
            p.x = 0;
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension thisSize = this.getPreferredSize();

        if ((p.x + thisSize.width) > screenSize.width) {
            p.x = screenSize.width - thisSize.width;
        }

        if ((p.y + thisSize.height) > screenSize.height) {
            p.y = screenSize.height - thisSize.height;
        }

        this.setLocation(p);
        super.show();
    }

    void init() {
        this.getContentPane().setLayout(borderLayout1);
        this.getContentPane().add(jPanelMain, BorderLayout.CENTER);
        tableMain.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent e) {
                    tableMainMouseReleased(e);
                }
            });

        owner.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    ownerMouseClicked(e);
                }
            });

        this.setSize(this.jPanelMain.getPreferredSize());
    }

    void ownerMouseClicked(MouseEvent e) {
        System.out.println("owner_mouseClicked =" + e);

        if (owner.getComponentAt(e.getPoint()) != this) {
            this.hide();
        }
    }

    void tableMainMouseReleased(MouseEvent e) {
        Calendar aCal = this.jPanelMain.getSelectCalendar();

        if (aCal != null) {
            String calText = calToString(aCal, this.df);

            if (this.textComp != null) {
                this.textComp.setText(calText);
            }

            this.hide();
            super.firePropertyChange(DATE_SELECTED, oldCal, aCal);
            this.oldCal = aCal;
        } else {
            this.show();
        }
    }

    /**
     * 以Winodws形式，取某组件的顶级窗口
     * @param aComp
     * @return
     */
    static Window getCompWinOwner(java.awt.Component aComp) {
        java.awt.Container parent = aComp.getParent();

        while (!(parent instanceof Window) && (parent != null)) {
            parent = parent.getParent();
        }

        return (Window) parent;
    }

    /**
     * 以Frame形式，取某组件的顶级窗口
     * @param aComp
     * @return
     */
    static Frame getCompFrameOwner(java.awt.Component aComp) {
        java.awt.Container parent = aComp.getParent();

        while (!(parent instanceof Frame) && (parent != null)) {
            parent = parent.getParent();
        }

        return (Frame) parent;
    }

    public static String calToString(Calendar             aCal,
                                     java.text.DateFormat df) {
        java.text.DateFormat dateFormat = defDateformat;

        if (df != null) {
            dateFormat = df;
        }

        return dateFormat.format(aCal.getTime());
    }

    public java.text.DateFormat getDf() {
        return df;
    }

    public PanelCalendar getJPanelMain() {
        return jpanelMain;
    }

    public void setDf(java.text.DateFormat df) {
        this.df = df;
    }
}
