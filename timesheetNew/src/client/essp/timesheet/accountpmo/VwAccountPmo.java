package client.essp.timesheet.accountpmo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import c2s.essp.timesheet.account.DtoAccount;
import client.essp.common.view.VWTDWorkArea;
import client.essp.timesheet.account.labor.VwLaborList;
import client.framework.view.common.comMSG;

import com.wits.util.Parameter;

public class VwAccountPmo extends VWTDWorkArea {
	private VwAccountListPmo vwAccountListPmo;
    private VwAccountGeneralPmo vwAccountGeneralPmo;
    private VwLaborList vwLaborList;
    
    private JButton saveBtn;
    
    public VwAccountPmo() {
    	super(300);
        try {
            jbInit();
        } catch (Exception e) {
            log.error(e);
        }
        addUICEvent();
    }
    /**
     * 界面初始化
     */
    private void jbInit() {
        vwAccountListPmo = new VwAccountListPmo();
        vwAccountGeneralPmo = new VwAccountGeneralPmo();
       
        vwLaborList = new VwLaborList();

        this.getTopArea().addTab("rsid.timesheet.tsAccountPmo", vwAccountListPmo);
        this.getDownArea().addTab("rsid.common.general", vwAccountGeneralPmo);
        this.getDownArea().addTab("rsid.timesheet.laborResource", vwLaborList);
    }

    /**
     * 事件处理
     */
    private void addUICEvent() {
    	
        vwAccountListPmo.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting() == false) {
					processSelectedProjectChanged();
				}
            }
        });
        //save
        saveBtn = vwAccountGeneralPmo.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processSaveAccountGeneral();
            }
        });
        saveBtn.setToolTipText("rsid.common.save");
        saveBtn.setEnabled(false);
        
       
        
    }
    
    private void processSelectedProjectChanged() {
		DtoAccount dtoProject = (DtoAccount) vwAccountListPmo.getTable()
				.getSelectedData();
		Long projectRid = null;
		if (dtoProject != null) {
			projectRid = dtoProject.getRid();
			saveBtn.setEnabled(true);
		} else {
			saveBtn.setEnabled(false);
		}
		Parameter param = new Parameter();
		param.put(DtoAccount.DTO, dtoProject);
		vwAccountGeneralPmo.setParameter(param);
		param = new Parameter();
		param.put(DtoAccount.DTO_RID, projectRid);
		vwLaborList.setParameter(param);
		this.getDownArea().getSelectedWorkArea().refreshWorkArea();

	}
    private void processSaveAccountGeneral() {
    	if(vwAccountGeneralPmo.processSaveAccountGeneral()) {
	    	DtoAccount dtoProject = (DtoAccount) vwAccountListPmo.getTable()
														.getSelectedData();
	    	DtoAccount dto = vwAccountGeneralPmo.getDtoAccount();
	    	dtoProject.setCodeTypeRid(dto.getCodeTypeRid());
	    	dtoProject.setCodeType(dto.getCodeType());
	    	vwAccountListPmo.repaint();
	    	comMSG.dispMessageDialog("rsid.common.saveComplete");
    	}
    }
	public void setParameter(Parameter param) {
		vwAccountListPmo.setParameter(param);
		
	}
	public void refreshWorkArea() {
		super.refreshWorkArea();
		vwAccountListPmo.refreshWorkArea();
		DtoAccount dtoProject = (DtoAccount) vwAccountListPmo.getTable()
				.getSelectedData();
		Long projectRid = null;
		if (dtoProject != null) {
			projectRid = dtoProject.getRid();
		}
		Parameter param = new Parameter();
		param.put(DtoAccount.DTO, dtoProject);
		vwAccountGeneralPmo.setParameter(param);
		param = new Parameter();
		param.put(DtoAccount.DTO_RID, projectRid);
		vwLaborList.setParameter(param);
		this.getDownArea().getSelectedWorkArea().refreshWorkArea();
	}

    
}
