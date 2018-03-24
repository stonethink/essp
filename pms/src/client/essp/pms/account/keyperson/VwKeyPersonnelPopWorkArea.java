package client.essp.pms.account.keyperson;

import java.awt.Dimension;

import c2s.essp.pms.account.DtoAcntKeyPersonnel;
import client.essp.common.view.VWGeneralWorkArea;
import com.wits.util.Parameter;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import client.framework.view.common.comFORM;
import c2s.essp.pms.account.DtoAcntLoaborRes;
import com.wits.util.StringUtil;
import client.framework.view.common.comMSG;

public class VwKeyPersonnelPopWorkArea extends VWGeneralWorkArea {
    public static final String ACTIONID_KEYPERSON_ADD = "FAcntLKeyPersonnelAddAction";
    public static final String ACTIONID_KEYPERSON_UPDATE = "FAcntLKeyPersonnelUpdateAction";
    private VwAcntKeyPersonnelGeneral person = new VwAcntKeyPersonnelGeneral();

    public VwKeyPersonnelPopWorkArea() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(580, 430));
        this.addTab("Personnel", person, true);
    }
    public void setParameter(Parameter param) {
        super.setParameter(param);
        person.setParameter(param);
    }
    public DtoAcntKeyPersonnel getDto(){
        return person.getDto();
    }
    public void resetUI(){
        person.resetUI();
    }
    public boolean saveOrUpdate(){
        DtoAcntKeyPersonnel dto = person.getDto();
        if(dto == null || !checkModified(dto))
            return true;
        if(validateDate()){
            if(dto.getRid() == null)
                return  save(dto);
            else
                return  update(dto);
        }
        return false;
    }
    private boolean checkModified(DtoAcntKeyPersonnel dto){
        return dto.isChanged();
    }
    private boolean validateDate(){
        String loginId = StringUtil.nvl(person.inputLoginId.getUICValue()).trim();
        String name = StringUtil.nvl(person.inputUserName.getUICValue()).trim();
        if("".equals(loginId) || "".equals(name)){
            comMSG.dispErrorDialog("Please enter the loginId and username!");
                return false;
        }
        if(person.inputPassword.isVisible()){
            String password = StringUtil.nvl(person.inputPassword.getUICValue()).trim();
            if("".equals(password)){
                comMSG.dispErrorDialog("Please enter the password!");
                return false;
            }
        }
        return true;
    }
    private boolean save(DtoAcntKeyPersonnel dto){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_KEYPERSON_ADD);
        inputInfo.setInputObj("dto",dto);
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()){
            return true;
        }else{
            comFORM.setFocus(person.inputLoginId);
            return false;
        }
    }
    private boolean update(DtoAcntKeyPersonnel dto){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_KEYPERSON_UPDATE);
        inputInfo.setInputObj("dto",dto);
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()){
            return true;
        }else{
            comFORM.setFocus(person.inputLoginId);
            return false;
        }
    }
}
