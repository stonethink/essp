package client.essp.pwm.wp;

import org.apache.log4j.Category;
import c2s.dto.TransactionData;
import java.util.List;
import java.util.Vector;
import c2s.dto.DtoComboItem;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import client.net.NetConnector;
import client.net.ConnectFactory;
import java.util.Iterator;
import c2s.essp.pwm.wp.DtoActivityInfo;

public class GetActivityList {
    static Category log = Category.getInstance("client");

    private List activityList;

    public GetActivityList() {
    }

    public Vector[] getActivityList(String inProjectId) {
        Vector[] vaActivityList = new Vector[2];

        String actionId = "FPWGetActivityList";
        String funId = "";

        TransactionData transData = new TransactionData();
        InputInfo inputInfo = transData.getInputInfo();
        //inputInfo.setControllerUrl(HandleServlet.getServerActionURL());
        inputInfo.setActionId(actionId);
        inputInfo.setFunId(funId);

        inputInfo.setInputObj("inProjectId", inProjectId);

        NetConnector connector = ConnectFactory.createConnector();
        connector.accessData(transData);

        ReturnInfo returnInfo = transData.getReturnInfo();
        if (returnInfo.isError() == false) {
            try {
                activityList = (List) returnInfo.getReturnObj("activityList");

                int iSize = activityList.size();
                vaActivityList[0] = new Vector(iSize);
                vaActivityList[1] = new Vector(iSize);

                for (int i = 0; i < iSize; i++) {
                    DtoActivityInfo ai = (DtoActivityInfo) activityList.get(i);
                    DtoComboItem ciActivityCode = new DtoComboItem(ai.getSchno(), ai.getActivityId());
//                    DtoComboItem ciActivityName = new DtoComboItem(ai.getClnitem(), ai.getClnitem(), ai.getActivityId());
                    DtoComboItem ciActivityName = new DtoComboItem(ai.getClnitem(), ai.getActivityId(), ai.getActivityId());
                    vaActivityList[0].add(ciActivityCode);
                    vaActivityList[1].add(ciActivityName);
                }

                return vaActivityList;
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
//        return vaActivityList;
    }

    public String getActivityNameById(Long lId) {
        if( lId == null ){
            return null;
        }

        String outStr = null;
        Iterator iter = activityList.iterator();
        while (iter.hasNext()) {
            DtoActivityInfo item = (DtoActivityInfo) iter.next();
            if (item.getActivityId() == null) {
                if (lId == null) {
                    outStr = item.getClnitem();
                }
            } else if (item.getActivityId().equals(lId)) {
                outStr = item.getClnitem();
                break;
            }
        }
        return outStr;
    }

    public Long getActivityIdByName(String accountName) {
        if( accountName == null ){
            return null;
        }

        Long outLong = null;
        Iterator iter = activityList.iterator();
        while (iter.hasNext()) {
            DtoActivityInfo item = (DtoActivityInfo) iter.next();
            if (item.getClnitem() == null) {
                if (accountName == null) {
                    outLong = item.getActivityId();
                }
            } else if (item.getClnitem().equals(accountName)) {
                outLong = item.getActivityId();
                break;
            }
        }
        return outLong;
    }

    public static void main(String[] args) {
        GetActivityList lgGetActivityList = new GetActivityList();
        TransactionData transData = new TransactionData();
        lgGetActivityList.getActivityList("1");

//        List list = (List) transData.getReturnInfo().getReturnObj("activityList");
//        log.debug("count=" + list.size());

//        lgGetActivityList.getActivityList(transData);
    }

}
