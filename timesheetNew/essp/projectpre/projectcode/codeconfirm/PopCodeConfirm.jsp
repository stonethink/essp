
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<bean:define id="status" value="<%=request.getParameter("status")%>"/>
<bean:define id="appType" value="<%=request.getParameter("appType")%>"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<%if(request.getParameter("appType").equals("check")) {%>
	<TITLE><bean:message bundle="projectpre" key="projectCode.ProjectCodeCheck.cardTitle.ProjectCodeCheck"/></TITLE>
	
	<%}else{%>
	<TITLE><bean:message bundle="projectpre" key="projectCode.PopCodeConfirm.ProjectCodeConfirmApply"/></TITLE>
	<%}%>
		<tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value="Project Code Confirm" />
			<tiles:put name="jspName" value="" />
		</tiles:insert>
		<style type="text/css">
          #input_common1{width:50}
          #input_common2{width:130}
          #input_common3{width:110}
		  #input_common4{width:212}
		  #input_common5{width:585}
          #input_field{width:60}
          #description{width:584}
    </style>
	</head>
	<script language="javascript" type="text/javascript">
	//此方法用于检查必填项
     function checkReq(form){
	     if(!submitForm(form)){
    	 	return false;
	    } else {
	       
              return true;	
           
	    }
	     
     }
     function onClickSubmit(){
       var appType = "<%=appType%>";
      if(appType!="check"){
       if(confirm("<bean:message bundle="application" key="global.confirm.submit"/>")){
       document.forms[0].action="<%=contextPath%>/project/confirm/confirmProjectApp.do";
       document.forms[0].submit();
       }
      }else {
       if(confirm("<bean:message bundle="application" key="global.confirm.check"/>")){
       document.forms[0].action="<%=contextPath%>/project/check/checkConfirmProjectApp.do?status=Confirmed";
       document.forms[0].submit();
       }
      }
     }
     function onClickCancel(){
       this.close();
     }
     function onClickReject(){
       try{
        var comment = document.all.comment.value;
        if(comment==null||comment.replace(/(^\s*)|(\s*$)/g, "")=="") {
          alert("<bean:message bundle="application" key="global.fill.Remark"/>");
          document.all.comment.focus();
          return;
        } 
       
          document.forms[0].action="<%=contextPath%>/project/check/checkConfirmProjectApp.do?status=Rejected";
          document.forms[0].submit();
       }catch(e){}
     }
     
     function onInit() {
     try{
       var status = "<%=status%>";
       var appType = "<%=appType%>";
        if(status=="Rejected") {
            hidComment.style.visibility="visible";
      	    hidCommentText.style.visibility="visible";
      	    document.all.comment.readOnly="true";
        } else if(appType=="check"){
      	    hidComment.style.visibility="visible";
      	    hidCommentText.style.visibility="visible";
      	    document.all.comment.value="";
      	} else {
      	    hidComment.style.visibility="hidden";
      	    hidCommentText.style.visibility="hidden";
      	
      	}
      	if(status=="Submitted") {
      	document.all.btnSubmit.disabled=true;
      	} else {
      	document.all.btnSubmit.disabled=false;
      	}
      }catch(e){}
     }
  </script>
	<body >

		<html:form id="ProjectConfirm" action="" method="post">
		<table align=center>
		<td class="list_range" >
		<%if(request.getParameter("appType").equals("check")) {%>
	    <bean:message bundle="projectpre" key="projectCode.ProjectCodeCheck.cardTitle.ProjectCodeCheck"/>
	
	    <%}else{%>
	    <bean:message bundle="projectpre" key="projectCode.PopCodeConfirm.ProjectCodeConfirmApply"/>
	    <%}%>
		</td>
		</table>
			<table width="713" border="0" align="center">
				<tr>
					<td class="list_range" ></td>
					<td class="list_range" ></td>
					<td class="list_range" ></td>
					<td class="list_range"></td>
					<td class="list_range" ></td>
					<td class="list_range" ></td>
					
				</tr>
				<tr>
					<td align="center" colspan="6">
			
					</td>
				</tr>
				<tr>
					<td class="list_range"></td>
					<td class="list_range" ></td>
					<td class="list_range" ></td>
					<td class="list_range" ></td>
					<td class="list_range" ></td>
					<td class="list_range" ></td>
				
				</tr>
				<tr>
					<td class="list_range" width="120"></td>
					<td class="list_range" width="60"></td>
					<td class="list_range" width="100"></td>
					<td class="list_range" width="60"></td>
					<td class="list_range" width="80"></td>
					<td class="list_range" width="60"></td>
				
				
				
				
				</tr>
				<tr>
					<td class="list_range" >
						<bean:message bundle="projectpre" key="projectCode.PopCodeConfirm.Applicant" />
					</td>
					<td class="list_range" >
						<html:text fieldtype="text" styleId="input_common2" name="applicant" beanName="webVo" readonly="true"  />
					</td>
					<td class="list_range" >
						<bean:message bundle="projectpre" key="projectCode.PopCodeConfirm.AchieveBelong" />
					</td>
					<td class="list_range" >
						<html:text fieldtype="text" styleId="input_common2" name="acntId" beanName="webVo" readonly="true"  />
					</td>
					<td class="list_range" >
						<bean:message bundle="projectpre" key="projectCode.PopCodeConfirm.Leader" />
					</td>
					<td class="list_range" >
						<html:text fieldtype="text" styleId="input_common2" name="leader" beanName="webVo" readonly="true"  />
					</td>
				
				</tr>
				<tr>
					<td class="list_range">
						<bean:message bundle="projectpre" key="projectCode.MasterData.ProjectManager" />
					</td>
					<td  class="list_range">
						<html:text fieldtype="text" styleId="input_common2" name="PMName" beanName="webVo" readonly="true"  />
					</td>
					<td class="list_range" >
						<bean:message bundle="projectpre" key="projectCode.MasterData.TimeCardSigner" />
					</td>
					<td class="list_range" >
						<html:text fieldtype="text" styleId="input_common2" name="TCSName" beanName="webVo" readonly="true"  />
					</td>
					<td class="list_range">
						<bean:message bundle="projectpre" key="projectCode.BD" />
					</td>
					<td class="list_range">
						<html:text fieldtype="text" styleId="input_common2" name="BDName" beanName="webVo" readonly="true"  />
					</td>
				
				</tr>
				<tr>
					<td class="list_range">
						<bean:message bundle="projectpre" key="projectCode.MasterData.NiceName" />
					</td>
					<td class="list_range" colspan="5">
						<html:text fieldtype="text" styleId="input_common5" name="acntName" beanName="webVo" readonly="true"  />
					</td>
					
					
				</tr>
				<tr>
					<td class="list_range">
						<bean:message bundle="projectpre" key="projectCode.PopCodeConfirm.ProjectName" />
					</td>
					<td class="list_range" colspan="5">
						<html:text fieldtype="text" styleId="input_common5" name="acntFullName" beanName="webVo" readonly="true"  />
					</td>
				
				
				</tr>
				<tr>
					<td class="list_range" >
						<bean:message bundle="projectpre" key="projectCode.PopCodeConfirm.ProjectDesc" />
					</td>
					<td class="list_range" colspan="5" >
						<html:textarea name="acntBrief" beanName="webVo" rows="3" styleId="description" readonly="true" maxlength="1000" styleClass="TextAreaStyle"  />
					</td>
				</tr>
				<tr>
					<td class="list_range">
						<bean:message bundle="projectpre" key="projectCode.PopCodeConfirm.ProjectProperty" />
					</td>
					<td class="list_range" >
						<html:text fieldtype="text" styleId="input_common2" name="acntAttribute" beanName="webVo" readonly="true"  />
					</td>
					<td class="list_range"  colspan="2">
						
						<bean:message bundle="projectpre" key="projectCode.PopCodeConfirm.ProjectSchedule" />&nbsp;&nbsp;
				</td>
						<td class="list_range"  colspan="2">
						<html:text fieldtype="text" styleId="input_common4" name="acntAnticpated" beanName="webVo" readonly="true"  />
					</td>
				
					
				
				</tr>
				<tr>
				<td class="list_range">
				<bean:message bundle="projectpre" key="projectCode.PopCodeConfirm.ProductType" />
				</td>
				<td class="list_range" colspan="5">
				<html:text fieldtype="text" styleId="input_common5" name="productType" beanName="webVo" readonly="true"  />
				</td>
				</tr>
				<tr>
					<td class="list_range">
						<bean:message bundle="projectpre" key="projectCode.PopCodeConfirm.ProductProperty" />
					</td>
					<td class="list_range" colspan="5">
						<html:text fieldtype="text" styleId="input_common5" name="productAttribute" beanName="webVo" readonly="true"  />
					</td>
				
					
			
				
				</tr>

				<tr>
					<td class="list_range" >
						<bean:message bundle="projectpre" key="projectCode.PopCodeConfirm.WorkItem" />
					</td>
					<td class="list_range" colspan="5">
						<html:text fieldtype="text" styleId="input_common5" name="workItem" beanName="webVo" readonly="true"  />
					</td>
	
						
				</tr>

				<tr>
   <td class="list_range"><div id="hidComment"><bean:message bundle="projectpre" key="customer.ClientRemark"/></div></td>
   <td class="list_range" colspan="5"><div id="hidCommentText"><html:textarea name="comment" beanName="webVo" rows="2" styleId="description" req="false" maxlength="500" /></div></td>
  </tr>
				<tr>
					<td class="list_range">
						 &nbsp;
					</td>
				
					<td class="list_range" colspan="5">
						 &nbsp;
					</td>
				
				</tr>
	</table>	
	<table width="713" border="0" align="center">
      <tr>
      
     <td align="right">
			<html:button styleId="btnOk" name="btnSubmit" onclick="onClickSubmit()">

			<%if(request.getParameter("appType").equals("check")){%>
			<bean:message bundle="application" key="global.button.confirm" />
			<%}else{%>
			<bean:message bundle="application" key="global.button.submit" />	
			<%}%>
			</html:button>
			<%if(request.getParameter("appType").equals("check")) { %>
			 <html:button styleId="btnReject" name="btnReject" onclick="onClickReject()">
			 <bean:message bundle="application" key="global.button.reject" />
		     </html:button>	
			<%}%>
			<html:button styleId="btnCancel" name="btnCancel" onclick="onClickCancel()">
			<bean:message bundle="application" key="global.button.cancel" />
			</html:button>
			</td>
		
			</tr>
    	</table>
		</html:form>
	  <SCRIPT language="JavaScript" type="text/JavaScript">
	    onInit();
	  </SCRIPT>
	</body>
</html>
