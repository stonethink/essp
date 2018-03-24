package client.essp.timesheet.workscope;

import client.essp.common.view.VWTableWorkArea;
import client.essp.timesheet.ActivityChangedListener;
import java.util.List;
import client.framework.view.vwcomp.VWJText;
import client.framework.model.VMColumnConfig;
import java.awt.Dimension;
import com.wits.util.Parameter;
import c2s.essp.timesheet.activity.DtoActivityKey;
import c2s.essp.timesheet.code.DtoCodeValue;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.code.DtoCodeKey;
import c2s.dto.InputInfo;
import c2s.essp.timesheet.workscope.DtoAccount;
import c2s.essp.timesheet.workscope.DtoActivity;

/**
 * <p>Title: VwJobCodeList</p>
 *
 * <p>Description:JobCodeList卡片</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public class VwJobCodeList extends VWTableWorkArea {
	private List<ActivityChangedListener> activityChangedListeners
    = new ArrayList<ActivityChangedListener>();
   private List jobCodeList = new ArrayList();
   private final static String actionId_GetJobCodeList = "FTSJobCodeList";
   private Long codeTypeRid = null;
   private List activityList = new ArrayList();
   
   public VwJobCodeList() {
       Object[][] configs = new Object[][] { {"rsid.common.name",
                            "showLeaveCodeName", VMColumnConfig.UNEDITABLE,
                            new VWJText()}
       };
       try {
           super.jbInit(configs, DtoCodeValue.class);
           //不显示表头
           getTable().getTableHeader().setPreferredSize(new Dimension(100, 0));
       } catch (Exception ex) {
           log.error(ex);
       }
       addUICEvent();
   }
   
   /**
    * 注册UI事件监听
    */
   private void addUICEvent() {
	   this.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()) {
					return;
				}
				fireactivityChanged(null);
			}
	   });
   }
   
   /**
    * 参数设置
    * @param p Parameter
    */
   public void setParameter(Parameter p) {
       DtoAccount dtoAccount = (DtoAccount)p.get(DtoAccount.DTO_ACCOUNT);
       activityList = (List) p.get(DtoActivityKey.ACTIVITY_LIST);
       if(dtoAccount != null) {
    	   codeTypeRid = dtoAccount.getCodeTypeRid();
       } else {
    	   codeTypeRid = null;
       }
       super.setParameter(p);
   }
   /**
    * 重置
    */
   protected void resetUI() {
       InputInfo inputInfo = new InputInfo();
       inputInfo.setActionId(this.actionId_GetJobCodeList);
       inputInfo.setInputObj(DtoAccount.CODE_TYPE_RID,codeTypeRid);
       ReturnInfo returnInfo = accessData(inputInfo);

       if (returnInfo.isError() == false) {
           jobCodeList = (List) returnInfo.
                         getReturnObj(DtoCodeKey.CODE_Type_LIST);
           if(jobCodeList == null) {
               jobCodeList = new ArrayList();
           }
       }
       if (jobCodeList != null && jobCodeList.size() > 0) {
           getTable().setSelectRow(0);
           resetActivityJobCodes();
       }
       getTable().setRows(jobCodeList);
       fireDataChanged();
   }
   
   /**
    * 为Activity 的作业分类码设置本系统的Rid和ShowName
    *
    */
   private void resetActivityJobCodes() {
	   if(activityList == null || activityList.size() < 1) {
		   return;
	   }
	   if(jobCodeList == null || jobCodeList.size() < 1) {
		   return;
	   }
	   for(int i = 0; i < activityList.size(); i ++) {
		   DtoActivity activity = (DtoActivity)activityList.get(i);
		   String codeName = activity.getCodeValueName();
		   if(codeName == null || "".equals(codeName.trim())) {
			   continue;
		   }
		   for(int j = 0; j < jobCodeList.size(); j ++) {
			   DtoCodeValue code = (DtoCodeValue)jobCodeList.get(j);
			   if(code != null && code.getName().equalsIgnoreCase(codeName)) {
				   activity.setCodeValueRid(code.getRid());
				   activity.setCodeShowName(code.getShowLeaveCodeName());
				   break;
			   }
		   }
	   }
   }
   
   public void addActivityChangedListener(ActivityChangedListener l) {
       activityChangedListeners.add(l);
   }

   private void fireactivityChanged(Long activityRid) {
       for(ActivityChangedListener l : activityChangedListeners) {
           l.processActivityChanged(activityRid);
       }
   }

}
