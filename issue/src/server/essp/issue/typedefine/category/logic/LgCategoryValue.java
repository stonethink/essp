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
 * ����IssueCategoryValueҵ���߼�
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
     * ��������typeName,categoryName,categoryValue����IssueCategoryValue
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
     * ����AfCategoryValue����IssueCategoryValue����                                     <br>
     * 1. �жϸ�IssueType��IssueCategoryType���Ƿ�����ͬ����IssueCategoryValue,�жϷ���Ϊ��<br>
     * 2. ʹ��Session.get()������������  IssueCategoryValue����                    <br>
     * 3. if   IssueCategoryValue��Ϊ��                    <br>&ensp;&ensp;&ensp;&ensp;&ensp;
     *          if    IssueCategoryValue״̬���� then  �����˳���                             <br>&ensp;&ensp;&ensp;&ensp;&ensp;
     *          else  then ����IssueCategoryValue״̬Ϊ����,����������IssueCategoryValue���ԣ�  <br>
     *    else  then ��AfCategoryValue������IssueCategoryValue;��
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
     * ��������typeName,categoryName,categoryValue�߼�ɾ��IssueCategoryValue                    <br>
     * 1.  ��������typeName��categoryName��categoryValue load() IssueCategoryValue����         <br>
     * 2.  ����IssueCategoryValue.statusΪdisable
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
     * ���ݴ����AfCategory�޸�IssueCategoryValue����<br>
     * 1  ��������typeName,categoryName��categoryValue load() IssueCategoryValue����<br>
     * 2  ����IssueCategoryValue�������������
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
