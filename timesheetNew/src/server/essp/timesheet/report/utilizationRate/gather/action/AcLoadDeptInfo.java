package server.essp.timesheet.report.utilizationRate.gather.action;

import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import c2s.dto.DtoComboItem;

import java.util.List;
import java.util.Vector;

import c2s.essp.common.user.DtoRole;
import c2s.essp.timesheet.report.DtoUtilRateKey;
import server.essp.timesheet.report.utilizationRate.gather.service.IUtilRateGatherService;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public class AcLoadDeptInfo extends AbstractESSPAction {
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
                   if(roleIdTmp.equals(DtoRole.ROLE_SYSUSER)
                    ||roleIdTmp.equals(DtoRole.ROLE_HAP)
                    || "SiteManager".equals(roleIdTmp)){
                	   roleId = DtoRole.ROLE_HAP;
                       break;
                   }
                   if(roleIdTmp.equals(DtoRole.ROLE_UAP) ) {
            		   roleId = DtoRole.ROLE_UAP;
            	   }
               }
           }
        IUtilRateGatherService lg = (IUtilRateGatherService) this.getBean("iUtilRateGatherService");
        String  loginId = this.getUser().getUserLoginId();
        Vector deptList = (Vector) lg.getDeptList(loginId, roleId);
        transactionData.getReturnInfo().setReturnObj(DtoUtilRateKey.DTO_DEPT_LIST,deptList);
    }
}
