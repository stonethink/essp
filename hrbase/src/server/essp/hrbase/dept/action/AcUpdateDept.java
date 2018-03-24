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
import server.essp.hrbase.dept.form.AfDeptQuery;
import server.essp.hrbase.dept.service.IDeptService;
import server.essp.hrbase.synchronization.BatchSynchronise;
import server.essp.hrbase.synchronization.service.ISyncMainService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.common.user.DtoUser;
import db.essp.hrbase.HrbUnitBase;

/**
 * 修改部門信息
 * @author TBH
 */
public class AcUpdateDept extends AbstractESSPAction{

    public void executeAct(HttpServletRequest request,
            HttpServletResponse response,
            TransactionData transData) throws BusinessException {
//      从Session中得到当前登陆者姓名和LoginId
        DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
        String userLoginId = user.getUserLoginId();
        String userName=user.getUserName();
        final AfDeptQuery af = (AfDeptQuery)this.getForm();
        af.setApplicantId(userLoginId);
        af.setApplicantName(userName);
        final IDeptService lg = (IDeptService)this.getBean("deptService");
        lg.updateDept(af);
        final ISyncMainService syncMainService = (ISyncMainService) this.getBean("syncMainService");
        Thread t = new Thread(new Runnable() {
            public void run() {
            	HrbUnitBase unitBase = lg.getByUnitCode(af.getUnitCode());
                boolean isError = syncMainService.updateUnit(unitBase);
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