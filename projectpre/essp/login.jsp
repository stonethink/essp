<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@ page import="server.essp.common.ldap.LDAPConfig,java.util.List,c2s.essp.common.user.DtoUserBase" %>
<%
    List domainList = LDAPConfig.getDomainIdList();
    String defaultDomain = LDAPConfig.getDefaultDomainId();
%>

<html>
	<head>

    <TITLE>ESSP</TITLE>
	<script type="text/javascript" language="JavaScript" src="/essp/js/AppMessage.js"></script>
	<script type="text/javascript" language="JavaScript" src="/essp/js/UIdesignDef.js"></script>
	<script type="text/javascript" language="JavaScript" src="/essp/js/global.js"></script>
	<script type="text/javascript" language="JavaScript" src="/essp/js/EventHandler.js"></script>
	<script type="text/javascript" language="JavaScript" src="/essp/js/FieldCheck.js"></script>
	<script type="text/javascript" language="JavaScript" src="/essp/js/Format.js"></script>
	<script type="text/javascript" language="JavaScript" src="/essp/js/UserUtils.js"></script>
        <wits-script:focusControl />
        <wits-script:messageDialog/>
        <wits-script:windowOpen/>
	<wits-script:confirm/>
<style type="text/css" >
  <!--
.list_desc
{
	text-align : left;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	font-style: normal;
	font-weight: bold;

}
.attention
{
	text-align : right;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	font-style: normal;
	font-color: yellow;
}
.button {
	BORDER-RIGHT: #104a7b 1px solid; BORDER-TOP: #afc4d5 1px solid; FONT-SIZE: 11px; BACKGROUND: #d6e7ef; BORDER-LEFT: #afc4d5 1px solid; CURSOR: hand; COLOR: #000066; BORDER-BOTTOM: #104a7b 1px solid; FONT-FAMILY: Arial, Helvetica, sans-serif; HEIGHT: 19px; font-weight : bold;TEXT-DECORATION: none
}

#loginID {
	width:150;
}

#passwd {
	width:150;
}
.style4 {
	font-size: 35px;
	font-family: Arial, Helvetica, sans-serif;
}
-->
</style>
<script>
  function check(){
     if ( mains.loginID.value == "" ) {
          alert("Login ID can't null.");
          mains.loginID.focus();
          return false ;
     }
     if ( mains.password.value == ""){
         alert("Password can't null.");
         mains.password.focus();
         return false ;
     }
     mains.submit();
  }

  function onPress(obj,obj2){
  	if((event.keyCode==13)){
  	   if ( obj == ""){
  	     alert("This value can't null");
  	   }else{
  	     obj2.focus();
  	   }
  	}
  }

  function doLogin() {
	if((event.keyCode==13)){
		if ( mains.password.value != ""){
			mains.B1.focus();
		}
		check();
	}
  }
  function onBodyLoad(){
    document.mains.loginId.focus();
  }
</script>
</head>
<body leftmargin="0" topmargin="0" onload="onBodyLoad()">


   	<table align="center" cellpadding="0" width="100%" cellspacing="0" >

    	<tr align="center">
          <td height="225" colspan="4" >
        	<font style="font-family:Arial, Helvetica, sans-serif;FONT-SIZE: 25pt"><b><font color="#174D70" >E</font>xcellent</b>
        	<b>
        	<font color="#174D70">S</font>oftware</b> <b> <FONT color="#174D70">S</font>ervice</b>
        	<b>
        	<FONT color="#174D70">P</font>latform</b></font>
            </td>
        </tr>

<html:form name="mains" action="login.do" >
        <%---此处登录均为内部人员--%>
        <input type="hidden" name="userType" value="<%=DtoUserBase.USER_TYPE_EMPLOYEE%>">
    	<tr align="center">
      		<td  width="302" class="list_desc" height="30"></td>
      		<td width="139"  class="list_desc"><font size="3">Login ID</font></td><%--Username--%>
      		<td width="190"  class="list_desc">
        		<html:text beanName="" name="loginId" fieldtype="text" next="passwd" styleId="loginID" value="wh0607016"/>
      		</td>
			<td  width="378" class="list_desc" height="19"></td>
    	</tr>

    	<tr align="center">
      		<td class="list_desc"  height="30"></td>
      		<td  height="20" class="list_desc" ><font size="3">Password</font></td><%--Password--%>
      		<td  height="20" class="list_desc" >
          <html:password name="password" next="domain" styleId="passwd"  onkeydown="doLogin();" value="tbh"/>                  </td>
		  <td  width="378" class="list_desc" height="30"><font size="3">

		  </td>
      </tr>

	   <tr align="center">
      		<td class="list_desc"  height="30"></td>
      		<td  height="20" class="list_desc" ><font size="3">Domain</font></td><%--Password--%>
      		<td  height="20" class="list_desc" >
                  <%---列出所有可选domain,默认选择default domain--%>
                  <select name="domain" >
                    <%
                    for(int i = 0;i < domainList.size() ;i ++){
                      String domain = (String)domainList.get(i);
                      String select = domain.equals(defaultDomain) ? "selected" : "";
                    %>
                    <option value="<%=domain%>" <%=select%>><%=domain%></option>
                    <%}%>
                  </select>
                </td>
                <td  width="378" class="list_desc" >
                  <input type="button" value='Login' name="B1" class="button" onclick="check();">
            <%---Login--%>
          </td>
      </tr>
</html:form>


	  <tr>
<table align="center" >
<tr><td colspan="4" height="70"></td></tr>
<tr>
      		<td  class="attention" ><font color="#8B0000" >Attention:</font> </td>
      		<td  colspan="3" >1.To use ESSP system normally,please install <a href="/jre-1_5_0_09-windows-i586-p.exe">jre1.5.0</a>,and download <a href="/jre-1_5_0_09-windows-i586-p.exe">here</a>. </td>
<tr>
<tr>
                <td  >&nbsp; </td>
      		<td  colspan="3" >2.Please use the LoginID and Password in AD to login.</td>
</tr>
 
</table>
     	</tr>

  </table>


</body>
</html>
