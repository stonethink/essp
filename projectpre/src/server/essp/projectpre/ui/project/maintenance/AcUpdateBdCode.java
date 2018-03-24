package server.essp.projectpre.ui.project.maintenance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.service.bd.IBdService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;

/**
 * 更新bd code的Action
 * 
 * @author wenhaizheng
 * 
 */
public class AcUpdateBdCode extends AbstractESSPAction {
    
    /**
     * 更新bd code
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
            
            // 如果有ActionForm传入的话，用此方法获得ActionFrom
            AfBd af=(AfBd) this.getForm();
            Bd bd=new Bd();
            if(af.getBdCode()!=null) {
                bd.setBdCode(af.getBdCode());
            }
            if(af.getBdName()!=null) {
                bd.setBdName(af.getBdName());
            }
          
            if(af.getStatus()!=null&&af.getStatus().equals("true")) {
                bd.setStatus(true);
            }else {
                bd.setStatus(false);
            }
            if(af.getAchieveBelong()!=null&&af.getAchieveBelong().equals("true")) {
                bd.setAchieveBelong(true);
            }else {
                bd.setAchieveBelong(false);
            }
            bd.setDescription(af.getDescription());
         
           // bd.setRid(new Long(3));
            // 通过此方法以接口的形式得到一个服务的实例
            IBdService logic = (IBdService) this
                    .getBean("BdCodeLogic");
            
            logic.update(bd);
            request.setAttribute(Constant.VIEW_BEAN_KEY,bd);
            //默认会转向ForwardId为sucess的页面
            //如果需要自定义ForWardId，用下面的语句
            //data.getReturnInfo().setForwardID("NULL");

    }

}
