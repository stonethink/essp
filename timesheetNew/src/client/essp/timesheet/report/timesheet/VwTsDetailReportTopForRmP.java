package client.essp.timesheet.report.timesheet;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

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
public class VwTsDetailReportTopForRmP extends VwTsDetailReportTopBase{

    private DtoQueryCondition dtoQueryCondition;
    private final static String acntionId_GetDeptPersonList= "FTSGetDeptPersonList";
    private final static String acntionId_GetPersonsByDept = "FTSGetPersonsByDept";
    private Map relationMap;
    private VMComboBox allPersonItem;
    private boolean isPMO = false;

    public VwTsDetailReportTopForRmP() {
        jbInit();
        addUICEvent();
    }
    protected void jbInit() {
        super.jbInit();

        lblDept.setText("rsid.timesheet.dept");
        lblDept.setBounds(new Rectangle(230, 20, 85, 20));
        selectDept.setBounds(new Rectangle(310, 20, 217, 20));
        
        lblSelectChildren.setText("rsid.timesheet.includeSub");
        lblSelectChildren.setBounds(new Rectangle(540, 20, 80, 20));
        chekSelectChildren.setBounds(new Rectangle(615, 20, 20, 20));

        lblPerson.setText("rsid.timesheet.person");
        lblPerson.setBounds(new Rectangle(230, 60, 85, 20));
        selectPerson.setBounds(new Rectangle(310, 60, 217, 20));
        this.add(selectDept);
        this.add(lblDept);
        this.add(selectPerson);
        this.add(lblPerson);
        this.add(lblSelectChildren);
        this.add(chekSelectChildren);

    }
    protected DtoQueryCondition getDtoQueryCondition() {
        dtoQueryCondition = new DtoQueryCondition();
        dtoQueryCondition.setBegin((Date) inputBeginDate.getUICValue());
        dtoQueryCondition.setEnd((Date) inputEndDate.getUICValue());
        dtoQueryCondition.setLoginId((String) selectPerson.getUICValue());
        dtoQueryCondition.setQueryWay(DtoQueryCondition.DTO_QUERY_WAY_RMP);
        dtoQueryCondition.setDeptId((String) selectDept.getUICValue());
        VMComboBox item = (VMComboBox) selectPerson.getModel();
        dtoQueryCondition.setPersonItem(item.getObjectVector());
        dtoQueryCondition.setIncludeSub(chekSelectChildren.isSelected());
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
   * 事件处理
   */
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
		  if(selectPerson.getSelectedItem() != null) {
			  selectPerson.setSelectedIndex(0);
		  }
		  selectPerson.setEnabled(false);
	  } else {
		  selectPerson.setEnabled(true);
	  }
  }
  
  private void processDeptSelected() {
      String deptId = (String) selectDept.getUICValue();
      if(isPMO){
    	    InputInfo inputInfo = new InputInfo();
			inputInfo.setActionId(acntionId_GetPersonsByDept);
			inputInfo.setInputObj(DtoTsDetailReport.DTO_DEPT_ID, deptId);
			ReturnInfo returnInfo = this.accessData(inputInfo);
			if (!returnInfo.isError()) {
				Vector personItem = (Vector) returnInfo
						.getReturnObj(DtoTsDetailReport.DTO_PERSON_LIST);
				VMComboBox item = new VMComboBox(personItem);
				selectPerson.setModel(item);
			}
      } else {
    	  if ("".equals(deptId)) {
              selectPerson.setModel(allPersonItem);
          } else {
              if (relationMap.containsKey(deptId)) {
                  Vector relatedProjectItem = (Vector) relationMap.get(deptId);
                  VMComboBox item = new VMComboBox(relatedProjectItem);
                  selectPerson.setModel(item);
              } else {
                  VMComboBox item = new VMComboBox();
                  selectPerson.setModel(item);
              }
          }
      }
    }

    /**
    * 刷新界面
    */
   protected void resetUI() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(acntionId_GetDeptPersonList);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if (!returnInfo.isError()) {
            Vector deptItem = (Vector) returnInfo
                                 .getReturnObj(DtoTsDetailReport.DTO_DEPT_LIST);
            VMComboBox item = new VMComboBox(deptItem);
            selectDept.setModel(item);

            Vector personItem = (Vector) returnInfo
                                 .getReturnObj(DtoTsDetailReport.DTO_PERSON_LIST);
            allPersonItem = new VMComboBox(personItem);
            selectPerson.setModel(allPersonItem);

            relationMap = (Map) returnInfo
                                 .getReturnObj(DtoTsDetailReport.DTO_RELATION_MAP);
            isPMO = (Boolean) returnInfo.getReturnObj(DtoTsDetailReport.DTO_IS_PMO);
        }
        initData();
   }

}
