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
		var names = new Array(document.getElementsByName("deptManager")[0],document.
		getElementsByName("BDMName")[0],document.getElementsByName("TCSName")[0],document.getElementsByName("applicantName")[0]);
		var loginId = new Array(document.forms[0].DMLoginId,document.forms[0].BDLoginId,document.forms[0].TCSLoginId,document.forms[0].applicantLoginId);
		return new Array(names,loginId);
	}
     //用于查人
    function allocateHr() {
     try{
      var form=document.forms[0];     
      var param = new HashMap();
      var result = allocSingleInAD(param,"","Allocate single user in AD","");
      if(result!=""){  
      document.getElementsByName("deptManager")[0].value = param.values()[0].name;
    document.forms[0].DMLoginId.value = param.values()[0].loginId;
      }
      }catch(e){}    
    }
//用于查人
    function allocateHrBD() {
     try{
      var form=document.forms[0];
      var param = new HashMap();
      var result = allocSingleInAD(param,"","Allocate single user in AD","");
       if(result!=""){  
       document.getElementsByName("BDMName")[0].value = param.values()[0].name;
       document.forms[0].DMLoginId.value = param.values()[0].loginId;
      }
      }catch(e){}    
    }
    
    function allocateHrApplicant() {
     try{
      var form=document.forms[0];
      var param = new HashMap();
      var result = allocSingleInAD(param,"","Allocate single user in AD","");
       if(result!=""){  
       document.getElementsByName("applicantName")[0].value = param.values()[0].name;
       document.forms[0].applicantLoginId.value = param.values()[0].loginId;
      }
      }catch(e){}    
    }
//用于查人
 function allocateHrTC() {
    try{
      var form=document.forms[0];
      var param = new HashMap();
      var result = allocSingleInAD(param,"","Allocate single user in AD","");
       if(result!=""){  
       document.getElementsByName("TCSName")[0].value = param.values()[0].name;
        document.forms[0].TCSLoginId.value = param.values()[0].loginId;
      }   
      }catch(e){}   
    }
     
     
     function onSearch(){
       AfQueryData.submit();
      //var downFrame=window.parent.queryList;
      //downFrame.location="/essp/customer/query/customerQueryList.do"
     }

     </script>
  </head>
  
  <body>
 
 <html:form id="AfQueryData" action="/dept/query/deptShowData" method="post" target="showData" >
<table width="800" border="0" style="padding-left:8px">
<html:hidden name="DMLoginId" beanName="webVo"/>
		<html:hidden name="BDLoginId" beanName="webVo"/>
		<html:hidden name="TCSLoginId" beanName="webVo"/>
		<html:hidden name="applicantLoginId" beanName="webVo"></html:hidden>
  <tr>
    <td class="list_range" width="120"><bean:message bundle="projectpre" key="deptCode.DepartmentNo"/>
    </td>
    <td class="list_range" >
    <html:text styleId="input_common1" name="acntId"  beanName="webVo"  fieldtype="text" readonly="false"  maxlength="20"/>
	</td>
     <td class="list_range" width="120"><bean:message bundle="projectpre" key="deptCode.Applicant"/>
    </td>
    <td class="list_range" >
    <html:text styleId="input_common1" 
      name="applicantName"
     maxlength="25"
	    beanName="webVo" 
		fieldtype="text" 
	    styleId="input_common1" 
	    prev="dueDate"
	    readonly="false" 
	    req="false"
		imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>" 
		imageWidth="18"
	    imageOnclick="allocateHrApplicant()"
      />
	</td>
	<td class="list_range" width="120"> <bean:message bundle="projectpre" key="deptCode.AcheiveBelongUnit"/>
    </td>
	
	 <td class="list_range">
	<html:select name="belongBd" styleId="input_common1" beanName="webVo" >
	<logic:notPresent name="webVo" property="bdList">
	<option value="" >--please select--</option>
	</logic:notPresent>
	<logic:present name="webVo" property="bdList">
	<html:optionsCollection name="webVo" property="bdList"></html:optionsCollection>
	</logic:present>
	</html:select></td>
    
    <td class="list_range">&nbsp;</td>
  </tr>
  <tr>
   <td class="list_range" width="120"><bean:message bundle="projectpre" key="projectCode.QueryProject.DivisionManager"/>
    </td>
    <td class="list_range" width="100">
		<html:text 
		name="BDMName"
	    maxlength="25"
	    beanName="webVo" 
		fieldtype="text" 
	    styleId="input_common1" 
	    prev="dueDate"
	    readonly="false" 
	    req="false"
		imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>" 
		imageWidth="18"
	    imageOnclick="allocateHrBD()" />
	</td>
	<td class="list_range" width="120"><bean:message bundle="projectpre" key="deptCode.TimeCardSigner"/>
    </td>
   <td class="list_range" width="100">
	<html:text 
	   name="TCSName"
	   maxlength="25"
	   beanName="webVo" 
	   fieldtype="text" 
	   styleId="input_common1" 
	   prev="dueDate" 
	   readonly="false" 
	   req="false"
	   imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>" 
       imageWidth="18"
	   imageOnclick="allocateHrTC()" />
</td>
	<td class="list_range" width="120"><bean:message bundle="projectpre" key="deptCode.Leader"/>
    </td>
    <td class="list_range" width="100">
	<html:text 
		name="deptManager"
		maxlength="25"
		beanName="webVo" 
	    fieldtype="text" 
		styleId="input_common1" 
		prev="dueDate" 
		readonly="false" 
		req="false"
        imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>" 
        imageWidth="18"
		imageOnclick="allocateHr()" />
		</td>

	<td class="list_range">&nbsp;</td>
  </tr>
  <tr>
   <td class="list_range" width="120"><bean:message bundle="projectpre" key="deptCode.DepartmentShortName"/>
    </td>
    <td class="list_range" colspan="6">
    <html:text styleId="input_common2" name="acntName"  beanName="webVo"  fieldtype="text" readonly="false"  maxlength="20"/>
	</td>
    <td class="list_range">&nbsp;</td>
  </tr>
</table>
</html:form>
    </body>
</html>
