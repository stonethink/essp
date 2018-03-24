package c2s.essp.common.menu;

import java.util.*;

/**
 * <p>Title:</p>
 *
 * <p>Description: 用户可看到所有菜单,分为两个层级</p>
 *   第一层为子系统,每个子系统下包含所有一个根菜单和其所有的子菜单项
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: Enovation</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DtoMenu {
        /**
         * 系统树的根节点ID
         */
        public static final String ROOT_FUNCTIONID = "M000000000";
        /**
         * 记录当前系统用户(可能是用自己ID登录的,也可能是通过别人授权而代理登录的)的对应菜单树
         */
        public static final String SESSION_MENU = "menu";
        /**
         * 记录登录系统的用户(用自己ID登录的,而不是通过代理登录的)的对应菜单树
         */
        public final static String SESSION_LOGIN_MENU = "loginMenu";
        /**
         * 记录登录系统的用户所有可代理人员的菜单树,用Map存入Session,Key为代理人的loginID,Value:DtoMenu对象
         */
        public final static String SESSION_AGENT_MENUS = "agentMenus";
    /**
     * 所有子系统菜单放入Map中,Key:子系统ID,Value:子系统对应DtoSubSysMenu对象
     */
    private List subSystem = new ArrayList();
    public List getSubSystem() {
        return subSystem;
    }
    public void setSubSystem(List subSys) {
        subSystem = subSys;
    }
    /**
     * 获得子系统下所有的菜单项
     * @param systemId String
     * @return List
     */
    public List getSubSystemMenus(String systemId){
        if(subSystem == null || systemId == null)
            return null;
        for(int i = 0;i < subSystem.size() ;i ++){
            DtoSubSystem sub = (DtoSubSystem) subSystem.get(i);
            if(systemId.equals(sub.getRoot().getFunctionID())){
                return sub.getMenus();
            }
        }
        return null;
    }
}
