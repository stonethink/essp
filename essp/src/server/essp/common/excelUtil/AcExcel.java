package server.essp.common.excelUtil;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.essp.common.excelUtil.IExcelParameter;
import org.apache.log4j.Category;
import server.framework.controller.CTActionLoad;
import c2s.essp.common.user.DtoUser;
import com.wits.util.ThreadVariant;

public class AcExcel extends HttpServlet {
    static Category log = Category.getInstance("excel server");
    //private static Config actionConfig = null;

    /***************************************************************
     * Initializate the Servlet
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //actionConfig = new Config("action");
    }

    /***************************************************************
     * Process incoming requests for a HTTP GET method
     * @param request The incoming request information
     * @param response The outgoing response information
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        ThreadVariant thread = ThreadVariant.getInstance();
        DtoUser user = (DtoUser) request.getSession().getAttribute(DtoUser.
                SESSION_USER);
        thread.set(DtoUser.SESSION_USER, user);

        //Added by stone 20060801
        DtoUser loginUser = (DtoUser) request.getSession().getAttribute(DtoUser.
                SESSION_LOGIN_USER);
        if (loginUser != null) {
            thread.set("LOGIN_ID", loginUser.getUserLoginId());
            thread.set(DtoUser.SESSION_LOGIN_USER, loginUser);
        }

        run(request, response);
    }

    /***************************************************************
     * Process incoming requests for a HTTP POST method
     * @param request The incoming request information
     * @param response The outgoing response information
     */
    public void doPost(
        HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
        run(request, response);
    }

    public void run(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        ServletOutputStream os = response.getOutputStream();

        try {
            //从request取得action代号，并查找对应的action
            String actionID = request.getParameter(IExcelParameter.ACTION_ID);
            if (actionID == null || actionID.length() == 0) {
                Exception e = new Exception("Input invalid action id!");
                throw e;
            }
            log.info("actionID=" + actionID);

            String actionName = CTActionLoad.getInstance().getValue(actionID);
            if (actionName == null || actionName.length() == 0) {
                Exception e = new Exception("Didn't define [" + actionID + "]!");
                throw e;
            }
            log.info("actionName=" + actionName);

            //调用action
            Class cls = Class.forName(actionName);
            IExcelBusinessAction action = (IExcelBusinessAction) cls.
                                          newInstance();

            action.execute(request, response, os);
        } catch (Exception ex) {
            errorHandle(response, os, ex);
        }
    }

    public void errorHandle(HttpServletResponse response, ServletOutputStream os,
                            Exception ex) {
        try {
            log.error("",ex);

            response.setContentType("text/html;charset=gb2312");

            String strErrMsg = "System Exception:" + ex.getMessage();

            (os).println(strErrMsg);

        } catch (Exception e) {
            log.error("",e);
        }
    }
}
