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
 * ���T�S�o��SERVICE
 * @author LION
 */
public interface IDeptService {
    /**
     * ������ԃ�l��������ϗl���Ĕ���
     * @param af
     * @return List
     */
    public List queryByCondition(AfDeptQuery af);
    
    /**
     * ����
     * @param af
     */
    public void addDept(AfDeptQuery af);
    
    /**
     * ����
     * @param af
     */
    public void updateDept(AfDeptQuery af);
    
    /**
     * �h��
     * @param unitCode
     */
    public void deleteDept(String unitCode);
    
    /**
     * �������T���a��ԃ
     * @param unitCode
     * @return AfDeptQuery
     */
    public AfDeptQuery queryByDeptId(String unitCode);
    
    /**
     * �õ����еĲ��T���a
     * @return List
     */
    public List getAllUnit();
    
    /**
     * �õ�����ǰ���T���a���������д��a
     * @param unitCode
     * @return List
     */
    public List getParentUnit(String unitCode);
    
    public HrbUnitBase getByUnitCode(String unitCode);

}
