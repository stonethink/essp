package server.essp.pwm.workbench.logic;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.essp.common.user.DtoUser;
import c2s.essp.pwm.workbench.DtoPwWkitem;
import c2s.essp.pwm.workbench.DtoPwWkitemPeriod;
import com.wits.util.StringUtil;
import com.wits.util.comDate;
import essp.tables.PwWkitem;
import essp.tables.PwWp;
import net.sf.hibernate.Query;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import server.framework.hibernate.HBSeq;

public class LgWorkItemList extends AbstractESSPLogic {
    String loginId = null;

    static final String pattern = "yyyyMMdd";

    public LgWorkItemList() {
        DtoUser user = this.getUser();
        if (user != null) {
            loginId = user.getUserLoginId();
        } else {
            //loginId = "stone.shi";
            throw new BusinessException("E001","Can't get user information from session.Please login first!");
        }
    }

    public List select(Date wkitemDate) {
        List workItems = new ArrayList();

        try {
            if (wkitemDate != null) {
                String sWkitemDate = comDate.dateToString(wkitemDate, pattern);

                Query q = getDbAccessor().getSession().createQuery("from essp.tables.PwWkitem t " +
                    " where t.wkitemOwner = :wkitemOwner "
                    + " and TO_CHAR(t.wkitemDate,'" + pattern + "') = :wkitemDate "
                    + " order by TO_CHAR(t.wkitemStarttime,'HHmmss') ");
                q.setString("wkitemOwner", loginId);
                q.setString("wkitemDate", sWkitemDate);

                List orgWkitems = q.list();
                workItems = DtoUtil.list2List(orgWkitems, DtoPwWkitem.class);

                for (Iterator iter = workItems.iterator(); iter.hasNext(); ) {
                    DtoPwWkitem item = (DtoPwWkitem) iter.next();

                    if (item.getWkitemWkhours() == null) {
                        item.setWkitemWkhours(new BigDecimal(0));
                    }
                    item.setWkitemWkhoursOld(new BigDecimal(item.getWkitemWkhours().doubleValue()));


                    item.setWpIdOld(item.getWpId());
                }
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("E000000",
                                        "Error when select work items.", ex);
        }

        return workItems;
    }

    public void update(List workItems) {
        try {
            if (workItems == null) {
                return;
            }

            updateWpActWkhrAndTime(getDbAccessor(), workItems);

            for (int i = 0; i < workItems.size(); i++) {
                DtoPwWkitem dtoPwWkitem = (DtoPwWkitem) workItems.get(i);
                if ("".equals(dtoPwWkitem.getWkitemName().trim()) == true ||
                    (new BigDecimal(0.00)).compareTo(dtoPwWkitem.getWkitemWkhours()) >= 0) {
                    continue;
                }

                PwWkitem wkitem = new PwWkitem();
                if (dtoPwWkitem.isInsert()) {

                    DtoUtil.copyProperties(wkitem, dtoPwWkitem);
                    getDbAccessor().save(wkitem);

                    dtoPwWkitem.setRid(wkitem.getRid());
                } else if (dtoPwWkitem.isDelete()) {

                    DtoUtil.copyProperties(wkitem, dtoPwWkitem);

                    log.debug("delete daily report: " + wkitem.getRid() + " -- " + wkitem.getWkitemName());
                    getDbAccessor().delete(wkitem);

                    workItems.remove(i);
                    i--;
                } else if (dtoPwWkitem.isChanged()) {

                    DtoUtil.copyProperties(wkitem, dtoPwWkitem);
                    getDbAccessor().update(wkitem);

                }
                dtoPwWkitem.setOp(IDto.OP_NOCHANGE);
            }

        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000", "Update wp work item error.", ex);
        }
    }

    public static void updateWpActWkhrAndTime(HBComAccess dbAccessor, List workItems) {
        if (workItems == null) {
            return;
        }

        //以“WpId”为key
        Map wpDateMap = new HashMap();

        //以“WpId”为key
        Map wpWkhourMap = new HashMap();

        for (int i = 0; i < workItems.size(); i++) {
            DtoPwWkitem dtoPwWkitem = (DtoPwWkitem) workItems.get(i);
            Long wpId = dtoPwWkitem.getWpId();
            Long wpIdOld = dtoPwWkitem.getWpIdOld();
            if (wpId == null && wpIdOld == null) {

                continue;
            }

            if( dtoPwWkitem.isdlrpt() == false ){
                continue;
            }

            BigDecimal wkhour = dtoPwWkitem.getWkitemWkhours();
            BigDecimal wkhourOld = dtoPwWkitem.getWkitemWkhoursOld();
            if (wkhour == null) {
                wkhour = new BigDecimal(0);
            }
            if (wkhourOld == null) {
                wkhourOld = new BigDecimal(0);
            }

            if (dtoPwWkitem.isInsert()) {

            } else if (dtoPwWkitem.isDelete()) {

                //delete work hours from old wp
                if (wpIdOld != null) {

                    //delete work hours from old wp
                    BigDecimal totalDiffOld = (BigDecimal) wpWkhourMap.get(wpIdOld);
                    if (totalDiffOld == null) {
                        totalDiffOld = new BigDecimal(0);
                    }
                    totalDiffOld = totalDiffOld.subtract(wkhourOld);
                    wpWkhourMap.put(wpIdOld, totalDiffOld);
                }
            } else if (dtoPwWkitem.isChanged()) {

                //delete work hours from old wp
                if( wpIdOld != null){
                    if( wpId == null || wpId.equals(wpIdOld)==false ){

                        //delete work hours from old wp
                        BigDecimal totalDiffOld = (BigDecimal)wpWkhourMap.get(wpIdOld);
                        if( totalDiffOld == null ){
                            totalDiffOld = new BigDecimal(0);
                        }
                        totalDiffOld = totalDiffOld.subtract(wkhourOld);
                        wpWkhourMap.put(wpIdOld, totalDiffOld);
                    }
                }

                //update work hours of new wp
                if (wpId != null) {
                    BigDecimal totalDiff = null;
                    totalDiff = (BigDecimal) wpWkhourMap.get(wpId);
                    if (totalDiff == null) {
                        totalDiff = new BigDecimal(0.0);
                    }

                    if (wpId.equals(wpIdOld) == true) {
                        totalDiff = totalDiff.add(wkhour.subtract(wkhourOld));
                    } else {
                        wpDateMap.put(wpId, dtoPwWkitem.getWkitemDate());
                        totalDiff = totalDiff.add(wkhour);
                    }

                    wpWkhourMap.put(wpId, totalDiff);
                }

                dtoPwWkitem.setWpIdOld(dtoPwWkitem.getWpId());
                dtoPwWkitem.setWkitemWkhoursOld(dtoPwWkitem.getWkitemWkhours());
            }

        }

        updateWpActWkhrAndTime(dbAccessor, wpWkhourMap, wpDateMap );
    }

    private static void submitDailyReportToUpWpActWkhrAndDate(HBComAccess dbAccessor, List workItems) {
        if (workItems == null) {
            return;
        }

        //以“WpId”为key
        Map wpDateMap = new HashMap();

        //以“WpId”为key
        Map wpWkhourMap = new HashMap();

        for (int i = 0; i < workItems.size(); i++) {
            DtoPwWkitem dtoPwWkitem = (DtoPwWkitem) workItems.get(i);
            Long wpId = dtoPwWkitem.getWpId();
            Long wpIdOld = dtoPwWkitem.getWpIdOld();
            if (wpId == null && wpIdOld == null) {

                continue;
            }

            BigDecimal wkhour = dtoPwWkitem.getWkitemWkhours();
            BigDecimal wkhourOld = dtoPwWkitem.getWkitemWkhoursOld();
            if (wkhour == null) {
                wkhour = new BigDecimal(0);
            }
            if (wkhourOld == null) {
                wkhourOld = new BigDecimal(0);
            }

            if (dtoPwWkitem.isInsert()) {
                if( wpId != null ){
                    BigDecimal totalDiff = null;
                    totalDiff = (BigDecimal) wpWkhourMap.get(wpId);
                    if (totalDiff == null) {
                        totalDiff = new BigDecimal(0.0);
                    }

                    //add daily report
                    totalDiff = totalDiff.add(wkhour);

                    wpDateMap.put(wpId, dtoPwWkitem.getWkitemDate());
                    wpWkhourMap.put(wpId, totalDiff);

                    dtoPwWkitem.setWpIdOld(dtoPwWkitem.getWpId());
                    dtoPwWkitem.setWkitemWkhoursOld(dtoPwWkitem.getWkitemWkhours());
                }
            } else if (dtoPwWkitem.isDelete()) {

                //delete work hours from old wp
                if (dtoPwWkitem.isdlrpt() == true && wpIdOld != null) {

                    //delete work hours from old wp
                    BigDecimal totalDiffOld = (BigDecimal) wpWkhourMap.get(wpIdOld);
                    if (totalDiffOld == null) {
                        totalDiffOld = new BigDecimal(0);
                    }
                    totalDiffOld = totalDiffOld.subtract(wkhourOld);
                    wpWkhourMap.put(wpIdOld, totalDiffOld);
                }
            } else if (dtoPwWkitem.isChanged() ||
                       dtoPwWkitem.isdlrpt() == false) {

                //delete work hours from old wp
                if( dtoPwWkitem.isdlrpt() == true && wpIdOld != null){
                    if( wpId == null || wpId.equals(wpIdOld)==false ){

                        //delete work hours from old wp
                        BigDecimal totalDiffOld = (BigDecimal)wpWkhourMap.get(wpIdOld);
                        if( totalDiffOld == null ){
                            totalDiffOld = new BigDecimal(0);
                        }
                        totalDiffOld = totalDiffOld.subtract(wkhourOld);
                        wpWkhourMap.put(wpIdOld, totalDiffOld);
                    }
                }

                //update work hours of new wp
                if (wpId != null) {

                    BigDecimal totalDiff = null;
                    totalDiff = (BigDecimal) wpWkhourMap.get(wpId);
                    if (totalDiff == null) {
                        totalDiff = new BigDecimal(0.0);
                    }

                    if( dtoPwWkitem.isdlrpt() == true && wpId.equals(wpIdOld)==true ){
                        //update work hours of new wp
                        totalDiff = totalDiff.add(wkhour.subtract(wkhourOld));
                        wpWkhourMap.put(wpId, totalDiff);
                    }else{
                        //add work hours to new wp
                        totalDiff = totalDiff.add( wkhour);
                        wpWkhourMap.put(wpId, totalDiff);
                        wpDateMap.put(wpId, dtoPwWkitem.getWkitemDate());
                    }
                }

                dtoPwWkitem.setWpIdOld(dtoPwWkitem.getWpId());
                dtoPwWkitem.setWkitemWkhoursOld(dtoPwWkitem.getWkitemWkhours());
            }

        }

        updateWpActWkhrAndTime( dbAccessor, wpWkhourMap, wpDateMap );
    }

    private static void updateWpActWkhrAndTime(HBComAccess dbAccessor, Map wpWkhourMap, Map wpDateMap) {


        try {
            //update wp actual time
            for (Iterator iter = wpWkhourMap.keySet().iterator(); iter.hasNext(); ) {
                Long wpId = (Long) iter.next();
                PwWp pwWp;
                try {
                    pwWp = (PwWp) dbAccessor.load(PwWp.class, wpId);
                } catch (Exception ex1) {
                    pwWp = null;
                }

                if( pwWp == null ){
                    continue;
                }

                BigDecimal actWkhr = pwWp.getWpActWkhr();
                if (actWkhr == null) {
                    actWkhr = new BigDecimal(0.0);
                }
                actWkhr = actWkhr.add((BigDecimal) wpWkhourMap.get(wpId));
                if(actWkhr.doubleValue() < 0 ){
                    actWkhr = new BigDecimal(0.0);
                }
                pwWp.setWpActWkhr(actWkhr);

                Date wkDate = (Date) wpDateMap.get(wpId);
                if( wkDate != null ){
                    if (pwWp.getWpActStart() == null || isDateBigger(pwWp.getWpActStart(), wkDate)) {
                        pwWp.setWpActStart(wkDate);
                    }

                    if (pwWp.getWpActFinish() == null || isDateBigger(wkDate, pwWp.getWpActFinish())) {
                        pwWp.setWpActFinish(wkDate);
                    }
                }
                log.info("Update pwWp[" + pwWp.getRid() + "," + pwWp.getWpName() + "]");
                dbAccessor.update(pwWp);
            }
        } catch (Exception ex) {
            throw new BusinessException("E00000","Error when update the time of work package.",ex);
        }
    }

    public void submitDailyReport(List workItems) {
        try {
            if (workItems == null) {
                return;
            }
            submitDailyReportToUpWpActWkhrAndDate(getDbAccessor(), workItems);

            for (int i = 0; i < workItems.size(); i++) {
                DtoPwWkitem dtoPwWkitem = (DtoPwWkitem) workItems.get(i);

                PwWkitem wkitem = new PwWkitem();

                if (dtoPwWkitem.isInsert()) {

                    dtoPwWkitem.setWkitemIsdlrpt("1");
                    DtoUtil.copyProperties(wkitem, dtoPwWkitem);
                    getDbAccessor().save(wkitem);

                    dtoPwWkitem.setRid(wkitem.getRid());
                } else if (dtoPwWkitem.isDelete()) {

                    DtoUtil.copyProperties(wkitem, dtoPwWkitem);

                    log.debug("delete daily report: " + wkitem.getRid() + " -- " + wkitem.getWkitemName());
                    getDbAccessor().delete(wkitem);

                    workItems.remove(i);
                    i--;
                } else if (dtoPwWkitem.isChanged() ||
                           dtoPwWkitem.isdlrpt() == false) {
                    dtoPwWkitem.setWkitemIsdlrpt("1");

                    DtoUtil.copyProperties(wkitem, dtoPwWkitem);
                    getDbAccessor().update(wkitem);
                }

                dtoPwWkitem.setOp(IDto.OP_NOCHANGE);
            }

        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000", "Update wp work item error.", ex);
        }
    }

    public void custom(DtoPwWkitemPeriod dto) {
        if (dto.isInsert() == true || dto.isDelete()) {
            try {
                insert(dto);
            } catch (Exception ex) {
                throw new BusinessException("E000000", "Custom fail. Error when save the work item to copy.", ex);
            }
        }

        List period = dto.getPeriod();
        try {
            for (Iterator iter = period.iterator(); iter.hasNext(); ) {
                int[] item = (int[]) iter.next();
                int y = item[0];
                int m = item[1];
                int d = item[2];

                Calendar wkitemDate = Calendar.getInstance();
                wkitemDate.set(y, m, d);
                //System.out.println("(y m d):" + y + " " + m + " " + d);
                //System.out.println(comDate.dateToString(wkitemDate.getTime(), "yyyy-MM-dd"));
                PwWkitem wkitem = new PwWkitem();
                DtoUtil.copyBeanToBean(wkitem, dto);
                wkitem.setWkitemIsdlrpt("0");
                wkitem.setWkitemDate(wkitemDate.getTime());

                if (dto.getWkitemCopyfrom() != null) {
                    wkitem.setWkitemCopyfrom(dto.getWkitemCopyfrom());
                } else {
                    wkitem.setWkitemCopyfrom(dto.getRid());
                }

                //如果当天已存在相同的copy,则不dumplicate
                String sWkitemDate = comDate.dateToString(wkitem.getWkitemDate(), pattern);
                String selSql = "select count(*) AS num from PW_WKITEM t "
                                + " where t.WKITEM_OWNER ='" + wkitem.getWkitemOwner() + "' "
                                + " and TO_CHAR(t.WKITEM_DATE,'" + pattern + "') = '" + sWkitemDate + "' "
                                + " and t.WKITEM_COPYFROM=" + wkitem.getWkitemCopyfrom().longValue() + ""
                                ;
                ResultSet rset = getDbAccessor().executeQuery(selSql);
                if (rset.next()) {
                    if (rset.getLong("num") == 0) {
                        wkitem.setRid(new Long(HBSeq.getSEQ("PW_WKITEM")));
                        getDbAccessor().save(wkitem);
                    }
                }
            }
        } catch (Exception ex) {
            throw new BusinessException("E000000", "Customize work item error.", ex);
        }
    }

    public List delCustom(DtoPwWkitem dto) {
        List dateList = new ArrayList();
        Calendar date = Calendar.getInstance();
        String owner = StringUtil.nvl(dto.getWkitemOwner());
        Long copyFrom = dto.getWkitemCopyfrom();
        if (copyFrom == null) {
            copyFrom = dto.getRid();
            if (copyFrom == null) {
                return dateList;
            }
        }

        String selSql = "from PwWkitem t "
                        + " where t.wkitemOwner =:owner "
                        + " and (t.wkitemCopyfrom =:copyFrom) "
                        + " and (t.wkitemIsdlrpt !='1') "
                        ;
        try {
            Iterator it = getDbAccessor().getSession().createQuery(selSql)
                          .setString("owner", owner)
                          .setLong("copyFrom", copyFrom.longValue())
                          .iterate();
            List wkitemDelList = new ArrayList();
            while (it.hasNext()) {
                PwWkitem pwWkitem = (PwWkitem) it.next();
                if (pwWkitem.getWkitemDate() != null) {
                    date.setTime(pwWkitem.getWkitemDate());
                    int[] d = new int[] {date.get(Calendar.YEAR)
                              , date.get(Calendar.MONTH)
                              , date.get(Calendar.DAY_OF_MONTH)};
                    dateList.add(d);
                }
                wkitemDelList.add(pwWkitem);
            }
            getDbAccessor().delete(wkitemDelList);
        } catch (Exception ex) {
            throw new BusinessException("E000000", "Delete Customize error.", ex);
        }

        return dateList;
    }

    private void insert(DtoPwWkitem dtoPwWkitem) throws Exception {
        PwWkitem wkitem = new PwWkitem();

        DtoUtil.copyBeanToBean(wkitem, dtoPwWkitem);
        getDbAccessor().save(wkitem);
        dtoPwWkitem.setRid(wkitem.getRid());
    }


    private static boolean isDateBigger(Date left, Date right){
        if( left != null && right == null ){
            return true;
        }else if( left == null || right == null ){
            return false;
        }


        int y1= left.getYear();
        int m1= left.getMonth();
        int d1 = left.getDate();

        int y2= right.getYear();
        int m2= right.getMonth();
        int d2 = right.getDate();

        if( ( y1 * 1024 + m1 * 512 + d1 ) > (  y2 * 1024 + m2 * 512 + d2  ) ){
            return true;
        }else{
            return false;
        }
    }

    public static void main(String args[]) {
        LgWorkItemList logic = new LgWorkItemList();
        Calendar c = Calendar.getInstance();
        c.set(2005, 9, 10);
        System.out.println(logic.select(c.getTime()).size());

        DtoPwWkitem dto = new DtoPwWkitem();
        dto.setWkitemOwner("stone.shi");
        dto.setRid(new Long(20101));
        System.out.println(logic.delCustom(dto).size());

    }
}
