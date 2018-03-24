package client.essp.timesheet;

/**
 * <p>Title: SelectActivityChangedListener</p>
 *
 * <p>Description: Activity变化监听器</p>
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
     * Activity变化，传递变化后的ActivityRid
     * @param activityRid Long
     */
    public void processActivityChanged(Long activityRid);
}
