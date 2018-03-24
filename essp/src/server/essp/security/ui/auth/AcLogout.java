package server.essp.security.ui.auth;

import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AcLogout extends AbstractBusinessAction {
    public AcLogout() {
    }

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
        //清空Session中所有数据
        HttpSession s = this.getSession();
        if(s != null){
            Enumeration e = s.getAttributeNames();
            while(e.hasMoreElements()){
                String name = (String) e.nextElement();
                s.removeAttribute(name);
            }
        }
    }
}
