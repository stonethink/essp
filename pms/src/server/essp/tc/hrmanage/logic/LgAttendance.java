package server.essp.tc.hrmanage.logic;

import server.framework.logic.AbstractBusinessLogic;
import c2s.essp.tc.attendance.DtoAttendance;
import db.essp.tc.TcAttendance;
import server.framework.common.BusinessException;
import net.sf.hibernate.Session;
import java.util.List;
import net.sf.hibernate.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.sql.RowSet;
import java.util.Calendar;
import server.essp.tc.common.LgTcCommon;

public class LgAttendance extends AbstractBusinessLogic {


    /**
     * 新增一条attendance记录
     * @param dto DtoAttendance
     */
    public void add(DtoAttendance dto) {
        TcAttendance tcAttendance = new TcAttendance();
        try {
            tcAttendance.setType(dto.getAttendanceType());
            tcAttendance.setDate(dto.getAttendanceDate());
            tcAttendance.setRemark(dto.getRemark());
            tcAttendance.setUserId(dto.getLoginId());
            this.getDbAccessor().save(tcAttendance);

        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("Error", "add attendance record error!");
        }
    }

    /**
     * 根据主键删除相应的记录
     * @param dto DtoAttendance
     */
    public void delete(DtoAttendance dto) {
        TcAttendance tcAttendance;
        try {
            Long attendanceRid=dto.getRid();
            Session session = this.getDbAccessor().getSession();
            tcAttendance = (TcAttendance) session.load(TcAttendance.class, attendanceRid);
            if (tcAttendance != null) {
                session.delete(tcAttendance);
            }
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("Error", "delete attendance record error!");
        }

    }
    /**
     * 更新attendance
     * @param dto DtoAttendance
     */
    public void update(DtoAttendance dto){
        TcAttendance tcAttendance;
        try {
            Long attendanceRid=dto.getRid();
            Session session = this.getDbAccessor().getSession();
            tcAttendance = (TcAttendance) session.load(TcAttendance.class, attendanceRid);
            tcAttendance.setType(dto.getAttendanceType());
            tcAttendance.setDate(dto.getAttendanceDate());
            tcAttendance.setRemark(dto.getRemark());

            session.update(tcAttendance);

        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("Error", "update attendance record error!");
        }

    }
    /**
     * 根据loginId查询attendance记录
     * @param dto DtoAttendance
     * @return List
     */
    public List attendanceList(String userId,Date beginPeriod,Date endPeriod){
        List resultList=new ArrayList();
        //将开始时间的时分秒都设为0
        Date resetBeginPeriod = LgTcCommon.resetBeginDate(beginPeriod);
        //将结束时间的设置为23:59:59 999
        Date resetEndPeriod = LgTcCommon.resetEndDate(endPeriod);
        String querySql="from TcAttendance tc where tc.userId=:userId"
                        +" and tc.date>=:beginPeriod"
                        +" and tc.date<=:endPeriod"
                        +" order by tc.date";
        try{
            Query q = this.getDbAccessor().getSession().createQuery(querySql);
            q.setString("userId",userId);
            q.setDate("beginPeriod",resetBeginPeriod);
            q.setDate("endPeriod",resetEndPeriod);
            List dbResult = q.list();
            Iterator it=dbResult.iterator();
            while(it.hasNext()){
                TcAttendance tcAttendance=(TcAttendance)it.next();
                DtoAttendance dto=new DtoAttendance();
                dto.setRid(tcAttendance.getRid());
                dto.setLoginId(tcAttendance.getUserId());
//                String typeName=getTypeName(tcAttendance.getType());
                dto.setAttendanceType(tcAttendance.getType());
//                dto.setTypeName(typeName);
                dto.setAttendanceDate(tcAttendance.getDate());
                dto.setRemark(tcAttendance.getRemark());
                resultList.add(dto);
            }
            return resultList;

        }  catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("Error", "list attendance record error!");
        }

    }
    public String getTypeName(String kind,String code){
        if(kind==null || code==null) return "";
        try{
            String querySql="select * from SYS_PARAMETER  sp where sp.KIND='"
                            +kind+"' and sp.CODE='"+code+"'";
            RowSet rset = getDbAccessor().executeQuery(querySql);
            if (rset.next()) {
                String typeName = rset.getString("name");
                return typeName;
            }
           return "";

        }catch(Exception ex){
            log.error(ex);
            throw new BusinessException("Error","Error when get attendance typeName!");
        }

    }
}
