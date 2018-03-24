package c2s.dto;

import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.*;
import java.util.Map;
import java.util.Iterator;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.sql.*;
import java.util.Vector;


/**
 * @author Stone 20050223
 *
 */

//import org.apache.commons.beanutils.PropertyUtils;

public class DtoUtil {

    /**
     * 检测属性是否存在
     * @param obj Object
     * @param propertyName String
     * @return boolean
     */
    public static boolean hasProperty(Object obj, String propertyName) {
        if (obj == null) {
            return false;
        }
        if (propertyName == null || propertyName.equals("")) {
            return false;
        }

        try {
            Class objClass = obj.getClass();
            String propertyNameA = null;
            if (propertyName.length() > 1) {
                propertyNameA = propertyName.substring(0, 1).toUpperCase() +
                                propertyName.substring(1);
            } else {
                propertyNameA = propertyName.toUpperCase();
            }

            String methodName = null;
            Method m = null;

            try {
                methodName = "get" + propertyNameA;
                m = objClass.getMethod(methodName);
            } catch (Exception ex) {
            }

            if (m == null) {
                try {
                    methodName = "is" + propertyNameA;
                    m = objClass.getMethod(methodName);
                } catch (Exception ex) {
                }
            }

            if (m == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 获取Bean(obj)中对应propertyName的属性值
     * @param obj Object
     * @param propertyName String
     * @throws Exception
     * @return Object
     */
    public static Object getProperty(Object obj, String propertyName) throws
        Exception {
//      return PropertyUtils.getProperty(obj, propertyName);
        if (obj == null) {
            throw new Exception("Input Object is Null!");
            //return null;
        }
        if (propertyName == null || propertyName.equals("")) {
            throw new Exception("Input propertyName is Null!");
            //return null;
        }

        String propertyNameA = null;
        if (propertyName.length() > 1) {
            propertyNameA = propertyName.substring(0, 1).toUpperCase() +
                            propertyName.substring(1);
        } else {
            propertyNameA = propertyName.toUpperCase();
        }

        Class objClass = obj.getClass();

        try {
            String methodName = null;
            Method m = null;

            try {
                methodName = "get" + propertyNameA;
                m = objClass.getMethod(methodName);
            } catch (Exception ex) {
            }

            if (m == null) {
                try {
                    methodName = "is" + propertyNameA;
                    m = objClass.getMethod(methodName);
                } catch (Exception ex) {
                }
            }

            Object value = m.invoke(obj);
            return value;
        } catch (Exception e) {
//            throw e;
            return null;
        }
    }


    /**
     * 设置Bean(obj)中对应propertyName的属性值(propertyValue)，
     * 要求对于Bean中每个属性的set方法只能定义一个,且程序会自动将传入的Value转换成Bean中对应set方法的数据类型
     * @param obj Object
     * @param propertyName String
     * @param propertyValue Object
     * @throws Exception
     */
    public static void setProperty(Object obj, String propertyName,
                                   Object propertyValue) throws Exception {
//      PropertyUtils.setProperty(obj,propertyName,propertyValue);
        if (obj == null) {
            throw new Exception("Input Object is Null!");
            //return null;
        }
        if (propertyName == null || propertyName.equals("")) {
            throw new Exception("Input propertyName is Null!");
            //return null;
        }

        String propertyNameA = null;
        if (propertyName.length() > 1) {
            propertyNameA = propertyName.substring(0, 1).toUpperCase() +
                            propertyName.substring(1);
        } else {
            propertyNameA = propertyName.toUpperCase();
        }

        Class objClass = obj.getClass();
        String methodName = "set" + propertyNameA;

        try {
            //Method[] ma =  objClass.getMethods();
            Method[] ma = objClass.getMethods();

            for (int i = 0; i < ma.length; i++) {
                Method m = ma[i];
                //System.out.println("i=" + i + ";m=" + m.getName() +";acc="+m.isAccessible());
                if (methodName.equals(m.getName()) == true) {
                    if (m.getParameterTypes().length == 1) {
                        Class[] pTypes = new Class[1];
                        pTypes[0] = m.getParameterTypes()[0];

                        Object[] arg = new Object[1];
                        if (propertyValue != null) {
                            if (pTypes[0].isInstance(propertyValue)) {
                                arg[0] = propertyValue;
                            } else {
                                arg[0] = ConvDataType.toOtherType(propertyValue,
                                    pTypes[0]);
                            }
                        } else {
                            arg[0] = null;
                        }
                        m.invoke(obj, arg);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static void copyProperties(Object dest, Object orig) throws
        Exception {
        copyProperties(dest, orig, null);
    }

    public static void copyProperties(Object dest, Object orig,
                                      String[] ignorProperties) throws
        Exception {
        if (dest == null || orig == null) {
            return;
        }

        if (orig instanceof Map && !(dest instanceof Map)) {
            copyMapToBean(dest, (Map) orig, ignorProperties);
        } else if (!(orig instanceof Map) && dest instanceof Map) {
            copyBeanToMap((Map) dest, orig, ignorProperties);
        } else if (orig instanceof Map && dest instanceof Map) {
            copyMapToMap((Map) dest, (Map) orig, ignorProperties);
        } else if (orig instanceof java.sql.ResultSet) {
            copyResultsetToBean(dest, (java.sql.ResultSet) orig,
                                ignorProperties);
        } else {
            copyBeanToBean(dest, orig, ignorProperties);
        }
    }

    public static void copyMapToBean(Object dest, Map orig) throws Exception {
        copyMapToBean(dest, orig, null);
    }

    public static void copyMapToBean(Object dest, Map orig,
                                     String[] ignorProperties) throws Exception {
        HashSet propSet = new HashSet();
        if (ignorProperties != null) {
            for (int i = 0; i < ignorProperties.length; i++) {
                propSet.add(ignorProperties[i]);
            }
        }
        for (Iterator it = orig.keySet().iterator(); it.hasNext(); ) {
            String fieldName = (String) it.next();
            if (!propSet.contains(fieldName)) {
                Object fieldValue = orig.get(fieldName);
                setProperty(dest, fieldName, fieldValue);
            }
        }
    }

    public static void copyBeanToMap(Map dest, Object orig) throws Exception {
        copyBeanToMap(dest, orig, null);
    }

    public static void copyBeanToMap(Map dest, Object orig,
                                     String[] ignorProperties) throws Exception {
        if (dest == null || orig == null) {
            return;
        }

        HashSet propSet = new HashSet();
        if (ignorProperties != null) {
            for (int i = 0; i < ignorProperties.length; i++) {
                propSet.add(ignorProperties[i]);
            }
        }

        String[] propNames = getPropertyNames(orig);
        if (propNames != null) {
            for (int i = 0; i < propNames.length; i++) {
                String fldName = propNames[i];
                if (!propSet.contains(fldName)) {
                    try {
                        Object fldValue = getProperty(orig, fldName);
                        dest.put(fldName, fldValue);
                    } catch (Exception ex) {
                    }
                }
            }
        }
    }

    public static void copyMapToMap(Map dest, Map orig) throws Exception {
        copyMapToMap(dest, orig, null);
    }

    public static void copyMapToMap(Map dest, Map orig,
                                    String[] ignorProperties) throws Exception {
        HashSet propSet = new HashSet();
        if (ignorProperties != null) {
            for (int i = 0; i < ignorProperties.length; i++) {
                propSet.add(ignorProperties[i]);
            }

            if (orig != null && dest != null) {
                for (Iterator it = orig.keySet().iterator(); it.hasNext(); ) {
                    Object key = it.next();
                    Object value = orig.get(key);
                    if (!propSet.contains(key) &&
                        !propSet.contains(key.toString())) {
                        dest.put(key, value);
                    }
                }
            }
        } else {
            dest.putAll(orig);
        }
    }


    /**
     * 按目标Bean的属性列表，将来源Bean中同名属性的对应属性值复制到目标Bean
     * @param dest Object（目标Bean）
     * @param orig Object（来源Bean）
     */
    public static void copyBeanToBean(Object dest, Object orig)
//            throws Exception
    {
        copyBeanToBean(dest, orig, null);
    }

    /**
     * 按目标Bean的属性列表，将来源Bean中同名属性的对应属性值复制到目标Bean
     * @param dest Object（目标Bean）
     * @param orig Object（来源Bean）
     * @param ignorProperties 被忽略的属性
     * @throws Exception
     */
    public static void copyBeanToBean(Object dest, Object orig,
                                      String[] ignorProperties) {
        if (dest == null || orig == null) {
            return;
        }
        HashSet propSet = new HashSet();
        if (ignorProperties != null) {
            for (int i = 0; i < ignorProperties.length; i++) {
                propSet.add(ignorProperties[i]);
            }
        }
        String sNoCopy = "";
        String[] allProperties = getPropertyNames(orig);
        if (allProperties != null) {
            for (int i = 0; i < allProperties.length; i++) {
                if (!propSet.contains(allProperties[i])) {
                    try {
                        Object value = getProperty(orig, allProperties[i]);
                        setProperty(dest, allProperties[i], value);
                    } catch (Exception ex1) {
                        if (sNoCopy.equals("")) {
                            sNoCopy = allProperties[i];
                        } else {
                            sNoCopy = sNoCopy + "," + allProperties[i];
                        }

                    }
                }
            }
        }
    }

    /**
     * 从orig中复制dest所需要的数据
     * @param dest Object
     * @param orig Object
     */
    public static void copyBeanToBeanByDestProperties(Object dest, Object orig) {
        String[] propertyNames = getPropertyNames(dest);
        copyBeanToBeanByProperties(dest, orig, propertyNames);
    }

    /**
     * 按目标copyProperties的属性列表，将来源Bean中同名属性的对应属性值复制到目标Bean
     * @param dest Object
     * @param orig Object
     * @param copyProperties String[]
     */
    public static void copyBeanToBeanByProperties(Object dest, Object orig,
                                      String[] copyProperties) {
        for (int i = 0; i < copyProperties.length; i++) {
            try {
                Object value = getProperty(orig, copyProperties[i]);
                setProperty(dest, copyProperties[i], value);
            } catch (Exception ex1) {
            }
        }
    }

    public static void copyResultsetToBean(Object dest, java.sql.ResultSet orig) {
        copyResultsetToBean(dest, orig, null);
    }

    private static String fieldName2PropertyName(String[] properties,
                                                 String fieldName) {
        String propertyName = null;
        String copyFieldName = fieldName;
        if (fieldName.indexOf("_") >= 0) {
            copyFieldName = fieldName.replaceAll("_", "");
        }

        if (properties != null) {
            for (int i = 0; i < properties.length; i++) {
                if (properties[i].equalsIgnoreCase(copyFieldName)) {
                    propertyName = properties[i];
                    break;
                }
            }
        }
        return propertyName;
    }

    public static void copyResultsetToBean(Object dest, java.sql.ResultSet orig,
                                           String[] ignorProperties) {
        if (dest == null || orig == null) {
            return;
        }
        HashSet propSet = new HashSet();
        if (ignorProperties != null) {
            for (int i = 0; i < ignorProperties.length; i++) {
                propSet.add(ignorProperties[i]);
            }
        }

        try {
            String[] allObjProperties = getPropertyNames(dest);

            java.sql.ResultSetMetaData meta = orig.getMetaData();
            for (int i = 0; i < meta.getColumnCount(); i++) {
                String columnName = meta.getColumnName(i + 1);
                String propertyName = fieldName2PropertyName(allObjProperties,
                    columnName);
                if (propertyName != null &&
                    !propSet.contains(propertyName) &&
                    !propSet.contains(columnName)) {
                    try {
                        Object value = orig.getObject(i + 1);
                        setProperty(dest, propertyName, value);
                    } catch (Exception e) {

                    }
                }
            }
        } catch (Exception ex) {
        }
    }

    public static java.util.Date getSqlDate(java.sql.ResultSet rs,
                                            String fieldName) {
        if (rs == null) {
            return null;
        }
        try {
            java.util.Date date = (java.util.Date) rs.getObject(fieldName);
            return date;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 依据目标Bean　Class类型生成输出的新的List，即会依据输入的inList，
     * 将每行的记录复制到按目标Bean类型所生成的Bean记录中，并加入新的List返回给使用者
     * @param inList List
     * @param destClass Class
     * @throws Exception
     * @return List
     */
    public static List list2List(List inList, Class destClass) throws Exception {
        if (inList == null) {
            return null;
        }
        int iSize = inList.size();
        List outList = new ArrayList(iSize);

        for (int i = 0; i < iSize; i++) {
            Object obj = null;
            try {
                obj = destClass.newInstance();
            } catch (Exception e) {
                throw e;
            }
            try {
                copyProperties(obj, inList.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
            outList.add(obj);
        }
        return outList;
    }

    /**
     * 依据目标Bean　Class类型生成输出的新的List，即会依据输入的inArray，
     * 将每行的记录(PropertyMap中的属性)复制到目标Bean，并加入新的List返回给使用者
     * @param inArray Object[]
     * @param destClass Class
     * @param PropertyMap Map <orig propertyName, dest propertyName>
     * @return List
     * @throws Exception
     */
    public static List array2List(Object[] inArray,
            Class destClass, Map<String, String> PropertyMap) throws Exception {
        int size = inArray.length;
        List destList = new ArrayList(size);
        for(int i = 0; i < size; i++) {
            Object dest = destClass.newInstance();
            copyBeanToBeanWithPropertyMap(dest, inArray[i], PropertyMap);
            destList.add(dest);
        }
        return destList;
    }

    /**
     * 将源Bean(PropertyMap中的属性)复制到目标Bean
     * @param dest Object
     * @param orig Object
     * @param PropertyMap Map
     * @throws Exception
     */
    public static void copyBeanToBeanWithPropertyMap(Object dest,Object orig,
                             Map<String,String> PropertyMap) throws Exception {
        Iterator<String> keys = PropertyMap.keySet().iterator();
        while (keys.hasNext()) {
            String origProperty = keys.next();
            String destProperty = PropertyMap.get(origProperty);
            Object value = getProperty(orig, origProperty);
            setProperty(dest, destProperty, value);
        }
    }

    /**
     * 依据目标Bean　Class类型生成输出的新的List，即会依据输入的inArray，
     * 将每行的记录复制到按目标Bean属性需要copy Properties，并加入新的List返回给使用者
     * @param inArray Object[]
     * @param destClass Class
     * @return List
     * @throws Exception
     */
    public static List array2ListBaseDestProperties(Object[] inArray,
            Class destClass) throws Exception {
        if(inArray == null) {
            return null;
        }
        int iSize = inArray.length;
        List outList = new ArrayList(iSize);

        for(int i = 0; i < iSize; i++) {
            Object obj = null;
            try {
                obj = destClass.newInstance();
            } catch (Exception e) {
                throw e;
            }
            copyBeanToBeanByDestProperties(obj, inArray[i]);
        }
        return outList;
    }

    /**
     * 将data中所有对象转换成选项列表
     * @param data List
     * @return Vector
     */
    public static Vector list2Combo(List data,String nameProperty,String valueProperty ){
        Vector result = new Vector();
        if(data == null || data.size() == 0)
            return result;
        for(int i = 0;i < data.size() ;i ++){
            Object obj = data.get(i);
            try {
                String name = (String) DtoUtil.getProperty(obj, nameProperty);
                Object value = DtoUtil.getProperty(obj, valueProperty);
                DtoComboItem item = new DtoComboItem(name,value);
                result.add(item);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return result;
    }
    /**
     * 检查List中的OP是否为被更改过，只要有一条记录被更改就返回为True，否则为false
     * @param inList List
     * @return boolean
     */
    public static boolean checkChanged(List inList) {
        boolean isChanged = false;
        if (inList == null) {
            return false;
        }
        for (int i = 0; i < inList.size(); i++) {
            Object obj = inList.get(i);
            if (obj instanceof IDto) {
                IDto dto = (IDto) obj;
                if (dto.isChanged() == true) {
                    isChanged = true;
                }
                break;
            }
        }
        return isChanged;
    }


    public static String dumpBean(Object bean) {
        String rtn = "";
        if (bean == null) {
            return "null";
        }
        try {
            rtn = bean.getClass().getName() + " : \t";
            if (bean instanceof IDto) {
                rtn += "op:" + ((IDto) bean).getOp();
            }
            rtn += "\r\n";

            Field[] fields = bean.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
//                if( f.getDeclaringClass().equals( bean.getClass() ) == true ){
                String fn = f.getName();
                rtn += "\t\t" + fn + " :" + DtoUtil.getProperty(bean, fn) +
                    "\t--\t" + f.getType() + "\r\n";
//                }
            }

        } catch (SecurityException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rtn;
    }

    public static Class getPropertyType(Object bean, String propertyName) {
        Class returnType = null;
        try {
            String fldName = propertyName.substring(0, 1).toUpperCase() +
                             propertyName.substring(1);
            String methodName1 = "get" + fldName;
            String methodName2 = "is" + fldName;
            Class cls = bean.getClass();
            Method[] methods = cls.getMethods();
            if (methods != null) {
                for (int i = 0; i < methods.length; i++) {
                    try {
                        Method method = methods[i];
                        String getMethodName = method.getName();
                        if ((getMethodName.equals(methodName1) ||
                             getMethodName.equals(methodName2))
                            &&
                            (method.getParameterTypes() == null ||
                             method.getParameterTypes().length == 0)) {
                            returnType = method.getReturnType();
                            break;
                        }
                    } catch (Exception ex) {
                    }
                }
            }
        } catch (Exception e) {
        }
        return returnType;
    }

    public static String[] getPropertyNames(Object bean) {
        ArrayList propertyNameList = new ArrayList();
        String[] propertyNames = null;
        try {
            Class cls = bean.getClass();
            Method[] methods = cls.getMethods();
            if (methods != null) {
                for (int i = 0; i < methods.length; i++) {
                    String fldName = "";
                    try {
                        Method method = methods[i];
                        String getMethodName = method.getName();
                        if (getMethodName.startsWith("get")
                            &&
                            (method.getParameterTypes() == null ||
                             method.getParameterTypes().length == 0)) {
                            fldName = method.getName().substring(3);
                            fldName = fldName.substring(0, 1).toLowerCase() +
                                      fldName.substring(1);
                            propertyNameList.add(fldName);
                        } else if (getMethodName.startsWith("is")
                                   &&
                                   (method.getParameterTypes() == null ||
                                    method.getParameterTypes().length == 0)) {
                            fldName = method.getName().substring(2);
                            fldName = fldName.substring(0, 1).toLowerCase() +
                                      fldName.substring(1);
                            propertyNameList.add(fldName);
                        }
                    } catch (Exception ex) {
                    }
                }
            }
        } catch (Exception e) {
        }
        if (propertyNameList.size() > 0) {
            propertyNames = new String[propertyNameList.size()];
            for (int i = 0; i < propertyNameList.size(); i++) {
                propertyNames[i] = (String) propertyNameList.get(i);
            }
        }
        return propertyNames;
    }


    /**
     * 解码encodeParam操作后的字符串
     * @param paramString String
     * @throws Exception
     * @return Map
     */
    public static Map decodeParam(String paramString) throws Exception {
        if (paramString == null) {
            return null;
        }

        String[] strList = paramString.split("Z");
        byte[] byteBuffer = new byte[strList.length];

        for (int i = 0; i < strList.length; i++) {
            byteBuffer[i] = Byte.parseByte(strList[i]);
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(byteBuffer);
        GZIPInputStream zis = new GZIPInputStream(bis);
        Map result = null;
        ObjectInputStream ois = new ObjectInputStream(zis);
        result = (Map) ois.readObject();
        ois.close();

        zis.close();
        bis.close();

        return result;
    }

    /**
     * 将Map类型的参数编码成字符串
     * @param params Map
     * @throws Exception
     * @return String
     */
    public static String encodeParam(Map params) throws Exception {
        StringBuffer buf = new StringBuffer("");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream zos = new GZIPOutputStream(bos);
        ObjectOutputStream oos = new ObjectOutputStream(zos);
        oos.writeObject(params);
        oos.flush();
        zos.finish();
        bos.flush();

        byte[] data = bos.toByteArray();

        if (data != null) {
            for (int i = 0; i < data.length; i++) {
                String ch = Byte.toString(data[i]);
                buf.append(ch + "Z");
            }
        }

        oos.close();
        zos.close();
        bos.close();

        return buf.toString();
    }

    public static Object deepClone(Object obj) {
        if (obj == null) {
            System.out.println(
                "Warn: object is null, so return null deepClone object");
            return null;
        }
        try {
            Map map = new HashMap();
            map.put("obj", obj);
            String str = encodeParam(map);
            Map newMap = decodeParam(str);
            return newMap.get("obj");
        } catch (Exception e) {
            System.out.print("This obj must implement java.io.Serializable : ");
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public static String getComboNameByValue(Object value, List comboList) {
        String sName = "";
        if (value == null) {
            return sName;
        }
        boolean bRtn = false;
        Iterator i = comboList.iterator();
        while (i.hasNext()) {
            Object other = i.next();
            if (!(other instanceof DtoComboItem)) {
                bRtn = false;
            } else {
                DtoComboItem castOther = (DtoComboItem) other;
                bRtn = value.equals(castOther.getItemValue());
                if (bRtn) {
                    sName = castOther.getItemName();
                    break;
                }
            }
        }
        return sName;
    }

    public static Object getComboRelationByValue(Object value, List comboList) {
        Object oRelation = null;
        if (value == null) {
            return oRelation;
        }
        boolean bRtn = false;
        Iterator i = comboList.iterator();
        while (i.hasNext()) {
            Object other = i.next();
            if (!(other instanceof DtoComboItem)) {
                bRtn = false;
            } else {
                DtoComboItem castOther = (DtoComboItem) other;
                bRtn = value.equals(castOther.getItemValue());
                if (bRtn) {
                    oRelation = castOther.getItemRelation();
                    break;
                }
            }
        }
        return oRelation;
    }

    /**
     * 本方法检查origObj是否有变更
     * 注意 changedObj 必须具有 origObj中的全部属性
     * @param changedObj Object
     * @param origObj Object
     * @param ignorProperties String[]
     * @return boolean
     */
    public static boolean checkModified(Object changedObj, Object origObj) {
        return checkModified(changedObj, origObj, null);
    }

    /**
     * 本方法检查origObj是否有变更
     * 注意 changedObj 必须具有 origObj中的全部属性
     * @param changedObj Object
     * @param origObj Object
     * @param ignorProperties String[]
     * @return boolean
     */
    public static boolean checkModified(Object changedObj, Object origObj,
                                        String[] ignorProperties) {
        boolean flag = false;
        String[] allProperties = getPropertyNames(origObj);
        HashSet ignorSet = new HashSet();
        if (ignorProperties != null) {
            for (int i = 0; i < ignorProperties.length; i++) {
                ignorSet.add(ignorProperties[i]);
            }
        }
        if (allProperties != null) {
            for (int i = 0; i < allProperties.length; i++) {
                if (!ignorSet.contains(allProperties[i])) {
                    try {
                        Object origValue = getProperty(origObj, allProperties[i]);
                        Object changedValue = getProperty(changedObj,
                            allProperties[i]);
                        if (origValue != null && changedValue != null &&
                            !origValue.equals(changedValue)) {
                            flag = true;
                            if (((IDto) origObj).getOp() != null &&
                                ((IDto) origObj).getOp().equals(IDto.OP_INSERT)) {
                                ((IDto) changedObj).setOp(IDto.OP_INSERT);
                            } else if (((IDto) origObj).getOp() != null &&
                                       ((IDto) origObj).getOp().equals(IDto.
                                OP_DELETE)) {
                                ((IDto) changedObj).setOp(IDto.OP_DELETE);
                            } else {
                                ((IDto) changedObj).setOp(IDto.OP_MODIFY);
                            }

                            break;
                        }
                    } catch (Exception ex) {
                    }
                }
            }
        }
        return flag;
    }

    public static void setPreparedParam(PreparedStatement stmt,
                                                 int parameterIndex,
                                                 int SqlType,
                                                 Object value) throws
        SQLException {
        if(value!=null) {
            stmt.setObject(parameterIndex,value);
        } else {
            stmt.setNull(parameterIndex,SqlType,null);
        }
    }

}
