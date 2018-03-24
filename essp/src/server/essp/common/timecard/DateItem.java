package server.essp.common.timecard;

import java.util.*;


/**
 *
 * <p>Title: 一笔日期范围</p>
 * <p>Description: 获取日期起始日</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author BoXiao
 * @version 1.0
 */
public class DateItem {
    /**
     * 周期起日
     */
    private Calendar start;

    /**
     * 周结束日
     */
    private Calendar end;

    public DateItem() {
    }

    public Calendar getEnd() {
        return end;
    }

    public Calendar getStart() {
        return start;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }
}
