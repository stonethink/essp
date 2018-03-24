package server.essp.projectpre.ui.project.maintenance;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.ui.parameter.VbParameter;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;

/**
 * 列出分类资料的Action
 * @author baohuitu
 *
 */
public class AcListAllProjectType extends AbstractESSPAction {
	/**
	 * 列出分类资料
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
        String kind=server.essp.projectpre.service.constant.Constant.PROJEC_TYPE;
//        String kind=request.getParameter("kind");
      //    String kind = af.getKind();
//      System.out.println("################ AcListAllParameter kind:="+kind);
//        if(kind==null) kind=af.getCompId().getKind();
        // 通过此方法以接口的形式得到一个服务的实例
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
        // 将需要返回的数据放到Request中，所有业务操作的数据不允许放到Session中
        request.setAttribute(Constant.VIEW_BEAN_KEY, newParameterList);
        
       
    }

}
