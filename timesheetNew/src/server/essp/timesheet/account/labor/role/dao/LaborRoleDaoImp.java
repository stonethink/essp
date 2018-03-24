package server.essp.timesheet.account.labor.role.dao;

import java.util.List;

import db.essp.timesheet.TsLaborRole;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;
import net.sf.hibernate.type.LongType;
import db.essp.timesheet.TsLaborResource;

/**
 * <p>Title: LaborRoleDaoImp</p>
 *
 * <p>Description: Labor role service data access object implement</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class LaborRoleDaoImp
        extends HibernateDaoSupport implements ILaborRoleDao {

    /**
     * ����һ����¼
     *
     * @param role TsLaborRole
     */
    public void add(TsLaborRole role) {
        this.getHibernateTemplate().save(role);
    }

    /**
     * ɾ��һ��Role��¼
     *
     * @param role Long
     */
    public void delete(TsLaborRole role) {
        //��TsLaborResource��ɾ���Դ�Role������
        clearLaborResourceRole(role.getRid());
        this.getHibernateTemplate().delete(role);
    }

    /**
     * ��ѯ��һ��seq��Role����
     *
     * @param seq Long
     * @return TsLaborRole
     */
    public TsLaborRole getDownSeqLaborRole(Long seq) {
        List<TsLaborRole> list =
                this.getHibernateTemplate()
                .find("from TsLaborRole where seq > ? order by seq asc", seq);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * ��ȡ����Seq
     *
     * @return Long
     */
    public Long getMaxSeq() {
        List<TsLaborRole> list = this.getHibernateTemplate()
                                .find("from TsLaborRole order by seq desc");
        if (list.size() > 0) {
            return list.get(0).getSeq();
        }
        return Long.valueOf(1);
    }

    /**
     * ��ѯ��һ��seq��Role����
     *
     * @param seq Long
     * @return TsLaborRole
     */
    public TsLaborRole getUpSeqLaborRole(Long seq) {
        List<TsLaborRole> list =
                this.getHibernateTemplate()
                .find("from TsLaborRole where seq < ? order by seq desc", seq);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * �õ�����Role��¼
     *
     * @return List
     */
    public List listLaborRole() {
        return this.getHibernateTemplate().find("from TsLaborRole order by seq");
    }

    /**
     * ����seq
     *
     * @param rid Long
     * @param seq Long
     */
    public void setSeq(Long rid, Long seq) {
        TsLaborRole bean = (TsLaborRole)
                          this.getHibernateTemplate().load(TsLaborRole.class, rid);
        bean.setSeq(seq);
        update(bean);
    }

    /**
     * ����һ����¼
     *
     * @param role TsLaborRole
     */
    public void update(TsLaborRole role) {
        if(role.getIsEnable() == false) {
            ///��TsLaborResource��ɾ���Դ�Role������
            clearLaborResourceRole(role.getRid());
        }
        this.getHibernateTemplate().update(role);

    }

    /**
     * ��TsLaborResource�����ô�Role�ļ�¼�ÿ�
     * @param roleRid Long
     */
    private void clearLaborResourceRole(Long roleRid) {
        List<TsLaborResource> labors = this.getHibernateTemplate()
                                   .find(
                "from TsLaborResource where role_rid = ?", roleRid, new LongType());
        for(TsLaborResource labor : labors) {
            labor.setRoleRid(null);
            this.getHibernateTemplate().update(labor);
        }
    }
    /**
     * ����Rid��ȡrole����
     */
	public TsLaborRole getRoleByRid(Long rid) {
		return (TsLaborRole)
        this.getHibernateTemplate().load(TsLaborRole.class, rid);
		
	}

}
