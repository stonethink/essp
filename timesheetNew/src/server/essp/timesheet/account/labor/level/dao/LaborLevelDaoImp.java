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
     * 新增一条记录
     *
     * @param level TsLaborLevel
     */
    public void add(TsLaborLevel level) {
         this.getHibernateTemplate().save(level);
    }

    /**
     * 删除一条Level记录
     *
     * @param level Long
     */
    public void delete(TsLaborLevel level) {
        //在TsLaborResource中删除对此Level的引用
        clearLaborResourceLevel(level.getRid());
        this.getHibernateTemplate().delete(level);
    }

    /**
     * 查询下一个seq的Level资料
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
     * 获取最大的Seq
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
     * 查询上一个seq的Level资料
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
     * 得到所有Level记录
     *
     * @return List
     */
    public List listLaborLevel() {
        return this.getHibernateTemplate().find("from TsLaborLevel order by seq");
    }

    /**
     * 设置seq
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
     * 更新一条记录
     *
     * @param level TsLaborLevel
     */
    public void update(TsLaborLevel level) {
        if(level.getIsEnable() == false) {
            ///在TsLaborResource中删除对此Level的引用
            clearLaborResourceLevel(level.getRid());
        }
        this.getHibernateTemplate().update(level);
    }

    /**
     * 在TsLaborResource中引用此Level的记录置空
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
     * 根据Rid获取Level资料
     */
	public TsLaborLevel getLevelByRid(Long rid) {
		return (TsLaborLevel)
                    this.getHibernateTemplate().load(TsLaborLevel.class, rid);
	}
}
