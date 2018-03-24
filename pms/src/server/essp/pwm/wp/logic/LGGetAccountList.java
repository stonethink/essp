package server.essp.pwm.wp.logic;

import org.apache.log4j.Category;
import server.framework.hibernate.HBComAccess;
import c2s.dto.TransactionData;
import java.util.List;
import c2s.dto.ReturnInfo;
import server.framework.common.BusinessException;
import itf.account.AccountFactory;
import c2s.essp.common.account.IDtoAccount;
import c2s.dto.DtoUtil;
import java.util.ArrayList;
import c2s.essp.pwm.wp.DtoAccountInfo;

public class LGGetAccountList {
    static Category log = Category.getInstance("server");

    public LGGetAccountList() {
    }

    HBComAccess hbAccess = new HBComAccess();

    public void getAccountList(TransactionData transData) {
        try {
            hbAccess.followTx();
            List accountList = new ArrayList();

//            String inUserId = (String) transData.getInputInfo().getInputObj("inUserId");
//            if (inUserId == null || inUserId.equals("")) {
                accountList = getAccountList();
//            } else {
//                accountList = getAccountList(inUserId);
//            }

            if (accountList == null || accountList.size() == 0 ) {
                throw new BusinessException("E0000000", "Didn't find the Account list!");
            }

            ReturnInfo returnInfo = transData.getReturnInfo();
            returnInfo.setReturnObj("accountList", accountList);

            hbAccess.endTxCommit();
            returnInfo.setError(false);
        } catch (Exception e) {
            try {
                hbAccess.endTxRollback();
            } catch (Exception ee) {
                log.error("find the Account list error!!",ee);
            }
            transData.getReturnInfo().setError(e);
            log.debug(e);
        }
    }

    public List getAccountList() throws Exception {
        List accountList = new ArrayList();

        List tmpAccountList = AccountFactory.create().listAllAccounts();
        for (int i = 0; i < tmpAccountList.size(); i++) {
            IDtoAccount tmpDto = (IDtoAccount) tmpAccountList.get(i);
            DtoAccountInfo dto = new DtoAccountInfo();
            //DtoUtil.copyBeanToBean(dto, tmpDto);
            copyAccount(dto, tmpDto);

            accountList.add(dto);
        }

        return accountList;
    }

    /*
         FPW01010.L02 = select ID,ACCOUNT_CODE,ACCOUNT_NAME,ACCOUNT_TYPE,MANAGER
     from ESSP_SYS_ACCOUNT_PERSONAL_T join ESSP_SYS_ACCOUNT_T
     on ESSP_SYS_ACCOUNT_PERSONAL_T.ACCOUNT_ID = ESSP_SYS_ACCOUNT_T.ID
     WHERE ESSP_SYS_ACCOUNT_PERSONAL_T.HR_CODE = ?HR_CODE and ESSP_SYS_ACCOUNT_T.STATUS!='Closed' order by ACCOUNT_CODE
     */
    public List getAccountList(String inUserId) throws Exception {
        List accountList = new ArrayList();

        List tmpAccountList = AccountFactory.create().listAllAccountsByLoginID(inUserId);
        for (int i = 0; i < tmpAccountList.size(); i++) {
            IDtoAccount tmpDto = (IDtoAccount) tmpAccountList.get(i);
            DtoAccountInfo dto = new DtoAccountInfo();
            //DtoUtil.copyBeanToBean(dto, tmpDto);
            copyAccount(dto, tmpDto);
            accountList.add(dto);
        }

        return accountList;
    }

    private void copyAccount(DtoAccountInfo dest, IDtoAccount orig) {
        dest.setAccountCode(orig.getId());
        dest.setAccountName(orig.getName());
//        dest.setAccountType(orig.getType()); //由于显示的是type name, 所以这里不设置也没有关系
        dest.setAccountTypeName(orig.getType());
        dest.setId(orig.getRid());
        dest.setManager(orig.getManager());
        dest.setManagerName(orig.getManager()); //暂用manger的login id代替她的名字
    }

    public static void main(String[] args) {
        LGGetAccountList lgGetAccountList = new LGGetAccountList();
        TransactionData transData = new TransactionData();
        lgGetAccountList.getAccountList(transData);
        List acountList = (List) transData.getReturnInfo().getReturnObj("accountList");
        log.debug("accout count=" + acountList.size());
    }

}
