package server.essp.pms.account.action;

import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import java.util.Vector;
import server.essp.common.syscode.LgSysParameter;
import java.util.ArrayList;
import java.util.List;
import c2s.dto.DtoComboItem;
import server.essp.pms.account.logic.LgAccountList;
import c2s.essp.pms.account.DtoPmsAcnt;
import c2s.essp.pms.account.DtoAcntKEY;

public class AcTemplateCreateMethod extends AbstractESSPAction{

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        InputInfo inputInfo = data.getInputInfo();
        ReturnInfo returnInfo = data.getReturnInfo();
        DtoPmsAcnt dto = (DtoPmsAcnt) inputInfo.getInputObj(DtoAcntKEY.DTO);
        String accountType = (String)dto.getType();

        LgSysParameter lgSysParameter = new LgSysParameter();
        Vector accountTypeList = lgSysParameter.listComboSysParas(AcAcntInit.kindAccountType);

        List typeSearchList = new ArrayList();
        if(accountType == null || "".equals(accountType)){
            typeSearchList = getValueList(accountTypeList);
        }else{
            typeSearchList.add(accountType);
        }
        LgAccountList lgAccountlist = new LgAccountList();
        List stateList = new ArrayList();
        stateList.add("Approved");
        List Templatelist = (List)lgAccountlist.listTemplate(stateList,typeSearchList);
        if(Templatelist==null){

        }
        List Accountlist = (List)lgAccountlist.ListbyType(typeSearchList);
        DtoPmsAcnt dtoOsp = (DtoPmsAcnt)lgAccountlist.getApprovedOSP();
        List OspList = new ArrayList();
        if(dtoOsp != null) {
            OspList.add(dtoOsp);
        }
        Vector Account = (Vector)getList(Accountlist);
        Vector Template = (Vector)getList(Templatelist);
        Vector Osp = (Vector)getList(OspList);
        returnInfo.setReturnObj("Account",Account);
        returnInfo.setReturnObj("template",Template);
        returnInfo.setReturnObj("OSP",Osp);

    }
    public Vector getList(List list){
        Vector vector = new Vector();
        for(int i=0;i<list.size();i++){
            DtoPmsAcnt dtopms = (DtoPmsAcnt)list.get(i);
            DtoComboItem dtoitem = new DtoComboItem(dtopms.getDisplayName(),dtopms.getRid().toString());
            vector.add(dtoitem);
        }
        return vector;
    }
    private List getValueList(Vector comItemList) {
            List tempList = new ArrayList();
            for (int i = 0; i < comItemList.size(); i++) {
                DtoComboItem item = (DtoComboItem) comItemList.get(i);
                String value = (String) item.getItemValue();
                if (value != null)
                    tempList.add(value);
            }
            return tempList;
    }
}
