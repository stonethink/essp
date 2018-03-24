package client.essp.timesheet.weeklyreport.detail;

import java.awt.Rectangle;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWButtonGroup;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJRadioButton;

public class VwSubmitChoiceset extends VWGeneralWorkArea {
	
	private VWJLabel lblReason;
	private VWJRadioButton radIngoing;
	private VWJRadioButton radDimission;
	private VWJRadioButton radNeither;
	private VWButtonGroup grpChoiceset;
	
	public VwSubmitChoiceset() {
		try {
            jbInit();
        } catch (Exception ex) {
            log.error(ex);
        }
	}
	
	/**
     * 初始化UI
     * @throws Exception
     */
    private void jbInit() throws Exception {
        this.setLayout(null);
        lblReason = new VWJLabel();
        radIngoing = new VWJRadioButton();
        radDimission = new VWJRadioButton();
        radNeither = new VWJRadioButton();
        
        lblReason.setBounds(new Rectangle(20, 10, 80, 22));
        lblReason.setText("rsid.timesheet.reason");
        radIngoing.setBounds(new Rectangle(40, 40, 80, 22));
        radIngoing.setText("rsid.timesheet.ingoing");
        radDimission.setBounds(new Rectangle(40, 70, 80, 22));
        radDimission.setText("rsid.timesheet.dimission");
        radNeither.setBounds(new Rectangle(40, 100, 80, 22));
        radNeither.setText("rsid.timesheet.neither");
        grpChoiceset = new VWButtonGroup();
        radIngoing.setSelected(true);
        grpChoiceset.add(radIngoing);
        grpChoiceset.add(radDimission);
        grpChoiceset.add(radNeither);
        
        this.add(lblReason);
        this.add(radIngoing);
        this.add(radDimission);
        this.add(radNeither);
    }
    
    /**
     * 获取用户选择的原因
     * @return String
     */
    public String getReason() {
    	if(radIngoing.isSelected()) {
    		return DtoTimeSheet.REASON_INGOING;
    	} else if(radDimission.isSelected()) {
    		return DtoTimeSheet.REASON_DIMISSION;
    	} else {
    		return null;
    	}
    }

}
