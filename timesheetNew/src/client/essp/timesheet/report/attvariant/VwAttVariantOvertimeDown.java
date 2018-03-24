/*
 * Created on 2008-2-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.attvariant;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import c2s.essp.timesheet.report.DtoAttVariant;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJText;


public class VwAttVariantOvertimeDown extends VwAttVariantDownBase{
    public VwAttVariantOvertimeDown() {
        try {
            jInit();
        } catch (Exception ex) {
        }
    }

    //初始化
    public void jInit() throws Exception {
        VWJText text = new VWJText();
        Object[][] configs = new Object[][] {
                {"rsid.timesheet.employeeId","loginId",VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.empName","fullName",VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.date","attDate",VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.tsunitcode", "tsUnitCode", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.tsprjtid", "tsCode", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.tsothours","tsHours", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.hrunitcode", "hrUnitCode", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.hrprjid", "hrCode", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.hrothours", "hrHours", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
      };
        super.jbInit(configs, DtoAttVariant.class);
        //设置初始列宽
        JTableHeader header = this.getTable().getTableHeader();

        TableColumnModel tcModel = header.getColumnModel();
        tcModel.getColumn(0).setPreferredWidth(60);
        tcModel.getColumn(1).setPreferredWidth(80);
        tcModel.getColumn(2).setPreferredWidth(80);
        tcModel.getColumn(3).setPreferredWidth(80);
        tcModel.getColumn(4).setPreferredWidth(80);
        tcModel.getColumn(5).setPreferredWidth(80);
        tcModel.getColumn(6).setPreferredWidth(80);
        tcModel.getColumn(7).setPreferredWidth(80);
        
//      可排序
        this.getTable().setSortable(true);
        VwVariantRender.setSumLevelRender(this);
        
    }

}

