package client.essp.timesheet.admin.common;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.essp.common.view.VWTDWorkArea;
import client.framework.view.event.DataChangedListener;
import client.framework.view.event.RowSelectedListener;
import com.wits.util.Parameter;

/**
 * <p>Title: VwMaintenanceBase</p>
 *
 * <p>Description: 主界面基类：</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public abstract class VwMaintenanceBase extends VWTDWorkArea {

    private VwListBase vwList;
    private VwGeneralBase vwGeneral;

    public VwMaintenanceBase() {
        super(300);
        try {
            jbInit();
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
        vwList = getVwList();
        vwGeneral = getVwGeneral();
        this.getTopArea().addTab(getTopTabTitle(), vwList);
        this.getDownArea().addTab("rsid.common.general", vwGeneral);
    }

    /**
     * 获取列表卡片实例
     * @return VwListBase
     */
    protected abstract VwListBase getVwList();

    /**
     * 获取常用卡片实例
     * @return VwListBase
     */
    protected abstract VwGeneralBase getVwGeneral();

    /**
     * 获取列表卡片标题
     * @return VwListBase
     */
    protected abstract String getTopTabTitle();

    private void addUICEvent() {

        //监听List卡片行选中事件
        vwList.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting() == false) {
					processSelectRow();
				}
            }
        });

        //监听常用卡片保存时间
        vwGeneral.addDataChangedListener(new DataChangedListener() {
            public void processDataChanged() {
                vwList.refeshTable();
            }
        });

    }

    /**
     * 处理选中行事件
     */
    public void processSelectRow() {
        Parameter param = new Parameter();
        param.put(getDtoKey(), vwList.getTable().getSelectedData());
        vwGeneral.setParameter(param);
        vwGeneral.refreshWorkArea();
    }

    /**
     * 获取传递Dto的Key
     * @return String
     */
    protected abstract String getDtoKey();

    /**
     * 将参数传递给List卡片
     * @param parameter Parameter
     */
    public void setParameter(Parameter parameter) {
        vwList.setParameter(parameter);

    }

    /**
     * 将刷新事件传递给List卡片
     */
    public void refreshWorkArea() {
        vwList.refreshWorkArea();
    }


}
