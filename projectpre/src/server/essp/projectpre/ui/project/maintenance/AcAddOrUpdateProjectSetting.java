package server.essp.projectpre.ui.project.maintenance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.IdSetting;
import server.essp.projectpre.service.idsetting.IIdSettingService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

/**
 * 新增或更新 project code 的设置
 * 
 * @author wenhaizheng
 *
 */
public class AcAddOrUpdateProjectSetting extends AbstractESSPAction {
    private String KIND = "ProjectCode";
    /**
     * 新增或更新project code
     * @param request
     * @param response
     * @param data
     * @throws BusinessException
     */
    public void executeAct (HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
    throws BusinessException  {
        
        AfProjectId af = (AfProjectId) this.getForm();
        IIdSettingService logic = (IIdSettingService) this.getBean("IdSettingLogic");
        IdSetting idSetting = new IdSetting();
        idSetting = logic.loadByKey(KIND);
        if (idSetting==null) {
            saveProjectCode(af, logic);
        }
        else {
            updateProjectCode(af, logic, idSetting);
        }
    }
    /**
     * 保存project code
     * @param af
     * @param logic
     * @throws BusinessException
     */
    private void saveProjectCode(AfProjectId af, IIdSettingService logic) throws BusinessException{
        IdSetting idSetting = new IdSetting();
        idSetting.setIdType(KIND);
        idSetting.setLength(Long.valueOf(af.getLength()));
        idSetting.setCodingMethod(af.getCodingMethod());
        idSetting.setCurrentSeq(Long.valueOf(af.getCurrentSeq()));
        logic.save(idSetting);
    }
    /**
     * 更新project code
     * @param af
     * @param logic
     * @param idSetting
     * @throws BusinessException
     */
    private void updateProjectCode(AfProjectId af, IIdSettingService logic, IdSetting idSetting) 
                                   throws BusinessException{
        idSetting.setLength(Long.valueOf(af.getLength()));
        idSetting.setCodingMethod(af.getCodingMethod());
        idSetting.setCurrentSeq(Long.valueOf(af.getCurrentSeq()));
        logic.update(idSetting);
    }
}
