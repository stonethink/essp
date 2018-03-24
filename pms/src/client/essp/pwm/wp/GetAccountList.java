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
import c2s.essp.pwm.wp.DtoAccountInfo;

public class GetAccountList {
    static Category log = Category.getInstance("client");

    private List accountList;

    public GetAccountList() {
    }

   public Vector[] getAccountList() {
        Vector[] vaAccountList = new Vector[2];
        String actionId = "FPWGetAccountList";
        String funId = "";

        TransactionData transData = new TransactionData();
        InputInfo inputInfo = transData.getInputInfo();
        //inputInfo.setControllerUrl(HandleServlet.getServerActionURL());
        inputInfo.setActionId(actionId);
        inputInfo.setFunId(funId);

        NetConnector connector = ConnectFactory.createConnector();
        connector.accessData(transData);

        ReturnInfo returnInfo = transData.getReturnInfo();
        if (returnInfo.isError() == false) {
            try {
                accountList = (List) returnInfo.getReturnObj("accountList");

                int iSize = accountList.size();
                vaAccountList[0] = new Vector(iSize);
                vaAccountList[1] = new Vector(iSize);

                for (int i = 0; i < iSize; i++) {
                    DtoAccountInfo ai = (DtoAccountInfo) accountList.get(i);
                    DtoComboItem ciProjectIdList = new DtoComboItem(ai.getAccountCode(), ai.getId());
//                    DtoComboItem ciAccountNameList = new DtoComboItem(ai.getAccountName(), ai.getAccountName(), ai.getId());
                    DtoComboItem ciAccountNameList = new DtoComboItem(ai.getAccountName(), ai.getId(), ai.getId());
                    vaAccountList[0].add(ciProjectIdList);
                    vaAccountList[1].add(ciAccountNameList);
                }

                return vaAccountList;
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
//        return vaAccountList;
    }

    public String getAccountNameById(Long lId) {
        if( lId == null ){
            return null;
        }

        String outStr = null;
        Iterator iter = accountList.iterator();
        while (iter.hasNext()) {
            DtoAccountInfo item = (DtoAccountInfo) iter.next();
            if (item.getId() == null) {
                if (lId == null) {
                    outStr = item.getAccountName();
                }
            } else if (item.getId().equals(lId)) {
                outStr = item.getAccountName();
                break;
            }
        }
        return outStr;
    }

    public String getAccountTypeById(Long lId) {
        String outStr = null;
        Iterator iter = accountList.iterator();
        while (iter.hasNext()) {
            DtoAccountInfo item = (DtoAccountInfo) iter.next();
            if (item.getId() == null) {
                if (lId == null) {
                    outStr = item.getAccountTypeName();
                }
            } else if (item.getId().equals(lId)) {
                outStr = item.getAccountTypeName();
                break;
            }
        }
        return outStr;
    }

    public String getManagerById(Long lId) {
        String outStr = null;
        Iterator iter = accountList.iterator();
        while (iter.hasNext()) {
            DtoAccountInfo item = (DtoAccountInfo) iter.next();
            if (item.getId() == null) {
                if (lId == null) {
//                    outStr = item.getManager();
                    outStr = item.getManagerName();
                }
            } else if (item.getId().equals(lId)) {
            //                outStr = item.getManager();
                    outStr = item.getManagerName();
                break;
            }
        }
        return outStr;
    }

    public Long getProjectIdByName(String accountName) {
        if( accountName == null ){
            return null;
        }

       Long outLong = null;
       Iterator iter = accountList.iterator();
       while (iter.hasNext()) {
           DtoAccountInfo item = (DtoAccountInfo) iter.next();
           if (item.getAccountName() == null) {
               if (accountName == null) {
                   outLong = item.getId();
               }
           } else if (item.getAccountName().equals(accountName)) {
               outLong = item.getId();
               break;
           }
       }
       return outLong;
   }

   public String getAccountTypeByName(String accountName) {
      String out = null;
      Iterator iter = accountList.iterator();
      while (iter.hasNext()) {
          DtoAccountInfo item = (DtoAccountInfo) iter.next();
          if (item.getAccountName() == null) {
              if (accountName == null) {
                  out = item.getAccountTypeName();
              }
          } else if (item.getAccountName().equals(accountName)) {
              out = item.getAccountTypeName();
              break;
          }
      }
      return out;
  }

  public String getManagerByName(String accountName) {
     String out = null;
     Iterator iter = accountList.iterator();
     while (iter.hasNext()) {
         DtoAccountInfo item = (DtoAccountInfo) iter.next();
         if (item.getAccountName() == null) {
             if (accountName == null) {
                 out = item.getManagerName();
             }
         } else if (item.getAccountName().equals(accountName)) {
             out = item.getManagerName();
             break;
         }
     }
     return out;
 }

   public static void main(String[] args) {
        GetAccountList lgGetAccountList = new GetAccountList();
        TransactionData transData = new TransactionData();
        lgGetAccountList.getAccountList();
//        lgGetAccountList.getAccountList(transData);
    }

}
