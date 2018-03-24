package client.framework.view.vwcomp;

import validator.Validator;
import validator.ValidatorResult;

/**
 *
 * <p>Title: public interface IVWComponent</p>
 * <p>Description: Interface of View Component
 *     �����Զ���View Component�����붨���Component���󶨵�IVMComponent
 * </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: WITS-WH</p>
 * @author Stone
 * @version 1.0
 */
public interface IVWComponent {
    /**
     * ��ȡ�ؼ�����Ӧ����������
     * @return String
     */
    public String getName();

    /**
     * ��ؼ���ֵ������ҵ�����ݸ�ֵ���ؼ�
     * @param value Object
     */
    public void setUICValue(Object value);

    /**
     * �ӿؼ�ȡֵ�������ؼ�������������ݷ���Ϊҵ������
     * @return Object
     */
    public Object getUICValue();

    /**
     * ���ÿؼ���֤�����õ�Class����
     * @param dtoClass Class
     */
    public void setDtoClass(Class dtoClass);

    /**
     * ��Component ����validator����
     * @param validator Validator
     */
    public void setValidator(Validator validator);

    /**
     * ��֤�ؼ��������ݵĺϷ������Ϸ�����True�����򷵻�False��
     * ���δ����Validator��Dto Class�򲻽�����֤����ֵΪTrue
     * @return boolean
     */
    public boolean validateValue();

    public ValidatorResult getValidatorResult();

    /**
     * ������ʾ��Ϣ
     * @param text String ��ʾ��Ϣ
     */
    public void setToolTipText(String text);

    /**
     * ���ÿؼ������������ʾ����ʽ��ǰ���ͱ�����
     * ���flag=true,������Ϊ�������ʽ
     * ��������Ϊ��������ʽ
     * @param flag boolean
     */
    public void setErrorField(boolean flag);


    /**
     * ���ÿؼ��Ƿ����
     * @param isEnabled boolean
     */
    public void setEnabled(boolean isEnabled);

    /**
     * ���ƿؼ�
     * @return Object
     */
    public IVWComponent duplicate();

    /**
     * ���ؼ�����ƫ��offet����(ͨ��getOffet()�õ�)�����統�ؼ���ΪTreeTable��editorʱ,��Ҫ�ؼ�����ƫ��һЩ����
     * @return boolean
     */
    public boolean isBReshap();

    public void setBReshap(boolean bReshap);

    //����ƫ�Ƶľ���
    public int getOffset();

    public void setOffset(int offset);

    public int getHorizontalAlignment();

    public boolean isEnabled();
}
