package client.essp.timesheet.weeklyreport.summary;

import java.math.BigDecimal;
import java.util.*;

import javax.swing.table.AbstractTableModel;

import client.essp.common.view.ui.MessageUtil;
import client.essp.timesheet.weeklyreport.common.ColumnNumListener;
import client.essp.timesheet.weeklyreport.common.ResetRenderListener;
import client.essp.timesheet.weeklyreport.common.WorkHourListener;
import client.essp.timesheet.weeklyreport.common.StandarHoursListener;
import c2s.essp.timesheet.preference.DtoPreference;
import c2s.essp.timesheet.weeklyreport.DtoAttLeave;
import c2s.essp.timesheet.weeklyreport.DtoAttOvertime;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetDay;
import client.essp.timesheet.weeklyreport.common.SubmitTimeSheetListener;
import client.framework.view.common.comMSG;

/**
 *
 * <p>Title: VMTableSum</p>
 *
 * <p>Description: 工时单汇总Table Model</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VMTableSum extends AbstractTableModel
        implements WorkHourListener, ColumnNumListener,
        StandarHoursListener, SubmitTimeSheetListener{

    public final static int BEGIN_HOUR_COLUMN_INDEX = 1;
    //Detail List 与 Summary List列监听偏差
    public final static int COLUMN_OFFSET = 4;

    final static int HEAD_COLUMN_INDEX = 0;
    final static int ACTUAL_HOUR_ROW_INDEX = 0;
    final static int LEAVE_HOUR_ROW_INDEX = 1;
    final static int OVERTIME_HOUR_ROW_INDEX = 2;
    final static int STANDAR_HOUR_ROW_INDEX = 3;
    final static int VARIANT_ROW_INDEX = 4;

    final static int ALL_ROW_CCOUNT = 5;

    private List<ResetRenderListener> resetRenderListeners = new ArrayList<ResetRenderListener> ();
    //当前行的时间数据,由表格TableWeeklyReport的model维护
    Map<Integer, DtoTimeSheetDay> hoursOnWeek = new HashMap<Integer,DtoTimeSheetDay>();

    //当前行的加班时间数据,由表格TableOvertime维护
    List<Double> standarHoursOnWeek = null;
    List<DtoAttLeave> leaveHoursOnWeek = null;
    List<DtoAttOvertime> overtimeHoursOnWeek = null;
    DtoPreference dtoPreference = null;
    Boolean tsEditable = null;
    

    private int ColumnCount = 10;

    public Class getColumnClass(int column) {
        if(column < BEGIN_HOUR_COLUMN_INDEX) {
            return String.class;
        } else {
            return Double.class;
        }
    }

    public String getColumnName(int column) {
        return "" + column;
    }

    public int getColumnCount() {
        return ColumnCount;
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void setValueAt(Object value, int row, int col) {
    }

    public int getRowCount() {
        return ALL_ROW_CCOUNT;
    }

    public Object getValueAt(int row, int col) {

         if (row == ACTUAL_HOUR_ROW_INDEX) {
             if( col == HEAD_COLUMN_INDEX ){
                 return MessageUtil.getMessage("rsid.timesheet.actualHours");
             }else {
                 return getActualHours(col);
             }
        } else if (row == LEAVE_HOUR_ROW_INDEX) {
            if( col == HEAD_COLUMN_INDEX ){
                return MessageUtil.getMessage("rsid.timesheet.leaveHours");
            } else {
                return getLeaveHour(col);
            }
        } else if (row == OVERTIME_HOUR_ROW_INDEX) {
            if( col == HEAD_COLUMN_INDEX ){
                return MessageUtil.getMessage("rsid.timesheet.overtimeHours");
            } else {
                return getOvertimeHour(col);
            }
        } else if (row == STANDAR_HOUR_ROW_INDEX) {
            if( col == HEAD_COLUMN_INDEX ){
                return MessageUtil.getMessage("rsid.timesheet.standardHours");
            } else {
                return getStandarHour(col);
            }
        } else if (row == VARIANT_ROW_INDEX) {
            if( col == HEAD_COLUMN_INDEX ){
                return MessageUtil.getMessage("rsid.common.variant");
            } else {
                return getVariant(col);
            }
        }

        return null;
    }
    
    private DtoTimeSheetDay getActualHours(int col) {
    	return hoursOnWeek.get(col);
    }

    private DtoTimeSheetDay getVariant(int col) {
    	DtoTimeSheetDay dtoV = new DtoTimeSheetDay();
        DtoTimeSheetDay dtoDay = getActualHours(col);
        Double wkHour = new Double(0);
        Double otHour = new Double(0);
        Double standarHour = (Double) getStandarHour(col);
        if(dtoDay != null && dtoDay.getWorkHours() != null) {
            wkHour = dtoDay.getWorkHours();
        }
        if(dtoDay != null && dtoDay.getOvertimeHours() != null) {
            otHour = dtoDay.getOvertimeHours();
        }

        if(standarHour == null) {
            standarHour = new Double(0);
        }
        if(this.dtoPreference == null || this.tsEditable == null) {
        	dtoV.setWorkHours(wkHour);
        	return dtoV;
        }
        Double d;
        Double otSum = 0D;
        Double lvSum = 0D;
        Double otVar = 0D;
//        if(this.lotEffective || this.tsEditable) { 
//        	d = (wkHour - getOvertimeHour(col).getActualHours() + getLeaveHour(col).getActualHours()) - standarHour;
//        } else {
//        	d = (wkHour - getOvertimeHour(col).getActualHours()) - standarHour;
//        }
//        
//        if(!this.lotEffective) {
//        	
//        }
        if(DtoPreference.OVERTIME_EFFECTIVE_REFER.equals(dtoPreference.getOvertimeEffective())) {
        	otSum = getOvertimeHour(col).getActualHours();
        	otVar = otHour - otSum;
        } else {
        	otSum = otHour;
        }
        if(DtoPreference.LEAVE_EFFECTIVE_REFER.equals(dtoPreference.getLeaveEffective())
        		|| (DtoPreference.LEAVE_EFFECTIVE_GENERATE.equals(dtoPreference.getLeaveEffective())
        				&& this.tsEditable)) {
        	lvSum = getLeaveHour(col).getActualHours();
        }
        d = (wkHour - otSum + lvSum) - standarHour;
        
        BigDecimal b = new BigDecimal(d);
        BigDecimal bOt = new BigDecimal(otVar);
        dtoV.setWorkHours(b.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue());
        dtoV.setOvertimeHours(bOt.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue());
        return dtoV;
    }

    private Double getStandarHour(int columnIndex) {
        int index = columnIndex - BEGIN_HOUR_COLUMN_INDEX;
        if(standarHoursOnWeek == null || standarHoursOnWeek.size() <= index) {
            return new Double(0);
        }
        return standarHoursOnWeek.get(index);
    }
    
    private DtoAttLeave getLeaveHour(int columnIndex) {
        int index = columnIndex - BEGIN_HOUR_COLUMN_INDEX;
        if(leaveHoursOnWeek == null || leaveHoursOnWeek.size() <= index) {
            return null;
        }
        return leaveHoursOnWeek.get(index);
    }
    
    private DtoAttOvertime getOvertimeHour(int columnIndex) {
        int index = columnIndex - BEGIN_HOUR_COLUMN_INDEX;
        if(overtimeHoursOnWeek == null || overtimeHoursOnWeek.size() <= index) {
            return null;
        }
        return overtimeHoursOnWeek.get(index);
    }

    public void standarHoursChanged(List dataList) {
    	if(dataList == null || dataList.size() < 5) {
    		this.standarHoursOnWeek = null;
    		this.leaveHoursOnWeek = null;
    		this.overtimeHoursOnWeek = null;
    		this.dtoPreference = null;
    		this.tsEditable = null;
    		return;
    	}
    	this.standarHoursOnWeek = (List) dataList.get(0);
		this.leaveHoursOnWeek = (List) dataList.get(1);
		this.overtimeHoursOnWeek = (List) dataList.get(2);
		this.dtoPreference = (DtoPreference) dataList.get(3);
		this.tsEditable = (Boolean) dataList.get(4);
    }

    public void workHourChanged(int columnIndex, DtoTimeSheetDay sumHour) {
        hoursOnWeek.put(columnIndex - (COLUMN_OFFSET - BEGIN_HOUR_COLUMN_INDEX),
                        sumHour);
        this.fireTableDataChanged();
    }

    public void columnNumChanged(int num) {
        ColumnCount = num - COLUMN_OFFSET;
        this.fireTableStructureChanged();
        fireRenderNeedReset();
    }

    public void addResetRenderListener(ResetRenderListener l) {
        resetRenderListeners.add(l);
    }

    private void fireRenderNeedReset() {
        Iterator<ResetRenderListener> iter = resetRenderListeners.iterator();
        while(iter.hasNext()) {
            iter.next().resetRender(null);
        }
    }

    /**
     * 触发工时单提交
     * @return boolean 是否允许提交
     */
    public boolean submitTimeSheet() {
        for(int c = BEGIN_HOUR_COLUMN_INDEX; c < this.getColumnCount(); c ++) {
        	DtoTimeSheetDay dayV = getVariant(c);
            if(!dayV.getWorkHours().equals(0D) || !dayV.getOvertimeHours().equals(0D)) {
                comMSG.dispErrorDialog("error.client.VMTableSum.VariantExist");
                return false;
            }
        }
        return true;
    }
}
