/*
 * Created on 2007-12-14
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
 * 初始化部門信息
 * @author TBH
 */
public class AcInitDept extends AbstractESSPAction{

    public void executeAct(HttpServletRequest request,
            HttpServletResponse response,
            TransactionData transData) throws BusinessException {
        IBdService lg = (IBdService)this.getBean("hrbBdSevice"); 
        AfDeptQuery afQuery = new AfDeptQuery();
        List bdList = lg.listEnabledBdCode();
        List achieveList = lg.listAchieveBelongUnit();
        ISiteService lgSite = (ISiteService)this.getBean("siteService");
        List listSite = lgSite.listEnabledSiteOption();
        IDeptService lgDept = (IDeptService)this.getBean("deptService");
        List listDept = lgDept.getAllUnit();
        afQuery.setBdList(achieveList);
        afQuery.setCostBelongUnitList(bdList);
        afQuery.setSiteList(listSite);
        afQuery.setAcntAttribute("Dept");
        afQuery.setBdName("");
        afQuery.setDmName("");
        afQuery.setParentUnitCode("");
        afQuery.setTsName("");
        afQuery.setUnitFullName("");
        afQuery.setUnitName("");
        afQuery.setParentUnitList(listDept);
        request.setAttribute(server.framework.common.Constant.VIEW_BEAN_KEY, afQuery);
    }
}