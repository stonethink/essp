package server.essp.security.service.user;

import server.essp.common.ldap.LDAPUtil;
import server.framework.common.BusinessException;
import c2s.essp.common.user.DtoUserBase;
import itf.hr.LgHrUtilImpl;
import java.sql.ResultSet;
import java.sql.*;

public class DBUserServiceImp extends AbstractUserServiceImp {
        /**
         *
         * @param userType String
         * @param site String
         * @param loginId String
         * @param passord String
         * @return DtoUserBase
         * @throws BusinessException
         * @todo Implement this server.essp.security.service.user.IUserService
         *   method
         */
        public DtoUserBase authenticationUser(String userType, String domain,
                                              String loginId, String password)  {
            String sql = "SELECT login_id,password,user_id FROM " + LgHrUtilImpl.LOGIN_TABLE + " WHERE login_id='"+loginId+"'";
            ResultSet rt = this.getDbAccessor().executeQuery(sql);
            try {
                if (!rt.next()) {
                    throw new BusinessException("error.login.nologinId",
                                                "Can not find[" + loginId +
                                                "] in system!");
                }
                String dbpassword = rt.getString("password");
                if(!password.equals(dbpassword))
                    throw new BusinessException("error.login.invalid",
                                                "Invalid loginId or password!");
            } catch (SQLException ex) {
                throw new BusinessException();
            }

            DtoUserBase user = new DtoUserBase();
            user.setDomain(domain);
            user.setUserLoginId(loginId);
            user.setUserType(userType);
            return user;
        }
}
