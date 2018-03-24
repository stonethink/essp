/*
 * Created on 2008-4-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.attvariant;

import java.awt.event.ActionEvent;
import java.util.List;
import c2s.essp.timesheet.report.DtoAttVariant;
import c2s.essp.timesheet.report.DtoAttVariantQuery;
import client.essp.common.view.VWTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;

import com.wits.util.Parameter;

public class VwAttVariantLeaveList extends VWTableWorkArea implements IVWPopupEditorEvent{
    
    private List<DtoAttVariant> resultList;
    
    public VwAttVariantLeaveList() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error(e);
        }
    }
    private void jbInit() throws Exception {
        VWJReal real = new VWJReal();
        VWJText text = new VWJText();
        real.setCanNegative(true);
        real.setMaxInputDecimalDigit(2);
        Object[][] configs = new Object[][] { {"rsid.timesheet.checked", "checked",
                            VMColumnConfig.EDITABLE, new VWJCheckBox(),Boolean.FALSE},
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
       //可排序
       this.getTable().setSortable(true);
       VwVariantRender.setSumLevelRender(this);
    }
    
    /**
     * 参数设置
     */
    public void setParameter(Parameter param) {
        super.setParameter(param);
        resultList = (List)param.get(DtoAttVariantQuery.DTO_LEAVE_LIST);
    }
    
    /**
     * 刷新界面
     */
    protected void resetUI() {
        this.getModel().setRows(resultList);
        if(resultList != null && resultList.size() > 0){
            this.getTable().setSelectRow(0);
        }
    }
    
    public boolean onClickCancel(ActionEvent e) {
        return true;
    }
    public boolean onClickOK(ActionEvent e) {
        return true;
    }
}
