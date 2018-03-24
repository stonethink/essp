package server.framework.common;

import org.apache.log4j.*;

/**
 * This is a special Exception for the FrameWork
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
public class BusinessException extends RuntimeException {
    static Category log = Category.getInstance("server");
    /**
     * messageFileID定义在struts-config文件中，如果messageFileID为空，则用缺省的message
     * 在struts-config文件中，messageFileID的定义格式为:
     * <message-resources key="crm.qc" parameter="server/essp/crm/qc/resources/application" />
     * 其中key="crm.qc" 就指明messageFileID为"crm.qc"，相应的message file就位于 "server/essp/crm/qc/resources/application"
     */
    String messageFileID=null;

    String errorCode = new String();
    String errorMsg = new String();

    /**
     * default Constructor
     */
    public BusinessException() {
        super();
    }

    /**
     * default Constructor
     * @param e Exception a base Exception
     */
    public BusinessException(Exception e) {
        super(e);
    }

    /**
     * default Constructor
     * @param errorCode String Error Code
     */
    public BusinessException(String errorCode) {
        super(MsgLoad.getInstance().getMsg(errorCode));
        this.errorCode = errorCode;
        this.errorMsg = MsgLoad.getInstance().getMsg(errorCode);
    }

    /**
     * default Constructor
     * @param errorCode String Error Code
     * @param e Throwable
     */
    public BusinessException(String errorCode,Throwable e) {
        super(MsgLoad.getInstance().getMsg(errorCode),e);
        this.errorCode = errorCode;
        this.errorMsg = MsgLoad.getInstance().getMsg(errorCode);
    }

    /**
     * default Constructor
     * @param errorCode String Error Code
     * @param e Throwable
     */
    public BusinessException(String errorCode,Throwable e,String messageFileID) {
        super(MsgLoad.getInstance().getMsg(errorCode),e);
        this.errorCode = errorCode;
        this.errorMsg = MsgLoad.getInstance().getMsg(errorCode);
        this.messageFileID=messageFileID;
    }

    /**
     * default Constructor
     * @param errorCode String Error Code
     * @param errorMsg String  Error Message
     */
    public BusinessException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    /**
     * default Constructor
     * @param errorCode String
     * @param errorMsg String
     * @param e Throwable
     */
    public BusinessException(String errorCode, String errorMsg,Throwable e) {
        super(errorMsg,e);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    /**
     * get Error Code
     * @return String Error Code
     */
    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public String getMessageFileID() {
        return messageFileID;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setMessageFileID(String messageFileID) {
        this.messageFileID = messageFileID;
    }
}
