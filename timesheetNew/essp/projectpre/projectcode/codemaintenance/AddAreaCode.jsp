
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <title><bean:message bundle="projectpre" key="projectCode.ProjectCodeMaintenance.cardTitle.AreaCodeSetting"/></title>
     <tiles:insert page="/layout/head.jsp">
		<tiles:put name="title" value=" " />
		<tiles:put name="jspName" value="" />
	 </tiles:insert>
    
     
    <STYLE type="text/css">
    #input_field {width:210px}
    .list_range{
	text-align : left;
	font-family: "DFKai-SB",Arial, Helvetica, sans-serif;
	font-size: 14px;
	font-style: normal;
	font-weight: bold;
	}
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
   <html:form id="AreaCode" method="post" action="/project/maintenance/addAreaCode" onsubmit="return checkReq(this)" initialForcus="siteName">
    <table width="350" border="0" align="center">
	 <tr>
    <td class="list_range" width="160px">&nbsp;</td>
    <td class="list_range" width="150px">&nbsp;</td>
  </tr>
 <tr>
    <td class="list_range"><bean:message bundle="projectpre" key="projectCode.AreaCodeList.ProjectCodeTrailCode"/></td>
    <td class="list_range"><html:text styleId="input_field" fieldtype="text" prev="siteName" next="siteManager" name="siteCode" beanName="AreaCode" maxlength="25" req="true"/></td>
  </tr>
  <tr>
    <td class="list_range"><bean:message bundle="projectpre" key="projectCode.AreaCodeList.ProjectExecuteCity"/></td>
    <td class="list_range"><html:text styleId="input_field" fieldtype="text" name="siteName" prev="cancel" next="siteCode" beanName="AreaCode" maxlength="25" req="true"/></td>
  </tr>
  <tr>
    <td class="list_range"><bean:message bundle="projectpre" key="projectCode.AreaCodeList.SiteLoading"/></td>
    <td class="list_range"><html:text styleId="input_field" fieldtype="text" name="siteLoading" prev="cancel" next="siteCode" beanName="AreaCode" maxlength="25" req="true"/></td>
  </tr>
  <tr>
    <td class="list_range"><bean:message bundle="projectpre" key="projectCode.Desc"/></td>
    <td class="list_range" colspan="2">
    <html:textarea name="description" beanName="webVo" rows="4" styleId="input_field" req="false" maxlength="250"  />
    </td>
  </tr>
  
  <tr>
    <td class="list_range">&nbsp;</td>
    <td class="list_range">&nbsp;</td>
  </tr>

  <tr>
    <td colspan="2" class="list_range" style="text-align:center" >
    <input class="button" name="submit" type="submit" value="<bean:message key="global.button.submit" bundle="application"/>" />&nbsp;&nbsp;&nbsp;&nbsp; 
    <input class="button" type="button" name="cancel"  value="<bean:message key="global.button.cancel" bundle="application"/>" onClick="onClickCancel()"/></td>
  </tr>
</table>

   </html:form>
  </center>
  </body>
</html>
