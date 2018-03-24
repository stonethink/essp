/*
 * Created on 2008-1-31
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.attvariant.action;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.report.attvariant.service.IAttVariantService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.report.DtoAttVariantQuery;

/**
 * AcLoadSite
 * @author TBH
 *
 */
public class AcLoadSite extends AbstractESSPAction {
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response,TransactionData data)
            throws BusinessException {
    IAttVariantService lg = (IAttVariantService) this.getBean("attVariantService");
    List rolesList = this.getUser().getRoles();
    String loginId = this.getUser().getUserLoginId();
    Map siteMap = lg.getSiteList(rolesList,loginId);
    Vector siteVec = (Vector)siteMap.get(DtoAttVariantQuery.DTO_SITE_LIST);
    Boolean isPMO = (Boolean)siteMap.get(DtoAttVariantQuery.DTO_IS_PMO);
    data.getReturnInfo().setReturnObj(DtoAttVariantQuery.DTO_SITE_LIST,siteVec);
    data.getReturnInfo().setReturnObj(DtoAttVariantQuery.DTO_IS_PMO,isPMO);
    }
}
