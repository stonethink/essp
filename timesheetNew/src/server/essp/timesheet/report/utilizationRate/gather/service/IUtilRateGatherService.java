package server.essp.timesheet.report.utilizationRate.gather.service;

import java.util.List;
import c2s.essp.timesheet.report.DtoUtilRateQuery;
import java.util.Date;
import java.util.Map;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public interface IUtilRateGatherService {

    /**
     * 根据loginId得到所管辖的部门集合
     * @param loginId String
     * @param roleId
     * @return List
     */
    public List getDeptList(String loginId, String roleId);

    /**
     * 判断获得的时间区域是否跨年，若跨年则取得每年的开始和结束时间
     * @param beginDate Date
     * @param endDate Date
     * @return List
     */
    public List getYearsList(Date beginDate, Date endDate);

    /**
     * 将得到的时间区域转换成"YYYY-MM月~YYYY-MM月"的格式
     * @param beginDate Date
     * @param endDate Date
     * @return String
     */
    public String getDateStr(Date beginDate, Date endDate);


    /**
     * 根据输入的时间范围判断是否跨年，如果跨年则以年为单位取得每年对应的数据
     * @param dtoQuery DtoUtilRateQuery
     * @return Map
     */
    public Map getDataTreeByYear(DtoUtilRateQuery dtoQuery);

    /**
     * 设置标志位
     * @param excelDto boolean
     */
    public void setExcelDto(boolean excelDto);
}
