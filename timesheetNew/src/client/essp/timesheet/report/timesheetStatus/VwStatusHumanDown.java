/*
 * Created on 2008-2-20
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
import client.framework.view.vwcomp.VWJText;
/**
 * <p>Title: </p>
 *
 * <p>Description:VwStatusHumanDown </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tubaohui
 * @version 1.0
 */
public class VwStatusHumanDown extends VwTsStatusDownBase{
        private Object[][] configs = null;
    
        public VwStatusHumanDown() {
            try {
                jInit();
            } catch (Exception ex) {
            }
        }
    
        //初始化
        public void jInit() throws Exception {
            VWJText text = new VWJText();
            configs = new Object[][] { 
                    {"rsid.timesheet.tsBegin","begin", VMColumnConfig.UNEDITABLE, text},
                    {"rsid.timesheet.tsEnd","end", VMColumnConfig.UNEDITABLE, text},
                    {"rsid.timesheet.needFillNum","NeedFillNum",VMColumnConfig.UNEDITABLE, text},
                    {"rsid.timesheet.unfilledsheet","unfillNum",VMColumnConfig.UNEDITABLE, text},
                    {"rsid.timesheet.activesheet", "activeNum", VMColumnConfig.UNEDITABLE, text},
                    {"rsid.timesheet.submitsheet", "submittedNum", VMColumnConfig.UNEDITABLE, text},
                    {"rsid.timesheet.pmappsheet", "pmApprovedNum", VMColumnConfig.UNEDITABLE, text},
                    {"rsid.timesheet.rmappsheet", "rmApprovedNum", VMColumnConfig.UNEDITABLE, text},
                    {"rsid.timesheet.appdsheet", "approvedNum", VMColumnConfig.UNEDITABLE, text},
                    {"rsid.timesheet.rejectedsheet","rejectedNum", VMColumnConfig.UNEDITABLE, text},
                    {"rsid.timesheet.sum","sum", VMColumnConfig.UNEDITABLE, text},
            };
            super.jbInit(configs, DtoBase.class,new LevelNodeViewManager());
            //设置初始列宽
            JTableHeader header = this.getTable().getTableHeader();
    //      可排序
            this.getTable().setSortable(true);
            TableColumnModel tcModel = header.getColumnModel();
            tcModel.getColumn(0).setPreferredWidth(100);
            tcModel.getColumn(1).setPreferredWidth(80);
            tcModel.getColumn(2).setPreferredWidth(80);
            tcModel.getColumn(3).setPreferredWidth(80);
            tcModel.getColumn(4).setPreferredWidth(80);
            tcModel.getColumn(5).setPreferredWidth(80);
            tcModel.getColumn(6).setPreferredWidth(80);
            tcModel.getColumn(7).setPreferredWidth(60);
            tcModel.getColumn(8).setPreferredWidth(60);
            tcModel.getColumn(9).setPreferredWidth(60);
        }
}
