package server.essp.timesheet.preference.dao;

import java.util.List;

import db.essp.timesheet.TsParameter;

public interface IPreferenceDao {

    /**
     * 从数据库中读取当前用户的系统参数设定表中的数据
     * @return TsParameter
     */
    public TsParameter loadPreference();
    
    /**
     * 从数据库中读取用户的系统设置信息记录
     * @param loginId String
     * @return TsParameter
     */
    public TsParameter loadPreferenceByLoginId(String loginId);
    
    /**
     * 向数据库写入系统设置信息
     * @param tsParameter TsParameter
     */
    public void saveOrUpdatePreference(TsParameter tsParameter);
    
    /**
     * 根据site从数据库中读取系统参数设定表中的数据
     * @param site
     * @return
     */
    public TsParameter loadPreferenceBySite(String site);
    
    /**
     * 从人员基本资料表中取出site
     * @return
     */
    public List<String> listSitesInTsHumanbase();

}
