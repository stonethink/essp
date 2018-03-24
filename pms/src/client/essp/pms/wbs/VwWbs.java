package client.essp.pms.wbs;

import client.essp.common.view.VWTDWorkArea;
import java.awt.Dimension;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.event.RowSelectedLostListener;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.wits.util.Parameter;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import c2s.dto.ITreeNode;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.essp.common.view.VWWorkArea;
import com.wits.util.TestPanel;
import c2s.essp.pms.wbs.DtoKEY;
import client.framework.view.event.DataChangedListener;
import com.wits.util.ProcessVariant;
import client.framework.view.vwcomp.IVWAppletParameter;
import client.essp.pms.wbs.activity.VwWbsActivityList;
import client.essp.pms.wbs.pbsAndFiles.VwPbsAndFile;
import com.wits.util.IVariantListener;
import client.framework.view.vwcomp.VWJLabel;
import client.essp.pms.wbs.checkpoint.VwCheckPointList;
import java.util.List;
import client.essp.pms.wbs.code.VwWbsCodeList;
import c2s.essp.pms.account.DtoAcntKEY;
import client.framework.view.vwcomp.VWJPopupEditor;
import java.awt.Frame;
import client.essp.pms.wbs.process.VwWbsProcess;

public class VwWbs extends VWTDWorkArea implements IVWAppletParameter,
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
    VwWbsList wbsList;
    VwWbsGeneral wbsGeneral;
    VwCheckPointList checkPoint;
    VwPbsAndFile pbs;
    VwWbsActivityList activityList;
    VwWbsCodeList wbsCode;
    VwWbsProcess wbsProcess;

    JButton addBtn = null;
    JButton deleteBtn = null;
    JButton refreshBtn = null;
    JButton upBtn = null;
    JButton downBtn = null;
    JButton leftBtn = null;
    JButton rightBtn = null;
    JButton expandBtn = null;
    JButton computeBtn = null;
    JButton filterBtn = null;
    private String entryFunType;
    VWJLabel filterLbl=new VWJLabel();

    public VwWbs() {
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

        wbsList = new VwWbsList(filterLbl);
        this.getTopArea().addTab("Wbs", wbsList);
        wbsGeneral = new VwWbsGeneral();
        this.getDownArea().addTab("General", wbsGeneral);
        wbsGeneral.setParentWorkArea(this.getDownArea());

        checkPoint = new VwCheckPointList();
        this.getDownArea().addTab("CheckPoint", checkPoint);
        pbs = new VwPbsAndFile();
        this.getDownArea().addTab("PBS", pbs);
//        earnedValue = new VwEarnedValue();
//        this.getDownArea().addTab("Earned Value", earnedValue);
        activityList = new VwWbsActivityList();
        this.getDownArea().addTab("Activity", activityList);

        wbsCode = new VwWbsCodeList();
        this.getDownArea().addTab("Code", wbsCode);

        wbsProcess = new VwWbsProcess();
        this.getDownArea().addTab("Process", wbsProcess);
    }

    /**
     * 定义界面：定义界面事件
     */
    private void addUICEvent() {
        this.wbsList.getTreeTable().addRowSelectedLostListener(new
            RowSelectedLostListener() {
            public boolean processRowSelectedLost(int oldSelectedRow,
                                                  Object oldSelectedNode) {
                return processRowSelectedLostHandle(oldSelectedRow,
                    oldSelectedNode);
            }
        });

        this.wbsList.getTreeTable().addRowSelectedListener(new
            RowSelectedListener() {
            public void processRowSelected() {
                ITreeNode node = wbsList.getSelectedNode();
                if (node != null) {
                    processRowSelectedWbsList(node);
                }
            }
        });

        //filter Label
        VWJLabel filterTitle=new VWJLabel();
        filterTitle.setSize(20,20);
//        filterTitle.setPreferredSize(new Dimension(80,20));
//        filterTitle.setHorizontalAlignment(SwingConstants.RIGHT);
//        filterTitle.setText("Filter By:");
        wbsList.getButtonPanel().add(filterTitle);
        filterLbl.setSize(60,20);
        filterLbl.setPreferredSize(new Dimension(60,20));
        filterLbl.setText("Filter By:All WBS");
        wbsList.getButtonPanel().add(filterLbl);

        //Add
        addBtn = wbsList.getButtonPanel().addAddButton(new
            ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd(e);

            }
        });
        addBtn.setToolTipText("add wbs");

        //Delete
        deleteBtn = wbsList.getButtonPanel().addDelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDel(e);
            }
        });

        //Down
        downBtn = wbsList.getButtonPanel().addButton("down.png");
        downBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDown(e);
            }
        });
       downBtn.setToolTipText("down");
        //Up
        upBtn = wbsList.getButtonPanel().addButton("up.png");
        upBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedUp(e);
            }
        });
        upBtn.setToolTipText("up");
        //Left
        leftBtn = wbsList.getButtonPanel().addButton("left.png");
        leftBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLeft(e);
            }
        });
        leftBtn.setToolTipText("left");
        //Right
        rightBtn = wbsList.getButtonPanel().addButton("right.png");
        rightBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedRight(e);
            }
        });
      rightBtn.setToolTipText("right");
        //expand
        expandBtn = wbsList.getButtonPanel().addButton("expand.png");
        expandBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedExpand(e);
            }
        });
       expandBtn.setToolTipText("expand");
        //filter
        filterBtn = wbsList.getButtonPanel().addButton("filter.gif");
        filterBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //弹出过滤条件选择窗口
                VwWbsFilter filter=new VwWbsFilter(wbsList.getTreeTable(),wbsList,filterLbl);

                VWJPopupEditor pop = new VWJPopupEditor(getParentWindow(),
                                     "Filter",
                                     filter, 2);
                pop.setSize(300,200);
                pop.show();
            }
        });
        filterBtn.setToolTipText("WBS filter");
        //Display
        TableColumnChooseDisplay chooseDisplay =
            new TableColumnChooseDisplay(wbsList.getTreeTable(), wbsList);
        JButton button = chooseDisplay.getDisplayButton();
        wbsList.getButtonPanel().addButton(button);

        computeBtn = wbsList.getButtonPanel().addButton("calc.gif");
        computeBtn.setToolTipText("Complete Rate");
        computeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wbsList.actionPerformedCompute(e);
            }
        });

        //Refresh
        refreshBtn = wbsList.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });

        DataChangedListener listener = new DataChangedListener() {
            public void processDataChanged() {
                wbsList.updateTreeTable();
                wbsList.fireProcessDataChange();
                ITreeNode node = wbsList.getSelectedNode();
                if (node != null) {
                    processRowSelectedWbsList(node);
                }
            }
        };
        wbsGeneral.addDataChangedListener(listener);

        ProcessVariant.addDataListener("account", this);
        ProcessVariant.addDataListener("wbstree", this);

        this.getDownArea().setSelectedIndex(0);
        this.getDownArea().disableAllTabs();
    }

    /////// 段2，设置参数：获取传入参数
    public void setParameter(Parameter param) {
        String accountId = (String) param.get("accountId");
        if (accountId != null) {
            this.inAcntRid = new Long(accountId);
        } else {
            this.inAcntRid = (Long) param.get("inAcntRid");
        }
        if (this.inAcntRid == null) {
            this.inAcntRid = new Long(0);
        }
        //add by xr 2006/06/26
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
            wbsList.setParameter(param);
            wbsList.refreshWorkArea();

            this.refreshFlag = false;
        }
    }

    /////// 段4，事件处理
    public void actionPerformedAdd(ActionEvent e) {
        wbsList.actionPerformedAdd(e);
        //this.getDownArea().enableTabOnly("General");
    }

    public void actionPerformedLoad(ActionEvent e) {
        Parameter param = new Parameter();
        param.put("inAcntRid", this.inAcntRid);
        wbsList.setParameter(param);
        wbsList.reloadData();
        wbsGeneral.hasGetManagers = false;
    }

    public void actionPerformedDel(ActionEvent e) {
        wbsList.actionPerformedDelete(e);
    }

    public void actionPerformedDown(ActionEvent e) {
        ITreeNode node = wbsList.getSelectedNode();
        wbsList.actionPerformedDown(e);
        this.setButtonStatus(node);
    }

    public void actionPerformedUp(ActionEvent e) {
        ITreeNode node = wbsList.getSelectedNode();
        wbsList.actionPerformedUp(e);
        this.setButtonStatus(node);
    }

    public void actionPerformedLeft(ActionEvent e) {
        ITreeNode node = wbsList.getSelectedNode();
        wbsList.actionPerformedLeft(e);
        this.setButtonStatus(node);
    }

    public void actionPerformedRight(ActionEvent e) {
        ITreeNode node = wbsList.getSelectedNode();
        wbsList.actionPerformedRight(e);
        this.setButtonStatus(node);
    }

    public void actionPerformedExpand(ActionEvent e) {
        wbsList.actionPerformedExpand(e);
    }

    public boolean processRowSelectedLostHandle(int oldSelectedRow,
                                                Object oldSelectedNode) {
        boolean result = true;
        VWWorkArea selectedArea = this.getDownArea().getSelectedWorkArea();
        if (selectedArea instanceof RowSelectedLostListener) {
            result = ((RowSelectedLostListener) selectedArea).
                     processRowSelectedLost(oldSelectedRow, oldSelectedNode);
        }
        return result;
    }

    private void setButtonStatus(ITreeNode node) {
        DtoWbsActivity dataBean;
        if (node != null) {
            dataBean = (DtoWbsActivity) node.getDataBean();
        } else {
            dataBean = new DtoWbsActivity();
        }

        /**
         * 设置按钮状态
         */
        if (dataBean.isReadonly()) {
            this.addBtn.setEnabled(false);
            this.deleteBtn.setEnabled(false);
            this.upBtn.setEnabled(false);
            this.downBtn.setEnabled(false);
            this.leftBtn.setEnabled(false);
            this.rightBtn.setEnabled(false);
        } else if (dataBean.isInsert()) {
            this.addBtn.setEnabled(true);
            this.deleteBtn.setEnabled(true);
            this.upBtn.setEnabled(false);
            this.downBtn.setEnabled(false);
            this.leftBtn.setEnabled(false);
            this.rightBtn.setEnabled(false);
        } else {
            this.addBtn.setEnabled(true);
            this.deleteBtn.setEnabled(true);
            this.upBtn.setEnabled(true);
            this.downBtn.setEnabled(true);
            this.leftBtn.setEnabled(true);
            ITreeNode parent = node.getParent();
            if (parent != null) {
//                int size = node.getParent().getChildCount();
//                int index = node.getParent().getIndex(node);
//                log.info("size=" + size + ",index=" + index);
                if (parent.getParent() == null) {
                    this.leftBtn.setEnabled(false);
                }
                List childs = parent.children();
                if (childs.indexOf(node) > 0) {
                    this.rightBtn.setEnabled(true);
                } else {
                    this.rightBtn.setEnabled(false);
                }
                if (childs.indexOf(node) == 0) {
                    this.upBtn.setEnabled(false);
                }
                if (childs.indexOf(node) == childs.size() - 1) {
                    this.downBtn.setEnabled(false);
                }
            } else {
                this.leftBtn.setEnabled(false);
                this.rightBtn.setEnabled(false);
                this.upBtn.setEnabled(false);
                this.downBtn.setEnabled(false);
                this.deleteBtn.setEnabled(false);
            }
        }
    }

    public void processRowSelectedWbsList(ITreeNode node) {
//      wbsGeneral.saveWorkArea();
        DtoWbsActivity dataBean;
        if (node != null) {
            dataBean = (DtoWbsActivity) node.getDataBean();
        } else {
            dataBean = new DtoWbsActivity();
        }

        setButtonStatus(node);

        Parameter param = new Parameter();
        param.put(DtoKEY.WBSTREE, node);
        param.put("wbs", dataBean);
        param.put("listPanel", this.wbsList);
        param.put("entryFunType",entryFunType);
        wbsGeneral.setParameter(param);
        checkPoint.setParameter(param);
//        earnedValue.setParameter(param);
        activityList.setParameter(param);
        pbs.setParameter(param);
        wbsCode.setParameter(param);
        wbsProcess.setParameter(param);
        try {
            this.getDownArea().getSelectedWorkArea().refreshWorkArea();
        } catch (Exception e) {

        }
    }

    /////// 段5，保存数据
    public void saveWorkArea() {
        if (this.refreshFlag == true) { //???
            wbsList.saveWorkArea();
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
                    this.wbsGeneral.hasGetManagers = false;
                    ProcessVariant.set("accountChanged_wbs", Boolean.TRUE);
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

    public static void main(String[] args) {
//        User user = new User();
//        user.setUserID("0");
//        ThreadVariant thread = ThreadVariant.getInstance();
//        thread.set(User.SESSION_NAME, user);

        VwWbs workArea = new VwWbs();
        Parameter param = new Parameter();
        param.put("inAcntRid", new Long(7));
        workArea.setParameter(param);

        VWWorkArea workArea2 = new VWWorkArea();
        workArea2.addTab("Wbs", workArea);

        TestPanel.show(workArea2);
        workArea.refreshWorkArea();
    }
}
