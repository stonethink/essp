package client.essp.pms.activity.wp;

import client.framework.model.VMTable2;
import c2s.essp.pwm.wp.DtoPwWp;
import java.util.Date;
import client.framework.model.VMColumnConfig;
import java.util.Calendar;
import c2s.essp.common.calendar.WrokCalendarFactory;
import client.framework.view.common.comMSG;
import c2s.essp.common.calendar.WorkCalendar;
import java.math.BigDecimal;
import c2s.dto.IDto;
import c2s.essp.pms.wbs.DtoWbsActivity;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author xlp
 * @version 1.0
 */
public class VMWpListModel extends VMTable2 {

    DtoWbsActivity dtoActivity = null;
    private boolean locked;

    public VMWpListModel(Object[][] configs) {
        super(configs);
    }

    //set belong activity, to check plan period.
    public void setDtoActivity(DtoWbsActivity dtoActivity) {
        this.dtoActivity = dtoActivity;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(dtoActivity != null && dtoActivity.isReadonly()) {
            return false;
        } else {
            return super.isCellEditable(rowIndex, columnIndex);
        }
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (locked == true) {
            return;
        }
        //get active column.
        VMColumnConfig columnConfig = (VMColumnConfig)this.getColumnConfigs().
                                      get(columnIndex);
        String dateName = columnConfig.getDataName();
        //get logic info.
        DtoPwWp dataBean = (DtoPwWp)this.getRow(rowIndex);
        Date pStart = dataBean.getWpPlanStart();
        Date pFinish = dataBean.getWpPlanFihish();

        if ("wpPlanStart".equals(dateName) || "wpPlanFihish".equals(dateName)) {
            if (aValue == null) {
                locked = true;
                comMSG.dispErrorDialog(dateName + " is invalid!");
                locked = false;
                return;
            }
            if ("wpPlanStart".equals(dateName)) {
                pStart = (Date) aValue;
            } else {
                pFinish = (Date) aValue;
            }
            if (checkInActivityPeriod(pStart, pFinish) == false) {
                locked = true;
                comMSG.dispMessageDialog(
                    "Work package's period is not in activity's period!");
                locked = false;
                super.setValueAt(aValue, rowIndex, columnIndex);
            } else if (pStart.compareTo(pFinish) > 0) { //stop abnormal plan start or finish date.
                String startBig = "Line[" + (rowIndex + 1) +
                                  "]:Planned start date shouldn't be bigger than finish!";
                String finishSmall = "Line[" + (rowIndex + 1) +
                                     "]:Planned finish date shouldn't be smaller than start!";
                locked = true;
                comMSG.dispErrorDialog("wpPlanStart".equals(dateName) ?
                                       startBig : finishSmall);
                locked = false;
            } else {
                //comput plan work hours.
                WorkCalendar wc = WrokCalendarFactory.clientCreate();
                Calendar cStart = Calendar.getInstance();
                Calendar cFinish = Calendar.getInstance();
                cStart.setTime(pStart);
                cFinish.setTime(pFinish);
                BigDecimal workHours = new BigDecimal(wc.getWorkHours(cStart,
                    cFinish));
                dataBean.setWpReqWkhr(workHours);
//                dataBean.setWpPlanWkhr(workHours);
                //commit
                super.setValueAt(aValue, rowIndex, columnIndex);
                //fire work hours process.
                this.fireTableDataChanged();
                dataBean.setOp(IDto.OP_MODIFY);
            }
        } else {
            //other columns, do nothing.
            super.setValueAt(aValue, rowIndex, columnIndex);
        }
    }

    /**
     * 判断输入日期是否在Activity区间内
     * @param dataBean DtoPwWp
     * @return boolean
     */
    private boolean checkInActivityPeriod(Date wpStart, Date wpFinish) {
        //no activity, no check.
        if (dtoActivity == null) {
            return true;
        }
        Date actStart = dtoActivity.getPlannedStart();
        Date actFinish = dtoActivity.getPlannedFinish();
        //no date, no check.
        if ((wpStart == null) || (wpFinish == null) || (actStart == null) ||
            (actFinish == null)) {
            return true;
        }
        return wpStart.compareTo(actStart) >= 0 &&
            wpFinish.compareTo(actFinish) <= 0;
    }
}
