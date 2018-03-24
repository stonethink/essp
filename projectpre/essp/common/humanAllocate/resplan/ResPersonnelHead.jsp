<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Resource Plan"/>
        <tiles:put name="jspName" value="Head"/>
</tiles:insert>
</head>
<%//\u5B9A\u4E49\u5F53\u524D\u65E5\u671F
java.util.GregorianCalendar today = new java.util.GregorianCalendar();
int iNowyear = today.get(java.util.Calendar.YEAR);

int iViewYear = 0;
String sYear = request.getParameter("year");
System.out.println("sYear:"+sYear);
if((sYear==null)||(sYear.equals("null"))) {
    iViewYear = iNowyear;
} else {
    iViewYear = Integer.parseInt(sYear);
}
String sPeriod = request.getParameter("hid_period_pv");//���ܵ�ǰPeriod��ֵ
if(sPeriod==null) {
    sPeriod = "title";
}
String sViewBy = request.getParameter("hid_view_pv");//���ܵ�ǰviewby��ֵ
if(sViewBy==null) {
    sViewBy = "0";
}
String sIsYearview = request.getParameter("hid_yearview_pv");//���ܵ�ǰsYearview��ֵ
if(sIsYearview==null) {
    sIsYearview = "01";
}

%>
<body topmargin="0" leftmargin="0"  >
<form method="post" name="headform">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
           <td width="64" background="../photo/alloc_tool/alloc_tool_back2.jpg"><img src="../photo/alloc_tool/alloc_tool_logo2.jpg" width="64" height="34"></td>
           <td width="100"class="list_desc" background="../photo/alloc_tool/alloc_tool_back2.jpg">Resource View</td>
            <td nowrap class="orarowheader"  bgcolor="#000000" > <div align="center">

       Period:<input type="radio" name="viewbase" value="0" onClick="parent.personnel_view.frm22.changeview(0) ">two years
              <input type="radio" name="viewbase" value="1" onClick="parent.personnel_view.frm22.changeview(1)"checked>whole life cycle
 </div></td>
<td nowrap class="orarowheader"  bgcolor="#000000" > <div align="right">
                  <!--
                <input type="button" class="button" value="Workload" onClick="parent.personnel_view.frm22.workload()">
                <input type="button" class="button" value="Labor Cost" onClick="parent.personnel_view.frm22.costview()">
                  -->
                <input type="button" name="close"class="button" value="Close" onClick="top.window.close()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input name="hid_period_pv" type="hidden" value="<%=sPeriod%>">

                <input name="hid_view_pv" type="hidden"  value="<%=sViewBy%>">
                <input type="hidden" name="year" value="<%=iViewYear%>">

              </div></td>
          </tr>
        </table>
</form>
<script language="JavaScript">
function sChangeView() {//change period的时候change viewbase显示的值
  if(( document.headform.period.value=="title")||( document.headform.period.value.value=="0112")) {
    document.headform.viewbase.value="0";
  } else {
    document.headform.viewbase.value="1";
  }
}
</script>
</body>
</html>
