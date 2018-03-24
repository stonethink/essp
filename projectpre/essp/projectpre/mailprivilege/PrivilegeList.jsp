<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <title>Site Privilege List</title>
    <tiles:insert page="/layout/head.jsp">
      <tiles:put name="title" value=" "/>
      <tiles:put name="jspName" value=""/>
    </tiles:insert>

     <STYLE type="text/css">
       input_field {width:60}
       #input_text {width:60}
     </STYLE>
  <script language="javascript" type="text/javascript">  	
      	function onSaveData(){    
       		var sites = document.forms[0].elements["type"];
       		var loginId = document.forms[0].elements["loginId"];
       		var data = "";
       		if(!sites.length){  
        		var obj = document.getElementById("type");      
        		if(obj.checked)
      			data=obj.value;
        		return;
      		}
       		for( i=0;i<sites.length;i++){       		      
	         	if(sites[i].checked)
	      		data+=","+sites[i].value;
     		}     	     	
          	var form=document.forms[0];
          	form.elements["dataScope"].value=data.substring(1,data.length);
          	form.action = "<%=contextPath%>/mailprivilege/savePrivilegeSite.do";
          	form.submit();
    	}
  
   </script>
  </head>
  <body>

    <html:form action="/mailprivilege/savePrivilegeSite.do" method="post"> 
    <html:hidden beanName="webVo" name="loginId" />
    <html:hidden beanName="webVo" name="loginName" />
    <html:hidden beanName="webVo" name="domain" />
    <html:hidden beanName="webVo" name="rid" />
    <html:hidden beanName="webVo" name="dataScope" />
    <table width="600" border="0">
    <tr>
    <td class="list_range"><bean:message bundle="projectpre" key="projectCode.MailPrivilege.newApplication"/></td>
    <td class="list_range"><html:checkbox styleId="CheckBox" name="addInform" checkedValue="1" beanName="webVo" uncheckedValue ="0" defaultValue="0"/><bean:message bundle="projectpre" key="projectCode.MailPrivilege.inform"/></td>
    <td class="list_range"><html:checkbox styleId="CheckBox" name="addAudit" checkedValue="1" beanName="webVo" uncheckedValue ="0" defaultValue="0"/><bean:message bundle="projectpre" key="projectCode.MailPrivilege.audit"/></td>
    </tr>
    <tr><td class="list_range"><bean:message bundle="projectpre" key="projectCode.MailPrivilege.changeApplication"/></td>
    <td class="list_range"><html:checkbox styleId="CheckBox" name="changeInform"  checkedValue="1" beanName="webVo" uncheckedValue ="0" defaultValue="0"/><bean:message bundle="projectpre" key="projectCode.MailPrivilege.inform"/></td>
    <td class="list_range"><html:checkbox styleId="CheckBox" name="changeAudit"  checkedValue="1" beanName="webVo" uncheckedValue ="0" defaultValue="0" /><bean:message bundle="projectpre" key="projectCode.MailPrivilege.audit"/></td>
    </tr>  
     <tr>
     <td class="list_range"><bean:message bundle="projectpre" key="projectCode.MailPrivilege.endApplication"/></td>
    <td class="list_range"><html:checkbox styleId="CheckBox" name="closeInform"  checkedValue="1" beanName="webVo" uncheckedValue ="0" defaultValue="0"/><bean:message bundle="projectpre" key="projectCode.MailPrivilege.inform"/></td>
    <td class="list_range"><html:checkbox styleId="CheckBox" name="closeAudit"  checkedValue="1" beanName="webVo" uncheckedValue ="0" defaultValue="0"/><bean:message bundle="projectpre" key="projectCode.MailPrivilege.audit"/></td>
    </tr>
    </table>  
    <TABLE width="820" border="0">
    <tr><td class="list_range"><bean:message bundle="projectpre" key="projectCode.MailPrivilege.dataScope"/></td>
    <logic:iterate id="item"
                  name="webVo"
                  property="dataScopeList"
                  scope="request"
                  indexId="a">
    <td class="list_range">  

     <input name="type" class="CheckBoxStyle" type="checkbox" value='<bean:write name="item" property="siteName"/>' <bean:write name="item" property="status"/> />
     <bean:write name="item" property="siteName"/>    
      
    </td> 
    </logic:iterate>
      </tr> 
    </table>
    </html:form>
   
  
  </body>

</html>
