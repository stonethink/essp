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
 * 所有操作Area Code的服务
 * 
 * @author Robin
 * 
 */

public class SiteServiceImpl extends AbstractBusinessLogic implements
		ISiteService {
	/**
	 * 列出所有AreaCode
	 * 
	 * @author Robin
	 * @return List
	 */
	public List listAll() throws BusinessException {
		List codeList = new ArrayList();
		try {
			// 先获得Session
			Session session = this.getDbAccessor().getSession();

			// 创建HQL查询对象
			Query query = session
					.createQuery("from Site as t order by t.siteName");

			// 查询
			codeList = query.list();
		} catch (Exception e) {
			// 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
			throw new BusinessException("error.system.db", e);
		}
		return codeList;
	}
	
	/**
	 * 保存Site
	 */
	public void save(Site site) {
		if(this.loadByCode(site.getSiteCode())!=null) {
			throw new BusinessException("error.logic.SiteServiceImpl.codeduplicate");
		}
		this.getDbAccessor().save(site);
	}

	/**
	 * 更新Site
	 */
	public void update(Site site) {
		this.getDbAccessor().update(site);
	}
	
	/**
	 * 删除Site
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
	 * 根据代码获取Site
	 */
	public Site loadByCode(String code) throws BusinessException {
		Site site = null;
		try {
			// 先获得Session
			Session session = this.getDbAccessor().getSession();

			// 查询对象
			site = (Site) session.createQuery("from Site as t where t.siteCode=:siteCode")
			.setString("siteCode",code)
			.setMaxResults(1)
			.uniqueResult();

		} catch (Exception e) {
			// 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
			throw new BusinessException("error.system.db", e);
		}
		return site;
	}

	/**
	 * 列出所有Status为true的Site
	 */
    public List listAllEabled() throws BusinessException {
        List codeList = new ArrayList();
        try {
            // 先获得Session
            Session session = this.getDbAccessor().getSession();

            // 创建HQL查询对象
            Query query = session
                    .createQuery("from Site as t where status=:status order by t.siteName")
                    .setBoolean("status", true);
            // 查询
            codeList = query.list();
        } catch (Exception e) {
            // 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
            throw new BusinessException("error.system.db", e);
        }
        return codeList;
    }
	
}
