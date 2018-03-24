package client.essp.timesheet.report.timesheet;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.report.DtoQueryCondition;
import c2s.essp.timesheet.report.DtoTsDetailReport;
import client.framework.model.VMComboBox;
import com.wits.util.Parameter;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class VwTsDetailReportTopForPm extends VwTsDetailReportTopBase{
    private DtoQueryCondition dtoQueryCondition;
    private final static String acntionId_GetDeptProjectList = "FTSGetDeptProjectList";
    private final static String acntionId_GetProjectsByDept = "FTSGetProjectsByDept";
    private Map relationMap;
    private VMComboBox allProjectItem;
    private boolean isPMO = false;
    public VwTsDetailReportTopForPm() {
        jbInit();
        addUICEvent();
    }
    protected void jbInit() {
        super.jbInit();

        lblDept.setText("rsid.timesheet.dept");
        lblDept.setBounds(new Rectangle(230, 20, 85, 20));
        selectDept.setBounds(new Rectangle(315, 20, 217, 20));
        
        lblSelectChildren.setText("rsid.timesheet.includeSub");
        lblSelectChildren.setBounds(new Rectangle(540, 20, 80, 20));
        chekSelectChildren.setBounds(new Rectangle(615, 20, 20, 20));

        lblProject.setText("rsid.timesheet.project");
        lblProject.setBounds(new Rectangle(230, 60, 85, 20));
        selectProject.setBounds(new Rectangle(315, 60, 217, 20));

        this.add(lblDept);
        this.add(selectDept);
        this.add(lblProject);
        this.add(selectProject);
        this.add(lblSelectChildren);
        this.add(chekSelectChildren);
    }
    private void addUICEvent() {
        selectDept.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                processDeptSelected();
            }
        });
        chekSelectChildren.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			processClickChek(chekSelectChildren.isSelected());
    		}
          });
    }
    private void processClickChek(boolean includeSub) {
  	  if(includeSub) {
  		  if(selectProject.getSelectedItem() != null) {
  			selectProject.setSelectedIndex(0);
  		  }
  		  selectProject.setEnabled(false);
  	  } else {
  		  selectProject.setEnabled(true);
  	  }
    }
    
    private void processDeptSelected() {
    	String deptId = (String)selectDept.getUICValue();
    	if(isPMO) {
    		InputInfo inputInfo = new InputInfo();
			inputInfo.setActionId(acntionId_GetProjectsByDept);
			inputInfo.setInputObj(DtoTsDetailReport.DTO_DEPT_ID, deptId);
			ReturnInfo returnInfo = this.accessData(inputInfo);
			if (!returnInfo.isError()) {
				Vector projectItem = (Vector) returnInfo
						.getReturnObj(DtoTsDetailReport.DTO_PROJECT_LIST);
				VMComboBox item = new VMComboBox(projectItem);
				selectProject.setModel(item);
			}
    	} else {
    		if("".equals(deptId)) {
        		selectProject.setModel(allProjectItem);
        	} else {
        		if(relationMap.containsKey(deptId)) {
        			Vector relatedProjectItem = (Vector) relationMap.get(deptId);
        			VMComboBox item = new VMComboBox(relatedProjectItem);
        			selectProject.setModel(item);
        		} else {
        			VMComboBox item = new VMComboBox();
        			selectProject.setModel(item);
        		}
        	}
    	}
    }
     protected DtoQueryCondition getDtoQueryCondition() {
         dtoQueryCondition = new DtoQueryCondition();
         dtoQueryCondition.setBegin((Date) inputBeginDate.getUICValue());
         dtoQueryCondition.setEnd((Date) inputEndDate.getUICValue());
         dtoQueryCondition.setQueryWay(DtoQueryCondition.DTO_QUERY_WAY_PM);
         dtoQueryCondition.setProjectId((String)selectProject.getUICValue());
         dtoQueryCondition.setDeptId((String)selectDept.getUICValue());
         VMComboBox item = (VMComboBox) selectProject.getModel();
         dtoQueryCondition.setProjectItem(item.getObjectVector());
         dtoQueryCondition.setIncludeSub(chekSelectChildren.isSelected());
         dtoQueryCondition.setPmo(isPMO);
         return dtoQueryCondition;

     }
  /**
   * 激活刷新
   */
  public void setParameter(Parameter param) {
      super.setParameter(param);
      this.refreshWorkArea();
  }

   /**
     * 刷新界面
     */
    protected void resetUI() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(acntionId_GetDeptProjectList);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if (!returnInfo.isError()) {
            Vector deptItem = (Vector) returnInfo
                                 .getReturnObj(DtoTsDetailReport.DTO_DEPT_LIST);
            VMComboBox item = new VMComboBox(deptItem);
            selectDept.setModel(item);

            Vector projectItem = (Vector) returnInfo
                                 .getReturnObj(DtoTsDetailReport.DTO_PROJECT_LIST);
            allProjectItem = new VMComboBox(projectItem);
            selectProject.setModel(allProjectItem);

            relationMap = (Map) returnInfo
                                 .getReturnObj(DtoTsDetailReport.DTO_RELATION_MAP);
            isPMO = (Boolean) returnInfo.getReturnObj(DtoTsDetailReport.DTO_IS_PMO);
        }
        initData();
    }

}
