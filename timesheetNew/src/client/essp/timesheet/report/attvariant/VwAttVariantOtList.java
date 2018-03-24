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
import c2s.essp.timesheet.report.DtoHumanTimes;
import client.essp.common.view.VWTableWorkArea;
import client.essp.timesheet.report.humanreport.VWJ2RealNumForReport;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;

public class VwAttVariantOtList extends VWTableWorkArea implements IVWPopupEditorEvent{
    
    private List<DtoHumanTimes> resultList;
    
    public VwAttVariantOtList() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error(e);
        }
    }
    
    private void jbInit() throws Exception {
        VWJ2RealNumForReport twoReal = new VWJ2RealNumForReport();
        twoReal.setMaxInputDecimalDigit(2);
        twoReal.setMaxInputIntegerDigit(6);
        twoReal.setCanNegative(true);
        twoReal.setCanNegative2(true);
        twoReal.setCanInputSecondNum(true);
        VWJReal real = new VWJReal();
        real.setCanNegative(true);
        real.setMaxInputDecimalDigit(2);
        VWJText text = new VWJText();
        Object[][] configs = new Object[][] { {"rsid.timesheet.checked", "checked",
                            VMColumnConfig.EDITABLE, new VWJCheckBox(), Boolean.FALSE},
                            {"rsid.timesheet.employeeId","loginId",VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                            {"rsid.timesheet.empName","fullName",VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                            {"rsid.timesheet.date","attDate",VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                            {"rsid.timesheet.tsunitcode", "tsUnitCode", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                            {"rsid.timesheet.tsprjtid", "tsCode", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                            {"rsid.timesheet.tsothours","tsHours", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                            {"rsid.timesheet.hrunitcode", "hrUnitCode", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                            {"rsid.timesheet.hrprjid", "hrCode", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE},
                            {"rsid.timesheet.hrothours", "hrHours", VMColumnConfig.UNEDITABLE, text,Boolean.FALSE}
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
        resultList = (List)param.get(DtoAttVariantQuery.DTO_OVERTIME_LIST);
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
