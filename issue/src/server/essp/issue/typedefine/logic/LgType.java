package server.essp.issue.typedefine.logic;

import db.essp.issue.*;
import net.sf.hibernate.*;
import server.essp.issue.typedefine.form.*;
import server.essp.issue.typedefine.viewbean.*;
import server.framework.common.*;
import server.framework.logic.*;
import c2s.dto.DtoUtil;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author QianLiu
 * @version 1.0*/
public class LgType extends AbstractBusinessLogic {
    private VbType oneViewBean = new VbType();
    public LgType() {
    }

    /**
     * 依据传入的typeName获取该IssueType对象
     * @param typeName String
     * @return IssueType
     */
    public IssueType load(String typeName) throws BusinessException {
        IssueType issueType = new IssueType();
        try {
            Session session = this.getDbAccessor().getSession();
            issueType = (IssueType) session.load(IssueType.class, typeName);
            return issueType;
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.typedefine.load.exception",
                                        "Load issuetype define error!");
        }
    }

    public VbType getOneViewBean(String typeName) throws BusinessException {
        IssueType it = this.load(typeName);
        if (it != null) {
            this.oneViewBean.setTypeName(it.getTypeName());
            if (it.getRst() != null) {
                this.oneViewBean.setStatus(it.getRst());
            }
            this.oneViewBean.setSequence(String.valueOf(it.getSequence()));
            this.oneViewBean.setDescription(it.getDescription());
            this.oneViewBean.setSaveStatusHistory(it.getSaveStatusHistory());
            this.oneViewBean.setSaveInfluenceHistory(it.getSaveInfluenceHistory());
        }
        return this.oneViewBean;
    }

    /**
     * 依据传入的typeName获取该AfType对象
     * {
     * 1. 调用以上IssueType load(String typeName)方法
     * 2. 将IssueType转换为AfType返回
     * }
     * @param typeName String
     * @return AfType
     */
    public AfType loadAfType(String typeName) {
        return null;
    }

    /**
     * 依据传入的AfType新增IssueType对象: <br>
     * 1. 判断该IssueType中是否存在同名的IssueType,判断方法为： <br>
     * 2. 根据主键typeName使用Session的get()获得IssueType对象<br>
     * 3. if IssueType不为空,  报错退出；                               <br>&ensp;&ensp;&ensp;&ensp;&ensp;
     *    else  将AfType新增到IssueType表;　
     * @param form AfType
     * @throws BusinessException
     */
    public void add(AfType afType) throws BusinessException {
        String typeName = afType.getTypeName().toUpperCase();
        try {
            Session session = this.getDbAccessor().getSession();
            IssueType issueType = (IssueType) session.get(IssueType.class,
                typeName);
            if (issueType != null) {
                if (Constant.RST_NORMAL.equals(issueType.getRst())) {
                    throw new BusinessException("issue.type.exist",
                                                "Same Issuetype has existed!!");
                } else {
                    issueType.setRst(afType.getRst());
                    issueType.setDescription(afType.getDescription());
                    issueType.setSequence(new Long(afType.getSequence()));
                    if(afType.getSaveStatusHistory()!=null) {
                        issueType.setSaveStatusHistory(afType.
                            getSaveStatusHistory());
                    } else {
                        issueType.setSaveStatusHistory("N");
                    }
                    if(afType.getSaveInfluenceHistory()!=null) {
                        issueType.setSaveInfluenceHistory(afType.getSaveInfluenceHistory());
                    } else {
                        issueType.setSaveInfluenceHistory("N");
                    }
                    this.getDbAccessor().update(issueType);
                }
            } else {
                issueType = new IssueType();
                issueType.setTypeName(typeName);
                issueType.setRst(afType.getRst());
                issueType.setDescription(afType.getDescription());
                issueType.setSequence(new Long(afType.getSequence()));
                if (afType.getSaveStatusHistory() != null) {
                    issueType.setSaveStatusHistory(afType.
                        getSaveStatusHistory());
                } else {
                    issueType.setSaveStatusHistory("N");
                }
                if (afType.getSaveInfluenceHistory() != null) {
                    issueType.setSaveInfluenceHistory(afType.
                        getSaveInfluenceHistory());
                } else {
                    issueType.setSaveInfluenceHistory("N");
                }

                //DtoUtil.copyProperties(issueType,afType);
                session.save(issueType);
            }
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("issue.typedefine.add.exception",
                                        "Add issuetype error!");
        }

    }

    /**
     * 依据传入的AfType修改IssueType
     * typeName是不可修改
     * @param afType AfType
     * @throws BusinessException
     */
    public void update(AfType afType) throws BusinessException {
        String typeName = afType.getTypeName().toUpperCase();
        try {
            IssueType issueType = this.load(typeName);
            issueType.setDescription(afType.getDescription());
            issueType.setSequence(new Long(afType.getSequence()));
            issueType.setRst(afType.getRst());
            if (afType.getSaveStatusHistory() != null) {
                issueType.setSaveStatusHistory(afType.
                                               getSaveStatusHistory());
            } else {
                issueType.setSaveStatusHistory("N");
            }
            if (afType.getSaveInfluenceHistory() != null) {
                issueType.setSaveInfluenceHistory(afType.
                                                  getSaveInfluenceHistory());
            } else {
                issueType.setSaveInfluenceHistory("N");
            }
            this.getDbAccessor().update(issueType);
            if(afType.getRst()!=null && afType.getRst().equals(Constant.RST_DELETE)) {
                this.delete(afType);
            }
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("issue.typedefine.update.exception",
                                        "update issuetype define error!");
        }

    }

    /**
     * 依据传入的AfType修改IssueType
     * typeName是不可修改
     * @param afType AfType
     * @throws BusinessException
     */
    public void delete(AfType afType) throws BusinessException {
        String typeName = afType.getTypeName().toUpperCase();
        try {
            IssueType issueType = this.load(typeName);
            issueType.setRst(Constant.RST_DELETE);
            this.getDbAccessor().update(issueType);

            this.getDbAccessor().executeUpdate(
                "update issue_priority      set rst='" + Constant.RST_DELETE +
                "' where type_name='" + typeName + "'");
            this.getDbAccessor().executeUpdate(
                "update issue_scope         set rst='" + Constant.RST_DELETE +
                "' where type_name='" + typeName + "'");
            this.getDbAccessor().executeUpdate(
                "update issue_status        set rst='" + Constant.RST_DELETE +
                "' where type_name='" + typeName + "'");
            this.getDbAccessor().executeUpdate(
                "update issue_risk          set rst='" + Constant.RST_DELETE +
                "' where type_name='" + typeName + "'");
            this.getDbAccessor().executeUpdate(
                "update issue_category_type set rst='" + Constant.RST_DELETE +
                "' where type_name='" + typeName + "'");
            this.getDbAccessor().executeUpdate(
                "update issue_category      set rst='" + Constant.RST_DELETE +
                "' where type_name='" + typeName + "'");
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("issue.typedefine.delete.exception",
                                        "delete issuetype error!");
        }
    }

    private IssueType lnkIssueType;
}
