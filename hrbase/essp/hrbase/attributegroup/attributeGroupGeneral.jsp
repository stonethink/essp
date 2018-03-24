<%@page contentType="text/html; charset=utf-8"%>
<%@include file="/inc/pagedef.jsp"%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="HRB Attribute General"/>
  <tiles:put name="jspName" value="site"/>
</tiles:insert>
<style type="text/css">
      #input_common1{width:260}
      #input_common2{width:120}
      #input_common3{width:637}
      #input_common4{width:150}
      #rewt{width:637}
      #input_field{width:60}
      #input_common5{width:100%}
    </style>
<script language="javascript">
	function onSave(){
	  var form=document.forms[1];
	  if(!checkReq(form)){
	      return;
	   }
	  form.submit();
	  parent.onTimeoutSearch();
	}

//此方法用于检查必填项
     function checkReq(form){
	     if(!submitForm(form)){
    	 	return false;
	     } else {
	     	return true;
	     }
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
	     <html:form id="attributeGroup" action="/hrbase/attgroup/saveAttGroup" method="POST" onsubmit="return checkReq(this)">
	       <table width="80%" border="0" cellspacing="0" cellpadding="0" align="center">
	       <html:hidden name="rid" beanName="webVo"></html:hidden>
	       <tr><td height="5"></td></tr>
	        <tr>
	        <td class="list_range"><bean:message bundle="hrbase" key="hrbase.site.name"/></td>
	        <td class="list_range"><html:text name="code" beanName="webVo" styleId="input_common4" fieldtype="text" req="true" /></td>
	        <td class="list_range" width="50"></td>
	        <td class="list_range" width="100"><bean:message bundle="hrbase" key="hrbase.attcomp.formalEmp"/></td>
	        <td class="list_range" width="60"><html:checkbox name="isFormal" beanName="webVo" styleId="isFormal" checkedValue="1" defaultValue="1" uncheckedValue="0"/></td>
	 
	        <td class="list_range" width="80"><bean:message bundle="hrbase" key="hrbase.site.enable"/></td>
	        <td class="list_range" width="60"><html:checkbox name="isEnable" beanName="webVo" styleId="isEnable" checkedValue="1" defaultValue="1" uncheckedValue="0"/> </td>
	      </tr>
	     <tr><td height="5"></td></tr>
	    <tr>
	     <td class="list_range" width="80"><bean:message bundle="hrbase" key="hrbase.dept.belongsite"/></td>
	     <td class="list_range" width="150">
	     <html:select name="site" styleId="input_common4" beanName="webVo"  req="true" >          
	         <html:optionsCollection name="webVo"  property="siteList"/>
	     </html:select>
	     </td>
	     <td class="list_range" width="50"></td>
	     <td class="list_range" width="100"><bean:message bundle="hrbase" key="hrbase.attrbute.hrAttribute"/></td>
	     <td class="list_range" width="150">
	     <html:select name="attributeRid" styleId="input_common4" beanName="webVo"  req="true" >          
	      <html:optionsCollection name="webVo" property="hrbAttList"/>
	     </html:select>
	    </td>
	    <td class="list_range">&nbsp;</td>
	    <td class="list_range">&nbsp;</td>
	   </tr>
	    <tr><td height="5"></td></tr>
	    <tr>
	     <td class="list_range" width="80"><bean:message bundle="hrbase" key="hrbase.site.description"/></td>
	     <td class="list_range" colspan="6">
		  <html:textarea name="description" beanName="webVo" styleId="input_common5" />
		 </td>
	    <td class="list_range">&nbsp;</td>
	    </tr>
	  </table>
	</html:form>
   </html:outborder>
  </html:tabcontent>
 </html:tabcontents>
</html:tabpanel>
</body>

</html>
