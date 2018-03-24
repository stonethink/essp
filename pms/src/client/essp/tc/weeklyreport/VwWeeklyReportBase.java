package client.essp.tc.weeklyreport;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Date;

import c2s.essp.tc.weeklyreport.DtoTcKey;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.tc.common.TableListener;
import client.essp.tc.common.TcLayout;
import com.wits.util.Parameter;
import com.wits.util.comDate;

public class VwWeeklyReportBase extends VWGeneralWorkArea {
    public final static String actionIdInit = "FTCWeeklyReportInitAction";

    //���panel�ĸ߶Ȼ���������ı��������仯
    VWWorkArea incrementPanel;

    public VwWeeklyReportListBase vwWeeklyReportList = null;
    public VwOvertimeList vwOvertimeList = null;
    public VwSumList vwSumList = null;

    //parameter
    private Date beginPeriod = null;
    private Date endPeriod = null;

    public VwWeeklyReportBase(){
        incrementPanel = new VWWorkArea();
        vwSumList = new VwSumList();
        vwOvertimeList = new VwOvertimeList();
    }

    //Component initialization
    protected void jbInit() throws Exception {
        this.setLayout(new BorderLayout());

        TcLayout layout = new TcLayout();
        layout.setBottomSpace(20);
        incrementPanel.setLayout(layout);
        incrementPanel.add(vwWeeklyReportList);
        incrementPanel.add(vwSumList);
        incrementPanel.add(vwOvertimeList);

        this.add(incrementPanel, BorderLayout.CENTER);

        this.getButtonPanel().add(vwWeeklyReportList.getButtonPanel());
    }

    protected void initUI() {

        TableWeeklyReport weeklyReportTable = (TableWeeklyReport) vwWeeklyReportList.getTable();
        TableSum sumTable = (TableSum) vwSumList.getTable();
        TableOvertime overtimeTable = (TableOvertime) vwOvertimeList.getTable();

        final VMTableWeeklyReport vmTableWeeklyReport = (VMTableWeeklyReport) vwWeeklyReportList.getTable().getModel();
        final VMTableSum vmTableSum = (VMTableSum) vwSumList.getTable().getModel();

        //���ñ���Ĺ���
        weeklyReportTable.setTableSum(sumTable);
        weeklyReportTable.setTableOvertime(overtimeTable);

        //���ñ�������ݹ���
        vmTableSum.setHoursOnWeek(vmTableWeeklyReport.getHoursOnWeek());
        vmTableSum.setLeaveOnWeek(vwOvertimeList.getLeaveOnWeek());
        vmTableSum.setLeaveOnWeekConfirmed(vwOvertimeList.getLeaveOnWeekConfirmed());
        vmTableSum.setOvertimeOnWeek(vwOvertimeList.getOvertimeOnWeek());
        vmTableSum.setOvertimeOnWeekConfirmed(vwOvertimeList.getOvertimeOnWeekConfirmed());

        vmTableWeeklyReport.addTableListeners(new TableListener() {
            public void processTableChanged(String eventType) {
                if (TableListener.EVENT_ROW_COUNT_CHANGED.equals(eventType)) {

                    //��weeklyreport��������仯ʱ ������panel -- vwWeeklyReportList�ĸ߶�
                    vwWeeklyReportList.setPreferredSize(new Dimension(
                            -1, vwWeeklyReportList.getPreferredTableHeight())
                            );

                    incrementPanel.updateUI();
                } else if (TableListener.EVENT_SUM_DATA_CHANGED.equals(eventType)) {
                    //ˢ��ͳ����
                    vmTableSum.fireTableRowsUpdated(0, 1);
                }
            }
        });
        vwOvertimeList.addTableListeners(new TableListener() {
            public void processTableChanged(String eventType) {
                if (TableListener.EVENT_ROW_COUNT_CHANGED.equals(eventType)) {

                    //��weeklyreport��������仯ʱ ������panel -- vwWeeklyReportList�ĸ߶�
                    vwOvertimeList.setPreferredSize(new Dimension(
                            -1, vwOvertimeList.getPreferredTableHeight())
                            );

                    incrementPanel.updateUI();
                }
            }
        });
    }

    public void setParameter(Parameter param) {
        //judge whether the parameter is changed
        boolean parameterChanged = false;
        Date newBeginPeriod = (Date) (param.get(DtoTcKey.BEGIN_PERIOD));
        Date newEndPeriod = (Date) (param.get(DtoTcKey.END_PERIOD));
        if ( newBeginPeriod == null || newEndPeriod == null ) {
            parameterChanged = true;
        } else {
            if (beginPeriod == null || comDate.compareDate(this.beginPeriod, newBeginPeriod) != 0
                || endPeriod == null || comDate.compareDate(this.endPeriod, newEndPeriod) != 0
                    ) {
                parameterChanged = true;
            }
        }

        this.beginPeriod = newBeginPeriod;
        this.endPeriod = newEndPeriod;

        if( parameterChanged == true ){
            setRefreshFlag();
        }
    }

    protected void setRefreshFlag(){
        super.setParameter(new Parameter());
    }

    protected void resetUI() {
        Parameter param = createParameterForRefreshWorkArea();

        vwOvertimeList.setParameter(param);
        vwOvertimeList.refreshWorkArea();
        vwWeeklyReportList.setParameter(param);
        vwWeeklyReportList.refreshWorkArea();
    }

    protected Parameter createParameterForRefreshWorkArea() {
        Parameter param = new Parameter();
        param.put(DtoTcKey.BEGIN_PERIOD, beginPeriod);
        param.put(DtoTcKey.END_PERIOD, endPeriod);
        return param;
    }

    public void setBottomSpace(int spaceHeight) {
        TcLayout layout = (TcLayout) incrementPanel.getLayout();
        layout.setBottomSpace(spaceHeight);
    }

    public void setReduceOrder(int order) {
        TcLayout layout = (TcLayout) incrementPanel.getLayout();
        layout.setReduceOrder(order);
    }

    public Date getBeginPeriod() {
        return beginPeriod;
    }

    public Date getEndPeriod() {
        return endPeriod;
    }
}
