package server.essp.common.timecard;

import java.util.*;


/**
 *
 * <p>Title: һ�����ڷ�Χ</p>
 * <p>Description: ��ȡ������ʼ��</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author BoXiao
 * @version 1.0
 */
public class DateItem {
    /**
     * ��������
     */
    private Calendar start;

    /**
     * �ܽ�����
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
