/*
 * Created on 2007-12-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.bd.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.bd.service.IBdService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
/**
 * 列出所有BD信息
 * @author TBH
 */
public class AcListAllBdCode extends AbstractESSPAction{

    public void executeAct(HttpServletRequest request,
            HttpServletResponse response,
            TransactionData transData) throws BusinessException {
        IBdService lg = (IBdService)this.getBean("hrbBdSevice"); 
        List bdList = lg.listAllBdCode();
        request.setAttribute("webVo",bdList);
    }
}