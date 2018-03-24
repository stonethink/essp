/*
 * Created on 2007-12-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.humanbase.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.humanbase.form.AfHumanBaseQuery;
import server.essp.hrbase.humanbase.service.IHumanBaseSevice;
import server.framework.common.BusinessException;

public class AcListEditingLog extends AbstractESSPAction{

    public void executeAct(HttpServletRequest request,
            HttpServletResponse response,
            TransactionData transData) throws BusinessException {
    	String ridStr = request.getParameter("rid");
		if (ridStr == null || "".equals(ridStr)) {
			return;
		}
    	IHumanBaseSevice service = (IHumanBaseSevice)this.getBean("humanBaseSevice");
        List resultList = service.listEditingLog(Long.valueOf(ridStr));
        request.setAttribute("webVo", resultList);
    }
}
