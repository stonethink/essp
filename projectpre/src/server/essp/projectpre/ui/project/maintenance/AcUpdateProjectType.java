package server.essp.projectpre.ui.project.maintenance;

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
 * ���·������ϵ�Action
 * @author baohuitu
 *
 */
public class AcUpdateProjectType extends AbstractESSPAction {
	/**
	 * ���·�������
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
            
            // �����ActionForm����Ļ����ô˷������ActionFrom
            AfParameter af=(AfParameter) this.getForm();
            Parameter parameter=new Parameter();        
            String kind=server.essp.projectpre.service.constant.Constant.PROJEC_TYPE;
            parameter.getCompId().setKind(kind);   
            if(af.getCode()!=null) {
                parameter.getCompId().setCode(af.getCode());
            }
            if(af.getName()!=null) {
                parameter.setName(af.getName());
            }
            if(af.getSequence()!=null) {
                parameter.setSequence(new Long(af.getSequence()));
            }
            if(af.getStatus()!=null&&af.getStatus().equals("true")) {
                parameter.setStatus(new Boolean(true));
            }else {
                parameter.setStatus(new Boolean(false));
            }
           
            parameter.setDescription(af.getDescription());
        
            // ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��
            IParameterService logic = (IParameterService) this
                    .getBean("ParameterLogic");          
            logic.update(parameter);
            VbParameter vb = new VbParameter();
            vb.setKind(parameter.getCompId().getKind());
            vb.setCode(parameter.getCompId().getCode());
            vb.setName(parameter.getName());
            vb.setSequence(parameter.getSequence());
            vb.setDescription(parameter.getDescription());
            vb.setStatus(parameter.getStatus());
            request.setAttribute(Constant.VIEW_BEAN_KEY,vb);

    }

}
