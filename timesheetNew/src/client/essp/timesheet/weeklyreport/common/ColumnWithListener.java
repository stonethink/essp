package client.essp.timesheet.weeklyreport.common;

/**
 * <p>Title: ColumnWithListener</p>
 *
 * <p>Description: �п������
 *                     Summary Table ����Detail Table�п�ı仯��ʵʩ����</p>
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
     * �����п�仯������ÿһ�еĿ��
     * @param widths int[] �п�����
     */
    public void columnWidthChanged(int[] widths);
}
