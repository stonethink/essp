package client.essp.pms.account;

import c2s.dto.DtoUtil;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;
import client.framework.view.vwcomp.IVWComponent;

public class VMAccountListModel extends VMTable2 {

    public VMAccountListModel(Object[][] configs) {
        super(configs);
    }

    /**
     * copy from VMTable2
     * VMTable2会设置op标志位 ，而这里并不需要
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Object oldValue = getValueAt(rowIndex, columnIndex);

        VMColumnConfig columnConfig = (VMColumnConfig) getColumnConfigs().get(columnIndex);
        ( (IVWComponent) columnConfig.getComponent()).setUICValue(aValue);
        ( (IVWComponent) columnConfig.getComponent()).validateValue();

        String dataName = columnConfig.getDataName();
        try {
            Object dtoBean = getRow(rowIndex);
            if (oldValue != aValue) {
                DtoUtil.setProperty(dtoBean, dataName, aValue);
//                if (dtoBean instanceof IDto) {
//                    ( (IDto) dtoBean).setOp(IDto.OP_MODIFY);
//                }
            }
        } catch (Exception e) {
            //
            log.debug("", e);
        }
    }
    }
