package client.essp.timesheet.activity.resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import c2s.dto.DtoBase;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.activity.DtoActivityKey;
import client.essp.common.view.VWTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWUtil;

import com.wits.util.Parameter;
/**
 * <p>Title: VwResourceLeftBench</p>
 *
 * <p>Description:ResourceList卡片 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author TBH
 * @version 1.0
 */
public class VwResourceLeftBench extends VWTableWorkArea{
    private Long activityRid = null;
    JButton btnFresh = new JButton();
    public Boolean isPrimaryResource = false;
    static final String actionIdInit = "FTSAssignedResourceList";
    Object[][] configs = null;
    static final String treeColumnName = "resource";

    public VwResourceLeftBench() {
      try {
          jbInit();
          addUICEvent();
      } catch (Exception e) {
          log.error(e);
      }
  }
     //初始化
     private void jbInit() throws Exception{
      VWJReal suplusDisp = new VWJReal();
      VWJText text = new VWJText();
      suplusDisp.setMaxInputDecimalDigit(2);
      suplusDisp.setMaxInputIntegerDigit(3);
      configs = new Object[][] { {"rsid.timesheet.resourceName", "resourceName",
                 VMColumnConfig.UNEDITABLE, text},
                 {"rsid.timesheet.remainingUnit/Time", "remainingUnits",
                 VMColumnConfig.UNEDITABLE, suplusDisp}
                 , {"rsid.timesheet.roleID", "roleId", VMColumnConfig.UNEDITABLE,
                 text},{"rsid.timesheet.primary", "primary", VMColumnConfig.UNEDITABLE,text},
                 {"rsid.timesheet.resourceType", "resourceType", VMColumnConfig.UNEDITABLE,text},
                 {"rsid.timesheet.start", "startDate",
                 VMColumnConfig.UNEDITABLE, new VWJDate()},
                 {"rsid.timesheet.finish", "finishDate",
                 VMColumnConfig.UNEDITABLE, new VWJDate()},
       };
        super.jbInit(configs,DtoBase.class);
                //设置初始列宽
        JTableHeader header = this.getTable().getTableHeader();
        this.getTable().setSortable(true);
        TableColumnModel tcModel = header.getColumnModel();
        tcModel.getColumn(0).setPreferredWidth(80);
        tcModel.getColumn(1).setPreferredWidth(50);
        tcModel.getColumn(2).setPreferredWidth(50);
        tcModel.getColumn(3).setPreferredWidth(50);
        tcModel.getColumn(4).setPreferredWidth(50);
        tcModel.getColumn(5).setPreferredWidth(50);
        tcModel.getColumn(6).setPreferredWidth(70);

    }
     
     private void addUICEvent() {
         //刷新
         btnFresh = this.getButtonPanel().addButton("refresh.png");
         btnFresh.setToolTipText("rsid.common.refresh");
         btnFresh.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 actionPerformedFefrsh(e);
             }
         });
     }
     
     private void actionPerformedFefrsh(ActionEvent e){
         resetUI();
     }
     
    //参数设置
    public void setParameter(Parameter param) {
        activityRid = (Long)param.get(DtoActivityKey.DTO_RID);
        super.setParameter(param);
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
        List resourceAssignlist = (List)returnInfo.getReturnObj(DtoActivityKey.RESOURCE_LIST);
        isPrimaryResource = (Boolean)returnInfo.getReturnObj(DtoActivityKey.DTO_ISPRIMARY_RESOURCE);
        getTable().setRows(resourceAssignlist);
        if(resourceAssignlist != null && resourceAssignlist.size() > 0) {
            getTable().setSelectRow(0);
        } else {
       	 //add by lipengxu 071224, 清空列表
       	 this.getTable().setRows(new ArrayList());
        }
    }
}
