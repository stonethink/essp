package client.essp.pms.account.cost;

import java.awt.Font;

import c2s.dto.IDto;
import c2s.essp.pms.account.cost.DtoSum;
import client.framework.view.common.DefaultComp;
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
import client.framework.view.vwcomp.NodeViewManager;

public class AcntCostListNodeViewManager extends NodeViewManager {
    public Font getTextFont() {
        //�ϼ�����ʹ�ô���
        IDto node = this.getDto();
        if (node instanceof DtoSum) {

            return DefaultComp.DEFUALT_BOLD_FONT;
        }
        return this.textFont;
    }

}
