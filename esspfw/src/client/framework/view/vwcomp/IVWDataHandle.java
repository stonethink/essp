package client.framework.view.vwcomp;

/**
 * <p>Title: </p>
 * <p>Description: �˽ӿڶ���UI�����ݼ�顢UI���ݳ�ʼ����UI�����ռ���UI���ݸ�ֵ</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author not attributable
 * @version 1.0
 */
public interface IVWDataHandle {
    /**
     * ���UI�������Ƿ��иĶ������������û�ж��嵽IVWComponent�У���Ϊ���ǵ�
     * ֻ������������жϳ�ĳ���ؼ��������Ƿ񱻸Ķ�����Ȼ����ؼ���;���ܾ�������
     * ���޸ģ������յ�ֵ���ֵ��ͬ����������ж��˿ؼ��е�����û�б��޸ģ���
     *
     * ����˷�����Ϊ����ͳһ������������Ƿ��޸ĵļ�顣���ھ�����жϱ�׼�������
     * UI���������ж��塣
     *
     * @return boolean
     */
    public boolean checkModified();

    /**
     * �������ý����ʼֵ
     */
    public void initData();

    /**
     * ���������õ�������
     * ͨ������£�����ʹ�ù�ͨ�������ṩ�ķ��������ý������ݡ�
     * ���ǿ��ǵ����ܴ���ĳЩ��������¹�ͨ�����޷���������setData�м����ر���
     */
    public void setData();

    /**
     * �ռ������ϵ�����
     * ͨ������£�����ʹ�ù�ͨ�������ṩ�ķ�������ȡ�������ݡ�
     * ���ǿ��ǵ����ܴ���ĳЩ��������¹�ͨ�����޷���������getData�м����ر���
     */
    public void getData();

    /**
     * ���ݼ�顣
     * ������������ύ������������ǰ����顣�����ؼ������ݼ�����ڸ����ؼ����¼��д���
     */
    public void validateData();
}
