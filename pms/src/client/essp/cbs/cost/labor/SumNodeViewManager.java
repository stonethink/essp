package client.essp.cbs.cost.labor;

import client.framework.view.vwcomp.NodeViewManager;
import java.awt.Font;
import c2s.dto.IDto;
import c2s.essp.cbs.cost.DtoResCostSum;
import c2s.essp.cbs.budget.DtoResBudgtSum;
import client.framework.view.common.DefaultComp;
import c2s.essp.cbs.cost.DtoActivityCostSum;
import c2s.essp.cbs.cost.DtoCostItemSum;

public class SumNodeViewManager extends NodeViewManager {
    public Font getTextFont() {
        //合计数据使用粗体
        IDto node = this.getDto();
        if(node instanceof DtoResCostSum ||
           node instanceof DtoResBudgtSum ||
           node instanceof DtoActivityCostSum ||
           node instanceof DtoCostItemSum) {
            return DefaultComp.DEFUALT_BOLD_FONT;
        }
        return this.textFont;
    }
}
