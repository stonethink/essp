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
 * <p>Description: 工时审批主界面抽象类</p>
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
     * 初始化UI
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
     * 返回VwAbstractApprovalList实例
     * @return VwAbstractApprovalList
     */
    protected abstract VwAbstractApprovalList getApprovalList();
    /**
     * 返回不同情况下构造的VwWorkGeneral
     * @return VwWorkGeneral
     */
    protected abstract VwWorkGeneral getWorkGeneral();

    /**
     * 事件处理
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
     * vwApprovalList选中一行，刷新General卡片
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
      * 设置参数，传递给vwApprovalList
      * @param param Parameter
      */
     public void setParameter(Parameter param) {
    	 needRefresh = true;
         super.setParameter(param);
         vwWorkGenral.setParameter(param);
     }
     /**
      * 刷新事件，传递给vwApprovalList
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
