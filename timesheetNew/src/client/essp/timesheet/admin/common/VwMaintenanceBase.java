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
 * <p>Description: ��������ࣺ</p>
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
     * ��ʼ��UI
     * @throws Exception
     */
    private void jbInit() throws Exception {
        vwList = getVwList();
        vwGeneral = getVwGeneral();
        this.getTopArea().addTab(getTopTabTitle(), vwList);
        this.getDownArea().addTab("rsid.common.general", vwGeneral);
    }

    /**
     * ��ȡ�б�Ƭʵ��
     * @return VwListBase
     */
    protected abstract VwListBase getVwList();

    /**
     * ��ȡ���ÿ�Ƭʵ��
     * @return VwListBase
     */
    protected abstract VwGeneralBase getVwGeneral();

    /**
     * ��ȡ�б�Ƭ����
     * @return VwListBase
     */
    protected abstract String getTopTabTitle();

    private void addUICEvent() {

        //����List��Ƭ��ѡ���¼�
        vwList.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting() == false) {
					processSelectRow();
				}
            }
        });

        //�������ÿ�Ƭ����ʱ��
        vwGeneral.addDataChangedListener(new DataChangedListener() {
            public void processDataChanged() {
                vwList.refeshTable();
            }
        });

    }

    /**
     * ����ѡ�����¼�
     */
    public void processSelectRow() {
        Parameter param = new Parameter();
        param.put(getDtoKey(), vwList.getTable().getSelectedData());
        vwGeneral.setParameter(param);
        vwGeneral.refreshWorkArea();
    }

    /**
     * ��ȡ����Dto��Key
     * @return String
     */
    protected abstract String getDtoKey();

    /**
     * ���������ݸ�List��Ƭ
     * @param parameter Parameter
     */
    public void setParameter(Parameter parameter) {
        vwList.setParameter(parameter);

    }

    /**
     * ��ˢ���¼����ݸ�List��Ƭ
     */
    public void refreshWorkArea() {
        vwList.refreshWorkArea();
    }


}
