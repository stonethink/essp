package server.essp.pms.account.code;
import com.wits.util.Config;
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
public class ActivityCodeConfig {
    private static final String CFG_FILE_NAME = "activityCode";
    private static final Config cfg = new Config(CFG_FILE_NAME);
    private static final String ACTIVITY_CODE_NAME = "activityCodeName";
    private static final String CODING_AND_UT_RID = "coding&UtRId";

    private static String activityCodeName = null;
    private static String codingUtRid = null;

    public static String getActivityCodeName() {
        if(activityCodeName == null) {
            activityCodeName = cfg.getValue(ACTIVITY_CODE_NAME);
        }
        return activityCodeName;
    }

    public static String getCodingUtRid() {
        if(codingUtRid == null) {
            codingUtRid = cfg.getValue(CODING_AND_UT_RID);
        }
        return codingUtRid;
    }
}
