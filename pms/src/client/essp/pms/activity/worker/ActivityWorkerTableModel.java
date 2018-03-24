package client.essp.pms.activity.worker;

import client.framework.model.VMTable2;
import org.apache.log4j.Category;
import c2s.essp.pms.wbs.DtoActivityWorker;
import client.framework.model.VMColumnConfig;
import c2s.dto.IDto;

public class ActivityWorkerTableModel extends VMTable2 {

    public ActivityWorkerTableModel(Object[][] configs){
        super(configs);
    }
    static Category log = Category.getInstance("client");

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        VMColumnConfig columnConfig = (VMColumnConfig)this.getColumnConfigs().
                                      get(columnIndex);
        String dataName = columnConfig.getDataName();
        DtoActivityWorker selected = (DtoActivityWorker) this.getRow(rowIndex);
        Long actualWorkTime = selected.getActualWorkTime();
        Long acCompleteTime = selected.getEtcWorkTime();
        Long inputTime = null;
        if("etcWorkTime".equals(dataName)){
            inputTime = (Long)aValue;
            if(inputTime != null && actualWorkTime != null &&
               inputTime.longValue() < actualWorkTime.longValue()){
                selected.setEtcWorkTime(actualWorkTime);
                selected.setOp(IDto.OP_MODIFY);
            }else{
                super.setValueAt(aValue, rowIndex, columnIndex);
            }
        }else if("actualWorkTime".equals(dataName)){
            inputTime = (Long)aValue;
            if(inputTime != null && acCompleteTime != null &&
               inputTime.longValue() > acCompleteTime.longValue() ){
                selected.setActualWorkTime(inputTime);
                selected.setEtcWorkTime(inputTime);
                selected.setOp(IDto.OP_MODIFY);
             }else{
                 super.setValueAt(aValue, rowIndex, columnIndex);
             }
        }else{
            super.setValueAt(aValue, rowIndex, columnIndex);
        }
        this.fireTableDataChanged();
    }
}
