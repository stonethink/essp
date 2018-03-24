package server.essp.issue.typedefine.logic;

import com.wits.util.*;
import server.framework.common.*;
import server.framework.logic.*;
import net.sf.hibernate.*;
import java.util.*;
import server.essp.issue.typedefine.viewbean.VbTypeList;
import db.essp.issue.IssueType;
import server.essp.issue.typedefine.viewbean.VbType;
import server.framework.hibernate.HBComAccess;
import server.framework.taglib.util.SelectOptionImpl;

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
public class LgTypeList extends AbstractBusinessLogic {
    private VbTypeList listViewBean=new VbTypeList();
    public LgTypeList() {
    }

    /**
     * ��IssueType���б��װ��ҳ����ʾ��������
     * @return VbTypeList
     * @throws BusinessException
     */
    public VbTypeList getListViewBean() throws BusinessException {
        int detailSize=0;
         List oldList=this.listAll();
         if (oldList!=null){
           detailSize=oldList.size();
           this.listViewBean.setDetailSize(new Long(detailSize));
           for(int i=0;i<detailSize;i++){
             IssueType it=(IssueType)oldList.get(i);
             VbType oneBean=new VbType();
             oneBean.setRid(String.valueOf(it.getRid()));
             oneBean.setTypeName(it.getTypeName());
             if(Constant.RST_NORMAL.equals(it.getRst()))
                 oneBean.setStatus("Enable");
             else
                 oneBean.setStatus("Disable");
             oneBean.setSequence(String.valueOf(it.getSequence()));
             oneBean.setDescription(it.getDescription());
             this.listViewBean.getDetail().add(oneBean);
           }
         }
         return this.listViewBean;
    }

    /**
     * ��ȡ����IssueType���б�����sequence��������
     * @return List
     */
    public List listAll() throws BusinessException {
        List result = null;
          try {
                      //��ѯ���е�IssueType Define
                      Session session = this.getDbAccessor().getSession();
                      result = session.createQuery("from IssueType as it " +
//                                                   "where it.rst=:rst " +
                                                   "order by it.sequence")
//                               .setString("rst",Constant.RST_NORMAL)
                               .list();
                      return result;
                  } catch (Exception ex) {
                      log.error(ex);
                      ex.printStackTrace();
                      throw new BusinessException("issue.typedefine.list.exception",
                                                  "List issuetype define error!");
          }
    }

    /**
     * ��ȡ����IssueType���б���sequence��������,��ת��ΪSelectOptionImpl(name,value)
     * name = issueType.typeName
     * value = issueType.typeName
     * @param typeName String
     * @return List
     * @throws BusinessException
     */
    public List listAllOpt()throws BusinessException {
        List modelList = this.listAll();
        List result = new ArrayList(modelList.size());
        Iterator i = modelList.iterator();
        while(i.hasNext()){
            IssueType issueType=(IssueType)i.next();
            SelectOptionImpl opt = new SelectOptionImpl(issueType.getTypeName(),
                                                         issueType.getTypeName());
            result.add(opt);
        }
        return result;
    }

    /** @link dependency */
    /*# db.essp.issue.IssueType lnkIssueType; */
}
