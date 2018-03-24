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
	 * 列出所有AreaCode
	 * @return
	 * @throws BusinessException
	 */
	public List listAll() throws BusinessException;
    
	/**
	 * 列出所有Status为true的Site
	 * @return
	 * @throws BusinessException
	 */
    public List listAllEabled() throws BusinessException;

    /**
     * 保存Site
     * @param site
     */
	public void save(Site site);

	/**
	 * 更新Site
	 * @param site
	 */
	public void update(Site site);

	/**
	 * 删除Site
	 * @param code
	 * @throws BusinessException
	 */
	public void delete(String code) throws BusinessException;

	/**
	 * 根据代码获取Site
	 * @param code
	 * @return
	 * @throws BusinessException
	 */
	public Site loadByCode(String code) throws BusinessException;

}
