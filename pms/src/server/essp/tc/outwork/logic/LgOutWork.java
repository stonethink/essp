package server.essp.tc.outwork.logic;

import server.framework.logic.AbstractBusinessLogic;
import server.framework.common.BusinessException;
import db.essp.tc.TcOutWorker;
import java.util.List;
import net.sf.hibernate.Session;
import server.essp.tc.outwork.form.AfSearchForm;
import java.util.ArrayList;
import net.sf.hibernate.Query;
import itf.orgnization.OrgnizationFactory;
import itf.orgnization.IOrgnizationUtil;
import itf.hr.LgHrUtilImpl;
import itf.hr.HrFactory;
import server.essp.tc.outwork.viewbean.vbOutWorker;
import itf.account.IAccountUtil;
import itf.account.AccountFactory;
import server.essp.pwm.wp.logic.LGGetActivityList;
import com.wits.util.comDate;
import java.util.Date;
import server.framework.common.Constant;
import c2s.dto.DtoTreeNode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import c2s.essp.common.calendar.WorkCalendarBase;
import c2s.essp.common.calendar.WrokCalendarFactory;
import java.util.Calendar;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import com.wits.util.IgnoreCaseStringComparator;
import server.essp.common.syscode.LgSysParameter;
import db.essp.code.SysParameter;

public class LgOutWork extends AbstractBusinessLogic {

    /**
     * 新增一条outWorker记录
     * @param outWorker TcOutWorker
     * @throws BusinessException
     */
    public void add(TcOutWorker outWorker) throws BusinessException {
        try {
            this.getDbAccessor().save(outWorker);
            this.getDbAccessor().commit();
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("Error", "add outwork record error!");
        }
    }

    /**
     * 根据主键更新outWorker
     * @param outWorker TcOutWorker
     * @throws BusinessException
     */
    public void update(TcOutWorker outWorker) {

        try {
            Session session = this.getDbAccessor().getSession();
            TcOutWorker tow = (TcOutWorker) session.load(TcOutWorker.class, outWorker.getRid());
            tow.setLoginId(outWorker.getLoginId());
            tow.setBeginDate(outWorker.getBeginDate());
            tow.setEndDate(outWorker.getEndDate());
            tow.setDestAddress(outWorker.getDestAddress());
            tow.setDays(outWorker.getDays());
            tow.setIsAutoWeeklyReport(outWorker.getIsAutoWeeklyReport());
            if(outWorker.getIsTravellingAllowance()==null || outWorker.getIsTravellingAllowance().length()==0){
                tow.setIsTravellingAllowance("");
            }else{
                tow.setIsTravellingAllowance(outWorker.getIsTravellingAllowance());
            }
            tow.setActivityRid(outWorker.getActivityRid());
            tow.setAcntRid(outWorker.getAcntRid());
            tow.setEvectionType(outWorker.getEvectionType());
            this.getDbAccessor().update(tow);
            this.getDbAccessor().commit();
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("Error", "update outwork record error!");
        }
    }

    /**
     * 删除相应的outWorker记录
     * @param outWorker TcOutWorker
     * @throws BusinessException
     */
    public void delete(TcOutWorker outWorker) throws BusinessException {
        try {

            this.getDbAccessor().delete(outWorker);
            this.getDbAccessor().commit();

        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("Error", "delete outwork record error!");
        }
    }

    /**
     * 根据主键删除相应的outWorker记录
     * @param Rid Long
     * @throws BusinessException
     */
    public void delete(Long Rid) throws BusinessException {
        try {

            this.getDbAccessor().executeUpdate(
                    "delete from TC_OUTWORKER where RID=" +
                    Rid);

        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("Error", "delete outwork record error by Rid!");
        }
    }


    /**
     *
     * @param afSearchForm AfSearchForm
     * @throws BusinessException
     */
    public List listAll(AfSearchForm afSearchForm) throws BusinessException {

        List result = new ArrayList();
        List list = new ArrayList();
        String str = str = "from TcOutWorker as t where t.rst='" + Constant.RST_NORMAL +
                         "' ";
        try {
            Session session = this.getDbAccessor().getSession();

            if (afSearchForm.getAcntRid() != null && !afSearchForm.getAcntRid().equals("")) {
                Long acntRid = new Long(afSearchForm.getAcntRid());
                str = str + " and t.acntRid='" + acntRid + "'";
            }
            if (afSearchForm.getBeginDate() != null && !afSearchForm.getBeginDate().equals("")) {
                String begindatge = afSearchForm.getBeginDate();
                str = str + " and to_char(t.endDate,'yyyyMMdd')>='" + begindatge + "'";
            }
            if (afSearchForm.getEndDate() != null && !afSearchForm.getEndDate().equals("")) {
                String enddatge = afSearchForm.getEndDate();
                str = str + " and to_char(t.beginDate,'yyyyMMdd')<= '" + enddatge + "'";
            }
            if (afSearchForm.getLoginId() != null && !afSearchForm.getLoginId().equals("")) {
                str = str + " and t.loginId like '%" + afSearchForm.getLoginId() + "%'";
            }
            if ((afSearchForm.getAcntRid() == null || afSearchForm.getAcntRid().equals("")) && afSearchForm.getDepartRid() != null &&
                !afSearchForm.getDepartRid().equals("")) {
                IOrgnizationUtil orgUtil = OrgnizationFactory.create();
                List acntList = (List) orgUtil.getAcntListInOrgs("'" + afSearchForm.getDepartRid() + "'");
                if (acntList.size() > 1) {
                    str = str + " and ( ";
                    for (int i = 0; i < acntList.size(); i++) {
                        Long acntId = (Long) acntList.get(i);
                        if (i == 0) {
                            str = str + " t.acntRid='" + acntId + "'";
                        } else {
                            str = str + " or t.acntRid='" + acntId + "'";
                        }
                    }
                    str = str + " )";
                } else {
                    Long acntId = (Long) acntList.get(0);
                    str = str + " and  t.acntRid='" + acntId + "'";
                }

            }
            str = str + " order by t.rid desc";
            Query query = session.createQuery(str);
            list = query.list();
            LgHrUtilImpl ihui = (LgHrUtilImpl) HrFactory.create();
            String chinesename = "";
            IAccountUtil acntUtil = AccountFactory.create();
            LGGetActivityList lgGetActivity = new LGGetActivityList();
            for (int i = 0; i < list.size(); i++) {
                vbOutWorker vbow = new vbOutWorker();
                TcOutWorker tow = (TcOutWorker) list.get(i);
                if (tow.getLoginId() != null && !tow.getLoginId().equals("")) {
                    chinesename = ihui.getChineseName(tow.getLoginId());
                }
                vbow.setRid(tow.getRid());
                vbow.setAccount(acntUtil.getAccountByRID(tow.getAcntRid()).getDisplayName());
                if (tow.getActivityRid() != null && !tow.getActivityRid().equals("")) {
                    vbow.setActivity(lgGetActivity.getActivityName(tow.getActivityRid(), tow.getAcntRid()));
                }
                vbow.setChineseName(chinesename == null ? "" : chinesename);
                vbow.setBeginDate(comDate.dateToString(tow.getBeginDate()));
                vbow.setEndDate(comDate.dateToString(tow.getEndDate()));
                vbow.setDays(tow.getDays().toString());
                if (tow.getDestAddress() != null && !tow.getDestAddress().equals("")) {
                    vbow.setDestAddress(tow.getDestAddress());
                }
                if (tow.getIsAutoWeeklyReport() != null && !tow.getIsAutoWeeklyReport().equals("")) {
                    vbow.setIsAutoFillWR(tow.getIsAutoWeeklyReport().toString());
                }
                if(tow.getIsTravellingAllowance()!=null && !tow.getIsTravellingAllowance().equals("")){
                    vbow.setIsTravellingAllowance(tow.getIsTravellingAllowance());
                }else{
                    vbow.setIsTravellingAllowance("false");
                }
                vbow.setLoginId(tow.getLoginId());
                vbow.setOrganization(acntUtil.getAccountByRID(tow.getAcntRid()).getOrganization());
                vbow.setEvectionType("");
                LgSysParameter lg = new LgSysParameter();
                List evectionTypeList = lg.listSysParas("EVECTION_TYPE");
                Iterator j = evectionTypeList.iterator();
                String evectionType = tow.getEvectionType();
                while(j.hasNext()){
                    SysParameter param = (SysParameter) j.next();
                    if(param.getComp_id().getCode().equals(evectionType)){
                        vbow.setEvectionType(param.getName());
                    }
                }
                result.add(vbow);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
//            log.error(ex);
//            throw new BusinessException("Error", "search outwork record error!");
        }
        return result;
    }


    /**
     *
     * @param afSearchForm AfSearchForm
     * @throws BusinessException
     */
    public DtoTreeNode listAllForTCReport(Date begin,Date end) throws BusinessException {
        DtoTreeNode rootTree = null; //=new DtoTreeNode();
        int totalDays=0;
        List list = new ArrayList();

        String str = "from TcOutWorker as t where t.rst='" + Constant.RST_NORMAL +
                     "' and t.isTravellingAllowance='true' ";
        if (begin != null) {
            str = str + " and to_char(t.endDate,'yyyyMMdd')>='" + comDate.dateToString(begin,"yyyyMMdd") + "'";
        }else{
            throw new BusinessException("","begin date not null");
        }
        if (end!=null) {
            str = str + " and to_char(t.beginDate,'yyyyMMdd')<= '" + comDate.dateToString(end,"yyyyMMdd") + "'";
        }
        else{
            throw new BusinessException("","end date not null");
        }

        str = str + " order by t.acntRid,t.rid desc";

        try {
            Session session = this.getDbAccessor().getSession();

            Query query = session.createQuery(str);
            list = query.list();

            LgHrUtilImpl ihui = (LgHrUtilImpl) HrFactory.create();
            String chinesename = "";
            IAccountUtil acntUtil = AccountFactory.create();
            LGGetActivityList lgGetActivity = new LGGetActivityList();
            String acountRidFlag = ""; //标志位，用于判断后面的每条数据是否相同
            HashMap hm = new HashMap();
            ArrayList listForSort=new ArrayList();//此LIST为排序用

            for (int i = 0; i < list.size(); i++) {

                vbOutWorker vbow = new vbOutWorker();
                TcOutWorker tow = (TcOutWorker) list.get(i);
                if (tow.getLoginId() != null && !tow.getLoginId().equals("")) {
                    chinesename = ihui.getChineseName(tow.getLoginId());
                }
                vbow.setAccount(acntUtil.getAccountByRID(tow.getAcntRid()).getDisplayName());
                if (tow.getActivityRid() != null && !tow.getActivityRid().equals("")) {
                    vbow.setActivity(lgGetActivity.getActivityName(tow.getActivityRid(), tow.getAcntRid()));
                }
                vbow.setChineseName(chinesename == null ? "" : chinesename);

                if(tow.getBeginDate().getTime()<begin.getTime()){
                    //如果数据的日期比开始区间　早　，则　时间为开始区间
                    vbow.setBeginDate(comDate.dateToString(begin));
                }else{
                    vbow.setBeginDate(comDate.dateToString(tow.getBeginDate()));
                }
                if(tow.getEndDate().getTime()>end.getTime()){
                    vbow.setEndDate(comDate.dateToString(end));
                }else{
                    vbow.setEndDate(comDate.dateToString(tow.getEndDate()));
                }
                //计算实际天数，并加到总天数上去
                Calendar b=Calendar.getInstance();
                b.setTime(comDate.toDate(vbow.getBeginDate()));
                Calendar e=Calendar.getInstance();
                e.setTime(comDate.toDate(vbow.getEndDate()));
                int dayNum=WorkCalendarBase.getDayNum(b,e);
                totalDays=totalDays+dayNum;
                vbow.setDays(Integer.toString(dayNum));

                if (tow.getDestAddress() != null && !tow.getDestAddress().equals("")) {
                    vbow.setDestAddress(tow.getDestAddress());
                }
                if (tow.getIsAutoWeeklyReport() != null && !tow.getIsAutoWeeklyReport().equals("")) {
                    vbow.setIsAutoFillWR(tow.getIsAutoWeeklyReport().toString());
                }
                vbow.setLoginId(tow.getLoginId());
                vbow.setOrganization(acntUtil.getAccountByRID(tow.getAcntRid()).getOrganization());
                vbow.setIsTravellingAllowance(tow.getIsTravellingAllowance());//是否有津贴
                //出差性质
                vbow.setEvectionType("");
                LgSysParameter lg = new LgSysParameter();
                List evectionTypeList = lg.listSysParas("EVECTION_TYPE");
                Iterator j = evectionTypeList.iterator();
                String evectionType = tow.getEvectionType();
                while(j.hasNext()){
                   SysParameter param = (SysParameter) j.next();
                   if(param.getComp_id().getCode().equals(evectionType)){
                       vbow.setEvectionType(param.getName());
                   }
                }

                DtoTreeNode leaf = new DtoTreeNode(vbow);
                listForSort.add(vbow.getAccount());
                //加数据到上一层
                if (vbow.getAccount().equals(acountRidFlag)) { //与上一条数据项目相同
                    //days加起来,并把该条数据加到上一条中
                    DtoTreeNode centerNode = (DtoTreeNode) hm.get(acountRidFlag);
                    vbOutWorker dataBean = (vbOutWorker) centerNode.getDataBean();
                    int daySum = Integer.parseInt(dataBean.getDays()) + Integer.parseInt(vbow.getDays());
                    dataBean.setDays(Integer.toString(daySum));
                    centerNode.addChild(leaf);

                    hm.put(acountRidFlag, centerNode);
                } else { //与上一项目不是同一项目
                    vbOutWorker dataBean = new vbOutWorker();
                    dataBean.setAccount(vbow.getAccount());
                    dataBean.setDays(vbow.getDays());
                    DtoTreeNode centerNode = new DtoTreeNode(dataBean);
                    acountRidFlag = vbow.getAccount();//tow.getAcntRid().toString();
                    centerNode.addChild(leaf);
                    hm.put(acountRidFlag, centerNode);
                }
                //把最后一条加进去

            }//for End

            vbOutWorker root=new vbOutWorker();
            root.setAccount("合计");
            root.setDays(Integer.toString(totalDays));
            rootTree=new DtoTreeNode(root);
            //把HashMap中的数据组成一颗树
            Set set = new HashSet(listForSort);
            Object []sortObj=new Object[set.size()];
            set.toArray(sortObj);
            Arrays.sort(sortObj,new IgnoreCaseStringComparator());
            for(int k=0;k<sortObj.length;k++){
                rootTree.addChild(((DtoTreeNode)hm.get((String)sortObj[k])));
            }
//            for(Iterator iter = hm.entrySet().iterator(); iter.hasNext();){
//                Entry item = (Entry) iter.next();
//                rootTree.addChild((DtoTreeNode)item.getValue());
//            }

        } catch (Exception ex) {
            ex.printStackTrace();
//            log.error(ex);
//            throw new BusinessException("Error", "search outwork record error!");
        }
        return rootTree;
    }


    public TcOutWorker loadByRid(Long rid) throws BusinessException {
        TcOutWorker tow = null;
        List result = new ArrayList();
        try {
            Session session = this.getDbAccessor().getSession();
            Query query = session.createQuery("from TcOutWorker as t where t.rid=" +
                                              rid);
            result = query.list();
            tow = (TcOutWorker) result.get(0);

        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("Error", "search outwork record error!");
        }
        return tow;
    }

    public static void main(String[] args) throws Exception {
        TcOutWorker tow = new TcOutWorker();
        Long acntrid = new Long("6022");
        tow.setAcntRid(acntrid);
        tow.setLoginId("mingxing.zhang");
        tow.setBeginDate(new java.util.Date());
        tow.setEndDate(new java.util.Date());
        tow.setDestAddress("BJ");
        tow.setDays(new Long("7"));

        LgOutWork low = new LgOutWork();
        low.add(tow);
//        tow = low.loadByRid(new Long("19"));
//        System.out.println(tow.getDays());

//        AfSearchForm af=new AfSearchForm();
//        af.setDays(20);
//        af.setAcntRid("6022");
        //TcOutWorker tow =low.loadByRid(new Long("11"));
//        low.delete(tow);

    }

}
