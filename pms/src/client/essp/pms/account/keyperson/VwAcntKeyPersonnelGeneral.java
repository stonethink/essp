package client.essp.pms.account.keyperson;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoAcntKeyPersonnel;
import client.framework.view.common.comFORM;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import c2s.dto.IDto;

public class VwAcntKeyPersonnelGeneral extends VwAcntKeyPersonnelGeneralBase {
    public static final String ACTIONID_KEYPERSON_LOGINID_CHECK = "FAcntLKeyPersonnelCheckLoginIdAction";

    private DtoAcntKeyPersonnel dto;
    private Long acntRid;

    public VwAcntKeyPersonnelGeneral() {
        super();
        try {
            addUICEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addUICEvent(){
        inputLoginId.getTextComp().addFocusListener(new FocusListener(){
            public void focusGained(FocusEvent e) {
            }
            public void focusLost(FocusEvent e) {
                ationPerformedCheckLoginId();
            }
        });
        inputType.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                ationPerformedTypeChange();
            }
        });
        inputEnable.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                dto.setOp(IDto.OP_MODIFY);
            }
        });
    }
    /**
     * 保存事件
     * @param e ActionEvent
     */
    public void actionPerformedSave(){
        if( dto == null)
            dto = new DtoAcntKeyPersonnel();
        VWUtil.convertUI2Dto(dto,this);
        dto.setTypeName(inputType.getUICValue().toString());
        dto.setAcntRid(this.acntRid);
        boolean isEnable = inputEnable.isSelected();
        dto.setEnable( isEnable ?
                       DtoAcntKeyPersonnel.ENABLE : DtoAcntKeyPersonnel.DISABLE );
    }

    public void ationPerformedTypeChange(){
        String type = StringUtil.nvl(inputType.getUICValue());
        if(type.equals(DtoAcntKeyPersonnel.KEY_PERSON_TYPES[0]) || "".equals(type)){
            inputLoginId.getButtonComp().setEnabled(false);
            lbPassword.setVisible(true);
            inputPassword.setVisible(true);
        }else{
            inputLoginId.getButtonComp().setEnabled(true);
            lbPassword.setVisible(false);
            inputPassword.setVisible(false);
        }
    }
    /**
     * 检查LoginId
     */
    public void ationPerformedCheckLoginId(){
        inputType.setEnabled(false);
        String loginId = StringUtil.nvl(inputLoginId.getUICValue()).trim();
        if("".equals(loginId))
            return;
        String type = StringUtil.nvl(inputType.getUICValue());
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_KEYPERSON_LOGINID_CHECK);
        inputInfo.setInputObj("loginId", loginId);
        inputInfo.setInputObj("type", type);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            DtoAcntKeyPersonnel keyPersonnel = (DtoAcntKeyPersonnel)returnInfo.getReturnObj("keyPersonnel");
            //类型为Customer，且找不到该Customer，则新增Customer
            if(keyPersonnel == null && type.equals(DtoAcntKeyPersonnel.KEY_PERSON_TYPES[0])){
                clearUI();
                inputLoginId.setUICValue(loginId);
            }//类型不为Customer且又找不到该内部人员，报错
            else if(keyPersonnel == null && !type.equals(DtoAcntKeyPersonnel.KEY_PERSON_TYPES[0])){
                 comMSG.dispErrorDialog("Login Id:["+loginId+"] is not valid!");
                 comFORM.setFocus(inputLoginId);
            }
            else if(keyPersonnel != null ) {
                VWUtil.bindDto2UI(keyPersonnel,this);
                inputType.setUICValue(type);
                if( dto == null)
                    dto = new DtoAcntKeyPersonnel();
                dto.setIsExisted(true);
                comFORM.setFocus(inputOrganization);
                if(!type.equals(DtoAcntKeyPersonnel.KEY_PERSON_TYPES[0])){
                    inputUserName.setEnabled(false);
                }
            }
        }
    }

    public void setParameter(Parameter param){
        super.setParameter(param);
        this.dto = (DtoAcntKeyPersonnel) param.get("dto");
        this.acntRid = (Long) param.get("acntRid");
    }

    public void resetUI(){
        if(dto != null){
            VWUtil.bindDto2UI(dto,this);
            inputType.setEnabled(false);
            inputUserName.setEnabled(false);
            inputLoginId.getButtonComp().setEnabled(false);
            if(dto.getRid() != null){
                boolean enable = StringUtil.nvl(dto.getEnable()).equals(DtoAcntKeyPersonnel.ENABLE);
                inputEnable.setSelected(enable);
                inputLoginId.getTextComp().setEnabled(false);
            }
            if(DtoAcntKeyPersonnel.KEY_PERSON_TYPES[0].equals(dto.getTypeName())){
                lbPassword.setVisible(true);
                inputPassword.setVisible(true);
                inputUserName.setEnabled(true);
                inputPassword.setUICValue(dto.getPassword());
            }else{
                lbPassword.setVisible(false);
                inputPassword.setVisible(false);
            }
        }else{
            clearUI();
            inputType.setUICValue(DtoAcntKeyPersonnel.KEY_PERSON_TYPES[0]);
        }
    }

    private void clearUI() {
        inputLoginId.setUICValue(null);
        inputUserName.setUICValue(null);
        inputUserName.setEnabled(true);
        inputOrganization.setUICValue(null);
        inputTitle.setUICValue(null);
        inputPhone.setUICValue(null);
        inputEmail.setUICValue(null);
        inputFax.setUICValue(null);
        inputEnable.setSelected(true);
        lbPassword.setVisible(true);
        inputPassword.setVisible(true);
    }

    public DtoAcntKeyPersonnel getDto(){
        actionPerformedSave();
        return dto;
    }
}
