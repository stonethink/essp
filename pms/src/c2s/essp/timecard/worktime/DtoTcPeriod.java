package c2s.essp.timecard.worktime;

import c2s.dto.DtoBase;

import java.io.Serializable;


public class DtoTcPeriod extends DtoBase implements Serializable {
    /** 月起始日 */
    private Integer monthId;

    /** 周起始日 */
    private Integer weekId;

    public DtoTcPeriod() {
    }

    public Integer getMonthID() {
        return monthId;
    }

    public void setMonthID(Integer monthId) {
        this.monthId = monthId;
    }

    public Integer getWeekID() {
        return weekId;
    }

    public void setWeekID(Integer weekId) {
        this.weekId = weekId;
    }
}
