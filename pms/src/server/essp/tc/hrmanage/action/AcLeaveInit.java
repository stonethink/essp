package server.essp.tc.hrmanage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import itf.hr.HrFactory;
import c2s.essp.common.user.DtoUser;
import java.util.List;
import java.util.Vector;
import c2s.dto.DtoComboItem;
import server.essp.attendance.leave.viewbean.VbLeaveType;
import server.essp.attendance.leave.logic.LgLeave;
import server.essp.attendance.leave.viewbean.VbLeavePersonalStatus;
import java.util.Map;
import java.util.HashMap;
import c2s.essp.tc.leave.DtoLeave;
import c2s.essp.common.calendar.WrokTimeFactory;
import c2s.essp.attendance.Constant;

public class AcLeaveInit extends AbstractESSPAction {
    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request, HttpServletResponse response, TransactionData data) throws BusinessException {
        DtoLeave dto = (DtoLeave)data.getInputInfo().getInputObj(DtoTcKey.USER_ID);
        String loginId = dto.getLoginId();
        DtoUser user = HrFactory.create().findResouce(loginId);
        if(user == null)
            throw new BusinessException("HR_LEAVE_00001","can not find user'"+loginId+"'");
        String organization = user.getOrganization();
        if(user.getOrganization() == null)
            throw new BusinessException("HR_LEAVE_00002","User'"+loginId+"' is not in any organization!");
        dto.setOrgId(user.getOrgId());
        dto.setOrgName(organization);
        LgLeave lg = new LgLeave();
        //查找人员的可用假别状况
        VbLeavePersonalStatus status = lg.getUserLeaveStatus(loginId);
        List usable = status.getUsableList();
        List leaveTypeList = status.getLeaveTypeList();
        Vector leaveTypeComList = new Vector(leaveTypeList.size());
        Map leaveUsableMap = new HashMap(leaveTypeList.size());
        Map leaveMaxHoursMap = new HashMap(leaveTypeList.size());
        for(int i = 0;i < leaveTypeList.size() ;i ++){
            VbLeaveType leaveType = (VbLeaveType) leaveTypeList.get(i);
            String leaveName = leaveType.getLeaveName();
            DtoComboItem item = new DtoComboItem(leaveName);
            item.setItemRelation(leaveType.getSettlementWay());//Relation设置成假别的支付方式
            leaveTypeComList.add(item);
            Object usableHours = (Object) usable.get(i);
            //修改时,年休或调休假可用时间加上本次申请的时间
            if(dto.getRid() != null
               &&(Constant.LEAVE_RELATION_SHIFT.equals(leaveType.getRelation()) || Constant.LEAVE_RELATION_ANNU_LEAVE.equals(leaveType.getRelation())
               && usableHours instanceof Double
               && usableHours != null)){
               Double acutalTotalHoursBak = dto.getActualTotalHoursBak();
               Double usableHours2 = (Double) usableHours;
               double temp = usableHours2.doubleValue() + acutalTotalHoursBak.doubleValue();
               usableHours = new Double(temp);
            }
            leaveUsableMap.put(leaveName,usableHours);
            Long maxDay = leaveType.getMaxDay();
            if(maxDay != null){
                double maxHours = WrokTimeFactory.serverCreate().getDailyWorkHours() * maxDay.longValue();
                leaveMaxHoursMap.put(leaveName,new Double(maxHours));
            }
        }

        data.getReturnInfo().setReturnObj("leaveUsableMap",leaveUsableMap);
        data.getReturnInfo().setReturnObj("leaveMaxHoursMap",leaveMaxHoursMap);
        data.getReturnInfo().setReturnObj("leaveTypeComList",leaveTypeComList);
    }
}
