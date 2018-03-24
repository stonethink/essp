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
 * �����������ϵ�Action
 * @author baohuitu
 *
 */
public class AcAddProjectType extends AbstractESSPAction {
	/**
	 * ������������
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
        String kind=server.essp.projectpre.service.constant.Constant.PROJEC_TYPE;
        // �����ActionForm����Ļ����ô˷������ActionFrom
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
        // ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��
        IParameterService logic = (IParameterService) this.getBean("ParameterLogic");

        logic.save(parameter);

    }
}
