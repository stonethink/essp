package server.essp.security.service.menu;

import c2s.dto.ITreeNode;
import java.util.List;
import c2s.essp.common.menu.DtoMenu;
import c2s.essp.common.user.DtoUser;

public interface IMenuService {
        /**
         * ��������ESSPϵͳ��Ӧ�Ĳ˵���
         * @return ITreeNode databeanΪ DtoMenuItem
         */
        public ITreeNode getMenuTree();
        /**
         * ����ĳ����ɫ,�г���ɫ��Ӧ�����в˵�
         * @return List contains DtoMenuItem
         */
        public List getRoleMenuTree(String roleId);
        /**
         * �������½�ɫ���Ե����в˵�
         * @param rootMenu
         * @param role
         */
        public void saveOrUpdateRoleMenu(String roleId,String[] menuId);
        /**
         * ������Ա������ɫ,�����ɫ��Ӧ�Ĳ˵���,һ���Թ����û���Ӧ�Ĳ˵�
         * @param loginId
         * @return
         */
        public DtoMenu getUserMenu(DtoUser user);
}
