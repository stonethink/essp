/*
 * Created on 2007-12-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.bd.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.bd.form.AfBd;
import server.essp.hrbase.bd.service.IBdService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

/**
 * BDÐÂÔö
 * @author TBH
 */
public class AcAddBdCode extends AbstractESSPAction{

    public void executeAct(HttpServletRequest request,
            HttpServletResponse response,
            TransactionData transData) throws BusinessException {
        IBdService lg = (IBdService)this.getBean("hrbBdSevice"); 
        AfBd afBd = (AfBd)this.getForm();
        lg.add(afBd);
    }
}
