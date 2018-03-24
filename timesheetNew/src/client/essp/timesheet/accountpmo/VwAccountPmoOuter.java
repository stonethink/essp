package client.essp.timesheet.accountpmo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import c2s.essp.timesheet.account.DtoAccount;
import client.essp.common.view.VWTDWorkArea;
import client.framework.view.vwcomp.VWUtil;

import com.wits.util.Parameter;

public class VwAccountPmoOuter extends VWTDWorkArea {
	private VwAccountQueryPmo vwAccountQueryPmo;
	private VwAccountPmo vwAccountPmo;
	private JButton queryBtn = new JButton();
//	private JButton clearBtn = new JButton();
	
	public VwAccountPmoOuter() {
		super(150);
		try {
            jbInit();
        } catch (Exception e) {
            log.error(e);
        }
        addUICEvent();
	}
	private void jbInit() {
		vwAccountQueryPmo = new VwAccountQueryPmo();
		vwAccountPmo = new VwAccountPmo();
		this.getTopArea().addTab("rsid.timesheet.tsAccountQuery", vwAccountQueryPmo);
		this.getDownArea().addPanel(vwAccountPmo);
	}
	/**
     * 事件处理
     */
    private void addUICEvent() {
    	vwAccountQueryPmo.getButtonPanel().addButton(queryBtn);
    	queryBtn.setText("rsid.timesheet.query");
    	queryBtn.setToolTipText("rsid.timesheet.query");
        queryBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processQuery();
            }
        });
        
//        vwAccountQueryPmo.getButtonPanel().addButton(clearBtn);
//        clearBtn.setText("rsid.timesheet.clear");
//        clearBtn.setToolTipText("rsid.timesheet.clear");
//        clearBtn.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//            	processClear();
//            }
//        });
        
    }
//    private void processClear() {
//    	VWUtil.clearUI(vwAccountQueryPmo);
//    }
    private void processQuery() {
    	DtoAccount dto = vwAccountQueryPmo.getQueryCondition();
    	Parameter param = new Parameter();
    	param.put(DtoAccount.DTO_CONDITION, dto);
    	vwAccountPmo.setParameter(param);
    	vwAccountPmo.refreshWorkArea();
	
    }
	public void setParameter(Parameter param) {
		param.put(DtoAccount.DTO_CONDITION, null);
		vwAccountPmo.setParameter(param);
    	vwAccountPmo.refreshWorkArea();
	}
    
}
