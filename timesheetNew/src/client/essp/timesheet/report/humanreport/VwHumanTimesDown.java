package client.essp.timesheet.report.humanreport;

import java.util.List;

import c2s.essp.timesheet.report.DtoHumanTimes;
import client.essp.common.view.VWTableWorkArea;
import client.essp.timesheet.common.LevelNodeViewManager;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.*;

import com.wits.util.Parameter;

public class VwHumanTimesDown extends VWTableWorkArea {
	private List resultList;
	
	public VwHumanTimesDown() {
		try {
			jbInit();
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	private void jbInit() throws Exception {
		VWJ2RealNumForReport twoReal = new VWJ2RealNumForReport();
		twoReal.setMaxInputDecimalDigit(2);
		twoReal.setMaxInputIntegerDigit(6);
		twoReal.setCanNegative(true);
		twoReal.setCanNegative2(true);
		twoReal.setCanInputSecondNum(true);
		VWJReal real = new VWJReal();
		real.setCanNegative(true);
		real.setMaxInputDecimalDigit(2);
        Object[][] configs = new Object[][] { {"rsid.timesheet.deptCode", "unitCode",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.TRUE}, {"rsid.timesheet.deptName", "unitName",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.FALSE}, {"rsid.timesheet.employeeId", "empId",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.TRUE}, {"rsid.timesheet.employeeName", "empName",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.FALSE}, {"rsid.timesheet.empProperty", "empProperty",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.FALSE}, {"rsid.timesheet.workStart", "beginDate",
                            VMColumnConfig.UNEDITABLE, new VWJDate(),
                            Boolean.FALSE}, {"rsid.timesheet.workEnd", "endDate",
                            VMColumnConfig.UNEDITABLE, new VWJDate(),
                            Boolean.FALSE}, {"rsid.timesheet.standardHours", "standarHours",
                            VMColumnConfig.UNEDITABLE, real,
                            Boolean.FALSE}, {"rsid.timesheet.actualHours", "actualHours",
                            VMColumnConfig.UNEDITABLE, real,
                            Boolean.FALSE}, {"rsid.timesheet.normalHours", "normalHours",
                            VMColumnConfig.UNEDITABLE, real,
                            Boolean.FALSE}, {"rsid.timesheet.overtimeHours", "overtimeHours",
                            VMColumnConfig.UNEDITABLE, real,
                            Boolean.FALSE}, {"rsid.timesheet.leaveHours", "leaveHours",
                            VMColumnConfig.UNEDITABLE, real,
                            Boolean.FALSE},{"rsid.timesheet.balance", "sumBalance",
                            VMColumnConfig.UNEDITABLE, twoReal,
                            Boolean.FALSE}
       };
       super.jbInit(configs, DtoHumanTimes.class, new LevelNodeViewManager());
       //可排序
       this.getTable().setSortable(true);
	}
	/**
     * 参数设置
     */
    public void setParameter(Parameter param) {
        super.setParameter(param);
        resultList = (List)param.get(DtoHumanTimes.DTO_QUERY_LIST);
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

}
