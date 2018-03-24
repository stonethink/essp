package client.essp.tc.common;

import client.essp.common.view.VWTDWorkArea;

public class TDWorkAreaSplitHeightSetter {

    /**根据orig的分隔条高度调整dest的分隔条高度，使两者的分隔条位置保持等高*/
    public static void set(VWTDWorkArea dest, VWTDWorkArea orig) {
        int origH = orig.getSplitPane().getDividerLocation();
        int destH = dest.getSplitPane().getDividerLocation();
        if( destH != origH ){
            dest.getSplitPane().setDividerLocation(origH);
        }
    }
}
