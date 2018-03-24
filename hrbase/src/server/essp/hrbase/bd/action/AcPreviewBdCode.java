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
 * BD信息初始化
 * @author TBH
 */
public class AcPreviewBdCode extends AbstractESSPAction{

    public void executeAct(HttpServletRequest request,
            HttpServletResponse response,
            TransactionData transData) throws BusinessException {
        String code = request.getParameter("code");
        IBdService lg = (IBdService)this.getBean("hrbBdSevice");
        AfBd af = lg.getDetailInfo(code);
        request.setAttribute("webVo",af);
    }
}
