
package server.essp.hrbase.dept.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import server.essp.hrbase.dept.dao.IDeptDao;
import server.essp.hrbase.dept.form.AfDeptQuery;
import server.framework.common.BusinessException;
import server.framework.taglib.util.SelectOptionImpl;
import c2s.dto.DtoUtil;
import c2s.dto.IDto;

import com.wits.util.comDate;

import db.essp.hrbase.HrbUnitBase;

/**
 * 部門維護的SERVICE
 * @author TBH
 */
public class DeptServiceImp implements IDeptService{
    private IDeptDao deptDao;
    
    /**
     * 根據查詢條件查出符合條件的數據
     * @param af
     * @return List
     */
    public List queryByCondition(AfDeptQuery af) {
            HrbUnitBase unit = new HrbUnitBase();
            DtoUtil.copyBeanToBean(unit,af);
            List resultList = deptDao.query(unit,af.getEffectiveBegin(),af.getEffectiveEnd());
            return resultList;
    }
   
    /**
     * 新增
     * @param af
     */
    public void addDept(AfDeptQuery af) {
           HrbUnitBase unit = new HrbUnitBase();
           HrbUnitBase unitBase = deptDao.loadByDeptId(af.getUnitCode());
           if(unitBase == null){
             try{
               DtoUtil.copyBeanToBean(unit,af);
               if(af.getEffectiveBegin() != null && !"".equals(af.
                       getEffectiveBegin().trim())){
                   Date effBegin = comDate.toDate(af.getEffectiveBegin());
                   unit.setEffectiveBegin(effBegin);
               }
               if(af.getEffectiveEnd() != null && !"".equals(af.
                       getEffectiveEnd().trim())){
                   Date effEnd = comDate.toDate(af.getEffectiveEnd());
                   unit.setEffectiveEnd(effEnd);
               }
               if(af.getIsBl() == null || "".equals(af.getIsBl())) {
            	   unit.setIsBl("0");
               }
               unit.setEffectiveDate(new Date());
               unit.setAcntAttribute("Dept");
               unit.setRst(IDto.OP_NOCHANGE);
               deptDao.save(unit);
             }catch(BusinessException ex){
               String message = "Unit Code is exist!";
               throw new BusinessException(ex.getErrorCode(), message);
             }
           }
    }

    /**
     * 更新
     * @param af
     */
    public void updateDept(AfDeptQuery af) {
            HrbUnitBase unitBase = deptDao.loadByDeptId(af.getUnitCode());
            if(unitBase != null){
              try{
                DtoUtil.copyBeanToBean(unitBase,af);
                if(af.getEffectiveBegin() != null && !"".equals(af.
                        getEffectiveBegin().trim())){
                    Date effBegin = comDate.toDate(af.getEffectiveBegin());
                    unitBase.setEffectiveBegin(effBegin);
                }
                if(af.getEffectiveEnd() != null && !"".equals(af.
                        getEffectiveEnd().trim())){
                    Date effEnd = comDate.toDate(af.getEffectiveEnd());
                    unitBase.setEffectiveEnd(effEnd);
                }
                if(af.getIsBl() == null || "".equals(af.getIsBl())) {
                	unitBase.setIsBl("0");
                }
                deptDao.update(unitBase);
            }catch(BusinessException ex){
                String message = "Unit Code is not exist!";
                throw new BusinessException(ex.getErrorCode(), message);
             }
            }
    }

    /**
     * 刪除
     * @param unitCode
     */
    public void deleteDept(String unitCode) {
            HrbUnitBase unitBase = deptDao.loadByDeptId(unitCode);
            if(unitBase != null){
            try{
                unitBase.setRst(IDto.OP_DELETE);
                deptDao.update(unitBase);
            }catch(BusinessException ex){
                String message = "Unit Code: "+unitCode +" is not exist!";
                throw new BusinessException(ex.getErrorCode(), message);
             }
            }
    }

    /**
     * 根據部門代碼查詢
     * @param unitCode
     * @return AfDeptQuery
     */
    public AfDeptQuery queryByDeptId(String unitCode) {
            AfDeptQuery af = new AfDeptQuery();
            HrbUnitBase unitBase = deptDao.loadByDeptId(unitCode);
            if(unitBase != null){
               DtoUtil.copyBeanToBean(af,unitBase);
            }
            return af;
    }
    
    /**
     * 得到所有的部門代碼
     * @return List
     */
    public List getAllUnit() {
            List dList = deptDao.getAllUnit();
            SelectOptionImpl deptOption = new SelectOptionImpl("--Please select--","");
            List resultList = new ArrayList();
            resultList.add(deptOption);
            if(dList != null && dList.size() > 0){
                for(int i=0;i<dList.size();i++){
                    HrbUnitBase unit = (HrbUnitBase)dList.get(i);
                    deptOption = new SelectOptionImpl(unit.getUnitCode(),unit.getUnitCode());
                    resultList.add(deptOption);
                }
            }
            return resultList;
    }
    
    /**
     * 得到除當前部門代碼的其他所有代碼
     * @param unitCode
     * @return List
     */
    public List getParentUnit(String unitCode) {
            List parUnitList = deptDao.getParentUnit(unitCode);
            SelectOptionImpl deptOption = new SelectOptionImpl("--Please select--","");
            List resultList = new ArrayList();
            resultList.add(deptOption);
            if(parUnitList != null && parUnitList.size() > 0){
                for(int i=0;i<parUnitList.size();i++){
                    HrbUnitBase unit = (HrbUnitBase)parUnitList.get(i);
                    deptOption = new SelectOptionImpl(unit.getUnitCode(),unit.getUnitCode());
                    resultList.add(deptOption);
                }
            }
            return resultList;
    }

    
    public void setDeptDao(IDeptDao deptDao) {
        this.deptDao = deptDao;
    }

	public HrbUnitBase getByUnitCode(String unitCode) {
		return deptDao.loadByDeptId(unitCode);
	}

}
