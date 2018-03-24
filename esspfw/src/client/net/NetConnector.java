package client.net;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;

public interface NetConnector {
    public int accessData(InputInfo input,ReturnInfo output);
    public int accessData(TransactionData data);
    public String getDefaultControllerURL();
    public void setDefaultControllerURL(String controllerURL);
//    public int accessData(c2s.vds.VDView_Data vd);
}
