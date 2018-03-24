package server.essp.issue.typedefine.category.logic;

import server.framework.logic.AbstractBusinessLogic;
import db.essp.issue.IssueCategoryValue;
import server.framework.common.BusinessException;
import server.essp.issue.typedefine.category.form.AfCategoryValue;
import db.essp.issue.IssueCategoryValuePK;
import net.sf.hibernate.Session;
import net.sf.hibernate.*;
import server.framework.common.Constant;
import java.util.List;

/**
 * 处理IssueCategoryValue业务逻辑
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class LgCategoryValue extends AbstractBusinessLogic {
    public LgCategoryValue() {
    }

    /**
     * 根据主键typeName,categoryName,categoryValue查找IssueCategoryValue
     * @param typeName String
     * @param categoryName String
     * @param categoryValue String
     * @throws BusinessException
     * @return IssueCategoryValue
     */
    public IssueCategoryValue load(String typeName, String categoryName,
                                   String categoryValue) throws
        BusinessException {
        IssueCategoryValuePK pk = new IssueCategoryValuePK(typeName,
            categoryName, categoryValue);
        try {
            Session s = this.getDbAccessor().getSession();
            IssueCategoryValue value = (IssueCategoryValue) s.load(
                IssueCategoryValue.class, pk);
            return value;
        } catch (Exception ex) {
            throw new BusinessException("issue.category.value.load.exception",
                                        "load issuetype category value error!");
        }
    }

    /**
     * 根据AfCategoryValue新增IssueCategoryValue对象                                     <br>
     * 1. 判断该IssueType和IssueCategoryType下是否否存在同名的IssueCategoryValue,判断方法为：<br>
     * 2. 使用Session.get()根据主键查找  IssueCategoryValue对象                    <br>
     * 3. if   IssueCategoryValue不为空                    <br>&ensp;&ensp;&ensp;&ensp;&ensp;
     *          if    IssueCategoryValue状态可用 then  报错退出；                             <br>&ensp;&ensp;&ensp;&ensp;&ensp;
     *          else  then 设置IssueCategoryValue状态为可用,并覆盖输入IssueCategoryValue属性；  <br>
     *    else  then 将AfCategoryValue新增到IssueCategoryValue;　
     * @param form AfCategoryValue
     * @throws BusinessException
     */
    public void add(AfCategoryValue form) throws BusinessException {
        String typeName = form.getTypeName();
        String categoryName = form.getCategoryName();
        String categoryValue = form.getCategoryValue();
        IssueCategoryValuePK pk = new IssueCategoryValuePK(typeName,
            categoryName, categoryValue);
        try {
            Session s = this.getDbAccessor().getSession();
            IssueCategoryValue value = (IssueCategoryValue) s.get(
                IssueCategoryValue.class, pk);
            if (value != null) {
                if (Constant.RST_NORMAL.equals(value.getRst())) {
                    throw new BusinessException("issue.category.value.exist",
                        "Issuetype category value's sequence or name has existed!!");
                } else {
                    value.setRst(Constant.RST_NORMAL);
                    c2s.dto.DtoUtil.copyProperties(value, form);
                }
            } else {
                value = new IssueCategoryValue(pk);
                c2s.dto.DtoUtil.copyProperties(value, form);
                value.setRst(Constant.RST_NORMAL);
                s.save(value);
            }
            s.flush();
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("issue.category.value.add.exception",
                                        "add issuetype category value error!");
        }
    }

    /**
     * 根据主键typeName,categoryName,categoryValue逻辑删除IssueCategoryValue                    <br>
     * 1.  根据主键typeName，categoryName，categoryValue load() IssueCategoryValue对象         <br>
     * 2.  设置IssueCategoryValue.status为disable
     * @param typeName String
     * @param categoryName String
     * @param categoryValue String
     * @throws BusinessException
     */
    public void delete(String typeName, String categoryName,
                       String categoryValue) throws BusinessException {
        try {
            IssueCategoryValue value = this.load(typeName, categoryName,
                                                 categoryValue);
            value.setRst(Constant.RST_DELETE);
            this.getDbAccessor().update(value);
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("issue.category.value.delete.exception",
                "delete issuetype category value error!");
        }
    }

    /**
     * 依据传入的AfCategory修改IssueCategoryValue对象<br>
     * 1  根据主键typeName,categoryName和categoryValue load() IssueCategoryValue对象<br>
     * 2  设置IssueCategoryValue对象的其他属性
     * @param form AfCategoryValue
     */
    public void update(AfCategoryValue form) throws BusinessException {
        try {
            IssueCategoryValue value = this.load(form.getTypeName(),
                                                 form.getCategoryName(),
                                                 form.getCategoryValue());
            c2s.dto.DtoUtil.copyProperties(value, form);
            this.getDbAccessor().update(value);
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("issue.category.update.exception",
                                        "update issuetype category  error!");
        }
    }

    public long getMaxSequence(String typeName, String categoryName) {
        long sequence = 1;
        try {
            Session session = this.getDbAccessor().getSession();
            Query q = session.createQuery(
                "from IssueCategoryValue s where s.comp_id.typeName='" +
                typeName + "' and s.comp_id.categoryName='" + categoryName +
                "' order by s.sequence desc");
            List results = q.list();
            if (results != null && results.size() > 0) {
                IssueCategoryValue firstRecord = (IssueCategoryValue) results.
                                                 get(0);
                sequence = firstRecord.getSequence().longValue() + 1;
            }
        } catch (Exception e) {

        }
        return sequence;
    }
}
