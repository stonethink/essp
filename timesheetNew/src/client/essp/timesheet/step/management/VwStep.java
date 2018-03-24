package client.essp.timesheet.step.management;

import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import c2s.essp.timesheet.step.management.DtoStep;
import client.essp.common.view.VWTDWorkArea;
import client.framework.view.vwcomp.VWUtil;

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
public class VwStep extends VWTDWorkArea {

	VwStepList stepList;
	private VwStepDesciption desc;
	DtoStep step;
	String projectObjectId;

	public VwStep() {
		super(600);
		this.setHorizontalSplit();
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
		stepList = new VwStepList();
		desc = new VwStepDesciption();
		this.getTopArea().addTab("rsid.timesheet.tab.workItemList", stepList);
		this.getDownArea().addTab("rsid.timesheet.tab.description", desc);
	}

	/**
	 * 事件处理
	 */
	private void addUICEvent() {
		stepList.getTable().getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {

						if (e.getValueIsAdjusting()) {
							VWUtil.convertUI2Dto(step, desc);
							return;
						}
						step = (DtoStep) stepList.getTable().getSelectedData();
						if (step == null) {
							return;
						}
						Parameter param = new Parameter();
						param.put("STEP", step);
						desc.setParameter(param);
						desc.refreshWorkArea();
					}

				});
	}

	/**
	 * 激活刷新
	 * 
	 * @param param
	 *            Parameter
	 */
	public void setParameter(Parameter param) {
		stepList.setParameter(param);

	}

	public void refreshWorkArea() {
		stepList.refreshWorkArea();
	}

}
