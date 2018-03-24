package server.essp.projectpre.ui.project.apply;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.AcntApp;
import server.essp.projectpre.db.AcntPersonApp;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.service.bd.IBdService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.DtoUser;

import com.wits.util.comDate;

/**
 * 列出所有申请的Action
 * @author wenhaizheng
 *
 * 
 */
public class AcListAllProjectAcntApp extends AbstractESSPAction {
    
    private String applicationStatus = server.essp.projectpre.service.constant.Constant.CONFIRMED;
    /**
     * 根据进入的不同页面列出相应的专案申请并将结果放到view bean中
     * @param request
     * @param response
     * @param data
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
        String applicationType = server.essp.projectpre.service.constant.Constant.PROJECTADDAPP;
        String appType = request.getParameter("appType");
        DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
      
        String userLoginId = user.getUserLoginId();
     
        //判断是否为变更申请功能
        if(appType.equals("change")) {
            applicationType = server.essp.projectpre.service.constant.Constant.PROJECTCHANGEAPP;
        }
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        IBdService bdLogic = (IBdService) this.getBean("BdCodeLogic");
        List accountApplicationList = logic.listAll(userLoginId, applicationType, applicationStatus);
        List viewBean = new ArrayList();
        Long rid = null;
        String name = null;
        String bdCode = null;
        String IDFORMATER = "00000000";
        String newRid = null;
        DecimalFormat df = new DecimalFormat(IDFORMATER);
        for(int i=0;i<accountApplicationList.size();i++){
            AcntApp acntApp = (AcntApp) accountApplicationList.get(i);
            rid = acntApp.getRid();
            AcntPersonApp acntPersonApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_PM);
            newRid = df.format(acntApp.getRid(), new StringBuffer(),
                    new FieldPosition(0)).toString();
            if(acntPersonApp!=null){
                name = acntPersonApp.getName();
            } else {
                name="";
            }
            VbAcntAppList vBAcntAppList = new VbAcntAppList();
            vBAcntAppList.setRid(newRid);
            vBAcntAppList.setAcntName(acntApp.getAcntName());
            vBAcntAppList.setPMName(name);
            if(acntApp.getAcntId()!=null) {
            vBAcntAppList.setAcntId(acntApp.getAcntId());
            }
            Bd bd = bdLogic.loadByBdCode(acntApp.getAchieveBelong());
            if(bd!=null) {
            bdCode = bd.getBdCode();
            } else {
                bdCode = "";
            }
            vBAcntAppList.setAchieveBelong(bdCode);         
            vBAcntAppList.setApplicationDate(comDate.dateToString(acntApp.getApplicationDate(),"yyyy-MM-dd"));
            vBAcntAppList.setApplicationStatus(acntApp.getApplicationStatus());
            viewBean.add(vBAcntAppList);
        }
        request.setAttribute(Constant.VIEW_BEAN_KEY, viewBean);
        if(appType.equals("change")) {
            data.getReturnInfo().setForwardID(appType);
        } 
            
       
            
        
    }

}
