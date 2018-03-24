package client.essp.timesheet.activity.steps;

import javax.swing.table.TableColumnModel;
import client.framework.model.VMColumnConfig;
import javax.swing.table.JTableHeader;
import client.framework.view.vwcomp.VWJCheckBox;
import client.essp.common.view.VWTableWorkArea;
import client.framework.view.vwcomp.VWJText;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;

import java.util.ArrayList;
import java.util.List;
import c2s.essp.timesheet.activity.DtoActivityKey;
import c2s.dto.DtoBase;
import com.wits.util.Parameter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import c2s.essp.timesheet.activity.DtoActivityStep;
import client.framework.view.common.comMSG;
import client.framework.view.event.DataChangedListener;
import client.framework.view.vwcomp.VWUtil;
/**
 * <p>Title: VwStepsLeftBench</p>
 *
 * <p>Description:StepsList卡片 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author TBH
 * @version 1.0
 */
public class VwStepsLeftBench extends VWTableWorkArea
        implements DataChangedListener{
    static final String actionIdInit = "FTSActivityStepList";
    static final String actionIdUpdate="FTSUpdateStepList";
    Object[][] configs = null;
    JButton btnSave = new JButton();
    JButton btnFresh = new JButton();
    private List steplist = null;
    VWJCheckBox checkBox = new VWJCheckBox();
    private Long activityRid = null;
    public VwStepsLeftBench() {
        try {
            JInit();
            addUIEvent();
        } catch (Exception e) {
            log.error(e);
        }
    }
    //初始化页面
    private void JInit() throws Exception {
        configs = new Object[][] { {"rsid.common.name", "name",
                  VMColumnConfig.UNEDITABLE, new VWJText()}, {"rsid.timesheet.completed",
                  "isCompleted",
                  VMColumnConfig.EDITABLE, checkBox},
        };

        super.jbInit(configs, DtoBase.class);
        //设置初始列宽
        JTableHeader header = this.getTable().getTableHeader();
//      可排序
        this.getTable().setSortable(true);
        TableColumnModel tcModel = header.getColumnModel();
        tcModel.getColumn(0).setPreferredWidth(200);
    }

    //事件处理
    public void addUIEvent() {
        //保存
        btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });
        btnSave.setToolTipText("rsid.common.save");
        
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

    //实行保存操作
    private void actionPerformedSave(ActionEvent e) {
        if (checkModified()) {
            saveData();
        }
    }

   //检测是否数据有改变
   private boolean checkModified() {
       for (int i = 0; i < steplist.size(); i++) {
           DtoActivityStep dtoStep = (DtoActivityStep) steplist.get(i);
           if (dtoStep.isChanged()) {
               return true;
           }
       }
       return false;
   }

   //保存
   private void saveData() {
       InputInfo inputInfo = new InputInfo();
       inputInfo.setActionId(this.actionIdUpdate);
       inputInfo.setInputObj(DtoActivityKey.STEP_LIST, steplist);
       ReturnInfo returnInfo = accessData(inputInfo);
       if (returnInfo.isError() == false) {
           List newlist = (List) returnInfo.getReturnObj(
                   DtoActivityKey.STEP_LIST);
           getTable().setRows(newlist);
           if (newlist != null && newlist.size() > 0) {
               getTable().setSelectRow(0);
           }
           this.fireDataChanged();
           comMSG.dispMessageDialog("rsid.common.saveComplete");
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
       inputInfo.setInputObj(DtoActivityKey.DTO_RID,activityRid);
       ReturnInfo returnInfo = accessData(inputInfo);
       if (returnInfo.isError() == true) {
           return;
       }
       
       steplist = (List) returnInfo.getReturnObj(DtoActivityKey.STEP_LIST);
       Boolean isPR = (Boolean)returnInfo.getReturnObj(DtoActivityKey.DTO_ISPRIMARY_RESOURCE);
       if (steplist != null && steplist.size() > 0) {
           this.getModel().getColumnConfigs().clear();
           if(!isPR){
               configs = new Object[][] { {"rsid.common.name", "name",
                   VMColumnConfig.UNEDITABLE, new VWJText()}, {"rsid.timesheet.completed",
                   "isCompleted",
                   VMColumnConfig.UNEDITABLE, checkBox},
             };
               btnSave.setEnabled(false);
           }else{
               configs = new Object[][] { {"rsid.common.name", "name",
                   VMColumnConfig.UNEDITABLE, new VWJText()}, {"rsid.timesheet.completed",
                   "isCompleted",
                   VMColumnConfig.EDITABLE, checkBox},
             }; 
               btnSave.setEnabled(true);
           }
           this.getModel().setColumnConfigs(configs);
           getTable().setRows(steplist);
           getTable().setSelectRow(0);
       } else {
    	   //add by lipengxu, 清空列表
           getTable().setRows(new ArrayList());
           btnSave.setEnabled(false);
       }
   }

   //设置参数
   public void setParameter(Parameter param) {
       activityRid = (Long)param.get(DtoActivityKey.DTO_RID);
       super.setParameter(param);
   }

   public void processDataChanged() {
       super.fireDataChanged();
   }
}
