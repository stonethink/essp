package client.essp.tc.hrmanager.overtime;

import client.framework.model.VMTable2;
import client.framework.model.VMColumnConfig;
import java.util.List;
import c2s.essp.tc.overtime.DtoOverTimeDetail;
import java.util.ArrayList;
import java.util.Collections;

public class OverTimeDetailTableModel extends VMTable2 {
    private List detailChangedListeners = Collections.synchronizedList(new ArrayList());

    public  OverTimeDetailTableModel(Object[][] configs){
        super(configs);
    }
    public void addDetailChangeListener(DetailChangedListener l){
        detailChangedListeners.add(l);
    }
    public void removeDetailChangeListener(DetailChangedListener l){
        detailChangedListeners.remove(l);
    }
    public void fireDetailChanged(){
        if(detailChangedListeners == null || detailChangedListeners.size() == 0)
            return;
        for(int i = 0;i < detailChangedListeners.size() ;i ++){
            DetailChangedListener lis = (DetailChangedListener) detailChangedListeners.get(i);
            lis.proccessDetailChange();
        }
    }
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue,rowIndex,columnIndex);
        VMColumnConfig columnConfig = (VMColumnConfig)this.getColumnConfigs().
                                      get(columnIndex);
        String columnName = columnConfig.getDataName();
        //如果输入OverTime Hours栏位,重新计算总加班时间并刷新
        if("hours".equals(columnName)){
                fireDetailChanged();
        }
    }

}
