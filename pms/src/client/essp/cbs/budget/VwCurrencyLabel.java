package client.essp.cbs.budget;

import java.awt.Dimension;

import client.framework.view.vwcomp.VWJLabel;
import java.awt.Rectangle;

public class VwCurrencyLabel extends VWJLabel {
    public VwCurrencyLabel() {
    }

    private String currency ;
    public VwCurrencyLabel(String currency){
        super();
        Dimension dim=new Dimension(800,20);
        this.setSize(dim);
        this.setPreferredSize(dim);
//        this.setBounds(new Rectangle(0,0,300,20));
        this.currency = currency;
    }
    public String getText(){
        if(currency == null ||"".equals(currency))
            return null;
        else
            return "Account Currency:" + currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
