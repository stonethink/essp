package server.essp.common.primavera;

import java.util.Date;

import c2s.essp.common.user.DtoUser;
import com.primavera.integration.client.GlobalObjectManager;
import com.primavera.integration.client.RMIURL;
import com.primavera.integration.client.Session;
import com.primavera.integration.client.bo.BOIterator;
import com.primavera.integration.client.bo.BusinessObjectException;
import com.primavera.integration.client.bo.object.Calendar;
import com.primavera.integration.client.bo.object.Resource;
import com.primavera.integration.client.bo.object.User;
import com.primavera.integration.common.DatabaseInstance;
import com.wits.util.ThreadVariant;
import org.apache.log4j.Category;
import server.framework.common.BusinessException;
import server.framework.primavera.IPrimaveraApi;

/**
 *
 * <p>Title: Primavera Api Base</p>
 *
 * <p>Description: ���ж�Primavera API����Ҫ�̳д˻��࣬�������API�ĵ�¼��ǳ���
 *    Session���̼߳����� </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class PrimaveraApiBase implements IPrimaveraApi {

    private String loginId = null;
    private Session session = null;
    protected static Category log = Category.getInstance("server");

    /**
     * get current login user's loginId
     * @return String
     */
    public String getLoginId() {
        DtoUser dtoUser = (DtoUser) ThreadVariant.getInstance().get(DtoUser.SESSION_USER);
        if (dtoUser == null) {
            throw new BusinessException("error.logic.PrimaveraApiBase.noSessionUser",
                                        "no user login");
        }
        loginId = dtoUser.getUserLoginId();
        return loginId;
    }

    /**
     * ��ȡ��ǰUser
     * @return User
     */
    public User getCurrentUser() {
         return getUser(getLoginId());
    }
    
    /**
     * ��ȡָ��loginId���û�
     * @param loginId
     * @return
     */
    public User getUser(String loginId) {
    	String strWhere = " upper(Name) = upper('" + loginId  + "')";
        try {
            BOIterator boiUsers = getGOM().loadUsers(User.getAllFields(), strWhere, null);
            if(boiUsers.hasNext()) {
                return (User) boiUsers.next();
            }
        } catch (Exception ex) {
            throw new BusinessException("error.logic.PrimaveraApiBase.getUserError",
                                        "get user [" + loginId + "] error", ex);
        }
       return null;
    }
    
    /**
     * ��ȡ��ǰResource
     * @return Resource
     */
    public Resource getCurrentResource() {
        try {
            return getCurrentUser().loadResource(Resource.getAllFields());
        } catch (BusinessException e) {
        	throw e;
        } catch (Exception ex) {
            throw new BusinessException("error.logic.PrimaveraApiBase.getResourceError",
                                        "load current resource error", ex);
        }
    }
    
    /**
     * ��ȡָ��loginId��Resource
     * @param loginId
     * @return
     */
    public Resource getResource(String loginId) {
    	try {
            return getUser(loginId).loadResource(Resource.getAllFields());
        } catch (BusinessException e) {
        	throw e;
        } catch (Exception ex) {
            throw new BusinessException("error.logic.PrimaveraApiBase.getResourceError",
                                        "get resource [" + loginId + "] error", ex);
        }
    }

    /**
     * ��ȡ��ǰCalendar
     * @return Calendar
     */
    public Calendar getCurrentCalendar() {
        try {
            return getCurrentResource().loadCalendar(Calendar.getAllFields());
        } catch (BusinessException e) {
        	throw e;
        } catch (Exception ex) {
            throw new BusinessException("error.logic.PrimaveraApiBase.getCalendarError",
                                        "load current calendar error", ex);
        }
    }
    
    /**
     * ��ȡָ��loginId��Calendar
     * @return Calendar
     */
    public Calendar getCalendar(String loginId) {
        try {
            return getResource(loginId).loadCalendar(Calendar.getAllFields());
        } catch (BusinessException e) {
        	throw e;
        } catch (Exception ex) {
            throw new BusinessException("error.logic.PrimaveraApiBase.getCalendarError",
                                        "get calendar [" + loginId + "] error", ex);
        }
    }

    /**
     * Ϊ���෽��ʹ��GlobalObjectManager
     * @return GlobalObjectManager
     */
    public GlobalObjectManager getGOM() {
        return getSession().getGlobalObjectManager();
    }

    /**
     * ��ȡ��ǰapi session
     * @return Session
     */
    public Session getSession() {
    	ThreadVariant thread = ThreadVariant.getInstance();
        session = (Session) thread.get(IPrimaveraApi.PRIMAVERA_API_SESSION);
        if(session == null) {
            session = getNewSession(PrimaveraConfig.getUserLoginId(),
                                    PrimaveraConfig.getUserPassWord());
            thread.set(PRIMAVERA_API_SESSION, session);
        }
        return session;
    }

    /**
     * logout��ǰsession
     */
    public void logout() {
        if(session != null) {
            session.logout();
        }
        ThreadVariant.getInstance().remove(IPrimaveraApi.PRIMAVERA_API_SESSION);
    }

    /**
     * ��ȡһ���µ�Primavera Api Session
     * @param loginId String
     * @param passWord String
     * @return Session
     */
    private final static Session getNewSession(String loginId, String passWord) {
        setBootStrapHome();
        try {
            String rmiUrl = RMIURL.getRmiUrl(RMIURL.LOCAL_SERVICE);
            DatabaseInstance[] dbs = Session.getDatabaseInstances(rmiUrl);
            int dbIndex = PrimaveraConfig.getDbIndex();
            log.info("Primavera API DB URL: " + dbs[dbIndex].getDatabaseUrl());
            return Session.login(RMIURL.getRmiUrl(RMIURL.LOCAL_SERVICE),
                                    dbs[dbIndex].getDatabaseId(),
                                    loginId, passWord);
        } catch (Exception ex) {
            throw new BusinessException("error.logic.PrimaveraApiBase.getSessionError",
                                        "login primavera integration api error", ex);
        }
    }

    /**
     * ���û�������primavera.bootstrap.home��BREBootStrap.xml����·����
     */
    private final static void setBootStrapHome() {
        System.setProperty(PrimaveraConfig.getBootstrapHomePropertyName(),
                           PrimaveraConfig.getBootStrapHome());
    }
    
    public static void main(String[] args) {
        getNewSession(PrimaveraConfig.getUserLoginId(),
                PrimaveraConfig.getUserPassWord());
    }
}
