package com.wits.util;

import java.util.*;

public class StringUtil {

    public static String[] split(String strLine, char cSp) {
        ArrayList v = new ArrayList();

        if ( (strLine == null || strLine.length() == 0)) {
            return null;
        }

        int iCurPos = 0;
        int posB = 0;
        int posE = 0;
        int iLen = strLine.length();
        String strThis = null;

        char[] cA = strLine.toCharArray();

        int iPosB = 0;
        int iPosE = 0;
        //System.out.print("posB = " + posB);
        for (int i = 0; i < iLen; i++) {
            iPosB = strLine.indexOf("\"", i);
            //System.out.println("iPosB = " + iPosB);
            if (iPosB == i) {
                iPosE = strLine.indexOf("\"", i + 1);
                //System.out.println("iPosB = " + iPosB);
                //System.out.println("iPosE = " + iPosE);
                if (i >= iLen) {
                    break;
                }

                if (iPosE != -1) {
                    strThis = strLine.substring(iPosB + 1, iPosE);
                    v.add(strThis);
                    //System.out.println(strThis);
                    //System.out.print("\n");
                    i = iPosE + 1;
                    if (i >= iLen) {
                        break;
                    }
                } else {
                    strThis = strLine.substring(iPosB);
                    v.add(strThis);
                    //System.out.println(strThis);
                    //System.out.print("\n");
                    break;
                }
                posB = i + 1;
                if (posB >= iLen) {
                    break;
                }
                ++i;
                posB = i;
                if (i >= iLen) {
                    break;
                }
            } else {

                //System.out.print(i + "=" + cA[i] +"; ");
                //if(cA[i] == ','){
                if (cA[i] == cSp) {
                    posE = i;
                    //System.out.print("posE = " + posE + ";");
                    if (posE >= posB && posB < iLen) {
                        strThis = new String(cA, posB, posE - posB);
                        //System.out.println(strThis);
                        //System.out.print("\n");
                        v.add(strThis);
                    }
                    //++i;
                    posB = i + 1;
                    posE = i + 1;
                    //System.out.print("posB = " + posB);
                    if (posB >= iLen) {
                        break;
                    }
                }
            }
        }

        if (posE <= iLen) {
            strThis = strLine.substring(posE);
            v.add(strThis);
        }

        if (v != null) {
            String[] sp = new String[v.size()];
            for (int i = 0; i < v.size(); i++) {
                sp[i] = (String) v.get(i);
            }
            return sp;
        } else {
            return null;
        }

    }

    public static String[] split(String sIn, String sSp) {
        ArrayList al = new ArrayList();

        StringTokenizer st = new StringTokenizer(sIn, sSp);
        while (st.hasMoreTokens()) {
            al.add(st.nextToken());
        }

        if (al != null) {
            String[] sp = new String[al.size()];
            for (int i = 0; i < al.size(); i++) {
                sp[i] = (String) al.get(i);
            }
            return sp;
        } else {
            return null;
        }
    }

    public static String[] split(String sIn) {
        ArrayList al = new ArrayList();

        StringTokenizer st = new StringTokenizer(sIn);
        while (st.hasMoreTokens()) {
            al.add(st.nextToken());
        }

        if (al != null) {
            String[] sp = new String[al.size()];
            for (int i = 0; i < al.size(); i++) {
                sp[i] = (String) al.get(i);
            }
            return sp;
        } else {
            return null;
        }
    }

    public static String[] splitS(String sIn, String sSp) {
        ArrayList al = new ArrayList();

        StringTokenizer st = new StringTokenizer(sIn, sSp);
        while (st.hasMoreTokens()) {
            //al.add(st.nextToken());
            String sTmp = "";
            String[] sp = split(st.nextToken());
            for (int i = 0; i < sp.length; i++) {
                sTmp = sTmp + sp[i];
                sTmp = sTmp + " ";
            }
            al.add(sTmp.trim());
        }

        if (al != null) {
            String[] sp = new String[al.size()];
            for (int i = 0; i < al.size(); i++) {
                sp[i] = (String) al.get(i);
            }
            return sp;
        } else {
            return null;
        }
    }

    public static String nvl( Object s ){
        if( s == null ){
            return "";
        }else{
            return s.toString().trim();
        }
    }

    public static boolean toBoolean( Object s ){
        if( s == null ){
            return false;
        }

        String str = s.toString();
        if( str.equalsIgnoreCase( "true" ) == true ){
            return true;
        }else if( str.equalsIgnoreCase( "false" ) == true ){
            return false;
        }else{
            return false;
        }
    }

    //将字符串换转为日历
    public static java.util.Calendar String2Calendar(String s ){
        String tmpStr[] = s.split("[-]");
        if ( tmpStr.length != 3 ) {
            return null;
        } else {
            java.util.Calendar ca = java.util.Calendar.getInstance() ;
            int iYear = Integer.parseInt(tmpStr[0]);
            int iMonth = Integer.parseInt(tmpStr[1]);
            int iDay = Integer.parseInt(tmpStr[2]);
            ca.set(iYear,iMonth-1,iDay,0,0,0);
            ca.set(Calendar.MILLISECOND,0);
            return ca;
        }
    }

    //将日历转换为字符串
    public static String Calendar2String (Calendar ca){
        int iYear = ca.get(Calendar.YEAR);
        int iMonth = ca.get(Calendar.MONTH) + 1;
        int iDay = ca.get(Calendar.DATE);
        String sRet = String.valueOf(iYear);
        if (iMonth < 10) {
            sRet = sRet + "-" + "0" + iMonth;
        } else {
            sRet = sRet + "-" + iMonth;
        }

        if (iDay < 10) {
            sRet = sRet + "-" + "0" + iDay;
        } else {
            sRet = sRet + "-" + iDay;
        }

        return sRet;
    }
    //保留浮点数小数点后指定的位数,没有考虑效率和溢出,add by xr
    public static double trunNum(double num,int decimal){
        if(decimal <= 0)
            return num;
        long pow = (long)Math.pow(10,decimal);
        return (double)((long)(num * pow + 0.5)) / pow ;
    }
    public static void main(String[] args){
        System.out.println(StringUtil.trunNum(100.121,2));
    }
}
