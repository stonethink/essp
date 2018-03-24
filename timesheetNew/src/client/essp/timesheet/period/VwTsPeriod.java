package client.essp.timesheet.period;

import java.awt.Rectangle;
import java.awt.event.*;
import java.util.*;

import javax.swing.SwingConstants;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.timesheet.approval.TsPeriodChangeListener;
import client.framework.common.Global;
import client.framework.view.vwcomp.VWJButton;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJDatePanel;
import client.framework.view.vwcomp.VWJLabel;
import com.wits.util.comDate;

/**
 * <p>Title: VwTsPeriod</p>
 *
 * <p>Description: 工时单区间选择控件</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwTsPeriod extends VWGeneralWorkArea {

    public final static int PERIOD_MODEL_ANY = 0;
    public final static int PERIOD_MODEL_TS = 1;
    public final static int PERIOD_MODEL_MONTH = 2;

    private final static String actionId_LoadPeriod = "FTSLoadPeriod";
    private final static String actionId_MovePeriod = "FTSMovePeriod";

    private List<TsPeriodChangeListener> tsChangeListeners = new ArrayList();
    private int periodMode = PERIOD_MODEL_ANY;
    private boolean multiFlag = false;

    protected Long tsId = null;

    protected Date theDate = null;
    protected boolean locked = false;
    private boolean modelChanged = false;

    protected Date oldBeginDate;
    protected Date oldEndDate;

    protected VWJDatePanel dteBegin = new VWJDatePanel();
    protected VWJDatePanel dteEnd = new VWJDatePanel();
    protected VWJLabel lblAnd = new VWJLabel();

    protected VWJButton btnLater = new VWJButton();
    protected VWJButton btnEarly = new VWJButton();
    VWJLabel lblPeriod = new VWJLabel();

    public VwTsPeriod() {
        try {
            jbInit();
        } catch (Exception ex) {
            log.error(ex);
        }
        resetButtonStatus();
        addUICEvent();
    }

    private void initData() {
       oldBeginDate = comDate.toDate(comDate.dateToString(Global.todayDate));
       setData(addPeriod(getTsNextPeriod(oldBeginDate, 
    		   DtoTimeSheetPeriod.MOVE_FLAG_PRE), getTsPeriod(oldBeginDate)), true);
    }
    /**
     * 合并两个工时单区间
     * @param earlyPeriod
     * @param laterPeriod
     * @return
     */
    private DtoTimeSheetPeriod addPeriod(DtoTimeSheetPeriod earlyPeriod, 
    		                             DtoTimeSheetPeriod laterPeriod) {
    	DtoTimeSheetPeriod period = new DtoTimeSheetPeriod();
    	period.setBeginDate(earlyPeriod.getBeginDate());
    	period.setEndDate(laterPeriod.getEndDate());
    	return period;
    	
    }
    /**
     * 初始化UI
     * @throws Exception
     */
    private void jbInit() throws Exception {
        this.setLayout(null);

        dteBegin.setCanSelect(true);
        dteEnd.setCanSelect(true);

        lblAnd.setText("~");
        lblAnd.setHorizontalAlignment(SwingConstants.CENTER);

        btnLater.setText(">");
        btnEarly.setText("<");

        dteBegin.setBounds(new Rectangle(66, 2, 100, 20));
        dteEnd.setBounds(new Rectangle(211, 2, 100, 20));

        btnEarly.setBounds(new Rectangle(166, 2, 15, 20));
        btnLater.setBounds(new Rectangle(196, 2, 15, 20));

        lblAnd.setBounds(new Rectangle(181, 11, 15, 10));
        lblPeriod.setText("rsid.timesheet.period");
        lblPeriod.setBounds(new Rectangle(10, 2, 53, 20));

        this.add(lblAnd);
        this.add(dteBegin);
        this.add(dteEnd);
        this.add(btnEarly);
        this.add(btnLater);
        this.add(lblPeriod);
    }

    /**
     * 事件处理
     */
    private void addUICEvent() {
        dteBegin.getDateComp().addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    setTheDate(getBeginDate(), true);
                }
            }
        });

        dteBegin.getDateComp().addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                setTheDate(getBeginDate(), true);
            }
        });

        dteEnd.getDateComp().addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    setTheDate(getEndDate(), false);
                }
            }
        });

        dteEnd.getDateComp().addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                setTheDate(getEndDate(), false);
            }
        });

        btnEarly.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                early();
            }
        });

        btnLater.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                later();
            }
        });
    }

    /**
     * @todo: 在子类中重载这个方法。
     */
    protected void resetUI() {
        initData();
    }


    /**
     * 设置当前区间为theDate所在的TS Period
     * @param theDate Date
     * @param type String
     */
    public void setTheDate(Date theDate, boolean beginFlag) {
        if (theDate == null || this.getPeriodMode() == PERIOD_MODEL_ANY) {
            return;
        }
        if (!modelChanged && (oldBeginDate != null && oldEndDate != null &&
        		(beginFlag && theDate.compareTo(oldBeginDate) == 0 )||
            (!beginFlag && theDate.compareTo(oldEndDate) == 0))) {
            return;
        }
        DtoTimeSheetPeriod period = null;

        switch (this.getPeriodMode()) {
        case PERIOD_MODEL_ANY:
        	period = new DtoTimeSheetPeriod();
            period.setBeginDate((Date)dteBegin.getUICValue());
            period.setEndDate((Date)dteEnd.getUICValue());
            fireTsChanged(period);
            return;
        case PERIOD_MODEL_TS:
            period = getTsPeriod(theDate);
            break;
        case PERIOD_MODEL_MONTH:
            period = getMonthPeriod(theDate);
            break;
        default:
            return;
        }

        setData(period, beginFlag);
    }

    /**
     * 根据传入日期获取工时单周期
     * @param theDate Date
     * @return DtoTimeSheetPeriod
     */
    private DtoTimeSheetPeriod getTsPeriod(Date theDate) {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_LoadPeriod);
        inputInfo.setInputObj(DtoTimeSheetPeriod.DTO_DAY, theDate);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        DtoTimeSheetPeriod period = null;
        if (returnInfo.isError() == false) {
            period = (DtoTimeSheetPeriod) returnInfo.getReturnObj(
                    DtoTimeSheetPeriod.DTO_PERIOD);
        }
        return period;
    }

    private DtoTimeSheetPeriod getMonthPeriod(Date theDate) {
        DtoTimeSheetPeriod period = new DtoTimeSheetPeriod();
        Date[] dates = TimesheetCalendar.getBEMonthDay(theDate);
        period.setBeginDate(dates[0]);
        period.setEndDate(dates[1]);
        return period;
    }

    /**
     * 设置当前区间为上一个TS Period
     */
    private void early() {
        Date date = getBeginDate();
        if (date == null) {
            return;
        }

        DtoTimeSheetPeriod period = null;

        switch (this.getPeriodMode()) {
        case PERIOD_MODEL_ANY:
        	period = new DtoTimeSheetPeriod();
            period.setBeginDate((Date)dteBegin.getUICValue());
            period.setEndDate((Date)dteEnd.getUICValue());
            fireTsChanged(period);
            return;
        case PERIOD_MODEL_TS:
            period = getTsNextPeriod(date, DtoTimeSheetPeriod.MOVE_FLAG_PRE);
            break;
        case PERIOD_MODEL_MONTH:
            period = getMonthNextPeriod(date, -1);
            break;
        default:
            return;
        }

        setData(period, true);
        this.refreshWorkArea();
    }


    private DtoTimeSheetPeriod getTsNextPeriod(Date date, String moveFlag) {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_MovePeriod);
        inputInfo.setInputObj(DtoTimeSheetPeriod.DTO_DAY, date);
        inputInfo.setInputObj(DtoTimeSheetPeriod.DTO_MOVE_FLAG, moveFlag);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if (returnInfo.isError() == false) {
            return (DtoTimeSheetPeriod) returnInfo.getReturnObj(
                    DtoTimeSheetPeriod.DTO_PERIOD);
        }
        return null;
    }

    private DtoTimeSheetPeriod getMonthNextPeriod(Date date, int iStep) {
        DtoTimeSheetPeriod period = new DtoTimeSheetPeriod();
        Date[] dates = TimesheetCalendar.getNextBEMonthDay(date, iStep);
        period.setBeginDate(dates[0]);
        period.setEndDate(dates[1]);
        return period;
    }


    /**
     * 设置当前区间为下一个TS Period
     */
    private void later() {
        Date date = getEndDate();
        if (date == null) {
            return;
        }

        DtoTimeSheetPeriod period = null;

        switch (this.getPeriodMode()) {
        case PERIOD_MODEL_ANY:
        	period = new DtoTimeSheetPeriod();
            period.setBeginDate((Date)dteBegin.getUICValue());
            period.setEndDate((Date)dteEnd.getUICValue());
            fireTsChanged(period);
            return;
        case PERIOD_MODEL_TS:
            period = getTsNextPeriod(date, DtoTimeSheetPeriod.MOVE_FLAG_NEXT);
            break;
        case PERIOD_MODEL_MONTH:
            period = getMonthNextPeriod(date, 1);
            break;
        default:
            return;
        }

        setData(period, false);
        this.refreshWorkArea();
    }


    private void setData(DtoTimeSheetPeriod period, boolean beginFlag) {
        if (period == null) {
            if(this.hasRelation()) {
                period = new DtoTimeSheetPeriod();
            } else {
                return;
            }
        }
        if (this.hasRelation() == false) {
            if (beginFlag) {
                if(oldEndDate != null) {
                    period.setEndDate(oldEndDate);
                }
            } else if(oldBeginDate != null) {
                period.setBeginDate(oldBeginDate);
            }
        }
        validateData(period, beginFlag);
        this.dteBegin.setUICValue(period.getBeginDate());
        oldBeginDate = period.getBeginDate();
        this.dteEnd.setUICValue(period.getEndDate());
        oldEndDate = period.getEndDate();
        tsId = period.getTsId();
        fireTsChanged(period);
    }

    private void validateData(DtoTimeSheetPeriod period, boolean beginFlag) {
        if(period.getBeginDate().after(period.getEndDate())) {
            if(beginFlag) {
                period.setEndDate(period.getBeginDate());
            } else {
                period.setBeginDate(period.getEndDate());
            }
        }

    }


    public void addTsChangeListener(TsPeriodChangeListener l) {
        tsChangeListeners.add(l);
    }

    public void fireTsChanged(DtoTimeSheetPeriod period) {
        this.dteBegin.setUICValue(period.getBeginDate());
        this.dteEnd.setUICValue(period.getEndDate());
        oldBeginDate = period.getBeginDate();
        oldEndDate = period.getEndDate();

        for (TsPeriodChangeListener l : tsChangeListeners) {
            l.performePeriodChanged(period);
        }
    }

    public Date getBeginDate() {
        return (Date)this.dteBegin.getUICValue();
    }

    public Date getEndDate() {
        return (Date)this.dteEnd.getUICValue();
    }

    public Long getTsId() {
        return tsId;
    }

    public void setPeriodMode(int periodMode) {
        this.periodMode = periodMode;
        resetButtonStatus();
    }

    public void setMultiFlag(boolean multiFlag) {
        this.multiFlag = multiFlag;
        resetButtonStatus();
    }

    /**
     * 根据类型选择前翻、后翻的Button是否可见
     */
    private void resetButtonStatus() {
        if (this.hasRelation()) {
            btnEarly.setVisible(true);
            btnLater.setVisible(true);
            btnLater.setText(">");
            btnEarly.setText("<");
            modelChanged = true;
            setTheDate(oldBeginDate, true);
            modelChanged = false;
        } else {
            btnEarly.setVisible(false);
            btnLater.setVisible(false);
            setTheDate(oldBeginDate, true);
            setTheDate(oldEndDate, false);
        }
    }

    private boolean hasRelation() {
        return isMultiFlag() == false && getPeriodMode() != PERIOD_MODEL_ANY;
    }

    public int getPeriodMode() {
        return periodMode;
    }

    public boolean isMultiFlag() {
        return multiFlag;
    }

    public void setLableVisiable(boolean visiable) {
        this.lblPeriod.setVisible(visiable);
    }
}
