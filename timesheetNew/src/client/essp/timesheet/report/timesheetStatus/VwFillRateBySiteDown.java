/*
 * Created on 2008-7-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.timesheetStatus;

import java.util.List;
import javax.swing.JButton;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import c2s.dto.DtoBase;
import c2s.essp.timesheet.report.DtoTsStatusQuery;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.timesheet.common.LevelNodeViewManager;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;

import com.wits.util.Parameter;

public class VwFillRateBySiteDown extends VwTsStatusDownBase{
        private Object[][] configs = null;
        private List resultList;
        private JButton buttonDisp = new JButton();
        public VwFillRateBySiteDown() {
            try {
                jInit();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    
        //初始化
        public void jInit() throws Exception {
            VWJText text = new VWJText();
            VWJReal real = new VWJReal();
            real.setMaxInputDecimalDigit(2);
            configs = new Object[][] { 
                    {"rsid.timesheet.deptCode","acntId", VMColumnConfig.UNEDITABLE, text},
                    {"rsid.timesheet.needFillEmpNum","needFillEmpNum", VMColumnConfig.UNEDITABLE, text},
                    {"rsid.timesheet.needFillNum","needFillNum",VMColumnConfig.UNEDITABLE, text},
                    {"rsid.timesheet.unfillSheets", "unfillNum", VMColumnConfig.UNEDITABLE, text},
                    {"rsid.timesheet.fillRate", "fillRate", VMColumnConfig.UNEDITABLE, real},
            };
            //设置初始列宽
            
            super.jbInit(configs, DtoBase.class, new LevelNodeViewManager());
            //设置初始列宽
            JTableHeader header = this.getTable().getTableHeader();
            TableColumnModel tcModel = header.getColumnModel();
            this.getTable().setSortable(true);
           // 可排序
            tcModel.getColumn(0).setPreferredWidth(100);
            tcModel.getColumn(1).setPreferredWidth(80);
            tcModel.getColumn(2).setPreferredWidth(80);
            tcModel.getColumn(3).setPreferredWidth(80);
            tcModel.getColumn(4).setPreferredWidth(80);
        }
        
        //參數傳遞
         public void setParameter(Parameter param) {
            resultList = (List)param.get(DtoTsStatusQuery.DTO_RESULT_LIST);
            super.setParameter(param);
        }
        
         //  resetUI
          protected void resetUI() {
            getTable().setRows(resultList);
            
         //  Display
            TableColumnChooseDisplay chooseDisplay =
                    new TableColumnChooseDisplay(getTable(), this);
            buttonDisp = chooseDisplay.getDisplayButton();
            this.getButtonPanel().addButton(buttonDisp);
        }
}