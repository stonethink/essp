package client.essp.timesheet.report.humanreport;

import java.awt.Rectangle;
import java.util.*;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.report.DtoHumanReport;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.model.VMComboBox;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.*;

import com.wits.util.Parameter;

public class VwHumanReportTop extends VWGeneralWorkArea {

	private static final String acntionId_GetSites = "FTSGetSites";
	private boolean isPMO = false;
	public VwHumanReportTop() {
		jbInit();
	}

	private void jbInit() {
		this.setLayout(null);
		lblBeginDate.setText("rsid.timesheet.begin");
		lblBeginDate.setBounds(new Rectangle(30, 20, 56, 20));
		inputBeginDate.setBounds(new Rectangle(100, 20, 90, 20));
		inputBeginDate.setCanSelect(true);

		lblEndDate.setText("rsid.timesheet.end");
		lblEndDate.setBounds(new Rectangle(30, 60, 56, 20));
		inputEndDate.setBounds(new Rectangle(100, 60, 90, 20));
		inputEndDate.setCanSelect(true);
		
		lblSite.setText("rsid.timesheet.site");
        lblSite.setBounds(new Rectangle(230, 20, 85, 20));
        selectSite.setBounds(new Rectangle(315, 20, 50, 20));
        
        this.add(lblSite);
        this.add(selectSite);
		this.add(inputEndDate);
		this.add(inputBeginDate);
		this.add(lblEndDate);
		this.add(lblBeginDate);
	}

	protected void initData() {
		Calendar ca = Calendar.getInstance();
		int month = ca.get(Calendar.MONTH);
		int year = ca.get(Calendar.YEAR);
		ca.set(year, month - 1, 1);
		int lastDay = ca.getActualMaximum(Calendar.DATE);
		inputBeginDate.setUICValue(ca.getTime());
		ca.set(year, month - 1, lastDay);
		inputEndDate.setUICValue(ca.getTime());
	}

	/**
	 * ¼¤»îË¢ÐÂ
	 */
	public void setParameter(Parameter param) {
		super.setParameter(param);
		this.refreshWorkArea();
	}

	protected void resetUI() {
		initData();
		InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(acntionId_GetSites);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if (!returnInfo.isError()) {
        	isPMO = (Boolean) returnInfo.getReturnObj(DtoHumanReport.DTO_IS_PMO);
        	Vector sites = (Vector) returnInfo.getReturnObj(DtoHumanReport.DTO_SITESLIST);
        	String site = (String) returnInfo.getReturnObj(DtoHumanReport.DTO_SITE);
        	if(sites == null) {
        		sites = new Vector();
        	}
        	VMComboBox item = new VMComboBox(sites);
        	selectSite.setModel(item);
        	selectSite.setUICValue(site);
        }
        if(isPMO) {
        	selectSite.setEnabled(true);
        } else {
        	selectSite.setEnabled(false);
        }
	}

	protected void foucsOnDate(String foucsOn) {
		if (foucsOn.equals("begin")) {
			comFORM.setFocus(inputBeginDate.getDateComp());
		} else if (foucsOn.equals("end")) {
			comFORM.setFocus(inputEndDate.getDateComp());
		}
	}

	VWJLabel lblBeginDate = new VWJLabel();
	VWJDatePanel inputBeginDate = new VWJDatePanel();
	VWJLabel lblEndDate = new VWJLabel();
	VWJDatePanel inputEndDate = new VWJDatePanel();
	VWJLabel lblSite = new VWJLabel();
    VWJComboBox selectSite = new VWJComboBox();
     
	public Date getBegin() {
		return (Date)inputBeginDate.getUICValue();
	}
	public Date getEnd() {
		return (Date)inputEndDate.getUICValue();
	}
	public String getSite() {
		return (String) selectSite.getUICValue();
	}

}
