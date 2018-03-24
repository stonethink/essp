package client.essp.timesheet.weeklyreport.common;

import client.framework.view.vwcomp.VWJReal;

/**
 * <p>Title: ResetRenderListener</p>
 *
 * <p>Description: Render������, ����ʱС����ȷλ�ı�ʱ����Ҫ����Render</p>
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
     * ��������Render,
     * @param comp VWJReal �趨���˹�ʱС����ȷλ�����ֿؼ�
     */
    public void resetRender(VWJTwoNumReal comp);
}
