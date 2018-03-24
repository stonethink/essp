package c2s.dto;

import java.io.Serializable;
import com.wits.util.Parameter;
import java.util.Iterator;

public class InputInfo implements Serializable {
    private String controllerUrl;
    private String actionId;
    private String funId;
    private Parameter inputObjs = new Parameter();

    public InputInfo() {
    }

    public InputInfo(String controllerUrl) {
        this.controllerUrl = controllerUrl;
        //this.actionId =actionId;
        //this.funId=funId;
    }

    public InputInfo(String controllerUrl, String actionId) {
        this.controllerUrl = controllerUrl;
        this.actionId = actionId;
        //this.funId=funId;
    }

    public InputInfo(String controllerUrl, String actionId, String funId) {
        this.controllerUrl = controllerUrl;
        this.actionId = actionId;
        this.funId = funId;
    }

    public String getControllerUrl() {
        return controllerUrl;
    }

    public void setControllerUrl(String controllerUrl) {
        this.controllerUrl = controllerUrl;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getFunId() {
        return funId;
    }

    public void setFunId(String funId) {
        this.funId = funId;
    }

    public Object getInputObj( String key ) {
        return this.inputObjs.get( key );
    }

    public void setInputObj( String key, Object inputObj ) {
        this.inputObjs.put( key, inputObj );
    }

    public void clearInputObjs(){
        this.inputObjs.clear();
    }

    public void copy(Object other) {
        if(other!=null && other instanceof InputInfo) {
            InputInfo otherObj=(InputInfo)other;
            this.controllerUrl=otherObj.controllerUrl;
            this.actionId=otherObj.actionId;
            this.funId=otherObj.funId;
            if(otherObj.inputObjs!=null) {
                for(Iterator it=otherObj.inputObjs.keySet().iterator();it.hasNext();) {
                    Object key=it.next();
                    inputObjs.put(key,otherObj.inputObjs.get(key));
                }
            }
        }
    }
}
