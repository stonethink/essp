package client.essp.timesheet.rmmaint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.JButton;

import com.wits.util.Parameter;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.rmmaint.DtoRmMaint;
import client.essp.common.humanAllocate.HrAllocate;
import client.essp.common.view.VWTableWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJText;

/**
 * @author wenhaizheng
 *
 */
public class VwRmMaintList extends VWTableWorkArea {
	
	private JButton searchBtn = new JButton(); 
	private String userLoginIds = "";
	private List userList;
	
	private final static String actionId_GetUserInfo = "FTSGetUserInfo";
	private final static String actionId_GetUserList = "FTSGetUserList";
	private final static String actionId_DelRM = "FTSDelRM";
	public VwRmMaintList() {
		try {
			jbInit();
		} catch (Exception e) {
			log.error(e);
		}
		addUICEvent();
	}
	private void jbInit() throws Exception {
		Object[][] configs = new Object[][] {
				{ "rsid.timesheet.empId", "loginId",  
					    VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.FALSE},
				{ "rsid.timesheet.empName", "name",
						VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.FALSE },
				{ "rsid.timesheet.dept", "dept",
						VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.FALSE }};
		super.jbInit(configs,  DtoRmMaint.class);
//      可排序
        this.getTable().setSortable(true);
	}
	/**
	 * 添加事件
	 *
	 */
	private void addUICEvent() {
		searchBtn = this.getButtonPanel().addButton("humanAllocate.gif");
		searchBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				processQuery();
			}
		});
        searchBtn.setToolTipText("rsid.common.allocate");
		
	}
	/**
	 * 查询操作
	 *
	 */
	private void processQuery() {
		HrAllocate hrAllocate = new HrAllocate();
        hrAllocate.setTitle("Get user for RM Maint");
        hrAllocate.allocMultiple();
        userLoginIds = hrAllocate.getNewUserIds();
//        comMSG.dispMessageDialog("Add Person");
//        userLoginIds= "WH0607015,WH0607016";
        if(userLoginIds != null && !"".equals(userLoginIds)){
        	addPerson();
        }
	}
	private void addPerson(){
		InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_GetUserInfo);
        inputInfo.setInputObj(DtoRmMaint.DTO_LOGINIDS, userLoginIds);
        ReturnInfo returnInfo = accessData(inputInfo);
        List<DtoRmMaint> list = null;
        if(!returnInfo.isError()) {
        	list = (List) returnInfo.getReturnObj(DtoRmMaint.DTO_RESULTS);
        }
        addToList(list);
	}
	private void addToList(List<DtoRmMaint> list) {
		if(list == null || list.size() == 0) {
			return;
		}
		Map loginIdMap = new HashMap();
		int rows = this.getTable().getRowCount();
		DtoRmMaint dtoRmMaint = null;
        for (int i = 0;i<rows;i++) {
        	dtoRmMaint = (DtoRmMaint)this.getModel().getRow(i);
        	loginIdMap.put(dtoRmMaint.getLoginId(), dtoRmMaint.getLoginId());
        }
        
		for(DtoRmMaint dto : list) {
			if(!loginIdMap.containsKey(dto.getLoginId())){
//				加到最后一行
				this.getTable().addRow(dto);
		        this.getTable().setSelectRow(this.getTable().getRowCount() - 1);
			}
		}
	}
	public void processDel() {
		int op = comMSG.dispConfirmDialog("error.client.VwRmMaint.confirmDel");
		if(op != Constant.OK) {
			return;
		}
		 DtoRmMaint dtoRmMaint = (DtoRmMaint) this.getTable().getSelectedData();
		InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_DelRM);
        inputInfo.setInputObj(DtoRmMaint.DTO_LOGINID, 
        							dtoRmMaint.getLoginId());
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()) {
        	this.getTable().deleteRow();
        }
	}
	 /**
     * 激活刷新
     */
    public void setParameter(Parameter param) {
        super.setParameter(param);
    }
	/**
	 * 刷新界面
	 */
	protected void resetUI() {
		InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_GetUserList);
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()) {
        	userList = (List) returnInfo.getReturnObj(DtoRmMaint.DTO_RESULTS);
        }
        this.getModel().setRows(userList);
        if(userList != null && userList.size() > 0) {
        	this.getTable().setSelectRow(0);
        }
	}
	
}
