package client.essp.tc.weeklyreport;

import c2s.essp.tc.weeklyreport.DtoTcKey;
import com.wits.util.Parameter;
import client.essp.tc.common.TableListener;
import java.awt.Dimension;

public class VwWeeklyReportOnWeek extends VwWeeklyReportBase {

    Long acntRid = null;
    String userId = null;
    String confirmStatus = null;

    public VwWeeklyReportOnWeek() {
        this(new VwWeeklyReportListOnWeekByPm());
    }

    public VwWeeklyReportOnWeek(VwWeeklyReportListBase vwWeeklyReportList) {
        this.vwWeeklyReportList = vwWeeklyReportList;

        try {
            jbInit();
            initUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void initUI(){
        super.initUI();

        final VMTableWeeklyReport vmTableWeeklyReport = (VMTableWeeklyReport) vwWeeklyReportList.getTable().getModel();
        vmTableWeeklyReport.addTableListeners(new TableListener() {
            public void processTableChanged(String eventType) {
                if (TableListener.EVENT_REFRESH_ACTION.equals(eventType)) {

                    //当weeklyReportList刷新时，同时刷新vwOvertimeList
                    vwOvertimeList.actionPerformedLoad();
                }
            }
        });
    }


    public void setParameter(Parameter param) {
        super.setParameter(param);

        String newUserId = (String) param.get(DtoTcKey.USER_ID);
        Long newAcntRid = (Long) param.get(DtoTcKey.ACNT_RID);
        String newConfirmStatus = (String)param.get(DtoTcKey.CONFIRM_STATUS);

        boolean isParameterChanged = false;
        if( newUserId == null || newUserId.equals(this.userId) == false
        || newAcntRid == null || newAcntRid.equals(this.acntRid) == false
                ){
            isParameterChanged = true;
        }

        if( isParameterChanged ){
            setRefreshFlag();
        }

        this.userId = newUserId;
        this.acntRid = newAcntRid;
        this.confirmStatus = newConfirmStatus;
    }

    protected Parameter createParameterForRefreshWorkArea() {
        Parameter param = super.createParameterForRefreshWorkArea();
        param.put(DtoTcKey.ACNT_RID, acntRid);
        param.put(DtoTcKey.USER_ID, userId);
        param.put(DtoTcKey.CONFIRM_STATUS, confirmStatus);
        return param;
    }

}
