package client.essp.pms.account.labor;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Date;
import java.util.Vector;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoAcntLoaborRes;
import c2s.essp.pms.account.DtoPmsAcnt;
import client.essp.common.view.VWWorkArea;
import client.framework.model.VMComboBox;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;
import com.wits.util.TestPanel;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;

public class VwAcntLaborResourceGeneral extends VwAcntLaborResourceGeneralBase {
    public static final String ACTIONID_RES_LOGINID_CHECK =
        "FAcntLaborResourceCheckLoginIdAction";
    private Vector jobCodeOptions;
    private Long acntRid;
    private DtoAcntLoaborRes dto;
    private String oldLoginId = "";

    public VwAcntLaborResourceGeneral() {
        super();
        try {
            addUICEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 添加事件
     */
    private void addUICEvent() {
        inputLoginId.getTextComp().addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            }

            public void focusLost(FocusEvent e) {
                if (isLoingIdChanged()) {
                    ationPerformedCheckLoginId();
                }
            }
        });
        //Detail表格数据改变时,更新General的三个栏位
        this.detailList.getModel().addTableModelListener(new TableModelListener(){
            public void tableChanged(TableModelEvent e) {
                inputStartDate.setUICValue(dto.getPlanStart());
                inputFinishDate.setUICValue(dto.getPlanFinish());
                inputPlanWorkHours.setUICValue(dto.getPlanWorkTime());
            }
        });
    }
    /**
     * 判断是否修改了LoginId
     * @return boolean
     */
    private boolean isLoingIdChanged() {
        String loginId = StringUtil.nvl(inputLoginId.getUICValue()).trim();
        boolean isChanged = !oldLoginId.equals(loginId);
        if (isChanged) {
            oldLoginId = loginId;
        }
        return isChanged;
    }


    /**
     * 判断新的LoiginId是否为系统内部人员
     */
    private void ationPerformedCheckLoginId() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_RES_LOGINID_CHECK);
        inputInfo.setInputObj("loginId", inputLoginId.getUICValue().toString().trim());
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            DtoAcntLoaborRes res = (DtoAcntLoaborRes) returnInfo.getReturnObj(
                "resource");
            //该loginId存在，填写栏位的默认值
            if ( res != null) {
                inputIsInternal.setSelected(true);
                inputName.setText(res.getEmpName());
                inputName.setEnabled(false);
                inputJobCode.setUICValue(res.getJobcodeId());
                inputResStatus.setEnabled(true);
                inputResStatus.setUICValue(DtoAcntLoaborRes.RESOURCE_STATUS_IN);
                comFORM.setFocus(inputStartDate);
            } else if ( res == null ) { //不存在该LoginId
                inputIsInternal.setSelected(false);
                inputName.setEnabled(true);
                inputJobCode.setEnabled(true);
                inputResStatus.setUICValue(DtoAcntLoaborRes.
                                           RESOURCE_STATUS_NOT_READY);
                inputResStatus.setEnabled(false);
            }
        }
    }

    /**
     * 保存LaborResource时间
     */
    public void actionPerformedSave() {
        if (dto == null) {
            dto = new DtoAcntLoaborRes();
            dto.setAcntRid(acntRid);
        }
        VWUtil.convertUI2Dto(dto,this);
        dto.setEmpName(inputName.getUICValue().toString());
        dto.setLoginId(inputLoginId.getUICValue().toString().trim());
        //如果该人员为外部，设置LoiginId状态为外部人员，ResouceStatus为Not Available
        if (!inputIsInternal.isSelected()) {
            dto.setLoginidStatus(DtoAcntLoaborRes.LOGINID_STATUS_OUT);
            dto.setResStatus(DtoAcntLoaborRes.RESOURCE_STATUS_NOT_READY);
        } else { //如果该人员为内部，设置LoiginId状态为内部部人员，ResouceStatus设置所选择的状态
            dto.setLoginidStatus(DtoAcntLoaborRes.LOGINID_STATUS_IN);
            String resStatus = StringUtil.nvl(inputResStatus.getUICValue());
            //默认状态为In
            if ("".equals(resStatus)) {
                resStatus = DtoAcntLoaborRes.RESOURCE_STATUS_IN;
            }
            dto.setResStatus(resStatus);
        }
    }

    public void resetUI() {
        if (jobCodeOptions == null) {
            jobCodeOptions = new Vector();
        }
        VMComboBox vmJobCode = new VMComboBox(jobCodeOptions);
        inputJobCode.setModel(vmJobCode);
        inputIsInternal.setEnabled(false);
        VWUtil.bindDto2UI(dto, this);
        detailList.resetUI();
    }

    public void setParameter(Parameter param) {
        dto = (DtoAcntLoaborRes) param.get("dto");
        DtoPmsAcnt pmsAcc = (DtoPmsAcnt) param.get("dtoAccount");
        acntRid = pmsAcc.getRid();
        jobCodeOptions = (Vector) param.get("jobCodeOptions");
        //新增Labor Resource
        if (dto == null) {
            dto = new DtoAcntLoaborRes();
            dto.setAcntRid(acntRid);
            dto.setResStatus (DtoAcntLoaborRes.RESOURCE_STATUS_NOT_READY);
        }
        //修改Labor Resource
        else{
            oldLoginId = dto.getLoginId();
            //内部人员LoginId,Name不能修改
            if (DtoAcntLoaborRes.LOGINID_STATUS_IN.equals(dto.getLoginidStatus())) {
                inputIsInternal.setSelected(true);
                inputName.setEnabled(false);
                inputLoginId.getTextComp().setEnabled(false);
                inputLoginId.getButtonComp().setEnabled(false);
                inputJobCode.setEnabled(true);
            }else{
                inputResStatus.setEnabled(false);
            }
        }
       Parameter param2 = new Parameter();
       param2.put("dto",dto);
       param2.put("dtoAccount",pmsAcc);
       detailList.setParameter(param2);
    }

    public DtoAcntLoaborRes getDto() {
        actionPerformedSave();
        return dto;
    }

    public static void main(String[] args) {
        VWWorkArea resource = new VWWorkArea();
        VwAcntLaborResourceGeneral general = new VwAcntLaborResourceGeneral();
        resource.addTab("add", general);
        TestPanel.show(resource);
    }
}
