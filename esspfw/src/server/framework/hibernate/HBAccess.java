package server.framework.hibernate;

import org.apache.log4j.Category;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Collection;
import java.util.List;
import net.sf.hibernate.Query;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class HBAccess {
    static Category log = Category.getInstance("server");
    protected boolean isNewSession=false;

    String DB_NAME = null;

    Session session = null;

    Transaction tx = null;
    boolean bTmpSession = true;

    public Session getSession() throws Exception {
        if (session == null) {
            if(isNewSession) {
                session = HibernateUtil.getSessionFactory().openSession();
            } else {
                session = HibernateUtil.getSession();
            }
        }
        return this.session;
    }

    public void newTx() throws Exception {
        session = getSession();
//    if (tx != null) {
//      tx.commit();
//    }
        tx = session.beginTransaction();
        bTmpSession = false;
    }

    public void followTx() throws Exception {
        session = getSession();
        if (tx == null) {
            tx = session.beginTransaction();
        }
        bTmpSession = false;
    }

    public void endTxCommit() throws Exception {
        commit();
        close();
    }

    public void endTxRollback() throws Exception {
        rollback();
        close();
    }

    public void commit() throws Exception {
        if (tx == null) {
            log.error("endTx, conn is commit! But tx is null!!");
            return;
        } else {
            tx.commit();
            log.debug("endTx, conn is commit!");
        }
    }

    public void rollback() throws Exception {
        if (tx == null) {
            log.error("endTx, conn is commit! But tx is null!!");
            return;
        } else {
            tx.rollback();
            log.debug("endTx, conn is rollback!");
        }
    }

    public void close() throws Exception {
        //HibernateUtil.closeSession();
        if (session != null) {
            session.close();
        }
        tx = null;
        session = null;
    }

    public HBAccess() {
    }

    protected void beginTx() throws Exception {
        session = getSession();
        if (bTmpSession) {
            if (tx != null) {
                tx.commit();
            }
            tx = session.beginTransaction();
        }
    }

    protected void endTx() throws Exception {
        if (bTmpSession) {
            if (tx != null) {
                tx.commit();
            }
            close();
        }
    }

    public void flush() throws Exception {
        session.flush();
    }

    public Object get(Class cls, Serializable key) throws Exception {
        followTx();
        Object rtnObj = session.get(cls, key);
        return rtnObj;
    }

    public Object load(Class cls, Serializable key) throws Exception {
        followTx();
        Object rtnObj = session.load(cls, key);
        return rtnObj;
    }

    public void save(Object object) throws Exception {
        followTx();
        session.save(object);
    }

    public void update(Object object) throws Exception {
        followTx();
        session.update(object);
    }

    public void saveOrUpdate(Object object) throws Exception {
        followTx();
        session.saveOrUpdate(object);
    }

    public void delete(Object object) throws Exception {
        followTx();
        session.delete(object);
    }

    public void delete(Collection collection) throws Exception {
        for (Iterator it = collection.iterator(); it.hasNext(); ) {
            delete(it.next());
        }
    }

    public List listAll(Class cls) throws Exception {
        //beginTx();
        String className = cls.getName();
        Query q = session.createQuery("from " + className);
        List list = q.list();
        //endTx();
        return list;
    }

}
