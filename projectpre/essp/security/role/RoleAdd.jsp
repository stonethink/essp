<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <title><bean:message bundle="application"  key="RoleDefine.RoleAdd.title"/></title>
     <tiles:insert page="/layout/head.jsp">
		<tiles:put name="title" value=" " />
		<tiles:put name="jspName" value="" />
	 </tiles:insert>


  <STYLE type="text/css">
    input_field {width:60}
    #input_text {width:100%}
    #description {width:100%}
  </STYLE>

    <script language="JavaScript" type="text/JavaScript">

    //此方法用于检查必填项
     function checkReq(form){
	     if(!submitForm(form)){
    	 	return false;
	     }

     }

     function onClickCancel(){
       this.close();
     }
</script>
  </head>

  <body>
  <center>
   <html:form id="Role" method="post" action="/security/addRole" onsubmit="return checkReq(this)" initialForcus="roleId">
    <table width="100%" border="0" align="center">
  <tr>
    <td class="list_range" width="120">&nbsp;</td>
    <td class="list_range" width="60">&nbsp;</td>
    <td class="list_range" width="20">&nbsp;</td>
    <td class="list_range" width="120">&nbsp;</td>
    <td class="list_range" width="60">&nbsp;</td>
  </tr>

  <tr>
    <td class="list_range"><bean:message bundle="application"  key="RoleDefine.Role.roleId"/></td>
    <td class="list_range"><html:text styleId="input_field" fieldtype="text" name="roleId" prev="cancel" next="roldId" beanName="Role" maxlength="25" req="true"/></td>
    <td class="list_range">&nbsp;</td>
    <td class="list_range"><bean:message bundle="application"  key="RoleDefine.Role.roleName"/></td>
    <td class="list_range"><html:text styleId="input_field" fieldtype="text" prev="roleName" next="siteManager" name="roleName" beanName="Role" maxlength="25" req="true"/></td>
  </tr>
  <tr>
    <td class="list_range"><bean:message bundle="application"  key="RoleDefine.Role.parentId"/></td>
    <td>
        <html:select name="parentId" styleId="input_text" beanName="webVo">
         <html:optionsCollection name="webVo" property="parentIdList" styleClass=""/>
        </html:select>
    </td>
    <td class="list_range">&nbsp;</td>
    <td class="list_range"><bean:message bundle="application"  key="RoleDefine.Role.sequence"/></td>
    <td class="list_range"><html:text styleId="input_field" fieldtype="text" name="seq" beanName="webVo"/></td>
  </tr>
  <tr>
    <td class="list_range"><bean:message bundle="application"  key="RoleDefine.Role.description"/></td>
    <td class="list_range" colspan="4"><html:textarea name="description" beanName="webVo" styleId="description"></html:textarea></td>
  </tr>
  <tr>
    <td class="list_range">&nbsp;</td>
    <td class="list_range">&nbsp;</td>
    <td class="list_range">&nbsp;</td>
    <td class="list_range">&nbsp;</td>
    <td class="list_range">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="5" class="list_range" style="text-align:right">
    <input class="button" name="submit" type="submit" value="<bean:message bundle="application"  key="global.button.submit"/>" />&nbsp;&nbsp;&nbsp;&nbsp;
    <input class="button" type="button" name="cancel"  value="<bean:message bundle="application"  key="global.button.cancel"/>" onClick="onClickCancel()"/></td>
  </tr>
</table>

   </html:form>
  </center>
  </body>
</html>
