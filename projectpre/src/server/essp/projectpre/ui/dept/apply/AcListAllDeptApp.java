package server.essp.projectpre.ui.dept.apply;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.AcntApp;
import server.essp.projectpre.db.AcntPersonApp;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;
import c2s.essp.common.user.DtoUser;



import com.wits.util.comDate;

public class AcListAllDeptApp extends AbstractESSPAction {
   
    private String applicationType = server.essp.projectpre.service.constant.Constant.DEPARTMENTADDAPP;
    private String applicationStatus = server.essp.projectpre.service.constant.Constant.CONFIRMED;
    /**
     * �г�������������Ϊ������������,����״̬Ϊ��ȷ�ϵĲ������������¼
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
        DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
        String userLoginId = user.getUserLoginId();
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        List deptApplicationList = logic.listAll(userLoginId, applicationType, applicationStatus);
        List viewBean = new ArrayList();
        Long rid = null;
        String IDFORMATER = "00000000";
        String newRid = null;
        DecimalFormat df = new DecimalFormat(IDFORMATER);
        for(int i=0;i<deptApplicationList.size();i++){
            AcntApp acntApp = (AcntApp) deptApplicationList.get(i);
            rid = acntApp.getRid();
            AcntPersonApp acntPersonApp = logic.loadByRidFromPersonApp(rid, "DeptManager");
            newRid = df.format(acntApp.getRid(), new StringBuffer(),
                    new FieldPosition(0)).toString();
            VbDeptAppList vBDeptAppList = new VbDeptAppList();
            vBDeptAppList.setRid(newRid);
            vBDeptAppList.setAcntName(acntApp.getAcntName().trim());
            vBDeptAppList.setDeptManager(acntPersonApp.getName().trim());      
            vBDeptAppList.setApplicationDate(comDate.dateToString(acntApp.getApplicationDate(),"yyyy-MM-dd"));
            vBDeptAppList.setApplicationStatus(acntApp.getApplicationStatus());
            viewBean.add(vBDeptAppList);
        }
        request.setAttribute(Constant.VIEW_BEAN_KEY, viewBean);
    }

}
