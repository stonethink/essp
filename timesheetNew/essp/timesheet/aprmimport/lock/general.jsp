<%@page contentType="text/html; charset=utf-8"%>
<%@include file="/inc/pagedef.jsp"%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="Import Lock General"/>
  <tiles:put name="jspName" value="."/>
</tiles:insert>
<style type="text/css">
  <!--
    #input_date{
    width=100%;
    }
  -->
  #select_date{
    width=200px;
    }
</style>
<script language="javascript">
function getMyDATE(dateName){
     try{
    	var date = document.getElementById(dateName);
    	date.focus();
    	getDATE(date);
    	} catch(e){}
     }

function onSave(){
    if (afImportLock.beginDate.value==""){
        alert("Please Fill in begin date!");
        return;
    }

    if (afImportLock.endDate.value==""){
        alert("Please Fill in finish date!");
        return;
    }
  afImportLock.submit();
  parent.onTimeOutSearch();
}

</script>
</head>
<body >
    <html:tabpanel id="condition" width="98%">
        <%-- card title--%>
        <html:tabtitles>
            <html:tabtitle selected="true" width="80">
                <center><a class="tabs_title"><bean:message bundle="timesheet" key="importLock.common.general"/></a></center>
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
              <html:form id="afImportLock" action="/timesheet/aprmimport/lock/SaveImportLock" method="POST">
                <table width="600" border="0" cellspacing="0" cellpadding="0" align="center">
                <tr style="height:15px"><td></td><td></td></tr>
                  <tr>
                  	<html:hidden name="rid" beanName="webVo"/>
                    <td height="30" class="list_range"><bean:message bundle="timesheet" key="importLock.common.beginDate"/></td>
                    <td width="180">
                    <html:text name="beginDate"
                      		beanName="webVo"
                      		fieldtype="dateyyyymmdd"
                      		styleId="input_date"
                      		imageSrc="<%=request.getContextPath()+"/image/cal.png"%>"
                      		imageWidth="18"
                      		imageOnclick="getMyDATE('beginDate')"
                      		maxlength="10" 
                      		ondblclick="getDATE(this)"
                            />
                    </td>
                    <td width="40" class="list_range">&nbsp;</td>
                    <td class="list_range"><bean:message bundle="timesheet" key="importLock.common.endDate"/></td>
                    <td width="180" class="list_range">
                    <html:text name="endDate"
                      		beanName="webVo"
                      		fieldtype="dateyyyymmdd"
                      		styleId="input_date"
                      		imageSrc="<%=request.getContextPath()+"/image/cal.png"%>"
                      		imageWidth="18"
                      		imageOnclick="getMyDATE('endDate')"
                      		maxlength="10" 
                      		ondblclick="getDATE(this)"
                            />
                    </td>
                    <td width="40" class="list_range">&nbsp;</td>
                    <td class="list_range"><bean:message bundle="timesheet" key="importLock.common.lock"/></td>
                    <td>
                      <html:checkbox name="status" beanName="webVo" checkedValue="true" defaultValue="true" uncheckedValue="false"/>
                    </td>
                  </tr>
                  <tr>
                    <td height="5" class="list_range">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td class="list_range">&nbsp;</td>
                    <td class="list_range">&nbsp;</td>
                    <td class="list_range">&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
                </table>
              </html:form>
            </html:outborder>
          </html:tabcontent>
        </html:tabcontents>
    </html:tabpanel>
</body>
</html>
