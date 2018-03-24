package client.essp.timesheet.activity.general;

import client.essp.common.view.VWGeneralWorkArea;
import java.awt.Rectangle;
import c2s.essp.timesheet.activity.DtoActivityGeneral;
import java.awt.Dimension;
import client.framework.view.vwcomp.VWUtil;
import client.framework.view.vwcomp.VWJTextArea;
import java.awt.event.ActionEvent;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import c2s.essp.timesheet.activity.DtoActivityKey;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import com.wits.util.Parameter;

/**
 * <p>Title: VwNoteToPM</p>
 *
 * <p>Description:NoteToPM卡片 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author TBH
 * @version 1.0
 */
public class VwNoteToPM extends VWGeneralWorkArea implements IVWPopupEditorEvent{
    static private String actionIdSaveFeedback = "FTSSaveFeedback";
     static private String actionIdNoteInit = "FTSShowNote";
    VWJTextArea feedBackInput = new VWJTextArea();
    DtoActivityGeneral dtoGeneral = new DtoActivityGeneral();

    public VwNoteToPM() {
        try {
           jbInit();
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
    //初始化
    public void jbInit(){
        this.setLayout(null);
        this.setPreferredSize(new Dimension(300, 300));
        feedBackInput.setBounds(new Rectangle(5, 5, 290, 295));
        feedBackInput.setName("feedBack");
        feedBackInput.setEnabled(true);
        this.add(feedBackInput);
    }

   //参数设置
   public void setParameter(Parameter para) {
       super.setParameter(para);
       this.dtoGeneral = (DtoActivityGeneral)para.get(DtoActivityKey.DTO_GENERAL);
       if (this.dtoGeneral == null) {
           this.dtoGeneral = new DtoActivityGeneral();
       }
        VWUtil.bindDto2UI(dtoGeneral, this);
        this.refreshWorkArea();
    }

   //重置
    public void resetUI() {
        if(dtoGeneral.getRid() == null){
            return;
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdNoteInit);
        inputInfo.setInputObj(DtoActivityKey.DTO_RID, dtoGeneral.getRid());
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            DtoActivityGeneral retGeneral = (DtoActivityGeneral) returnInfo.
                                            getReturnObj(DtoActivityKey.DTO_GENERAL);
            VWUtil.bindDto2UI(retGeneral, this);
        }
    }
    //点击OK按钮保存数据
    public boolean onClickOK(ActionEvent actionEvent) {
        if (checkModified()) {
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.actionIdSaveFeedback);
            inputInfo.setInputObj(DtoActivityKey.DTO_RID, dtoGeneral.getRid());
            inputInfo.setInputObj(DtoActivityGeneral.KEY_FEEDBACK, feedBackInput.getUICValue());

            ReturnInfo returnInfo = accessData(inputInfo);
            if (returnInfo.isError() == false) {
                DtoActivityGeneral retGeneral = (DtoActivityGeneral) returnInfo.
                                                getReturnObj(DtoActivityKey.DTO);
                VWUtil.bindDto2UI(retGeneral, this);
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
    //检测是否数据有变动
    private boolean checkModified() {
         VWUtil.convertUI2Dto(dtoGeneral, this);
         return dtoGeneral.isChanged();
  }

    public boolean onClickCancel(ActionEvent actionEvent) {
       return true;
   }

}
