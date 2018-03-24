/*
 * Created on 2008-6-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.attendance;

import java.util.List;

import javax.swing.JButton;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import c2s.dto.DtoBase;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.report.DtoAttendanceQuery;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.view.VWTableWorkArea;
import client.essp.timesheet.common.LevelNodeViewManager;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;

import com.wits.util.Parameter;

public class VwAttendanceDown  extends VWTableWorkArea{
        private Object[][] configs = null;
        static final String actionIdQuery = "FTAttendanceQuery";
        private DtoAttendanceQuery dtoQuery = new DtoAttendanceQuery();
        public List queryList;
        public List resultList;
        public VwAttendanceDown() {
            try {
                jInit();
                addUICEvent();
            } catch (Exception ex) {
            }
        }

    //初始化
     public void jInit() throws Exception {
        VWJText text = new VWJText();
        VWJReal real = new VWJReal();
        real.setMaxInputDecimalDigit(2);
        configs = new Object[][] { 
                {"rsid.timesheet.dept","unitCode",VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.projId","projCode", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.empName","empName", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.empId","empId", VMColumnConfig.UNEDITABLE, text,Boolean.TRUE},
                {"rsid.timesheet.standardHours","fullHours",VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
                {"rsid.timesheet.actualHours", "actualHours", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
                {"rsid.timesheet.onProjectHours", "projActHours", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
                {"rsid.timesheet.onProjectRate", "inProjRate", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
                {"rsid.timesheet.personMath", "personMath", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
                {"rsid.timesheet.outProjectHours","outProjHours", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
                {"rsid.timesheet.sickLeave","sickLeave", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
                {"rsid.timesheet.privateLeave","privateLeave", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
                {"rsid.timesheet.absenteeism","absenteeism", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
                {"rsid.timesheet.othersDuduct","othersDuduct", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
                {"rsid.timesheet.deductSum","deductSum", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
                {"rsid.timesheet.shiftAdjsut","shiftAdjsut", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
                {"rsid.timesheet.annualLeave","annualLeave", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
                {"rsid.timesheet.marrigeLeave","marrigeLeave", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
                {"rsid.timesheet.funeralLeave","funeralLeave", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
                {"rsid.timesheet.maternityLeave","maternityLeave", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
                {"rsid.timesheet.lactationLeave","lactationLeave", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
                {"rsid.timesheet.workInjuryLeave","workInjuryLeave", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
                {"rsid.timesheet.medicalLeave","medicalLeave", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
                {"rsid.timesheet.othersNondeduct","othersNondeduct", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
                {"rsid.timesheet.nondeductSum","nondeductSum", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
        };
        super.jbInit(configs, DtoBase.class, new LevelNodeViewManager());
        //设置初始列宽
        JTableHeader header = this.getTable().getTableHeader();
        TableColumnModel tcModel = header.getColumnModel();
        //可排序
        this.getTable().setSortable(true);
       }
        //參數傳遞
        public void setParameter(Parameter param) {
         dtoQuery = (DtoAttendanceQuery) param.get(DtoAttendanceQuery.DTO_QUERY);
         super.setParameter(param);
       }

       //resetUI
       protected void resetUI() {
             if (validate(dtoQuery)) {
               InputInfo inputInfo = new InputInfo();
               inputInfo.setActionId(this.actionIdQuery);
               inputInfo.setInputObj(DtoAttendanceQuery.DTO_QUERY, dtoQuery);
               ReturnInfo returnInfo = accessData(inputInfo);
               if (returnInfo.isError() == false) {
                   resultList = (List) returnInfo.getReturnObj(DtoAttendanceQuery.DTO_RESULT_LIST);
                   this.getModel().setRows(resultList);
                   if (resultList != null && resultList.size() > 0) {
                       getTable().setSelectRow(0);
                   }
               }
              }
       }
    
       /**
        * 校验输入条件是否正确
        * @param dtoQuery
        * @return Boolean
        */
       private Boolean validate(DtoAttendanceQuery dtoQuery){
           if (dtoQuery.getBegin() == null && dtoQuery.getEnd() == null) {
               comMSG.dispErrorDialog("rsid.common.fill.begEnd");
               return false;
           }
           if(dtoQuery.getBegin()==null && dtoQuery.getEnd() != null){
               comMSG.dispErrorDialog("rsid.common.fill.begin");
               return false;
           }
           if(dtoQuery.getEnd()==null && dtoQuery.getBegin()!=null){
               comMSG.dispErrorDialog("rsid.common.fill.end");
               return false;
           }
           if (dtoQuery.getBegin().after(dtoQuery.getEnd())) {
               comMSG.dispErrorDialog("rsid.common.beginlessEnd");
               return false;
           }
           return true;
       }
       
       
       public void addUICEvent() {
//         Display
           TableColumnChooseDisplay chooseDisplay =
                   new TableColumnChooseDisplay(this.getTable(), this);
           JButton button = chooseDisplay.getDisplayButton();
           this.getButtonPanel().addButton(button);
         }

}
