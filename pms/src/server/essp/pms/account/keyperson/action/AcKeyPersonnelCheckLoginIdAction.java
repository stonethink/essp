package server.essp.pms.account.keyperson.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.DtoUtil;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.user.DtoUser;
import c2s.essp.pms.account.DtoAcntKeyPersonnel;
import itf.hr.HrFactory;
import itf.hr.IHrUtil;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;

public class AcKeyPersonnelCheckLoginIdAction extends AbstractESSPAction {
    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws
        BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        String loginId = (String) inputInfo.getInputObj("loginId");
        String type = (String) inputInfo.getInputObj("type");
        DtoUser user = null;
        IHrUtil hrUtil = HrFactory.create();
        //如果Keyperson类型是Customer，则查找是否存在该Customer
        if(DtoAcntKeyPersonnel.KEY_PERSON_TYPES[0].equals(type)){
            user = hrUtil.findCustomer(loginId);
        }else{
            user = hrUtil.findResouce(loginId);
        }
        if(user != null){
            DtoAcntKeyPersonnel keyPersonnel = new DtoAcntKeyPersonnel();
            try {
                DtoUtil.copyBeanToBean(keyPersonnel, user);
                keyPersonnel.setLoginId(loginId);
            } catch (Exception ex) {
                throw new BusinessException("","copy properties error while find user:[" + loginId + "]");
            }
            returnInfo.setReturnObj("keyPersonnel",keyPersonnel);
        }
    }
}
