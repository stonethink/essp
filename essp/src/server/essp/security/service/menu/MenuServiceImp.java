package server.essp.security.service.menu;

import java.util.*;

import c2s.dto.*;
import c2s.essp.common.menu.*;
import server.framework.logic.AbstractBusinessLogic;
import java.sql.ResultSet;
import java.sql.SQLException;
import server.framework.common.BusinessException;
import c2s.essp.common.user.DtoUser;
import c2s.essp.common.user.DtoRole;

public class MenuServiceImp extends AbstractBusinessLogic implements IMenuService {
    /**
     *
     * @return ITreeNode databean为 DtoMenuItem
     * @todo Implement this server.essp.security.service.menu.IMenuService
     *   method
     */
    public ITreeNode getMenuTree() {
        ITreeNode root = null;
        String sql1 = "SELECT * FROM upms_function where LAYER=0 and ISVALID=1";
        List menus = sql2MenuList(sql1);
        DtoMenuItem rootMenu = (DtoMenuItem) menus.get(0);
        root = createChildTree(rootMenu);
        return root;
    }

    /**
     *
     * @return List contains DtoMenuItem
     * @param roleId String
     * @todo Implement this server.essp.security.service.menu.IMenuService
     *   method
     */
    public List getRoleMenuTree(String roleId) {
        String sql= "SELECT * FROM upms_function WHERE  FUNCTION_ID In(SELECT FUNCTION_ID FROM upms_role_function where ROLE_ID='"+roleId+"') order by Order_no";
        List menus = sql2MenuList(sql);
        return menus;
    }

    /**
     *
     * @param roleId String
     * @param menuId String[]
     * @todo Implement this server.essp.security.service.menu.IMenuService
     *   method
     */
    public void saveOrUpdateRoleMenu(String roleId, String[] menuId) {
        String str="delete from upms_role_function where ROLE_ID='"+roleId+"'";
        try{
            this.getDbAccessor().executeUpdate(str);
            if(menuId!=null && menuId.length>0){
                for(int i=0;i<menuId.length;i++){
                    String sql="insert into upms_role_function (ROLE_ID,FUNCTION_ID) values ('"+roleId+"','"+menuId[i]+"')";
                    this.getDbAccessor().executeUpdate(sql);
                }
            }
        }catch(Exception ex){
            throw new BusinessException("error.logic.MenuServiceImpl.saveOrUpdateRoleMenu",
                                            ex);
        }
    }
    /**
     * 构造用户对应的菜单树,先查找所有子系统即layer=1的菜单,再查找各子系统下对应的所有菜单项
     * @param loginId String
     * @return c2s.essp.common.menu.DtoMenu
     * @todo Implement this server.essp.security.service.menu.IMenuService
     *   method
     */
    public DtoMenu getUserMenu(DtoUser user) {
        DtoMenu result = new DtoMenu();
        if(user == null)
            return result;
        List roles = user.getRoles();
        if(roles == null || user.getRoles().size() == 0)
            return result;
        String roleSQL = null;
        for(int i = 0;i < roles.size() ;i ++){
            DtoRole role = (DtoRole) roles.get(i);
            if(!role.isStatus())
                continue;
            if(roleSQL == null)
                roleSQL = "'"+role.getRoleId()+"'";
            else
                roleSQL = roleSQL + ",'"+role.getRoleId()+"'";
        }
        //查找Role对应的所有menuID
        String menuScopeSQL = " SELECT distinct function_id FROM upms_role_function WHERE role_id IN ("+roleSQL+") ";

        List subSystems = new ArrayList();
        String subSysSQL = " SELECT * FROM upms_function WHERE isvalid = '1' AND  layer='1' AND function_id IN ("+menuScopeSQL+") order by Order_no" ;
        List menus = sql2MenuList(subSysSQL);
        for(int i = 0;i < menus.size() ;i ++){
            DtoMenuItem rootMenu = (DtoMenuItem) menus.get(i);
            DtoSubSystem system = new DtoSubSystem(rootMenu);
            List subMenus = new ArrayList();
            getChildrenMenu(rootMenu.getFunctionID(), subMenus);
            system.setMenus(subMenus);
            subSystems.add(system);
        }
        result.setSubSystem(subSystems);

        return result;
    }
    
    /**
     * 递归查询某子系统中的所有菜单
     * 将oracle的递归查询方言改为Java递归查询，由广度优先变为深度优先。
     * @author LipengXu at 2009-01-13
     * @ORA2SQL essp security menu
     * @param parents List
     * @return List children
     */
    private void getChildrenMenu(String parentId, List subSystems) {
    	String menuTreeSQL = " SELECT * FROM upms_function WHERE isvalid = '1' " +
        	" and father_id = '" + parentId + "' Order by Order_no ";
    	List children = sql2MenuList(menuTreeSQL);
    	subSystems.addAll(children);
    	for(int i = 0; i < children.size(); i ++) {
            DtoMenuItem child = (DtoMenuItem) children.get(i);
            getChildrenMenu(child.getFunctionID(), subSystems);
        }
    	return;
    }
    
    //SQL转换成DtoMenuItem的List
    private List sql2MenuList(String sql){
        List result = new ArrayList();
        log.info("menu sql:"+sql);
        ResultSet rs = this.getDbAccessor().executeQuery(sql);
        try{
            while(rs.next()){
                 DtoMenuItem item = new DtoMenuItem();
                item.setFunctionID(rs.getString("FUNCTION_ID"));
                item.setName(rs.getString("Name"));
                item.setLayer(rs.getInt("Layer"));
                item.setOrderNO(rs.getInt("ORDER_NO"));
                item.setFatherID(rs.getString("FATHER_ID"));
                item.setIsValid("1".equals(rs.getString("ISVALID")));
                item.setIsLeaf("1".equals(rs.getString("ISLEAF")));
                item.setAppContext(rs.getString("APP_CONTEXT"));
                item.setFunctionAddress(rs.getString("FUNCTIONADDRESS"));
                item.setType(rs.getString("TYPE"));
                item.setIcon(rs.getString("ICON"));
                item.setDescription(rs.getString("DESCRIPTION"));
                item.setIsVisable("1".equals(rs.getString("ISVISIABLE")));
                item.setIconOn(rs.getString("ICON_ON"));
                item.setIconDown(rs.getString("ICON_DOWN"));

                result.add(item);
            }
        }catch(SQLException ex){
            throw new BusinessException("error.system.db", ex);
        }
        return result;
    }

    //获取root的子节点及孙子节点
    public ITreeNode createChildTree(DtoMenuItem root){
        ITreeNode Root = new DtoTreeNode(root);
        String sql2 = "SELECT * FROM upms_function where FATHER_ID='"+root.getFunctionID()+"' and ISVALID=1 Order by Layer , Order_no";
        List menusChild = sql2MenuList(sql2);
        if(menusChild.size()>0){
            for (int i = 0; i < menusChild.size(); i++) {
                DtoMenuItem childMenu = (DtoMenuItem) menusChild.get(i);
                ITreeNode childTreeRoot = createChildTree(childMenu);
                Root.addChild(childTreeRoot,IDto.OP_NOCHANGE);
            }
        }
        return Root;
    }
}
