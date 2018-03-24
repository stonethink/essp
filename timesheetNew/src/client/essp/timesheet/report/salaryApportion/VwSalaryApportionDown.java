/*
 * Created on 2008-1-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.salaryApportion;

import java.util.List;
import javax.swing.JButton;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import c2s.dto.DtoBase;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.report.DtoSalaryWkHrQuery;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.view.VWTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;

public class VwSalaryApportionDown extends VWTableWorkArea{
        static final String actionIdQuery = "FTSSalaryWkHrQuery";
        DtoSalaryWkHrQuery dtoQuery = new DtoSalaryWkHrQuery();
        public List salaryList;
    
        public VwSalaryApportionDown() {
            try {
                jbInit();
            } catch (Exception ex) {
                log.error(ex);
            }
            addUICEvent();
        }
    
        //初始化
        public void jbInit() throws Exception {
            Object[][] configs = null;
            VWJText text = new VWJText();
            VWJReal realInt = new VWJReal();
            realInt.setMaxInputDecimalDigit(0);
            VWJReal real = new VWJReal();
            real.setMaxInputDecimalDigit(2);
            configs = new Object[][] { {"rsid.timesheet.comId", "compId",
                      VMColumnConfig.EDITABLE, text,Boolean.FALSE},
                      {"rsid.timesheet.employeeId","empId",VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                      {"rsid.timesheet.employeeName","empName",VMColumnConfig.UNEDITABLE, text,Boolean.TRUE},
                      {"rsid.timesheet.projectId", "prjCode", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                      {"rsid.timesheet.projectName", "prjName", VMColumnConfig.UNEDITABLE, text,Boolean.TRUE},
                      {"rsid.timesheet.phaseId", "phaseCode", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                      {"rsid.timesheet.achieveBelong", "showAchieveBelong", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                      {"rsid.timesheet.year","year", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                      {"rsid.timesheet.month","month", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                      {"rsid.timesheet.workhours","workHours", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
                      {"rsid.timesheet.leaveHours","leaveHours", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
                      {"rsid.timesheet.overtimeHours","overtimeHours", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
            };
            super.jbInit(configs, DtoBase.class);
            //设置初始列宽
            JTableHeader header = this.getTable().getTableHeader();
    //      可排序
            this.getTable().setSortable(true);
            TableColumnModel tcModel = header.getColumnModel();
            tcModel.getColumn(0).setPreferredWidth(40);
            tcModel.getColumn(1).setPreferredWidth(40);
            tcModel.getColumn(2).setPreferredWidth(40);
            tcModel.getColumn(3).setPreferredWidth(60);
            tcModel.getColumn(4).setPreferredWidth(40);
            tcModel.getColumn(5).setPreferredWidth(40);
            tcModel.getColumn(6).setPreferredWidth(40);
            tcModel.getColumn(7).setPreferredWidth(40);
            tcModel.getColumn(8).setPreferredWidth(40);
        }
        
        private void addUICEvent() {
           
    //      Display
             TableColumnChooseDisplay chooseDisplay =
                     new TableColumnChooseDisplay(this.getTable(), this);
             JButton button = chooseDisplay.getDisplayButton();
             this.getButtonPanel().addButton(button);
        }
    
        
    
        //參數傳遞
        public void setParameter(Parameter param) {
         dtoQuery = (DtoSalaryWkHrQuery) param.get(DtoSalaryWkHrQuery.DTO_SALARY_QUERY);
         super.setParameter(param);
       }
       
       
       protected void resetUI() {      
               InputInfo inputInfo = new InputInfo();
               inputInfo.setActionId(actionIdQuery);
               inputInfo.setInputObj(DtoSalaryWkHrQuery.DTO_SALARY_QUERY, dtoQuery);
               ReturnInfo returnInfo = accessData(inputInfo);
               if (returnInfo.isError() == false) {
                   salaryList = (List) returnInfo.getReturnObj(DtoSalaryWkHrQuery.
                           DTO_QUERY_LIST);
                   this.getModel().setRows(salaryList);
                   if (salaryList != null && salaryList.size() > 0) {
                       getTable().setSelectRow(0);
                   }
               } else {
                   salaryList = null;
               }
       }
}
