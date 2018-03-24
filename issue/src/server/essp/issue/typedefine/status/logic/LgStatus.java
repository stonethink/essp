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
     * ���ݴ����AfStatus��Ϣ����IssueStatus����: <br>
     * 1. �жϸ�IssueType���Ƿ����ͬ����IssueStatus,�жϷ���Ϊ�� <br>
     * 2. ��������typeName��statusʹ��Session��get()���IssueStatus����<br>
     * 3. if IssueStatus��Ϊ�ա�                            <br>&ensp;&ensp;&ensp;&ensp;&ensp;
     *          if    IssueStatus״̬����,  �����˳���                               <br>&ensp;&ensp;&ensp;&ensp;&ensp;
     *          else  ����IssueStatus״̬Ϊ���ã�����������IssueRisk���ԣ�  <br>
     *    else  ȡ��AfStatus��Ϣ������IssueStatus;��
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
     * ���ݴ�AfStatus�������typeName,status����IssueStatus
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
     * ���ݴ����AfStatus�޸�IssueStatus����<br>
     * 1  ��������typeName��status load() IssueRisk����  <br>
     * 2  ����IssueStatus�������������  <br>
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
     * ����typeName��status�߼�ɾ��IssueStatus   <br>
     * 1.  ��������typeName��status load() IssueRisk����   <br>
     * 2.  ����IssueStatus.statusΪdisable  <br>
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
