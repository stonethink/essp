package server.essp.projectpre.ui.dept.check;

import java.text.DecimalFormat;
import java.text.FieldPosition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.db.AcntApp;
import server.essp.projectpre.db.AcntPersonApp;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.service.bd.IBdService;
import server.essp.projectpre.ui.dept.apply.AfDeptApp;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.DtoUtil;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;

import com.wits.util.comDate;

public class AcInitialAddApp extends AbstractESSPAction {
    private final static String rid="rid";

    /**
     * ��ʼ��������������ҳ��
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
            Long code=null;
            if (request.getParameter(rid) != null) {
                code =Long.valueOf(request.getParameter(rid)) ;
            }       
   
            String IDFORMATER = "00000000";
            AcntApp acntApp=new AcntApp();
            Acnt acnt = null;
            AfDeptApp af = new AfDeptApp();
            IAccountApplicationService logic = (IAccountApplicationService) this
                    .getBean("AccountApplicationLogic");       
            IAccountService logicAcnt = (IAccountService) this.getBean("AccountLogic");
            acntApp=logic.loadByRid(code);        
            try {
                DtoUtil.copyProperties(af, acntApp);
            } catch (Exception e) {            
                e.printStackTrace();
            }
            if(acntApp.getRelParentId() != null && !"".equals(acntApp.getRelParentId())) {
            	acnt = logicAcnt.loadByAcntId(acntApp.getRelParentId(), "0");
            	af.setParentDept(acntApp.getRelParentId()+"---"+acnt.getAcntName());
            }
            IBdService logicBD = (IBdService) this.getBean("BdCodeLogic");
            Bd bd = logicBD.loadByBdCode(acntApp.getAchieveBelong());
            if(bd!=null){
              af.setAchieveBelong(bd.getBdName());
            }
            af.setRemark("");
       // ��������Ա,�������ž�����ʱ��ǩ���ߡ�BD���ܡ�������             
            af = getPerson(af, logic,code);
      //��ʽ�����뵥��
            DecimalFormat df = new DecimalFormat(IDFORMATER);
            String newRid = df.format(acntApp.getRid(), new StringBuffer(),
                                    new FieldPosition(0)).toString();
            af.setRid(newRid);
      //��ʽ������
            af.setApplicationDate(comDate.dateToString(acntApp.getApplicationDate(),"yyyy-MM-dd"));
            request.setAttribute(Constant.VIEW_BEAN_KEY,af);
    }
    /**
     * ��ȡ�벿����ص���Ա����
     * @param af
     * @param logic
     * @param rid
     * @return ����Ա������af
     */
    private AfDeptApp getPerson(AfDeptApp af, IAccountApplicationService  logic, Long rid) {
//      ��������Ա,�������ž�����ʱ��ǩ���ߡ�BD���ܡ�������
        AcntPersonApp acntPersonApp = new AcntPersonApp();
        acntPersonApp = logic.loadByRidFromPersonApp(rid, "DeptManager");
        af.setDeptManager(acntPersonApp.getName());
        af.setDMLoginId(acntPersonApp.getLoginId());

        acntPersonApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_TC_SIGNER);      
        af.setTCSName(acntPersonApp.getName());
        af.setTCSLoginId(acntPersonApp.getLoginId());
      
        acntPersonApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_BD_MANAGER);
        af.setBDMName(acntPersonApp.getName());
        af.setBDLoginId(acntPersonApp.getLoginId());
    
        return af;
    }
}



