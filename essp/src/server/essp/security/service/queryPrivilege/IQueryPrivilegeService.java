package server.essp.security.service.queryPrivilege;

import java.util.List;
import c2s.dto.ITreeNode;

/**
 * <p>Title: IQueryPrivilege</p>
 *
 * <p>Description: ר����ѯ��Ȩ����ӿ�</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface IQueryPrivilegeService {

    /**
     * �����û�loginId(�á����������Ķ��û�)��ȡ�û����ƣ�Ȩ�޽�ɫ����Ϣ
     * @param loginIds String
     * @return List
     */
    public List getUserInfo(String loginIds);

    /**
     * �����û�ר����ѯȨ��
     * @param loginId String
     * @param root ITreeNode
     */
    public void saveQueryPrivilege(String loginId, ITreeNode root);

    /**
     * �����û�loginId��ȡ�û���ѯȨ��
     * @param loginId String
     * @return ITreeNode
     */
    public ITreeNode loadQueryPrivilege(String loginId);
    
    /**
     * �h��ĳ�û�������ר����ѯ��Ȩ
     * @param loginId
     */
    public void clearQueryPrivilege(String loginId);
}
