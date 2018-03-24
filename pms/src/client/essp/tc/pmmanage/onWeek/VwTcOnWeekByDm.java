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
     * ������棺��������¼�
     */
    protected void addUICEvent() {
        //����ı��
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
                    //ˢ��������ʱ,Ҫˢ�µ�ǰѡ�е���
                    vwTcListTempOnWeek.vwTcListOnWeek.refreshRow();
                }
            }
        });
    }

}
