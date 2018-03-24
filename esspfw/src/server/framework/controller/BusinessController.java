package server.framework.controller;

/**
 * @author Stone
 *
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Category;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import server.framework.action.IBusinessAction;
import junitpack.HttpServletRequestMock;
import junitpack.HttpServletResponseMock;

public class BusinessController extends HttpServlet {
    static Category log = Category.getInstance("server");

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

    /***************************************************************
     * Process both HTTP GET and HTTP POST methods
     * @param request The incoming request information
     * @param response The outgoing response information
     */
    public void run(
        HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {

        TransactionData receivedData = null;
        TransactionData returnData = null;

        try {
            receivedData = receivedData(request);

            //从request取得action代号，并查找对应的action
            String actionID = receivedData.getInputInfo().getActionId();
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
            IBusinessAction action = (IBusinessAction) cls.newInstance();
            //returnData = action.execute(request, response, receivedData);
            action.execute(request, response, receivedData);

            returnData = receivedData;

            //向Client返回结果
            returnData.getInputInfo().clearInputObjs();
            returnData(response, returnData);
        } catch (Exception e) {
            //e.printStackTrace();
            //System.out.println("Exception =  " + e.toString());
            errorHandle(response, e);
        }

    }

    /***************************************************************
     * Receive data
     * @param HttpServletRequest request
     */
    public TransactionData receivedData(HttpServletRequest request) throws
        Exception {
        ObjectInputStream input = null;
        Object o = null;

        try {
            input = new ObjectInputStream(request.getInputStream());
            if (input == null) {
                log.error("Input is null!");
                Exception e = new Exception("Input is null!");
                throw e;
            }
            o = input.readObject();
        } catch (Exception e) {
            log.error("Read data error!");
            throw e;
            //return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception e) {
                    log.error("Input close error!");
                    throw e;
                    //return null;
                }
            }
        }
        if (o == null) {
            log.error("Input data is null!");
            Exception e = new Exception("Input data is null!");
            throw e;
            //return null;
        }
        if (! (o instanceof TransactionData)) {
            log.error("transData type error!");
            Exception e = new Exception("transData type error!");
            throw e;
            //return null;
        }

        return (TransactionData) o;
    }

    /***************************************************************
     * Return data
     * @param HttpServletRequest request
     */
    public void returnData(HttpServletResponse response, TransactionData data) throws
        Exception {
        ObjectOutputStream output = null;

        try {
            data.getInputInfo().clearInputObjs();

            output = new ObjectOutputStream(response.getOutputStream());
            if (output == null) {
                log.error("Output is null!");
                Exception e = new Exception("Output is null!");
                throw e;
            }
            output.writeObject(data);
        } catch (Exception e) {
            log.error("Write data error!", e);
            //Exception e = new Exception("Write data error!");
            throw e;
        } finally {
            if (output != null) {
                output.close();
            }
        }

    }

    /***************************************************************
     * Error handle
     * @param ex The incoming exception information
     */
    public void errorHandle(HttpServletResponse response, Exception ex) {
        try {
            //输出ERROR结果,System Exception
            log.error(ex);
            String strErrMsg = "System Exception:" + ex.getMessage();
            log.info("ERROR=" + strErrMsg);

            TransactionData returnData = new TransactionData();
            ReturnInfo rtnInfo = returnData.getReturnInfo();
            rtnInfo.setReturnCode("S0000999");
            rtnInfo.setReturnMessage(strErrMsg);

            returnData(response, returnData);
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Exception =  " + e.toString());
            log.error("ERROR=" + e.toString());
        }
    }

    /***************************************************************
     * Destroy the Servlet
     */
    public void destroy() {
        //Place code here to be done when the servlet is shutdown
        //Destroy the database connection
    }

    public void run(TransactionData transData) {
        HttpServletRequest request = new HttpServletRequestMock();
        HttpServletResponse response = new HttpServletResponseMock();;

        TransactionData receivedData = null;
        TransactionData returnData = null;

        try {
            receivedData = transData;

//从request取得action代号，并查找对应的action
            String actionID = receivedData.getInputInfo().getActionId();
            if (actionID == null || actionID.length() == 0) {
                Exception e = new Exception("Input invalid action id!");
                throw e;
            }
            log.debug("actionID=" + actionID);

            String actionName = CTActionLoad.getInstance().getValue(actionID);
            if (actionName == null || actionName.length() == 0) {
                Exception e = new Exception("Didn't define [" + actionID + "]!");
                throw e;
            }
            log.debug("actionName=" + actionName);

//调用action
            Class cls = Class.forName(actionName);
            IBusinessAction action = (IBusinessAction) cls.newInstance();
//returnData = action.execute(request, response, receivedData);
            action.execute(request, response, receivedData);

            //returnData = receivedData;

//向Client返回结果
//returnData(response, returnData);
            //transData = returnData;
        } catch (Exception e) {
            //e.printStackTrace();
//            System.out.println("Exception =  " + e.toString());
            log.error("",e);
            transData.getReturnInfo().setError(e);
//      errorHandle(response, e);
        }

    }

}
