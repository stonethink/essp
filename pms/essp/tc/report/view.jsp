<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="server.essp.tc.attreport.AfAttendanceReport"%>
<%@page import="server.essp.tc.hrmanage.logic.AttendanceExporter"%>
<%@page import="com.wits.util.Parameter"%>
<%@page import="c2s.essp.tc.weeklyreport.DtoTcKey"%>
<%@page import="com.wits.util.comDate"%>
<%@page import="c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByHr"%>

<%@page import="java.util.Date"%>
<%
	 AfAttendanceReport form = (AfAttendanceReport)session.getAttribute("AttendanceReportForm");
 	AttendanceExporter exporter = new AttendanceExporter();
 	Parameter param = new Parameter();
        Date begin = comDate.toDate(form.getReportBegin());
        Date end = comDate.toDate(form.getReportEnd());
        param.put(DtoTcKey.ACNT_RID,new Long(form.getAcntRid()));
        param.put(DtoTcKey.BEGIN_PERIOD,begin);
        param.put(DtoTcKey.END_PERIOD,end);
        //���ñ���Ϊ���µ�
        param.put(DtoTcKey.PERIOD_TYPE,DtoWeeklyReportSumByHr.ON_MONTH);
        exporter.webExport(response, response.getOutputStream(), param);
        
        
%>


<script language="javascript">
try{
    window.close();
}catch(e){
}
</script>
