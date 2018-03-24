/*
 * Created on 2008-1-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.attvariant.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import com.wits.util.comDate;
import server.essp.common.ldap.LDAPUtil;
import server.essp.common.mail.ContentBean;
import server.essp.common.mail.SendHastenMail;
import server.essp.timesheet.report.attvariant.dao.IAttVariantDao;
import server.essp.timesheet.rmmaint.dao.IRmMaintDao;
import c2s.dto.DtoComboItem;
import c2s.dto.DtoUtil;
import c2s.essp.common.user.DtoRole;
import c2s.essp.common.user.DtoUserInfo;
import c2s.essp.timesheet.report.DtoAttVariant;
import c2s.essp.timesheet.report.DtoAttVariantContent;
import c2s.essp.timesheet.report.DtoAttVariantMail;
import c2s.essp.timesheet.report.DtoAttVariantQuery;
import db.essp.timesheet.TsHumanBase;

/**
 * AttVariantServiceImp
 * @author TuBaoHui
 */
public class AttVariantServiceImp implements IAttVariantService{
    private IAttVariantDao attVariantDao;
    private IRmMaintDao rmMaintDao;
    private static final String vmFile = "mail/template/ts/AttVariantMail.htm";
    
    /**
     * 根据查询条件查询出在TIMECARD和ATT_LEAVE，ATT_OVERTIME中的请假，加班时间差异列表
     * @param dtoQuery
     * @return List
     */
    public Map queryByCondition(DtoAttVariantQuery dtoQuery) {
       Map map = new HashMap();
       List leaveList = attVariantDao.queryLeaveVariant(dtoQuery);
       List overtimeList = attVariantDao.queryOvertimeVariant(dtoQuery);
       distinctRow(leaveList,true);
       distinctRow(overtimeList,false);
       map.put(DtoAttVariantQuery.DTO_LEAVE_HOURS, leaveList);
       map.put(DtoAttVariantQuery.DTO_OVERTIME_HOURS, overtimeList);
       return map;
    }
    
    /**
     * 得到不重復SITE，且判斷當前登錄者是否為PMO
     * @param roleList
     * @return Map
     */
    public Map getSiteList(List rolesList,String loginId) {
        Map map = new HashMap();
        String site = "";
        List siteList = new ArrayList();
        TsHumanBase tshb = rmMaintDao.loadHumanById(loginId);
        if(tshb != null){
            site = tshb.getSite();
        }
        Boolean isPMO = false;
        DtoRole dtoRole = null;
        Vector siteVector = new Vector();
        if(rolesList != null && rolesList.size() > 0){
         for(int i = 0;i<rolesList.size();i++){  
          dtoRole = (DtoRole)rolesList.get(i);
          String roleId = dtoRole.getRoleId();
          if(DtoRole.ROLE_APO.equals(roleId) || DtoRole.ROLE_HAP.
                  equals(roleId)||DtoRole.ROLE_UAP.equals(roleId)
                  || DtoRole.ROLE_SYSUSER.equals(roleId)){
              isPMO = true;
          }
        }
        if(isPMO){
         siteList = rmMaintDao.getSiteFromHumanBase(site);
        }
       }
        siteVector = beanList2DtoComboItemList(site,siteList);
        map.put(DtoAttVariantQuery.DTO_SITE_LIST,siteVector);
        map.put(DtoAttVariantQuery.DTO_IS_PMO,isPMO);
        return map;
    }
    
    /**
     * 清空重复记录
     * @param list
     */
    private void distinctRow(List<DtoAttVariant> list,boolean flag) {
        Long tsLastRid = null;
        Long hrLastRid = null;
        for(DtoAttVariant dto : list) {
            Long tsRid = dto.getTsRid();
            if(tsRid != null && tsLastRid != null && tsRid.equals(tsLastRid)) {
                dto.setTsUnitCode(null);
                dto.setTsCode(null);
                dto.setTsHours(null);
            }
            tsLastRid = tsRid;
            
            Long hrRid = dto.getHrRid();
            if(hrRid != null && hrLastRid != null && hrRid.equals(hrLastRid)) {
                dto.setHrUnitCode(null);
                dto.setHrCode(null);
                dto.setHrHours(null);
            }
            hrLastRid = hrRid;
            
            if(dto.getTsHours() == null || dto.getHrHours() == null
                    || (!dto.getTsHours().equals(dto.getHrHours()))){
                dto.setBalanced(true);
            }else{
                dto.setBalanced(false);
            }
            dto.setLeave(flag);
        }
    }
    
    /**
     * 將Site集合放入Vector中
     * @param beanList
     * @return Vector
     */
    private static Vector beanList2DtoComboItemList(String site,
            List beanList) {
        Vector siteVector = new Vector(beanList.size());
        if(beanList != null){
         siteVector.add(new DtoComboItem(site,site));
         for (int i=0;i<beanList.size();i++) {
            String sites = (String)beanList.get(i);
            DtoComboItem dto = new DtoComboItem(sites,sites);
            siteVector.add(dto);
         }
        }
        return siteVector;
    }
    
    /**
     * 发送邮件
     */
    public void sendMails(List<DtoAttVariant> sendList) {
        HashMap hm = new HashMap();
        DtoUserInfo dtoUser = null;
        ArrayList al = null;
        ContentBean cb = null;
        String userMail = null;
        String userName = null;
        LDAPUtil ldapUtil = new LDAPUtil();
        HashMap leaveMap = new HashMap();
        HashMap otMap = new HashMap();
        List<String> employeeList = new ArrayList();
        //按员工将请假和加班记录分别放入请假，加班对应的MAP中，并将不重复的员工代号放入员工LIST中
        setMapByEmployee(sendList,leaveMap,otMap,employeeList);
        //将同一员工的请假和加班差异记录放到同一封邮件中
        for(String loginId : employeeList){
            List leaveList = (List)leaveMap.get(loginId);
            List otList = (List)otMap.get(loginId);
            al = new ArrayList();
            cb = new ContentBean();
            DtoAttVariantContent attContent = new DtoAttVariantContent();
            if(leaveList != null){
             attContent.setLeaveList(leaveList);
            }
            if(otList != null){
             attContent.setOvertimeList(otList);
            }
            al.add(attContent);
            dtoUser = ldapUtil.findUser(LDAPUtil.DOMAIN_ALL, loginId);
            if (dtoUser != null) {
                userMail = dtoUser.getEmail();
                userName = dtoUser.getUserName();
                cb.setUser(userName);
                cb.setEmail(userMail);
                cb.setMailcontent(al);
                hm.put(userName, cb);
            }
        }
        sendMail(vmFile, "Attendance Variant Mail", hm);
    }
    
    /**
     * 按员工将请假和加班记录分别放入请假，加班对应的MAP中，并将不重复的员工代号放入员工LIST中
     * @param sendList
     * @param leaveMap
     * @param otMap
     */
    private void setMapByEmployee(List<DtoAttVariant> sendList,HashMap leaveMap,
            HashMap otMap, List<String> employeeList){
        DtoAttVariantMail dtoMail = null;
        HashMap employeeMap = new HashMap();
        for(DtoAttVariant dto : sendList){
            String loginId = dto.getLoginId();
            dtoMail = getDtoAttVariantMail(dto);
            if(!employeeMap.containsKey(loginId)){
                employeeMap.put(loginId,loginId);
//              employeeList存放不重复的员工代号
                employeeList.add(loginId);
            }
            if(dto.isLeave()){
                if(leaveMap.containsKey(loginId)){
                    List leaveList = (List)leaveMap.get(loginId);
                    leaveList.add(dtoMail);
                }else{
                    List leaveList = new ArrayList();
                    leaveList.add(dtoMail);
                    leaveMap.put(loginId,leaveList);
                }
            }else{
                if(otMap.containsKey(loginId)){
                    List otList = (List)otMap.get(loginId);
                    otList.add(dtoMail);
                }else{
                    List otList = new ArrayList();
                    otList.add(dtoMail);
                    otMap.put(loginId,otList);
                }
            }
        }
    }
    
    /**
     * 将DtoAttVariant中的信息拷贝到DtoAttVariantMail中
     * @param dto
     * @return DtoAttVariantMail
     */
    private DtoAttVariantMail getDtoAttVariantMail(DtoAttVariant dto){
        DtoAttVariantMail dtoMail = new DtoAttVariantMail();
        DtoUtil.copyBeanToBean(dtoMail,dto);
        dtoMail.setAttDate(comDate.dateToString(dto.getAttDate(),"yyyy-MM-dd"));
        if(dtoMail.getTsCode() == null){
            dtoMail.setTsCode("");
        }
        if(dtoMail.getTsHours() == null){
            dtoMail.setTsHours(Double.valueOf(0));
        }
        if(dtoMail.getTsUnitCode() == null){
            dtoMail.setTsUnitCode("");
        }
        if(dtoMail.getHrCode() == null){
            dtoMail.setHrCode("");
        }
        if(dtoMail.getHrUnitCode() == null){
            dtoMail.setHrUnitCode("");
        }
        if(dtoMail.getHrHours() == null){
            dtoMail.setHrHours(Double.valueOf(0));
        }
        return dtoMail;
    }
    
    /**
     * 发送邮件
     * @param vmFile
     * @param title
     * @param hm
     */
    private void sendMail(final String vmFile, final String title, final HashMap hm) {
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
            t.stop();
        }
    }
    
    public void setAttVariantDao(IAttVariantDao attVariantDao) {
        this.attVariantDao = attVariantDao;
    }

    public void setRmMaintDao(IRmMaintDao rmMaintDao) {
        this.rmMaintDao = rmMaintDao;
    }
  }
