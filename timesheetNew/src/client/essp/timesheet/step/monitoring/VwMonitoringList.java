/*
 * Created on 2008-5-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.step.monitoring;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;

import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.step.DtoActivityFilter;
import c2s.essp.timesheet.step.monitoring.DtoMonitoring;
import c2s.essp.timesheet.step.monitoring.DtoMonitoringQuery;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.excelUtil.VWJSExporterUtil;
import client.essp.common.view.VWTreeTableWorkArea;
import client.essp.timesheet.common.StatusNodeViewManagerProxy;
import client.essp.timesheet.step.VwActivityFilter;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMComboBox;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.NodeViewManager;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import client.image.ComImage;
import com.wits.util.Parameter;

/**
 * <p>Title: </p>
 *
 * <p>Description: VwMonitoringList</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author tubaohui
 * @version 1.0
 */
public class VwMonitoringList extends VWTreeTableWorkArea {
	
    	public static ImageIcon WBS_ICON = new ImageIcon(ComImage.getImage("wbs.png"));
    	public static ImageIcon ACTIVITY_ICON = new ImageIcon(ComImage.getImage("activity.png"));
    	public static ImageIcon STEP_ICON = new ImageIcon();

        private final static String actionIdLoadProject = "FTSLoadProject";
        private final static String actionIdMLoadInfo = "FTSMonitoringLoadInfo";
        private static final String actionIdExporter = "FTSMonitoringExporter";
        private static final String actionIdFilterTreeNode = "FTSGetFilterTreeNode";
        private VwActivityFilter vwFilter;
    	private DtoActivityFilter dtoFilter;
        private JButton refBtn = null;
        public JButton btnDel = null;
        public JButton filterBtn;
        public JButton expBtn;
        private VWJComboBox projectComb;
        private ITreeNode treeNode;
        public boolean isNotStart = true;
        public boolean isInProgress = true;
        public boolean isCompleted = false;
        private boolean isSelectProject = false;
        private String currentProjectId = null;
        public String projectId = null;
        public Vector acntItem;
        String projectName = "";
    
        public VwMonitoringList() {
            try {
                jbInit();
            } catch (Exception e) {
                log.error(e);
            }
            addUICEvent();
        }
    
        /**
         * 初始化UI
         * @throws Exception
         */
        public void jbInit() throws Exception {
            VWJReal real = new VWJReal();
            real.setMaxInputDecimalDigit(2);
            Object[][] configs = new Object[][] { 
                                 {"rsid.timesheet.WBS/ActivityName", "name",VMColumnConfig.EDITABLE, null,Boolean.FALSE},
                                 {"rsid.timesheet.category", "stepName",VMColumnConfig.UNEDITABLE, new VWJText(),Boolean.FALSE},
                                 {"rsid.timesheet.categoryDes", "categoryDes",VMColumnConfig.UNEDITABLE, new VWJText(),Boolean.TRUE},
                                 {"rsid.timesheet.statusIndicator", "statusIndicator", VMColumnConfig.UNEDITABLE, new VWJText(),Boolean.FALSE},
                                 {"rsid.timesheet.status", "status", VMColumnConfig.UNEDITABLE, new VWJText(),Boolean.TRUE},
                                 {"rsid.timesheet.completePct", "complete", VMColumnConfig.UNEDITABLE, new VWJText(),Boolean.FALSE},
                                 {"rsid.timesheet.resourceName", "resourceNames",VMColumnConfig.UNEDITABLE, new VWJText(),Boolean.FALSE},
                                 {"rsid.timesheet.resourceIds", "resourceIds",VMColumnConfig.UNEDITABLE, new VWJText(),Boolean.TRUE},
                                 {"rsid.timesheet.primaryResource", "managerName",VMColumnConfig.UNEDITABLE, new VWJText(),Boolean.TRUE},
                                 {"rsid.timesheet.primaryResourceId", "managerId",VMColumnConfig.UNEDITABLE, new VWJText(),Boolean.TRUE},
                                 {"rsid.timesheet.planStart", "planStart",VMColumnConfig.UNEDITABLE, new VWJDate(),Boolean.FALSE}, 
                                 {"rsid.timesheet.planFinish", "planFinish",VMColumnConfig.UNEDITABLE, new VWJDate(),Boolean.FALSE},   
                                 {"rsid.timesheet.planHours", "planHours", VMColumnConfig.UNEDITABLE, real,Boolean.FALSE},
                                 {"rsid.timesheet.actualStart", "actualStart", VMColumnConfig.UNEDITABLE, new VWJDate(),Boolean.FALSE},
                                 {"rsid.timesheet.actualFinish", "actualFinish", VMColumnConfig.UNEDITABLE,new VWJDate(),Boolean.FALSE},
                                 {"rsid.timesheet.actualHours", "actualHours",VMColumnConfig.UNEDITABLE, real,Boolean.FALSE}
                  };
            
            super.jbInit(configs, "name", DtoMonitoring.class, new StatusNodeViewManagerProxy(new BeanTypeNodeViewManager(), "statusIndicator"));
            this.treeTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
            vwFilter = new VwActivityFilter();
            projectComb = new VWJComboBox();
        }

        /**
         * 注册UI事件
         */
        private void addUICEvent() {
            this.getButtonPanel().add(projectComb);
            JLabel blankLabel = new JLabel("           ");
            blankLabel.setSize(100, 20);
            this.getButtonPanel().add(blankLabel);
            
            
            //過濾
            filterBtn = getButtonPanel().addButton("filter.gif");
            filterBtn.setToolTipText("rsid.common.filter");
            filterBtn.addActionListener(new ActionListener(){
    
             public void actionPerformed(ActionEvent e) {
            	 actionPerformedFilter();
                }
            });
            
            //導出
            expBtn = getButtonPanel().addButton("export.png");
            expBtn.setToolTipText("rsid.common.export");
            expBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    processExport();
                }
             });
            
            //Display
            TableColumnChooseDisplay chooseDisplay =
                    new TableColumnChooseDisplay(this.getTreeTable(), this);
            JButton button = chooseDisplay.getDisplayButton();
            this.getButtonPanel().addButton(button);
            
            //expand
            JButton expandBtn = this.getButtonPanel().addButton("expand.png");
            expandBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    getTreeTable().expandsRow();
                }
            });
            expandBtn.setToolTipText("rsid.common.expand");
            
            //刷新
            refBtn = this.getButtonPanel().addButton("refresh.png");
            refBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    resetUI();
                }
            });
            refBtn.setToolTipText("rsid.common.refresh");
            
            //
            projectComb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    currentProjectId =  (String)projectComb.getUICValue();
                    isSelectProject = true;
                    dtoFilter.setWbs(null);
                    resetUI();
                }
            });
        }
        
        /**
    	 * 过滤Activity
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
                	ITreeNode filterNode = VwActivityFilter.filterData(dtoFilter, treeNode);
                	this.getTreeTable().setRoot(filterNode);
                    InputInfo inputInfoFilter = new InputInfo();
                    inputInfoFilter.setActionId(this.actionIdFilterTreeNode);
                    inputInfoFilter.setInputObj(DtoMonitoringQuery.DTO_TREE_NODE,
                            filterNode);         
                    inputInfoFilter.setInputObj(DtoMonitoringQuery.DTO_PROJECT_NAME,
                           projectName); 
                    accessData(inputInfoFilter);
                }
    	}
    
        /**
         * 导出报表
         */
        protected void processExport() {
                 projectId = currentProjectId;
                 Map<String, String> param = new HashMap<String, String>();
                 if(checkError(projectId)){
                    return;
                 }         
                VWJSExporterUtil.excuteJSExporter(actionIdExporter, param);
        }
        
        /**
         * 校验开始和结束时间
         * @param dtoAtt
         * @return boolean
         */
        private boolean checkError(String projectId) {
               if(projectId== null){
                   comMSG.dispErrorDialog("rsid.common.select.project");
                   return true;
               }
               return false;
        }
    
        /**
         * 传递参数
         * @param param Parameter
         */
        public void setParameter(Parameter param) {
            super.setParameter(param);
        }
    
        //重置
        protected void resetUI() {
               
            	if(dtoFilter == null) {
        			dtoFilter = VwActivityFilter.getDefaultFilter();
        		}
                if(!isSelectProject){
                 InputInfo acntInput = new InputInfo();
                 acntInput.setActionId(actionIdLoadProject);
                 Vector projectList = (Vector)accessData(acntInput).
                 getReturnObj(DtoMonitoringQuery.DTO_PROJECT_LIST);
                 projectComb.setModel(new VMComboBox(projectList));
                 currentProjectId = (String)projectComb.getUICValue();
                }
                projectId =currentProjectId;
                InputInfo inputInfo = new InputInfo();
                inputInfo.setActionId(this.actionIdMLoadInfo);
                inputInfo.setInputObj(DtoMonitoringQuery.DTO_PROJECT_ID,
                        projectId);           
                ReturnInfo returnInfo = accessData(inputInfo);
                if (returnInfo.isError() == false) {
                    treeNode = (ITreeNode) returnInfo.
                            getReturnObj(DtoMonitoringQuery.DTO_TREE_NODE);
                    projectName = (String)returnInfo.getReturnObj(DtoMonitoringQuery.
                            DTO_PROJECT_NAME);
//                  reset vwFilter parameters
            		Parameter param = new Parameter();
            		param.put(DtoActivityFilter.DTO_KEY, dtoFilter);
            		param.put(DtoActivityFilter.TREE_NODE_KEY, treeNode);
            		vwFilter.setParameter(param);
                }
                this.getTreeTable().setRoot(treeNode);
                ITreeNode filterNode = VwActivityFilter.filterData(dtoFilter, treeNode);
            	this.getTreeTable().setRoot(filterNode);
                
                InputInfo inputInfoFilter = new InputInfo();
                inputInfoFilter.setActionId(this.actionIdFilterTreeNode);
                inputInfoFilter.setInputObj(DtoMonitoringQuery.DTO_TREE_NODE,
                        filterNode);         
                inputInfoFilter.setInputObj(DtoMonitoringQuery.DTO_PROJECT_NAME,
                       projectName); 
                accessData(inputInfoFilter);
        }
        
        public class BeanTypeNodeViewManager extends NodeViewManager {
        	public Color getOddBackground() {
        		return getBackground();
            }

            public Color getEvenBackground() {
            	return getBackground();
            }

			public Color getBackground() {
				ITreeNode node = this.getNode();
        		if(node == null) {
        			return super.getEvenBackground();
        		}
        		Object bean = node.getDataBean();
        		if(bean instanceof DtoMonitoring) {
        			DtoMonitoring dto  = (DtoMonitoring) bean;
        			if(DtoMonitoring.BEAN_TYPE_WBS.equals(dto.getBeanType())) {
        				return new Color(153, 204, 255);
        			} else if(DtoMonitoring.BEAN_TYPE_ACTIVITY.equals(dto.getBeanType())) {
        				return new Color(204, 255, 204);
        			}
        		}
				return super.getBackground();
			}

			public Icon getIcon() {
				if(!"name".equals(this.getDataName())) {
					return super.getIcon();
				}
				ITreeNode node = this.getNode();
        		if(node == null) {
        			return super.getIcon();
        		}
				Object bean = node.getDataBean();
        		if(bean instanceof DtoMonitoring) {
        			DtoMonitoring dto  = (DtoMonitoring) bean;
        			if(DtoMonitoring.BEAN_TYPE_WBS.equals(dto.getBeanType())) {
        				return WBS_ICON;
        			} else if(DtoMonitoring.BEAN_TYPE_ACTIVITY.equals(dto.getBeanType())) {
        				return ACTIVITY_ICON;
        			} else {
        				return STEP_ICON;
        			}
        		}
				return super.getIcon();
			}
        }
}
