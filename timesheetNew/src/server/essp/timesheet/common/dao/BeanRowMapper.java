package server.essp.timesheet.common.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import c2s.dto.DtoUtil;
import org.springframework.jdbc.core.RowMapper;

/**
 * <p>Title: BeanRowMapper</p>
 *
 * <p>Description: RowMapper�Զ�д��Bean���ݵ�ʵ��</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class BeanRowMapper implements RowMapper {

    private Class beanClass;
    private String indexPropertyName;

    public BeanRowMapper(Class beanClass) {
        this.beanClass = beanClass;
    }

    /**
     * Constructor
     * @param beanClass Class
     * @param indexPropertyName String ��Ҫд���кŵ�������
     */
    public BeanRowMapper(Class beanClass, String indexPropertyName) {
        this.beanClass = beanClass;
        this.indexPropertyName = indexPropertyName;
    }

    /**
     * ��ResultSet�е�һ����������һ��beanClass���͵�bean
     *      ����ResultSet���ֶ�����ת����������user_login_id  ==>  userLoginId����
     *      ��bean��д�����ݡ�
     * @param resultSet ResultSet
     * @param _int int
     * @return Object
     * @throws SQLException
     */
    public Object mapRow(ResultSet rs, int _int) throws SQLException {
        //д���ֶ�����
        Object bean = DaoUtil.ResultSet2Bean(beanClass, rs);
        //д���к�
        if(indexPropertyName != null && "".equals(indexPropertyName.trim()) == false) {
            try {
                DtoUtil.setProperty(bean, indexPropertyName, _int);
            } catch (Exception ex1) {
            }
        }
        return bean;
    }
}
