/*
 * Created on 2007-10-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.aprm.service;

import java.io.IOException;
import server.essp.timesheet.aprm.form.AfAPRMTSImport;
import server.essp.timesheet.aprm.viewbean.VbAprmImportInfo;

public interface IAPRMService {

    /**
     * ½âÎö×Ö·ûÁ÷
     * @param is
     * @return
     * @throws IOException
     */
    public VbAprmImportInfo saveDateFromInputStream(AfAPRMTSImport af,String loginId);

    
    
}
