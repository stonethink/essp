<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Resource Plan"/>
        <tiles:put name="jspName" value="Head"/>
</tiles:insert>
<script type="text/javascript" language="JavaScript">
  var a;
  var b;
  var c;
  a=null;
  b=null;
  c=null;
 function sendid(tempid,irow,trid){
 	form_tp.del_temp.value=tempid;
 	b=a;
	a=trid;
	a.style.backgroundColor="#ccccff";
        if(b!=null&&b!=a && c!=null) {
		if (c == 0){b.style.backgroundColor="#F0F0F0";}
                if (c == 1){b.style.backgroundColor="#fffffd";}
	}
        c=irow%2;
 }
 function del_f(){
 	var del_temp = form_tp.del_temp.value;
 	if(del_temp == null || del_temp == ""){
 		alert("Please Select a record!");
 	}else if(confirm("Are you sure you want to delete the data?")){
                form_tp.submit();
        }
 }
</script>
</head>

<body leftmargin="0" topmargin="0">
<form name="form_tp" method="post">
<input type="hidden" name="del_temp">
</form>
<table width="150" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td bgcolor="#cccccc">
      <table width="100%" border="0" cellspacing="1" cellpadding="0">
        <logic:iterate id="element" name="webVo" indexId="index" >
          <tr class="<%=index.intValue()%2==0?"oracletdtwo":"oracletdone"%>"
              id="tr<%=index.intValue()%>"

              >
            <td width="4%" height="20" class="oracelltext"><div align="center"></div></td>
            <td width="30%"  class="oracelltext">
              <div align="left">
                <%-- a href="javascript:parent.frm22.gotoPage('<bean:write name="element" property="rid"/>-<bean:write name="element" property="loginId"/>-<bean:write name="element" property="empName"/>')"><bean:write name="element" property="empName"/></a --%>
                <bean:write name="element" property="empName"/>
              </div>
            </td>
          </tr>
        </logic:iterate>
        <%--
        <tr   onclick="sendid('aaa',aaaa,aaa)">
          <td width="4%" height="20" class="oracelltext"><div align="center"><font face="Wingdings">v</font></div></td>
          <td width="30%"  class="oracelltext"><div align="left"><a href="#">Hua.Xiao</a></div></td>
        </tr>
        --%>
      </table></td>
    </tr>
   <tr><td background="../photo/alloc_tool/alloc_tool_back3.jpg">&nbsp;</td></tr>
</table>
</body>
</html>
