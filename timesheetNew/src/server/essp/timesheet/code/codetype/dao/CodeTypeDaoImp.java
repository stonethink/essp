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
     * ��ȡ����Seq
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
     * ɾ��һ��CodeType��¼
     * @param rid Long
     */
    public void delete(TsCodeType codeType) {
        Long rid = codeType.getRid();
        //��ɾ���������codeType��ص�Relation
        deleteRelation(rid);
        //ȡ��Account�Դ�Code Type������
        setAccountCodeTypeRidNull(rid);
        //ɾ��codeType
        this.getHibernateTemplate().delete(codeType);
    }

    /**
     * ��ɾ���������ض�codeType��ص�Relation
     * @param typeRid Long
     */
    private void deleteRelation(Long typeRid) {
        this.getHibernateTemplate()
                .delete("from TsCodeRelation where type_rid = ?",
                        typeRid, longType);
    }

    /**
     * ȡ��Account���ض�Code Type������
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
     * ��ѯ��һ��seq��CodeType����
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
     * ��ѯ��һ��seq��CodeType����
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
     * �õ�����CodeType��¼
     * @param isLeaveType String
     * @return List
     */
    public List listCodeType(String isLeaveType) {
        return this.getHibernateTemplate().find("from TsCodeType where is_leave_type = ? order by seq", isLeaveType);
    }

    /**
     * ����һ����¼
     * @param codeType TsCodeType
     */
    public void add(TsCodeType codeType) {
        this.getHibernateTemplate().save(codeType);
    }

    /**
     * ����һ����¼
     * @param codeType TsCodeType
     */
    public void update(TsCodeType codeType) {
        if(codeType.getIsEnable() == false) {
            //ȡ��Account�Դ�Code Type������
            setAccountCodeTypeRidNull(codeType.getRid());
        }
        this.getHibernateTemplate().update(codeType);
    }

    /**
     * ����seq
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
     * �õ�CodeType
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
