package client.essp.tc.dmView.byAcnt;

import java.awt.BorderLayout;

import c2s.essp.tc.weeklyreport.DtoTcKey;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWTDWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.tc.dmView.VwPeriod;
import client.framework.view.event.DataChangedListener;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.vwcomp.IVWAppletParameter;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByDmAcnt;
import client.essp.tc.common.VWJDatePeriod;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwDmViewByAcnt extends VWGeneralWorkArea implements IVWAppletParameter {
    public VwPeriod vwPeriod = null;
    VwTcListByAcnt vwTcListByAcnt = null;
    VwTcListByAcntWorker vwTcListByAcntWorker = null;

    public VwDmViewByAcnt() {
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

        vwTcListByAcnt = new VwTcListByAcnt();
        vwTcListByAcntWorker = new VwTcListByAcntWorker();

        VWTDWorkArea mainWorkArea = new VWTDWorkArea(200);
        mainWorkArea.getTopArea().add(vwTcListByAcnt);
        mainWorkArea.getDownArea().addTab("General", vwTcListByAcntWorker);

        VWWorkArea w = new VWWorkArea();
        w.add(vwPeriod, BorderLayout.NORTH);
        w.add(mainWorkArea, BorderLayout.CENTER);
        w.getButtonPanel().add(vwTcListByAcnt.getButtonPanel());

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

        this.vwTcListByAcnt.getTable().addRowSelectedListener(new RowSelectedListener() {
            public void processRowSelected() {
                processRowSelectAcnt();
            }
        });
    }

    protected void processDataChangedPeriod() {
        Parameter param = new Parameter();
        param.put(DtoTcKey.INC_SUB, new Boolean(vwPeriod.isIncSub()));
        param.put(DtoTcKey.ORG, vwPeriod.getSelectOrg());
        param.put(DtoTcKey.BEGIN_PERIOD, vwPeriod.dtePeriod.getBeginPeriod());
        param.put(DtoTcKey.END_PERIOD, vwPeriod.dtePeriod.getEndPeriod());
        vwTcListByAcnt.setParameter(param);
        vwTcListByAcnt.refreshWorkArea();

        if (vwPeriod.dtePeriod.getTheType() == VWJDatePeriod.PERIOD_ONE_WEEK) {
            vwTcListByAcntWorker.setWeekVisible();
        } else if (vwPeriod.dtePeriod.getTheType() == VWJDatePeriod.PERIOD_ONE_MONTH) {
            vwTcListByAcntWorker.setMonthVisible();
        }

    }

    protected void processRowSelectAcnt() {
        DtoWeeklyReportSumByDmAcnt dto = (DtoWeeklyReportSumByDmAcnt) vwTcListByAcnt.getSelectedData();
        if (dto != null) {
            Parameter param = new Parameter();
            param.put(DtoTcKey.ACNT_RID, dto.getAcntRid());
            param.put(DtoTcKey.BEGIN_PERIOD, dto.getBeginPeriod());
            param.put(DtoTcKey.END_PERIOD, dto.getEndPeriod());
            vwTcListByAcntWorker.setParameter(param);
        } else {
            vwTcListByAcntWorker.setParameter(new Parameter());
        }

        vwTcListByAcntWorker.refreshWorkArea();
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
        VWWorkArea workArea = new VwDmViewByAcnt();

        TestPanel.show(workArea);

        Parameter param = new Parameter();
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

}
