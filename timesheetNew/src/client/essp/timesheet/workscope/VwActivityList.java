package client.essp.timesheet.workscope;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import c2s.essp.timesheet.workscope.DtoActivity;
import c2s.essp.timesheet.workscope.DtoActivityFilter;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.TableUitl.TableColumnWidthSetter;
import client.essp.common.view.VWTableWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;
import client.framework.view.vwcomp.NodeViewManager;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWUtil;

import com.wits.util.Parameter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;

import c2s.essp.timesheet.activity.DtoActivityKey;
import client.essp.timesheet.ActivityChangedListener;
import client.framework.view.event.RowSelectedListener;
/**
 * <p>Title:VwActivityList </p>
 *
 * <p>Description: ActivityList卡片</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public class VwActivityList extends VWTableWorkArea {
    private List<ActivityChangedListener> activityChangedListeners
            = new ArrayList<ActivityChangedListener>();
    private List activityList = new ArrayList();
    private DtoActivityFilter dtoFilter = new DtoActivityFilter();
    private VwFilter vwFilter = new VwFilter();

    public VwActivityList() {
        Object[][] configs = new Object[][] { {"rsid.timesheet.wBSName",
        					"wbsName", VMColumnConfig.UNEDITABLE,
        					new VWJText(),Boolean.TRUE},
        					{"rsid.common.name",
                             "showName", VMColumnConfig.UNEDITABLE,
                             new VWJText(),Boolean.FALSE},
                             {"rsid.common.role",
                                 "roleName", VMColumnConfig.UNEDITABLE,
                                 new VWJText(),Boolean.TRUE},
                             {"rsid.timesheet.assignStart",
                                  "PlannedStartDate", VMColumnConfig.UNEDITABLE,
                                  new VWJDate(),Boolean.TRUE},
                              {"rsid.timesheet.assignFinish",
                                  "PlannedFinishDate", VMColumnConfig.UNEDITABLE,
                                  new VWJDate(),Boolean.TRUE}
        };
        try {
            model = new VMTable2(configs);
            model.setDtoType(DtoActivity.class);
            table = new VWJTable(model, new ActivityNodeViewManager());
            TableColumnWidthSetter.set(table);
            table.setSortable(true);

            this.add(table.getScrollPane(), null);
            //不显示表头
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
    	
    	JButton btnFilter = this.getButtonPanel().addButton("filter.gif");
    	btnFilter.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
		        VWJPopupEditor poputEditor = new VWJPopupEditor(getParentWindow(), "Filter",
		                vwFilter);
		        VWUtil.bindDto2UI(dtoFilter, vwFilter);
		        int result = poputEditor.showConfirm();
		        if(Constant.OK == result) {
		        	VWUtil.convertUI2Dto(dtoFilter, vwFilter);
		        	resetUI();
		        }
			}
    		
    	});
    	
    	//    	Display
        TableColumnChooseDisplay chooseDisplay =
                new TableColumnChooseDisplay(this.getTable(), this);
        JButton button = chooseDisplay.getDisplayButton();
        this.getButtonPanel().addButton(button);
        
        this.getTable().getColumnModel().addColumnModelListener(new TableColumnModelListener() {

			public void columnAdded(TableColumnModelEvent e) {
				if(getTable().getColumnModel().getColumnCount() > 1) {
					getTable().getTableHeader().setPreferredSize(new Dimension(100, 22));
				}
				
			}

			public void columnRemoved(TableColumnModelEvent e) {
				if(getTable().getColumnModel().getColumnCount() == 1) {
					getTable().getTableHeader().setPreferredSize(new Dimension(100, 0));
				}
			}

			public void columnMoved(TableColumnModelEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void columnMarginChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void columnSelectionChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        this.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()) {
					return;
				}
                DtoActivity dto = (DtoActivity) getSelectedData();
                if(dto != null) {
                    fireactivityChanged(new Long(dto.getId()));
                }
            }
        });

    }


    //参数设置
    public void setParameter(Parameter p) {
        activityList = (List) p.get(DtoActivityKey.ACTIVITY_LIST);
        super.setParameter(p);
    }

    //重置
    protected void resetUI() {
        List dataList = getFilterData();
        getTable().setRows(dataList);
        if (dataList != null && dataList.size() > 0) {
            getTable().setSelectRow(0);
        }
        fireDataChanged();
    }
    
    private List getFilterData() {
    	if(activityList == null) {
            return activityList;
        }
    	List filterList = new ArrayList();
    	for(int i = 0; i < activityList.size(); i ++) {
    		DtoActivity dto = (DtoActivity)activityList.get(i);
    		if(dto == null) {
    			continue;
    		} else if((dtoFilter.isNotStart() && dto.isNotStart())
    				|| (dtoFilter.isInProgress() && dto.isInProgress())
    				|| (dtoFilter.isCompleted() && dto.isCompleted())) {
    			filterList.add(dto);
    		}
    	}
    	return filterList;
    }

    public void addActivityChangedListener(ActivityChangedListener l) {
        activityChangedListeners.add(l);
    }

    private void fireactivityChanged(Long activityRid) {
        for(ActivityChangedListener l : activityChangedListeners) {
            l.processActivityChanged(activityRid);
        }
    }
    
    public class ActivityNodeViewManager extends NodeViewManager {
    	public Color getOddBackground() {
            if(this.getDto() instanceof DtoActivity) {
            	DtoActivity dto = (DtoActivity) this.getDto();
            	if(dto.isPrimaryResource()) {
            		return Color.PINK;
            	}
            }
            return super.getOddBackground();
        }

        public Color getEvenBackground() {
        	if(this.getDto() instanceof DtoActivity) {
            	DtoActivity dto = (DtoActivity) this.getDto();
            	if(dto.isPrimaryResource()) {
            		return Color.PINK;
            	}
            }
        	return super.getEvenBackground();
        }

        public Color getSelectBackground() {
        	if(this.getDto() instanceof DtoActivity) {
            	DtoActivity dto = (DtoActivity) this.getDto();
            	if(dto.isPrimaryResource()) {
            		return new Color(50, 175, 175);
            	}
            }
        	return super.getSelectBackground();
        }
    }

}
