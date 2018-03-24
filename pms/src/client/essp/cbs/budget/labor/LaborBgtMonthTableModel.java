package client.essp.cbs.budget.labor;

import java.util.List;

import c2s.essp.cbs.budget.DtoResBudgetItem;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;
import com.wits.util.StringUtil;
import c2s.essp.cbs.budget.DtoResBudgtSum;
import c2s.dto.IDto;

public class LaborBgtMonthTableModel extends VMTable2 {
    public  LaborBgtMonthTableModel(List configs){
        super();
        super.setColumnConfigs(configs);
    }
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(rowIndex == 0)//��һ�кϼƲ����޸�
            return false;
        else
            return super.isCellEditable(rowIndex,columnIndex);
    }
    public Object getValueAt(int rowIndex, int columnIndex) {
        List rows = this.getRows();
        if(rows == null)
            return null;
        VMColumnConfig columnConfig = (VMColumnConfig)this.getColumnConfigs().
                                      get(columnIndex);
        String month = columnConfig.getDataName();
        DtoResBudgetItem resBudget = (DtoResBudgetItem) getRow(rowIndex);
        Double amount =  resBudget.getMonthUnitNum(month);
        return amount == 0D ? null : amount;
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        List rows = this.getRows();
        if(rows == null)
            return ;
        VMColumnConfig columnConfig = (VMColumnConfig)this.getColumnConfigs().
                                      get(columnIndex);
        String month = columnConfig.getDataName();
        DtoResBudgetItem resBudget = (DtoResBudgetItem) getRow(rowIndex);
        Double oldNum = resBudget.getMonthUnitNum(month);
        Double inputNum = new Double(StringUtil.nvl(aValue));
        resBudget.setMonthUnitNum(month,inputNum);
        resBudget.getUnitNum();
        resBudget.getAmt();
        resBudget.setOp(IDto.OP_MODIFY);
        //���㵱�������������ܺ�:�ɵĺ�ֵ �� ��������ֵ + ���¾�ֵ
        DtoResBudgtSum sumBudget = (DtoResBudgtSum) getRow(0);
        Double oldUnitSum = sumBudget.getMonthUnitNum(month);
        double newUnitSum = oldUnitSum.doubleValue() + inputNum.doubleValue() - oldNum.doubleValue();
        sumBudget.setMonthUnitNum(month,new Double(newUnitSum));
        sumBudget.getUnitNum();

        //�����Ǯ���ܺ�:�ɵĺ�ֵ + (��������ֵ �� ���¾�ֵ)*�۸�
        Double oldAmt = sumBudget.getAmt();
        double newAmt = oldAmt.doubleValue() +( inputNum.doubleValue() - oldNum.doubleValue())*(resBudget.getPrice().doubleValue());
        sumBudget.setAmt(new Double(newAmt));
        fireTableDataChanged();
        /*
         *1.ÿ���޸ı���ڵ�ֵ�����õ�һ�е�Dto״̬Ϊ��MODIFY�����Դ����ж���������Ƿ��޸Ĺ�
         *2.ÿ���ύ���������ȷ�������������ֵΪ"NO_Change"
         */
        sumBudget.setOp(IDto.OP_MODIFY);
    }

    public Class getColumnClass(int columnIndex) {
        return Double.class;
    }
}
