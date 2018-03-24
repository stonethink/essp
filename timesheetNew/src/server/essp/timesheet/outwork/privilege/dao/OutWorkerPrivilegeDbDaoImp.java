package server.essp.timesheet.outwork.privilege.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import server.essp.timesheet.common.dao.BeanRowMapper;
import c2s.essp.common.user.DtoRole;
import c2s.essp.timesheet.outwork.DtoPrivilege;
import c2s.essp.timesheet.outwork.DtoUser;

public class OutWorkerPrivilegeDbDaoImp extends JdbcDaoSupport implements IOutWorkerPrivilegeDbDao  {
           final static String SQL_LOAD_UESER="select distinct l.login_id,l.login_name "
           		   +" from Ts_Outworker_Privilege l";
           
           final static String SQL_LOAD_PRIVILIGE="select a.rid as acnt_rid, a.account_id, a.account_name,p.login_id,p.login_name "
           		    +" from ts_account a left join TS_OUTWORKER_PRIVILEGE p "
           		    +" on a.rid=p.acnt_rid and lower(p.login_id)=lower(?) where a.dept_flag='1' and a.account_status = 'N'";
          
           final static String SQL_INSERT="insert into Ts_Outworker_Privilege(rid,login_id,login_name,acnt_rid) values(?,?,?,?)";
           
           final static String SQL_DELETE="delete from Ts_Outworker_Privilege where lower(login_id)=lower(?)";
           
           final static String SQL_GET_MAX_RID="select max(rid) as rid from Ts_Outworker_Privilege";
           
           final static String SQL_ROLE = "select t.name as role_name from UPMS_ROLE t where t.ROLE_ID in ("
               +" select u.ROLE_ID from UPMS_ROLE_USER u where Upper(u.LOGIN_ID)=Upper(?)) order by t.SEQUENCE";
           
           private final static BeanRowMapper MAPPER_USER = new BeanRowMapper(
        		   DtoUser.class);
           
           private final static BeanRowMapper MAPPER_PRIVILEGE = new BeanRowMapper(
        		   DtoPrivilege.class);
           
           private final static BeanRowMapper MAPPER_ROLE = new BeanRowMapper(
                   DtoRole.class);
           
    	   public List listUser() {
    		   return getJdbcTemplate().query(SQL_LOAD_UESER,MAPPER_USER);
    	   }
           
        /**
         * 根据传进来的loginId批量删除
         *
         */ 
    	  public void delete(final String loginId) {
    		     getJdbcTemplate().execute(SQL_DELETE,
    				new PreparedStatementCallback() { 
    			    public Object doInPreparedStatement(PreparedStatement stmt)
    				      throws SQLException, DataAccessException {
    				  stmt.setString(1, loginId);
    				  stmt.execute();
    				  return null;
    				    }
    		 });
        }
       
       /**
        * 得到各個部門及當前員工能授權部門
        * @param loginId
        * @param accountId
        * @param isRoot
        * @return List
       */
    	public List loadPrivilege(String loginId,String accountId,boolean isRoot) {
    		    Object[] args = new Object[] {loginId,accountId};
    		    int[] TYPES = {Types.VARCHAR,Types.VARCHAR};
    		    String sql="";
    		    if(isRoot){
    		     sql=" and a.account_id=?";
    		    }else{
    		     sql=" and a.parent_id=?";
    		    }
                sql += " order by a.account_id asc";
    	    	return getJdbcTemplate().query(SQL_LOAD_PRIVILIGE+sql,args, TYPES, MAPPER_PRIVILEGE);
    	}
        
         /**
         * 根據loginId得到對應的角色
         * @param loginId
         * @return List
         */
        public List getRoleList(String loginId) {
            Object[] args = new Object[] {loginId};
            int[] TYPES = {Types.VARCHAR};
            return getJdbcTemplate().query(SQL_ROLE,args, TYPES, MAPPER_ROLE);
        }

}
