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
 * 预览参数设定页面的Action
 * @author hongweihou
 *
 */
public class AcPreviewParameter extends AbstractESSPAction {
	/**
	 * 预览参数设定页面
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
			
			// 通过此方法以接口的形式得到一个服务的实例
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
			//默认会转向ForwardId为sucess的页面
			//如果需要自定义ForWardId，用下面的语句
			//data.getReturnInfo().setForwardID("ForwardId");
	
	}

}
