package server.essp.issue.typedefine.priority.logic;

import com.wits.util.*;
import server.framework.common.*;
import server.framework.logic.*;
import net.sf.hibernate.*;
import java.util.*;
import server.essp.issue.typedefine.priority.viewbean.VbPriorityList;
import server.essp.issue.typedefine.priority.viewbean.VbPriority;
import db.essp.issue.IssuePriority;
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
public class LgPriorityList extends AbstractBusinessLogic {
    private VbPriorityList listViewBean = new VbPriorityList();
    public LgPriorityList() {
    }

    public VbPriorityList getListViewBean(String tName) throws
        BusinessException {
        int detailSize = 0;
        List oldList = this.list(tName);
        if (oldList != null) {
            detailSize = oldList.size();
            this.listViewBean.setDetailSize(new Long(detailSize));
            for (int i = 0; i < detailSize; i++) {
                IssuePriority ip = (IssuePriority) oldList.get(i);
                VbPriority oneBean = new VbPriority();
                oneBean.setPriority(ip.getComp_id().getPriority());
                oneBean.setRst(ip.getRst());
                oneBean.setDuration(String.valueOf(ip.getDuration()));
                oneBean.setSequence(String.valueOf(ip.getSequence()));
                oneBean.setDescription(ip.getDescription());
                this.listViewBean.getDetail().add(oneBean);
            }
        }
        return this.listViewBean;
    }

    /**
     * 按IssueType查找Priority的列表，并按sequence升序排列
     * @return List
     */
    public List list(String tName) throws BusinessException {
        List result = new ArrayList();
        try {
            //根据IssueTypeName查询所有的priority
            Session session = this.getDbAccessor().getSession();
            result = session.createQuery(
                "from IssuePriority as ip where ip.comp_id.typeName='" + tName +
                "' and ip.rst='N' order by ip.sequence")
                     .list();
            return result;
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.priority.list.exception",
                                        "List issuetype priority error!");
        }
    }
    /**
     * 获取所有Priority的列表，并按sequence升序排列
     * @return List
     * @throws BusinessException
     */
    public List listAll()throws BusinessException {
        try {
            Session session = this.getDbAccessor().getSession();
            List result = session.createQuery("from IssuePriority as priority " +
                                              "where priority.rst=:rst " +
                                              "order by priority.sequence")
                          .setString("rst",Constant.RST_NORMAL)
                          .list();
            return result;
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.priority.list.all.exception",
                                        "List all issuetype priority error!");
        }
    }
    /**
     * 获取所有Priority的列表，并按sequence升序排列，并转换为SelectOptionImpl(name,value)
     * name = issuePriority.typeName
     * value = issuePriority.priority
     * @return List
     * @throws BusinessException
     */
    public List listAllOpt() throws BusinessException {
        List models = this.listAll();
        List result = new ArrayList(models.size());
        Iterator i = models.iterator();
        while(i.hasNext()){
           IssuePriority ip = (IssuePriority) i.next();
           SelectOptionImpl opt = new SelectOptionImpl(ip.getComp_id().getTypeName(),
                                                        ip.getComp_id().getPriority());
           result.add(opt);
        }
        return result;
    }
}
