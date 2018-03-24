package client.net;

import java.util.ResourceBundle;

public class ConnectFactory {
    private static final String CONFIG_FILE_NAME = "client/net/config";
    private static final String LEVEL = "LEVEL";
    public static NetConnector createConnector() {
        try {
            ResourceBundle resources = ResourceBundle.getBundle(CONFIG_FILE_NAME);
            String level = resources.getString(LEVEL);
            if (level != null && level.trim().equals("") == false) {
                String classKey = level + ".CLASS";
                String className = resources.getString(classKey);
                if (className != null && className.trim().equals("") == false) {
                    Class netConnectorClass = Class.forName(className);
                    if (netConnectorClass != null) {
                        NetConnector connector = (NetConnector) netConnectorClass.newInstance();
                        return connector;
                    }
                }
            }
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static NetConnector createDownloadConnector() {
        NetConnector connector = new DownloadImp();
        return connector;
    }
}
