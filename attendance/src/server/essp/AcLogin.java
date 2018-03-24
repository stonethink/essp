package server.essp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import c2s.dto.TransactionData;
import c2s.essp.common.user.DtoUser;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import java.io.InputStream;
import java.io.*;
import java.util.Enumeration;

public class AcLogin extends AbstractBusinessAction {
    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
//        System.out.println("AuthType: " + request.getAuthType());
//        System.out.println("ContentType: " + request.getContentType());
//        System.out.println("RemoteUser: " + request.getRemoteUser());
//        Enumeration en = request.getHeaderNames();
//        while(en.hasMoreElements()){
//            String hearName = (String) en.nextElement();
//            System.out.println("hearName: " +hearName + ":"+ request.getHeader(hearName));
//        }
            Enumeration en = request.getHeaderNames();
            while(en.hasMoreElements()){
                String header = (String) en.nextElement();
                String value = request.getHeader(header);
                System.out.println("header:" + header +"  ---  value:" + value);
            }
        HttpSession session = request.getSession();
//        System.out.println("session id:" + session.getId());
//        System.out.println("session create time:" + session.getCreationTime());
//        System.out.println("session last acc time:" + session.getLastAccessedTime());
//        System.out.println("session max time:" + session.getMaxInactiveInterval());
        String loginId = request.getParameter("loginId");
        DtoUser user = new DtoUser();
        user.setUserID(loginId);
        user.setUserLoginId(loginId);
        user.setUserName(loginId);
        session.setAttribute(DtoUser.SESSION_USER, user);

        String id = request.getParameter("id");
        data.getReturnInfo().setForwardID(id);
    }

}
