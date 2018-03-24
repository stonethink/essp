package server.essp.projectpre.service.bd;

import java.util.List;

import server.essp.projectpre.db.Bd;
import server.framework.common.BusinessException;

public interface IBdService {

  /**
   * 从获取Bd表中获取所有的记录
   * @return
   * @throws BusinessException
   */
  public List listAll() throws BusinessException;
  
  /**
   * 从获取Bd表中获取所有Status为true的记录
   * @return
   * @throws BusinessException
   */
  public List listAllEabled() throws BusinessException;
  
  /**
   * 从获取Bd表中获取可用的业绩归属
   * @return
   * @throws BusinessException
   */
  public List listEabledBelongs(boolean isAchieveBelong) throws BusinessException;
  
  /**
   * 保存DB
   * @param bd
   */
  public void save(Bd bd);
  
  /**
   * 更新BD
   * @param bd
   */
  public void update(Bd bd);
  
  /**
   * 删除BD
   * @param bdCode
   */
  public void delete(String bdCode);
  
  /**
   * 根据BD 代码获取BD
   * @param bdCode
   * @return
   */
  public Bd loadByBdCode(String bdCode);
}
