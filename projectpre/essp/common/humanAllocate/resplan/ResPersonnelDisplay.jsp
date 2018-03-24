<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:parameter id="accountId" name="accountId"/>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Resource Plan"/>
        <tiles:put name="jspName" value="Head"/>
</tiles:insert>
</head>
<body leftmargin="0" topmargin="0" onscroll="scrollit()">
<form method="post" name="frm_pv">
<table width="<bean:write name="webVo" property="tabWidth"/>" border="0" cellspacing="0" cellpadding="0">
<tr >
  <td width="<bean:write name="webVo" property="tabWidth"/>" bgcolor="#cccccc">
    <table width="100%" border="0" cellspacing="1" cellpadding="0">
      <%-- 遍历显示每个人的计划 --%>
      <logic:iterate id="element" name="webVo" property="resourcePlan" indexId="index">
       <tr width="<bean:write name="webVo" property="tabWidth"/>"
           class="<%=index.intValue()%2==0?"oracletdtwo":"oracletdone"%>"
           id="tr<%=index.intValue()%>"
           onClick="changeStyle(tr<%=index.intValue()%>,'<bean:write name="element" property="rid"/>',<%=index.intValue()%>,'<bean:write name="element" property="empName"/>',0);">
           <%-- 每周的方格 --%>
           <logic:iterate id="weekPlan" name="element" property="weekPlan">
            <td width="20" height="20" <bean:write name="weekPlan" property="bgColor"/> >
             <logic:notEqual value="0" name="weekPlan" property="usagePercent">
               <table width="<bean:write name="weekPlan" property="usagePercent"/>%" border="0" cellspacing="0" cellpadding="0" class="inn"><tr><td>&nbsp;</td></tr></table>
             </logic:notEqual>
            </td>
           </logic:iterate>
      </tr>
      </logic:iterate>
      <input type="hidden" name="emp_text">
      <input name="proj_text" type="hidden" value = "">
      <input type="hidden" name="year" value="">
      <input type="hidden" name="alloc_code" value=>
      <input name="hid_view_pv" type="hidden"  value="">
    </table>
  </td>
</tr>
</table>
</form>
<script language="JavaScript">
var firstclick = true;
var last;
var lastid;
//切换视图（按生命周期显示还是按两年显示）
function changeview(selectid) {
        //var viewbaseselect=parent.parent.personnel_head.headform.viewbase;
	//    document.frm_pv.hid_view_pv.value =  selectid;
	//    document.frm_pv.submit();
        parent.frm12.location="PlanPeriodPart.do?viewbase="+selectid+"&accountId=<%=accountId%>";
        parent.frm22.location="ListResourcePlan.do?viewbase="+selectid+"&accountId=<%=accountId%>";
}
//点击人员名称时弹出窗口显示该员工的工作日程（日历）
function gotoPage(pre_emptext) {
window.open('calerdar_main.jsp?emp_text='+pre_emptext+'&alloc_code=','','status=yes,width=810,height=350,top=200,left=120');

}
function create_p(){
window.open('res_account_personnel_create.jsp?alloc_code=','','status=yes,width=400,height=200,top=200,left=350');
}
function proj_manage() {
    document.frm_pv.submit();
    parent.location="res_account_project_manage.jsp?alloc_code=&year=";
}
//滚动条滚动时同时滚动头部或左半部分窗体
function scrollit(){
    parent.frm12.scrollTo(document.body.scrollLeft,0);
	parent.frm21.scrollTo(0,document.body.scrollTop);
}
//选中某一人员信息时该行反显并刷新下面窗口的详细信息
function changeStyle(prm_tr,res_rid,id,emp_name,temp) {
    if(firstclick) {
        prm_tr.className = "line3";
		last = prm_tr;
                lastid=id;
		firstclick = false;

	} else{
	    if (lastid%2==0) {
		    last.className = "oracletdtwo";
		} else {
		    last.className = "oracletdone";
		}
		prm_tr.className = "line3";
		last = prm_tr;
                lastid=id;
	}
var obj=parent.parent.personnel_foot;
//alert("emp_id:"+emp_id);
obj.a1.innerHTML="NAME:"+emp_name;
obj.main_view.location= "PersonnelPlanDetail.do?resRid="+res_rid;

}
</script>
</body>
</html>
