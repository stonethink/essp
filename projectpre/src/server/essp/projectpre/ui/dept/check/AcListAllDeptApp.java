package server.essp.projectpre.ui.dept.check;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.AcntApp;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;

import com.wits.util.comDate;

public class AcListAllDeptApp extends AbstractESSPAction {

    /**
     * 列出所有已经提交的部门新增申请和部门变更申请
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {      
        // 通过此方法以接口的形式得到一个服务的实例
    
        IAccountApplicationService  logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");       
        List codeList = logic.listAllProjectApp("0");
        List viewBean = new ArrayList();
        String IDFORMATER = "00000000";
        String newRid = null;
        Long rid = null;
        DecimalFormat df = new DecimalFormat(IDFORMATER);
        for(int i=0;i<codeList.size();i++){         
            AcntApp acntApp = (AcntApp) codeList.get(i);    
            rid = acntApp.getRid();     
            VbDeptListApp vbDeptListApp = new VbDeptListApp();          
            newRid = df.format(rid, new StringBuffer(),
                    new FieldPosition(0)).toString();
            vbDeptListApp.setRid(newRid);
            vbDeptListApp.setApplicantName(acntApp.getApplicantName());
            vbDeptListApp.setApplicationType(acntApp.getApplicationType());
            vbDeptListApp.setAcntName(acntApp.getAcntName());       
            vbDeptListApp.setApplicationDate(comDate.dateToString(acntApp.getApplicationDate(),"yyyy-MM-dd"));
 
            viewBean.add(vbDeptListApp);           
        }
        request.setAttribute(Constant.VIEW_BEAN_KEY, viewBean);
    }
}
