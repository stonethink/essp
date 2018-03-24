package server.essp.timesheet.account.labor.level.dao;

import java.util.List;

import db.essp.timesheet.TsLaborLevel;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;
import db.essp.timesheet.TsLaborResource;
import net.sf.hibernate.type.LongType;

/**
 * <p>Title: LaborLevelDaoImp</p>
 *
 * <p>Description: Labor level data access object implement</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class LaborLevelDaoImp
        extends HibernateDaoSupport implements ILaborLevelDao {

    /**
     * ����һ����¼
     *
     * @param level TsLaborLevel
     */
    public void add(TsLaborLevel level) {
         this.getHibernateTemplate().save(level);
    }

    /**
     * ɾ��һ��Level��¼
     *
     * @param level Long
     */
    public void delete(TsLaborLevel level) {
        //��TsLaborResource��ɾ���Դ�Level������
        clearLaborResourceLevel(level.getRid());
        this.getHibernateTemplate().delete(level);
    }

    /**
     * ��ѯ��һ��seq��Level����
     *
     * @param seq Long
     * @return TsLaborLevel
     */
    public TsLaborLevel getDownSeqLaborLevel(Long seq) {
        List<TsLaborLevel> list =
                this.getHibernateTemplate()
                .find("from TsLaborLevel where seq > ? order by seq asc", seq);
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
        List<TsLaborLevel> list = this.getHibernateTemplate()
                                .find("from TsLaborLevel order by seq desc");
        if (list.size() > 0) {
            return list.get(0).getSeq();
        }
        return Long.valueOf(1);
    }

    /**
     * ��ѯ��һ��seq��Level����
     *
     * @param seq Long
     * @return TsLaborLevel
     */
    public TsLaborLevel getUpSeqLaborLevel(Long seq) {
        List<TsLaborLevel> list =
                this.getHibernateTemplate()
                .find("from TsLaborLevel where seq < ? order by seq desc", seq);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * �õ�����Level��¼
     *
     * @return List
     */
    public List listLaborLevel() {
        return this.getHibernateTemplate().find("from TsLaborLevel order by seq");
    }

    /**
     * ����seq
     *
     * @param rid Long
     * @param seq Long
     */
    public void setSeq(Long rid, Long seq) {
        TsLaborLevel bean = (TsLaborLevel)
                          this.getHibernateTemplate().load(TsLaborLevel.class, rid);
        bean.setSeq(seq);
        update(bean);

    }

    /**
     * ����һ����¼
     *
     * @param level TsLaborLevel
     */
    public void update(TsLaborLevel level) {
        if(level.getIsEnable() == false) {
            ///��TsLaborResource��ɾ���Դ�Level������
            clearLaborResourceLevel(level.getRid());
        }
        this.getHibernateTemplate().update(level);
    }

    /**
     * ��TsLaborResource�����ô�Level�ļ�¼�ÿ�
     * @param levelRid Long
     */
    private void clearLaborResourceLevel(Long levelRid) {
        List<TsLaborResource> labors = this.getHibernateTemplate()
                                   .find(
                "from TsLaborResource where level_rid = ?", levelRid, new LongType());
        for(TsLaborResource labor : labors) {
            labor.setLevelRid(null);
            this.getHibernateTemplate().update(labor);
        }
    }
    /**
     * ����Rid��ȡLevel����
     */
	public TsLaborLevel getLevelByRid(Long rid) {
		return (TsLaborLevel)
                    this.getHibernateTemplate().load(TsLaborLevel.class, rid);
	}
}
