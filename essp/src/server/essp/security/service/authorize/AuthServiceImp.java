package server.essp.security.service.authorize;

import server.framework.logic.AbstractBusinessLogic;
import server.framework.common.BusinessException;
import java.util.List;
import javax.sql.RowSet;
import c2s.essp.common.user.DtoUserBase;
import java.util.ArrayList;

public class AuthServiceImp extends AbstractBusinessLogic implements IAuthService{

        /**
         * 添加授权人
         * @param userId String
         * @param name String
         * @param loginIds String[]
         * @param names String[]
         */
        public void addAuthorize(String userId, String name, String[] loginIds,
                                 String[] names){
            String sql ="select * from UPMS_USER_AUTHORIZE t where t.LOGIN_ID='"+userId+"'";
            try {
                RowSet rs = this.getDbAccessor().executeQuery(sql);
                while(rs.next()){
                    String auth_id = rs.getString("AUTHORIZE_ID");
                    if(loginIds != null && loginIds.length > 0){
                        for(int k =0;k<loginIds.length;k++){
                            if(auth_id.equals(loginIds[k])){
                                throw new BusinessException("error.logic.AuthServiceImp.Authorizeduplicate",
                                "Authorize duplicate["+loginIds[k]+"]");
                            }
                        }
                    }
                }
                if (loginIds != null && loginIds.length > 0) {
                    for (int i = 0; i < loginIds.length; i++) {
                        for (int j = 0; j < names.length; j++) {
                            if (i == j) {
                                String sql1 =
                                        "insert into UPMS_USER_AUTHORIZE (login_id,name,authorize_id,authorize_name)" +
                                        " values ('" + userId + "','" + name +
                                        "','" + loginIds[i] + "','" + names[i] +
                                        "')";
                                this.getDbAccessor().executeUpdate(sql1);
                                break;
                            }
                        }
                    }
                }
            }catch(BusinessException ex){
                throw new BusinessException("error.logic.AuthServiceImp.Authorizeduplicate",ex);
            }catch (Exception ex) {
                throw new BusinessException(
                        "error.logic.AuthServiceImpl.addAuthorize",
                        ex);
            }
        }

        /**
         * 删除授权人
         * @param userId String
         * @param loginId String
         */
        public void delAuthorize(String userId, String loginId) {

            try {
                if (loginId != null && loginId.length() > 0) {
                    String sql1 =
                            "delete from UPMS_USER_AUTHORIZE where login_id='" +
                            userId + "' and authorize_id='" + loginId + "'";
                    this.getDbAccessor().executeUpdate(sql1);
                }
            } catch (Exception ex) {
                throw new BusinessException(
                        "error.logic.AuthServiceImpl.delAuthorize",
                        ex);
            }
        }

        /**
         * 将获取的授权人以list集合的形式返回
         * @param userId String
         * @return List
         */
        public List listAuthorize(String userId) {
            List list = new ArrayList();
            String sql = "select * from UPMS_USER_AUTHORIZE t where t.LOGIN_ID='" +
                         userId + "'";
            try {
                RowSet rs = this.getDbAccessor().executeQuery(sql);
                while (rs.next()) {
                    DtoUserBase dtouser = new DtoUserBase();
                    dtouser.setUserLoginId(rs.getString("AUTHORIZE_ID"));
                    dtouser.setUserName(rs.getString("AUTHORIZE_NAME"));
                    list.add(dtouser);
                }
                return list;
            } catch (Exception ex) {
                throw new BusinessException(
                        "error.logic.AuthServiceImpl.listAuthorize",
                        ex);
            }
        }

        /**
         * 将获取的代理人以list集合的形式返回
         * @param auth_id String
         * @return List
         */
        public List listAgent(String auth_id) {
            List list = new ArrayList();
            String sql =
                    "select * from UPMS_USER_AUTHORIZE t where Upper(t.AUTHORIZE_ID)=Upper('" +
                    auth_id + "')";
            try {
                RowSet rs = this.getDbAccessor().executeQuery(sql);
                while (rs.next()) {
                    DtoUserBase dtouser = new DtoUserBase();
                    dtouser.setUserLoginId(rs.getString("LOGIN_ID"));
                    dtouser.setUserName(rs.getString("NAME"));
                    list.add(dtouser);
                }
                return list;
            } catch (Exception ex) {
                throw new BusinessException("error.logic.AuthServiceImpl.listAgent",
                                            ex);
            }
        }

        /**
         * check agent exist
         * @param agent String
         * @param authorize_id_id String
         * @return boolean
         */
        public boolean checkAgent(String agent_id, String authorize_id) {
            String sql =
                    "select * from UPMS_USER_AUTHORIZE t " +
                    "where Upper(t.login_id) = Upper('" + agent_id + "') " +
                    "and Upper(t.AUTHORIZE_ID)=Upper('" + authorize_id + "')";
            try {
                RowSet rs = this.getDbAccessor().executeQuery(sql);
                if (rs.next()) {
                    return true;
                }
            } catch (Exception ex) {
                throw new BusinessException("error.logic.AuthServiceImpl.listAgent",
                                            ex);
            }
            return false;
        }
}
