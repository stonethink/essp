package server.essp.timesheet.preference.dao;


/**
 *
 * <p>Title: IPreferenceP3DbDao</p>
 *
 * <p>Description: 访问P3数据库查询preference设置的接口</p>
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
     * 获取P3管理员设置中周开始日
     * @return DtoPreference
     */
    public int getWeekStartDayNum();
}
