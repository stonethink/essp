package client.essp.timesheet.step.management;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.step.management.DtoStep;
import c2s.essp.timesheet.step.management.DtoTemplateStep;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.view.VWTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;

import com.wits.util.Parameter;

/**
 * <p>
 * Title:VwActivityList
 * </p>
 * 
 * <p>
 * Description: Template Step List卡片
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
public class VwTemplateStepList extends VWTableWorkArea {

	private String templateId;

	public VwTemplateStepList() {
		VWJReal real = new VWJReal();
		real.setMaxInputDecimalDigit(2);
		VWJDate date = new VWJDate();
		date.setCanSelect(true);

		Object[][] configs = new Object[][] {
				{ "rsid.timesheet.category", "category",
						VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.FALSE },
				{ "rsid.timesheet.stepName", "procName",
						VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.FALSE },
				{ "rsid.timesheet.procWt", "procWt",
						VMColumnConfig.UNEDITABLE, real, Boolean.FALSE },
				{ "rsid.timesheet.planStartOffset", "planStartOffset",
						VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.TRUE },
				{ "rsid.timesheet.planFinishOffset", "planFinishOffset",
						VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.TRUE },
				{ "rsid.timesheet.isCorp", "isCorp", VMColumnConfig.UNEDITABLE,
						new VWJCheckBox(), Boolean.FALSE },
				{ "rsid.timesheet.procDescr", "procDescr",
						VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.TRUE } };
		try {
			this.jbInit(configs, DtoTemplateStep.class);

		} catch (Exception ex) {
			log.error(ex);
		}
		addUICEvent();
	}

	/**
	 * 注册UI事件监听
	 */
	private void addUICEvent() {

		// Display
		TableColumnChooseDisplay chooseDisplay = new TableColumnChooseDisplay(
				this.getTable(), this);
		JButton button = chooseDisplay.getDisplayButton();
		this.getButtonPanel().addButton(button);
		this.getTable().getColumnModel().addColumnModelListener(
				new TableColumnModelListener() {
					public void columnAdded(TableColumnModelEvent e) {
						if (getTable().getColumnModel().getColumnCount() > 1) {
							getTable().getTableHeader().setPreferredSize(
									new Dimension(100, 22));
						}
					}

					public void columnRemoved(TableColumnModelEvent e) {
						if (getTable().getColumnModel().getColumnCount() == 1) {
							getTable().getTableHeader().setPreferredSize(
									new Dimension(100, 0));
						}
					}

					public void columnMoved(TableColumnModelEvent e) {
					}

					public void columnMarginChanged(ChangeEvent e) {
					}

					public void columnSelectionChanged(ListSelectionEvent e) {
					}
				});
		this.getButtonPanel().addLoadButton(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetUI();
			}
		});

	}

	// 参数设置
	public void setParameter(Parameter param) {
		super.setParameter(param);
		Object id = param.get("TemplateId");
		if (id != null) {
			templateId = id.toString();
		}
		this.resetUI();
	}

	// 重置
	protected void resetUI() {
		if(templateId == null) {
			getTable().setRows(new ArrayList());
			return;
		}
		InputInfo inputInfo = new InputInfo();
		inputInfo.setActionId("FTSLoadTemplateStepList");
		inputInfo.setInputObj("TemplateId", templateId);
		ReturnInfo rtInfo = this.accessData(inputInfo);
		List dataList = (List) rtInfo
				.getReturnObj(DtoTemplateStep.KEY_TEMPLATE_STEP_LIST);
		getTable().setRows(dataList);
		if (dataList != null && dataList.size() > 0) {
			getTable().setSelectRow(0);
		}
	}

}
