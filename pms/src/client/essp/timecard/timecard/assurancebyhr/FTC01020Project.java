package client.essp.timecard.timecard.assurancebyhr;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.timecard.timecard.DtoQueryCondition;
import c2s.essp.timecard.timecard.DtoTcTm;

import client.essp.common.view.VWTDWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMTable2;
import client.framework.view.common.comMSG;
import client.framework.view.event.DataChangedListener;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;
import client.image.ComImage;
import client.net.ConnectFactory;
import client.net.NetConnector;

import com.wits.util.Parameter;
import com.wits.util.StringUtil;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.wits.util.comDate;
import javax.swing.JFrame;
import java.io.File;
import javax.swing.JFileChooser;
import client.framework.common.Global;

public class FTC01020Project extends VWTDWorkArea {
    private boolean               refreshFlag          = false;
    private List                  dataChangedLisenters = new ArrayList();
    private String                actionId             = "FTC01020S_HRTimeCard";
    private Date                  weekStart;
    private Date                  weekEnd;
    private boolean               isInit               = true;
    private String                submitStatus         = "0";
    private String                newSubmitStatus      = "";
    private int                   rowClicked           = 0;
    private VWWorkArea            topArea              = new VWWorkArea();
    private VWWorkArea            downArea             = new VWWorkArea();
    private VWJDate               txtStart             = new VWJDate();
    private VWJLabel              lblTo                = new VWJLabel();
    private VWJDate               txtFinish            = new VWJDate();
    private JButton               btLastPeriod         = new JButton();
    private JButton               btNextPeriod         = new JButton();
    private VWJLabel              lblStatus            = new VWJLabel();
    private VWJComboBox           cbxStatus            = new VWJComboBox();
    private JButton               btSave;
    private JButton               btReload;
    private JButton               btExportExcel        = new JButton();
    private BorderLayout          borderLayout         = new BorderLayout();
    private ProjectListTableModel currTableModel       = new ProjectListTableModel(DtoTcTm.class); //当前表的tableModel
    private VWJTable              currTable            = new VWJTable(currTableModel); //当前表
    private FTC01020EmployeeInfo  employeeInfo         = new FTC01020EmployeeInfo();
    private FlowLayout            flowLayout           = new FlowLayout(FlowLayout.LEFT,
                                                                        5, 5);
    private boolean               isPrompt       = false;
    private boolean               isAlwaysOk     = false;
    private boolean               isAllowChanges = false;

    public FTC01020Project() {
        super(300);

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(800, 505));
        this.setMaximumSize(new Dimension(1024, 768));
        this.setMinimumSize(new Dimension(500, 200));

        btExportExcel.setIcon(new ImageIcon(ComImage.getImage("excel.png")));
        btExportExcel.setMaximumSize(new Dimension(20, 20));
        btExportExcel.setMinimumSize(new Dimension(20, 20));
        btExportExcel.setPreferredSize(new Dimension(20, 20));
        btExportExcel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actionPerformedExportExcel(e);
                }
            });
        btExportExcel = this.getButtonPanel().addButton(btExportExcel);

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

        VWWorkArea periodPanel = new VWWorkArea();
        periodPanel.setBorder(null);
        periodPanel.setLayout(flowLayout);

        btLastPeriod.setText("<");
        btLastPeriod.setToolTipText("Last Week");
        btLastPeriod.setPreferredSize(new Dimension(42, 20));
        btLastPeriod.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    getLastPeriod();
                }
            });
        periodPanel.add(btLastPeriod);

        txtStart.setEnabled(false);
        txtStart.setDataType(Constant.DATE_YYYYMMDD);
        periodPanel.add(txtStart);

        lblTo.setText("To");
        lblTo.setHorizontalAlignment(SwingConstants.CENTER);
        periodPanel.add(lblTo);

        txtFinish.setEnabled(false);
        txtFinish.setDataType(Constant.DATE_YYYYMMDD);
        periodPanel.add(txtFinish);

        btNextPeriod.setText(">");
        btNextPeriod.setToolTipText("Next Week");
        btNextPeriod.setPreferredSize(new Dimension(42, 20));
        btNextPeriod.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    getNextPeriod();
                }
            });
        periodPanel.add(btNextPeriod);

        lblStatus.setText("Checking Status");
        periodPanel.add(lblStatus);

        cbxStatus.addItem("Checking");
        cbxStatus.addItem("Passed");
        cbxStatus.setBorder(BorderFactory.createLoweredBevelBorder());
        cbxStatus.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    newSubmitStatus = "" + cbxStatus.getSelectedIndex();
                }
            });
        periodPanel.add(cbxStatus);

        topArea.add(periodPanel, BorderLayout.NORTH);

        currTable.getTableHeader().setReorderingAllowed(false);
        currTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        currTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    currTableRowChanged(e);
                }
            });

        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setBorder(null);
        jScrollPane.getViewport().add(currTable, null); //给table加滚动条

        topArea.add(jScrollPane, BorderLayout.CENTER);

        this.setTopArea(topArea);
        downArea.addTab("Project Group Members", employeeInfo, false);
        this.setDownArea(downArea);
    }

    //点击上面的表格
    private void currTableRowChanged(ListSelectionEvent e) {
        rowClicked = currTable.getSelectedRow();

        if (rowClicked >= 0) {
            refreshDownArea();
        }

        rowClicked = 0;
    }

    //点击上个工作区间按钮
    private void getLastPeriod() {
        saveWorkArea();

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

    //刷新显示
    private void actionPerformedLoad(ActionEvent e) {
        saveWorkArea();
        this.refreshFlag = true;
        refreshWorkArea();
    }

    public void setParameter(Parameter para) {
        this.isInit    = ((Boolean) para.get("ISINIT")).booleanValue();
        this.weekStart = (Date) para.get("START");
        this.weekEnd   = (Date) para.get("END");

        if (para.get("ISPROMPT") != null) {
            this.isPrompt = ((Boolean) para.get("ISPROMPT")).booleanValue();
        }

        if (para.get("ISALWAYSOK") != null) {
            this.isAlwaysOk = ((Boolean) para.get("ISALWAYSOK")).booleanValue();
        }

        txtStart.setUICValue(weekStart);
        txtFinish.setUICValue(weekEnd);
        this.refreshFlag = true;
    }

    public void refreshWorkArea() {
        int rowCount = 0;

        if (this.refreshFlag == true) {
            refreshFlag = false;

            TransactionData transData  = new TransactionData();
            ReturnInfo      returnInfo = prepareData(transData);

            if (returnInfo != null) {
                //基本信息
                DtoQueryCondition dtoQueryCondition = (DtoQueryCondition) returnInfo
                                                      .getReturnObj("DtoQueryCondition");
                this.txtStart.setUICValue(dtoQueryCondition.getWeekStart());
                this.txtFinish.setUICValue(dtoQueryCondition.getWeekEnd());
                submitStatus    = dtoQueryCondition.getSubmitStatus();
                newSubmitStatus = submitStatus;

                if (StringUtil.nvl(submitStatus).equals("")) {
                    submitStatus    = "0";
                    newSubmitStatus = "0";
                }

                this.cbxStatus.setSelectedIndex(Integer.parseInt(submitStatus));

                //项目列表
                List dtoTcTmList = (List) returnInfo.getReturnObj("DtoTcTmList");
                rowCount = dtoTcTmList.size();
                this.currTableModel.setRows(dtoTcTmList);
                this.currTableModel.fireTableDataChanged();
                isInit = false;

                //设置当前选中行
                if (rowCount > 0) {
                    isAllowChanges = true;
                    currTable.setRowSelectionInterval(rowClicked, rowClicked);
                } else {
                    isAllowChanges = false;
                }

                this.cbxStatus.setEnabled(isAllowChanges);

                //刷新下面卡片
                refreshDownArea();
            }
        }
    }

    private void refreshDownArea() {
        List dtoTcTimecardList = new ArrayList();

        if (currTableModel.getRows().size() > 0) {
            dtoTcTimecardList = (ArrayList) ((DtoTcTm) currTableModel.getRows()
                                                                     .get(rowClicked))
                                .getDtoTcTimeCardList();
        }

        Parameter paraDown = new Parameter();
        paraDown.put("DtoTcTimecardList", dtoTcTimecardList);
        employeeInfo.setParameter(paraDown);
        employeeInfo.refreshWorkArea();
        rowClicked = 0;
    }

    //根据条件，抽出数据
    private ReturnInfo prepareData(TransactionData transData) {
        log.debug("prepare data");

        String funId = "getProject";

        //提交信息（条件）
        InputInfo inputInfo = transData.getInputInfo();

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
        inputInfo.setInputObj("DtoQueryCondition", dtoQueryCondition);

        //连接servlet，执行操作
        NetConnector connector = ConnectFactory.createConnector();
        connector.accessData(transData);

        //返回信息（结果）
        ReturnInfo returnInfo = transData.getReturnInfo();

        if (returnInfo.isError() == false) {
            log.debug("Getting Settings Successfully");

            return returnInfo;
        } else {
            log.error(returnInfo.getReturnMessage());

            return null;
        }
    }

    //这里将数据保存到数据库中去
    private void actionPerformedSave(ActionEvent e) {
        if (newSubmitStatus.equals(submitStatus) == false) {
            saveData();
            submitStatus = newSubmitStatus;
        }
    }

    public void saveWorkArea() {
        if (newSubmitStatus.equals(submitStatus) == false) {
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
                    saveData();
                    submitStatus = newSubmitStatus;
                }
            } else {
                if (this.isAlwaysOk == true) {
                    saveData();
                    submitStatus = newSubmitStatus;
                }
            }
        }

        fireDataChanged();
    }

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
        dtoQueryCondition.setSubmitStatus(newSubmitStatus);
        inputInfo.setInputObj("DtoQueryCondition", dtoQueryCondition);

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

    //通知:数据已修改
    public void fireDataChanged() {
        for (Iterator it = this.dataChangedLisenters.iterator(); it.hasNext();) {
            DataChangedListener listener = (DataChangedListener) it.next();
            listener.processDataChanged();
        }
    }

    public void addDataChangedListener(DataChangedListener lisenter) {
        this.dataChangedLisenters.add(lisenter);
    }

    Date getStartDate() {
        return (Date) this.txtStart.getUICValue();
    }

    Date getFinishDate() {
        return (Date) this.txtFinish.getUICValue();
    }

    Boolean isPrompt() {
        return new Boolean(isPrompt);
    }

    Boolean isAlwaysOk() {
        return new Boolean(isAlwaysOk);
    }

    Boolean isAllowChanges() {
        return new Boolean(isAllowChanges);
    }

    //导出excel
    private void actionPerformedExportExcel(ActionEvent e) {
            String actionExcelId = "FTC01020S_Excel";
            String funId = "getTmCdRpList";
            Object[][] tbSConfig = { {"EMPLOYEE", "tmcEmpName", "Editable", ""}
            , {"Position Type", "tmcEmpPositionType", "Editable", ""}
            , {"Start", "tmcWeeklyStart", "Editable", "yyyy-MM-dd"}
            , {"Finish", "tmcWeeklyFinish", "Editable", "yyyy-MM-dd"}
            , {"Actual", "tmcActualWorkHours", "Editable"}
            , {"Allocatted", "tmcAllocatedWorkHours", "Editable"}
            , {"Offset Work", "tmcAttenOffsetWork", "Editable"}
            , {"Overtime", "tmcAttenOvertime", "Editable"}
            , {"Vacation", "tmcAttenVacation", "Editable"}
            , {"Shift-Adjustment", "tmcAttenShiftAdjustment", "Editable"}
            , {"Private", "tmcAttenPrivateLeave", "Editable"}
            , {"Sick", "tmcAttenSickLeave", "Editable"}
            , {"Absence", "tmcAttenAbsence", "Editable"}
            , {"Breaking Rules", "tmcAttenBreakingRules", "Editable"}
        };
            String inTempleteFileName = "d:/sample.xls";

            TransactionData transData = new TransactionData();
            InputInfo inputInfo = transData.getInputInfo();
            inputInfo.setActionId(actionExcelId);
            inputInfo.setFunId(funId);
            inputInfo.setInputObj("inStartTime", comDate.dateToString(this.getStartDate(),"yyyy-MM-dd"));
            inputInfo.setInputObj("inFinishTime", comDate.dateToString(this.getFinishDate(),"yyyy-MM-dd"));
            inputInfo.setInputObj("inTempleteFileName", inTempleteFileName);
            inputInfo.setInputObj("intbSConfig", tbSConfig);

            NetConnector connector = ConnectFactory.createConnector();
            connector.accessData(transData);
            String filePath=(String)transData.getReturnInfo().getReturnObj("SERVER_FILE");
            // popup dialog ...

            // get client filepath
            String clientFilePath="";
            JFrame se = null;
            File fileName = null;
            JFileChooser fileChooser = new JFileChooser(new File("filename.xls"));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            ExampleFileFilter ff = new ExampleFileFilter();
            ff.addExtension("xls");
            fileChooser.setFileFilter(ff);
            fileChooser.showSaveDialog(se);
            fileName = fileChooser.getSelectedFile();
            if(fileName != null){
                clientFilePath = fileName.getPath()+".xls";
            }
            if(!clientFilePath.equals("")){
                NetConnector downloadConnector = ConnectFactory.createDownloadConnector();
                TransactionData downloadTransData = new TransactionData();
                InputInfo downloadInputInfo = downloadTransData.getInputInfo();
                downloadInputInfo.setActionId("FTC01020S_Down");
                downloadInputInfo.setInputObj("SERVER_FILE", filePath);
                downloadInputInfo.setInputObj("CLIENT_FILE", clientFilePath);
                downloadConnector.accessData(downloadTransData);
            }
    }
}


class ProjectListTableModel extends VMTable2 {
    private static String[][] tblFields = {
                                              {
                                                  "ACCOUNT", "AccountCode",
                                                  Constant.UNEDITABLE
                                              },
                                              {
                                                  "NAME", "AccountName",
                                                  Constant.UNEDITABLE
                                              },
                                              {
                                                  "Actual", "tmcActualWorkHours",
                                                  Constant.UNEDITABLE
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
                                                  Constant.UNEDITABLE
                                              },
                                              {
                                                  "Absence", "tmcAttenAbsence",
                                                  Constant.UNEDITABLE
                                              },
                                              {
                                                  "Breaking Rules",
                                                  "tmcAttenBreakingRules",
                                                  Constant.UNEDITABLE
                                              },
                                              {
                                                  "Recognized WorkHours",
                                                  "tmcRecongnizedWorkhours",
                                                  Constant.UNEDITABLE
                                              }
                                          };
    private VWJText           vwjtext         = new VWJText();
    private VWJDate           vwjdate         = new VWJDate();
    private VWJReal           vwjreal         = new VWJReal();
    private Font              tableFontArial  = new Font("Arial", Font.PLAIN, 9);
    private VWJText           vwjtextSongti   = new VWJText();
    private Font              tableFontSongti = new Font("宋体", Font.PLAIN, 9);

    public ProjectListTableModel(Class dtoClass) {
        super(tblFields);

        vwjtext.setFont(tableFontArial);
        vwjtextSongti.setFont(tableFontSongti);
        vwjdate.setFont(tableFontArial);
        vwjreal.setFont(tableFontArial);

        vwjdate.setDataType(Constant.DATE_YYYYMMDD);

        vwjreal.setMaxInputIntegerDigit(10);
        vwjreal.setMaxInputDecimalDigit(2);

        this.getColumnConfigByDataName("AccountCode").setComponent(vwjtext);
        this.getColumnConfigByDataName("AccountName").setComponent(vwjtextSongti);
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
        this.getColumnConfigByDataName("tmcRecongnizedWorkhours").setComponent(vwjreal);

        super.setDtoType(dtoClass);
    }
}
