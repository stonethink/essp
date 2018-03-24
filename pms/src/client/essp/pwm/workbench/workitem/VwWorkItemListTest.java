package client.essp.pwm.workbench.workitem;

import client.essp.pwm.workbench.workitem.VwWorkItemList;
import java.util.List;
import c2s.essp.pwm.workbench.DtoPwWkitem;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.ArrayList;
import client.essp.common.view.VWWorkArea;
import com.wits.util.TestPanel;
import com.wits.util.comDate;
import client.framework.common.Global;

public class VwWorkItemListTest extends VwWorkItemList{
    public VwWorkItemListTest() {
        wkitemList = getTestData();
        this.getTable().setRows(getTestData());
    }

    protected void resetUI(){
        if( this.getTable().getRowCount() % 2 == 0 ){
            wkitemList = getTestData2();
            this.getTable().setRows(wkitemList);
        }else{
            wkitemList = getTestData();
            this.getTable().setRows(wkitemList);
        }
    }

    List getTestData(){
        DtoPwWkitem dto1 = new DtoPwWkitem();
        dto1.setWkitemBelongto("dto 1");
        dto1.setWkitemDate(Global.todayDate);
        dto1.setWkitemStarttime(getC(9).getTime());
        dto1.setWkitemFinishtime(getC(15).getTime());
        dto1.setWkitemIsdlrpt("0");
        dto1.setWkitemName("test 1");
        dto1.setWkitemOwner("stone.shi");
        dto1.setWkitemWkhours(new BigDecimal(2));

        DtoPwWkitem dto2 = new DtoPwWkitem();
        dto2.setWkitemBelongto("dto 2");
        dto2.setWkitemDate(new Date());
        dto2.setWkitemStarttime(getC(5).getTime());
        dto2.setWkitemFinishtime(getC(15).getTime());
        dto2.setWkitemIsdlrpt("0");
        dto2.setWkitemName("test 2");
        dto2.setWkitemOwner("stone.shi");
        dto2.setWkitemWkhours(new BigDecimal(10));

        DtoPwWkitem dto3 = new DtoPwWkitem();
        dto3.setWkitemBelongto("dto 3");
        dto3.setWkitemDate(new Date());
        dto3.setWkitemStarttime(getC(5).getTime());
        dto3.setWkitemFinishtime(getC(23).getTime());
        dto3.setWkitemIsdlrpt("0");
        dto3.setWkitemName("test 3");
        dto3.setWkitemOwner("stone.shi");
        dto3.setWkitemWkhours(new BigDecimal(10));


        List list = new ArrayList();
        list.add(dto1);
        list.add(dto2);
        list.add(dto3);
        return list;
    }

    List getTestData2(){
        DtoPwWkitem dto1 = new DtoPwWkitem();
        dto1.setWkitemBelongto("dto 1");
        dto1.setWkitemDate(new Date());
        dto1.setWkitemStarttime(getC(9).getTime());
        dto1.setWkitemFinishtime(getC(15).getTime());
        dto1.setWkitemIsdlrpt("0");
        dto1.setWkitemName("test 1");
        dto1.setWkitemOwner("stone.shi");
        dto1.setWkitemWkhours(new BigDecimal(2));

        DtoPwWkitem dto2 = new DtoPwWkitem();
        dto2.setWkitemBelongto("dto 2");
        dto2.setWkitemDate(new Date());
        dto2.setWkitemStarttime(getC(5).getTime());
        dto2.setWkitemFinishtime(getC(15).getTime());
        dto2.setWkitemIsdlrpt("0");
        dto2.setWkitemName("test 2");
        dto2.setWkitemOwner("stone.shi");
        dto2.setWkitemWkhours(new BigDecimal(10));

        List list = new ArrayList();
        list.add(dto1);
        list.add(dto2);
        return list;
    }


    Calendar getC(int hour){
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(2000,1,1,0,0,0);
        c.set(Calendar.HOUR_OF_DAY, hour);
        //System.out.println(comDate.dateToString(c.getTime(),"HH:mm:ss"));
        return c;
    }

    public static void main(String args[]){
        VwWorkItemListTest w = new VwWorkItemListTest();

        VWWorkArea w2 = new VWWorkArea();
        w2.addTab("daily report", w);
        TestPanel.show(w2);
    }
}

