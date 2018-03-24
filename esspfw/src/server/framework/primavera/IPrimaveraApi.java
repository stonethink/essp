package server.framework.primavera;

import com.primavera.integration.client.*;

public interface IPrimaveraApi {
    //the key which the primavera api session in thread
    public final static String PRIMAVERA_API_SESSION =
            "primavera.api.session";

    /**
     * get current thread primavera api session
     * @return Session
     */
    public Session getSession();

    /**
     * get current session's GlobalObjectManager
     * @return GlobalObjectManager
     */
    public GlobalObjectManager getGOM();

    /**
     * logout current session if exist.
     */
    public void logout();
}
