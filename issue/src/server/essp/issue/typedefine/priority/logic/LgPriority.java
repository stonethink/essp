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
    * 依据AfScope主键typeName,scope载入IssueScope
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
    * 依据传入的AfPriority新增IssuePriority对象: <br>
    * 1. 判断该IssueType中是否存在同名的IssuePriority,判断方法为： <br>
    * 2. 根据主键typeName和priority使用Session的get()获得IssuePriority对象<br>
    * 3. if IssuePriority不为空　                            <br>&ensp;&ensp;&ensp;&ensp;&ensp;
    *          if    IssuePriority状态可用,  报错退出；                               <br>&ensp;&ensp;&ensp;&ensp;&ensp;
    *          else  设置IssuePriority状态为可用，并覆盖输入IssuePriorty属性；  <br>
    *    else  将AfPriority新增到IssuePriority;　
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
    * 依据传入的AfPriority修改IssuePriority对象<br>
    * 1  根据主键typeName和priority load() IssuePriority对象  <br>
    * 2  设置IssuePriority对象的其他属性  <br>
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
    * 依据typeName，scope逻辑删除IssueScope   <br>
    * 1.  根据主键typeName，scope load() IssueScope对象   <br>
    * 2.  设置IssueScope.status为disable  <br>
    * @param typeName String
    * @param scope String
    * @throws BusinessException
    */
   public void delete(String typeName, String priority) throws BusinessException {
     //逻辑删除IssuePriority
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
