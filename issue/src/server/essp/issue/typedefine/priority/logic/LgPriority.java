package server.essp.issue.typedefine.priority.logic;

import server.essp.issue.typedefine.priority.form.AfPriority;
import db.essp.issue.IssuePriority;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;
import db.essp.issue.IssuePriorityPK;
import net.sf.hibernate.Session;
import server.essp.issue.typedefine.priority.viewbean.VbPriority;
import java.lang.Long;
import com.wits.util.StringUtil;
import server.framework.common.Constant;
import net.sf.hibernate.Query;
import java.util.List;

public class LgPriority extends AbstractBusinessLogic {

    private VbPriority oneViewBean=new VbPriority();

    public LgPriority() {
    }

  public VbPriority getOneViewBean(String typeName,String priority) throws BusinessException {
    IssuePriority ip=this.load(typeName.trim(),priority.trim());
    if (ip!=null){
        //this.oneViewBean.setTypeName(ip.getComp_id().getTypeName());
        this.oneViewBean.setPriority(ip.getComp_id().getPriority());
        this.oneViewBean.setDuration(String.valueOf(ip.getDuration()));
        this.oneViewBean.setSequence(String.valueOf(ip.getSequence()));
        this.oneViewBean.setDescription(ip.getDescription());
    }
    return this.oneViewBean;
  }

  /**
    * ����AfScope����typeName,scope����IssueScope
    * @param typeName String
    * @param scope String
    * @throws BusinessException
    * @return IssueScope
    */
   public IssuePriority load(String typeName,String priority) throws BusinessException {
     IssuePriorityPK pk = new IssuePriorityPK(typeName,priority);
     try {
       Session session =  this.getDbAccessor().getSession();
       IssuePriority issuePriority = (IssuePriority)session.load(IssuePriority.class, pk);
       return issuePriority;
     } catch (Exception ex) {
       log.error(ex);
       throw new BusinessException("issue.priority.load.exception",
                                   "load issuetype priority error!");
     }
   }

   /**
    * ���ݴ����AfPriority����IssuePriority����: <br>
    * 1. �жϸ�IssueType���Ƿ����ͬ����IssuePriority,�жϷ���Ϊ�� <br>
    * 2. ��������typeName��priorityʹ��Session��get()���IssuePriority����<br>
    * 3. if IssuePriority��Ϊ�ա�                            <br>&ensp;&ensp;&ensp;&ensp;&ensp;
    *          if    IssuePriority״̬����,  �����˳���                               <br>&ensp;&ensp;&ensp;&ensp;&ensp;
    *          else  ����IssuePriority״̬Ϊ���ã�����������IssuePriorty���ԣ�  <br>
    *    else  ��AfPriority������IssuePriority;��
    * @param form AfPriority
    * @throws BusinessException
    */
   public void add(AfPriority form) throws BusinessException {
     String typeName = form.getTypeName().toUpperCase();
     String priority = form.getPriority().toUpperCase();
     IssuePriorityPK pk = new IssuePriorityPK(typeName,priority);
     try {
       Session session = this.getDbAccessor().getSession();
       IssuePriority issuePriority = (IssuePriority)session.get(IssuePriority.class,pk);
       if (issuePriority != null) {
         if(issuePriority.getRst().equalsIgnoreCase(Constant.RST_NORMAL)){
           throw new BusinessException("issue.priority.exist",
                                       "Same issuetype priority has existed!!");
         }
         else{
           issuePriority.setDuration(new Long(form.getDuration()));
           issuePriority.setRst(Constant.RST_NORMAL);
           issuePriority.setSequence(new Long(form.getSequence()));
           issuePriority.setDescription(form.getDescription());
           this.getDbAccessor().update(issuePriority);
         }
       }else{
         issuePriority = new IssuePriority(pk);
         issuePriority.setRst(Constant.RST_NORMAL);
         issuePriority.setSequence(new Long(form.getSequence()));
         issuePriority.setDuration(new Long(form.getDuration()));
         issuePriority.setDescription(form.getDescription());
         session.save(issuePriority);
       }
     } catch (Exception ex) {
       log.error(ex);
       throw new BusinessException("issue.priority.add.exception",
                                   "add issuetype priority error!");
     }
   }

   /**
    * ���ݴ����AfPriority�޸�IssuePriority����<br>
    * 1  ��������typeName��priority load() IssuePriority����  <br>
    * 2  ����IssuePriority�������������  <br>
    * @param oldPriority String
    * @param form AfPriority
    * @throws BusinessException
    */
   public void update(AfPriority form) throws BusinessException {
     String typeName = form.getTypeName();
     String priority = form.getPriority();
     if (StringUtil.nvl(typeName).equals("")==true){
              typeName="";
     }
     if (StringUtil.nvl(priority).equals("")==true){
             priority="";
     }
     try {
       IssuePriority issuePriority = this.load(typeName.trim(),priority.trim());
       issuePriority.setDuration(new Long(form.getDuration()));
       issuePriority.setSequence(new Long(form.getSequence()));
       issuePriority.setDescription(form.getDescription());
       this.getDbAccessor().update(issuePriority);
     } catch (Exception ex) {
       log.error(ex);
       throw new BusinessException("issue.priority.update.exception",
                                   "update issuetype priority error!");
     }
   }

   /**
    * ����typeName��scope�߼�ɾ��IssueScope   <br>
    * 1.  ��������typeName��scope load() IssueScope����   <br>
    * 2.  ����IssueScope.statusΪdisable  <br>
    * @param typeName String
    * @param scope String
    * @throws BusinessException
    */
   public void delete(String typeName, String priority) throws BusinessException {
     //�߼�ɾ��IssuePriority
     try {
       IssuePriority issuePriority = this.load(typeName.trim(),priority.trim());
       issuePriority.setRst(Constant.RST_DELETE);
       this.getDbAccessor().update(issuePriority);
     } catch (Exception ex) {
       log.error(ex);
       throw new BusinessException("issue.priority.delete.exception",
                                   "delete issuetype priority error!");
     }
   }

   public long getMaxSequence(String typeName) {
       long sequence=1;
       try {
           Session session = this.getDbAccessor().getSession();
           Query q=session.createQuery("from IssuePriority s where s.comp_id.typeName='"+typeName+"' order by s.sequence desc");
           List results=q.list();
           if(results!=null && results.size()>0) {
               IssuePriority firstRecord=(IssuePriority)results.get(0);
               sequence=firstRecord.getSequence().longValue()+1;
           }
       } catch(Exception e) {

       }
       return sequence;
    }
}
