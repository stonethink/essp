package client.essp.tc.search;

import com.wits.util.TestPanel;

public class VwTcSearchByDm extends VwTcSearchBase{
    public VwTcSearchByDm() {
        super( new VwTcSearchConditionByDm() );
    }

    public static void main(String args[]){
        TestPanel.show(new VwTcSearchByDm());
    }
}
