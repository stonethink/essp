package server.essp.common.humanAllocate.action;

import server.essp.framework.action.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import java.util.List;
import itf.hr.HrFactory;
import java.util.HashMap;
import java.util.Map;
import c2s.essp.common.user.DtoUser;
import server.essp.common.ldap.LDAPUtil;

public class AcGetName extends AbstractESSPAction {
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
        List unNameIds = (List) data.getInputInfo().getInputObj("unNameIds");
        Map names = new HashMap();
        Map loginIds = new HashMap();
        if(unNameIds != null){
            for(int i = 0;i < unNameIds.size() ; i ++){
                String loginId = (String) unNameIds.get(i);
                LDAPUtil ldap = new LDAPUtil();
                DtoUser user = ldap.findUser("all", loginId);
                if(user != null) {
                    names.put(loginId, user.getUserName());
                    loginIds.put(loginId, user.getUserLoginId());
                } else {
                    names.put(loginId, "");
                    loginIds.put(loginId, loginId);
                }
            }
        }
        data.getReturnInfo().setReturnObj("userName",names);
        data.getReturnInfo().setReturnObj("userLoginId",loginIds);
    }
}
