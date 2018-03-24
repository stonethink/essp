package server.essp.projectpre.ui.parameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.ui.parameter.AfParameter;
import server.framework.common.BusinessException;

/**
 * ���������趨��Action
 * @author hongweihou
 *
 */
public class AcAddParameter extends AbstractESSPAction {
	/**
	 * ���������趨
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {

		// �����ActionForm����Ļ����ô˷������ActionFrom
		AfParameter af = (AfParameter) this.getForm();
		Parameter parameter = new Parameter();
		if (af.getKind() != null && !af.getKind().trim().equals("")) {
			parameter.getCompId().setKind(af.getKind().trim());
		}
		if (af.getCode() != null && !af.getCode().trim().equals("")) {
			parameter.getCompId().setCode(af.getCode().trim());
		}
		if (af.getName() != null && !af.getName().trim().equals("")) {
			parameter.setName(af.getName().trim());
		}
        if (af.getSequence() != null && !af.getSequence().trim().equals("")) {
            parameter.setSequence(new Long(af.getSequence().trim()));
        }
       
        parameter.setDescription(af.getDescription().trim());
   
		parameter.setStatus(new Boolean(true));
		// ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��
		IParameterService logic = (IParameterService) this.getBean("ParameterLogic");

		logic.save(parameter);

		// Ĭ�ϻ�ת��ForwardIdΪsucess��ҳ��
		// �����Ҫ�Զ���ForWardId������������
		// data.getReturnInfo().setForwardID("ForwardId");
	}

}
