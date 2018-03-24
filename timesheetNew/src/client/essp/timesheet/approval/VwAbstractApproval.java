package client.essp.timesheet.approval;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import c2s.essp.timesheet.approval.DtoTsApproval;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import client.essp.common.view.VWTDWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.timesheet.approval.pm.VwPmApprovalList;
import client.essp.timesheet.approval.rm.VwRmApprovalList;
import client.framework.view.event.RowSelectedListener;

import com.wits.util.Parameter;

/**
 * <p>Title: VwAbstractApproval</p>
 *
 * <p>Description: ��ʱ���������������</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public abstract class VwAbstractApproval extends VWTDWorkArea {
    private VwAbstractApprovalList vwApprovalList;
    private VwApprovalGeneral vwGeneral;
    private VwWorkGeneral vwWorkGenral;
    private boolean needRefresh = false;
    public VwAbstractApproval() {
        super(300);
        try {
            jbInit();
            addUICEvent();
        } catch (Exception e) {
            log.error(e);
        }

    }

    /**
     * ��ʼ��UI
     * @throws Exception
     */
    private void jbInit() throws Exception {
        vwApprovalList = getApprovalList();
        vwGeneral = new VwApprovalGeneral();
        vwWorkGenral = getWorkGeneral();
        VWWorkArea w = new VWWorkArea();
        w.setLayout(new BorderLayout());
        vwWorkGenral.setPreferredSize(new Dimension(0, 70));
        w.add(vwWorkGenral, BorderLayout.NORTH);
        w.add(vwApprovalList, BorderLayout.CENTER);
        String topTitle = "";
        if (vwApprovalList instanceof VwPmApprovalList) {
        	topTitle = "rsid.timesheet.pmApproval";
		} else if(vwApprovalList instanceof VwRmApprovalList) {
			topTitle = "rsid.timesheet.rmApproval";
		}
        this.getTopArea().addTab(topTitle, w);
        this.getTopArea().setButtonPanel(vwApprovalList.getButtonPanel());
        this.getDownArea().addTab("rsid.common.general", vwGeneral);
    }

    /**
     * ����VwAbstractApprovalListʵ��
     * @return VwAbstractApprovalList
     */
    protected abstract VwAbstractApprovalList getApprovalList();
    /**
     * ���ز�ͬ����¹����VwWorkGeneral
     * @return VwWorkGeneral
     */
    protected abstract VwWorkGeneral getWorkGeneral();

    /**
     * �¼�����
     */
    private void addUICEvent() {
        vwApprovalList.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting() == false) {
					processSelectedTs();
				}
            }
        });

        vwWorkGenral.addAccountOrPeriodListeners(vwApprovalList);
    }

    /**
     * vwApprovalListѡ��һ�У�ˢ��General��Ƭ
     */
    private void processSelectedTs() {
        Parameter para = new Parameter();
        if(vwApprovalList.getSelectedData() != null){
            DtoTsApproval dtoApproval = (DtoTsApproval)vwApprovalList
                                        .getSelectedData();
            para.put(DtoTimeSheet.DTO_RID, dtoApproval.getTsRid());
        }

        vwGeneral.setParameter(para);
        vwGeneral.refreshWorkArea();
    }
    /**
      * ���ò��������ݸ�vwApprovalList
      * @param param Parameter
      */
     public void setParameter(Parameter param) {
    	 needRefresh = true;
         super.setParameter(param);
         vwWorkGenral.setParameter(param);
     }
     /**
      * ˢ���¼������ݸ�vwApprovalList
      */
     public void refreshWorkArea() {
    	 resetUI();
     }
     
     private void resetUI() {
    	 if(needRefresh) {
    		 vwWorkGenral.refreshWorkArea();
    		 vwApprovalList.setParameter(vwWorkGenral.getPara());
    		 vwApprovalList.refreshWorkArea();
    		 needRefresh  = false;
    	 }
     }
    
}
