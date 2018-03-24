package client.essp.timesheet.workscope;

import client.essp.common.view.VWTableWorkArea;
import client.essp.timesheet.ActivityChangedListener;

import java.util.List;
import client.framework.view.vwcomp.VWJText;
import client.framework.model.VMColumnConfig;
import java.awt.Dimension;
import com.wits.util.Parameter;
import c2s.essp.timesheet.code.DtoCodeValue;
import java.util.ArrayList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import c2s.essp.timesheet.code.DtoCodeKey;
import c2s.essp.timesheet.workscope.DtoAccount;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;

/**
 * <p>
 * Title:VwLeaveCodeList
 * </p>
 * 
 * <p>
 * Description: 假别卡片
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company: WistronITS
 * </p>
 * 
 * @author tbh
 * @version 1.0
 */
public class VwLeaveCodeList extends VWTableWorkArea {
	private List<ActivityChangedListener> activityChangedListeners = new ArrayList<ActivityChangedListener>();

	private final static String actionId_GetLeaveCodeList = "FTSLeaveCodeList";

	List leaveCodeList = new ArrayList();

	private Long leaveCodeTypeRid;

	public VwLeaveCodeList() {
		Object[][] configs = new Object[][] { { "rsid.common.name",
				"showLeaveCodeName", VMColumnConfig.UNEDITABLE, new VWJText() } };
		try {
			super.jbInit(configs, DtoCodeValue.class);
			// 不显示表头
			getTable().getTableHeader().setPreferredSize(new Dimension(100, 0));
		} catch (Exception ex) {
			log.error(ex);
		}
		addUICEvent();
	}

	/**
	 * 注册UI事件监听
	 */
	private void addUICEvent() {
		this.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()) {
					return;
				}
				fireactivityChanged(null);
			}
	   });
	}

	/**
	 * 设置参数
	 * 
	 * @param Parameter
	 */
	public void setParameter(Parameter p) {
		DtoAccount dtoAccount = (DtoAccount) p.get(DtoAccount.DTO_ACCOUNT);
		if(dtoAccount != null) {
			leaveCodeTypeRid = dtoAccount.getLeaveCodeTypeRid();
		} else {
			leaveCodeTypeRid = null;
		}
		super.setParameter(p);
	}

	/**
	 * 重置
	 */
	protected void resetUI() {
		InputInfo inputInfo = new InputInfo();
		inputInfo.setActionId(this.actionId_GetLeaveCodeList);
		inputInfo.setInputObj(DtoAccount.LEAVE_CODE_TYPE_RID, leaveCodeTypeRid);
		ReturnInfo returnInfo = accessData(inputInfo);

		if (returnInfo.isError() == false) {
			leaveCodeList = (List) returnInfo
					.getReturnObj(DtoCodeKey.CODE_LEAVE_LIST);
			if (leaveCodeList == null) {
				leaveCodeList = new ArrayList();
			}
			getTable().setRows(leaveCodeList);
		}
		if (leaveCodeList != null && leaveCodeList.size() > 0) {
			getTable().setSelectRow(0);
		}
		fireDataChanged();
	}

	public void addActivityChangedListener(ActivityChangedListener l) {
		activityChangedListeners.add(l);
	}

	private void fireactivityChanged(Long activityRid) {
		for (ActivityChangedListener l : activityChangedListeners) {
			l.processActivityChanged(activityRid);
		}
	}

}
