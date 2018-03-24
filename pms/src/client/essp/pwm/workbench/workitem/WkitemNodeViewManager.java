package client.essp.pwm.workbench.workitem;

import client.framework.view.vwcomp.NodeViewManager;
import c2s.essp.pwm.workbench.DtoPwWkitem;
import java.awt.Color;

public class WkitemNodeViewManager extends NodeViewManager{
    final static Color DLRPT_FOREGROUND = new Color(0,128,0);

    public DtoPwWkitem getDataBean() {
        if( getDto() != null ){
            return (DtoPwWkitem)getDto();
        }else{
            return null;
        }
    }

    public Color getForeground(){
        if( getDataBean() != null ){
            if( getDataBean().isdlrpt() ){
                return DLRPT_FOREGROUND;
            }
        }

        return super.getForeground();
    }
}
