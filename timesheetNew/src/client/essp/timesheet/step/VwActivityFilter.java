package client.essp.timesheet.step;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.wits.util.Parameter;
import com.wits.util.TestPanel;

import c2s.dto.DtoTreeNode;
import c2s.dto.ITreeNode;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.timesheet.step.DtoActivityFilter;
import c2s.essp.timesheet.step.IDtoActivityFilterData;
import c2s.essp.timesheet.step.management.DtoActivityForStep;
import c2s.essp.timesheet.step.monitoring.DtoMonitoring;
import c2s.essp.timesheet.workscope.DtoActivity;
import client.essp.common.humanAllocate.VWJHrAllocateButton;
import client.essp.common.view.MLTitleBorder;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.common.Global;
import client.framework.model.VMComboBox;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWUtil;

/**
 * <p>Title: VwActivityFilter</p>
 *
 * <p>Description: Step Management $ Monitoring Filter</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwActivityFilter extends VWGeneralWorkArea implements IVWPopupEditorEvent {
	
	private DtoActivityFilter dtoFilter;
	List<IDtoActivityFilterData> dataList;
	private ITreeNode treeNode;
	
	private VWJLabel lblWbs;
	private VWJComboBox cmbWbs;
	
	private VWJCheckBox chkNotStart;
	private VWJLabel lblStart;
	private VWJDate datStart;
	private VWJCheckBox chkInProgress;
	private VWJCheckBox chkCompleted;
	private VWJLabel lblFinish;
	private VWJDate datFinish;
	
	private VWJCheckBox chkManager;
	private VWJHrAllocateButton habManager;
	private VWJCheckBox chkResource;
	private VWJHrAllocateButton habResource;
	private VWJCheckBox chkOthers;
	
	private VWJLabel lblName;
	private VWJText txtName;
	
	
	public VwActivityFilter() {
		try {
            jbInit();
            setUIComponentName();
            setEnterOrder();
            setTabOrder();
            addUICEvent();
        } catch (Exception ex) {
            log.error(ex);
        }
	}

	/**
     * 初始化UI
     * @throws Exception
     */
    private void jbInit() throws Exception {
    	this.setLayout(null);
    	setPreferredSize(new Dimension(450, 350));
    	
    	lblWbs = new VWJLabel();
    	cmbWbs = new VWJComboBox();
    	
    	chkNotStart = new VWJCheckBox();
    	lblStart = new VWJLabel();
    	datStart = new VWJDate();
    	datStart.setCanSelect(true);
    	chkInProgress = new VWJCheckBox();
    	chkCompleted = new VWJCheckBox();
    	lblFinish = new VWJLabel();
    	datFinish = new VWJDate();
    	datFinish.setCanSelect(true);
    	
    	chkManager = new VWJCheckBox();
    	habManager = new VWJHrAllocateButton();
    	chkResource = new VWJCheckBox();
    	habResource = new VWJHrAllocateButton();
    	chkOthers = new VWJCheckBox();
    	
    	lblName = new VWJLabel();
    	txtName = new VWJText();
    	
    	//wbs
    	lblWbs.setText("WBS");
    	lblWbs.setBounds(new Rectangle(35, 24, 150, 20));
    	cmbWbs.setBounds(new Rectangle(215, 24, 186, 20));
    	
    	
    	Border bdrStatus = new MLTitleBorder("rsid.timesheet.status");

        JPanel pnlStatus = new JPanel();
        pnlStatus.setLayout(null);
        pnlStatus.setBounds(34, 54, 380,110);
        pnlStatus.setBorder(bdrStatus);
        //status
    	chkNotStart.setText("rsid.timesheet.activity.notStart");
    	chkNotStart.setBounds(new Rectangle(5, 24, 150, 20));
    	lblStart.setText(" <= ");
    	lblStart.setBounds(new Rectangle(160, 24, 40, 20));
    	datStart.setBounds(new Rectangle(215, 24, 150, 20));
    	
    	chkInProgress.setText("rsid.timesheet.activity.inProgress");
    	chkInProgress.setBounds(new Rectangle(5, 54, 150, 20));
    	
    	chkCompleted.setText("rsid.timesheet.activity.completed");
    	chkCompleted.setBounds(new Rectangle(5, 84, 150, 20));
    	lblFinish.setText(" >= ");
    	lblFinish.setBounds(new Rectangle(160, 84, 40, 20));
    	datFinish.setBounds(new Rectangle(215, 84, 150, 20));
    	
    	pnlStatus.add(chkNotStart);
    	pnlStatus.add(lblStart);
    	pnlStatus.add(datStart);
    	pnlStatus.add(chkInProgress);
    	pnlStatus.add(chkCompleted);
    	pnlStatus.add(lblFinish);
    	pnlStatus.add(datFinish);
    	
    	Border bdrResource = new MLTitleBorder("rsid.timesheet.resources");
        JPanel pnlResource = new JPanel();
        pnlResource.setLayout(null);
        pnlResource.setBounds(34, 174, 380,110);
        pnlResource.setBorder(bdrResource);
    	
    	//resource
    	chkManager.setText("rsid.timesheet.step.sponsor");
    	chkManager.setBounds(new Rectangle(5, 24, 150, 20));
    	habManager.setBounds(new Rectangle(215, 24, 150, 20));
    	
    	chkResource.setText("rsid.timesheet.resources");
    	chkResource.setBounds(new Rectangle(5, 54, 150, 20));
    	habResource.setBounds(new Rectangle(215, 54, 150, 20));
    	
    	chkOthers.setText("rsid.timesheet.all");
    	chkOthers.setBounds(new Rectangle(5, 84, 150, 20));
    	
    	pnlResource.add(chkManager);
    	pnlResource.add(habManager);
    	pnlResource.add(chkResource);
    	pnlResource.add(habResource);
    	pnlResource.add(chkOthers);
    	
    	//name
    	lblName.setText("rsid.timesheet.activityName");
    	lblName.setBounds(new Rectangle(35, 294, 150, 20));
    	txtName.setBounds(new Rectangle(215, 294, 186, 20));
    	
    	this.add(lblWbs);
    	this.add(cmbWbs);
    	this.add(pnlStatus);
    	this.add(pnlResource);
    	this.add(lblName);
    	this.add(txtName);
    }
    
    
    /**
     * 添加UI事件控制
     *
     */
    private void addUICEvent() {
    	
    	chkNotStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetDatStartEnable();
			}
    	});
    	
    	chkCompleted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetDatFinishEnable();
			}
    	});
    	
    	chkManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetHabManagerEnable();
			}
    	});
    	
    	chkResource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetHabResourceEnable();
			}
    	});
    }
    
    /**
     * 设置开始日期控件是否可用
     */
    private void resetDatStartEnable() {
    	//如果筛选未开始状态在可用，否则不可用
    	datStart.setEnabled(chkNotStart.isSelected());
    }
    
    /**
     * 设置结束日期控件是否可用
     */
    private void resetDatFinishEnable() {
    	//如果筛已完成状态在可用，否则不可用
    	datFinish.setEnabled(chkCompleted.isSelected());
    }
    
    /**
     * 设置负责人控件是否可用
     */
    private void resetHabManagerEnable() {
    	//如果筛选任负责人状态在可用，否则不可用
    	habManager.setEnabled(chkManager.isSelected());
    }
    
    /**
     * 设置资源控件是否可用
     */
    private void resetHabResourceEnable() {
    	//如果筛任资源状态在可用，否则不可用
    	habResource.setEnabled(chkResource.isSelected());
    }
    
    /**
     * 重设所有有状态控制的输入控件状态
     *
     */
    private void resetInputComStatus() {
    	resetDatStartEnable();
    	resetDatFinishEnable();
    	resetHabManagerEnable();
    	resetHabResourceEnable();
    }
    
    /**
     * 设置控件名称
     */
    private void setUIComponentName() {
    	cmbWbs.setName("wbs");
    	chkNotStart.setName("notStart");
    	datStart.setName("start");
    	chkInProgress.setName("inProgress");
    	chkCompleted.setName("completed");
    	datFinish.setName("finish");
    	chkManager.setName("checkManager");
    	habManager.setName("manager");
    	chkResource.setName("checkResource");
    	habResource.setName("resource");
    	chkOthers.setName("checkOthers");
    	txtName.setName("name");
    }

    /**
     * 设置Tab键跳转顺序
     */
    private void setTabOrder() {
        List compList = new ArrayList();
        compList.add(cmbWbs);
        compList.add(chkNotStart);
        compList.add(datStart);
        compList.add(chkInProgress);
        compList.add(chkCompleted);
        compList.add(datFinish);
        compList.add(chkManager);
        compList.add(habManager);
        compList.add(chkResource);
        compList.add(habResource);
        compList.add(chkOthers);
        compList.add(txtName);
        comFORM.setTABOrder(this, compList);
    }

    /**
     * 设置Enter键跳转顺序
     */
    private void setEnterOrder() {
        List compList = new ArrayList();
        compList.add(cmbWbs);
        compList.add(chkNotStart);
        compList.add(datStart);
        compList.add(chkInProgress);
        compList.add(chkCompleted);
        compList.add(datFinish);
        compList.add(chkManager);
        compList.add(habManager);
        compList.add(chkResource);
        compList.add(habResource);
        compList.add(chkOthers);
        compList.add(txtName);
        comFORM.setEnterOrder(this, compList);
    }
    
    /**
     * 刷新UI
     */
	protected void resetUI() {
		checkDtoFilter();
		initCmbWbs();
		VWUtil.bindDto2UI(dtoFilter, this);
    	resetInputComStatus();
	}

	/* 
	 * 传递参数
	 */
	@Override
	public void setParameter(Parameter param) {
		if(param != null) {
			dtoFilter = (DtoActivityFilter) param.get(DtoActivityFilter.DTO_KEY);
			dataList = (List) param.get(DtoActivityFilter.DTO_ACTIVITY_LIST);
			treeNode = (ITreeNode) param.get(DtoActivityFilter.TREE_NODE_KEY);
		}
		super.setParameter(param);
	}
	
	
	/**
	 * 检查dtoFilter， 如果为空进行初试化
	 */
	private void checkDtoFilter() {
		if(dtoFilter == null) {
			dtoFilter = getDefaultFilter();
		}
	}
	
	/**
	 * 提炼dataList中的WBS，对cmbWbs进行初试化
	 *
	 */
	private void initCmbWbs() {
		VMComboBox model = new VMComboBox();
		if(treeNode != null) {
			List<ITreeNode> nodeList = treeNode.listAllChildrenByPreOrder();
			for(ITreeNode node : nodeList) {
				Object bean = node.getDataBean();
				if(bean instanceof DtoMonitoring) {
					DtoMonitoring dto = (DtoMonitoring) bean;
					if(DtoMonitoring.BEAN_TYPE_WBS.equals(dto.getBeanType())) {
						model.addElement(dto.getWbsName(), dto.getWbsId());
					}
				}
			}
			
		} else if(dataList != null) {
			HashMap wbsMap = new HashMap();
			for(IDtoActivityFilterData dto : dataList) {
				String wbs = dto.getWbsName();
				wbsMap.put(wbs, wbs);
			}
			model = new VMComboBox(new Vector(wbsMap.keySet()));
		}
		model.insertElementAt("-- Select WBS --", null, 0);
		cmbWbs.setModel(model);
	}
	
	/**
	 * 默认筛选
	 * 		1、计划开始日期在未来7天内的作业
	 * 		2、实际完成日期在过去6天内的作业
	 * 		3、当前用户为主要资源的作业
	 * @return DtoActivityFilter
	 */
	public static DtoActivityFilter getDefaultFilter() {
		DtoActivityFilter dtoFilter = new DtoActivityFilter();
		dtoFilter.setNotStart(true);
		dtoFilter.setInProgress(true);
		dtoFilter.setCheckManager(true);
		Calendar today = Calendar.getInstance();
		Calendar calStart = WorkCalendar.getNextDay(today, 7);
		dtoFilter.setStart(calStart.getTime());
		Calendar calFinish = WorkCalendar.getNextDay(today, -6);
		dtoFilter.setFinish(calFinish.getTime());
		dtoFilter.setManager(Global.userId);
		dtoFilter.setResource(Global.userId);
		return dtoFilter;
	}
	
	/**
	 * 根据dtoFilter筛选内容，采用排除法
	 * 		wbs、status、resource、name之间是“与”
	 * 		status和resource的各个子条件是“或”的关系
	 * 
	 * @return List<IDtoActivityFilterData>
	 */
	public List getFilterData() {
		return filterData(this.dtoFilter, this.dataList);
	}
	
	/**
	 * 根据dtoFilter筛选内容，采用排除法
	 * 		wbs、status、resource、name之间是“与”
	 * 		status和resource的各个子条件是“或”的关系
	 * 
	 * @param dtoFilter DtoActivityFilter
	 * @param dataList List<IDtoActivityFilterData>
	 * @return List<IDtoActivityFilterData>
	 */
	public static List filterData(DtoActivityFilter dtoFilter, List<IDtoActivityFilterData> dataList) {
		List filterList = new ArrayList();
		if(dataList == null || dataList.size() < 1) {
			return filterList;
		}
		if(dtoFilter == null) {
			dtoFilter = getDefaultFilter();
		}
		for(IDtoActivityFilterData dto : dataList) {
			//check wbs
			String wbs = dtoFilter.getWbs();
			if(wbs != null && !"".equals(wbs) && !wbs.equals(dto.getWbsName())) {
				continue;
			}
			
			if(!checkActivity(dto, dtoFilter)) {
				continue;
			}
			filterList.add(dto);
		}
		return filterList;
	}
	
	public static ITreeNode filterData(DtoActivityFilter dtoFilter, ITreeNode root) {
		if(root == null) {
			return null;
		}
		
		DtoTreeNode dtoRoot = null;
		if(dtoFilter == null) {
			dtoFilter = getDefaultFilter();
		}
		//check wbs
		String wbs = dtoFilter.getWbs();
		if(wbs == null || "".equals(wbs)) {
			dtoRoot = new DtoTreeNode(root.getDataBean());
			getFilterNodes(dtoFilter, root, dtoRoot);
		} else {
			List<ITreeNode> nodeList = root.listAllChildrenByPreOrder();
			for(ITreeNode node : nodeList) {
				DtoMonitoring dto = (DtoMonitoring) node.getDataBean();
				if(DtoMonitoring.BEAN_TYPE_WBS.equals(dto.getBeanType())
						&& new Long(wbs).equals(dto.getWbsId())) {
					dtoRoot = new DtoTreeNode(dto);
					getFilterNodes(dtoFilter, node, dtoRoot);
					break;
				}
			}
		}
		return dtoRoot;
	}
	 
	private static void getFilterNodes(DtoActivityFilter dtoFilter, ITreeNode parentSrc, ITreeNode parentTag) {
		List<ITreeNode> children = parentSrc.children();
		for(ITreeNode child : children) {
			DtoMonitoring dto = (DtoMonitoring) child.getDataBean();
			if(DtoMonitoring.BEAN_TYPE_ACTIVITY.equals(dto.getBeanType()) 
					&& !checkActivity(dto, dtoFilter)) {
				continue;
			} else {
				ITreeNode node = new DtoTreeNode(dto);
				parentTag.addChild(node);
				getFilterNodes(dtoFilter, child, node);
			}
		}
		
	}
	
	/**
	 * 检查dto的状态，资源，名称是否符合要求
	 * @param dto IDtoActivityFilterData
	 * @param dtoFilter DtoActivityFilter
	 * @return boolean
	 */
	private static boolean checkActivity(IDtoActivityFilterData dto, DtoActivityFilter dtoFilter) {
		//check status
		if(!(dtoFilter.isNotStart() 
				&& DtoActivity.NOT_STARTED.equals(dto.getStatus())
				&& (dtoFilter.getStart() == null 
				|| WorkCalendar.resetEndDate(dtoFilter.getStart()).after(dto.getPlanStart())))
			&& !(dtoFilter.isInProgress() 
				&& DtoActivity.IN_PROGRESS.equals(dto.getStatus()))
			&& !(dtoFilter.isCompleted() 
				&& DtoActivity.COMPLETED.equals(dto.getStatus())
				&& (dtoFilter.getFinish() == null
				|| dtoFilter.getFinish().before(dto.getActualFinish())))) {
			return false;
		} 
		
		//check resource
		if(!dtoFilter.isCheckOthers()
			&& !(dtoFilter.isCheckManager() 
				&& (dtoFilter.getManager() == null
				|| "".equals(dtoFilter.getManager())
				|| dtoFilter.getManager().equalsIgnoreCase(dto.getManagerId())))
			&& !(dtoFilter.isCheckResource() 
				&& (dto.getResourceIds() == null 
				|| "".equals(dto.getResourceIds())
				|| dto.getResourceIds().toUpperCase().indexOf(dtoFilter.getResource().toUpperCase()) >= 0))) {
			return false;
		} 
		
		//check name
		String name = dtoFilter.getName();
		if(name != null 
				&& !"".equals(name) 
				&& dto.getName() != null 
				&& dto.getName().toUpperCase().indexOf(name.toUpperCase()) < 0) {
			return false;
		}
		return true;
	}

	public boolean onClickOK(ActionEvent e) {
		VWUtil.convertUI2Dto(dtoFilter, this);
		return true;
	}

	public boolean onClickCancel(ActionEvent e) {
		return true;
	}
	
	public static void main(String[] args) {
    	VwActivityFilter filter = new VwActivityFilter();
    	filter.setParameter(new Parameter());
    	filter.refreshWorkArea();
		VWJPopupEditor poputEditor = new VWJPopupEditor(null, "Filter",
				filter);
		poputEditor.showConfirm();
//    	TestPanel.show(filter);
    }
}
