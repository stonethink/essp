package client.essp.cbs.cost.labor;

import java.math.BigDecimal;

import c2s.dto.IDto;
import c2s.essp.cbs.cost.DtoResCostItem;
import c2s.essp.cbs.cost.DtoResCostSum;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;

public class LaborCostTableModel extends VMTable2 {
    public LaborCostTableModel(Object[][] configs){
        super(configs);
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        VMColumnConfig columnConfig = (VMColumnConfig)this.getColumnConfigs().
                                      get(columnIndex);
        String dataName = columnConfig.getDataName();
        if("costUnitNum".equals(dataName)){
            double inputUnitNum = ((BigDecimal) aValue).doubleValue();
            DtoResCostItem laborCost = (DtoResCostItem) this.getRow(rowIndex);
            Double costUnitNum = laborCost.getCostUnitNum();
            DtoResCostSum sum = (DtoResCostSum) this.getRow(0);
            if(sum != null){
                Double costUnitNumSum = sum.getCostUnitNum();
                if(costUnitNumSum == null){
                    sum.setCostUnitNum(new Double(inputUnitNum));
                    double costAmt = inputUnitNum * laborCost.getPrice().doubleValue();
                    sum.setCostAmt(new Double(costAmt));
                }else if(costUnitNum != null ){
                    double temp = costUnitNumSum.doubleValue() + inputUnitNum - costUnitNum.doubleValue();
                    sum.setCostUnitNum(new Double(temp));
                    temp = sum.getCostAmt().doubleValue() + (inputUnitNum - costUnitNum.doubleValue()) * laborCost.getPrice().doubleValue();
                    sum.setCostAmt(new Double(temp));
                }
                sum.setOp(IDto.OP_MODIFY);
            }
            super.setValueAt(aValue,rowIndex,columnIndex);
        }else{
            super.setValueAt(aValue,rowIndex,columnIndex);
        }
        this.fireTableDataChanged();
    }
}
