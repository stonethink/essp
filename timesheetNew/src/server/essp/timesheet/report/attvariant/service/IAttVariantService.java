/*
 * Created on 2008-1-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.attvariant.service;

import java.util.List;
import java.util.Map;

import c2s.essp.timesheet.report.DtoAttVariant;
import c2s.essp.timesheet.report.DtoAttVariantQuery;
import c2s.essp.timesheet.report.DtoHumanTimes;

/**
 * IAttVariantService
 * @author TBH
 */
public interface IAttVariantService {
    /**
     * 根据查询条件查询出在TIMECARD和ATT_LEAVE，ATT_OVERTIME中的请假，加班时间差异列表
     * @param dtoQuery
     * @return List
     */
    public Map queryByCondition(DtoAttVariantQuery dtoQuery);
    
    /**
     * 得到不重復SITE，且判斷當前登錄者是否為PMO
     * @param roleList
     * @return Map
     */
    public Map getSiteList(List roleList,String loginId);

    /**
     * 发送催缴邮件
     * @param sendList
     */
    public void sendMails(List<DtoAttVariant> sendList);
}
