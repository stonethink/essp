package client.essp.tc.hrmanager;

import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByHr;
import client.essp.common.view.VWTDWorkArea;
import client.essp.tc.weeklyreport.VwWeeklyReportListBase;
import client.framework.view.event.RowSelectedListener;
import com.wits.util.Parameter;
import client.essp.tc.hrmanager.overtime.VwOverTimeList;
import client.essp.tc.hrmanager.leave.VwLeaveList;
import client.essp.tc.hrmanager.nonattend.VwNonattendList;
import client.essp.tc.hrmanager.attendance.VwAttendanceList;
import client.essp.tc.weeklyreport.VMTableWeeklyReport;
import client.essp.tc.common.TableListener;
import client.essp.tc.weeklyreport.VMTableWeeklyReportOnWeekByHr;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwTcBase extends VWTDWorkArea {
    public final static String actionIdInit = "FTCPmManageInitAction";

    public VwTcListBase vwTcList = null;
    VwWeeklyReportListBase vwWeeklyReportListByHr = null;
    VwOverTimeList vwOverTimeList = null;
    VwLeaveList vwLeaveList = null;
    VwNonattendList vwNonattendList = null;
    VwAttendanceList vwAttendanceList = null;

    public VwTcBase(VwTcListBase vwTcList,
                    VwWeeklyReportListBase vwWeeklyReportListByHr) {
        super(200);

        try {
            this.vwTcList = vwTcList;
            this.vwWeeklyReportListByHr = vwWeeklyReportListByHr;

            vwOverTimeList = new VwOverTimeList();
            vwLeaveList = new VwLeaveList();
            vwAttendanceList = new VwAttendanceList();
            vwNonattendList = new VwNonattendList();

            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
    }

    private void jbInit() throws Exception {

        this.getTopArea().add(vwTcList);
        this.getDownArea().addTab("General", vwWeeklyReportListByHr);
        this.getDownArea().addTab("Overtime", vwOverTimeList);
        this.getDownArea().addTab("Leave", vwLeaveList);
        this.getDownArea().addTab("None Attendance", vwNonattendList);
        this.getDownArea().addTab("Deregulation", vwAttendanceList);

        this.getButtonPanel().add(vwTcList.getButtonPanel());
    }

    /**
     * 定义界面：定义界面事件
     */
    protected void addUICEvent() {
        this.vwTcList.getTable().addRowSelectedListener(new RowSelectedListener() {
            public void processRowSelected() {
                processRowSelectedUser();
            }
        });
    }

    protected void processRowSelectedUser() {
        DtoWeeklyReportSumByHr dto = (DtoWeeklyReportSumByHr) vwTcList.getSelectedData();
        Parameter param = new Parameter();
        if (dto != null) {
            param.put(DtoTcKey.USER_ID, dto.getUserId());
            param.put(DtoTcKey.BEGIN_PERIOD, dto.getBeginPeriod());
            param.put(DtoTcKey.END_PERIOD, dto.getEndPeriod());
        }

        vwWeeklyReportListByHr.setParameter(param);
        vwLeaveList.setParameter(param);
        vwOverTimeList.setParameter(param);
        vwNonattendList.setParameter(param);
        vwAttendanceList.setParameter(param);

        //vwWeeklyReportListByHr.refreshWorkArea();
        getDownArea().getSelectedWorkArea().refreshWorkArea();
    }

    public void setParameter(Parameter param) {
        this.vwTcList.setParameter(param);
    }

    /////// 段3，获取数据并刷新画面
    public void refreshWorkArea() {
        this.vwTcList.refreshWorkArea();
    }
}
