package client.essp.timesheet.admin.code;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import c2s.essp.timesheet.code.DtoCodeType;
import client.essp.common.view.VWTDWorkArea;
import client.framework.view.event.DataChangedListener;
import client.framework.view.event.RowSelectedListener;
import com.wits.util.Parameter;
import client.essp.timesheet.admin.code.codetype.VwCodeTypeList;
import client.essp.timesheet.admin.code.codetype.VwCodeTypeGeneral;
import client.essp.timesheet.admin.code.relation.VwCodeRelationList;

/**
 * <p>Title: VwCodeMaintenance</p>
 *
 * <p>Description: Code type 维护作业主界面</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwCodeMaintenance extends VWTDWorkArea {

    private VwCodeTypeList vwCodeTypeList;
    private VwCodeTypeGeneral vwCodeTypeGeneral;
    private VwCodeRelationList vwCodeRelationList;

    public VwCodeMaintenance() {
        super(300);
        try {
                jbInit();
            } catch (Exception ex) {
                log.error(ex);
            }
            addUICEvent();
        }

        /**
         * 初始化页面
         */
        private void jbInit() {
            vwCodeTypeList = new VwCodeTypeList();
            vwCodeTypeGeneral = new VwCodeTypeGeneral();
            vwCodeRelationList = new VwCodeRelationList();
            this.getTopArea().addTab("rsid.timesheet.jobCodeType", vwCodeTypeList);
            this.getDownArea().addTab("rsid.common.general", vwCodeTypeGeneral);
            this.getDownArea().addTab("rsid.timesheet.jobCode", vwCodeRelationList);
        }

        /**
         * 事件
         */
        private void addUICEvent() {
            vwCodeTypeList.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
    			public void valueChanged(ListSelectionEvent e) {
    				if(e.getValueIsAdjusting() == false) {
    					processCodeValueSelected();
    				}
                }
            });

            vwCodeTypeGeneral.addDataChangedListener(new DataChangedListener() {
                public void processDataChanged() {
                	processCodeValueSelected();
                }
            });
        }

        /**
         * 选中行处理
         */
        private void processCodeValueSelected() {
            Parameter param = new Parameter();
            DtoCodeType data = (DtoCodeType) vwCodeTypeList.getSelectedData();
            if(data == null || data.isInsert()) {
            	this.getDownArea().setSelectedIndex(0);
            	this.getDownArea().enableTabOnly(0);
            } else {
            	this.getDownArea().enableAllTabs();
            }
            param.put(DtoCodeType.DTO, data);
            param.put(DtoCodeType.DTO_IS_LEAVE_TYPE, false);
            vwCodeTypeGeneral.setParameter(param);
            vwCodeRelationList.setParameter(param);
            this.getDownArea().getSelectedWorkArea().refreshWorkArea();
        }

        /**
         * 传递参数
         * @param param Parameter
         */
        public void setParameter(Parameter param) {
        	param = new Parameter();
    		param.put(DtoCodeType.DTO_IS_LEAVE_TYPE, false);
            vwCodeTypeList.setParameter(param);
        }

        /**
         * 传递刷新
         */
        public void refreshWorkArea() {
            vwCodeTypeList.refreshWorkArea();
        }

}
