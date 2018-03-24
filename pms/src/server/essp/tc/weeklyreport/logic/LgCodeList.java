package server.essp.tc.weeklyreport.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.code.DtoKey;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import db.essp.code.Code;
import db.essp.code.CodeValue;
import itf.account.AccountFactory;
import itf.account.IAccountUtil;
import itf.seq.AcntSeqFactory;
import itf.seq.IAcntSeqUtil;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import server.essp.pms.account.code.ActivityCodeConfig;

public class LgCodeList extends AbstractESSPLogic {

    public List[] list(Long acntRid) {
        List codeValueRidList = new ArrayList();
        List codeValueNameList = new ArrayList();
        List codeValueDescriptionList = new ArrayList();

        //Long codeRid = new Long(1);//for test

        try {
            if( acntRid != null ){
                Long codeRid = getCode(acntRid, ActivityCodeConfig.getActivityCodeName(), DtoKey.TYPE_ACTIVITY);
                if (codeRid != null) {
                    IAcntSeqUtil util = AcntSeqFactory.create();
                    Long rootRid = util.getRootRid(codeRid, CodeValue.class);

                    //getDbAccessor().newTx();
                    if (rootRid != null) {

                        //get root
                        CodeValue codeValue = (CodeValue) getDbAccessor().load(CodeValue.class,
                                                                               rootRid);
                        if (codeValue != null) {

                            getListFromTree(codeValue, codeValueRidList, codeValueNameList, codeValueDescriptionList);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new BusinessException("E000", "Error when get code list.", ex);
        }

        return new List[] {codeValueRidList, codeValueNameList, codeValueDescriptionList};
    }

    private Long getCode(Long acntRid, String codeName, String codeType) throws Exception{
        IAccountUtil acntUtil = AccountFactory.create();
        IDtoAccount acnt = acntUtil.getAccountByRID(acntRid);
        String catalog = null;
        if (acnt == null) {
            return null;
        } else {
            catalog = acnt.getType();
        }

        String sql = " from Code t where t.catalog =:catalog and t.name =:codeName and t.type =:codeType";
        Iterator it = getDbAccessor().getSession().createQuery(sql)
                      .setString("catalog", catalog)
                      .setString("codeName", codeName)
                      .setString("codeType", codeType)
                      .iterate();
        if (it.hasNext()) {
            Code code = (Code) it.next();
            return code.getRid();
        } else {
            return null;
        }
    }

    private void getListFromTree(CodeValue codeValue, List ridList, List nameList, List descriptionList) {
        if (codeValue == null) {
            return;
        }


        ridList.add(codeValue.getRid());
        //nameList.add(codeValue.getCodeName());
        nameList.add(codeValue.getPath());
        descriptionList.add(codeValue.getDescription());

        for (Iterator iter = codeValue.getChilds().iterator(); iter.hasNext(); ) {
            CodeValue child = (CodeValue) iter.next();

            getListFromTree(child, ridList, nameList, descriptionList);
        }
    }

    public static void main(String args[]) {
        HBComAccess hBComAccess = new HBComAccess();
        LgCodeList logic = new LgCodeList();
        logic.setDbAccessor(hBComAccess);
        List[] array = logic.list(null);
        System.out.println(array[0].size());
        System.out.println(array[1].size());
    }
}
