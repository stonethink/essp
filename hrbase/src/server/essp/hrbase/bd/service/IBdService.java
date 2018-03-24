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
     * 列出所有的BD資料
     * @return List
     */
    public List listAllBdCode();
    
    /**
     * 列出有效的BD集合
     * @return List
     */
    public List listEnabledBdCode();
    
    /**
     * 列出有效的业绩归属单位
     * @return List
     */
    public List listAchieveBelongUnit();
    
    /**
     * 根據BdCode得到詳細信息
     * @param bdCode
     * @return AfBd
     */
    public AfBd getDetailInfo(String bdCode);
    
    /**
     * 新增
     * @param afBd
     */
    public void add(AfBd afBd);
    
    /**
     * 更新
     * @param afBd
     */
    public void update(AfBd afBd);
    
    /**
     * 刪除
     * @param bdCode
     */
    public void delete(String bdCode);
   
}
