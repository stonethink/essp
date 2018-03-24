/*
 * Created on 2007-12-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.bd.service;

import java.util.List;
import server.essp.hrbase.bd.form.AfBd;

public interface IBdService{
    /**
     * �г����е�BD�Y��
     * @return List
     */
    public List listAllBdCode();
    
    /**
     * �г���Ч��BD����
     * @return List
     */
    public List listEnabledBdCode();
    
    /**
     * �г���Ч��ҵ��������λ
     * @return List
     */
    public List listAchieveBelongUnit();
    
    /**
     * ����BdCode�õ�Ԕ����Ϣ
     * @param bdCode
     * @return AfBd
     */
    public AfBd getDetailInfo(String bdCode);
    
    /**
     * ����
     * @param afBd
     */
    public void add(AfBd afBd);
    
    /**
     * ����
     * @param afBd
     */
    public void update(AfBd afBd);
    
    /**
     * �h��
     * @param bdCode
     */
    public void delete(String bdCode);
   
}
