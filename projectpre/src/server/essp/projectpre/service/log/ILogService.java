package server.essp.projectpre.service.log;

import java.util.List;
import server.essp.projectpre.db.PPLog;
import server.essp.projectpre.ui.log.AfLog;
import server.framework.common.BusinessException;

public interface ILogService {
	
	 /**
     *列出符合查询条件的LOG
     */   
	public List query(AfLog af) throws BusinessException;
	/**
	 * 新增记录
	 * @param customer
	 */
	public void save(PPLog ppLog);
}
