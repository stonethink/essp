package client.essp.pwm.wp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumn;

import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pwm.wp.DtoPwWpchklog;
import client.essp.common.util.TestPanelParam;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.common.Global;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;
import com.wits.util.comDate;

public class FPW01022CheckpointLog extends VWGeneralWorkArea {
    private String actionId = "FPW01022CheckpointLogAction";

    //parameter
    private String inCheckpointId = "";

    //control data
    private boolean isSaveOk = true;

    //common data
    private List chkLogList;
    private VMTblCheckpointLog vmTblChkLog;
    private VWJTable tblChkLog;
    private DtoPwWpchklog dtoPwWpchklog = new DtoPwWpchklog();

    //UI component
    private VWJLabel lblResult = new VWJLabel();
    private VWJText txtResult = new VWJText();
    private VWJLabel lblDeliveryDate = new VWJLabel();
    private VWJDate dteDeliveryDate = new VWJDate();

    public FPW01022CheckpointLog() {
        try {
            jbInit();
            addUIEvent();
            addUIComponentName();
            VWUtil.convertUI2Dto(dtoPwWpchklog, this, IDto.OP_NOCHANGE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setEnabledMode();
    }

    //Component initialization
    private void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(800, 306));
        this.setLayout(new BorderLayout());

        JPanel panelCheckPointLog = new JPanel();
        Border border1 = new EtchedBorder(EtchedBorder.RAISED, Color.white,
                                          new Color(148, 145, 140));
        panelCheckPointLog.setBorder(new TitledBorder(border1, "CheckPoint Log"));
        panelCheckPointLog.setPreferredSize(new Dimension(22, 200));
        panelCheckPointLog.setLayout(new BorderLayout());

        vmTblChkLog = new VMTblCheckpointLog(DtoPwWpchklog.class);
        tblChkLog = new VWJTable(vmTblChkLog);

        for (int i = 0; i < 6; i++) {
            TableColumn column = tblChkLog.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(30);
            } else if (i == 3) {
                column.setPreferredWidth(200);
            } else {
                column.setPreferredWidth(80);
            }
        }
        panelCheckPointLog.add(tblChkLog.getScrollPane(), BorderLayout.CENTER);

        JPanel panelChange = new JPanel();
        panelChange.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.
            RAISED, Color.white, new Color(148, 145, 140)),
                                               "Change Delivery Date"));
        panelChange.setLayout(null);
        lblResult.setBounds(26, 20, 120, 20);
        lblResult.setText("Reason");
        txtResult.setBounds(151, 20, 300, 20);
        txtResult.setText("");
        lblDeliveryDate.setBounds(26, 45, 120, 20);
        lblDeliveryDate.setText("Delivery Date");
        dteDeliveryDate.setBounds(151, 45, 100, 20);
        dteDeliveryDate.setCanSelect(true);
        panelChange.add(lblResult, null);
        panelChange.add(txtResult, null);
        panelChange.add(lblDeliveryDate, null);
        panelChange.add(dteDeliveryDate, null);

        this.add(panelCheckPointLog, BorderLayout.NORTH);
        this.add(panelChange, BorderLayout.CENTER);
    }

    public void addUIComponentName() {
        this.dteDeliveryDate.setName("wpchklogsActchk");
        this.txtResult.setName("wpchklogsReason");
    }

    public void addUIEvent() {
        this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });

        dteDeliveryDate.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    keyPressedTxtDeliveryDate();
                }
            }
        });
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.inCheckpointId = StringUtil.nvl(param.get("inCheckpointId"));

    }


    public void resetUI() {
        setEnabledMode();

        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId);
        inputInfo.setFunId("getCheckpointLog");
        inputInfo.setInputObj("inCheckpointId", this.inCheckpointId);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            this.chkLogList = (List) returnInfo.getReturnObj("chkLogList");

            tblChkLog.setRows(chkLogList);

            this.txtResult.setText("");
            this.dteDeliveryDate.setText("");
        }
    }

    private void setEnabledMode() {

    }

    public void actionPerformedSave(ActionEvent e) {
        if (checkModified()) {
            if (validateData() == true) {
                saveData();
            }
        }
    }

    public void keyPressedTxtDeliveryDate() {
//        if (dteDeliveryDate.getValueText().trim().equals("") == false) {
//            if (comDate.checkDate(dteDeliveryDate.getValueText()) == false) {
//                comMSG.dispErrorDialog("日期格式有错,正确格式为yyyy-MM-dd.");
//
//                dteDeliveryDate.requestFocus();
//            }
//        }
    }

    /////// 段5，保存数据
    public void saveWorkArea() {
        if (checkModified()) {
            isSaveOk = false;
            if (
                //confirmSaveWorkArea("Do you save the work items?") ==
                true) {
                if (validateData() == true) {
                    isSaveOk = saveData();
                }
            }else{
                isSaveOk = true;
            }
        }else{
            isSaveOk = true;
        }
    }

    public boolean isSaveOk() {
        return this.isSaveOk;
    }

    public boolean checkModified() {
        getDataFromUI();

        return dtoPwWpchklog.isChanged();
    }

    private boolean saveData() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId);
        inputInfo.setFunId("saveCheckpointLog");
        inputInfo.setInputObj("dtoPwWpchklog", dtoPwWpchklog);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            return true;
        }else{
            return false;
        }
    }

    private void getDataFromUI() {
        //getCheckPointLog
        VWUtil.convertUI2Dto(dtoPwWpchklog, this);

        Date date = Global.todayDate;
        dtoPwWpchklog.setWpchklogsDate(date);
        dtoPwWpchklog.setWpchklogsBaselinechk(Global.todayDate);
        dtoPwWpchklog.setWpchkId(new Long(this.inCheckpointId));
//        Date actualDate = date;
//        try {
//            java.text.DateFormat df = new java.text.SimpleDateFormat("yyyyMMdd");
//            actualDate = df.parse(this.TXT_DeliveryDate.getValueText());
//        }
//        catch (ParseException ex) {
//            actualDate = date;
//        }
//
//        dtoPwWpchklog.setWpchklogsActchk(actualDate);

//        dtoPwWpchklog.setWpchklogsReason(txtResult.getText());
        dtoPwWpchklog.setWpchklogsFrom(Global.userId);
    }

    public boolean validateData() {
        if (dteDeliveryDate.getValueText().trim().equals("") == false) {
            if (comDate.checkDate(dteDeliveryDate.getValueText()) == false) {
                comMSG.dispErrorDialog("日期格式有错,正确格式为yyyyMMdd.");
                dteDeliveryDate.setErrorField(true);
                dteDeliveryDate.requestFocus();
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        FPW01022CheckpointLog _VWPwWpCheckpointLog = new FPW01022CheckpointLog();
        Parameter param = new Parameter();
        param.put("inCheckpointId", "34");
        _VWPwWpCheckpointLog.setParameter(param);

        TestPanelParam.show(_VWPwWpCheckpointLog);
    }

}
