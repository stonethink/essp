package server.essp.projectpre.ui.project.maintenance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.db.ParameterId;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.ui.parameter.AfParameter;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
/**
 * 删除分类资料的Action
 * @author baohuitu
 *
 */
public class AcDeleteProjectType extends AbstractESSPAction {
	/**
	 * 删除分类资料
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
        AfParameter af=(AfParameter) this.getForm();
        String code = af.getCode();
        String kind=server.essp.projectpre.service.constant.Constant.PROJEC_TYPE;
        Parameter parameter=new Parameter();
        parameter.getCompId().setKind(kind);
        if(af.getCode()!=null) {
            parameter.getCompId().setCode(af.getCode());
        }
        parameter.getCompId().setCode(String.valueOf(code));
        // 通过此方法以接口的形式得到一个服务的实例
        IParameterService logic = (IParameterService) this.getBean("ParameterLogic");
        ParameterId parameterId = parameter.getCompId();
        logic.delete(parameterId);
   //   request.setAttribute("compId.kind",parameter.getCompId().getKind());


    }


}
