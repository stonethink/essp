<%@page contentType="text/html; charset=utf-8"%>
<%@include file="/inc/pagedef.jsp"%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="Site General"/>
  <tiles:put name="jspName" value="site"/>
</tiles:insert>
<STYLE type="text/css">
       #description {width:100%}
</STYLE>
<script language="javascript">

function onSave(){
    if (afSite.name.value==""){
        alert('<bean:message bundle="application" key="global.confirm.require"/>');
		afSite.name.focus();
        return;
    }
  afSite.submit();
  parent.onTimeoutSearch();
}

</script>
</head>
<body >
    <html:tabpanel id="condition" width="98%">
        <%-- card title--%>
        <html:tabtitles>
            <html:tabtitle selected="true" width="80">
                <center><a class="tabs_title"><bean:message bundle="hrbase" key="hrbase.site.general"/></a></center>
            </html:tabtitle>
        </html:tabtitles>
        <%-- card buttons--%>
  <html:tabbuttons>
    <html:form action=".">
      <input type="button" name="Save" value="<bean:message bundle="application" key="global.button.save"/>"  onclick="onSave();" class="button" />
    </html:form>
  </html:tabbuttons>
        <%-- card --%>
        <html:tabcontents>
          <html:tabcontent styleClass="wind">
            <html:outborder height="8%" width="98%">
              <html:form id="afSite" action="/hrbase/site/saveSite" method="POST">
                <table width="80%" border="0" cellspacing="0" cellpadding="0" align="center">
                	<tr height="8">
                    	<td></td>
                    	<td></td>
                   </tr>
                  <tr>
	                  <html:hidden name="rid" beanName="webVo"/>
			          <td height="30" class="list_range"><bean:message bundle="hrbase" key="hrbase.site.name"/></td>
	                  <TD>
	                  	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
		                  	<tr>
			                    <td width="30%">
			                      <html:text name="name" beanName="webVo" fieldtype="text" styleId="" prev="" next="description"/>
			                    </td>
			                    <td width="40" class="list_range">&nbsp;</td>
			                    <td class="list_range" width="40"><bean:message bundle="hrbase" key="hrbase.site.enable"/></td>
			                    <td width="30%" class="list_range">
			                      <html:checkbox name="isEnable" beanName="webVo" styleId="isEnable" checkedValue="1" defaultValue="1" uncheckedValue="0"/>
			                    </td>
			                    <td width="40" class="list_range">&nbsp;</td>
		                    </tr>
	                    </table>
	                   </TD>
                    </tr>
                    <tr height="8">
                    	<td></td>
                    	<td></td>
                    </tr>
                    <tr>
	                    <td class="list_range" width="80"><bean:message bundle="hrbase" key="hrbase.site.description"/></td>
	                    <TD>
	                  	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
		                  	<tr>
			                    <td class="list_range" colspan="4">
			                    <html:textarea name="description" beanName="webVo" styleId="description" prev="name" next="enable"/>
	                    		</td>
	                  		</tr>
	                  	</table>
	                  	</td>
                  	</tr>
                </table>
              </html:form>
            </html:outborder>
          </html:tabcontent>
        </html:tabcontents>
    </html:tabpanel>
</body>
</html>
