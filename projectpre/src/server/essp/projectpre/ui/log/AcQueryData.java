package server.essp.projectpre.ui.log;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import com.wits.util.comDate;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.PPLog;
import server.essp.projectpre.service.log.ILogService;
import server.framework.common.BusinessException;

public class AcQueryData extends AbstractESSPAction{
	/**  
	 *根据查询条件查询出符合条件的结案记录
	*/
	    public void executeAct(HttpServletRequest request,
	            HttpServletResponse response, TransactionData data)
	            throws BusinessException {       
	    	ILogService logic = (ILogService)this.getBean("LogLogic");    	
	    	AfLog af = (AfLog)this.getForm();
	        List logList = logic.query(af);                  
	        List viewBeanList = new ArrayList();        
	         for(int i=0;i<logList.size();i++) {
	            VbLog vb = new VbLog();       	
	            PPLog log = (PPLog)logList.get(i);
	            vb.setApplicationType(log.getApplicationType());
	            vb.setAcntId(log.getAcntId());
	            vb.setDataType(log.getDataType());
	            vb.setMailSender(log.getMailReceiver());
	            vb.setOperation(log.getOperation());
	            vb.setOperator(log.getOperator());
	            if(log.getOperationDate()!=null&&!log.getOperationDate().equals("")){
	              String operationDate=comDate.dateToString(log.getOperationDate(),"yyyy-MM-dd HH:mm:ss");
	              vb.setOperationDate(operationDate);
	            }
	            viewBeanList.add(vb);
	          }
	            request.setAttribute(server.framework.common.Constant.
	                    VIEW_BEAN_KEY, viewBeanList);  
	    }
	    }           
	          

