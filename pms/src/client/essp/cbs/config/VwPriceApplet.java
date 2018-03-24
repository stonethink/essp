package client.essp.cbs.config;

import client.essp.common.view.VWWorkArea;
import com.wits.util.Parameter;
import c2s.essp.cbs.CbsConstant;

public class VwPriceApplet extends VWWorkArea {
    VwPrice vwPrice;
    public VwPriceApplet(){
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void jbInit() throws Exception {
        vwPrice = new VwPrice();
        this.addTab("Price",vwPrice);
    }

    public void refreshWorkArea() {
        Parameter param = new Parameter();
        Long acntRid = new Long(0);
        String priceScope = CbsConstant.SCOPE_GLOBAL;
        param.put("acntRid",acntRid);
        param.put("priceScope",priceScope);
        vwPrice.setParameter(param);
        vwPrice.resetUI();
    }
}
