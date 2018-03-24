package client.essp.timesheet.step.management;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.step.DtoActivityFilter;
import c2s.essp.timesheet.step.management.DtoActivityForStep;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.TableUitl.TableColumnWidthSetter;
import client.essp.common.TableUitl.TableLayoutChooseDisplay;
import client.essp.common.excelUtil.VWJSExporterUtil;
import client.essp.common.view.VWTableWorkArea;
import client.essp.timesheet.common.PrimaryResuorceNodeViewManagerProxy;
import client.essp.timesheet.common.StatusNodeViewManagerProxy;
import client.essp.timesheet.step.VwActivityFilter;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMComboBox;
import client.framework.model.VMTable2;
import client.framework.view.event.DataChangedListener;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDate;
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
 * Description: ActivityList¿¨Æ¬
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
public class VwActivityList extends VWTableWorkArea {
	
	private VwActivityFilter vwFilter = new VwActivityFilter();
	private DtoActivityFilter dtoFilter;
	private List dataList;
	Vector projectList = null;
	VWJText txtAcnt = new VWJText();
	VWJComboBox acntList = new VWJComboBox();
	TableLayoutChooseDisplay layoutChooseDisplay;
	JButton btnExport;
	private String currentProjectId = "";
	private boolean viewAll = false;
	
	private final static String[] planColumns = new String[] {
		"rsid.common.code",
		"rsid.common.name",
		"rsid.timesheet.wbsName",
		"rsid.timesheet.planStart",
		"rsid.timesheet.planFinish",
		"rsid.timesheet.planHours",
		"rsid.timesheet.resources",
		"rsid.timesheet.status",
		"rsid.timesheet.statusIndicator"
	};
	
	private final static String[] actualColumns = new String[] {
		"rsid.common.code",
		"rsid.common.name",
		"rsid.timesheet.wbsName",
		"rsid.timesheet.planStart",
		"rsid.timesheet.planFinish",
		"rsid.timesheet.actualStart",
		"rsid.timesheet.actualFinish",
		"rsid.timesheet.resources",
		"rsid.timesheet.status",
		"rsid.timesheet.statusIndicator"
	};
	
	public VwActivityList() {
		this(false);
	}

	public VwActivityList(boolean viewAll) {
		this.viewAll = viewAll;
		VWJReal duration = new VWJReal();
		duration.setMaxInputDecimalDigit(2);
		Object[][] configs = new Object[][] {
				{ "rsid.common.code", "code", VMColumnConfig.UNEDITABLE,
						new VWJText(), Boolean.FALSE },
				{ "rsid.common.name", "name", VMColumnConfig.UNEDITABLE,
						new VWJText(), Boolean.FALSE },
				{ "rsid.timesheet.wbsCode", "wbsCode",
						VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.TRUE },
				{ "rsid.timesheet.wbsName", "wbsName",
						VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.FALSE },
				{ "rsid.timesheet.planStart", "planStart",
						VMColumnConfig.UNEDITABLE, new VWJDate(), Boolean.FALSE },
				{ "rsid.timesheet.planFinish", "planFinish",
						VMColumnConfig.UNEDITABLE, new VWJDate(), Boolean.FALSE },
				{ "rsid.timesheet.actualStart", "actualStart",
						VMColumnConfig.UNEDITABLE, new VWJDate(), Boolean.TRUE },
				{ "rsid.timesheet.actualFinish", "actualFinish",
						VMColumnConfig.UNEDITABLE, new VWJDate(), Boolean.TRUE },
				{ "rsid.timesheet.step.sponsorId", "managerId",
						VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.TRUE },
				{ "rsid.timesheet.step.sponsor", "manager",
						VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.TRUE },
				{ "rsid.timesheet.resourceIds", "resourceIds",
						VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.TRUE },
				{ "rsid.timesheet.resources", "resources",
						VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.FALSE },
				{ "rsid.timesheet.planDuration", "planDuration",
						VMColumnConfig.UNEDITABLE, duration, Boolean.TRUE },
				{ "rsid.timesheet.planHours", "planHour",
							VMColumnConfig.UNEDITABLE, duration, Boolean.FALSE },
				{ "rsid.timesheet.status", "status", VMColumnConfig.UNEDITABLE,
						new VWJText(), Boolean.FALSE },
				{ "rsid.timesheet.statusIndicator", "statusIndicator",
						VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.FALSE },
				{ "rsid.timesheet.functionId", "functionId",
						VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.TRUE },
				{ "rsid.timesheet.stepType", "stepType",
						VMColumnConfig.UNEDITABLE, new VWJText(), Boolean.TRUE },
				{ "rsid.timesheet.sponsor", "sponsor",
						VMColumnConfig.UNEDITABLE, new VWJDate(), Boolean.TRUE },
				{ "rsid.timesheet.earliestStart", "earliestStart",
						VMColumnConfig.UNEDITABLE, new VWJDate(), Boolean.TRUE },
				{ "rsid.timesheet.earliestFinish", "earliestFinish",
						VMColumnConfig.UNEDITABLE, new VWJDate(), Boolean.TRUE },
				{ "rsid.timesheet.atLatestStart", "atLatestStart",
						VMColumnConfig.UNEDITABLE, new VWJDate(), Boolean.TRUE },
				{ "rsid.timesheet.atLatestFinish", "atLatestFinish",
						VMColumnConfig.UNEDITABLE, new VWJDate(), Boolean.TRUE },
				{ "rsid.timesheet.estimatefinish", "estimatefinish",
						VMColumnConfig.UNEDITABLE, new VWJDate(), Boolean.TRUE } };
		try {
			this.jbInit(configs, DtoActivityForStep.class);
		this.getTable().setSortable(true);
		} catch (Exception ex) {
			log.error(ex);
		}
		addUICEvent(viewAll);

	}
	
	protected void jbInit(Object[][] configs, Class dtoClass) throws Exception {
        //table
        model = new VMTable2(configs);
        model.setDtoType(dtoClass);
        table = new VWJTable(model, new PrimaryResuorceNodeViewManagerProxy(new StatusNodeViewManagerProxy("statusIndicator"), "managerId"));
        TableColumnWidthSetter.set(table);

        this.add(table.getScrollPane(), null);
    }

	private void actionPerformedExport() {
		List list = this.getModel().getRows();
		String activityIds = null;
		for (int i = 0; i < list.size(); i++) {
			DtoActivityForStep dto = (DtoActivityForStep) list.get(i);
			if (activityIds == null) {
				activityIds = dto.getId().toString();

			} else {
				activityIds = activityIds + "," + dto.getId().toString();
			}
		}
		Map<String, String> param = new HashMap<String, String>();
		param.put("activityIds",activityIds);
		VWJSExporterUtil.excuteJSExporter("FTSStepExport", param);
	}
	
	/**
	 * ¹ýÂËActivity
	 *
	 */
	private void actionPerformedFilter() {
		if(dtoFilter == null) {
			dtoFilter = VwActivityFilter.getDefaultFilter();
		}
		VWJPopupEditor poputEditor = new VWJPopupEditor(getParentWindow(), "Filter",
                vwFilter, vwFilter);
		vwFilter.refreshWorkArea();
        int result = poputEditor.showConfirm();
        if(Constant.OK == result) {
        	List filterList = vwFilter.getFilterData();
    		getTable().setRows(filterList);
    		if (filterList != null && filterList.size() > 0) {
    			getTable().setSelectRow(0);
    		}
        }
	}

	/**
	 * ×¢²áUIÊÂ¼þ¼àÌý
	 */
	private void addUICEvent(boolean viewAll) {
		if(viewAll) {
			txtAcnt.setPreferredSize(new Dimension(100, 18));
			this.getButtonPanel().add(txtAcnt);
			
			txtAcnt.addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent e) {
					currentProjectId = txtAcnt.getText();
				}
			});
			
			txtAcnt.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						currentProjectId = txtAcnt.getText();
						resetUI();
					}
			}});
		} else {
			this.getButtonPanel().add(acntList);
			acntList.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					currentProjectId = (String)acntList.getUICValue();
					dtoFilter.setWbs(null);
					resetUI();
				}
			});
		}
		JLabel blankLabel = new JLabel("           ");
		blankLabel.setSize(100, 20);
		this.getButtonPanel().add(blankLabel);
		JButton btnFilter = this.getButtonPanel().addButton("filter.gif");
		btnFilter.setToolTipText("rsid.common.filter");
		
		btnFilter.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				actionPerformedFilter();
			}});
		// export
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
		
		this.getButtonPanel().addLoadButton(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				resetUI();
			}

		});
		
	}

	// ÖØÖÃ
	protected void resetUI() {
		if(dtoFilter == null) {
			dtoFilter = VwActivityFilter.getDefaultFilter();
		}
		
		if(!viewAll && projectList == null) {
			initProjectList();
		}
		InputInfo inputInfo = new InputInfo();
		inputInfo.setActionId("FTSListActivityForStep");
		inputInfo.setInputObj("ProjectId", currentProjectId);
		ReturnInfo rtInfo = this.accessData(inputInfo);
		dataList = (List) rtInfo
				.getReturnObj(DtoActivityForStep.KEY_ACTIVITY_LIST_FOR_STEP);
		
		//reset vwFilter parameters
		Parameter param = new Parameter();
		param.put(DtoActivityFilter.DTO_KEY, dtoFilter);
		param.put(DtoActivityFilter.DTO_ACTIVITY_LIST, dataList);
		vwFilter.setParameter(param);
		
		List filterList = vwFilter.filterData(dtoFilter, dataList);
		getTable().setRows(filterList);
		if (filterList != null && filterList.size() > 0) {
			getTable().setSelectRow(0);
		}
		// fireDataChanged();
	}
	
	private void initProjectList() {
		InputInfo acntInput = new InputInfo();
		acntInput.setActionId("FTSListProjects");
		projectList = (Vector) this.accessData(acntInput).getReturnObj(
				"ProjectList");
		
		if (projectList != null && projectList.size() > 0) {
			acntList.setModel(new VMComboBox(projectList));
			currentProjectId = (String)acntList.getUICValue();
		}
	}

	public String getCurrentProjectObjectId() {
		return currentProjectId;
	}

}
