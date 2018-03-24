package client.essp.common;

import c2s.dto.ReturnInfo;
import validator.ValidatorResult;
import client.net.NetConnector;
import client.framework.view.common.comMSG;
import client.net.ConnectFactory;
import c2s.dto.TransactionData;
import client.framework.view.vwcomp.VWUtil;
import c2s.dto.InputInfo;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AccessServer {
    public AccessServer() {
    }
    public static ReturnInfo accessData(InputInfo inputInfo){
        TransactionData transData = new TransactionData();
        transData.setInputInfo(inputInfo);

        NetConnector connector = ConnectFactory.createConnector();
        connector.accessData(transData);

        ReturnInfo returnInfo = transData.getReturnInfo();

        return returnInfo;

    }
}
