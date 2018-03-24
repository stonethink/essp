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
 * <p>Description: timesheetģ��Dao������</p>
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
     * ����ResultSet���������ݣ���ResultSet2Bean��������beanClass���͵�bean�б�
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
     * ��ResultSet�е�һ����������һ��beanClass���͵�bean
     *      ����ResultSet���ֶ�����ת�����������󣩣���bean��д�����ݡ�
     * @param beanClass Class
     * @param rs ResultSet
     * @return Object
     * @throws SQLException
     */
    public static Object ResultSet2Bean(Class beanClass, ResultSet rs)
            throws SQLException {
        //��֤rs��Ч
        if (rs == null) {
            return null;
        }

        //ʵ����Bean
        Object bean = null;
        try {
            bean = beanClass.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        //�������ֶβ��뵽bean��
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
     * ���ֶ���ת����Bean��������
     *     user_login_id  ==>  userLoginId
     * @param fieldName String
     * @return String
     */
    public static String fieldName2PropertyName(String fieldName) {
        String type = "";
        if (fieldName == null || fieldName.equals("")) {
            return type;
        }

        //����ֶ�û�е��ʷ��飨��������_������ת����Сдֱ�ӷ��ء�
        int iPos = fieldName.indexOf("_");
        if (iPos < 0) {
            return new String(fieldName).toLowerCase();
        }

        //ȥ����_��������������һ����ĸ��Ϊ��д�������ΪСд��
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
