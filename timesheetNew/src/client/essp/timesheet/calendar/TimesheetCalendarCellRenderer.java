package client.essp.timesheet.calendar;

import java.awt.*;
import java.util.Calendar;

import javax.swing.*;

import c2s.dto.*;
import c2s.essp.timesheet.preference.DtoPreference;
import client.essp.common.calendar.*;
import client.net.ConnectFactory;
import client.net.NetConnector;


public class TimesheetCalendarCellRenderer extends CalendarCellRenderer {
	private Color holidayForeground = Color.black;
    private Color holidayBackground = Color.lightGray;
    private Color todayForeground   = Color.black;
    private Color todayBackground   = Color.yellow;
    private Color monthEndForeground = Color.black;
    private Color monthEndBackground = Color.red;
    private Color monthEndTwoForeground = Color.black;
    private Color monthEndTwoBackground = Color.pink;
    private int monthEnd = 0;
    private int monthEndTwo = 0;
    private final static String actionId_Load = "FTSLoadPreference";
    public TimesheetCalendarCellRenderer() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 判断日历表某个单元格是不是今天
     * @param table
     * @param row
     * @param column
     * @return
     */
    private static boolean isToday(JTable table,
                                   int    row,
                                   int    column) {
        if (!(table.getModel() instanceof EditableCalendarTableModel)) {
            return false;
        }

        EditableCalendarTableModel tblMode = (EditableCalendarTableModel) table
                                             .getModel();

        return tblMode.isToday(row, column);
    }

    private boolean isMonthEnd(JTable table,
                                   int    row,
                                   int    column){
        if (!(table.getModel() instanceof EditableCalendarTableModel)) {
           return false;
        }
        EditableCalendarTableModel tblMode = (EditableCalendarTableModel) table
                                             .getModel();
        int celldate = tblMode.getCellDate(row,column);
        if(celldate == monthEnd){
            return true;
        }
        if(monthEnd == 0){
        	Calendar caCell = tblMode.getCellCalendar(row,column);
        	if(caCell != null) {
        		int day = caCell.get(Calendar.DAY_OF_MONTH);
            	int maxDay = caCell.getActualMaximum(Calendar.DAY_OF_MONTH);
            	if(day == maxDay) {
            		return true;
            	}
        	}
        }
        return false;
    }
    private boolean isMonthEndTwo(JTable table, int row, int column) {
		if (!(table.getModel() instanceof EditableCalendarTableModel)) {
			return false;
		}
		EditableCalendarTableModel tblMode = (EditableCalendarTableModel) table
				.getModel();
		int celldate = tblMode.getCellDate(row, column);
		if (celldate == monthEndTwo) {
			return true;
		}
		if(monthEndTwo == 0){
        	Calendar caCell = tblMode.getCellCalendar(row,column);
        	if(caCell != null) {
        		int day = caCell.get(Calendar.DAY_OF_MONTH);
            	int maxDay = caCell.getActualMaximum(Calendar.DAY_OF_MONTH);
            	if(day == maxDay) {
            		return true;
            	}
        	}
        }
		return false;
	}

    /**
	 * 描绘器接口需的主要方法
	 * 
	 * @param table
	 *            表格引用
	 * @param value
	 *            单元内容
	 * @param isSelected
	 *            是否选中
	 * @param hasFocus
	 *            是否有焦点
	 * @param row
	 *            行号
	 * @param column
	 *            列号
	 * @return 描绘组件
	 */
    public Component getTableCellRendererComponent(JTable  table,
                                                   Object  value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int     row,
                                                   int     column) {
    	
        CellValue cellValue = (CellValue) value;

        Font font = new Font("Arial",2,9);
        if (cellValue.isCurrentmonth()) {
            if (isSelected) {
                super.setForeground(table.getSelectionForeground());
                super.setBackground(table.getSelectionBackground());
            } else if (isToday(table, row, column)) {
                this.setForeground(todayForeground);
                this.setBackground(todayBackground);
            } else if (isMonthEnd(table, row, column)) {
                this.setForeground(monthEndForeground);
                this.setBackground(monthEndBackground);
            } else if (isMonthEndTwo(table, row, column)) {
                this.setForeground(monthEndTwoForeground);
                this.setBackground(monthEndTwoBackground);
            } else if (cellValue.isWorkday()) {
                super.setForeground(table.getForeground());
                super.setBackground(table.getBackground());
            } else {
                this.setForeground(holidayForeground);
                this.setBackground(holidayBackground);
            }
            setFont(table.getFont());
        }else{
            if (isSelected) {
                super.setForeground(table.getSelectionForeground());
                super.setBackground(table.getSelectionBackground());
            } else if (cellValue.isWorkday()) {
                super.setForeground(table.getForeground());
                super.setBackground(table.getBackground());
            } else {
                this.setForeground(holidayForeground);
                this.setBackground(holidayBackground);
            }
            setFont(font);
        }
        setHorizontalAlignment(SwingConstants.CENTER);

        if (hasFocus) {
            setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));

            if (table.isCellEditable(row, column)) {
                super.setForeground(UIManager.getColor("Table.focusCellForeground"));
                super.setBackground(UIManager.getColor("Table.focusCellBackground"));
            }
        } else {
            setBorder(noFocusBorder);
        }

        setValue(cellValue.getDayValue());

        return this;
    }

    private void jbInit() throws Exception {
    	InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(actionId_Load);
        inputInfo.setInputObj(DtoPreference.DTO_SITE, DtoPreference.DTO_SITE_GLOBAL);
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()) {
        	DtoPreference dtoPreference = (DtoPreference) returnInfo
        	                                     .getReturnObj(DtoPreference.DTO);
        	monthEnd = dtoPreference.getMonthStartDay().intValue() - 1;
        	monthEndTwo = dtoPreference.getMonthStartDayTwo().intValue() - 1;
        }
    }
    /**
     * 访问服务端
     */
    protected ReturnInfo accessData(InputInfo inputInfo) {
        TransactionData transData = new TransactionData();
        transData.setInputInfo(inputInfo);

        NetConnector connector = ConnectFactory.createConnector();
        connector.accessData(transData);

        ReturnInfo returnInfo = transData.getReturnInfo();

        return returnInfo;
    }

}
