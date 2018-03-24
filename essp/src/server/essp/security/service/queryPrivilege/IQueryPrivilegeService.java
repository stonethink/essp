package server.essp.security.service.queryPrivilege;

import java.util.List;
import c2s.dto.ITreeNode;

/**
 * <p>Title: IQueryPrivilege</p>
 *
 * <p>Description: 专案查询授权服务接口</p>
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
     * 根据用户loginId(用“，”隔开的多用户)获取用户名称，权限角色等信息
     * @param loginIds String
     * @return List
     */
    public List getUserInfo(String loginIds);

    /**
     * 保存用户专案查询权限
     * @param loginId String
     * @param root ITreeNode
     */
    public void saveQueryPrivilege(String loginId, ITreeNode root);

    /**
     * 根据用户loginId获取用户查询权限
     * @param loginId String
     * @return ITreeNode
     */
    public ITreeNode loadQueryPrivilege(String loginId);
    
    /**
     * 刪除某用户的所有专案查询授权
     * @param loginId
     */
    public void clearQueryPrivilege(String loginId);
}
