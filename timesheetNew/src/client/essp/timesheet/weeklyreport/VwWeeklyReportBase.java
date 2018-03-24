package client.essp.timesheet.weeklyreport;

import java.awt.BorderLayout;

import client.essp.common.view.VWGeneralWorkArea;
import client.essp.timesheet.weeklyreport.detail.VwDetailList;
import client.essp.timesheet.weeklyreport.summary.VwSummaryList;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import com.wits.util.Parameter;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public abstract class VwWeeklyReportBase extends VWGeneralWorkArea {
    protected VwDetailList detail = new VwDetailList();
    protected VwSummaryList sum = new VwSummaryList();
    protected DtoTimeSheet dtoTimeSheet;

    public VwWeeklyReportBase() {
        try {
            jbInit();
        } catch (Exception ex) {
            log.error(ex);
        }
        addUICEvent();
    }

    /**
     * ��ʼ��UI
     * @throws Exception
     */
    private void jbInit() throws Exception {
//        TcLayout layout = new TcLayout();
//        layout.setBottomSpace(20);
//        in.setLayout(layout);

        this.add(detail, BorderLayout.CENTER);
        this.add(sum, BorderLayout.SOUTH);
    }

    /**
     * ע��UI�¼�
     */
    private void addUICEvent() {
        detail.addColumnWithListener(sum.getColumnWithListener());
        detail.addWorkHourListener(sum.getWorkHourListener());
        detail.addColumnNumListener(sum.getColumnNumListener());
        detail.addResetRenderListener(sum);
        detail.addSubmitTimeSheetListener(sum.getSubmitTimeSheetListener());
        detail.addStandarHoursListener(sum.getStandarHoursListener());
    }

    /**
     * ˢ��UI
     */
    public void resetUI() {
        Parameter param = new Parameter();
        dtoTimeSheet = loadData();
        param.put(DtoTimeSheet.DTO, dtoTimeSheet);
        detail.setParameter(param);
        detail.refreshWorkArea();
    }
    public Long getSelectActivityId() {
    	return detail.getSelectActivityId();
    }
    /**
     * ��ȡ����
     * @return DtoTimeSheet
     */
    protected abstract DtoTimeSheet loadData();
}
