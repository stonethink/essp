/*
 * Created on 2007-12-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.dept.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.dept.form.AfDeptQuery;
import server.essp.hrbase.dept.service.IDeptService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

/**
 * 根據查詢條件列出部門LIST
 * @author TBH
 */
public class AcListDept extends AbstractESSPAction{

    public void executeAct(HttpServletRequest request,
            HttpServletResponse response,
            TransactionData transData) throws BusinessException {
        AfDeptQuery af = (AfDeptQuery)this.getForm();
        IDeptService lg = (IDeptService)this.getBean("deptService");
        List resultList = lg.queryByCondition(af);
        request.setAttribute("webVo",resultList);
    }
}