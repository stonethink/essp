package server.essp.projectpre.ui.project.product;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.ui.parameter.AfParameter;
import server.essp.projectpre.ui.parameter.VbParameter;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;
/**
 * �г�����ԭ�����ϵ�Action
 * @author junzheng
 *
 */
public class AcListAllOriginalLanguage extends AbstractESSPAction {
	/**
	 * �г�����ԭ������
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		AfParameter af=(AfParameter)this.getForm();
		
//        String kind=request.getParameter("kind");
		String kind = af.getKind();
//		System.out.println("################ AcListAllParameter kind:="+kind);
//        if(kind==null) kind=af.getCompId().getKind();
		// ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��
		IParameterService logic = (IParameterService) this.getBean("ParameterLogic");

		List parameterList = logic.listAllByKind(kind);
        List newParameterList = new ArrayList();
        for(int i = 0;i<parameterList.size();i++){
            Parameter parameter = (Parameter)parameterList.get(i);
            VbParameter vb = new VbParameter();
            vb.setKind(parameter.getCompId().getKind());
            vb.setCode(parameter.getCompId().getCode());
            vb.setName(parameter.getName());
            vb.setDescription(parameter.getDescription());
            vb.setStatus(parameter.getStatus());
            vb.setSequence(parameter.getSequence());
            newParameterList.add(vb);
            
        }
		// ����Ҫ���ص����ݷŵ�Request�У�����ҵ����������ݲ�����ŵ�Session��
		request.setAttribute(Constant.VIEW_BEAN_KEY, newParameterList);
		
		// Ĭ�ϻ�ת��ForwardIdΪsucess��ҳ��
		// �����Ҫ�Զ���ForWardId������������
		// data.getReturnInfo().setForwardID("NULL");

	}

}
