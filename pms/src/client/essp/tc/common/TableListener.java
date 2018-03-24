package client.essp.tc.common;

public interface TableListener {

    //���������仯��table�Ĵ�С����������̬�仯
    static final String EVENT_ROW_COUNT_CHANGED = "rowCountChanged";

    //����confirm���ݱ仯
//    static final String EVENT_CONFIRM_DATA_CHANGED = "confirmDataChanged";
    //��ϸ��confirm���ݱ仯
    static final String EVENT_DETAIL_CONFIRM_DATA_CHANGED = "detailConfirmDataChanged";
    //���ܵ�confirm���ݱ仯
    static final String EVENT_TOTAL_CONFIRM_DATA_CHANGED = "totalConfirmDataChanged";

    //����ͳ�����ݱ仯
    static final String EVENT_SUM_DATA_CHANGED = "sumDataChanged";
//    static final String EVENT_SUM_DATA_ON_MONTH_CHANGED = "sumDataOnMonthChanged";

    static final String EVENT_REFRESH_ACTION = "refreshAction";

    static final String EVENT_LOCK_OFF = "lockOff";
    static final String EVENT_LOCK_On = "lockOn";

    public void processTableChanged(String eventType);
}
