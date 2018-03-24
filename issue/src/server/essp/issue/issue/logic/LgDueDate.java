package server.essp.issue.issue.logic;

import server.essp.issue.common.logic.AbstractISSUELogic;
import server.framework.common.BusinessException;
import db.essp.issue.IssuePriorityPK;
import server.essp.issue.issue.form.AfDueDate;
import java.util.Date;
import com.wits.util.comDate;
import db.essp.issue.IssuePriority;
import java.util.Calendar;
import server.essp.issue.issue.viewbean.VbDueDate;

public class LgDueDate extends AbstractISSUELogic {
    public VbDueDate accountDueDate(server.essp.issue.issue.form.AfDueDate form) throws
        BusinessException {
        try {
            log.debug("form.typeName=["+form.getTypeName()+"]");
            log.debug("form.priority=["+form.getPriority()+"]");
            log.debug("form.filleDate=["+form.getFilleDate()+"]");
            VbDueDate webVo = new VbDueDate();
            String strDueDate = "";

            if(form.getPriority()!=null && !form.getPriority().trim().equals("") &&
               form.getTypeName()!=null && !form.getTypeName().trim().equals("")) {
                IssuePriorityPK pk = new IssuePriorityPK();

                pk.setPriority(form.getPriority());
                pk.setTypeName(form.getTypeName());

                Date filleDate = comDate.toDate(form.getFilleDate());
                if (filleDate != null) {
                    IssuePriority issuePriority = (IssuePriority) this.getDbAccessor().load(
                        IssuePriority.class, pk);
                    Long duration = issuePriority.getDuration();

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(filleDate);

                    long lTime = calendar.getTimeInMillis() +
                                 24 * 60 * 60 * 1000 * duration.longValue();
                    calendar.setTimeInMillis(lTime);

                    Date dueDate = calendar.getTime();
                    strDueDate = comDate.dateToString(dueDate, "yyyyMMdd");
                }
            }
            webVo.setDueDate(strDueDate);

            return webVo;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        String strFilleDate="2005-12-30";
        Date filleDate = comDate.toDate(strFilleDate);
        calendar.setTime(filleDate);
        int duration = 3;
        long lTime=calendar.getTimeInMillis()+24*60*60*1000*duration;
        calendar.setTimeInMillis(lTime);
        Date dueDate = calendar.getTime();
        String strDueDate = comDate.dateToString(dueDate, "yyyy/MM/dd");
        System.out.println(strFilleDate+" + "+duration+" = "+strDueDate);
    }
}
