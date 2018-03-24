package client.essp.common.security.role;

import client.essp.common.view.VWTDWorkArea;
import java.awt.Dimension;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.wits.util.TestPanel;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.event.RowSelectedLostListener;
import c2s.essp.common.user.DtoRole;
import com.wits.util.Parameter;
/**
 * 给角色分配菜单的UI，分为左右两帧，左边列出所有的角色，右边列出所有的菜单树
 * @author XiaoRong
 */
public class VwRoleMenu extends VWTDWorkArea {

    private VwRoleList vwRoleList;
    private VwMenuList vwMenuList;

    public VwRoleMenu(){
        super(420);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
    }

    public void jbInit() throws Exception{
        this.setPreferredSize(new Dimension(700, 470));
        this.setHorizontalSplit();
        vwRoleList = new VwRoleList();
        vwMenuList = new VwMenuList();
        this.getTopArea().addTab("rsid.common.role",vwRoleList);
        this.getDownArea().addTab("rsid.common.menu",vwMenuList);
    }

    public void refreshWorkArea() {
        this.vwRoleList.setParameter(new Parameter());
        this.vwRoleList.refreshWorkArea();
    }
    /**
     * 注册事件
     */
    public void addUICEvent(){

        this.vwRoleList.getTreeTable().addRowSelectedLostListener(new
            RowSelectedLostListener() {
            public boolean processRowSelectedLost(int oldSelectedRow,
                                                  Object oldSelectedData) {
                return processRowSelectedLostAccList(oldSelectedRow,
                    oldSelectedData);
            }
        });

        this.vwRoleList.getTreeTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
                processRowSelectedAccList();
            }
        });

    }
    /**
     * 处理选择的行丢失事件
     * @param oldSelectedRow
     * @param oldSelectedData
     * @return
     */
    public boolean processRowSelectedLostAccList(int oldSelectedRow,
                                                 Object oldSelectedData) {
        this.getDownArea().getSelectedWorkArea().saveWorkArea();
        return this.getDownArea().getSelectedWorkArea().isSaveOk();
    }
    /**
     * 处理选择新行事件
     */
    public void processRowSelectedAccList() {
        DtoRole  dto = (DtoRole)this.vwRoleList.getSelectedData();
        if(dto!=null){
            Parameter param = new Parameter();
            param.put("RoleId", dto.getRoleId());
            param.put("ParentId", dto.getParentId());
            vwMenuList.setParameter(param);
        }
    }

}
