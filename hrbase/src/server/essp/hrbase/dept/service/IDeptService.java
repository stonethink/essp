/*
 * Created on 2007-12-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.dept.service;

import java.util.List;

import db.essp.hrbase.HrbUnitBase;
import server.essp.hrbase.dept.form.AfDeptQuery;
/**
 * 部門維護的SERVICE
 * @author LION
 */
public interface IDeptService {
    /**
     * 根據查詢條件查出符合條件的數據
     * @param af
     * @return List
     */
    public List queryByCondition(AfDeptQuery af);
    
    /**
     * 新增
     * @param af
     */
    public void addDept(AfDeptQuery af);
    
    /**
     * 更新
     * @param af
     */
    public void updateDept(AfDeptQuery af);
    
    /**
     * 刪除
     * @param unitCode
     */
    public void deleteDept(String unitCode);
    
    /**
     * 根據部門代碼查詢
     * @param unitCode
     * @return AfDeptQuery
     */
    public AfDeptQuery queryByDeptId(String unitCode);
    
    /**
     * 得到所有的部門代碼
     * @return List
     */
    public List getAllUnit();
    
    /**
     * 得到除當前部門代碼的其他所有代碼
     * @param unitCode
     * @return List
     */
    public List getParentUnit(String unitCode);
    
    public HrbUnitBase getByUnitCode(String unitCode);

}
