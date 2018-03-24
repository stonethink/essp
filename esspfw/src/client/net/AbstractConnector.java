package client.net;

import client.framework.common.Global;

public abstract class AbstractConnector {
    //private static String defaultControllerURL = "http://localhost:8080/essp/Controller";

    public String getDefaultControllerURL() {
        return Global.defaultControllerURL;
    }

    public void setDefaultControllerURL(String controllerURL) {
        Global.defaultControllerURL = controllerURL;
    }
}
