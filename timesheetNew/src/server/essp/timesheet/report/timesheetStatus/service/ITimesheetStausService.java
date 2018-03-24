/*
 * Created on 2007-11-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.timesheetStatus.service;

import java.util.List;
import java.util.Map;
import c2s.essp.timesheet.report.DtoTsStatusQuery;

public interface ITimesheetStausService {
    
    /**
     * 根據時間區間得到符合查詢條件的記錄（包括狀態人數統計，未填寫，未提交，已提交，已審批，PM已審批，被拒絕的工時單信息）
     * @param dtoQuery
     * @return Map
     * @throws Exception 
     */
    public Map queryByPeriod(DtoTsStatusQuery dtoQuery,String loginId,
            Boolean isExp) throws Exception;
    
    public void setExcelDto(boolean excelDto);
    
    /**
     * 得到未填寫周報的員工資料
     * @param periodLst
     * @param dtoQuery
     * @param loginId
     * @return
     */
    public List getUnfilled(List periodLst, DtoTsStatusQuery dtoQuery, String loginId);
    
    /**
     * 初始MAP
     * @throws Exception
     */
    public void initMap() throws Exception;
}
