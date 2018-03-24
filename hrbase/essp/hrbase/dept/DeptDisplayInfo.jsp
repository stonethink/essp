
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
      #input_common2{width:490}
      #input_common3{width:160}
      #input_common4{width:273}
      #input_field{width:60}
      #description{width:600}
    </style>
     <script type="text/javascript" language="javascript"> 
     function onClickClose(){
	   window.close(); 	  
     }
     function onClickPrint(){
      if(confirm("<bean:message bundle="application" key="global.confirm.print"/>")){
        window.print();
      } else {
        return;
     }
   }
     </script>
  </head>
  
  <body>
 
 <html:form id="AfQueryData" action="/dept/addDept" method="post" onsubmit="return checkReq(this)" >
 <table align="center"><tr><td><STRONG><bean:message bundle="hrbase" key="hrbase.dept.deptdetail"/></STRONG></td></tr><tr height="20"><td></td></tr></table>
 <table width="600" border="1"  cellspacing="1" style="padding-left:8px" align="center">
	<html:hidden name="bdId" beanName="webVo"/>
	<html:hidden name="tsId" beanName="webVo"/>
	<html:hidden name="dmId" beanName="webVo"/>
  <tr>
    <td class="list_range" width="100"><bean:message bundle="hrbase" key="hrbase.dept.deptcode"/>
    </td>
    <td class="list_range" >
     <bean:write name="webVo" property="unitCode"/>&nbsp;
	</td>
	<td class="list_range"><bean:message bundle="hrbase" key="hrbase.dept.isBl"/></td>
	<td class="list_range"><bean:write name="webVo" property="blLable"/>&nbsp;</td>
	</tr>
	<tr>
	<td class="list_range" width="100"><bean:message bundle="hrbase" key="hrbase.dept.acheivebelongunit"/>
    </td>
	 <td class="list_range">
	 <bean:write name="webVo" property="belongBd"/>&nbsp;
	</td>
	   <td class="list_range" width="100"><bean:message bundle="hrbase" key="hrbase.dept.CostBelongUnit"/>
    <td class="list_range" >
     <bean:write name="webVo" property="costBelongUnit"/>&nbsp;
	</td>
	</tr>
	<tr>
     <td class="list_range" width="100"><bean:message bundle="hrbase" key="hrbase.dept.deptmanager"/>
    </td>
    <td class="list_range" width="100">
     <bean:write name="webVo" property="dmName"/>&nbsp;
    </td>
	<td class="list_range" width="100"><bean:message bundle="hrbase" key="hrbase.dept.tcsinger"/>
    </td>
   <td class="list_range" width="100">
	   <bean:write name="webVo" property="tsName"/>&nbsp;
   </td>
   </tr>
   <tr>
	<td class="list_range" width="100"><bean:message bundle="hrbase" key="hrbase.dept.bdmanager"/>
    </td>
    <td class="list_range" width="100">
    <bean:write name="webVo" property="bdName"/>&nbsp;
	</td>
	<td class="list_range" width="100"><bean:message bundle="hrbase" key="hrbase.dept.projattribute"/>
    </td>
    <td class="list_range" >
	 <bean:write name="webVo" property="acntAttribute"/>&nbsp;
	</td>
    </tr>
   <tr>
    <td class="list_range" width="100"><bean:message bundle="hrbase" key="hrbase.dept.belongsite"/>
    </td>
	 <td class="list_range">
	 <bean:write name="webVo" property="belongSite"/>&nbsp;
	</td>
	  <td class="list_range" width="100"><bean:message bundle="hrbase" key="hrbase.dept.parentdeptcode"/>
    </td>
    <td class="list_range">
	 <bean:write name="webVo" property="parentUnitCode"/>&nbsp;
	</td>
 </tr>
  <tr>
	<td class="list_range" width="100"><bean:message bundle="hrbase" key="hrbase.dept.effBegin"/>
    </td>
	 <td class="list_range">
	 <bean:write name="webVo" property="effectiveBegin"/>&nbsp;
    <td class="list_range" width="100"><bean:message bundle="hrbase" key="hrbase.dept.effEnd"/>
    </td>
    <td class="list_range"  >
    <bean:write name="webVo" property="effectiveEnd"/>&nbsp;
	</td>
	</tr>
   <tr>
   <td class="list_range" width="100"><bean:message bundle="hrbase" key="hrbase.dept.deptname"/>
    </td>
    <td class="list_range" colspan="4" >
    <bean:write name="webVo" property="unitName"/>&nbsp;
	</td>
   </tr>
   <tr>
   <td class="list_range" width="100"><bean:message bundle="hrbase" key="hrbase.dept.deptfullname"/>
    </td>
    <td class="list_range" colspan="4">
	<bean:write name="webVo" property="unitFullName"/>&nbsp;
	</td>
   </tr>
   <tr height="60"><td></td></tr>
</table>
<table align="center" width="600">
  <tr align="center">
		<td >
		<html:button styleId="noPrint" name="printInfo" onclick="onClickPrint()">
			<bean:message bundle="application" key="global.button.print" />
		</html:button>
        <html:button styleId="noPrint" name="closeCancel" onclick="onClickClose()">
			<bean:message bundle="application" key="global.button.close" />
		</html:button>
		</td>
		<td width="60"></td>
	</tr>
</table>
</html:form>
    </body>
</html>
