/*
 * Created on 2007-12-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.humanbase.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.humanbase.service.IHumanBaseSevice;
import server.essp.hrbase.site.logic.LgSiteSelection;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import db.essp.hrbase.HrbSite;

public class AcListAddingLog extends AbstractESSPAction{

    public void executeAct(HttpServletRequest request,
            HttpServletResponse response,
            TransactionData transData) throws BusinessException {
    	IHumanBaseSevice service = (IHumanBaseSevice)this.getBean("humanBaseSevice");
    	
        List resultList = service.listAddingLog(getPrivilegeSites());
        request.setAttribute("webVo",resultList);
    }
    
    private String getPrivilegeSites() {
    	String loginId = this.getUser().getUserLoginId();
    	LgSiteSelection lg = new LgSiteSelection();
    	List<HrbSite> siteList = lg.listEnableSites(loginId);
    	String sitesStr = null;
    	for(int i = 0; i < siteList.size(); i ++) {
    		String name = siteList.get(i).getName();
    		if(sitesStr == null) {
    			sitesStr = name;
    		} else {
    			sitesStr += "," + name;
    		}
    	}
    	return sitesStr;
    }
}
