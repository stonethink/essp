package c2s.essp.pwm.workbench;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class DtoPwWkitemPeriod extends DtoPwWkitem {
    List period = new ArrayList();

    public void setPeriod(List period){
        this.period = period;
    }

    public List getPeriod(){
        return period;
    }

    public void add(Calendar day){
        int[] date= new int[]{
                    day.get(Calendar.YEAR)
                    , day.get(Calendar.MONTH)
                    , day.get(Calendar.DAY_OF_MONTH)};
        period.add(date);
    }
}
