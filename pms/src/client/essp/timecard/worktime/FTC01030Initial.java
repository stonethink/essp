package client.essp.timecard.worktime;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.timecard.worktime.DtoTcPeriod;
import c2s.essp.timecard.worktime.DtoTcWorktime;

import client.framework.common.Constant;
import client.framework.view.common.comMSG;
import client.framework.view.event.DataChangedListener;
import client.net.ConnectFactory;
import client.net.NetConnector;

import com.essp.calendar.WorkDay;
import com.wits.util.comDate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.util.ArrayList;
import java.util.Calendar;


public class FTC01030Initial extends FTC01030InitialBase {
    private String                 actionId    = "FTC01030S_SetWorkTime";
    private boolean                refreshFlag = false;
    private java.text.NumberFormat nf          = java.text.NumberFormat
                                                 .getIntegerInstance();
    private boolean                isPrompt   = false;
    private boolean                isAlwaysOk = false;

    public FTC01030Initial() {
        super();

        nf.setMaximumIntegerDigits(2);
        nf.setMinimumIntegerDigits(2);

        addUIEvent();
        calendarSelectedChange();
    }

    private void addUIEvent() {
        calendar.addDataChangedListener(new DataChangedListener() {
                public void processDataChanged() {
                    calendarSelectedChange();
                }
            });
        txtStart[0].addFocusListener(new FocusListener() {
                public void focusLost(FocusEvent e) {
                    startTimeLostFocus(0);
                }

                public void focusGained(FocusEvent e) {
                    startTimeGainedFocus(0);
                }
            });
        txtStart[1].addFocusListener(new FocusListener() {
                public void focusLost(FocusEvent e) {
                    startTimeLostFocus(1);
                }

                public void focusGained(FocusEvent e) {
                    startTimeGainedFocus(1);
                }
            });
        txtStart[2].addFocusListener(new FocusListener() {
                public void focusLost(FocusEvent e) {
                    startTimeLostFocus(2);
                }

                public void focusGained(FocusEvent e) {
                    startTimeGainedFocus(2);
                }
            });
        txtStart[3].addFocusListener(new FocusListener() {
                public void focusLost(FocusEvent e) {
                    startTimeLostFocus(3);
                }

                public void focusGained(FocusEvent e) {
                    startTimeGainedFocus(3);
                }
            });
        txtStart[4].addFocusListener(new FocusListener() {
                public void focusLost(FocusEvent e) {
                    startTimeLostFocus(4);
                }

                public void focusGained(FocusEvent e) {
                    startTimeGainedFocus(4);
                }
            });
        txtEnd[0].addFocusListener(new FocusListener() {
                public void focusLost(FocusEvent e) {
                    endTimeLostFocus(0);
                }

                public void focusGained(FocusEvent e) {
                    endTimeGainedFocus(0);
                }
            });
        txtEnd[1].addFocusListener(new FocusListener() {
                public void focusLost(FocusEvent e) {
                    endTimeLostFocus(1);
                }

                public void focusGained(FocusEvent e) {
                    endTimeGainedFocus(1);
                }
            });
        txtEnd[2].addFocusListener(new FocusListener() {
                public void focusLost(FocusEvent e) {
                    endTimeLostFocus(2);
                }

                public void focusGained(FocusEvent e) {
                    endTimeGainedFocus(2);
                }
            });
        txtEnd[3].addFocusListener(new FocusListener() {
                public void focusLost(FocusEvent e) {
                    endTimeLostFocus(3);
                }

                public void focusGained(FocusEvent e) {
                    endTimeGainedFocus(3);
                }
            });
        txtEnd[4].addFocusListener(new FocusListener() {
                public void focusLost(FocusEvent e) {
                    endTimeLostFocus(4);
                }

                public void focusGained(FocusEvent e) {
                    endTimeGainedFocus(4);
                }
            });

        this.getButtonPanel().addSaveButton(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actionPerformedSave(e);
                }
            });
        this.getButtonPanel().addLoadButton(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actionPerformedLoad(e);
                }
            });
        rdbWorkDay.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setToWorkDay();
                }
            });
        rdbHoliday.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setToHoliday();
                }
            });
        rdbDefault.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setToDefault();
                }
            });
    }

    //设置为工作日
    private void setToWorkDay() {
        rdbDefault.setSelected(false);
        rdbHoliday.setSelected(false);
        rdbWorkDay.setSelected(true);
        calendar.setWorkDay(WorkDay.WORKDAY);
    }

    //设置为非工作日
    private void setToHoliday() {
        rdbDefault.setSelected(false);
        rdbHoliday.setSelected(true);
        rdbWorkDay.setSelected(false);
        calendar.setWorkDay(WorkDay.HOLIDAY);
    }

    //设置为默认值
    private void setToDefault() {
        rdbDefault.setSelected(true);
        rdbHoliday.setSelected(false);
        rdbWorkDay.setSelected(false);
        calendar.setWorkDay(WorkDay.DEFAULT_SETTINGS);
    }

    public static String formatTimeStr(String oldStr) {
        String value = oldStr;

        if (value.equals("")) {
            value = "HH:MM";
        } else {
            String[] hhmm = value.split(":");
            String   hh = hhmm[0].trim();
            String   mm = "";

            try {
                mm = hhmm[1].trim();
            } catch (Exception ex) {
                mm = "00";
            }

            if (hh.length() < 2) {
                hh = "0" + hh;
            }

            if (mm.length() == 1) {
                mm = "0" + mm;
            }

            if (mm.length() == 0) {
                mm = "00" + mm;
            }

            value = hh + ":" + mm;
        }

        return value;
    }

    //开始时间获得焦点
    private void startTimeGainedFocus(int count) {
        if (txtStart[count].getText().trim().equals("HH:MM")) {
            txtStart[count].setText("");
        }
    }

    //开始时间设置失去焦点
    private void startTimeLostFocus(int count) {
        String value = txtStart[count].getText().trim();

        //        txtStart[count].setText(comDate.convertNumToTime(value));
        value = formatTimeStr(value);

        txtStart[count].setText(value);
    }

    //结束时间获得焦点
    private void endTimeGainedFocus(int count) {
        if (txtEnd[count].getText().equals("HH:MM")) {
            txtEnd[count].setText("");
        }
    }

    //结束时间设置失去焦点
    private void endTimeLostFocus(int count) {
        String value = txtEnd[count].getText();

        //        txtEnd[count].setText(comDate.convertNumToTime(value));
        value = formatTimeStr(value);

        txtEnd[count].setText(value);
    }

    //刷新显示
    private void actionPerformedLoad(ActionEvent e) {
        saveWorkArea();
        this.refreshFlag = true;
        refreshWorkArea();
    }

    public void refreshWorkArea() {
        if (refreshFlag == true) {
            refreshFlag = false;

            calendar.refreshSettings();

            TransactionData transData  = new TransactionData();
            ReturnInfo      returnInfo = prepareData(transData);

            if (returnInfo != null) {
                DtoTcPeriod dtoTcPeriod = (DtoTcPeriod) returnInfo.getReturnObj("DtoTcPeriod");
                cbxMonthStart.setSelectedIndex(dtoTcPeriod.getMonthID()
                                                          .intValue() - 1);
                cbxWeekStart.setSelectedIndex(dtoTcPeriod.getWeekID().intValue()
                                              - 1);

                for (int i = 0; i < PERIODROWSIZE; i++) {
                    txtStart[i].setText("HH:MM");
                    txtEnd[i].setText("HH:MM");
                }

                ArrayList alDtoTcWorktime = (ArrayList) returnInfo.getReturnObj("DtoTcWorktimeList");
                int       rowSize = alDtoTcWorktime.size();

                for (int i = 0; i < rowSize; i++) {
                    DtoTcWorktime dtoTcWorktime = (DtoTcWorktime) alDtoTcWorktime
                                                  .get(i);
                    txtStart[i].setText(dtoTcWorktime.getWtStarttime());
                    txtEnd[i].setText(dtoTcWorktime.getWtEndtime());
                }

                setFieldNormal();
            }
        }
    }

    //根据条件，抽出数据
    private ReturnInfo prepareData(TransactionData transData) {
        log.debug("prepare data");

        String funId = "get";

        //提交信息（条件）
        InputInfo inputInfo = transData.getInputInfo();
        inputInfo.setActionId(actionId);
        inputInfo.setFunId(funId);

        //连接servlet，执行操作
        NetConnector connector = ConnectFactory.createConnector();
        connector.accessData(transData);

        //返回信息（结果）
        ReturnInfo returnInfo = transData.getReturnInfo();

        if (returnInfo.isError() == false) {
            log.debug("Getting Settings Successfully");

            return returnInfo;
        } else {
            comMSG.dispErrorDialog(returnInfo.getReturnMessage());

            return null;
        }
    }

    //这里将数据保存到数据库中去
    private void actionPerformedSave(ActionEvent e) {
        if (checkInput()) {
            setFieldNormal();
            calendar.saveSettings();
            saveData();
        }
    }

    public void saveWorkArea() {
        if (this.isPrompt == false) {
            int option = comMSG.dispConfirmDialogByPrompt("Save Changes?");

            if (option == Constant.ALWAYS_OK) {
                this.isPrompt   = true;
                this.isAlwaysOk = true;
            } else if (option == Constant.ALWAYS_CANCEL) {
                this.isPrompt   = true;
                this.isAlwaysOk = false;
            } else {
                this.isPrompt   = false;
                this.isAlwaysOk = false;
            }

            if ((option == Constant.ALWAYS_OK) || (option == Constant.OK)) {
                if (checkInput()) {
                    calendar.saveSettings();
                    saveData();
                } else {
                    return;
                }
            }
        } else {
            if (this.isAlwaysOk == true) {
                if (checkInput()) {
                    calendar.saveSettings();
                    saveData();
                } else {
                    return;
                }
            }
        }
    }

    private void saveData() {
        log.debug("save data");

        String funId = "save";

        //提交信息（条件）
        TransactionData transData = new TransactionData();
        InputInfo       inputInfo = transData.getInputInfo();
        inputInfo.setActionId(actionId);
        inputInfo.setFunId(funId);

        DtoTcPeriod dtoTcPeriod = new DtoTcPeriod();
        dtoTcPeriod.setMonthID(new Integer(cbxMonthStart.getSelectedIndex() + 1));
        dtoTcPeriod.setWeekID(new Integer(cbxWeekStart.getSelectedIndex() + 1));
        inputInfo.setInputObj("DtoTcPeriod", dtoTcPeriod);

        ArrayList alDtoTcWorktime = new ArrayList();

        for (int i = 0; i < PERIODROWSIZE; i++) {
            if ((txtStart[i].getText().equals("HH:MM") == false)
                    && (txtEnd[i].getText().equals("HH:MM") == false)) {
                DtoTcWorktime dtoTcWorktime = new DtoTcWorktime();
                dtoTcWorktime.setWtStarttime(txtStart[i].getText());
                dtoTcWorktime.setWtEndtime(txtEnd[i].getText());
                alDtoTcWorktime.add(dtoTcWorktime);
            }
        }

        inputInfo.setInputObj("DtoTcWorktimeList", alDtoTcWorktime);

        //连接servlet，执行操作
        NetConnector connector = ConnectFactory.createConnector();
        connector.accessData(transData);

        //返回信息（结果）
        ReturnInfo returnInfo = transData.getReturnInfo();

        if (returnInfo.isError() == false) {
            log.debug("Save Settings Successfully");
        } else {
            comMSG.dispErrorDialog(returnInfo.getReturnMessage());
        }
    }

    private boolean checkInput() {
        for (int i = 0; i < PERIODROWSIZE; i++) {
            String start   = txtStart[i].getText();
            String end     = txtEnd[i].getText();
            String lastEnd = "HH:MM";
            int    currRow = i;

            while (currRow > 0) {
                lastEnd = txtEnd[currRow - 1].getText();

                if (lastEnd.equals("HH:MM") == false) {
                    break;
                }

                currRow--;
            }

            if ((start.equals("HH:MM") == true)
                    && (end.equals("HH:MM") == false)) {
                comMSG.dispErrorDialog("Ending Without Beginnig!");
                txtStart[i].setErrorField(true);
                txtStart[i].requestFocus();

                return false;
            } else if ((start.equals("HH:MM") == false)
                           && (end.equals("HH:MM") == true)) {
                comMSG.dispErrorDialog("Beginning Without Ending!");
                txtEnd[i].setErrorField(true);
                txtEnd[i].requestFocus();

                return false;
            } else if ((start.equals("HH:MM") == false)
                           && (end.equals("HH:MM") == false)) {
                if (comDate.checkTimeHM(start, "HH:mm") == false) {
                    comMSG.dispErrorDialog("Input Error!");
                    txtStart[i].setErrorField(true);
                    txtStart[i].requestFocus();

                    return false;
                } else if (comDate.checkTimeHM(end, "HH:mm") == false) {
                    comMSG.dispErrorDialog("Input Error!");
                    txtEnd[i].setErrorField(true);
                    txtEnd[i].requestFocus();

                    return false;
                } else if ((lastEnd.equals("HH:MM") == false)
                               && (start.compareTo(lastEnd) < 0)) {
                    comMSG.dispErrorDialog("There is Superposition Between Working-Periods!");
                    txtStart[i].setErrorField(true);
                    txtStart[i].requestFocus();

                    return false;
                } else if (start.compareTo(end) > 0) {
                    comMSG.dispErrorDialog("End Time is After the Start!");
                    txtEnd[i].setErrorField(true);
                    txtEnd[i].requestFocus();

                    return false;
                }
            }
        }

        return true;
    }

    private void setFieldNormal() {
        for (int i = 0; i < PERIODROWSIZE; i++) {
            txtStart[i].setErrorField(false);
            txtEnd[i].setErrorField(false);
        }
    }

    /**
     * 当日历面板上的选择的日期发生变化时，激发此方法
     * @param e <code>PropertyChangeEvent</code>
     */
    private void calendarSelectedChange() {
        boolean   isAllWorkday = true;
        boolean   isAllHoliday = true;

        ArrayList alCalendar = calendar.getSelectCalendars();
        WorkDay[] workday    = calendar.getWorkDay();

        if (alCalendar.size() != 0) {
            for (int i = 0; i < alCalendar.size(); i++) {
                int iDay = ((Calendar) (alCalendar.get(i))).get(Calendar.DAY_OF_YEAR)
                           - 1;
                isAllWorkday = isAllWorkday && (workday[iDay].isWorkDay());
                isAllHoliday = isAllHoliday
                               && (workday[iDay].isWorkDay() == false);
            }
        }

        if (isAllWorkday) {
            rdbWorkDay.setSelected(true);
            rdbHoliday.setSelected(false);
            rdbDefault.setSelected(false);
        } else if (isAllHoliday) {
            rdbWorkDay.setSelected(false);
            rdbHoliday.setSelected(true);
            rdbDefault.setSelected(false);
        } else {
            rdbWorkDay.setSelected(false);
            rdbHoliday.setSelected(false);
            rdbDefault.setSelected(false);
        }
    }

    public void setRefreshFlag(boolean refreshFlag) {
        this.refreshFlag = refreshFlag;
    }
}
