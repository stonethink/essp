package server.essp.timesheet.code.codevalue.dao;

import java.util.List;

import db.essp.timesheet.TsCodeValue;
import net.sf.hibernate.type.*;

import org.springframework.orm.hibernate.support.HibernateDaoSupport;
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
public class CodeValueDaoImp extends HibernateDaoSupport implements
        ICodeValueDao {

    private final static LongType longType = new LongType();
    private final static StringType stringType = new StringType();
    private final static LongType[] longTypes = {longType, longType};
    private final static Long FIRST_SEQ = new Long(1);

    /**
     * 根据传入参数获取Job Code或Leave Code列表, Seq升序
     * @return List
     */
    public List listLeaveCodeValue() {
        return this.getHibernateTemplate()
                .find("from TsCodeValue where is_leave_type = '1' order by seq");
    }

    /**
     * 获取Job Code根节点Bean
     * @param isLeavelType String
     * @return TsCodeValue
     */
    public TsCodeValue getRootJobCodeValue(String isLeavelType) {
        List<TsCodeValue> list =
            this.getHibernateTemplate()
            .find("from TsCodeValue "
                  + "where is_leave_type = ? and parent_rid is null", isLeavelType);
        if(list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据父节点Rid获取所有子节点, Seq升序
     * @param parentRid Long
     * @param isLeaveType String
     * @return List
     */
    public List listJobCodeValueByParentRid(Long parentRid, String isLeaveType) {

        return this.getHibernateTemplate()
            .find("from TsCodeValue where is_leave_type = ? "
                  + "and parent_rid = ? order by seq", 
                  new Object[]{isLeaveType, parentRid}, new Type[]{stringType, longType} );
    }


    public void add(TsCodeValue tsJobCode) {
    	if(tsJobCode.getIsLeaveType() == null) {
    		tsJobCode.setIsLeaveType(true);
    	}
        this.getHibernateTemplate().save(tsJobCode);
    }

    /**
     * 得到JobCode最大的SEQ
     * @param isLeaveType String
     * @param parentRid Long
     * @return Long
     */
    public Long getJobCodeMaxSeq(Long parentRid, String isLeaveType) {
        List<TsCodeValue> list =
            this.getHibernateTemplate()
            .find( "from TsCodeValue where is_leave_type = ? "
                   + " and parent_rid = ? order by seq desc", new Object[]{isLeaveType, parentRid}, 
                   new Type[]{stringType, longType});
        if (list.size() > 0) {
            return list.get(0).getSeq();
        }
        return FIRST_SEQ;
    }

    /**
     * 得到Leave Code最大的SEQ
     * @return Long
     */
    public Long getLeaveCodeMaxSeq() {
        List<TsCodeValue> list =
            this.getHibernateTemplate()
            .find( "from TsCodeValue where is_leave_type = '1' order by seq desc");
        if (list.size() > 0) {
            return list.get(0).getSeq();
        }
        return FIRST_SEQ;
    }


    public void update(TsCodeValue jobCode) {
        this.getHibernateTemplate().update(jobCode);
    }

    /**
     * 更新isEnable属性
     * @param rid Long
     * @param isEnable Boolean
     */
    public void updateEnable(Long rid, Boolean isEnable) {
        TsCodeValue bean = (TsCodeValue) this.getHibernateTemplate()
                           .load(TsCodeValue.class, rid);
        bean.setIsEnable(isEnable);
        this.getHibernateTemplate().update(bean);
    }


    public void delete(TsCodeValue codeValue) {
        //先删除所有与此codeValue相关的Relation
        this.getHibernateTemplate()
                .delete("from TsCodeRelation where value_rid = ?",
                        codeValue.getRid(), longType);
        //删除codeValue
        this.getHibernateTemplate().delete(codeValue);
    }

    public void setSeq(Long codeValueRid, Long seq) {
        TsCodeValue codeValue =
                (TsCodeValue)this.getHibernateTemplate().load(TsCodeValue.class,
                codeValueRid);
        codeValue.setSeq(seq);
        this.getHibernateTemplate().update(codeValue);
    }

    public TsCodeValue getCodeValue(Long rid) {
        TsCodeValue codeValue =
                (TsCodeValue)this.getHibernateTemplate().load(TsCodeValue.class,
                rid);
        return codeValue;
    }

    /**
     * 获取上一个JobCodeValue
     * @param parentRid Long
     * @param seq Long
     * @param isLeaveType String
     * @return TsCodeValue
     */
    public TsCodeValue getUpSeqJobCodeValue(Long parentRid, Long seq, String isLeaveType) {
        List<TsCodeValue> list =
            this.getHibernateTemplate()
            .find( "from TsCodeValue where is_leave_type = ? "
                   + " and parent_rid = ? and seq < ? order by seq desc",
                   new Object[]{isLeaveType, parentRid, seq}, new Type[]{stringType, longType, longType});
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 获取上一个LeaveCodeValue
     * @param seq Long
     * @return TsCodeValue
     */
    public TsCodeValue getUpSeqLeaveCodeValue(Long seq) {
        List<TsCodeValue> list =
            this.getHibernateTemplate()
            .find( "from TsCodeValue where is_leave_type = '1' "
                   + " and seq < ? order by seq desc", seq, longType);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 获取下一个JobCodeValue
     * @param parentRid Long
     * @param seq Long
     * @param isLeaveType String
     * @return TsCodeValue
     */
    public TsCodeValue getDownSeqJobCodeValue(Long parentRid, Long seq, String isLeaveType) {
        List<TsCodeValue> list =
            this.getHibernateTemplate()
            .find( "from TsCodeValue where is_leave_type = ? "
                   + " and parent_rid = ? and seq > ? order by seq asc",
                   new Object[]{isLeaveType, parentRid, seq}, new Type[]{stringType, longType, longType});
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 获取下一个LeaveCodeValue
     * @param seq Long
     * @return TsCodeValue
     */
    public TsCodeValue getDownSeqLeaveCodeValue(Long seq) {
        List<TsCodeValue> list =
            this.getHibernateTemplate()
            .find( "from TsCodeValue where is_leave_type = '1' "
                   + " and seq > ? order by seq asc", seq, longType);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 列出所有的有效假别记录
     * @return TsCodeValue
     */
    public List getLeaveCode() {
        String isLeaveType = DtoCodeKey.IS_LEAVE_CODE_TRUE;
        String isEnable = DtoCodeKey.CODE_ENABLE_TRUE;
        List list = this.getHibernateTemplate().find(
                "from TsCodeValue where is_leave_type = ? and is_enable=?",
                new String[] {isLeaveType, isEnable});
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    public TsCodeValue isExistWorkCode(String workCode) {
        List<TsCodeValue> list = this.getHibernateTemplate()
            .find( "from TsCodeValue where name = ?", workCode);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    /**
     * 列出某个父节点下的seq之后的子节点
     */
	public List<TsCodeValue> getChildrenAfterSeq(Long parentRid, Long seq) {
		return this.getHibernateTemplate()
        				.find( "from TsCodeValue where parent_rid = ? and seq >= ? order by seq asc",
        				new Object[]{parentRid, seq}, longTypes);
	}

    public TsCodeValue findCodeValue(Long rid) {
        List list = (List)this.getHibernateTemplate()
        .find("from TsCodeValue where rid =?",rid, longType);
        if(list != null && list.size()>0){
            return (TsCodeValue)list.get(0);
        }
        return null;
    }

    /**
     * 根据typeRid和name查找
     * @param typeRid Long
     * @param name String
     * @return TsCodeValue
     */
	public TsCodeValue findByTypeRidName(Long typeRid, String name) {
		String hql = "select t from TsCodeValue t, TsCodeRelation r "
					+ " where t.name = ? and r.typeRid = ? and t.rid = r.valueRid ";
		List list = this.getHibernateTemplate()
				.find(hql, 
				new Object[]{name, typeRid},
				new Type[]{stringType, longType});
		if(list != null && list.size() > 0) {
			return (TsCodeValue)list.get(0);
		} else {
			return null;
		}
	}
}
