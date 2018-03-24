package server.essp.pms.wbs.action;

import server.essp.framework.action.AbstractESSPAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import c2s.dto.ReturnInfo;
import server.essp.pms.activity.logic.LgActivity;
import java.util.List;
import c2s.dto.InputInfo;
import c2s.essp.pms.wbs.DtoKEY;
import c2s.essp.pms.wbs.DtoWbsActivity;
import c2s.dto.IDto;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AcWbsActivityListAllSave extends AbstractESSPAction {
    public AcWbsActivityListAllSave() {
    }

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        InputInfo inputInfo = data.getInputInfo();
        ReturnInfo returnInfo = data.getReturnInfo();

        List allActivity = (List) inputInfo.getInputObj(DtoKEY.DTO);
        LgActivity logic = new LgActivity();
        for(int i=0;i<allActivity.size();i++){
           DtoWbsActivity dto= (DtoWbsActivity)allActivity.get(i);
           if(dto.getOp().equals(IDto.OP_INSERT)){
               //新增
               logic.add(dto);
           }else if(dto.getOp().equals(IDto.OP_MODIFY)){
               //更新
               logic.update(dto);
           }
        }
        returnInfo.setError(false);
//        returnInfo.setReturnObj("wbsActivityList",wbsActivityList);

    }
}
