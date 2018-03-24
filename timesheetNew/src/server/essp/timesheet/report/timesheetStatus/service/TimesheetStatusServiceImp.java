/*
 * Created on 2007-11-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.timesheetStatus.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import server.essp.timesheet.account.dao.IAccountDao;
import server.essp.timesheet.activity.resources.dao.IResourceDao;
import server.essp.timesheet.period.dao.IPeriodDao;
import server.essp.timesheet.report.LevelCellStyleUtils;
import server.essp.timesheet.report.timesheetStatus.dao.ITimesheetStatusDao;
import server.essp.timesheet.rmmaint.dao.IRmMaintDao;
import server.essp.timesheet.weeklyreport.dao.IAttLeaveDao;
import server.essp.timesheet.weeklyreport.dao.ITimeSheetP3ApiDao;
import server.framework.common.BusinessException;
import c2s.dto.DtoTreeNode;
import c2s.dto.DtoUtil;
import c2s.dto.ITreeNode;
import c2s.essp.timesheet.report.DtoFilledRate;
import c2s.essp.timesheet.report.DtoStatusHuman;
import c2s.essp.timesheet.report.DtoTimesheetStatus;
import c2s.essp.timesheet.report.DtoTsStatusQuery;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import com.wits.excel.ICellStyleSwitch;
import com.wits.util.comDate;
import db.essp.timesheet.TsAccount;
import db.essp.timesheet.TsHumanBase;

/**
 * <p>Title:TimesheetStatusServiceImp</p>
 *
 * <p>Description: ���r�Π�B���</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tubaohui
 * @version 1.0
 */
public class TimesheetStatusServiceImp implements ITimesheetStausService {
    
        private ITimesheetStatusDao tsStatusDao;
        
        private ITimeSheetP3ApiDao  timeSheetApiDao;
    
        private IRmMaintDao rmMaintDao;
    
        private IPeriodDao periodDao;
        
        private IResourceDao iresDao;
        
        private IAccountDao accountDao;
        
        private IAttLeaveDao attLeaveDao;
        
        private boolean excelDto = false;
        
        private String checkLeaveHours = "true";
        
        private List siteList = null;
        
        private Map activeMap = new HashMap();
        private Map unfillMap = new HashMap();
        private Map submitMap = new HashMap();
        private Map pmApproveMap = new HashMap();
        private Map rmApproveMap = new HashMap();
        private Map approveMap = new HashMap();
        private Map rejectMap = new HashMap();
        private Map isfillMap = new HashMap();
        private Map empMap = new HashMap();
        private Map unfillYesMap = new HashMap();
        private Map fillRateMap = new HashMap();
        private Map loginIdMap = new HashMap();
        private Map leaveHoursMap = new HashMap();
        private Map calendarMap = new HashMap();
        private Map calendarNameMap = new HashMap();
        private Map unfillByDateMap = new HashMap();
        private Map needFillTSBySiteMap = new HashMap();
        private Map unfillTSBySiteMap = new HashMap();
        private Map accountMap = new HashMap();
        private Map empNumInfoMap = new HashMap();
        
        private int sumActiveFNum = 0;
        private int sumUnfillFNum = 0;
        private int sumSubmitFNum = 0;
        private int sumPmAppFNum = 0;
        private int sumAppFNum = 0;
        private int sumRejectFNum = 0;
        private int sumNeedFillNum = 0;
        private int sumRmAppFNum = 0;
       
        private int sumActiveUnfNum = 0;
        private int sumUnfillUnfNum = 0;
        private int sumSubmitUnfNum = 0;
        private int sumPmAppUnfNum = 0;
        private int sumAppUnfNum = 0;
        private int sumRejectUnfNum = 0;
        private int sumRmAppUnfNum = 0;
    
        /**
         * ��ʼ��MAP
         * @throws Exception
         */
        public void initMap() throws Exception {
            	Map map = iresDao.getEmployInfoMap();
                isfillMap = (Map)map.get("isFill");
                //ÿ���˶�Ӧ��Canlendar
                calendarNameMap = (Map)map.get("CanlendarName");
                //ͨ��CanlendarName�õ���Ӧ��Canlendar
                calendarMap = (Map)map.get("Canlendar");
        }
        
        /**
         * �����r�g�^�g�õ����ϲ�ԃ�l����ӛ䛣�������B�˔��yӋ��δ���δ�ύ�����ύ���ь�����PM�ь��������ܽ^�Ĺ��r����Ϣ��
         * 
         * @param dtoQuery
         * @return Map
         * @throws Exception
         */
        public Map queryByPeriod(DtoTsStatusQuery dtoQuery,
                String loginId,Boolean isExp) throws Exception{
                activeMap.clear();
                unfillMap.clear();
                submitMap.clear();
                pmApproveMap.clear();
                approveMap.clear();
                rejectMap.clear();
                isfillMap.clear();
                empMap.clear();
                fillRateMap.clear();
                unfillYesMap.clear();
                loginIdMap.clear();
                calendarNameMap.clear();
                calendarMap.clear();
                unfillByDateMap.clear();
                needFillTSBySiteMap.clear();
                unfillTSBySiteMap.clear();
                accountMap.clear();
                empNumInfoMap.clear();
                rmApproveMap.clear();
                String status = "";
                Map resultMap = new HashMap();
                boolean unfillExist = false;
                boolean tsExist = false;
                if(dtoQuery.getIsDeptQuery() && (dtoQuery.getDeptId() == null
                        || dtoQuery.getDeptId().equals(""))){
                    return resultMap;
                }
                setEmpInfoMap();
                if(!dtoQuery.getIsDeptQuery() && dtoQuery.getSite() == null){
                    siteList = rmMaintDao.getSiteFromHumanBase("''");
                }
                Date begin = dtoQuery.getBeginDate();
                Date end = dtoQuery.getEndDate();
               
                Map map = iresDao.getEmployInfoMap();
                isfillMap = (Map)map.get("isFill");
                    //ÿ���˶�Ӧ��Canlendar
                calendarNameMap = (Map)map.get("CanlendarName");
                    //ͨ��CanlendarName�õ���Ӧ��Canlendar
                calendarMap = (Map)map.get("Canlendar");
                //�����ѯ�����Ӳ�����õ��Ӳ��ż���
                if(dtoQuery.getIsDeptQuery() && dtoQuery.getIsSub()){
                    String deptIdStr = getDeptIds(dtoQuery.getDeptId());
                    dtoQuery.setDeptIdStr(deptIdStr);
                }else if(dtoQuery.getIsDeptQuery()){
                    dtoQuery.setDeptIdStr(dtoQuery.getDeptId());
                }
                List acntList = accountDao.listAllDept();
                setAccountMap(acntList);
                String begStr = comDate.dateToString(begin,"yyyy/MM/dd");
                String endStr = comDate.dateToString(end,"yyyy/MM/dd");
                String dateScope = begStr + "~" +endStr;
                //���ݿ�ʼ�ͽ���ʱ��õ����ֺõ�ʱ������
                List periodList = periodDao.getPeriodByDate(begin,end);
                //δ��r����Ϣ
                List unfillList = getUnfilled(periodList, dtoQuery, loginId);
                if (unfillList != null && unfillList.size() > 0) {
                    unfillExist = true;
                    resultMap.put(DtoTsStatusQuery.DTO_UNFILLED, unfillList);
                }
                for (int i = 0; i < 6; i++) {
                    status = getStatus(i);
                    List qryList = tsStatusDao.queryByPeriod(dtoQuery,status);
                    List resList = copyPropertyToBean(qryList,i);
                    if(resList != null && resList.size() > 0){
                      resultMap = setResultToMap(resList,i,resultMap);
                      tsExist = true;
                    }
                }
                String curDateStr = comDate.dateToString(new Date(),"yyyy/MM/dd");
                ITreeNode filledRateRoot =  new DtoTreeNode(new DtoFilledRate());
                ITreeNode fRGahterroot = new DtoTreeNode(new DtoFilledRate());
                List fRBySiteList = new ArrayList();
                //���Ϊ���Ų�ѯ�����ѯ��д��
                if(dtoQuery.getIsDeptQuery()){
                   filledRateRoot = queryFillRate(periodList,dtoQuery);
                   fRGahterroot = queryFillRateGather(periodList,dtoQuery);
                }
                
                //����B�˔��yӋ,�������״̬�Ĺ�ʱ��������������������ͳ��
                if(tsExist || unfillExist){
                  if(isExp){//����
                    Map humanMap = getStatusPersonByExp(dtoQuery,periodList);
                    List fillList = (List)humanMap.get("fill");
                    List ufillList = (List)humanMap.get("unfill");
                    resultMap.put(DtoTsStatusQuery.DTO_IS_FILL,fillList);
                    resultMap.put(DtoTsStatusQuery.DTO_UNFILL,ufillList);
                    //�a�����r�g
                    resultMap.put(DtoTsStatusQuery.DTO_CURRENT_DATE,curDateStr);
                    //��ԃ���r����/�����څ^�g
                    resultMap.put(DtoTsStatusQuery.DTO_DATE_SCOPE,dateScope);
                    if(dtoQuery.getIsDeptQuery()){
                        resultMap.put(DtoTsStatusQuery.DTO_FILLED_RATE,filledRateRoot);
                        resultMap.put(DtoTsStatusQuery.DTO_FILLED_RATE_GATHER,fRGahterroot);
                    }else{
                        fRBySiteList = getFilledRateBySite(dtoQuery,periodList);
                        resultMap.put(DtoTsStatusQuery.DTO_FILLED_RATE,fRBySiteList);
                    }
                  }else{
                     List statusHumanList = getStatusPersonByQuery(dtoQuery,periodList);
                     resultMap.put(DtoTsStatusQuery.DTO_STATUS_HUMAN,statusHumanList);
                     if(dtoQuery.getIsDeptQuery()){
                         //��д�ʣ������ţ�
                         resultMap.put(DtoTsStatusQuery.DTO_FILLED_RATE,filledRateRoot);
                         resultMap.put(DtoTsStatusQuery.DTO_FILLED_RATE_GATHER,fRGahterroot);
                     }else{
                         //��д�ʣ����ݵ㣩
                         fRBySiteList = getFilledRateBySite(dtoQuery,periodList);
                         resultMap.put(DtoTsStatusQuery.DTO_FILLED_RATE,fRBySiteList);
                     }
                  }
                }
                return resultMap;
        }
        
        /**
         *��Ա����Ϣ���뻺����
         *
         */
        private void setEmpInfoMap(){
                List listEmp = rmMaintDao.listAllEmployee();
                if(listEmp == null) return;
                for(int i=0;i<listEmp.size();i++){
                    TsHumanBase hb = (TsHumanBase)listEmp.get(i);
                    empMap.put(hb.getEmployeeId().toUpperCase(),hb);
                }
        }
        
        /**
         * setAccountMap
         * @param accountList
         */
        private void setAccountMap(List accountList){
            if(accountList == null)return;
            for(int i = 0;i<accountList.size();i++){
                TsAccount acnt = (TsAccount)accountList.get(i);
                List acntList = (List)accountMap.get(acnt.getParentId());
                if(acntList == null){
                    acntList = new ArrayList();
                    acntList.add(acnt);
                    accountMap.put(acnt.getParentId(),acntList);
                }else{
                    acntList.add(acnt);
                }
            }
       }
        
        /**
         * ��д�ʣ��ݵ㣩
         * @param dtoQuery
         * @return List
         */
        private List getFilledRateBySite(DtoTsStatusQuery dtoQuery,List periodList){
               Date begin = dtoQuery.getBeginDate();
               Date end = dtoQuery.getEndDate();
               String site = dtoQuery.getSite();
               List fillRateList = new ArrayList();
               DtoFilledRate dtoSum = getDtoFillRateInstance();
               dtoSum.setSumLevel(1);
               if(site != null){
                  List loginIdList = rmMaintDao.listAllHuman(begin,end,site);
                  DtoFilledRate dtoFillRate = getDtoFillRate(loginIdList,site,periodList,true);
                  fillRateList.add(dtoFillRate);
                  DtoUtil.copyBeanToBean(dtoSum,dtoFillRate);
                  dtoSum.setSumLevel(1);
                  dtoSum.setAcntId("Sum");
                  fillRateList.add(dtoSum);
               }else{
                   if(siteList == null)return null;
                   int needFillEmpNum = 0;
                   int needFillNum = 0;
                   int unfillNum = 0;
                   for(int i=0;i<siteList.size();i++){
                       String sites = ((String)siteList.get(i)).toUpperCase();
                       List loginIdList = rmMaintDao.listAllHuman(begin,end,sites);
                       DtoFilledRate dto = getDtoFillRate(loginIdList,sites,periodList,false);
                       fillRateList.add(dto);
                       needFillEmpNum += dto.getNeedFillEmpNum();
                       needFillNum += dto.getNeedFillNum();
                       unfillNum += dto.getUnfillNum();
                   }
                   Double fillRate = divide(needFillNum-unfillNum,needFillNum);
                   dtoSum.setAcntId("Sum");
                   dtoSum.setNeedFillEmpNum(needFillEmpNum);
                   dtoSum.setNeedFillNum(needFillNum);
                   dtoSum.setUnfillNum(unfillNum);
                   dtoSum.setFillRate(fillRate);
                   fillRateList.add(dtoSum);
               }
               return fillRateList;
       }
       
       /**
        * getDtoFillRate
        * @param loginIdList
        * @param site
        * @param begin
        * @param end
        * @param flag
        * @return DtoFilledRate
        */
       private DtoFilledRate getDtoFillRate(List loginIdList,String site,
               List periodList,boolean flag){
               DtoFilledRate dtoFR =  getDtoFillRateInstance();
               dtoFR.setSumLevel(0);
               site = site.toUpperCase();
               dtoFR.setAcntId(site);
               dtoFR.setNeedFillEmpNum(getCountsByNeedFS(loginIdList,periodList));
               if(flag){
                   dtoFR.setUnfillNum(sumUnfillFNum);
                   dtoFR.setNeedFillNum(sumNeedFillNum);
               }else{
                DtoTimesheetStatus dto = (DtoTimesheetStatus)needFillTSBySiteMap.get(site);
                if(dto != null){
                   dtoFR.setNeedFillNum(dto.getNum());
                }else{
                   dtoFR.setNeedFillNum(0);
                }
                DtoTimesheetStatus dtoUnfill = (DtoTimesheetStatus)unfillTSBySiteMap.get(site);
                if(dtoUnfill != null){
                   dtoFR.setUnfillNum(dtoUnfill.getNum());
                }else{
                   dtoFR.setUnfillNum(0);
                }
               }
               Double fillRate = divide(dtoFR.getNeedFillNum() - dtoFR.
                                  getUnfillNum(),dtoFR.getNeedFillNum());
               dtoFR.setFillRate(fillRate);
               return dtoFR;
       }
       
       /**
        * �õ����ʱԱ����
        * @param loginIdList
        * @param begin
        * @param end
        * @return
        */
       private int getCountsByNeedFS(List loginIdList,List periodList){
               if(loginIdList == null) return 0;
               List loginIds = new ArrayList();
               int count = 0;
               for(int i=0;i<loginIdList.size();i++){
                   TsHumanBase tsHuman = (TsHumanBase)loginIdList.get(i);
                   String loginId = tsHuman.getEmployeeId().toUpperCase();
                   String site = tsHuman.getSite().toUpperCase();
                   Boolean flag = (Boolean)isfillMap.get(loginId);
                   for(int j=0;j<periodList.size();j++){
                       DtoTimeSheetPeriod dtoPeriod = (DtoTimeSheetPeriod)
                       periodList.get(j);
                       String begStr = comDate.dateToString(dtoPeriod.getBeginDate(),comDate.pattenDate);
                       String endStr = comDate.dateToString(dtoPeriod.getEndDate(),comDate.pattenDate);
                       String keyNN = begStr + endStr + loginId + site;
                       String statuss = (String)unfillByDateMap.get(keyNN);
                   //�����ǰԱ����P6�б���Ϊ������д��ʱ�������ڵ�ǰ�����Ƿǹ�����������
                       if((flag == null || !flag)|| (statuss != null && statuss.
                           equals(DtoTsStatusQuery.DTO_ISFILL_NO))){
                        continue;
                       }else if(((flag != null && flag) && (statuss ==null ||
                               statuss.equals(DtoTsStatusQuery.DTO_ISFILL_YES)))){
                        if(!loginIds.contains(loginId)){
                           loginIds.add(loginId);
                           count++;
                       }
                   }
                }
               }
               return count;
       }
        
        /**
         * �ݹ��ѯ�����µ����ﲿ��
         * @param deptMap Map
         * @param deptList List
         */
        private void findDeptChildrenByDeptId(List dList, List<TsAccount> deptList) {
                List list = null;
                for(TsAccount tsAccount : deptList) {
                    dList.add(tsAccount.getAccountId());
                    list = accountDao.listChildrenDeptByParentId(tsAccount.getAccountId());
                    if(list == null || list.size() ==0) {
                        continue;
                    }
                    findDeptChildrenByDeptId(dList, list);
                }
        }
    
        /**
         * ���ݲ��Ŵ����ȡ��ͬ�����ﲿ�ŵĴ���
         * @param deptId
         * @return String
         */
        private String getDeptIds(String deptId) {
                String result = "('')";
                TsAccount account = new TsAccount();
                account.setAccountId(deptId);
                List dList = new ArrayList();
                List deptList = new ArrayList();
                deptList.add(account);
                findDeptChildrenByDeptId(dList, deptList);
                String tmp = "''";
                int size = dList.size();
                for(int i = 0; i < size; i++) {
                    if(i == 0) {
                        tmp = "'" + (String) dList.get(0) + "'";
                    } else {
                        tmp += ", '" + (String) dList.get(i) + "'";
                    }
                }
                result = "(" + tmp + ")";
                return result;
        }
        
        /**
         * ��д��
         * @param periodList
         * @param dtoQuery
         * @param viewAll
         * @return ITreeNode
         * @throws Exception
         */
        private ITreeNode queryFillRate(List periodList,DtoTsStatusQuery dtoQuery
               ) throws Exception{
                 DtoFilledRate dtoGather = getDtoFillRateInstance();
                 DtoTsStatusQuery dtoQry = new DtoTsStatusQuery();
                 DtoUtil.copyBeanToBean(dtoQry,dtoQuery);
                 dtoGather.setAcntId(dtoQry.getDeptId());
                 dtoGather.setSumLevel(0);
                 ITreeNode root = new DtoTreeNode(dtoGather);
                 if(dtoQry.getIsDeptQuery() && dtoQry.getIsSub()){
                     getChildren(root,dtoQry,periodList,0);
                     return root;
                }else{
                     getFillRateInfo(dtoGather,dtoQry,periodList,true);
                     return root;
                }
        }
        
        
        /**
         * ��д�ʣ����ܣ�
         * @param periodSize
         * @param dtoQuery
         * @param site
         * @param viewAll
         * @return ITreeNode
         * @throws Exception
         */
        private ITreeNode queryFillRateGather(List periodList,
                DtoTsStatusQuery dtoQuery) throws Exception{
                 DtoFilledRate dtoGather = getDtoFillRateInstance();
                 DtoTsStatusQuery dtoQry = new DtoTsStatusQuery();
                 DtoUtil.copyBeanToBean(dtoQry,dtoQuery);
                 dtoGather.setAcntId(dtoQry.getDeptId());
                 dtoGather.setSumLevel(0);
                 ITreeNode root = new DtoTreeNode(dtoGather);
                 if(dtoQry.getIsDeptQuery() && dtoQry.getIsSub()){
                   getChildrenGather(root,dtoQry,periodList,0);
                   return root;
                 } else {
                   getFillRateInfo(dtoGather,dtoQry,periodList,false);
                   return root;
                }
        }
        
        /**
         * �ݹ�
         * @param node
         * @param dtoQuery
         * @param periodSize
         * @param j
         * @param site
         * @param viewAll
         * @throws Exception
         */
        private void getChildrenGather(ITreeNode node,DtoTsStatusQuery dtoQuery,
                List periodList,int j) throws Exception {
                DtoFilledRate dto = (DtoFilledRate)node.getDataBean();
                String ouId = dto.getAcntId();
                getFillRateInfo(dto,dtoQuery,periodList,false);
                //����ouId��ȡdto��ʱ
                List chidren = getSubDept(ouId);
                j=j+1;
                for (int i = 0; i <  chidren.size(); i++) {        
                    DtoFilledRate dtoChildren = (DtoFilledRate) chidren.get(i);
                    DtoFilledRate dtoChild = getDtoFillRateInstance();
                    DtoUtil.copyBeanToBean(dtoChild,dtoChildren);
                    dtoQuery.setDeptId(dtoChild.getAcntId());
                    getFillRateInfo(dtoChild,dtoQuery,periodList,false);
                    dtoChild.setSumLevel(j);
                    DtoTreeNode child = new DtoTreeNode(dtoChild);
                    node.addChild(child);
                    getChildrenGather(child,dtoQuery,periodList,j);
                    aggregate(dto, dtoChild);       
                }
             }
        
        /**
         * �õ�������
         * @param node
         * @param dtoQuery
         * @param periodSize
         * @param j
         * @param site
         * @param viewAll
         * @throws Exception
         */
        private void getChildren(ITreeNode node,DtoTsStatusQuery dtoQuery,List periodList,
                int j) throws Exception {
                DtoFilledRate dto = (DtoFilledRate)node.getDataBean();
                String ouId = dto.getAcntId();
                dtoQuery.setDeptId(ouId);
                if(j == 0){
                    getFillRateInfo(dto,dtoQuery,periodList,true);
                }else{
                    getFillRateInfo(dto,dtoQuery,periodList,false);
                }
                //����ouId��ȡdto��ʱ
                List chidren = getSubDept(ouId);
                j=j+1;
                for (int i = 0; i < chidren.size(); i++) {        
                    DtoFilledRate dtoChildren = (DtoFilledRate) chidren.get(i);
                    DtoFilledRate dtoChild = getDtoFillRateInstance();
                    DtoUtil.copyBeanToBean(dtoChild,dtoChildren);
                    dtoQuery.setDeptId(dtoChild.getAcntId());
                    getFillRateInfo(dtoChild,dtoQuery,periodList,false);
                    dtoChild.setSumLevel(j);
                    DtoTreeNode child = new DtoTreeNode(dtoChild);
                    node.addChild(child);
                    getChildren(child,dtoQuery,periodList,j);
                }
             }
            
             /**
              * ���ӽ��ļ�¼�ۼӵ��������
              * @param dto
              * @param dtoChildren
              */
           private void aggregate(DtoFilledRate dto,DtoFilledRate dtoChildren){
                    dto.setNeedFillEmpNum(dto.getNeedFillEmpNum()  
                                     +dtoChildren.getNeedFillEmpNum());
                    dto.setNeedFillNum(dto.getNeedFillNum() 
                                       + dtoChildren.getNeedFillNum());
                    dto.setUnfillNum(dto.getUnfillNum()+ dtoChildren.getUnfillNum());
                    Double rate = divide(dto.getNeedFillNum() - dto.getUnfillNum(),
                                     dto.getNeedFillNum());
                    dto.setFillRate(rate);
            }
        
          /**
           * �õ���д����Ϣ
           * @param dto
           * @param dtoQuery
           * @param site
           * @param viewAll
           * @param periodSize
           * @return DtoFilledRate
           * @throws Exception
           */
           private void getFillRateInfo(DtoFilledRate dto,DtoTsStatusQuery dtoQuery,
                  List periodList,boolean isFirst) throws Exception{
                  DtoFilledRate dtoFillRate = (DtoFilledRate)fillRateMap.
                  get(dtoQuery.getDeptId());
                  List loginIdList = new ArrayList();
                  int humanCount = 0;
                  int count = 0;
                  if(dtoFillRate != null){
                     DtoUtil.copyBeanToBean(dto,dtoFillRate);
                  }else{
                     DtoTsStatusQuery dtoQry = new DtoTsStatusQuery();
                     DtoUtil.copyBeanToBean(dtoQry,dtoQuery);
                     dtoQry.setIsSub(false);
                     // �ݲ��ź�ʱ�����ڵõ�����������µ�Ա����Ϣ
                     for(int k=0;k<periodList.size();k++){
                         DtoTimeSheetPeriod dtoPeriod = (DtoTimeSheetPeriod)
                                           periodList.get(k);
                         String begStr = comDate.dateToString(dtoPeriod.getBeginDate(),
                                   comDate.pattenDate);
                         String endStr = comDate.dateToString(dtoPeriod.getEndDate(),
                                   comDate.pattenDate);
                         dtoQry.setBeginDate(dtoPeriod.getBeginDate());
                         dtoQry.setEndDate(dtoPeriod.getEndDate());
                         if(isFirst){
                            setEmpNumMap(dtoQry,begStr,endStr);
                          }
                          String key = dtoQry.getDeptId() + begStr + endStr;
                          List fillList = (List)empNumInfoMap.get(key);
                          if(fillList == null)continue;
                          for(int i=0;i<fillList.size();i++){
                              DtoTsStatusQuery dtoT =(DtoTsStatusQuery)fillList.get(i);
                              String loginId = dtoT.getEmpId().toUpperCase();
                              String site  = dtoT.getSite();
                              Boolean flag = (Boolean)isfillMap.get(loginId);
                              String keyNN = begStr + endStr + loginId + site;
                              String status = (String)unfillByDateMap.get(keyNN);
                              //�����ǰԱ����P6�б���Ϊ������д��ʱ�������ڵ�ǰ�����Ƿǹ�����������
                              if((flag == null || !flag)|| (status != null && status.
                                      equals(DtoTsStatusQuery.DTO_ISFILL_NO))){
                               continue;
                              }else if((flag != null && flag) && (status == null ||
                                      status.equals(DtoTsStatusQuery.DTO_ISFILL_YES))){
                              count++;
                               if(!loginIdList.contains(loginId)){
                                 loginIdList.add(loginId);
                                  humanCount++;
                                } 
                              }
                            }                          
                    } 
                    dto.setNeedFillNum(count);
                    dto.setNeedFillEmpNum(humanCount);
                    List unfillList =(List) unfillYesMap.get(dtoQuery.getDeptId());
                   if(unfillList != null && unfillList.size() > 0){
                     dto.setUnfillNum(unfillList.size());
                   }else{
                     dto.setUnfillNum(0);
                   }
                   Double fillRate = divide(count - dto.getUnfillNum(),count);
                   dto.setFillRate(fillRate);
                  }
                fillRateMap.put(dtoQuery.getDeptId(),dto);
              }
           
           /**
            * �T�������T��site�ֽM���뾏����
            * @param dtoQry
            * @throws Exception
            */
           private void setEmpNumMap(DtoTsStatusQuery dtoQry,String beginStr,String endStr) throws Exception{
                   List fillList = tsStatusDao.getFillTSEmpIdList(dtoQry,false);
                   for(int i = 0;i < fillList.size(); i++){
                       Object[] obj = (Object[])fillList.get(i);
                       String empId = (String)obj[0];
                       String unitCode = (String)obj[1];
                       String site = (String)obj[2];
                       DtoTsStatusQuery dto = new DtoTsStatusQuery();
                       dto.setSite(site);
                       dto.setEmpId(empId);
                       String key = unitCode + beginStr + endStr;
                       List empNumIfoList = (List)empNumInfoMap.get(key);
                       if(empNumIfoList == null){
                           empNumIfoList = new ArrayList();
                           empNumIfoList.add(dto);
                           empNumInfoMap.put(key,empNumIfoList);
                       }else{
                           empNumIfoList.add(dto);
                       }
                   }
           }
        
        /**
         * �õ��ӽ���LIST
         * @param acntId String
         * @return List
         */
        private List getSubDept(String acntId){
                List cList = (List)accountMap.get(acntId);
                if(cList == null) return new ArrayList();
                List subDeptList = new ArrayList();
                for (int i = 0; i < cList.size(); i++) {
                     TsAccount ts = (TsAccount) cList.get(i);
                     DtoFilledRate dtoChildren = getDtoFillRateInstance();
                     dtoChildren.setAcntId(ts.getAccountId());
                     subDeptList.add(dtoChildren);
                 }
                return subDeptList;
         }
        
        /**
         * ����
         * @param count1
         * @param count2
         * @return Double
         */
        private Double divide(int count1,int count2){
                 BigDecimal countA = BigDecimal.valueOf(Double.
                                     valueOf(String.valueOf(count1)));
                 BigDecimal countB = BigDecimal.valueOf(Double.
                                     valueOf(String.valueOf(count2)));
                 if(count2 > 0){
                  BigDecimal rate =  countA.divide(countB,2,BigDecimal.ROUND_HALF_UP);
                  return rate.doubleValue();
                 }
                 return Double.valueOf(0);
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
        
        /**
         * �����õ��ĽY������MAP��
         * @param resList
         * @param i
         * @param resultMap
         * @return Map
         */
        private Map setResultToMap(List resList,int i,Map resultMap){
                switch (i) {
                case 0:
                    resultMap.put(DtoTsStatusQuery.DTO_SUBMITTED, resList);
                    break;
                case 1:
                    resultMap.put(DtoTsStatusQuery.DTO_APPROVED, resList);
                    break;
                case 2:
                    resultMap.put(DtoTsStatusQuery.DTO_ACTIVE, resList);
                    break;
                case 3:
                    resultMap.put(DtoTsStatusQuery.DTO_REJECTED, resList);
                    break;
                case 4:
                    resultMap.put(DtoTsStatusQuery.DTO_PM_APPROVED, resList);
                    break;
                case 5:
                    resultMap.put(DtoTsStatusQuery.DTO_RM_APPROVED, resList);
                    break;
                default:
                    break;
                }
            return resultMap;
        }
        
       
        
        /**
         * ����B�˔��yӋ(��ѯ��
         * @param begin
         * @param end
         * @return List
         */
        private List getStatusPersonByQuery(DtoTsStatusQuery dtoQry,
                List periodLst) throws Exception{
                List resList = new ArrayList();
                List tempList = new ArrayList();
                sumActiveFNum = 0;
                sumAppFNum = 0;
                sumPmAppFNum = 0;
                sumRejectFNum = 0;
                sumSubmitFNum = 0;
                sumUnfillFNum = 0;
                sumActiveUnfNum = 0;
                sumUnfillUnfNum = 0;
                sumSubmitUnfNum = 0;
                sumPmAppUnfNum = 0;
                sumAppUnfNum = 0;
                sumRejectUnfNum = 0;
                sumNeedFillNum = 0;
                sumRmAppUnfNum = 0;
                sumRmAppFNum = 0;
                DtoTsStatusQuery dtoQuery = new DtoTsStatusQuery();
                DtoUtil.copyBeanToBean(dtoQuery,dtoQry);
                if(periodLst != null){
                    for(int i=0;i<periodLst.size();i++){
                        DtoTimeSheetPeriod period = (DtoTimeSheetPeriod)periodLst.get(i);
                        dtoQuery.setBeginDate(period.getBeginDate());
                        dtoQuery.setEndDate(period.getEndDate());
                        //�yӋ����������r�ε��ˆT
                        DtoStatusHuman dtoHuman = getDtoStatusHuman(dtoQuery,
                               DtoTsStatusQuery.DTO_ISFILL_YES);
                        //�yӋ������������r�ε��ˆT
                        DtoStatusHuman dtoHumanUnfill = getDtoStatusHuman(dtoQuery,DtoTsStatusQuery.DTO_ISFILL_NO);
                        resList.add(dtoHuman);
                        tempList.add(dtoHumanUnfill);
                    }
                    //����r�ε��ˆT�ąR��ӛ�
                    DtoStatusHuman dtoFill = getDtoDetailInstance();
                    dtoFill.setSumLevel(1);
                    dtoFill.setEnd("Sum(need fill timesheet)");
                    dtoFill.setActiveNum(sumActiveFNum);
                    dtoFill.setApprovedNum(sumAppFNum);
                    dtoFill.setPmApprovedNum(sumPmAppFNum);
                    dtoFill.setRejectedNum(sumRejectFNum);
                    dtoFill.setSubmittedNum(sumSubmitFNum);
                    dtoFill.setUnfillNum(sumUnfillFNum);
                    dtoFill.setNeedFillNum(sumNeedFillNum);
                    dtoFill.setRmApprovedNum(sumRmAppFNum);
                    int sum = sumActiveFNum+sumAppFNum+sumPmAppFNum+sumRejectFNum
                              +sumSubmitFNum+sumUnfillFNum+sumRmAppFNum;
                    dtoFill.setSum(sum);
                    resList.add(dtoFill);
                    resList.addAll(tempList);
                    //������r�ε��ˆT�ąR��ӛ�
                    DtoStatusHuman dtoUnFill = getDtoDetailInstance();
                    dtoUnFill.setSumLevel(1);
                    dtoUnFill.setEnd("Sum(needn't fill timesheet)");
                    dtoUnFill.setActiveNum(sumActiveUnfNum);
                    dtoUnFill.setApprovedNum(sumAppUnfNum);
                    dtoUnFill.setPmApprovedNum(sumPmAppUnfNum);
                    dtoUnFill.setRejectedNum(sumRejectUnfNum);
                    dtoUnFill.setSubmittedNum(sumSubmitUnfNum);
                    dtoUnFill.setUnfillNum(sumUnfillUnfNum);
                    dtoUnFill.setRmApprovedNum(sumRmAppUnfNum);
                    dtoUnFill.setNeedFillNum(0);
                    int sumUf = sumActiveUnfNum + sumAppUnfNum + sumPmAppUnfNum 
                                + sumRejectUnfNum + sumSubmitUnfNum+sumUnfillUnfNum+sumRmAppUnfNum;
                    dtoUnFill.setSum(sumUf);
                    resList.add(dtoUnFill);
                 }
                return resList;
        }
        
        /**
         * ����B�˔��yӋ(������
         * @param begin
         * @param end
         * @return List
         * @throws Exception 
         */
        private Map getStatusPersonByExp(DtoTsStatusQuery dtoQry,
                List periodLst) throws Exception{
                List fillList = new ArrayList();
                List unfillList = new ArrayList();
                Map map = new HashMap();
                sumActiveFNum = 0;
                sumAppFNum = 0;
                sumPmAppFNum = 0;
                sumRejectFNum = 0;
                sumSubmitFNum = 0;
                sumUnfillFNum = 0;
                sumActiveUnfNum = 0;
                sumUnfillUnfNum = 0;
                sumSubmitUnfNum = 0;
                sumPmAppUnfNum = 0;
                sumAppUnfNum = 0;
                sumRejectUnfNum = 0;
                sumNeedFillNum = 0;
                sumRmAppUnfNum = 0;
                sumRmAppFNum = 0;
                DtoTsStatusQuery dtoQuery = new  DtoTsStatusQuery();
                DtoUtil.copyBeanToBean(dtoQuery,dtoQry);
                if(periodLst != null){
                    for(int i=0;i<periodLst.size();i++){
                        DtoTimeSheetPeriod period = (DtoTimeSheetPeriod)periodLst.get(i);
                        //�yӋ����������r�ε��ˆT
                        dtoQuery.setBeginDate(period.getBeginDate());
                        dtoQuery.setEndDate(period.getEndDate());
                        DtoStatusHuman dtoHuman = getDtoStatusHuman(dtoQuery,DtoTsStatusQuery.DTO_ISFILL_YES);
                        //�yӋ������������r�ε��ˆT
                        DtoStatusHuman dtoHumanUnfill = getDtoStatusHuman(dtoQuery,DtoTsStatusQuery.DTO_ISFILL_NO);
                        fillList.add(dtoHuman);
                        unfillList.add(dtoHumanUnfill);
                    }
                    //����r�ε��ˆT�ąR��ӛ�
                    DtoStatusHuman dtoFill =getDtoDetailInstance();
                    dtoFill.setSumLevel(1);
                    dtoFill.setEnd("СӋ");
                    dtoFill.setActiveNum(sumActiveFNum);
                    dtoFill.setApprovedNum(sumAppFNum);
                    dtoFill.setPmApprovedNum(sumPmAppFNum);
                    dtoFill.setRejectedNum(sumRejectFNum);
                    dtoFill.setSubmittedNum(sumSubmitFNum);
                    dtoFill.setUnfillNum(sumUnfillFNum);
                    dtoFill.setNeedFillNum(sumNeedFillNum);
                    dtoFill.setRmApprovedNum(sumRmAppFNum);
                    int sum = sumActiveFNum+sumAppFNum+sumPmAppFNum+sumRejectFNum
                              +sumSubmitFNum+sumUnfillFNum+sumRmAppFNum;
                    dtoFill.setSum(sum);
                    fillList.add(dtoFill);
                   
                    //������r�ε��ˆT�ąR��ӛ�
                    DtoStatusHuman dtoUnFill =getDtoDetailInstance();
                    dtoUnFill.setSumLevel(1);
                    dtoUnFill.setEnd("СӋ");
                    dtoUnFill.setActiveNum(sumActiveUnfNum);
                    dtoUnFill.setApprovedNum(sumAppUnfNum);
                    dtoUnFill.setPmApprovedNum(sumPmAppUnfNum);
                    dtoUnFill.setRejectedNum(sumRejectUnfNum);
                    dtoUnFill.setSubmittedNum(sumSubmitUnfNum);
                    dtoUnFill.setUnfillNum(sumUnfillUnfNum);
                    dtoUnFill.setRmApprovedNum(sumRmAppUnfNum);
                    int sumUf = sumActiveUnfNum + sumAppUnfNum + sumPmAppUnfNum 
                                + sumRejectUnfNum + sumSubmitUnfNum+sumUnfillUnfNum+sumRmAppUnfNum;
                    dtoUnFill.setSum(sumUf);
                    unfillList.add(dtoUnFill);
                 }
                map.put("fill",fillList);
                map.put("unfill",unfillList);
                return map;
        }
        
        /**
         * ��B�˔��yӋ
         * @param begin
         * @param end
         * @param status
         * @return DtoStatusHuman
         * @throws Exception 
         */
        private DtoStatusHuman getDtoStatusHuman(DtoTsStatusQuery dtoQry,
                String status) throws Exception{
                DtoStatusHuman dtoHuman = getDtoDetailInstance();
                dtoHuman.setSumLevel(0);
                int sum = 0;
                String begStr = comDate.dateToString(dtoQry.getBeginDate(),
                        comDate.pattenDate);
                String endStr = comDate.dateToString(dtoQry.getEndDate(),
                        comDate.pattenDate);
                
                //δ����r�� 
                String key = begStr+endStr+status+DtoTsStatusQuery.DTO_UNFILLED;
                DtoTimesheetStatus dtoUnfill = (DtoTimesheetStatus)unfillMap.get(key);
                if(dtoUnfill != null){
                    dtoHuman.setUnfillNum(dtoUnfill.getNum());
                  if(status.equals(DtoTsStatusQuery.DTO_ISFILL_YES)){
                      if(!dtoQry.getIsDeptQuery() && dtoQry.getSite() == null){
                          getUnfillTSBySite(key);
                      }
                      sumUnfillFNum +=dtoUnfill.getNum();
                  }else{      
                      sumUnfillUnfNum+=dtoUnfill.getNum();
                  }
                   sum+=dtoUnfill.getNum();
                }
                
                
                //���ύ���r�� 
                String subKey = begStr+endStr + status + DtoTsStatusQuery.DTO_SUBMITTED;
                DtoTimesheetStatus dtoSub = (DtoTimesheetStatus)submitMap.get(subKey);
                if(dtoSub != null){
                    dtoHuman.setSubmittedNum(dtoSub.getNum());
                    if(status.equals(DtoTsStatusQuery.DTO_ISFILL_YES)){
                        sumSubmitFNum  +=dtoSub.getNum();
                    }else{
                        sumSubmitUnfNum +=dtoSub.getNum();
                    }
                    sum +=dtoSub.getNum();
                }
                
                //�ь������r��
                String approveKey = begStr+endStr + status + DtoTsStatusQuery.DTO_APPROVED;
                DtoTimesheetStatus dtoApp = (DtoTimesheetStatus)approveMap.get(approveKey);
                if(dtoApp != null){
                  dtoHuman.setApprovedNum(dtoApp.getNum());
                  if(status.equals(DtoTsStatusQuery.DTO_ISFILL_YES)){
                      sumAppFNum +=dtoApp.getNum();
                  }else{
                      sumAppUnfNum +=dtoApp.getNum();
                  }
                  sum +=dtoApp.getNum();
                }
                
                 //δ�ύ���r��
                String actKey = begStr+endStr + status + DtoTsStatusQuery.DTO_ACTIVE;
                DtoTimesheetStatus dtoAct = (DtoTimesheetStatus)activeMap.get(actKey);
                if(dtoAct != null){
                  dtoHuman.setActiveNum(dtoAct.getNum());
                  if(status.equals(DtoTsStatusQuery.DTO_ISFILL_YES)){
                      sumActiveFNum +=dtoAct.getNum();
                  }else{
                      sumActiveUnfNum +=dtoAct.getNum();
                  }
                  sum +=dtoAct.getNum();
                }
                
                //�e�ܽ^���r�� 
                String rejKey = begStr+endStr + status + DtoTsStatusQuery.DTO_REJECTED;
                DtoTimesheetStatus dtoRej = (DtoTimesheetStatus)rejectMap.get(rejKey);
                if(dtoRej != null){
                   dtoHuman.setRejectedNum(dtoRej.getNum());
                   if(status.equals(DtoTsStatusQuery.DTO_ISFILL_YES)){
                       sumRejectFNum +=dtoRej.getNum();
                   }else{
                       sumRejectUnfNum +=dtoRej.getNum(); 
                   }
                   sum +=dtoRej.getNum(); 
                }
                
                //PM�ь������r�� 
                String pmAppKey = begStr+endStr + status + DtoTsStatusQuery.DTO_PM_APPROVED;
                DtoTimesheetStatus dtoPmApp = (DtoTimesheetStatus)pmApproveMap.get(pmAppKey);
                if(dtoPmApp != null){
                 dtoHuman.setPmApprovedNum(dtoPmApp.getNum());
                 if(status.equals(DtoTsStatusQuery.DTO_ISFILL_YES)){
                     sumPmAppFNum +=dtoPmApp.getNum();
                 }else{
                     sumPmAppUnfNum +=dtoPmApp.getNum();
                 }
                 sum +=dtoPmApp.getNum(); 
                }

                //RM�ь������r�� 
                String rmAppKey = begStr+endStr + status + DtoTsStatusQuery.DTO_RM_APPROVED;
                DtoTimesheetStatus dtoRmApp = (DtoTimesheetStatus)rmApproveMap.get(rmAppKey);
                if(dtoRmApp != null){
                 dtoHuman.setRmApprovedNum(dtoRmApp.getNum());
                 if(status.equals(DtoTsStatusQuery.DTO_ISFILL_YES)){
                     sumRmAppFNum +=dtoRmApp.getNum();
                 }else{
                     sumRmAppUnfNum +=dtoRmApp.getNum();
                 }
                 sum +=dtoRmApp.getNum(); 
                }
                
                dtoHuman.setBegin(begStr);
                dtoHuman.setEnd(endStr);
                dtoHuman.setSum(sum);
                //������Ҫ��д��ʱ���ĸ���
                int count = 0;
                if(status.equals(DtoTsStatusQuery.DTO_ISFILL_YES)){
                   List fillList = tsStatusDao.getFillTSEmpIdList(dtoQry,true);
                   if(fillList != null){
                    for(int i=0;i<fillList.size();i++){
                       Object[] obj = (Object[])fillList.get(i);
                       String loginId = (String)obj[0];
                       String site = (String)obj[2];
                       Boolean flag = (Boolean)isfillMap.get(loginId);
                       String keyNN = begStr + endStr + loginId + site;
                       String statuss = (String)unfillByDateMap.get(keyNN);
                       //�����ǰԱ����P6�б���Ϊ������д��ʱ�������ڵ�ǰ�����Ƿǹ�����������
                       if((flag== null || !flag)|| (statuss != null && statuss.
                               equals(DtoTsStatusQuery.DTO_ISFILL_NO))){
                           continue;
                       }else if(((flag != null && flag) && (statuss ==null || statuss.
                               equals(DtoTsStatusQuery.DTO_ISFILL_YES)))){
                           if(!dtoQry.getIsDeptQuery() && dtoQry.getSite() == null){
                               getNeedFillTSBySite(site);
                           }
                           count++;
                       }
                     }
                   }
                   dtoHuman.setNeedFillNum(count);
                   sumNeedFillNum +=count;
                }else{
                   dtoHuman.setNeedFillNum(0);
                }
              return dtoHuman;
        }
       
            /**
             * �����c�õ�����r�Ώ������뾏����
             * @param site
             */
           private void getNeedFillTSBySite(String site){
                   DtoTimesheetStatus dto = (DtoTimesheetStatus)needFillTSBySiteMap.get(site);
                   if(dto == null){
                      dto = new DtoTimesheetStatus();
                      dto.setNum(1);
                      needFillTSBySiteMap.put(site,dto);
                    }else{
                      dto.setNum(dto.getNum() + 1);
                    }
            }
            
           /**
            * �������c��δ����r�Ώ���
            * @param key
            */
            private void getUnfillTSBySite(String key){
                    if(siteList == null)return;
                    for(int i=0;i<siteList.size();i++){
                     String site = ((String)siteList.get(i)).toUpperCase();
                     String keyN= key + site;
                     DtoTimesheetStatus dtoUnfill = (DtoTimesheetStatus)unfillMap.get(keyN);
                     if(dtoUnfill == null)continue;
                     DtoTimesheetStatus dto = (DtoTimesheetStatus)unfillTSBySiteMap.get(site);
                     if(dto != null){
                        dto.setNum(dto.getNum()+dtoUnfill.getNum());
                     }else{
                         dto = new DtoTimesheetStatus();
                         dto.setNum(dtoUnfill.getNum());
                         unfillTSBySiteMap.put(site,dto);
                     }
                }
        }
        
            /**
             * �õ�δ��܈�ĆT���Y��
             * 
             * @param dtoQuery
             * @return List
             * @throws Exception
             */
           public List getUnfilled(List periodLst, DtoTsStatusQuery dtoQuery, String loginId) {
                  List unfilledList = new ArrayList();
                  DtoTsStatusQuery dtoQry = new DtoTsStatusQuery();
                  DtoUtil.copyBeanToBean(dtoQry, dtoQuery);
                  for(int i = 0; i < periodLst.size(); i++) {
                     DtoTimeSheetPeriod dtoPeriod = (DtoTimeSheetPeriod)periodLst.get(i);
                     dtoQry.setBeginDate(dtoPeriod.getBeginDate());
                     dtoQry.setEndDate(dtoPeriod.getEndDate());
                     try {
                        List unfillList = tsStatusDao.queryUnfilledByPeriod(dtoQry);
                        List dateList = getDateList(dtoPeriod.getBeginDate(),dtoPeriod.getEndDate());
                        List ufList = objList2StatusList(unfillList,dateList,dtoQry);
                        if(ufList != null && ufList.size() > 0) {
                           unfilledList.addAll(ufList);
                        }
                     } catch (Exception e) {
                        throw new BusinessException("error.logic.TimesheetStatusServiceImp.getUnfilledError",
                                "Get unfilled data by period is error!", e);
                     }
                 }
                return unfilledList;
         }
        
            /**
             * ����Ա�������ϵ���ȡ�õ�Ա�����ϲ�ѯP6��ָ��Ա���Ƿ���Ҫ��д��ʱ�����������Ҫ������
             * @param data
             * @param begin
             * @param end
             * @return List
             * @throws Exception 
             */
            private List<DtoTimesheetStatus> objList2StatusList(List unfList,
                List dateList,DtoTsStatusQuery dtoQry) {
                if(unfList == null) return null;
                leaveHoursMap.clear();
                Boolean needFlag = false;
                List resList = new ArrayList();
                List empList = new ArrayList();
                DtoTimesheetStatus dto;
                String empId;
                String site;
                String key = "";
                com.primavera.integration.client.bo.object.Calendar cal;
                Date begin = dtoQry.getBeginDate();    
                Date end = dtoQry.getEndDate();
                // ��ָ�����ڃȌ��T��Ո�ٵĿ����r���뾏����
                setWorkHoursMap(begin,end);
                String beginStr = comDate.dateToString(begin,comDate.pattenDate);
                String endStr = comDate.dateToString(end,comDate.pattenDate);
                for(int i = 0; i < unfList.size(); i ++) {
                    needFlag = true;
                    TsHumanBase hb = (TsHumanBase)unfList.get(i);
                    dto = new DtoTimesheetStatus();
                    empId = hb.getEmployeeId();
                    site = hb.getSite();
                    Double sumHours  = Double.valueOf(0);
                    String cName = (String)calendarNameMap.get(empId.toUpperCase());
                    cal = (com.primavera.integration.client.bo.object.Calendar)calendarMap.
                           get(cName);
                    Double leaveHours = (Double)leaveHoursMap.get(empId.toUpperCase());
                    if(cal != null){
                       sumHours = timeSheetApiDao.getSumStandarHours(cal,
                            dateList,hb.getInDate(),hb.getOutDate());
                      //���Ո�ٹ��r���ژ˜ʹ��r�t����Ҫ����r��
                      if(sumHours <= 0 || ("true".equals(checkLeaveHours) 
                             && leaveHours != null && leaveHours.equals(sumHours))){
                        needFlag = false;
                      }
                    }else{
                        needFlag = false;
                    }
                    dto.setEmpId(empId);
                    dto.setEmpName(hb.getFullName());
                    dto.setAcntName(hb.getUnitCode());
                    dto.setResManagerId(hb.getResManagerId());
                    dto.setResManagerName(getFullName(hb.getResManagerId()));
                    dto.setBegin(begin);
                    dto.setEnd(end);
                    Boolean flag = (Boolean)isfillMap.get(empId.toUpperCase());
                    key = beginStr + endStr + empId.toUpperCase()+ site;
                    if((flag == null || !flag)|| !needFlag){
                        dto.setIsFillTimesheet(DtoTsStatusQuery.DTO_ISFILL_NO);
                        unfillByDateMap.put(key,DtoTsStatusQuery.DTO_ISFILL_NO);
                    }else if(flag != null && flag && needFlag){
                        dto.setIsFillTimesheet(DtoTsStatusQuery.DTO_ISFILL_YES);
                        List unfillList = (List)unfillYesMap.get(hb.getUnitCode());
                        if(unfillList == null){
                           unfillList = new ArrayList();
                           unfillList.add(dto);
                           unfillYesMap.put(hb.getUnitCode(),unfillList);
                        }else{
                           unfillList.add(dto);
                        }
                       unfillByDateMap.put(key,DtoTsStatusQuery.DTO_ISFILL_YES);
                    }
                    resList.add(dto);
                    if(!empList.contains(hb.getEmployeeId())){
                        String keys = beginStr+endStr+dto.getIsFillTimesheet()
                                  +DtoTsStatusQuery.DTO_UNFILLED;
                       DtoTimesheetStatus dtoStatus = (DtoTimesheetStatus)unfillMap.get(keys);
                       if(dtoStatus != null ){
                          dtoStatus.setNum(dtoStatus.getNum()+1);
                       }else{
                        dto.setNum(1);
                        unfillMap.put(keys,dto);
                      }
                      //�ݵ��ѯ��SITE��������ʱ��SITE����δ�����
                      if(!dtoQry.getIsDeptQuery() && dtoQry.getSite() == null){
                          keys = beginStr+endStr+dto.getIsFillTimesheet()
                                +DtoTsStatusQuery.DTO_UNFILLED + site.toUpperCase();
                          dtoStatus = (DtoTimesheetStatus)unfillMap.get(keys);
                          if(dtoStatus != null ){
                           dtoStatus.setNum(dtoStatus.getNum()+1);
                          }else{
                           dtoStatus = new DtoTimesheetStatus();
                           dtoStatus.setNum(1);
                           unfillMap.put(keys,dtoStatus);
                        }
                      }
                      empList.add(hb.getEmployeeId());
                    }
                }
            return resList;
        }
        
           /**
             * ��ָ�����ڃȌ��T��Ո�ٵĿ����r���뾏����
             * @param begin
             * @param end
             */
            private void setWorkHoursMap(Date begin,Date end){
                List list = attLeaveDao.sumHoursByDates(begin,end);
                for(int i =0;i<list.size();i++){
                    Object[] obj = (Object[])list.get(i);
                    String loginId = (String)obj[0];
                    Double sumLeaveHours = (Double)obj[1];
                    leaveHoursMap.put(loginId.toUpperCase(),sumLeaveHours);
                }
          }
        
            /**
             * copyPropertyToBean
             * 
             * @param lst
             * @return List
             */
             private List copyPropertyToBean(List lst,int k) {
                int size = 0;
                if (lst != null && lst.size() > 0) {
                    size = lst.size();
                }
                List empList = new ArrayList();
                List resList = new ArrayList();
                Date beginDate = null;
                for (int i = 0; i < size; i++) {
                    boolean needFill = true;
                    DtoTimesheetStatus dto = new DtoTimesheetStatus();
                    Object[] obj = (Object[]) lst.get(i);
                    dto.setAcntName((String) obj[0]);
                    String empId = ((String) obj[1]);
                    dto.setEmpId(empId);
                    //�T��ȫ�Q��Ӣ��+���ģ�
                    dto.setEmpName(getFullName(empId.toUpperCase()));
                    String pmManagerId = (String)obj[2];
                    dto.setManagerId(pmManagerId);
                    //�Ŀ����ȫ�Q
                    if(pmManagerId != null){
                        pmManagerId = pmManagerId.toUpperCase();
                    }
                    dto.setManagerName(getFullName(pmManagerId));
                    String resManagerId = (String)obj[3];
                    dto.setResManagerId(resManagerId);
                    //�YԴ����ȫ�Q
                    if(resManagerId != null){
                        resManagerId = resManagerId.toUpperCase();
                    }
                    dto.setResManagerName(getFullName(resManagerId));
                    Date begin = (Date)obj[4];
                    Date end = (Date)obj[5];
                    dto.setBegin(begin);
                    dto.setEnd(end);
                    String status = (String)obj[6];
                    String site = (String)obj[7];
                    dto.setStatus(DtoTimeSheet.statusCode2Name(status));        
                    //�ȵ��������ж��Ƿ��Ѵ��ڣ����������ֱ��ȡ�ô��ڵ���Ա��Ӧ����Ϣ
                    Boolean flag = (Boolean)isfillMap.get(empId.toUpperCase());
                    TsHumanBase humanBase = (TsHumanBase)empMap.get(empId.toUpperCase());
                    Date inDate =  humanBase.getInDate();
                    Date outDate = humanBase.getOutDate();
                    
                    if((inDate != null && inDate.after(end))
                            || outDate!=null && outDate.before(begin)){
                        needFill = false;
                    }
                    if(flag != null && flag && needFill){
                        dto.setIsFillTimesheet(DtoTsStatusQuery.DTO_ISFILL_YES);
                     }else{
                        dto.setIsFillTimesheet(DtoTsStatusQuery.DTO_ISFILL_NO); 
                     }
                    if(beginDate != null && !beginDate.equals(dto.getBegin())){
                        empList = new ArrayList();
                    }
                    dto.setSite(site);
                    if(k==2){
                        TsHumanBase emp = (TsHumanBase)empMap.get(empId.toUpperCase());
                        if(emp != null){
                            dto.setUnitCode(emp.getUnitCode());
                         }
                      }
                    beginDate = dto.getBegin();
                    if(!empList.contains(empId)){
                       setHashMap(dto,k);
                    }
                    empList.add(empId);
                    resList.add(dto);
                }
                return resList;
        }
            
          /**
           * ��������B�ĽyӋ�����������״̬������MAP��
           * @param dto
           * @param k
           */
         private void setHashMap(DtoTimesheetStatus dto,int k){
                  String begStr = comDate.dateToString(dto.getBegin(),
                          comDate.pattenDate);
                  String endStr = comDate.dateToString(dto.getEnd(),
                          comDate.pattenDate);
                  String key = begStr + endStr + dto.getIsFillTimesheet();
                  if( k == 0 ){
                        key+= DtoTsStatusQuery.DTO_SUBMITTED;
                        DtoTimesheetStatus dtoStatus = (DtoTimesheetStatus)submitMap.get(key);
                        if(dtoStatus != null){
                           dtoStatus.setNum(dtoStatus.getNum() + 1);
                        }else{
                           dto.setNum(1);
                           submitMap.put(key,dto);
                        }
                    }
                   if( k == 1 ){
                        key+= DtoTsStatusQuery.DTO_APPROVED;
                        DtoTimesheetStatus dtoStatus = (DtoTimesheetStatus)approveMap.get(key);
                        if(dtoStatus != null){
                           dtoStatus.setNum(dtoStatus.getNum() + 1);
                        }else{
                           dto.setNum(1);
                           approveMap .put(key,dto);
                        }
                    }
                   if( k == 2 ){
                        key+= DtoTsStatusQuery.DTO_ACTIVE;
                        DtoTimesheetStatus dtoStatus = (DtoTimesheetStatus)activeMap.get(key);
                        if(dtoStatus != null){
                           dtoStatus.setNum(dtoStatus.getNum() + 1);
                        }else{
                           dto.setNum(1);
                           activeMap.put(key,dto);
                        }
                    }
                   if( k == 3 ){
                        key+= DtoTsStatusQuery.DTO_REJECTED;
                        DtoTimesheetStatus dtoStatus = (DtoTimesheetStatus)rejectMap.get(key);
                        if(dtoStatus != null){
                           dtoStatus.setNum(dtoStatus.getNum() + 1);
                        }else{
                           dto.setNum(1);
                           rejectMap .put(key,dto);
                        }
                    }
                   if( k == 4 ){
                        key+= DtoTsStatusQuery.DTO_PM_APPROVED;
                        DtoTimesheetStatus dtoStatus = (DtoTimesheetStatus)pmApproveMap.get(key);
                        if(dtoStatus != null){
                           dtoStatus.setNum(dtoStatus.getNum() + 1);
                        }else{
                           dto.setNum(1);
                           pmApproveMap.put(key,dto);
                        }
                    }
                   if( k == 5 ){
                       key+= DtoTsStatusQuery.DTO_RM_APPROVED;
                       DtoTimesheetStatus dtoStatus = (DtoTimesheetStatus)rmApproveMap.get(key);
                       if(dtoStatus != null){
                          dtoStatus.setNum(dtoStatus.getNum() + 1);
                       }else{
                          dto.setNum(1);
                          rmApproveMap.put(key,dto);
                       }
                   }
            }
            
          /**
           * �õ��T��ȫ�Q(Ӣ����+��������
           * @param loginId
           * @return String
           */
           private String getFullName(String loginId) {
                TsHumanBase hb = (TsHumanBase)empMap.get(loginId);
                if(hb != null){
                    return hb.getFullName();
                }else{
                    return null;
                }
        }
    
        /**
         * �քe�õ�������B
         * 
         * @param i
         * @return String
         */
        private String getStatus(int i) {
                String status = "";
                switch(i){
                    case 0:  status =DtoTimeSheet.STATUS_SUBMITTED;
                    break;
                    case 1:  status =DtoTimeSheet.STATUS_APPROVED;
                    break;
                    case 2:  status =DtoTimeSheet.STATUS_ACTIVE;
                    break;
                    case 3:  status =DtoTimeSheet.STATUS_REJECTED;
                    break;
                    case 4:  status =DtoTimeSheet.STATUS_PM_APPROVED;
                    break;
                    case 5:  status =DtoTimeSheet.STATUS_RM_APPROVED;
                    break;
                    default:  break;
                }
                return status;
        }
        
        /**
         * ���ݲ�ѯ�򵼳��������жϵ����ĸ�DTO
         * @return DtoUtilRate
         */
        private DtoStatusHuman getDtoDetailInstance() {
              if(excelDto) {
                  return new StyledDtoHumanStatus();
              } else {
                  return new DtoStatusHuman();
              }
          }
    
    
        public class StyledDtoHumanStatus extends DtoStatusHuman
                implements ICellStyleSwitch {
                public HSSFCellStyle getStyle(String styleName, HSSFCellStyle cellStyle) {
                return LevelCellStyleUtils.getStyleByName(styleName, cellStyle);
        }
    
        public String getStyleName(String propertyName) {
            return LevelCellStyleUtils.getStyleName(this);
         }
         }
    
        private DtoFilledRate getDtoFillRateInstance() {
            if (excelDto) {
                return new StyledDtoFillRate();
            } else {
                return new DtoFilledRate();
            }
        }

        public class StyledDtoFillRate extends DtoFilledRate implements
                ICellStyleSwitch {
                public HSSFCellStyle getStyle(String styleName, HSSFCellStyle cellStyle) {
                    return LevelCellStyleUtils.getStyleByName(styleName, cellStyle);
                }
                public String getStyleName(String propertyName) {
                    return LevelCellStyleUtils.getStyleName(this);
                }
        }
    
        public void setTsStatusDao(ITimesheetStatusDao tsStatusDao) {
            this.tsStatusDao = tsStatusDao;
        }
    
        public void setRmMaintDao(IRmMaintDao rmMaintDao) {
            this.rmMaintDao = rmMaintDao;
        }
    
        public void setPeriodDao(IPeriodDao periodDao) {
            this.periodDao = periodDao;
        }
    
        public void setIresDao(IResourceDao iresDao) {
            this.iresDao = iresDao;
        }
        
        public boolean isExcelDto() {
            return excelDto;
        }
    
        public void setExcelDto(boolean excelDto) {
            this.excelDto = excelDto;
        }
    
        public void setAccountDao(IAccountDao accountDao) {
            this.accountDao = accountDao;
        }
    
        public void setTimeSheetApiDao(ITimeSheetP3ApiDao timeSheetApiDao) {
            this.timeSheetApiDao = timeSheetApiDao;
        }
    
        public void setAttLeaveDao(IAttLeaveDao attLeaveDao) {
            this.attLeaveDao = attLeaveDao;
        }
        public void setCheckLeaveHours(String checkLeaveHours) {
            this.checkLeaveHours = checkLeaveHours;
        }

      
}
