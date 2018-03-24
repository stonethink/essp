package server.essp.projectpre.ui.parameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.db.ParameterId;
import server.essp.projectpre.service.parameter.IParameterService;
import server.framework.common.BusinessException;
/**
 * ɾ�������趨��Action
 * @author hongweihou
 *
 */
public class AcDeleteParameter extends AbstractESSPAction {
	/**
	 * ɾ�������趨
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		AfParameter af=(AfParameter) this.getForm();
		Parameter parameter=new Parameter();
		if(af.getKind()!=null) {
			parameter.getCompId().setKind(af.getKind());
		}
		if(af.getCode()!=null) {
			parameter.getCompId().setCode(af.getCode());
		}
		// ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��
		IParameterService logic = (IParameterService) this.getBean("ParameterLogic");
        ParameterId parameterId = parameter.getCompId();
	    logic.delete(parameterId);
        request.setAttribute("compId.kind",parameter.getCompId().getKind());
		// Ĭ�ϻ�ת��ForwardIdΪsucess��ҳ��
		// �����Ҫ�Զ���ForWardId������������
		// data.getReturnInfo().setForwardID("ForwardId");

	}


}
