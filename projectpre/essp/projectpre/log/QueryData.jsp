<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp" %>
<%@ include file="/layout/dynamicLoginId.jsp"%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <tiles:insert page="/layout/head.jsp">
       <tiles:put name="title" value=" "/>
       <tiles:put name="jspName" value=""/>
     </tiles:insert>
      <style type="text/css">
      #input_common1{width:120}
      #input_common2{width:650}
      #input_common3{width:160}
      #input_common4{width:273}
      #input_field{width:60}
      #description{width:600}
    </style>
    <script type="text/JavaScript" src="<%=contextPath%>/js/humanAllocate.js"></script>
     <script type="text/javascript" language="javascript">  
     function getQueryUserElementArray() {
		var names = new Array(document.getElementsByName("operator")[0],document.getElementsByName("mailSender")[0]);
		var loginId = new Array(document.forms[0].OperatorId,document.forms[0].mailSenderId);
		return new Array(names,loginId);
	}

    function allocateHr() {
     try{  
      var param = new HashMap();
      var result = allocSingleInAD(param,"","Allocate single user in AD","");
      if(result!=""){  
      document.getElementsByName("operator")[0].value = param.values()[0].name;
      document.forms[0].OperatorId.value = param.values()[0].loginId;
      document.forms[0].DMDomain.value=param.values()[0].domain;
      }
      }catch(e){}
     
    }
       function allocateMailSender() {
     try{  
      var param = new HashMap();
      var result = allocSingleInAD(param,"","Allocate single user in AD","");
      if(result!=""){  
      document.getElementsByName("mailSender")[0].value = param.values()[0].name;
      document.forms[0].mailSenderId.value = param.values()[0].loginId;
      document.forms[0].DMDomain.value=param.values()[0].domain;
      }
      }catch(e){}
     
    }
     
     function onSearch(){      
        var startDate = document.all.startDate.value;
      var endDate = document.all.endDate.value;
      //alert(beginDate+"~"+endDate);
      if(startDate!=""&&endDate!=""&&startDate>=endDate){
         alert("<bean:message bundle="application" key="global.fill.dateError"/>");
         return;
         }
       AfLog.submit();
     }
      function getMyDATE(dateName){
     try{
    	var date = document.getElementById(dateName);
    	date.focus();
    	getDATE(date);
    	} catch(e){}
     }

     </script>
  </head>
  
  <body>
 
 <html:form id="AfLog" action="/log/resultList" method="post" target="resultList" >
<table width="800" border="0" style="padding-left:8px">
<html:hidden name="OperatorId" beanName="webVo"/>
<html:hidden name="mailSenderId" beanName="webVo"></html:hidden>
  <tr>
    <td class="list_range" width="120"><bean:message bundle="projectpre" key="log.acntId"/>
    </td>
    <td class="list_range">
    <html:text styleId="input_common1" name="acntId"  beanName="webVo"  fieldtype="text" readonly="false"  maxlength="20"/>
	</td>

	 <td class="list_range">
	 <bean:message bundle="projectpre" key="log.applicationType"/>
	 </td>
	  <td>
	  <SELECT name="applicationType" class="Selectbox  Nreq" id="input_common1" onblur="doBlur();" onfocus="doFocus();">
	  <OPTION value="">--please select--</OPTION>
	   <OPTION value="ProjectAddApp">ProjectAddApp</OPTION>
	 <OPTION value="ProjectChangeApp">ProjectChangeApp</OPTION>
	 <OPTION value="ProjectConfirmApp">ProjectConfirmApp</OPTION>	 
	  </SELECT>
	 </td>
	 <td class="list_range" width="70"> <bean:message bundle="projectpre" key="log.operation"/>
    </td>
	 <td class="list_range" width="100">
	 <SELECT name="operation" class="Selectbox  Nreq" id="input_common1" onblur="doBlur();" onfocus="doFocus();">
	 <OPTION value="">--please select--</OPTION>
	 <OPTION value="Submitted">Submitted</OPTION>
	  <OPTION value="Rejected">Rejected</OPTION>
	  <OPTION value="Confirmed">Confirmed</OPTION>
	 </SELECT>
	   </td>  
	</tr>
	<tr>
	 
       <td class="list_range" width="120"> <bean:message bundle="projectpre" key="log.operator"/>
    </td>
	 <td class="list_range" width="100">
						<html:text 
						name="operator"
						maxlength="20"
						 beanName="webVo" 
						 fieldtype="text" 
						 styleId="input_common1" 
						 prev="dueDate" 
						 readonly="false"				
						 imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>" 
						 imageWidth="18"
						 imageOnclick="allocateHr()" />
	</td>
     
      <td class="list_range" width="120"> <bean:message bundle="projectpre" key="log.mailSender"/>
    </td>
	<td class="list_range" width="100">
						<html:text 
						name="mailSender"
						maxlength="20"
						 beanName="webVo" 
						 fieldtype="text" 
						 styleId="input_common1" 
						 prev="dueDate" 
						 readonly="false"				
						 imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>" 
						 imageWidth="18"
						 imageOnclick="allocateMailSender()" />
	</td> 
  </tr>
  <tr>
   <td class="list_range" width="120"><bean:message bundle="projectpre" key="projectCode.CodeChangeDetail.BeginDate"/>
    </td>
    <td class="list_range" width="100">
			<html:text name="startDate"
                      beanName="webVo"
                      fieldtype="dateyyyymmdd"
                      styleId="input_common1"
                      imageSrc="<%=contextPath+"/image/cal.png"%>"
                      imageWidth="18"
                      imageOnclick="getMyDATE('startDate')"
                      maxlength="10" 
                      ondblclick="getDATE(this)"
            />
			</td>
			
			<td class="list_range" width="120"><bean:message bundle="projectpre" key="projectCode.CodeChangeDetail.EndDate"/>
    </td>
    <td class="list_range" width="100">
			<html:text name="endDate"
                      beanName="webVo"
                      fieldtype="dateyyyymmdd"
                      styleId="input_common1"
                      imageSrc="<%=contextPath+"/image/cal.png"%>"
                      imageWidth="18"
                      imageOnclick="getMyDATE('endDate')"
                      maxlength="10" 
                      ondblclick="getDATE(this)"
            />
			</td>
  </tr>
</table>
</html:form>
    </body>
</html>
