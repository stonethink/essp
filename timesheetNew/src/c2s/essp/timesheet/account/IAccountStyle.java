package c2s.essp.timesheet.account;

/**
 * <p>Title: IAccountIcon</p>
 *
 * <p>Description: AccountNodeViewManagerʹ�ô˽ӿ���ȷ��Account�Ľ��������ʽ</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface IAccountStyle {

    /**
     * ��Account�ǲ��ǲ���
     * @return boolean
     */
    public boolean isDept();

    /**
     * ��Account��û��ʹ��P3
     * @return boolean
     */
    public boolean isP3Adapted();
}
