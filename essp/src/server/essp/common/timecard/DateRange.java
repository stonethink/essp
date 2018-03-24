package server.essp.common.timecard;

import java.util.*;


/**
 *
 * <p>Title: ���ڷ�Χ�ӿ�</p>
 * <p>Description: ��Ϊ�ṩ���ڷ�Χ֮�ӿڣ����ڶ����ṩĳЩ�ض������ڵ���ʼ��</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author BoXiao
 * @version 1.0
 * @since JDK1.1
 * @see DataItem
 */
public interface DateRange {
    /**
     * ������ʼ����
     * @author BoXiao
     * @version 1.0
     * @param obj ������
     */
    public abstract void setRange(Object obj) throws Exception;

    /**
     * ����ĳ�����ڣ���ȡ�������ڵ���ʼ��Χ
     * @author BoXiao
     * @version 1.0
     * @param paDate �ض�����
     * @return ���������ڵ����ڷ�Χ
     */
    public abstract DateItem getRange(Calendar paDate);

    /**
     * �������ڷ�Χ����ȡ�������ڵ���ʼ��Χ
     * @author BoXiao
     * @version 1.0
     * @param paStart ��ʼ����
     * @param paEnd ��������
     * @return ���ڷ�Χ�ڵ�������ֹ����
     */
    public abstract DateItem[] getRange(Calendar paStart,
                                        Calendar paEnd);

    /**
     * ����ĳ���ض�����
     * @author BoXiao
     * @version 1.0
     * @param paDate �ض�����
     * @param paBefore ǰ������
     * @param paAfter ��������
     * @return ���ڷ�Χ�ڵ�������ֹ����
     */
    public abstract DateItem[] getRange(Calendar paDate,
                                        int      paBefore,
                                        int      paAfter);

    /**
     * ��ȡ���÷�Χ��ʼ��
     * @author BoXiao
     * @version 1.0
     * @return ��ʼ��
     */
    public abstract int getRange();
}
