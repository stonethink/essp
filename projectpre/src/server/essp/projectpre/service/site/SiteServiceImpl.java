/**
 * 
 */
package server.essp.projectpre.service.site;

import java.util.ArrayList;
import java.util.List;


import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import server.essp.projectpre.db.Site;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;

/**
 * ���в���Area Code�ķ���
 * 
 * @author Robin
 * 
 */

public class SiteServiceImpl extends AbstractBusinessLogic implements
		ISiteService {
	/**
	 * �г�����AreaCode
	 * 
	 * @author Robin
	 * @return List
	 */
	public List listAll() throws BusinessException {
		List codeList = new ArrayList();
		try {
			// �Ȼ��Session
			Session session = this.getDbAccessor().getSession();

			// ����HQL��ѯ����
			Query query = session
					.createQuery("from Site as t order by t.siteName");

			// ��ѯ
			codeList = query.list();
		} catch (Exception e) {
			// �����������ϲ㼴�ɣ��˴������Ĵ����Ϊ�������ݿ��������
			throw new BusinessException("error.system.db", e);
		}
		return codeList;
	}
	
	/**
	 * ����Site
	 */
	public void save(Site site) {
		if(this.loadByCode(site.getSiteCode())!=null) {
			throw new BusinessException("error.logic.SiteServiceImpl.codeduplicate");
		}
		this.getDbAccessor().save(site);
	}

	/**
	 * ����Site
	 */
	public void update(Site site) {
		this.getDbAccessor().update(site);
	}
	
	/**
	 * ɾ��Site
	 */
	public void delete(String code) throws BusinessException {
		try {
			Site site=this.loadByCode(code);
			this.getDbAccessor().delete(site);
		}catch(Exception e) {
			throw new BusinessException("error.system.db", e);
		}
	}

	/**
	 * ���ݴ����ȡSite
	 */
	public Site loadByCode(String code) throws BusinessException {
		Site site = null;
		try {
			// �Ȼ��Session
			Session session = this.getDbAccessor().getSession();

			// ��ѯ����
			site = (Site) session.createQuery("from Site as t where t.siteCode=:siteCode")
			.setString("siteCode",code)
			.setMaxResults(1)
			.uniqueResult();

		} catch (Exception e) {
			// �����������ϲ㼴�ɣ��˴������Ĵ����Ϊ�������ݿ��������
			throw new BusinessException("error.system.db", e);
		}
		return site;
	}

	/**
	 * �г�����StatusΪtrue��Site
	 */
    public List listAllEabled() throws BusinessException {
        List codeList = new ArrayList();
        try {
            // �Ȼ��Session
            Session session = this.getDbAccessor().getSession();

            // ����HQL��ѯ����
            Query query = session
                    .createQuery("from Site as t where status=:status order by t.siteName")
                    .setBoolean("status", true);
            // ��ѯ
            codeList = query.list();
        } catch (Exception e) {
            // �����������ϲ㼴�ɣ��˴������Ĵ����Ϊ�������ݿ��������
            throw new BusinessException("error.system.db", e);
        }
        return codeList;
    }
	
}
