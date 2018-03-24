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
     * ���ݲ�ѯ������ѯ����TIMECARD��ATT_LEAVE��ATT_OVERTIME�е���٣��Ӱ�ʱ������б�
     * @param dtoQuery
     * @return List
     */
    public Map queryByCondition(DtoAttVariantQuery dtoQuery);
    
    /**
     * �õ����؏�SITE�����Дஔǰ������Ƿ��PMO
     * @param roleList
     * @return Map
     */
    public Map getSiteList(List roleList,String loginId);

    /**
     * ���ʹ߽��ʼ�
     * @param sendList
     */
    public void sendMails(List<DtoAttVariant> sendList);
}
