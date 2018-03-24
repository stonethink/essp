package client.essp.tc.weeklyreport;

import java.awt.Color;
import java.awt.Dimension;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.table.TableColumn;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.tc.weeklyreport.DtoHoursOnWeek;
import c2s.essp.tc.weeklyreport.DtoOvertimeLeave;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.tc.common.TableListener;
import client.essp.tc.common.TcLineBorder;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.NodeViewManager;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;

public class VwOvertimeList extends VWTableWorkArea {
    static final String actionIdList = "FTCOvertimeListAction";

    /**
     * define common data
     */
    private List overtimeList = new ArrayList();
    private Date beginPeriod;
    private Date endPeriod;
    private String userId;

    private DtoHoursOnWeek overtimeOnWeek = new DtoHoursOnWeek();
    private DtoHoursOnWeek leaveOnWeek = new DtoHoursOnWeek();
    private DtoHoursOnWeek overtimeOnWeekConfirmed = new DtoHoursOnWeek();
    private DtoHoursOnWeek leaveOnWeekConfirmed = new DtoHoursOnWeek();

    public VwOvertimeList() {

        VWJText text = new VWJText();
        VWJReal real = new VWJReal();
        real.setMaxInputIntegerDigit(2);
        real.setMaxInputDecimalDigit(2);
        Object[][] configs = new Object[][] {
                             {"Account/Dept", "belongTo", VMColumnConfig.UNEDITABLE, text},
                             {"Type", "type", VMColumnConfig.UNEDITABLE, text},
                             {"Cause", "cause", VMColumnConfig.UNEDITABLE, text},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.SATURDAY], "satHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.SUNDAY], "sunHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.MONDAY], "monHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.TUESDAY], "tueHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.WEDNESDAY], "wedHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.THURSDAY], "thuHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.FRIDAY], "friHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.SUMMARY, "sumHour", VMColumnConfig.UNEDITABLE, real},
                             {"Comments", "commentStatus", VMColumnConfig.UNEDITABLE, text},
        };

        try {
            model = new VMTableOvertime(configs);
            model.setDtoType(DtoOvertimeLeave.class);

            NodeViewManager nodeViewManager = new NodeViewManager() {
                public Color getSelectBackground() {
                    return new Color(200, 200, 200);
                }

                public Color getSelectForeground() {
                    return getForeground();
                }
            };
            table = new TableOvertime(model, nodeViewManager);

            this.add(table.getScrollPane(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initUI();
    }

    //设置表格的外观
    private void initUI() {

        //设置header的描绘器
        HeaderRenderOvertime headerRenderOvertime = new HeaderRenderOvertime();
        for (int i = 0; i < getModel().getColumnCount(); i++) {
            TableColumn column = getTable().getColumnModel().getColumn(i);
            column.setHeaderRenderer(headerRenderOvertime);
        }

        //不容许调整列的顺序
        table.getTableHeader().setReorderingAllowed(false);

        //表格标题栏的背景与表格背景色同,这样多出来的部分就看不见了
        table.getTableHeader().setBackground(table.getBackground());

        //设置滚动条的边框
        TcLineBorder tableBorder = new TcLineBorder(UIManager.getColor(
                "Table.gridColor"));
        //tableBorder.setShowBottom(false);
        tableBorder.setShowRight(false);
        table.getScrollPane().setBorder(tableBorder);

        clearOvertimeSum();

        this.setMinimumSize(new Dimension( -1, this.getMinmizeTableHeight()));
        this.setPreferredSize(new Dimension( -1, this.getPreferredTableHeight()));
        this.setMaximumSize(new Dimension( -1, this.getMaxmizeTableHeight()));
    }

    public void actionPerformedLoad() {
        resetUI();
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.beginPeriod = (Date) (param.get(DtoTcKey.BEGIN_PERIOD));
        this.endPeriod = (Date) (param.get(DtoTcKey.END_PERIOD));
        this.userId = (String)param.get(DtoTcKey.USER_ID);
    }

    //页面刷新－－－－－
    protected void resetUI() {
        if (this.beginPeriod == null || this.endPeriod == null || userId == null) {

            overtimeList = new ArrayList();
            getTable().setRows(overtimeList);

            setVisible(false);
            clearOvertimeSum();
            fireTableChanged(TableListener.EVENT_ROW_COUNT_CHANGED);
        } else {

            InputInfo inputInfo = new InputInfo();

            inputInfo.setActionId(this.actionIdList);
            inputInfo.setInputObj(DtoTcKey.USER_ID, this.userId);
            inputInfo.setInputObj(DtoTcKey.BEGIN_PERIOD, this.beginPeriod);
            inputInfo.setInputObj(DtoTcKey.END_PERIOD, this.endPeriod);

            ReturnInfo returnInfo = accessData(inputInfo);

            if (returnInfo.isError() == false) {
                overtimeList = (List) returnInfo.getReturnObj(DtoTcKey.OVERTIME_LEAVE_LIST);

                DtoHoursOnWeek newLeaveOnWeek = (DtoHoursOnWeek) returnInfo.getReturnObj(DtoTcKey.LEAVE_ON_WEEK);
                DtoHoursOnWeek newOvertimeOnWeek = (DtoHoursOnWeek) returnInfo.getReturnObj(DtoTcKey.OVERTIME_ON_WEEK);
                DtoHoursOnWeek newLeaveOnWeekConfirmed = (DtoHoursOnWeek) returnInfo.getReturnObj(DtoTcKey.LEAVE_ON_WEEK_CONFIRMED);
                DtoHoursOnWeek newOvertimeOnWeekConfirmed = (DtoHoursOnWeek) returnInfo.getReturnObj(DtoTcKey.OVERTIME_ON_WEEK_CONFIRMED);

                for (int i = DtoHoursOnWeek.SATURDAY; i <= DtoHoursOnWeek.SUMMARY; i++) {
                    if(newOvertimeOnWeekConfirmed != null)
                        leaveOnWeekConfirmed.setHour(i, newLeaveOnWeekConfirmed.getHour(i));
                    if(newLeaveOnWeek != null)
                        leaveOnWeek.setHour(i, newLeaveOnWeek.getHour(i));
                    if(newOvertimeOnWeek != null)
                        overtimeOnWeek.setHour(i, newOvertimeOnWeek.getHour(i));
                    if(newOvertimeOnWeekConfirmed != null)
                        overtimeOnWeekConfirmed.setHour(i, newOvertimeOnWeekConfirmed.getHour(i));
                }

                getTable().setRows(overtimeList);

                if (overtimeList.size() > 0) {
                    setVisible(true);
                } else {
                    setVisible(false);
                }

                fireTableChanged(TableListener.EVENT_ROW_COUNT_CHANGED);
                fireTableChanged(TableListener.EVENT_SUM_DATA_CHANGED);
            }
        }
    }

    private void clearOvertimeSum(){
        for (int i = DtoHoursOnWeek.SATURDAY; i <= DtoHoursOnWeek.SUMMARY; i++) {

            leaveOnWeekConfirmed.setHour(i, new BigDecimal(0));
            leaveOnWeek.setHour(i, new BigDecimal(0));
            overtimeOnWeek.setHour(i, new BigDecimal(0));
            overtimeOnWeekConfirmed.setHour(i, new BigDecimal(0));
        }
        fireTableChanged(TableListener.EVENT_SUM_DATA_CHANGED);
    }



    /**
     * "行数量变化"事件
     * table的大小会随行数动态变化
     */
    TableListener tableListener = null;
    protected void fireTableChanged(String eventType) {
        if (tableListener != null) {
            tableListener.processTableChanged(eventType);
        }
    }

    public void addTableListeners(TableListener listener) {
        this.tableListener = listener;
    }

    //得到表的高度
    public int getPreferredTableHeight() {
        int actualHeight = getTable().getRowCount() * VWJTable.TABLE_ROW_HEIGHT + TableWeeklyReport.HEADER_HEIGHT-1;
        return actualHeight > getMinmizeTableHeight() ? actualHeight : getMinmizeTableHeight();
    }

    //得到表的最小高度
    int minTableHeight = 4; //最少可以显示的行数，默认可显示4行
    public int getMinmizeTableHeight() {
        int rowNum = getTable().getRowCount();
        if( rowNum > minTableHeight ){
            rowNum = minTableHeight;
        }
        return rowNum * VWJTable.TABLE_ROW_HEIGHT + TableWeeklyReport.HEADER_HEIGHT - 4;
    }

    //得到表的最大高度
    int maxTableHeight = 8; //最少可以显示的行数,默认可显示8行
    public int getMaxmizeTableHeight() {
        return maxTableHeight * VWJTable.TABLE_ROW_HEIGHT + TableWeeklyReport.HEADER_HEIGHT - 4;
    }

    public void setMaxTableHeight(int rowNum) {
        this.maxTableHeight = rowNum;
        this.setMaximumSize(new Dimension( -1, getMaxmizeTableHeight()));
    }

    public void setMinTableHeight(int rowNum) {
        this.minTableHeight = rowNum;
        this.setMinimumSize(new Dimension( -1, getMinmizeTableHeight()));
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwOvertimeList();

        w1.addTab("Overtime", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

    public DtoHoursOnWeek getLeaveOnWeek() {
        return leaveOnWeek;
    }

    public DtoHoursOnWeek getLeaveOnWeekConfirmed() {
        return leaveOnWeekConfirmed;
    }

    public DtoHoursOnWeek getOvertimeOnWeek() {
        return overtimeOnWeek;
    }

    public DtoHoursOnWeek getOvertimeOnWeekConfirmed() {
        return overtimeOnWeekConfirmed;
    }

}
