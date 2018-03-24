/*
 * Created on 2008-3-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.timesheetStatus;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import c2s.dto.DtoBase;
import client.essp.timesheet.common.LevelNodeViewManager;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJText;

public class VwTsActiveDown  extends VwTsStatusDownBase{
    private Object[][] configs = null;

    public VwTsActiveDown() {
        try {
            jInit();
        } catch (Exception ex) {
        }
    }

    //��ʼ��
    public void jInit() throws Exception {
        VWJText text = new VWJText();
        configs = new Object[][] { 
                {"rsid.timesheet.isfillts","isFillTimesheet",VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.employeeId","empId", VMColumnConfig.UNEDITABLE, text,Boolean.TRUE},
                {"rsid.timesheet.employeeName","empName", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.accountName","acntName",VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.deptCode", "unitCode", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.accountManagerId", "managerId", VMColumnConfig.UNEDITABLE, text,Boolean.TRUE},
                {"rsid.timesheet.accountManager", "managerName", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.resManagerId", "resManagerId", VMColumnConfig.UNEDITABLE, text,Boolean.TRUE},
                {"rsid.timesheet.resourceManager", "resManagerName", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.tsBegin","begin", VMColumnConfig.UNEDITABLE, new VWJDate(),Boolean.FALSE},
                {"rsid.timesheet.tsEnd","end", VMColumnConfig.UNEDITABLE, new VWJDate(),Boolean.FALSE},
                {"rsid.common.status","status", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
        };
        super.jbInit(configs, DtoBase.class, new LevelNodeViewManager());
        //���ó�ʼ�п�
        JTableHeader header = this.getTable().getTableHeader();
        TableColumnModel tcModel = header.getColumnModel();
//      ������
        this.getTable().setSortable(true);
        tcModel.getColumn(0).setPreferredWidth(60);
        tcModel.getColumn(1).setPreferredWidth(60);
        tcModel.getColumn(2).setPreferredWidth(60);
        tcModel.getColumn(3).setPreferredWidth(60);
        tcModel.getColumn(4).setPreferredWidth(60);
        tcModel.getColumn(5).setPreferredWidth(60);
        tcModel.getColumn(6).setPreferredWidth(60);
        tcModel.getColumn(7).setPreferredWidth(60);
        tcModel.getColumn(8).setPreferredWidth(60);
        tcModel.getColumn(9).setPreferredWidth(40);
        tcModel.getColumn(10).setPreferredWidth(40);
        tcModel.getColumn(11).setPreferredWidth(40);
    }
}
