package client.essp.timecard.timecard.weeklyreport;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.timecard.timecard.DtoQueryCondition;
import c2s.essp.timecard.timecard.DtoTcTimecard;

import client.essp.common.hrallocate.HrAllocate;
import client.essp.common.hrallocate.Sponsor;
import client.essp.common.view.VWTDWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMTable2;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;
import client.net.ConnectFactory;
import client.net.NetConnector;

import com.wits.util.Parameter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import client.framework.common.Global;


public class FTC01010PM extends VWTDWorkArea {
    private ArrayList            prjList         = null;
    private int                  prjID           = 807;
    private String               prjAccount      = "";
    private String               prjName         = "";
    private String               actionId        = "FTC01010S_ProjectTimeCard";
    private ReturnInfo           returnInfo      = new ReturnInfo();
    private int                  rowClicked      = 0;
    private int                  rowLastClicked  = 0;
    private String               submitStatus    = "1";
    private double               recWorkHours    = 0;
    private double               newRecWorkHours = 0;
    private boolean              refreshFlag     = false;
    private boolean              isInit          = true;
    private boolean              changedFlag     = false;
    private boolean              savedFlag       = false;
    private boolean              checkFlag       = true;
    private boolean              isPM            = true;
    private boolean              isPrompt        = false;
    private boolean              isAlwaysOk      = false;
    private VWWorkArea           topArea         = new VWWorkArea();
    private VWWorkArea           downArea        = new VWWorkArea();
    private FTC01010WeeklyReport weeklyReport    = new FTC01010WeeklyReport();
    private VWJTable             currTable; //当前表
    private PmTableModel         currTableModel; //当前表的tableModel
    private VWJLabel             lblPrjList      = new VWJLabel();
    private VWJComboBox          cbxPrjList      = new VWJComboBox();
    private VWJDate              txtStart        = new VWJDate();
    private VWJLabel             lblTo           = new VWJLabel();
    private VWJDate              txtFinish       = new VWJDate();
    private VWJLabel             lblRecWkHours   = new VWJLabel();
    private VWJReal              txtRecWkHours   = new VWJReal();
    private JButton              btLastPeriod    = new JButton();
    private JButton              btNextPeriod    = new JButton();
    private JButton              btSave;
    private JButton              btReload;
    private JButton              btAdd;
    private JButton              btDel;
    private FlowLayout           flowLayout      = new FlowLayout(FlowLayout.RIGHT,
                                                                  5, 5);
    private Font                 fontSongTi = new Font("宋体", Font.PLAIN, 12);
    private Font                 fontArial  = new Font("Arial", Font.PLAIN, 12);
    private String[][]           employees  = null;
    private Sponsor              sponsor    = new Sponsor() {
        public String[][] getOldData() {
            return new String[0][0];
        }

        public void setNewData(String[][] newData) {
            if ((newData != null) && (newData.length > 0)) {
                transactNewData(newData);
            }
        }
    };

    public FTC01010PM() {
        super(300);

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String array2DToString(Object[][] objectArray) {
        String result   = "";
        int    rowCount = objectArray.length;

        for (int i = 0; i < rowCount; i++) {
            Object[] row      = objectArray[i];
            int      colCount = row.length;
            result += ("\nRow " + (i + 1) + " : ");

            for (int j = 0; j < colCount; j++) {
                result += (row[j] + "\t");
            }
        }

        return result;
    }

    private void transactNewData(String[][] newData) {
        int newCount = newData.length;

        //取得新增的员工列表，从返回的列表中剔除现有的员工;
        int       oldCount        = employees.length;
        ArrayList newEmployeeList = new ArrayList();

        for (int i = 0; i < newCount; i++) {
            boolean isExist = false;

            for (int j = 0; j < oldCount; j++) {
                if (employees[j][0].equals(newData[i][0])) {
                    isExist = true;

                    break;
                }
            }

            if (isExist == false) {
                newEmployeeList.add(newData[i]);
            }
        }

        int addedCount = newEmployeeList.size();

        //新增的员工ID和Name列表
        String[][] addedEmpList = new String[addedCount][2];

        //新增的员工ID列表
        String[] addedEmpIdList = new String[addedCount];

        for (int i = 0; i < addedCount; i++) {
            addedEmpList[i]   = (String[]) newEmployeeList.get(i);
            addedEmpIdList[i] = (String) addedEmpList[i][0];
        }

        log.debug("\nAdded EmployeeList After Having Been Transacted is :"
                  + array2DToString(addedEmpList));

        addEmployees(addedEmpIdList);
    }

    private void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(800, 505));
        this.setMaximumSize(new Dimension(1024, 768));
        this.setMinimumSize(new Dimension(500, 200));

        btSave = this.getButtonPanel().addSaveButton(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actionPerformedSave(e);
                }
            });
        btReload = this.getButtonPanel().addLoadButton(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actionPerformedLoad(e);
                }
            });
        btAdd = this.getButtonPanel().addAddButton(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actionPerformedAdd(e);
                }
            });
        btDel = this.getButtonPanel().addDelButton(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actionPerformedDel(e);
                }
            });

        VWWorkArea fieldPanel = new VWWorkArea();
        fieldPanel.setBorder(null);

        VWWorkArea prjPanel = new VWWorkArea();
        prjPanel.setBorder(null);
        prjPanel.setLayout(null);

        lblPrjList.setText("Project");
        lblPrjList.setBounds(0, 5, 50, 20);
        prjPanel.add(lblPrjList);

        cbxPrjList.setBorder(BorderFactory.createLoweredBevelBorder());
        cbxPrjList.setToolTipText("Project Code - Project Name");
        cbxPrjList.setFont(fontSongTi);
        cbxPrjList.setBounds(50, 2, 220, 25);
        cbxPrjList.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    projectChanged(e);
                }
            });
        prjPanel.add(cbxPrjList);

        lblRecWkHours.setText("Recognized Work Hours");
        lblRecWkHours.setToolTipText("Project Recognized Work Hours");
        lblRecWkHours.setBounds(280, 5, 150, 20);
        prjPanel.add(lblRecWkHours);

        txtRecWkHours.setToolTipText("Project Recognized Work Hours");

        txtRecWkHours.setFont(fontArial);
        txtRecWkHours.setMaxInputDecimalDigit(2);
        txtRecWkHours.setMaxInputIntegerDigit(10);

        txtRecWkHours.setEnabled(true);
        txtRecWkHours.setBounds(426, 5, 75, 20);
        prjPanel.add(txtRecWkHours);

        prjPanel.setMinimumSize(new Dimension(530, 30));
        prjPanel.setMaximumSize(new Dimension(800, 30));
        prjPanel.setPreferredSize(new Dimension(730, 30));
        fieldPanel.add(prjPanel, BorderLayout.CENTER);

        VWWorkArea periodPanel = new VWWorkArea();
        periodPanel.setBorder(null);
        periodPanel.setLayout(flowLayout);

        btLastPeriod.setText("<");
        btLastPeriod.setPreferredSize(new Dimension(42, 20));
        btLastPeriod.setToolTipText("Last Week");
        btLastPeriod.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    getLastPeriod();
                }
            });
        periodPanel.add(btLastPeriod);

        txtStart.setEnabled(false);
        txtStart.setDataType(Constant.DATE_YYYYMMDD);
        txtStart.setToolTipText("Week Start");
        txtStart.setFont(fontArial);
        periodPanel.add(txtStart);

        lblTo.setText("To");
        lblTo.setHorizontalAlignment(SwingConstants.CENTER);
        periodPanel.add(lblTo);

        txtFinish.setEnabled(false);
        txtFinish.setDataType(Constant.DATE_YYYYMMDD);
        txtFinish.setToolTipText("Week End");
        txtFinish.setFont(fontArial);
        periodPanel.add(txtFinish);

        btNextPeriod.setText(">");
        btNextPeriod.setPreferredSize(new Dimension(42, 20));
        btNextPeriod.setToolTipText("Next Week");
        btNextPeriod.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    getNextPeriod();
                }
            });
        periodPanel.add(btNextPeriod);

        fieldPanel.add(periodPanel, BorderLayout.EAST);

        //        VMUI.configUI(fields, fieldPanel, vmui);
        currTableModel = new PmTableModel(DtoTcTimecard.class);
        currTable      = new VWJTable(currTableModel);
        FTC01010PMTableRender.setRender(currTable);

        //        currTable.setCellEditor(currEditor);
        currTable.getTableHeader().setReorderingAllowed(false);
        currTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        currTable.setRowHeight(18);
        currTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    currTableRowChanged(e);
                }
            });

        //        currTable.addMouseListener(new MouseAdapter() {
        //          public void mouseClicked(MouseEvent e) {
        //            currTable_RowChanged();
        //          }
        //        });
        //        editorText.setInputChar(VWJText.CH_ALL);
        //        currEditor.addCellEditorListener(new CellEditorListener() {
        //            public void editingStopped(ChangeEvent e) {
        //                computeForActualChanged();
        //            }
        //
        //            public void editingCanceled(ChangeEvent e) {
        //            }
        //        });
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setBorder(null);
        jScrollPane.getViewport().add(currTable, null); //给table加滚动条

        //         jScrollPane.setBounds(10,30,800,260);
        //        topArea.add(jScrollPane);
        topArea.add(fieldPanel, BorderLayout.NORTH);
        topArea.add(jScrollPane, BorderLayout.CENTER);

        this.setTopArea(topArea);
        downArea.addTab("Weekly Report", weeklyReport, false);
        this.setDownArea(downArea);
    }

    //上面的表格行选择发生变化
    private void currTableRowChanged(ListSelectionEvent e) {
        rowClicked = currTable.getSelectedRow();

        if (rowClicked >= 0) {
            java.util.List dtoTcTimecardList = currTableModel.getRows();
            int            rowCount = dtoTcTimecardList.size() - 1;

            if (currTable.getSelectedRow() != rowCount) {
                rowLastClicked = rowClicked;
                refreshDownArea();
            } else {
                currTable.setRowSelectionInterval(0, 0);
            }
        } else {
            rowClicked = 0;
        }
    }

    //点击上个工作区间按钮
    private void getLastPeriod() {
        saveWorkArea();

        if (checkFlag == false) {
            return;
        }

        Date     dStart  = (Date) txtStart.getUICValue();
        Date     dEnd    = (Date) txtFinish.getUICValue();
        Calendar caStart = Calendar.getInstance();
        Calendar caEnd   = Calendar.getInstance();
        caStart.setTime(dStart);
        caEnd.setTime(dEnd);
        caStart.add(Calendar.DAY_OF_YEAR, -7);
        caEnd.add(Calendar.DAY_OF_YEAR, -7);
        dStart = caStart.getTime();
        dEnd   = caEnd.getTime();
        txtStart.setUICValue(dStart);
        txtFinish.setUICValue(dEnd);
        this.refreshFlag = true;
        refreshWorkArea();
    }

    //点击下个工作区间按钮
    private void getNextPeriod() {
        saveWorkArea();

        if (checkFlag == false) {
            return;
        }

        Date     dStart  = (Date) txtStart.getUICValue();
        Date     dEnd    = (Date) txtFinish.getUICValue();
        Calendar caStart = Calendar.getInstance();
        Calendar caEnd   = Calendar.getInstance();
        caStart.setTime(dStart);
        caEnd.setTime(dEnd);
        caStart.add(Calendar.DAY_OF_YEAR, 7);
        caEnd.add(Calendar.DAY_OF_YEAR, 7);

        Calendar caToday = Calendar.getInstance();

        if (caStart.before(caToday)) {
            dStart = caStart.getTime();
            dEnd   = caEnd.getTime();
            txtStart.setUICValue(dStart);
            txtFinish.setUICValue(dEnd);
            this.refreshFlag = true;
            refreshWorkArea();
        } else {
            comMSG.dispErrorDialog("There is No Record Next Week!");
        }
    }

    //切换项目
    private void projectChanged(ItemEvent e) {
        if ((e.getStateChange() == ItemEvent.DESELECTED) && (isInit == false)) {
            saveWorkArea();

            if (checkFlag == false) {
                return;
            }

            int iSelectIndex = cbxPrjList.getSelectedIndex();
            prjID      = Integer.parseInt(((String[]) prjList.get(iSelectIndex))[0]);
            prjAccount = ((String[]) prjList.get(iSelectIndex))[1];
            prjName    = ((String[]) prjList.get(iSelectIndex))[2];
            isPM       = ((String[]) prjList.get(iSelectIndex))[3].equals("1")
                         ? true : false;
            this.cbxPrjList.setToolTipText(prjName);
            this.refreshFlag = true;
            refreshWorkArea();
        }
    }

    //根据获取的基本参数设置本卡片
    public void setParameter(Parameter param) {
        this.prjList = (ArrayList) param.get("prjList");

        for (int i = 0; i < prjList.size(); i++) {
            String[] project = (String[]) prjList.get(i);
            cbxPrjList.addItem(project[1] + " - " + project[2]);
        }

        try {
            prjID = Integer.parseInt(((String[]) prjList.get(0))[0]);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        prjAccount = ((String[]) prjList.get(0))[1];
        prjName    = ((String[]) prjList.get(0))[2];
        isPM       = ((String[]) prjList.get(0))[3].equals("1") ? true : false;
        this.refreshFlag = true;
    }

    //刷新显示
    private void actionPerformedLoad(ActionEvent e) {
        saveWorkArea();

        if (checkFlag == false) {
            return;
        }

        this.refreshFlag = true;
        refreshWorkArea();
    }

    //刷新当前卡片
    public void refreshWorkArea() {
        int rowCount = 0;

        if (refreshFlag == true) {
            refreshFlag = false;

            log.debug("prepareData()");
            prepareData();

            DtoQueryCondition dtoQueryCondition = (DtoQueryCondition) returnInfo
                                                  .getReturnObj("DtoQueryCondition");

            if (dtoQueryCondition != null) {
                //基本信息
                this.txtStart.setUICValue(dtoQueryCondition.getWeekStart());
                this.txtFinish.setUICValue(dtoQueryCondition.getWeekEnd());
                this.submitStatus = dtoQueryCondition.getSubmitStatus();
                this.txtRecWkHours.setUICValue(dtoQueryCondition
                                               .getRecWorkHours());
                recWorkHours = ((BigDecimal) this.txtRecWkHours.getUICValue())
                               .doubleValue();
                newRecWorkHours = recWorkHours;

                boolean isEditable = isPM && submitStatus.equals("0");
                btSave.setEnabled(isEditable);
                btAdd.setEnabled(isEditable);
                btDel.setEnabled(isEditable);
                txtRecWkHours.setEnabled(isEditable);
                currTableModel.setColumnEditable(2, new Boolean(isEditable));
                currTableModel.setColumnEditable(3, new Boolean(isEditable));
                currTableModel.setColumnEditable(5, new Boolean(isEditable));

                //Timecard表信息
                java.util.List dtoTcTimecardList = (java.util.List) returnInfo
                                                   .getReturnObj("DtoTcTimecardList");

                rowCount = dtoTcTimecardList.size();

                if (rowCount == 0) {
                    DtoTcTimecard totalDtoTcTimecard = new DtoTcTimecard();
                    totalDtoTcTimecard.setTmcPersonalWorkHours(new BigDecimal(0));
                    totalDtoTcTimecard.setTmcActualWorkHours(new BigDecimal(0));
                    totalDtoTcTimecard.setTmcAllocatedWorkHours(new BigDecimal(0));
                    totalDtoTcTimecard.setTmcAttenOffsetWork(new BigDecimal(0));
                    totalDtoTcTimecard.setTmcAttenOvertime(new BigDecimal(0));
                    totalDtoTcTimecard.setTmcAttenVacation(new BigDecimal(0));
                    totalDtoTcTimecard.setTmcAttenShiftAdjustment(new BigDecimal(0));
                    totalDtoTcTimecard.setTmcAttenPrivateLeave(new BigDecimal(0));
                    totalDtoTcTimecard.setTmcAttenSickLeave(new BigDecimal(0));
                    totalDtoTcTimecard.setTmcAttenAbsence(new BigDecimal(0));
                    totalDtoTcTimecard.setTmcAttenBreakingRules(new BigDecimal(0));
                    dtoTcTimecardList.add(totalDtoTcTimecard);
                    rowCount = 1;
                }

                log.debug("\nReturn Table Row Count is : " + rowCount);
                this.employees = new String[rowCount - 1][2];

                for (int i = 0; i < (rowCount - 1); i++) {
                    employees[i][0] = ((DtoTcTimecard) dtoTcTimecardList.get(i))
                                      .getTmcEmpId();
                    employees[i][1] = ((DtoTcTimecard) dtoTcTimecardList.get(i))
                                      .getTmcEmpName();
                    System.out.println("EmployeeList Row " + (i + 1) + " : "
                                       + employees[i][0] + "\t"
                                       + employees[i][1]);
                }

                //处理合计项
                DtoTcTimecard totalRow = (DtoTcTimecard) dtoTcTimecardList.get(rowCount
                                                                               - 1);
                totalRow.setTmcEmpName("Total");
                currTableModel.setRows(dtoTcTimecardList);
                currTableModel.fireTableDataChanged();

                if (rowCount > 1) {
                    currTable.setRowSelectionAllowed(true);
                    currTable.setRowSelectionInterval(rowClicked, rowClicked);
                } else {
                    currTable.setRowSelectionAllowed(false);
                }

                isInit = false;
                refreshDownArea();
                savedFlag = false;
                this.updateUI();
            }
        }
    }

    //根据条件，获取数据
    private void prepareData() {
        log.debug("prepare data");

        String          funId     = "get";
        TransactionData transData = new TransactionData();

        //提交信息（条件）
        InputInfo inputInfo = transData.getInputInfo();

        //        inputInfo.setControllerUrl(controllerUrl);
        inputInfo.setActionId(actionId);
        inputInfo.setFunId(funId);

        DtoQueryCondition dtoQueryCondition = new DtoQueryCondition();
        dtoQueryCondition.setInit(isInit);

        Date start  = Global.todayDate;
        Date finish = Global.todayDate;

        if ((txtStart.getUICValue() != null)
                && (txtStart.getUICValue().equals("") == false)) {
            start = (java.util.Date) txtStart.getUICValue();
        }

        if ((txtFinish.getUICValue() != null)
                && (txtFinish.getUICValue().equals("") == false)) {
            finish = (java.util.Date) txtFinish.getUICValue();
        }

        dtoQueryCondition.setWeekStart(start);
        dtoQueryCondition.setWeekEnd(finish);
        dtoQueryCondition.setProjectID(prjID);
        dtoQueryCondition.setPM(isPM);
        inputInfo.setInputObj("DtoQueryCondition", dtoQueryCondition);

        //连接servlet，执行操作
        NetConnector connector = ConnectFactory.createConnector();
        connector.accessData(transData);

        //返回信息（结果）
        ReturnInfo returnInfo = transData.getReturnInfo();

        if (returnInfo.isError() == false) {
            log.debug("Getting Settings Successfully");
            this.returnInfo = returnInfo;
        } else {
            log.error(returnInfo.getReturnMessage());
            this.returnInfo = null;
        }
    }

    private void changesJudge() {
        newRecWorkHours = txtRecWkHours.getValue();

        if ((newRecWorkHours != recWorkHours)
                || DtoUtil.checkChanged(currTableModel.getDtoList())) {
            changedFlag = true;
        }

        if (currTable.getCellEditor() != null) {
            currTable.getCellEditor().stopCellEditing();
            currTable.setRowSelectionInterval(rowLastClicked, rowLastClicked);
        }
    }

    //这里将数据保存到数据库中去
    private void actionPerformedSave(ActionEvent e) {
        changesJudge();
        checkInput();

        if (((changedFlag == true) || (savedFlag == false))
                && ((currTable.getRowCount() > 1) || (newRecWorkHours != 0))
                && (isPM == true) && submitStatus.equals("0") && checkFlag) {
            saveData();
            recWorkHours = newRecWorkHours;
            changedFlag  = false;
            savedFlag    = true;
        }
    }

    //保存当前卡片
    public void saveWorkArea() {
        changesJudge();

        if (((changedFlag == true) || (savedFlag == false))
                && ((currTable.getRowCount() > 1) || (newRecWorkHours != 0))
                && (isPM == true) && submitStatus.equals("0")) {
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
                    checkInput();
                    if(checkFlag){
                        saveData();
                        recWorkHours = newRecWorkHours;
                        changedFlag = false;
                        savedFlag = true;
                    }
                }
            } else {
                if (this.isAlwaysOk == true) {
                    checkInput();
                    if(checkFlag){
                        saveData();
                        recWorkHours = newRecWorkHours;
                        changedFlag = false;
                        savedFlag = true;
                    }
                }
            }
        }
    }

    //将数据送到服务端实现保存动作
    private void saveData() {
        log.debug("save data");

        String funId = "save";

        //提交信息（条件）
        TransactionData transData = new TransactionData();
        InputInfo       inputInfo = transData.getInputInfo();

        //        inputInfo.setControllerUrl(controllerUrl);
        inputInfo.setActionId(actionId);
        inputInfo.setFunId(funId);

        DtoQueryCondition dtoQueryCondition = new DtoQueryCondition();
        dtoQueryCondition.setWeekStart((java.util.Date) txtStart.getUICValue());
        dtoQueryCondition.setWeekEnd((java.util.Date) txtFinish.getUICValue());
        dtoQueryCondition.setRecWorkHours(new BigDecimal(newRecWorkHours));
        dtoQueryCondition.setSubmitStatus(submitStatus);
        dtoQueryCondition.setProjectID(prjID);
        inputInfo.setInputObj("DtoQueryCondition", dtoQueryCondition);
        inputInfo.setInputObj("DtoTcTimecardList", currTableModel.getDtoList());

        //连接servlet，执行操作
        NetConnector connector = ConnectFactory.createConnector();
        connector.accessData(transData);

        //返回信息（结果）
        ReturnInfo returnInfo = transData.getReturnInfo();

        if (returnInfo.isError() == false) {
            log.debug("Save Settings Successfully");
        } else {
            log.error(returnInfo.getReturnMessage());
        }
    }

    /**
     * @author Emil Lu
     * 提交前的输入检查
     * @return boolean
     */
    private void checkInput() {
        DtoQueryCondition dtoQueryCondition = (DtoQueryCondition) this.returnInfo
                                              .getReturnObj("DtoQueryCondition");
        java.util.List    dtoTcTimecardList = (java.util.List) this.returnInfo
                                              .getReturnObj("DtoTcTimecardList");

        Date       dWeekStart = dtoQueryCondition.getWeekStart();
        Date       dWeekEnd = dtoQueryCondition.getWeekEnd();

        DateFormat df        = new SimpleDateFormat("yyyy/MM/dd");
        String     weekStart = df.format(dWeekStart);
        String     weekEnd   = df.format(dWeekEnd);

        int        rowCount = dtoTcTimecardList.size() - 1;

        for (int i = 0; i < rowCount; i++) {
            DtoTcTimecard dtoTcTimecard = (DtoTcTimecard) dtoTcTimecardList.get(i);
            Date          dStart = dtoTcTimecard.getTmcEmpStart();
            Date          dEnd   = dtoTcTimecard.getTmcEmpFinish();

            String        start = df.format(dStart);
            String        end   = df.format(dEnd);

            if ((start.compareTo(weekStart) < 0)
                    || (start.compareTo(weekEnd) > 0)) {
                comMSG.dispErrorDialog("The Date Employee Work Start is Out of Range\nin Row "
                                       + (i + 1) + " !");

                checkFlag = false;

                return;
            } else if ((end.compareTo(weekStart) < 0)
                           || (end.compareTo(weekEnd) > 0)) {
                comMSG.dispErrorDialog("The Date Employee Work Finish is Out of Range\nin Row "
                                       + (i + 1) + " !");

                checkFlag = false;

                return;
            } else if (end.compareTo(start) < 0) {
                comMSG.dispErrorDialog("The Date Employee Work Start is After Finish\nin Row "
                                       + (i + 1) + " !");
                currTable.setRowSelectionInterval(i, i);

                checkFlag = false;

                return;
            }
        }

        checkFlag = true;
    }

    //从jsp页面获取员工列表,通过接口Sponsor 的 setNewData()将jsp页面数据返回并调用addEmployees()
    private void actionPerformedAdd(ActionEvent e) {
        log.debug("1 begin to call requestHrAlloc()");

        HrAllocate hrAllocate = new HrAllocate();
        hrAllocate.allocWorker(sponsor);

        //        String[] newList = new String[] {"000018", "000038" , "000012"};
        //        addEmployees(newList);
        log.debug("2 after call requestHrAlloc()");
    }

    //获取新增员工的信息并添加到列表中
    private void addEmployees(String[] addedEmployees) {
        java.util.List dtoTcTimecardList = currTableModel.getRows();
        java.util.List dtoPwWkitemList = (java.util.List) returnInfo
                                         .getReturnObj("DtoPwWkitemList");
        int            oldRowCount   = dtoTcTimecardList.size();
        int            addedRowCount = addedEmployees.length;
        log.debug("add employee");

        String funId = "add";

        //提交信息（条件）
        TransactionData tempData  = new TransactionData();
        InputInfo       inputInfo = tempData.getInputInfo();

        //        inputInfo.setControllerUrl(controllerUrl);
        inputInfo.setActionId(actionId);
        inputInfo.setFunId(funId);

        DtoQueryCondition dtoQueryCondition = new DtoQueryCondition();
        dtoQueryCondition.setInit(isInit);
        dtoQueryCondition.setWeekStart((java.util.Date) txtStart.getUICValue());
        dtoQueryCondition.setWeekEnd((java.util.Date) txtFinish.getUICValue());
        dtoQueryCondition.setProjectID(prjID);
        dtoQueryCondition.setPM(isPM);
        inputInfo.setInputObj("DtoQueryCondition", dtoQueryCondition);
        inputInfo.setInputObj("EMPLOYEELIST", addedEmployees);

        //连接servlet，执行操作
        NetConnector connector = ConnectFactory.createConnector();
        connector.accessData(tempData);

        ReturnInfo tempReturnData = tempData.getReturnInfo();

        if (tempReturnData.isError() == false) {
            log.debug("Get Employees Successfully");

            java.util.List tempDtoTcTimecardList = (java.util.List) tempReturnData
                                                   .getReturnObj("DtoTcTimecardList");
            java.util.List tempDtoPwWkitemList = (java.util.List) tempReturnData
                                                 .getReturnObj("DtoPwWkitemList");

            for (int i = 0; i < addedRowCount; i++) {
                DtoTcTimecard  tempDtoTcTimecard = (DtoTcTimecard) tempDtoTcTimecardList
                                                   .get(i);
                java.util.List tempDtoPwWkitem = (java.util.List) tempDtoPwWkitemList
                                                 .get(i);

                if ((tempDtoTcTimecard.getTmcEmpStart() == null)
                        || (tempDtoTcTimecard.getTmcEmpFinish() == null)
                        || tempDtoTcTimecard.getTmcEmpStart().toString().equals("")
                        || tempDtoTcTimecard.getTmcEmpFinish().toString()
                                                .equals("")) {
                    tempDtoTcTimecard.setTmcEmpStart((Date) txtStart
                                                     .getUICValue());
                    tempDtoTcTimecard.setTmcEmpFinish((Date) txtFinish
                                                      .getUICValue());
                }

                //                tempDtoTcTimecard.setOp(IDto.OP_INSERT);
                //                dtoTcTimecardList.add(oldCount - 1 + i, tempDtoTcTimecard);
                currTableModel.addRow(oldRowCount - 2 + i, tempDtoTcTimecard);
                dtoPwWkitemList.add(oldRowCount - 1 + i, tempDtoPwWkitem);
            }

            reComputeTotal();
            currTable.setRowSelectionAllowed(true);
            rowLastClicked = oldRowCount - 1;
            currTable.setRowSelectionInterval(rowLastClicked, rowLastClicked);
            rowClicked = rowLastClicked;
            refreshDownArea();
        }
    }

    //删除员工
    private void actionPerformedDel(ActionEvent e) {
        java.util.List dtoTcTimecardList = currTableModel.getRows();
        java.util.List dtoPwWkitemList = (java.util.List) returnInfo
                                         .getReturnObj("DtoPwWkitemList");
        int            rowCount = dtoTcTimecardList.size();

        if (rowCount > 1) {
            int iAnswer = comMSG.dispConfirmDialog("Are You Sure to Delete this Row?");

            if (iAnswer == Constant.OK) {
                rowCount--;
                currTableModel.deleteRow(rowLastClicked);

                //                dtoTcTimecardList.remove(rowLastClicked);
                dtoPwWkitemList.remove(rowLastClicked);
                reComputeTotal();

                if ((rowLastClicked - 1) >= 0) {
                    rowLastClicked--;
                    currTable.setRowSelectionInterval(rowLastClicked,
                                                      rowLastClicked);
                } else {
                    rowLastClicked = 0;

                    if (rowCount > 1) {
                        currTable.setRowSelectionInterval(rowLastClicked,
                                                          rowLastClicked);
                    } else {
                        currTable.setRowSelectionAllowed(false);
                    }
                }

                rowClicked = rowLastClicked;
                refreshDownArea();
            }
        }
    }

    //刷新下面卡片
    private void refreshDownArea() {
        Parameter      paraDown           = new Parameter();
        java.util.List employeeReportList = (java.util.List) returnInfo
                                            .getReturnObj("DtoPwWkitemList");
        java.util.List employeeReport = new ArrayList();

        if (employeeReportList.size() > 0) {
            employeeReport = (java.util.List) employeeReportList.get(rowClicked);
        }

        paraDown.put("DtoPwWkitemList", employeeReport);
        weeklyReport.setParameter(paraDown);
        weeklyReport.refreshWorkArea();

        rowClicked = 0;
    }

    //添加删除行后重新计算合计行
    private void reComputeTotal() {
        java.util.List dtoTcTimecardList = currTableModel.getRows();
        double         totalPersonal  = 0;
        double         totalActual    = 0;
        double         totalAllocated = 0;
        double         totalOffset    = 0;
        double         totalOvertime  = 0;
        double         totalShift     = 0;
        double         totalVacation  = 0;
        double         totalPrivate   = 0;
        double         totalSick      = 0;
        double         totalAbsence   = 0;
        double         totalBreakrule = 0;

        int            rowCount = dtoTcTimecardList.size() - 1;
        employees = new String[rowCount][2];

        for (int i = 0; i < rowCount; i++) {
            DtoTcTimecard dtoTcTimecardRow = (DtoTcTimecard) dtoTcTimecardList
                                             .get(i);

            employees[i][0] = dtoTcTimecardRow.getTmcEmpId();
            employees[i][1] = dtoTcTimecardRow.getTmcEmpName();

            totalPersonal += dtoTcTimecardRow.getTmcPersonalWorkHours()
                                             .doubleValue();
            totalActual += dtoTcTimecardRow.getTmcActualWorkHours().doubleValue();
            totalAllocated += dtoTcTimecardRow.getTmcAllocatedWorkHours()
                                              .doubleValue();
            totalOffset += dtoTcTimecardRow.getTmcAttenOffsetWork().doubleValue();
            totalOvertime += dtoTcTimecardRow.getTmcAttenOvertime().doubleValue();
            totalVacation += dtoTcTimecardRow.getTmcAttenVacation().doubleValue();
            totalShift += dtoTcTimecardRow.getTmcAttenShiftAdjustment()
                                          .doubleValue();
            totalPrivate += dtoTcTimecardRow.getTmcAttenPrivateLeave()
                                            .doubleValue();
            totalSick += dtoTcTimecardRow.getTmcAttenSickLeave().doubleValue();
            totalAbsence += dtoTcTimecardRow.getTmcAttenAbsence().doubleValue();
            totalBreakrule += dtoTcTimecardRow.getTmcAttenBreakingRules()
                                              .doubleValue();
        }

        DtoTcTimecard totalRow = (DtoTcTimecard) dtoTcTimecardList.get(dtoTcTimecardList
                                                                       .size()
                                                                       - 1);
        totalRow.setTmcPersonalWorkHours(new BigDecimal(totalPersonal));
        totalRow.setTmcActualWorkHours(new BigDecimal(totalActual));
        totalRow.setTmcAllocatedWorkHours(new BigDecimal(totalAllocated));
        totalRow.setTmcAttenOffsetWork(new BigDecimal(totalOffset));
        totalRow.setTmcAttenOvertime(new BigDecimal(totalOvertime));
        totalRow.setTmcAttenVacation(new BigDecimal(totalVacation));
        totalRow.setTmcAttenShiftAdjustment(new BigDecimal(totalShift));
        totalRow.setTmcAttenPrivateLeave(new BigDecimal(totalPrivate));
        totalRow.setTmcAttenSickLeave(new BigDecimal(totalSick));
        totalRow.setTmcAttenAbsence(new BigDecimal(totalAbsence));
        totalRow.setTmcAttenBreakingRules(new BigDecimal(totalBreakrule));
        currTableModel.fireTableDataChanged();
        currTable.setRowSelectionInterval(rowLastClicked, rowLastClicked);
        changedFlag = true;
    }

    public JButton getBtAdd() {
        return btAdd;
    }

    public JButton getBtDel() {
        return btDel;
    }

    public JButton getBtReload() {
        return btReload;
    }

    public JButton getBtSave() {
        return btSave;
    }

    public VWJTable getCurrTable() {
        return currTable;
    }

    public PmTableModel getCurrTableModel() {
        return currTableModel;
    }

    public JButton getBtLastPeriod() {
        return btLastPeriod;
    }

    public JButton getBtNextPeriod() {
        return btNextPeriod;
    }

    public VWJComboBox getCbxPrjList() {
        return cbxPrjList;
    }

    public VWJReal getTxtRecWkHours() {
        return txtRecWkHours;
    }
}


class PmTableModel extends VMTable2 {
    private static String[][] tblFields = {
                                              {
                                                  "EMPLOYEE", "tmcEmpName",
                                                  Constant.UNEDITABLE
                                              },
                                              {
                                                  "Position Type",
                                                  "tmcEmpPositionType",
                                                  Constant.UNEDITABLE
                                              },
                                              {
                                                  "Start", "tmcEmpStart",
                                                  Constant.EDITABLE
                                              },
                                              {
                                                  "Finish", "tmcEmpFinish",
                                                  Constant.EDITABLE
                                              },
                                              {
                                                  "Personal",
                                                  "tmcPersonalWorkHours",
                                                  Constant.UNEDITABLE
                                              },
                                              {
                                                  "Actual", "tmcActualWorkHours",
                                                  Constant.EDITABLE
                                              },
                                              {
                                                  "Allocatted",
                                                  "tmcAllocatedWorkHours",
                                                  Constant.UNEDITABLE
                                              },
                                              {
                                                  "Offset Work",
                                                  "tmcAttenOffsetWork",
                                                  Constant.UNEDITABLE
                                              },
                                              {
                                                  "Overtime", "tmcAttenOvertime",
                                                  Constant.UNEDITABLE
                                              },
                                              {
                                                  "Vacation", "tmcAttenVacation",
                                                  Constant.UNEDITABLE
                                              },
                                              {
                                                  "Shift-Adjustment",
                                                  "tmcAttenShiftAdjustment",
                                                  Constant.UNEDITABLE
                                              },
                                              {
                                                  "Private",
                                                  "tmcAttenPrivateLeave",
                                                  Constant.UNEDITABLE
                                              },
                                              {
                                                  "Sick", "tmcAttenSickLeave",
                                                  "Uneditable"
                                              },
                                              {
                                                  "Absence", "tmcAttenAbsence",
                                                  Constant.UNEDITABLE
                                              },
                                              {
                                                  "Breaking Rules",
                                                  "tmcAttenBreakingRules",
                                                  Constant.UNEDITABLE
                                              }
                                          };
    private VWJText           vwjtext        = new VWJText();
    private VWJDate           vwjdate        = new VWJDate();
    private VWJReal           vwjreal        = new VWJReal();
    private HashMap           editMap        = new HashMap();
    private Font              tableFontArial = new Font("Arial", Font.PLAIN, 9);

    public PmTableModel(Class dtoClass) {
        super(tblFields);

        vwjtext.setFont(tableFontArial);
        vwjdate.setFont(tableFontArial);
        vwjreal.setFont(tableFontArial);

        vwjdate.setDataType(Constant.DATE_YYYYMMDD);

        vwjreal.setMaxInputIntegerDigit(10);
        vwjreal.setMaxInputDecimalDigit(2);

        this.getColumnConfigByDataName("tmcEmpName").setComponent(vwjtext);
        this.getColumnConfigByDataName("tmcEmpPositionType").setComponent(vwjtext);
        this.getColumnConfigByDataName("tmcEmpStart").setComponent(vwjdate);
        this.getColumnConfigByDataName("tmcEmpFinish").setComponent(vwjdate);
        this.getColumnConfigByDataName("tmcPersonalWorkHours").setComponent(vwjreal);
        this.getColumnConfigByDataName("tmcActualWorkHours").setComponent(vwjreal);
        this.getColumnConfigByDataName("tmcAllocatedWorkHours").setComponent(vwjreal);
        this.getColumnConfigByDataName("tmcAttenOffsetWork").setComponent(vwjreal);
        this.getColumnConfigByDataName("tmcAttenOvertime").setComponent(vwjreal);
        this.getColumnConfigByDataName("tmcAttenVacation").setComponent(vwjreal);
        this.getColumnConfigByDataName("tmcAttenShiftAdjustment").setComponent(vwjreal);
        this.getColumnConfigByDataName("tmcAttenPrivateLeave").setComponent(vwjreal);
        this.getColumnConfigByDataName("tmcAttenSickLeave").setComponent(vwjreal);
        this.getColumnConfigByDataName("tmcAttenAbsence").setComponent(vwjreal);
        this.getColumnConfigByDataName("tmcAttenBreakingRules").setComponent(vwjreal);

        super.setDtoType(dtoClass);
    }

    public void setValueAt(Object aValue,
                           int    rowIndex,
                           int    columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);

        if (getColumnName(columnIndex).equals("Actual")) {
            java.util.List dtoTcTimecardList = (java.util.List) this.getRows();
            double         totalActual    = 0;
            double         totalAllocated = 0;
            DtoTcTimecard  changedRow     = (DtoTcTimecard) dtoTcTimecardList
                                            .get(rowIndex);
            double         allocatedValue = changedRow.getTmcActualWorkHours()
                                                      .doubleValue()
                                            - (changedRow.getTmcAttenOvertime()
                                                         .doubleValue()
                                            - changedRow.getTmcAttenPrivateLeave()
                                                        .doubleValue()
                                            - changedRow.getTmcAttenSickLeave()
                                                        .doubleValue()
                                            - changedRow.getTmcAttenVacation()
                                                        .doubleValue()
                                            - changedRow.getTmcAttenAbsence()
                                                        .doubleValue()
                                            - changedRow.getTmcAttenShiftAdjustment()
                                                        .doubleValue());
            changedRow.setTmcAllocatedWorkHours(new BigDecimal(allocatedValue));

            for (int i = 0; i < (dtoTcTimecardList.size() - 1); i++) {
                DtoTcTimecard dtoTcTimecardRow = (DtoTcTimecard) dtoTcTimecardList
                                                 .get(i);
                totalActual += dtoTcTimecardRow.getTmcActualWorkHours()
                                               .doubleValue();
                totalAllocated += dtoTcTimecardRow.getTmcAllocatedWorkHours()
                                                  .doubleValue();
            }

            DtoTcTimecard totalRow = (DtoTcTimecard) dtoTcTimecardList.get(dtoTcTimecardList
                                                                           .size()
                                                                           - 1);
            totalRow.setTmcActualWorkHours(new BigDecimal(totalActual));
            totalRow.setTmcAllocatedWorkHours(new BigDecimal(totalAllocated));
            this.fireTableDataChanged();
        }
    }

    public boolean isCellEditable(int iRow,
                                  int iCol) {
        int rowCount = this.getRowCount();

        if (iRow == (rowCount - 1)) {
            return false;
        }

        if (editMap.get("@" + iCol) != null) {
            return Boolean.TRUE.equals(editMap.get("@" + iCol));
        }

        return super.isCellEditable(iRow, iCol);
    }

    public void setColumnEditable(int     iCol,
                                  Boolean isEditable) {
        editMap.put("@" + iCol, isEditable);
    }

    public Object addRow(int    rowIndex,
                         Object dtoBean) {
        if (dtoBean == null) {
            return null;
        }

        if (dtoBean instanceof IDto) {
            ((IDto) dtoBean).setOp(IDto.OP_INSERT);
        }

        int addRowIndex = 0;

        if (rowIndex >= getRowCount()) {
            addRowIndex = getRowCount();
        } else {
            addRowIndex = rowIndex + 1;
        }

        this.getRows().add(addRowIndex, dtoBean);
        this.getDtoList().add(addRowIndex, dtoBean);
        fireTableRowsInserted(addRowIndex, addRowIndex);

        return dtoBean;
    }

    public Object deleteRow(int rowIndex) {
        if (rowIndex < 0) {
            return null;
        }

        Object dtoBean = getRow(rowIndex);

        if (dtoBean != null) {
            if (dtoBean instanceof IDto) {
                IDto thisDtoBean = (IDto) dtoBean;

                if (IDto.OP_INSERT.equals(thisDtoBean.getOp())) {
                    this.getDtoList().remove(thisDtoBean);
                } else {
                    thisDtoBean.setOp(IDto.OP_DELETE);
                }
            }

            this.getRows().remove(rowIndex);
        }

        return dtoBean;
    }
}
