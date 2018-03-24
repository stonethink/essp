package server.essp.issue.issue.sendmail.logic;

import server.essp.issue.common.logic.AbstractISSUELogic;
import db.essp.issue.IssueMailDefaultTocc;
import server.framework.common.BusinessException;
import java.util.List;
import db.essp.issue.IssueMailDefaultTemp;
import net.sf.hibernate.Session;

public class lgDefaultToCc extends AbstractISSUELogic {
    public lgDefaultToCc() {
    }

    public IssueMailDefaultTocc getByCondition(Long acntRid,String issueType,Long tempId){
        String querySql="from IssueMailDefaultTocc as t where t.acntRid="
                        +acntRid+" and issuetype='"+issueType+"' and templaterid="
                        +tempId;
        IssueMailDefaultTocc defTocc=null;
        try {
            Session session = (Session)this.getDbAccessor().getSession();
            List list = session.createQuery(querySql).list();
            if (list.size() ==1) {
                defTocc = (IssueMailDefaultTocc) list.get(0);
            }else if(list.size()==0){
                System.out.println("没有默认的记录");
            }else{
                throw new BusinessException("", "记录不只一条也，出了问题");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("", "get default template error");
        }
        return defTocc;
    }

    public void save(IssueMailDefaultTocc defTocc){
        try {
            this.getDbAccessor().save(defTocc);
        } catch (Exception ex) {
            throw new BusinessException("","update default mailTo&CC error");
        }
    }

    public void update(IssueMailDefaultTocc defTocc){
        try {
            this.getDbAccessor().update(defTocc);
        } catch (Exception ex) {
            throw new BusinessException("","update default mailTo&CC error");
        }
    }

}
