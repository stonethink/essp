package server.essp.projectpre.ui.project.maintenance;

import java.text.DecimalFormat;
import java.text.FieldPosition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.IdSetting;
import server.essp.projectpre.service.idsetting.IIdSettingService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;

/**
 * 显示当前的Project Code设置
 * 
 * @author wenhaizheng
 */
public class AcDisplayProjectId extends AbstractESSPAction {
    private String KIND = "kind";
    /**
     * 预览Project Code设置页面
     * @param request
     * @param response
     * @param data
     * @throws BusinessException
     */
    public void executeAct (HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
    throws BusinessException  {
        
        String kind=null;
        String IDFORMATER = "0";
        AfProjectId af = (AfProjectId) this.getForm();
       
        if (request.getParameter(KIND) != null) {
            kind = (String) request.getParameter(KIND);
        }
        
        IdSetting idSetting = new IdSetting();
        
        IIdSettingService logic = (IIdSettingService) this.getBean("IdSettingLogic");
        
        idSetting = logic.loadByKey(kind);
        if (idSetting!=null){
        Long iLong =idSetting.getLength();
//        int j = idSetting.getCurrentSeq().toString().length();
        int i = iLong.intValue();
        for(int k = 1;k < i;k++) {
            IDFORMATER = IDFORMATER + "0";
        }
        DecimalFormat df = new DecimalFormat(IDFORMATER);
        String newCurrentSeq = df.format(idSetting.getCurrentSeq(), new StringBuffer(),
                                  new FieldPosition(0)).toString();
        af.setLength(idSetting.getLength().toString());
        af.setCurrentSeq(newCurrentSeq);
        af.setCodingMethod(idSetting.getCodingMethod());
        }
        request.setAttribute(Constant.VIEW_BEAN_KEY,af);
        
    }
}
