package client.essp.tc.common;

public interface TableListener {

    //表格的行数变化，table的大小会随行数动态变化
    static final String EVENT_ROW_COUNT_CHANGED = "rowCountChanged";

    //表格的confirm数据变化
//    static final String EVENT_CONFIRM_DATA_CHANGED = "confirmDataChanged";
    //明细的confirm数据变化
    static final String EVENT_DETAIL_CONFIRM_DATA_CHANGED = "detailConfirmDataChanged";
    //汇总的confirm数据变化
    static final String EVENT_TOTAL_CONFIRM_DATA_CHANGED = "totalConfirmDataChanged";

    //表格的统计数据变化
    static final String EVENT_SUM_DATA_CHANGED = "sumDataChanged";
//    static final String EVENT_SUM_DATA_ON_MONTH_CHANGED = "sumDataOnMonthChanged";

    static final String EVENT_REFRESH_ACTION = "refreshAction";

    static final String EVENT_LOCK_OFF = "lockOff";
    static final String EVENT_LOCK_On = "lockOn";

    public void processTableChanged(String eventType);
}
