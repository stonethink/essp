
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
<%
  String contextPath = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
         <tiles:insert page="/layout/head.jsp">
            <tiles:put name="title" value=" "/>
            <tiles:put name="jspName" value=""/>
        </tiles:insert>
		<title></title>


		<script language="JavaScript" type="text/javascript">
		function autoClickFirstRow(){	
		  try{
		   var  table=document.getElementById("ShowData_table");
           var  str0="<bean:message bundle='projectpre' key='deptCode.DepartmentNo'/>";
           var  str1="<bean:message bundle='projectpre' key='deptCode.Applicant'/>";
           var  str2="<bean:message bundle='projectpre' key='deptCode.AcheiveBelongUnit'/>";
           var  str3="<bean:message bundle='projectpre' key='deptCode.BDManager'/>";           
           var  str4="<bean:message bundle='projectpre' key='projectCode.MasterData.TimeCardSigner'/>";
           var  str5="<bean:message bundle='projectpre' key='deptCode.DepartmentManager'/>";
           var  str6="<bean:message bundle='projectpre' key='deptCode.DepartmentShortName'/>";
           var  str7="<bean:message bundle='projectpre' key='deptCode.DepartmentFullName'/>";
   	       var  thead_length=table.tHead.rows.length;
	       var  tds=table.rows[thead_length-1];
		   var  cells=tds.cells;
           cells[0].innerHTML=cells[0].innerHTML.replace("acntId",str0);
           cells[1].innerHTML=cells[1].innerHTML.replace("applicantName",str1);
           cells[2].innerHTML=cells[2].innerHTML.replace("achieveBelong",str2);
           cells[3].innerHTML=cells[3].innerHTML.replace("BDMName",str3);
           cells[4].innerHTML=cells[4].innerHTML.replace("TCSName",str4);
           cells[5].innerHTML=cells[5].innerHTML.replace("deptManager",str5);
           cells[6].innerHTML=cells[6].innerHTML.replace("acntName",str6);
           cells[7].innerHTML=cells[7].innerHTML.replace("acntFullName",str7);
           if(table.rows.length>thead_length){
	            var firstRow=table.rows[thead_length];
	         //   rowOnClick(firstRow);
		        onChangeBackgroundColor(firstRow);
	       } 
	       }catch(e){}
       }
	</script>

	</head>
	<body >
	<%
	java.util.Locale locale = (java.util.Locale)session.getAttribute(org.apache.struts.Globals.LOCALE_KEY); 	
  	String _language = locale.toString();	
  %>
   <ec:table 
       tableId="ShowData"
       items="webVo"
       var="dQuery"
       action="${pageContext.request.contextPath}/dept/query/deptShowData.do"  
       imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
        locale="<%=_language%>"
    >
        <ec:row >
             
             <ec:column headerStyle="width:100" property="acntId" tooltip="${dQuery.acntId}" title="acntId"/>    
             <ec:column headerStyle="width:100" property="applicantName" tooltip="${dQuery.applicantName}" title="applicantName" /> 
             <ec:column headerStyle="width:100" property="achieveBelong" tooltip="${dQuery.achieveBelong}" title="achieveBelong" />        
             <ec:column headerStyle="width:100" property="BDMName" tooltip="${dQuery.BDMName}" title="BDMName" />             
             <ec:column headerStyle="width:100"  property="TCSName" tooltip="${dQuery.TCSName}" title="TCSName" />
             <ec:column headerStyle="width:100"  property="deptManager" tooltip="${dQuery.deptManager}" title="deptManager" />
             <ec:column headerStyle="width:170" property="acntName" tooltip="${dQuery.acntName}" title="acntName" />
             <ec:column headerStyle="width:200" property="acntFullName" tooltip="${dQuery.acntFullName}" title="acntFullName" />
  
         </ec:row>
     </ec:table>
       <script type="text/javascript" language="javascript">
      autoClickFirstRow();
     </script>
     
     
	</body>

</html>
