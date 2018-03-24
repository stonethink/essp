package client.essp.timesheet.tsmodify;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import c2s.essp.timesheet.approval.DtoTsApproval;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import c2s.essp.timesheet.workscope.DtoAccount;
import client.essp.common.view.VWTDWorkArea;

import com.wits.util.Parameter;

public class VwTsModifyTop extends VWTDWorkArea {
	
	private VwTsModifyList vwTsModifyList;
	private VwTsModifyGeneral vwTsModifyGeneral;
	
	public VwTsModifyTop() {
		super(220);
		try {
            jbInit();
        } catch (Exception e) {
            log.error(e);
        }
        addUICEvent();
	}
	private void jbInit() {
		vwTsModifyList = new VwTsModifyList();
		vwTsModifyGeneral = new VwTsModifyGeneral();
		
		this.getTopArea().addTab("rsid.timesheet.tsModify.tsList", vwTsModifyList);
		this.getDownArea().add(vwTsModifyGeneral);
		
	}
	private void addUICEvent() {
		vwTsModifyList.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting() == false) {
					processSelected();
				}
			}
		});
	}
	private void processSelected() {
		DtoTsApproval dto = (DtoTsApproval) vwTsModifyList.getTable().getSelectedData();
		Long rid = null;
		String loginId = "";
		if(dto != null) {
			rid = dto.getTsRid();
			loginId = dto.getLoginId();
		}
		Parameter para = new Parameter();
		para.put(DtoTimeSheet.DTO_RID, rid);
		para.put(DtoAccount.SCOPE_LOGIN_ID, loginId);
		vwTsModifyGeneral.setParameter(para);
		vwTsModifyGeneral.refreshWorkArea();
	}

	public void setParameter(Parameter param) {
		vwTsModifyList.setParameter(param);
	}

	public void refreshWorkArea() {
		super.refreshWorkArea();
		vwTsModifyList.refreshWorkArea();
		DtoTsApproval dto = (DtoTsApproval) vwTsModifyList.getTable().getSelectedData();
		Long rid = null;
		String loginId = "";
		if(dto != null) {
			rid = dto.getTsRid();
			loginId = dto.getLoginId();
		}
		Parameter para = new Parameter();
		para.put(DtoTimeSheet.DTO_RID, rid);
		para.put(DtoAccount.SCOPE_LOGIN_ID, loginId);
		vwTsModifyGeneral.setParameter(para);
		vwTsModifyGeneral.refreshWorkArea();
	}
}
