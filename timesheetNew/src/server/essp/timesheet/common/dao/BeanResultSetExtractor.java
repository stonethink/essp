package server.essp.timesheet.common.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 * <p>Title: BeanResultSetExtractor</p>
 *
 * <p>Description: ResultSetExtractor�Զ�д��Bean���ݵ�ʵ��</p>
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
     * ��ResultSet�е�һ����������һ��beanClass���͵�bean
     *      ����ResultSet���ֶ�����ת����������user_login_id  ==>  userLoginId����
     *      ��bean��д�����ݡ�
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
