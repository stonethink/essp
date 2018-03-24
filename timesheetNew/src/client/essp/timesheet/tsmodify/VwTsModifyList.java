package client.essp.timesheet.tsmodify;

import java.util.List;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.approval.DtoTsApproval;
import c2s.essp.timesheet.tsmodify.DtoTsModify;
import client.essp.common.loginId.VWJLoginId;
import client.essp.common.view.VWTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.*;

import com.wits.util.Parameter;

public class VwTsModifyList extends VWTableWorkArea {
	
	private final static String acntionId_Query="FTSQueryTsModifyList"; 
	
	private DtoTsModify dto;
	private List resultList;
	
	public VwTsModifyList() {
		try {
            jbInit();
        } catch (Exception e) {
            log.error(e);
        }
        addUICEvent();
	}
	/**
     * 初始化UI
     * @throws Exception
     */
    protected void jbInit() throws Exception {
        Object[][] configs = new Object[][] { {"rsid.common.user", "loginId",
                             VMColumnConfig.UNEDITABLE, new VWJLoginId(),
                             Boolean.FALSE},{"rsid.timesheet.startDate", "startDate",
                             VMColumnConfig.UNEDITABLE, new VWJDate(),
                             Boolean.FALSE}, {"rsid.timesheet.finishDate", "finishDate",
                             VMColumnConfig.UNEDITABLE, new VWJDate(),
                             Boolean.FALSE}, {"rsid.timesheet.standardHours", "standarHours",
                             VMColumnConfig.UNEDITABLE, new VWJReal(),
                             Boolean.FALSE}, {"rsid.timesheet.actualHours", "actualHours",
                             VMColumnConfig.UNEDITABLE, new VWJReal(),
                             Boolean.FALSE}, {"rsid.timesheet.overtimeHours", "overtimeHours",
                             VMColumnConfig.UNEDITABLE, new VWJReal(),
                             Boolean.FALSE}, {"rsid.timesheet.leaveHours", "leaveHours",
                             VMColumnConfig.UNEDITABLE, new VWJReal(),
                             Boolean.FALSE}, {"rsid.common.status", "statusName",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.FALSE}
        };
        super.jbInit(configs, DtoTsApproval.class);
//      可排序
        this.getTable().setSortable(true);
    }
    public void addUICEvent() {
//    	 this.getTable().getSelectionModel().addListSelectionListener (new ListSelectionListener() {
//             public void valueChanged(ListSelectionEvent e) {
//             	if(e.getValueIsAdjusting()) {
//             		return;
//             	}
//             	if (getTable().getSelectedRowCount() >= 1) {
//             		dto = (DtoTsModify) getTable().getSelectedData();
//				}
//             }
//         });
    }
	public void setParameter(Parameter param) {
		super.setParameter(param);
		dto = (DtoTsModify) param.get(DtoTsModify.DTO_CONDITION);
	}
	/**
     * 刷新界面
     */
    protected void resetUI() {
    	if(dto == null) {
    		return;
    	}
    	InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(acntionId_Query);
        inputInfo.setInputObj(DtoTsModify.DTO_CONDITION, dto);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if(returnInfo.isError()){
            return;
        }
        resultList = (List) returnInfo.getReturnObj(DtoTsModify.DTO_QUERY_RESULT);
        this.getTable().setRows(resultList);
         if(resultList != null && resultList.size() > 0) {
             this.getTable().setSelectRow(0);
         }
    }
}
