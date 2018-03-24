package client.essp.pwm.workbench.workscope;

import java.util.ArrayList;
import java.util.List;

import c2s.essp.pwm.wp.DtoWSAccount;
import c2s.essp.pwm.wp.DtoWSActivity;
import c2s.essp.pwm.wp.DtoWSWp;
import client.essp.common.view.VWWorkArea;
import client.essp.pwm.workbench.workscope.VwAccountList;
import com.wits.util.TestPanel;

public class VwAccountListTest extends VwAccountList{
    public VwAccountListTest() {
        this.getTable().setRows(getTestData());
    }

    protected void resetUI(){
        this.getTable().setRows(getTestData());
    }

    List getTestData(){
        DtoWSAccount dto1 = new DtoWSAccount();
        dto1.setAcntRid(new Long(1));
        dto1.setAcntCode("acnt cd 1");
        dto1.setAcntName("acnt n 1");

        DtoWSActivity dto11 = new DtoWSActivity();
        dto11.setAcntRid(new Long(1));
        dto11.setAcntCode("acnt cd 1");
        dto11.setAcntName("acnt n 1");
        dto11.setActivityRid( new Long(11) );
        dto11.setActivityCode("actv cd 11");
        dto11.setActivityName("actv n11");

        DtoWSWp dto12 = new DtoWSWp();
        dto12.setAcntRid(new Long(1));
        dto12.setAcntCode("acnt cd 1");
        dto12.setAcntName("acnt n 1");
        dto12.setWpRid( new Long(12) );
        dto12.setWpCode("wp cd 12");
        dto12.setWpName("wp n12");

        dto1.getActivityList().add(dto11);
        dto1.getWpList().add(dto12);

        DtoWSAccount dto2 = new DtoWSAccount();
        dto2.setAcntRid(new Long(2));
        dto2.setAcntCode("cd 2");
        dto2.setAcntName("n 2");


        List list = new ArrayList();
        list.add(dto1);
        list.add(dto2);
        list.add(dto2);
        return list;
    }

    public static void main(String args[]){
        VwAccountListTest w = new VwAccountListTest();

        VWWorkArea w2 = new VWWorkArea();
        w2.addTab("Scope", w);
        TestPanel.show(w2);
    }
}

