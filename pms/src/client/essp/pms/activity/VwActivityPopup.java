package client.essp.pms.activity;

import javax.swing.JDialog;
import client.essp.common.view.VWWorkArea;
import client.framework.view.vwcomp.IVWTreeTablePopupEvent;
import java.awt.Dimension;
import com.wits.util.Parameter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.vwcomp.IVWTreeTableMouseClickListener;
import java.awt.event.MouseEvent;
import c2s.dto.ITreeNode;
import java.awt.PopupMenu;
import client.framework.view.vwcomp.VWJTreeTable;
import javax.swing.JFrame;
import java.awt.Dialog;
import java.awt.Frame;

public class VwActivityPopup extends JDialog {
    VWWorkArea mainPanel;
    VWWorkArea buttonPanel;
    VwActivityList tree;
    IVWTreeTablePopupEvent eventHandle = null;
    Long inAcntRid;

    public VwActivityPopup(Frame frame,IVWTreeTablePopupEvent eventHandle) {
        super(frame,"Activities",true);

        try {
            this.eventHandle = eventHandle;
            if(eventHandle==null) {
                throw new Exception("eventHandle is null");
            }
            if(eventHandle.getParameter()==null ||
               eventHandle.getParameter().get(AbstractActivityEvent.ACCOUNT_RID)==null) {
                throw new Exception("account rid not set!!!");
            }
            inAcntRid=(Long)eventHandle.getParameter().get(AbstractActivityEvent.ACCOUNT_RID);
            jbInit();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void jbInit() throws Exception {
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(800, 600));
        mainPanel = new VWWorkArea();
        mainPanel.setBorder(null);
        tree = new VwActivityList();
        tree.setBorder(null);
        mainPanel.addTab("Activity", tree);
        Parameter param = new Parameter();
        param.put("inAcntRid", inAcntRid);
        tree.setParameter(param);
        tree.refreshWorkArea();
        tree.getTreeTable().expandsRow();
        //expand
        JButton btnExpand = tree.getButtonPanel().addButton("expand.png");
        btnExpand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tree.actionPerformedExpand(e);
            }
        });

        //Display
        TableColumnChooseDisplay chooseDisplay =
            new TableColumnChooseDisplay(tree.getTreeTable(), tree);
        JButton button = chooseDisplay.getDisplayButton();
        tree.getButtonPanel().addButton(button);

        //Refresh
        tree.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Parameter param = new Parameter();
                param.put("inAcntRid", inAcntRid);
                tree.setParameter(param);
                tree.refreshWorkArea();
            }
        });

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);

        buttonPanel = new VWWorkArea();
        buttonPanel.setBorder(null);
        buttonPanel.setLayout(new FlowLayout());
        JButton okBtn = new JButton("  OK  ");
        buttonPanel.add(okBtn);
        okBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (eventHandle != null) {
                    if(eventHandle.onClickOK(e)) {
                        dispose();
                    }
                } else {
                    dispose();
                }
            }
        });

        JButton cancelBtn = new JButton("Cancel");
        buttonPanel.add(cancelBtn);
        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (eventHandle != null) {
                    if(eventHandle.onClickCancel(e)) {
                        dispose();
                    }
                } else {
                    dispose();
                }
            }
        });

        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        tree.getTreeTable().addRowSelectedListener(new RowSelectedListener() {
            public void processRowSelected() {
                if (eventHandle != null) {
                    eventHandle.onSelectedNode(tree.getTreeTable().getSelectedNode());
                }

            }
        });

        tree.getTreeTable().addMouseRightClickListener(new IVWTreeTableMouseClickListener() {
           public void onMouseClick(MouseEvent e,ITreeNode node,int positionType) {
               if(eventHandle!=null) {
                   //只有在树上点击，才可以出现
                   //if(positionType==IVWTreeTableMouseClickListener.POSITION_TREE) {
                       //eventHandle.setSelectedNode(node);
                       if (eventHandle.getPopupMenu() != null) {
                           PopupMenu menu = eventHandle.getPopupMenu();
                           if (menu.getParent() == null) {
                               tree.getTreeTable().add(menu);
                           }
                           menu.show(tree.getTreeTable(), e.getX(), e.getY());
                       }
                   //}
               }
           }
        });
    }

    public VWJTreeTable getTreeTable(){
        return tree.getTreeTable();
    }
}
