package client.essp.timesheet;

/**
 * <p>Title: SelectActivityChangedListener</p>
 *
 * <p>Description: Activity�仯������</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface ActivityChangedListener {

    /**
     * Activity�仯�����ݱ仯���ActivityRid
     * @param activityRid Long
     */
    public void processActivityChanged(Long activityRid);
}
