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
       var currentRow=null;
		function onUpdateData(){
		 var height =400;
         var width = 750;
         var topis = (screen.height - height) / 2;
         var leftis = (screen.width - width) / 2;
         var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
		 var url="<%=request.getContextPath()%>/dept/initUpdateDeptInfo.do?unitCode="+currentRow.selfproperty;
		 var windowTitle="";
         window.open(url,windowTitle,option);
		}
		
		function onViewData(){
		 var height =400;
         var width = 650;
         var topis = (screen.height - height) / 2;
         var leftis = (screen.width - width) / 2;
         var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
		 var url="<%=request.getContextPath()%>/dept/displayDeptInfo.do?unitCode="+currentRow.selfproperty;
		 var windowTitle="";
         window.open(url,windowTitle,option);
		}
		
		function onDeleteData(){
		 if(currentRow==null) {
          alert("<bean:message bundle="application" key="global.confirm.selectRow"/>");
          return;
          }  
         if(confirm("<bean:message bundle="application" key="global.confirm.delete"/>")) {
    	  window.location="<%=contextPath%>/dept/deleteDept.do?deptCode="+currentRow.selfproperty;
          }
		}
		function autoClickFirstRow(){	
		  try{
		   var  table=document.getElementById("deptCodeList_table");
           var  str0="<bean:message bundle="hrbase" key="hrbase.dept.deptcode"/>";
           var  str1="<bean:message bundle="hrbase" key="hrbase.dept.deptname"/>";
           var  str2="<bean:message bundle="hrbase" key="hrbase.dept.deptmanager"/>";
           var  str3="<bean:message bundle="hrbase" key="hrbase.dept.bdmanager"/>";           
           var  str4="<bean:message bundle="hrbase" key="hrbase.dept.tcsinger"/>";
           var  str5="<bean:message bundle="hrbase" key="hrbase.dept.parentdeptcode"/>";
           var  str6="<bean:message bundle="hrbase" key="hrbase.dept.belongsite"/>";
   	       var  thead_length=table.tHead.rows.length;
	       var  tds=table.rows[thead_length-1];
		   var  cells=tds.cells;
           cells[0].innerHTML=cells[0].innerHTML.replace("acntId",str0);
           cells[1].innerHTML=cells[1].innerHTML.replace("acntName",str1);
           cells[2].innerHTML=cells[2].innerHTML.replace("deptManager",str2);
           cells[3].innerHTML=cells[3].innerHTML.replace("BDMName",str3);
           cells[4].innerHTML=cells[4].innerHTML.replace("TCSName",str4);
           cells[5].innerHTML=cells[5].innerHTML.replace("parentUnitCode",str5);
           cells[6].innerHTML=cells[6].innerHTML.replace("belongSite",str6);
           var firstRow=table.rows[thead_length];
           disableBtn();
           rowOnClick(firstRow);
           if(table.rows.length>thead_length){
		        onChangeBackgroundColor(firstRow);
	       } 
	       }catch(e){}
       }
       
     function disableBtn(){
	  try{
	   var table=document.getElementById("deptCodeList_table");
	   var thead_length=table.tHead.rows.length;
	   var firstRow=table.rows[thead_length];
	   if(firstRow==null){
	     window.parent.updateBtn.disabled=true;
	     window.parent.delBtn.disabled=true;
	     window.parent.displayBtn.disabled=true;
	   }else{
	     window.parent.updateBtn.disabled=false;
	     window.parent.delBtn.disabled=false;
	     window.parent.displayBtn.disabled=false;
	   }
	   }catch(e){}
	}
	
	//单击行时的动作，更新下帧中的数据
    function rowOnClick(rowObj) {
     try{
	if(rowObj==null){
		window.parent.onRefreshData("");
	} else {
	   currentRow=rowObj;
	   var code=rowObj.selfproperty;
	   window.parent.onRefreshData(code);
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
       tableId="deptCodeList"
       items="webVo"
       var="dQuery"
       action="${pageContext.request.contextPath}/dept/query/deptList.do"  
       imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
        locale="<%=_language%>"
    >
        <ec:row onclick="rowOnClick(this);" selfproperty="${dQuery.unitCode}">
            <ec:column headerStyle="width:70" property="unitCode" tooltip="${dQuery.unitCode}" title="acntId"/>    
            <ec:column headerStyle="width:130" property="unitName" tooltip="${dQuery.unitName}" title="acntName" />
            <ec:column headerStyle="width:80"  property="dmName" tooltip="${dQuery.dmName}" title="deptManager" />
            <ec:column headerStyle="width:80" property="bdName" tooltip="${dQuery.bdName}" title="BDMName" />             
            <ec:column headerStyle="width:80"  property="tsName" tooltip="${dQuery.tsName}" title="TCSName" />
            <ec:column headerStyle="width:100" property="parentUnitCode" tooltip="${dQuery.parentUnitCode}" title="parentUnitCode" />
            <ec:column headerStyle="width:80" property="belongSite" tooltip="${dQuery.belongSite}" title="belongSite" />
         </ec:row>
     </ec:table>
       <script type="text/javascript" language="javascript">
       autoClickFirstRow();
     </script>
	</body>

</html>
