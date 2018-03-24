package server.essp.security.service.authorize;

import java.util.List;

public interface IAuthService {

        /**
         * �����Ȩ��
         * @param loginId String
         * @param name String
         * @param loginIds String[]
         * @param names String[]
         */
        public void addAuthorize(String loginId,String name,String[] loginIds,String[] names);

        /**
         * ɾ����Ȩ��
         * @param loginId String
         * @param auth_Id String
         */
        public void delAuthorize(String loginId,String auth_Id);

        /**
         * ����ȡ����Ȩ����list���ϵ���ʽ����
         * @param loginId String
         * @return List
         */
        public List listAuthorize(String loginId);

        /**
         * ����ȡ�Ĵ�������list���ϵ���ʽ����
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
