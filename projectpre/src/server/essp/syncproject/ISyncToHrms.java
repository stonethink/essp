package server.essp.syncproject;

import server.essp.projectpre.db.Acnt;
import server.framework.common.BusinessException;

public interface ISyncToHrms {
	
	public void addProjcet(Acnt acnt, String site, String pmId) throws BusinessException;
	
	public void updateProject(Acnt acnt, String site, String pmId) throws BusinessException;
	
	public void closeProject(Acnt acnt, String site, String pmId) throws BusinessException;

}
