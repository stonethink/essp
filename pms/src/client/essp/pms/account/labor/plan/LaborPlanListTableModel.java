package client.essp.pms.account.labor.plan;

import client.framework.model.VMTable2;
import org.apache.log4j.Category;
import client.framework.model.VMColumnConfig;
import c2s.essp.pms.account.DtoAcntLaborResDetail;
import java.util.Date;
import client.framework.view.common.comMSG;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.calendar.WrokCalendarFactory;
import java.util.Calendar;
import c2s.dto.IDto;
import java.util.List;

public class LaborPlanListTableModel extends VMTable2 {
    static Category log = Category.getInstance("client");
    private boolean locked;

    public LaborPlanListTableModel(Object[][] configs){
        super(configs);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return super.getValueAt(rowIndex,columnIndex);
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if( locked == false ){
            VMColumnConfig columnConfig = (VMColumnConfig)this.getColumnConfigs().
                                          get(columnIndex);
            String dateName = columnConfig.getDataName();
            if ("hour".equals(dateName)) {
                super.setValueAt(aValue, rowIndex, columnIndex);
            } else {
                DtoAcntLaborResDetail dataBean = (DtoAcntLaborResDetail) getRow(
                    rowIndex);
                Date begin = dataBean.getBeginDate();
                Date end = dataBean.getEndDate();
                Long percent = dataBean.getPercent();
                if ("beginDate".equals(dateName) || "endDate".equals(dateName)) {
                    Date inputDate = (Date) aValue;
                    if (isInPlannedPeriod(rowIndex, dateName, inputDate,
                                          dataBean)) {
                        locked = true;
                        comMSG.dispErrorDialog("Line[" + rowIndex +
                            "]:Input Date is in a planned date period!");
                        locked = false;
                        return;
                    }
                    if ("beginDate".equals(dateName))
                        begin = inputDate;
                    if ("endDate".equals(dateName))
                        end = inputDate;
                    if (begin != null && end != null &&
                        begin.getTime() > end.getTime()) {
                        locked = true;
                        comMSG.dispErrorDialog("Line[" + rowIndex +
                                               "]:End Date must be larger than Begin Date!");
                        locked = false;
                        return;

                    }
                }
                if ("percent".equals(dateName)) {
                    percent = (Long) aValue;
                    if (percent == null || percent.longValue() > 100 ||
                        percent.longValue() < 0) {
                        locked = true;
                        comMSG.dispErrorDialog("Line[" + rowIndex +
                                               "]:Usage percent be between 0 and 100!");
                        locked = false;
                        return;
                    }
                }
                super.setValueAt(aValue, rowIndex, columnIndex);
                if (begin != null && end != null) {
                    WorkCalendar wc = WrokCalendarFactory.clientCreate();
                    Calendar start = Calendar.getInstance();
                    Calendar finish = Calendar.getInstance();
                    start.setTime(begin);
                    finish.setTime(end);
                    double workHour = wc.getWorkHours(start, finish) *
                                    percent.longValue() / 100;
                    dataBean.setHour(new Double(workHour));
                }
                dataBean.setOp(IDto.OP_MODIFY);
            }
            this.fireTableDataChanged();
        }
    }

    /**
     * 判断输入日期是否在已存在的区间内
     * @param rowIndex int
     * @param dateName String
     * @param inputDate Date
     * @param dataBean DtoAcntLaborResDetail
     * @return boolean
     */
    private boolean isInPlannedPeriod(int rowIndex,String dateName,Date inputDate,DtoAcntLaborResDetail dataBean) {
        if(inputDate == null)
            return false;
        Date begin = dataBean.getBeginDate();
        Date end = dataBean.getEndDate();
        List rows = getRows();
        if(rows != null && rows.size() > 0){
            for(int i = 0;i < rows.size() ;i ++){
                if(rowIndex == i)
                    continue;
                DtoAcntLaborResDetail row = (DtoAcntLaborResDetail) getRow(i);
                Date rowBegin = row.getBeginDate();
                Date rowEnd = row.getEndDate();
                if(rowBegin.getTime() <= inputDate.getTime() &&
                   rowEnd.getTime() >= inputDate.getTime()){
                    return true;
                }
                if("beginDate".equals(dateName)){
                    if(end != null && end.getTime() >= rowEnd.getTime()
                       && inputDate.getTime() <= rowBegin.getTime())
                    return true;
                }
                if("endDate".equals(dateName)){
                    if(begin != null && inputDate.getTime() >= rowEnd.getTime()
                       && begin.getTime() <= rowBegin.getTime())
                    return true;
                }
            }
        }
        return false;
    }
}
