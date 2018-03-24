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
         * �г����еĿɼ�Role
         * @return
         */
	public List listAllVisibleRole();

        /**
         * �г��û����еĽ�ɫ,�������úͲ�����
         * @param loginId
         * @return
         */
	public List listUserAllRole(String loginId);

        /**
         * ����������Ա��Ӧ�Ľ�ɫ
         */
	public void saveOrUpdateUserRole(String loginId,String[] roleIds,String domain);
}
