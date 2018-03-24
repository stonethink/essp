package server.essp.timesheet.preference.dao;

import java.sql.SQLException;
import java.util.List;

import db.essp.timesheet.TsParameter;

import net.sf.hibernate.*;

import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import com.wits.util.ThreadVariant;

public class PreferenceDaoImp extends HibernateDaoSupport implements IPreferenceDao{

    /**
     * 从数据库中读取当前用户的系统设置信息记录
     * @return TsParameter
     */
    public TsParameter loadPreference() {
		return loadPreferenceByLoginId(getLoginId());
    }
    
    /**
     * 从数据库中读取用户的系统设置信息记录
     * @param loginId String
     * @return TsParameter
     */
    public TsParameter loadPreferenceByLoginId(String loginId) {
    	String sql = "from TsParameter p where lower(p.site)=lower((select h.site from TsHumanBase h where lower(h.employeeId)=lower(?)))";
		List list = this.getHibernateTemplate().find(sql, loginId);
		if(list != null && list.size() > 0) {
			return (TsParameter)list.get(0);
		}
		return new TsParameter();
    }

    /**
     * 向数据库写入系统设置信息记录
     * @param tsParameter TsParameter
     */
    public void saveOrUpdatePreference(TsParameter tsParameter) {
        this.getHibernateTemplate().saveOrUpdate(tsParameter);
    }
    /**
     * 根据site从数据库中读取系统设置信息记录
     */
	public TsParameter loadPreferenceBySite(String site) {
		String sql = "from TsParameter where lower(site)=lower(?)";
		List list = this.getHibernateTemplate().find(sql, site);
		if(list != null && list.size() > 0) {
			return (TsParameter)list.get(0);
		}
		return new TsParameter();
	}

	public List<String> listSitesInTsHumanbase() {
		final String sql = "select upper(t.site) from TsHumanBase t group by t.site order by t.site";
		List<String> list = (List<String>) this.getHibernateTemplate()
		                                        .execute(new HibernateCallback(){
			public Object doInHibernate(Session session) 
			                          throws HibernateException, SQLException {
				Query q = session.createQuery(sql);
				return q.list();
			}
			
		});
		return list;
	}
	
	private String getLoginId() {
		ThreadVariant thread = ThreadVariant.getInstance();
		return (String)thread.get("LOGIN_ID");
	}
}
