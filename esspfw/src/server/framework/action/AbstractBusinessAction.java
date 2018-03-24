package server.framework.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import c2s.dto.DtoUtil;
import c2s.dto.TransactionData;
import com.primavera.integration.client.Session;
import com.wits.util.Parameter;
import com.wits.util.ThreadVariant;
import org.apache.log4j.Category;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.struts.ActionSupport;
import server.framework.authorize.DefaultAuthorizeImp;
import server.framework.authorize.IAuthorizable;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import server.framework.hibernate.HBComAccess;
import server.framework.logic.AbstractBusinessLogic;
import server.framework.primavera.IPrimaveraApi;

public abstract class AbstractBusinessAction extends ActionSupport implements
        IBusinessAction {
    protected boolean isEncoding = true;
    protected String encode = "iso8859_1";
    protected String dencode = "utf-8";
    protected Category log = Category.getInstance("server");
    private HBComAccess hbAccess = null;
    private static ApplicationContext context = null;

    public HBComAccess getDbAccessor() {
        return hbAccess;
    }

    public final void execute(HttpServletRequest request1,
                              HttpServletResponse response1,
                              TransactionData data) throws Exception {
    	HBComAccess hbAccess1 = null;
        try {
        	synchronized(this) {
        		initTranction();
        		hbAccess1 = this.hbAccess;
        	}
            data.getReturnInfo().setError(false);
            /*
             * 初始化属性
             */
            this.request = request1;
            this.response = response1;

            /*
             * 取得session对象
             */
            if (request1 != null) {
                validateRequest();
                getSessionFromRequest(request1);
            }
            preProcess(request1, response1, data);
            executeAct(request1, response1, data);
            postProcess(request1, response1, data);
            if (data.getReturnInfo().isError()) {
                hbAccess1.rollback();
            } else {
                hbAccess1.endTxCommit();
            }
        } catch (Exception ex) {
            log.error("", ex);
            try {
                session.setAttribute("exception", ex);
            } catch (Exception ex1) {
            }
            try {
                hbAccess1.rollback();
            } catch (Exception ex1) {
                log.error("", ex1);
                ex = new BusinessException("error.system","System Error.");
            }
            if (!(ex instanceof BusinessException)) {
                ex = new BusinessException("error.system",
                        "System Error.");
            }
            data.getReturnInfo().setError(true);
            data.getReturnInfo().setError(ex);
            String errorCode = ((BusinessException)ex).getErrorCode();
            data.getReturnInfo().setReturnCode(errorCode);
        } finally {
            try {
                hbAccess1.close();
            } catch (Exception ex1) {
            }
            try {
                logoutPrimaveraApi();
            } catch (Exception ex1) {
            }
        }
    }

    private void getSessionFromRequest(HttpServletRequest request1) {
        session = request1.getSession();
        if (session == null) {
            session = request.getSession(true);
        }
    }
    /**
     * 验证Request是否合法
     * @throws BusinessException
     */
    private void validateRequest() throws BusinessException {
        if (!isAuthorized()) {
            request.setAttribute("functionName",
                                 getFunctionID() + ".name");
            request.setAttribute("returnCode", "MHE107");
            throw new BusinessException("E_FW001",
                                        "User not authorized!!!");
        }
    }

    public void executeLogic(AbstractBusinessLogic logic, Parameter param) throws
            BusinessException {
        logic.executeLogic(param);
    }

    /**
     * 前置处理
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     */
    public void preProcess(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) {

    }

    /**
     * 后置处理
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     */
    public void postProcess(HttpServletRequest request,
                            HttpServletResponse response,
                            TransactionData data) {

    }

    public abstract void executeAct(HttpServletRequest request,
                                    HttpServletResponse response,
                                    TransactionData data) throws
            BusinessException;

    private void dumpForm(Object obj) {
        if (form != null) {
            log.info("******************Dump form data**************");
            log.info("form class is " + obj.getClass().getName());
            String[] pnames = DtoUtil.getPropertyNames(obj);
            if (pnames != null) {
                for (int i = 0; i < pnames.length; i++) {
                    try {
                        log.info(pnames[i] + "=[" +
                                 DtoUtil.getProperty(obj, pnames[i]) + "]");
                    } catch (Exception ex) {
                    }
                }
            }
            log.info("******************Dump end!!!**************");
        } else {
            log.info("form is null");
        }
    }

    /**
     * 从Spring的配置中取得Service Bean
     *
     * @param beanName
     * @return Object
     * @author Robin
     */
    protected Object getBean(String beanName) {
        ApplicationContext context = this.getWebApplicationContext();
        if(context == null){
            context = getContext();
        }
        return context.getBean(beanName);
    }

    /**
     * context 单例
     * @return ApplicationContext
     * @throws BeansException
     */
    private ApplicationContext getContext() throws BeansException {
        if(context == null) {
            context = new ClassPathXmlApplicationContext(SPRING_CONFIG_MATCH);
        }
        return context;
    }

    public static final String SPRING_CONFIG_MATCH="classpath*:sysCfg/applicationContext*.xml";//hibernatep配置文件的匹配规则
    /**
     * 针对Struts架构实现的接口方法
     * @param mapping1 ActionMapping
     * @param form1 ActionForm
     * @param request1 HttpServletRequest
     * @param response1 HttpServletResponse
     * @return ActionForward
     */
    public final ActionForward execute(ActionMapping mapping1,
                                       ActionForm form1,
                                       HttpServletRequest request1,
                                       HttpServletResponse response1) {
    	HBComAccess hbAccess1 = null;
        try {
        	synchronized(this) {
        		initTranction();
        		hbAccess1 = this.hbAccess;
        	}
            /*
             * 初始化属性
             */
            this.mapping = mapping1;
            this.form = form1;
            this.request = request1;
            this.response = response1;

            dumpForm(form1);
            /*
             * 并将ActionForm转换成TransactionData
             */
            TransactionData data = new TransactionData();

            data.getInputInfo().setInputObj(Constant.ACTION_FORM_KEY, form1);
            //初始化TransactionData
            data.getReturnInfo().setError(false);

            /*
             * 取得session对象
             */
            if (request1 != null) {
                validateRequest();
                getSessionFromRequest(request1);
            }

            /*
             * 执行子类中的executeAct方法，每个子类处理完成之后返回一个AppStatus对象
             */
            preProcess(request1, response1, data);
            executeAct(request1, response1, data);
            postProcess(request1, response1, data);
            if (data.getReturnInfo().isError()) {
                hbAccess1.rollback();
            } else {
                hbAccess1.endTxCommit();
            }

            request1.setAttribute("functionName", getFunctionID() + ".name");
            request1.setAttribute("returnCode",
                                 data.getReturnInfo().getReturnMessage());

            if (data.getReturnInfo().isError()) {
                return mapping1.findForward(Constant.PAGE_FAILURE);
            } else if (data.getReturnInfo().getForwardID() != null &&
                       !data.getReturnInfo().getForwardID().trim().equals("")) {
                return mapping1.findForward(data.getReturnInfo().getForwardID());
            } else {
                return mapping1.findForward(Constant.PAGE_SUCCESS);
            }
        } catch (Exception e) {
            log.error("", e);
            try {
                hbAccess1.rollback();
            } catch (Exception ex1) {
                log.error("", ex1);
                e = new BusinessException("error.system","System Error.");
            }
            if (!(e instanceof BusinessException)) {
                e = new BusinessException("error.system",
                        "System Error.");
            }

            request1.setAttribute("errorCode", ((BusinessException)e).getErrorCode());
            request1.setAttribute("errorMsg", ((BusinessException)e).getErrorMsg());
            return mapping1.findForward(Constant.PAGE_FAILURE);
        } finally {
            try {
                hbAccess1.close();
            } catch (Exception ex1) {
            }
            try {
                logoutPrimaveraApi();
            } catch (Exception ex1) {
            }
        }
    }
    /**
     * 初始化数据库连接和事物
     * @throws Exception
     */
    private void initTranction() throws Exception {
        ThreadVariant thread = ThreadVariant.getInstance();
        thread.set("CURRENT_ACTION", this);
        hbAccess = HBComAccess.newInstance();
        //开始事务
        hbAccess.followTx();
    }

    /**
     * 返回功能名称在application.properties中对应的键值
     * 请务必在自己的Action中重载此方法。本来应该将此方法声明为abstract，
     * 可是考虑到以前的Action程序中没有这个方法，所以暂不做改动。因此请在需要使用Struts的Action中重载此方法。
     * @return 功能名称在application.properties中对应的键值
     */
    protected String getFunctionID() {
        String className = this.getClass().getName();
        int dotIndex = className.lastIndexOf(".");
        String id = "";
        if (dotIndex >= 0) {
            id = className.substring(dotIndex + 1);
        } else {
            id = className;
        }
        return id;
    }

    /**
     * 转换字符串编码(from iso8859_1 to GBK )
     * @param value String
     * @return String
     */
    private String transcode(String value) {
        String result;
        try {
            result = new String(value.getBytes(encode), dencode);
            //if (result.indexOf('?') >= 0) {
            //    result = value;
            //}
        } catch (Exception e) {
            result = value;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 定义Session属性
     */
    private HttpSession session = null;

    /**
     * 定义Request属性
     */
    private HttpServletRequest request;

    /**
     * 定义response属性
     */
    private HttpServletResponse response;

    /**
     * 定义ActionMapping属性
     */
    private ActionMapping mapping = null;

    /**
     * 定义ActionForm属性
     */
    private ActionForm form = null;

    /**
     * 取得Session对象
     *
     * @return Session对象
     */
    protected HttpSession getSession() {
        return session;
    }

    /**
     * 取得Request对象
     *
     * @return Request对象
     */
    protected HttpServletRequest getRequest() {
        return request;
    }

    /**
     * 取得Response对象
     *
     * @return Response对象
     */
    protected HttpServletResponse getResponse() {
        return response;
    }

    /**
     * 取得当前的ActionMapping
     *
     * @return 当前的ActionMapping
     */
    protected ActionMapping getMapping() {
        return mapping;
    }

    /**
     * 取得当前的ActionForm
     *
     * @return 当前的ActionForm
     */
    protected ActionForm getForm() {
        return form;
    }

    private boolean isAuthorized() {
        boolean result = false;
        try {
            IAuthorizable authorize = (IAuthorizable) getAuthorizeClass().
                                      newInstance();
            result = authorize.isAuthorized(this.getRequest());
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }

        return result;
    }

    protected Class getAuthorizeClass() {
        return DefaultAuthorizeImp.class;
    }

    /**
     * logout primavera api in current thread
     */
    private void logoutPrimaveraApi() {
        ThreadVariant thread = ThreadVariant.getInstance();
        Session apiSession =  (Session) thread.get(IPrimaveraApi.PRIMAVERA_API_SESSION);
        if(apiSession != null) {
            apiSession.logout();
            thread.remove(IPrimaveraApi.PRIMAVERA_API_SESSION);
        }
    }
}
