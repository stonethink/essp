package server.essp.projectpre.service.log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.wits.util.comDate;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import server.essp.projectpre.db.PPLog;
import server.essp.projectpre.ui.log.AfLog;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;

public class LogServiceImpl extends AbstractBusinessLogic implements ILogService{

	/**
	 * 获取Log表中所有的记录
	 */
	public List query(AfLog af) throws BusinessException {
        List logList = new ArrayList();
        try {
        	StringBuffer buf = new StringBuffer();
        	Date _beginDate=null;
        	Date _endDate=null;
        	boolean existStartDate=false;
        	boolean existEndDate=false;
        	buf.append("from PPLog as l where 1=1");
        	if(af.getAcntId()!=null&&!af.getAcntId().equals("")){
        		buf.append(" and lower(l.acntId) like lower('%"+af.getAcntId()+"%')");
        	}
        	if(af.getApplicationType()!=null&&!af.getApplicationType().equals("")){
        		buf.append(" and l.applicationType='"+af.getApplicationType()+"'");
        	}
        	if(af.getDataType()!=null&&!af.getDataType().equals("")){
        	    buf.append(" and l.dataType='"+af.getDataType()+"'");
        	}
        	if(af.getMailSender()!=null&&!af.getMailSender().equals("")){
        		buf.append(" and lower(l.mailReceiver) like lower('%"+af.getMailSender()+"%')");
        	}
        	if(af.getOperation()!=null&&!af.getOperation().equals("")){
        		buf.append(" and l.operation='"+af.getOperation()+"'");
        	}
        	if(af.getOperator()!=null&&!af.getOperator().equals("")){
        		buf.append(" and lower(l.operator) like lower('%"+af.getOperator()+"%')");
        	}
        	
        	 if(af.getStartDate()!=null&&!af.getStartDate().equals("")&&
             		af.getEndDate()!=null&&!af.getEndDate().equals("")){
             	 Date tmpBDate = comDate.toDate(af.getStartDate());
                  Calendar cBDate = Calendar.getInstance();
                  cBDate.setTime(tmpBDate);
                  cBDate.set(Calendar.HOUR,0);
                  cBDate.set(Calendar.MINUTE,0);
                  cBDate.set(Calendar.SECOND,0);
                  cBDate.set(Calendar.MILLISECOND,0);
                 _beginDate = cBDate.getTime();
                  
                  Date tempBDate = comDate.toDate(af.getEndDate());
                  Calendar cbDate = Calendar.getInstance();
                  cbDate.setTime(tempBDate);
                  cbDate.set(Calendar.HOUR,0);
                  cbDate.set(Calendar.MINUTE,0);
                  cbDate.set(Calendar.SECOND,0);
                  cbDate.set(Calendar.MILLISECOND,0);
                  _endDate = cbDate.getTime();
                  
             	 buf.append(" and l.operationDate>=:startDate and l.operationDate<=:endDate");
             	 existStartDate=true;
             	 existEndDate=true;
             }else if((af.getStartDate()!=null&&!af.getStartDate().equals(""))&&(af.getEndDate()==null
             		||af.getEndDate().equals(""))){
             	 Date tmpBDate = comDate.toDate(af.getStartDate());
                 Calendar cBDate = Calendar.getInstance();
                 cBDate.setTime(tmpBDate);
                 cBDate.set(Calendar.HOUR,0);
                 cBDate.set(Calendar.MINUTE,0);
                 cBDate.set(Calendar.SECOND,0);
                 cBDate.set(Calendar.MILLISECOND,0);
                 _beginDate = cBDate.getTime();
            	 buf.append(" and l.operationDate>=:startDate");
            	 existStartDate=true;
            }else if((af.getEndDate()!=null&&!af.getEndDate().equals(""))
         		   &&(af.getStartDate()==null||af.getStartDate().equals(""))){
         	   Date tempBDate = comDate.toDate(af.getEndDate());
               Calendar cbDate = Calendar.getInstance();
               cbDate.setTime(tempBDate);
               cbDate.set(Calendar.HOUR,0);
               cbDate.set(Calendar.MINUTE,0);
               cbDate.set(Calendar.SECOND,0);
               cbDate.set(Calendar.MILLISECOND,0);
               _endDate = cbDate.getTime();
         	   buf.append(" and l.operationDate<=:endDate");
         	  existEndDate=true;
            }
             buf.append(" order by l.operationDate desc");
            Session session = this.getDbAccessor().getSession();
            Query query = session.createQuery(buf.toString());
            if(existStartDate){
            query.setDate("startDate",_beginDate);
            }
            if(existEndDate){
            query.setDate("endDate",_endDate);	
            }
            logList = query.list();
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        return logList;
    }
	
	 /**
     * 保存
     */
	public void save(PPLog log) {
        this.getDbAccessor().save(log);
    }
	
}
