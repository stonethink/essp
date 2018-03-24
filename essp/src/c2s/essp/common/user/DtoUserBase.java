package c2s.essp.common.user;

import c2s.dto.*;

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
public class DtoUserBase extends DtoBase {
    public final static String USER_TYPE_EMPLOYEE = "EMP";
    public final static String USER_TYPE_CUST = "CUST";
    public final static String USER_LOGIN_ID = "loginId";
 
    public DtoUserBase() {
    }

    public String getDomain() {
        return domain;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getDisplayName() {
        String displayName = "";
        if (this.getUserName() == null || this.getUserName().equals("")) {
            displayName = this.getUserLoginId();
        } else {
            displayName = this.getUserLoginId()
                          + " - "
                          + this.getUserName();
        }
        return displayName;
    };

    //用户类型：标识为内部员工，还是外部客户联系人
    private String userType;
    //所属domain
    private String domain;
    //用户登录ID
    private String userLoginId;
    //用户姓名
    private String userName;
}
