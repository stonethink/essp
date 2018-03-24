/*
 * Created on 2008-5-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.outwork.privilege.dao;

import org.springframework.orm.hibernate.support.HibernateDaoSupport;
import db.essp.timesheet.TsOutworkerPrivilege;

public class OutWorkerPrivilegeDaoImp extends HibernateDaoSupport 
      implements IOutWorkerPrivilegeDao{
    
    /**
     * ÐÂÔö
     * @param tsOwPrivilge
     */
    public void insert(TsOutworkerPrivilege tsOwPrivilge) {
        this.getHibernateTemplate().save(tsOwPrivilge);
    }
    
}
