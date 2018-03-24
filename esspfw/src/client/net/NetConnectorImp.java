package client.net;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.net.URLConnection;
import org.apache.log4j.Category;

public class NetConnectorImp extends AbstractConnector implements NetConnector {
    Category log = Category.getInstance(this.getClass().getName());

    public NetConnectorImp() {
    }

    public int accessData(InputInfo input, ReturnInfo output) {
        TransactionData data = new TransactionData();
        data.setInputInfo(input);
        data.setReturnInfo(output);
        return accessData(data);
    }

    public int accessData(TransactionData data) {
        int iRet = 0;
        String controllerURL = "";

        ObjectOutputStream out = null;
        ObjectInputStream input = null;
        TransactionData returnObject = null;

        try {
            data.getReturnInfo().clearReturnObjs();
            //URL url = new URL(ControllerURL);
            controllerURL = data.getInputInfo().getControllerUrl();
            if (controllerURL == null || controllerURL.length() == 0) {
                if(getDefaultControllerURL() == null || getDefaultControllerURL().length() == 0){
                    System.out.println("defaultControllerURL is null");
                    return -1;
                }else{
                    controllerURL = getDefaultControllerURL();
               }
            }

            log.info( "controllerURL: " + controllerURL );
            URL url = new URL(controllerURL);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);

            out = new ObjectOutputStream(conn.getOutputStream());
            out.writeObject(data);
            out.flush();
            out.close();

            input = new ObjectInputStream(conn.getInputStream());

            returnObject = (TransactionData) input.readObject();
            input.close();
            data.copy(returnObject);

        } catch (Exception e) {
            e.printStackTrace();
            data.getReturnInfo().setError(e);
            try {
                if (out != null) {
                    out.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return iRet;
    }

}
