package server.essp.projectpre.ui.parameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.db.ParameterId;
import server.essp.projectpre.service.parameter.IParameterService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
/**
 * Ԥ�������趨ҳ���Action
 * @author hongweihou
 *
 */
public class AcPreviewParameter extends AbstractESSPAction {
	/**
	 * Ԥ�������趨ҳ��
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		
		    Parameter parameter=new Parameter();
			AfParameter af=(AfParameter)this.getForm();
			
			if (af!=null&&af.getKind()!=null) {
				parameter.getCompId().setKind(af.getKind());
			}
			if(af!=null&&af.getCode()!=null){
				parameter.getCompId().setCode(af.getCode());
			}
            if(af!=null&&af.getDescription()!=null){
                parameter.setDescription(af.getDescription());
            }
//			System.out.println("************ AcPreviewParameter  kind:="+parameter.getCompId().getKind());
			
			// ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��
			IParameterService logic = (IParameterService) this
					.getBean("ParameterLogic");
			ParameterId parameterId=parameter.getCompId();
			
			 parameter=logic.loadByKey(parameterId);
			VbParameter vb = new VbParameter();
            vb.setKind(parameter.getCompId().getKind());
            vb.setCode(parameter.getCompId().getCode());
            vb.setName(parameter.getName());
            vb.setSequence(parameter.getSequence());
            vb.setStatus(parameter.getStatus());
            vb.setDescription(parameter.getDescription());
			request.setAttribute(Constant.VIEW_BEAN_KEY,vb);
//			throw new BusinessException("error.system.db");
			//Ĭ�ϻ�ת��ForwardIdΪsucess��ҳ��
			//�����Ҫ�Զ���ForWardId������������
			//data.getReturnInfo().setForwardID("ForwardId");
	
	}

}
