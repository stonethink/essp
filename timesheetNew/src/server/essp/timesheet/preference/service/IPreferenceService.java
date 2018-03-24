package server.essp.timesheet.preference.service;

import java.util.Vector;

import server.framework.common.BusinessException;
import c2s.essp.timesheet.preference.DtoPreference;

public interface IPreferenceService {

    /**
     * ��ȡϵͳ���������Ϣ
     * @return DtoPreference
     * @throws BusinessException
     */
    public DtoPreference getLoadPreference() throws BusinessException;

    /**
     * �޸�ϵͳ���������Ϣ
     * @param para DtoPreference
     * @throws BusinessException
     */
    public void savePreference(DtoPreference para) throws BusinessException;
    
    /**
     * ����site��ȡϵͳ���������Ϣ
     * @param site
     * @return
     * @throws BusinessException
     */
    public DtoPreference getLoadPreferenceBySite(String site) throws BusinessException;
    
    /**
     * ��ȡ��Ա�������ϱ��е�site
     * @return
     * @throws BusinessException
     */
    public Vector listSites() throws BusinessException;

}
