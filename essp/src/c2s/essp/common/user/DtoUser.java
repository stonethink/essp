package c2s.essp.common.user;

import java.util.*;


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
 * @version 1.0*/

public class DtoUser extends DtoUserInfo {
        /**
         * ��¼��ǰϵͳ�û�,���������Լ�ID��¼��,Ҳ������ͨ��������Ȩ�������¼��
         */
        public final static String SESSION_USER = "user";
        /**
         * ��¼��¼ϵͳ���û�,���Լ�ID��¼��,������ͨ�������¼��.
         */
        public final static String SESSION_LOGIN_USER = "loginUser";

    public DtoUser() {
    }
    public String getPassword() {
        return password;
    }

    public List getRoles() {
        return roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(List roles) {
        this.roles = roles;
    }

    //�û�����
    private String password;
    //�û���ɫ�б�
    private List roles;
}
