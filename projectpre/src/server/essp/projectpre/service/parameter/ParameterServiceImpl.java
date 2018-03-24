package server.essp.projectpre.service.parameter;

import java.util.ArrayList;
import java.util.List;

import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.db.ParameterId;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;

public class ParameterServiceImpl extends AbstractBusinessLogic implements
		IParameterService {

	/**
	 * 根据类型列出参数资料
	 */
	public List listAllByKind(String kind) throws BusinessException {
        List codeList = new ArrayList();
        try {
            // 先获得Session
            Session session = this.getDbAccessor().getSession();
            // 创建HQL查询对象
            String querySql="from Parameter t where t.compId.kind='"+kind+"' order by t.sequence";
            Query query = session.createQuery(querySql);
            
            // 查询
            codeList = query.list();
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        return codeList;
    }
    
	/**
	 * 保存参数资料
	 */
	public void save(Parameter parameter) {
        if(this.loadByKey(parameter.getCompId())!=null) {
            throw new BusinessException("error.logic.ParameterServiceImpl.codeduplicate");
        }
        this.getDbAccessor().save(parameter);
    }

	/**
	 * 更新参数资料
	 */
	public void update(Parameter parameter) {
		
        this.getDbAccessor().update(parameter);

	}

	/**
	 * 删除参数资料
	 */
	public void delete(ParameterId parameterId) throws BusinessException {
        try {
            Parameter parameter=this.loadByKey(parameterId);
            if(parameter==null) return;
            this.getDbAccessor().delete(parameter);
        }catch(Exception e) {
            throw new BusinessException("error.system.db", e);
        }
    }
    
	/**
	 * 获取参数资料
	 */
	public Parameter loadByKey(ParameterId parameterId)
			throws BusinessException {
        Parameter parameter = null;
        try {
            Session session = this.getDbAccessor().getSession();
            String queryStr="from Parameter as t where t.compId.code='"+parameterId.getCode()+"' and t.compId.kind='"+parameterId.getKind()+"'";
            Query q=session.createQuery(queryStr);
//            q.setString("code",parameterId.getCode());
//            q.setString("kind",parameterId.getKind());
            List l=q.list();
            if(l.size()>0)
            parameter=(Parameter)l.get(0);
           
//            parameter = (Parameter) session.createQuery("from Parameter as t where t.compId.code=:code and t.compId.kind=:kind")
//            .setString("code",parameterId.getCode())
//            .setString("kind",parameterId.getKind())
//            .setMaxResults(1)
//            .uniqueResult();
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        return parameter;
    }

	/**
	 * 根据参数类型列出所有Status为true的参数资料
	 */
	public List listAllByKindEnable(String kind) throws BusinessException {
		
        List codeList = new ArrayList();
        try {
            // 先获得Session
            Session session = this.getDbAccessor().getSession();
            // 创建HQL查询对象
            
            Query query = session
                      .createQuery("from Parameter as t where t.compId.kind=:kind and t.status=:status order by t.sequence")
                      .setString("kind",kind)
                      .setBoolean("status",true);
            
            // 查询
            codeList = query.list();
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        return codeList;
	}

	/**
	 * 根据参数类型和代码查询参数资料
	 */
    public Parameter loadByKindCode(String kind, String code) throws BusinessException {
        Parameter parameter = new Parameter();
        try {
            Session session = this.getDbAccessor().getSession();
            
            parameter = (Parameter)session.createQuery("from Parameter as t where t.compId.kind=:kind and t.compId.code=:code")
                                 .setString("kind", kind)
                                 .setString("code", code)
                                 .setMaxResults(1)
                                 .uniqueResult();
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        
        
        
        return parameter;
    }
}
