package client.essp.cbs.config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import c2s.dto.DtoTreeNode;
import c2s.dto.ITreeNode;
import c2s.essp.cbs.DtoSubject;
import client.framework.view.common.comFORM;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;
import c2s.dto.DtoUtil;
import c2s.dto.IDto;

public class VwCbsGeneral extends VwCbsGeneralBase {
    private DtoSubject dtoSubject = new DtoSubject();
    private DtoTreeNode root;
    private boolean isSaveOk = true;
    public VwCbsGeneral() {
        super();
        addUICEvent();
    }

    private void addUICEvent() {
        getButtonPanel().addSaveButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave();
            }
        });
    }

    private void actionPerformedSave(){
        if(validateData()){
            VWUtil.convertUI2Dto(dtoSubject,this);
            fireDataChanged();
        }
    }
    public void saveWorkArea(){
        isSaveOk = true;
        DtoSubject copy = new DtoSubject();
        DtoUtil.copyBeanToBean(copy,dtoSubject);
        VWUtil.convertUI2Dto(copy,this);
        if(copy.isChanged()){
            isSaveOk = true;
            if (validateData()) {
                VWUtil.convertUI2Dto(dtoSubject, this);
                isSaveOk = true;
                fireDataChanged();
                dtoSubject.setOp(IDto.OP_NOCHANGE);
            } else {
                isSaveOk = false;
            }
        }
    }
    /**
     * 验证每个科目
     * 1.Code和Name不能为空
     * 2.Code在整课树中唯一
     * 3.科目的Attribute唯一
     * @return boolean
     */
    private boolean validateData(){
        String subjectCode = StringUtil.nvl(inputCode.getUICValue()) ;
        String subjectName = StringUtil.nvl(inputName.getUICValue()) ;
        if("".equals(subjectCode) || "".equals(subjectName)){
            comMSG.dispErrorDialog("Please fill subjectCode and subjectName first!");
            if("".equals(subjectCode) ){
                comFORM.setFocus(this.inputCode);
            }else{
                comFORM.setFocus(this.inputName);
            }
            return false;
        }
        String attribute = StringUtil.nvl(inputAttribute.getUICValue());
        if(root != null){
            DtoSubject rootSubject = (DtoSubject) root.getDataBean();
            if(rootSubject != dtoSubject && subjectCode.equals(rootSubject.getSubjectCode())){
                comMSG.dispErrorDialog("The subjectCode has been in the tree!");
                comFORM.setFocus(this.inputCode);
                return false;
            }
            if(rootSubject != dtoSubject && !"".equals(attribute)
               && attribute.equals(rootSubject.getSubjectAttribute())){
                comMSG.dispErrorDialog("The subject Attribute has been in the tree!");
                comFORM.setFocus(this.inputAttribute);
                return false;
            }
            List allChild = root.listAllChildrenByPreOrder();
            for(int i =0;i < allChild.size() ;i ++){
                ITreeNode node = (ITreeNode) allChild.get(i);
                DtoSubject subject  = (DtoSubject) node.getDataBean();
                if(subject != dtoSubject && subjectCode.equals(subject.getSubjectCode())){
                    comMSG.dispErrorDialog("The subjectCode has been in the tree!");
                    comFORM.setFocus(this.inputCode);
                    return false;
                }
                if(subject != dtoSubject && !"".equals(attribute)
                   && attribute.equals(subject.getSubjectAttribute())){
                    comMSG.dispErrorDialog("The subject Attribute has been in the tree!");
                    comFORM.setFocus(this.inputAttribute);
                    return false;
                }
            }
        }
        return true;
    }
    public boolean isSaveOk(){
        return isSaveOk;
    }

    public void setParameter(Parameter param){
        this.dtoSubject = (DtoSubject) param.get("dto");
        this.root = (DtoTreeNode) param.get("root");
        if(dtoSubject == null)
            dtoSubject = new DtoSubject();
    }

    public void resetUI(){
        VWUtil.bindDto2UI(dtoSubject, this);
        comFORM.setFocus(this.inputCode);
    }
}
