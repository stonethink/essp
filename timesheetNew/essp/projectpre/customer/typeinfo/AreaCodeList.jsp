<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
   request.setAttribute("areaCodeList",areaCodeList);
%>
<html>
  <head>
        <tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>
		
	<script language="javascript" type="text/javascript">
	 var countryCode="<bean:message bundle="projectpre" key="customer.CountryCode"/>";
	 var chineseName="<bean:message bundle="projectpre" key="customer.ChineseInstruction"/>";
	 var Enable="<bean:message bundle="projectpre" key="customer.Enable"/>";
	 
	 function onAddData(){      
       var height = 150;
       var width = 350;
       var topis = (screen.height - height) / 2;
       var leftis = (screen.width - width) / 2;
       var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
	   var url='<%=contextPath%>/projectpre/customer/typeinfo/AreaCodeAdd.jsp';
	   var windowTitle="";
       window.open(url,windowTitle,option);

    }
  function onDeleteData(){
	     alert("Delete Area Code?");	  
	}
   function setAreaCodeState(chkbx) {
      //make sure that always know the state of the checkbox
       if (chkbx.checked) {
             eval('document.forms.ac_setting.chkbx_' + chkbx.name).value='SELECTED';
           } else {
                   eval('document.forms.ac_setting.chkbx_' + chkbx.name).value='UNSELECTED';
           }
      }
	
	  function setColumnTitle(){
      var cells=document.getElementById("AreaCodeListTable_table").rows[0].cells;
      cells[0].innerText=countryCode;
      cells[1].innerText=chineseName;
      cells[2].innerText=Enable;  
    }
	</script>
  </head>
  
<body topmargin="0" >
  <%
	java.util.Locale locale = (java.util.Locale)session.getAttribute(org.apache.struts.Globals.LOCALE_KEY); 	
  	String _language = locale.toString();	
  %>
   <ec:table 
       tableId="AreaCodeListTable"
       items="areaCodeList"
       var="pres"
       action="${pageContext.request.contextPath}/selectedPresidentsController.run"  
       view="compact"
       showStatusBar="false"
       showPagination="false"
       imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
       autoIncludeParameters="false"
       filterable="false"
       sortable="false"
       border="0"
       cellspacing="2"
       cellpadding="1"
       styleClass="extremecomponents.css"
       form="ac_setting"
        locale="<%=_language%>"
       >

        <ec:row onclick="onChangeBackgroundColor(this);">
             
             <ec:column style="width:30%" property="countryCode" title="Country Code"/>
             <ec:column style="width:30%" property="chineseName" title="Chinese Name"/>
             <ec:column 
                 alias="checkbox"
                 title="Enable " 
                 style="width:30%" 
                 filterable="false" 
                 sortable="false" 
                 cell="server.essp.customercode.selectedAreaCode"
                 />             
         </ec:row>
     </ec:table>
  </body>
   <script language="javascript" type="text/javascript">
     setColumnTitle();
    </script>
</html>
