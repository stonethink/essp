package client.essp.timesheet.tsmodify;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;

import c2s.essp.timesheet.account.DtoAccount;
import c2s.essp.timesheet.tsmodify.DtoTsModify;
import client.essp.common.view.VWTDWorkArea;
import client.framework.view.common.comMSG;

import com.wits.util.Parameter;

public class VwTsModifyOuter extends VWTDWorkArea {
	
	private VwTsModifyQuery vwTsModifyQuery;
	private VwTsModifyTop vwTsModifyTop;
	private JButton queryBtn = new JButton();
	
	public VwTsModifyOuter() {
		super(150);
		try {
            jbInit();
        } catch (Exception e) {
            log.error(e);
        }
        addUICEvent();
	}
	private void jbInit() {
		vwTsModifyQuery = new VwTsModifyQuery();
		vwTsModifyTop = new VwTsModifyTop();
		
		this.getTopArea().addTab("rsid.timesheet.tsModify", vwTsModifyQuery);
		this.getDownArea().add(vwTsModifyTop);
		
	}
	private void addUICEvent() {
		vwTsModifyQuery.getButtonPanel().addButton(queryBtn);
    	queryBtn.setText("rsid.timesheet.query");
    	queryBtn.setToolTipText("rsid.timesheet.query");
        queryBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processQuery();
            }
        });
	}
	 private void processQuery() {
		DtoTsModify dto = vwTsModifyQuery.getCondition();
		if(checkError(dto)) {
			return;
		}
		Parameter param = new Parameter();
		param.put(DtoTsModify.DTO_CONDITION, dto);
		vwTsModifyTop.setParameter(param);
		vwTsModifyTop.refreshWorkArea();

	}
	 private boolean checkError(DtoTsModify dto) {
		Date begin = dto.getStartDate();
		Date end = dto.getFinishDate();
		String person = dto.getLoginId();
		if (begin == null) {
			comMSG.dispErrorDialog("rsid.common.fill.begin");
			vwTsModifyQuery.foucsOnDate("begin");
			return true;
		}
		if (end == null) {
			comMSG.dispErrorDialog("rsid.common.fill.end");
			vwTsModifyQuery.foucsOnDate("end");
			return true;
		}
		if (begin.after(end)) {
			comMSG.dispErrorDialog("rsid.common.beginlessEnd");
			vwTsModifyQuery.foucsOnDate("begin");
			return true;
		}
		if (person == null) {
			comMSG.dispErrorDialog("rsid.common.select.person");
			vwTsModifyQuery.foucsOnDate("person");
			return true;
		}
		return false;
	}
	public void setParameter(Parameter param) {
		param.put(DtoAccount.DTO_CONDITION, null);
		vwTsModifyTop.setParameter(param);
		vwTsModifyTop.refreshWorkArea();
	}
	
}
