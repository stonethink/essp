package client.essp.timesheet.report.humanreport;

import java.util.*;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.report.DtoHumanReport;
import client.essp.common.view.VWTableWorkArea;
import client.essp.timesheet.common.LevelNodeViewManager;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.*;

import com.wits.util.Parameter;

public class VwHumanReportDown extends VWTableWorkArea {
	private final static String acntionId_Query="FTSQueryHumanReport";
	private List resultList;
	
	public VwHumanReportDown() {
		try {
			jbInit();
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	private void jbInit() throws Exception {
		VWJReal real = new VWJReal();
		real.setCanNegative(true);
        real.setMaxInputDecimalDigit(2);
        Object[][] configs = new Object[][] { {"rsid.timesheet.projId", "projectId",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.FALSE}, {"rsid.timesheet.projName", "projectName",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.FALSE},{"rsid.timesheet.projType", "projectType",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.TRUE}, {"rsid.timesheet.projDeptId", "deptId",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.TRUE}, {"rsid.timesheet.projDept", "deptName",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.FALSE}, {"rsid.timesheet.employeeId", "empId",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.TRUE}, {"rsid.timesheet.employeeName", "empName",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.FALSE}, {"rsid.timesheet.empType", "empType",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.TRUE}, {"rsid.timesheet.empProperty", "empProperty",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.FALSE}, {"rsid.timesheet.workStart", "beginDate",
                            VMColumnConfig.UNEDITABLE, new VWJDate(),
                            Boolean.FALSE}, {"rsid.timesheet.workEnd", "endDate",
                            VMColumnConfig.UNEDITABLE, new VWJDate(),
                            Boolean.FALSE}, {"rsid.timesheet.actualHours", "actualHours",
                            VMColumnConfig.UNEDITABLE, real,
                            Boolean.FALSE}, {"rsid.timesheet.normalHours", "normalHours",
                            VMColumnConfig.UNEDITABLE, real,
                            Boolean.FALSE}, {"rsid.timesheet.overtimeHours", "overtimeHours",
                            VMColumnConfig.UNEDITABLE, real,
                            Boolean.FALSE}, {"rsid.timesheet.assignedHours", "assignedHours",
                            VMColumnConfig.UNEDITABLE, real,
                            Boolean.FALSE}
       };
       super.jbInit(configs, DtoHumanReport.class, new LevelNodeViewManager());
       //可排序
       this.getTable().setSortable(true);
	}
	/**
     * 参数设置
     */
    public void setParameter(Parameter param) {
        super.setParameter(param);
        resultList = (List)param.get(DtoHumanReport.DTO_QUERY_RESULT);
    }
    /**
     * 刷新界面
     */
    protected void resetUI() {
        this.getModel().setRows(resultList);
//         if(resultList != null && resultList.size() > 0) {
//             this.getTable().setSelectRow(0);
//         }
    }
    /**
     * 查询操作
     * @param begin
     * @param end
     * @param site
     * @return
     */
    public Map processQuery(Date begin, Date end, String site) {
    	Map resultMap = new HashMap();
    	InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(acntionId_Query);
        inputInfo.setInputObj(DtoHumanReport.DTO_BEGIN, begin);
        inputInfo.setInputObj(DtoHumanReport.DTO_END, end);
        inputInfo.setInputObj(DtoHumanReport.DTO_SITE, site);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if(returnInfo.isError()){
            return resultMap;
        }
        resultMap = (Map) returnInfo.getReturnObj(DtoHumanReport.DTO_QUERY_RESULT);
        return resultMap;
    }

}
