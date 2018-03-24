package server.essp.projectpre.ui.project.edit;

import java.util.Date;

import itf.webservices.IAccountWService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.*;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.constant.Constant;
import server.essp.projectpre.service.log.ILogService;
import server.essp.projectpre.service.syncException.ISyncExceptionService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.common.user.DtoUser;

public class AcReopenProject extends AbstractESSPAction {
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		String acntId=null;
		String status = "";
		DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
		String userName = user.getUserName();
		if(request.getParameter("acntId")!=null){
			acntId = (String)request.getParameter("acntId");
        }
		if(request.getParameter("status") != null){
			status = (String)request.getParameter("status");
		}
		IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
		Acnt acnt = acntLogic.loadByAcntId(acntId, "1");
		Long rid = acnt.getRid();
		request.setAttribute("rid", rid);
		request.setAttribute("opType", "save");
		acnt.setAcntStatus(status);
		acntLogic.update(acnt);
		//数据同步（财务_新增, TimeSheet）
        IAccountWService finLogic = (IAccountWService)this.getBean("FinAccountService");
        IAccountWService tsLogic = (IAccountWService)this.getBean("TSAccountService");
        
        try{
      //      finLogic.reopenOrCloseAccount(acntId, status);
          }catch (BusinessException e){
              //如果同步失败则向异常同步信息记录表中插入数据
             String errMessge = e.getErrorCode();           
             if(errMessge.equals("Reopen Finance ERROR")||errMessge.
                         equals("Reopen Finance Rollback ERROR")){        
               updateSyncException(acnt,"FinAccountWServiceImpl",errMessge,user.getUserLoginId());
               request.setAttribute("Message","Carry forward fail!");
             }  
          }
          try{
        	  if(!"V".equalsIgnoreCase(acnt.getExecSite())) {
        //		  tsLogic.reopenOrCloseAccount(acntId, status);
        	  }
          }catch(BusinessException e){
              String errMessge = e.getErrorCode();    
              updateSyncException(acnt,"SyncAccountImp",errMessge,user.getUserLoginId());
              request.setAttribute("Message","Carry forward fail!");  
          }
          ILogService logicLog=(ILogService)this.getBean("LogLogic");
          PPLog log = new PPLog();
          log.setAcntId(acnt.getAcntId());
          log.setApplicationType(Constant.PROJECTEDIT);
          log.setDataType(Constant.LOG_PROJECT);
          log.setMailReceiver("");
          if("N".equals(acnt.getAcntStatus())) {
        	  log.setOperation("Reopen");
          } else {
        	  log.setOperation("Close");
          }
          log.setOperationDate(new Date());
          log.setOperator(userName);
          logicLog.save(log);
	}
	/**
     * 截转失败时向同步异常信息表中插入一笔数据
     * @param acnt
     * @param impClassName
     * @param errorMessge
     * @param loginId
     */
    private void updateSyncException(Acnt acnt,String impClassName,
            String errorMessge,String loginId){
        ISyncExceptionService syncLogic = (ISyncExceptionService) this.getBean("SyncExceptionLogic");
        PpSyncException syncExcption = new PpSyncException();
        syncExcption.setAcntRid(acnt.getRid());
        syncExcption.setAcntId(acnt.getAcntId());
        syncExcption.setAcntName(acnt.getAcntName());
        syncExcption.setImpClassName(impClassName);
        if(impClassName.equals("FinAccountWServiceImpl")){
            syncExcption.setModel("Finance");
        } else if("SyncAccountImp".equals(impClassName)){
            syncExcption.setModel("TimeSheet");
        } else if("SyncToHrms".equals(impClassName)) {
        	syncExcption.setModel("Hrms");
        }
        syncExcption.setStatus(Constant.ACTIVE);
        syncExcption.setErrorMessage(errorMessge);
        if("N".equals(acnt.getAcntStatus())) {
        	syncExcption.setType("Reopen");
        } else {
        	syncExcption.setType("DClose");
        }
        
        syncExcption.setRcu(loginId);
        syncExcption.setRut(new Date());
        syncLogic.save(syncExcption);
    }

}
