package com.wits.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//����ָ������,timeָʱ����
public class comDate {
    public static String pattenDate = "yyyy-MM-dd";
    public static String pattenTimeH = "HH";
    public static String pattenTimeHM = "HHmm";
    public static String pattenTimeHMS = "HHmmss";//0-23;
    public static String pattenTimestamp = "yyyyMMdd HHmmss";

    public comDate() {
    }

    public static boolean checkTimeHM( String timeStr ){
        if(timeStr.compareTo("23:59")>0){
            return false;
        }
        return comDate.checkDate( timeStr, comDate.pattenTimeHM );
    }

    public static boolean checkTimeHM( String timeStr,String pattenTime){
        if(timeStr.compareTo("23:59")>0){
            return false;
        }
        return comDate.checkDate(timeStr,pattenTime);
    }

    public static boolean checkDate( String dateStr ){
        String pattern=comDate.pattenDate;
        if(dateStr.length()>4 && dateStr.charAt(4)=='/') {
            pattern="yyyy/MM/dd";
        } else if(dateStr.length()>4 && dateStr.charAt(4)=='-') {
            pattern="yyyy-MM-dd";
        } else {
            pattern="yyyyMMdd";
        }

        return comDate.checkDate( dateStr, pattern  );
    }

    public static boolean checkDate( String dateStr, String patten ){
        if( dateStr == null || dateStr.equals( "" ) == true ){
            return false;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(patten);
            java.util.Date utilDate = sdf.parse(dateStr, new java.text.ParsePosition(0));
            if( utilDate == null ){
                return false;
            }else{
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * dateLeft < dateRight   return <0
     * dateLeft = dateRight   return 0
     * dateLeft > dateRight   return >0
     * @param dateLeft Date
     * @param dateRight Date
     * @return int
     */
    public static int compareDate( java.util.Date dateLeft,java.util.Date dateRight ){
        return dateLeft.compareTo( dateRight );
    }
    public static int compareDate( java.sql.Date dateLeft,java.sql.Date dateRight ){
        return dateLeft.compareTo( dateRight );
    }
    public static int compareDate( String dateLeft,String dateRight, String patten ){
        java.util.Date leftDate=comDate.toDate(dateLeft, patten);
        java.util.Date rightDate=comDate.toDate(dateRight, patten);
        if(leftDate!=null && rightDate!=null) {
            return leftDate.compareTo(rightDate);
        }
        throw new RuntimeException("Can't compare null date : dateLeft=["+dateLeft+"] , dateRight=["+dateRight+"] ,pattern="+patten);
    }

    public static int compareDate( String dateLeft,String dateRight ){
        return comDate.compareDate( dateLeft, dateRight, null );
    }

    public static int compareDateByYMD( java.util.Date dateLeft,java.util.Date dateRight ){
        Calendar left = Calendar.getInstance();
        left.setTime(dateLeft);
        Calendar right = Calendar.getInstance();
        right.setTime(dateRight);

        long leftY = left.get(Calendar.YEAR);
        long leftM = left.get(Calendar.MONTH);
        long leftD = left.get(Calendar.DAY_OF_MONTH);

        long rightY = right.get(Calendar.YEAR);
        long rightM = right.get(Calendar.MONTH);
        long rightD = right.get(Calendar.DAY_OF_MONTH);

        long i = ( leftY * 1024 + leftM * 64 + leftD ) -
                ( rightY * 1024 + rightM * 64 + rightD );
        if( i > 0 ){
            return 1;
        }else if( i < 0 ){
            return -1;
        }else{
            return 0;
        }
    }

    //patten�����ֻ�����ڸ�ʽ,��Ҫ��time��ʽ
    //����һ����������Ϣ��java.util.Date����, ��time��ϢΪ0
    public static java.util.Date toDate(String dateStr, String patten) {
        if(patten==null || patten.trim().equals("")) {
            return toDate(dateStr);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(patten);
            java.util.Date utilDate = sdf.parse(dateStr, new java.text.ParsePosition(0));
            return utilDate;
        }
    }

    //Ĭ�ϵ����ڸ�ʽΪcomDate.pattenDate = "yyyy-MM-dd"
    public static java.util.Date toDate(String dateStr) {
        if(dateStr==null || dateStr.trim().equals("")) {
            return null;
        }
        String pattern=comDate.pattenDate;
        if(dateStr.length()>4 && dateStr.charAt(4)=='/') {
            pattern="yyyy/MM/dd";
        } else if(dateStr.length()>4 && dateStr.charAt(4)=='-') {
            pattern="yyyy-MM-dd";
        } else {
            pattern="yyyyMMdd";
        }
        return comDate.toDate( dateStr, pattern );
    }

    public static java.sql.Date toSQLDate(String dateStr) {
        return comDate.toSQLDate(dateStr, comDate.pattenDate);
    }

    //patten�����ֻ�����ڸ�ʽ,��Ҫ��time��ʽ
    //����һ����������Ϣ��java.util.Date����, ��time��ϢΪ0
    public static java.sql.Date toSQLDate(String dateStr, String patten) {
        SimpleDateFormat sdf = new SimpleDateFormat(patten);
        java.util.Date utilDate = sdf.parse(dateStr, new java.text.ParsePosition(0));
        if(utilDate!=null){
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            return sqlDate;
        }
        return null;
    }



    //Ĭ�ϵ����ڸ�ʽΪcomDate.pattenDate = "yyyy-MM-dd"
    public static String dateToString(java.sql.Date date){
        return dateToString(date,pattenDate);
    }

    //����patten��ʽת��
    public static String dateToString(java.sql.Date date,String patten){
        if(date == null || patten == null)
            return "";
        SimpleDateFormat df = new SimpleDateFormat(patten);
        return df.format(date);
    }


    //Ĭ�ϵ����ڸ�ʽΪcomDate.pattenDate = "yyyy-MM-dd"
    public static String dateToString(java.util.Date date) {
        return dateToString(date, pattenDate);
    }

    //����patten��ʽת��
    public static String dateToString(java.util.Date date, String patten) {
        if(date == null || patten == null)
            return "";
        SimpleDateFormat df = new SimpleDateFormat(patten);
        return df.format(date);
    }


    //Ĭ�ϵ����ڸ�ʽΪcomDate.pattenTimeHMS = "HH:mm:ss"
    public static String timeToString(java.sql.Date date) {
        return dateToString(date, pattenTimeHMS);
    }

    //patten��ú����ڸ�ʽ��time��ʽ
    //����һ����������Ϣ��time��Ϣ��java.util.Date����
    public static java.util.Date toTimestamp(String timestampStr, String patten) {
        return comDate.toDate( timestampStr, patten );
    }

    //Ĭ�ϵ�timeStamp��ʽΪcomDate.pattenTimestamp = "yyyy-MM-dd HH:mm:ss"
    public static java.util.Date toTimestamp(String timestampStr) {
        return comDate.toTimestamp( timestampStr, comDate.pattenTimestamp );
    }

    /**
     * �ĸ�����toSqlDate, toSqlDate(), toSqlTimestamp, toSqlTimestamp����java.sql���Ķ���
     * ��toDate, toDate, toTimestamp, toTimestamp��Ӧ
     * ��һ�㲻ͬ����:java.util.Date�����ں�time��Ϣ,��java.sql.Dateֻ��������Ϣ,����time��Ϣ.
     * java.sql.Timestamp�����ں�time��Ϣ.
     * @param dateStr String
     * @param patten String
     * @return Date
     */

    /**
     * ���ִ�ת�ɱ�׼��ʱ���ִ���HH:mm�����,
     * NUll����""����""��
     * ������ִ����޷�����ת��Ϊ"00:00"���
     * @author Lu Liang
     * @param HHMM String
     * @return String
     */
    public static String convertNumToTime(String HHMM) {
      String standardTimeStr = "";
      if(HHMM != null && HHMM.equals("") == false){
        int iHH = 0;
        int iMM = 0;
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setMinimumIntegerDigits(2);
        nf.setMaximumIntegerDigits(2);
        try {
          int iHHMM = Integer.parseInt(HHMM);
          if (HHMM.length() <= 2) {
            iHH = iHHMM;
          } else if (HHMM.length() == 3) {
            iHH = Integer.parseInt(HHMM.substring(0, 2));
            iMM = Integer.parseInt(HHMM.substring(2));
          } else if (HHMM.length() >= 4) {
            iHH = Integer.parseInt(HHMM.substring(0, 2));
            iMM = Integer.parseInt(HHMM.substring(2, 4));
          }
        } catch (NumberFormatException ex) {
          String[] timeStrings = HHMM.split(":");
          if (timeStrings.length >= 2) {
            try {
              iHH = Integer.parseInt(timeStrings[0]);
              iMM = Integer.parseInt(timeStrings[1]);
            } catch (NumberFormatException ex1) {
              System.out.println("It's NOT a Time String at all!");
            }
          }
        } finally {
          if (iHH >= 24) {
            iHH = iHH % 24;
          }
          if (iMM >= 60) {
            iMM = iMM % 60;
          }
          standardTimeStr = nf.format(iHH) + ":" + nf.format(iMM);
          if (standardTimeStr.length() == 5 && checkTimeHM(standardTimeStr, "HH:mm") == false) {
            standardTimeStr = "";
          }
        }
      }
      return standardTimeStr;
    }

      public static java.sql.Timestamp toSqlTimestamp(String timestampStr, String patten) {
        java.util.Date utilDate = comDate.toTimestamp(timestampStr, patten);
        if (utilDate == null) {
          return null;
        } else {
          return new java.sql.Timestamp(utilDate.getTime());
        }
      }

      public static java.sql.Timestamp toSqlTimestamp(String timestampStr) {
        return comDate.toSqlTimestamp(timestampStr, comDate.pattenTimestamp);
      }

      //���ִ��õ�java.sql.Timestamp����
      public static String formatSqlTimestamp(java.util.Date sqlTimestamp, String patten) {
        java.util.Date utilDate = sqlTimestamp;
        SimpleDateFormat sdf = new SimpleDateFormat(patten);
        StringBuffer dateSb = new StringBuffer();
        sdf.format(utilDate, dateSb, new java.text.FieldPosition(0));
        return dateSb.toString();
      }

      //Ĭ�ϵ�timestamp��ʽΪ"yyyy-MM-dd HH:mm:ss"
      public static String formatSqlTimestamp(java.util.Date sqlTimestamp) {
        return comDate.formatSqlTimestamp(sqlTimestamp, comDate.pattenTimestamp);
      }

      /**
       * ��ȡ��ֹ��֮��������������ֹʱ����ͬ����������1
       * @param Start Calendar
       * @param End Calendar
       * @return int
       */
      public static int getBetweenDays(Calendar Start, Calendar End) {
        Calendar cStart = (Calendar) Start.clone();
        cStart.set(Calendar.HOUR, 0);
        cStart.set(Calendar.MINUTE, 0);
        cStart.set(Calendar.SECOND, 0);
        cStart.set(Calendar.MILLISECOND, 0);
        cStart.set(Calendar.AM_PM, Calendar.AM);

        Calendar cEnd = (Calendar) End.clone();
        cEnd.set(Calendar.HOUR, 0);
        cEnd.set(Calendar.MINUTE, 0);
        cEnd.set(Calendar.SECOND, 0);
        cEnd.set(Calendar.MILLISECOND, 0);
        cEnd.set(Calendar.AM_PM, Calendar.AM);

        long lRet = cEnd.getTimeInMillis() - cStart.getTimeInMillis();
        int iRet = (int) (lRet/( 24 * 60 * 60 * 1000 ));
        iRet ++;

        return iRet;
    }

    /**
      * ��ȡ��ֹ��֮��������������ֹʱ����ͬ����������1
      * @param Start Date
      * @param End Date
      * @return int
      */
     public static int getBetweenDays(Date Start, Date End) {
         Calendar cStart = Calendar.getInstance();
         Calendar cEnd = Calendar.getInstance();
         cStart.setTime(Start);
         cEnd.setTime(End);
         return getBetweenDays(cStart,cEnd);
     }
    public static String getSysDate() {
                return dateToString(new java.util.Date(),"yyyy-MM-dd");
    }
}
