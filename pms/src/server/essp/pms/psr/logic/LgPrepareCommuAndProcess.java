package server.essp.pms.psr.logic;

import server.essp.framework.logic.AbstractESSPLogic;
import java.util.List;
import java.util.ArrayList;
import net.sf.hibernate.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class LgPrepareCommuAndProcess extends AbstractESSPLogic {

    public List getProcessSheetDate(Long acntRid){
        List result=new ArrayList();
        try {
            Query query=this.getDbAccessor().getSession()
                        .createQuery("from PmsStatusReportIssue t where t.acntRid=:acntRid "+
                                     " and t.type='NCR'");
            query.setString("acntRid",acntRid.toString());
            result=query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public List getCommuSheetDate(Long acntRid){
        List result=new ArrayList();
        try {
            Query query=this.getDbAccessor().getSession()
                        .createQuery("from PmsStatusReportIssue t where t.acntRid=:account "+
                                     " and t.type<>'NCR'");
//            query.setString("acntRid",acntRid.toString());
            query.setLong("account",acntRid.longValue());
            result=query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


}
