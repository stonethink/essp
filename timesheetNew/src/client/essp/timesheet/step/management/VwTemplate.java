package client.essp.timesheet.step.management;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Vector;

import c2s.dto.DtoComboItem;
import c2s.dto.InputInfo;
import c2s.essp.timesheet.step.management.DtoTemplate;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.model.VMComboBox;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJTextArea;

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
public class VwTemplate extends VWGeneralWorkArea {
	private String accountId;
	VWJLabel nameLable = new VWJLabel();
	VWJLabel descLable = new VWJLabel();
	List tempList;
	VWJComboBox tList = new VWJComboBox();
	private String currentTemplateId = "";
	boolean templateNeedInitFlag = true;
	VWJTextArea desc = new VWJTextArea();
	VwTemplateStepList templateStepList;

	public VwTemplate() {
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
		this.setLayout(null);
		nameLable.setText("rsid.timesheet.template");
		descLable.setText("rsid.timesheet.tab.description");
		nameLable.setLocation(10, 10);
		descLable.setLocation(10, 30);
		nameLable.setSize(100, 20);
		descLable.setSize(100, 20);
		tList.setSize(300, 20);
		desc.setSize(300, 60);
//		desc
		tList.setLocation(110, 10);
		desc.setLocation(110, 35);
		this.add(nameLable);
		this.add(tList);
		this.add(descLable);
		this.add(desc);
	}

	/**
	 * 事件处理
	 */
	private void addUICEvent() {
		tList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				String id = tList.getUICValue().toString();
				currentTemplateId=id.toString();
				Parameter param = new Parameter();
				param.put("TemplateId", id);
				templateStepList.setParameter(param);
				setDescConent();
			}
		});
	}

	protected void resetUI() {
		InputInfo acntInput = new InputInfo();
		acntInput.setInputObj("AccountId", accountId);
		acntInput.setActionId("FTSListTemplate");
		tempList = (List) this.accessData(acntInput).getReturnObj(
				DtoTemplate.KEY_TEMPLATE_LIST);
		Vector tempVector = new Vector();
		for (int i = 0;tempList != null && i < tempList.size(); i++) {
			DtoTemplate t = (DtoTemplate) tempList.get(i);
			DtoComboItem item = new DtoComboItem(t.getTemplateCode() + "--"
					+ t.getTemplateName(), t.getRid().toString());
			tempVector.add(item);
		}

		tList.setModel(new VMComboBox(tempVector));
		currentTemplateId = (String) tList.getUICValue();
		setDescConent();

	}

	private void setDescConent() {
		for (int i = 0;tempList != null && i < tempList.size(); i++) {
			DtoTemplate t = (DtoTemplate) tempList.get(i);
			if (t.getRid().toString().equals(currentTemplateId)) {
				desc.setText(t.getDescription());
				return;
			}
		}
	}

	public void setParameter(Parameter param) {
		accountId = (String) param.get("AccountId");
		super.setParameter(param);
	}

	public String getCurrentTemplateId() {
		return currentTemplateId;
	}

	public void setTemplateStepList(VwTemplateStepList templateStepList) {
		this.templateStepList = templateStepList;
	}

}
