package client.essp.timesheet.activity.document;

import client.essp.common.view.VWTableWorkArea;
import c2s.dto.DtoBase;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import client.framework.view.vwcomp.VWJText;
import client.framework.model.VMColumnConfig;
import c2s.dto.ReturnInfo;

import java.util.ArrayList;
import java.util.List;
import c2s.dto.InputInfo;
import com.wits.util.Parameter;
import java.awt.event.ActionEvent;
import client.framework.view.vwcomp.VWJPopupEditor;
import c2s.essp.timesheet.activity.DtoDocument;
import c2s.essp.timesheet.activity.DtoActivityKey;
import client.framework.view.vwcomp.VWUtil;


/**
 * <p>Title: VwWpsDocsLeftBench</p>
 *
 * <p>Description: Document列表卡片</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VwWpsDocsLeftBench extends VWTableWorkArea{
    static final String actionIdInit = "FTSWpsDocsList";
    List docslist = null;
    DtoDocument dtoDocument = new DtoDocument();
    private Long activityRid = null;

    public VwWpsDocsLeftBench() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    //初始化
    private void jbInit() throws Exception {
    Object[][] configs = null;
    try {
     VWJText text =  new VWJText();
      configs = new Object[][] { {"rsid.common.title", "title",
                VMColumnConfig.UNEDITABLE,text},
                {"rsid.common.status", "statusName",
                VMColumnConfig.UNEDITABLE, text}
                , {"rsid.timesheet.workProduct", "isWorkProduct", VMColumnConfig.UNEDITABLE,
                text},
      };
       super.jbInit(configs,DtoBase.class);
               //设置初始列宽
       JTableHeader header = this.getTable().getTableHeader();
//     可排序
       this.getTable().setSortable(true);
       TableColumnModel tcModel = header.getColumnModel();
       tcModel.getColumn(0).setPreferredWidth(200);
       tcModel.getColumn(1).setPreferredWidth(50);
       tcModel.getColumn(2).setPreferredWidth(80);
       } catch (Exception e) {
         e.printStackTrace();
       }
     }
    //重置
     protected void resetUI() {
         VWUtil.clearUI(this);
        if (activityRid == null) {
            return;
        }

         InputInfo inputInfo = new InputInfo();
         inputInfo.setActionId(this.actionIdInit);
         inputInfo.setInputObj(DtoActivityKey.DTO_RID, activityRid);
         ReturnInfo returnInfo = accessData(inputInfo);
         if (returnInfo.isError() == true) {
             return;
         }
         docslist = (List) returnInfo.getReturnObj(DtoActivityKey.DTO_DOCUMENT_LIST);
         getTable().setRows(docslist);
         if (docslist != null && docslist.size() > 0) {
             getTable().setSelectRow(0);
         } else {
        	 //add by lipengxu 071224, 清空列表
        	 this.getTable().setRows(new ArrayList());
         }
     }

     protected void actionPerformedBtnDaily(ActionEvent e) {
         VwWpsDocsDetail vwDocsDetail = new VwWpsDocsDetail();
         VWJPopupEditor popupEditor = new VWJPopupEditor(getParentWindow(),
                 "Work Product and Document Detail", vwDocsDetail, vwDocsDetail);

         vwDocsDetail.setParameter(dtoDocument);
         vwDocsDetail.refreshWorkArea();
         popupEditor.showConfirm();
   }

   public void setDtoDoc(DtoDocument dtoDoc) {
       super.setParameter(null);
       this.dtoDocument = dtoDoc;
   }


     //参数设置
     public void setParameter(Parameter param) {
         activityRid = (Long)param.get(DtoActivityKey.DTO_RID);
         param.put("docList",docslist);
         super.setParameter(param);
     }
}
