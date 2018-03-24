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
	 * ���������г���������
	 */
	public List listAllByKind(String kind) throws BusinessException {
        List codeList = new ArrayList();
        try {
            // �Ȼ��Session
            Session session = this.getDbAccessor().getSession();
            // ����HQL��ѯ����
            String querySql="from Parameter t where t.compId.kind='"+kind+"' order by t.sequence";
            Query query = session.createQuery(querySql);
            
            // ��ѯ
            codeList = query.list();
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        return codeList;
    }
    
	/**
	 * �����������
	 */
	public void save(Parameter parameter) {
        if(this.loadByKey(parameter.getCompId())!=null) {
            throw new BusinessException("error.logic.ParameterServiceImpl.codeduplicate");
        }
        this.getDbAccessor().save(parameter);
    }

	/**
	 * ���²�������
	 */
	public void update(Parameter parameter) {
		
        this.getDbAccessor().update(parameter);

	}

	/**
	 * ɾ����������
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
	 * ��ȡ��������
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
	 * ���ݲ��������г�����StatusΪtrue�Ĳ�������
	 */
	public List listAllByKindEnable(String kind) throws BusinessException {
		
        List codeList = new ArrayList();
        try {
            // �Ȼ��Session
            Session session = this.getDbAccessor().getSession();
            // ����HQL��ѯ����
            
            Query query = session
                      .createQuery("from Parameter as t where t.compId.kind=:kind and t.status=:status order by t.sequence")
                      .setString("kind",kind)
                      .setBoolean("status",true);
            
            // ��ѯ
            codeList = query.list();
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        return codeList;
	}

	/**
	 * ���ݲ������ͺʹ����ѯ��������
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
