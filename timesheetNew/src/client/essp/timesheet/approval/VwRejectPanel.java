package client.essp.timesheet.approval;

import java.awt.Rectangle;

import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJTextArea;

public class VwRejectPanel extends VWGeneralWorkArea {
	
	private VWJLabel reasonLabel = new VWJLabel();
	private VWJTextArea reasonText = new VWJTextArea();
	
	public VwRejectPanel() {
		jbInit();
	}
	private void jbInit() {
		this.setLayout(null);
		reasonLabel.setBounds(new Rectangle(5, 5, 100, 20));
		reasonLabel.setText("rsid.timesheet.rejectReason");
		
		reasonText.setBounds(new Rectangle(5, 25, 300, 60));
		
		this.add(reasonLabel);
		this.add(reasonText);
	}
	
	public String getReason() {
		return (String) reasonText.getUICValue();
	}
}
