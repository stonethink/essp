package server.essp.common.timecard;

import java.util.*;


/**
 *
 * <p>Title: 日期范围接口</p>
 * <p>Description: 做为提供日期范围之接口，用于对外提供某些特定的日期的起始日</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author BoXiao
 * @version 1.0
 * @since JDK1.1
 * @see DataItem
 */
public interface DateRange {
    /**
     * 设置起始期限
     * @author BoXiao
     * @version 1.0
     * @param obj 起期限
     */
    public abstract void setRange(Object obj) throws Exception;

    /**
     * 依据某个日期，获取当日所在的起始范围
     * @author BoXiao
     * @version 1.0
     * @param paDate 特定日期
     * @return 包含此日期的日期范围
     */
    public abstract DateItem getRange(Calendar paDate);

    /**
     * 依据日期范围，获取当日所在的起始范围
     * @author BoXiao
     * @version 1.0
     * @param paStart 起始日期
     * @param paEnd 结束日期
     * @return 日期范围内的所有起止日期
     */
    public abstract DateItem[] getRange(Calendar paStart,
                                        Calendar paEnd);

    /**
     * 依据某个特定日期
     * @author BoXiao
     * @version 1.0
     * @param paDate 特定日期
     * @param paBefore 前周期数
     * @param paAfter 后周期数
     * @return 日期范围内的所有起止日期
     */
    public abstract DateItem[] getRange(Calendar paDate,
                                        int      paBefore,
                                        int      paAfter);

    /**
     * 获取设置范围起始日
     * @author BoXiao
     * @version 1.0
     * @return 起始日
     */
    public abstract int getRange();
}
