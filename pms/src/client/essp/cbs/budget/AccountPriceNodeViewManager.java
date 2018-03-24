package client.essp.cbs.budget;

import client.framework.view.vwcomp.NodeViewManager;
import java.awt.Font;
import c2s.dto.IDto;
import c2s.essp.cbs.DtoPrice;
import client.framework.view.common.DefaultComp;
import c2s.essp.cbs.CbsConstant;

public class AccountPriceNodeViewManager extends NodeViewManager {
    public Font getTextFont() {
        //合计数据使用粗体
        IDto node = this.getDto();
        if(node instanceof DtoPrice) {
            DtoPrice price = (DtoPrice) node;
            if(CbsConstant.SCOPE_ACCOUNT.equals(price.getPriceScope()))
                return DefaultComp.DEFUALT_ITALIC_FONT;
        }
        return this.textFont;
    }

}
