package server.essp.timesheet.common.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import c2s.dto.DtoUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: DaoUtils</p>
 *
 * <p>Description: timesheet模块Dao工具类</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class DaoUtil {

    /**
     * 遍历ResultSet中所有数据，按ResultSet2Bean规则生成beanClass类型的bean列表
     * @param beanClass Class
     * @param rs ResultSet
     * @return List
     * @throws SQLException
     */
    public static List ResultSet2List(Class beanClass, ResultSet rs)
            throws SQLException {
        List list = new ArrayList();

        Object obj = ResultSet2Bean(beanClass, rs);
        while(obj != null) {
            list.add(obj);
            obj = ResultSet2Bean(beanClass, rs);
        }
        return list;
    }

    /**
     * 将ResultSet中的一条数据生成一个beanClass类型的bean
     *      根据ResultSet的字段名（转换成属性名后），向bean中写入数据。
     * @param beanClass Class
     * @param rs ResultSet
     * @return Object
     * @throws SQLException
     */
    public static Object ResultSet2Bean(Class beanClass, ResultSet rs)
            throws SQLException {
        //保证rs有效
        if (rs == null) {
            return null;
        }

        //实例化Bean
        Object bean = null;
        try {
            bean = beanClass.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        //遍历个字段插入到bean中
        ResultSetMetaData rsmd = rs.getMetaData();
        int iCC = rsmd.getColumnCount();
        for (int i = 0; i < iCC; i++) {
            String fieldName = rsmd.getColumnName(i + 1);
            Object value = rs.getObject(i + 1);
            try {
                DtoUtil.setProperty(bean,
                                    fieldName2PropertyName(fieldName),
                                    value);
            } catch (Exception ex1) {
                ex1.printStackTrace();
            }
        }
        return bean;
    }

    /**
     * 将字段名转换成Bean的属性名
     *     user_login_id  ==>  userLoginId
     * @param fieldName String
     * @return String
     */
    public static String fieldName2PropertyName(String fieldName) {
        String type = "";
        if (fieldName == null || fieldName.equals("")) {
            return type;
        }

        //如果字段没有单词分组（不包含“_”），转换成小写直接返回。
        int iPos = fieldName.indexOf("_");
        if (iPos < 0) {
            return new String(fieldName).toLowerCase();
        }

        //去掉“_”，并将其随后的一个字母变为大写，其余均为小写。
        char[] caClassName = fieldName.toCharArray();
        for (int i = 0; i < caClassName.length; i++) {
            if (caClassName[i] == '_') {
                i++;
                if (i < caClassName.length) {
                    char[] ca = new char[1];
                    ca[0] = caClassName[i];
                    type += new String(ca).toUpperCase();
                }
            } else {
                char[] ca = new char[1];
                ca[0] = caClassName[i];
                type += new String(ca).toLowerCase();
            }
        }
        return type;
    }


}
