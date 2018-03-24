package client.essp.common.calendar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;

import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import java.util.List;
import java.util.ArrayList;
import client.framework.view.event.DataSelectListener;


/**
 * <p>Title: </p>
 * <p>Description: 一个日历面板，
 * 当日期发生改变时引发一个名字为DATE_SELECT_CHANGE的属性变化</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Enovation</p>
 * @author Xuxi.Chen
 * @version 1.0
 */
public class PanelCalendar extends JPanel {
    public static final String     DATE_SELECT_CHANGE = "calendar data select changed";
    BorderLayout                   borderLayout1   = new BorderLayout();
    JScrollPane                    jScrollPane1    = new JScrollPane();
    JPanel                         jPanel1         = new JPanel();
    BorderLayout                   borderLayout2   = new BorderLayout();
    JPanel                         jPaneSubSeg     = new JPanel();
    JButton                        jButtonSubMonth = new JButton();
    JButton                        jButtonSubYear  = new JButton();
    JPanel                         jPanelAddSeg    = new JPanel();
    JButton                        jButtonAddYear  = new JButton();
    JButton                        jButtonAddMonth = new JButton();
    JPanel                         jPanelYearMonth = new JPanel();
    JLabel                         jLabelYearMonth = new JLabel();
    TableModelCalendar             tblModel        = new TableModelCalendar();
    JTable                         jTableMain      = new JTable(tblModel);
    javax.swing.ListSelectionModel selectModel; // = jTableMain.getSelectionModel();
    javax.swing.ListSelectionModel colSelectModel; // = jTableMain.getSelectionModel();
    FlowLayout                     flowLayout1     = new FlowLayout();
    FlowLayout                     flowLayout2     = new FlowLayout();
    java.util.Calendar             lstCalendar     = null;

    /**
     * 在一个新线程中产生选择变化事件，每次选择变化并不马上处理，而是延时一段时间
     * 这样防止一次选择变化产生两次事件(行与列名产生一次)
     * 也防止年或月变化太快时产生两次事件。
     */
    SelectChgHdlThread selChgHdlThread = new SelectChgHdlThread(this);

    List dataSelectListener = new ArrayList();

    /**
     * 默认的构造方法
     */
    public PanelCalendar() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jTableMain.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableMain.setMinimumSize(new Dimension(105, 90));
        jTableMain.setPreferredSize(new Dimension(525, 90));
        jTableMain.setCellSelectionEnabled(true);

        //        jTableMain.setColumnSelectionAllowed(true);
        //        jTableMain.setRowSelectionAllowed(true);
        selectModel    = jTableMain.getSelectionModel();
        colSelectModel = jTableMain.getColumnModel().getSelectionModel();

        //        jTableMain.setSelectionMode();
        jPanel1.setLayout(borderLayout2);
        jButtonSubMonth.setToolTipText("Last Month");
        jButtonSubMonth.setMargin(new Insets(0, 1, 0, 1));
        jButtonSubMonth.setText("<");
        jButtonSubMonth.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButtonSubMonthActionPerformed(e);
                }
            });
        jButtonSubYear.setToolTipText("Last Year");
        jButtonSubYear.setMargin(new Insets(0, 1, 0, 1));
        jButtonSubYear.setText("<<");
        jButtonSubYear.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButtonSubYearActionPerformed(e);
                }
            });
        jButtonAddYear.setToolTipText("Next Year");
        jButtonAddYear.setMargin(new Insets(0, 1, 0, 1));
        jButtonAddYear.setText(">>");
        jButtonAddYear.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButtonAddYearActionPerformed(e);
                }
            });
        jButtonAddMonth.setToolTipText("Next Month");
        jButtonAddMonth.setMargin(new Insets(0, 1, 0, 1));
        jButtonAddMonth.setText(">");
        jButtonAddMonth.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButtonAddMonthActionPerformed(e);
                }
            });
        jLabelYearMonth.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelYearMonth.setText(tblModel.getYear() + "-" + tblModel.getMonth());
        jTableMain.setShowHorizontalLines(false);
        jTableMain.setShowVerticalLines(false);
        jTableMain.setDefaultRenderer(Object.class, new CalendarCellRenderer());
        jPaneSubSeg.setLayout(flowLayout1);
        jPanelAddSeg.setLayout(flowLayout1);
        flowLayout1.setHgap(2);
        flowLayout1.setVgap(1);
        tblModel.addTableModelListener(new javax.swing.event.TableModelListener() {
                public void tableChanged(TableModelEvent e) {
                    tblModelTableChanged(e);
                }
            });
        this.setBorder(BorderFactory.createRaisedBevelBorder());
        jPanelYearMonth.setLayout(flowLayout2);
        flowLayout2.setHgap(5);
        flowLayout2.setVgap(3);
        selectModel.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    selectModelValueChanged(e);
                }
            });
        colSelectModel.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    colSelectModelValueChanged(e);
                }
            });
        jScrollPane1.setPreferredSize(new Dimension(454, 110));
        this.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(jTableMain, null);
        this.add(jPanel1, BorderLayout.NORTH);
        jPanel1.add(jPaneSubSeg, BorderLayout.WEST);
        jPaneSubSeg.add(jButtonSubYear, null);
        jPaneSubSeg.add(jButtonSubMonth, null);
        jPanel1.add(jPanelAddSeg, BorderLayout.EAST);
        jPanelAddSeg.add(jButtonAddMonth, null);
        jPanelAddSeg.add(jButtonAddYear, null);
        jPanel1.add(jPanelYearMonth, BorderLayout.CENTER);
        jPanelYearMonth.add(jLabelYearMonth, null);
        setPreferredSize(new Dimension(210, 160));

        //jTableMain.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        //不允许移动列
        jTableMain.getTableHeader().setReorderingAllowed(false);
        selChgHdlThread.start();
        setSelectToday();
    }

    /**
     * 设置被选中的时期为今天
     */
    private void setSelectToday() {
        Calendar cal = this.tblModel.getTodayCalendar();
        Point    p = this.tblModel.getDateCell(cal.get(Calendar.DAY_OF_MONTH));
        this.jTableMain.setRowSelectionInterval(p.y, p.y);
        this.jTableMain.setColumnSelectionInterval(p.x, p.x);
    }

    /**
     * 日历的年度加1
     * @param e
     */
    void jButtonAddYearActionPerformed(ActionEvent e) {
        changeYearMonth(2);
    }

    /**
     * 日历年度减1
     * @param e
     */
    void jButtonSubYearActionPerformed(ActionEvent e) {
        changeYearMonth(-2);
    }

    /**
     * 日历月份减1
     * @param e
     */
    void jButtonSubMonthActionPerformed(ActionEvent e) {
        changeYearMonth(-1);
    }

    /**
     * 日历月分加1
     * @param e
     */
    void jButtonAddMonthActionPerformed(ActionEvent e) {
        changeYearMonth(1);
    }

    /**
     * 处理年/月的改变
     * @param mode 改变方式：2，-2：表示年度加减
     *                       1，-1 表示月份加减
     */
    private void changeYearMonth(int mode) {
        int iRow  = this.jTableMain.getSelectedRow();
        int iCol  = this.jTableMain.getSelectedColumn();
        int iDate = 0;

        if ((iRow >= 0) && (iCol >= 0)) {
            iDate = this.tblModel.getCellDate(iRow, iCol);
        }

        switch (mode) {
            case -2:
                this.tblModel.setYear(tblModel.getYear() - 1);

                break;

            case -1:
                this.tblModel.setMonth(tblModel.getMonth() - 1);

                break;

            case 1:
                this.tblModel.setMonth(tblModel.getMonth() + 1);

                break;

            case 2:
                this.tblModel.setYear(tblModel.getYear() + 1);

                break;

            default:}

        if (iDate > 0) {
            java.awt.Point p = this.tblModel.getDateCell(iDate);

            if (p != null) {
                this.jTableMain.setRowSelectionInterval(p.y, p.y);
                this.jTableMain.setColumnSelectionInterval(p.x, p.x);
            }
        }
    }

    /**
     * 处理选择变化事件，每次选择变化并不马上处理，而是在一个新线程中延时一段时间处理
     * 这样防止一次选择变化产生两次事件(行与列名产生一次)
     * 也防止年或月变化太快时产生两次事件。
     */
    protected void handleChanged(ListSelectionEvent e) {
        selChgHdlThread.setChanged(true);

        //System.out.println("selectModel_valueChanged e="+e);
    }

    /**
     * 在另一个线程中调用这个方法。
     * 引发日期选择变化事件，由handleChanged 可间接调用这个方法
     */
    protected void fireDataSelectChanged() {
        int iRow = this.jTableMain.getSelectedRow();
        int iCol = this.jTableMain.getSelectedColumn();

        if ((iRow < 0) || (iCol < 0)) {
            return;
        }

        Calendar selectCal = this.tblModel.getCellCalendar(iRow, iCol);

        if ((selectCal != null) && !sameDate(selectCal, lstCalendar)) {
            super.firePropertyChange(DATE_SELECT_CHANGE, lstCalendar, selectCal);

            for (int i = 0; i < dataSelectListener.size(); i++) {
                DataSelectListener listener = (DataSelectListener)
                                              dataSelectListener.get(i);
                listener.processDataSelect(lstCalendar, selectCal);
            }

            //System.out.println("firePropertyChange completely="+DATE_SELECT_CHANGE);
            lstCalendar = selectCal;
        }
    }

    public void addDataSelectListener(DataSelectListener listener){
        this.dataSelectListener.add(listener);
    }

    /**
     * 判断两个日期是否为同一天
     * @param aCal
     * @param otherCal
     * @return
     */
    static boolean sameDate(Calendar aCal,
                            Calendar otherCal) {
        if ((aCal == null) || (otherCal == null)) {
            return false;
        }

        if (aCal.equals(otherCal)) {
            return true;
        }

        return (aCal.get(Calendar.YEAR) == otherCal.get(Calendar.YEAR))
               && (aCal.get(Calendar.MONTH) == otherCal.get(Calendar.MONTH))
               && (aCal.get(Calendar.DAY_OF_MONTH) == otherCal.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 表格日期变化，更新年/月的显示
     * @param e
     */
    void tblModelTableChanged(TableModelEvent e) {
        this.jLabelYearMonth.setText(tblModel.getYear() + "-"
                                     + tblModel.getMonth());
    }

    /**
     * 表格行选择发生变化
     * @param e
     */
    void selectModelValueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }

        DefaultListSelectionModel selectModel = (DefaultListSelectionModel) e
                                                .getSource();

        if (selectModel.isSelectionEmpty()) {
            return;
        }

        handleChanged(e);
    }

    /**
     * 表格列选择发生变化，与行变化一起组成一个选择的变化
     * @param e
     */
    void colSelectModelValueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }

        DefaultListSelectionModel selectModel = (DefaultListSelectionModel) e
                                                .getSource();

        if (selectModel.isSelectionEmpty()) {
            return;
        }

        handleChanged(e);
    }

    /**
     * 取选择的日历
     * @return
     */
    public Calendar getSelectCalendar() {
        int iRow = this.jTableMain.getSelectedRow();
        int iCol = this.jTableMain.getSelectedColumn();

        if ((iRow < 0) || (iCol < 0)) {
            return null;
        }

        return this.tblModel.getCellCalendar(iRow, iCol);
    }

    /**
     * 设置选中的日期
     * @param calendar
     */
    public void setSelectCalendar(Calendar calendar) {
        this.tblModel.setYear(calendar.get(Calendar.YEAR));
        this.tblModel.setMonth(calendar.get(Calendar.MONTH) + 1);

        Point p = this.tblModel.getDateCell(calendar.get(Calendar.DAY_OF_MONTH));
        this.selectModel.setSelectionInterval(p.y, p.y);
        this.colSelectModel.setSelectionInterval(p.x, p.x);
    }

    /**
     * 设置本日
     * @param calendar
     */
    public void setToDay(Calendar calendar) {
        this.tblModel.setTodayCalendar(calendar);
    }

    //for test
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            //com.enovation.ui.UISetting.SettingUIManager();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PanelCalendar panelCalendar1 = new PanelCalendar();
        javax.swing.JOptionPane.showMessageDialog(null, panelCalendar1);
        System.out.println("select Date:"
                           + panelCalendar1.getSelectCalendar().getTime());
    }

    public JTable getJTableMain() {
        return jTableMain;
    }
}


class SelectChgHdlThread extends Thread {
    PanelCalendar   owner;
    boolean         stop    = false;
    private boolean changed = false;

    public SelectChgHdlThread(PanelCalendar owner) {
        this.owner = owner;
    }

    synchronized void setChanged(boolean changed) {
        this.changed = changed;
    }

    public void run() {
        while (!stop) {
            if (changed) {
                try {
                    sleep(100);
                } catch (InterruptedException ex1) {
                    ex1.printStackTrace();
                }

                synchronized (this) {
                    owner.fireDataSelectChanged();
                    changed = false;
                }
            }

            yield();

            try {
                sleep(50);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
