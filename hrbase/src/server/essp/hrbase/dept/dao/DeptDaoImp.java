/*
 * Created on 2007-12-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.dept.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.sf.hibernate.type.DateType;
import net.sf.hibernate.type.Type;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;
import com.wits.util.comDate;
import db.essp.hrbase.HrbUnitBase;
/**
 * 部門信息DAO
 * @author TBH
 */
public class DeptDaoImp extends HibernateDaoSupport implements IDeptDao{

    /**
     * 根據查詢條件查詢
     * @param unit
     * @return List
     */
    public List query(HrbUnitBase unit,String beginDate,String endDate) {
            List args = new ArrayList();
            List<Type> types = new ArrayList<Type>();
            StringBuffer sb = new StringBuffer("from HrbUnitBase where acnt_attribute='Dept' and rst='N'");
            if(unit.getUnitCode() != null && !unit.getUnitCode().trim().equals("")){
                sb.append(" and lower(unit_code) like lower('%"+unit.getUnitCode().trim()+"%')");
            }
            if(unit.getUnitName() != null && !unit.getUnitName().trim().equals("")){
                sb.append(" and lower(unit_name) like lower('%"+unit.getUnitName().trim()+"%')");
            }
            if(unit.getBelongBd() != null && !("").equals(unit.getBelongBd().trim())){
                sb.append(" and belong_bd ='"+unit.getBelongBd().trim()+"'");
            }
            if(unit.getBelongSite() != null && !"".equals(unit.getBelongSite().trim())){
                sb.append(" and belong_site ='"+unit.getBelongSite().trim()+"'");
            }
            if(unit.getDmName() != null && !"".equals(unit.getDmName().trim())){
                sb.append(" and lower(dm_Name) like lower('%"+unit.getDmName().trim()+"%')");
            }
            if(unit.getBdName() != null && !"".equals(unit.getBdName().trim())){
                sb.append(" and lower(bd_Name) like lower('%"+unit.getBdName().trim()+"%')");
            }
            if(unit.getTsName() != null && !"".equals(unit.getTsName().trim())){
                sb.append(" and lower(ts_Name) like lower('%"+unit.getTsName().trim()+"%')");
            }
            if(unit.getUnitFullName() != null && !"".equals(unit.getUnitFullName().trim())){
                sb.append(" and lower(unit_Full_Name) like lower('%"+unit.getUnitFullName().trim()+"%')");
            }
            if(unit.getParentUnitCode() != null && !("").equals(unit.getParentUnitCode().trim())){
                sb.append(" and Parent_Unit_Code ='"+unit.getParentUnitCode()+"'");
            }
            if(unit.getCostBelongUnit() != null && !("").equals(unit.getCostBelongUnit().trim())){
                sb.append(" and cost_Belong_Unit ='"+unit.getCostBelongUnit()+"'");
            }
            if(beginDate != null && !("").equals(beginDate.trim())){
                sb.append(" and effective_begin>=?");
                Date begin = comDate.toDate(beginDate);
                args.add(begin);
                types.add(new DateType());
            }
            if(endDate != null && !("").equals(endDate.trim())){
                sb.append(" and effective_end<=?");
                Date end = comDate.toDate(endDate);
                args.add(end);
                types.add(new DateType());
            }
            if (args.size() > 0) {
                return this.getHibernateTemplate().find(sb.toString(),
                        args.toArray(), (Type[]) list2Types(types));
            } else {
                return this.getHibernateTemplate().find(sb.toString());
            }
           
    }
    
    private final static Type[] list2Types(List<Type> list) {
        if(list == null || list.size() < 1) {
            return null;
        }
        Type[] types = new Type[list.size()];
        for(int i = 0; i < list.size(); i ++) {
            types[i] = list.get(i);
        }
        return types;
    }

    /**
     * 保存
     * @param unit
     */
      public void save(HrbUnitBase unit) {
          this.getHibernateTemplate().save(unit);
        }
      
      /**
       * 更新
       * @param unit
       */
      public void update(HrbUnitBase unit) {
           this.getHibernateTemplate().update(unit);
        }

      /**
       * 根據部門代碼查詢
       * @param unitCode
       * @return HrbUnitBase
       */
     public HrbUnitBase loadByDeptId(String unitCode) {
        String sql = "from HrbUnitBase where unit_Code=? order by unit_Code";
        List list = (List)this.getHibernateTemplate()
                .find(sql, new Object[] {unitCode});
        if(list != null && list.size() >0 ){
            return (HrbUnitBase)list.get(0);
        }
        return null;
    }

     /**
      * 得到所有的部門代碼
      * @return List
      */
    public List getAllUnit() {
        String sql = "from HrbUnitBase order by unit_Code";
        List unitList = (List)this.getHibernateTemplate().find(sql);
        return unitList;
    }

    /**
     * 得到除當前部門代碼的其他所有部門代碼
     * @param unitCode
     * @return List
     */
    public List getParentUnit(String unitCode) {
        String sql = "from HrbUnitBase where unit_Code<>? order by unit_Code";
        List unitList = (List)this.getHibernateTemplate().
                        find(sql,new Object[] {unitCode});
        return unitList;
    }
    
    public HrbUnitBase getUnitBaseByRid(Long rid) {
		String sql = "from HrbUnitBase where rid=?";
		List list = this.getHibernateTemplate().find(sql, rid);
		if(list != null && list.size() > 0) {
			return (HrbUnitBase)list.get(0);
		}
		return null;
	}

}
