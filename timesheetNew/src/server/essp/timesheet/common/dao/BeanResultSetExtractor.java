package server.essp.timesheet.common.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 * <p>Title: BeanResultSetExtractor</p>
 *
 * <p>Description: ResultSetExtractor自动写入Bean数据的实例</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class BeanResultSetExtractor implements ResultSetExtractor {

    private Class beanClass;

    public BeanResultSetExtractor(Class beanClass) {
        this.beanClass = beanClass;
    }

    /**
     * 将ResultSet中的一条数据生成一个beanClass类型的bean
     *      根据ResultSet的字段名（转换成属性名user_login_id  ==>  userLoginId），
     *      向bean中写入数据。
     *
     * @param resultSet ResultSet
     * @return Object
     * @throws SQLException
     * @throws DataAccessException
     */
    public Object extractData(ResultSet rs) throws SQLException,
            DataAccessException {
        return DaoUtil.ResultSet2Bean(beanClass, rs);
    }
}
