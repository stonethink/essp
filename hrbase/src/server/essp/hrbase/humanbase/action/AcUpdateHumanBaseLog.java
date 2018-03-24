/*
 * Created on 2007-12-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.humanbase.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.humanbase.form.AfHumanBaseLog;
import server.essp.hrbase.humanbase.service.IHumanBaseSevice;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcUpdateHumanBaseLog extends AbstractESSPAction {

	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData transData)
			throws BusinessException {
		AfHumanBaseLog af = (AfHumanBaseLog) this.getForm();
		IHumanBaseSevice service = (IHumanBaseSevice) this
				.getBean("humanBaseSevice");
		service.updateHumanBaseLog(af);
	}
}
