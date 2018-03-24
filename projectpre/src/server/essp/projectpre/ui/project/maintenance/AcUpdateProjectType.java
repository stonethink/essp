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
 * 更新分类资料的Action
 * @author baohuitu
 *
 */
public class AcUpdateProjectType extends AbstractESSPAction {
	/**
	 * 更新分类资料
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
            
            // 如果有ActionForm传入的话，用此方法获得ActionFrom
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
        
            // 通过此方法以接口的形式得到一个服务的实例
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
