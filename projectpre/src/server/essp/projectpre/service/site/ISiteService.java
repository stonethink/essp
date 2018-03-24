/**
 * 
 */
package server.essp.projectpre.service.site;

import java.util.List;

import server.essp.projectpre.db.Site;
import server.framework.common.BusinessException;


/**
 * code maintance interface
 * 
 * @author Robin
 * 
 */
public interface ISiteService {

	/**
	 * �г�����AreaCode
	 * @return
	 * @throws BusinessException
	 */
	public List listAll() throws BusinessException;
    
	/**
	 * �г�����StatusΪtrue��Site
	 * @return
	 * @throws BusinessException
	 */
    public List listAllEabled() throws BusinessException;

    /**
     * ����Site
     * @param site
     */
	public void save(Site site);

	/**
	 * ����Site
	 * @param site
	 */
	public void update(Site site);

	/**
	 * ɾ��Site
	 * @param code
	 * @throws BusinessException
	 */
	public void delete(String code) throws BusinessException;

	/**
	 * ���ݴ����ȡSite
	 * @param code
	 * @return
	 * @throws BusinessException
	 */
	public Site loadByCode(String code) throws BusinessException;

}
