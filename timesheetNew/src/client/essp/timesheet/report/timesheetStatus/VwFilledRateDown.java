/*
 * Created on 2008-4-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.timesheetStatus;

import javax.swing.JButton;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import c2s.dto.DtoBase;
import c2s.dto.ITreeNode;
import c2s.essp.timesheet.report.DtoTsStatusQuery;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.view.VWTreeTableWorkArea;
import client.essp.timesheet.common.LevelNodeViewManager;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;

import com.wits.util.Parameter;

public class VwFilledRateDown extends VWTreeTableWorkArea{
    private Object[][] configs = null;
    private ITreeNode root;
    private JButton buttonDisp = new JButton();
    public VwFilledRateDown() {
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
                {"rsid.timesheet.deptCode","acntId", VMColumnConfig.EDITABLE, null},
                {"rsid.timesheet.needFillEmpNum","needFillEmpNum", VMColumnConfig.UNEDITABLE, text},
                {"rsid.timesheet.needFillNum","needFillNum",VMColumnConfig.UNEDITABLE, text},
                {"rsid.timesheet.unfillSheets", "unfillNum", VMColumnConfig.UNEDITABLE, text},
                {"rsid.timesheet.fillRate", "fillRate", VMColumnConfig.UNEDITABLE, real},
        };
        //设置初始列宽
        
        super.jbInit(configs, "acntId", DtoBase.class, new LevelNodeViewManager());
        //设置初始列宽
        JTableHeader header = this.getTreeTable().getTableHeader();
        TableColumnModel tcModel = header.getColumnModel();
       // 可排序
        tcModel.getColumn(0).setPreferredWidth(100);
        tcModel.getColumn(1).setPreferredWidth(80);
        tcModel.getColumn(2).setPreferredWidth(80);
        tcModel.getColumn(3).setPreferredWidth(80);
        tcModel.getColumn(4).setPreferredWidth(80);
    }
    //參數傳遞
    public void setParameter(Parameter param) {
       root = (ITreeNode)param.get(DtoTsStatusQuery.DTO_RESULT_LIST);
       super.setParameter(param);
   }
    
//  resetUI
    protected void resetUI() {
        getTreeTable().setRoot(root);
        
 //     Display
        TableColumnChooseDisplay chooseDisplay =
                new TableColumnChooseDisplay(this.getTreeTable(), this);
        buttonDisp = chooseDisplay.getDisplayButton();
        this.getButtonPanel().addButton(buttonDisp);
        
    }
}
