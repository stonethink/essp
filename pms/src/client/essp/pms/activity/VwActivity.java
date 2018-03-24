package client.essp.pms.activity;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import c2s.dto.ITreeNode;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.wbs.DtoKEY;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.view.VWTDWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.pms.activity.code.VwActivityCodeList;
import client.essp.pms.activity.cost.VwActivityCost;
import client.essp.pms.activity.relation.VwRelationPredecessors;
import client.essp.pms.activity.relation.VwRelationSuccessors;
import client.essp.pms.activity.worker.VwActivityWorkerList;
import client.essp.pms.activity.wp.VwWpListActivity;
import client.essp.pms.wbs.pbsAndFiles.VwPbsAndFile;
import client.framework.view.event.DataChangedListener;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.event.RowSelectedLostListener;
import client.framework.view.vwcomp.IVWAppletParameter;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJPopupEditor;
import com.wits.util.IVariantListener;
import com.wits.util.Parameter;
import com.wits.util.ProcessVariant;
import client.essp.pms.activity.relation.VwRelationship;
import client.essp.pms.activity.process.VwActivityProcess;

public class VwActivity extends VWTDWorkArea implements IVWAppletParameter,
    IVariantListener {
    /**
     * define control variable
     */
    private boolean refreshFlag = false;

    /**
     * define common data (globe)
     */
    Long inAcntRid;

/////// 段1，定义界面：定义界面（定义控件，设置控件名称，光标控制顺序）、定义界面事件
    VwActivityList activityList;
    VwWbsDisp wbsDisp;
    VwActivityGeneral activityGeneral;
    VwActivityStatus activityStatus;
    VwPbsAndFile pbs;
    VwActivityWorkerList activityWorkers;
    VwRelationship relation;
    VwWpListActivity activityWp;
    VwActivityCodeList activityCode;
    VwActivityCost activityCost;
    VwActivityProcess activityProcess;

    VWWorkArea activityDownArea;
    VWWorkArea wbsDownArea;

    JButton addBtn = null;
    JButton deleteBtn = null;
    JButton refreshBtn = null;
    JButton upBtn = null;
    JButton downBtn = null;
    JButton leftBtn = null;
    JButton rightBtn = null;
    JButton expandBtn = null;
    JButton filterBtn = null;
    JButton computeBtn = null;
    private String entryFunType;

    VWJLabel filterLbl = new VWJLabel();

    public VwActivity() {
        super(250);

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
    }

    //Component initialization
    private void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(700, 470));

        activityList = new VwActivityList(filterLbl);
        this.getTopArea().addTab("Activity", activityList);

        activityDownArea = new VWWorkArea();
        activityGeneral = new VwActivityGeneral();
        activityDownArea.addTab("General", activityGeneral);
        activityGeneral.setParentWorkArea(activityDownArea);

        wbsDisp = new VwWbsDisp();

        activityStatus = new VwActivityStatus();
        activityDownArea.addTab("Status", activityStatus);

        activityWorkers = new VwActivityWorkerList();
        activityDownArea.addTab("Workers", activityWorkers);

        relation=new VwRelationship();
        activityDownArea.addTab("Relationship", relation);

        pbs = new VwPbsAndFile();
        activityDownArea.addTab("PBS", pbs);

        activityWp = new VwWpListActivity();
        activityDownArea.addTab("WP", activityWp);

        activityCode = new VwActivityCodeList();
        activityDownArea.addTab("Code", activityCode);

        activityCost = new VwActivityCost();
        activityDownArea.addTab("Cost", activityCost);

        activityProcess = new VwActivityProcess();
        activityDownArea.addTab("Process", activityProcess);
        this.setDownArea(activityDownArea);

        wbsDownArea = new VWWorkArea();
        wbsDownArea.addTab("General", wbsDisp);

    }

    /**
     * 定义界面：定义界面事件
     */
    private void addUICEvent() {
        this.activityList.getTreeTable().addRowSelectedLostListener(new
            RowSelectedLostListener() {
            public boolean processRowSelectedLost(int oldSelectedRow,
                                                  Object oldSelectedNode) {
                return processRowSelectedLostHandle(oldSelectedRow,
                    oldSelectedNode);
            }
        });

        this.activityList.getTreeTable().addRowSelectedListener(new
            RowSelectedListener() {
            public void processRowSelected() {
                processRowSelectedactivityList();
            }
        });

        //filter Label
        VWJLabel filterTitle = new VWJLabel();
        filterTitle.setSize(20, 20);
        filterLbl.setPreferredSize(new Dimension(60,20));
//        filterTitle.setPreferredSize(new Dimension(80, 20));
//        filterTitle.setHorizontalAlignment(SwingConstants.RIGHT);
//        filterTitle.setText("Filter By:");
        activityList.getButtonPanel().add(filterTitle);
        filterLbl.setSize(80, 20);
        filterLbl.setText("All Activity");
        filterLbl.setToolTipText("Filter By:All Activity");
        activityList.getButtonPanel().add(filterLbl);

        //Add
        addBtn = activityList.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd(e);

            }
        });
        addBtn.setToolTipText("add activity");

        //Delete
        deleteBtn = activityList.getButtonPanel().addDelButton(new
            ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDel(e);
            }
        });
        deleteBtn.setToolTipText("delete data");
        //Down
        downBtn = activityList.getButtonPanel().addButton("down.png");
        downBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDown(e);
            }
        });
       downBtn.setToolTipText("down");
        //Up
        upBtn = activityList.getButtonPanel().addButton("up.png");
        upBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedUp(e);
            }
        });
       upBtn.setToolTipText("up");
        //Left
        leftBtn = activityList.getButtonPanel().addButton("left.png");
        leftBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLeft(e);
            }
        });
       leftBtn.setToolTipText("left");
        //Right
        rightBtn = activityList.getButtonPanel().addButton("right.png");
        rightBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedRight(e);
            }
        });
       rightBtn.setToolTipText("right");
        //expand
        expandBtn = activityList.getButtonPanel().addButton("expand.png");
        expandBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedExpand(e);
            }
        });
       expandBtn.setToolTipText("expand");
        //filter
        filterBtn = activityList.getButtonPanel().addButton("filter.gif");
        filterBtn.setToolTipText("Activity Filter");
        filterBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //弹出过滤条件选择窗口
                VwActivityFilter filter = new VwActivityFilter(activityList.
                    getTreeTable(), activityList, filterLbl);

                VWJPopupEditor pop = new VWJPopupEditor(getParentWindow(),
                    "Filter",
                    filter, 2);
                pop.setSize(300, 200);
                pop.show();
            }
        });

        //Display
        TableColumnChooseDisplay chooseDisplay =
            new TableColumnChooseDisplay(activityList.getTreeTable(),
                                         activityList);
        JButton button = chooseDisplay.getDisplayButton();
        activityList.getButtonPanel().addButton(button);

        DataChangedListener listener = new DataChangedListener() {
            public void processDataChanged() {
                activityList.updateTreeTable();
                ITreeNode node = activityList.getSelectedNode();
                setButtonStatus(node);
            }
        };
        activityGeneral.addDataChangedListener(listener);
        activityStatus.addDataChangedListener(listener);
        activityWorkers.addDataChangedListener(listener);
        relation.getPredecessor().addDataChangedListener(listener);
        relation.getSuccessor().addDataChangedListener(listener);

        computeBtn = activityList.getButtonPanel().addButton("calc.gif");
        computeBtn.setToolTipText("Complete Rate");
        computeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                activityList.actionPerformedCompute(e);
            }
        });

        //Refresh
        refreshBtn = activityList.getButtonPanel().addLoadButton(new
            ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });
        refreshBtn.setToolTipText("refresh data");
        ProcessVariant.addDataListener("account", this);
        ProcessVariant.addDataListener("wbstree", this);

        this.getDownArea().setSelectedIndex(0);
        this.getDownArea().disableAllTabs();
    }

    private void setButtonStatus(ITreeNode node) {
        if (node == null) {
            return;
        }
        DtoWbsActivity dataBean = (DtoWbsActivity) node.getDataBean();
        if (dataBean == null) {
            return;
        }

        if (dataBean.isActivity()) {
            int size = -1;
            int index = -1;

            if (node.getParent() != null) {
                size = node.getParent().getChildCount();
                index = node.getParent().getIndex(node);
                log.info("size=" + size + ",index=" + index);
            }

            if (dataBean.isReadonly()) {
                addBtn.setEnabled(false);
                deleteBtn.setEnabled(false);
                refreshBtn.setEnabled(true);
                upBtn.setEnabled(false);
                downBtn.setEnabled(false);
                leftBtn.setEnabled(false);
                rightBtn.setEnabled(false);
                expandBtn.setEnabled(true);
            } else if (dataBean.isInsert()) {
                addBtn.setEnabled(true);
                deleteBtn.setEnabled(true);
                refreshBtn.setEnabled(true);
                upBtn.setEnabled(false);
                downBtn.setEnabled(false);
                leftBtn.setEnabled(false);
                rightBtn.setEnabled(false);
                expandBtn.setEnabled(true);

            } else {
                addBtn.setEnabled(true);
                deleteBtn.setEnabled(true);
                refreshBtn.setEnabled(true);
                if (index > 0) {
                    upBtn.setEnabled(true);
                } else {
                    upBtn.setEnabled(false);
                }
                if (index < size - 1) {
                    downBtn.setEnabled(true);
                } else {
                    downBtn.setEnabled(false);
                }
                leftBtn.setEnabled(false);
                rightBtn.setEnabled(false);
                expandBtn.setEnabled(true);
            }
        } else {
            if (dataBean.isReadonly()) {
                this.addBtn.setEnabled(false);
            } else {
                this.addBtn.setEnabled(true);
            }
            this.deleteBtn.setEnabled(false);
            this.refreshBtn.setEnabled(true);
            this.upBtn.setEnabled(false);
            this.downBtn.setEnabled(false);
            this.leftBtn.setEnabled(false);
            this.rightBtn.setEnabled(false);
            this.expandBtn.setEnabled(true);
        }
    }

    private boolean processRowSelectedLostHandle(int oldSelectedRow,
                                                 Object oldSelectedNode) {
        boolean result = true;
        VWWorkArea selectedArea = this.getDownArea().getSelectedWorkArea();
        if (selectedArea instanceof RowSelectedLostListener) {
            result = ((RowSelectedLostListener) selectedArea).
                     processRowSelectedLost(oldSelectedRow, oldSelectedNode);
        }
        return result;

    }

    /////// 段2，设置参数：获取传入参数
    public void setParameter(Parameter param) {
        String accountId = (String) param.get("accountId");
        if (accountId != null) {
            this.inAcntRid = new Long(accountId);
        } else {
            this.inAcntRid = (Long) param.get("inAcntRid");
        }

        //mod by xr 2006/06/26
        //增加界面菜单的参数,SEPG可任意修改,其他菜单保持不变
        String newEntryFunType = (String) param.get("entryFunType");
        if(newEntryFunType != null) {
            entryFunType = newEntryFunType;
        } else if (entryFunType == null || entryFunType.length() == 0) {
            entryFunType = DtoAcntKEY.PMS_ACCOUNT_FUN;
        }

        this.refreshFlag = true;

    }

    /////// 段3，获取数据并刷新画面
    //最外层的workArea不需要刷新自己
    public void refreshWorkArea() {
        if (this.refreshFlag == true) {
            Parameter param = new Parameter();
            param.put("inAcntRid", this.inAcntRid);
            param.put("entryFunType", entryFunType);
            activityList.setParameter(param);
            activityList.refreshWorkArea();
            this.refreshFlag = false;
        }
    }

    /////// 段4，事件处理
    public void actionPerformedAdd(ActionEvent e) {
        activityList.actionPerformedAdd(e);
        //this.getDownArea().enableTabOnly("General");
    }

    public void actionPerformedLoad(ActionEvent e) {
        Parameter param = new Parameter();
        param.put("inAcntRid", this.inAcntRid);
        activityList.setParameter(param);
        activityList.reloadData();
        activityGeneral.hasGetManagers = false;
    }

    public void actionPerformedDel(ActionEvent e) {
        activityList.actionPerformedDelete(e);
    }

    public void actionPerformedDown(ActionEvent e) {
        ITreeNode node = activityList.getSelectedNode();
        activityList.actionPerformedDown(e);
        this.setButtonStatus(node);
    }

    public void actionPerformedUp(ActionEvent e) {
        ITreeNode node = activityList.getSelectedNode();
        activityList.actionPerformedUp(e);
        this.setButtonStatus(node);
    }

    public void actionPerformedLeft(ActionEvent e) {
        ITreeNode node = activityList.getSelectedNode();
        activityList.actionPerformedLeft(e);
        this.setButtonStatus(node);
    }

    public void actionPerformedRight(ActionEvent e) {
        ITreeNode node = activityList.getSelectedNode();
        activityList.actionPerformedRight(e);
        this.setButtonStatus(node);
    }

    public void actionPerformedExpand(ActionEvent e) {
        activityList.actionPerformedExpand(e);
    }

    public void processRowSelectedactivityList() {
        ITreeNode node = activityList.getSelectedNode();
        DtoWbsActivity dataBean;
        if (node != null) {
            dataBean = (DtoWbsActivity) node.getDataBean();
        } else {
            dataBean = new DtoWbsActivity();
        }
        setButtonStatus(node);
        if (dataBean.isActivity()) {
            this.setDownArea(activityDownArea);
            this.getDownArea().updateUI();
        } else {
            this.setDownArea(wbsDownArea);
            this.getDownArea().updateUI();
        }
        if (node != null) {
            Parameter param = new Parameter();
//            param.put(DtoKEY.DTO, dataBean);
            param.put(DtoKEY.WBSTREE, node);
            param.put("listPanel", activityList);
            param.put("entryFunType",entryFunType);
            if (this.getDownArea().equals(activityDownArea)) {
                activityGeneral.setParameter(param);
                activityStatus.setParameter(param);
                activityWorkers.setParameter(param);
                relation.setParameter(param);
                activityWp.setParameter(param);
                activityCode.setParameter(param);
                pbs.setParameter(param);
                activityCost.setParameter(param);
                activityProcess.setParameter(param);
            } else {
                wbsDisp.setParameter(param);
            }

            try {
                this.getDownArea().getSelectedWorkArea().refreshWorkArea();
            } catch (Exception e) {

            }
        }
    }

    /////// 段5，保存数据
    public void saveWorkArea() {
        if (this.refreshFlag == true) {
            activityList.saveWorkArea();
            this.getDownArea().getSelectedWorkArea().saveWorkArea();
        }
    }

    public String[][] getParameterInfo() {
        String[][] parameterInfo = { {"accountId", "", "the rid of account"}
                                   , {"entryFunType", "", "entryFunType"}
        };
        return parameterInfo;
    }


    public void dataChanged(String name, Object value) {
        if (name.equals("account")) {
            Long acntRid = null;
            if (value instanceof String) {
                acntRid = new Long((String) value);
            } else if (value instanceof Long) {
                acntRid = (Long) value;
            }
            if (acntRid != null) {
                if (this.inAcntRid == null ||
                    acntRid.longValue() != inAcntRid.longValue()) {
                    this.inAcntRid = acntRid;
                    this.refreshFlag = true;
                    this.activityGeneral.hasGetManagers = false;
                    ProcessVariant.set("accountChanged_activity", Boolean.TRUE);
                }
            }
        } else if (name.equals("wbstree")) {
            if (value == null) {
                this.getDownArea().setSelected("General");
                this.getDownArea().disableAllTabs();
            } else {
                this.getDownArea().enableAllTabs();
            }
        }
    }
//
//    public static void main(String[] args) {
//        VwActivity workArea = new VwActivity();
//        Parameter param = new Parameter();
//        param.put("inAcntRid", new Long(1));
//        workArea.setParameter(param);
//
//        VWWorkArea workArea2 = new VWWorkArea();
//        workArea2.addTab("activity", workArea);
//
//        TestPanel.show(workArea2);
//        workArea.refreshWorkArea();
//
//    }
}
