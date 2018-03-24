package server.essp.issue.common.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.framework.action.*;
import server.framework.common.*;
import server.essp.issue.common.constant.Sort;
import java.util.List;
import server.essp.issue.common.logic.LgSort;

public class AcSort extends AbstractBusinessAction {
    public AcSort() {
    }

    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        /**
         * ���������Ҫ�⼸��������
         * 1. beanName : �����session�е�viewbean;���û������property����������һ��List
         * 2. property : viewbean�е�����������Ӧһ��List (��������beanName��Ӧһ��List)
         * 3. sortType : ����ʽ�����򣯽���
         * 4. sortItem : ������Ŀ (����ʲô��λ����)
         */
        String beanName=request.getParameter("beanName");
        if(beanName==null) {
            beanName="webVo";
        }
        String property=request.getParameter("property");
        String sortType=request.getParameter("sortType");
        String sortItem=request.getParameter("sortItem");
        if(sortType==null || sortType.equals("")) {
            sortType=Sort.SORT_ASC;
        }
        List details=null;
        if(property==null || property.equals("")) {
            details=(List)this.getSession().getAttribute(beanName);
        } else {
            Object viewbean=this.getSession().getAttribute(beanName);
            try {
                details = (List) DtoUtil.getProperty(viewbean, property);
            } catch (Exception ex) {
            }
        }
        LgSort logic=new LgSort();
        //logic.sort(sortType,details,sortItem);
    }
}
