package client.essp.pms.wbs;

import client.essp.common.view.VWWorkArea;
import javax.swing.JScrollPane;
import java.awt.*;
import javax.swing.JButton;
import client.framework.view.vwcomp.VWJTable;
import client.framework.model.VMTable2;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
public class VwIssueBase extends VWWorkArea {
    private JButton addBtn;
    private JButton editBtn;
    private JButton deleteBtn;

    JScrollPane jScrollPane1 = null;

    private VMTable2 vmTblIssue;
    private VWJTable vwTblIssue;

    public VwIssueBase() {
        super();
        this.vmTblIssue = new VMTable2(new Object[][] { {"Issue Type",
                                       "issueType"}, {"Issue ID", "issueID"},
                                       {"Issue Name",
                                       "issueName"}, {"Filled Date",
                                       "filledDate"}, {"Priority", "priority"},
                                       {"Due Date", "dueDate"}, {"Principal",
                                       "principal"}, {"Status", "status"}
        });
        this.vwTblIssue=new VWJTable(this.vmTblIssue);
        this.jScrollPane1=new JScrollPane(this.vwTblIssue);
        addBtn = this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        editBtn = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        deleteBtn = this.getButtonPanel().addDelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        VwIssueBase vwissuebase = new VwIssueBase();
    }

    public void jbInit() throws Exception {
        jScrollPane1.getViewport().setBackground(Color.white);
        this.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        this.getButtonPanel().add(this.addBtn);
        this.getButtonPanel().add(this.editBtn);
        this.getButtonPanel().add(this.deleteBtn);
    }

}
