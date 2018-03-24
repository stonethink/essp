package server.essp.tc.attimport;

import server.framework.common.BusinessException;
import net.sf.hibernate.Session;
import server.framework.logic.AbstractBusinessLogic;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.InputStream;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import c2s.essp.tc.nonattend.DtoNonattend;
import com.wits.util.comDate;
import java.util.Calendar;
import server.essp.tc.hrmanage.logic.LgNonattend;
import c2s.essp.common.calendar.WrokTimeFactory;
import c2s.essp.common.calendar.WorkTime;
import c2s.essp.tc.attendance.DtoAttendance;
import server.essp.tc.hrmanage.logic.LgAttendance;
import java.sql.ResultSet;
import java.math.BigDecimal;

public class LgImport extends AbstractBusinessLogic {
    public LgImport() {
    }

    /**
     * ���ݴ�ActionForm��ȡ�õ�EXCEL�ļ����������Ϣ
     * @param im AfImport
     * @return VbImportStat
     */
    public VbImportStat importAtt(AfImport im) throws BusinessException {
        VbImportStat webVo = new VbImportStat();
        try {

            if (im.getLocalfile() != null &&
                im.getLocalfile().getFileName() != null &&
                im.getLocalfile().getFileSize() > 0) {
                //           FileInfo fileInfo = new FileInfo();
                String filename = im.getLocalfile().getFileName();
                //����ͳ������
                int totalRows = 0, importedRows = 0, importedNoAtt = 0, importedAtt = 0;

                if (this.isXLSType(filename)) {

                    InputStream is = im.getLocalfile().getInputStream();
                    try {
                        HSSFWorkbook wb = new HSSFWorkbook(is);
                        HSSFSheet sheet = wb.getSheetAt(0);
                        int rowIndex = 1;
                        HSSFRow arow = sheet.getRow(rowIndex);

                        HSSFCell empIdCell, dateCell, timeAmCell, timePmCell, amDescCell, pmDescCell;
                        empIdCell = arow.getCell((short) 2);

                        String employeeId, date, timeAm, timePm, amDesc, pmDesc;
                        employeeId = empIdCell.getStringCellValue();
                        totalRows++; //��������1
                        // System.out.println(employeeId);
                        while (!employeeId.equals("") && employeeId != null) {
                            dateCell = arow.getCell((short) 4); //����
                            if (dateCell.getStringCellValue() != null) {
                                date = dateCell.getStringCellValue();
                            } else {
                                date = "";
                            }
                            timeAmCell = arow.getCell((short) 5); //�ϰ�ʱ��
                            try {
                                timeAm = timeAmCell.getStringCellValue();
                            } catch (NullPointerException e) {
                                timeAm = "NULL";
                            }
                            amDescCell = arow.getCell((short) 6); //�ϰ�����
                            try {
                                amDesc = amDescCell.getStringCellValue();
                            } catch (NullPointerException e) {
                                amDesc = "NULL";
                            }
                            timePmCell = arow.getCell((short) 7); //�°�ʱ��
                            try {
                                timePm = timePmCell.getStringCellValue();
                            } catch (NullPointerException e) {
                                timePm = "NULL";
                            }
                            pmDescCell = arow.getCell((short) 8); //�°�����
                            try {
                                pmDesc = pmDescCell.getStringCellValue();
                            } catch (NullPointerException e) {
                                pmDesc = "NULL";
                            }

                            DtoNonattend noatt = new DtoNonattend();
                            noatt.setLoginId(employeeId);
                            DtoAttendance att = new DtoAttendance();
                            att.setLoginId(employeeId);
                            att.setAttendanceDate(comDate.toDate(date));

                            Calendar calAmStandard = Calendar.getInstance();
                            Calendar calPmStandard = Calendar.getInstance();
                            calAmStandard.setTime(comDate.toDate(date));
                            calPmStandard.setTime(comDate.toDate(date));
                            calAmStandard.set(Calendar.HOUR, 9);
                            calPmStandard.set(Calendar.HOUR, 18);
                            WorkTime wt = WrokTimeFactory.serverCreate();
                            if (!timeAm.equals("NULL")) { //�ϰ���ʱ��
                                if (amDesc.equals("����")) { //�ϰ���ʱ���ҿ�����ֱ�Ӽ������ʱ��
                                    Calendar calAm = Calendar.getInstance();
                                    calAm.setTime(comDate.toDate(date));
                                    calAm.set(Calendar.HOUR, Integer.parseInt(timeAm.substring(0, 2)));
                                    calAm.set(Calendar.MINUTE, Integer.parseInt(timeAm.substring(3, 5)));
                                    calAm.set(Calendar.SECOND, Integer.parseInt(timeAm.substring(6)));
                                    noatt.setDateFrom(calAmStandard.getTime());
                                    noatt.setDateTo(calAm.getTime());
                                    double totalHours = wt.getWorkHours("09:00", timeAm.substring(0, 5));
                                    //��ʱ��ת��Ϊ������Сʱ
                                    BigDecimal inteBig = new BigDecimal(totalHours);
                                    inteBig = inteBig.setScale(0, BigDecimal.ROUND_CEILING);
                                    totalHours = inteBig.doubleValue();

                                    noatt.setTotalHours(new Double(totalHours));
                                    noatt.setRemark("����");
                                    //�������ݿ�
                                    if (!this.isNoAttExist(employeeId, date + " 09:00:00", date + " " + timeAm)
                                            &&! this.isTravelsExist(employeeId,date + " 09:00:00", date + " " + timeAm)) {
                                        //���DB�в��������ڳ�����Ϣ����Ҳ�����ڡ�������Ϣ�ŵ���
                                        LgNonattend lgnoatt = new LgNonattend();
                                        lgnoatt.add(noatt);
                                        importedNoAtt++;
                                    }
                                } else if (amDesc.equals("NULL")) { //�ϰ���ʱ�����������ж���������
                                    if (pmDesc.equals("����")) {
                                        //�����°��������
                                        Calendar calPm = Calendar.getInstance();
                                        calPm.setTime(comDate.toDate(date));
                                        calPm.set(Calendar.HOUR, Integer.parseInt(timePm.substring(0, 2)));
                                        calPm.set(Calendar.MINUTE, Integer.parseInt(timePm.substring(3, 5)));
                                        calPm.set(Calendar.SECOND, Integer.parseInt(timePm.substring(6)));
                                        noatt.setDateFrom(calPm.getTime());
                                        noatt.setDateTo(calPmStandard.getTime());
                                        double totalHours = wt.getWorkHours(timePm.substring(0, 5), "18:00");

                                        BigDecimal inteBig = new BigDecimal(totalHours);
                                        inteBig = inteBig.setScale(0, BigDecimal.ROUND_CEILING);
                                        totalHours = inteBig.doubleValue();

                                        noatt.setTotalHours(new Double(totalHours));
                                        noatt.setRemark("����");
                                        //�������ݿ�
                                        if (!this.isNoAttExist(employeeId, date + " " + timePm, date + " 18:00:00")
                                                &&! this.isTravelsExist(employeeId, date + " " + timePm, date + " 18:00:00")) {
                                            //���DB�в��������ڳ�����Ϣ����Ҳ�����ڡ�������Ϣ�ŵ���
                                            LgNonattend lgnoatt = new LgNonattend();
                                            lgnoatt.add(noatt);
                                            importedNoAtt++;
                                        }

                                    } else { //���˲���
                                    }
                                } else if (amDesc.equals("�ٵ�")) { //�ϰ���ʱ���ҳٵ�������ٵ�����
                                    att.setAttendanceType("�ٵ�");
                                    att.setRemark(timeAm);
                                    //�������ݿ�
                                    if (!this.isAttExist(employeeId, date, "�ٵ�")) {
                                        //���DB�в����ڴ�����Ϣ�ŵ���
                                        LgAttendance lgatt = new LgAttendance();
                                        lgatt.add(att);
                                        importedAtt++;
                                    }
                                }
                            } else if (timeAm.equals("NULL")) { //�ϰ���ʱ�䣬�����Ϊ����
                                noatt.setDateFrom(calAmStandard.getTime());
                                noatt.setDateTo(calPmStandard.getTime());
                                noatt.setTotalHours(new Double(8.00));
                                noatt.setRemark("����");
                                //�������ݿ�
                                if (!this.isNoAttExist(employeeId, date + " 09:00:00", date + " 18:00:00")
                                        &&! this.isTravelsExist(employeeId, date + " 09:00:00", date + " 18:00:00")) {
                                    //���DB�в��������ڳ�����Ϣ����Ҳ�����ڡ�������Ϣ�ŵ���
                                    LgNonattend lgnoatt = new LgNonattend();
                                    lgnoatt.add(noatt);
                                    importedNoAtt++;
                                }
                            }

                            if (pmDesc.equals("����")) { //��������
                                att.setAttendanceType("����");
                                att.setRemark(timePm);
                                if (!this.isAttExist(employeeId, date, "����")) {
                                    //���DB�в����ڴ�����Ϣ�ŵ���
                                    LgAttendance lgatt = new LgAttendance();
                                    lgatt.add(att);
                                    importedAtt++;
                                }

                            }

//                            System.out.println(employeeId + "~~~" + date.toString() + "~~~" + timeAm.toString());

                            arow = sheet.getRow(++rowIndex);
                            empIdCell = arow.getCell((short) 2);
                            employeeId = empIdCell.getStringCellValue();
                            totalRows++;
                        }
                    } catch (NullPointerException ex) {
                        //ex.printStackTrace();
                        System.out.println("�����Ѷ���");

                    }
                    //��EXCEL�ļ��е����ݴ������
                    //������ص���Ϣ
                    importedRows = importedNoAtt + importedAtt;
                    System.out.println("Total Rows=" + totalRows);
                    System.out.println("Imported Rows=" + importedRows);
                    webVo.setTotal(Integer.toString(totalRows));
                    webVo.setImported(Integer.toString(importedRows));
                    webVo.setImportedNoAtt(Integer.toString(importedNoAtt));
                    webVo.setImportedAtt(Integer.toString(importedAtt));
                } else { //��EXCEL�ļ���ʾ�ļ����Ͳ���ȷ
                    throw new BusinessException("", "File type not matches");
                }
            }

            // return issue.getRid().toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException(ex);
        }
        return webVo;
    }

    /**
     * �ж�һ���ļ��������Ƿ�Ϊ.xls
     * @param file String
     * @return boolean
     */

    public boolean isXLSType(String file) {
        int sp = file.lastIndexOf(".");
        String type = file.substring(sp);
        if (type.equalsIgnoreCase(".xls")) {
            System.out.println(file + " is " + type + " file.");
            return true;
        } else {
            return false;
        }
    }

    /**
     * �ж�ĳ�������Ƿ��Ѿ�������DB����
     * @param loginId String
     * @param fromTime String
     * @param toTime String
     * @return boolean
     */
    public boolean isNoAttExist(String loginId, String fromTime, String toTime) {
        String sql = "select count(*) from tc_nonatten t "
                     + "where t.loginname='" + loginId + "'and"
                     + " to_char(t.timefrom ,'yyyy-mm-dd HH24:MI:SS')='" + fromTime + "' and"
                     + " to_char(t.timeto ,'yyyy-mm-dd HH24:MI:SS')='" + toTime + "'";
        //System.out.println(sql);
        ResultSet rs;
        boolean flag = false;
        try {
            rs = this.getDbAccessor().executeQuery(sql);
            rs.next();
            int t = rs.getInt(1);
            if (t > 0) {
                flag = true;
            }
        } catch (Throwable tx) {
            String msg = "query DB(tc_nonatten) error!";
            log.error(msg);
            throw new BusinessException("", msg, tx);
        }

        return flag;
    }

    /**
     * �ж�ĳ��������Ϣ�Ƿ��Ѵ����ڳ�����Ϣ����
     * @param loginId String
     * @param date String
     * @param type String
     * @return boolean
     */

    public boolean isTravelsExist(String loginId, String fromTime, String toTime) {
        String sql = "select count(*) from tc_outworker t "
                     + "where t.login_id='" + loginId + "'and"
                     + " to_char(t.begin_date ,'yyyy-mm-dd HH24:MI:SS')>='" + fromTime + "' and"
                     + " to_char(t.end_date ,'yyyy-mm-dd HH24:MI:SS')<='" + toTime + "'";
        //System.out.println(sql);
        ResultSet rs;
        boolean flag = false;
        try {
            rs = this.getDbAccessor().executeQuery(sql);
            rs.next();
            int t = rs.getInt(1);
            if (t > 0) {
                flag = true;
            }
        } catch (Throwable tx) {
            String msg = "query DB(tc_outworker) error!";
            log.error(msg);
            throw new BusinessException("", msg, tx);
        }

        return flag;
    }


    /**
     * �ж�ĳ���ٵ���������Ϣ�Ƿ��Ѵ�����DB����
     * @param loginId String
     * @param date String
     * @param type String
     * @return boolean
     */

    public boolean isAttExist(String loginId, String date, String type) {
        String sql = "select count(*) from tc_attendance t "
                     + "where t.loginname='" + loginId + "' and "
                     + "to_char(t.atten_date ,'yyyy-mm-dd')='" + date + "' and "
                     + "t.atten_type='" + type + "'";
        ResultSet rs;
        boolean flag = false;
        try {
            rs = this.getDbAccessor().executeQuery(sql);
            rs.next();
            int t = rs.getInt(1);
            if (t > 0) {
                flag = true;
            }
        } catch (Throwable tx) {
            String msg = "query DB(tc_attendance) error!";
            log.error(msg);
            throw new BusinessException("", msg, tx);
        }

        return flag;
    }

}
