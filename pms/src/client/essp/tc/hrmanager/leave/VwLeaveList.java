package client.essp.tc.hrmanager.leave;

import java.util.Date;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import com.wits.util.Parameter;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import c2s.essp.common.code.DtoKey;
import c2s.essp.tc.leave.DtoLeave;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.common.Constant;
import client.framework.view.common.comMSG;
import c2s.essp.tc.overtime.DtoOverTime;

public class VwLeaveList extends VwLeaveListBase {
    static final String actionIdList = "FTCHrManageLeaveListAction";
    static final String actionIdDelete = "FTCHrManageLeaveDeleteAction";

    private String userId;
    private Date beginPeriod;
    private Date endPeriod;
    private List leaveList;
    private boolean isParameterValidate = true;
    VwLeaveGeneral vwLeaveGeneral;
    public VwLeaveList() {
        super();
        try {
            addUICEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addUICEvent() {
        this.getButtonPanel().addAddButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd();
            }
        });

        this.getButtonPanel().addEditButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedEdit();
            }
        });

        this.getButtonPanel().addDelButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedDel();
            }
        });
        this.getButtonPanel().addLoadButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad();
            }
        });
    }

    private void actionPerformedAdd(){
        if(vwLeaveGeneral == null)
            vwLeaveGeneral = new VwLeaveGeneral();
        Parameter param = new Parameter();
        param.put(DtoTcKey.BEGIN_PERIOD,beginPeriod);
        param.put(DtoTcKey.END_PERIOD,endPeriod);
        DtoLeave dto = new DtoLeave();
        dto.setLoginId(userId);
        param.put(DtoKey.DTO,dto);
        vwLeaveGeneral.setParameter(param);
        vwLeaveGeneral.resetUI();
        VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(), "Leave",
                                                vwLeaveGeneral, vwLeaveGeneral);
        pop.show();
        if(vwLeaveGeneral.needRefrehParent())
            resetUI();
    }

    private void actionPerformedEdit(){
        if(vwLeaveGeneral == null)
            vwLeaveGeneral = new VwLeaveGeneral();
        Parameter param = new Parameter();
        param.put(DtoTcKey.BEGIN_PERIOD,beginPeriod);
        param.put(DtoTcKey.END_PERIOD,endPeriod);
        param.put(DtoKey.DTO,this.getTable().getSelectedData());
        vwLeaveGeneral.setParameter(param);
        vwLeaveGeneral.resetUI();
                VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(), "Leave",
                                                        vwLeaveGeneral, vwLeaveGeneral);
        pop.show();
        if(vwLeaveGeneral.needRefrehParent())
            resetUI();
    }
    private void actionPerformedDel(){
        DtoLeave dto = (DtoLeave) this.getTable().getSelectedData();
        if(dto == null)
            return;
        if(!c2s.essp.attendance.Constant.STATUS_FINISHED.equals(dto.getStatus())){
            comMSG.dispMessageDialog("Can not delete unfinished work flow!");
            return;
        }
        int f = comMSG.dispConfirmDialog("Confirm to delete selected record!");
        if( f == Constant.OK ){
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.actionIdDelete);
            inputInfo.setInputObj(DtoKey.DTO, this.getTable().getSelectedData());
            ReturnInfo returnInfo = accessData(inputInfo);
            if(!returnInfo.isError()){
                resetUI();
            }
        }
    }
    private void actionPerformedLoad(){
        resetUI();
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.beginPeriod = (Date) (param.get(DtoTcKey.BEGIN_PERIOD));
        this.endPeriod = (Date) (param.get(DtoTcKey.END_PERIOD));
        this.userId = (String) (param.get(DtoTcKey.USER_ID));
        if(beginPeriod == null || endPeriod == null || userId == null)
            isParameterValidate = false;
        else
            isParameterValidate = true;
    }

    public void resetUI(){
        this.getButtonPanel().setVisible(isParameterValidate);
        if (!isParameterValidate)
            return;

        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdList);
        inputInfo.setInputObj(DtoTcKey.USER_ID, this.userId);
        inputInfo.setInputObj(DtoTcKey.BEGIN_PERIOD, this.beginPeriod);
        inputInfo.setInputObj(DtoTcKey.END_PERIOD, this.endPeriod);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            leaveList = (List) returnInfo.getReturnObj("leaveList");
            getTable().setRows(leaveList);
        }
    }

}
