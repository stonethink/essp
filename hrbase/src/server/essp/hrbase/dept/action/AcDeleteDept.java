/*
 * Created on 2007-12-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.dept.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.dept.service.IDeptService;
import server.essp.hrbase.synchronization.BatchSynchronise;
import server.essp.hrbase.synchronization.service.ISyncMainService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import db.essp.hrbase.HrbUnitBase;

/**
 * „h³ý²¿éT
 * @author TBH
 */
public class AcDeleteDept  extends AbstractESSPAction{

    public void executeAct(HttpServletRequest request,
            HttpServletResponse response,
            TransactionData transData) throws BusinessException {
    	final String unitCode = (String)request.getParameter("deptCode");
        if(unitCode != null){
        	final IDeptService lg = (IDeptService)this.getBean("deptService");
            lg.deleteDept(unitCode);
            final ISyncMainService syncMainService = (ISyncMainService) this.getBean("syncMainService");
            Thread t = new Thread(new Runnable() {
                public void run() {
                	HrbUnitBase unitBase = lg.getByUnitCode(unitCode);
                    boolean isError = syncMainService.deleteUnit(unitBase);
                    if(isError) {
                    	BatchSynchronise.sendMail(BatchSynchronise.vmFile1, 
                    			"Synchronise error", BatchSynchronise.getMailMap());
                    }
                }
            });
            try {
                t.start();
            } catch (Throwable tx) {
                tx.printStackTrace();
                t.stop();
            }
        }
    }
}