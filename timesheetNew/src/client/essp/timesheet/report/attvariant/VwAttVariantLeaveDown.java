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

public class VwAttVariantLeaveDown extends VwAttVariantDownBase{
    public VwAttVariantLeaveDown() {
        try {
            jInit();
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    //��ʼ��
    public void jInit() throws Exception {
        VWJText text = new VWJText();
        Object[][]  configs = new Object[][] {
                {"rsid.timesheet.employeeId","loginId",VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.empName","fullName",VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.date","attDate",VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.tsunitcode", "tsUnitCode", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.tsLeaveCode", "tsCode", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.tsLeaveHours","tsHours", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.hrunitcode", "hrUnitCode", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.hrLeaveCode", "hrCode", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.hrLeaveHours", "hrHours", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
      };
        super.jbInit(configs, DtoAttVariant.class);

        //���ó�ʼ�п�
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
        tcModel.getColumn(8).setPreferredWidth(80);
        
//      ������
        this.getTable().setSortable(true);
        VwVariantRender.setSumLevelRender(this);
    }
    
}

