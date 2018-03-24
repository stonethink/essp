package com.wits.util;

import java.net.URL;

public class PathGetter {
    final static String WEB_INF_PATTERN = "WEB-INF/";
    final static String CLASSES_PATTERN = "classes/";
    final static String JAR_PATTERN = "jar!/";
    final static String LIB_PATTERN = "lib/";

    /**
     * 猜测:
     * 1.如果本class文件在工程中，则返回工程的目录
     * 2.如果本class文件在tomcat的web应用中，则返回web目录
     *
     * @return String 以"/"结尾
     */
    public static String getStandardDir() throws Exception{
        URL url = PathGetter.class.getResource("PathGetter.class");

        //形如"file:/D:/essp/essp2005/esspV20.3/essp2/tc/classes/client/WebPathGetter.class"
        String urlStr = url.toString();

        int i = urlStr.indexOf("/");
        int j = urlStr.lastIndexOf("/");
        //形如"D:/essp/essp2005/esspV20.3/essp2/tc/classes/client/"
        String classDirPath = urlStr.substring(i+1, j+1);

        if( classDirPath.indexOf(WEB_INF_PATTERN) >= 0 ){
            //在tomcat的web-inf下
            return getWebDir(classDirPath);
        }else{
            //在工程目录下
            return getProjectDir(classDirPath);
        }
    }

    //返回web目录：即"WEB-INF"的上一级目录的上一级目录，即web目录的上一级目录
    private static String getWebDir(String classDirPath){
        int i = classDirPath.lastIndexOf(WEB_INF_PATTERN);
        classDirPath = classDirPath.substring(0, i-1);
        i = classDirPath.lastIndexOf("/");
        return classDirPath.substring(0, i+1);
    }

    //返回工程目录：即"classes"或"lib"的上一级目录
    private static String getProjectDir(String classDirPath)throws Exception{
        int i = classDirPath.lastIndexOf(JAR_PATTERN);
        if( i >= 0 ){
            //在工程目录的lib目录的jar中
            classDirPath = classDirPath.substring(0, i);
            int j = classDirPath.lastIndexOf(LIB_PATTERN);
            if( j >= 0 ){
                return classDirPath.substring(0, j);
            }
        }else{
            //在工程目录的classes目录下
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
