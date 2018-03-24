package server.essp.timesheet.preference.service;

import java.util.Vector;

import server.framework.common.BusinessException;
import c2s.essp.timesheet.preference.DtoPreference;

public interface IPreferenceService {

    /**
     * 获取系统相关设置信息
     * @return DtoPreference
     * @throws BusinessException
     */
    public DtoPreference getLoadPreference() throws BusinessException;

    /**
     * 修改系统设置相关信息
     * @param para DtoPreference
     * @throws BusinessException
     */
    public void savePreference(DtoPreference para) throws BusinessException;
    
    /**
     * 根据site获取系统相关设置信息
     * @param site
     * @return
     * @throws BusinessException
     */
    public DtoPreference getLoadPreferenceBySite(String site) throws BusinessException;
    
    /**
     * 获取人员基本资料表中的site
     * @return
     * @throws BusinessException
     */
    public Vector listSites() throws BusinessException;

}
