package server.essp.projectpre.ui.project.maintenance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.ui.parameter.AfParameter;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
/**
 * 新增分类资料的Action
 * @author baohuitu
 *
 */
public class AcAddProjectType extends AbstractESSPAction {
	/**
	 * 新增分类资料
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
        String kind=server.essp.projectpre.service.constant.Constant.PROJEC_TYPE;
        // 如果有ActionForm传入的话，用此方法获得ActionFrom
        AfParameter af = (AfParameter) this.getForm();
        Parameter parameter = new Parameter();
        parameter.getCompId().setKind(kind);
        if (af.getCode() != null && !af.getCode().trim().equals("")) {
            parameter.getCompId().setCode(af.getCode().trim());
        }
        if (af.getName() != null && !af.getName().trim().equals("")) {
            parameter.setName(af.getName().trim());
        }
        if (af.getSequence() != null && !af.getSequence().trim().equals("")) {
            parameter.setSequence(new Long(af.getSequence().trim()));
        }
       
        parameter.setDescription(af.getDescription());
   
        parameter.setStatus(new Boolean(true));
        // 通过此方法以接口的形式得到一个服务的实例
        IParameterService logic = (IParameterService) this.getBean("ParameterLogic");

        logic.save(parameter);

    }
}
