package client.essp.pms.account.template;

import client.essp.common.view.VWTreeTableWorkArea;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.framework.view.vwcomp.VWJText;
import client.essp.pms.wbs.WbsNodeViewManager;
import client.framework.model.VMColumnConfig;
import javax.swing.JButton;
import c2s.dto.ITreeNode;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import client.framework.view.event.RowSelectedListener;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.wbs.DtoWbsTreeNode;
import c2s.dto.InputInfo;
import c2s.essp.pms.wbs.DtoKEY;
import com.wits.util.Parameter;
import c2s.dto.IDto;
import client.framework.common.Constant;
import client.framework.view.common.comMSG;
import javax.swing.tree.TreePath;
import c2s.dto.DtoUtil;
import javax.swing.table.TableModel;
import client.framework.model.VMTreeTableModelAdapter;
import client.essp.common.view.VWAccountLabel;
import client.framework.view.vwcomp.VWJDate;
import javax.swing.table.TableColumnModel;
import javax.swing.table.JTableHeader;


public class VwTemplateWbsList extends VWTreeTableWorkArea {

    private static final String ACTIONID_GETCODE = "FWbsActivityGetCodeAction";
    private static final String actionIdList = "FAccountWbsListAction";
    static final String treeColumnName = "name";
    private Long acntrid;
    private Long rootrid;
    JButton addWbsBtn;
    JButton addActivityBtn;
    JButton cutBtn;
    JButton copyBtn;
    JButton pasteBtn;
    JButton btnDel;
    JButton btnDown;
    JButton btnUp;
    JButton btnLeft;
    JButton btnRight;
    JButton btnExpand;
    private Parameter param;
    private ITreeNode copyNode = null;
    private TreePath copyNodePath;
    private boolean cutFlag = false;
    private boolean copyFlag = false;
    private boolean copyOnlyActivity = false;
    private int pasteTimes = 1;
    private VWAccountLabel accountLabel = null;

    public VwTemplateWbsList(){

     try {

         final Object[][] configs = new Object[][] { {"Name", "name",
                      VMColumnConfig.EDITABLE, null}, {"Code", "code",
                      VMColumnConfig.UNEDITABLE, new VWJText()},
                      {"Planned Start", "plannedStart",
                     VMColumnConfig.UNEDITABLE, new VWJDate()},
                     {"Planned Finish", "plannedFinish",
                     VMColumnConfig.UNEDITABLE, new VWJDate()},
                      {"Weight","weight", VMColumnConfig.UNEDITABLE, new VWJText()},
                      {"Complete Method", "completeMethod",
                      VMColumnConfig.UNEDITABLE, new VWJText()},
                      {"Decsription", "brief", VMColumnConfig.UNEDITABLE,
                      new VWJText()}};

        super.jbInit(configs, treeColumnName,
                         DtoWbsActivity.class, new WbsNodeViewManager());
         //调整列的宽度
         JTableHeader header = this.getTreeTable().getTableHeader();
         TableColumnModel tcModel = header.getColumnModel();
         tcModel.getColumn(0).setPreferredWidth(140);
         tcModel.getColumn(1).setPreferredWidth(45);
         tcModel.getColumn(2).setPreferredWidth(95);
         tcModel.getColumn(3).setPreferredWidth(95);
         tcModel.getColumn(4).setPreferredWidth(45);
         tcModel.getColumn(5).setPreferredWidth(100);
         tcModel.getColumn(6).setPreferredWidth(75);

     }catch(Exception e){
         e.printStackTrace();
     }


       addUIEvent();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addUIEvent(){

        //捕获事件－－－－
        this.getTreeTable().addRowSelectedListener(new RowSelectedListener() {
            public void processRowSelected() {
               //setButtonVisible();
            }
        });

//        //Add wbs
//        addWbsBtn =this.getButtonPanel().addButton("wbs.png");
//        addWbsBtn.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                actionPerformedAddWbs(e);
//            }
//        });
//        addWbsBtn.setToolTipText("add wbs");
//
//        //Add activity
//        addActivityBtn =this.getButtonPanel().addButton("activity.png");
//        addActivityBtn.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                actionPerformedAddActivity(e);
//            }
//        });
//        addActivityBtn.setToolTipText("add activity");
//
//        cutBtn = this.getButtonPanel().addButton("cut.png");
//        cutBtn.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//               actionPerformedCut(e);
//            }
//        });
//        cutBtn.setToolTipText("cut");
//
//        //copy
//        copyBtn = this.getButtonPanel().addButton("copy.png");
//        copyBtn.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//               actionPerformedCopy(e);
//            }
//        });
//        copyBtn.setToolTipText("copy");
//
//        //paste
//        pasteBtn = this.getButtonPanel().addButton("paste.png");
//        pasteBtn.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//               actionPerforemdPaste(e);
//            }
//        });
//        pasteBtn.setToolTipText("paste");
//
//
//        //Delete
//        btnDel = this.getButtonPanel().addDelButton(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                actionPerformedDelete(e);
//            }
//        });
//
//
//        //Down
//        btnDown = this.getButtonPanel().addButton("down.png");
//        btnDown.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                actionPerformedDown(e);
//            }
//        });
//        btnDown.setToolTipText("move down");
//
//        //Up
//        btnUp = this.getButtonPanel().addButton("up.png");
//        btnUp.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                actionPerformedUp(e);
//            }
//        });
//        btnUp.setToolTipText("move up");
//
//        //Left
//        btnLeft = this.getButtonPanel().addButton("left.png");
//        btnLeft.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                actionPerformedLeft(e);
//            }
//        });
//        btnLeft.setToolTipText("move left");
//
//        //Right
//        btnRight = this.getButtonPanel().addButton("right.png");
//        btnRight.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                actionPerformedRight(e);
//            }
//        });
//        btnRight.setToolTipText("move right");

        //expand
        btnExpand = this.getButtonPanel().addButton("expand.png");
        btnExpand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedExpand(e);
            }
        });
        btnExpand.setToolTipText("expand the tree");



    }

    public void actionPerformedAddWbs(ActionEvent e){
         pasteTimes++;
         ITreeNode currentNode = this.getSelectedNode();
         if (currentNode != null) {
             DtoWbsActivity currentDataBean = (DtoWbsActivity) currentNode.
                                             getDataBean();
             DtoWbsActivity dataBean = new DtoWbsActivity();
             int temp = currentDataBean.getWbsRid().intValue() * pasteTimes * 13 + 37;
             dataBean.setWbsRid(new Long(Integer.toString(temp)));
             dataBean.setAcntRid(currentDataBean.getAcntRid());
             dataBean.setParentRid(currentDataBean.getWbsRid());
             dataBean.setWbs(true);

             ITreeNode rootwbs = null;
             TableModel tablewbs = this.getTreeTable().getModel();
             VMTreeTableModelAdapter modelwbs = null;
             if (tablewbs instanceof VMTreeTableModelAdapter) {
                 modelwbs = (VMTreeTableModelAdapter) tablewbs;
             }
             if (modelwbs != null) {
                 Object object = modelwbs.getTreeTableModel().getRoot();
                 if (object instanceof ITreeNode) {
                     rootwbs = (ITreeNode) object;
                 }
             }

             List list = rootwbs.listAllChildrenByPreOrder();
             int rid = list.size()+2;
             String code = "";
             java.text.DecimalFormat df = new java.text.
                                          DecimalFormat("W0000");
             code = df.format(rid);
             dataBean.setName(code);
             dataBean.setAutoCode(code);
             dataBean.setCode(code);
             dataBean.setWeight(new Double(1.00));
             dataBean.setCompleteMethod(DtoWbsActivity.
                                        WBS_COMPLETE_BY_ACTIVITY);

             dataBean.setOp(IDto.OP_INSERT);
             DtoWbsTreeNode newNode = new DtoWbsTreeNode(dataBean);
             this.getTreeTable().addRow(newNode);

         }else{
             ITreeNode root = (ITreeNode)this.getTreeTable().getTreeTableModel().
                             getRoot();
             if (root == null) {
                DtoWbsActivity dataBean = new DtoWbsActivity();
                dataBean.setWbsRid(new Long(1));
                dataBean.setAcntRid(this.acntrid);
                dataBean.setParentRid(null);
                dataBean.setWbs(true);
                dataBean.setName("W00001");
                dataBean.setAutoCode("W00001");
                dataBean.setCode("W00001");
                dataBean.setWeight(new Double(1.00));
                dataBean.setCompleteMethod(DtoWbsActivity.
                                            WBS_COMPLETE_BY_ACTIVITY);

                DtoWbsTreeNode newNode = new DtoWbsTreeNode(dataBean);
                this.getTreeTable().setRoot(newNode);
             }
         }
    }

    public void actionPerformedAddActivity(ActionEvent e){

        ITreeNode currentNode = this.getSelectedNode();
        if (currentNode != null) {
                DtoWbsActivity currentDataBean = (DtoWbsActivity) currentNode.
                                             getDataBean();
                DtoWbsActivity dataBean = new DtoWbsActivity();
                dataBean.setAcntRid(currentDataBean.getAcntRid());
                dataBean.setWbsRid(currentDataBean.getWbsRid());
                dataBean.setActivity(true);

                InputInfo inputInfo = new InputInfo();
                inputInfo.setInputObj(DtoKEY.DTO, dataBean);
                inputInfo.setInputObj("Acntrid",this.acntrid);
                inputInfo.setActionId(this.ACTIONID_GETCODE);

                ReturnInfo returnInfo = accessData(inputInfo);
                dataBean = (DtoWbsActivity) returnInfo.getReturnObj(DtoKEY.DTO);
                dataBean.setAutoCode(dataBean.getCode());
                dataBean.setName(dataBean.getCode());
                dataBean.setCode(dataBean.getCode());
                dataBean.setWeight(new Double(1.0));
                dataBean.setCompleteMethod(DtoWbsActivity.
                                               WBS_COMPLETE_BY_ACTIVITY);
                dataBean.setOp(IDto.OP_INSERT);
                DtoWbsTreeNode newNode = new DtoWbsTreeNode(dataBean);
                this.getTreeTable().addRow(currentNode, newNode);
        }
    }

    public void actionPerformedCut(ActionEvent e){
        //执行剪切动作
        //弄一个弹出窗口
        //popWindowForNextVesion();
        copyNode = this.getSelectedNode();
        pasteTimes = 1;
        if (copyNode != null) {
            cutFlag = true;
            copyNodePath = this.getTreeTable().getSelectionPath();
            if (((DtoWbsActivity) copyNode.getDataBean()).isActivity()) {
                copyOnlyActivity = true;
            } else {
                copyOnlyActivity = false;
            }
            pasteBtn.setEnabled(false);
        }

    }

    public void actionPerformedCopy(ActionEvent e){
        copyNode = this.getSelectedNode();
        pasteTimes = 1;
        if(copyNode !=null){
            copyFlag = true;
            copyNodePath = this.getTreeTable().getSelectionPath();
            if (((DtoWbsActivity) copyNode.getDataBean()).isActivity()) {
                copyOnlyActivity = true;
            } else {
                copyOnlyActivity = false;
            }
            pasteBtn.setEnabled(false);
        }
    }

    public void actionPerforemdPaste(ActionEvent e){
        //执行粘贴操作
        ITreeNode pasteNode = this.getSelectedNode();
        if (cutFlag) {
            //清空标志位
            this.getTreeTable().deleteRow(copyNode);
            cutFlag = false;
        }

        if (copyNode != null && pasteNode != null) {
            ITreeNode treenode = setNodeInsertProp(copyNode, pasteNode);
            this.getTreeTable().addRow(pasteNode, treenode);
        }
        pasteTimes++;
    }

    public void actionPerformedDelete(ActionEvent e){

        ITreeNode deleteNode = this.getSelectedNode();
        int option = comMSG.dispConfirmDialog("Do you delete the data?");
        if (option == Constant.OK) {
            getTreeTable().deleteRow();
        }
    }

    public void actionPerformedDown(ActionEvent e){

        ITreeNode node = this.getSelectedNode();
        if( node == null ){
            return;
        }
        this.getTreeTable().downMove();

    }

    public void actionPerformedUp(ActionEvent e){

        ITreeNode node = this.getSelectedNode();
        if( node == null ){
            return;
        }
        this.getTreeTable().upMove();

    }

    public void actionPerformedLeft(ActionEvent e){

        if (this.getTreeTable().isLeftMovable() == false) {
            return;
        }
        this.getTreeTable().leftMove();

    }

    public void actionPerformedRight(ActionEvent e){

        if (this.getTreeTable().isRightMovable() == false) {
            return;
        }
        this.getTreeTable().rightMove();

    }

    public void actionPerformedExpand(ActionEvent e){
        this.getTreeTable().expandsRow();
    }

    public void actionPerformedLoad(ActionEvent e){
        resetUI();
    }

    public void refresh(){
         resetUI();
    }

    private ITreeNode setNodeInsertProp(ITreeNode node, ITreeNode parentNode) {
        DtoWbsActivity dataBean = (DtoWbsActivity) node.getDataBean();
        DtoWbsActivity dotwbs = new DtoWbsActivity();
        ITreeNode treeNode = null;
        try{
            DtoUtil.copyProperties(dotwbs,dataBean);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        if (copyOnlyActivity) {
            dotwbs.setOp(IDto.OP_INSERT);
            Long parentRid = ((DtoWbsActivity) parentNode.getDataBean()).
                             getWbsRid();
            dotwbs.setWbsRid(parentRid);
            treeNode = new DtoWbsTreeNode(dotwbs);
            if(!node.equals(copyNode)){
                parentNode.addChild(treeNode);
            }
            return treeNode;
        }
        dotwbs.setOp(IDto.OP_INSERT);
        int temp = dataBean.getWbsRid().intValue() * pasteTimes * 13 + 37;
        dotwbs.setWbsRid(new Long(Integer.toString(temp)));
        dotwbs.setParentRid(((DtoWbsActivity) parentNode.getDataBean()).
                              getWbsRid());
        treeNode = new DtoWbsTreeNode(dotwbs);
        if (!node.isLeaf()) {
            List list = node.children();
            for (int i = 0; i < list.size(); i++) {
                setNodeInsertProp((ITreeNode) list.get(i), treeNode);
            }
        }
        if(!node.equals(copyNode)){
            parentNode.addChild(treeNode);
        }
        return treeNode;
    }


    public void setParameter(Parameter param) {
         super.setParameter(param);
         this.param=param;

    }

    protected void resetUI() {

        if(param !=null){
             this.acntrid = (Long)param.get("acntrid");
        }

        if(this.acntrid !=null){
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.actionIdList);

            inputInfo.setInputObj("acntrid", this.acntrid);
            ReturnInfo returnInfo = accessData(inputInfo);

            if (returnInfo.isError() == false) {
                DtoWbsTreeNode root = (DtoWbsTreeNode) returnInfo.
                                      getReturnObj(
                                          DtoKEY.WBSTREE);
                if(root !=null){
                    DtoWbsActivity dtowbs = (DtoWbsActivity) root.getDataBean();
                    this.rootrid = dtowbs.getWbsRid();
                    root.setActivityTree(true);
                    setReadonly(root);
                    this.getTreeTable().setRoot(root);
                }else{
                    this.getTreeTable().setRoot(null);
                }
            }
        }else{
            this.getTreeTable().setRoot(null);
        }
    }
    //设置setReadonly为false,以方便节点数据可以修改
    public void setReadonly(ITreeNode root){
        DtoWbsActivity dtowbs = (DtoWbsActivity)root.getDataBean();
        dtowbs.setReadonly(false);
        if(root.getChildCount()>0){
            for(int i=0;i<root.getChildCount();i++){
                setReadonly(root.getChildAt(i));
            }
        }
    }

    protected void jbInit() throws Exception {
    }

    //    public void setButtonVisible(){
//        ITreeNode node = this.getSelectedNode();
//        addWbsBtn.setEnabled(true);
//        addActivityBtn.setEnabled(true);
//        cutBtn.setEnabled(true);
//        copyBtn.setEnabled(true);
//        pasteBtn.setEnabled(true);
//        btnDel.setEnabled(true);
//        btnDown.setEnabled(true);
//        btnUp.setEnabled(true);
//        btnLeft.setEnabled(true);
//        btnRight.setEnabled(true);
//        if(node!=null){
//            DtoWbsActivity dtowbs = (DtoWbsActivity)node.getDataBean();
//            if(dtowbs.getParentRid()==null){
//                if(!dtowbs.isActivity()){
//                    btnDel.setEnabled(false);
//                    btnDown.setEnabled(false);
//                    btnUp.setEnabled(false);
//                    btnLeft.setEnabled(false);
//                    btnRight.setEnabled(false);
//                    cutBtn.setEnabled(false);
//                }else{
//                    addWbsBtn.setEnabled(false);
//                    addActivityBtn.setEnabled(false);
//                    btnRight.setEnabled(false);
//                    if(dtowbs.getWbsRid().equals(this.rootrid)){
//                        btnLeft.setEnabled(false);
//                    }
//                    DtoWbsActivity firstchild = (DtoWbsActivity) node.getParent().
//                                                getChildAt(0).getDataBean();
//                    if (dtowbs.equals(firstchild)) {
//                        btnUp.setEnabled(false);
//                    }
//                    int child = node.getParent().getChildCount();
//                    DtoWbsActivity lastchild = (DtoWbsActivity) node.getParent().
//                                               getChildAt(child - 1).
//                                               getDataBean();
//                    if (dtowbs.equals(lastchild)){
//                        btnDown.setEnabled(false);
//                    }
//
//                }
//            }else{
//                if(dtowbs.getParentRid().equals(this.rootrid)){
//                    btnLeft.setEnabled(false);
//                }
//                DtoWbsActivity firstchild = (DtoWbsActivity)node.getParent().getChildAt(0).getDataBean();
//                if(dtowbs.equals(firstchild)){
//                    btnUp.setEnabled(false);
//                    btnRight.setEnabled(false);
//                }
//                int child = node.getParent().getChildCount();
//                DtoWbsActivity lastchild = (DtoWbsActivity)node.getParent().getChildAt(child-1).getDataBean();
//                if(dtowbs.equals(lastchild)){
//                    btnDown.setEnabled(false);
//                }
//            }
//            if(cutFlag == true){
//                 DtoWbsActivity dtoWbs = (DtoWbsActivity)this.getSelectedNode().getDataBean();
//                 TreePath selectedPath = this.getTreeTable().
//                                            getSelectionPath();
//                 if (copyNodePath.isDescendant(selectedPath)) {
//                     pasteBtn.setEnabled(false);
//                 } else {
//                     pasteBtn.setEnabled(true);
//                 }
//
//                 if(dtoWbs.isActivity()){
//                     pasteBtn.setEnabled(false);
//                 }
//
//            }
//
//            if(copyFlag == true){
//                DtoWbsActivity dtoWbs = (DtoWbsActivity)this.getSelectedNode().getDataBean();
//                TreePath selectedPath = this.getTreeTable().
//                                            getSelectionPath();
//                if (copyNodePath.isDescendant(selectedPath)) {
//                     pasteBtn.setEnabled(false);
//                } else {
//                     pasteBtn.setEnabled(true);
//                }
//
//                if(dtoWbs.isActivity()){
//                    pasteBtn.setEnabled(false);
//                }
//            }
//        }
//    }

}
