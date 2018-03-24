package client.essp.common.code.choose;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.tree.TreePath;

import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.common.code.DtoCodeValueChoose;
import c2s.essp.common.code.DtoKey;
import client.essp.common.view.VWTreeTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import c2s.dto.DtoTreeNode;
import c2s.dto.IDto;
import client.framework.view.vwcomp.IVWTreeTableMouseClickListener;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import com.wits.util.StringUtil;

public class VwCodeTree extends VWTreeTableWorkArea {
    static final String actionIdList = "F00CodeTreeAction";
    static final String treeColumnName = "name";

    String type = "";
    String catalog = "";
    Long codeRid = null;
    ITreeNode root = null;

    boolean isTypeRefresh = false;
    boolean isCodeRidRefresh = false;
    //boolean onlyShowCodeValue = false;

    JButton btnLoad;
    ICodeChooser chooser = null;

    public VwCodeTree() {
        VWJText text = new VWJText();

        Object[][] configs = { {"Name", "name", VMColumnConfig.EDITABLE, null, Boolean.FALSE}
                                  , {"Description", "description", VMColumnConfig.UNEDITABLE, text, Boolean.FALSE}
                              };

        try {

            super.jbInit(configs, treeColumnName, DtoCodeValueChoose.class, new CodeNodeViewManager());
            this.setPreferredSize(new Dimension(400,500));

            this.getTreeTable().getColumnModel().getColumn(1).setPreferredWidth(200);

            getTreeTable().getTree().setShowsRootHandles(true);
            getTreeTable().getTree().setRootVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
    }

    private void addUICEvent() {
        //Refresh
        btnLoad = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });

        getTreeTable().addMouseLeftClickListener(new IVWTreeTableMouseClickListener(){
            public void onMouseClick(MouseEvent e,ITreeNode node,int positionType){
                onMouseClickTreeTable(e, node);
            }
        });
    }

    public void onMouseClickTreeTable(MouseEvent e, ITreeNode node) {
        if( e.getClickCount() == 2 ){
            DtoCodeValueChoose dto = (DtoCodeValueChoose)node.getDataBean();

            if( dto.isCodeValue() == true && chooser != null ){
                chooser.chooseCode(dto);
            }
        }
    }

    public void actionPerformedLoad(ActionEvent e){
        isTypeRefresh = true;
        resetUI();
    }

    public void actionPerformedExpand(ActionEvent e) {
        this.getTreeTable().expandsRow();
    }

    public void setChooser(ICodeChooser chooser){
        this.chooser = chooser;
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);

        String t = StringUtil.nvl(param.get(DtoKey.TYPE));
        if(  t.equals(this.type) ){
            this.isTypeRefresh = false;
        }else{
            this.isTypeRefresh = true;
        }
        type = t;

        String cata = StringUtil.nvl(param.get(DtoKey.CATALOG));
        if( isTypeRefresh == false
            && cata.equals(this.catalog) ){
            this.isTypeRefresh = false;
        }else{
            this.isTypeRefresh = true;
        }
        catalog = cata;


        codeRid = (Long)param.get(DtoKey.CODE_RID);
    }

    //页面刷新－－－－－
    protected void resetUI() {
        if( type.equals("") ){
            root = null;
            getTreeTable().setRoot(root);
            return;
        }

        if( isTypeRefresh == true ){
            InputInfo inputInfo = new InputInfo();

            inputInfo.setActionId(this.actionIdList);
            inputInfo.setInputObj(DtoKey.TYPE, this.type);
            inputInfo.setInputObj(DtoKey.CATALOG, this.catalog);

            ReturnInfo returnInfo = accessData(inputInfo);

            if (returnInfo.isError() == false) {
                root = (ITreeNode) returnInfo.getReturnObj(DtoKey.ROOT);
            }
        }

        if (this.codeRid != null) {
            ITreeNode subRoot = findSubRoot();
            this.setRoot(subRoot);
        } else {
            this.setRoot(root);
        }
    }

    private ITreeNode findSubRoot(){
        if( root == null || codeRid == null ){
            return null;
        }

        //在root的孙子中找codeRid为this.codeRid的node
        for (int i = 0; i < root.getChildCount(); i++) {
            ITreeNode son = root.getChildAt(i);

            for (int j = 0; j < son.getChildCount(); j++) {
                ITreeNode grandSon = son.getChildAt(j);
                DtoCodeValueChoose dto = (DtoCodeValueChoose)grandSon.getDataBean();

                if( codeRid.equals( dto.getCodeRid() ) ){

                    ITreeNode subRoot = new DtoTreeNode( new DtoCodeValueChoose() );
                    subRoot.addChild(grandSon, IDto.OP_NOCHANGE);
                    return subRoot;
                }
            }
        }

        return null;
    }

    private void setRoot(ITreeNode newRoot) {
        this.getTreeTable().setRoot(newRoot);
        if (newRoot != null) {
            this.getTreeTable().expandPath(new TreePath(newRoot));
            if (newRoot.getChildCount() > 0) {
                this.getTreeTable().setSelectedRow(newRoot.getChildAt(0));
            }
        }
    }

    public static void main(String args[]) {
        VWWorkArea workArea0 = new VWWorkArea();
        VWWorkArea workArea = new VwCodeTree();

        workArea0.addTab("Code", workArea);
        TestPanel.show(workArea0);

        Parameter param = new Parameter();
        param.put(DtoKey.TYPE, "activity");
        param.put(DtoKey.CODE_RID, new Long(113));
        workArea.setParameter(param);

        workArea.refreshWorkArea();
    }


}
