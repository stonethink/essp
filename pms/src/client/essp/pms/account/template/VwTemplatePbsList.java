package client.essp.pms.account.template;

import client.essp.common.view.VWTreeTableWorkArea;
import client.essp.pms.pbs.PmsPbsNodeViewManager;
import client.framework.view.vwcomp.VWJText;
import c2s.essp.pms.pbs.DtoPmsPbs;
import client.framework.model.VMColumnConfig;
import c2s.dto.ITreeNode;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import javax.swing.JButton;
import client.framework.view.event.RowSelectedListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import client.framework.view.vwcomp.IVWWizardEditorEvent;
import com.wits.util.Parameter;
import c2s.dto.DtoTreeNode;
import client.framework.common.Constant;
import client.framework.view.common.comMSG;
import c2s.essp.pms.wbs.DtoWbsActivity;
import javax.swing.tree.TreePath;
import c2s.dto.IDto;
import java.util.List;
import c2s.essp.pms.account.DtoPmsAcnt;
import c2s.dto.DtoUtil;
import javax.swing.table.TableModel;
import client.framework.model.VMTreeTableModelAdapter;


public class VwTemplatePbsList extends VWTreeTableWorkArea {
    static final String actionIdList = "FAccountPbsListAction";
    static final String treeColumnName = "productName";
    boolean isPm = false;
    private Long acntrid;
    private Long rootrid;
    JButton btnAdd;
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
    private ITreeNode copyNode;
    private TreePath copyNodePath;
    private boolean cutFlag = false;
    private boolean copyFlag = false;
    private int pasteTimes = 1;


    public VwTemplatePbsList() {

        final Object[][] configs = new Object[][] { {"Product Name",
                                   "productName", VMColumnConfig.EDITABLE, null},
                                   {"Product Code", "productCode",
                                   VMColumnConfig.UNEDITABLE,
                                   new VWJText()}, {"Reference",
                                   "pbsReferrence", //the tree column's render component must be null.
                                   VMColumnConfig.UNEDITABLE,
                                   new VWJText()}, {"Description", "pbsBrief",
                                   VMColumnConfig.UNEDITABLE, new VWJText()}};

        try {
            super.jbInit(configs, treeColumnName,
                         DtoPmsPbs.class, new PmsPbsNodeViewManager());
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addUICEvent() {
        //捕获事件－－－－
        this.getTreeTable().addRowSelectedListener(new RowSelectedListener() {
            public void processRowSelected() {
               //setButtonVisible();
            }
        });

        //Add pbs
//        btnAdd = this.getButtonPanel().addAddButton(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                actionPerformedAdd(e);
//            }
//        });
//        btnAdd.setToolTipText("add pbs");
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
//        //Delete
//        btnDel = this.getButtonPanel().addDelButton(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                actionPerformedDelete(e);
//            }
//        });
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

    public void actionPerformedAdd(ActionEvent e) {

        ITreeNode currentNode = this.getSelectedNode();
        pasteTimes++;
        if (currentNode != null) {
            DtoPmsPbs currentDataBean = (DtoPmsPbs) currentNode.
                                        getDataBean();
            DtoPmsPbs dtoPmsPbs = new DtoPmsPbs();
            int temp = currentDataBean.getPbsRid().intValue() * pasteTimes * 13 + 37;
            dtoPmsPbs.setPbsRid(new Long(Integer.toString(temp)));
            dtoPmsPbs.setPbsParentRid(currentDataBean.getPbsRid());
            dtoPmsPbs.setReadonly(false);

            ITreeNode rootpbs =null;
            TableModel tablepbs = this.getTreeTable().getModel();
            VMTreeTableModelAdapter modelpbs = null;
            if (tablepbs instanceof VMTreeTableModelAdapter){
                modelpbs = (VMTreeTableModelAdapter) tablepbs;
            }
            if (modelpbs != null) {
                Object object = modelpbs.getTreeTableModel().getRoot();
                if (object instanceof ITreeNode) {
                    rootpbs = (ITreeNode) object;
                }
            }
            List list = rootpbs.listAllChildrenByPreOrder();
            int rid = list.size()+2;

            String code = "";
            java.text.DecimalFormat df = new java.text.
                                          DecimalFormat("P0000");
            code = df.format(rid);
            dtoPmsPbs.setProductName(code);
            dtoPmsPbs.setProductCode(code);
            ITreeNode newNode = new DtoTreeNode(dtoPmsPbs);
            newNode = this.getTreeTable().addRow(newNode);
        } else {
            if (this.getTreeTable().getRowCount() == 0) {
                //add a root
                DtoPmsPbs dtoPmsPbs = new DtoPmsPbs();
                dtoPmsPbs.setPbsRid(new Long(1));
                dtoPmsPbs.setPbsParentRid(null);
                dtoPmsPbs.setReadonly(false);
                dtoPmsPbs.setProductName("P0001");
                dtoPmsPbs.setProductCode("P0001");
                ITreeNode newNode = new DtoTreeNode(dtoPmsPbs);
                newNode = this.getTreeTable().addRow(newNode);
            }

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
            pasteBtn.setEnabled(false);
        }

    }

    public void actionPerformedCopy(ActionEvent e){
        copyNode = this.getSelectedNode();
        pasteTimes = 1;
        if(copyNode !=null){
            copyFlag = true;
            copyNodePath = this.getTreeTable().getSelectionPath();
            pasteBtn.setEnabled(false);
        }

    }

    public void actionPerforemdPaste(ActionEvent e){
        //执行粘贴操作
        ITreeNode selectedNode = this.getSelectedNode();
        if (cutFlag) {
            //清空标志位
            this.getTreeTable().deleteRow(copyNode);
            cutFlag = false;
        }

        if (copyNode != null && selectedNode != null) {
            ITreeNode treeNode = setNodeInsertProp(copyNode, selectedNode);
            this.getTreeTable().addRow(selectedNode, treeNode);
        }
        pasteTimes++;
    }

    public void actionPerformedDelete(ActionEvent e) {

        ITreeNode deleteNode = this.getSelectedNode();
        int option = comMSG.dispConfirmDialog("Do you delete the data?");
        if (option == Constant.OK) {
            this.getTreeTable().deleteRow();
        }

    }

    public void actionPerformedDown(ActionEvent e) {

        ITreeNode node = this.getSelectedNode();
        if( node == null ){
            return;
        }
        this.getTreeTable().downMove();

    }

    public void actionPerformedUp(ActionEvent e) {

        ITreeNode node = this.getSelectedNode();
        if( node == null ){
            return;
        }
        this.getTreeTable().upMove();

    }

    public void actionPerformedRight(ActionEvent e) {

        if (this.getTreeTable().isRightMovable() == false) {
            return;
        }
        this.getTreeTable().rightMove();
    }

    public void actionPerformedExpand(ActionEvent e) {
         this.getTreeTable().expandsRow();
    }

    public void actionPerformedLoad(ActionEvent e) {
         resetUI();
    }

    public void actionPerformedLeft(ActionEvent e) {

        if (this.getTreeTable().isLeftMovable() == false) {
            return;
        }
        this.getTreeTable().leftMove();

    }

    public void refresh() {
        resetUI();
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.param = param;

    }

    protected void resetUI() {

        if (param != null) {
            this.acntrid = (Long) param.get("acntrid");
        }
        if (this.acntrid !=null) {

            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.actionIdList);
            inputInfo.setInputObj("acntrid", this.acntrid);
            ReturnInfo returnInfo = accessData(inputInfo);

            if (returnInfo.isError() == false) {
                ITreeNode root = (ITreeNode) returnInfo.getReturnObj("root");
                if(root !=null){
                    DtoPmsPbs dto = (DtoPmsPbs) root.getDataBean();
                    this.rootrid = (Long) dto.getPbsRid();
                    setReadonly(root);
                    this.getTreeTable().setRoot(root);
                }else{
                    this.getTreeTable().setRoot(null);
                }
            }
        } else {
            this.getTreeTable().setRoot(null);
        }
    }
    //设置setReadonly为false,以方便节点数据可以修改
    public void setReadonly(ITreeNode root){
        DtoPmsPbs dpp = (DtoPmsPbs)root.getDataBean();
        dpp.setReadonly(false);
        root.setDataBean(dpp);
        if(root.getChildCount()>0){
            for(int i=0;i<root.getChildCount();i++){
                setReadonly(root.getChildAt(i));
            }
        }
    }

    private ITreeNode setNodeInsertProp(ITreeNode node, ITreeNode parentNode) {
        DtoPmsPbs dataBean = (DtoPmsPbs) node.getDataBean();
        DtoPmsPbs dotwbs = new DtoPmsPbs();
        ITreeNode treeNode = null;
        try{
            DtoUtil.copyProperties(dotwbs,dataBean);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        dotwbs.setOp(IDto.OP_INSERT);
        int temp = dataBean.getPbsRid().intValue() * pasteTimes * 13 + 37;
        dotwbs.setPbsRid(new Long(Integer.toString(temp)));
        dotwbs.setPbsRid(((DtoPmsPbs) parentNode.getDataBean()).getPbsRid());
        treeNode = new DtoTreeNode(dotwbs);
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

//    public void setButtonVisible(){
//        ITreeNode node = this.getSelectedNode();
//        cutBtn.setEnabled(true);
//        copyBtn.setEnabled(true);
//        pasteBtn.setEnabled(true);
//        btnDel.setEnabled(true);
//        btnDown.setEnabled(true);
//        btnUp.setEnabled(true);
//        btnLeft.setEnabled(true);
//        btnRight.setEnabled(true);
//
//        if(node!=null){
//            DtoPmsPbs dpp = (DtoPmsPbs)node.getDataBean();
//            if(dpp.getPbsParentRid()==null){
//                btnDel.setEnabled(false);
//                btnDown.setEnabled(false);
//                btnUp.setEnabled(false);
//                btnLeft.setEnabled(false);
//                btnRight.setEnabled(false);
//                cutBtn.setEnabled(false);
//            }else{
//                if(dpp.getPbsParentRid().equals(this.rootrid)){
//                    btnLeft.setEnabled(false);
//                }
//                DtoPmsPbs firstchild = (DtoPmsPbs)node.getParent().getChildAt(0).getDataBean();
//                if(firstchild.equals(dpp)){
//                    btnUp.setEnabled(false);
//                    btnRight.setEnabled(false);
//                }
//                int last = node.getParent().getChildCount();
//                DtoPmsPbs lastchild =(DtoPmsPbs)node.getParent().getChildAt(last-1).getDataBean();
//                if(lastchild.equals(dpp)){
//                    btnDown.setEnabled(false);
//                }
//             }
//             if(cutFlag == true){
//
//                 TreePath selectedPath = this.getTreeTable().
//                                            getSelectionPath();
//                 if (copyNodePath.isDescendant(selectedPath)) {
//                     pasteBtn.setEnabled(false);
//                 } else {
//                     pasteBtn.setEnabled(true);
//                 }
//
//            }
//
//            if(copyFlag == true){
//
//                TreePath selectedPath = this.getTreeTable().
//                                            getSelectionPath();
//                 if (copyNodePath.isDescendant(selectedPath)) {
//                     pasteBtn.setEnabled(false);
//                 } else {
//                     pasteBtn.setEnabled(true);
//                 }
//
//            }
//
//        }
//    }

    protected void jbInit() throws Exception {
    }

}
