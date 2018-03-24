package server.essp.common.hrallocate;

import org.apache.log4j.Category;
import server.framework.hibernate.HBComAccess;
import c2s.dto.TransactionData;
import c2s.dto.ReturnInfo;
import server.framework.common.BusinessException;
import com.wits.util.StringUtil;
import essp.tables.EsspHrEmployeeMainT;

public class LGHrAllocate {
    static Category log = Category.getInstance("server");

    public LGHrAllocate() {
    }

    HBComAccess hbAccess = new HBComAccess();

    public void getUserName(TransactionData transData) {
        try {
            hbAccess.followTx();
            String userName = "";

            String inUserId = (String) transData.getInputInfo().getInputObj("inUserId");
            log.debug("userId="+ inUserId);
            if (inUserId == null || inUserId.equals("")) {
                throw new BusinessException("E0000000", "Input user is is null!");
            } else {
                userName = getUserName(inUserId);
            }

            if (userName == null || userName.equals("")) {
                throw new BusinessException("E0000000", "Didn't find the user name by: " + inUserId);
            }

            ReturnInfo returnInfo = transData.getReturnInfo();
            returnInfo.setReturnObj("userName", userName);
            log.debug("userName="+ userName);

            hbAccess.endTxCommit();
            returnInfo.setError(false);
        } catch (Exception e) {
            try {
                hbAccess.endTxRollback();
            } catch (Exception ee) {
                log.error("find the user name error!!");
            }
            transData.getReturnInfo().setError(e);
        }
    }

    public String getUserName(String inUserId) throws Exception {
        String userName = "";

        String[] saUserIds = StringUtil.splitS(inUserId, ",");
        if (saUserIds == null) {
            return "";
        }

        for (int i = 0; i < saUserIds.length; i++) {
            if (saUserIds[i] != null && !saUserIds[i].equals("")) {
                String oneUserName = getOneUserName(saUserIds[i]);
                if (oneUserName == null || oneUserName.equals("")){
                    continue;
                }
                if(userName.equals("")){
                    userName = oneUserName;
                }else{
                    userName = userName + "," + oneUserName;
                }
            }
        }

        return userName;
    }

    public String getOneUserName(String inUserId) throws BusinessException {
        String userName = "";
        try {
            EsspHrEmployeeMainT esspHrEmployeeMainT = new EsspHrEmployeeMainT();
            esspHrEmployeeMainT = (EsspHrEmployeeMainT) hbAccess.load(EsspHrEmployeeMainT.class, inUserId);
            userName = esspHrEmployeeMainT.getName();
        } catch (Exception ex) {
//            ex.printStackTrace();
            throw new BusinessException( "E000000", "Not find the user - " + inUserId + "." );
        }

        return userName;
    }

    public static void main(String[] args) {
        LGHrAllocate lg = new LGHrAllocate();
        TransactionData transData = new TransactionData();
        String inUserId = ",123,10,";
        transData.getInputInfo().setInputObj("inUserId",inUserId);
        lg.getUserName(transData);
    }

}
