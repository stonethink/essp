package client.essp.tc.dmView.byWorker;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.Date;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.tc.common.VWJDatePeriod;
import client.essp.tc.dmView.VwPeriod;
import client.essp.tc.pmmanage.onMonth.VwTcOnMonth;
import client.essp.tc.pmmanage.onMonth.VwTcOnMonthByDm;
import client.essp.tc.pmmanage.onWeek.VwTcOnWeek;
import client.framework.view.event.DataChangedListener;
import client.framework.view.vwcomp.IVWAppletParameter;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import client.essp.tc.pmmanage.onWeek.VwTcOnWeekByDm;
import client.essp.tc.common.TDWorkAreaSplitHeightSetter;
import client.framework.common.Global;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwDmViewByWorker extends VWGeneralWorkArea implements IVWAppletParameter {
    public VwPeriod vwPeriod = null;
    VwTcOnWeek vwTcOnWeek = null;
    VwTcOnMonth vwTcOnMonth = null;

    VWWorkArea mainWorkArea = new VWWorkArea();
    CardLayout cardLayout = new CardLayout();

    public VwDmViewByWorker() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
        vwPeriod.setToday();
    }

    private void jbInit() throws Exception {
        vwPeriod = new VwPeriod();

        vwTcOnWeek = new VwTcOnWeekByDm();
        vwTcOnMonth = new VwTcOnMonthByDm();
        mainWorkArea.setLayout(cardLayout);
        mainWorkArea.add(vwTcOnWeek, String.valueOf(VWJDatePeriod.PERIOD_ONE_WEEK));
        mainWorkArea.add(vwTcOnMonth, String.valueOf(VWJDatePeriod.PERIOD_ONE_MONTH));

        VWWorkArea w = new VWWorkArea();
        w.setLayout(new BorderLayout());
        w.add(vwPeriod, BorderLayout.NORTH);
        w.add(mainWorkArea, BorderLayout.CENTER);
        w.getButtonPanel().add(vwTcOnWeek.vwTcListTempOnWeek.vwTcListOnWeek.getButtonPanel());
        w.getButtonPanel().add(vwTcOnMonth.vwTcListTempOnMonth.vwTcListOnMonth.getButtonPanel());
        vwTcOnWeek.vwTcListTempOnWeek.vwTcListOnWeek.getButtonPanel().setVisible(true);
        vwTcOnMonth.vwTcListTempOnMonth.vwTcListOnMonth.getButtonPanel().setVisible(false);

        this.addTab("TimeCard", w);

    }

    /**
     * 定义界面：定义界面事件
     */
    private void addUICEvent() {
        this.vwPeriod.addDataChangedListener(new DataChangedListener() {
            public void processDataChanged() {
                processDataChangedPeriod();
            }
        });
    }

    protected void processDataChangedPeriod() {
        Parameter param = new Parameter();
        param.put(DtoTcKey.INC_SUB, new Boolean(vwPeriod.isIncSub()));
        param.put(DtoTcKey.ORG, vwPeriod.getSelectOrg());
        param.put(DtoTcKey.BEGIN_PERIOD, vwPeriod.dtePeriod.getBeginPeriod());
        param.put(DtoTcKey.END_PERIOD, vwPeriod.dtePeriod.getEndPeriod());

        if (vwPeriod.dtePeriod.getTheType() == VWJDatePeriod.PERIOD_ONE_WEEK) {
            TDWorkAreaSplitHeightSetter.set(vwTcOnWeek, vwTcOnMonth);
            cardLayout.show(mainWorkArea, String.valueOf(VWJDatePeriod.PERIOD_ONE_WEEK));
            vwTcOnWeek.setParameter(param);
            vwTcOnWeek.refreshWorkArea();
            vwTcOnWeek.vwTcListTempOnWeek.vwTcListOnWeek.getButtonPanel().setVisible(true);
            vwTcOnMonth.vwTcListTempOnMonth.vwTcListOnMonth.getButtonPanel().setVisible(false);
        } else if (vwPeriod.dtePeriod.getTheType() == VWJDatePeriod.PERIOD_ONE_MONTH) {
            TDWorkAreaSplitHeightSetter.set(vwTcOnMonth, vwTcOnWeek);
            cardLayout.show(mainWorkArea, String.valueOf(VWJDatePeriod.PERIOD_ONE_MONTH));
            vwTcOnMonth.setParameter(param);
            vwTcOnMonth.refreshWorkArea();
            vwTcOnWeek.vwTcListTempOnWeek.vwTcListOnWeek.getButtonPanel().setVisible(false);
            vwTcOnMonth.vwTcListTempOnMonth.vwTcListOnMonth.getButtonPanel().setVisible(true);
        }
    }

    protected ReturnInfo accessDataT(InputInfo inputInfo) {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setReturnObj(DtoTcKey.ACNT_Name, "essp");
        returnInfo.setReturnObj(DtoTcKey.ACNT_RID, new Long(1));
        returnInfo.setReturnObj(DtoTcKey.TODAY_DATE, Global.todayDate);
        return returnInfo;
    }

//    //for test
//    public void refreshWorkArea() {
//        vwPeriod.refreshWorkArea();
//    }

    public String[][] getParameterInfo() {
        String[][] parameterInfo = {};
        return parameterInfo;
    }

    public static void main(String args[]) {
        VWWorkArea workArea = new VwDmViewByWorker();

        TestPanel.show(workArea);

        Parameter param = new Parameter();
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

}
