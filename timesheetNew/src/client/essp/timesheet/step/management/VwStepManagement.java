package client.essp.timesheet.step.management;

import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import c2s.essp.timesheet.account.DtoAccount;
import c2s.essp.timesheet.activity.DtoActivityKey;
import c2s.essp.timesheet.step.management.DtoActivityForStep;
import client.essp.common.view.VWTDWorkArea;
import client.essp.timesheet.activity.VwActivityDetail;
import client.framework.view.vwcomp.VWJComboBox;

import com.wits.util.Parameter;

/**
 * <p>
 * Title: View of Step Management
 * </p>
 * 
 * <p>
 * Description: 项目视图：包含Activity List，Step List卡片
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
 * @author Robin
 * @version 1.0
 */
public class VwStepManagement extends VWTDWorkArea {

	private VwActivityList vwActivityList;
	private VwActivityDetail vwDetail;
	VWJComboBox acntList = new VWJComboBox();
	private JButton saveBtn;

	public VwStepManagement() {
		super(300);
		try {
			jbInit();
		} catch (Exception e) {
			log.error(e);
		}
		addUICEvent();
	}

	/**
	 * 界面初始化
	 */
	private void jbInit() {
		vwActivityList = getVwActivityList();
		vwDetail = new VwActivityDetail();
		this.getTopArea().addTab("rsid.timesheet.tab.activity", vwActivityList);
		this.setDownArea(vwDetail);
	}
	
	/**
	 * 获取VwActivityList
	 * @return VwActivityList
	 */
	protected VwActivityList getVwActivityList() {
		return new VwActivityList();
	}

	/**
	 * 事件处理
	 */
	private void addUICEvent() {
		vwActivityList.getTable().getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if(e.getValueIsAdjusting()) {
							return;
						}
						DtoActivityForStep dto = (DtoActivityForStep) vwActivityList
								.getTable().getSelectedData();
						
						Parameter param=new Parameter();
						if(dto != null){
							param.put(DtoActivityKey.DTO_RID, new Long(dto.getId()));
						}
						param.put(DtoActivityKey.STEP_MODE, Boolean.TRUE);
						vwDetail.setParameter(param);
						vwDetail.getSelectedWorkArea().refreshWorkArea();
					}
				});
		vwActivityList.layoutChooseDisplay.addLayoutListener(vwDetail.getTimeCardStep().stepList.layoutChooseDisplay);
		vwDetail.getTimeCardStep().stepList.layoutChooseDisplay.addLayoutListener(vwActivityList.layoutChooseDisplay);
	}


	protected void resetUI() {
	}

	/**
	 * 激活刷新
	 * 
	 * @param param
	 *            Parameter
	 */
	public void setParameter(Parameter param) {
		vwActivityList.setParameter(param);
	}

	public void refreshWorkArea() {
		vwActivityList.refreshWorkArea();
	}

	private void processSelectedProjectChanged() {
		DtoAccount dtoProject = (DtoAccount) vwActivityList.getTable()
				.getSelectedData();
		Long projectRid = null;
		if (dtoProject != null) {
			projectRid = dtoProject.getRid();
			saveBtn.setEnabled(true);
		} else {
			saveBtn.setEnabled(false);
		}
		Parameter param = new Parameter();
		param.put(DtoAccount.DTO, dtoProject);
		// vwAccountGeneral.setParameter(param);
		param = new Parameter();
		param.put(DtoAccount.DTO_RID, projectRid);
		this.getDownArea().getSelectedWorkArea().refreshWorkArea();
	}

}
