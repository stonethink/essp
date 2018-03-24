
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
		<title>Project Code Apply</title>


		<script language="JavaScript" type="text/javascript">
		function autoClickFirstRow(){
		  try{
		   var  table=document.getElementById("QueryList_table");
           var  clientShortName="<bean:message bundle='projectpre' key='customer.ClientShortName'/>";
           var  customerNo="<bean:message bundle='projectpre' key='customer.ClientNo'/>";
           var  customerAttribute="<bean:message bundle='projectpre' key='customer.Attribute'/>";
           var  groupCustomerNo="<bean:message bundle='projectpre' key='customer.GroupNo'/>";           
           var  belongedBD="<bean:message bundle='projectpre' key='customer.BelongedBD'/>";
           var  belongedSite="<bean:message bundle='projectpre' key='customer.BelongedSite'/>";
           var  clientClassCode="<bean:message bundle='projectpre' key='customer.ClientClassCode'/>";
           var  clientCountryCode="<bean:message bundle='projectpre' key='customer.ClientCountryCode'/>";
           var  createDate="<bean:message bundle='projectpre' key='customer.CreateDate'/>";
   		   var  thead_length=table.tHead.rows.length;
		   var  tds=table.rows[thead_length-1];
		   var  cells=tds.cells;

           cells[0].innerHTML=cells[0].innerHTML.replace("Client Short Name",clientShortName);
           cells[1].innerHTML=cells[1].innerHTML.replace("Customer No",customerNo);
           cells[2].innerHTML=cells[2].innerHTML.replace("Customer Attribute",customerAttribute);
           cells[3].innerHTML=cells[3].innerHTML.replace("GroupCustomer No",groupCustomerNo);
           cells[4].innerHTML=cells[4].innerHTML.replace("Belonged BD",belongedBD);
           cells[5].innerHTML=cells[5].innerHTML.replace("Belonged Site",belongedSite);
           cells[6].innerHTML=cells[6].innerHTML.replace("Client Class Code",clientClassCode);
           cells[7].innerHTML=cells[7].innerHTML.replace("Client Country Code",clientCountryCode);
           cells[8].innerHTML=cells[8].innerHTML.replace("Create Date",createDate);
		
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
       tableId="QueryList"
       items="webVo"
       var="cQuery"
       action="${pageContext.request.contextPath}/customer/query/customerQueryList.do"  
       imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
       locale="<%=_language%>"
    >
        <ec:row >
             
             <ec:column headerStyle="width:100" property="short_" tooltip="${cQuery.short_}" title="Client Short Name"/>    
             <ec:column headerStyle="width:100" property="customerId" tooltip="${cQuery.customerId}" title="Customer No" /> 
             <ec:column headerStyle="width:80" property="attribute" tooltip="${cQuery.attribute}" title="Customer Attribute" />        
             <ec:column headerStyle="width:80" property="groupId" tooltip="${cQuery.groupId}" title="GroupCustomer No" />             
             <ec:column headerStyle="width:100"  property="belongBd" tooltip="${cQuery.belongBd}" title="Belonged BD" />
             <ec:column headerStyle="width:100"  property="belongSite" tooltip="${cQuery.belongSite}" title="Belonged Site" />
             <ec:column headerStyle="width:180" property="class_" tooltip="${cQuery.class_}" title="Client Class Code" />
             <ec:column headerStyle="width:180" property="country" tooltip="${cQuery.country}" title="Client Country Code" />
             <ec:column headerStyle="width:100" property="createDate" cell="date" format="yyyy-MM-dd" tooltip="${cQuery.createDate}" title="Create Date" />          
         </ec:row>
     </ec:table>
     <script type="text/javascript" language="javascript">
      autoClickFirstRow();
     </script>
     
	</body>

</html>
