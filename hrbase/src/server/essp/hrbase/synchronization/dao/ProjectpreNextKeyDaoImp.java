package server.essp.hrbase.synchronization.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class ProjectpreNextKeyDaoImp extends JdbcDaoSupport implements INextKeyDao {

private static final String NEXT_KEY_TABLE = "ESSP_HBSEQ";
	
	private static final String SQL_GET_NEXTKEY =
        "SELECT (seq_no + 1)seq_no FROM " + NEXT_KEY_TABLE
        + " WHERE seq_type = ?";
	
	private static final String SQL_INCREASE_NEXTKEY =
        "UPDATE " + NEXT_KEY_TABLE
        + " SET seq_no = seq_no + 1 "
        + " WHERE seq_type = ?";
	private static final String SQL_CREATE_NEXTKEY = 
		"INSERT INTO " + NEXT_KEY_TABLE
	   +" (seq_type, seq_no)"
	   +" VALUES(?, 1)";
	
	/**
	 * ��ESSP_HBSEQ���л�ȡĳ�ű�����RID
	 */
	public Long getNextKey(String nextKeyName) {
		Object[] args = new Object[]{nextKeyName};
		Long nextKey = (Long) this.getJdbcTemplate().query(SQL_GET_NEXTKEY, 
								args, new NextKeyResultSetExtractor());
		if(nextKey.compareTo(new Long(-1)) != 0) {
			this.getJdbcTemplate().update(SQL_INCREASE_NEXTKEY, args);
			return nextKey;
		} else {
			this.getJdbcTemplate().update(SQL_CREATE_NEXTKEY, args);
			return new Long(1);
		}
	}
	
	/**
	 * Ϊĳ�ű���ESSP_HBSEQ���н���RID���ֵ��¼
	 */
	public void createNewKey(String nextKeyName) {
		Object[] args = new Object[]{nextKeyName};
		this.getJdbcTemplate().update(SQL_CREATE_NEXTKEY, args);
	}
	
	/**
	 * �����ѯ��¼RID��Ľ��������
	 * @author wenhaizheng
	 *
	 */
	private class NextKeyResultSetExtractor implements ResultSetExtractor {
		public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
			if (rs == null || !rs.next()) {
	               return new Long(-1);
	            }
	        return rs.getLong("seq_no");
		}
		
	}

}
