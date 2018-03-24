/*
 * Created on 2007-12-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.dept.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.bd.service.IBdService;
import server.essp.hrbase.dept.form.AfDeptQuery;
import server.essp.hrbase.dept.service.IDeptService;
import server.essp.hrbase.site.service.ISiteService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
/**
 * 初始化修改部門信息 
 * @author TBH
 */
public class AcInitUpdateDeptInfo extends AbstractESSPAction{

    public void executeAct(HttpServletRequest request,
            HttpServletResponse response,
            TransactionData transData) throws BusinessException {
        IDeptService lg = (IDeptService)this.getBean("deptService");
        AfDeptQuery afQuery = new AfDeptQuery();
        String unitCode = (String)request.getParameter("unitCode");
        afQuery = lg.queryByDeptId(unitCode);
        IBdService lgBd = (IBdService)this.getBean("hrbBdSevice"); 
        List bdList = lgBd.listEnabledBdCode();
        List achieveBelongList = lgBd.listAchieveBelongUnit();
        ISiteService lgSite = (ISiteService)this.getBean("siteService");
        List listSite = lgSite.listEnabledSiteOption();
        List parentUnitList = lg.getParentUnit(unitCode);
        afQuery.setParentUnitList(parentUnitList);
        afQuery.setCostBelongUnitList(bdList);
        afQuery.setSiteList(listSite);
        afQuery.setBdList(achieveBelongList);
        request.setAttribute(server.framework.common.Constant.VIEW_BEAN_KEY, afQuery);
    }
}