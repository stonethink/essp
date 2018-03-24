package server.essp.timesheet.code.coderelation.dao;

import java.util.List;

import db.essp.timesheet.TsCodeRelation;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;
import net.sf.hibernate.type.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class CodeRelationDaoImp extends HibernateDaoSupport implements ICodeRelationDao {

    private final static LongType longType = new LongType();
    private final static StringType stringType = new StringType();

    /**
     * 新增code relation
     *
     * @param relation TsCodeRelation
     * @todo Implement this
     *   server.essp.timesheet.code.coderelation.dao.ICodeRelationDao method
     */
    public void add(TsCodeRelation relation) {
        this.getHibernateTemplate().save(relation);
    }

    /**
     * 删除某code type下所有的code relation
     *
     * @param typeRid Long
     * @param isLeaveType String
     * @todo Implement this
     *   server.essp.timesheet.code.coderelation.dao.ICodeRelationDao method
     */
    public void deleteRelations(Long typeRid, String isLeaveType) {
        this.getHibernateTemplate()
                .delete("from TsCodeRelation where type_rid = ? and is_leave_type = ?",
                        new Object[]{typeRid, isLeaveType}, new Type[]{longType, stringType});
    }

    /**
     * 列出某code type下所有的code relation
     *
     * @param typeRid Long
     * @param isLeaveType String
     * @return List
     * @todo Implement this
     *   server.essp.timesheet.code.coderelation.dao.ICodeRelationDao method
     */
    public List listRelation(Long typeRid, String isLeaveType) {
        return this.getHibernateTemplate()
                .find("from TsCodeRelation where type_rid = ? and is_leave_type = ?",
                      new Object[]{typeRid, isLeaveType}, new Type[]{longType, stringType});
    }
}
