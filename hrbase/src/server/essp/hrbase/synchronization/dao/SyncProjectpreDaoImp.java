package server.essp.hrbase.synchronization.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import server.essp.hrbase.dept.viewbean.VbSyncUnitBase;


public class SyncProjectpreDaoImp extends JdbcDaoSupport implements ISyncProjectpreDao {
	
	private static final String TABLE_NAME_PP_ACNT = "PP_ACNT";
	private static final String TABLE_NAME_PP_ACNT_PERSON = "PP_ACNT_PERSON";
	
	private static final String SQL_INSERT_PP_ACNT = 
		"INSERT INTO " + TABLE_NAME_PP_ACNT
	   +" (rid, acnt_id, acnt_name, acnt_full_name, achieve_belong, cost_attach_bd, acnt_attribute, " 
	   +" acnt_status, rel_parent_id, is_acnt, is_enable, rel_prj_type, contract_acnt_id, is_bl, rct, rcu) " 
	   +" VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String SQL_UPDATE_PP_ACNT = 
		"UPDATE " + TABLE_NAME_PP_ACNT
	   +" SET acnt_name=?, acnt_full_name=?, achieve_belong=?, cost_attach_bd=?," 
	   +" rel_parent_id=?, is_bl=?, rut=?, ruu=?" 
	   +" WHERE acnt_id=?";
	
	private static final String SQL_INSERT_PP_ACNT_PERSON = 
		"INSERT INTO " + TABLE_NAME_PP_ACNT_PERSON
	   +" (rid, acnt_rid, person_type, login_id, name)"
	   +" VALUES(?, ?, ?, ?, ?)";
	
	private static final String SQL_UPDATE_PP_ACNT_PERSON = 
		"UPDATE " + TABLE_NAME_PP_ACNT_PERSON
	   +" SET login_id=?, name=?"
	   +" WHERE acnt_rid=? and person_type=?";
	
	private static final String SQL_GET_PP_ACNT_RID = 
		"SELECT rid FROM " + TABLE_NAME_PP_ACNT
	   +" WHERE acnt_id=?";
	
	private static final String SQL_SET_PP_ACNT_ISENABLE = 
		"UPDATE " + TABLE_NAME_PP_ACNT
	   +" SET is_enable='0'"
	   +" WHERE acnt_id=?";

	/**
	 * ��projectpreϵͳ���벿����Ϣ��¼
	 */
	public void add(VbSyncUnitBase unitBase) {
		Object[] args =  new Object[] {unitBase.getRid(), unitBase.getUnitCode(),
									   unitBase.getUnitName(), unitBase.getUnitFullName(),
									   unitBase.getBelongBd(), unitBase.getCostBelongUnit(),
									   "Dept", "Confirmed", 
									   unitBase.getParentUnitCode(),
									   "0", "1", "Master", unitBase.getParentUnitCode(),
									   unitBase.getIsBl(),
									   unitBase.getRct(), unitBase.getRcu()};
		this.getJdbcTemplate().update(SQL_INSERT_PP_ACNT, args);
	}
	
	/**
	 * ����projectpreϵͳ�Ĳ�����Ϣ
	 */
	public void update(VbSyncUnitBase unitBase) {
		Object[] args =  new Object[] {unitBase.getUnitName(), unitBase.getUnitFullName(),
				   unitBase.getBelongBd(), unitBase.getCostBelongUnit(), unitBase.getParentUnitCode(),
				   unitBase.getIsBl(), unitBase.getRut(), unitBase.getRuu(), unitBase.getUnitCode()};
		this.getJdbcTemplate().update(SQL_UPDATE_PP_ACNT, args);
	}
	
	/**
	 * ��projectpreϵͳ������Ա��Ϣ
	 */
	public void addPerson(Long rid, Long acntRid, String personType, 
			                String loginId, String name) {
		Object[] args =  new Object[] {rid, acntRid,
									   personType, loginId, name};
		this.getJdbcTemplate().update(SQL_INSERT_PP_ACNT_PERSON, args);
	}
	/**
	 * ����projectpreϵͳ���벿����ص���Ա��Ϣ
	 */
	public void updatePerson(Long acntRid, String personType, String loginId, String name) {
		Object[] args =  new Object[] {loginId, name,
										acntRid, personType};
		this.getJdbcTemplate().update(SQL_UPDATE_PP_ACNT_PERSON, args);
	}
	/**
	 * ����ר�������ȡר����rid
	 */
	public Long getPPAcntRid(String acntId) {
		return this.getJdbcTemplate().queryForLong(SQL_GET_PP_ACNT_RID, new Object[]{acntId});
	}
	
	/**
	 * ɾ��projectpreϵͳ�Ĳ��ţ��޸�is_enable״̬Ϊ0��
	 */
	public void delete(String unitCode) {
		this.getJdbcTemplate().update(SQL_SET_PP_ACNT_ISENABLE, new Object[]{unitCode});
	}

	
}
