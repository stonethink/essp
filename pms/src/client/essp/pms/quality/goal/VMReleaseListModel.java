package client.essp.pms.quality.goal;

import client.framework.model.VMTable2;
import client.framework.model.VMColumnConfig;
import c2s.essp.pms.quality.goal.DtoReleaseRecord;
import java.util.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VMReleaseListModel extends VMTable2 {

    public VMReleaseListModel(Object[][] configs) {
        super(configs);
    }


    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
        //get active column.
        List columnCfgList = this.getColumnConfigs();
        VMColumnConfig columnConfig = (VMColumnConfig)columnCfgList.
                                      get(columnIndex);
        String dateName = columnConfig.getDataName();
        //get logic info.
        DtoReleaseRecord dataBean = (DtoReleaseRecord)this.getRow(rowIndex);

        if ("size".equals(dateName) || "defects".equals(dateName)) {
            //Calculate qualification rate
            Long size = dataBean.getSize();
            Long defects = dataBean.getDefects();
            Double qualificationRate = new Double(0);
            if(size != null && defects != null && defects.equals(new Long(0)) == false) {
                qualificationRate = new Double(defects.doubleValue() / size.doubleValue());
                dataBean.setQualificationRate(qualificationRate);
                this.fireTableDataChanged();
            }
        }
    }
}
