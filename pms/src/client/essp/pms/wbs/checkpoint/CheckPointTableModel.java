package client.essp.pms.wbs.checkpoint;

import c2s.dto.IDto;
import c2s.essp.pms.wbs.DtoCheckPoint;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;
import org.apache.log4j.Category;

public class CheckPointTableModel extends VMTable2 {

    public CheckPointTableModel(Object[][] configs){
        super(configs);
    }
    static Category log = Category.getInstance("client");

    public Object getValueAt(int rowIndex, int columnIndex) {
        VMColumnConfig columnConfig = (VMColumnConfig)this.getColumnConfigs().
                                      get(columnIndex);
        String dateName = columnConfig.getDataName();
        //如果不是completed栏位，使用父类的getValueAt()方法
        if(!dateName.equals("completed")){
            if(!dateName.equals("weight"))
                return super.getValueAt(rowIndex,columnIndex);
            //如果weight栏位值为空，返回1.00
            Object value =  super.getValueAt(rowIndex,columnIndex);
            if(value == null )
                return "1.00";
            return super.getValueAt(rowIndex,columnIndex);
        }
        DtoCheckPoint dtoBean = (DtoCheckPoint) getRow(rowIndex);
        if(DtoCheckPoint.COMPLETED.equals(dtoBean.getCompleted()))
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        VMColumnConfig columnConfig = (VMColumnConfig) this.getColumnConfigs().get(columnIndex);
        String dateName = columnConfig.getDataName();
        //如果不是completed栏位，使用父类的setValueAt()方法
        if(!dateName.equals("completed")){
//            if(!dateName.equals("weight")){
                super.setValueAt(aValue, rowIndex, columnIndex);
                return;
//            }
        }
        DtoCheckPoint dtoBean = (DtoCheckPoint) getRow(rowIndex);
        if("true".equalsIgnoreCase(aValue.toString()))
            dtoBean.setCompleted(DtoCheckPoint.COMPLETED);
        else
            dtoBean.setCompleted(DtoCheckPoint.UNCOMPLETED);
        dtoBean.setOp(IDto.OP_MODIFY);
    }


}
