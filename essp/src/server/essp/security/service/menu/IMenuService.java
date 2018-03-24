package server.essp.security.service.menu;

import c2s.dto.ITreeNode;
import java.util.List;
import c2s.essp.common.menu.DtoMenu;
import c2s.essp.common.user.DtoUser;

public interface IMenuService {
        /**
         * 构造整个ESSP系统对应的菜单树
         * @return ITreeNode databean为 DtoMenuItem
         */
        public ITreeNode getMenuTree();
        /**
         * 根据某个角色,列出角色对应的所有菜单
         * @return List contains DtoMenuItem
         */
        public List getRoleMenuTree(String roleId);
        /**
         * 保存或更新角色所对的所有菜单
         * @param rootMenu
         * @param role
         */
        public void saveOrUpdateRoleMenu(String roleId,String[] menuId);
        /**
         * 根据人员几个角色,构造角色对应的菜单树,一次性构造用户对应的菜单
         * @param loginId
         * @return
         */
        public DtoMenu getUserMenu(DtoUser user);
}
