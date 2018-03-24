package client.essp.common.calendar;

import client.framework.common.Constant;
import client.framework.view.common.comMSG;
import client.framework.view.event.DataChangedListener;

import com.essp.calendar.WorkDay;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;

import server.framework.common.BusinessException;

import java.util.Date;
import client.framework.common.Global;


//import java.awt.*;
public class EditableCalendarPanel extends JPanel {
    public static final String         DATE_SELECT_CHANGE   = "calendar data select changed";
    private BorderLayout               borderLayout1        = new BorderLayout();
    private JScrollPane                jScrollPane1         = new JScrollPane();
    private JPanel                     jPanel1              = new JPanel();
    private BorderLayout               borderLayout2        = new BorderLayout();
    private JPanel                     jPaneSubSeg          = new JPanel();
    private JButton                    jButtonSubMonth      = new JButton();
    private JButton                    jButtonSubYear       = new JButton();
    private JPanel                     jPanelAddSeg         = new JPanel();
    private JButton                    jButtonAddYear       = new JButton();
    private JButton                    jButtonAddMonth      = new JButton();
    private JPanel                     jPanelYearMonth      = new JPanel();
    private JLabel                     jLabelYearMonth      = new JLabel();
    private FlowLayout                 flowLayout1          = new FlowLayout();
    private FlowLayout                 flowLayout2          = new FlowLayout();
    private EditableCalendarTableModel tblModel             = null;
    private JTable                     table                = null;
    private JTableHeader               tHeader;
    private ListSelectionModel         selectModel;
    private ListSelectionModel         colSelectModel;
    private java.util.List             dataChangedLisenters = new ArrayList();

    /**
     * ��һ�����߳��в���ѡ��仯�¼���ÿ��ѡ��仯�������ϴ���������ʱһ��ʱ��
     * ������ֹһ��ѡ��仯���������¼�(������������һ��)
     * Ҳ��ֹ����±仯̫��ʱ���������¼���
     */
    private SelectChgHdlThread selChgHdlThread = new SelectChgHdlThread(this);
    private WorkDay[]          workDay;
    private ArrayList          lstSelectCals  = new ArrayList();
    private int[]              iRows          = null;
    private int[]              iCols          = null;
    private int                iDate          = 0;
    private boolean            settingChanged = false;

    //    JButton jButton1 = new JButton();
    public EditableCalendarPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        workDay = tblModel.getWorkDay();
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        tHeader.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent event) {
                    jTableMainHeadMouseClicked(event);
                }
            });
        setSelectToday();
    }

    /**
     * ֻ����ʾ��ʱ�Ĺ��캯��
     */
    public EditableCalendarPanel(boolean isShowOnly) {
        try {
            initModel();
            jbInit();
        } catch (Exception ex) {
        	ex.printStackTrace();
        }

        if (isShowOnly == false) {
            table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            tHeader.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent event) {
                        jTableMainHeadMouseClicked(event);
                    }
                });
        } else {
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
        setSelectToday();
    }
    /**
     * ��ʼ������model
     */
    protected void initModel(){
         tblModel = new EditableCalendarTableModel();
         table = new JTable(tblModel);
    }
    protected CalendarCellRenderer initCellRenderer() {
    	return new CalendarCellRenderer();
    }
    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setMinimumSize(new Dimension(105, 95));
        table.setPreferredSize(new Dimension(525, 95));
        table.setCellSelectionEnabled(true);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        table.setDefaultRenderer(Object.class,
        						 initCellRenderer());
        tHeader = table.getTableHeader();

        //�������ƶ���
        tHeader.setReorderingAllowed(false);

        //jTableMain.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        selectModel    = table.getSelectionModel();
        colSelectModel = table.getColumnModel().getSelectionModel();

        jPanel1.setLayout(borderLayout2);
        jButtonSubMonth.setToolTipText("Last Month");
        jButtonSubMonth.setMargin(new Insets(0, 1, 0, 1));
        jButtonSubMonth.setText("<");
        jButtonSubMonth.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButtonSubMonthActionPerformed(e);
                }
            });
        jButtonSubYear.setToolTipText("Last Year");
        jButtonSubYear.setMargin(new Insets(0, 1, 0, 1));
        jButtonSubYear.setText("<<");
        jButtonSubYear.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButtonSubYearActionPerformed(e);
                }
            });
        jButtonAddYear.setToolTipText("Next Year");
        jButtonAddYear.setMargin(new Insets(0, 1, 0, 1));
        jButtonAddYear.setText(">>");
        jButtonAddYear.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButtonAddYearActionPerformed(e);
                }
            });
        jButtonAddMonth.setToolTipText("Next Month");
        jButtonAddMonth.setMargin(new Insets(0, 1, 0, 1));
        jButtonAddMonth.setText(">");
        jButtonAddMonth.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButtonAddMonthActionPerformed(e);
                }
            });
        jLabelYearMonth.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelYearMonth.setText(tblModel.getYear() + "-" + tblModel.getMonth());

        jPaneSubSeg.setLayout(flowLayout1);
        jPanelAddSeg.setLayout(flowLayout1);
        flowLayout1.setHgap(2);
        flowLayout1.setVgap(1);
        tblModel.addTableModelListener(new TableModelListener() {
                public void tableChanged(TableModelEvent e) {
                    tblModelTableChanged(e);
                }
            });
        this.setBorder(BorderFactory.createRaisedBevelBorder());
        jPanelYearMonth.setLayout(flowLayout2);
        flowLayout2.setHgap(5);
        flowLayout2.setVgap(3);
        selectModel.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    selectModelValueChanged(e);
                }
            });
        colSelectModel.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    colSelectModelValueChanged(e);
                }
            });
        jScrollPane1.setPreferredSize(new Dimension(454, 110));
        this.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(table, null);
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

//        setSelectToday();
        selChgHdlThread.start();
        workDay = tblModel.getWorkDay();
    }

    /**
     * ������ڱ仯��������/�µ���ʾ
     * @param e
     */
    private void tblModelTableChanged(TableModelEvent e) {
        this.jLabelYearMonth.setText(tblModel.getYear() + "-"
                                     + tblModel.getMonth());
    }

    /**
     * �����ѡ�����仯
     * @param e
     */
    private void selectModelValueChanged(ListSelectionEvent e) {
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
     * �����ѡ�����仯�����б仯һ�����һ��ѡ��ı仯
     * @param e
     */
    private void colSelectModelValueChanged(ListSelectionEvent e) {
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
     * �����ͷѡ�����¼�
     * @param event MouseEvent
     */
    private void jTableMainHeadMouseClicked(MouseEvent event) {
        if (event.getClickCount() == 1) {
            int tableColumn = table.columnAtPoint(event.getPoint());
            table.setColumnSelectionInterval(tableColumn, tableColumn);
            table.setRowSelectionInterval(0, 5);
        }
    }

    /**
     * �����·ݼ�1
     * @param e
     */
    private void jButtonSubMonthActionPerformed(ActionEvent e) {
        if (tblModel.month == 1) {
            if (settingChanged) {
                asktoSave();
            }

            changeYearMonth(-1);
            tblModel.getSettings();
            workDay = tblModel.getWorkDay();
        } else {
            changeYearMonth(-1);
            tblModel.calcData();
        }

        selectedDays();
    }

    /**
     * �����·ּ�1
     * @param e
     */
    private void jButtonAddMonthActionPerformed(ActionEvent e) {
        if (tblModel.month == 12) {
            if (settingChanged) {
                asktoSave();
            }

            changeYearMonth(1);
            tblModel.getSettings();
            workDay = tblModel.getWorkDay();
        } else {
            changeYearMonth(1);
            tblModel.calcData();
        }

        selectedDays();
    }

    /**
     * ������ȼ�1
     * @param e
     */
    private void jButtonSubYearActionPerformed(ActionEvent e) {
        if (settingChanged) {
            asktoSave();
        }

        changeYearMonth(-2);
        tblModel.getSettings();
        workDay = tblModel.getWorkDay();
        selectedDays();
    }

    /**
     * ��������ȼ�1
     * @param e
     */
    private void jButtonAddYearActionPerformed(ActionEvent e) {
        if (settingChanged) {
            asktoSave();
        }

        changeYearMonth(2);
        tblModel.getSettings();
        workDay = tblModel.getWorkDay();
        selectedDays();
    }

    /**
     * ������/�µĸı�
     * @param mode �ı䷽ʽ��2��-2����ʾ��ȼӼ�
     *                       1��-1 ��ʾ�·ݼӼ�
     */
    private void changeYearMonth(int mode) {
        iRows = table.getSelectedRows();
        iCols = table.getSelectedColumns();

        if ((iRows.length == 1) && (iCols.length == 1)) {
            if ((iRows[0] >= 0) && (iCols[0] >= 0)) {
                iDate = tblModel.getCellDate(iRows[0], iCols[0]);
            }
        }

        switch (mode) {
            case -2:
                tblModel.setYear(tblModel.getYear() - 1);

                break;

            case -1:
                tblModel.setMonth(tblModel.getMonth() - 1);

                break;

            case 1:
                tblModel.setMonth(tblModel.getMonth() + 1);

                break;

            case 2:
                tblModel.setYear(tblModel.getYear() + 1);

                break;

            default:}
    }

    /**
     * ѡ����ǰ(�л�����ǰ)ѡ�е�ĳ�����ڻ��߶�Ӧ������
     * @return ArrayList
     */
    protected void selectedDays() {
        if ((iRows.length == 1) && (iCols.length == 1)) {
            if (iDate > 0) {
                java.awt.Point p = tblModel.getDateCell(iDate);

                if (p != null) {
                    table.setRowSelectionInterval(p.y, p.y);
                    table.setColumnSelectionInterval(p.x, p.x);
                }
            }
        } else {
            if ((iRows.length >= 1) && (iCols.length >= 1)) {
                table.setRowSelectionInterval(iRows[0], iRows[iRows.length - 1]);
                table.setColumnSelectionInterval(iCols[0],
                                                 iCols[iCols.length - 1]);
            }
        }
    }

    /**
     * ȡѡ�����������
     * @return
     */
    public ArrayList getSelectCalendars() {
        int[]     iRows      = table.getSelectedRows();
        int[]     iCols      = table.getSelectedColumns();
        ArrayList alCalendar = new ArrayList();

        for (int i = 0; i < iRows.length; i++) {
            for (int j = 0; j < iCols.length; j++) {
                if ((iRows[i] >= 0) && (iCols[j] >= 0)
                        && (tblModel.getCellCalendar(iRows[i], iCols[j]) != null)) {
                    alCalendar.add(tblModel.getCellCalendar(iRows[i], iCols[j]));
                }
            }
        }

        return alCalendar;
    }

    /**
     * ����ѡ��仯�¼���ÿ��ѡ��仯�������ϴ���������һ�����߳�����ʱһ��ʱ�䴦��
     * ������ֹһ��ѡ��仯���������¼�(������������һ��)
     * Ҳ��ֹ����±仯̫��ʱ���������¼���
     */
    protected void handleChanged(ListSelectionEvent e) {
        selChgHdlThread.setChanged(true);

        //System.out.println("selectModel_valueChanged e="+e);
    }

    //Ӧ�ü�������ѡ�����仯
    public void addDataChangedListener(DataChangedListener lisenter) {
        this.dataChangedLisenters.add(lisenter);
    }

    /**
     * ����һ���߳��е������������
     * ��������ѡ��仯�¼�����handleChanged �ɼ�ӵ����������
     */
    protected void fireDataChanged() {
        ArrayList currSelectCals = getSelectCalendars();

        if ((currSelectCals != null)
                && !sameDates(lstSelectCals, currSelectCals)) {
            for (Iterator it = this.dataChangedLisenters.iterator();
                     it.hasNext();) {
                DataChangedListener listener = (DataChangedListener) it.next();
                listener.processDataChanged();
            }

            lstSelectCals = currSelectCals;
        }
    }

    /**
     * �ж��Ƿ�ѡ����ͬ�����ڻ���������
     * @param WorkDayOrHolidayOrDefault String
     */
    protected boolean sameDates(ArrayList lstSelect,
                                ArrayList currSelect) {
        if ((lstSelect == null) || (currSelect == null)) {
            return false;
        } else if (lstSelect.size() != currSelect.size()) {
            return false;
        } else {
            for (int i = 0; i < lstSelect.size(); i++) {
                if (sameDate((Calendar) lstSelect.get(i),
                                 (Calendar) currSelect.get(i)) == false) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * �ж����������Ƿ�Ϊͬһ��
     * @param aCal
     * @param otherCal
     * @return
     */
    public static boolean sameDate(Calendar aCal,
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
     * ���ñ�ѡ�е�ʱ��Ϊ����
     */
    public void setSelectToday() {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        Point    p = tblModel.getDateCell(cal.get(Calendar.DAY_OF_MONTH));
        table.setRowSelectionInterval(p.y, p.y);
        table.setColumnSelectionInterval(p.x, p.x);
    }

    public WorkDay[] getWorkDay() {
        return workDay;
    }

    /**
     * ���ù����շǹ����պ�Ĭ��ֵ
     * @param WorkDayOrHolidayOrDefault String
     */
    public void setWorkDay(String workDayOrHolidayOrDefault) {
        iRows          = table.getSelectedRows();
        iCols          = table.getSelectedColumns();
        settingChanged = true;

        ArrayList alCalendar = this.getSelectCalendars();

        for (int i = 0; i < alCalendar.size(); i++) {
            Calendar cal  = (Calendar) alCalendar.get(i);
            int      date = cal.get(Calendar.DAY_OF_YEAR) - 1;

            if (workDayOrHolidayOrDefault.equals(WorkDay.DEFAULT_SETTINGS) == false) {
                workDay[date].setDayType(workDayOrHolidayOrDefault);
            } else {
                workDay[date].setDefualt();
            }
        }

        tblModel.setWorkDay(workDay);
        tblModel.calcData();

        if ((iRows.length >= 1) && (iCols.length >= 1)) {
            table.setRowSelectionInterval(iRows[0], iRows[iRows.length - 1]);
            table.setColumnSelectionInterval(iCols[0], iCols[iCols.length - 1]);
        }
    }

    /**
     * ���ñ���
     * @param calendar
     */
    public void setToDay(Calendar calendar) {
        this.tblModel.setTodayCalendar(calendar);
    }

    /**
     * ˢ�»�ȡ��������
     * @param args String[]
     */
    public void refreshSettings() {
        //        if (settingChanged) {
        //            asktoSave();
        //        }
        Date today = new Date();
        Calendar ca = Calendar.getInstance();
        ca.setTime(today);
        setToDay(ca);
        tblModel.year  = ca.get(Calendar.YEAR);
        tblModel.month = ca.get(Calendar.MONTH) + 1;
        tblModel.getSettings();
        workDay = tblModel.getWorkDay();
        setSelectToday();
    }

    /**
     * ��ʾ�Ƿ񱣴浱ǰ��������޸�
     * @param mode int
     */
    public void asktoSave() {
        int doSave = comMSG.dispConfirmDialog("Save the Changes of This Year?");

        if (doSave == Constant.OK) {
            saveSettings();
        }

        settingChanged = false;
    }

    /**
     * ��������
     * @param args String[]
     */
    public void saveSettings() {
        tblModel.saveSettings();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            //com.enovation.ui.UISetting.SettingUIManager();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //        NetConnector connector = ConnectFactory.createConnector();
        //        connector.setDefaultControllerURL("http://localhost:8080/Controller");
        EditableCalendarPanel multiCalendars = new EditableCalendarPanel(true);

        JOptionPane.showMessageDialog(null, multiCalendars);

        ArrayList al = multiCalendars.getSelectCalendars();

        for (int i = 0; i < al.size(); i++) {
            System.out.println("select Date:"
                               + ((Calendar) al.get(i)).getTime());
        }
    }

    public JTable getTable() {
        return table;
    }

    public EditableCalendarTableModel getTblModel() {
        return tblModel;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public void setTblModel(EditableCalendarTableModel tblModel) {
        this.tblModel = tblModel;
    }

    //    void jButton1_setWorkDay(ActionEvent e) {
    //        setWorkDay(WorkDay.DEFAULT_SETTINGS);
    //    }
    class SelectChgHdlThread extends Thread {
        EditableCalendarPanel owner;
        boolean               stop    = false;
        private boolean       changed = false;

        public SelectChgHdlThread(EditableCalendarPanel owner) {
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
                        owner.fireDataChanged();
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
}
