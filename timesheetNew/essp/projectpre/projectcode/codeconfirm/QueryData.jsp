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
		var names = new Array(document.getElementsByName("applicant")[0]);
		var loginId = new Array(document.forms[0].ApplicantId);
		return new Array(names,loginId);
	}
//用于查部门经理
    function allocateHr() {
     try{
     
      var param = new HashMap();
      var result = allocSingleInAD(param,"","Allocate single user in AD","");
      if(result!=""){  
      document.getElementsByName("applicant")[0].value = param.values()[0].name;
      document.forms[0].ApplicantId.value = param.values()[0].loginId;
      document.forms[0].DMDomain.value=param.values()[0].domain;
      }
      }catch(e){}
     
    }
     
     function onSearch(){
      var beginDate = document.all.acntPlannedStart.value;
      var endDate = document.all.acntPlannedFinish.value;
      //alert(beginDate+"~"+endDate);
      if(beginDate!=""&&endDate!=""&&beginDate>=endDate){
         alert("<bean:message bundle="application" key="global.fill.dateError"/>");
         return;
         }
       AfProjectConfirm.submit();
      //var downFrame=window.parent.queryList;
      //downFrame.location="/essp/customer/query/customerQueryList.do"
     }
      function getMyDATE(dateName){
     try{
    	var date = document.getElementById(dateName);
    	date.focus();
    	getDATE(date);
    	} catch(e){}
     }
	function subForm(){
	  document.forms[0].submit();
	}
     </script>
  </head>
  
  <body>
 
 <html:form id="AfProjectConfirm" action="/project/confirm/resultList" method="post" target="resultList" >
<table width="800" border="0" style="padding-left:8px">
<html:hidden name="ApplicantId" beanName="webVo"/>
  <tr>
    <td class="list_range" width="120"><bean:message bundle="projectpre" key="projectCode.QueryProject.ProjectCode"/>
    </td>
    <td class="list_range">
    <html:text styleId="input_common1" name="acntId"  beanName="webVo"  fieldtype="text" readonly="false"  maxlength="10"/>
	</td>
     <td class="list_range" width="120"><bean:message bundle="projectpre" key="deptCode.Applicant"/>
    </td>
    <td class="list_range" width="100">
						<html:text 
						name="applicant"
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
	<td></td><td></td>
	</tr>
	<tr>
	<td class="list_range" width="120"> <bean:message bundle="projectpre" key="projectCode.projectName"/>
    </td>
	 <td class="list_range" width="100">
	 <html:text styleId="input_common1" name="acntName" beanName="webVo" fieldtype="text" maxlength="20"></html:text>
     </td>
       
       <td class="list_range" width="120"> <bean:message bundle="projectpre" key="projectCode.CodeChangeDetail.CodeType"/>
    </td>
	 <td class="list_range">
	 <select class="Selectbox  Nreq" name="acntType"
    id="input_common1" 
    onblur="doBlur();" 
    onfocus="doFocus();">
	<OPTION value="">--please select--</OPTION>
	<OPTION value="Master"><bean:message bundle="projectpre" key="projectCode.MasterData.MasterProject"/></OPTION>
	<OPTION value="Sub"><bean:message bundle="projectpre" key="projectCode.MasterData.SubProject"/></OPTION>
	<OPTION value="Related"><bean:message bundle="projectpre" key="projectCode.MasterData.RelProject"/></OPTION>
	<OPTION value="Finance"><bean:message bundle="projectpre" key="projectCode.MasterData.FinProject"/></OPTION>
	</select>
	
     </td>
     
      <td class="list_range" width="120"> <bean:message bundle="projectpre" key="projectCode.CodeChangeDetail.CustomerShort"/>
    </td>
	 <td class="list_range">
	 <html:text styleId="input_common1" name="customerId" beanName="webVo" fieldtype="text" maxlength="20"></html:text>
     </td>  
  </tr>
  <tr>
   <td class="list_range" width="120"><bean:message bundle="projectpre" key="projectCode.CodeChangeDetail.BeginDate"/>
    </td>
    <td class="list_range" width="100">
			<html:text name="acntPlannedStart"
                      beanName="webVo"
                      fieldtype="dateyyyymmdd"
                      styleId="input_common1"
                      imageSrc="<%=contextPath+"/image/cal.png"%>"
                      imageWidth="18"
                      imageOnclick="getMyDATE('acntPlannedStart')"
                      maxlength="10" 
                      ondblclick="getDATE(this)"
            />
			</td>
	
	<td class="list_range" width="120"><bean:message bundle="projectpre" key="projectCode.CodeChangeDetail.EndDate"/>
    </td>
  <td class="list_range" width="100">
			 <html:text name="acntPlannedFinish"
                      beanName="webVo"
                      fieldtype="dateyyyymmdd"
                      styleId="input_common1"
                      imageSrc="<%=contextPath+"/image/cal.png"%>"
                      imageWidth="18"
                      imageOnclick="getMyDATE('acntPlannedFinish')"
                      maxlength="10" 
                      ondblclick="getDATE(this)"
            />
			</td>
	<td class="list_range" width="120"><bean:message bundle="projectpre" key="customer.StatusCode"/>
    </td>
    <td class="list_range" width="100">
   
	<select class="Selectbox  Nreq" name="acntStatus"
    id="input_common1" 
    onblur="doBlur();" 
    onfocus="doFocus();">
	<OPTION value="">--please select--</OPTION>
	<OPTION value="UnApply">UnApply</OPTION>
	<OPTION value="Submitted">Submitted</OPTION>
	<OPTION value="Rejected">Rejected</OPTION>
	</select>
	</td>
	
  </tr>
</table>
</html:form>
    </body>
</html>
