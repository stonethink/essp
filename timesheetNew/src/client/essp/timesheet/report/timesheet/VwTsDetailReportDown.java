package client.essp.timesheet.report.timesheet;

import java.util.List;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.report.DtoQueryCondition;
import c2s.essp.timesheet.report.DtoTsDetailReport;
import client.essp.common.view.VWTableWorkArea;
import client.essp.timesheet.common.LevelNodeViewManager;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;

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
public class VwTsDetailReportDown extends VWTableWorkArea{
    private final static String acntionId_Query="FTSQueryTsDetailReport";
    private DtoQueryCondition dtoQueryCondition;
    private List resultList;
    public VwTsDetailReportDown() {
        try {
           jbInit();
       } catch (Exception e) {
           log.error(e);
       }
    }
    private void jbInit() throws Exception {
    	VWJReal real = new VWJReal();
        real.setMaxInputDecimalDigit(2);
        Object[][] configs = new Object[][] { {"rsid.timesheet.date", "workDate",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.FALSE}, {"rsid.timesheet.projectId", "projectId",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.FALSE}, {"rsid.timesheet.projectName", "projectName",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.TRUE}, {"rsid.timesheet.deptId", "deptId",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.FALSE}, {"rsid.timesheet.empId", "loginId",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.TRUE}, {"rsid.timesheet.empName", "name",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.FALSE}, {"rsid.timesheet.jobCode", "jobCode",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.FALSE}, {"rsid.timesheet.jobDesc", "jobDesc",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.TRUE}, {"rsid.timesheet.normalHours", "normalWorkHours",
                            VMColumnConfig.UNEDITABLE, real,
                            Boolean.FALSE}, {"rsid.timesheet.leaveHours", "leaveHours",
                            VMColumnConfig.UNEDITABLE, real,
                            Boolean.FALSE}, {"rsid.timesheet.overTimeHours", "overTimeHours",
                            VMColumnConfig.UNEDITABLE, real,
                            Boolean.FALSE}, {"rsid.timesheet.total", "total",
                            VMColumnConfig.UNEDITABLE, real,
                            Boolean.FALSE}
       };
       super.jbInit(configs, DtoTsDetailReport.class, new LevelNodeViewManager());
//     可排序
       this.getTable().setSortable(true);
    }
    /**
     * 参数设置
     */
    public void setParameter(Parameter param) {
        super.setParameter(param);
        dtoQueryCondition = (DtoQueryCondition) param.get(DtoTsDetailReport.DTO_CONDITION);
    }
    /**
    * 刷新界面
    */
   protected void resetUI() {
       InputInfo inputInfo = new InputInfo();
       inputInfo.setActionId(acntionId_Query);
       inputInfo.setInputObj(DtoTsDetailReport.DTO_CONDITION, dtoQueryCondition);
       ReturnInfo returnInfo = this.accessData(inputInfo);
       if(returnInfo.isError()){
           return;
       }
       resultList = (List) returnInfo.getReturnObj(DtoTsDetailReport.DTO_QUERY_RESULT);
       this.getModel().setRows(resultList);
        if(resultList != null && resultList.size() > 0) {
            this.getTable().setSelectRow(0);
        }
   }

}
