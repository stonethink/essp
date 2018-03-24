package server.essp.issue.typedefine.risk.logic;

import server.framework.logic.AbstractBusinessLogic;
import db.essp.issue.IssueRisk;
import server.essp.issue.typedefine.risk.form.AfRisk;
import com.wits.util.Parameter;
import server.framework.common.BusinessException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import db.essp.issue.IssueRiskPK;
import net.sf.hibernate.HibernateException;
import java.lang.Long;
import server.framework.common.Constant;
import net.sf.hibernate.Query;
import java.util.List;


public class LgRisk extends AbstractBusinessLogic {
    private Session session = null;
    private Transaction tx = null;

    public LgRisk() {
    }

    /**
     * ���ݴ�AfRisk�������typeName,riskInfluence����IssueRisk
     * @param typeName String
     * @param riskInfluence String
     * @throws BusinessException
     * @return IssueRisk
     */
    public IssueRisk load(String typeName, String riskInfluence) throws
            BusinessException {
        IssueRisk issueRisk = null;
        try {
            session = this.getDbAccessor().getSession();
            tx = session.beginTransaction();
            IssueRiskPK pk = new IssueRiskPK(typeName, riskInfluence);
            issueRisk = (IssueRisk) session.load(IssueRisk.class, pk);
        } catch (Exception ex) {
            log.error("Error: Load Issue Risk failed!");
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.risk.load.error","Issuetype risk load exception!");
        }
        return issueRisk;
     }

    /**
     * ���ݴ����AfRisk��Ϣ����IssueRisk����: <br>
     * 1. �жϸ�IssueType���Ƿ����ͬ����IssueRisk,�жϷ���Ϊ�� <br>
     * 2. ��������typeName��riskInfluenceʹ��Session��get()���IssueRisk����<br>
     * 3. if IssueRisk��Ϊ�ա�                            <br>&ensp;&ensp;&ensp;&ensp;&ensp;
     *          if    IssueRisk״̬����,  �����˳���                               <br>&ensp;&ensp;&ensp;&ensp;&ensp;
     *          else  ����IssueRisk״̬Ϊ���ã�����������IssueRisk���ԣ�  <br>
     *    else  ȡ��AfRisk��Ϣ������IssueRisk;��
     * @param form AfRisk
     * @throws BusinessException
     */
    public void add(AfRisk form) throws BusinessException {

        try {
            session = this.getDbAccessor().getSession();

            IssueRiskPK pk = new IssueRiskPK(form.getTypeName(), form.getRiskInfluence());
            IssueRisk risk = (IssueRisk) session.get(IssueRisk.class, pk);
            if (risk != null){

                if (risk.getRst().equals(Constant.RST_NORMAL)){
                    throw new BusinessException("issue.risk.add.exist.error","Issuetype risk's name has existed!!");
                }else{
                    risk.setRst(Constant.RST_NORMAL);
                    risk.setMinLevel(new Long(form.getRiskMinLevel()));
                    risk.setMaxLevel(new Long(form.getRiskMaxLevel()));
                    risk.setWeight(new Long(form.getRiskWeight()));
                    risk.setSequence(new Long(form.getRiskSequence()));
                    risk.setDescription(form.getRiskDescription());
                    session.update(risk);
                }
           } else{
               IssueRisk newRisk = new IssueRisk(pk);
               newRisk.setRst(Constant.RST_NORMAL);
               newRisk.setMinLevel(new Long(form.getRiskMinLevel()));
               newRisk.setMaxLevel(new Long(form.getRiskMaxLevel()));
               newRisk.setWeight(new Long(form.getRiskWeight()));
               newRisk.setSequence(new Long(form.getRiskSequence()));
               newRisk.setDescription(form.getRiskDescription());
               session.save(newRisk);
           }

        } catch (Exception ex) {
            try {
                tx.rollback();
            } catch (HibernateException ex1) {
            }
            log.error("Error: Save Issue Risk failed!");
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.risk.add.error","Issuetype risk add exception!");

        }
    }

    /**
     * ���ݴ����AfRisk�޸�IssueRisk����<br>
     * 1  ��������typeName��riskInfluence load() IssueRisk����  <br>
     * 2  ����IssueRisk�������������  <br>
     * @param form AfRisk
     * @throws BusinessException
     */
    public void update(AfRisk form) throws BusinessException {

        try {
            session = this.getDbAccessor().getSession();
            IssueRisk risk = (IssueRisk) session.load(IssueRisk.class, new IssueRiskPK(form.getTypeName(),form.getRiskInfluence()));
            risk.setMinLevel(new Long(form.getRiskMinLevel()));
            risk.setMaxLevel(new Long(form.getRiskMaxLevel()));
            risk.setWeight(new Long(form.getRiskWeight()));
            risk.setSequence(new Long(form.getRiskSequence()));
            risk.setDescription(form.getRiskDescription());
            session.update(risk);
        } catch (Exception ex) {
            try {
                tx.rollback();
            } catch (HibernateException ex1) {
            }
            log.error("Error: Update Issue Risk failed!");
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.risk.update.error","Issuetype risk update exception!");
        }
    }

    /**
     * ����typeName��riskInfluence�߼�ɾ��IssueRisk   <br>
     * 1.  ��������typeName��riskInfluence load() IssueRisk����   <br>
     * 2.  ����IssueRisk.statusΪdisable  <br>
     * @param typeName String
     * @param riskInfluence String
     * @throws BusinessException
     */
    public void delete(String typeName , String riskInfluence) throws
            BusinessException {

        try {
            session = this.getDbAccessor().getSession();
            IssueRisk delRisk = (IssueRisk) session.load(IssueRisk.class, new IssueRiskPK(typeName, riskInfluence));
            delRisk.setRst(Constant.RST_DELETE);
            session.update(delRisk);
        } catch (Exception ex) {
            try {
                tx.rollback();
            } catch (HibernateException ex1) {
            }
            log.error("Error: Delete Issue Risk failed!");
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.risk.delete.error","Issuetype risk delete exception!");

        }
    }

    public long getMaxSequence(String typeName) {
        long sequence=1;
        try {
            Session session = this.getDbAccessor().getSession();
            Query q=session.createQuery("from IssueRisk s where s.comp_id.typeName='"+typeName+"' order by s.sequence desc");
            List results=q.list();
            if(results!=null && results.size()>0) {
                IssueRisk firstRecord=(IssueRisk)results.get(0);
                sequence=firstRecord.getSequence().longValue()+1;
            }
        } catch(Exception e) {

        }
        return sequence;
    }
}
