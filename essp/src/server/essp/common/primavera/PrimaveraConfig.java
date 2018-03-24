package server.essp.common.primavera;

import com.wits.util.Config;

public class PrimaveraConfig {
    private final static String BOOTSTRAP_HOME_CFG_FILE_NAME = "PrimaveraApi";
    private final static Config cfg = new Config(BOOTSTRAP_HOME_CFG_FILE_NAME);

    private final static String PROPERTY_BOOTSTRAP_HOME =
            "primavera.bootstrap.home";
    private final static String PROPERTY_USER_LOGINID =
            "primavera.user.loginId";
    private final static String PROPERTY_USER_PASSWORD =
            "primavera.user.passWord";
    private final static String PROPERTY_DATABASE_INDEX =
            "primavera.database.index";

    private static String bootStrapHome = null;
    private static String userLoginId = null;
    private static String userPassWord = null;
    private static Integer dbIndex = null;

    public final static String getBootstrapHomePropertyName() {
        return PROPERTY_BOOTSTRAP_HOME;
    }

    public final static String getBootStrapHome() {
        if (bootStrapHome == null) {
            bootStrapHome = cfg.getValue(PROPERTY_BOOTSTRAP_HOME);
        }
        return bootStrapHome;
    }

    public final static String getUserLoginId() {
        if (userLoginId == null) {
            userLoginId = cfg.getValue(PROPERTY_USER_LOGINID);
        }
        return userLoginId;
    }

    public final static String getUserPassWord() {
        if (userPassWord == null) {
            userPassWord = cfg.getValue(PROPERTY_USER_PASSWORD);
        }
        return userPassWord;
    }

    public final static Integer getDbIndex() {
        if (dbIndex == null) {
            String strIndex = cfg.getValue(PROPERTY_DATABASE_INDEX);
            try {
                dbIndex = Integer.parseInt(strIndex);
            } catch (Exception e) {
                dbIndex = new Integer(0);
            }
        }
        return dbIndex;
    }
}
