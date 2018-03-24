package client.essp.tc.pmmanage.onMonth;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.math.BigDecimal;
import java.util.Date;

import c2s.essp.tc.weeklyreport.DtoTcKey;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.tc.common.TableListener;
import client.essp.tc.common.TcLayout;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;

public class VwTcListTempOnMonth extends VWGeneralWorkArea {
    public final static String actionIdInit = "FTCPmManageInitAction";

    //���panel�ĸ߶Ȼ���������ı��������仯�����������������
    VWWorkArea incrementPanel;

    public VwTcListOnMonth vwTcListOnMonth = null;
    VwTcListSumOnMonth vwTcListSumOnMonth = null;

    public VwTcListTempOnMonth() {
        this(new VwTcListOnMonth());
    }

    public VwTcListTempOnMonth(VwTcListOnMonth vwTcListOnMonth) {
        try {
            this.vwTcListOnMonth = vwTcListOnMonth;
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        addUICEvent();
    }

    private void jbInit() throws Exception {

        vwTcListSumOnMonth = new VwTcListSumOnMonth();

        incrementPanel = new VWWorkArea();
        TcLayout layout = new TcLayout();
        incrementPanel.setLayout(layout);
        incrementPanel.add(vwTcListOnMonth);
        incrementPanel.add(vwTcListSumOnMonth);

        this.add(incrementPanel, BorderLayout.CENTER);
    }

    private void addUICEvent() {
        final TableTcOnMonth tableTcOnMonth = (TableTcOnMonth) vwTcListOnMonth.getTable();
        final TableTcSumOnMonth tableTcSumOnMonth = (TableTcSumOnMonth) vwTcListSumOnMonth.table;

        final VMTableTcOnMonth vmTableTcOnMonth = (VMTableTcOnMonth) vwTcListOnMonth.getTable().getModel();
        final VMTableTcSumOnMonth vmTableTcSumOnMonth = (VMTableTcSumOnMonth) vwTcListSumOnMonth.table.getModel();

        //�趨������ʾ������������п��й���
        tableTcOnMonth.setTableSum(tableTcSumOnMonth);

        //���ݹ���
        vmTableTcSumOnMonth.setTotalOnMonth( vmTableTcOnMonth.getTotalOnMonth() );

        vmTableTcOnMonth.addTableListeners(new TableListener() {
            public void processTableChanged(String eventType) {
                if (TableListener.EVENT_ROW_COUNT_CHANGED.equals(eventType)) {

                    //����������仯ʱ ������panel�ĸ߶�
                    vwTcListOnMonth.setPreferredSize(new Dimension(
                            -1, vwTcListOnMonth.getPreferredTableHeight())
                            );

                    if (vwTcListOnMonth.getTable().getRowCount() == 0) {
                        vwTcListSumOnMonth.setVisible(false);
                    } else {
                        vwTcListSumOnMonth.setVisible(true);
                    }

                    incrementPanel.updateUI();
                } else if (TableListener.EVENT_SUM_DATA_CHANGED.equals(eventType)) {

                    //ˢ��ͳ����
                    vwTcListSumOnMonth.refreshTable();
                }
//                else if (TableListener.EVENT_DETAIL_CONFIRM_DATA_CHANGED.equals(eventType)) {
//
//                    //Ӱ������е�confirm��λ
//                    String confirmStatus = vmTableTcOnMonth.getAbountConfirmStatus();
//                    vwTcListSumOnMonth.refreshConfirmStatus(confirmStatus);
//                }
            }
        });

    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.vwTcListOnMonth.setParameter(param);
    }

    protected void resetUI() {
        vwTcListOnMonth.refreshWorkArea();
    }

    public void setBottomSpace(int spaceHeight) {
        TcLayout layout = (TcLayout) incrementPanel.getLayout();
        layout.setBottomSpace(spaceHeight);
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwTcListTempOnMonth();

        w1.addTab("Timecard", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        param.put(DtoTcKey.BEGIN_PERIOD, new Date(105, 1, 1));
        param.put(DtoTcKey.END_PERIOD, new Date(105, 12, 30));
        param.put(DtoTcKey.ACNT_RID, new Long(1));
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }
}
