package server.essp.timesheet.rmmaint.dao;

import java.util.Date;
import java.util.List;

import c2s.essp.common.user.DtoUserBase;
import c2s.essp.timesheet.report.DtoTsStatusQuery;

import db.essp.timesheet.TsHumanBase;
import db.essp.timesheet.TsRmMaint;

public interface IRmMaintDao {
	
	/**
	 * ����loginId��ѯ����RM��Ӧ��ϵ
	 * @param loginId
	 * @return
	 */
	public TsRmMaint getRmByLoginId(String loginId);
	
	/**
	 * ��������RM��Ӧ��ϵ
	 * @param tsRmMaint
	 */
	public void addRmMaint(TsRmMaint tsRmMaint);
	/**
	 * �����ػ�RM��Ӧ��ϵ
	 * @param tsRmMaint
	 */
	public void updateRmMaint(TsRmMaint tsRmMaint);
	/**
	 * ɾ������RM��Ӧ��ϵ
	 * @param tsRmMaint
	 */
	public void delRmMaint(TsRmMaint tsRmMaint);
	
	/**
	 * ��ѯRM�µ���Ա��ID
	 * @param rmId
	 * @return
	 */
	public List<TsRmMaint> getPersonUnderRm(String rmId);
	
	/**
	 * ��ѯ�����Ѿ�ά���˵���Ա��Ϣ
	 * @return
	 */
	public List<TsRmMaint> getAllUserMainted();
	
	/**
	 * ����loginId ��ѯԱ����Ϣ
	 * @param employeeId
	 * @return
	 */
	public TsHumanBase loadHumanById(String employeeId);
	
	/**
     * �г���ǩ�����µ�������Ա
     * @param managerId String
     * @return List
     */
    public List<DtoUserBase> listPersonByManagerFromAD(String managerId);
    
    /**
     * �г�rm�µ�������Ա
     * @param rmId
     * @return
     */
    public List<TsHumanBase> listHumanByRmFromDB(String rmId);
    
    /**
     * ͨ��site��DB�л�ȡRM�µ���Ա
     * @param rmId
     * @param site
     * @return
     */
    public List<TsHumanBase> listHumanByRmFromDBBySite(String rmId, String site);
    
    
    /**
     * ����SITE�õ�SITE�¶�Ӧ����Ա
     * @param site
     * @return List
     */
    public List getLoginIdList(String site, boolean viewAll);
    
    /**
     * ����SITE�õ�SITE�¶�Ӧ��Ч�ڣ���ְ����ְ���ڣ���Χ�ڵ���Ա
     * @param site
     * @param begin
     * @param end
     * @return List
     */
    public List listEnableLoginId(String site, Date begin, Date end);
    
    /**
     * ��ѯ������Ա(����ְ���ں���ְ������ָ��ʱ���н�����)
     * @param begin
     * @param end
     * @param site
     * @return
     */
    public List<TsHumanBase> listAllHuman(Date begin, Date end, String site);
    
    /**
     * �õ����ظ���SITE����
     * @return List
     */
    public List getSiteFromHumanBase(String site);
    
    /**
     * ����SITE�õ�SITE�¶�Ӧ��Ч�ڣ���ְ����ְ���ڣ���Χ�ڵ���Ա
     * @param site
     * @param begin
     * @param end
     * @return List
     */
    public List listEnableLoginId(String site, DtoTsStatusQuery dtoQuery,
            boolean viewAll);
    
    /**
     * �õ����еĆT��
     * @return
     */
    public List listAllEmployee();
    
    /**
     * �������T�õ�ԓ���T�����µ����ІT������
     * @param deptIds
     * @return List
     */
    public List getLoginIdsByDept(String deptIds);
    
    /**
     * ����SITE�͕r�g������ԃ��ÿ���T����ÿ���لe�еĿ����r��
     * @param site
     * @param begin
     * @param end
     * @return List
     */
    public List getSumLeaveHoursByDate(Date begin,Date end,String site);
    
    /**
     * ȡ��ֱ������
     * @return List
     */
    public List listDirectHuman();
    /**
     * ���ݲ��Ŵ����ѯ�����µ���Ա
     * @param deptId
     * @return
     */
    public List listPersonsByDept(String deptId);
}