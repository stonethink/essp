package server.essp.security.service.authorize;

import java.util.List;

public interface IAuthService {

        /**
         * 添加授权人
         * @param loginId String
         * @param name String
         * @param loginIds String[]
         * @param names String[]
         */
        public void addAuthorize(String loginId,String name,String[] loginIds,String[] names);

        /**
         * 删除授权人
         * @param loginId String
         * @param auth_Id String
         */
        public void delAuthorize(String loginId,String auth_Id);

        /**
         * 将获取的授权人以list集合的形式返回
         * @param loginId String
         * @return List
         */
        public List listAuthorize(String loginId);

        /**
         * 将获取的代理人以list集合的形式返回
         * @param auth_id String
         * @return List
         */
        public List listAgent(String auth_id);

        /**
         * check agent exist
         * @param agent String
         * @param authorize_id_id String
         * @return boolean
         */
        public boolean checkAgent(String agent_id, String authorize_id);

}
