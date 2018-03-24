
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>

<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<TITLE><bean:message bundle="projectpre" key="projectCode.upload.Title" /></TITLE>
         <tiles:insert page="/layout/head.jsp">
            <tiles:put name="title" value="Upload Page"/>
            <tiles:put name="jspName" value=""/>
        </tiles:insert>


<script language="JavaScript" type="text/javascript">
	 
	 function onClickCancel(){
	   this.close();
	 }
	 function onClickSub(){
	   var form = document.forms[0];
	   form.submit();
	   
	 }
	  function allocate(){
    try{
    	param = new HashMap();
   	 	var result = allocMultipleInAD(param,"","","")
   	 	var person = param.values();
   	 	var otherPerson = document.forms[0].otherPerson;
   	 	var temp;
   	 	var tempLoginId;
   	 	var tempDomain;
   	 	for(var i=0;i<param.values().length;i++){
   	 	  //alert("name:"+person[i].name+"loginId:"+person[i].loginId+"domain:"+person[i].domain);
   	 	  if(i==0){
   	 	  	temp = person[i].name;
   	 	  	tempLoginId = person[i].loginId;
   	 	  	tempDomain = person[i].domain;
   	 	  } else {
   	 	    temp = temp+","+person[i].name;
   	 	    tempLoginId = tempLoginId+","+person[i].loginId;
   	 	    tempDomain = tempDomain+","+person[i].domain;
   	 	  }
   	 	}
   	 	otherPerson.value = temp;
   	 	document.forms[0].otherPersonId.value = tempLoginId;
   	 	document.forms[0].otherPersonDomain.value = tempDomain;
    } catch (e){}
    }
    function bodyLoad(){
      var myParam = window.dialogArguments;
      //alert(myObj.values()[0]);
      document.forms[0].PMName.value=myParam.values()[0];
      document.forms[0].BDMName.value=myParam.values()[1];
      document.forms[0].leaderName.value=myParam.values()[2];
    }
	</script>
	<style type="text/css">
      #input_common{width:120}
      #input_common1{width:324}
    </style>
	</head>
	<body >
	<html:form  id="UploadForm" method="post" action="/project/apply/uploadFile" target="_self"  enctype="multipart/form-data"  >
	<html:hidden name="otherPersonId"/>
	<html:hidden name="otherPersonDomain"/>
   <table align="center" height="50">
   <tr>
   <td class="list_range">
<bean:message bundle="projectpre" key="projectCode.MasterData.ProjectManager"/>
         </td>
   <td class="list_range" width="110">
   <html:text name="PMName"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_common"
                      readonly="true"
         />
   </td>
   <td class="list_range"><bean:message bundle="projectpre" key="projectCode.BD" /></td>
   <td class="list_range">
   <html:text name="BDMName" 
               beanName="webVo" 
               fieldtype="text" 
               styleId="input_common" 
               readonly="true"
               />
   </td>
   </tr>
   <tr>
    <td class="list_range"><bean:message bundle="projectpre" key="projectCode.MasterData.Leader"/></td>
    <td class="list_range">
    <html:text name="leaderName"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_common"
                	  readonly="true"
         />
    </td>
    <td class="list_range"></td>
    <td class="list_range">
   
    </td>
   </tr>
    <tr>
    <td class="list_range"><bean:message bundle="projectpre" key="projectCode.MasterData.OtherPerson"/></td>
    <td class="list_range" colspan="3">
     <html:textarea name="otherPerson"
                      beanName="webVo"
                      styleId="input_common1"
                      readonly="true"
                   	  cols="100"
         /><IMG alt="" src="<%=contextPath+"/image/humanAllocate.gif"%>" onclick="allocate()" style="CURSOR:hand">
    </td>
   </tr>
    <tr>
    <td class="list_range">&nbsp;</td>
    <td class="list_range"></td>
    <td class="list_range"></td>
    <td class="list_range"></td>
   </tr>
   </table>
   <table>
   <tr>
  <td class="list_range">
  <bean:message bundle="projectpre" key="projectCode.upload.Comment3" />
  <FONT color="red"><bean:message bundle="projectpre" key="projectCode.upload.Attention" /></FONT>
  </td>
  </tr>
  
   </table>
  <table id="Table1" cellSpacing="0" align="center" height="50" cellPadding="0"  width="450" border="0" style="padding-left:8px">
  
  <tr>
    <td class="list_range" width="120"><bean:message bundle="projectpre" key="projectCode.upload.SelectFile" /></td>
    <td class="list_range" width="100"><html:file name="file" styleId="" /></td>
    <td class="list_range" width="*">&nbsp;</td>
  </tr>
   <tr>
    <td class="list_range" colspan="3"></td>
    
  </tr>
  
</table>
	<table width="300" border="0" align="center">
				<tr >
					
					<td align="center">
					
						<html:submit styleId="btnOk" name="submit" >
							<bean:message bundle="application" key="global.button.confirm" />
						</html:submit> 
						<html:button styleId="btnCancel" name="btnCancel" onclick="onClickCancel()">
							<bean:message bundle="application" key="global.button.cancel" />
						</html:button>
					</td>
				</tr>
		</table>

</html:form>
	</body>
	<script language="JavaScript" type="text/javascript">
		bodyLoad();
	</script>
	
</html>
