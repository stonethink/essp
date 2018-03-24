package itf.user;

import java.util.List;
import c2s.essp.common.user.DtoUserBase;
import server.framework.common.BusinessException;
import server.framework.logic.IBusinessLogic;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IRoleUtil {
        /**
         * 列出所有的可见Role
         * @return
         */
	public List listAllVisibleRole();

        /**
         * 列出用户所有的角色,包含可用和不可用
         * @param loginId
         * @return
         */
	public List listUserAllRole(String loginId);

        /**
         * 保存或更新人员对应的角色
         */
	public void saveOrUpdateUserRole(String loginId,String[] roleIds,String domain);
}
