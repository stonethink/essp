package server.essp.timesheet.report.utilizationRate.detail.action;

import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.report.utilizationRate.detail.service.IUtilRateService;
import c2s.essp.common.user.DtoRole;
import c2s.essp.timesheet.report.DtoUtilRateKey;

import java.util.List;
import java.util.Vector;
import c2s.dto.DtoComboItem;

/**
 * <p>Title: </p>
 *
 * <p>Description:得到員工和部門集合 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public class AcLoadUsersAndDept extends AbstractESSPAction {
    public void executeAct(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           TransactionData transactionData) throws
            BusinessException {
    	List roleList = (List)this.getUser().getRoles();
        String roleIdTmp = "";
        String roleId = "";
           for(int i=0;i<roleList.size();i++){
               DtoRole role = (DtoRole)roleList.get(i);
               if(role!=null){
            	   roleIdTmp = role.getRoleId();
            	   if(roleIdTmp.equals(DtoRole.ROLE_UAP)) {
            		   roleId = roleIdTmp;
            	   }
                   if(roleIdTmp.equals(DtoRole.ROLE_SYSUSER)
                    ||roleIdTmp.equals(DtoRole.ROLE_HAP)){
                	   roleId = roleIdTmp;
                       break;
                   }
               }
           }
        IUtilRateService lg = (IUtilRateService) this.getBean("iUtilRateService");
        String  loginId = this.getUser().getUserLoginId();
        Vector deptList = lg.getDeptList(loginId, roleId);
        transactionData.getReturnInfo().setReturnObj(DtoUtilRateKey.DTO_DEPT_LIST,deptList);
        if(deptList != null && deptList.size()>0){
          DtoComboItem tsAcnt = (DtoComboItem) deptList.get(0);
          String acntId = (String) tsAcnt.getItemValue();
          transactionData.getReturnInfo().setReturnObj(DtoUtilRateKey.
                    DTO_USER_LIST, lg.getUserList(acntId));
        }
    }
}
