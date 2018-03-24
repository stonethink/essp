<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <title>Mail Privilege Infomation</title>
    <tiles:insert page="/layout/head.jsp">
      <tiles:put name="title" value=" "/>
      <tiles:put name="jspName" value=""/>
    </tiles:insert>

     <STYLE type="text/css">
       input_field {width:60}
       #input_text {width:20}

     </STYLE>
   <script language="javascript" type="text/javascript">
      function onSaveData(){
          var form=document.forms[0];
          var dataScope = getDataScope("type");
	      document.all.dataScope.value = dataScope;
          form.submit();
      }
    
      function getDataScope(type){
          var flag = "false";
          var types = "";
          var form=document.forms[0];
          var dataScope = form.elements[type];
	      if(dataScope==null){
	        return type;
	       }
	      if(!dataScope.length){
	        var obj = document.getElementById(type);
	        if(obj.checked){
	          types = obj.value;
	        }
	        return types;
	      }
	     
	      for( i=0;i<dataScope.length;i++){
	      	if(dataScope[i].checked != null && dataScope[i].checked == true){
	      	     if(flag=="false"){
	      	      types = dataScope[i].value;
	      	     }else{
	      	      types = types+","+dataScope[i].value;
	      	     }
	      	     flag = "true";
	      	}
	     }
	     return types;
     }
     
     </script>

  </head>
  <body>

 <html:form id="MailPrivilege" action="/project/mail/updateMailPrivilege" method="post" >
  <html:hidden beanName="webVo" name="loginId" />
 <html:hidden beanName="webVo" name="loginName" />
 <html:hidden beanName="webVo" name="domain" />
 <html:hidden beanName="webVo" name="rid" />
 <html:hidden beanName="webVo" name="dataScope" />
 <table width="600" border="0" style="padding-left:8px" >
 <tr>
 <td class="list_range" width="100">Add Apply</td>
 <td class="list_range" width="60">Inform</td>                              
 <td class="list_range" width="20"><html:checkbox beanName="webVo" checkedValue="1" defaultValue="0" uncheckedValue="0" name="addInform"/></td>
 <td class="list_range" width="60">Audit</td>
 <td class="list_range" width="20"><html:checkbox name="addAudit" beanName="webVo" checkedValue="1" defaultValue="0" uncheckedValue="0"/></td>
 <td></td>
 </tr>
 <tr>
 <td class="list_range" width="100">Change Apply</td>
 <td class="list_range" width="60">Inform</td>
 <td class="list_range" width="20"><html:checkbox name="changeInform" beanName="webVo" checkedValue="1" defaultValue="0" uncheckedValue="0"/></td>
 <td class="list_range" width="60">Audit</td>
 <td class="list_range" width="20"><html:checkbox name="changeAudit" beanName="webVo" checkedValue="1" defaultValue="0" uncheckedValue="0"/></td>
 <td></td>
 </tr>
 <tr>
 <td class="list_range" width="100">Close Apply</td>
 <td class="list_range" width="60">Inform</td>
 <td class="list_range" width="20"><html:checkbox beanName="webVo" checkedValue="1" defaultValue="0" name="closeInform" uncheckedValue="0"/></td>
 <td class="list_range" width="60">Audit</td>
 <td class="list_range" width="20"><html:checkbox beanName="webVo" checkedValue="1" defaultValue="0" name="closeAudit" uncheckedValue="0"/></td>
 <td></td>
 </tr>
 <tr height="20"><td></td></tr>
  </table>
  <table width="600" border="0" >
   <tr>
   <td class="list_range" width="100">Data Scope</td>
  <logic:iterate id="item"
                  name="webVo"
                  property="dataScopeList"
                  scope="request"
                  indexId="a" 
   >
   	   <td class="list_range" ><bean:write name="item" property="name"/></td>
   	   <td><input name="type" class="CheckBoxStyle" type="checkbox" value='<bean:write name="item" property="code"/>' <bean:write name="item" property="status"/> />
   	   </td>
   	</logic:iterate>
  </tr>
   </table>
  </body>
</html:form>
