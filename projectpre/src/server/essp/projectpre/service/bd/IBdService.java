package server.essp.projectpre.service.bd;

import java.util.List;

import server.essp.projectpre.db.Bd;
import server.framework.common.BusinessException;

public interface IBdService {

  /**
   * �ӻ�ȡBd���л�ȡ���еļ�¼
   * @return
   * @throws BusinessException
   */
  public List listAll() throws BusinessException;
  
  /**
   * �ӻ�ȡBd���л�ȡ����StatusΪtrue�ļ�¼
   * @return
   * @throws BusinessException
   */
  public List listAllEabled() throws BusinessException;
  
  /**
   * �ӻ�ȡBd���л�ȡ���õ�ҵ������
   * @return
   * @throws BusinessException
   */
  public List listEabledBelongs(boolean isAchieveBelong) throws BusinessException;
  
  /**
   * ����DB
   * @param bd
   */
  public void save(Bd bd);
  
  /**
   * ����BD
   * @param bd
   */
  public void update(Bd bd);
  
  /**
   * ɾ��BD
   * @param bdCode
   */
  public void delete(String bdCode);
  
  /**
   * ����BD �����ȡBD
   * @param bdCode
   * @return
   */
  public Bd loadByBdCode(String bdCode);
}
