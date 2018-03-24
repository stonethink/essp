package client.essp.pms.account.baseline;


import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJTextArea;
import com.wits.util.TestPanel;
import java.awt.*;
import client.essp.common.loginId.VWJLoginId;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VwAcntBLCurrentBase extends VWGeneralWorkArea {

////////////////////////////////////////////////////////////////
    VWJLabel lblCurrentBaseline = new VWJLabel();
    JLabel lblApplicationReason = new JLabel();
    JLabel lblApprovedBy = new JLabel();
    JLabel lblStatus = new JLabel();
    JLabel lblComment = new JLabel();
    VWJDate txtApplicationDate = new VWJDate();
    VWJLabel lblApprovedDate = new VWJLabel();
    VWJComboBox txtSatus = new VWJComboBox();
    VWJTextArea txaApplicationReason = new VWJTextArea();
    VWJTextArea txaComment = new VWJTextArea();

    VWJText txtApprovedBy = new VWJLoginId();
    VWJDate txtApprovedDate = new VWJDate();
    VWJText txtCurrentBaseline = new VWJText();
    VWJLabel lblApplicationDate = new VWJLabel();

    ///////////////////////////////////////////////////////////////
    /**
     * default constructor
     */
    public VwAcntBLCurrentBase() {
        super();

        try {
            jbInit();
            setUIComponentName();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Component initialization
    private void jbInit() throws Exception {
        this.setLayout(null);

/////////////////////////////////////////////////////////////////
        lblCurrentBaseline.setText("Current Baseline");
        lblCurrentBaseline.setBounds(new Rectangle(30, 11, 125, 20));
        lblApplicationReason.setText("Application Reason");
        lblApplicationReason.setBounds(new Rectangle(30, 49, 127, 32));
        lblApprovedBy.setText("Approved By");
        lblApprovedBy.setBounds(new Rectangle(295, 120, 97, 30));
        lblStatus.setText("Approved Status");
        lblStatus.setBounds(new Rectangle(26, 115, 108, 38));
        lblComment.setText("Comment");
        lblComment.setBounds(new Rectangle(26, 154, 58, 36));
        txtApplicationDate.setBounds(new Rectangle(547, 9, 180, 25));
        txtApplicationDate.setCanSelect(true);
        lblApprovedDate.setText("Approved Date");
        lblApprovedDate.setBounds(new Rectangle(516, 119, 92, 33));
        txaApplicationReason.setBounds(new Rectangle(166, 48, 562, 50));
        txaComment.setBounds(new Rectangle(166, 163, 562, 50));
        txtApprovedBy.setBounds(new Rectangle(384, 125, 115, 25));
        txtSatus.setBounds(new Rectangle(166, 125, 115, 25));
        txtApprovedDate.setBounds(new Rectangle(613, 125, 115, 25));
        txtApprovedDate.setCanSelect(true);

        txtCurrentBaseline.setBounds(new Rectangle(166, 10, 180, 25));
        lblApplicationDate.setText("Application Date");
        lblApplicationDate.setBounds(new Rectangle(396, 5, 132, 26));

        TitledBorder titledBorder1 = new TitledBorder(BorderFactory.
            createEtchedBorder(Color.white,
                               new Color(164, 163, 165)), "Application");

//        JPanel jpApplaction = new JPanel();
//        jpApplaction.setBorder(titledBorder1);
//        jpApplaction.setBounds(30, 240, 700, 125);
//        jpApplaction.setLayout(new BorderLayout());
//        jpApplaction.add(vwTblAppLog.getScrollPane());

        this.add(txaComment.getScrollPane());
        this.add(txtCurrentBaseline);
        this.add(txtApplicationDate);
        this.add(lblCurrentBaseline);
        this.add(lblApplicationDate);
        this.add(txaApplicationReason.getScrollPane());
        this.add(lblApplicationReason);
        this.add(txtSatus);
        this.add(lblStatus);
        this.add(txtApprovedBy);
        this.add(txtApprovedDate);
        this.add(lblApprovedDate);
        this.add(lblApprovedBy);
        this.add(lblComment);
    }

    private void setUIComponentName() {
        txtCurrentBaseline.setName("baselineId");
        txtApplicationDate.setName("appDate");
        txaApplicationReason.setName("appReason");
        txtApprovedBy.setName("approveUser");
        txtApprovedDate.setName("approveDate");
        txaComment.setName("remark");
        txtSatus.setName("appStatus");
    }

    public static void main(String[] args) {
        VwAcntBLCurrentBase acntBL = new VwAcntBLCurrentBase();

        TestPanel.show(acntBL);
    }


}
