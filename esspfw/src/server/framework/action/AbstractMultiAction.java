package server.framework.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import server.framework.form.CommonForm;
import server.framework.logic.AbstractMultiLogic;
import com.wits.util.Parameter;

public abstract class AbstractMultiAction extends AbstractBusinessAction {
    public AbstractMultiAction() {
        super();
    }

    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data any
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public final void executeAct(HttpServletRequest request,
                                 HttpServletResponse response,
                                 TransactionData data) throws
            BusinessException {

        if (this.getForm() != null) {
            if (this.getForm() instanceof CommonForm) {
                CommonForm commonForm = (CommonForm)this.getForm();
                if (commonForm.getFunctionId() == null) {
                    throw new BusinessException(this.getClass().getName(),"functionId is null");
                }

                if (commonForm.getFunctionId().equals(commonForm.ADD)) {
                    executeAdd(request, response, data);

                    if(data.getReturnInfo().getForwardID()==null ||
                       data.getReturnInfo().getForwardID().trim().equals("")) {
                        data.getReturnInfo().setForwardID("add_success");
                    }
                } else if (commonForm.getFunctionId().equals(commonForm.DELETE)) {
                    executeDelete(request, response, data);

                    if(data.getReturnInfo().getForwardID()==null ||
                       data.getReturnInfo().getForwardID().trim().equals("")) {
                        data.getReturnInfo().setForwardID("delete_success");
                    }

                } else if (commonForm.getFunctionId().equals(commonForm.DETAIL)) {
                    executeDetail(request, response, data);

                    if(data.getReturnInfo().getForwardID()==null ||
                       data.getReturnInfo().getForwardID().trim().equals("")) {
                        data.getReturnInfo().setForwardID("detail_success");
                    }
                } else if (commonForm.getFunctionId().equals(commonForm.QUERY)) {
                    executeQuery(request, response, data);

                    if(data.getReturnInfo().getForwardID()==null ||
                       data.getReturnInfo().getForwardID().trim().equals("")) {
                        data.getReturnInfo().setForwardID("query_success");
                    }

                } else if (commonForm.getFunctionId().equals(commonForm.UPDATE)) {
                    executeUpdate(request, response, data);

                    if(data.getReturnInfo().getForwardID()==null ||
                       data.getReturnInfo().getForwardID().trim().equals("")) {
                        data.getReturnInfo().setForwardID("update_success");
                    }

                } else if (commonForm.getFunctionId().equals(commonForm.EDIT)) {
                    executeUpdate(request, response, data);

                    if(data.getReturnInfo().getForwardID()==null ||
                       data.getReturnInfo().getForwardID().trim().equals("")) {
                        data.getReturnInfo().setForwardID("update_success");
                    }

                } else if (commonForm.getFunctionId().equals(commonForm.INITADDPAGE)) {
                    executeInitAddPage(request, response, data);

                    if (data.getReturnInfo().getForwardID() == null ||
                        data.getReturnInfo().getForwardID().trim().equals("")) {
                        data.getReturnInfo().setForwardID("initAddPage_success");
                    }

                } else if (commonForm.getFunctionId().equals(commonForm.INITUPDATEPAGE)) {
                    executeInitUpdatePage(request, response, data);

                    if (data.getReturnInfo().getForwardID() == null ||
                        data.getReturnInfo().getForwardID().trim().equals("")) {
                        data.getReturnInfo().setForwardID("initUpdatePage_success");
                    }

                }

                else {
                    throw new BusinessException(this.getClass().getName(),"functionId is error:" +
                                                commonForm.getFunctionId());
                }

            } else {
                throw new BusinessException(this.getClass().getName(),"form instance type error:" +
                                            this.getForm().getClass().getName());
            }
        } else {
            throw new BusinessException(this.getClass().getName(),"form is null");
        }
    }

    /**
     * 执行查询列表功能
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     */
    public abstract void executeQuery(HttpServletRequest request,
                                      HttpServletResponse response,
                                      TransactionData data) throws
            BusinessException;

    /**
     * 执行新增功能
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     */
    public abstract void executeAdd(HttpServletRequest request,
                                    HttpServletResponse response,
                                    TransactionData data) throws
        BusinessException;

    public abstract void executeInitAddPage(HttpServletRequest request,
                                    HttpServletResponse response,
                                    TransactionData data) throws
        BusinessException;


    /**
     * 执行更新功能
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     */
    public abstract void executeUpdate(HttpServletRequest request,
                                       HttpServletResponse response,
                                       TransactionData data) throws
        BusinessException;

    public abstract void executeInitUpdatePage(HttpServletRequest request,
                                               HttpServletResponse response,
                                               TransactionData data) throws
        BusinessException;

    /**
     * 执行删除功能
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     */
    public abstract void executeDelete(HttpServletRequest request,
                                       HttpServletResponse response,
                                       TransactionData data) throws
            BusinessException;

    /**
     * 执行查询详细资料功能
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     */
    public abstract void executeDetail(HttpServletRequest request,
                                       HttpServletResponse response,
                                       TransactionData data) throws
            BusinessException;

    public void executeQuery(AbstractMultiLogic logic, Parameter param) throws
            BusinessException {
        logic.executeQuery(param);
    }

    public void executeAdd(AbstractMultiLogic logic, Parameter param) throws
            BusinessException {
        logic.executeAdd(param);
    }

    public void executeDelete(AbstractMultiLogic logic, Parameter param) throws
            BusinessException {
        logic.executeDelete(param);
    }

    public void executeUpdate(AbstractMultiLogic logic, Parameter param) throws
            BusinessException {
        logic.executeUpdate(param);
    }

    public void executeDetail(AbstractMultiLogic logic, Parameter param) throws
            BusinessException {
        logic.executeDetail(param);
    }
}
