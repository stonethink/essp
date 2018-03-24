package server.essp.timesheet.code.codetype.dao;

import org.springframework.orm.hibernate.support.HibernateDaoSupport;
import java.util.List;
import db.essp.timesheet.TsCodeType;
import net.sf.hibernate.type.LongType;
import db.essp.timesheet.TsAccount;
import c2s.essp.timesheet.code.DtoCodeKey;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class CodeTypeDaoImp extends HibernateDaoSupport implements ICodeTypeDao {

    private final static LongType longType = new LongType();

    /**
     * 获取最大的Seq
     * @return Long
     */
    public Long getMaxSeq() {
        List<TsCodeType> list = this.getHibernateTemplate()
                                .find("from TsCodeType order by seq desc");
        if (list.size() > 0) {
            return list.get(0).getSeq();
        }
        return Long.valueOf(1);
    }

    /**
     * 删除一条CodeType记录
     * @param rid Long
     */
    public void delete(TsCodeType codeType) {
        Long rid = codeType.getRid();
        //先删除所有与此codeType相关的Relation
        deleteRelation(rid);
        //取消Account对此Code Type的引用
        setAccountCodeTypeRidNull(rid);
        //删除codeType
        this.getHibernateTemplate().delete(codeType);
    }

    /**
     * 先删除所有与特定codeType相关的Relation
     * @param typeRid Long
     */
    private void deleteRelation(Long typeRid) {
        this.getHibernateTemplate()
                .delete("from TsCodeRelation where type_rid = ?",
                        typeRid, longType);
    }

    /**
     * 取消Account对特定Code Type的引用
     * @param typeRid Long
     */
    private void setAccountCodeTypeRidNull(Long typeRid) {
        List<TsAccount> acntList = this.getHibernateTemplate()
                                   .find(
                "from TsAccount where code_type_rid = ?", typeRid, longType);
        for(TsAccount account : acntList) {
            account.setCodeTypeRid(null);
            this.getHibernateTemplate().update(account);
        }
    }

    /**
     * 查询上一个seq的CodeType资料
     * @param seq Long
     * @return TsCodeType
     */
    public TsCodeType getUpSeqCodeType(Long seq) {
        List<TsCodeType> list =
                this.getHibernateTemplate()
                .find("from TsCodeType where seq < ? order by seq desc", seq);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询下一个seq的CodeType资料
     * @param seq Long
     * @return TsCodeType
     */
    public TsCodeType getDownSeqCodeType(Long seq) {
        List<TsCodeType> list =
                this.getHibernateTemplate()
                .find("from TsCodeType where seq > ? order by seq asc", seq);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


    /**
     * 得到所有CodeType记录
     * @param isLeaveType String
     * @return List
     */
    public List listCodeType(String isLeaveType) {
        return this.getHibernateTemplate().find("from TsCodeType where is_leave_type = ? order by seq", isLeaveType);
    }

    /**
     * 新增一条记录
     * @param codeType TsCodeType
     */
    public void add(TsCodeType codeType) {
        this.getHibernateTemplate().save(codeType);
    }

    /**
     * 更新一条记录
     * @param codeType TsCodeType
     */
    public void update(TsCodeType codeType) {
        if(codeType.getIsEnable() == false) {
            //取消Account对此Code Type的引用
            setAccountCodeTypeRidNull(codeType.getRid());
        }
        this.getHibernateTemplate().update(codeType);
    }

    /**
     * 设置seq
     * @param rid Long
     * @param seq Long
     */
    public void setSeq(Long rid, Long seq) {
        TsCodeType bean = (TsCodeType)
                          this.getHibernateTemplate().load(TsCodeType.class, rid);
        bean.setSeq(seq);
        update(bean);
    }

    /**
     * 得到CodeType
     * @param rid Long
     * @return TsCodeType
     */
    public TsCodeType getCodeType(Long rid) {
        String isEnable = DtoCodeKey.CODE_ENABLE_TRUE;
        List<TsCodeType> list = this.getHibernateTemplate()
                                .find("from TsCodeType where rid = ? and is_enable = ?",
                                      new Object[]{rid,isEnable});
        TsCodeType tsCodeType = null;
        if (list != null && list.size() > 0) {
            tsCodeType = (TsCodeType) list.get(0);
        }
        return tsCodeType;
    }

}
