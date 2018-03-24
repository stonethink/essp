package client.essp.tc.search;

import com.wits.util.TestPanel;

public class VwTcSearchByPm extends VwTcSearchBase{
    public VwTcSearchByPm() {
        super( new VwTcSearchConditionByPm() );
    }

    public static void main(String args[]){
        TestPanel.show(new VwTcSearchByPm());
    }
}
