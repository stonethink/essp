package client.essp.common.hrallocate;

import org.apache.log4j.Category;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import client.framework.common.Global;
import client.framework.view.vwcomp.IVWJavaScriptCaller;
import client.framework.view.vwcomp.IVWJavaScriptListener;
import client.net.ConnectFactory;
import client.net.NetConnector;
import com.wits.util.Parameter;

public class HrAllocate {
    static Category log = Category.getInstance("client");
//    public static HrAllocate instance;
    private Sponsor sponsor = null;
    private IVWJavaScriptListener jsListener = null;

    public HrAllocate(){
        jsListener = new IVWJavaScriptListener(){
            public void actionPerformed(Parameter param){
                String newDataStr = (String)param.get( "newData" );
                hrAllocOK( newDataStr );
            }
        };
    }

    public void allocWorker( Sponsor sponsor ){
        this.sponsor = sponsor;
        alloc( this.sponsor.getOldData(), "", "99" );
    }

    public static String getWorkerName(String[] userIds) {
        if (userIds == null || userIds.equals("")) {
            return "";
        }
        String inUserId = "";
        for (int i = 0; i < userIds.length; i++) {
            if (!(userIds[i] == null || userIds[i].equals(""))) {
                inUserId = inUserId + "," + userIds[i];
            }
        }
        return getWorkerName(inUserId);
    }

    public static String getWorkerName(String inUserId) {
        String workerName = "";
        if (inUserId == null || inUserId.equals("")) {
            return "";
        }

        String actionId = "F00HrAllocate";
        String funId = "getUserName";

        TransactionData transData = new TransactionData();
        InputInfo inputInfo = transData.getInputInfo();
        //inputInfo.setControllerUrl(HandleServlet.getServerActionURL());
        inputInfo.setActionId(actionId);
        inputInfo.setFunId(funId);

        inputInfo.setInputObj("inUserId", inUserId);

        NetConnector connector = ConnectFactory.createConnector();
        connector.accessData(transData);

        ReturnInfo returnInfo = transData.getReturnInfo();
        if (returnInfo.isError() == false) {
            try {
                workerName = (String) returnInfo.getReturnObj("userName");
                return workerName;
            } catch (Exception e) {
                return "";
            }
        } else {
            return "";
        }
    }

    private void alloc(String[][] sOldUserIds, String accountId,String resId) {
        if( Global.applet == null || !(Global.applet instanceof IVWJavaScriptCaller) ){
            return;
        }

        String[][] oldData = sOldUserIds;
        java.lang.StringBuffer strBuf = new StringBuffer("new Array( ");
        for (int i = 0; i < oldData.length; i++) {
            strBuf.append("new Array('" + oldData[i][0] + "', '" + oldData[i][1] + "') ,");
        }
        strBuf.deleteCharAt(strBuf.length() - 1);
        strBuf.append(");");

        log.debug("accountId = " + accountId);
        log.debug( "res_id = " + resId );
        log.debug( "oldData = " + strBuf.toString() );
        //log.debug( "function name = 'HrAlloc();'" );
	log.debug( "function name = 'HrAlloc2005();'" );

        Object[] args = new Object[]{ accountId, resId, strBuf.toString() };
        String functionName = "HrAlloc2005";
        ( (IVWJavaScriptCaller)Global.applet ).performJS( this.jsListener, functionName, args );
    }

    /**
     * @param newData,对HRML言是一个数组或以逗号分开的字符串
     */
    public void hrAllocOK(String newDataStr) {
        log.debug("calling hrAllocOK( " + newDataStr + "):");
        String str = "";
        if (newDataStr == null || newDataStr.equals("") == true) {
            this.sponsor.setNewData(null);
        } else {
            newDataStr = newDataStr.trim();
            int i;
            for( i = 0; i < newDataStr.length(); i++ ){
                if (! (newDataStr.charAt(i) == ',' ||
                       newDataStr.charAt(i) == ' ')) {
                    break;
                }
            }
            newDataStr = newDataStr.substring( i, newDataStr.length() );

            String[] aUserIds = newDataStr.split(",");

            //这里newdatastr只含userid信息，所以还须求username信息
            String userNames = HrAllocate.getWorkerName( newDataStr );
            if(userNames!=null) {
                String[] aUserNames = userNames.split(",");
                String[][] aaNewData = new String[aUserIds.length][2];
                for (int j = 0; j < aUserIds.length; j++) {
                    aaNewData[j][0] = aUserIds[j];
                    aaNewData[j][1] = aUserNames[j];
                    str += aUserIds[j];
                }
                log.debug(str);
                this.sponsor.setNewData(aaNewData);
            } else {
                log.debug("userNames is null");
            }
        }

    }

}
