package client.essp.timesheet.weeklyreport.common;

/**
 * <p>Title: ColumnNumListener</p>
 *
 * <p>Description: 列数监听器
 *                     Summary Table 根据Detail Table列数的变化而变化</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface ColumnNumListener {

    /**
     * 触发变化，传递列数
     * @param num int 列数
     */
    public void columnNumChanged(int num);
}
