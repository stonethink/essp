package client.essp.tc.hrmanager;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.Date;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByHr;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.tc.common.TDWorkAreaSplitHeightSetter;
import client.essp.tc.common.VWJDatePeriod;
import client.framework.common.Global;
import client.framework.view.event.DataChangedListener;
import client.framework.view.vwcomp.IVWAppletParameter;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import com.wits.util.comDate;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwHrManage extends VWGeneralWorkArea implements IVWAppletParameter {
    public final static String actionIdInit = "FTCPmManageInitAction";

    public VwPeriod vwPeriod = null;
    VwTcOnWeek vwTcOnWeek = null;
    VwTcOnMonth vwTcOnMonth = null;

    VWWorkArea mainWorkArea = new VWWorkArea();
    CardLayout cardLayout = new CardLayout();

    public VwHrManage() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
        initUI();
    }

    private void jbInit() throws Exception {
        vwPeriod = new VwPeriod();

        vwTcOnWeek = new VwTcOnWeek();
        vwTcOnMonth = new VwTcOnMonth();
        mainWorkArea.setLayout(cardLayout);
        mainWorkArea.add(vwTcOnWeek, String.valueOf(VWJDatePeriod.PERIOD_ONE_WEEK));
        mainWorkArea.add(vwTcOnMonth, String.valueOf(VWJDatePeriod.PERIOD_ONE_MONTH));

        VWWorkArea w = new VWWorkArea();
        w.setLayout(new BorderLayout());
        w.add(vwPeriod, BorderLayout.NORTH);
        w.add(mainWorkArea, BorderLayout.CENTER);
        w.getButtonPanel().add(vwTcOnWeek.vwTcList.getButtonPanel());
        w.getButtonPanel().add(vwTcOnMonth.vwTcList.getButtonPanel());
        //w.setBorder(BorderFactory.createLineBorder(Color.blue));
        //vwPeriod.setBorder(BorderFactory.createLineBorder(Color.red));

        this.addTab("TimeCard", w);
    }

    /**
     * 定义界面：定义界面事件
     */
    private void addUICEvent() {
        this.vwPeriod.addDataChangedListener(new DataChangedListener() {
            public void processDataChanged() {
                //account选择
                processDataChangedPeriod();
            }
        });

//        ((VMTableTcOnWeek)this.vwTcOnWeek.vwTcOnWeek.vwTcListOnWeek.getTable().getModel()).addTableListeners(new TableListener() {
//            public void processTableChanged(String eventType) {
//                if (TableListener.EVENT_SUM_DATA_CHANGED.equals(eventType)) {
//
//                    //刷新统计行
//                    vwTcOnMonth.vwTcListTempOnMonth.vwTcListOnMonth.setRefreshFlag();
//                }
//            }
//        });
    }

    private void initUI() {
//        Global.todayDate = "20051203"; //for test
//        Global.todayDatePattern = "yyyyMMdd";
        Date todayDate = Global.todayDate;

        vwPeriod.dtePeriod.setTheType(VWJDatePeriod.PERIOD_ONE_WEEK);
        vwPeriod.dtePeriod.setTheDate(todayDate);
    }

    protected void processDataChangedPeriod() {
        Parameter param = new Parameter();
        param.put(DtoTcKey.ACNT_RID, vwPeriod.getSelectAcnt());
        param.put(DtoTcKey.BEGIN_PERIOD, vwPeriod.dtePeriod.getBeginPeriod());
        param.put(DtoTcKey.END_PERIOD, vwPeriod.dtePeriod.getEndPeriod());

        if (vwPeriod.dtePeriod.getTheType() == VWJDatePeriod.PERIOD_ONE_WEEK) {
            TDWorkAreaSplitHeightSetter.set(vwTcOnWeek, vwTcOnMonth);
            cardLayout.show(mainWorkArea, String.valueOf(VWJDatePeriod.PERIOD_ONE_WEEK));
            param.put(DtoTcKey.PERIOD_TYPE, DtoWeeklyReportSumByHr.ON_WEEK);
            vwTcOnWeek.setParameter(param);
            vwTcOnWeek.refreshWorkArea();
            vwTcOnWeek.vwTcList.getButtonPanel().setVisible(true);
            vwTcOnMonth.vwTcList.getButtonPanel().setVisible(false);
        } else if (vwPeriod.dtePeriod.getTheType() == VWJDatePeriod.PERIOD_ONE_MONTH) {
            TDWorkAreaSplitHeightSetter.set(vwTcOnMonth, vwTcOnWeek);
            cardLayout.show(mainWorkArea, String.valueOf(VWJDatePeriod.PERIOD_ONE_MONTH));
            param.put(DtoTcKey.PERIOD_TYPE, DtoWeeklyReportSumByHr.ON_MONTH);
            vwTcOnMonth.setParameter(param);
            vwTcOnMonth.refreshWorkArea();
            vwTcOnWeek.vwTcList.getButtonPanel().setVisible(false);
            vwTcOnMonth.vwTcList.getButtonPanel().setVisible(true);
        }
    }

    protected ReturnInfo accessDataT(InputInfo inputInfo) {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setReturnObj(DtoTcKey.ACNT_Name, "essp");
        returnInfo.setReturnObj(DtoTcKey.ACNT_RID, new Long(1));
        returnInfo.setReturnObj(DtoTcKey.TODAY_DATE, Global.todayDate);
        return returnInfo;
    }

    public String[][] getParameterInfo() {
        String[][] parameterInfo = { {"entryFunType", "", "entryFunType"}
        };
        return parameterInfo;
    }

    public static void main(String[] args) {
        VwHrManage workArea = new VwHrManage();

        TestPanel.show(workArea);

        Parameter param = new Parameter();
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

}
