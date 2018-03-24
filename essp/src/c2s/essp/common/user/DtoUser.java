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
         * 记录当前系统用户,可能是用自己ID登录的,也可能是通过别人授权而代理登录的
         */
        public final static String SESSION_USER = "user";
        /**
         * 记录登录系统的用户,用自己ID登录的,而不是通过代理登录的.
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

    //用户密码
    private String password;
    //用户角色列表
    private List roles;
}
