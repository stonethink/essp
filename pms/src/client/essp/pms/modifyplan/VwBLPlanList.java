package client.essp.pms.modifyplan;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.wbs.DateCheck;
import c2s.essp.pms.wbs.DtoKEY;
import c2s.essp.pms.wbs.DtoWbsActivity;
import c2s.essp.pms.wbs.DtoWbsTreeNode;
import client.essp.common.loginId.VWJLoginId;
import client.essp.common.view.VWAccountLabel;
import client.essp.common.view.VWWorkArea;
import client.essp.pms.activity.wp.VwWpList;
import client.essp.pms.modifyplan.base.VWTreeTableWorkAreaForBL;
import client.essp.pms.wbs.WbsNodeViewManager;
import client.essp.pms.workerPop.VwAllocateWorker;
import client.essp.pwm.wp.CalculateWPDefaultDate;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMComboBox;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.IVariantListener;
import com.wits.util.Parameter;
import com.wits.util.ProcessVariant;
import com.wits.util.TestPanel;
import client.essp.pms.templatePop.WbsTemplateDragSource;

public class VwBLPlanList extends VWTreeTableWorkAreaForBL implements
    IVariantListener {
    private static final String ACTIONID_LIST = "FWbsActivityListAction";
    private static final String ACTIONID_NODECOPY ="FBLPlanModifyCopyNodeAction";
    private static final String ACTIONID_SAVEALL = "FBLPlanModifySaveAction";
    private static final String ACTIONID_GETACNT_LABOR =
        "FBLPlanModifyGetAcntLabor";
    //操作Activity的Action
//    private static final String ACTIONID_ADDACTIVITY = "FWbsActivityAddAction"; //增加Activity
    private static final String ACTIONID_DELETE = "FWbsActivityDeleteAction"; //删除
//    private static final String ACTIONID_UPDATE = "FWbsActivityUpdateAction"; //更新
    private static final String ACTIONID_UPMOVEFORACTIVITY =
        "FWbsActivityUpMoveAction";
    private static final String ACTIONID_DOWNMOVEFORACTIVITY =
        "FWbsActivityDownMoveAction";
    private static final String ACTIONID_LEFTMOVEFORACTIVITY =
        "FWbsActivityLeftMoveAction";
    private static final String ACTIONID_RIGHTMOVEFORACTIVITY =
        "FWbsActivityRightMoveAction";
    private static final String ACTIONID_GETCODE = "FWbsActivityGetCodeAction";
    private static final String ACTIONID_COMPUTE = "FWbsComputeAction"; //计算
    private static final String ACTIONID_GETRID = "FWbsGetRidAction";

    //操作WBS的Action
//    private static final String ACTIONID_ADDWBS = "FWbsAddAction"; //增加WBS
//    private static final String ACTIONID_DELETEFORWBS = "FWbsDeleteAction";
//    private static final String ACTIONID_UPDATEFORWBS = "FWbsUpdateAction";
    private static final String ACTIONID_UPMOVEFORWBS = "FWbsUpMoveAction";
    private static final String ACTIONID_DOWNMOVEFORWBS = "FWbsDownMoveAction";
    private static final String ACTIONID_LEFTMOVEFORWBS = "FWbsLeftMoveAction";
    private static final String ACTIONID_RIGHTMOVEFORWBS =
        "FWbsRightMoveAction";

    public static final String ACTIONID_ACT_PRE_RLATION_LIST =
        "FWbsActivitySuccessorListAction";

    /**
     * define common data (globe)
     */
    private Object[][] configs = null;
    private static final String TREE_COLUMN_NAME = "name";
    private Long inAcntRid;
    private String entryFunType;
    private VWAccountLabel accountLabel = null;
    JButton btnWorker = null;
    VwAllocateWorker workers;

    private VWJLabel filterLbl;
    public VwBLPlanList(VWJLabel filterLbl, VWAccountLabel accountLabel) {
        this();
        this.accountLabel = accountLabel;
        this.filterLbl = filterLbl;
    }

    //模版的标志
    //用以区别模版的configs不同
    private boolean templateFlag = false;

    public VwBLPlanList() {
        try {
            VWJReal weightDisp = new VWJReal();
            weightDisp.setMaxInputDecimalDigit(2);
            weightDisp.setMaxInputIntegerDigit(3);

            VWJReal completeRateDisp = new VWJReal();
            completeRateDisp.setMaxInputDecimalDigit(2);
            completeRateDisp.setMaxInputIntegerDigit(3);

            VWJReal timeLimitDisp = new VWJReal();
            timeLimitDisp.setMaxInputDecimalDigit(0);
            timeLimitDisp.setMaxInputIntegerDigit(8);

            VWJDate planDate = new VWJDate();
            planDate.setCanSelect(true);

            VWJComboBox activityCalMethod = new VWJComboBox();
            activityCalMethod.setModel(VMComboBox.toVMComboBox(DtoWbsActivity.
                ACTIVITY_COMPLETE_METHOD_TITLE,
                DtoWbsActivity.ACTIVITY_COMPLETE_METHOD));

            configs = new Object[][] { {"Name", "name",
                      VMColumnConfig.EDITABLE, null}, {"Code", "code",
                      VMColumnConfig.EDITABLE, new VWJText(), Boolean.TRUE},
                      {"Duration", "timeLimit", VMColumnConfig.UNEDITABLE,
                      timeLimitDisp}, {"Planned Start", "plannedStart",
                      VMColumnConfig.EDITABLE, planDate}, {"Planned Finish",
                      "plannedFinish", VMColumnConfig.EDITABLE, planDate},
                      {"Actual Start", "actualStart", VMColumnConfig.UNEDITABLE,
                      planDate, Boolean.TRUE}, {"Actual Finish",
                      "actualFinish", VMColumnConfig.UNEDITABLE, planDate,
                      Boolean.TRUE}, {"Weight", "weight",
                      VMColumnConfig.EDITABLE, weightDisp}, {"%Complete",
                      "completeRate", VMColumnConfig.EDITABLE,
                      completeRateDisp, Boolean.TRUE}, {"Manager", "manager",
                      VMColumnConfig.UNEDITABLE, new VWJLoginId()},
                      {"Complete Method", "completeMethod",
                      VMColumnConfig.EDITABLE, activityCalMethod, Boolean.TRUE}
            };
            super.jbInit(configs, TREE_COLUMN_NAME,
                         DtoWbsActivity.class, new WbsNodeViewManager());
            //调整列的宽度
            JTableHeader header = this.getTreeTable().getTableHeader();

            header.setPreferredSize(new Dimension(Double.valueOf(header.getSize().
                getWidth()).intValue(), 40));

            TableColumnModel tcModel = header.getColumnModel();
            tcModel.getColumn(0).setPreferredWidth(250);
            tcModel.getColumn(1).setPreferredWidth(30);
            tcModel.getColumn(2).setPreferredWidth(70);
            tcModel.getColumn(3).setPreferredWidth(70);
            tcModel.getColumn(4).setPreferredWidth(30);
            tcModel.getColumn(5).setPreferredWidth(100);

            model.setTreeColumnEditable(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        addUICEvent();
        //拖放事件
        (new WbsTemplateDragSource(getTreeTable())).create();
    }

    protected void addUICEvent() {
        //捕获事件－－－－
        ProcessVariant.addDataListener("planningtree", this);
        btnWorker = this.getButtonPanel().addButton("worker.png");
        btnWorker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Point p= ((JButton) e.getSource()).getLocationOnScreen();
                p.setLocation(p.getX()+230, p.getY()+420);
                actionPerformedWorker(p);
            }
        });
        btnWorker.setToolTipText("Workers");
        this.getTreeTable().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedIndex = getTreeTable().getSelectedColumn();
                    if (selectedIndex < 0) {
                        return;
                    }
                    String selectedColName = getTreeTable().getModel().
                                             getColumnName(selectedIndex);
                    if ("Manager".equals(selectedColName)) {
                        btnWorker.doClick();
                    }
                }
            }
        });


    }
    protected void actionPerformedWorker(Point p) {
        if (workers == null) {
            Container c = this.getMyParentWindow();
            workers = new VwAllocateWorker( c);
            Parameter param = new Parameter();
            param.put("acntRid", inAcntRid);
            workers.setParameter(param);
            workers.firePropertyChange("resizable", Boolean.parseBoolean("false"), Boolean.parseBoolean("true"));
            this.add(workers, BorderLayout.EAST);
        } else {
            workers.showPopSelect();
        }
    }

    /**
     * 取得父窗口句柄
     * @return Frame
     */
    protected Container getMyParentWindow() {
        java.awt.Container c = this.getParent();

        while (c != null) {
            if ((c instanceof java.awt.Frame) || (c instanceof java.awt.Dialog)) {
                return c;
            }

            c = c.getParent();
        }

        return null;
    }



    public void actionPerformedAddWBS(ActionEvent e) {
        ITreeNode currentNode = this.getSelectedNode();
        if (currentNode != null) {
            DtoWbsActivity currentDataBean = (DtoWbsActivity) currentNode.
                                             getDataBean();
            if (currentDataBean.isInsert()) {
//                comMSG.dispErrorDialog(
//                    "This wbs has not been saved.Cannot add a new wbs to this wbs.");
                currentDataBean.setOp(IDto.OP_NOCHANGE);
            }
            DtoWbsActivity dataBean = new DtoWbsActivity();
            dataBean.setAcntRid(currentDataBean.getAcntRid());
            dataBean.setParentRid(currentDataBean.getWbsRid());
            dataBean.setWbs(true);
            InputInfo inputInfo = new InputInfo();
            inputInfo.setInputObj(DtoKEY.DTO, dataBean);
            inputInfo.setActionId(VwBLPlanList.ACTIONID_GETRID);

            ReturnInfo returnInfo = accessData(inputInfo);
            if (!returnInfo.isError()) {
                dataBean = (DtoWbsActivity) returnInfo.getReturnObj(DtoKEY.
                    DTO);
                Long rid = dataBean.getWbsRid();
                String code = "";
                if (rid != null) {
                    java.text.DecimalFormat df = new java.text.
                                                 DecimalFormat("W0000");
                    code = df.format(rid);
                }
                dataBean.setName(code);
                dataBean.setAutoCode(code);
                dataBean.setCode(code);
                dataBean.setWeight(new Double(1.00));
                dataBean.setCompleteMethod(DtoWbsActivity.
                                           WBS_COMPLETE_BY_ACTIVITY);

                dataBean.setOp(IDto.OP_INSERT);
                DtoWbsTreeNode newNode = new DtoWbsTreeNode(dataBean);
                this.getTreeTable().addRow(newNode);
            }

        } else {
            ITreeNode root = (ITreeNode)this.getTreeTable().getTreeTableModel().
                             getRoot();
            if (root == null) {
                DtoWbsActivity dataBean = new DtoWbsActivity();
                dataBean.setAcntRid(inAcntRid);
                dataBean.setParentRid(null);
                dataBean.setWbs(true);

                InputInfo inputInfo = new InputInfo();
                inputInfo.setInputObj(DtoKEY.DTO, dataBean);
                inputInfo.setActionId(VwBLPlanList.ACTIONID_GETRID);

                ReturnInfo returnInfo = accessData(inputInfo);
                if (!returnInfo.isError()) {
                    dataBean = (DtoWbsActivity) returnInfo.getReturnObj(DtoKEY.
                        DTO);
                }

                dataBean.setOp(IDto.OP_INSERT);
                DtoWbsTreeNode newNode = new DtoWbsTreeNode(dataBean);
                this.getTreeTable().setRoot(newNode);

            }
        }
    }

    public Long getWbsRid(DtoWbsActivity currentDataBean) {
        Long rid = null;
        DtoWbsActivity dataBean = new DtoWbsActivity();
        dataBean.setAcntRid(currentDataBean.getAcntRid());
        dataBean.setParentRid(currentDataBean.getWbsRid());
        dataBean.setWbs(true);

        InputInfo inputInfo = new InputInfo();
        inputInfo.setInputObj(DtoKEY.DTO, dataBean);
        inputInfo.setActionId(VwBLPlanList.ACTIONID_GETRID);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            dataBean = (DtoWbsActivity) returnInfo.getReturnObj(DtoKEY.
                DTO);
            rid = dataBean.getWbsRid();
        } else {
            //取RID失败
        }
        return rid;

    }

    public void actionPerformedAddActivity(ActionEvent e) {
//        if (!checkUnsaved()) {
//            return;
//        }
        ITreeNode currentNode = this.getSelectedNode();
        if (currentNode != null) {
            DtoWbsActivity currentDataBean = (DtoWbsActivity) currentNode.
                                             getDataBean();
            if (currentDataBean.isActivity()) {
                currentNode = currentNode.getParent();
                currentDataBean = (DtoWbsActivity) currentNode.
                                  getDataBean();
            }

            if (currentDataBean.isInsert()) {
                currentDataBean.setOp(IDto.OP_NOCHANGE);
//                comMSG.dispErrorDialog(
//                    "This wbs has not been saved.Cannot add a new activity to this wbs.");
            }

            DtoWbsActivity dataBean = new DtoWbsActivity();
            dataBean.setAcntRid(currentDataBean.getAcntRid());
            //dataBean.setParentRid(currentDataBean.getWbsRid());
            dataBean.setWbsRid(currentDataBean.getWbsRid());
            dataBean.setActivity(true);

            InputInfo inputInfo = new InputInfo();
            inputInfo.setInputObj(DtoKEY.DTO, dataBean);
            inputInfo.setActionId(VwBLPlanList.ACTIONID_GETCODE);

            ReturnInfo returnInfo = accessData(inputInfo);
            dataBean = (DtoWbsActivity) returnInfo.getReturnObj(DtoKEY.DTO);
            dataBean.setAutoCode(dataBean.getCode());
            dataBean.setName(dataBean.getCode());
            dataBean.setWeight(new Double(1.00));
            Date wbsStart = currentDataBean.getPlannedStart();
            Date wbsFinish = currentDataBean.getPlannedFinish();
            Date[] tmp = CalculateWPDefaultDate.calculateDefaultPlanDate(wbsStart,
                wbsFinish);
            dataBean.setPlannedStart(tmp[0]);
            dataBean.setPlannedFinish(tmp[1]);
            dataBean.setTimeLimit(WorkCalendar.calculateTimeLimit(tmp[0],
                tmp[1]));
            dataBean.setTimeLimitType(DtoWbsActivity.ACTIVITY_TIME_LIMIT_TYPE[0]);
            dataBean.setOp(IDto.OP_INSERT);
            DtoWbsTreeNode newNode = new DtoWbsTreeNode(dataBean);
            this.getTreeTable().addRow(currentNode, newNode);
            fireProcessDataChange();
        }

    }

    public void actionPerformedDelete(ActionEvent e) {
        int option = comMSG.dispConfirmDialog("Do you delete the data?");
        if (option == Constant.OK) {
            ITreeNode deleteNode = this.getSelectedNode();
            ITreeNode parentNode = deleteNode.getParent();
            IDto dataBean = (IDto) deleteNode.getDataBean();
            DtoWbsActivity parentBean = (DtoWbsActivity) parentNode.getDataBean();

            //delete from db
            if (!dataBean.isInsert()) {
                InputInfo inputInfo = new InputInfo();
                inputInfo.setInputObj(DtoKEY.DTO, dataBean);
                inputInfo.setActionId(VwBLPlanList.ACTIONID_DELETE);

                ReturnInfo returnInfo = accessData(inputInfo);
                if (!returnInfo.isError()) {
                    getTreeTable().deleteRow();
                    if (parentNode.getChildCount() == 0) {
                        parentBean.setHasActivity(false);
                    }
                    fireProcessDataChange();
                }
            } else {
                getTreeTable().deleteRow();
            }
        }
    }


    //删除指定的节点，专为cut使用
    public void actionPerformedDelete(ITreeNode node) {
//        int option = comMSG.dispConfirmDialog("Do you delete the data?");
//        if (option == Constant.OK) {
        ITreeNode deleteNode = node;
        ITreeNode parentNode = deleteNode.getParent();
        IDto dataBean = (IDto) deleteNode.getDataBean();
        DtoWbsActivity parentBean = (DtoWbsActivity) parentNode.getDataBean();

        //delete from db
        if (!dataBean.isInsert()) {
            InputInfo inputInfo = new InputInfo();
            inputInfo.setInputObj(DtoKEY.DTO, dataBean);
            inputInfo.setActionId(VwBLPlanList.ACTIONID_DELETE);

            ReturnInfo returnInfo = accessData(inputInfo);
            if (!returnInfo.isError()) {
                getTreeTable().deleteRow(node);
                if (parentNode.getChildCount() == 0) {
                    parentBean.setHasActivity(false);
                }
            }
        } else {
            this.getTreeTable().deleteRow(node);
        }

    }

    public ReturnInfo actionPerformedSave() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(VwBLPlanList.ACTIONID_SAVEALL);
        inputInfo.setInputObj("RootNode", this.getModel().getRoot());
        ReturnInfo returnInfo = accessData(inputInfo);
//        ITreeNode root = (ITreeNode) returnInfo.getReturnObj("RootNode");
//        this.getModel().setRoot(root);
        return returnInfo;
    }

    public void actionPerformedDownForWBS(ActionEvent e) {
        ITreeNode node = this.getSelectedNode();
        //save this node to db
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(VwBLPlanList.ACTIONID_DOWNMOVEFORWBS);
        inputInfo.setInputObj(DtoKEY.DTO, node);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            this.getTreeTable().downMove();
            fireProcessDataChange();
        }
    }

    public void actionPerformedDownForActivity(ActionEvent e) {
        ITreeNode node = this.getSelectedNode();
        //save this node to db
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(VwBLPlanList.ACTIONID_DOWNMOVEFORACTIVITY);
        inputInfo.setInputObj(DtoKEY.DTO, node);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            getTreeTable().downMove();
            fireProcessDataChange();
        }
    }

    public void actionPerformedUpForWBS(ActionEvent e) {
        ITreeNode node = this.getSelectedNode();
//        DtoWbsTreeNode node = (DtoWbsTreeNode)this.getSelectedNode();
        //save this node to db
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(VwBLPlanList.ACTIONID_UPMOVEFORWBS);
        inputInfo.setInputObj(DtoKEY.DTO, node);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            this.getTreeTable().upMove();
            fireProcessDataChange();
        }
    }

    public void actionPerformedUpForActivity(ActionEvent e) {
        ITreeNode node = this.getSelectedNode();
        //save this node to db
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(VwBLPlanList.ACTIONID_UPMOVEFORACTIVITY);
        inputInfo.setInputObj(DtoKEY.DTO, node);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            this.getTreeTable().upMove();
            fireProcessDataChange();
        }
    }


    public void actionPerformedLeftForWBS(ActionEvent e) {
        DtoWbsTreeNode node = (DtoWbsTreeNode)this.getSelectedNode();
        //save this node to db
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(VwBLPlanList.ACTIONID_LEFTMOVEFORWBS);
        inputInfo.setInputObj(DtoKEY.DTO, node);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            this.getTreeTable().leftMove();
            fireProcessDataChange();
        }
    }

    public void actionPerformedLeftForActivity(ActionEvent e) {
        ITreeNode node = this.getSelectedNode();

        //save this node to db
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(VwBLPlanList.ACTIONID_LEFTMOVEFORACTIVITY);
        inputInfo.setInputObj(DtoKEY.DTO, node);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            getTreeTable().leftMove();
            fireProcessDataChange();
        }
    }

    public void actionPerformedRightForWBS(ActionEvent e) {
        ITreeNode node = this.getSelectedNode();

        //save this node to db
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(VwBLPlanList.ACTIONID_RIGHTMOVEFORWBS);
        inputInfo.setInputObj(DtoKEY.DTO, node);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            this.getTreeTable().rightMove();

            DateCheck.upModifyDate(node, "anticipatedStart",
                                   DateCheck.TYPE_START_DATE);
            DateCheck.upModifyDate(node, "anticipatedFinish",
                                   DateCheck.TYPE_FINISH_DATE);

            fireProcessDataChange();
        }
    }
    //把srcWbsTreeNode复制到destWbsTreeNode
    public DtoWbsTreeNode copyNode(DtoWbsTreeNode srcWbsTreeNode,DtoWbsTreeNode destWbsTreeNode){
        return copyNode(srcWbsTreeNode,destWbsTreeNode,"false");

    }
    //把源节点srcWbsTreeNode复制（isCut=="false"）或剪切（isCut=="true"）到目标节点destWbsTreeNode
    public DtoWbsTreeNode copyNode(DtoWbsTreeNode srcWbsTreeNode,DtoWbsTreeNode destWbsTreeNode,String isCut){
       InputInfo inputInfo = new InputInfo();
       inputInfo.setInputObj("strItreeNode",srcWbsTreeNode);
       inputInfo.setInputObj("destItreeNode",destWbsTreeNode);
       inputInfo.setInputObj("isCut",isCut);
       inputInfo.setActionId(ACTIONID_NODECOPY);
       ReturnInfo returnInfo =accessData(inputInfo);
       if(returnInfo.isError() == false){
           String hasCopy = (String)returnInfo.getReturnObj("hasCopy");
           if(hasCopy != null && "true".equals(hasCopy)){
//               this.reloadData();
               DtoWbsTreeNode wbsTreeNode = (DtoWbsTreeNode) returnInfo.getReturnObj(
                     DtoKEY.WBSTREE);
//
//               destWbsTreeNode.addChild(wbsTreeNode);
//                getTreeTable().addRow(destWbsTreeNode, wbsTreeNode);

             return wbsTreeNode;
           }
       }
       return null;

   }

    public DtoWbsTreeNode getBackRoot() {
        String curFilter = this.filterLbl.getText();
        if (curFilter != null &&
            !curFilter.equals(VwBLPlanFilter.FILTER_CONDITION[0])) {
            reloadData();
        }
        return (DtoWbsTreeNode)this.getModel().getRoot();
    }

    public void actionPerformedRightForActivity(ActionEvent e) {
        //此方法实质上在此用不上，先保留着再说
        ITreeNode node = this.getSelectedNode();

        //save this node to db
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(VwBLPlanList.ACTIONID_RIGHTMOVEFORACTIVITY);
        inputInfo.setInputObj(DtoKEY.DTO, node);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            getTreeTable().rightMove();
            fireProcessDataChange();
        }
    }

    public void actionPerformedExpand(ActionEvent e) {
        this.getTreeTable().expandsRow();
    }

    public void actionPerformedCompute(ActionEvent e) {
        //计算之前先清除过滤
        String curFilter = filterLbl.getText();
        if (curFilter != null &&
            !curFilter.equals(VwBLPlanFilter.FILTER_CONDITION[0])) {
            reloadData();
        }

        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(VwBLPlanList.ACTIONID_COMPUTE);
        inputInfo.setInputObj(DtoKEY.ACCOUNT_ID, inAcntRid.longValue() + "");

        ReturnInfo returnInfo = accessData(inputInfo);

        if (!returnInfo.isError()) {
            DtoWbsTreeNode root = (DtoWbsTreeNode) returnInfo.getReturnObj(
                DtoKEY.WBSTREE);
            root.setActivityTree(true);
            fireProcessDataChange();
            this.getTreeTable().setRoot(root);
            //this.getTreeTable().updateUI();
        }
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.inAcntRid = (Long) (param.get("inAcntRid"));
        if(workers != null) {
            Parameter wkParam = new Parameter();
            wkParam.put("acntRid", inAcntRid);
            workers.setParameter(wkParam);
        }

        //增加界面菜单的参数,SEPG可任意修改,其他菜单保持不变
        String newEntryFunType = (String) param.get("entryFunType");
        if(newEntryFunType != null) {
            entryFunType = newEntryFunType;
        } else if (entryFunType == null || entryFunType.length() == 0) {
            entryFunType = DtoAcntKEY.PMS_ACCOUNT_FUN;
        }

    }

    public void reloadData() {
        if (inAcntRid == null) {
            inAcntRid = new Long(0);
        }
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(VwBLPlanList.ACTIONID_LIST);
        inputInfo.setInputObj("entryFunType", entryFunType);
        inputInfo.setInputObj("ForceUseAcntRid", inAcntRid.longValue() + "");
        ReturnInfo returnInfo = accessData(inputInfo);

        if (!returnInfo.isError()) {
            DtoWbsTreeNode root = (DtoWbsTreeNode) returnInfo.getReturnObj(
                DtoKEY.WBSTREE);
            root.setActivityTree(true);

            //将所有的节点的isReadonly设为false
            //以保证所有只要有权限修改基线计划的人都可以修改
//            setNodeWrittable(root);
            this.getTreeTable().setRoot(root);
            if (filterLbl != null) {
                filterLbl.setText(VwBLPlanFilter.FILTER_CONDITION[0]);
            }
            DtoWbsActivity dto = (DtoWbsActivity) root.getDataBean();
//            inAcntRid = dto.getAcntRid();

//            ProcessVariant.set("accountChanged", Boolean.FALSE);

            fireProcessDataChange();
            this.getTreeTable().updateUI();
        }

//        if (inAcntRid != null && !templateFlag) {
//            VWJComboBox manager = new VWJComboBox();
//            List labor = null;
//            InputInfo inputInfoForLabor = new InputInfo();
//            inputInfoForLabor.setActionId(VwBLPlanList.ACTIONID_GETACNT_LABOR);
//            inputInfoForLabor.setInputObj("AcntRid", inAcntRid.toString());
//            ReturnInfo returnInfoForLabor = accessData(inputInfoForLabor);
//            if (!returnInfoForLabor.isError()) {
//                labor = (List) returnInfoForLabor.getReturnObj("AcntLaborList");
//            }
//            if (labor != null) {
//                String[] nameStr = new String[labor.size()];
//                String[] valueStr = new String[labor.size()];
//                for (int i = 0; i < labor.size(); i++) {
//                    nameStr[i] = ((DtoUser) labor.get(i)).getUserName();
//                    valueStr[i] = ((DtoUser) labor.get(i)).getUserLoginId();
//                }
//                manager.setVMComboBox(VMComboBox.toVMComboBox(nameStr, valueStr));
//            }
//            VMTreeTableModel tempModel = (VMTreeTableModel)this.treeTable.
//                                         getTreeTableModel();
//            Object[] obj = {"Manager", "manager",
//                           VMColumnConfig.EDITABLE, new VWJCmbLoginId(manager)};
//            tempModel.updateColumnConfig(obj);
//            VWUtil.setTreeTableEditor(this.treeTable);
//        }
    }

    //设置所有节点的isReadonly属性为false
    private void setNodeWrittable(ITreeNode node) {
        DtoWbsActivity dataBean = (DtoWbsActivity) node.getDataBean();
        dataBean.setReadonly(false);
        node.setDataBean(dataBean);
        if (!node.isLeaf()) {
            List list = node.children();
            for (int i = 0; i < list.size(); i++) {
                setNodeWrittable((ITreeNode) list.get(i));
            }
        }
    }


    //页面刷新－－－－－
    protected void resetUI() {
        reloadData();
//        Object wbstree = ProcessVariant.get("planningtree");
//        if (wbstree != null) {
//            DtoWbsTreeNode root = (DtoWbsTreeNode) wbstree;
//            Object acccountChanged = ProcessVariant.get("accountChanged");
//            if (acccountChanged != null && Boolean.TRUE.equals(acccountChanged)) {
//                reloadData();
//            } else {
//                root = (DtoWbsTreeNode) root.clone();
//                root.setActivityTree(true);
//                this.getTreeTable().setRoot(root);
//                DtoWbsActivity rootDto = (DtoWbsActivity) root.getDataBean();
//                accountLabel.setModel(rootDto);
//            }
//        } else {
//            reloadData();
//        }
    }

    public void saveWorkArea() {
    }

    public void updateTreeTable() {
        getTreeTable().refreshTree();
    }

    public void dataChanged(String name, Object value) {
        if (value != null) {
            DtoWbsTreeNode root = (DtoWbsTreeNode) DtoUtil.deepClone(value);
            root.setActivityTree(true);
            this.getTreeTable().setRoot(root);
        }
    }

    protected void fireProcessDataChange() {
        Object root = this.getTreeTable().getTreeTableModel().getRoot();
        ProcessVariant.set("planningtree", root);
        ProcessVariant.fireDataChange("planningtree", this);

        DtoWbsActivity rootDto = (DtoWbsActivity) ((ITreeNode) root).
                                 getDataBean();
        accountLabel.setModel(rootDto);
    }

    public void setTemplateFlag(boolean flag) {
        this.templateFlag = flag;
    }

    public List loadAllRelation() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_ACT_PRE_RLATION_LIST);
        inputInfo.setInputObj("acntRid", inAcntRid);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            List relationList = (List) returnInfo.getReturnObj(
                "successorList");
            return relationList;
        }
        return null;
    }


    public static void main(String args[]) {

        VWWorkArea workArea0 = new VWWorkArea();
        VWWorkArea workArea = new VwBLPlanList();

        workArea0.addTab("Activity", workArea);
        TestPanel.show(workArea0);

        Parameter param = new Parameter();
        param.put("inAcntRid", new Long(1));
        workArea.setParameter(param);

        workArea.refreshWorkArea();
    }
}
