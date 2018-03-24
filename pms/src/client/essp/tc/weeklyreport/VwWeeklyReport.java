package client.essp.tc.weeklyreport;

import java.awt.Dimension;
import java.util.Date;

import c2s.essp.tc.weeklyreport.DtoTcKey;
import client.essp.common.util.TestPanelParam;
import client.essp.common.view.VWWorkArea;
import client.essp.tc.common.TableListener;
import client.essp.tc.common.TcLineBorder;
import client.framework.common.Global;
import com.wits.util.Parameter;
import c2s.essp.common.calendar.WorkCalendarConstant;
import java.util.Calendar;
import c2s.essp.common.calendar.WrokCalendarFactory;
import c2s.essp.common.calendar.WorkCalendar;
import com.wits.util.comDate;
import c2s.essp.pwm.workbench.DtoKey;

/**个人填写周报的画面*/
public class VwWeeklyReport extends VwWeeklyReportBase {

    //VwPeriod vwPeriod = null;
    VwAcntList vwAcntList = null;
    VwMessage vwMessage = null;
    boolean showMessage = false;

    WorkCalendar workCalendar = null;

    //parameter
    private Date beginPeriod = null;
    private Date endPeriod = null;
    protected String userId = null;

    public VwWeeklyReport() {
        this(true);
    }

    public VwWeeklyReport(boolean showMessage) {
        try {
            this.showMessage = showMessage;
            preInit();
            jbInit();

            initUI();
            userId = Global.userId;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void preInit() {
        this.vwWeeklyReportList = new VwWeeklyReportList();
        this.workCalendar = WrokCalendarFactory.clientCreate();
    }

    //Component initialization
    protected void jbInit() throws Exception {
        //period面板在最上面,所以要在super.jbInit()之前加入
        //vwPeriod = new VwPeriod();
        //incrementPanel.add(vwPeriod);

        super.jbInit();

        //account list面板在最下面,所以要在super.jbInit()之后加入
        vwAcntList = new VwAcntList();
        incrementPanel.add(vwAcntList);

        if( showMessage ){
            vwMessage = new VwMessage();
            incrementPanel.add(vwMessage);
        }

        //设置overtime表格的滚动条的边框
        TcLineBorder tableBorder = (TcLineBorder)vwOvertimeList.getTable().getScrollPane().getBorder();
        tableBorder.setShowBottom(false);
    }

    protected void initUI(){
        super.initUI();

        vwAcntList.addTableListeners(new TableListener() {
            public void processTableChanged(String eventType) {
                if (TableListener.EVENT_ROW_COUNT_CHANGED.equals(eventType)) {

                    //当weeklyreport表的行数变化时 ，调整panel -- vwAcntList的高度
                    vwAcntList.setPreferredSize(new Dimension(
                            -1, vwAcntList.getPreferredTableHeight())
                            );

                    incrementPanel.repaint();
                }
            }
        });

        final VMTableWeeklyReport vmTableWeeklyReport = (VMTableWeeklyReport) vwWeeklyReportList.getTable().getModel();
        final VMTableSum vmTableSum = (VMTableSum) vwSumList.getTable().getModel();
        vmTableWeeklyReport.addTableListeners(new TableListener() {
            public void processTableChanged(String eventType) {
                if (TableListener.EVENT_REFRESH_ACTION.equals(eventType)) {

                    //当weeklyReportList刷新时，同时刷新acntList
                    vwAcntList.actionPerformedLoad();
                    vwOvertimeList.actionPerformedLoad();
                }
            }
        });

        //设置数据关联
        if( showMessage ){
            vwMessage.setAllocateHourSupply(vmTableSum);
            vwMessage.setPeriodBitmap(vmTableWeeklyReport.getPeriodBitmap());
            vwMessage.setWorkDayBitmap(vwWeeklyReportList.getWorkDayBitmap());
            vwWeeklyReportList.setCheckMessage(vwMessage.getCheckMessage());
            vwWeeklyReportList.setErrorMessage(vwMessage.getErrorMsgs());
            vmTableWeeklyReport.addTableListeners(new TableListener() {
                public void processTableChanged(String eventType) {
                    if (TableListener.EVENT_SUM_DATA_CHANGED.equals(eventType)) {
                        //刷新message的显示
                        vwMessage.refresh();
                    }
                }
            });
        }

        //接受拖放事件
        (new WeeklyReportDropTarget(vwWeeklyReportList)).create();
    }

    public void setParameter(Parameter param) {
//        Parameter param = new Parameter();
        Date theDate = (Date) param.get(DtoKey.SELECTED_DATE);
        if (theDate != null) {
            Calendar theCal = Calendar.getInstance();
            theCal.setTime(theDate);

            Calendar beginCal = workCalendar.beginDayOfWeek(theCal, WorkCalendarConstant.SPLIT_WEEK_BY_MONTH_END);
            beginCal.set(Calendar.HOUR_OF_DAY, 0);
            beginCal.set(Calendar.MINUTE, 0);
            beginCal.set(Calendar.SECOND, 0);
            beginCal.set(Calendar.MILLISECOND, 0);
            Calendar endCal = workCalendar.endDayOfWeek(theCal, WorkCalendarConstant.SPLIT_WEEK_BY_MONTH_END);
            endCal.set(Calendar.HOUR_OF_DAY, 0);
            endCal.set(Calendar.MINUTE, 0);
            endCal.set(Calendar.SECOND, 0);
            endCal.set(Calendar.MILLISECOND, 0);

            Date newBeginDate = beginCal.getTime();
            Date newEndDate = endCal.getTime();

            if (beginPeriod == null || newBeginDate == null || endPeriod == null || newEndDate == null ||
                    comDate.compareDateByYMD(this.beginPeriod, newBeginDate) != 0
                || comDate.compareDateByYMD(this.endPeriod, newEndDate) != 0) {

                this.endPeriod = newEndDate;
                this.beginPeriod = newBeginDate;
                param.put(DtoTcKey.BEGIN_PERIOD, newBeginDate);
                param.put(DtoTcKey.END_PERIOD, newEndDate);
                super.setParameter(param);
            }
        }else{
            super.setParameter(param);
        }
    }

    protected void resetUI() {
//        this.vwPeriod.refresh();//for test

        //this.vwPeriod.setBeginPeriod(getBeginPeriod());
        //this.vwPeriod.setEndPeriod(getEndPeriod());
        //this.vwPeriod.setUserId(this.userId);

        super.resetUI();

        Parameter param = new Parameter();
        param.put(DtoTcKey.USER_ID,this.userId);
        param.put(DtoTcKey.BEGIN_PERIOD, getBeginPeriod());
        param.put(DtoTcKey.END_PERIOD, getEndPeriod());
        vwAcntList.setParameter(param);
        vwAcntList.resetUI();
    }

    protected Parameter createParameterForRefreshWorkArea() {
        Parameter param = super.createParameterForRefreshWorkArea();
        param.put(DtoTcKey.USER_ID, this.userId);
        return param;
    }

    public static void main(String args[]) {
        final VWWorkArea workArea = new VwWeeklyReport();
        VWWorkArea w1 = new VWWorkArea() {
            public void setParameter(Parameter p) {
                workArea.setParameter(p);
            }

            public void refreshWorkArea() {
                workArea.refreshWorkArea();
            }
        };

        w1.addTab("Weekly Report", workArea);
        TestPanelParam.show(w1);
        Parameter param = new Parameter();
        param.put(DtoTcKey.BEGIN_PERIOD, new Date(105, 11, 8));
        Date d2 = new Date(105, 11, 14);
        param.put(DtoTcKey.END_PERIOD, d2);

        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

}
