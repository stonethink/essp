package client.essp.timesheet.accountpmo;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import c2s.essp.timesheet.account.DtoAccount;
import client.essp.common.humanAllocate.VWJHrAllocateButton;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.*;

public class VwAccountQueryPmo extends VWGeneralWorkArea {
	
	   VWJLabel lblAcntOrganization = new VWJLabel();
	   VWJText txtAcntOrganization = new VWJText();
	   VWJLabel lblAcntManager = new VWJLabel();
	   VWJHrAllocateButton txtAcntManager = new VWJHrAllocateButton();
	   VWJDatePanel txtAcntPlannedFinish = new VWJDatePanel();
	   VWJLabel lblPlannedFinish = new VWJLabel();
	   VWJDatePanel txtAcntPlannedStart = new VWJDatePanel();
	   VWJLabel lblPlannedStart = new VWJLabel();
	   VWJText txtAcntName = new VWJText();
	   VWJLabel lblAcntName = new VWJLabel();
	   VWJText txtAcntId = new VWJText();
	   VWJLabel lblAcntId = new VWJLabel();
	  
	   public VwAccountQueryPmo() { 
		   try {
			jbInit();
			setTabOrder();
			setEnterOrder();
			setUIComponentName();
		} catch (Exception e) {
			log.error(e);
		}
		addUICEvent();
	   }
	   private void jbInit() {
		   this.setLayout(null);
		   
	       lblAcntId.setText("rsid.timesheet.accountCode");
	       lblAcntId.setBounds(new Rectangle(35, 24, 128, 20));
	       txtAcntId.setBounds(new Rectangle(169, 24, 212, 20));
	       
	       lblAcntName.setText("rsid.timesheet.accountName");
	       lblAcntName.setBounds(new Rectangle(402, 24, 133, 20));
	       txtAcntName.setBounds(new Rectangle(544, 24, 212, 20));
	       
	       lblAcntManager.setText("rsid.timesheet.accountManager");
	       lblAcntManager.setBounds(new Rectangle(35, 49, 128, 20));
	       txtAcntManager.setBounds(new Rectangle(169, 49, 212, 20));
		   
		   lblAcntOrganization.setText("rsid.timesheet.organization");
	       lblAcntOrganization.setBounds(new Rectangle(402, 49, 133, 20));
	       txtAcntOrganization.setBounds(new Rectangle(544, 49, 212, 20));
	       
	       lblPlannedStart.setText("rsid.timesheet.plannedStart");
	       lblPlannedStart.setBounds(new Rectangle(35, 74, 104, 20));
	       txtAcntPlannedStart.setBounds(new Rectangle(169, 74, 212, 20));
	       txtAcntPlannedStart.setCanSelect(true);

	       lblPlannedFinish.setText("rsid.timesheet.plannedFinish");
	       lblPlannedFinish.setBounds(new Rectangle(402, 74, 133, 20));
	       txtAcntPlannedFinish.setBounds(new Rectangle(544, 74, 212, 20));
	       txtAcntPlannedFinish.setCanSelect(true);

	       
	       this.add(lblAcntId);
	       this.add(lblAcntManager);
	       this.add(txtAcntId);
	       this.add(txtAcntManager);
	       this.add(txtAcntName);
	       this.add(lblAcntName);
	       this.add(lblPlannedStart);
	       this.add(txtAcntPlannedStart);
	       this.add(lblPlannedFinish);
	       this.add(lblAcntOrganization);
	       this.add(txtAcntPlannedFinish);
	       this.add(txtAcntOrganization);
	   }
	   private void setTabOrder() {
	       List compList = new ArrayList();
	       compList.add(txtAcntId);
	       compList.add(txtAcntName);
	       compList.add(txtAcntManager);
	       compList.add(txtAcntOrganization);
	       compList.add(txtAcntPlannedStart);
	       compList.add(txtAcntPlannedFinish);

	       comFORM.setTABOrder(this, compList);
	   }
	   
	   private void setEnterOrder() {
	       List compList = new ArrayList();
	       compList.add(txtAcntId);
	       compList.add(txtAcntName);
	       compList.add(txtAcntManager);
	       compList.add(txtAcntOrganization);
	       compList.add(txtAcntPlannedStart);
	       compList.add(txtAcntPlannedFinish);

	       comFORM.setEnterOrder(this, compList);
	   }

	   private void setUIComponentName() {
	       txtAcntId.setName("accountId");
	       txtAcntName.setName("accountName");
	       txtAcntManager.setName("manager");
	       txtAcntOrganization.setName("orgCode");
	       txtAcntPlannedStart.setName("plannedStart");
	       txtAcntPlannedFinish.setName("plannedFinish");
	   }
	          
	   /**
	    * 事件处理
	    */
	   private void addUICEvent() {

	   }
	   public DtoAccount getQueryCondition() {
		   DtoAccount dto = new DtoAccount();
		   VWUtil.convertUI2Dto(dto, this);
		   return dto;
	   }
}
