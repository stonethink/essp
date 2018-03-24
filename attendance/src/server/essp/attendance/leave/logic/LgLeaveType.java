package server.essp.attendance.leave.logic;


import server.framework.common.BusinessException;
import server.essp.framework.logic.AbstractESSPLogic;
import java.util.List;
import net.sf.hibernate.Session;
import db.essp.attendance.TcLeaveType;
import java.util.ArrayList;
import server.essp.attendance.leave.viewbean.VbLeaveType;
import c2s.dto.DtoUtil;
import server.essp.attendance.leave.form.AfLeaveType;
import c2s.essp.attendance.Constant;
import java.util.Iterator;

public class LgLeaveType extends AbstractESSPLogic {

    /**
     * 列出所有的假别
     * @return List
     */
    public List listLeaveType(){
        try {
            List result = new ArrayList();
            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery("from TcLeaveType type order by type.seq").list();
            for(int i = 0;i < l.size();i ++){
                TcLeaveType leaveType = (TcLeaveType) l.get(i);
                VbLeaveType vb = new VbLeaveType();
                DtoUtil.copyBeanToBean(vb,leaveType);
                if(server.framework.common.Constant.RST_NORMAL.equals(leaveType.getStatus())){
                    vb.setStatus(Constant.LEAVE_TYPE_STATUS_EN);
                }else if(server.framework.common.Constant.RST_DELETE.equals(leaveType.getStatus())){
                    vb.setStatus(Constant.LEAVE_TYPE_STATUS_DIS);
                }
                result.add(vb);
            }
            return result;
        } catch (Exception ex) {
            throw new BusinessException("TC_LV0002","can not list leave type!",ex);
        }
    }
    /**
     * 列出所有可用的假别
     * @return List
     */
    public List listEnableLeaveType(){
        List list = listLeaveType();
        Iterator it = list.iterator();
        while(it.hasNext()){
            VbLeaveType vb = (VbLeaveType) it.next();
            if(Constant.LEAVE_TYPE_STATUS_DIS.equals(vb.getStatus())){
                it.remove();
            }
        }
        return list;
    }
    /**
     * 新增假别
     * @param form AfLeaveType
     */
    public void addLeaveType(AfLeaveType form){
        if(form == null || form.getLeaveName() == null)
            throw new BusinessException("TC_LV0003","can not add null leave type!");
        if(getLeaveType(form.getLeaveName()) != null)
            throw new BusinessException("TC_LV0005","the leave type [" + form.getLeaveName() + "] has been in system!");
        TcLeaveType leaveType = new TcLeaveType();
        DtoUtil.copyBeanToBean(leaveType,form);
        leaveType.setMaxDay(new Long(form.getMaxDay()));
        leaveType.setSeq(new Long(form.getSeq()));
        try {
            this.getDbAccessor().save(leaveType);
        } catch (Exception ex) {
            throw new BusinessException("TC_LV0004","can not add leave type!",ex);
        }
    }
    /**
     * 更新假别
     * @param form AfLeaveType
     */
    public void updateLeaveType(AfLeaveType form){
        if(form == null || form.getLeaveName() == null) {
            throw new BusinessException("TC_LV0003","can not update null leave type!");
        }
        TcLeaveType leaveType = getLeaveType(form.getLeaveName());
        DtoUtil.copyBeanToBean(leaveType,form);
        leaveType.setRelation(form.getRelation());
        leaveType.setMaxDay(new Long(form.getMaxDay()));
        leaveType.setSeq(new Long(form.getSeq()));
        leaveType.setDescription(form.getDescription());
        leaveType.setSettlementWay(form.getSettlementWay());
        leaveType.setStatus(form.getStatus());
    }
    public TcLeaveType getLeaveType(String leaveName){
        try {
            return (TcLeaveType)this.getDbAccessor().get(TcLeaveType.class,
                    leaveName);
        } catch (Exception ex) {
            throw new BusinessException("TC_LV0005","can not get leave type!",ex);
        }
    }
}
