package client.essp.tc.pmmanage.onWeek;

import client.essp.tc.common.TableListener;
import java.awt.Dimension;

public class VwTcListTempOnWeekByDm extends VwTcListTempOnWeek {
    public VwTcListTempOnWeekByDm() {
        super(new VwTcListOnWeekByDm(), new VwTcListSumOnWeekByDm());
    }

    protected void addUICEvent() {
        final TableTcOnWeekByDm tableTcOnWeekByDm = (TableTcOnWeekByDm) vwTcListOnWeek.getTable();
        final TableTcSumOnWeekByDm tableTcSumOnWeekByDm = (TableTcSumOnWeekByDm) vwTcListSumOnWeek.getTable();

        final VMTableTcOnWeekByDm vmTableTcOnWeekByDm = (VMTableTcOnWeekByDm) vwTcListOnWeek.getTable().getModel();
        final VMTableTcSumOnWeekByDm vmTableTcSumOnWeekByDm = (VMTableTcSumOnWeekByDm) vwTcListSumOnWeek.table.getModel();

        //�趨���ݹ���
        vmTableTcSumOnWeekByDm.setTotalOnWeek(vmTableTcOnWeekByDm.getTotalOnWeek());

        //�趨������ʾ������������п��й���
        tableTcOnWeekByDm.setTableSum(tableTcSumOnWeekByDm);

        vmTableTcOnWeekByDm.addTableListeners(new TableListener() {
            public void processTableChanged(String eventType) {
                if (TableListener.EVENT_ROW_COUNT_CHANGED.equals(eventType)) {

                    //����������仯ʱ ������panel�ĸ߶�
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

                    //ˢ��ͳ����
                    vmTableTcSumOnWeekByDm.fireTableRowsUpdated(0, 0);
                }
            }
        });
    }

}
