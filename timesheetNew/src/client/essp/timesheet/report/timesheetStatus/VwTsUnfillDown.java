/*
 * Created on 2007-11-30
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
/**
 * <p>Title: </p>
 *
 * <p>Description:VwTSStatusDown </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tubaohui
 * @version 1.0
 */
public class VwTsUnfillDown extends VwTsStatusDownBase{
    Object[][] configs = null;

    public VwTsUnfillDown() {
        try {
            jInit();
        } catch (Exception ex) {
        }
    }

    //初始化
    public void jInit() throws Exception {
        VWJText text = new VWJText();
        configs = new Object[][] { 
                {"rsid.timesheet.isfillts","isFillTimesheet",VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.employeeId","empId", VMColumnConfig.UNEDITABLE, text,Boolean.TRUE},
                {"rsid.timesheet.employeeName","empName", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.deptCode", "acntName", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.resManagerId", "resManagerId", VMColumnConfig.UNEDITABLE, text,Boolean.TRUE},
                {"rsid.timesheet.resourceManager", "resManagerName", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                {"rsid.timesheet.tsBegin","begin", VMColumnConfig.UNEDITABLE, new VWJDate(),Boolean.FALSE},
                {"rsid.timesheet.tsEnd","end", VMColumnConfig.UNEDITABLE, new VWJDate(),Boolean.FALSE},
      };
        super.jbInit(configs, DtoBase.class, new LevelNodeViewManager());
       
        // 设置初始列宽
        JTableHeader header = this.getTable().getTableHeader();
        //可排序
        this.getTable().setSortable(true);
        TableColumnModel tcModel = header.getColumnModel();
        tcModel.getColumn(0).setPreferredWidth(80);
        tcModel.getColumn(1).setPreferredWidth(60);
        tcModel.getColumn(2).setPreferredWidth(80);
        tcModel.getColumn(3).setPreferredWidth(60);
        tcModel.getColumn(4).setPreferredWidth(80);
        tcModel.getColumn(5).setPreferredWidth(80);
        tcModel.getColumn(6).setPreferredWidth(80);
        tcModel.getColumn(7).setPreferredWidth(80);
    }
}

