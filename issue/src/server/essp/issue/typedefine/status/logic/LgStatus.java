package server.essp.issue.typedefine.status.logic;

import server.framework.logic.AbstractBusinessLogic;
import server.essp.issue.typedefine.status.form.AfStatus;
import db.essp.issue.IssueStatus;
import com.wits.util.Parameter;
import server.framework.common.BusinessException;
import net.sf.hibernate.Session;
import db.essp.issue.IssueStatusPK;
import java.lang.Long;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.HibernateException;
import server.framework.common.Constant;
import net.sf.hibernate.Query;
import java.util.List;

public class LgStatus extends AbstractBusinessLogic {

    private Session session = null;

    public LgStatus() {
    }

    /**
     * 依据传入的AfStatus信息新增IssueStatus对象: <br>
     * 1. 判断该IssueType中是否存在同名的IssueStatus,判断方法为： <br>
     * 2. 根据主键typeName和status使用Session的get()获得IssueStatus对象<br>
     * 3. if IssueStatus不为空　                            <br>&ensp;&ensp;&ensp;&ensp;&ensp;
     *          if    IssueStatus状态可用,  报错退出；                               <br>&ensp;&ensp;&ensp;&ensp;&ensp;
     *          else  设置IssueStatus状态为可用，并覆盖输入IssueRisk属性；  <br>
     *    else  取得AfStatus信息新增到IssueStatus;　
     * @param form AfStatus
     * @throws BusinessException
     */
    public void add(AfStatus form) throws BusinessException {

        try {
            session = this.getDbAccessor().getSession();

            IssueStatusPK pk = new IssueStatusPK(form.getTypeName(), form.getStatusName());
            IssueStatus status = (IssueStatus) session.get(IssueStatus.class, pk);
            if (status != null){

                if (status.getRst().equals(Constant.RST_NORMAL)){
                    throw new BusinessException("issue.status.add.exist.error","Issuetype status's name has existed!!");
                }else{
                    status.setRst(Constant.RST_NORMAL);
                    status.setBelongTo(form.getStatusBelongTo());
                    status.setRelationDate(form.getStatusRelationDate());
                    status.setSequence(new Long(form.getStatusSequence()));
                    status.setDescription(form.getStatusDescription());
                    session.update(status);
                }
           } else{
               IssueStatus newStatus = new IssueStatus(pk);
               newStatus.setRst(Constant.RST_NORMAL);
               newStatus.setBelongTo(form.getStatusBelongTo());
               newStatus.setRelationDate(form.getStatusRelationDate());
               newStatus.setSequence(new Long(form.getStatusSequence()));
               newStatus.setDescription(form.getStatusDescription());
               session.save(newStatus);
           }

        } catch (Exception ex) {
            log.error("Error: Save Issue Status failed!");
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.status.add.error","Issuetype status add exception!");

        }
    }

    /**
     * 依据从AfStatus获得主键typeName,status载入IssueStatus
     * @param typeName String
     * @param status String
     * @throws BusinessException
     * @return IssueStatus
     */
    public IssueStatus load(String typeName, String status) throws
            BusinessException {
        IssueStatus issueStatus = null;
        try {
            session = this.getDbAccessor().getSession();
            IssueStatusPK pk = new IssueStatusPK(typeName, status);
            issueStatus = (IssueStatus) session.load(IssueStatus.class, pk);
        } catch (Exception ex) {
            log.error("Error: load Issue Status failed!");
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.status.load.error","Issuetype status load exception!");

        }
        return issueStatus;
    }

    /**
     * 依据传入的AfStatus修改IssueStatus对象<br>
     * 1  根据主键typeName和status load() IssueRisk对象  <br>
     * 2  设置IssueStatus对象的其他属性  <br>
     * @param form AfStatus
     * @throws BusinessException
     */
    public void update(AfStatus form) throws BusinessException {

        try {
            session = this.getDbAccessor().getSession();
            IssueStatus status = (IssueStatus) session.load(IssueStatus.class, new IssueStatusPK(form.getTypeName(),form.getStatusName()));
            status.setBelongTo(form.getStatusBelongTo());
            status.setRelationDate(form.getStatusRelationDate());
            status.setSequence(new Long(form.getStatusSequence()));
            status.setDescription(form.getStatusDescription());
            this.getDbAccessor().update(status);
        } catch (Exception ex) {
            log.error("Error: Update Issue Status failed!");
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.status.update.error","Issuetype status update exception!");
        }

    }

    /**
     * 依据typeName，status逻辑删除IssueStatus   <br>
     * 1.  根据主键typeName，status load() IssueRisk对象   <br>
     * 2.  设置IssueStatus.status为disable  <br>
     * @param typeName String
     * @param status String
     * @throws BusinessException
     */
    public void delete(String typeName, String status) throws BusinessException {

        try {
            session = this.getDbAccessor().getSession();
            IssueStatus delStatus = (IssueStatus) session.load(IssueStatus.class, new IssueStatusPK(typeName, status));
            delStatus.setRst(Constant.RST_DELETE);
            session.update(delStatus);
        } catch (Exception ex) {
            log.error("Error: Delete Issue Status failed!");
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.status.delete.error","Issuetype status delete exception!");

        }
    }

    public long getMaxSequence(String typeName) {
        long sequence=1;
        try {
            session = this.getDbAccessor().getSession();
            Query q=session.createQuery("from IssueStatus s where s.comp_id.typeName='"+typeName+"' order by s.sequence desc");
            List results=q.list();
            if(results!=null && results.size()>0) {
                IssueStatus firstRecord=(IssueStatus)results.get(0);
                sequence=firstRecord.getSequence().longValue()+1;
            }
        } catch(Exception e) {

        }
        return sequence;
    }
}
