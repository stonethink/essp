package client.essp.tc.hrmanager.leave;

import client.essp.common.view.VWTableWorkArea;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJText;
import client.framework.model.VMColumnConfig;
import c2s.essp.tc.overtime.DtoOverTime;
import java.util.ArrayList;
import client.framework.view.vwcomp.VWJReal;
import java.util.List;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJComboBox;
import c2s.essp.tc.leave.DtoLeaveDetail;
import client.framework.view.vwcomp.VWJTable;
import client.essp.tc.hrmanager.overtime.DetailChangedListener;

public class VwLeaveDetailList extends VWTableWorkArea {
    private List detailList;
    private LeaveDetailTableModel model;
    public VwLeaveDetailList() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void jbInit() throws Exception {
        VWJDate inputDate = new VWJDate();
        inputDate.setCanSelect(true);
        VWJReal inputHours = new VWJReal();
        inputHours.setMaxInputIntegerDigit(8);
        inputHours.setMaxInputDecimalDigit(2);
        VWJComboBox inputPayWay = new VWJComboBox();
        inputPayWay.setVMComboBox(DtoOverTime.PAY_WAYS);
        Object[][] configs = new Object[][]{
                             {"Day", "leaveDay",VMColumnConfig.UNEDITABLE, inputDate},
                             {"Leave Hours", "hours",VMColumnConfig.EDITABLE, inputHours},
                             {"Remark", "remark",VMColumnConfig.EDITABLE, new VWJText()}
        };
        try {
            model = new LeaveDetailTableModel(configs);
            model.setDtoType(DtoLeaveDetail.class);
            table = new VWJTable(model);
            this.add(table.getScrollPane(), null);
//            super.jbInit(configs, DtoLeaveDetail.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void addDetailChangeListener(DetailChangedListener l){
        model.addDetailChangeListener(l);
    }
    public void removeDetailChangeListener(DetailChangedListener l){
        model.removeDetailChangeListener(l);
    }
    public void resetUI(){
        if(detailList == null)
            detailList = new ArrayList();
        this.getTable().setRows(detailList);
        model.fireTableDataChanged();
    }

    public List getDetailList() {
        return detailList;
    }

    public void setDetailList(List detailList) {
        this.detailList = detailList;
    }

    boolean validateData(){
        if(detailList == null || detailList.size() == 0)
            return true;
        StringBuffer msg = new StringBuffer();
        boolean isError = false;
        for(int i = 0;i < detailList.size() ;i ++){
            DtoLeaveDetail detail = (DtoLeaveDetail) detailList.get(i);
            Double hours = detail.getHours();
            if(hours == null || (hours != null && hours.doubleValue() <= 0D)){
                msg.append("Line[" + i + "]:The leave hours must be larger than 0!\n");
                isError = true;
            }
        }
        if(isError)
            comMSG.dispErrorDialog(msg.toString());
        return !isError;
    }

    boolean checkModified(){
        if(detailList == null || detailList.size() == 0)
            return false;
        for(int i = 0;i < detailList.size() ;i ++){
            DtoLeaveDetail detail = (DtoLeaveDetail) detailList.get(i);
            if(detail.isChanged())
                return true;
        }
        return false;
    }
}
