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
            // 先获得Session
//            Session session = this.getDbAccessor().getSession();

            // 创建HQL查询对象
//            Query query = session
//                    .createQuery("from idSetting as t where t.kind=:kind and t.kind=:kind1")
//                    .setString("kind",kind);
//                   .setting("kind",kind1);

            // 查询
    //        codeList = query.list();
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        return codeList;
    }

	/**
	 * 保存代码设定资料
	 */
	public void save(IdSetting idSetting){
        if(this.loadByKey(idSetting.getIdType())!=null) {
            throw new BusinessException("error.logic.SiteServiceImpl.codeduplicate");
        }
        this.getDbAccessor().save(idSetting);
    }

	/**
	 * 更新代码设定资料
	 */
	public void update(IdSetting idSetting) {
        this.getDbAccessor().update(idSetting);

	}

	/**
	 * 删除代码设定资料
	 */
	public void delete(String kind) throws BusinessException {
		// TODO Auto-generated method stub

	}

	/**
	 * 根据代码设定类型获取代码设定资料
	 */
	public IdSetting loadByKey(String kind) throws BusinessException {
        IdSetting idSetting = null;
        try {
            // 先获得Session
            Session session = this.getDbAccessor().getSession();

            // 查询对象
            idSetting = (IdSetting) session.createQuery("from IdSetting as t where t.idType=:idType")
            .setString("idType",kind)
            .setMaxResults(1)
            .uniqueResult();

        } catch (Exception e) {
            // 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
            throw new BusinessException("error.system.db", e);
        }
        return idSetting;
    }
	

}
