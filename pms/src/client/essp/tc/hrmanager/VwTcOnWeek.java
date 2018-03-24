package client.essp.tc.hrmanager;

import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByHr;
import client.essp.tc.common.TableListener;
import client.essp.tc.weeklyreport.VMTableWeeklyReportOnWeekByHr;
import client.essp.tc.weeklyreport.VwWeeklyReportListOnWeekByHr;

public class VwTcOnWeek extends VwTcBase{
    public VwTcOnWeek() {
        super( new VwTcListOnWeek(),
               new VwWeeklyReportListOnWeekByHr() );
    }

    /**
     * 定义界面：定义界面事件
     */
    protected void addUICEvent() {
        super.addUICEvent();

        final VMTableWeeklyReportOnWeekByHr vmTableWeeklyReport = (VMTableWeeklyReportOnWeekByHr)vwWeeklyReportListByHr.getModel();
        final VMTableTc vmTableTc = (VMTableTc)vwTcList.getModel();

        //建立数据关联
        vmTableTc.setAllocateHourOnWeek(vmTableWeeklyReport.getAllcoateHourOnWeek());

        vmTableWeeklyReport.addTableListeners(new TableListener(){
            public void processTableChanged(String eventType){
                if(eventType.equals(TableListener.EVENT_LOCK_OFF)){
                    DtoWeeklyReportSumByHr dto = (DtoWeeklyReportSumByHr)vwTcList.getSelectedData();
                    if( dto != null ){
                        dto.setLocked(Boolean.toString(false));
                        vwTcList.getTable().refreshTable();
                    }
                }else if(eventType.equals(TableListener.EVENT_LOCK_On)){
                    DtoWeeklyReportSumByHr dto = (DtoWeeklyReportSumByHr)vwTcList.getSelectedData();
                    if( dto != null ){
                        dto.setLocked(Boolean.toString(true));
                        vwTcList.getTable().refreshTable();
                    }
                }
            }
        });

        vmTableWeeklyReport.addTableListeners(new TableListener() {
            public void processTableChanged(String eventType) {
                if (eventType.equals(TableListener.EVENT_SUM_DATA_CHANGED) == true) {
                    //下面表格的时间栏位影响上面表格的时间栏位
                    vmTableTc.refreshHoursOnWeek( vwTcList.getTable().getSelectedRow() );
                }
            }
        });
    }

}
