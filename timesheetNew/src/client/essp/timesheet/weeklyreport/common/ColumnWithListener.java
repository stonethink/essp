package client.essp.timesheet.weeklyreport.common;

/**
 * <p>Title: ColumnWithListener</p>
 *
 * <p>Description: 列宽监听器
 *                     Summary Table 根据Detail Table列宽的变化而实施调整</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface ColumnWithListener {

    /**
     * 触发列宽变化，传递每一列的宽度
     * @param widths int[] 列宽数组
     */
    public void columnWidthChanged(int[] widths);
}
