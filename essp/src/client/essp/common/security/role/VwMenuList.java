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
 * �г���ɫ��Ӧ�����в˵���UI
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
     * ���ò˵���
     * @param evt
     */
    public void processRowSelectedList(ActionEvent evt) {
        VWJCheckBox checkBox = (VWJCheckBox) evt.getSource();
        ITreeNode Node = this.getTreeTable().getSelectedNode();
        //��ѡ��һ���ڵ㣬��Ҫ��Ӧ�Ĺ��������ӽڵ����ӽڵ��Լ������ڵ�����ĸ��ڵ�
        if(checkBox.getUICValue()=="true"){
            if(!Node.isLeaf()){
                addChild(Node);//ѡ�������ӽڵ����ӽڵ�
            }
            DtoMenuItem dataBean = (DtoMenuItem) Node.getDataBean();
            dataBean.setSelect(true);
            MenuList.add(dataBean);//ѡ�����Լ�

            if(Node.getParent()!=null){
                if (!root_Id.equals(dataBean.getFatherID())) {
                    addParent(Node.getParent()); //ѡ�����Լ��ĳ����ڵ�����ĸ��ڵ�
                }
            }
        }else{//��ȡ��һ���ڵ�ʱ����Ҫ��Ӧ��ȡ�������ӽڵ����ӽڵ�
            DtoMenuItem dataBean= (DtoMenuItem)Node.getDataBean();
            //��ɾ��һ���ڵ�ʱ���Ƚ���ɾ������Ȼ��ͨ������ɾ�������ӽڵ��Լ����ӽڵ�
            for (int i = 0; i < MenuList.size(); i++) {
                DtoMenuItem dtoMI = (DtoMenuItem) MenuList.get(i);
                if ((dataBean.getFunctionID()).equals(dtoMI.getFunctionID())) {
                    dataBean.setSelect(false);
                    MenuList.remove(i);
                    break;
                }
            }
            //ͨ������ɾ�������ӽڵ��Լ����ӽڵ�
            if(!Node.isLeaf()){
               deleteChild(Node);
            }

        }
        this.getTreeTable().refreshTree();

    }
    /**
     * ��ѡ�нڵ���ӽڵ�����Ϊѡ��
     * @param Node
     */
    public void addChild(ITreeNode Node){
        DtoMenuItem data =(DtoMenuItem)Node.getDataBean();
       //�жϹ�ѡ���Ƿ��Ǹ��ڵ㣬����ǣ�ֻ�빴ѡ�Լ��������Ҫ��ѡ�����ӽڵ����ӽڵ�
                if(!root_Id.equals(data.getFunctionID())){
                    for (int i = 0; i < Node.listAllChildrenByPostOrder().size(); i++) {
                        ITreeNode childNode = (ITreeNode) Node.listAllChildrenByPostOrder().get(i);
                        DtoMenuItem dataBean = (DtoMenuItem) childNode.
                                               getDataBean();
                        //��MenuList�ĳ���Ϊ0ʱ����ֱ�Ӱ��Լ��ĵ�i���ӽڵ㹴��
                        if(MenuList.size()==0){
                            dataBean.setSelect(true);
                            MenuList.add(dataBean);
                        }else{ //��MenuList�ĳ��Ȳ�Ϊ0ʱ����Ҫ�ж��Լ��ĵ�i���ӽڵ��Ƿ��ϣ�
                               //��������ˣ��Ͳ���Ҫ���ˣ����û���ϣ�����Ҫ����

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
//                        //�ж��Լ��ĵ�i���ӽڵ��Ƿ�ΪҶ�ӽڵ㣬������ǣ���Ҫ���������ӽڵ㹴��
//                        if(!childNode.isLeaf()){
//                            addChild(childNode);
//                        }
                    }
                }
    }

    /**
     * ��ѡ�нڵ�ĸ��ڵ�����Ϊѡ��
     * @param node
     */
    public void addParent(ITreeNode node) {
        DtoMenuItem dataBean = (DtoMenuItem) node.getDataBean();
        //���MenuList�ĳ���Ϊ0����ֱ�ӽ��Լ��ĳ����ڵ�����ĸ��ڵ㹴��
        if(MenuList.size()==0){
            dataBean.setSelect(true);
            MenuList.add(dataBean);
        }else{
            //���MenuList�ĳ��Ȳ�Ϊ0������Ҫ�ж��Լ��ĳ����ڵ�����ĸ��ڵ��Ƿ��Ѿ�����
            //��������ˣ��Ͳ���Ҫ���ˣ����û���ϣ�����Ҫ����
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
        //������ѡ�Լ��ĳ����ڵ�����ĸ��ڵ�
        if (node.getParent() != null) {
            if (!root_Id.equals(dataBean.getFatherID())) {
                addParent(node.getParent());
            }
        }
    }

    /**
     * ɾ��ѡ�нڵ���ӽڵ��Լ����ӽڵ�
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
     * ����ѡ�еĲ˵���
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
     * չ��ѡ�����ڵ���ӽڵ��Լ����ӽڵ�
     * @param e
     */
    public void actionPerformedExpand(ActionEvent e) {
        this.getTreeTable().expandsRow();
    }
    /**
     * ˢ�½���,�����̳���ɫ��Ӧ�����в˵�
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

            //ȡ�������ڵ���Ľڵ���MenuList���бȽϣ����MenuList������ھ͹���
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
