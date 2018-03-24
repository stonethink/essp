package client.essp.tc.pmmanage.onMonth;

import java.util.Date;

import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumOnMonthByPm;
import client.essp.common.view.VWTDWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.tc.common.TableListener;
import client.essp.tc.common.TcLayout;
import client.essp.tc.weeklyreport.VMTableWeeklyReportOnMonth;
import client.essp.tc.weeklyreport.VwWeeklyReportBase;
import client.essp.tc.weeklyreport.VwWeeklyReportListBase;
import client.essp.tc.weeklyreport.VwWeeklyReportOnMonth;
import client.framework.view.event.RowSelectedListener;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwTcOnMonth extends VWTDWorkArea {

    private boolean refreshFlag = false;

    public VwTcListTempOnMonth vwTcListTempOnMonth;
    private VwWeeklyReportBase vwWeeklyReport;
    VwWeeklyReportListBase vwWeeklyReportList;

    public VwTcOnMonth() {
        this(new VwTcListTempOnMonth(), new VwWeeklyReportOnMonth());
    }

    public VwTcOnMonth(VwTcListTempOnMonth vwTcListTempOnMonth, VwWeeklyReportBase vwWeeklyReport) {

        super(200);

        try {
            this.vwTcListTempOnMonth = vwTcListTempOnMonth;
            this.vwWeeklyReport = vwWeeklyReport;
            this.vwWeeklyReportList = vwWeeklyReport.vwWeeklyReportList;
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
    }

    private void jbInit() throws Exception {
        vwTcListTempOnMonth.setBottomSpace(0);

//        vwWeeklyReportList = new VwWeeklyReportListOnMonth(0);
//        vwWeeklyReport = new VwWeeklyReport(vwWeeklyReportList, false);
        vwWeeklyReport.setBottomSpace(0);
        vwWeeklyReport.setReduceOrder(TcLayout.REDUCE_FROM_DOWN_TO_TOP);
        vwWeeklyReport.vwOvertimeList.setMinTableHeight(3);
        vwWeeklyReport.vwOvertimeList.setMaxTableHeight(3);

        this.getTopArea().add(vwTcListTempOnMonth);
        this.getDownArea().addTab("General", vwWeeklyReport);
    }

    /**
     * 定义界面：定义界面事件
     */
    private void addUICEvent() {
        //上面的表格
        VMTableTcOnMonth vmTableTcOnMonth = (VMTableTcOnMonth)this.vwTcListTempOnMonth.vwTcListOnMonth.getModel();
        //下面的表格
        final VMTableWeeklyReportOnMonth vmTableWeeklyReportOnMonth = (VMTableWeeklyReportOnMonth)this.vwWeeklyReportList.getModel();

        //建立数据关联
//        vmTableTcOnMonth.setSumHourOfDays(vmTableWeeklyReportOnMonth.getSumHourOfDays());

        //RowSelected
        this.vwTcListTempOnMonth.vwTcListOnMonth
                .getTable().addRowSelectedListener(new
                RowSelectedListener() {
            public void processRowSelected() {
                processRowSelectedList();
            }
        });

        vmTableWeeklyReportOnMonth.addTableListeners(new TableListener() {
            public void processTableChanged(String eventType) {
                if(eventType.equals(TableListener.EVENT_REFRESH_ACTION) == true) {
                    //刷新下面表格时,要刷新当前选中的行
                    vwTcListTempOnMonth.vwTcListOnMonth.refreshRow();
                }
            }
        });
    }

    /////// 段2，设置参数：获取传入参数
    public void setParameter(Parameter para) {
        this.vwTcListTempOnMonth.setParameter(para);
        this.refreshFlag = true;
    }

    /////// 段3，获取数据并刷新画面
    public void refreshWorkArea() {
        if (refreshFlag == true) {

            vwTcListTempOnMonth.refreshWorkArea();

            this.refreshFlag = false;
        }
    }

    /////// 段4，事件处理

    public void processRowSelectedList() {
        Parameter param = new Parameter();
        DtoWeeklyReportSumOnMonthByPm dto = (DtoWeeklyReportSumOnMonthByPm) vwTcListTempOnMonth.vwTcListOnMonth.getSelectedData();
        if (dto != null) {
            Long acntRid = dto.getAcntRid();
            String userId = dto.getUserId();
            Date beginPeriod = dto.getBeginPeriod();
            Date endPeriod = dto.getEndPeriod();

            param.put(DtoTcKey.ACNT_RID, acntRid);
            param.put(DtoTcKey.USER_ID, userId);
            param.put(DtoTcKey.BEGIN_PERIOD, beginPeriod);
            param.put(DtoTcKey.END_PERIOD, endPeriod);
            vwWeeklyReport.setParameter(param);
        } else {
            vwWeeklyReport.setParameter(param);
        }

        this.getDownArea().getSelectedWorkArea().refreshWorkArea();
    }


    /////// 段5，保存数据
    public void saveWorkArea() {
        vwTcListTempOnMonth.saveWorkArea();
        if (vwTcListTempOnMonth.isSaveOk() == true) {
            this.getDownArea().getSelectedWorkArea().saveWorkArea();
        }
    }

    public boolean isSaveOk() {
        return vwTcListTempOnMonth.isSaveOk()
                && this.getDownArea().getSelectedWorkArea().isSaveOk();
    }

    public static void main(String args[]) {
        VWWorkArea workArea = new VwTcOnMonth();

        TestPanel.show(workArea);

        Parameter param = new Parameter();
        param.put(DtoTcKey.BEGIN_PERIOD, new Date(105, 1, 1));
        param.put(DtoTcKey.END_PERIOD, new Date(105, 12, 30));
        param.put(DtoTcKey.ACNT_RID, new Long(1));
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

}
