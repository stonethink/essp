package client.essp.timesheet.tsmodify;

import java.awt.Rectangle;
import java.util.Calendar;
import java.util.Date;

import c2s.essp.timesheet.tsmodify.DtoTsModify;
import client.essp.common.humanAllocate.VWJHrAllocateButton;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWJDatePanel;
import client.framework.view.vwcomp.VWJLabel;

import com.wits.util.Parameter;

public class VwTsModifyQuery extends VWGeneralWorkArea {
	
	private VWJLabel lblStart = new VWJLabel();
	private VWJDatePanel inputStart = new VWJDatePanel();
	private VWJLabel lblFinish = new VWJLabel();
	private VWJDatePanel inputFinish = new VWJDatePanel();
	private VWJLabel lblPerson = new VWJLabel();
	private VWJHrAllocateButton person = new VWJHrAllocateButton(); 
	
	public VwTsModifyQuery() {
		try {
            jbInit();
        } catch (Exception e) {
            log.error(e);
        }
	}
	private void jbInit() {
		this.setLayout(null);
		lblStart.setText("rsid.timesheet.tsModify.start");
		lblStart.setBounds(new Rectangle(30, 20, 56, 20));
		
		inputStart.setBounds(new Rectangle(105, 20, 90, 20));
		inputStart.setCanSelect(true);
		
		lblFinish.setText("rsid.timesheet.tsModify.finish");
		lblFinish.setBounds(new Rectangle(30, 60, 56, 20));
		
		inputFinish.setBounds(new Rectangle(105, 60, 90, 20));
		inputFinish.setCanSelect(true);
		
		lblPerson.setText("rsid.timesheet.employee");
		lblPerson.setBounds(new Rectangle(230, 20, 85, 20));
		
		person.setBounds(new Rectangle(310, 20, 130, 20));
		
		this.add(lblStart);
		this.add(inputStart);
		this.add(lblFinish);
		this.add(inputFinish);
		this.add(lblPerson);
		this.add(person);
		initData();
	}

    private void initData() {
        Calendar ca = Calendar.getInstance();
        int month = ca.get(Calendar.MONTH);
        int year = ca.get(Calendar.YEAR);
        ca.set(year, month-1, 1);
        int lastDay = ca.getActualMaximum(Calendar.DATE);
        inputStart.setUICValue(ca.getTime());
        ca.set(year, month-1, lastDay);
        inputFinish.setUICValue(ca.getTime());
    }
    public DtoTsModify getCondition() {
    	DtoTsModify dtoTsModify = new DtoTsModify();
    	dtoTsModify.setLoginId((String)person.getUICValue());
    	dtoTsModify.setStartDate((Date)inputStart.getUICValue());
    	dtoTsModify.setFinishDate((Date)inputFinish.getUICValue());
    	return dtoTsModify;
    }
    public void foucsOnDate(String foucsOn) {
    	if(foucsOn.equals("begin")) {
    		comFORM.setFocus(inputStart.getDateComp());
    	} else if(foucsOn.equals("end")){
    		comFORM.setFocus(inputFinish.getDateComp());
    	} else if(foucsOn.equals("person")){
    		comFORM.setFocus(person.getTextComp());
    	}
    }

}
