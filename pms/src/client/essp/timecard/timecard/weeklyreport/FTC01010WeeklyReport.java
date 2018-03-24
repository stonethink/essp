package client.essp.timecard.timecard.weeklyreport;

import c2s.essp.timecard.timecard.DtoPwWkitem;

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


public class FTC01010WeeklyReport extends VWWorkArea implements IVWWorkArea {
    private boolean                refreshFlag     = true;
    private WeeklyReportTableModel currTableModel  = new WeeklyReportTableModel(DtoPwWkitem.class);
    private VWJTable               currTable       = new VWJTable(currTableModel);
    private java.util.List         dtoPwWkitemList = new ArrayList();

    public FTC01010WeeklyReport() {
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
        this.dtoPwWkitemList = (java.util.List) para.get("DtoPwWkitemList");
        this.refreshFlag     = true;
    }

    public void refreshWorkArea() {
        if (this.refreshFlag == true) {
            this.refreshFlag = false;
            currTableModel.setRows(dtoPwWkitemList);
            currTableModel.fireTableDataChanged();
        }
    }
}


class WeeklyReportTableModel extends VMTable2 {
    private static String[][] tblFields = {
                                              {
                                                  "PWP/WP Code", "wpCode",
                                                  Constant.UNEDITABLE
                                              },
                                              {
                                                  "PWP/WP Name", "wpName",
                                                  Constant.UNEDITABLE
                                              },
                                              {
                                                  "Work Item", "wkitemName",
                                                  Constant.UNEDITABLE
                                              },
                                              {
                                                  "Employee Name", "empName",
                                                  Constant.UNEDITABLE
                                              },
                                              {
                                                  "Date", "wkitemDate",
                                                  Constant.UNEDITABLE
                                              },
                                              {
                                                  "Start Time",
                                                  "wkitemStarttime",
                                                  Constant.UNEDITABLE
                                              },
                                              {
                                                  "Finish Time",
                                                  "wkitemFinishtime",
                                                  Constant.UNEDITABLE
                                              },
                                              {
                                                  "Work Hours", "wkitemWkhours",
                                                  Constant.UNEDITABLE
                                              }
                                          };
    private VWJText           vwjtext         = new VWJText();
    private VWJDate           vwjdate         = new VWJDate();
    private VWJDate           vwjtime         = new VWJDate();
    private VWJReal           vwjreal         = new VWJReal();
    private Font              tableFontArial  = new Font("Arial", Font.PLAIN, 9);
    private VWJText           vwjtextSongti   = new VWJText();
    private Font              tableFontSongti = new Font("宋体", Font.PLAIN, 9);

    public WeeklyReportTableModel(Class dtoClass) {
        super(tblFields);

        vwjtext.setFont(tableFontArial);
        vwjtextSongti.setFont(tableFontSongti);
        vwjdate.setFont(tableFontArial);
        vwjtime.setFont(tableFontArial);
        vwjreal.setFont(tableFontArial);

        vwjdate.setDataType(Constant.DATE_YYYYMMDD);
        vwjtime.setDataType(Constant.DATE_HHMM);

        vwjreal.setMaxInputIntegerDigit(10);
        vwjreal.setMaxInputDecimalDigit(2);

        this.getColumnConfigByDataName("wpCode").setComponent(vwjtext);
        this.getColumnConfigByDataName("wpName").setComponent(vwjtextSongti);
        this.getColumnConfigByDataName("wkitemName").setComponent(vwjtextSongti);
        this.getColumnConfigByDataName("empName").setComponent(vwjtext);
        this.getColumnConfigByDataName("wkitemDate").setComponent(vwjdate);
        this.getColumnConfigByDataName("wkitemStarttime").setComponent(vwjtime);
        this.getColumnConfigByDataName("wkitemFinishtime").setComponent(vwjtime);
        this.getColumnConfigByDataName("wkitemWkhours").setComponent(vwjreal);

        super.setDtoType(dtoClass);
    }
}
