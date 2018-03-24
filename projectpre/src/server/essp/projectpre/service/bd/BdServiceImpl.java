package server.essp.projectpre.service.bd;

import java.util.List;
import java.util.ArrayList;

import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import server.essp.projectpre.db.Bd;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;

/**
 * 所有操作BD Code的服务
 * 
 * @author Stephen.Zheng
 * 
 */
public class BdServiceImpl extends AbstractBusinessLogic implements IBdService {
    
	/**
	 * 从获取Bd表中获取所有的记录
	 */
	public List listAll() throws BusinessException {
        List codeList = new ArrayList();
        try {
            // 先获得Session
            Session session = this.getDbAccessor().getSession();

            // 创建HQL查询对象
            Query query = session
                    .createQuery("from Bd as t order by t.bdName");

            // 查询
            codeList = query.list();
        } catch (Exception e) {
            // 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
            throw new BusinessException("error.system.db", e);
        }
        return codeList;
	}

	/**
	 * 从获取Bd表中获取所有Status为true的记录
	 */
	public List listAllEabled() throws BusinessException {
        List codeList = new ArrayList();
       
        try {
            // 先获得Session
            Session session = this.getDbAccessor().getSession();

            // 创建HQL查询对象
            Query query = session
                    .createQuery("from Bd as t where status=:status order by t.bdCode")
                    .setBoolean("status",true);
            // 查询
            codeList = query.list();
        } catch (Exception e) {
            // 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
            throw new BusinessException("error.system.db", e);
        }
        return codeList;
	}
	/**
	 * 从获取Bd表中获取可用的业绩归属
	 */
	public List listEabledBelongs(boolean isAchieveBelong) throws BusinessException {
        List codeList = new ArrayList();
       
        try {
            // 先获得Session
            Session session = this.getDbAccessor().getSession();

            // 创建HQL查询对象
            Query query = session
                    .createQuery("from Bd as t where t.status=:status and t.achieveBelong=:achieveBelong order by t.bdCode")
                    .setBoolean("status",true)
                    .setBoolean("achieveBelong", isAchieveBelong);
            // 查询
            codeList = query.list();
        } catch (Exception e) {
            // 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
            throw new BusinessException("error.system.db", e);
        }
        return codeList;
	}
	/**
	 * 保存DB
	 */
	public void save(Bd bd) {
        if(this.loadByBdCode(bd.getBdCode())!=null) {
            throw new BusinessException("error.logic.BdServiceImpl.codeduplicate");
        }
        this.getDbAccessor().save(bd);

	}

	/**
	 * 更新BD
	 */
	public void update(Bd bd) {
        this.getDbAccessor().update(bd);

	}

	/**
	 * 删除BD
	 */
	public void delete(String bdCode) {
        try {
            if(bdCode==null||"".equals(bdCode)) return;
            Bd bd=this.loadByBdCode(bdCode);
            if(bd!=null){
                this.getDbAccessor().delete(bd);
            }
        }catch(Exception e) {
            throw new BusinessException("error.system.db", e);
        }

	}

	/**
	 * 根据BD 代码获取BD
	 */
	public Bd loadByBdCode(String bdCode) {
        Bd bd = null;
        try {
            // 先获得Session
            Session session = this.getDbAccessor().getSession();

            // 查询对象
            bd = (Bd) session.createQuery("from Bd as t where t.bdCode=:bdCode")
            .setString("bdCode",bdCode)
            .setMaxResults(1)
            .uniqueResult();

        } catch (Exception e) {
            // 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
            throw new BusinessException("error.system.db", e);
        }
        return bd;
	}

}
