package client.essp.cbs.budget.labor;

import java.awt.Font;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import c2s.dto.IDto;
import c2s.essp.cbs.budget.DtoResBudgetItem;
import c2s.essp.cbs.budget.DtoResBudgtSum;
import client.framework.view.common.DefaultComp;
import client.framework.view.vwcomp.NodeViewManager;

public class LaborBgtNodeViewManager extends NodeViewManager {
    public Font getTextFont() {
        //合计数据使用粗体,数字不为0的使用斜体
        IDto node = this.getDto();
        if (node instanceof DtoResBudgtSum) {
            return DefaultComp.DEFUALT_BOLD_FONT;
        } else {
            DtoResBudgetItem item = (DtoResBudgetItem) node;
            Map monthMap = item.getHmMonthUnitNum();
            if(monthMap != null){
                Collection values = monthMap.values();
                Iterator it = values.iterator();
                while(it.hasNext()){
                    try{
                        Double d = (Double) it.next();
                        if(d != null && d.doubleValue() != 0.0d)
                            return DefaultComp.DEFUALT_ITALIC_FONT;
                    }catch(Exception ex){
                        return this.textFont;
                    }
                }
            }
        }
        return this.textFont;
    }
}
