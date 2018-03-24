package server.essp.timesheet.preference.dao;

import java.util.List;

import db.essp.timesheet.TsParameter;

public interface IPreferenceDao {

    /**
     * �����ݿ��ж�ȡ��ǰ�û���ϵͳ�����趨���е�����
     * @return TsParameter
     */
    public TsParameter loadPreference();
    
    /**
     * �����ݿ��ж�ȡ�û���ϵͳ������Ϣ��¼
     * @param loginId String
     * @return TsParameter
     */
    public TsParameter loadPreferenceByLoginId(String loginId);
    
    /**
     * �����ݿ�д��ϵͳ������Ϣ
     * @param tsParameter TsParameter
     */
    public void saveOrUpdatePreference(TsParameter tsParameter);
    
    /**
     * ����site�����ݿ��ж�ȡϵͳ�����趨���е�����
     * @param site
     * @return
     */
    public TsParameter loadPreferenceBySite(String site);
    
    /**
     * ����Ա�������ϱ���ȡ��site
     * @return
     */
    public List<String> listSitesInTsHumanbase();

}
