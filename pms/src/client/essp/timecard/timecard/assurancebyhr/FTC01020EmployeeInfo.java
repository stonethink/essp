package client.essp.timecard.timecard.assurancebyhr;

import c2s.essp.timecard.timecard.DtoTcTimecard;

import client.essp.common.view.VWWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMTable2;
import client.framework.view.IVWWorkArea;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;

import com.wits.util.Parameter;

import java.awt.BorderLayout;
import java.awt.Font;

import java.util.ArrayList;

import javax.swing.JScrollPane;


public class FTC01020EmployeeInfo extends VWWorkArea implements IVWWorkArea {
    private boolean            refreshFlag       = true;
    private TimeCardTableModel currTableModel    = new TimeCardTableModel(DtoTcTimecard.class);
    private VWJTable           currTable         = new VWJTable(currTableModel);
    private java.util.List     dtoTcTimecardList = new ArrayList();

    public FTC01020EmployeeInfo() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() {
        this.setLayout(new BorderLayout());
        currTable.setRowSelectionAllowed(false);
        currTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane jScrollPane = new JScrollPane();

        jScrollPane.getViewport().add(currTable, null); //给table加滚动条
        this.add(jScrollPane, BorderLayout.CENTER);
    }

    public void setParameter(Parameter para) {
        this.dtoTcTimecardList = (java.util.List) para.get("DtoTcTimecardList");
        this.refreshFlag       = true;
    }

    public void refreshWorkArea() {
        if (this.refreshFlag == true) {
            this.refreshFlag = false;
            currTableModel.setRows(dtoTcTimecardList);
            currTableModel.fireTableDataChanged();
        }
    }
}


class TimeCardTableModel extends VMTable2 {
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
                                                  Constant.UNEDITABLE
                                              },
                                              {
                                                  "Finish", "tmcEmpFinish",
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
                                              }
                                          };
    private VWJText           vwjtext        = new VWJText();
    private VWJDate           vwjdate        = new VWJDate();
    private VWJReal           vwjreal        = new VWJReal();
    private Font              tableFontArial = new Font("Arial", Font.PLAIN, 9);

    public TimeCardTableModel(Class dtoClass) {
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
}
