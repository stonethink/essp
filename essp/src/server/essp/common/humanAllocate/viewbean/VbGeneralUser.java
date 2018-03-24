package server.essp.common.humanAllocate.viewbean;

/**
 * Allocated用户的概要信息：name,loginid和code,sex
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
public class VbGeneralUser {
    private String name;
    private String loginid;
    private String code;
    private String sex;

    public static final String MALE_CODE="1";
    public static final String FEMALE_CODE="0";
    public String getName() {
        return name;
    }

    public String getLoginid() {
        return loginid;
    }

    public String getCode() {
        return code;
    }

    public String getSex() {
        return sex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setSex(String sex) {
        if(MALE_CODE.equals(sex))
            this.sex = "MALE";
        else if(FEMALE_CODE.equals(sex))
            this.sex = "FEMALE";
        else
            this.sex = sex;
    }

}
