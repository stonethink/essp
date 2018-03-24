package client.essp.timesheet.activity.general;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.activity.DtoActivityGeneral;
import c2s.essp.timesheet.activity.DtoActivityKey;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJEditorPane;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.Parameter;

/**
 * <p>Title:VwFeedBackNote </p>
 *
 * <p>Description:FeedBackNote卡片 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VwFeedBackNote extends VWGeneralWorkArea implements IVWPopupEditorEvent{
    static private String actionIdNoteInit = "FTSShowNote";
    VWJEditorPane feedBackInput = new VWJEditorPane();
    DtoActivityGeneral dtoGeneral = new DtoActivityGeneral();

    public VwFeedBackNote() {
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
        feedBackInput.setName("noteTo");
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

   //OK事件
    public boolean onClickOK(ActionEvent actionEvent) {
           return true;
       }
    //Cancel事件
    public boolean onClickCancel(ActionEvent actionEvent) {
           return true;
     }
}
