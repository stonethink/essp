package client.essp.timesheet.step.management;

import java.awt.event.ActionEvent;

import client.essp.common.view.VWTDWorkArea;
import client.framework.view.vwcomp.IVWPopupEditorEvent;

import com.wits.util.Parameter;

/**
 * <p>
 * Title: Step Description
 * </p>
 * 
 * <p>
 * Description: Step's Description
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * 
 * <p>
 * Company: WistronITS
 * </p>
 * 
 * @author Robin
 * @version 1.0
 */
public class VwApplyTemplate extends VWTDWorkArea implements
		IVWPopupEditorEvent {

	VwTemplate temp = new VwTemplate();
	VwTemplateStepList detail;
	VwStepList stepList;
	public VwApplyTemplate() {
		super(100);
		try {
			jbInit();
		} catch (Exception e) {
			log.error(e);
		}
		addUICEvent();

	}

	/**
	 * 初始化界面
	 * 
	 * @throws Exception
	 */
	private void jbInit() throws Exception {
		this.getTopArea().add(temp);
		detail = new VwTemplateStepList();
		temp.setTemplateStepList(detail);
		this.getDownArea().addTab("rsid.timesheet.tab.stepList", detail);
	}

	/**
	 * 事件处理
	 */
	private void addUICEvent() {

	}

	protected void resetUI() {

	}

	public void setParameter(Parameter param) {
		super.setParameter(param);
		stepList=(VwStepList)param.get("StepListVM");
		temp.setParameter(param);
		temp.refreshWorkArea();
		param.put("TemplateId", temp.getCurrentTemplateId());
		detail.setParameter(param);
	}

	public boolean onClickCancel(ActionEvent e) {
		return true;
	}

	public boolean onClickOK(ActionEvent e) {
		stepList.setTemplateList(detail.getModel().getRows());
		return true;
	}

}
