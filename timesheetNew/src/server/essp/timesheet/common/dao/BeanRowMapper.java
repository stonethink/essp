package server.essp.timesheet.common.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import c2s.dto.DtoUtil;
import org.springframework.jdbc.core.RowMapper;

/**
 * <p>Title: BeanRowMapper</p>
 *
 * <p>Description: RowMapper自动写入Bean数据的实例</p>
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
     * @param indexPropertyName String 需要写如行号的属性名
     */
    public BeanRowMapper(Class beanClass, String indexPropertyName) {
        this.beanClass = beanClass;
        this.indexPropertyName = indexPropertyName;
    }

    /**
     * 将ResultSet中的一条数据生成一个beanClass类型的bean
     *      根据ResultSet的字段名（转换成属性名user_login_id  ==>  userLoginId），
     *      向bean中写入数据。
     * @param resultSet ResultSet
     * @param _int int
     * @return Object
     * @throws SQLException
     */
    public Object mapRow(ResultSet rs, int _int) throws SQLException {
        //写入字段数据
        Object bean = DaoUtil.ResultSet2Bean(beanClass, rs);
        //写入行号
        if(indexPropertyName != null && "".equals(indexPropertyName.trim()) == false) {
            try {
                DtoUtil.setProperty(bean, indexPropertyName, _int);
            } catch (Exception ex1) {
            }
        }
        return bean;
    }
}
