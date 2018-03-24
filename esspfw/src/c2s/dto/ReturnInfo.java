package c2s.dto;

import java.io.Serializable;
import com.wits.util.Parameter;
import validator.ValidatorResult;
import java.util.Iterator;

public class ReturnInfo implements Serializable {
    private boolean isError = false;
    private String returnCode;
    private String returnMessage;
    private String forwardID;
    private boolean needForward=true;
    private Parameter returnObjs = new Parameter();
    private ValidatorResult validatorResult = new ValidatorResult();

    ///////////////////////////////////
    public ReturnInfo() {
    }

    public ReturnInfo(String returnCode) {
        this.returnCode = returnCode;
        //this.returnMessage = returnMessage;
        //this.RTN_OBJ =RTN_OBJ;
    }

    public ReturnInfo(String returnCode, String returnMessage) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        //this.RTN_OBJ =RTN_OBJ;
    }

//    public VDRtn_Info(String returnCode, String returnMessage, Object RTN_OBJ) {
//        this.returnCode = returnCode;
//        this.returnMessage = returnMessage;
//        this.RTN_OBJ = RTN_OBJ;
//    }

    ///////////////////////////////////
    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public Object getReturnObj(String key) {
        return returnObjs.get(key);
    }

    public void setReturnObj(String key, Object returnObj) {
        this.returnObjs.put(key, returnObj);
    }

    public void clearReturnObjs() {
        this.returnObjs.clear();
    }

    public ValidatorResult getValidatorResult() {
        return validatorResult;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean isError) {
        this.isError = isError;
    }

    public void setError(Exception e) {
        if (e != null) {
//            StringWriter sw = new StringWriter();
//            PrintWriter pw = new PrintWriter(sw);
//            e.printStackTrace(pw);
//            setReturnMessage(sw.toString());
           setReturnMessage(e.getMessage());
        } else {
            setReturnMessage("");
        }
        this.setError(true);
    }

    public void copy(Object other) {
        if (other != null && other instanceof ReturnInfo) {
            ReturnInfo otherObj = (ReturnInfo) other;
            this.returnCode = otherObj.returnCode;
            this.returnMessage = otherObj.returnMessage;
            this.isError = otherObj.isError;
            if (otherObj.returnObjs != null) {
                for (Iterator it = otherObj.returnObjs.keySet().iterator(); it.hasNext(); ) {
                    Object key = it.next();
                    returnObjs.put(key, otherObj.returnObjs.get(key));
                }
            }
            this.validatorResult = otherObj.validatorResult;
        }
    }

    public String getForwardID() {
      return forwardID;
    }

    public void setForwardID(String forwardId) {
      this.forwardID=forwardId;
    }
    public boolean isNeedForward() {
        return needForward;
    }
    public void setNeedForward(boolean isNeedForward) {
        this.needForward = isNeedForward;
    }
}
