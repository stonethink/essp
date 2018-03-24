package c2s.essp.common.workflow;

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
public interface IParticipant {

    public static String PART_TYPE_ANYPERSONAL = "00";
    public static String PART_TYPE_PM_OF_SUBMITUSER = "01";
    public static String PART_TYPE_DM_OF_SUBMITUSER = "02";
    public static String PART_TYPE_GM_OF_SUBMITUSER = "03";
    public static String PART_TYPE_UPLEADER_IN_OBS_OF_ACTIVITY = "04";
    public static String PART_TYPE_UPLEADER_IN_EBS_OF_ACTIVITY = "05";
    public static String PART_TYPE_EMPLOYEE = "06";

    public String getLoginID();
    public String getUserName();
    public String getType();

    public void setLoginID(String loginID);

    public void setType(String type);

    public void setUserName(String userName);
}
