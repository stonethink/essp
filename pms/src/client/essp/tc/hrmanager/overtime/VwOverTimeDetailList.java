package client.essp.tc.hrmanager.overtime;

import java.util.List;

import c2s.essp.tc.overtime.DtoOverTimeDetail;
import client.essp.common.view.VWTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import java.util.ArrayList;
import client.framework.view.vwcomp.VWJComboBox;
import c2s.essp.tc.overtime.DtoOverTime;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJTable;

public class VwOverTimeDetailList extends VWTableWorkArea {
    private List detailList;
    private OverTimeDetailTableModel model;
    public VwOverTimeDetailList() {
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
                             {"Day", "overtimeDay",VMColumnConfig.UNEDITABLE, inputDate},
                             {"OverTime Hours", "hours",VMColumnConfig.EDITABLE, inputHours},
                             {"Shifted Hours", "shiftHours",VMColumnConfig.EDITABLE, inputHours},
                             {"Payed Hours", "payedHours",VMColumnConfig.EDITABLE, inputHours},
                             {"Payed Way", "payedWay",VMColumnConfig.EDITABLE, inputPayWay},
                             {"Remark", "remark",VMColumnConfig.EDITABLE, new VWJText()}
        };
        try {
            model = new OverTimeDetailTableModel(configs);
            table = new VWJTable(model);
            model.setDtoType(DtoOverTimeDetail.class);
            this.add(table.getScrollPane(), null);
//            super.jbInit(configs, DtoOverTimeDetail.class);
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
            DtoOverTimeDetail detail = (DtoOverTimeDetail) detailList.get(i);
            Double hours = detail.getHours();
            Double shilfHours = detail.getShiftHours();
            Double payHours = detail.getPayedHours();
            double gap = (hours == null ? 0D : hours.doubleValue()) -
                         (shilfHours == null ? 0D : shilfHours.doubleValue()) -
                         (payHours == null ? 0D : payHours.doubleValue());
            if(gap < 0){
                msg.append("Line[" + i + "]:The sum of shifted hours and payed hours can not be larger than total hours!\n");
                isError = true;
            }
            String payWay = detail.getPayedWay();
            if(payHours != null && payHours.doubleValue() != 0D && (payWay == null || "".equals(payWay))){
                msg.append("Line[" + i + "]:Please select pay way!\n");
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
            DtoOverTimeDetail detail = (DtoOverTimeDetail) detailList.get(i);
            if(detail.isChanged())
                return true;
        }
        return false;
    }
}
