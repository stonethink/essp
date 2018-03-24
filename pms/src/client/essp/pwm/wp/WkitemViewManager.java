package client.essp.pwm.wp;

import java.awt.Color;

import c2s.essp.pwm.wp.DtoPwWkitem;
import client.framework.view.vwcomp.NodeViewManager;

public class WkitemViewManager extends NodeViewManager{
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
