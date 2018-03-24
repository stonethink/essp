<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
<%@ page import="server.essp.projectpre.service.constant.Constant" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value="Project Code Check" />
			<tiles:put name="jspName" value="" />
		</tiles:insert>
	</head>
	
	<script language="JavaScript" type="text/javascript">
   
	   function onCheckData(){
	    try{
	    var rid = currentRow.cells[0].innerText;
	    if(currentRow==null) {
	        alert("<bean:message bundle="application" key="global.confirm.selectRow"/>");
	        return;
	      }  
	     if(confirm("<bean:message bundle="application" key="global.confirm.check"/>")) {
	       window.location="<%=contextPath%>/sync/checkSyncException.do?CODE="+currentRow.selfproperty;          
	      }
		  }catch(e){} 
		}
    
	     function disableBtn(){
		  try{
		   var table=document.getElementById("appCheckList_table");
		   var thead_length=table.tHead.rows.length;
		   var firstRow=table.rows[thead_length];
		  // alert(table);
		   if(firstRow==null){
		     window.parent.checkBtn.disabled=true;
		   }else{
		     window.parent.checkBtn.disabled=false;
		   }
		   }catch(e){}
		}
		
	     //单击行时的动作，更新下帧中的数据
	    function rowOnClick(rowObj) {
		 try{
		    if(rowObj==null){
		    //alert("ok");
		    window.parent.checkBtn.disabled=true;
		    return;
		    }
			currentRow=rowObj;
		}catch(e){}
		}
	    //当进入此页时，自动选中第一行，并国际化表头
		function autoClickFirstRow(){
		   try{
		    var table=document.getElementById("appCheckList_table");
		    var str0="<bean:message bundle="projectpre" key="projectCode.ApplyRecordList.ApplyNo"/>";
	        var str1="<bean:message bundle="projectpre" key="projectCode.MasterData.ProjectCode"/>";
			var str2="<bean:message bundle="projectpre" key="projectCode.PopCodeConfirm.ProjectName"/>";
			var str3="<bean:message bundle="projectpre" key="sync.model"/>";
			var str4="<bean:message bundle="projectpre" key="projectCode.CodeCheckDetail.ProcessType"/>";
			
			var thead_length=table.tHead.rows.length;
			var tds=table.rows[thead_length-1];
			var cells=tds.cells;
			//使EC标签的表头国际化
			
			cells[0].innerHTML=cells[0].innerHTML.replace("ApplyNo",str0);
			cells[0].title = cells[0].title.replace("ApplyNo",str0);
			cells[1].innerHTML=cells[1].innerHTML.replace("Project Code",str1);
			cells[1].title = cells[1].title.replace("Project Code",str1);
			cells[2].innerHTML=cells[2].innerHTML.replace("Project Name",str2);
			cells[2].title = cells[2].title.replace("Project Name",str2);
			cells[3].innerHTML=cells[3].innerHTML.replace("Model",str3);
			cells[3].title = cells[3].title.replace("Model",str3);
			cells[4].innerHTML=cells[4].innerHTML.replace("Process Type",str4);
			cells[4].title = cells[4].title.replace("Process Type",str4);
		
			var firstRow=table.rows[thead_length];
			rowOnClick(firstRow);
		    if(table.rows.length>thead_length){
		       //如果有数据
			   onChangeBackgroundColor(firstRow);
		    }
		    }catch(e){}
		}
		
  
   </script>
	<body >
  <%
	java.util.Locale locale = (java.util.Locale)session.getAttribute(org.apache.struts.Globals.LOCALE_KEY); 	
  	String _language = locale.toString();	
  %>
	<ec:table 
		tableId="appCheckList"
		items="webVo" 
		var="aCheck" scope="request" 
		action="${pageContext.request.contextPath}/sync/listSyncException.do" 
		imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif" 
		locale="<%=_language%>"
	>
		<ec:row onclick="rowOnClick(this);"  selfproperty="${aCheck.rid}">
			<ec:column style="width:20%" property="rid" tooltip="${aCheck.rid}" title="ApplyNo"/>	
			<ec:column style="width:20%" property="acntId" tooltip="${aCheck.acntId}" title="Project Code"/>	
			<ec:column style="width:20%" property="acntName" tooltip="${aCheck.acntName}"  title="Project Name"/>
			<ec:column style="width:20%" property="model" tooltip="${aCheck.model}" title="Model"/>						
			<ec:column style="width:20%" property="type" tooltip="${aCheck.type}" title="Process Type"/>
			
		</ec:row>
	</ec:table>
	
	</body>
	 <script language="javascript" type="text/javascript">
        autoClickFirstRow();
     </script>
</html>
