package client.essp.timesheet.activity;

import javax.swing.JButton;

import com.wits.util.Parameter;
import client.essp.timesheet.activity.general.VwGeneral;
import client.essp.timesheet.activity.resources.VwResource;
import client.essp.timesheet.activity.notebook.VwNoteBook;
import client.essp.timesheet.activity.steps.VwSteps;
import client.essp.timesheet.activity.relationships.VwRelationShips;
import client.essp.timesheet.activity.document.VwWpsDocs;
import client.essp.timesheet.step.management.VwStep;
import client.framework.view.event.DataChangedListener;
import client.essp.common.view.VWGeneralWorkArea;
import c2s.essp.timesheet.activity.DtoActivityKey;
/**
 * <p>Title: VwWeeklyReportFrame</p>
 *
 * <p>Description:VwWeeklyReportFrame卡片 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author TBH
 * @version 1.0
 */
public class VwActivityDetail extends VWGeneralWorkArea {
	private final static int GENERAL_TAB_INDEX = 0;
	private final static int STEP_TAB_INDEX = 3;
	VwStep vwTimeCardStep = null;
    VwGeneral vwGeneral = null;
    VwResource vwResource = null;
    VwNoteBook vwRecordBook = null;
    VwSteps vwStep = null;
    VwRelationShips vwLgRelation = null;
    VwWpsDocs vwWpsDocs =  null;
    private Long activityRid = null;

    public VwActivityDetail() {
       try {
           jbInit();
       } catch (Exception ex) {
       }
       addUICEvent();
   }

   //事件处理
   private void addUICEvent() {
       vwGeneral.addDataChangedListener(new DataChangedListener(){
       public void processDataChanged() {
          Parameter param = new Parameter();
          if (vwGeneral.inputIsStart.isSelected()
              && !vwGeneral.inputIsFinish.isSelected()) {
              param.put("IsStarted", "true");
          } else {
              param.put("IsStarted", "false");
          }
           param.put(DtoActivityKey.DTO_RID,activityRid);
           param.put(DtoActivityKey.DTO_ISPRIMARY_RESOURCE,
                   vwResource.isPrimaryResource);
           vwStep.setParameter(param);
           vwResource.setParameter(param);
           vwLgRelation.setParameter(param);
           vwWpsDocs.setParameter(param);
           vwGeneral.setParameter(param);
        }
      });
   }

    //初始化
     private void jbInit() throws Exception {
    	 
         vwGeneral = new VwGeneral();
         this.addTab("rsid.common.general", vwGeneral);

         vwResource = new VwResource();
         this.addTab("rsid.timesheet.resources", vwResource);

         vwRecordBook = new VwNoteBook();
         this.addTab("rsid.timesheet.noteBook",vwRecordBook);
         
         vwTimeCardStep = new VwStep();
    	 this.addTab("rsid.timesheet.tab.workItem", vwTimeCardStep);
    	 
         vwStep = new VwSteps();
         this.addTab("rsid.timesheet.steps",vwStep);

         vwLgRelation = new VwRelationShips();
         this.addTab("rsid.timesheet.relationships",vwLgRelation);

         vwWpsDocs = new VwWpsDocs();
         this.addTab("rsid.timesheet.wps&Docs",vwWpsDocs);
    }

    //参数设置
    public void setParameter(Parameter parameter) {
        Long activityRid = (Long)parameter.get(DtoActivityKey.DTO_RID);
        Boolean StepMode = (Boolean)parameter.get(DtoActivityKey.STEP_MODE);
        if (activityRid != null ) {
            this.enableAllTabs();
            if(activityRid.equals(this.activityRid) == false) {
            	super.setParameter(parameter);
            	vwGeneral.setParameter(parameter);
                vwResource.setParameter(parameter);
                vwRecordBook.setParameter(parameter);
                vwTimeCardStep.setParameter(parameter);
                vwStep.setParameter(parameter);
                vwLgRelation.setParameter(parameter);
                vwWpsDocs.setParameter(parameter);
            }
        } else {
        	if(StepMode == null || !StepMode) {
        		this.enableTabOnly(GENERAL_TAB_INDEX);
        		this.setSelectedIndex(GENERAL_TAB_INDEX);
        	} else {
        		this.enableTabOnly(STEP_TAB_INDEX);
        		this.setSelectedIndex(STEP_TAB_INDEX);
        	}
            if(this.activityRid != null) {
            	super.setParameter(parameter);
            	vwGeneral.setParameter(parameter);
                vwResource.setParameter(parameter);
                vwRecordBook.setParameter(parameter);
                vwTimeCardStep.setParameter(parameter);
                vwStep.setParameter(parameter);
                vwLgRelation.setParameter(parameter);
                vwWpsDocs.setParameter(parameter);
            }
        }
        this.activityRid = activityRid;
    }
    
    public VwStep getTimeCardStep() {
    	return this.vwTimeCardStep;
    }

    /* 
     * 刷新UI
	 */
	protected void resetUI() {
		this.getSelectedWorkArea().refreshWorkArea();
	}
}
