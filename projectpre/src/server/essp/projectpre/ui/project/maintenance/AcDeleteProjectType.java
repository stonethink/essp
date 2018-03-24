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
 * ɾ���������ϵ�Action
 * @author baohuitu
 *
 */
public class AcDeleteProjectType extends AbstractESSPAction {
	/**
	 * ɾ����������
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
        // ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��
        IParameterService logic = (IParameterService) this.getBean("ParameterLogic");
        ParameterId parameterId = parameter.getCompId();
        logic.delete(parameterId);
   //   request.setAttribute("compId.kind",parameter.getCompId().getKind());


    }


}
