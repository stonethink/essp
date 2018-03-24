package server.essp.tc.search.logic;

import c2s.essp.tc.weeklyreport.DtoTcSearchResult;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import java.util.List;
import java.util.ArrayList;
import server.framework.hibernate.HBComAccess;
import java.util.Calendar;
import java.util.Date;

/**
 * 本程序每天运行,如果这一天为一周的末尾,就催缴没有填写周报的人
 */
public class LgTcAutoSendMail {
    LgTcSendMail sender = new LgTcSendMail();
    LgTcSearchByHrAcnt searcher = new LgTcSearchByHrAcnt();
    public LgTcAutoSendMail() {
    }

    public void send(Long acntRid) {
        System.out.println("The hr account: ["+acntRid+"], at [" +(new Date()).toString()+ "]");
        List userList = searcher.search(acntRid , Calendar.getInstance());

        System.out.println(userList.size());

        DtoTcSearchResult dto1 = new DtoTcSearchResult();
        dto1.setUser("hua.xiao");
        dto1.setStatus(DtoWeeklyReport.STATUS_NONE);

        DtoTcSearchResult dto2 = new DtoTcSearchResult();
        dto2.setUser("hua.xiao");
        dto2.setStatus(DtoWeeklyReport.STATUS_NONE);

        List userList2 = new ArrayList();
        userList2.add(dto1);
        userList2.add(dto2);

        //sender.send(userList2); // for test
        sender.send(userList);
    }

    public static void main(String args[]){
        try {

            if( args.length < 1 ){
                throw new RuntimeException("Not give an argument.A hr account id is required.");
            }

            Long acntRid = new Long(args[0]);
            //acntRid = new Long("883"); // for test

            HBComAccess dba = HBComAccess.newInstance();
            dba.newTx();
            LgTcAutoSendMail logic = new LgTcAutoSendMail();
            logic.send(acntRid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
