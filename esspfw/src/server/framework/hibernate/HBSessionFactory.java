package server.framework.hibernate;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;

import javax.naming.NamingException;
import javax.naming.Reference;

import net.sf.hibernate.*;
import net.sf.hibernate.exception.SQLExceptionConverter;
import net.sf.hibernate.metadata.ClassMetadata;
import net.sf.hibernate.metadata.CollectionMetadata;
import server.framework.hibernate.HibernateUtil;

/**
 * <p>Title: HBSessionFactory</p>
 *
 * <p>Description: 为Spring代理SessionFactory 从而设置Hibernate拦截器</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class HBSessionFactory implements SessionFactory {
    private Interceptor defaultInterceptor;
    private SessionFactory factory;

    public HBSessionFactory() throws HibernateException {
        factory = HibernateUtil.getSessionFactory();
        defaultInterceptor = new HBInterceptor();
    }

    /**
     * close
     *
     * @throws HibernateException
     * @todo Implement this net.sf.hibernate.SessionFactory method
     */
    public void close() throws HibernateException {
        factory.close();
    }

    /**
     * evict
     *
     * @param _class Class
     * @param serializable Serializable
     * @throws HibernateException
     * @todo Implement this net.sf.hibernate.SessionFactory method
     */
    public void evict(Class _class, Serializable serializable) throws
            HibernateException {
        factory.evict(_class, serializable);
    }

    /**
     * evict
     *
     * @param _class Class
     * @throws HibernateException
     * @todo Implement this net.sf.hibernate.SessionFactory method
     */
    public void evict(Class _class) throws HibernateException {
        factory.evict(_class);
    }

    /**
     * evictCollection
     *
     * @param string String
     * @throws HibernateException
     * @todo Implement this net.sf.hibernate.SessionFactory method
     */
    public void evictCollection(String string) throws HibernateException {
        factory.evictCollection(string);
    }

    /**
     * evictCollection
     *
     * @param string String
     * @param serializable Serializable
     * @throws HibernateException
     * @todo Implement this net.sf.hibernate.SessionFactory method
     */
    public void evictCollection(String string, Serializable serializable) throws
            HibernateException {
        factory.evictCollection(string, serializable);
    }

    /**
     * evictQueries
     *
     * @param string String
     * @throws HibernateException
     * @todo Implement this net.sf.hibernate.SessionFactory method
     */
    public void evictQueries(String string) throws HibernateException {
        factory.evictQueries(string);
    }

    /**
     * evictQueries
     *
     * @throws HibernateException
     * @todo Implement this net.sf.hibernate.SessionFactory method
     */
    public void evictQueries() throws HibernateException {
        factory.evictQueries();
    }

    /**
     * getAllClassMetadata
     *
     * @return Map
     * @throws HibernateException
     * @todo Implement this net.sf.hibernate.SessionFactory method
     */
    public Map getAllClassMetadata() throws HibernateException {
        return factory.getAllClassMetadata();
    }

    /**
     * getAllCollectionMetadata
     *
     * @return Map
     * @throws HibernateException
     * @todo Implement this net.sf.hibernate.SessionFactory method
     */
    public Map getAllCollectionMetadata() throws HibernateException {
        return factory.getAllCollectionMetadata();
    }

    /**
     * getClassMetadata
     *
     * @param _class Class
     * @return ClassMetadata
     * @throws HibernateException
     * @todo Implement this net.sf.hibernate.SessionFactory method
     */
    public ClassMetadata getClassMetadata(Class _class) throws
            HibernateException {
        return factory.getClassMetadata(_class);
    }

    /**
     * getCollectionMetadata
     *
     * @param string String
     * @return CollectionMetadata
     * @throws HibernateException
     * @todo Implement this net.sf.hibernate.SessionFactory method
     */
    public CollectionMetadata getCollectionMetadata(String string) throws
            HibernateException {
        return factory.getCollectionMetadata(string);
    }

    /**
     * Retrieves the Reference of this object.
     *
     * @return The non-null Reference of this object.
     * @throws NamingException If a naming exception was encountered while
     *   retrieving the reference.
     * @todo Implement this javax.naming.Referenceable method
     */
    public Reference getReference() throws NamingException {
        return factory.getReference();
    }

    /**
     * getSQLExceptionConverter
     *
     * @return SQLExceptionConverter
     * @todo Implement this net.sf.hibernate.SessionFactory method
     */
    public SQLExceptionConverter getSQLExceptionConverter() {
        return factory.getSQLExceptionConverter();
    }

    /**
     * openDatabinder
     *
     * @return Databinder
     * @throws HibernateException
     * @todo Implement this net.sf.hibernate.SessionFactory method
     */
    public Databinder openDatabinder() throws HibernateException {
        return factory.openDatabinder();
    }

    /**
     * openSession
     *
     * @param interceptor Interceptor
     * @return Session
     * @throws HibernateException
     * @todo Implement this net.sf.hibernate.SessionFactory method
     */
    public Session openSession(Interceptor interceptor) throws
            HibernateException {
        return factory.openSession(interceptor);
    }

    /**
     * openSession
     *
     * @param connection Connection
     * @param interceptor Interceptor
     * @return Session
     * @todo Implement this net.sf.hibernate.SessionFactory method
     */
    public Session openSession(Connection connection, Interceptor interceptor) {
        return factory.openSession(connection, interceptor);
    }

    /**
     * openSession
     *
     * @return Session
     * @throws HibernateException
     * @todo Implement this net.sf.hibernate.SessionFactory method
     */
    public Session openSession() throws HibernateException {
        //使用默认拦截器
        return factory.openSession(defaultInterceptor);
    }

    /**
     * openSession
     *
     * @param connection Connection
     * @return Session
     * @todo Implement this net.sf.hibernate.SessionFactory method
     */
    public Session openSession(Connection connection) {
        return factory.openSession(connection);
    }
}
