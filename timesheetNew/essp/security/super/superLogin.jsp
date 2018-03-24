<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<%@page import="c2s.essp.common.user.*,server.essp.security.ui.auth.AcSuperLogin" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<%
   DtoUser currentUser = (DtoUser)session.getAttribute(DtoUser.SESSION_LOGIN_USER);
   String loginId = currentUser.getUserLoginId();
   if(!AcSuperLogin.SUPER_USER.contains(loginId)){
     response.sendRedirect("/essp/index.jsp");
   }
%>
<html>
<head>
<TITLE>ESSP</TITLE>
<script language="JavaScript" src="<%=contextPath%>/js/humanAllocate.js" type=""></script>
<script language="JavaScript" src="<%=contextPath%>/js/hashmap.js" type=""></script>
<script language="javascript" type="text/javascript">
function allocateHr(){
  var txt_loginId = document.getElementsByName("loginid")[0];
 var param = new HashMap();
 var result = allocSingleInALL(param,"","",txt_loginId.value,"true");
 txt_loginId.value = result;
 if(result != null && result != "" && result != "undefined") {
   mains.submit();
 }
}
</script>
</head>
<body leftmargin="0" topmargin="0">
<table align="center" cellpadding="0" width="100%" cellspacing="0">
  <tr align="center">
    <td height="225" colspan="4">
      <font style="font-family:Arial, Helvetica, sans-serif;FONT-SIZE: 25pt">
        Super Login
      </font>
    </td>
  </tr>
  <form name="mains" action="/essp/superLogin.do" method="POST">
    <%---�˴���¼��Ϊ�ڲ���Ա--%>
    <input type="hidden" name="userType" value="<%=DtoUser.USER_TYPE_EMPLOYEE%>">
    <tr align="center">
      <td width="302" class="list_desc" height="30">      </td>
      <td width="139" class="list_desc">
        <font size="3">Login ID</font>
      </td>
      <%--Username--%>
      <td width="190" class="list_desc">
        <input type="text" name="loginId" ondblclick="allocateHr()"/>
      </td>
      <td width="378" class="list_desc" height="19"><input type="submit" value="login" />      </td>
    </tr>
      <tr align="center">
        <td class="list_desc" height="30">    </td>
        <td height="20" class="list_desc">    </td>

        <td height="20" class="list_desc">    </td>
        <td width="378" class="list_desc">    </td>
      </tr>
  </form>

</table>
</body>
</html>
