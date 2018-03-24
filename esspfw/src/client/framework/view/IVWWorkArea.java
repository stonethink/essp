package client.framework.view;

import com.wits.util.Parameter;



public interface IVWWorkArea {

    //������setParameter��workArea������
    //workArea����ֱ����ʾ��Щ����, Ҳ��������Щ����������Ϊ���������ݿ��в�������
    public void setParameter( Parameter parameter );

    //ˢ��WorkArea
    //һ����������µ�parameterʱҪˢ���Լ��� ��ֻ��������ʹ��ʱ�ŵ���refreshWorkArea.
    public void refreshWorkArea();

    //����workArea�������ֵ������б�Ҫ�Ļ��� ���������ｫ���ݱ��浽���ݿ���ȥ��
    public void saveWorkArea();

    //saveWorkArea�Ƿ�ɹ���һ���ڼ������ͨ�������߱������ݲ��ɹ�ʱ����false
    public boolean isSaveOk();

}
