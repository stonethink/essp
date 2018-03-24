package server.essp.projectpre.ui.project.maintenance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.db.ParameterId;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.ui.parameter.AfParameter;
import server.essp.projectpre.ui.parameter.VbParameter;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;
/**
 * Ԥ���������ϵ�Action
 * @author baohuitu
 *
 */
public class AcPreviewProjectType extends AbstractESSPAction {
    /**
     * Ԥ����������
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
            String kind=server.essp.projectpre.service.constant.Constant.PROJEC_TYPE;
            parameter.getCompId().setKind(kind);
            if(af!=null&&af.getCode()!=null){
                parameter.getCompId().setCode(af.getCode());
            }
//          System.out.println("************ AcPreviewParameter  kind:="+parameter.getCompId().getKind());
            
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
             vb.setDescription(parameter.getDescription());
             vb.setStatus(parameter.getStatus());
             request.setAttribute(Constant.VIEW_BEAN_KEY,vb);
//          throw new BusinessException("error.system.db");
            //Ĭ�ϻ�ת��ForwardIdΪsucess��ҳ��
            //�����Ҫ�Զ���ForWardId������������
            //data.getReturnInfo().setForwardID("ForwardId");
    
    }

}
