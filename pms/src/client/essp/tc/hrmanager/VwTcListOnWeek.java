package client.essp.tc.hrmanager;

import java.util.List;
import client.framework.model.VMColumnConfig;

public class VwTcListOnWeek extends VwTcListBase {
    public VwTcListOnWeek() {
        //OnWeek是区间和标准工时可修改
        List configs = getModel().getColumnConfigs();
        for (int i = 0; i < configs.size(); i++) {
            VMColumnConfig config = (VMColumnConfig) configs.get(i);
            String dataName = config.getName();
            if("Begin Period".equals(dataName) ||
               "End Period".equals(dataName) ||
                VMTableTc.STANDARD_TITLE.equals(dataName) ){
                 config.setEditable(VMColumnConfig.EDITABLE);
             }

        }
    }
}
