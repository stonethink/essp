package client.essp.cbs.config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import c2s.dto.DtoTreeNode;
import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.cbs.DtoCbs;
import c2s.essp.cbs.DtoSubject;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.view.VWWorkArea;
import client.framework.view.common.comMSG;
import com.wits.util.StringUtil;
import com.wits.util.TestPanel;
import com.wits.util.Parameter;

public class VwCbsList extends VwCbsListBase {
    static final String actionIdList = "FCbsListAction";
    static final String actionIdSave = "FCbsSaveAction";
    DtoCbs cbs ;
    private boolean isSaveOk = true;
    boolean isModified = false;
    VwCbsGeneral vwCbsGeneral;
    public VwCbsList(){
        super();
        try {
            addUICEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void  addUICEvent(){
        this.getButtonPanel().addAddButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd();
            }
        });

        this.getButtonPanel().addDelButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedDel();
            }
        });
        this.getButtonPanel().addSaveButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave();
            }
        });
        //Down
        btnDown = this.getButtonPanel().addButton("down.png");
        btnDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDown();
            }
        });
        btnDown.setToolTipText("move down");
        //Up
        btnUp = this.getButtonPanel().addButton("up.png");
        btnUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedUp();
            }
        });
        btnUp.setToolTipText("move up");
        //Left
        btnLeft = this.getButtonPanel().addButton("left.png");
        btnLeft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLeft();
            }
        });
        btnLeft.setToolTipText("move left");
        //Right
        btnRight = this.getButtonPanel().addButton("right.png");
        btnRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedRight();
            }
        });
        btnRight.setToolTipText("move right");
        //expand
        btnExpand = this.getButtonPanel().addButton("expand.png");
        btnExpand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedExpand();
            }
        });
        btnExpand.setToolTipText("expand the tree");
        //Display
        TableColumnChooseDisplay chooseDisplay =
            new TableColumnChooseDisplay(this.getTreeTable(), this);
        btnDisplay = chooseDisplay.getDisplayButton();
        this.getButtonPanel().addButton(btnDisplay);

        this.getButtonPanel().addLoadButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                resetUI();
            }
        });


    }
    //新增事件
    private void actionPerformedAdd(){
        ITreeNode currentNode = this.getSelectedNode();
         if (currentNode != null) {
             DtoSubject currentSubject = (DtoSubject) currentNode.getDataBean();
             if("".equals(StringUtil.nvl(currentSubject.getSubjectCode())) ||
                "".equals(StringUtil.nvl(currentSubject.getSubjectName()))){
                 comMSG.dispErrorDialog("Please fill the subject code and name first!");
                 return;
             }
             DtoSubject newSubject = new DtoSubject();
             newSubject.setSubjectAttribute(null);
             ITreeNode newNode = new DtoTreeNode(newSubject);
             newNode = this.getTreeTable().addRow(newNode);
         }else{
             if( this.getTreeTable().getRowCount() == 0 ){
                 DtoSubject rootSubject = new DtoSubject(DtoCbs.DEFAULT_ROOT,
                     DtoCbs.DEFAULT_ROOT);
                 rootSubject.setBudgetCalType(DtoSubject.TYPE_AUTO_CALCULATE);
                 rootSubject.setCostCalType(DtoSubject.TYPE_AUTO_CALCULATE);
                 rootSubject.setSubjectAttribute(null);
                 ITreeNode rootNode = new DtoTreeNode(rootSubject);
                 this.getTreeTable().addRow(rootNode);
             }
         }
         isModified = true;
    }
    //删除事件
    private void actionPerformedDel(){
        ITreeNode currentNode = this.getSelectedNode();
        if (currentNode != null) {
                ITreeNode parentNode = currentNode.getParent();
                if (parentNode == null) {
                    comMSG.dispErrorDialog(
                        "The root subject cannot be deleted!");
                    return;
                }
                this.getTreeTable().deleteRow();
                List children = currentNode.children();
                children.clear();
                parentNode.children().remove(currentNode);
        }
        isModified = true;
    }

    //保存
    private void actionPerformedSave(){
        if(vwCbsGeneral != null)
            vwCbsGeneral.saveWorkArea();
        if(checkModifed() && vwCbsGeneral != null && vwCbsGeneral.isSaveOk())
            saveData();
    }
    //下移
    private void actionPerformedDown(){
        ITreeNode currentNode = this.getTreeTable().getSelectedNode();
        if(currentNode == null)
            return;
        DtoSubject dataBean = (DtoSubject) currentNode.getDataBean();
        if(StringUtil.nvl(dataBean.getSubjectCode()).equals("") ||
           StringUtil.nvl(dataBean.getSubjectName()).equals("")){
            comMSG.dispErrorDialog("Please fill name and code first!");
            return;
        }
        this.getTreeTable().downMove();
        isModified = true;
    }

    //上移
    private void actionPerformedUp(){
        ITreeNode currentNode = this.getTreeTable().getSelectedNode();
        if(currentNode == null)
            return;
        DtoSubject dataBean = (DtoSubject) currentNode.getDataBean();
        if(StringUtil.nvl(dataBean.getSubjectCode()).equals("") ||
           StringUtil.nvl(dataBean.getSubjectName()).equals("")){
            comMSG.dispErrorDialog("Please fill name and code first!");
            return;
        }
        this.getTreeTable().upMove();
        isModified = true;
    }
    //右移
    private void actionPerformedRight(){
        ITreeNode currentNode = this.getTreeTable().getSelectedNode();
        if(currentNode == null)
            return;
        DtoSubject dataBean = (DtoSubject) currentNode.getDataBean();
        if(StringUtil.nvl(dataBean.getSubjectCode()).equals("") ||
           StringUtil.nvl(dataBean.getSubjectName()).equals("")){
            comMSG.dispErrorDialog("Please fill name and code first!");
            return;
        }
        isModified = true;
        this.getTreeTable().rightMove();
    }
    //左移
    private void actionPerformedLeft(){
        ITreeNode currentNode = this.getTreeTable().getSelectedNode();
        if(currentNode == null)
            return;
        DtoSubject dataBean = (DtoSubject) currentNode.getDataBean();
        if(StringUtil.nvl(dataBean.getSubjectCode()).equals("") ||
           StringUtil.nvl(dataBean.getSubjectName()).equals("")){
            comMSG.dispErrorDialog("Please fill name and code first!");
            return;
        }
        isModified = true;
        this.getTreeTable().leftMove();
    }
    //展开
    private void actionPerformedExpand(){
        this.getTreeTable().expandsRow();
    }
    public void setParameter(Parameter param){
        super.setParameter(param);
        vwCbsGeneral = (VwCbsGeneral)param.get("vwCbsGeneral");
    }
    public void resetUI() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdList);
        inputInfo.setInputObj("cbsType",DtoCbs.DEFAULT_TYPE);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            cbs = (DtoCbs) returnInfo.getReturnObj("cbs");
            this.getTreeTable().setRoot(cbs.getCbsRoot());
            this.getTreeTable().expandRow(0);
            isModified = false;
        }
    }
    public void saveWorkArea(){
        if(checkModifed()){
            if(confirmSaveWorkArea()){
                isSaveOk = saveData();
            }else{
                isSaveOk = true;
            }
        }else{
            isSaveOk = true;
        }
    }
    public boolean isSaveOk(){
        return this.isSaveOk;
    }
    private boolean saveData(){
        if(cbs == null)
            return true;
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdSave);
        inputInfo.setInputObj("cbs",cbs);
        ReturnInfo returnInfo = accessData(inputInfo);
        isModified = false;
        return !returnInfo.isError();
    }
    //检查树上所有节点是否有修改过
    private boolean checkModifed(){
        return isModified;
    }
    public static void main(String[] args) {
        VWWorkArea w1 = new VWWorkArea();
        VwCbsList resource = new VwCbsList();
        w1.addTab("CBS",resource);
        TestPanel.show(w1);
        resource.resetUI();
    }
}
