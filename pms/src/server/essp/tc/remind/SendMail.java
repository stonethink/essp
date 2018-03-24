package server.essp.tc.remind;

import server.framework.logic.AbstractBusinessLogic;
import c2s.essp.tc.weeklyreport.DtoTcSearchCondition;
import java.util.Date;
import java.util.Calendar;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.calendar.WrokCalendarFactory;
import java.util.ResourceBundle;
import server.essp.tc.search.logic.LgTcSearchByOrg;
import java.util.List;
import server.essp.tc.search.logic.LgTcSendMail;
import com.wits.util.Config;

public class SendMail extends AbstractBusinessLogic {
    public void Send() {
        DtoTcSearchCondition dto = new DtoTcSearchCondition();
        Date curTime = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        System.out.println(calendar.getTime());
        WorkCalendar wc = WrokCalendarFactory.serverCreate();
        Calendar[] cl = wc.getNextBEWeekDay(calendar, -1);
        System.out.println(cl[0].getTime());
        System.out.println(cl[1].getTime());
//        ResourceBundle resource = ResourceBundle.getBundle("timecard");
        Config config = new Config("timecard");
        dto.setOrgId(config.getValue("startOrg"));
        dto.setIncSub("true");
        dto.setBeginPeriod(cl[0].getTime());
        dto.setEndPeriod(cl[1].getTime());
        dto.setNoneStatus("true");
        dto.setLockedStatus("true");
        dto.setUnLockedStatus("true");
        dto.setConfirmedStatus("false");
        LgTcSearchByOrg logic = new LgTcSearchByOrg();
        List uesrList = logic.search(dto);
        LgTcSendMail lg = new LgTcSendMail();
        lg.send(uesrList);
    }
}
