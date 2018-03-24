package client.essp.common.security.role;

import client.essp.common.view.VWTreeTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJCheckBox;
import java.util.List;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.common.menu.DtoMenuItem;
import c2s.dto.ITreeNode;
import client.framework.view.vwcomp.VWJTreeTable;
import client.essp.common.TableUitl.TableColumnWidthSetter;
import java.awt.Dimension;
import com.wits.util.Parameter;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import client.framework.model.VMTreeTableModel;
import c2s.essp.common.code.DtoKey;

/**
 * 列出角色对应的所有菜单的UI
 * @author XiaoRong
 */
public class VwMenuList extends VWTreeTableWorkArea{

    static final String actionMenuTree="F00MenuTreeAction";
    static final String actionSaveMenu="F00SaveMenuAction";
    static final String actionIdChoose="";
    static final String treeColumnName = "name";
    public List MenuList;
    private String roleId;
    private String ParentId;
    private JButton btnSave = null;
    private String root_Id;
    JButton btnExpand = null;

    public VwMenuList(){

        VWJCheckBox checkBox = new VWJCheckBox();
        checkBox.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent evt){
                 processRowSelectedList(evt);
             }
        });
        Object[][] configs = new Object[][] {
                                   {"rsid.common.menuName", "name",VMColumnConfig.EDITABLE, null},
                                   {"rsid.common.privilege", "select",VMColumnConfig.EDITABLE, checkBox},
        };

        try {
            this.setPreferredSize(new Dimension(680, 240));

            //tree table
            model = new  VMTreeTableModel(null, configs, treeColumnName);
            model.setDtoType(DtoMenuItem.class);
            treeTable = new VWJTreeTable(model, new MenuNodeViewManager());
            TableColumnWidthSetter.set(treeTable);
            this.add(treeTable.getScrollPane(), null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        addUICEvent();
    }

    private void addUICEvent() {

        btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });
        btnSave.setToolTipText("rsid.common.save");

        //expand
        btnExpand = this.getButtonPanel().addButton("expand.png");
        btnExpand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedExpand(e);
            }
        });
        btnExpand.setToolTipText("rsid.common.expand");

    }
    /**
     * 设置菜单项
     * @param evt
     */
    public void processRowSelectedList(ActionEvent evt) {
        VWJCheckBox checkBox = (VWJCheckBox) evt.getSource();
        ITreeNode Node = this.getTreeTable().getSelectedNode();
        //当选中一个节点，就要相应的勾上它的子节点孙子节点以及除根节点以外的父节点
        if(checkBox.getUICValue()=="true"){
            if(!Node.isLeaf()){
                addChild(Node);//选中它的子节点孙子节点
            }
            DtoMenuItem dataBean = (DtoMenuItem) Node.getDataBean();
            dataBean.setSelect(true);
            MenuList.add(dataBean);//选中它自己

            if(Node.getParent()!=null){
                if (!root_Id.equals(dataBean.getFatherID())) {
                    addParent(Node.getParent()); //选中它自己的除根节点以外的父节点
                }
            }
        }else{//当取消一个节点时，就要相应的取消它的子节点孙子节点
            DtoMenuItem dataBean= (DtoMenuItem)Node.getDataBean();
            //当删除一个节点时，先将它删除掉，然后通过迭代删掉它的子节点以及孙子节点
            for (int i = 0; i < MenuList.size(); i++) {
                DtoMenuItem dtoMI = (DtoMenuItem) MenuList.get(i);
                if ((dataBean.getFunctionID()).equals(dtoMI.getFunctionID())) {
                    dataBean.setSelect(false);
                    MenuList.remove(i);
                    break;
                }
            }
            //通过迭代删掉它的子节点以及孙子节点
            if(!Node.isLeaf()){
               deleteChild(Node);
            }

        }
        this.getTreeTable().refreshTree();

    }
    /**
     * 将选中节点的子节点设置为选中
     * @param Node
     */
    public void addChild(ITreeNode Node){
        DtoMenuItem data =(DtoMenuItem)Node.getDataBean();
       //判断勾选的是否是根节点，如果是，只须勾选自己，否则就要勾选它的子节点孙子节点
                if(!root_Id.equals(data.getFunctionID())){
                    for (int i = 0; i < Node.listAllChildrenByPostOrder().size(); i++) {
                        ITreeNode childNode = (ITreeNode) Node.listAllChildrenByPostOrder().get(i);
                        DtoMenuItem dataBean = (DtoMenuItem) childNode.
                                               getDataBean();
                        //当MenuList的长度为0时，就直接把自己的第i个子节点勾上
                        if(MenuList.size()==0){
                            dataBean.setSelect(true);
                            MenuList.add(dataBean);
                        }else{ //当MenuList的长度不为0时，需要判断自己的第i个子节点是否勾上，
                               //如果勾上了，就不需要勾了，如果没勾上，就需要勾了

                            boolean isExist = false;
                            for (int j = 0; j < MenuList.size(); j++) {
                                DtoMenuItem dto = (DtoMenuItem) MenuList.get(j);
                                if ((dataBean.getFunctionID()).equals(dto.getFunctionID())) {
                                    isExist = true;
                                    break;
                                }
                            }
                            if(!isExist){
                                dataBean.setSelect(true);
                                MenuList.add(dataBean);
                            }
                        }
//                        //判断自己的第i个子节点是否为叶子节点，如果不是，就要将它的孙子节点勾上
//                        if(!childNode.isLeaf()){
//                            addChild(childNode);
//                        }
                    }
                }
    }

    /**
     * 将选中节点的父节点设置为选中
     * @param node
     */
    public void addParent(ITreeNode node) {
        DtoMenuItem dataBean = (DtoMenuItem) node.getDataBean();
        //如果MenuList的长度为0，就直接将自己的除根节点以外的父节点勾上
        if(MenuList.size()==0){
            dataBean.setSelect(true);
            MenuList.add(dataBean);
        }else{
            //如果MenuList的长度不为0，就需要判断自己的除根节点以外的父节点是否已经勾上
            //如果勾上了，就不需要勾了，如果没勾上，就需要勾了
            boolean isExist =false;
            for (int i = 0; i < MenuList.size(); i++) {
                DtoMenuItem dto = (DtoMenuItem) MenuList.get(i);
                if ((dataBean.getFunctionID()).equals(dto.getFunctionID())) {
                    isExist = true;
                    break;
                }
            }
            if(!isExist){
                dataBean.setSelect(true);
                MenuList.add(dataBean);
            }
        }
        //迭代勾选自己的除根节点以外的父节点
        if (node.getParent() != null) {
            if (!root_Id.equals(dataBean.getFatherID())) {
                addParent(node.getParent());
            }
        }
    }

    /**
     * 删除选中节点的子节点以及孙子节点
     * @param node
     */
    public void deleteChild(ITreeNode node) {
        DtoMenuItem dataBean = (DtoMenuItem) node.getDataBean();
        if (!root_Id.equals(dataBean.getFunctionID())) {
            if (node.getChildCount() > 0) {
                for (int i = 0; i < node.listAllChildrenByPostOrder().size(); i++) {
                    ITreeNode childNode = (ITreeNode) node.listAllChildrenByPostOrder().get(i);
                    DtoMenuItem dto = (DtoMenuItem) childNode.
                                               getDataBean();
                    for (int j = 0; j < MenuList.size(); j++) {
                        DtoMenuItem dtoMI = (DtoMenuItem) MenuList.get(j);
                        if ((dto.getFunctionID()).equals(dtoMI.getFunctionID())) {
                            dto.setSelect(false);
                            MenuList.remove(j);
                            break;
                        }
                    }
//                    deleteChild(node.getChildAt(i));
                }
            }
        }
    }

    /**
     * 保存选中的菜单项
     * @param e
     */
    public void actionPerformedSave(ActionEvent e) {

        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionSaveMenu);
        inputInfo.setInputObj("SaveMenu",MenuList);
        inputInfo.setInputObj("RoleId",roleId);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
        	comMSG.dispMessageDialog("rsid.common.saveComplete");
        }
    }

    /**
     * 展开选中树节点的子节点以及孙子节点
     * @param e
     */
    public void actionPerformedExpand(ActionEvent e) {
        this.getTreeTable().expandsRow();
    }
    /**
     * 刷新界面,重新捞出角色对应的所有菜单
     */
    protected void resetUI() {

        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionMenuTree);
        inputInfo.setInputObj("SelectRole",roleId);
        inputInfo.setInputObj("SelectParentId",ParentId);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            ITreeNode root= (ITreeNode) returnInfo.getReturnObj(DtoKey.ROOT);
            MenuList = (List)returnInfo.getReturnObj("SelectMenu");
            DtoMenuItem databean = (DtoMenuItem)root.getDataBean();
            root_Id = databean.getFunctionID();

            //取出除根节点外的节点与MenuList进行比较，如果MenuList里面存在就勾上
            List list = (List)root.listAllChildrenByPreOrder();
            list.add(0,root);
            for(int j=0;j<list.size();j++){
                ITreeNode childNode = (ITreeNode)list.get(j);
                DtoMenuItem menu = (DtoMenuItem)childNode.getDataBean();
                for(int n=0;n<MenuList.size();n++){
                    DtoMenuItem dto = (DtoMenuItem) MenuList.get(n);
                    if((menu.getFunctionID()).equals(dto.getFunctionID())){
                        menu.setSelect(true);
                        break;
                    }
                }
            }
            if(root !=null){
                this.getTreeTable().setRoot(root);
                this.getTreeTable().expandRow(0);
                this.getTreeTable().setSelectedRow(0);
            }
        }
    }

    public void setParameter(Parameter param) {
         roleId = (String)param.get("RoleId");
         ParentId = (String)param.get("ParentId");
         resetUI();
    }
}
