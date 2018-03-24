package c2s.essp.timesheet.step;

import java.util.Date;

import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.timesheet.step.management.DtoStepBase;

public class DtoStatus {

	public static String getStatus(Date planStart, Date planFinish, Date actualStart, Date actualFinish) {
		if (actualFinish != null && planFinish != null) {
            Date aFinish = WorkCalendar.resetEndDate(actualFinish);
            Date pFinish = WorkCalendar.resetEndDate(planFinish);
			int t = aFinish.compareTo(pFinish);
			return getStatusByCompareValue(t);
		}
		if (actualStart != null && planStart != null) {
            Date  aStart = WorkCalendar.resetBeginDate(actualStart);
            Date  pStart = WorkCalendar.resetBeginDate(planStart);
			int t = aStart.compareTo(pStart);
            //如果實際完成時間為空且計劃完成實際小于當前時間則為推遲
             if(actualFinish == null && planFinish != null){
            	 Date today = WorkCalendar.resetEndDate(new Date());
            	 Date pFinish = WorkCalendar.resetEndDate(planFinish);
                 int m = today.compareTo(pFinish);
                 if(m > 0){
                     return DtoStepBase.DELAY;
                 }
              }
			return getStatusByCompareValue(t);
		}

		if (actualStart == null && planStart != null) {
			Date today = WorkCalendar.resetBeginDate(new Date());
			Date  pStart = WorkCalendar.resetBeginDate(planStart);
			int t = today.compareTo(pStart);
            if (t > 0) {
                return DtoStepBase.DELAY;
            }
		}
		return DtoStepBase.NORMAL;
	}

	private static String getStatusByCompareValue(int t) {
		if (t < 0) {
			return DtoStepBase.AHEAD;
		} else if (t == 0) {
			return DtoStepBase.NORMAL;
		} else {
			return DtoStepBase.DELAY;
		}
	}
}
