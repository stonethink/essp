package client.net;


import c2s.dto.TransactionData;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import org.apache.log4j.Category;
import java.net.URL;
import java.net.URLConnection;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileOutputStream;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author not attributable
 * @version 1.0
 */

public class DownloadImp extends AbstractConnector implements NetConnector {
    Category log = Category.getInstance(this.getClass().getName());

    public DownloadImp() {
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
        InputStream input = null;
        FileOutputStream fos=null;
        TransactionData returnObject = null;

        try {
            fos=new FileOutputStream((String)data.getInputInfo().getInputObj("CLIENT_FILE"));
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

            input = conn.getInputStream();
            byte[] buffer=new byte[8192];
            int readSize=0;
            while((readSize=input.read(buffer,0,8192))!=-1) {
                fos.write(buffer,0,readSize);
            }
            fos.close();
            input.close();
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
