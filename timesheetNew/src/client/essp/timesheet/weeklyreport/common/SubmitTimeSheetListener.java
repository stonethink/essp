package client.essp.timesheet.weeklyreport.common;

/**
 * <p>Title: SubmitTimeSheetListener</p>
 *
 * <p>Description: 监听工时单提交事件，并返回是否允许提交</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface SubmitTimeSheetListener {

    /**
     * 触发工时单提交
     * @return boolean 是否允许提交
     */
    public boolean submitTimeSheet();
}
