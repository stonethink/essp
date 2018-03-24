package client.essp.common.security.role;

import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJText;
import c2s.essp.common.user.DtoRole;
import java.awt.event.ActionEvent;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import javax.swing.JButton;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.view.VWTreeTableWorkArea;
import c2s.dto.ITreeNode;
import javax.swing.tree.TreePath;
import c2s.essp.common.code.DtoKey;
import java.awt.event.ActionListener;
import com.wits.util.Parameter;


/**
 * �г����еĽ�ɫ��UI
 * @author XiaoRong
 */
public class VwRoleList extends VWTreeTableWorkArea{

    static final String actionRoleList = "F00RoleListAction";
    static final String treeColumnName = "roleId";
    JButton Expandbtn = null;
    public VwRoleList(){

         Object[][] configs = new Object[][] {
                             {"rsid.common.roleId", "roleId", VMColumnConfig.EDITABLE, null},
                             {"rsid.common.roleName", "roleName", VMColumnConfig.UNEDITABLE, new VWJText()},
                             {"rsid.common.parentRoleId", "parentId", VMColumnConfig.UNEDITABLE, new VWJText()},
                             {"rsid.common.sequence", "seq", VMColumnConfig.UNEDITABLE, new VWJText(),Boolean.TRUE},
                             {"rsid.common.description", "description", VMColumnConfig.UNEDITABLE, new VWJText(),Boolean.TRUE}
         };

         try {
             //tree table
             super.jbInit(configs, treeColumnName, DtoRole.class);
             getTreeTable().getTree().setShowsRootHandles(true);
             getTreeTable().getTree().setRootVisible(false);

         } catch (Exception e) {
            e.printStackTrace();
         }
         addUICEvent();
    }

    private void addUICEvent(){

        TableColumnChooseDisplay chooseDisplay =
        new TableColumnChooseDisplay(this.getTreeTable(), this);
        JButton btnDisplay = chooseDisplay.getDisplayButton();
        this.getButtonPanel().addButton(btnDisplay);

        //expand
        Expandbtn = this.getButtonPanel().addButton("expand.png");
        Expandbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedExpand(e);
            }
        });
        Expandbtn.setToolTipText("rsid.common.expand");


    }
    /**
     *չ��ѡ�����ڵ���ӽڵ��Լ����ӽڵ�
     * @param e
     */
    public void actionPerformedExpand(ActionEvent e) {
        this.getTreeTable().expandsRow();
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
    }
    /**
     * ˢ�½��棬���»�ȡ��ɫ��
     */
    protected void resetUI() {

        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionRoleList);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            ITreeNode root = (ITreeNode) returnInfo.getReturnObj(DtoKey.ROOT);
            this.getTreeTable().setRoot(root);
            if( root != null ){
                this.getTreeTable().expandPath(new TreePath(root));
                if( root.getChildCount() > 0 ){
                    this.getTreeTable().setSelectedRow(root.getChildAt(0));
                }
            }
        }
    }
}
