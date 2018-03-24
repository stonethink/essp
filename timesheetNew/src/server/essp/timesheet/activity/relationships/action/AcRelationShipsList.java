package server.essp.timesheet.activity.relationships.action;

import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import server.essp.timesheet.activity.relationships.service.IRelationShipsService;
import c2s.essp.timesheet.activity.DtoActivityKey;

/**
 * <p>Description: ÏÔÊ¾RelationShipsListµÄAction</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AcRelationShipsList extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {
        InputInfo inputInfo = data.getInputInfo();
        ReturnInfo returnInfo = data.getReturnInfo();
        IRelationShipsService lg = (IRelationShipsService)this.getBean("iRelationService");
        Long activityRid = (Long) inputInfo.getInputObj(DtoActivityKey.DTO_RID);
        boolean isPre = (Boolean)inputInfo.getInputObj("IsPre");
        List relationList = lg.getRelationShipsList(activityRid,isPre);
        returnInfo.setReturnObj(DtoActivityKey.RELATION_LIST, relationList);
    }
}
