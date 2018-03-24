package client.essp.tc.pmmanage.onWeek;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Date;

import c2s.essp.tc.weeklyreport.DtoTcKey;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.tc.common.TableListener;
import client.essp.tc.common.TcLayout;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import client.framework.common.Global;

public class VwTcListTempOnWeek extends VWGeneralWorkArea {
    public final static String actionIdInit = "FTCPmManageInitAction";

    //这个panel的高度会随它里面的表格的行数变化，它里面有两个表格
    VWWorkArea incrementPanel;

    public VwTcListOnWeekBase vwTcListOnWeek = null;
    public VwTcListSumOnWeek vwTcListSumOnWeek = null;

    public VwTcListTempOnWeek() {
        this(new VwTcListOnWeek(), new VwTcListSumOnWeek());
    }

    public VwTcListTempOnWeek(VwTcListOnWeekBase vwTcListOnWeek, VwTcListSumOnWeek vwTcSumOnWeek) {
        try {
            this.vwTcListOnWeek = vwTcListOnWeek;
            this.vwTcListSumOnWeek = vwTcSumOnWeek;

            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        addUICEvent();
    }

    private void jbInit() throws Exception {
        incrementPanel = new VWWorkArea();
        TcLayout layout = new TcLayout();
        incrementPanel.setLayout(layout);
        incrementPanel.add(vwTcListOnWeek);
        incrementPanel.add(vwTcListSumOnWeek);

        this.add(incrementPanel, BorderLayout.CENTER);
    }

    protected void addUICEvent() {
        final TableTcOnWeek tableTcOnWeek = (TableTcOnWeek) vwTcListOnWeek.getTable();
        final TableTcSumOnWeek tableTcSumOnWeek = (TableTcSumOnWeek) vwTcListSumOnWeek.getTable();

        final VMTableTcOnWeek vmTableTcOnWeek = (VMTableTcOnWeek) vwTcListOnWeek.getTable().getModel();
        final VMTableTcSumOnWeek vmTableTcSumOnWeek = (VMTableTcSumOnWeek) vwTcListSumOnWeek.table.getModel();

        //设定数据关联
        vmTableTcSumOnWeek.setTotalOnWeek(vmTableTcOnWeek.getTotalOnWeek());

        //设定表格的显示关联，两表的列宽有关联
        tableTcOnWeek.setTableSum(tableTcSumOnWeek);

        vmTableTcOnWeek.addTableListeners(new TableListener() {
            public void processTableChanged(String eventType) {
                if (TableListener.EVENT_ROW_COUNT_CHANGED.equals(eventType)) {

                    //当表的行数变化时 ，调整panel的高度
                    vwTcListOnWeek.setPreferredSize(new Dimension(
                            -1, vwTcListOnWeek.getPreferredTableHeight())
                            );

                    if (vwTcListOnWeek.getTable().getRowCount() == 0) {
                        vwTcListSumOnWeek.setVisible(false);
                    } else {
                        vwTcListSumOnWeek.setVisible(true);
                    }

                    incrementPanel.updateUI();
                } else if (TableListener.EVENT_SUM_DATA_CHANGED.equals(eventType)) {

                    //刷新统计行
                    vmTableTcSumOnWeek.fireTableRowsUpdated(0, 0);
                } else if (TableListener.EVENT_DETAIL_CONFIRM_DATA_CHANGED.equals(eventType)) {

                    //影响汇总行的confirm栏位
                    String confirmStatus = vmTableTcOnWeek.getAboutConfirmStatus();
                    vmTableTcSumOnWeek.setConfirmStatus(confirmStatus);
                    vmTableTcSumOnWeek.fireTableRowsUpdated(0, VMTableTcSumOnWeek.CONFIRM_COLUMN_INDEX);
                }
            }
        });
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.vwTcListOnWeek.setParameter(param);
    }

    protected void resetUI() {
        vwTcListOnWeek.refreshWorkArea();
    }

    public void setBottomSpace(int spaceHeight) {
        TcLayout layout = (TcLayout) incrementPanel.getLayout();
        layout.setBottomSpace(spaceHeight);
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwTcListTempOnWeek();

        w1.addTab("Timecard", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        param.put(DtoTcKey.BEGIN_PERIOD, Global.todayDate);
        param.put(DtoTcKey.END_PERIOD, Global.todayDate);
        param.put(DtoTcKey.ACNT_RID, new Long(1));
        workArea.setParameter(param);

        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }
}
