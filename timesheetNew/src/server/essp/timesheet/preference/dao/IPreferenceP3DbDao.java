package server.essp.timesheet.preference.dao;


/**
 *
 * <p>Title: IPreferenceP3DbDao</p>
 *
 * <p>Description: ����P3���ݿ��ѯpreference���õĽӿ�</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface IPreferenceP3DbDao {

    /**
     * ��ȡP3����Ա�������ܿ�ʼ��
     * @return DtoPreference
     */
    public int getWeekStartDayNum();
}
