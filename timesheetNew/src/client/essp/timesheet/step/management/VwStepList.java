package client.essp.timesheet.step.management;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellEditor;

import c2s.dto.DtoBase;
import c2s.dto.DtoUtil;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.timesheet.activity.DtoActivityKey;
import c2s.essp.timesheet.step.management.DtoActivityForStep;
import c2s.essp.timesheet.step.management.DtoStep;
import c2s.essp.timesheet.step.management.DtoTemplateStep;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.TableUitl.TableColumnWidthSetter;
import client.essp.common.TableUitl.TableLayoutChooseDisplay;
import client.essp.common.excelUtil.VWJSExporterUtil;
import client.essp.common.view.VWTableWorkArea;
import client.essp.timesheet.common.StatusNodeViewManagerProxy;
import client.essp.timesheet.step.management.worker.VwAllocateWorker;
import client.essp.timesheet.step.management.worker.WorkersDropTarget;
import client.framework.common.Constant;
import client.framework.common.Global;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.vwcomp.NodeViewManager;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJNumber;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;

import com.wits.util.Parameter;

/**
 * <p>
 * Title:VwActivityList
 * </p>
 * 
 * <p>
 * Description: Step List卡片
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
public class VwStepList extends VWTableWorkArea {

	private DtoActivityForStep activityForStep;
	private int selectRow = -1;
	
	private Long activityId;
	private JButton btnApply;
	private JButton btnClone;
	private JButton btnAdd;
	private JButton btnDelete;
	private JButton btnSave;
	private JButton btnWorker;
	private JButton btnExport;
	// 模版中的List
	private List templateList;
	VwAllocateWorker workers = null;
	TableLayoutChooseDisplay layoutChooseDisplay;
	
	private final static String[] planColumns = new String[] {
		"rsid.timesheet.category",
		"rsid.common.name",
		"rsid.timesheet.planStart",
		"rsid.timesheet.planFinish",
		"rsid.timesheet.resources",
		"rsid.timesheet.status",
		"rsid.timesheet.isCorp",
		"rsid.timesheet.procWt",
		"rsid.common.sequence"
	};
	
	private final static String[] actualColumns = new String[] {
		"rsid.timesheet.category",
		"rsid.common.name",
		"rsid.timesheet.planStart",
		"rsid.timesheet.planFinish",
		"rsid.timesheet.actualStart",
		"rsid.timesheet.actualFinish",
		"rsid.timesheet.resources",
		"rsid.timesheet.status",
		"rsid.timesheet.completeFlag",
		"rsid.timesheet.isCorp",
		"rsid.timesheet.actualCostTime"
	};

	public VwStepList() {
		VWJReal real = new VWJReal();
		VWJReal integer = new VWJReal();
		integer.setMaxInputDecimalDigit(0);
		real.setMaxInputDecimalDigit(2);
		VWJDate date = new VWJDate();
		date.setCanSelect(true);
		Object[][] configs = new Object[][] {
				{ "rsid.timesheet.category", "category",
						VMColumnConfig.EDITABLE, new VWJText(), Boolean.FALSE },
				{ "rsid.common.name", "procName",
						VMColumnConfig.EDITABLE, new VWJText(), Boolean.FALSE },

				{ "rsid.timesheet.planStart", "planStart",
						VMColumnConfig.EDITABLE, date, Boolean.FALSE },
				{ "rsid.timesheet.planFinish", "planFinish",
						VMColumnConfig.EDITABLE, date, Boolean.FALSE },
				{ "rsid.timesheet.actualStart", "actualStart",
						VMColumnConfig.EDITABLE, date, Boolean.TRUE },
				{ "rsid.timesheet.actualFinish", "actualFinish",
						VMColumnConfig.EDITABLE, date, Boolean.TRUE },

				{ "rsid.timesheet.resourceIds", "resourceId",
						VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.TRUE },
				{ "rsid.timesheet.resources", "resourceName",
						VMColumnConfig.EDITABLE, new VWJText(), Boolean.FALSE },
				{ "rsid.timesheet.function", "function",
						VMColumnConfig.EDITABLE, new VWJText(), Boolean.TRUE },
				{ "rsid.timesheet.status", "status", VMColumnConfig.UNEDITABLE,
						new VWJText(), Boolean.FALSE },

				{ "rsid.timesheet.completeFlag", "completeFlag",
						VMColumnConfig.EDITABLE, new VWJCheckBox(),
						Boolean.TRUE },
				{ "rsid.timesheet.isCorp", "isCorp", VMColumnConfig.EDITABLE,
						new VWJCheckBox(), Boolean.FALSE },
				{ "rsid.timesheet.procWt", "procWt", VMColumnConfig.EDITABLE,
						real, Boolean.FALSE },
				{ "rsid.common.sequence", "seqNum", VMColumnConfig.EDITABLE,
							integer, Boolean.FALSE },
				{ "rsid.timesheet.actualCostTime", "actualCostTime",
						VMColumnConfig.UNEDITABLE, real, Boolean.TRUE }

		};
		try {
			// this.jbInit(configs, DtoStep.class);
			model = new VMStepListModel(configs);
			model.setDtoType(DtoStep.class);
			table = new VWJTable(model, new StatusNodeViewManagerProxy(new NodeViewManager(), "status"));
			TableColumnWidthSetter.set(table);
			table.setSortable(true);
			this.add(table.getScrollPane(), null);

		} catch (Exception ex) {
			log.error(ex);
		}
		addUICEvent();
		(new WorkersDropTarget(this.getTable())).create();
	}

	private void actionPerformedClone() {
		DtoStep step = new DtoStep();
		DtoStep selectedStep = (DtoStep) this.getModel().getRow(selectRow);
		DtoUtil.copyBeanToBean(step, selectedStep);
		step.setActualStart(null);
		step.setActualFinish(null);
		step.setActualCostTime(null);
		step.setRid(null);
		step.setSeqNum(step.getSeqNum() + 1);

		this.getModel().addRow(selectRow, step);
		this.getTable().setSelectRow(selectRow + 1);
	}

	private void actionPerformedAdd() {
		DtoStep step = new DtoStep();
		step.setTaskId(activityId);
		step.setProjId(new Long(activityForStep.getProjectId()));
		int rowCount = this.getTable().getRowCount();
		if (rowCount > 0) {
			DtoStep lastStep = (DtoStep) this.getModel().getRow(rowCount - 1);
			step.setSeqNum(new Long(lastStep.getSeqNum().intValue() + 1));
		} else {
			step.setSeqNum(new Long(1));
		}
		step.setCategory("Other");
		this.getModel().addRow(rowCount + 1, step);
		getTable().setSelectRow(rowCount);
	}

	private void actionPerformedDelete() {
		this.getTable().deleteRow();
	}

	private boolean checkListField(List stepList) {
		Date start = activityForStep.getPlanStart();
		Date finish = activityForStep.getPlanFinish();
		for (int i = 0; i < stepList.size(); i++) {
			DtoStep step = (DtoStep) stepList.get(i);
			if (step.getOp().equals(DtoBase.OP_NOCHANGE)
					|| step.getOp().equals(DtoBase.OP_DELETE)) {
				continue;
			}
			if (step.getCategory() == null || step.getProcName() == null
					|| step.getResourceName() == null) {
				comMSG.dispMessageDialog("rsid.timesheet.checkListFieldTipMsg");
				return false;
			}
			if (step.getPlanStart() != null
					&& WorkCalendar.ignoreTimeCompare(step.getPlanStart(), start) < 0) {
				comMSG
						.dispMessageDialog("rsid.timesheet.checkListFieldTipMsgForStartDate");
				return false;
			}
			if (step.getPlanFinish() != null
					&& WorkCalendar.ignoreTimeCompare(step.getPlanFinish(),finish) > 0) {
				comMSG
						.dispMessageDialog("rsid.timesheet.checkListFieldTipMsgForFinishDate");
				return false;
			}
		}

		return true;
	}

	private void actionPerformedSave() {

		int row = this.getTable().getSelectedRow();
		List stepList = this.getModel().getDtoList();
		if (!checkListField(stepList)) {
			return;
		}
		InputInfo inputInfo = new InputInfo();
		inputInfo.setActionId("FTSSaveStep");

		inputInfo.setInputObj("ActvityObjectId", activityForStep.getId()
				.toString());
		inputInfo.setInputObj(DtoStep.KEY_SAVE_STEP_LIST, stepList);
		ReturnInfo rtInfo = this.accessData(inputInfo);
		if(rtInfo.isError() == false) {
			List dataList = (List) rtInfo.getReturnObj(DtoStep.KEY_STEP_LIST);
			getTable().setRows(dataList);
			if (dataList != null && dataList.size() > 0) {
				getTable().setSelectRow(0);
			}
			this.getTable().setSelectRow(row);
			comMSG.dispMessageDialog("rsid.common.saveComplete");
		}
	}

	private void actionPerformedWorker() {
		
		Parameter param = new Parameter();
		if(activityForStep != null) {
			param.put("ResourceIds", activityForStep.getResourceIds());
			param.put("Resources", activityForStep.getResources());
		}
		if (workers == null) {
			Container c = this.getMyParentWindow();
			workers = new VwAllocateWorker(c);
			workers.setParameter(param);
			workers.showPopSelect();
		} else {
			workers.showPopSelect();
			workers.setParameter(param);
		}

	}

	/**
	 * 取得父窗口句柄
	 * 
	 * @return Frame
	 */
	private Container getMyParentWindow() {
		java.awt.Container c = this.getParent();
		while (c != null) {
			if ((c instanceof java.awt.Frame) || (c instanceof java.awt.Dialog)) {
				return c;
			}
			c = c.getParent();
		}
		return null;
	}

	private void actionPerformedApply() {
		VwApplyTemplate template = new VwApplyTemplate();
		// show
		VWJPopupEditor popup = new VWJPopupEditor(this.getParentWindow(),
				"Apply Template", template, template);
		Parameter param = new Parameter();
		param.put("StepListVM", this);
		param.put("AccountId", activityForStep.getProjectCode());
		template.setParameter(param);
		int result = popup.showConfirm();
		// refresh labors table
		if (Constant.OK == result) {
			InputInfo inputInfo = new InputInfo();
			inputInfo.setActionId("FTSGetWorkDay");

			inputInfo.setInputObj("StartDate", activityForStep.getPlanStart());
			inputInfo
					.setInputObj("FinishDate", activityForStep.getPlanFinish());
			inputInfo.setInputObj("ActivityObjectId", activityForStep.getId()
					.toString());

			ReturnInfo rtInfo = this.accessData(inputInfo);
			List<Date> workDay = (List) rtInfo.getReturnObj("WorkDay");
			// 计算模版中的最大偏移天数
			int maxOffset = 0;
			for (int i = 0; i < templateList.size(); i++) {
				DtoTemplateStep tStep = (DtoTemplateStep) templateList.get(i);
				int offset = tStep.getPlanFinishOffset().intValue();
				if (offset > maxOffset) {
					maxOffset = offset;
				}
			}
			double unit = 0;
			if (workDay.size() > 0) {
				unit = (double) workDay.size() / (double) maxOffset;
			}
			int rowCount = this.getTable().getRowCount();
			for (int i = 0; i < templateList.size(); i++) {
				DtoTemplateStep tStep = (DtoTemplateStep) templateList.get(i);
				DtoStep step = new DtoStep();
				step.setTaskId(activityForStep.getId().longValue());
				step.setProjId(new Long(activityForStep.getProjectId()));
				step.setCategory(tStep.getCategory());
				step.setStepTRid(tStep.getRid());
				step.setProcName(tStep.getProcName());
				step.setIsCorp(tStep.getIsCorp());
				step.setProcWt(tStep.getProcWt());
				step.setResourceId(activityForStep.getManagerId());
				step.setResourceName(activityForStep.getManager());
				if (unit == 0) {
					step.setPlanStart(activityForStep.getPlanStart());
					step.setPlanFinish(activityForStep.getPlanFinish());
				} else {
					double s = tStep.getPlanStartOffset().doubleValue() * unit;
					double f = tStep.getPlanFinishOffset().doubleValue() * unit;
					Double start = s > workDay.size() ? workDay.size() : s;
					Double finish = f = f > workDay.size() ? workDay.size() : f;
					int startIndex = start.intValue() >= 1 ? start.intValue() - 1
							: 0;
					int finishIndex = finish.intValue() >= 1 ? finish
							.intValue() - 1 : 0;
					step.setPlanStart(workDay.get(startIndex));
					step.setPlanFinish(workDay.get(finishIndex));
				}
				if (rowCount > 0) {
					DtoStep lastStep = (DtoStep) this.getModel().getRow(
							rowCount - 1);
					step.setSeqNum(new Long(lastStep.getSeqNum().intValue()
							+ tStep.getSeqNum().intValue()));
				} else {
					step.setSeqNum(tStep.getSeqNum());
				}
				this.getModel().addRow(rowCount + i - 1, step);
			}
		}

	}



	private void setButtonStatus() {
		if (activityId == null || !isManager(activityForStep)) {
			btnApply.setEnabled(false);
			btnClone.setEnabled(false);
			btnAdd.setEnabled(false);
			btnDelete.setEnabled(false);
			btnSave.setEnabled(false);
		} else {
			btnApply.setEnabled(true);
			btnClone.setEnabled(true);
			btnAdd.setEnabled(true);
			btnDelete.setEnabled(true);
			btnSave.setEnabled(true);
		}
	}
	
	private static boolean isManager(DtoActivityForStep dto) {
		return dto != null && dto.getManagerId() != null 
		&& Global.userId.toUpperCase().equals(dto.getManagerId().toUpperCase());
	}

	/**
	 * 注册UI事件监听
	 */
	private void addUICEvent() {
		
		this.getTable().addRowSelectedListener(new RowSelectedListener() {
			public void processRowSelected() {
				selectRow = getTable().getSelectedRow();
		}});

		// Apply
		btnApply = this.getButtonPanel().addButton("applyTemplate.gif");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedApply();
			}
		});
		btnApply.setToolTipText("Apply");
		// Clone
		btnClone = this.getButtonPanel().addButton("copy.png");
		btnClone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedClone();
			}
		});
		btnClone.setToolTipText("Clone");
		// add

		btnAdd = this.getButtonPanel().addAddButton(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionPerformedAdd();
			}
		});
		btnAdd.setToolTipText("rsid.common.add");
		// delete
		btnDelete = this.getButtonPanel().addDelButton(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				actionPerformedDelete();
			}
		});
		btnDelete.setToolTipText("rsid.common.delete");

		// save
		btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionPerformedSave();
			}
		});
		btnSave.setToolTipText("rsid.common.save");
		// worker
		btnWorker = this.getButtonPanel().addButton("worker.png");
		btnWorker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedWorker();
			}
		});
		btnWorker.setToolTipText("rsid.common.resource");

//		 export
		btnExport = this.getButtonPanel().addButton("export.png");
		btnExport.setToolTipText("rsid.common.export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedExport();
			}
		});
		
		// Display
		TableColumnChooseDisplay chooseDisplay = new TableColumnChooseDisplay(
				this.getTable(), this);
		JButton button = chooseDisplay.getDisplayButton();
		this.getButtonPanel().addButton(button);
		
		layoutChooseDisplay = new TableLayoutChooseDisplay(chooseDisplay);
		layoutChooseDisplay.addLayout("rsid.common.plan", planColumns);
		layoutChooseDisplay.addLayout("rsid.timesheet.monitoring", actualColumns);
		this.getButtonPanel().addButton(layoutChooseDisplay.getDisplayButton());
		
		
		// load
		this.getButtonPanel().addLoadButton(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetUI();
			}
		});

	}

	// 参数设置
	public void setParameter(Parameter param) {
		super.setParameter(param);
		activityId = (Long) param.get(DtoActivityKey.DTO_RID);
	}

	/**
	 * 刷新Worker List,用于当Worker List并未关闭时，切换了Activity的情况
	 */
	public void refreshWorkerList() {
		if (workers == null) {
			return;
		}
		Parameter param = new Parameter();
		String ids = "";
		String name  = "";
		if(activityForStep != null) {
			ids = activityForStep.getResources();
			name = activityForStep.getResourceIds();
		}
		param.put("Resources", ids);
		param.put("ResourceIds", name);
		workers.setParameter(param);
	}
	private void actionPerformedExport() {
		String activityIds = activityId + "";
		Map<String, String> param = new HashMap<String, String>();
		param.put("activityIds",activityIds);
		VWJSExporterUtil.excuteJSExporter("FTSStepExport", param);
	}

	// 重置
	protected void resetUI() {
		if (activityId == null) {
			getTable().setRows(new ArrayList());
			setButtonStatus();
			refreshWorkerList();
			return;
		}
		InputInfo inputInfo = new InputInfo();
		inputInfo.setActionId("FTSListStep");

		inputInfo.setInputObj("ActvityObjectId", activityId.toString());
		ReturnInfo rtInfo = this.accessData(inputInfo);
		List dataList = (List) rtInfo.getReturnObj(DtoStep.KEY_STEP_LIST);
		activityForStep = (DtoActivityForStep) rtInfo.getReturnObj(DtoActivityForStep.KEY_DTO);
		getTable().setRows(dataList);
		if (dataList != null && dataList.size() > 0) {
			getTable().setSelectRow(0);
		}
		setButtonStatus();
		refreshWorkerList();
	}

	public void setTemplateList(List templateList) {
		this.templateList = templateList;
	}
}
