package client.essp.timesheet.weeklyreport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import client.essp.timesheet.calendar.CalendarSelectDateListener;
import com.wits.util.Parameter;
import client.essp.timesheet.ActivityChangedListener;
import client.framework.common.Global;

/**
 *
 * <p>Title: VwWeeklyReport</p>
 *
 * <p>Description: 工时单填写主界面</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwWeeklyReport extends VwWeeklyReportBase
        implements CalendarSelectDateListener {

    private final static String actionId_LoadTimeSheet = "FTSLoadTimeSheetByDate";

    private boolean selectDateChangeLock = false;
    private Date workDay = Global.todayDate;
    private JButton btnRefresh;

    public VwWeeklyReport() {
        super();
        addUICEvent();
    }

    private void addUICEvent() {
        this.getButtonPanel().add(detail.getButtonPanel());

        btnRefresh = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetUI();
            }
        });
        btnRefresh.setToolTipText("rsid.common.refresh");
    }

    public void setParameter(Parameter p) {
        super.setParameter(p);
    }

    /**
     * 根据日历选中的当前日期加载数据
     */
    protected DtoTimeSheet loadData() {
        if(workDay == null) return null;
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_LoadTimeSheet);
        inputInfo.setInputObj("workDay", workDay);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if(!returnInfo.isError()) {
           return (DtoTimeSheet) returnInfo.getReturnObj(DtoTimeSheet.DTO);
        }
        return null;
    }

    public void selectDateChanged(Date date) {
        if(!selectDateChangeLock) {
            selectDateChangeLock = true;
            workDay = date;
            resetUI();
            selectDateChangeLock = false;
        }
    }

    public void addActivityChangedListener(ActivityChangedListener l) {
        detail.addActivityChangedListener(l);
    }

}
