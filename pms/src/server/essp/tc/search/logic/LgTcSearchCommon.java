package server.essp.tc.search.logic;

import java.util.List;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.calendar.WrokCalendarFactory;
import java.util.*;
import c2s.essp.tc.weeklyreport.DtoTcSearchResult;
import com.wits.util.comDate;
import c2s.essp.tc.weeklyreport.DtoTcSearchCondition;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class LgTcSearchCommon {
    static WorkCalendar workCalendar = WrokCalendarFactory.serverCreate();

    public static List getWeekList(Date beginPeriod, Date endPeriod){

        Calendar bc = Calendar.getInstance();
        bc.setTime(beginPeriod);
        Calendar ec = Calendar.getInstance();
        ec.setTime(endPeriod);
        List weekList = workCalendar.getBEWeekListMax(bc, ec);
        List resultList = new ArrayList();
        for (Iterator iterWeek = weekList.iterator(); iterWeek.hasNext(); ) {
            Calendar[] weekPeriod = (Calendar[]) iterWeek.next();
            Calendar beginOfWeek = weekPeriod[0];
            Calendar endOfWeek = weekPeriod[1];

            beginOfWeek.set(Calendar.HOUR_OF_DAY, 0);
            beginOfWeek.set(Calendar.MINUTE, 0);
            beginOfWeek.set(Calendar.SECOND, 0);
            beginOfWeek.set(Calendar.MILLISECOND, 0);
            endOfWeek.set(Calendar.HOUR_OF_DAY, 23);
            endOfWeek.set(Calendar.MINUTE, 59);
            endOfWeek.set(Calendar.SECOND, 59);
            endOfWeek.set(Calendar.MILLISECOND, 999);

            resultList.add(new Date[]{beginOfWeek.getTime(), endOfWeek.getTime()});
        }

        return resultList;
    }

    /**将查询结果中的一些项合并，包括：
     * 1。 如果两项数据只有时间项不同，且时间相邻，则可合并
     */
    public static List merge(List doList) {

        Map resultMap = new HashMap();
        List resultList = new ArrayList();

        for (Iterator iterDto = doList.iterator(); iterDto.hasNext(); ) {
            DtoTcSearchResult dto = (DtoTcSearchResult) iterDto.next();

            Key key = new Key(dto.getAcntRid(), dto.getStatus(), dto.getUser());
            List resultListOfSameUser = (List) resultMap.get(key);
            if (resultListOfSameUser == null) {
                resultListOfSameUser = new ArrayList();
                resultListOfSameUser.add(dto);
                resultList.add(dto);
                resultMap.put(key, resultListOfSameUser);
            } else {
                boolean isMerge = false;
                for (Iterator iterResult = resultListOfSameUser.iterator(); iterResult.hasNext(); ) {
                    DtoTcSearchResult result = (DtoTcSearchResult) iterResult.next();

                    if (merge(result, dto)) {
                        isMerge = true;
                        break;
                    }
                }
                if (isMerge == false) {
                    resultList.add(dto);
                    resultListOfSameUser.add(dto);
                }
            }
        }

        return resultList;
    }

    /**将orig合并到dest中去，如果合并成功则返回true,否则返回false*/
    private static boolean merge(DtoTcSearchResult dest, DtoTcSearchResult orig) {
        Date destBegin = dest.getBeginPeriod();
        Date destEnd = dest.getEndPeriod();
        Date origBegin = orig.getBeginPeriod();
        Date origEnd = orig.getEndPeriod();
        Calendar bC = Calendar.getInstance();
        bC.setTime(destBegin);
        Calendar eC = Calendar.getInstance();
        eC.setTime(destEnd);

        int iStep = 0;
        int i = comDate.compareDate(destBegin, origBegin);
        if (i > 0) {
            iStep = -1;

            Calendar[] nextPeriod = workCalendar.getNextBEWeekDay(bC, iStep);
            Calendar nextBegin = nextPeriod[0];
            Calendar nextEnd = nextPeriod[1];

            //dest比orig大
            Calendar origC = Calendar.getInstance();
            origC.setTime(origEnd);
            if (compareDate(nextEnd, origC)) {
                //合并为：[origBegin, destEnd]
                dest.setBeginPeriod(origBegin);
                return true;
            }
        } else if (i < 0) {
            iStep = 1;

            Calendar[] nextPeriod = workCalendar.getNextBEWeekDay(eC, iStep);
            Calendar nextBegin = nextPeriod[0];
            Calendar nextEnd = nextPeriod[1];

            //dest比orig小
           Calendar origC = Calendar.getInstance();
           origC.setTime(origBegin);
           if (compareDate(nextBegin, origC)) {
               //合并为：[destBegin, origEnd]
               dest.setEndPeriod(origEnd);
               return true;
           }
        } else {
            if (comDate.compareDate(destEnd, origEnd) < 0) {
                dest.setEndPeriod(origEnd);
            }
            return true;
        }

        return false;
    }

    private static boolean compareDate(Calendar one, Calendar two) {
        if (one.get(Calendar.YEAR) == two.get(Calendar.YEAR)
            && one.get(Calendar.MONTH) == two.get(Calendar.MONTH)
            && one.get(Calendar.DAY_OF_MONTH) == two.get(Calendar.DAY_OF_MONTH)) {
            return true;
        } else {
            return false;
        }
    }

    public static String[] getStatus(DtoTcSearchCondition condition){
        List status = new ArrayList();
        if (condition.getUnLockedStatus()) {
            status.add(DtoWeeklyReport.STATUS_UNLOCK);
        }
        if (condition.getLockedStatus()) {
            status.add(DtoWeeklyReport.STATUS_LOCK);
        }
        if (condition.getConfirmedStatus()) {
            status.add(DtoWeeklyReport.STATUS_CONFIRMED);
        }
        if (condition.getRejectedStatus()) {
            status.add(DtoWeeklyReport.STATUS_REJECTED);
        }

        if (status.size() == 0) {
            if (condition.getNoneStatus() == false) {
                //如果一个都没有填写，那么查询所有填写的周报
                return new String[] {
                        DtoWeeklyReport.STATUS_UNLOCK,
                        DtoWeeklyReport.STATUS_LOCK,
                        DtoWeeklyReport.STATUS_CONFIRMED,
                        DtoWeeklyReport.STATUS_REJECTED,
                };
            }else{
                return new String[]{};
            }
        }else{
            String[] array = new String[ status.size() ];
            for (int i = 0; i < array.length; i++) {
                array[i] = (String)status.get(i);
            }
            return array;
        }
    }

    public static void sort(List dtoList){
        for (int i = 0; i < dtoList.size(); i++) {
            DtoTcSearchResult dto = (DtoTcSearchResult)dtoList.get(i);
            int j = i - 1;
            for (; j >= 0; j--) {
                DtoTcSearchResult tmp = (DtoTcSearchResult) dtoList.get(j);

                int r = dto.getUser().compareToIgnoreCase(tmp.getUser());
                if( r > 0 ){
                    break;
                }else if( r == 0 ){
                    if( dto.getBeginPeriod().compareTo(tmp.getBeginPeriod()) > 0 ){
                        break;
                    }
                }
            }

            //移动到j+1
            if( j+1 < i ){
                dtoList.remove(i);
                dtoList.add(j+1, dto);
            }
        }
    }




    private static class Key {
        Long acntRid;
        String status;
        String userId;

        public Key(Long acntRid, String status, String userId) {
            if (acntRid != null) {
                this.acntRid = acntRid;
            } else {
                this.acntRid = new Long( -1);
            }
            this.status = status;
            this.userId = userId;
        }

        public int hashCode() {
            return new HashCodeBuilder()
                    .append((acntRid == null ? new Object() : acntRid))
                    .append((status == null ? new Object() : status))
                    .append((userId == null ? new Object() : userId))
                    .toHashCode();
        }

        public boolean equals(Object obj) {

            if (obj == this) {
                return true;
            }

            if (obj != null && obj instanceof Key) {
                Key other = (Key) obj;
                if (other.acntRid.equals(this.acntRid) &&
                    other.status.equals(this.status) &&
                    other.userId.equals(this.userId)) {
                    return true;
                }
            }

            return false;
        }
    }

}
