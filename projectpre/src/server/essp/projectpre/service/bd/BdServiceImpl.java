package server.essp.projectpre.service.bd;

import java.util.List;
import java.util.ArrayList;

import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import server.essp.projectpre.db.Bd;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;

/**
 * ���в���BD Code�ķ���
 * 
 * @author Stephen.Zheng
 * 
 */
public class BdServiceImpl extends AbstractBusinessLogic implements IBdService {
    
	/**
	 * �ӻ�ȡBd���л�ȡ���еļ�¼
	 */
	public List listAll() throws BusinessException {
        List codeList = new ArrayList();
        try {
            // �Ȼ��Session
            Session session = this.getDbAccessor().getSession();

            // ����HQL��ѯ����
            Query query = session
                    .createQuery("from Bd as t order by t.bdName");

            // ��ѯ
            codeList = query.list();
        } catch (Exception e) {
            // �����������ϲ㼴�ɣ��˴������Ĵ����Ϊ�������ݿ��������
            throw new BusinessException("error.system.db", e);
        }
        return codeList;
	}

	/**
	 * �ӻ�ȡBd���л�ȡ����StatusΪtrue�ļ�¼
	 */
	public List listAllEabled() throws BusinessException {
        List codeList = new ArrayList();
       
        try {
            // �Ȼ��Session
            Session session = this.getDbAccessor().getSession();

            // ����HQL��ѯ����
            Query query = session
                    .createQuery("from Bd as t where status=:status order by t.bdCode")
                    .setBoolean("status",true);
            // ��ѯ
            codeList = query.list();
        } catch (Exception e) {
            // �����������ϲ㼴�ɣ��˴������Ĵ����Ϊ�������ݿ��������
            throw new BusinessException("error.system.db", e);
        }
        return codeList;
	}
	/**
	 * �ӻ�ȡBd���л�ȡ���õ�ҵ������
	 */
	public List listEabledBelongs(boolean isAchieveBelong) throws BusinessException {
        List codeList = new ArrayList();
       
        try {
            // �Ȼ��Session
            Session session = this.getDbAccessor().getSession();

            // ����HQL��ѯ����
            Query query = session
                    .createQuery("from Bd as t where t.status=:status and t.achieveBelong=:achieveBelong order by t.bdCode")
                    .setBoolean("status",true)
                    .setBoolean("achieveBelong", isAchieveBelong);
            // ��ѯ
            codeList = query.list();
        } catch (Exception e) {
            // �����������ϲ㼴�ɣ��˴������Ĵ����Ϊ�������ݿ��������
            throw new BusinessException("error.system.db", e);
        }
        return codeList;
	}
	/**
	 * ����DB
	 */
	public void save(Bd bd) {
        if(this.loadByBdCode(bd.getBdCode())!=null) {
            throw new BusinessException("error.logic.BdServiceImpl.codeduplicate");
        }
        this.getDbAccessor().save(bd);

	}

	/**
	 * ����BD
	 */
	public void update(Bd bd) {
        this.getDbAccessor().update(bd);

	}

	/**
	 * ɾ��BD
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
	 * ����BD �����ȡBD
	 */
	public Bd loadByBdCode(String bdCode) {
        Bd bd = null;
        try {
            // �Ȼ��Session
            Session session = this.getDbAccessor().getSession();

            // ��ѯ����
            bd = (Bd) session.createQuery("from Bd as t where t.bdCode=:bdCode")
            .setString("bdCode",bdCode)
            .setMaxResults(1)
            .uniqueResult();

        } catch (Exception e) {
            // �����������ϲ㼴�ɣ��˴������Ĵ����Ϊ�������ݿ��������
            throw new BusinessException("error.system.db", e);
        }
        return bd;
	}

}
