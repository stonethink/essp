package client.essp.tc.weeklyreport;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import c2s.dto.DtoComboItem;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import client.framework.view.common.comMSG;
import client.net.ConnectFactory;
import client.net.NetConnector;
import validator.ValidatorResult;

public class GetCodeList {
    public final static String actionId = "FTCCodeListAction";

    public Vector getCodeList(Long acntRid) {
        Vector codeList = new Vector();

        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId);
        inputInfo.setInputObj(DtoTcKey.ACNT_RID, acntRid);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            List codeValueRidList = (List) returnInfo.getReturnObj(DtoTcKey.CODE_VALUE_RID_LIST);
            List codeValueNameList = (List) returnInfo.getReturnObj(DtoTcKey.CODE_VALUE_NAME_LIST);
            List codeValueDescriptionList = (List) returnInfo.getReturnObj(DtoTcKey.CODE_VALUE_DESCRIPTION_LIST);

            Iterator iterName = codeValueNameList.iterator();
            Iterator iterRid = codeValueRidList.iterator();
            Iterator iterDescription = codeValueDescriptionList.iterator();
            while (iterName.hasNext() && iterRid.hasNext() && iterDescription.hasNext()) {
                String name = (String) iterName.next();
                Long rid = (Long) iterRid.next();
                String description = (String)iterDescription.next();
                if(rid != null && name != null && !"".equals(name.trim())){
                    DtoComboItem item = new DtoComboItem(name, rid, description);
                    codeList.add(item);
                }
            }
        }

        return codeList;
    }

    /**
     * 访问服务端
     */
    protected ReturnInfo accessData(InputInfo inputInfo) {
        TransactionData transData = new TransactionData();
        transData.setInputInfo(inputInfo);

        NetConnector connector = ConnectFactory.createConnector();
        connector.accessData(transData);

        ReturnInfo returnInfo = transData.getReturnInfo();

        if (returnInfo.isError() == true) {
            ValidatorResult result = returnInfo.getValidatorResult();

            if (!result.isValid()) {
                //comMSG.dispErrorDialog(result.g);
                StringBuffer msg = new StringBuffer();

                for (int i = 0; i < result.getAllMsg().length; i++) {
                    msg.append(result.getAllMsg()[i]);
                    msg.append("\r\n");
                }

                comMSG.dispErrorDialog(msg.toString());
            } else {
                comMSG.dispErrorDialog(returnInfo.getReturnMessage());

                //全部置红
            }
        } else {

        }
        return returnInfo;
    }

    public static void main(String args[]) {
        GetCodeList getCodeList = new GetCodeList();
        System.out.println(getCodeList.getCodeList(null).size());
    }
}
