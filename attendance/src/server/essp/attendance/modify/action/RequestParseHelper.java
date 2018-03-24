package server.essp.attendance.modify.action;

import db.essp.attendance.TcLeaveMain;
import java.util.Iterator;
import db.essp.attendance.TcLeaveDetail;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

public class RequestParseHelper {
    private TcLeaveMain leaveMain;
    private HttpServletRequest request;
    public RequestParseHelper(TcLeaveMain leaveMain,HttpServletRequest request){
        this.leaveMain = leaveMain;
        this.request = request;
    }
    //��Request�����������ݽ���,����Map��,Key--�����ϸ��Rid,Value--�����ϸ��ChangeHours��Remark
    public Map getRequestDate(){
        Map result = new HashMap();
        Set detailSet = leaveMain.getTcLeaveDetails();
        if(detailSet != null && detailSet.size() != 0)
            for(Iterator it = detailSet.iterator() ;it.hasNext();){
                TcLeaveDetail detail = (TcLeaveDetail) it.next();
                Long rid = detail.getRid();
                TcLeaveDetail requestData = new TcLeaveDetail();
                Double changeHours = getChangeHours(rid);
                String remark = getRemark(rid);
                requestData.setChangeHours(changeHours);
                requestData.setRemark(remark);
                result.put(rid,requestData);
            }
        return result;
    }
    //���LeaveDetail��Ӧ��changeHours(��Request�еĲ�����:rid-changeHours)
    private Double getChangeHours(Long rid){
        String param = rid + "-changeHours" ;
        String changeHours = request.getParameter(param);
        if(changeHours != null)
            return new Double(changeHours);
        return new Double(0);
    }
    //���LeaveDetail��Ӧ��Remark(��Request�еĲ�����:rid-remark)
    private String getRemark(Long rid){
        String param = rid + "-remark" ;
        return request.getParameter(param);
    }
}
