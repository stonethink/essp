package client.essp.pms.qa.plan;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import c2s.dto.ITreeNode;
import c2s.essp.common.code.DtoKey;
import c2s.essp.common.excelUtil.IExcelParameter;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.excelUtil.VWJButtonExcel;
import client.essp.common.view.VWTDWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.pms.activity.VwActivityFilter;
import client.essp.pms.activity.VwActivityList;
import client.essp.pms.activity.process.guideline.VwAcGuideLine;
import client.essp.pms.qa.monitoring.VwQaMonitoring;
import client.essp.pms.wbs.process.checklist.VwCheckList;
import client.essp.pms.wbs.process.guideline.VwWbsGuideLine;
import client.framework.common.Global;
import client.framework.view.event.DataChangedListener;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.event.RowSelectedLostListener;
import client.framework.view.vwcomp.IVWAppletParameter;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJPopupEditor;
import com.wits.util.IVariantListener;
import com.wits.util.Parameter;
import com.wits.util.ProcessVariant;

public class VwPlan extends VWTDWorkArea implements IVWAppletParameter,
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
    VwActivityList planList;

    VwWbsGuideLine wbsGuideLine;
    VwCheckList activityCheckList;
    VwCheckList wbsCheckList;
    VwAcGuideLine activityGuideLine;


    VWWorkArea activityDownArea;
    VWWorkArea wbsDownArea;


    JButton refreshBtn = null;

    JButton expandBtn = null;
    JButton filterBtn = null;


    VWJButtonExcel btnExcel;

    private String entryFunType;

    VWJLabel filterLbl = new VWJLabel();

    public VwPlan() {
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

        planList = new VwActivityList(filterLbl);
        this.getTopArea().addTab("Plan", planList);

        activityDownArea = new VWWorkArea();
        wbsDownArea = new VWWorkArea();

        wbsGuideLine = new VwWbsGuideLine();
        activityGuideLine = new VwAcGuideLine();
        activityCheckList = new VwCheckList();

        wbsCheckList = new VwCheckList();

        activityDownArea.addTab("GuideLine", activityGuideLine);
        activityDownArea.addTab("QaCheckList", activityCheckList);

        wbsDownArea.addTab("GuideLine", wbsGuideLine);
        wbsDownArea.addTab("QaCheckList", wbsCheckList);

    }

    /**
     * 定义界面：定义界面事件
     */
    private void addUICEvent() {
        this.planList.getTreeTable().addRowSelectedLostListener(new
            RowSelectedLostListener() {
            public boolean processRowSelectedLost(int oldSelectedRow,
                                                  Object oldSelectedNode) {
                return processRowSelectedLostHandle(oldSelectedRow,
                    oldSelectedNode);
            }
        });

        this.planList.getTreeTable().addRowSelectedListener(new
            RowSelectedListener() {
            public void processRowSelected() {
                processRowSelectedactivityList();
            }
        });

        //filter Label
        VWJLabel filterTitle = new VWJLabel();
        filterTitle.setSize(20, 20);
//        filterTitle.setPreferredSize(new Dimension(80, 20));
//        filterTitle.setHorizontalAlignment(SwingConstants.RIGHT);
//        filterTitle.setText("Filter By:");
        planList.getButtonPanel().add(filterTitle);
        filterLbl.setText("Filter By:All Activity");
        planList.getButtonPanel().add(filterLbl);

        //expand
        expandBtn = planList.getButtonPanel().addButton("expand.png");
        expandBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedExpand(e);
            }
        });
        expandBtn.setToolTipText("Expand the tree");

        //filter
        filterBtn = planList.getButtonPanel().addButton("filter.gif");
        filterBtn.setToolTipText("Activity Filter");
        filterBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //弹出过滤条件选择窗口
                VwActivityFilter filter = new VwActivityFilter(planList.
                    getTreeTable(), planList, filterLbl);

                VWJPopupEditor pop = new VWJPopupEditor(getParentWindow(),
                    "Filter",
                    filter, 2);
                pop.setSize(300, 200);
                pop.show();
            }
        });
        filterBtn.setToolTipText("Filter");

        //Display
        TableColumnChooseDisplay chooseDisplay =
            new TableColumnChooseDisplay(planList.getTreeTable(),
                                         planList);
        JButton button = chooseDisplay.getDisplayButton();
        planList.getButtonPanel().addButton(button);
        button.setToolTipText("Display");

        DataChangedListener listener = new DataChangedListener() {
            public void processDataChanged() {
                planList.updateTreeTable();
                ITreeNode node = planList.getSelectedNode();
                setButtonStatus(node);
            }
        };

        //导出报表
        btnExcel = new VWJButtonExcel(new IExcelParameter(){
             public String getUrlAddress(){
                 StringBuffer sb = new StringBuffer(Global.appRoot);
                 sb.append(IExcelParameter.DEFAULT_EXCEL_JSP_ADDRESS);
                 sb.append("?");
                 sb.append(IExcelParameter.ACTION_ID);
                 sb.append("=");
                 sb.append(VwQaMonitoring.actionIdExcel);
                 sb.append("&");
                 sb.append("acntRid=");
                 sb.append(inAcntRid.longValue());

                 return sb.toString();
             }
         });

        planList.getButtonPanel().addButton(btnExcel);
        btnExcel.setToolTipText("Excel");

        //Refresh
        refreshBtn = planList.getButtonPanel().addLoadButton(new
            ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });
        refreshBtn.setToolTipText("Refresh");

        ProcessVariant.addDataListener("account", this);
        ProcessVariant.addDataListener("wbstree", this);

//        this.getDownArea().setSelectedIndex(0);
//        this.getDownArea().disableAllTabs();
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

                refreshBtn.setEnabled(true);

                expandBtn.setEnabled(true);
            } else if (dataBean.isInsert()) {

                refreshBtn.setEnabled(true);

                expandBtn.setEnabled(true);

            } else {

                refreshBtn.setEnabled(true);
                if (index > 0) {

                } else {

                }
                if (index < size - 1) {

                } else {

                }

                expandBtn.setEnabled(true);
            }
        } else {

            this.refreshBtn.setEnabled(true);

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
            planList.setParameter(param);
            planList.refreshWorkArea();
            this.refreshFlag = false;
        }
    }

    /////// 段4，事件处理
    public void actionPerformedAdd(ActionEvent e) {
        planList.actionPerformedAdd(e);
        //this.getDownArea().enableTabOnly("General");
    }

    public void actionPerformedLoad(ActionEvent e) {
        Parameter param = new Parameter();
        param.put("inAcntRid", this.inAcntRid);
        planList.setParameter(param);
        planList.reloadData();
//        activityGeneral.hasGetManagers = false;
    }

    public void actionPerformedExpand(ActionEvent e) {
        planList.actionPerformedExpand(e);
    }

    public void processRowSelectedactivityList() {
        ITreeNode node = planList.getSelectedNode();
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
            param.put(DtoKey.DTO, dataBean);
            param.put("listPanel", planList);

            param.put("inAcntRid", dataBean.getAcntRid());
            param.put("inWbsRid", dataBean.getWbsRid());
            param.put("inActivityRid", dataBean.getActivityRid());
            param.put("isOnlyRead",new Boolean(dataBean.isReadonly()));

            if (this.getDownArea().equals(activityDownArea)) {
                activityCheckList.setParameter(param);
                activityGuideLine.setParameter(param);
            } else {

                wbsGuideLine.setParameter(param);
                wbsCheckList.setParameter(param);
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
            planList.saveWorkArea();
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
//                    this.activityGeneral.hasGetManagers = false;
                    ProcessVariant.set("accountChanged", Boolean.TRUE);
                }
            }
        }

        if (name.equals("wbstree")) {
            if (value == null) {
                this.getDownArea().setSelected("General");
                this.getDownArea().disableAllTabs();
            } else {
                this.getDownArea().enableAllTabs();
            }
        }
    }

//    public static void main(String[] args) {
//        Global.todayDateStr = "2005-12-03";
//        Global.todayDatePattern = "yyyy-MM-dd";
//        Global.userId = "stone.shi";
//        DtoUser user = new DtoUser();
//        user.setUserID(Global.userId);
//        user.setUserLoginId(Global.userId);
//        HttpServletRequest request = new HttpServletRequestMock();
//        HttpSession session = request.getSession();
//        session.setAttribute(DtoUser.SESSION_USER, user);
//
//        VwPlan workArea = new VwPlan();
//        Parameter param = new Parameter();
//        param.put("inAcntRid", new Long(1));
//        workArea.setParameter(param);
//
//        VWWorkArea workArea2 = new VWWorkArea();
//        workArea2.addTab("Plan", workArea);
//
//        TestPanel.show(workArea2);
//        workArea.refreshWorkArea();
//
//    }
}
