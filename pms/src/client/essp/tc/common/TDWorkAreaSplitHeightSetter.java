package client.essp.tc.common;

import client.essp.common.view.VWTDWorkArea;

public class TDWorkAreaSplitHeightSetter {

    /**����orig�ķָ����߶ȵ���dest�ķָ����߶ȣ�ʹ���ߵķָ���λ�ñ��ֵȸ�*/
    public static void set(VWTDWorkArea dest, VWTDWorkArea orig) {
        int origH = orig.getSplitPane().getDividerLocation();
        int destH = dest.getSplitPane().getDividerLocation();
        if( destH != origH ){
            dest.getSplitPane().setDividerLocation(origH);
        }
    }
}
