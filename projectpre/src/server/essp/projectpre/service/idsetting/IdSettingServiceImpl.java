package server.essp.projectpre.service.idsetting;

import java.util.ArrayList;
import java.util.List;


import net.sf.hibernate.Session;

import server.essp.projectpre.db.IdSetting;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;

public class IdSettingServiceImpl extends AbstractBusinessLogic implements
		IIdSettingService {

	public List listAllByKinds(String[] kind) throws BusinessException {
        List codeList = new ArrayList();
        try {
            // �Ȼ��Session
//            Session session = this.getDbAccessor().getSession();

            // ����HQL��ѯ����
//            Query query = session
//                    .createQuery("from idSetting as t where t.kind=:kind and t.kind=:kind1")
//                    .setString("kind",kind);
//                   .setting("kind",kind1);

            // ��ѯ
    //        codeList = query.list();
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        return codeList;
    }

	/**
	 * ��������趨����
	 */
	public void save(IdSetting idSetting){
        if(this.loadByKey(idSetting.getIdType())!=null) {
            throw new BusinessException("error.logic.SiteServiceImpl.codeduplicate");
        }
        this.getDbAccessor().save(idSetting);
    }

	/**
	 * ���´����趨����
	 */
	public void update(IdSetting idSetting) {
        this.getDbAccessor().update(idSetting);

	}

	/**
	 * ɾ�������趨����
	 */
	public void delete(String kind) throws BusinessException {
		// TODO Auto-generated method stub

	}

	/**
	 * ���ݴ����趨���ͻ�ȡ�����趨����
	 */
	public IdSetting loadByKey(String kind) throws BusinessException {
        IdSetting idSetting = null;
        try {
            // �Ȼ��Session
            Session session = this.getDbAccessor().getSession();

            // ��ѯ����
            idSetting = (IdSetting) session.createQuery("from IdSetting as t where t.idType=:idType")
            .setString("idType",kind)
            .setMaxResults(1)
            .uniqueResult();

        } catch (Exception e) {
            // �����������ϲ㼴�ɣ��˴������Ĵ����Ϊ�������ݿ��������
            throw new BusinessException("error.system.db", e);
        }
        return idSetting;
    }
	

}
