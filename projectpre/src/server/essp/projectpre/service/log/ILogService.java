package server.essp.projectpre.service.log;

import java.util.List;
import server.essp.projectpre.db.PPLog;
import server.essp.projectpre.ui.log.AfLog;
import server.framework.common.BusinessException;

public interface ILogService {
	
	 /**
     *�г����ϲ�ѯ������LOG
     */   
	public List query(AfLog af) throws BusinessException;
	/**
	 * ������¼
	 * @param customer
	 */
	public void save(PPLog ppLog);
}
