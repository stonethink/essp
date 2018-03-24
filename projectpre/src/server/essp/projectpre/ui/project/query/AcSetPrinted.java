
package server.essp.projectpre.ui.project.query;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.service.account.IAccountService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
/**
 * 将列印过的专案设置为已经列印
 * @author wenhaizheng
 *
 */
public class AcSetPrinted extends AbstractESSPAction{
	/**
	 * 将列印过的专案设置为已经列印
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
    public void executeAct(HttpServletRequest request, 
                           HttpServletResponse response, TransactionData data) 
                           throws BusinessException {
       try{
        String acntId = null;
        if(request.getParameter("acntId")!=null){
             acntId = request.getParameter("acntId");
        }
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        Acnt acnt = acntLogic.loadByAcntId(acntId, "1");
        if(acnt!=null){
            acnt.setIsPrinted("1");
        }
        acntLogic.update(acnt);
       }catch (Exception e){
           e.printStackTrace();
           throw new BusinessException(e);
       }
        
    }

    

}
