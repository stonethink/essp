package com.wits.util;

import java.net.URL;

public class PathGetter {
    final static String WEB_INF_PATTERN = "WEB-INF/";
    final static String CLASSES_PATTERN = "classes/";
    final static String JAR_PATTERN = "jar!/";
    final static String LIB_PATTERN = "lib/";

    /**
     * �²�:
     * 1.�����class�ļ��ڹ����У��򷵻ع��̵�Ŀ¼
     * 2.�����class�ļ���tomcat��webӦ���У��򷵻�webĿ¼
     *
     * @return String ��"/"��β
     */
    public static String getStandardDir() throws Exception{
        URL url = PathGetter.class.getResource("PathGetter.class");

        //����"file:/D:/essp/essp2005/esspV20.3/essp2/tc/classes/client/WebPathGetter.class"
        String urlStr = url.toString();

        int i = urlStr.indexOf("/");
        int j = urlStr.lastIndexOf("/");
        //����"D:/essp/essp2005/esspV20.3/essp2/tc/classes/client/"
        String classDirPath = urlStr.substring(i+1, j+1);

        if( classDirPath.indexOf(WEB_INF_PATTERN) >= 0 ){
            //��tomcat��web-inf��
            return getWebDir(classDirPath);
        }else{
            //�ڹ���Ŀ¼��
            return getProjectDir(classDirPath);
        }
    }

    //����webĿ¼����"WEB-INF"����һ��Ŀ¼����һ��Ŀ¼����webĿ¼����һ��Ŀ¼
    private static String getWebDir(String classDirPath){
        int i = classDirPath.lastIndexOf(WEB_INF_PATTERN);
        classDirPath = classDirPath.substring(0, i-1);
        i = classDirPath.lastIndexOf("/");
        return classDirPath.substring(0, i+1);
    }

    //���ع���Ŀ¼����"classes"��"lib"����һ��Ŀ¼
    private static String getProjectDir(String classDirPath)throws Exception{
        int i = classDirPath.lastIndexOf(JAR_PATTERN);
        if( i >= 0 ){
            //�ڹ���Ŀ¼��libĿ¼��jar��
            classDirPath = classDirPath.substring(0, i);
            int j = classDirPath.lastIndexOf(LIB_PATTERN);
            if( j >= 0 ){
                return classDirPath.substring(0, j);
            }
        }else{
            //�ڹ���Ŀ¼��classesĿ¼��
            int j = classDirPath.lastIndexOf(CLASSES_PATTERN);
            if( j >= 0 ){
                return classDirPath.substring(0, j);
            }
        }

        throw new Exception("Can't guess the directry.");
    }

    public static void main(String args[]){
        try {
            System.out.println(PathGetter.getStandardDir());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
