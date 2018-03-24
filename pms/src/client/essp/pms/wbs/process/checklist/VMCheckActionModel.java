package client.essp.pms.wbs.process.checklist;

import java.util.List;

import c2s.essp.pms.wbs.DtoWbsActivity;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;
import c2s.essp.pms.qa.DtoQaCheckAction;
import java.util.Date;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VMCheckActionModel extends VMTable2 {

    private DtoWbsActivity dtoWbsActivity = null;

    public VMCheckActionModel(Object[][] configs) {
        super(configs);
    }

    //set belong activity, to check plan period.
    public void setDtoWbsActivity(DtoWbsActivity dtoWbsActivity) {
        this.dtoWbsActivity = dtoWbsActivity;
    }
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        VMColumnConfig columnConfig = (VMColumnConfig) getColumnConfigs().get(
                   columnIndex);
       String setName = columnConfig.getDataName(); //columnConfig.getName();//列的名字
       if("planDate".equals(setName)) {
           DtoQaCheckAction dto = (DtoQaCheckAction) this.getRow(rowIndex);
           if(VwCheckActionList.chkActionOccasion[4].equals(dto.getOccasion())) {
               return true;
           } else {
               return false;
           }
       } else {
           return super.isCellEditable(rowIndex, columnIndex);
       }
    }

     public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
        List columnConfigs = getColumnConfigs();
        VMColumnConfig columnConfig = (VMColumnConfig) columnConfigs.get(
                   columnIndex);
       String setName = columnConfig.getDataName(); //columnConfig.getName();//列的名字

       //if occasion is plan/actual start/finish, get parent plan/actual start/finish date
       if("occasion".equals(setName)) {
           int dataColumn = columnConfigs.indexOf(this.getColumnConfig("Plan Date"));
           Date planDate = VwCheckActionList.getQaCheckAtionPlanDate(aValue.toString(),dtoWbsActivity);
           if(planDate != null) {
                super.setValueAt(planDate, rowIndex, dataColumn);
           }
       }

    }

}
