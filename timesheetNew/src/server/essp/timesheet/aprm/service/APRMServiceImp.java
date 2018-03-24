/*
 * Created on 2007-10-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.aprm.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import server.essp.common.ldap.LDAPUtil;
import server.essp.common.mail.ContentBean;
import server.essp.common.mail.SendHastenMail;
import server.essp.framework.logic.AbstractESSPLogic;
import server.essp.security.service.role.IRoleService;
import server.essp.timesheet.account.dao.IAccountDao;
import server.essp.timesheet.aprm.dao.IAPRMDao;
import server.essp.timesheet.aprm.form.AfAPRMTSImport;
import server.essp.timesheet.aprm.lock.dao.IImportLockDao;
import server.essp.timesheet.aprm.viewbean.VbAprmImportInfo;
import server.essp.timesheet.code.codevalue.dao.ICodeValueDao;
import server.essp.timesheet.period.dao.IPeriodDao;
import server.essp.timesheet.rmmaint.dao.IRmMaintDao;
import server.essp.timesheet.weeklyreport.dao.ITimeSheetDao;
import server.essp.timesheet.weeklyreport.dao.ITimeSheetP3ApiDao;
import server.framework.common.BusinessException;
import c2s.essp.common.user.DtoRole;
import c2s.essp.common.user.DtoUserBase;
import c2s.essp.common.user.DtoUserInfo;
import c2s.essp.timesheet.approval.DtoApprovalMail;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import com.wits.util.comDate;
import db.essp.timesheet.TsAccount;
import db.essp.timesheet.TsCodeValue;
import db.essp.timesheet.TsHumanBase;
import db.essp.timesheet.TsImportTemp;
import db.essp.timesheet.TsTimesheetDay;
import db.essp.timesheet.TsTimesheetDetail;
import db.essp.timesheet.TsTimesheetMaster;
/**
 *���������뵽���r�����n��
 *author:TuBaohui
 */
public class APRMServiceImp extends AbstractESSPLogic implements IAPRMService {  
        public static final String vmFile = "mail/template/ts/APRMImportSuccessByPMO.htm";
        private IAPRMDao aprmDao;
        private IPeriodDao periodDao;
        private ICodeValueDao codeValueDao;
        private ITimeSheetP3ApiDao  timeSheetApiDao;
        private ITimeSheetDao timeSheetDao;
        private IAccountDao accountDao;
        private IImportLockDao importLockDao;
        private IRmMaintDao rmMaintDao;
        private IRoleService roleService;
        private int scale = 2;
        private String encode="BIG5";

        /**
         * �����ַ���
         * @param is
         * @return List
         * @throws IOException
         */
        public VbAprmImportInfo saveDateFromInputStream(AfAPRMTSImport af,String loginId) {
                InputStream stream = null;
                List list = new ArrayList();
                VbAprmImportInfo vbInfo = new VbAprmImportInfo();
                try {
                     stream = af.getLocalFile().getInputStream();
                     InputStreamReader reader = new InputStreamReader(stream, encode);
                     BufferedReader br = new BufferedReader(reader);
                     list = getDataListFromInputStreamData(br);
                     stream.close(); 
                     //����TEMP����ʱҪ��ɾ��TEMP��֮ǰ���ڵ�����
                     aprmDao.deleteAllTemp();
                     //����������Ĕ���LIST���浽IMPORT_TEMP��ȥ
                     Map dateMap = saveImportDataTemp(list);
                     String beginStr = (String)dateMap.get("beginDate");
                     String endStr = (String)dateMap.get("endDate");
                     Date beginDate = comDate.toDate(beginStr,comDate.pattenDate);
                     Date endDate = comDate.toDate(endStr,comDate.pattenDate);
                     if(importLockDao.isPeriodLocked(beginDate,endDate)){
                         String show = "BeginDate: " + beginStr + " and endDate: "+endStr+" is locked!";
                         throw new BusinessException("Date is locked!", show);
                     }
                     //��IMPORT_TEMP�еõ��������α��浽TimeSheetMaster,TimeSheetDetail,TimeSheetDay��
                     int timeSheetSize =  saveTimeSheetData();
                     //�������ɹ�����ָ����PMO���ʼ�
                     sendMailForPMO(beginStr,endStr,loginId);
                     //�������翪ʼʱ��
                     vbInfo.setBeginDate(beginStr);
                     //�����������ʱ��
                     vbInfo.setEndDate(endStr);
                     //���浽�ݴ浵���ܵļ�¼��
                     vbInfo.setTotalRows(String.valueOf(list.size()));
                     //������TimeSheetMaster���ܼ�¼��
                     vbInfo.setTimeSheetRows(String.valueOf(timeSheetSize));
                     //����ɹ������óɹ���Ϣ
                     vbInfo.setRemark("Success!");
                     return vbInfo;
                } catch (FileNotFoundException e1) {
                    log.error(e1.getMessage(),e1);
                    //����쳣���쳣��Ϣ��ʾ��ҳ����
                    vbInfo.setRemark(e1.getMessage());
                    return vbInfo;
                } catch (IOException e1) {
                    log.error(e1.getMessage(),e1);
                    vbInfo.setRemark(e1.getMessage());
                    return vbInfo;
                }catch(Exception e){
                    log.error(e.getMessage(),e);
                    vbInfo.setRemark(e.getMessage());
                    return vbInfo;
                }
        }
     
        /**
         * �����ʼ�����Ӧ��PMO
         * @param begin
         * @param end
         * @param loginId
         */
        private void sendMailForPMO(String begin,String end,String loginId){
                DtoApprovalMail approvalMail = new DtoApprovalMail();
                approvalMail.setBegin(begin);
                approvalMail.setEnd(end);
                approvalMail.setApprovaler(loginId);
                sendMail(loginId,vmFile,"APRM Import Success",approvalMail);
        }
       
        /**
         * ��������������ж��Ƿ���ϸ�ʽ���������͵�Ҫ�������������׳��쳣��Ϣ
         * @param data
         * @return List
         */
        private List getDataListFromInputStreamData(BufferedReader br){
                String show ="";
                List list = new ArrayList();
                String data = getDataFromBR(br);
                int row = 0;
                while(data != null){
                    if(data.equals("")) {
                       data = getDataFromBR(br);
                        continue;
                    }
                    TsImportTemp temp = new TsImportTemp();
                    String loginId = data.substring(0,9);
                    if(loginId == null || loginId.trim().equals("")){
                        show ="row: " + (row + 1) +" " + "LoginId " + loginId + " is null";
                        throw new BusinessException("LoginId is null!", show);
                    }
                    //��AD�в�ѯԱ���Ƿ����
                    findUser(loginId,row);
                    temp.setLoginId(loginId);
                    Map map = getBeginAndEndDate(data);
                    String bDate = (String)map.get("beginStr");
                    String eDate =(String)map.get("endStr");
                    String beginYM = bDate.substring(0,7);
                    String endYM = eDate.substring(0,7);
                    //�������Ŀ�ʼ�ͽ���ʱ�䲻��ͬһ�����򱨴�
                    if(!beginYM.equals(endYM)){
                        show = "row: " + (row + 1) +" LoginId "+loginId+","+"BeginDate " + bDate + "" +
                                " and endDate "+eDate+" are not same month!";
                        throw new BusinessException("BeginDate and endDate are not same month!", show);
                    }
                    Date begin = comDate.toDate(bDate, comDate.pattenDate);
                    Date end = comDate.toDate(eDate,comDate.pattenDate);
                    DtoTimeSheetPeriod dtoPeriod = findDtoTSPeriod(loginId,begin,end,row);
                    temp.setTsId(dtoPeriod.getTsId());
                    temp.setBeginDate(begin);
                    temp.setEndDate(end);
                    
                    String acntId = data.substring(26,34);
                    //��Ŀ���Ų�����
                    if(acntId == null || "".equals(acntId.trim())){
                        show = "row: " + (row + 1) +" LoginId "+loginId+","+"AccountId " + acntId + " is null!";
                        throw new BusinessException("AccountId is null!", show);
                    }
                    TsAccount acnt = getAccount(loginId,acntId,row);
                    temp.setAcntId(acntId);
                    temp.setAcntRid(acnt.getRid());
                    
                    String workCode = data.substring(34,36);
                    //�жϹ��������Ƿ����
                    TsCodeValue codeValue = getCodeValue(loginId,workCode,row);
                    temp.setCodeValueRid(codeValue.getRid());
                    temp.setIsLeaveType(codeValue.getIsLeaveType());
                    temp.setWorkCode(workCode);
                    
                    String sumWorkHours = data.substring(36,46);
                    Double sumHours = getHours(loginId,"SumWorkHours",sumWorkHours,row);
                    temp.setSumStandardHours(sumHours);
                    
                    String totalHours = data.substring(46,54).trim();
                    Double toHours = getHours(loginId,"TotalHours",totalHours,row);
                    temp.setTotalHours(toHours);
                    
                    String foodAmount = data.substring(54,58).trim();
                    Double foodAm = getHours(loginId,"FoodAmount",foodAmount,row);;
                    temp.setFoodAmount(foodAm);
                    
                    String trafficAmount = data.substring(58,62).trim();
                    Double traAmount = getHours(loginId,"TrafficAmount",trafficAmount,row);
                    temp.setTrafficAmount(traAmount);
                    
                    String mondayHours = data.substring(62,67).trim();
                    Double monHours = getHours(loginId,"MondayHours",mondayHours,row);
                    temp.setMondayHours(monHours);
                    
                    String tuesdayHours = data.substring(67,72).trim();
                    Double tuHours = getHours(loginId,"TuesdayHours",tuesdayHours,row);
                    temp.setTuesdayHours(tuHours);
                    
                    String wednesdayHours = data.substring(72,77).trim();
                    Double wedHours = getHours(loginId,"WednesdayHours",wednesdayHours,row);
                    temp.setWednesdayHours(wedHours);
                    
                    String thursdayHours = data.substring(77,82).trim();
                    Double thurHours = getHours(loginId,"ThursdayHours",thursdayHours,row);
                    temp.setThursdayHours(thurHours);
                    
                    String fridayHours = data.substring(82,87).trim();
                    Double friHours = getHours(loginId,"FridayHours",fridayHours,row);     
                    temp.setFridayHours(friHours);
                    
                    String saturdayHours = data.substring(87,92).trim();
                    Double satHours = getHours(loginId,"SaturdayHours",saturdayHours,row);  
                    temp.setSaturdayHours(satHours);
                    
                    String sundayHours = data.substring(92,97).trim();
                    Double sunHours = getHours(loginId,"SundayHours",sundayHours,row);  
                    temp.setSundayHours(sunHours);
                    
                    String remark = data.substring(98);
                    temp.setRemark(remark);
                    
                    list.add(temp);
                    data = getDataFromBR(br);
                    row++;
                }
                return list;
        }
        
        /**
         * ���ݿ�ʼ�ͽ���ʱ������Ƿ����
         * @param beginDate
         * @param endDate
         * @return DtoTimeSheetPeriod
         */
        private DtoTimeSheetPeriod findDtoTSPeriod(String loginId,Date beginDate,
                Date endDate,int row){
                try {
                    List list = periodDao.getPeriodByDate(beginDate,endDate);
                    String begStr = comDate.dateToString(beginDate,comDate.pattenDate);
                    String endStr = comDate.dateToString(endDate,comDate.pattenDate);
                    if(list == null || list.size() == 0){
                        String show ="row: " + (row + 1)+" LoginId "+loginId+","+"" +
                                " BeginDate " + begStr + " and endDate "+endStr+" are inexistent!";
                        throw new BusinessException("BeginDate and endDate are not inexistent!", show);
                    }
                    DtoTimeSheetPeriod dtoPeriod = (DtoTimeSheetPeriod)list.get(0);
                    return dtoPeriod;
                } catch (Exception e) {
                    throw new BusinessException("IMP0004",e.getLocalizedMessage(),e);
                }
        }
         
        /**
         * ���ݹ����������CODE_VALUE���Ƿ���ڴ˼�¼
         * @param workCode
         * @return TsCodeValue
         */
        private TsCodeValue getCodeValue(String loginId,String workCode,int row){
                TsCodeValue codeValue = null;
                try{
                  codeValue = codeValueDao.isExistWorkCode(workCode);
                  if(codeValue == null || codeValue.getRid() == null){
                    String show = "row: "+ (row + 1)+ " LoginId "+loginId+"," +" WorkCode " + workCode + " is inexistent!";
                    throw new BusinessException("WorkCode is inexistent", show);
                  }
                  }catch(Exception e){
                     throw new BusinessException("IMP0007",e.getLocalizedMessage(),e);
                  }
                  return codeValue;
        }
        
        /**
         * ������Ŀ���Ų����Ƿ���TS_ACCOUNT�д��ڴ���Ŀ
         * @param acntId
         * @return TsAccount
         */
        private TsAccount getAccount(String loginId,String acntId,int row){
                TsAccount acnt = null;
                try{
                acnt = accountDao.loadByAccountId(acntId.trim());
                if(acnt == null || acnt.getRid() == null){
                    String show ="row: " + (row + 1) + " "+"LoginId "+loginId+"," 
                    + "AccountId " + acntId + " is inexistent!";
                    throw new BusinessException("AccountId is inexistent!", show);
                }
                }catch(Exception e){
                    throw new BusinessException("IMP0005", e.getLocalizedMessage(),e);
                }
                return acnt;
        }
        
        /**
         * ��ȡһ������
         * @param br
         * @return String
         */
        private String getDataFromBR(BufferedReader br){
                try {
                   String data = br.readLine();
                   return data;
                } catch (IOException e1) {
                    log.error(e1.getMessage());
                    new BusinessException("Read Line error","Read Line error", e1);
                    return null;
                }
        }
    
        /**
         * ����������ַ�����ȡ��ʼʱ��ͽ���ʱ��
         * @param data
         * @return Map
         */
        private Map getBeginAndEndDate(String data){
                  Map map = new HashMap();
                  String startDate = data.substring(10,18);
                  String newStart = startDate.substring(0,4)+"-" + startDate.
                  substring(4,6)+"-"+startDate.substring(6,8);
                  String endDate = data.substring(18,26);
                  String newEnd = endDate.substring(0,4)+"-" + endDate.
                  substring(4,6)+"-"+endDate.substring(6,8);
                  map.put("beginStr",newStart);
                  map.put("endStr",newEnd);
                  return map;
        }
    
        /**
         * ��AD�в����Ƿ���������Ա�����ţ���������������쳣
         * @param loginId
         */
        public void findUser(String loginId,int row){
                TsHumanBase humanBase = rmMaintDao.loadHumanById(loginId);
                if(humanBase == null){
                    String show ="row: " + (row + 1) +" "+ "LoginId " + loginId + " is inexistent!";
                    throw new BusinessException("IMP0001", show);
                }
            }
    
        /**
         * �Д������Ĺ��r�Ƿ�锵�֣�����Ƿǔ��քt�׮���
         * @param name
         * @param hoursString
         * @return Double
         */
        private Double getHours(String loginId,String hourName,String hoursString,int row){
                Double hours;
                try{
                    hours = Double.valueOf(hoursString);
                }catch(Exception e){
                    String show ="row: " + (row + 1) + " " +"LoginId "+loginId+","+hourName + " " 
                    + hoursString + " is not numeric!";
                    throw new BusinessException("Hours is not numeric!", show);
                }
                return hours;
        }
    
        /**
         * ��LIST�д�ŵĔ������浽IMPORT_REPORT��
         * @param dataList
         */
        private Map saveImportDataTemp(List dataList) {
                Date beginDate = null;
                Date endDate = null;
                Map dateMap = new HashMap();
                for(int i=0;i<dataList.size();i++){
                    TsImportTemp temp = (TsImportTemp)dataList.get(i);
                    //�õ�������_ʼ�r�g������ĽY���r�g
                    if(beginDate==null || beginDate.after(temp.getBeginDate())){
                        beginDate = temp.getBeginDate();
                    }
                    if(endDate == null || endDate.before(temp.getEndDate())){
                        endDate = temp.getEndDate();
                    }
                    aprmDao.save(temp);
                }
                String beginStr = comDate.dateToString(beginDate,comDate.pattenDate);
                String endStr = comDate.dateToString(endDate,comDate.pattenDate);
               //��������_ʼ�r�g������ĽY���r�g����MAP��
                dateMap.put("beginDate",beginStr);
                dateMap.put("endDate",endStr);
                return dateMap;
        }
 
        /**
         * saveTimeSheetData
         * @return int
         * @throws Exception 
         */
        public int saveTimeSheetData() throws Exception {
                List list = aprmDao.getImportInfoList();
                int tempSize = 0;
                if(list != null){
                    tempSize = list.size();
                }
                for(int i=0;i<tempSize;i++){
                    Object[] obj =(Object[])list.get(i);
                    String employeeId = (String)obj[0];
                    Date beginDate = (Date)obj[1];
                    Date endDate = (Date)obj[2];
                    String begStr = comDate.dateToString(beginDate,comDate.pattenDate);
                    String endStr = comDate.dateToString(endDate,comDate.pattenDate);
                    Double sumStandardHours = (Double)obj[3];
                    List dateList = getDateList(beginDate,endDate);
                    List standHoursList = timeSheetApiDao.listStandarHours(employeeId,dateList);
                    if(standHoursList != null && standHoursList.size() > 0){
                        Double standardHour = (Double)standHoursList.get(standHoursList.size() - 1);
                        //�������ı�׼��ʱ��Primavera6�еı�׼��ʱ��һ���򱨴�
                        if(sumStandardHours.compareTo(standardHour)!=0){
                            String show = employeeId + " Imported SumStandardHours " + ":"
                            + sumStandardHours + " is not equal StandardHour:"+standardHour+
                            " in Primavera 6 between "+begStr +" and "+endStr;
                            throw new BusinessException("SumStandardHours is error!", show);
                        }
                    }
                    try {
                         saveData(employeeId,beginDate,endDate,i+1);
                    } catch (Exception e) {        
                        throw new BusinessException("IMP0009", e.getLocalizedMessage(),e);
                    }
                }
                return tempSize;
        }

        /**
         * �����ʼ��������Ա
         * @param loginId String
         * @param ccId String[]
         * @param vmFile String
         * @param title String
         * @param obj Object
         */
        public void sendMail(final String loginId,
                             final String vmFile, final  String title,
                             final Object obj) {
                final HashMap hm = new HashMap();
                String[] roleIds = {DtoRole.ROLE_TSM};
                List pmoList = roleService.listLoginIdUnderRole(roleIds);
                try {
                    ArrayList al = new ArrayList();
                    al.add(obj);
        
                    LDAPUtil ldapUtil = new LDAPUtil();
                    DtoUserInfo dtoUser = ldapUtil.findUser("all", loginId);
                    if (dtoUser != null) {
                        String userMail = dtoUser.getEmail();
                        String userName = dtoUser.getUserName();
                        ContentBean cb = new ContentBean();
                        cb.setUser(userName);
                        cb.setEmail(getUserMail(pmoList));
                        cb.setMailcontent(al);
                        cb.setCcAddress(userMail);
                        hm.put(userName, cb);
                        final SendHastenMail shMail = new SendHastenMail();
                        Thread t = new Thread(new Runnable() {
                            public void run() {
                                shMail.sendHastenMail(vmFile, title, hm);
                            }
                        });
                        try {
                            t.start();
                        } catch (Throwable tx) {
                            tx.printStackTrace();
                        }
                    }
                } catch (Throwable tx) {
                    throw new BusinessException("error.logic.ApprovalAssistServiceImp.noPmApproval",
                                                "error get all Email data", tx);
                }
        }
        
        /**
         * �õ������ʼ���PMO�����伯�� 
         * @param loginIds
         * @return String
         */
        private String getUserMail(List loginIds) {
                if(loginIds == null) return "";
                LDAPUtil ldapUtil = new LDAPUtil();
                Map ccMap = new HashMap();
                String mails = "";
                for (int i = 0; i < loginIds.size(); i++) {
                    DtoUserBase dtoBase = (DtoUserBase)loginIds.get(i);
                    String loginId = dtoBase.getUserLoginId();
                    if(loginId == null || "".equals(loginId)) continue;
                    DtoUserInfo dtoUser = ldapUtil.findUser("all", loginId);
                    if(dtoUser == null) continue;
                    String mail = dtoUser.getEmail();
                    if (ccMap.containsKey(loginId) == false) {
                        if("".equals(mails)) {
                            mails = mail;
                        } else {
                            mails += "," + mail;
                        }
                        ccMap.put(loginId, dtoUser.getEmail());
                    }
                }
                return mails;
        }
    
        /**
         * �������ݵ�TimesheetMaster��TimesheetDetail��TimesheetDay
         * @param loginId
         * @param beginDate
         * @param endDate
         * @param dateList
         * @param tsId
         * @throws Exception 
         */
        private void saveData(String loginId,Date beginDate,Date endDate,
                int row) throws Exception{
                //���ݿ�ʼ�ͽ���ʱ�仮������
                List periodList = periodDao.getPeriodByDate(beginDate,endDate);
                List mRidList = new ArrayList();//����N���^�g�t���½�N��TimesheetMaster��mRidList�Á���MasterRID����
                for(int k=0;k<periodList.size();k++){
                  DtoTimeSheetPeriod dtoPeriod = (DtoTimeSheetPeriod)periodList.get(k);
                  timeSheetDao.deleteTimeSheetData(loginId,dtoPeriod.getTsId());
                  //��TimeSheetMaster,TimeSheetDetail,TimeSheetDay�в�������
                   //Save TimesheetMaster
                  TsTimesheetMaster master = new TsTimesheetMaster();
                  master.setBeginDate(dtoPeriod.getBeginDate());
                  master.setEndDate(dtoPeriod.getEndDate());
                  master.setLoginId(loginId);
                  master.setTsId(dtoPeriod.getTsId());
                  master.setStatus(DtoTimeSheet.STATUS_APPROVED);
                  master.setStatusDate(new Date());
                  timeSheetDao.addTimeSheetMaster(master);
                  mRidList.add(master.getRid());
                }
        //       Save TimesheetDetail
                 List detailList = aprmDao.getImportTemp(loginId,beginDate,endDate);
                 for(int i=0;i<detailList.size();i++){
                    List dRidList = new ArrayList();//���detailRid�ļ���
                    TsImportTemp temp = (TsImportTemp)detailList.get(i);
                    for(int j=0;j<mRidList.size();j++){ 
                     Long mRid = (Long)mRidList.get(j);
                     TsTimesheetDetail detail = new TsTimesheetDetail();
                     detail.setAccountRid(temp.getAcntRid());
                     detail.setAccountName(temp.getAcntId());
                     detail.setCodeValueRid(temp.getCodeValueRid());
                     detail.setCodeValueName(temp.getWorkCode());
                     detail.setIsLeaveType(temp.getIsLeaveType());
                     detail.setTsRid(mRid);
                     detail.setStatus(DtoTimeSheet.STATUS_APPROVED);
                     detail.setJobDescription(temp.getRemark());
                     timeSheetDao.addTimeSheetDetail(detail);
                     dRidList.add(detail.getRid());
                     }
                     List dList = getDateList(beginDate,endDate);
                     saveTimeSheetDay(loginId,temp,dRidList,dList,periodList,row);
                  }
             }
        
    /**
     * ����TIMESHEETDAY
     * @param temp
     * @param detailRid
     * @param dateList
     */
        private void saveTimeSheetDay(String loginId,TsImportTemp temp,
                List dRidList,List dateList,List periodList,int row){
                  int r = 0;
                  Long dRid = (Long)dRidList.get(r);
                  DtoTimeSheetPeriod dtoPeriod = (DtoTimeSheetPeriod)periodList.get(r);
                  Date date = dtoPeriod.getEndDate();
                  for(int j=0;j<dateList.size();j++){
                    Date workDate = (Date)dateList.get(j);
                    TsTimesheetDay day = new TsTimesheetDay();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(workDate);
                    int week = calendar.get(Calendar.DAY_OF_WEEK);
                    //���������һ�죬�t���H���r��G�Č��H���r-ǰ����Č��H���r�ĺϣ��Ӱ๤�r��G�Ĺ��r-ǰ����Ӱ๤�r�ĺ�
                    Double workHours = getHours(temp,week);
                    day.setWorkHours(workHours);
                    day.setTsDetailRid(dRid);
                    //���r�g���ڄ��֣��Ўׂ��r�g���ھ��Ўׂ�dRid 
                    if(workDate.compareTo(date)==0 && periodList.size()-1 > r){
                        r++;
                        dRid = (Long)dRidList.get(r);
                        dtoPeriod = (DtoTimeSheetPeriod)periodList.get(r);
                        date = dtoPeriod.getEndDate();
                    }
                    if(day.getWorkHours() != null && day.getWorkHours() > 0){//�����ʱ��Ϊ0����������TimeSheetDay��
                      day.setWorkDate(workDate);
                      timeSheetDao.addTimeSheetDay(day);
                    }
                }
        }
    
        /**
         * ��������ȡ��TsImportTemp��Ӧ����һ��ʱ��
         * @param temp
         * @param week
         * @return Double
         */
        private Double getHours(TsImportTemp temp,int week){
            Double workHours = Double.valueOf(0);
            switch(week){
                case Calendar.SUNDAY:
                    workHours = temp.getSundayHours();
                     break;
                case Calendar.MONDAY:
                    workHours = temp.getMondayHours();
                      break;
                case Calendar.TUESDAY:
                    workHours = temp.getTuesdayHours();
                     break;
                case Calendar.WEDNESDAY:
                    workHours = temp.getWednesdayHours();
                     break;    
                case Calendar.THURSDAY:
                    workHours = temp.getThursdayHours();
                     break;
                case Calendar.FRIDAY:
                    workHours = temp.getFridayHours();
                     break;
                case Calendar.SATURDAY:
                    workHours = temp.getSaturdayHours();
                     break;
                default:
                     break;
            }
            return workHours;
        }
        
        /**
         * ����ʱ�����佫���ڻ��ֳ�������LIST��
         * @param beginDate
         * @param endDate
         * @return List
         */
        private List getDateList(Date beginDate, Date endDate){
                List dateList = new ArrayList();
                dateList.add(beginDate);
                while(endDate.after(beginDate)){
                     Calendar calendar = Calendar.getInstance();
                     calendar.setTime(beginDate);
                     calendar.add(Calendar.DATE, 1);
                     Date date = calendar.getTime();
                     dateList.add(date);
                     beginDate = date;
                }
                return dateList;
        }

    public ITimeSheetDao getTimeSheetDao() {
        return timeSheetDao;
    }

    public void setTimeSheetApiDao(ITimeSheetP3ApiDao timeSheetApiDao) {
        this.timeSheetApiDao = timeSheetApiDao;
    }
    
    public void setPeriodDao(IPeriodDao periodDao) {
        this.periodDao = periodDao;
    }

    public void setCodeValueDao(ICodeValueDao codeValueDao) {
        this.codeValueDao = codeValueDao;
    }
    
    public void setScale(int scale) {
        this.scale = scale;
    }

    public void setTimeSheetDao(ITimeSheetDao timeSheetDao) {
        this.timeSheetDao = timeSheetDao;
    }

    public void setAprmDao(IAPRMDao aprmDao) {
        this.aprmDao = aprmDao;
    }


    public void setRmMaintDao(IRmMaintDao rmMaintDao) {
        this.rmMaintDao = rmMaintDao;
    }
    
    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public void setImportLockDao(IImportLockDao importLockDao) {
        this.importLockDao = importLockDao;
    }

    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }


}
