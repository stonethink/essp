package client.essp.timesheet.weeklyreport.detail;

import java.util.*;

import javax.swing.event.TableModelEvent;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetDay;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetDetail;
import client.essp.common.view.ui.MessageUtil;
import client.essp.timesheet.period.TimesheetCalendar;
import client.essp.timesheet.weeklyreport.common.*;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.comDate;

/**
 * <p>Title: VMTableDetail</p>
 *
 * <p>Description: ��ʱ����ϸ����</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VMTableDetail extends VMTable2 {

    //Account, Activity, Code, Description
    private final static int COLUMN_NUM_BEFORE_DATE = 4;
    private final static int COLUMN_NUM_AFTER_DATE = 2;
    private final static String DATE_PATTEN = " (MM-dd)";
    private final static Map<Integer, String> WeekDateMap;
    private final static int DEFAULT_DECIMAL_DIGIT = 2;

    private static final Object[] projectNameConfig = new Object[] {"rsid.common.account",
            "accountName", VMColumnConfig.UNEDITABLE, new VWJText()};
    private static final Object[] activityNameConfig = new Object[] {"rsid.common.activity",
            "activityName", VMColumnConfig.UNEDITABLE, new VWJText()};
    private static final Object[] codeNameConfig = new Object[] {"rsid.common.code",
            "codeValueName", VMColumnConfig.UNEDITABLE, new VWJText()};
//    private static final Object[] jobDescriptionConfig = new Object[] {"Description",
//            "jobDescription", VMColumnConfig.EDITABLE, new VWJText()};

    private static final Object[] statusConfig = new Object[] {"rsid.common.status",
            "statusName", VMColumnConfig.UNEDITABLE, new VWJText()};

    private List<WorkHourListener> workHourListeners =
            new ArrayList<WorkHourListener>();
    private List<ColumnNumListener> columnNumListeners =
            new ArrayList<ColumnNumListener>();
    private List<StandarHoursListener> standarHoursListeners =
            new ArrayList<StandarHoursListener>();

    private int decimalDigit = DEFAULT_DECIMAL_DIGIT;
    private String workHourEditable = VMColumnConfig.EDITABLE;
    private boolean canBeforeActivityStart = true;
    private boolean canAfterActivityFinish = true;


    public VMTableDetail(Object[][] configs) {
        super(configs);
    }

    /**
     * Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * @param	rowIndex	the row whose value is to be queried
     * @param	columnIndex 	the column whose value is to be queried
     * @return	the value Object at the specified cell*/
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == getSumColumnIndex()) {
            return getRowSumValue(rowIndex);
        }
        //�ǹ�ʱ�����ݣ����ø����ȡ
        if (!isWorkHourColumn(columnIndex)) {
            return super.getValueAt(rowIndex, columnIndex);
        }
        //��ʱ���������⴦��
        DtoTimeSheetDetail dto = (DtoTimeSheetDetail) this.getRow(rowIndex);
        if(dto == null) return null;
        String data = ((VMColumnConfig) getColumnConfigs().get(columnIndex)).
                      getDataName();
        Date day = comDate.toDate(data);
        return dto.getHour(day);
//        if (hour != null) {
//            Double wkHour = hour.getWorkHours();
//            Double otHour = hour.getOvertimeHours();
//            //����мӰ�ʱ�䣬����ʾ
//            if(wkHour != null && otHour != null
//               && !otHour.equals(Double.valueOf(0))) {
//                return wkHour + "/" + otHour;
//            }
//            return wkHour;
//        }
//        return null;
    }
    
    private Date getWorkHourColumnDate(int columnIndex) {
    	if(isWorkHourColumn(columnIndex)) {
    		String data = ((VMColumnConfig) getColumnConfigs().get(columnIndex)).
            getDataName();
    		return comDate.toDate(data);
    	}
    	return null;
    }

    /**
     * Sets the value in the cell at <code>columnIndex</code> and
     * <code>rowIndex</code> to <code>aValue</code>.
     *
     * @param	aValue		 the new value
     * @param	rowIndex	 the row whose value is to be changed
     * @param	columnIndex 	 the column whose value is to be changed
     * @see #getValueAt
     * @see #isCellEditable*/
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (aValue != null && isWorkHourColumn(columnIndex)) {
            DtoTimeSheetDetail dto = (DtoTimeSheetDetail)this.getRow(
                    rowIndex);
            String data = ((VMColumnConfig) getColumnConfigs().get(columnIndex)).
                          getDataName();
            Date day = comDate.toDate(data);
            String sValue = aValue.toString();
            String[] sValues = sValue.split("/");
            Double wkHour = null;
            Double otHour = null;
            if (sValues.length >= 1) {
                try {
                    wkHour = Double.valueOf(sValues[0]);
                } catch (Exception e) {
                }
            }
            if (sValues.length >= 2) {
                try {
                    otHour = Double.valueOf(sValues[1]);
                } catch (Exception e) {
                }
            }
            //�Ӱ�ʱ�䲻�ܴ����ܹ�ʱ
            if (wkHour != null && otHour != null && otHour > wkHour) {
                otHour = wkHour;
            }

            dto.setHour(day, wkHour, otHour);
//            this.fireTableDataChanged();
            fireWorkHourChanged(columnIndex);
        } else {
            super.setValueAt(aValue, rowIndex, columnIndex);
        }
    }

    /**
     * ���ӹ�ʱ�仯������
     * @param l WorkHourListener
     */
    public void addWorkHourListener(WorkHourListener l) {
        workHourListeners.add(l);
    }
    
    /**
     * �����Ƿ�������ʱ��д��Activity��ʼ֮ǰ�ͽ���֮�����������Ƿ�ɱ༭
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
    	if(isWorkHourColumn(columnIndex) == false) {
    		return super.isCellEditable(rowIndex, columnIndex);
    	}
    	DtoTimeSheetDetail dto = (DtoTimeSheetDetail) this.getRow(rowIndex);
    	Date columnDate = getWorkHourColumnDate(columnIndex);
    	if(dto == null || columnDate == null) {
    		return super.isCellEditable(rowIndex, columnIndex);
    	}
    	Date start = dto.getActivityStart();
    	TimesheetCalendar.resetBeginDate(start);
    	if(!canBeforeActivityStart && start != null && columnDate.before(start)) {
    		return false;
    	}
    	Date finish = dto.getActivityFinish();
    	if(!canAfterActivityFinish && finish != null && columnDate.after(finish)) {
    		return false;
    	}
    	return super.isCellEditable(rowIndex, columnIndex);
    }

    /**
     * Forwards the given notification event to all
     * <code>TableModelListeners</code> that registered
     * themselves as listeners for this table model.
     *
     * @param e  the event to be forwarded
     *
     * @see #addTableModelListener
     * @see TableModelEvent
     * @see EventListenerList
     */
    public void fireTableChanged(TableModelEvent e) {
        super.fireTableChanged(e);
        fireAllWorkHourChanged();
    }

    /**
     * ���������еĹ�ʱ�������˱仯
     */
    private void fireAllWorkHourChanged() {
        for (int i = COLUMN_NUM_BEFORE_DATE; i < this.getSumColumnIndex(); i++) {
            fireWorkHourChanged(i);
        }
    }

    /**
     * ����ĳ�й�ʱ�仯
     * @param columnIndex int
     */
    private void fireWorkHourChanged(int columnIndex) {
        Iterator<WorkHourListener> iter = workHourListeners.iterator();
        while (iter.hasNext()) {
            WorkHourListener l = iter.next();
            l.workHourChanged(columnIndex, getColumnSumValue(columnIndex));
            l.workHourChanged(getSumColumnIndex(),
                              getColumnSumValue(getSumColumnIndex()));
        }
    }

    /**
     * ��������������
     * @param l ColumnNumListener
     */
    public void addColumnNumListener(ColumnNumListener l) {
        columnNumListeners.add(l);
    }

    /**
     * ���������仯
     */
    private void fireColumnNumChanged() {
        Iterator<ColumnNumListener> iter = columnNumListeners.iterator();
        while (iter.hasNext()) {
            iter.next().columnNumChanged( this.getColumnCount());
        }
    }

    /**
     * ���ӱ�׼��ʱ������
     * @param l StandarHoursListener
     */
    public void addStandarHoursListener(StandarHoursListener l) {
        standarHoursListeners.add(l);
    }

    /**
     * ������׼��ʱ�����仯
     * @param standarHours List
     */
    private void fireStandarHoursChanged(DtoTimeSheet data) {
    	List list = DtoTimeSheet2ChangeList(data);
        Iterator<StandarHoursListener> iter = standarHoursListeners.iterator();
        while(iter.hasNext()) {
            iter.next().standarHoursChanged(list);
        }
    }
    
    private final static List DtoTimeSheet2ChangeList(DtoTimeSheet data) {
    	if(data == null) return null;
    	List list = new ArrayList();
    	list.add(data.getStandarHours());
    	list.add(data.getLeaveHours());
    	list.add(data.getOvertimeHours());
    	list.add(data.getPreference());
    	list.add(data.editable());
    	return list;
    }

    /**
     * ��ȡĳ�й�ʱ֮��
     * @param rowIndex int
     * @return Double
     */
    public DtoTimeSheetDay getRowSumValue(int rowIndex) {
        Double sumWH = new Double(0);
        Double sumOT = new Double(0);
        DtoTimeSheetDetail dto = (DtoTimeSheetDetail)this.getRow(rowIndex);
        for (int i = COLUMN_NUM_BEFORE_DATE; i < getSumColumnIndex() && dto != null; i++) {

            String data = ((VMColumnConfig) getColumnConfigs().get(i)).
                          getDataName();
            Date day = comDate.toDate(data);
            DtoTimeSheetDay hour = dto.getHour(day);
            if(hour == null) continue;
            if (hour.getWorkHours() != null) {
                sumWH += hour.getWorkHours();
            }
            if(hour.getOvertimeHours() != null) {
                sumOT += hour.getOvertimeHours();
            }
        }
        DtoTimeSheetDay dtoDay = new DtoTimeSheetDay();
        dtoDay.setWorkHours(sumWH);
        dtoDay.setOvertimeHours(sumOT);
        return dtoDay;
    }

    /**
     * ��ȡĳ�й�ʱ֮��
     * @param columnIndex int
     * @return Double
     */
    private DtoTimeSheetDay getColumnSumValue(int columnIndex) {
        Double sumWH = new Double(0);
        Double sumOT = new Double(0);
        for (int i = 0; i < this.getRowCount(); i++) {
            DtoTimeSheetDay hour;
            if(columnIndex == getSumColumnIndex()) {
                hour = getRowSumValue(i);
            } else {
                DtoTimeSheetDetail dto = (DtoTimeSheetDetail)this.getRow(i);
                String data = ((VMColumnConfig) getColumnConfigs()
                               .get(columnIndex)).getDataName();
                Date day = comDate.toDate(data);
                hour = dto.getHour(day);
            }
            if(hour == null) continue;
            if (hour.getWorkHours() != null) {
                sumWH += hour.getWorkHours();
            }
            if(hour.getOvertimeHours() != null) {
                sumOT += hour.getOvertimeHours();
            }
        }
        DtoTimeSheetDay dtoDay = new DtoTimeSheetDay();
        dtoDay.setWorkHours(sumWH);
        dtoDay.setOvertimeHours(sumOT);
        return dtoDay;
    }

    public int getSumColumnIndex() {
        return this.getColumnCount() - 2;
    }

    /**
     * ��Model���������б���ʼ���У�����¼���ݣ���ʼ���У���
     * @param data DtoTimeSheet
     */
    void setData(DtoTimeSheet data) {
        if(data == null) {
            this.setDecimalDigit(DEFAULT_DECIMAL_DIGIT);
            this.setDateConfigs(new ArrayList());
            this.setRows(new ArrayList());
            this.fireStandarHoursChanged(null);
            this.fireWorkHourChanged(this.getSumColumnIndex());
            return;
        }
        int decimalDigit = data.getDecimalDigit();
        this.canAfterActivityFinish = data.isCanAfterActivityFinish();
        this.canBeforeActivityStart = data.isCanBeforeActivityStart();
        List dateList = new ArrayList(data.getDateList());
        List detailList = new ArrayList(data.getTsDetails());

        this.setDecimalDigit(decimalDigit);
        this.setDateConfigs(dateList);
        this.setRows(detailList);
        this.fireStandarHoursChanged(data);
    }

    /**
     * ���ù�ʱ����д��С��λ
     * @param decimalDigit int
     */
    void setDecimalDigit(int decimalDigit) {
        this.decimalDigit = decimalDigit;
    }

    /**
     * ���������б�����������
     * @param DateList List
     */
    private void setDateConfigs(List<Date> dateList) {
        if(dateList == null) return;
        this.getColumnConfigs().clear();
        int length = dateList.size() + COLUMN_NUM_BEFORE_DATE +
                     COLUMN_NUM_AFTER_DATE;
        Object[][] configs = new Object[length][];
        configs[0] = projectNameConfig;
        configs[1] = activityNameConfig;
        configs[2] = codeNameConfig;
        configs[3] = getJobDescriptionConfig();
        for (int i = 0; i < dateList.size(); i++) {
            configs[i + COLUMN_NUM_BEFORE_DATE] = Date2Config(dateList.get(i));
        }
        configs[length - 2] = new Object[] {"rsid.common.sum", "sumHour",
                              VMColumnConfig.UNEDITABLE, getWorkHourComp()};
        configs[length - 1] = statusConfig;
        setColumnConfigs(configs);
        //���������仯
        fireColumnNumChanged();
    }

    /**
     * ��ȡJobDescription��λ������
     * @return Object[]
     */
    private Object[] getJobDescriptionConfig() {
        return new Object[] {"rsid.common.description",
            "jobDescription", workHourEditable, new VWJText()};
    }

    /**
     * �����б��жϴ����Ƿ�Ϊ��ʱ��
     * @param columnIndex int
     * @param columnCount int
     * @return boolean
     */
    public boolean isWorkHourColumn(int columnIndex) {
        return columnIndex >= COLUMN_NUM_BEFORE_DATE
                && columnIndex < (getColumnCount() - COLUMN_NUM_AFTER_DATE);
    }
    
    /**
     * �жϴ������Ƿ��ǡ���������
     * @param columnIndex
     * @return
     */
    public final static boolean isDescriptionColumn(int columnIndex) {
    	return columnIndex == 3;
    }

    /**
     * �������ڻ�ȡ������
     * @param d Date
     * @return Object[]
     *    Object[0] ColumnName: "���ڼ�� (��-��)",  Mon (07-03)
     *    Object[1] DataName  : "(��-��-��)", 2007-07-03
     *    Object[2] isEditable: EDITABLE
     *    Object[3] Component : VWJReal
     */
    public Object[] Date2Config(Date d) {
        return new Object[] {getWorkHourColumnName(d), getWorkHourDataName(d),
                workHourEditable, getWorkHourComp()};
    }

    /**
     * ����Work Hour���Ƿ�ɱ༭
     * @param editable boolean
     */
    public void setWorkHourEditable(boolean editable) {
        workHourEditable =
            editable ? VMColumnConfig.EDITABLE : VMColumnConfig.UNEDITABLE;
    }

    /**
     * ���ݵ�ǰС��λDecimalDigit������VWJReal
     * @return VWJReal
     */
    public VWJTwoNumReal getWorkHourComp() {
        VWJTwoNumReal real = new VWJTwoNumReal();
        real.setMaxInputDecimalDigit(decimalDigit);
        return real;
    }

    /**
     * �������ڻ�ȡ��ʱ������
     * @param d Date
     * @return String "���ڼ�� (��-��)",  Mon (07-03)
     */
    public static String getWorkHourColumnName(Date d) {
        return getDayShortNameOfWeek(d) + comDate.dateToString(d, DATE_PATTEN);
    }

    /**
     * �������ڻ�ȡ���ڼ��
     * @param d Date
     * @return String
     */
    private static String getDayShortNameOfWeek(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        String rsid = WeekDateMap.get(c.get(Calendar.DAY_OF_WEEK));
        return MessageUtil.getMessage(rsid);
    }

    /**
     * �������ڻ�ȡ������������
     * @param d Date
     * @return String "(��-��-��)", yyyy-MM-dd, 2007-07-03
     */
    public static String getWorkHourDataName(Date d) {
        return comDate.dateToString(d);
    }

    static {
        WeekDateMap = new HashMap<Integer, String>();
        WeekDateMap.put(Calendar.MONDAY, "rsid.timesheet.mon");
        WeekDateMap.put(Calendar.TUESDAY, "rsid.timesheet.tue");
        WeekDateMap.put(Calendar.WEDNESDAY, "rsid.timesheet.wed");
        WeekDateMap.put(Calendar.THURSDAY, "rsid.timesheet.thu");
        WeekDateMap.put(Calendar.FRIDAY, "rsid.timesheet.fri");
        WeekDateMap.put(Calendar.SATURDAY, "rsid.timesheet.sat");
        WeekDateMap.put(Calendar.SUNDAY, "rsid.timesheet.sun");
    }
}
