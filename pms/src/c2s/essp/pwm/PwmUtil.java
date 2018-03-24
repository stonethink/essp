package c2s.essp.pwm;

import java.math.BigDecimal;
import java.util.Date;
import c2s.essp.pwm.wp.DtoPwWkitem;
import java.util.Calendar;

public class PwmUtil {
    public PwmUtil() {
    }

    public static BigDecimal computeWorkHours(DtoPwWkitem dto) {
        Date sTime = dto.getWkitemStarttime();
        Date fTime = dto.getWkitemFinishtime();
        if ( sTime == null ){
            sTime = new Date(0);
        }
        if( fTime == null ){
            fTime = new Date(0);
        }
        return computeWorkHours(sTime,fTime);
    }

    public static BigDecimal computeWorkHours(Date sTime, Date fTime) {
        if ( sTime == null ){
            sTime = new Date(0);
        }
        if( fTime == null ){
            fTime = new Date(0);
        }

        Calendar sCalendar = Calendar.getInstance();
        Calendar fCalendar = Calendar.getInstance();
        sCalendar.set(0, 0, 0, sTime.getHours(), sTime.getMinutes(), sTime.getSeconds());

        //当finish=00:00:00时,调整它为1天的时间
        int fH = fTime.getHours();
        int fM = fTime.getMinutes();
        int fS = fTime.getSeconds();
        if (sTime.getDate() != fTime.getDate()
            && (fH == 0 && fM == 0 && fS == 0)
            ) {
            fCalendar.set(0, 0, 1, 0, 0, 0);
        } else {
            fCalendar.set(0, 0, 0, fTime.getHours(), fTime.getMinutes(), fTime.getSeconds());
        }

        long i = Math.abs((fCalendar.getTimeInMillis() - sCalendar.getTimeInMillis()) / (10 * 60 * 60));

        if( i > 8 * 100 ){
            //如果大于8小时，则按8小时算
            i = 8 * 100;
        }

        if (i < 0) {
            i = 0;
        }
        BigDecimal wkhr = new BigDecimal((double) i / 100);
        BigDecimal value = wkhr.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return value;
    }

    public static Calendar getMinHMS(){
        Calendar c = Calendar.getInstance();
        c.set(2000,1,1,0,0,0);
        return c;
    }

}
