package server.essp.timesheet.preference.service;

import java.util.List;
import java.util.Vector;

import server.essp.timesheet.preference.dao.IPreferenceDao;
import server.framework.common.BusinessException;
import c2s.dto.DtoComboItem;
import c2s.dto.DtoUtil;
import c2s.essp.timesheet.preference.DtoPreference;
import db.essp.timesheet.TsParameter;

public class PreferenceServiceImp implements IPreferenceService{
    private IPreferenceDao preferenceDao;

    /**
     * ��ȡϵͳ��ǰ������Ϣ
     * @return DtoPreference
     * @throws BusinessException
     */
    public DtoPreference getLoadPreference() throws BusinessException {
        TsParameter tsPara = preferenceDao.loadPreference();
        DtoPreference dtoPreference = new DtoPreference();
        DtoUtil.copyBeanToBean(dtoPreference, tsPara);
        return dtoPreference;
    }

    /**
     * �����޸ĺ��ϵͳ������Ϣ
     * @param para DtoPreference
     * @throws BusinessException
     */
    public void savePreference(DtoPreference prefer) throws BusinessException {
        TsParameter tsPara = new TsParameter();
        DtoUtil.copyBeanToBean(tsPara, prefer);
        preferenceDao.saveOrUpdatePreference(tsPara);
    }

    public void setPreferenceDao(IPreferenceDao preferenceDao) {
        this.preferenceDao = preferenceDao;
    }

	public DtoPreference getLoadPreferenceBySite(String site) throws BusinessException {
		TsParameter tsPara = preferenceDao.loadPreferenceBySite(site);
        DtoPreference dtoPreference = new DtoPreference();
        DtoUtil.copyBeanToBean(dtoPreference, tsPara);
        return dtoPreference;
	}

	public Vector listSites() throws BusinessException {
		List<String> list = preferenceDao.listSitesInTsHumanbase();
		if(list == null || list.size() == 0) {
			return new Vector();
		}
		Vector v = new Vector();
		for(String site : list) {
			v.add(new DtoComboItem("--"+site+"--", site));
		}
		return v;
	}
}
