package client.essp.timesheet.weeklyreport.common;

import client.framework.view.vwcomp.VWJReal;

/**
 * <p>Title: ResetRenderListener</p>
 *
 * <p>Description: Render监听器, 当工时小数精确位改变时，需要重设Render</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface ResetRenderListener {

    /**
     * 触发重设Render,
     * @param comp VWJReal 设定好了工时小数精确位的数字控件
     */
    public void resetRender(VWJTwoNumReal comp);
}
