package db.essp.pms;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.hibernate.CompositeUserType;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.engine.SessionImplementor;
import net.sf.hibernate.type.Type;
import net.sf.hibernate.Hibernate;
import java.sql.Types;

public class WbsUserType implements CompositeUserType {
    private String[] propertyNames = new String[] {"acntRid", "wbsRid"};
    private Type[] propertyTypes = new Type[] {Hibernate.LONG, Hibernate.LONG};

    public WbsUserType() {
    }

    /**
     * Reconstruct an object from the cacheable representation.
     *
     * @param cached the object to be cached
     * @param session SessionImplementor
     * @param owner the owner of the cached object
     * @return a reconstructed object from the cachable representation
     * @throws HibernateException
     * @todo Implement this net.sf.hibernate.CompositeUserType method
     */
    public Object assemble(Serializable cached, SessionImplementor session,
                           Object owner) throws HibernateException {
        return deepCopy(cached);
    }

    /**
     * Return a deep copy of the persistent state, stopping at entities and
     * at collections.
     *
     * @param value generally a collection element or entity field
     * @return Object a copy
     * @throws HibernateException
     * @todo Implement this net.sf.hibernate.CompositeUserType method
     */
    public Object deepCopy(Object value) throws HibernateException {
        if(value==null) {
            return null;
        }
        WbsPK pk=(WbsPK)value;
        return new WbsPK(pk.getAcntRid(),pk.getWbsRid());
    }

    /**
     * Transform the object into its cacheable representation.
     *
     * @param value the object to be cached
     * @param session SessionImplementor
     * @return a cachable representation of the object
     * @throws HibernateException
     * @todo Implement this net.sf.hibernate.CompositeUserType method
     */
    public Serializable disassemble(Object value, SessionImplementor session) throws
        HibernateException {
        return (Serializable)deepCopy(value);
    }

    /**
     * Compare two instances of the class mapped by this type for persistence
     * "equality".
     *
     * @param x Object
     * @param y Object
     * @return boolean
     * @throws HibernateException
     * @todo Implement this net.sf.hibernate.CompositeUserType method
     */
    public boolean equals(Object x, Object y) throws HibernateException {
        if(x==null || y==null) {
            return false;
        }
        if(x instanceof WbsPK && y instanceof WbsPK) {
            return x.equals(y);
        }
        return false;
    }

    /**
     * Get the "property names" that may be used in a query.
     *
     * @return an array of "property names"
     * @todo Implement this net.sf.hibernate.CompositeUserType method
     */
    public String[] getPropertyNames() {
        return propertyNames;
    }

    /**
     * Get the corresponding "property types".
     *
     * @return an array of Hibernate types
     * @todo Implement this net.sf.hibernate.CompositeUserType method
     */
    public Type[] getPropertyTypes() {
        return propertyTypes;
    }

    /**
     * Get the value of a property.
     *
     * @param component an instance of class mapped by this "type"
     * @param property int
     * @return the property value
     * @throws HibernateException
     * @todo Implement this net.sf.hibernate.CompositeUserType method
     */
    public Object getPropertyValue(Object component, int property) throws
        HibernateException {
        WbsPK pk = (WbsPK) component;
        Long result;
        switch (property) {
        case 0:
            result = pk.getAcntRid();
            break;
        case 1:
            result = pk.getWbsRid();
            break;
        default:
            throw new HibernateException("unknown property: " + property);
        }
        return result;
    }

    /**
     * Check if objects of this type mutable.
     *
     * @return boolean
     * @todo Implement this net.sf.hibernate.CompositeUserType method
     */
    public boolean isMutable() {
        return true;
    }

    /**
     * Retrieve an instance of the mapped class from a JDBC resultset.
     *
     * @param rs a JDBC result set
     * @param names the column names
     * @param session SessionImplementor
     * @param owner the containing entity
     * @return Object
     * @throws HibernateException
     * @throws SQLException
     * @todo Implement this net.sf.hibernate.CompositeUserType method
     */
    public Object nullSafeGet(ResultSet rs, String[] names,
                              SessionImplementor session, Object owner) throws
        HibernateException, SQLException {
        try {
            String acntRidStr = rs.getString(names[0]);
            String wbsRidStr = rs.getString(names[1]);
            if(acntRidStr!=null && wbsRidStr!=null) {
                Long acntRid=new Long(acntRidStr);
                Long wbsRid=new Long(wbsRidStr);
                return new WbsPK(acntRid, wbsRid);
            }
            return null;
        } catch(Exception e) {
            //e.printStackTrace();
            return null;
        }

    }

    /**
     * Write an instance of the mapped class to a prepared statement.
     *
     * @param st a JDBC prepared statement
     * @param value the object to write
     * @param index statement parameter index
     * @param session SessionImplementor
     * @throws HibernateException
     * @throws SQLException
     * @todo Implement this net.sf.hibernate.CompositeUserType method
     */
    public void nullSafeSet(PreparedStatement st, Object value, int index,
                            SessionImplementor session) throws
        HibernateException, SQLException {
        if(value==null) {
            st.setNull(index,Types.INTEGER);
            st.setNull(index+1,Types.INTEGER);
        } else {
            WbsPK pk=(WbsPK)value;
            if(pk.getAcntRid()!=null && pk.getWbsRid()!=null) {
                st.setLong(index, pk.getAcntRid().longValue());
                st.setLong(index + 1, pk.getWbsRid().longValue());
            } else {
                st.setNull(index,Types.INTEGER);
                st.setNull(index+1,Types.INTEGER);
            }
        }
    }

    /**
     * The class returned by <tt>nullSafeGet()</tt>.
     *
     * @return Class
     * @todo Implement this net.sf.hibernate.CompositeUserType method
     */
    public Class returnedClass() {
        return WbsPK.class;
    }

    /**
     * Set the value of a property.
     *
     * @param component an instance of class mapped by this "type"
     * @param property int
     * @param value the value to set
     * @throws HibernateException
     * @todo Implement this net.sf.hibernate.CompositeUserType method
     */
    public void setPropertyValue(Object component, int property, Object value) throws
        HibernateException {
        WbsPK pk = (WbsPK) component;
        Long acntRid = null;
        Long wbsRid=null;
        switch (property) {
        case 0:
            if (value != null) {
                acntRid = (Long) value;
                pk.setAcntRid(acntRid);
            }
            break;
        case 1:
            if (value != null) {
                wbsRid = (Long) value;
                pk.setWbsRid(wbsRid);
            }
            break;
        default:
            throw new HibernateException("unknown property: " + property);
        }
    }

    public static void main(String[] args) {
    }
}
