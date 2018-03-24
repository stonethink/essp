package c2s.essp.timesheet.report;

/**
 * <p>Title: ISumLevel</p>
 *
 * <p>Description: 汇总等级接口</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface ISumLevel {

    /**
     * 给出当前Bean的汇总等级
     * @return int
     */
    public int getSumLevel();
}
