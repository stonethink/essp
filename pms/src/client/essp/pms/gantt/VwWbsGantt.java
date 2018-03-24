package client.essp.pms.gantt;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JSplitPane;

import c2s.dto.ITreeNode;
import c2s.essp.common.gantt.ITreeGanttDto;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.gantt.Gantt;
import client.essp.common.view.VWWorkArea;
import client.framework.model.VMTreeTableModel;
import client.framework.view.vwcomp.IVWAppletParameter;
import client.framework.view.vwcomp.VWJTreeTable;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import client.framework.model.VMColumnConfig;

public class VwWbsGantt extends VWWorkArea implements IVWAppletParameter {
    static final long ONE_DAY_TIME_MILLIS = 24 * 3600 * 1000;
    private Long inAcntRid;

    JSplitPane splitPane;
    VwWbsGanttList vwWbsList;
    Gantt gantt;

    public VwWbsGantt() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
    }

    private void jbInit() throws Exception {
        vwWbsList = new VwWbsGanttList();
        setColumnWidth();


        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setDividerLocation(400);
        this.splitPane.setTopComponent(vwWbsList);

        this.add(splitPane, BorderLayout.CENTER);
    }

    private void setColumnWidth(){
        //200 90 90 60 70 100
        VWJTreeTable treeTable = vwWbsList.getTreeTable();

        treeTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        treeTable.getColumnModel().getColumn(1).setPreferredWidth(90);
        treeTable.getColumnModel().getColumn(2).setPreferredWidth(90);
        treeTable.getColumnModel().getColumn(3).setPreferredWidth(60);
        treeTable.getColumnModel().getColumn(4).setPreferredWidth(70);
        treeTable.getColumnModel().getColumn(5).setPreferredWidth(100);

        VMColumnConfig config0 = (VMColumnConfig)vwWbsList.getModel().getColumnConfigs().get(0);
        config0.setPreferWidth(200);
        VMColumnConfig config1 = (VMColumnConfig)vwWbsList.getModel().getColumnConfigs().get(1);
        config1.setPreferWidth(90);
        VMColumnConfig config2 = (VMColumnConfig)vwWbsList.getModel().getColumnConfigs().get(2);
        config2.setPreferWidth(90);
        VMColumnConfig config3 = (VMColumnConfig)vwWbsList.getModel().getColumnConfigs().get(3);
        config3.setPreferWidth(60);
        VMColumnConfig config4 = (VMColumnConfig)vwWbsList.getModel().getColumnConfigs().get(4);
        config4.setPreferWidth(70);
        VMColumnConfig config5 = (VMColumnConfig)vwWbsList.getModel().getColumnConfigs().get(5);
        config5.setPreferWidth(100);
    }

    public void refreshWorkArea() {

        if (inAcntRid == null) {
            vwWbsList.getTreeTable().setRoot(null);
        } else {
            Parameter param = new Parameter();
            param.put("inAcntRid", inAcntRid);
            vwWbsList.setParameter(param);
            vwWbsList.refreshWorkArea();
        }
        //build treeTable
        VWJTreeTable treeTable = vwWbsList.getTreeTable();

        //build gantt
        gantt = new Gantt(treeTable
                          , getMinTime(treeTable) //getTimeInMillis(2005, 5, 1)
                          , getMaxTime(treeTable)); //getTimeInMillis(2005, 12, 1));

        this.splitPane.setBottomComponent(gantt);
        splitPane.setDividerLocation(500);
        splitPane.validate();

        if (treeTable.getRowCount() > 0) {
            treeTable.expandRow(0);
        }
    }

    private void addUICEvent() {
        //Refresh
        this.getButtonPanel().addLoadButton(new
                                            ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });

        JButton expandBtn = this.getButtonPanel().addButton("expand.png");
        expandBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vwWbsList.actionPerformedExpand(e);
            }
        });

        //Display
        TableColumnChooseDisplay chooseDisplay =
            new TableColumnChooseDisplay(vwWbsList.getTreeTable(),
                                         vwWbsList);
        JButton button = chooseDisplay.getDisplayButton();
        this.getButtonPanel().addButton(button);
    }

    public void actionPerformedLoad(ActionEvent e) {
        if (this.inAcntRid != null) {
            Parameter param = new Parameter();
            param.put("inAcntRid", this.inAcntRid);
            vwWbsList.setParameter(param);
            vwWbsList.reloadData();
        }
    }

    public VwWbsGanttList getGanttList() {
        return vwWbsList;
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);

        String sAcntRid = (String) (param.get("inAcntRid"));
        try {
            this.inAcntRid = new Long(sAcntRid);
        } catch (NumberFormatException ex) {
            inAcntRid = null;
        }
    }

    public String[][] getParameterInfo() {
        return new String[][] { {"inAcntRid", "inAcntRid", "inAcntRid"}
        };
    }

    private long getMinTime(VWJTreeTable treeTable) {
        VMTreeTableModel model = (VMTreeTableModel) treeTable.getTreeTableModel();
        ITreeNode root = (ITreeNode) model.getRoot();

        if (root != null) {
            ITreeGanttDto dto = (ITreeGanttDto) root.getDataBean();
            if (dto.getBeginTime() != 0) {
                return dto.getBeginTime() - 7 * ONE_DAY_TIME_MILLIS;
            }
        }

        Calendar t = Calendar.getInstance();
        t.set(Calendar.MONTH, t.get(Calendar.MONTH) - 2);
        return t.getTimeInMillis();

    }

    private long getMaxTime(VWJTreeTable treeTable) {
        VMTreeTableModel model = (VMTreeTableModel) treeTable.getTreeTableModel();
        ITreeNode root = (ITreeNode) model.getRoot();

        if (root != null) {
            ITreeGanttDto dto = (ITreeGanttDto) root.getDataBean();
            if (dto.getEndTime() != 0) {
                return dto.getEndTime() + 14 * ONE_DAY_TIME_MILLIS;
            }
        }

        Calendar t = Calendar.getInstance();
        t.set(Calendar.MONTH, t.get(Calendar.MONTH) + 2);
        return t.getTimeInMillis();
    }

    private long getTimeInMillis(int y, int m, int d) {
        Calendar t = Calendar.getInstance();
        t.set(y, m, d);
        return t.getTimeInMillis();
    }

    public static void main(String[] args) {
        VwWbsGantt workArea = new VwWbsGantt();

        TestPanel.show(workArea);

        Parameter param = new Parameter();

        param.put("inAcntRid", "6");
        workArea.setParameter(param);

        workArea.refreshWorkArea();
        workArea.updateUI();
    }
}
