package client.essp.tc.pmmanage.onWeek;

import client.essp.tc.common.TableListener;
import client.essp.tc.weeklyreport.VMTableWeeklyReport;
import client.essp.tc.weeklyreport.VwWeeklyReportOnWeekByDm;
import client.framework.view.event.RowSelectedListener;

public class VwTcOnWeekByDm extends VwTcOnWeek {
    public VwTcOnWeekByDm() {
        super(new VwTcListTempOnWeekByDm(),
              new VwWeeklyReportOnWeekByDm());
    }

    /**
     * 定义界面：定义界面事件
     */
    protected void addUICEvent() {
        //下面的表格
        final VMTableWeeklyReport vmTableWeeklyReportOnWeek = (VMTableWeeklyReport)this.vwWeeklyReport.vwWeeklyReportList.getModel();

        //RowSelected
        this.vwTcListTempOnWeek.vwTcListOnWeek
                .getTable().addRowSelectedListener(new
                                                   RowSelectedListener() {
            public void processRowSelected() {
                processRowSelectedList();
            }
        });

        vmTableWeeklyReportOnWeek.addTableListeners(new TableListener() {
            public void processTableChanged(String eventType) {
                if(eventType.equals(TableListener.EVENT_REFRESH_ACTION) == true) {
                    //刷新下面表格时,要刷新当前选中的行
                    vwTcListTempOnWeek.vwTcListOnWeek.refreshRow();
                }
            }
        });
    }

}
