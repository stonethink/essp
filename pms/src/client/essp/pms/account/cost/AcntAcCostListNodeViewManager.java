package client.essp.pms.account.cost;


import client.framework.view.vwcomp.NodeViewManager;

import java.awt.Font;
import c2s.dto.IDto;
import client.framework.view.common.DefaultComp;
import c2s.essp.pms.account.cost.DtoAcntActivityCostSum;

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
public class AcntAcCostListNodeViewManager extends NodeViewManager {
    public Font getTextFont() {
        //合计数据使用粗体
        IDto node = this.getDto();
        if (node instanceof DtoAcntActivityCostSum) {

            return DefaultComp.DEFUALT_BOLD_FONT;
        }
        return this.textFont;
    }

}
