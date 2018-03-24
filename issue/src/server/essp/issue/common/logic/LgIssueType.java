package server.essp.issue.common.logic;

import server.framework.logic.*;
import java.util.List;
import java.util.ArrayList;
import server.framework.taglib.util.SelectOptionImpl;
import net.sf.hibernate.Session;
import server.framework.common.BusinessException;
import net.sf.hibernate.Query;
import db.essp.issue.IssuePriority;
import db.essp.issue.IssueStatus;
import db.essp.issue.IssueScope;
import c2s.essp.common.user.DtoUser;
import db.essp.issue.IssueType;
import db.essp.issue.IssuePriorityPK;
import db.essp.issue.IssueScopePK;
import db.essp.issue.IssueStatusPK;
import server.essp.issue.common.constant.Vision;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import server.essp.issue.common.constant.Status;
import itf.issue.IIssueTypeUtil;
import c2s.dto.DtoComboItem;
import java.util.Vector;

public class LgIssueType extends AbstractBusinessLogic implements IIssueTypeUtil{
    public LgIssueType() {
    }

    public List getIssueTypes() {
        List options = new ArrayList();
        try {
            Session session = this.getDbAccessor().getSession();
            Query q = session.createQuery(
                "from IssueType p where p.rst='N' order by p.sequence");
            List types = q.list();
            if (types != null) {
                options.addAll(types);
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        return options;
    }

    public long getIssueTypeSequence(String typeName) {
        long sequence=-1;
        try {
            IssueType issueType = (IssueType)this.getDbAccessor().get(IssueType.class,
                typeName);
            if(issueType!=null) {
                sequence=issueType.getSequence().longValue();
            }
        } catch (Exception ex) {
        }
        return sequence;
    }

    public boolean isSaveStatusHistory(String typeName) {
        boolean flag=true;
        try {
            IssueType issueType = (IssueType)this.getDbAccessor().get(IssueType.class,
                typeName);
            if(issueType!=null) {
                if(issueType.getSaveStatusHistory()!=null &&
                   issueType.getSaveStatusHistory().equals("N")) {
                    flag=false;
                }
            }
        } catch (Exception ex) {
        }
        return flag;
    }

    public boolean isSaveInfluenceHistory(String typeName) {
        boolean flag=true;
        try {
            IssueType issueType = (IssueType)this.getDbAccessor().get(IssueType.class,
                typeName);
            if(issueType!=null) {
                if(issueType.getSaveInfluenceHistory()!=null &&
                   issueType.getSaveInfluenceHistory().equals("N")) {
                    flag=false;
                }
            }
        } catch (Exception ex) {
        }
        return flag;
    }

    //判断某一个字符串是否表示一个已经存在的IssueType
    //note by:Robin
    public boolean isExistIssueType(String typeName) {
        boolean flag=false;
        try {
            IssueType issueType = (IssueType)this.getDbAccessor().get(IssueType.class,
                typeName);
            if(issueType!=null) {
                flag=true;
            }
        } catch (Exception ex) {
        }
        return flag;
    }

    public boolean isExistPriority(String typeName,String priority) {
        boolean flag=false;
        try {
            IssuePriorityPK pk=new IssuePriorityPK(typeName,priority);
            IssuePriority priorityEntity=(IssuePriority)this.getDbAccessor().get(IssuePriority.class,pk);
            if(priorityEntity!=null) {
                flag=true;
            }
        } catch(Exception e) {

        }
        return flag;
    }

    public boolean isExistScope(String typeName,String scope,String userType) {
        boolean flag=false;
        try {
            IssueScope scopeEntity=getIssueScope(typeName,scope);
            if(scopeEntity!=null) {
                if(userType!=null && userType.equals(DtoUser.USER_TYPE_EMPLOYEE)) {
                    flag=true;
                } else if(scopeEntity.getVision().equals(Vision.VISION_Y)) {
                    flag = true;
                }
            }
        } catch(Exception e) {
            throw new BusinessException(e);
        }
        return flag;
    }

    public boolean isExistScope(String typeName,String scope) {
        boolean flag=false;
        try {
            IssueScope scopeEntity=getIssueScope(typeName,scope);
            if(scopeEntity!=null) {
                flag=true;
            }
        } catch(Exception e) {
            throw new BusinessException(e);
        }
        return flag;
    }

    public boolean isExistStatus(String typeName,String status) {
        boolean flag=false;
        try {
            IssueStatus statusEntity=getIssueStatus(typeName,status);
            if(statusEntity!=null) {
                flag=true;
            }
        } catch(Exception e) {
            throw new BusinessException(e);
        }
        return flag;
    }

    public List getIssueTypeOptions() {
        List options = new ArrayList();

        try {
            Session session = this.getDbAccessor().getSession();
            Query q = session.createQuery(
                "from IssueType p where p.rst='N' order by p.sequence");
            List types = q.list();
            if (types != null) {
                for (int i = 0; i < types.size(); i++) {
                    IssueType issueType = (IssueType) types.get(i);
                    SelectOptionImpl option = new SelectOptionImpl(issueType.
                        getTypeName(),
                        issueType.getTypeName());
                    options.add(option);
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        }

        if(options.size()==0) {
            SelectOptionImpl defaultOption = new SelectOptionImpl(
                "  ----  Please Select  ----  ", "");
            options.add(defaultOption);
        }
        return options;
    }

    public List getPriority(String issueType) {
        List options = new ArrayList();
        try {
            Session session = this.getDbAccessor().getSession();
            Query q = session.createQuery(
                "from IssuePriority p where p.rst='N' and p.comp_id.typeName='" +
                issueType +
                "' order by p.sequence");
            List priorities = q.list();
            if (priorities != null) {
                options.addAll(priorities);
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        return options;
    }

    public List getPriorityOptions(String issueType,String defaultValue) {
        List options = getPriorityOptions(issueType);
        if(defaultValue!=null && !defaultValue.trim().equals("")) {
            boolean isExist=false;
            for(int i=0;i<options.size();i++) {
                SelectOptionImpl option=(SelectOptionImpl)options.get(i);
                if(option.getValue().equals(defaultValue)) {
                    isExist=true;
                    break;
                }
            }
            if(!isExist) {
                options.add(new SelectOptionImpl(defaultValue, defaultValue));
            }
        }
        return options;
    }

    public List getPriorityOptions(String issueType) {
        List options = new ArrayList();
        if (issueType != null && !issueType.trim().equals("")) {
            try {
                Session session = this.getDbAccessor().getSession();
                Query q = session.createQuery(
                    "from IssuePriority p where p.rst='N' and p.comp_id.typeName='" +
                    issueType +
                    "' order by p.sequence");
                List priorities = q.list();
                if (priorities != null) {
                    for (int i = 0; i < priorities.size(); i++) {
                        IssuePriority priority = (IssuePriority) priorities.get(
                            i);
                        SelectOptionImpl option = new SelectOptionImpl(priority.
                            getComp_id().getPriority(),
                            priority.getComp_id().getPriority());
                        options.add(option);
                    }
                }
            } catch (Exception e) {
                throw new BusinessException(e);
            }
        }

        if(options.size()==0) {
            SelectOptionImpl defaultOption = new SelectOptionImpl(
                "  ----  Please Select  ----  ", "");
            options.add(defaultOption);
        }
        return options;
    }

    public List getScope(String issueType, String userType) {
        List options = new ArrayList();
        try {
            Session session = this.getDbAccessor().getSession();
            Query q = null;
            if (userType != null && userType.equals(DtoUser.USER_TYPE_CUST)) {
                q = session.createQuery(
                    "from IssueScope p where p.rst='N' and p.comp_id.typeName='" +
                    issueType +
                    "' and p.vision='Y' " +
                    " order by p.sequence");
            } else {
                q = session.createQuery(
                    "from IssueScope p where p.comp_id.typeName='" +
                    issueType +
                    "' order by p.sequence");
            }
            List priorities = q.list();

            if (priorities != null) {
                options.addAll(priorities);
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        return options;
    }

    public List getScopeOptions(String issueType, String userType, String defaultValue) {
        List options = getScopeOptions(issueType,userType);
        if(defaultValue!=null && !defaultValue.trim().equals("")) {
            boolean isExist=false;
            for(int i=0;i<options.size();i++) {
                SelectOptionImpl option=(SelectOptionImpl)options.get(i);
                if(option.getValue().equals(defaultValue)) {
                    isExist=true;
                    break;
                }
            }
            if(!isExist) {
                options.add(new SelectOptionImpl(defaultValue, defaultValue));
            }
        }

        return options;
    }

    public List getScopeOptions(String issueType, String userType) {
        List options = new ArrayList();

        if (issueType != null && !issueType.trim().equals("")) {
            try {
                Session session = this.getDbAccessor().getSession();
                Query q = null;
                if (userType != null && userType.equals(DtoUser.USER_TYPE_CUST)) {
                    q = session.createQuery(
                        "from IssueScope p where p.rst='N' and p.comp_id.typeName='" +
                        issueType +
                        "' and p.vision='Y' " +
                        " order by p.sequence");
                } else {
                    q = session.createQuery(
                        "from IssueScope p where p.rst='N' and p.comp_id.typeName='" +
                        issueType +
                        "' order by p.sequence");
                }
                List priorities = q.list();
                if (priorities != null) {
                    for (int i = 0; i < priorities.size(); i++) {
                        IssueScope priority = (IssueScope) priorities.get(i);
                        SelectOptionImpl option = new SelectOptionImpl(priority.
                            getComp_id().getScope(),
                            priority.getComp_id().getScope());
                        options.add(option);
                    }
                }
            } catch (Exception e) {
                throw new BusinessException(e);
            }
        }

        if (options.size() == 0) {
            SelectOptionImpl defaultOption = new SelectOptionImpl(
                "  ----  Please Select  ----  ", "");
            options.add(defaultOption);
        }

        return options;
    }

    public List getStatus(String issueType) {
        List options = new ArrayList();
        if (issueType != null && !issueType.trim().equals("")) {
            try {
                Session session = this.getDbAccessor().getSession();
                Query q = session.createQuery(
                    "from IssueStatus p where p.rst='N' and p.comp_id.typeName='" + issueType +
                    "' order by p.sequence");
                List priorities = q.list();
                if (priorities != null) {
                    options.addAll(priorities);
                }
            } catch (Exception e) {
                throw new BusinessException(e);
            }
        }
        return options;
    }

    public List getStatusOptions(String issueType,String defaultValue) {
        List options = getStatusOptions(issueType);
        if(defaultValue!=null && !defaultValue.trim().equals("")) {
            boolean isExist=false;
            for(int i=0;i<options.size();i++) {
                SelectOptionImpl option=(SelectOptionImpl)options.get(i);
                if(option.getValue().equals(defaultValue)) {
                    isExist=true;
                    break;
                }
            }
            if(!isExist) {
                options.add(new SelectOptionImpl(defaultValue, defaultValue));
            }
        }

        return options;
    }

    public List getRelationDataStatus(String issueType,String relationDate){
        try {
            Session session = this.getDbAccessor().getSession();
            return session.createQuery("from IssueStatus p where p.rst='N' and p.comp_id.typeName='" + issueType +"' and p.relationDate='"+relationDate+"' order by p.sequence")
                .list();
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
    }

    public List getStatusOptions(String issueType) {
        List options = new ArrayList();
        if (issueType != null && !issueType.trim().equals("")) {
            try {
                Session session = this.getDbAccessor().getSession();
                Query q = session.createQuery(
                    "from IssueStatus p where p.rst='N' and p.comp_id.typeName='" + issueType +
                    "' order by p.sequence");
                List priorities = q.list();
                if (priorities != null) {
                    for (int i = 0; i < priorities.size(); i++) {
                        IssueStatus priority = (IssueStatus) priorities.get(i);
                        SelectOptionImpl option = new SelectOptionImpl(priority.
                            getComp_id().getStatusName(),
                            priority.getComp_id().getStatusName());
                        options.add(option);
                    }
                }
            } catch (Exception e) {
                throw new BusinessException(e);
            }
        }
        if (options.size() == 0) {
            SelectOptionImpl defaultOption = new SelectOptionImpl(
                "  ----  Please Select  ----  ", "");
            options.add(defaultOption);
        }
        return options;
    }

    public String getStatusRelationDate(String typeName, String status) {
        String relationDate = "";
        if (typeName != null && status != null && !typeName.trim().equals("") &&
            !status.trim().equals("")) {
            IssueStatus issueStatus = getIssueStatus(typeName,status);
            if(issueStatus != null)
                relationDate = issueStatus.getRelationDate();
        }
        if(relationDate==null) {
            relationDate="";
        }
        return relationDate;
    }
    /**
     * 根据内部的状态(IssueStatus.belongTo)查找所有的IssueStatus对象
     * @param innerStatus String
     * @return List
     */
    public List listIssueTypeStatus(String innerStatus){
        String hql = null;
        if(Status.LEFT.equals(innerStatus)){
            hql = "from IssueStatus st " +
                  "where st.rst='N' and " +
                  "st.belongTo in ('"+Status.PROCESSING+"','"+Status.DELIVERED+"')";
        }else{
            hql = "from IssueStatus st " +
                  "where st.rst='N' and " +
                  "st.belongTo='"+innerStatus+"'";
        }
        try {
            return this.getDbAccessor().getSession().createQuery(hql)
                .list();
        } catch ( Exception ex) {
            throw new BusinessException(ex);
        }
    }
    public String getStatusBelongTo(String typeName, String status) {
        String belongTo = "";
        if (typeName != null && status != null && !typeName.trim().equals("") &&
            !status.trim().equals("")) {
            IssueStatus issueStatus = getIssueStatus(typeName,status);
            if(issueStatus != null)
                belongTo = issueStatus.getBelongTo();
        }
        if(belongTo==null) {
            belongTo="";
        }
        return belongTo;
    }
    public IssueStatus getIssueStatus(String typeName, String status){
        IssueStatusPK pk = new IssueStatusPK(typeName,status);
        IssueStatus issueStatus = (IssueStatus) statusCache.get(pk);
        if(issueStatus != null)
            return  issueStatus;
        try {
            issueStatus = (IssueStatus)this.getDbAccessor().get(IssueStatus.class,
                pk);
            if("N".equals(issueStatus.getRst())){
                statusCache.put(pk, issueStatus);
                return issueStatus;
            }
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return null;
    }

    private IssueScope getIssueScope(String typeName,String scope){
        IssueScopePK pk = new IssueScopePK(typeName,scope);
        IssueScope issueScope = (IssueScope) scopeCache.get(pk);
        if(issueScope != null)
            return issueScope;
        try {
            issueScope = (IssueScope) this.getDbAccessor().get(IssueScope.class, pk);
            if("N".equals(issueScope.getRst())){
                statusCache.put(pk, issueScope);
                return issueScope;
            }
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return null;
    }
    //暂时缓存IssueStatus对象
    private Map statusCache = Collections.synchronizedMap(new HashMap());
    //暂时缓存IssueScope对象
    private Map scopeCache = Collections.synchronizedMap(new HashMap());
    public Vector getIssueTypeComboItems() {
        Vector items = new Vector();

         try {
             Session session = this.getDbAccessor().getSession();
             Query q = session.createQuery(
                 "from IssueType p where p.rst='N' order by p.sequence");
             List types = q.list();
             if (types != null) {
                 for (int i = 0; i < types.size(); i++) {
                     IssueType issueType = (IssueType) types.get(i);
                     DtoComboItem item = new DtoComboItem(issueType.
                         getTypeName(),
                         issueType.getTypeName());
                     items.add(item);
                 }
             }
         } catch (Exception e) {
             throw new BusinessException(e);
         }
         return items;
    }

    public Vector getPriorityComboItems(String type) {
        Vector items = new Vector();
        if (type != null && !type.trim().equals("")) {
            try {
                Session session = this.getDbAccessor().getSession();
                Query q = session.createQuery(
                    "from IssuePriority p where p.rst='N' and p.comp_id.typeName='" +
                    type +
                    "' order by p.sequence");
                List priorities = q.list();
                if (priorities != null) {
                    for (int i = 0; i < priorities.size(); i++) {
                        IssuePriority priority = (IssuePriority) priorities.get(
                            i);
                        DtoComboItem item = new DtoComboItem(priority.
                            getComp_id().getPriority(),
                            priority.getComp_id().getPriority());
                        items.add(item);
                    }
                }
            } catch (Exception e) {
                throw new BusinessException(e);
            }
        }
        return items;
    }

    public Vector getStatusComboItem(String type) {
        Vector items = new Vector();
       if (type != null && !type.trim().equals("")) {
           try {
               Session session = this.getDbAccessor().getSession();
               Query q = session.createQuery(
                   "from IssueStatus p where p.rst='N' and p.comp_id.typeName='" + type +
                   "' order by p.sequence");
               List priorities = q.list();
               if (priorities != null) {
                   for (int i = 0; i < priorities.size(); i++) {
                       IssueStatus priority = (IssueStatus) priorities.get(i);
                       DtoComboItem item = new DtoComboItem(priority.
                           getComp_id().getStatusName(),
                           priority.getComp_id().getStatusName());
                       items.add(item);
                   }
               }
           } catch (Exception e) {
               throw new BusinessException(e);
           }
       }
       return items;

    }

    public Vector getScopeComboItems(String type, String userType) {
        Vector items = new Vector();

      if (type != null && !type.trim().equals("")) {
          try {
              Session session = this.getDbAccessor().getSession();
              Query q = null;
              if (userType != null && userType.equals(DtoUser.USER_TYPE_CUST)) {
                  q = session.createQuery(
                      "from IssueScope p where p.rst='N' and p.comp_id.typeName='" +
                      type +
                      "' and p.vision='Y' " +
                      " order by p.sequence");
              } else {
                  q = session.createQuery(
                      "from IssueScope p where p.rst='N' and p.comp_id.typeName='" +
                      type +
                      "' order by p.sequence");
              }
              List priorities = q.list();
              if (priorities != null) {
                  for (int i = 0; i < priorities.size(); i++) {
                      IssueScope priority = (IssueScope) priorities.get(i);
                      DtoComboItem item = new DtoComboItem(priority.
                          getComp_id().getScope(),
                          priority.getComp_id().getScope());
                      items.add(item);
                  }
              }
          } catch (Exception e) {
              throw new BusinessException(e);
          }
      }
      return items;

    }

    //实现Applet版Add Issue的下拉框初始数据方法



}
