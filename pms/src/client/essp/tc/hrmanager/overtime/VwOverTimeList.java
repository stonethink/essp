package client.essp.tc.hrmanager.overtime;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.wits.util.Parameter;
import java.util.Date;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import java.util.List;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import client.framework.common.Constant;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJPopupEditor;
import c2s.essp.common.code.DtoKey;
import c2s.essp.tc.overtime.DtoOverTime;

public class VwOverTimeList extends VwOverTimeListBase {
    static final String actionIdList = "FTCHrManageOverTimListeAction";
    static final String actionIdDelete = "FTCHrManageOverTimDeleteAction";

    private String userId;
    private Date beginPeriod;
    private Date endPeriod;
    private List overtimeList;
    private boolean isParameterValidate = true;
    VwOverTimeGeneral vwOverTime;
    public VwOverTimeList() {
        super();
        try {
            addUICEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addUICEvent(){
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
        if(vwOverTime == null)
            vwOverTime = new VwOverTimeGeneral();
        Parameter param = new Parameter();
        param.put(DtoTcKey.BEGIN_PERIOD,beginPeriod);
        param.put(DtoTcKey.END_PERIOD,endPeriod);
        DtoOverTime dto = new DtoOverTime();
        dto.setLoginId(userId);
        param.put(DtoKey.DTO,dto);
        vwOverTime.setParameter(param);
        vwOverTime.resetUI();
        VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(), "Over Time",
                                                vwOverTime, vwOverTime);
        pop.show();
        if(vwOverTime.needRefrehParent())
            resetUI();
    }
    private void actionPerformedEdit(){
        if(vwOverTime == null)
            vwOverTime = new VwOverTimeGeneral();
        Parameter param = new Parameter();
        param.put(DtoTcKey.BEGIN_PERIOD,beginPeriod);
        param.put(DtoTcKey.END_PERIOD,endPeriod);
        param.put(DtoKey.DTO,this.getTable().getSelectedData());
        vwOverTime.setParameter(param);
        vwOverTime.resetUI();
        VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(), "Over Time",
                                                vwOverTime, vwOverTime);
        pop.show();
        if(vwOverTime.needRefrehParent())
            resetUI();
    }
    private void actionPerformedDel(){
        DtoOverTime dto = (DtoOverTime) this.getTable().getSelectedData();
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
            overtimeList = (List) returnInfo.getReturnObj("overTimeList");
            getTable().setRows(overtimeList);
        }
    }
}

