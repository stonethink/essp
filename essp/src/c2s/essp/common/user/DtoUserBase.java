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

    //�û����ͣ���ʶΪ�ڲ�Ա���������ⲿ�ͻ���ϵ��
    private String userType;
    //����domain
    private String domain;
    //�û���¼ID
    private String userLoginId;
    //�û�����
    private String userName;
}
