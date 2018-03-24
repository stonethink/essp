package client.essp.timesheet.account;

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

/**
 * <p>Title: View of Account</p>
 *
 * <p>Description: 项目视图：包含项目列表，项目基本信息卡片，项目人力资源信息卡片</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwAccount extends VWTDWorkArea {

    private VwAccountList vwAccountList;
    private VwAccountGeneral vwAccountGeneral;
    private VwLaborList vwLaborList;
    private JButton saveBtn;

    public VwAccount() {
        super(200);
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
        vwAccountList = new VwAccountList();
        vwAccountGeneral = new VwAccountGeneral();

        vwLaborList = new VwLaborList();
        

        this.getTopArea().addTab("rsid.timesheet.tsAccount", vwAccountList);
        this.getDownArea().addTab("rsid.common.general", vwAccountGeneral);
        this.getDownArea().addTab("rsid.timesheet.laborResource", vwLaborList);
    }

    /**
     * 事件处理
     */
    private void addUICEvent() {
        vwAccountList.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting() == false) {
					processSelectedProjectChanged();
				}
            }
        });
        //save
        saveBtn = vwAccountGeneral.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processSaveAccountGeneral();
            }
        });
        saveBtn.setToolTipText("rsid.common.save");
        saveBtn.setEnabled(false);
    }
    private void processSaveAccountGeneral() {
    	if(vwAccountGeneral.processSaveAccountGeneral()) {
	    	DtoAccount dtoProject = (DtoAccount) vwAccountList.getTable()
					.getSelectedData();
			DtoAccount dto = vwAccountGeneral.getDtoAccount();
			dtoProject.setCodeTypeRid(dto.getCodeTypeRid());
			dtoProject.setCodeType(dto.getCodeType());
			vwAccountList.repaint();
			comMSG.dispMessageDialog("rsid.common.saveComplete");
    	}
    }

    /**
     * 激活刷新
     * @param param Parameter
     */
    public void setParameter(Parameter param) {
        vwAccountList.setParameter(param);
    }

    public void refreshWorkArea() {
		vwAccountList.refreshWorkArea();
		DtoAccount dtoProject = (DtoAccount) vwAccountList.getTable()
				.getSelectedData();
		Long projectRid = null;
		if (dtoProject != null) {
			projectRid = dtoProject.getRid();
		} 

		Parameter param = new Parameter();
		param.put(DtoAccount.DTO, dtoProject);
		vwAccountGeneral.setParameter(param);
		param = new Parameter();
		param.put(DtoAccount.DTO_RID, projectRid);
		vwLaborList.setParameter(param);
		this.getDownArea().getSelectedWorkArea().refreshWorkArea();
	}


    private void processSelectedProjectChanged() {
        DtoAccount dtoProject = (DtoAccount) vwAccountList.getTable().
                                getSelectedData();
        Long projectRid = null;
        if (dtoProject != null) {
            projectRid = dtoProject.getRid();
            saveBtn.setEnabled(true);
        } else {
        	saveBtn.setEnabled(false);
        }
        Parameter param = new Parameter();
        param.put(DtoAccount.DTO, dtoProject);
        vwAccountGeneral.setParameter(param);
        param = new Parameter();
        param.put(DtoAccount.DTO_RID, projectRid);
        vwLaborList.setParameter(param);
        this.getDownArea().getSelectedWorkArea().refreshWorkArea();
    }

}
