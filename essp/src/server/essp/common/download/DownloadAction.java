package server.essp.common.download;

import server.framework.action.*;
import javax.servlet.http.*;
import c2s.dto.*;
import java.io.OutputStream;
import java.io.FileInputStream;
import server.framework.common.BusinessException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author not attributable
 * @version 1.0
 */

public class DownloadAction extends AbstractBusinessAction {
    public DownloadAction() {
    }

    public void executeAct(HttpServletRequest request, HttpServletResponse response, TransactionData data) throws server.framework.common.BusinessException {
        String filePath = (String) data.getInputInfo().getInputObj("SERVER_FILE");
        try {
            OutputStream os = response.getOutputStream();
            FileInputStream fis = new FileInputStream(filePath);
            byte[] buffer = new byte[8192];
            int readSize = 0;
            while ( (readSize = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, readSize);
            }
            os.flush();
            fis.close();
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("500000", "download.fail");
        }

    }

}
