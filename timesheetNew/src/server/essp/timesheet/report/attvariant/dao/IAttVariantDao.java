/*
 * Created on 2008-1-31
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.attvariant.dao;

import java.util.List;
import c2s.essp.timesheet.report.DtoAttVariantQuery;

public interface IAttVariantDao {
    
    /**
     *  ��ѯ��ٹ�ʱ����
     * @param dtoQuery
     * @return List
     */
	public List queryLeaveVariant(DtoAttVariantQuery dtoQuery);
    
	/**
     * ��ѯ�Ӱ๤ʱ����
     * @param dtoQuery
     * @return List
	 */
    public List queryOvertimeVariant(DtoAttVariantQuery dtoQuery);

  
}
